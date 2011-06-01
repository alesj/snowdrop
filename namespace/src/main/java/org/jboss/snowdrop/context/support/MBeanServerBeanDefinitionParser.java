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

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Marius Bogoevici
 */
public class MBeanServerBeanDefinitionParser extends AbstractBeanDefinitionParser
{

   public static final String DEFAULT_JBOSS_MBEAN_SERVER_BEAN_NAME = "mbeanServer";

   private static final String MBEAN_SERVER_LOCATOR_CLASS_NAME = "org.jboss.mx.util.MBeanServerLocator";

   private static final String MBEAN_SERVER_LOCATOR_METHOD_NAME = "locateJBoss";


   @Override
   protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext)
   {
      BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(MBEAN_SERVER_LOCATOR_CLASS_NAME, MBEAN_SERVER_LOCATOR_METHOD_NAME);
      return beanDefinitionBuilder.getBeanDefinition();
   }

   @Override
   protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException
   {
      String id = element.getAttribute("id");
      if (!StringUtils.hasText(id))
      {
         id = DEFAULT_JBOSS_MBEAN_SERVER_BEAN_NAME;
      }
      return id;
   }

}
