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

import org.jboss.logging.Logger;

/**
 * Reflection helper.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class ReflectionHelper
{
   protected Logger log = Logger.getLogger(getClass());

   /**
    * Get method from class.
    *
    * @param clazz the owner class
    * @param name the method name
    * @return declared method
    * @throws Exception for any error
    */
   protected static Method getMethod(Class<?> clazz, String name) throws Exception
   {
      Method method = clazz.getDeclaredMethod(name);
      method.setAccessible(true);
      return method;
   }

   /**
    * Invoke method and check the result.
    *
    * @param method the method
    * @param target the target
    * @param expectedType the expected type
    * @param <T> the exact type
    * @return invocation's result
    * @throws Exception for any error
    */
   protected static <T> T invokeMethod(Method method, Object target, Class<T> expectedType) throws Exception
   {
      Object result = method.invoke(target);
      if (!expectedType.isInstance(result))
         throw new IllegalArgumentException("Returned result must be instance of [" + expectedType.getName() + "]");

      return expectedType.cast(result);
   }
}