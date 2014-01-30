/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.spring.util;

import org.springframework.util.ClassUtils;

/**
 * @author Marius Bogoevici
 */
public class VersionProvider {

    public static final Version VERSION;

    static {
        // on JBoss AS 5 and JBoss AS 6
        Version version = null;
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        Class<? extends ClassLoader> classLoaderClass = classLoader.getClass();
        if (classLoaderClass.getName().startsWith("org.jboss.modules")) {
            if (classLoaderClass.getPackage().getImplementationVersion()
                    .startsWith("1.0.0")) {
                version = Version.AS_7;
            } else {
                version = Version.AS_7_1;
            }
        }
        if (version == null) {
            throw new IllegalStateException("Cannot find the JBoss AS version");
        } else {
            VERSION = version;
        }
    }
}
