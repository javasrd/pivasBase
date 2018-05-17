package com.zc.pivas.backup.service.impl;


import com.zc.pivas.backup.bean.ServerNodeBean;
import com.zc.pivas.backup.dao.BackDAO;
import com.zc.pivas.backup.service.ServerNodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 
 * 服务器节点实现类
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("ServerNodeService")
public class ServerNodeServiceImpl implements ServerNodeService
{

    @Resource
    private BackDAO backDAO;
    
    @Override
    public ServerNodeBean getServerNode(String ip)
    {
       return backDAO.getServerNode(ip);
    }
    
}