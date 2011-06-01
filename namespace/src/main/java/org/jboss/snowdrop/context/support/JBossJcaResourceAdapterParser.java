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

import static org.jboss.snowdrop.context.support.MBeanServerBeanDefinitionParser.DEFAULT_JBOSS_MBEAN_SERVER_BEAN_NAME;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Parser for the &lt;resource-adapter .../&gt; element
 *
 * @author Marius Bogoevici
 */
public class JBossJcaResourceAdapterParser  extends AbstractBeanDefinitionParser
{

   private static final String DEFAULT_JCA_MBEAN_NAME = "jboss.jca:name='jms-ra.rar',service=RARDeployment";

   private static final String FACTORY_METHOD_NAME = "getAttribute";

   @Override
   protected boolean shouldGenerateIdAsFallback()
   {
      return true;
   }

   @Override
   protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext)
   {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
      builder.getRawBeanDefinition().setFactoryBeanName(DEFAULT_JBOSS_MBEAN_SERVER_BEAN_NAME);
      builder.getRawBeanDefinition().setFactoryMethodName(FACTORY_METHOD_NAME);
      try
      {
         builder.addConstructorArgValue(ObjectName.getInstance(DEFAULT_JCA_MBEAN_NAME));
      }
      catch (MalformedObjectNameException e)
      {
         throw new IllegalArgumentException(e);
      }
      builder.addConstructorArgValue("ResourceAdapter");
      return builder.getBeanDefinition();
   }

}
