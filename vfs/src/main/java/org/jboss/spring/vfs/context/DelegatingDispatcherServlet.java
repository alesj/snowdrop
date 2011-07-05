package org.jboss.spring.vfs.context;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Marius Bogoevici
 */
public class DelegatingDispatcherServlet extends DispatcherServlet {

    @Override
    public Class getContextClass() {
        if (ContextClassUtil.isJBossAS5orHigher()) {
            return ContextClassUtil.getVFSWebContextClass();
        } else {
            return super.getContextClass();
        }
    }
}
