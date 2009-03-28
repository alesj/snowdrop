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

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

import org.jboss.classloader.spi.ClassLoaderPolicy;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloader.spi.base.BaseClassLoader;
import org.jboss.classloader.spi.base.BaseClassLoaderDomain;
import org.jboss.classloader.spi.base.BaseClassLoaderPolicy;
import org.jboss.classloader.spi.base.BaseClassLoaderSystem;
import org.jboss.logging.Logger;
import org.jboss.util.loading.Translator;
import org.springframework.util.Assert;

/**
 * Reflective wrapper around a JBoss5 class loader. Used to
 * encapsulate the classloader-specific methods (discovered and
 * called through reflection) from the load-time weaver.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class JBoss5ClassLoader
{
   private static Logger log = Logger.getLogger(JBoss5ClassLoader.class);

   private final BaseClassLoader classLoader;
   private final ClassLoaderPolicy policy;
   private ClassLoaderSystem system;

   private Method setTranslator;

   public JBoss5ClassLoader(BaseClassLoader classLoader)
   {
      Assert.notNull(classLoader, "ClassLoader must not be null");
      this.classLoader = classLoader;

      try
      {
         Method getPolicy = BaseClassLoader.class.getDeclaredMethod("getPolicy");
         policy = (ClassLoaderPolicy)getPolicy.invoke(classLoader);
         try
         {
            // let's check if we have a patched policy, with translator per policy
            setTranslator = BaseClassLoaderPolicy.class.getDeclaredMethod("setTranslator");
         }
         catch (Exception ignored)
         {
            log.info("Policy doesn't have setTranslator, falling back to ClassLoaderSystem.");

            Method getClassLoaderDomain = BaseClassLoaderPolicy.class.getDeclaredMethod("getClassLoaderDomain");
            BaseClassLoaderDomain domain = (BaseClassLoaderDomain)getClassLoaderDomain.invoke(policy);
            Method getClassLoaderSystem = BaseClassLoaderDomain.class.getDeclaredMethod("getClassLoaderSystem");
            BaseClassLoaderSystem system = (BaseClassLoaderSystem)getClassLoaderSystem.invoke(domain);
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
      catch (Exception e)
      {
         throw new IllegalStateException("Could not initialize JBoss ClassLoader because JBoss5 API classes are not available", e);
      }
   }

   public void addTransformer(ClassFileTransformer transformer)
   {
      Assert.notNull(transformer, "ClassFileTransformer must not be null");
      Translator translator = new ClassFileTransformer2Translator(transformer);
      if (setTranslator != null)
      {
         try
         {
            setTranslator.invoke(translator);
         }
         catch (Exception e)
         {
            throw new IllegalArgumentException(e);
         }
      }
      else
      {
         system.setTranslator(translator);   
      }
   }

   public ClassLoader getInternalClassLoader()
   {
      return classLoader;
   }

   public ClassLoader getThrowawayClassLoader()
   {
      return new BaseClassLoader(policy);
   }
}