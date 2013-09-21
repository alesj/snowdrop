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

//import org.jboss.as.naming.context.NamespaceContextSelector;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
//import org.jboss.msc.value.InjectedValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Marius Bogoevici
 *
 * NOTE: Thanks to Zemian Deng for indicating the fix on
 * https://issues.jboss.org/browse/SNOWDROP-57
 */
public class ApplicationContextService implements Service<ApplicationContext> {

    private ConfigurableApplicationContext applicationContext;

    public ApplicationContextService(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void start(StartContext context) throws StartException {
        this.applicationContext.start();
    }

    @Override
    public void stop(StopContext context) {
        this.applicationContext.close();
        this.applicationContext = null;
    }

    @Override
    public ApplicationContext getValue() throws IllegalStateException, IllegalArgumentException {
        return applicationContext;
    }
}
