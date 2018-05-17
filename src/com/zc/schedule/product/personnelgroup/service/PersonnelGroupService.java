package com.zc.schedule.product.personnelgroup.service;

import java.text.ParseException;

import com.zc.schedule.common.entity.ResultData;

public interface PersonnelGroupService
{
    
    /**
     * 保存心新分组
     * 
     * @param groupName
     * @return ResultData
     */
    ResultData saveGroup(String opr, Long groupId, String groupName, String startDate, String endDate);
    
    /**
     * 复制指定日期范围内的人员分组信息到另一范围
     * 
     * @param lastStartDate
     * @param lastEndDate
     * @param startDate
     * @param endDate
     */
    void copyGroup(String lastStartDate, String lastEndDate, String startDate, String endDate);
    
    /**
     * 获取指定周的分组信息
     * 
     * @param startDate
     * @return ResultData
     * @throws ParseException 
     */
    ResultData getGroupInfos(String startDate)
        throws ParseException;
        
    
    /**
     * 
     * 保存人员分组关联关系
     * @param startDate
     * @param endDate
     * @param datas
     * @return ResultData
     */
    ResultData saveGroupInfos(String startDate, String endDate, String datas);
    
    /**
     * 删除分组
     * 
     * @param groupId
     * @param startDate
     * @param endDate
     * @return ResultData
     */
    ResultData del(Long groupId, String startDate, String endDate);
    
}
