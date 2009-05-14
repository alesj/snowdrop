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
import java.net.URL;
import java.util.*;

import org.jboss.logging.Logger;
import org.jboss.virtual.VFS;
import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.VirtualFileVisitor;
import org.jboss.virtual.VisitorAttributes;
import org.springframework.core.io.Resource;
import org.springframework.util.PathMatcher;


/**
 * Helper class implementing class-path scanning pattern resolution strategy for
 * the VFS filesystem.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 *
 * Note: Thanks to David Ward for providing a fix for resource path matching.
 */
public class VFSResourcePatternResolvingHelper
{
    private static Logger log = Logger.getLogger(VFSResourcePatternResolvingHelper.class);

    public static Resource[] locateResources(String locationPattern, String rootDirPath, ClassLoader classLoader,
                                             PathMatcher pathMatcher, boolean oneMatchingRootOnly)
            throws IOException
    {
        String subPattern = locationPattern.substring(rootDirPath.length());
        if (rootDirPath.startsWith("/"))
            rootDirPath = rootDirPath.substring(1);

        List<Resource> resources = new ArrayList<Resource>();
        Enumeration<URL> urls = classLoader.getResources(rootDirPath);
        if (!oneMatchingRootOnly)
        {
            while (urls.hasMoreElements())
                resources.addAll(getVFSResources(urls.nextElement(), subPattern, pathMatcher));
        } else
        {
            resources.addAll(getVFSResources(classLoader.getResource(rootDirPath), subPattern, pathMatcher));
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    /**
     * Get VFS resources.
     *
     * @param rootURL    the root URL
     * @param subPattern the sub pattern
     * @param pathMatcher the PathMatcher used for matching directories
     * @return vfs resources list
     * @throws java.io.IOException for any error
     */
    public static Set<Resource> getVFSResources(URL rootURL, String subPattern, PathMatcher pathMatcher) throws IOException
    {
        log.debug("Scanning url: " + rootURL + ", sub-pattern: " + subPattern);
        VirtualFile root = VFS.getRoot(rootURL);
        PatternVirtualFileVisitor visitor = new PatternVirtualFileVisitor(root.getPathName(), subPattern, pathMatcher);
        root.visit(visitor);
        if (log.isTraceEnabled())
            log.trace("Found resources: " + visitor);
        return visitor.getResources();
    }

    protected static class PatternVirtualFileVisitor implements VirtualFileVisitor
    {
        private final String subPattern;
        private final Set<Resource> resources = new HashSet<Resource>();
        private final PathMatcher pathMatcher;
        private final String rootPath;

        private PatternVirtualFileVisitor(String rootPath, String subPattern, PathMatcher pathMatcher)
        {
            this.subPattern = subPattern;
            this.pathMatcher = pathMatcher;
            this.rootPath = rootPath.length() == 0 || rootPath.endsWith("/") ? rootPath : rootPath + "/";
        }

        public VisitorAttributes getAttributes()
        {
            return VisitorAttributes.RECURSE_LEAVES_ONLY;
        }

        public void visit(VirtualFile vf)
        {
            if (pathMatcher.match(subPattern, vf.getPathName().substring(rootPath.length())))
                resources.add(new VFSResource(vf));
        }

        public Set<Resource> getResources()
        {
            return resources;
        }

        public int size()
        {
            return resources.size();
        }

        public String toString()
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("sub-pattern: ").append(subPattern);
            buffer.append(", resources: ").append(resources);
            return buffer.toString();
        }
    }
}
