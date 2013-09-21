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

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Phase;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController;

import java.util.List;

//import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;

/**
 * @author Marius Bogoevici
 */
public class SpringSubsystemAdd extends AbstractBoottimeAddStepHandler {

    private static final Logger log = Logger.getLogger("org.jboss.snowdrop");


    public static final SpringSubsystemAdd INSTANCE = new SpringSubsystemAdd();

    @Override
    protected void performBoottime(OperationContext operationContext, ModelNode modelNode, ModelNode modelNode1, ServiceVerificationHandler serviceVerificationHandler, List<ServiceController<?>> serviceControllers) throws OperationFailedException {
        log.info("Activating Spring Deployer subsystem");
        operationContext.addStep(new AbstractDeploymentChainStep() {
            protected void execute(DeploymentProcessorTarget bootContext) {
                bootContext.addDeploymentProcessor(Phase.STRUCTURE, Phase.STRUCTURE_JBOSS_DEPLOYMENT_STRUCTURE_DESCRIPTOR + 1, new SpringStructureProcessor());
                bootContext.addDeploymentProcessor(Phase.PARSE, Phase.PARSE_DEPENDENCIES_MANIFEST, new SpringDependencyProcessor());
                bootContext.addDeploymentProcessor(Phase.INSTALL, Integer.MAX_VALUE, new SpringBootstrapProcessor());
            }
        }, OperationContext.Stage.RUNTIME);
    }

    @Override
    protected void populateModel(ModelNode modelNode, ModelNode modelNode1) throws OperationFailedException {

    }

}
