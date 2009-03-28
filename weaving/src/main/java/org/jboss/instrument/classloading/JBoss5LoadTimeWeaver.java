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

import org.jboss.classloader.spi.base.BaseClassLoader;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link LoadTimeWeaver} implementation for JBoss5's instrumentable ClassLoader.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class JBoss5LoadTimeWeaver implements LoadTimeWeaver
{
   private JBoss5ClassLoader classLoader;

   public JBoss5LoadTimeWeaver()
   {
      this(ClassUtils.getDefaultClassLoader());
   }

   public JBoss5LoadTimeWeaver(ClassLoader classLoader)
   {
      Assert.notNull(classLoader, "ClassLoader must not be null");
      BaseClassLoader bcl = determineClassLoader(classLoader);
      if (bcl == null)
      {
         throw new IllegalArgumentException(classLoader + " and its parents are not suitable ClassLoaders: " +
               "An [" + BaseClassLoader.class.getName() + "] implementation is required.");
      }
      this.classLoader = new JBoss5ClassLoader(bcl);
   }

   /**
    * Find first BaseClassLoader implementation.
    *
    * @param classLoader the classloader
    * @return BaseClassLoader instance or null if not found
    */
   private BaseClassLoader determineClassLoader(ClassLoader classLoader)
   {
      for (ClassLoader cl = classLoader; cl != null; cl = cl.getParent())
      {
         if (cl instanceof BaseClassLoader)
         {
            return (BaseClassLoader)cl;
         }
      }
      return null;
   }

   public void addTransformer(ClassFileTransformer transformer)
   {
      classLoader.addTransformer(transformer);
   }

   public ClassLoader getInstrumentableClassLoader()
   {
      return classLoader.getInternalClassLoader();
   }

   public ClassLoader getThrowawayClassLoader()
   {
      return classLoader.getThrowawayClassLoader();
   }
}
