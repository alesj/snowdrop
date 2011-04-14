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

import java.util.logging.Logger;

import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * {@link FactoryBean} that exposes an Infinispan {@link org.infinispan.manager.EmbeddedCacheManager}
 * instance (independent or shared), configured from a specified config location.
 *
 * <p>If no config location is specified, a EmbeddedCacheManager will be configured from
 * "infinispan.xml" in the root of the class path (that is, default Infinispan initialization
 * - as defined in the Infinispan docs - will apply).
 *
 * <p>Setting up a separate InfinispanManagerFactoryBean is also advisable when using
 * InfinispanFactoryBean, as it provides a (by default) independent EmbeddedCacheManager instance
 * and cares for proper shutdown of the EmbeddedCacheManager. InfinispanManagerFactoryBean is
 * also necessary for loading Infinispan configuration from a non-default config location.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class InfinispanManagerFactoryBean implements FactoryBean<EmbeddedCacheManager>, InitializingBean, DisposableBean
{
   protected final Logger logger = Logger.getLogger(getClass().getName());

   private EmbeddedCacheManager cacheManager;

   public EmbeddedCacheManager getObject()
   {
      return this.cacheManager;
   }

   public Class<? extends EmbeddedCacheManager> getObjectType()
   {
      return (this.cacheManager != null ? this.cacheManager.getClass() : EmbeddedCacheManager.class);
   }

   public boolean isSingleton()
   {
      return true;
   }

   public void destroy()
   {
      logger.info("Shutting down Infinispan EmbeddedCacheManager");
      this.cacheManager.stop();
   }

   public void afterPropertiesSet() throws Exception
   {
      // TODO
   }
}
