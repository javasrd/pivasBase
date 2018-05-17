package com.zc.schedule.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 过滤器
 *
 * @author Justin
 * @version v1.0
 */
public class SessionFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;

        HttpSession sessionSchedule = httpReq.getSession();

        ServletContext ContextSchedule = sessionSchedule.getServletContext();

        //项目路径
        String path = Propertiesconfiguration.getStringProperty("schedule.contextPath");
        //端口
        String httpsPort = Propertiesconfiguration.getStringProperty("webapp.httpsPort");
        String httpsLoginUrl = "https://" + request.getServerName() + ":" + httpsPort + path + "/login";

        ServletContext ContextPivas = ContextSchedule.getContext(path);

        if (ContextPivas != null) {

            HttpSession sessionPivas = (HttpSession) ContextPivas.getAttribute("sessionInfo");

            if (sessionPivas != null) {

                try {
                    Object obj = sessionPivas.getAttribute(SysConstant.sessionName.userId);
                    if (obj != null && !"".equals(obj)) {
                        chain.doFilter(request, response);
                    }
                } catch (Exception e1) {

                    String str = "<script language='javascript'>window.top.location.href='" + httpsLoginUrl + "';</script>";
                    response.setContentType("text/html;charset=UTF-8");
                    try {
                        PrintWriter writer = response.getWriter();
                        writer.write(str);
                        writer.flush();
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }

    @Override
    public void init(FilterConfig arg0)
            throws ServletException {

    }

}
