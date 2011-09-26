package org.jboss.spring.deployers;

import java.io.Serializable;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Marius Bogoevici
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
