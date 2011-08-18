/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

package org.jboss.spring.deployers.as7;

import org.jboss.as.naming.ManagedReferenceInjector;
import org.jboss.as.naming.NamingStore;
import org.jboss.as.naming.deployment.ContextNames;
import org.jboss.as.naming.deployment.JndiName;
import org.jboss.as.naming.service.BinderService;
import org.jboss.as.server.deployment.*;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceTarget;
import org.jboss.spring.factory.NamedXmlApplicationContext;
import org.jboss.spring.vfs.VFSResource;
import org.jboss.vfs.VirtualFile;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Marius Bogoevici
 */
public class SpringBootstrapProcessor implements DeploymentUnitProcessor {

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        ServiceTarget serviceTarget = phaseContext.getServiceTarget();
        SpringDeployment locations = SpringDeployment.retrieveFrom(phaseContext.getDeploymentUnit());
        if (locations == null) {
            return;
        }
        for (VirtualFile virtualFile : locations.getContextDefinitionLocations()) {
            NamedXmlApplicationContext applicationContext;
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(phaseContext.getDeploymentUnit().getAttachment(Attachments.MODULE).getClassLoader());
                applicationContext = new NamedXmlApplicationContext(phaseContext.getDeploymentUnit().getName(), new VFSResource(virtualFile));
                applicationContext.getName();
            } finally {
                Thread.currentThread().setContextClassLoader(cl);
            }
            ApplicationContextService service = new ApplicationContextService(applicationContext);
            ServiceName serviceName = phaseContext.getDeploymentUnit().getServiceName().append(applicationContext.getName());
            ServiceBuilder<?> serviceBuilder = serviceTarget.addService(serviceName, service);
            serviceBuilder.install();
            String jndiName = JndiName.of("java:jboss").append(applicationContext.getName()).getAbsoluteName();
            int index = jndiName.indexOf("/");
            String namespace = (index > 5) ? jndiName.substring(5, index) : null;
            String binding = (index > 5) ? jndiName.substring(index + 1) : jndiName.substring(5);
            ServiceName naming = (namespace != null) ? ContextNames.JAVA_CONTEXT_SERVICE_NAME.append(namespace) : ContextNames.JAVA_CONTEXT_SERVICE_NAME;
            ServiceName bindingName = naming.append(binding);
            BinderService binder = new BinderService(binding);
            serviceTarget.addService(bindingName, binder)
                    .addAliases(ContextNames.JAVA_CONTEXT_SERVICE_NAME.append(jndiName))
                    .addDependency(serviceName, ApplicationContext.class, new ManagedReferenceInjector<ApplicationContext>(binder.getManagedObjectInjector()))
                    .addDependency(naming, NamingStore.class, binder.getNamingStoreInjector())
                    .install();
        }
    }

    @Override
    public void undeploy(DeploymentUnit context) {

    }
}
