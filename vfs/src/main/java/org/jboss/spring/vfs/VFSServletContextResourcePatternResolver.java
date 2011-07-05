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

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;

/**
 * VFS based ServletContextResourcePatternResolver
 *
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class VFSServletContextResourcePatternResolver extends ServletContextResourcePatternResolver {

    public VFSServletContextResourcePatternResolver(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    protected Resource[] findPathMatchingResources(String locationPattern) throws IOException {
        if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
            locationPattern = locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length());
            String rootDirPath = determineRootDir(locationPattern);
            return VFSResourcePatternResolvingHelper.locateResources(locationPattern, rootDirPath, getClassLoader(), getPathMatcher(), false);
        }
        if (locationPattern.startsWith(CLASSPATH_URL_PREFIX)) {
            locationPattern = locationPattern.substring(CLASSPATH_URL_PREFIX.length());
            String rootDirPath = determineRootDir(locationPattern);
            return VFSResourcePatternResolvingHelper.locateResources(locationPattern, rootDirPath, getClassLoader(), getPathMatcher(), true);
        } else {
            return super.findPathMatchingResources(locationPattern);
        }
    }

    protected Resource convertClassLoaderURL(URL url) {
        try {
            Object file = VFSUtil.invokeVfsMethod(VFSUtil.VFS_METHOD_GET_ROOT_URL, null, url);
            return new VFSResource(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
