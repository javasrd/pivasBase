package com.zc.schedule.product.scheduleSettings.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.product.scheduleSettings.entity.WorkBean;
/**
 * 
 * 班次接口接口类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Repository(value = "workService")
public interface WorkService
{
    /**
     * 
     * 查询所有班次信息
     * @return List
     */
    List<WorkBean> getWorkInfoList(Map<String,Object> map, PageInfo page);
    
    
    /**
     * 查询单个班次信息
     * 
     * @param workId 
     * @return WorkBean
     */
    WorkBean getWorkById(Integer workId);
    
    /**
     * 
     * 判断班次名称是否重复
     * 
     * @param workName
     * @return Integer
     */
    Integer checkWorkNameIsExitst(String workName);
    
    /**
     * 新增班次信息
     * @param workBean
     * @return Integer
     */
    Integer addWorkInfo(WorkBean workBean);

    
    /**
     * 
     * 修改班次信息
     * @param workBean    新的班次信息
     * @param oldworkBean 旧的班次信息
     * @return Integer
     */
    Integer updateWorkInfo(WorkBean workBean, WorkBean oldworkBean);
    
    
    /**
     * 删除班次信息
     * @param workId
     * @return Integer
     */
    Integer delWorkInfo(String workId);
    
    
    /**
     * 
     * 验证班次是否存在排班数据和排班历史数据中
     * @param workId
     * @return Boolean
     */
    Boolean checkWorkIsExitst(String workId);
    
    /**
     * 更新班次的使用状态
     * 
     * @param workBean
     * @return Integer
     */
    Integer changeWorkStatus(WorkBean workBean);
    
    /**
     * 
     * 查询选择班次的历史信息
     * 
     * @param map
     * @param page
     * @return List
     */
    List<WorkBean> queryOldWorkInfo(Map<String, Object> map, PageInfo page);
    
    
    /**
     * 更新班次的配色
     * 
     * @param workBean
     * @return Integer
     */
    Integer changeWorkColor(WorkBean workBean);
}
