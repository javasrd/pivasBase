package com.zc.pivas.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;
/**
 * 
 * WebSocket拦截器
 *
 * @author  cacabin
 * @version  1.0
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor
{
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Map<String, Object> attributes)
        throws Exception
    {
        if (request instanceof ServletServerHttpRequest)
        {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
            HttpSession session = servletRequest.getServletRequest().getSession(true);
            if (session != null)
            {
                //使用userName区分WebSocketHandler，以便定向发送消息
                //String userName = (String)session.getAttribute(WSConstants.SESSION_USERNAME);
                attributes.put(WSConstants.WEBSOCKET_USERNAME, "admin");
            }
        }
        return true;
    }
    
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Exception exception)
    {
        
    }
}
