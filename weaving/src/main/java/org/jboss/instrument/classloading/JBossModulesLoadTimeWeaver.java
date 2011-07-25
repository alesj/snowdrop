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

package org.jboss.instrument.classloading;

import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleThrowawayClassLoader;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * {@link LoadTimeWeaver} implementation supporting the modular classloader
 * of JBoss AS7+.
 *
 * @author Marius Bogoevici
 */
public class JBossModulesLoadTimeWeaver implements LoadTimeWeaver {

    private final ClassLoader classLoader;

    public JBossModulesLoadTimeWeaver() {
        this(ClassUtils.getDefaultClassLoader());
    }

    public JBossModulesLoadTimeWeaver(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void addTransformer(ClassFileTransformer transformer) {
        try {
            Field transformers = ReflectionUtils.findField(classLoader.getClass(), "transformer");
            transformers.setAccessible(true);
            Object delegatingTransformer = transformers.get(classLoader);
            Method ADD_TRANSFORMER = ReflectionUtils.findMethod(delegatingTransformer.getClass(), "addTransformer", new Class<?>[]{ClassFileTransformer.class});
            ADD_TRANSFORMER.setAccessible(true);
            ADD_TRANSFORMER.invoke(delegatingTransformer, transformer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ClassLoader getInstrumentableClassLoader() {
        return classLoader;
    }

    public ClassLoader getThrowawayClassLoader() {
        return new SimpleThrowawayClassLoader(this.classLoader);
    }
}
