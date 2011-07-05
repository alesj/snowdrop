package org.jboss.test.deployers.test;

import junit.framework.Test;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.deployers.BootstrapDeployersTest;
import org.jboss.util.naming.NonSerializableFactory;
import org.jboss.virtual.AssembledDirectory;


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

}
