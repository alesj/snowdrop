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
package org.jboss.spring.factory;

import org.jboss.spring.vfs.context.VFSClassPathXmlApplicationContext;
import org.jboss.util.naming.Util;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:ales.justin@genera-lynx.com">Ales Justin</a>
 */
/*
    Consider refactoring to Subsystem AS7 Module
 */
public class NamedXmlApplicationContext extends VFSClassPathXmlApplicationContext implements Nameable {

    private String defaultName;


    private String name;
    private Resource resource;

    public NamedXmlApplicationContext(String defaultName, Resource resource) throws BeansException {
        this(defaultName, resource, true);
    }

    public NamedXmlApplicationContext(String defaultName, Resource resource, boolean refresh) throws BeansException {
        //loading config from Resource
        super(new String[]{}, false);
        this.defaultName = defaultName;
        this.resource = resource;
        initializeNames(resource);
        if (refresh) {
            refresh();
        }
    }

    @Override
    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
        reader.loadBeanDefinitions(this.resource);
    }

    public String getName() {
        String name = this.name != null? this.name : defaultName;
        if (name == null) {
            throw new IllegalArgumentException("Bean factory JNDI name must be set!");
        }
        return name;
    }

    private void initializeNames(Resource resource) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            xPath.setNamespaceContext(new NamespaceContext() {
                @Override
                public String getNamespaceURI(String prefix) {
                    return "http://www.springframework.org/schema/beans";
                }

                @Override
                public String getPrefix(String namespaceURI) {
                    return "beans";
                }

                @Override
                public Iterator<String> getPrefixes(String namespaceURI) {
                    return Collections.singleton("beans").iterator();
                }
            });
            String expression = "/beans:beans/beans:description";
            InputSource inputSource = new InputSource(resource.getInputStream());
            String description = xPath.evaluate(expression, inputSource);
            if (description != null) {
                Matcher bfm = Pattern.compile(Constants.BEAN_FACTORY_ELEMENT).matcher(description);
                if (bfm.find()) {
                    this.name = bfm.group(1);
                }
                Matcher pbfm = Pattern.compile(Constants.PARENT_BEAN_FACTORY_ELEMENT).matcher(description);
                if (pbfm.find()) {
                    String parentName = pbfm.group(1);
                    try {
                        this.getBeanFactory().setParentBeanFactory((BeanFactory) Util.lookup(parentName, BeanFactory.class));
                    } catch (Exception e) {
                        throw new BeanDefinitionStoreException("Failure during parent bean factory JNDI lookup: " + parentName, e);
                    }
                }
            }
            if (this.name == null || "".equals(StringUtils.trimAllWhitespace(this.name))) {
                this.name = this.defaultName;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
