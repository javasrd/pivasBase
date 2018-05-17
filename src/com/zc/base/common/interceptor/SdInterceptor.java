package com.zc.base.common.interceptor;

import com.zc.base.common.response.ResponseContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SdInterceptor
        extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ResponseContextHolder.setCurrentResponse(response);
        return true;
    }
}
