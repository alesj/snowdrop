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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
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
public class NamedXmlApplicationContext extends VFSClassPathXmlApplicationContext implements Nameable {

    private String defaultName;

    private Resource resource;

    private NamedXmlBeanDefinitionReader beanDefinitionReader;

    private String name;

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

    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws IOException {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        beanDefinitionReader = new NamedXmlBeanDefinitionReader(beanFactory);

        // Configure the bean definition reader with this context's
        // resource loading environment.
        beanDefinitionReader.setResourceLoader(this);
        ClassLoader cl = getClassLoader();
        if (cl != null) {
            beanDefinitionReader.setBeanClassLoader(cl);
        }
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // Allow a subclass to provide custom initialization of the reader,
        // then proceed with actually loading the bean definitions.
        initBeanDefinitionReader(beanDefinitionReader);
        loadBeanDefinitions(beanDefinitionReader);
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
        reader.loadBeanDefinitions(resource);
    }

    public String getName() {
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
                public Iterator getPrefixes(String namespaceURI) {
                    return Collections.singleton("beans").iterator();
                }
            });
            String expression = "/beans:beans/beans:description";
            InputSource inputSource = new InputSource(resource.getInputStream());
            String description = xPath.evaluate(expression, inputSource);
            if (description != null) {
                Matcher bfm = Pattern.compile(NamedXmlBeanDefinitionParser.BEAN_FACTORY_ELEMENT).matcher(description);
                if (bfm.find()) {
                    this.name = bfm.group(1);
                }
                //                Matcher pbfm = Pattern.compile(NamedXmlBeanDefinitionParser.PARENT_BEAN_FACTORY_ELEMENT).matcher(description);
                //                if (pbfm.find()) {
                //                    parentName = pbfm.group(1);
                //                }
            }
            if (this.name == null || "".equals(StringUtils.trimAllWhitespace(this.name))) {
                this.name = this.defaultName;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
