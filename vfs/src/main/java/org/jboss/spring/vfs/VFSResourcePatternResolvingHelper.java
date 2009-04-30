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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
 */
public class VFSResourcePatternResolvingHelper
{

    private static Logger log = Logger.getLogger(VFSResourcePatternResolvingHelper.class);

    public static Resource[] locateResources(String locationPattern, String rootDirPath, ClassLoader classLoader, PathMatcher pathMatcher)
            throws IOException
    {
        String subPattern = locationPattern.substring(rootDirPath.length());
        if (rootDirPath.startsWith("/"))
            rootDirPath = rootDirPath.substring(1);

        List<Resource> resources = new ArrayList<Resource>();
        Enumeration<URL> urls = classLoader.getResources(rootDirPath);
        while (urls.hasMoreElements())
            resources.addAll(getVFSResources(urls.nextElement(), subPattern, pathMatcher));

        return resources.toArray(new Resource[resources.size()]);
    }

    /**
     * Get VFS resources.
     *
     * @param rootURL    the root URL
     * @param subPattern the sub pattern
     * @return vfs resources list
     * @throws java.io.IOException for any error
     */
    public static List<Resource> getVFSResources(URL rootURL, String subPattern, PathMatcher pathMatcher) throws IOException
    {
        log.debug("Scanning url: " + rootURL + ", sub-pattern: " + subPattern);
        VirtualFile root = VFS.getRoot(rootURL);
        PatternVirtualFileVisitor visitor = new PatternVirtualFileVisitor(subPattern, pathMatcher);
        root.visit(visitor);
        if (log.isTraceEnabled())
            log.trace("Found resources: " + visitor);
        return visitor.getResources();
    }

    protected static class PatternVirtualFileVisitor implements VirtualFileVisitor
    {
        private final String subPattern;
        private final List<Resource> resources = new ArrayList<Resource>();
        private final PathMatcher pathMatcher;

        private PatternVirtualFileVisitor(String subPattern, PathMatcher pathMatcher)
        {
            this.subPattern = subPattern;
            this.pathMatcher = pathMatcher;
        }

        public VisitorAttributes getAttributes()
        {
            return VisitorAttributes.RECURSE_LEAVES_ONLY;
        }

        public void visit(VirtualFile vf)
        {
            if (pathMatcher.match(subPattern, vf.getPathName()))
                resources.add(new VFSResource(vf));
        }

        public List<Resource> getResources()
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
