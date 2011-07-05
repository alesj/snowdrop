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
package org.jboss.spring.vfs.context;

import org.jboss.spring.vfs.VFSResourceLoader;
import org.jboss.spring.vfs.VFSServletContextResourcePatternResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * XmlWebApplicationContext variant adding support for classpath
 * scanning in JBoss AS 5, using the VFS file system.
 *
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class VFSXmlWebApplicationContext extends XmlWebApplicationContext {

    @Override
    protected ResourcePatternResolver getResourcePatternResolver() {
        return new VFSServletContextResourcePatternResolver(
                new WebApplicationContextAwareVFSResourceLoader(getClassLoader()));
    }

    /**
     * Customization of {@link VFSResourceLoader} that delegates to the owner class
     * (i.e. XmlWebApplicationContext) for retrieving resources by path, allowing
     * for ServletContextResources to be returned in this case.
     */
    private class WebApplicationContextAwareVFSResourceLoader extends VFSResourceLoader {

        WebApplicationContextAwareVFSResourceLoader(ClassLoader classLoader) {
            super(classLoader);
        }

        @Override
        protected Resource getResourceByPath(String path) {
            return VFSXmlWebApplicationContext.this.getResourceByPath(path);
        }
    }

    @Override
    public Resource getResource(String location) {
        return getResourcePatternResolver().getResource(location);
    }
}

