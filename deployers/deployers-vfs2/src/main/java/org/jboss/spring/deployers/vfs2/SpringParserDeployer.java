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
package org.jboss.spring.deployers.vfs2;

import java.util.List;

import org.jboss.deployers.vfs.spi.deployer.AbstractVFSParsingDeployer;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;

import org.jboss.spring.deployers.SpringMetaData;
import org.jboss.spring.deployers.SpringParserDeployerHandler;
import org.jboss.virtual.VirtualFile;

/**
 * Spring deployer.
 * Picks up -spring.xml file.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @author Marius Bogoevici
 */
public class SpringParserDeployer extends AbstractVFSParsingDeployer<SpringMetaData> {

    private final SpringParserDeployerHandler springParserDeployerHandler = new SpringParserDeployerHandler();

    public SpringParserDeployer() {
        super(SpringMetaData.class);
        setSuffix("-spring.xml");
        setJarExtension(".spring");
        springParserDeployerHandler.setUseLegacyDefaultName(false);
    }

    /**
     * Should we use deployment unit's name as default.
     * e.g. using string before .jar|spring|... as the name
     * <p/>
     * Previous versions used string before .spring as the name,
     * setting this to true results in this legacy behaviour.
     * <p/>
     * Current default is string before -spring.xml.
     *
     * @param useLegacyDefaultName the flag
     */
    public void setUseLegacyDefaultName(boolean useLegacyDefaultName) {
        springParserDeployerHandler.setUseLegacyDefaultName(useLegacyDefaultName);
    }

    @Override
    protected SpringMetaData parse(VFSDeploymentUnit unit, VirtualFile file, SpringMetaData metaData) throws Exception {
        return springParserDeployerHandler.parse(unit, file);
    }

    @Override
    protected SpringMetaData handleMultipleFiles(VFSDeploymentUnit unit, SpringMetaData root, List<VirtualFile> files) throws Exception {
        return springParserDeployerHandler.handleMultipleFiles(files);
    }
}
