/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.spring.vfs;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;

import org.jboss.logging.Logger;
import org.springframework.core.io.Resource;
import org.springframework.util.PathMatcher;


/**
 * Helper class implementing class-path scanning pattern resolution strategy for
 * the VFS filesystem.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 *         <p/>
 *         Note: Thanks to David Ward for providing a fix for resource path matching.
 */
public class VFSResourcePatternResolvingHelper {

    private static Logger log = Logger.getLogger(VFSResourcePatternResolvingHelper.class);

    public static Resource[] locateResources(String locationPattern, String rootDirPath, ClassLoader classLoader,
                                             PathMatcher pathMatcher, boolean oneMatchingRootOnly)
            throws IOException {
        String subPattern = locationPattern.substring(rootDirPath.length());
        if (rootDirPath.startsWith("/")) {
            rootDirPath = rootDirPath.substring(1);
        }

        List<Resource> resources = new ArrayList<Resource>();
        Enumeration<URL> urls = classLoader.getResources(rootDirPath);
        if (!oneMatchingRootOnly) {
            while (urls.hasMoreElements()) {
                resources.addAll(getVFSResources(urls.nextElement(), subPattern, pathMatcher));
            }
        } else {
            resources.addAll(getVFSResources(classLoader.getResource(rootDirPath), subPattern, pathMatcher));
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    /**
     * Get VFS resources.
     *
     * @param rootURL     the root URL
     * @param subPattern  the sub pattern
     * @param pathMatcher the PathMatcher used for matching directories
     * @return vfs resources list
     * @throws java.io.IOException for any error
     */
    public static Set<Resource> getVFSResources(URL rootURL, String subPattern, PathMatcher pathMatcher) throws IOException {
        log.debug("Scanning url: " + rootURL + ", sub-pattern: " + subPattern);
        Object root = VFSResource.getChild(rootURL);
        String pathName = VFSUtil.invokeVfsMethod(VFSUtil.VIRTUAL_FILE_METHOD_GET_PATH_NAME, root);
        PatternVirtualFileVisitorInvocationHandler visitorInvocationHandler = new PatternVirtualFileVisitorInvocationHandler(pathName, subPattern, pathMatcher);
        Object visitor = Proxy.newProxyInstance(VFSUtil.VIRTUAL_FILE_VISITOR_CLASS.getClassLoader(),
                new Class<?>[]{VFSUtil.VIRTUAL_FILE_VISITOR_CLASS}, visitorInvocationHandler);
        VFSUtil.invokeVfsMethod(VFSUtil.VIRTUAL_FILE_METHOD_VISIT, root, visitor);
        if (log.isTraceEnabled()) {
            log.trace("Found resources: " + visitor);
        }
        return visitorInvocationHandler.getResources();
    }


    protected static class PatternVirtualFileVisitorInvocationHandler implements InvocationHandler {

        private final String subPattern;

        private final Set<Resource> resources = new HashSet<Resource>();

        private final PathMatcher pathMatcher;

        private final String rootPath;

        private PatternVirtualFileVisitorInvocationHandler(String rootPath, String subPattern, PathMatcher pathMatcher) {
            this.subPattern = subPattern;
            this.pathMatcher = pathMatcher;
            this.rootPath = rootPath.length() == 0 || rootPath.endsWith("/") ? rootPath : rootPath + "/";
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (Object.class.equals(method.getDeclaringClass())) {
                if (methodName.equals("equals")) {
                    // Only consider equal when proxies are identical.
                    return (proxy == args[0]);
                } else if (methodName.equals("hashCode")) {
                    return System.identityHashCode(proxy);
                } else if ("toString".equals(methodName)) {
                    return toString();
                }
            } else if ("getAttributes".equals(methodName)) {
                return getAttributes();
            } else if ("visit".equals(methodName)) {
                visit(args[0]);
                return null;
            } else if ("toString".equals(methodName)) {
                return toString();
            }

            throw new IllegalStateException("Unexpected method invocation: " + method);
        }

        private Object getAttributes() throws IllegalAccessException {
            return VFSUtil.VISITOR_ATTRIBUTES_FIELD_RECURSE.get(null);
        }

        private void visit(Object vf) throws IOException {
            if (pathMatcher.match(subPattern, VFSUtil.<String>invokeVfsMethod(VFSUtil.VIRTUAL_FILE_METHOD_GET_PATH_NAME, vf).substring(rootPath.length()))) {
                resources.add(new VFSResource(vf));
            }
        }

        public Set<Resource> getResources() {
            return resources;
        }

        public int size() {
            return resources.size();
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("sub-pattern: ").append(subPattern);
            buffer.append(", resources: ").append(resources);
            return buffer.toString();
        }
    }
}
