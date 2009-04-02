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
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import org.jboss.classloader.spi.ClassLoaderPolicy;
import org.jboss.classloader.spi.base.BaseClassLoader;
import org.jboss.util.loading.Translator;
import org.springframework.util.Assert;

/**
 * Reflective wrapper around a JBoss5 class loader. Used to
 * encapsulate the classloader-specific methods (discovered and
 * called through reflection) from the load-time weaver.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class JBoss5ClassLoader extends ReflectionHelper
{
   private final BaseClassLoader classLoader;
   private ClassLoaderPolicy policy;

   @SuppressWarnings("unchecked")
   protected JBoss5ClassLoader(BaseClassLoader classLoader)
   {
      Assert.notNull(classLoader, "ClassLoader must not be null");
      this.classLoader = classLoader;

      try
      {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null)
            AccessController.doPrivileged(new InstantiationAction());
         else
            doInstantiate();
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Could not initialize JBoss ClassLoader because JBoss5 API classes are not available", e);
      }
   }

   /**
    * Get the policy.
    *
    * @return the policy
    */
   protected ClassLoaderPolicy getPolicy()
   {
      return policy;
   }

   /**
    * Do instantiate method, variables.
    *
    * @throws Exception for any error
    */
   private void doInstantiate() throws Exception
   {
      Method getPolicy = getMethod(BaseClassLoader.class, "getPolicy");
      policy = invokeMethod(getPolicy, classLoader, ClassLoaderPolicy.class);
      fallbackStrategy();
   }

   /**
    * The fallback strategy.
    *
    * @throws Exception for any error
    */
   protected void fallbackStrategy() throws Exception
   {
   }

   public void addTransformer(ClassFileTransformer transformer)
   {
      Assert.notNull(transformer, "ClassFileTransformer must not be null");
      Translator translator = new ClassFileTransformer2Translator(transformer);
      addTranslator(translator);
   }

   /**
    * Add the translator.
    *
    * @param translator the translator
    */
   protected abstract void addTranslator(Translator translator);

   public ClassLoader getInternalClassLoader()
   {
      return classLoader;
   }

   public ClassLoader getThrowawayClassLoader()
   {
      return new BaseClassLoader(policy);
   }

   /**
    * Instantiation action.
    */
   private class InstantiationAction implements PrivilegedExceptionAction
   {
      public Object run() throws Exception
      {
         doInstantiate();
         return null;
      }
   }
}