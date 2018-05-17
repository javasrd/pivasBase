package com.zc.pivas.redis.impl;

import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.backup.bean.ServerNodeBean;
import com.zc.pivas.backup.service.ServerNodeService;
import com.zc.pivas.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis 服务接口实现类
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService
{
    @Resource
    RedisTemplate<String, String> redisTemplate;
    
    @Resource
    private DoctorAdviceMainService yzMainService;
    
    @Resource
    private ServerNodeService serverNodeService;
    
    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);
    
    @Override
    public String get(String key)
    {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }
    
    @Override
    public void set(String key, String data)
    {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, data);
    }
    
    @Override
    public void delete(String key)
    {
        redisTemplate.delete(key);
    }
    
    @Override
    public void delete(Collection<String> keyList)
    {
        redisTemplate.delete(keyList);
    }
    
    @Override
    public void setKnowledgeData()
    {
        //备机状态下不执行任务
        if(serverNodeService != null)
        {
            String localip = Propertiesconfiguration.getStringProperty("localip");
            if(localip != null)
            {
                ServerNodeBean bean = serverNodeService.getServerNode(localip);
                if(bean != null)
                {
                    if(bean.getFlag() == 1)
                    {
                        log.info("Backup mode,do not exec time task setKnowledgeData");
                        return;
                    }
                }
            }
        }

        log.info("Ready to exec time task setKnowledgeData");
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        // 查询知识库表中需要更新数据
        List<Map<String, Object>> knowledgeData = yzMainService.selectKnowledgeData(map);
        
        map.put("isdelete", "Y");
        // 查询需要删除的数据
        List<Map<String, Object>> knowledgeDataDel = yzMainService.selectKnowledgeData(map);
        
        if (DefineCollectionUtil.isNotEmpty(knowledgeDataDel))
        {
            for (Map<String, Object> data : knowledgeDataDel)
            {
                delete(data.get("KEY").toString());
            }
        }
        
        if (DefineCollectionUtil.isNotEmpty(knowledgeData))
        {
            for (Map<String, Object> data : knowledgeData)
            {
                set(data.get("KEY").toString(), data.get("RESULT").toString());
            }
        }
        
        // 删除不确定的结果
        yzMainService.deleteKnowledge();
    }
    
    /**
     * @return
     */
    public Object deleteAll()
    {
        return redisTemplate.execute(new RedisCallback<Object>()
        {
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.flushDb();
                return null;
            }
        });
    }
}
