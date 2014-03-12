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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class SpringResourceLoadingTestCase {

    @Test
    public void testLoadResourceFromClasspath() {
        VFSXmlWebApplicationContext context = new VFSXmlWebApplicationContext();
        context.setConfigLocations(new String[]{"classpath:spring-config/spring-app-config.xml"});
        context.refresh();
        @SuppressWarnings("unused")
		String myBean = (String) context.getBean("myBean");
    }


    @Test
    public void testLoadResourceFromClasspathWildCard() {
        VFSXmlWebApplicationContext context = new VFSXmlWebApplicationContext();
        context.setConfigLocations(new String[]{"classpath*:spring-config/spring-app-config.xml"});
        context.refresh();
        @SuppressWarnings("unused")
        String myBean = (String) context.getBean("myBean");
    }

    @Test
    public void testLoadWildCardedResourceFromClasspath() {
        VFSXmlWebApplicationContext context = new VFSXmlWebApplicationContext();
        context.setConfigLocations(new String[]{"classpath:/spring-config/spring-*.xml"});
        context.refresh();
        @SuppressWarnings("unused")
        String myBean = (String) context.getBean("myBean");
    }

    @Test
    public void testLoadWildCardedResourceFromClasspathWildCard() {
        VFSXmlWebApplicationContext context = new VFSXmlWebApplicationContext();
        context.setConfigLocations(new String[]{"classpath*:/spring-config/spring-*.xml"});
        context.refresh();
        @SuppressWarnings("unused")
        String myBean = (String) context.getBean("myBean");
        Resource r = context.getResource("classpath:/spring-config/spring-app-config.xml");
        Assert.assertNotNull(r);
    }

    @Test
    public void testLoadImportLocally() {
        VFSXmlWebApplicationContext context = new VFSXmlWebApplicationContext();
        context.setConfigLocations(new String[]{"classpath:/org/jboss/spring/vfs/context/spring-*.xml"});
        context.refresh();
        @SuppressWarnings("unused")
        String myBean = (String) context.getBean("myBean");
        Resource r = context.getResource("classpath:/spring-config/spring-app-config.xml");
        Assert.assertNotNull(r);
    }

}
