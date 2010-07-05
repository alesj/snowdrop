package org.jboss.test.deployers.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * @author Marius Bogoevici
 */
public class MockInitialContextFactory implements InitialContextFactory
{
   public MockInitialContextFactory()
   {
   }

   public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException
   {
      return new MockContext();
   }

}
