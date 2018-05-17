package com.zc.schedule.product.scheduleRule.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleRule.entity.CountBean;
import com.zc.schedule.product.scheduleRule.entity.GroupBean;

/**
 * 班次规则接口
 * 
 * @author  Justin
 * @version  v1.0
 */
@Repository(value = "ruleService")
public interface RuleService
{

    
    /**
     * 查询每个班次的每日次数
     * 
     * @param map
     * @return List
     */
    public List<CountBean> getWorkCountInfo(Map<String,Object> map);
    
    /**
     * 修改点击次数
     * 
     * @param ruleBean
     * @return Integer
     */
    Integer updateCount(CountBean ruleBean);

    /**
     * 查询分组和班次关联信息 全部信息
     * 
     * @param map
     * @return List
     */
    List<GroupBean> getGroupInfo(Map<String, Object> map);
    
    
    /**
     * 修改分组与班次关联信息
     * 
     * @param groupBean
     * @return Integer
     */
    Integer updateGroup(GroupBean groupBean);
    
    
    /**
     * 查询分组与班次的关联信息 单个分组
     * 
     * @param groupBean
     * @return List
     */
    List<GroupBean> getWorkInfo(GroupBean groupBean);
    
}
