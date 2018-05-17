package com.zc.pivas.backup;

import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.backup.bean.ServerNodeBean;
import com.zc.pivas.backup.service.ServerNodeService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求过滤
 *
 * @author  cacabin
 * @version  1.0
 */

public class BackFilter implements Filter {



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        
       //读取跳转URI
       String reurl = Propertiesconfiguration.getStringProperty("redirecturi");
       if(reurl == null)
       {
           filterChain.doFilter(servletRequest, servletResponse);
           return;
       }
       
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
       String uri = httpRequest.getRequestURI();
       
       if(reurl.equals(uri))
       {
           filterChain.doFilter(servletRequest, servletResponse);
           return;
       }
       
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //读取本地IP
        String ipStr = Propertiesconfiguration.getStringProperty("localip"); 
        if(ipStr == null)
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
        ServletContext servletContext = servletRequest.getServletContext();
        ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(servletContext);
        
        ServerNodeService serviceNodeService = (ServerNodeService)ctx.getBean("ServerNodeService");
        if(serviceNodeService != null)
        {
            ServerNodeBean bean = serviceNodeService.getServerNode(ipStr);
            if(bean != null)
            {
                if(bean.getFlag() == 1)
                {
                    httpResponse.sendRedirect(reurl);
                    return;
                }
            }
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }


    @Override
    public void destroy()
    {

    }


    @Override
    public void init(FilterConfig arg0)
        throws ServletException
    {

    }

  
}
