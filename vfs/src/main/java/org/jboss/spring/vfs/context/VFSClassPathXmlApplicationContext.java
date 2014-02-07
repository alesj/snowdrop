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
package org.jboss.spring.vfs.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.Resource;
import org.jboss.spring.vfs.VFSResourcePatternResolver;
import org.jboss.spring.vfs.VFSResourceLoader;

/**
 * {@link org.springframework.context.support.ClassPathXmlApplicationContext} variant
 * adding support for classpath scanning in JBoss AS 5, using the VFS file system.
 *
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class VFSClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

    public VFSClassPathXmlApplicationContext() {
        super();
    }

    public VFSClassPathXmlApplicationContext(ApplicationContext parent) {
        super(parent);
    }

    public VFSClassPathXmlApplicationContext(String configLocation) throws BeansException {
        super(configLocation);
    }

    public VFSClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        super(configLocations);
    }

    public VFSClassPathXmlApplicationContext(String[] configLocations, ApplicationContext parent) throws BeansException {
        super(configLocations, parent);
    }

    public VFSClassPathXmlApplicationContext(String[] configLocations, boolean refresh) throws BeansException {
        super(configLocations, refresh);
    }

    public VFSClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent) throws BeansException {
        super(configLocations, refresh, parent);
    }

    public VFSClassPathXmlApplicationContext(String path, Class<?> clazz) throws BeansException {
        super(path, clazz);
    }

    public VFSClassPathXmlApplicationContext(String[] paths, Class<?> clazz) throws BeansException {
        super(paths, clazz);
    }

    public VFSClassPathXmlApplicationContext(String[] paths, Class<?> clazz, ApplicationContext parent) throws BeansException {
        super(paths, clazz, parent);
    }

    protected ResourcePatternResolver getResourcePatternResolver() {
        return new VFSResourcePatternResolver(new VFSResourceLoader(getClassLoader()));
    }

    @Override
    public Resource getResource(String location) {
        return getResourcePatternResolver().getResource(location);
    }
}

