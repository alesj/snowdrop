package org.jboss.test.deployers.test;

import junit.framework.Test;
import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.deployers.BootstrapDeployersTest;
import org.jboss.util.naming.NonSerializableFactory;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.junit.Assert;


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
        Assert.assertNull(NonSerializableFactory.lookup("TestContext"));
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
