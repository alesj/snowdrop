/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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

package org.jboss.snowdrop.context.support;

import java.util.Properties;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Marius Bogoevici
 */
public class JBossActivationSpecBeanDefinitionParser extends AbstractBeanDefinitionParser
{

   private static final String DEFAULT_ACTIVATION_SPEC_FACTORY_CLASS_NAME = "org.springframework.jms.listener.endpoint.DefaultJmsActivationSpecFactory";

   private static final String JBM_ACTIVATION_SPEC_CLASS_NAME = "org.jboss.resource.adapter.jms.inflow.JmsActivationSpec";

   private static final String HORNETQ_ACTIVATION_SPEC_CLASS_NAME = "org.hornetq.ra.inflow.HornetQActivationSpec";

   private static final String[] ACTIVATION_SPEC_CLASSNAME_CANDIDATES = new String[]{HORNETQ_ACTIVATION_SPEC_CLASS_NAME, JBM_ACTIVATION_SPEC_CLASS_NAME};

   private static final String USE_DEAD_LETTER_QUEUE_ATTRIBUTE_NAME = "use-dead-letter-queue";

   private static final String SNOWDROP_JCA_PROCESSOR_DEFAULT_SUBSCRIPTION_NAME = "snowdrop-jca-processor";

   private static final String CLIENT_ID_ATTRIBUTE_NAME = "client-id";

   private static final String CLASS_ATTRIBUTE_NAME = "class";

   private static final String DEFAULT_CLIENT_ID = "snowdrop-remote-client";

   private static final String SUBSCRIPTION_NAME_ATTRIBUTE_NAME = "subscription-name";

   private static final String ACTIVATION_SPEC_CLASS_PROPERTY = "activationSpecClass";

   private static final String CLIENT_ID_PROPERTY_NAME = "clientId";

   private static final String SUBSCRIPTION_NAME_PROPERTY_NAME = "subscriptionName";

   private static final String USE_DLQ_PROPERTY_NAME = "useDLQ";

   private static final String DEFAULT_PROPERTIES_PROPERTY_NAME = "defaultProperties";


   @Override
   protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException
   {
      return super.resolveId(element, definition, parserContext);
   }

   @Override
   protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext)
   {
      String useDlq = element.getAttribute(USE_DEAD_LETTER_QUEUE_ATTRIBUTE_NAME);
      String subscriptionName = element.getAttribute(SUBSCRIPTION_NAME_ATTRIBUTE_NAME);
      String clientId = element.getAttribute(CLIENT_ID_ATTRIBUTE_NAME);
      String activationSpecClassName = element.getAttribute(CLASS_ATTRIBUTE_NAME);

      BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DEFAULT_ACTIVATION_SPEC_FACTORY_CLASS_NAME);
      beanDefinitionBuilder.addPropertyValue(ACTIVATION_SPEC_CLASS_PROPERTY, StringUtils.hasText(activationSpecClassName) ? activationSpecClassName : detectJBossActivationSpecClass(parserContext));
      Properties properties = new Properties();
      properties.setProperty(CLIENT_ID_PROPERTY_NAME, StringUtils.hasText(clientId) ? clientId : DEFAULT_CLIENT_ID);
      properties.setProperty(SUBSCRIPTION_NAME_PROPERTY_NAME, StringUtils.hasText(subscriptionName) ? subscriptionName : SNOWDROP_JCA_PROCESSOR_DEFAULT_SUBSCRIPTION_NAME);
      properties.setProperty(USE_DLQ_PROPERTY_NAME, StringUtils.hasText(useDlq) ? useDlq : "false");
      beanDefinitionBuilder.addPropertyValue(DEFAULT_PROPERTIES_PROPERTY_NAME, properties);

      return beanDefinitionBuilder.getBeanDefinition();
   }

   private String detectJBossActivationSpecClass(ParserContext parserContext)
   {
      ClassLoader classLoader = parserContext.getReaderContext().getBeanClassLoader();
      if (classLoader == null)
      {
         classLoader = ClassUtils.getDefaultClassLoader();
      }
      for (String activationSpecClassNameCandidate : ACTIVATION_SPEC_CLASSNAME_CANDIDATES)
      {
         try
         {
            classLoader.loadClass(activationSpecClassNameCandidate);
            return activationSpecClassNameCandidate;
         }
         catch (ClassNotFoundException e)
         {
            // ignore
         }
      }

      throw new BeanCreationException("Cannot find a suitable ActivationSpec class on the classpath");
   }
}
