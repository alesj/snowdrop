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

import org.jboss.spring.util.Version;
import org.jboss.spring.util.VersionProvider;
import org.springframework.instrument.classloading.LoadTimeWeaver;

import java.lang.instrument.ClassFileTransformer;

/**
 * @author Marius Bogoevici
 */
public class JBossLoadTimeWeaver implements LoadTimeWeaver {

    private final LoadTimeWeaver delegateLoadTimeWeaver;

    public JBossLoadTimeWeaver() {
        if (VersionProvider.VERSION.compareTo(Version.AS_7) >= 0) {
            this.delegateLoadTimeWeaver = new JBossModulesLoadTimeWeaver();
        } else {
            throw new IllegalStateException("Cannot initialize delegate: JBoss version not recognized");
        }
    }

    public void addTransformer(ClassFileTransformer transformer) {
        delegateLoadTimeWeaver.addTransformer(transformer);
    }

    public ClassLoader getInstrumentableClassLoader() {
        return delegateLoadTimeWeaver.getInstrumentableClassLoader();
    }

    public ClassLoader getThrowawayClassLoader() {
        return delegateLoadTimeWeaver.getThrowawayClassLoader();
    }
}
