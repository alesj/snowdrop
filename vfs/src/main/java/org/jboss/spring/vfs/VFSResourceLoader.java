/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * VFS based ResourceLoader.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class VFSResourceLoader extends DefaultResourceLoader {

    public VFSResourceLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return doGetResourceForLocation(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            return super.getResource(location);
        }
    }

    protected Resource getResourceByPath(String path) {
        return doGetResourceForLocation(path);
    }

    private Resource doGetResourceForLocation(String path) {
        URL url = getClassLoader().getResource(path);
        if (url != null) {
            // TejasM: Snowdrop 52, 17/06/2013: With AS7+ not all files are uploaded using JBoss VFS
            // So we check whether the url protocols are vfs or not, if not then delegate to Spring for resource loading in this case.
            if (url.getProtocol().contains("vfs")) {
                return new VFSResource(url);
            } else {
                return super.getResource(CLASSPATH_URL_PREFIX+path);
            }
        } else {
            return new InexistentResource(path);
        }
    }

    /* A special type of resource, for the case when the resource does not exist */
    private static class InexistentResource extends AbstractResource {

        private final String path;

        private InexistentResource(String path) {
            this.path = path;
        }

        public String getDescription() {
            return null;
        }

        public InputStream getInputStream() throws IOException {
            throw new FileNotFoundException("Resource does not exist for path " + path);
        }

        public boolean exists() {
            return false;
        }
    }
}