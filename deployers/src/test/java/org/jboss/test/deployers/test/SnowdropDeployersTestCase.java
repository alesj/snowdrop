package org.jboss.test.deployers.test;

import junit.framework.Test;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.test.deployers.BootstrapDeployersTest;
import org.jboss.util.naming.NonSerializableFactory;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;


/**
 * @author Marius Bogoevici
 */
public class SnowdropDeployersTestCase extends BootstrapDeployersTest
{
   public SnowdropDeployersTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(SnowdropDeployersTestCase.class);
   }

   public void testSimpleEar() throws Exception
   {
      System.setProperty("java.naming.factory.initial", MockInitialContextFactory.class.getName());
      VirtualFile ear = VFS.getChild("multiplefiles-top-level.ear");
      createAssembledDirectory(ear).addPath("multiplefiles");
      VFSDeploymentUnit unit = assertDeploy(ear);
      assertNotNull(NonSerializableFactory.lookup("TestContext1"));
      assertNotNull(NonSerializableFactory.lookup("TestContext2"));
      undeploy(unit);
      assertNull(NonSerializableFactory.lookup("TestContext1"));
      assertNull(NonSerializableFactory.lookup("TestContext2"));
   }
}
