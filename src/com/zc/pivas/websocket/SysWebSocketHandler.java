package com.zc.pivas.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * 扫描
 *
 * @author  cacabin
 * @version  1.0
 */
public class SysWebSocketHandler implements WebSocketHandler
{
    
    private static final Logger logger;
    
    private static final ArrayList<WebSocketSession> users;
    
    static
    {
        users = new ArrayList<WebSocketSession>();
        logger = LoggerFactory.getLogger(WebSocketHandler.class);
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
        throws Exception
    {
        logger.debug("connect to the websocket success......");
        users.add(session);
        //        String userName = (String)session.getAttributes().get(WSConstants.WEBSOCKET_USERNAME);
        //        if (userName != null)
        //        {
        //            
        //            session.sendMessage(new TextMessage("445555"));
        //        }
    }
    
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
        throws Exception
    {
        
        //sendMessageToUsers();
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
        throws Exception
    {
        if (session.isOpen())
        {
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
        throws Exception
    {
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
    
    @Override
    public boolean supportsPartialMessages()
    {
        return false;
    }
    
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message)
    {
        for (WebSocketSession user : users)
        {
            try
            {
                if (user.isOpen())
                {
                    user.sendMessage(message);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 给某个用户发送消息
     *
     * @param message
     */
    public void sendMessageToUser(String message)
    {
        for (WebSocketSession user : users)
        {
            //if (user.getAttributes().get(WSConstants.WEBSOCKET_USERNAME).equals(userName))
            //{
            try
            {
                if (user.isOpen())
                {
                    user.sendMessage(new TextMessage(message));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //break;
            //}
        }
    }
}