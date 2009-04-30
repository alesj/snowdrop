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
package org.jboss.spring.facade;

import org.jboss.kernel.plugins.bootstrap.basic.BasicBootstrap;
import org.jboss.kernel.plugins.deployment.xml.BasicXMLDeployer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.UrlResource;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class KernelControllerListableBeanFactoryTestCase
{               

    @Test
    public void testKernelControllerAssignableAsParentBeanFactory() throws Throwable
    {
        BasicBootstrap bootstrap = new BasicBootstrap();
        bootstrap.run();
        BasicXMLDeployer deployer = new BasicXMLDeployer(bootstrap.getKernel());
        BeanFactory parentBeanFactory = new KernelControllerListableBeanFactory(bootstrap.getKernel().getController());
        deployer.deploy(KernelControllerListableBeanFactory.class.getResource("microcontainer-beans.xml"));
        BeanFactory beanFactory = new XmlBeanFactory(new UrlResource(KernelControllerListableBeanFactory.class.getResource("spring-beans.xml")), parentBeanFactory);
        ComplexService service = (ComplexService) beanFactory.getBean("service");
        Assert.assertEquals("Result is: 2", service.processNumbers(1, 1));
        deployer.shutdown();
    }

    @Test(expected = BeanCreationException.class)
    public void testConfigurationFailsIfParentBeanMissing() throws Throwable
    {
        BasicBootstrap bootstrap = new BasicBootstrap();
        bootstrap.run();
        BasicXMLDeployer deployer = new BasicXMLDeployer(bootstrap.getKernel());
        BeanFactory parentBeanFactory = new KernelControllerListableBeanFactory(bootstrap.getKernel().getController());
        deployer.deploy(KernelControllerListableBeanFactory.class.getResource("microcontainer-beans-2.xml"));
        BeanFactory beanFactory = new XmlBeanFactory(new UrlResource(KernelControllerListableBeanFactory.class.getResource("spring-beans.xml")), parentBeanFactory);
        ComplexService service = (ComplexService) beanFactory.getBean("service");
        Assert.assertEquals("Result is: 2", service.processNumbers(1, 1));
        deployer.shutdown();
    }
    
}
