package com.zc.pivas.redis;

import java.util.Collection;

/**
 * Redis 服务接口
 *
 * @author  cacabin
 * @version  1.0
 */
public interface RedisService
{
    /**
     * 往Redis内存服务里面，存放键值对
     * 
     * @param key
     * @param data
     */
    void set(String key, String data);
    
    /**
     * 根据指定的键，获取对应的值
     * 
     * @param key
     * @return
     */
    String get(String key);
    
    /**
     * 根据键，删除指定的内容
     * 
     * @param key
     */
    void delete(String key);
    
    /**
     * 清空
     * 
     * @param key
     */
    Object deleteAll();
    
    /**
     * 根据键的集合，删除内容
     * 
     * @param list
     */
    void delete(Collection<String> keyList);
    
    /**
     * 
     * 获取药师审方知识库数据放到redis中
     * <功能详细描述>
     */
    void setKnowledgeData();
}
