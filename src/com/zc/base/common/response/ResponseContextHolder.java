package com.zc.base.common.response;

import org.springframework.core.NamedThreadLocal;

import javax.servlet.http.HttpServletResponse;


public class ResponseContextHolder {
    private static final ThreadLocal<HttpServletResponse> responseHolder = new NamedThreadLocal(
            "Response threadLocal");


    public static HttpServletResponse getCurrentResponse() {
        return (HttpServletResponse) responseHolder.get();
    }


    public static void setCurrentResponse(HttpServletResponse res) {
        responseHolder.set(res);
    }
}
