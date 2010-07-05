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
package org.jboss.spring.deployers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.deployers.vfs.spi.deployer.AbstractVFSParsingDeployer;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.virtual.VirtualFile;

/**
 * Spring deployer.
 * Picks up -spring.xml file.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class SpringParserDeployer extends AbstractVFSParsingDeployer<SpringMetaData>
{
   private boolean useLegacyDefaultName;

   public SpringParserDeployer()
   {
      super(SpringMetaData.class);
      setSuffix("-spring.xml");
      setJarExtension(".spring");
      setUseLegacyDefaultName(false);
   }

   /**
    * Get the use name flag.
    *
    * @return true if the default name should be determined from deployment unit
    */
   protected boolean isUseLegacyDefaultName()
   {
      return useLegacyDefaultName;
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
   public void setUseLegacyDefaultName(boolean useLegacyDefaultName)
   {
      this.useLegacyDefaultName = useLegacyDefaultName;
   }

   /**
    * Get default name from meta file.
    *
    * @param file the virtual file
    * @return default name
    */
   protected String getDefaultName(VirtualFile file)
   {
      String shortName = file.getName();
      int p = shortName.indexOf("-spring.xml");
      return shortName.substring(0, p);
   }

   /**
    * Get default name from unit.
    *
    * @param unit the deployment unit
    * @return default name
    */
   protected String getDefaultName(VFSDeploymentUnit unit)
   {
      String shortName = unit.getSimpleName();
      int p = shortName.lastIndexOf(".");
      return shortName.substring(0, p);
   }

   protected SpringMetaData parse(VFSDeploymentUnit unit, VirtualFile file, SpringMetaData metaData) throws Exception
   {
      String defaultName;
      if (isUseLegacyDefaultName())
         defaultName = getDefaultName(unit);
      else
         defaultName = getDefaultName(file);

      return new SpringMetaData(Collections.singletonList(createSpringContextDescriptor(file, defaultName)));
   }

   private SpringContextDescriptor createSpringContextDescriptor(VirtualFile file, String defaultName)
         throws MalformedURLException, URISyntaxException
   {
      return new SpringContextDescriptor(file.toURL(), defaultName);
   }

   @Override
   protected SpringMetaData handleMultipleFiles(VFSDeploymentUnit unit, SpringMetaData root, List<VirtualFile> files) throws Exception
   {
      List<SpringContextDescriptor> descriptors = new ArrayList<SpringContextDescriptor>();
      for (VirtualFile virtualFile : files)
      {
         String defaultName;
         if (isUseLegacyDefaultName())
            throw new IllegalStateException("Cannot use the legacy default name for multiple Spring configuration files");
         else
            defaultName = getDefaultName(virtualFile);
         descriptors.add(new SpringContextDescriptor(virtualFile.toURL(), defaultName));
      }
      return new SpringMetaData(descriptors);
   }
}
