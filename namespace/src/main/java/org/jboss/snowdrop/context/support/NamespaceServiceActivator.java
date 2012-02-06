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


import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistryException;
import org.jboss.msc.service.ServiceTarget;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;
import org.jboss.spring.util.Version;
import org.jboss.spring.util.VersionProvider;

import javax.resource.spi.ResourceAdapter;

/**
 * @author: Marius Bogoevici
 */
public class NamespaceServiceActivator implements ServiceActivator {

    public void activate(ServiceActivatorContext serviceActivatorContext) throws ServiceRegistryException {
        ServiceTarget serviceTarget = serviceActivatorContext.getServiceTarget();

        ModuleClassLoader moduleClassLoader = (ModuleClassLoader) getClass().getClassLoader();
        ModuleIdentifier moduleIdentifier = moduleClassLoader.getModule().getIdentifier();
        String moduleName = moduleIdentifier.getName();
        String moduleSlot = moduleIdentifier.getSlot();
        ServiceName serviceIdentifier;
        if (VersionProvider.VERSION.compareTo(Version.AS_7_1) >= 0) {
            serviceIdentifier = ServiceName.of("jboss", "ra", "hornetq-ra");
        } else {
            serviceIdentifier = ServiceName.of("hornetq-ra");
        }
        JcaResourceAdapterService service = new JcaResourceAdapterService();

        ServiceName serviceName = ServiceName.JBOSS.append(moduleName, moduleSlot, "ResourceAdapter");
        serviceTarget.addService(serviceName, service)
                .addDependency(serviceIdentifier, ResourceAdapter.class, service.getResourceAdapterValue())
                .setInitialMode(ServiceController.Mode.ACTIVE)
                .install();

    }

    static class JcaResourceAdapterService implements Service<ResourceAdapter> {

        InjectedValue<ResourceAdapter> resourceAdapterValue = new InjectedValue<ResourceAdapter>();

        public void start(StartContext context) throws StartException {
            ActivatorHolder.initializeResourceAdapter(resourceAdapterValue.getValue());
        }

        public void stop(StopContext context) {
            ActivatorHolder.initializeResourceAdapter(null);
        }

        public InjectedValue<ResourceAdapter> getResourceAdapterValue() {
            return resourceAdapterValue;
        }

        public ResourceAdapter getValue() throws IllegalStateException, IllegalArgumentException {
            return resourceAdapterValue.getOptionalValue();
        }
    }


}
