package org.jboss.spring.vfs.context;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Marius Bogoevici
 */
public class DelegatingContextLoaderListener extends ContextLoaderListener {

    @Override
    protected ContextLoader createContextLoader() {
        if (!ContextClassUtil.isJBossAS5orHigher()) {
            return super.createContextLoader();
        } else {
            return new VFSContextLoader();
        }
    }

    private static class VFSContextLoader extends ContextLoader {

        @Override
        protected Class determineContextClass(ServletContext servletContext) throws ApplicationContextException {
            return ContextClassUtil.getVFSWebContextClass();

        }
    }
}
