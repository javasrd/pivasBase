package com.zc.base.sys.modules.login;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.UserService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class LogedLogoutFilter
        extends LogoutFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogedLogoutFilter.class);


    protected boolean preHandle(ServletRequest request, ServletResponse response)
            throws Exception {
        Subject subject = getSubject(request, response);
        User user = (User) subject.getPrincipal();
        if (user != null) {


            UserService.MAP.remove(user.getAccount());

            if (UserService.ENTER_COUNT.get() > 0) {
                UserService.ENTER_COUNT.decrementAndGet();
            }
            logger.info(user.getAccount() + " logout!" + "|request host:" + request.getRemoteHost());
        }

        return super.preHandle(request, response);
    }
}
