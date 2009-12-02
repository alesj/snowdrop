package org.jboss.spring.vfs.context;

import org.springframework.context.ApplicationContextException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author Marius Bogoevici
 */
public class ContextClassUtil
{
   static final String VFS_APPLICATION_CONTEXT_CLASS_NAME = "org.jboss.spring.vfs.context.VFSXmlWebApplicationContext";

   public static boolean isJBossAS5orHigher()
   {
      try
      {
         Class jBossVersionClass = VFSAwareContextLoaderListener.class.getClassLoader().loadClass("org.jboss.Version");
         Object versionObject = ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(jBossVersionClass, "getInstance"), null);
         Integer majorVersion = (Integer) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(jBossVersionClass, "getMajor"), versionObject);
         // For JBoss AS versions 5 and higher
         if (majorVersion >= 5) {
            return true;
         }
      }
      catch (ClassNotFoundException e)
      {
         // do nothing;
      }
      return false;
   }

   public static Class getVFSWebContextClass()
   {
      try
      {
            return ClassUtils.forName(VFS_APPLICATION_CONTEXT_CLASS_NAME);
      }
      catch (ClassNotFoundException ex)
      {
         throw new ApplicationContextException(
               "Failed to load custom context class [" + VFS_APPLICATION_CONTEXT_CLASS_NAME + "]", ex);
      }
   }
}
