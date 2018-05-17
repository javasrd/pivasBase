package com.zc.schedule.product.personnelgroup.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.personnel.entity.Personnel;
import com.zc.schedule.product.personnelgroup.entity.GroupInfo;

@MyBatisRepository("personnelGroupDao")
public interface PersonnelGroupDao
{
    
    /**
     * 获取指定日期的分组信息
     * 
     * @param startDate
     * @return List
     */
    List<GroupInfo> getGroups(@Param("startDate") String startDate);
    
    /**
     * 
     * 查询最近的分组信息的开始和结束时间
     * @param startDate
     * @return Map
     */
    Map<String, String> getLatestValidityDate(@Param("startDate") String startDate);
    
    /**
     * 查询没有参与分组的人员
     * 
     * @param startDate
     * @return List
     */
    List<Personnel> getDefaultPersonnels(@Param("startDate") String startDate);
    
    /**
     * 查询分组下的人员信息
     * 
     * @param groupId
     * @param startDate
     * @return List
     */
    List<Personnel> getPersonnels(@Param("groupId") Long groupId, @Param("startDate") String startDate);
    
    /**
     * 查询所有分组
     * 
     * @return List
     */
    List<GroupInfo> getAllGroups();
    
    /**
     * 判断本周内分组名称是否重复
     * 
     * @param groupName
     * @param startDate
     * @return integer
     */
    int getGroupCount(@Param("groupId") Long groupId, @Param("groupName") String groupName,
        @Param("startDate") String startDate);
    
    /**
     * 保存分组信息
     * 
     * @param group
     */
    void saveGroup(GroupInfo group);
    
    /**
     * 获取指定周的人员分组信息
     * 
     * @param startDate
     * @return List
     */
    List<GroupInfo> getGroupInfos(@Param("startDate") String startDate);
    
    /**
     * 把简略的分组信息存入分组人员关联表， 只保存分组信息，用户信息空缺
     * 
     * @param group
     */
    void saveGroupInfo(@Param("group") GroupInfo group);
    
    /**
     * 把完整的分组信息存入分组人员关联表
     * 
     * @param group
     */
    void saveGroupInfo2(@Param("group") GroupInfo group);
    
    /**
     * 把分组信息存入分组人员关联表（用户名称查表）
     * 
     * @param group
     */
    void saveGroupInfo3(@Param("group") GroupInfo group);
    
    /**
     * 更新分组排序
     * 
     * @param groupId
     * @param sort
     */
    void updateGroupSort(@Param("groupId") Long groupId, @Param("sort") int sort);
    
    /**
     * 删除旧的人员分组信息
     * 
     * @param startDate
     */
    void removeAll(@Param("startDate") String startDate);
    
    /**
     * 修改分组名称
     * 
     * @param groupId
     * @param groupName
     */
    void updateGroupName(@Param("groupId") Long groupId, @Param("groupName") String groupName);
    
    /**
     * 移除指定周中的分组信息
     * 
     * @param groupId
     * @param startDate
     */
    void deleteGroupInfoByGroupIdAndStartDate(@Param("groupId") Long groupId, @Param("startDate") String startDate);
    
    /**
     * 从分组表中删除该分组，如果该分组没有被关联
     * 
     * @param groupId
     */
    void delGroupByGroupId(@Param("groupId") Long groupId);
    
    /**
     * 获取本周的排版数据
     * 
     * @param startDate
     * @return List
     */
    List<ScheduleBean> getScheduleDataByStartDate(@Param("startDate") String startDate);
    
    /**
     * 删除排版数据
     * 
     * @param userId
     * @param startDate
     * @return integer
     */
    int delScheduleData(@Param("userId") Integer userId, @Param("startDate") String startDate);
    
    /**
     * 更新排版数据
     * 
     * @param userId
     * @param startDate
     * @param groupIdNew
     * @return integer
     */
    int updateScheduleData(@Param("userId") Integer userId, @Param("startDate") String startDate,
        @Param("groupId") Long groupIdNew);
    
    /**
     * 
     * 根据id获取分组信息
     * @param groupId
     * @return GroupInfo
     */
    GroupInfo getById(@Param("groupId") Long groupId);
    
    /**
     * 删除指定周的排班数据
     * 
     * @param startDate
     */
    void delScheduleDataByStartDate(@Param("startDate") String startDate);
    
    /**
     * 删除与班次关联的信息
     * 
     * @param groupId
     */
    void delWorkRefDataByGroupId(@Param("groupId") Long groupId);
    
}
