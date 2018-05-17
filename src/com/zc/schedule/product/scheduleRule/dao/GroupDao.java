package com.zc.schedule.product.scheduleRule.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.base.NormalDao;


@MyBatisRepository(value = "groupDao")
public interface GroupDao<GroupBean> extends NormalDao<GroupBean>
{
    /**
     * 
     * 查询分组信息
     * 
     * @param map
     * @return List
     */
    public List<GroupBean> getGroupInfo(Map<String, Object> map);
    
    
    /**
     * 
     * 查询分组与班次的关联信息
     * 
     * @param groupBean
     * @return List
     */
    public List<GroupBean> getWorkInfo(GroupBean groupBean);
    
    
    /**
     * 
     * 新增分组与班次关联信息
     * 
     * @param groupBean
     * @return Integer
     */
    Integer addGroupWork(GroupBean groupBean);
    
    
    /**
     * 删除分组与班次关联信息
     * 
     * @param groupBean
     * @return Integer
     */
    Integer deleteGroupWork(GroupBean groupBean);
}
