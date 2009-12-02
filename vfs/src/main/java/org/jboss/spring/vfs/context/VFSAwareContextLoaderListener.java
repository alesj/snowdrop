package org.jboss.spring.vfs.context;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * A VFS-compatible {@link org.springframework.web.context.ContextLoaderListener},
 * which works transparently in or outside JBoss AS.
 *
 * It detects whether the current runtime is JBoss AS in a version later than 5.0.0,
 * and if that is the case, instantiates a {@link VFSXmlWebApplicationContext}, otherwise
 * it instantiates a {@link org.springframework.web.context.support.XmlWebApplicationContext}.
 *  
 * @author Marius Bogoevici
 */
public class VFSAwareContextLoaderListener extends ContextLoaderListener
{

   @Override
   protected ContextLoader createContextLoader()
   {
      if (!ContextClassUtil.isJBossAS5orHigher())
      {
         return super.createContextLoader();
      }
      else
      {
         return new VFSContextLoader();
      }
   }

   private static class VFSContextLoader extends ContextLoader
   {
      @Override
      protected Class determineContextClass(ServletContext servletContext) throws ApplicationContextException
      {
         return ContextClassUtil.getVFSWebContextClass();

      }
   }
}
