/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.instrument.classloading;

import java.lang.reflect.Method;

import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloader.spi.base.BaseClassLoader;
import org.jboss.classloader.spi.base.BaseClassLoaderDomain;
import org.jboss.classloader.spi.base.BaseClassLoaderPolicy;
import org.jboss.classloader.spi.base.BaseClassLoaderSystem;
import org.jboss.util.loading.Translator;

/**
 * Reflective wrapper around a JBoss_5.0.x class loader. Used to
 * encapsulate the classloader-specific methods (discovered and
 * called through reflection) from the load-time weaver.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class JBoss50ClassLoader extends JBoss5ClassLoader
{
   private Method addTranslator;
   private ClassLoaderSystem system;

   public JBoss50ClassLoader(BaseClassLoader classLoader)
   {
      super(classLoader);
   }

   protected void fallbackStrategy() throws Exception
   {
      try
      {
         // let's check if we have a patched policy, with translator per policy
         addTranslator = getMethod(BaseClassLoaderPolicy.class, "addTranslator");
      }
      catch (Exception ignored)
      {
         log.info("Policy doesn't have addTranslator, falling back to ClassLoaderSystem.");

         Method getClassLoaderDomain = getMethod(BaseClassLoaderPolicy.class, "getClassLoaderDomain");
         BaseClassLoaderDomain domain = invokeMethod(getClassLoaderDomain, getPolicy(), BaseClassLoaderDomain.class);
         Method getClassLoaderSystem = getMethod(BaseClassLoaderDomain.class, "getClassLoaderSystem");
         BaseClassLoaderSystem system = invokeMethod(getClassLoaderSystem, domain, BaseClassLoaderSystem.class);
         if (system instanceof ClassLoaderSystem)
         {
            this.system = ClassLoaderSystem.class.cast(system);
         }
         else
         {
            throw new IllegalArgumentException("ClassLoaderSyatem must be instance of [" + ClassLoaderSystem.class.getName() + "]");
         }
      }
   }

   protected void addTranslator(Translator translator)
   {
      if (addTranslator != null)
      {
         try
         {
            addTranslator.invoke(translator);
         }
         catch (Exception e)
         {
            throw new IllegalArgumentException(e);
         }
      }
      else
      {
         //noinspection deprecation
         system.setTranslator(translator);
      }
   }
}