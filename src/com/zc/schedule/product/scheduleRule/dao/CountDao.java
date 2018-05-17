package com.zc.schedule.product.scheduleRule.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.base.NormalDao;


@MyBatisRepository(value = "countDao")
public interface CountDao<CountBean> extends NormalDao<CountBean>

{
    
    /**
     * 
     * 查询每个班次的每日次数
     * 
     * @param map
     * @return List
     */
    public List<CountBean> getWorkCountInfo(Map<String,Object> map);
    
    /**
     * 
     * 修改点击次数
     * 
     * @param countBean
     * @return Integer
     */
    Integer updateCount(CountBean countBean);
    
    
}
