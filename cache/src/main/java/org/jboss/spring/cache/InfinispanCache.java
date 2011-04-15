/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.spring.cache;

import org.springframework.cache.Cache;
import org.springframework.util.Assert;


/**
 * {@link org.springframework.cache.Cache} implementation on top of an {@link Infinispan} instance.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class InfinispanCache implements Cache<Object, Object>
{
   private org.infinispan.Cache<Object, Object> cache;

   public InfinispanCache(org.infinispan.Cache<Object, Object> cache)
   {
      Assert.notNull(cache, "non null infinispan cache required");
      this.cache = cache;
   }

   public String getName()
   {
      return cache.getName();
   }

   public org.infinispan.Cache<Object, Object> getNativeCache()
   {
      return cache;
   }

   public boolean containsKey(Object key)
   {
      return cache.containsKey(key);
   }

   public Object get(Object key)
   {
      return cache.get(key);
   }

   public Object put(Object key, Object value)
   {
      return cache.put(key, value);
   }

   public Object putIfAbsent(Object key, Object value)
   {
      return cache.putIfAbsent(key, value);
   }

   public Object remove(Object key)
   {
      return cache.remove(key);
   }

   public boolean remove(Object key, Object value)
   {
      return cache.remove(key, value);
   }

   public boolean replace(Object key, Object oldValue, Object newValue)
   {
      return cache.replace(key, oldValue, newValue);
   }

   public Object replace(Object key, Object value)
   {
      return cache.replace(key, value);
   }

   public void clear()
   {
      cache.clear();
   }
}
