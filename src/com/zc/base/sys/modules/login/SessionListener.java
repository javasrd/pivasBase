package com.zc.base.sys.modules.login;

import com.zc.base.sys.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener
        implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);


    public void sessionCreated(HttpSessionEvent se) {
    }


    public void sessionDestroyed(HttpSessionEvent se) {
        Object obj = se.getSession().getAttribute("current_user");
        if (obj != null) {

            String account = obj.toString();
            UserService.MAP.remove(account);

            UserService.ENTER_COUNT.decrementAndGet();
        }

        logger.info("session[" + se.getSession().getId() + "] destroyed!");
    }
}
