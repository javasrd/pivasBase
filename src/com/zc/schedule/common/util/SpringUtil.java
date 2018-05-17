package com.zc.schedule.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Spring 工具类
 *
 * @author Justin
 * @version v1.0
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        setContext(arg0);
    }

    /**
     * 设置上下文
     *
     * @param arg0
     */
    private void setContext(ApplicationContext arg0) {
        SpringUtil.appContext = arg0;
    }

    /**
     * 获取session
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletrequestattributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (servletrequestattributes != null)
            request = servletrequestattributes.getRequest();
        return request;
    }
}
