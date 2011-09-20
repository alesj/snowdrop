package org.jboss.test.deployers.test;

import junit.framework.Test;
import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.deployers.BootstrapDeployersTest;
import org.jboss.util.naming.NonSerializableFactory;
import org.jboss.virtual.AssembledDirectory;
import org.junit.Assert;


/**
 * @author Marius Bogoevici
 */
public class SnowdropDeployersTestCaseVfs2 extends BootstrapDeployersTest {

    public SnowdropDeployersTestCaseVfs2(String name) {
        super(name);
    }

    public static Test suite() {
        return AbstractTestCaseWithSetup.suite(SnowdropDeployersTestCaseVfs2.class);
    }

    public void testSimpleEar() throws Exception {
        System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
        AssembledDirectory ear = createAssembledDirectory("multiplefiles-top-level.ear", "multiplefiles-top-level.ear");
        ear.mkdir("META-INF");
        addPath(ear, "multiplefiles", "META-INF");
        VFSDeploymentUnit unit = assertDeploy(ear);
        assertNotNull(NonSerializableFactory.lookup("TestContext1"));
        assertNotNull(NonSerializableFactory.lookup("TestContext2"));
        undeploy(unit);
        assertNull(NonSerializableFactory.lookup("TestContext1"));
        assertNull(NonSerializableFactory.lookup("TestContext2"));
    }

    public void testOverlappingJndiNames() throws Exception {
        VFSDeploymentUnit unit = null;
        System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
        AssembledDirectory ear = createAssembledDirectory("multiplefiles-top-level.ear", "multiplefiles-top-level.ear");
        ear.mkdir("META-INF");
        addPath(ear, "overlappingnames", "META-INF");
        try {
            unit = assertDeploy(ear);
            fail();
        } catch (DeploymentException e) {
            // ignore, we are expecting this
        }
        undeploy(unit);
        Assert.assertNull(NonSerializableFactory.lookup("TestContext"));
    }

    public void testPreexistingJndiNames() throws Exception {
        VFSDeploymentUnit unit = null;
        System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
        AssembledDirectory ear = createAssembledDirectory("multiplefiles-top-level.ear", "multiplefiles-top-level.ear");
        ear.mkdir("META-INF");
        addPath(ear, "preexisting", "META-INF");
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
