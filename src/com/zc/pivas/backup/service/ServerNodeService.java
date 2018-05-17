package com.zc.pivas.backup.service;

import com.zc.pivas.backup.bean.ServerNodeBean;

/**
 * 
 * 服务器节点接口类
 *
 * @author  cacabin
 * @version  1.0
 */
public interface ServerNodeService
{
    public ServerNodeBean getServerNode(String ip);
}