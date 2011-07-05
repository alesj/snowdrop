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
package org.jboss.instrument.classloading;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

import org.jboss.classloader.spi.base.BaseClassLoader;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link LoadTimeWeaver} implementation for JBoss5's instrumentable ClassLoader.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class JBoss5LoadTimeWeaver extends ReflectionHelper implements LoadTimeWeaver {

    private JBoss5ClassLoader classLoader;

    public JBoss5LoadTimeWeaver() {
        this(ClassUtils.getDefaultClassLoader());
    }

    public JBoss5LoadTimeWeaver(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ClassLoader must not be null");
        BaseClassLoader bcl = determineClassLoader(classLoader);
        if (bcl == null) {
            throw new IllegalArgumentException(classLoader + " and its parents are not suitable ClassLoaders: " +
                    "An [" + BaseClassLoader.class.getName() + "] implementation is required.");
        }
        this.classLoader = createClassLoaderWrapper(bcl);
    }

    /**
     * Create a JBoss5 classloader wrapper
     * based on the underlying JBossAS version.
     *
     * @param bcl the base classloader
     * @return new JBoss5 classloader wrapper
     */
    protected JBoss5ClassLoader createClassLoaderWrapper(BaseClassLoader bcl) {
        int versionNumber = 0;
        String tag;

        try {
            // BCL should see Version class
            Class<?> versionClass = bcl.loadClass("org.jboss.Version");
            Method getInstance = getMethod(versionClass, "getInstance");
            Object version = getInstance.invoke(null); // static method

            Method getMajor = getMethod(versionClass, "getMajor");
            versionNumber += 100 * invokeMethod(getMajor, version, Integer.class);
            Method getMinor = getMethod(versionClass, "getMinor");
            versionNumber += 10 * invokeMethod(getMinor, version, Integer.class);
            Method getRevision = getMethod(versionClass, "getRevision");
            versionNumber += invokeMethod(getRevision, version, Integer.class);
            Method getTag = getMethod(versionClass, "getTag");
            tag = invokeMethod(getTag, version, String.class);
        } catch (Exception e) {
            log.warn("Exception creating JBoss5 CL wrapper: " + e + ", falling back to JBoss50ClassLoader wrapper.");
            return new JBoss50ClassLoader(bcl);
        }

        if (versionNumber < 500) // this only works on new MC code
        {
            throw new IllegalArgumentException("JBoss5LoadTimeWeaver can only be used on new JBoss Microcontainer ClassLoader.");
        } else if (versionNumber <= 501 || (versionNumber == 510 && "Beta1".equals(tag))) {
            return new JBoss50ClassLoader(bcl);
        } else {
            return new JBoss51ClassLoader(bcl);
        }
    }

    /**
     * Find first BaseClassLoader implementation.
     *
     * @param classLoader the classloader
     * @return BaseClassLoader instance or null if not found
     */
    private BaseClassLoader determineClassLoader(ClassLoader classLoader) {
        for (ClassLoader cl = classLoader; cl != null; cl = cl.getParent()) {
            if (cl instanceof BaseClassLoader) {
                return (BaseClassLoader) cl;
            }
        }
        return null;
    }

    public void addTransformer(ClassFileTransformer transformer) {
        classLoader.addTransformer(transformer);
    }

    public ClassLoader getInstrumentableClassLoader() {
        return classLoader.getInternalClassLoader();
    }

    public ClassLoader getThrowawayClassLoader() {
        return classLoader.getThrowawayClassLoader();
    }
}
