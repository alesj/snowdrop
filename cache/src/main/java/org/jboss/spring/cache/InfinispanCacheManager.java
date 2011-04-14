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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.infinispan.lifecycle.ComponentStatus;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;

/**
 * CacheManager backed by an Infinispan {@link EmbeddedCacheManager}.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class InfinispanCacheManager extends AbstractCacheManager
{
   private EmbeddedCacheManager cacheManager;

   @Override
   protected Collection<Cache<?, ?>> loadCaches()
   {
      Assert.notNull(cacheManager, "a backing Infinispan cache manager is required");
      ComponentStatus status = cacheManager.getStatus();
      Assert.isTrue(ComponentStatus.RUNNING.equals(status), "an 'alive' Infinispan cache manager is required - current cache is " + status.toString());

      Set<String> names = cacheManager.getCacheNames();
      Collection<Cache<?, ?>> caches = new LinkedHashSet<Cache<?, ?>>(names.size());

      for (String name : names)
      {
         caches.add(new InfinispanCache(cacheManager.<Object, Object>getCache(name)));
      }

      return caches;
   }

   @SuppressWarnings("unchecked")
   public <K, V> Cache<K, V> getCache(String name)
   {
      Cache cache = super.getCache(name);
      if (cache == null)
      {
         // check the Ehcache cache again
         // in case the cache was added at runtime

         org.infinispan.Cache ispanCache = cacheManager.getCache(name);
         if (ispanCache != null)
         {
            // reinitialize cache map
            afterPropertiesSet();
            cache = super.getCache(name);
         }
      }

      return cache;
   }

   /**
    * Sets the backing Ehcache {@link EmbeddedCacheManager}.
    *
    * @param cacheManager backing Ehcache {@link EmbeddedCacheManager}
    */
   public void setCacheManager(EmbeddedCacheManager cacheManager)
   {
      this.cacheManager = cacheManager;
   }
}