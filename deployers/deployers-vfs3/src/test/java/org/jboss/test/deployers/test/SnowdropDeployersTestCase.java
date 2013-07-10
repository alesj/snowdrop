/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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

package org.jboss.test.deployers.test;

import junit.framework.Test;
import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.structure.spi.DeploymentContext;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.deployers.BootstrapDeployersTest;
import org.jboss.util.naming.NonSerializableFactory;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.junit.Assert;

import java.util.Collection;


/**
 * @author Marius Bogoevici
 */
public class SnowdropDeployersTestCase extends BootstrapDeployersTest {

    public SnowdropDeployersTestCase(String name) {
        super(name);
    }

    public static Test suite() {
        return AbstractTestCaseWithSetup.suite(SnowdropDeployersTestCase.class);
    }

    public void testSimpleEar() throws Exception {
        System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
        VirtualFile ear = VFS.getChild("multiplefiles-top-level.ear");
        createAssembledDirectory(ear).addPath("multiplefiles");

        //Check if there are any lingering deployments
        Collection<DeploymentContext> deploymentContexts = getMainDeployerInternals().getAll();
        for (DeploymentContext context: deploymentContexts){
            // Undeploy them if so
            undeploy(context.getDeploymentUnit());
        }

        VFSDeploymentUnit unit = assertDeploy(ear);
        Assert.assertNotNull(NonSerializableFactory.lookup("TestContext1"));
        Assert.assertNotNull(NonSerializableFactory.lookup("TestContext2"));
        undeploy(unit);
        Assert.assertNull(NonSerializableFactory.lookup("TestContext1"));
        Assert.assertNull(NonSerializableFactory.lookup("TestContext2"));
    }

    public void testOverlappingJndiNames() throws Exception {
        VFSDeploymentUnit unit = null;
        System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
        VirtualFile ear = VFS.getChild("multiplefiles-top-level.ear");
        createAssembledDirectory(ear).addPath("overlappingnames");

        try {
            unit = assertDeploy(ear);
            fail();
        } catch (DeploymentException e) {
            // ignore, we are expecting this
        }
        undeploy(unit);
        Assert.assertNull(NonSerializableFactory.lookup("TestContextOverlap"));
    }

    public void testPreExistingBindings() throws Exception {
        VFSDeploymentUnit unit = null;
        System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
        VirtualFile ear = VFS.getChild("multiplefiles-top-level.ear");
        createAssembledDirectory(ear).addPath("preexisting");
        Object preboundObject = new Object();
        NonSerializableFactory.bind("TestContext", preboundObject);

        try {
            unit = assertDeploy(ear);
            fail();
        } catch (DeploymentException e) {
            // ignore, we are expecting this
        }
        undeploy(unit);
        Object lookedUpObject = NonSerializableFactory.lookup("TestContext");
        Assert.assertNotNull(lookedUpObject);
        Assert.assertTrue(preboundObject == lookedUpObject);
    }
}
