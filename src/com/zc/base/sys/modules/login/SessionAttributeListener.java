package com.zc.base.sys.modules.login;

import com.zc.base.sys.modules.user.entity.User;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;


public class SessionAttributeListener
        implements HttpSessionAttributeListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionAttributeListener.class);


    public void attributeAdded(HttpSessionBindingEvent se) {
    }


    public void attributeRemoved(HttpSessionBindingEvent se) {
        String name = se.getName();
        if ("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY".equals(name)) {
            SimplePrincipalCollection simplePrincipal = (SimplePrincipalCollection) se.getValue();
            User user = (User) simplePrincipal.getPrimaryPrincipal();
            logger.info("user:" + user.getAccount() + " authenticationInfo removed from session[" +
                    se.getSession().getId() + "]....");
        }
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
    }
}
