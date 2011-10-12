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

import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ModuleDependency;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.filter.PathFilters;

/**
 * @author Marius Bogoevici
 */
public class SpringDependencyProcessor implements DeploymentUnitProcessor {

    private static final ModuleIdentifier MODULE_IDENTIFIER_SNOWDROP = ModuleIdentifier.create("org.jboss.snowdrop");

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();


        SpringDeployment locations = SpringDeployment.retrieveFrom(deploymentUnit);
        if (locations == null || locations.getContextDefinitionLocations().isEmpty()) {
            return;
        }

        ModuleSpecification moduleSpecification = deploymentUnit.getAttachment(Attachments.MODULE_SPECIFICATION);


        addDependency(MODULE_IDENTIFIER_SNOWDROP, moduleSpecification);

        deploymentUnit.addToAttachmentList(Attachments.ADDITIONAL_ANNOTATION_INDEXES, MODULE_IDENTIFIER_SNOWDROP);
    }

    private ModuleDependency addDependency(ModuleIdentifier moduleIdentifier, ModuleSpecification moduleSpecification) {
        ModuleDependency moduleDependency = new ModuleDependency(Module.getBootModuleLoader(), moduleIdentifier, false, false, true);
        moduleDependency.addExportFilter(PathFilters.acceptAll(), true);
        moduleDependency.addImportFilter(PathFilters.acceptAll(), true);
        moduleSpecification.addUserDependency(moduleDependency);
        return moduleDependency;
    }

    @Override
    public void undeploy(DeploymentUnit context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
