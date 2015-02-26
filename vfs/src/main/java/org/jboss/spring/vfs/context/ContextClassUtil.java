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

package org.jboss.spring.vfs.context;

import org.springframework.context.ApplicationContextException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author Marius Bogoevici
 */
public class ContextClassUtil {

    static final String VFS_APPLICATION_CONTEXT_CLASS_NAME = "org.jboss.spring.vfs.context.VFSXmlWebApplicationContext";

    // Admittedly this is poorly worded. Since "org.jboss.Version" only exists in JBossAS 4 and was removed in 5, it will
    //   almost always return false.  So, it was reversed. Now it checks if the version is 4 or less. 
    public static boolean isJBossAS5orHigher() {
        try {
            Class<?> jBossVersionClass = DelegatingContextLoaderListener.class.getClassLoader().loadClass("org.jboss.Version");
            Object versionObject = ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(jBossVersionClass, "getInstance"), null);
            Integer majorVersion = (Integer) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(jBossVersionClass, "getMajor"), versionObject);
            // For JBoss AS versions 5 and higher
            if (majorVersion <= 4) {
                return false;
            }
        } catch (ClassNotFoundException e) {
            // do nothing;
        }
        return true;
    }

    public static Class<?> getVFSWebContextClass() {
        try {
            return ClassUtils.forName(VFS_APPLICATION_CONTEXT_CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            throw new ApplicationContextException(
                    "Failed to load custom context class [" + VFS_APPLICATION_CONTEXT_CLASS_NAME + "]", ex);
        }
    }
}
