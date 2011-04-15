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

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * {@link FactoryBean} that creates a named Infinispan {@link org.infinispan.Cache} instance
 * representing a cache region within an Infinispan {@link org.infinispan.manager.EmbeddedCacheManager}.
 *
 * <p>If the specified named cache is not configured in the cache configuration descriptor,
 * this FactoryBean will construct an instance of a Cache with the provided name and the
 * specified cache properties and add it to the EmbeddedCacheManager for later retrieval. If some
 * or all properties are not set at configuration time, this FactoryBean will use defaults.
 *
 * <p>Note: If the named Cache instance is found, the properties will be ignored and the
 * Cache instance will be retrieved from the EmbeddedCacheManager.

 * @author Ales Justin
 * @see #setCacheManager
 * @see InfinispanManagerFactoryBean
 * @see org.infinispan.Cache
 */
public class InfinispanCacheFactoryBean implements FactoryBean<Cache>, BeanNameAware, InitializingBean
{
   private String beanName;
   private EmbeddedCacheManager cacheManager;
   private Cache cache;

   /**
    * Set a CacheManager from which to retrieve a named Cache instance.
    * By default, <code>CacheManager.getInstance()</code> will be called.
    * <p>Note that in particular for persistent caches, it is advisable to
    * properly handle the shutdown of the CacheManager: Set up a separate
    * EhCacheManagerFactoryBean and pass a reference to this bean property.
    * <p>A separate EhCacheManagerFactoryBean is also necessary for loading
    * EHCache configuration from a non-default config location.
    *
    * @param cacheManager the embedded cache manager
    * @see InfinispanManagerFactoryBean
    * @see org.infinispan.manager.EmbeddedCacheManager
    */
   public void setCacheManager(EmbeddedCacheManager cacheManager) {
      this.cacheManager = cacheManager;
   }

   public void setBeanName(String s)
   {
      this.beanName = s;
   }

   public Cache getObject() throws Exception
   {
      return cache;
   }

   public Class<?> getObjectType()
   {
      return (this.cache != null ? this.cache.getClass() : Cache.class);
   }

   public boolean isSingleton()
   {
      return true;
   }

   public void afterPropertiesSet() throws Exception
   {
      // TODO
   }
}
