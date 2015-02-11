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
package org.jboss.spring.deployers;

import java.io.Serializable;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Marius Bogoevici
 */
/*
 * Consider removing as it appears no longer used.
 */
public class SpringContextDescriptor implements Serializable {

    private static final long serialVersionUID = 8989753488155849440L;

    private URL fileURL;

    private String defaultName;

    private String name;

    private transient Resource resource;

    public SpringContextDescriptor() {
    }

    public SpringContextDescriptor(URL fileURL, String defaultName) {
        this.fileURL = fileURL;
        this.defaultName = defaultName;
    }

    public URL getFileURL() {
        return fileURL;
    }

    public void setFileURL(URL fileURL) {
        this.fileURL = fileURL;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getName() {
        return name != null ? name : defaultName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resource getResource() {
        if (resource == null) {
            resource = new UrlResource(fileURL);
        }
        return resource;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("fileURL=").append(fileURL);
        builder.append(", defaultName=").append(defaultName);
        return builder.toString();
    }
}
