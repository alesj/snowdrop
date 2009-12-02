package org.jboss.spring.vfs.context;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * A VFS-compatible {@link org.springframework.web.servlet.DispatcherServlet},
 * which works transparently in or outside JBoss AS.
 *
 * It detects whether the current runtime is JBoss AS in a version later than 5.0.0,
 * and if that is the case, instantiates a {@link VFSXmlWebApplicationContext}, otherwise
 * it instantiates a {@link org.springframework.web.context.support.XmlWebApplicationContext}.
 *
 * @author Marius Bogoevici
 */
public class VFSAwareDispatcherServlet extends DispatcherServlet
{

   @Override
   public Class getContextClass()
   {
      if (ContextClassUtil.isJBossAS5orHigher())
         return ContextClassUtil.getVFSWebContextClass();
      else
         return super.getContextClass();
   }
}
