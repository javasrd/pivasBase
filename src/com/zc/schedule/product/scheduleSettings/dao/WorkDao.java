package com.zc.schedule.product.scheduleSettings.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.base.NormalDao;
import com.zc.schedule.common.entity.PageInfo;


@MyBatisRepository(value = "workDao")
public interface WorkDao<WorkBean> extends NormalDao<WorkBean>
{
    /**
     * 
     * 查询所有班次信息
     * @return List
     */
    List<WorkBean> getWorkInfoList(@Param("map") Map<String,Object> map, PageInfo page);
    
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
     * 
     * @param workBean
     * @return Integer
     */
    Integer addWorkInfo(WorkBean workBean);

    /**
     * 
     * 新增新的班次信息（修改操作）
     * 
     * @param workBean
     * @return Integer
     */
    Integer addNewWorkInfo(WorkBean workBean);
    
    /**
     * 
     * 根据copyid（即旧的workId）获取新的班次信息
     * 
     * @param copyId
     * @return WorkBean
     */
    WorkBean getWorkInfoByCopyId(Integer copyId);

    /**
     * 
     * 将旧的班次状态改为无效
     * 
     * @param workBean
     * @return Integer
     */
    Integer updateOldWorkInfo(WorkBean workBean);
    
    /**
     * 
     * 修改班次信息
     * @param workBean
     * @return Integer
     */
    Integer updateWorkInfo(WorkBean workBean);
    
    
    /**
     * 删除班次信息
     * @param workId
     * @return Integer
     */
    Integer delWorkInfo(Integer workId);
    
    
    /**
     * 
     * 判断班次是否存在排班数据中
     * @param workIds
     * @return Integer
     */
    Integer getScheduleCount(String[] workIds);
    
    
    /**
     * 
     * 判断班次是否存在排班历史数据中
     * @param workIds
     * @return Integer
     */
    Integer getScheduleHistoryCount(String[] workIds);
    
    
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
    List<WorkBean> queryOldWorkInfo(@Param("map") Map<String,Object> map, PageInfo page);
    
    
    /**
     * 
     * 修改班次配色
     * 
     * @param workBean
     * @return Integer
     */
    Integer changeWorkColor(WorkBean workBean);
    
    
    /**
     * 
     * 修改分组班次关联信息
     * 
     * @param workBean
     * @return Integer
     */
    Integer changeGroupInfo(WorkBean workBean);
    
    
    /**
     * 
     * 修改一周排班中关联信息
     * 
     * @param workBean
     * @return Integer
     */
    Integer changeDataInfo(WorkBean workBean);
    
    
    /**
     * 
     * 删除分组班次关联信息
     * 
     * @param workId
     * @return Integer
     */
    Integer deleteGroupInfo(Integer workId);
    
    /**
     * 
     * 获得历史workids
     * 根据当前workId获取修改前的workids信息
     * @param workId
     * @return List
     */
    List<WorkBean> getOldWorkIds(Integer workId);
}
