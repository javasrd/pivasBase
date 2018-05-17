package com.zc.pivas.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 
 * WebSocket配置类
 *
 * @author  cacabin
 * @version  1.0
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer
{
    private String httpPort;
    
    private String serverIP;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry.addHandler(sysWebSocketHandler(), "/WebSocketServer")
            .addInterceptors(new WebSocketHandshakeInterceptor())
            .setAllowedOrigins("http://" + serverIP + ":" + httpPort);
        
    }
    
    @Bean
    public SysWebSocketHandler sysWebSocketHandler()
    {
        return new SysWebSocketHandler();
    }
    
    public String getHttpPort()
    {
        return httpPort;
    }
    
    @Value(value = "#{propertiesConfig['webapp.httpPort']}")
    public void setHttpPort(String httpPort)
    {
        this.httpPort = httpPort;
    }
    
    public String getServerIP()
    {
        return serverIP;
    }
    
    @Value(value = "#{propertiesConfig['server.ip']}")
    public void setServerIP(String serverIP)
    {
        this.serverIP = serverIP;
    }
    
}