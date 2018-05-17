package com.zc.schedule.product.personnel.service;

import java.util.List;
import java.util.Map;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.product.personnel.entity.Personnel;

public interface PersonnelService
{
    
    /**
     * 分页查询人员列表
     * 
     * @param params
     * @return List
     */
    List<Personnel> getPersonnels(Map<String, Object> params, PageInfo page);
    
    /**
     * 保存新人员或者更新人员信息
     * 
     * @param personnel
     * @return ResultData
     */
    ResultData save(Personnel personnel);
    
    /**
     * 查询用户信息
     * 
     * @param userId
     * @return ResultData
     */
    ResultData getById(Long userId);
    
    /**
     * 删除人员
     * 
     * @param userIds
     * @return ResultData
     */
    String del(String userIds);
    
    /**
     * 切换参与排版状态
     * 
     * @param userId
     * @return ResultData
     */
    ResultData changePartake(Long userId, String partake);
    
    /**
     * 批量
     * 
     * @param list
     * @return ResultData
     */
    ResultData batchSave(List<Personnel> list)
        throws Exception;
        
}
