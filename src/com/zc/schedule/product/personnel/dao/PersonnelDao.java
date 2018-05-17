package com.zc.schedule.product.personnel.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.product.manager.entity.ManagerBean;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.personnel.entity.Personnel;

@MyBatisRepository
public interface PersonnelDao
{
    
    /**
     * 分页查询人员列表
     * 
     * @param params
     * @return List
     */
    List<Personnel> getPersonnels(@Param("params") Map<String, Object> params, PageInfo page);
    
    /**
     * 保存新人员
     * 
     * @param personnel
     */
    void save(@Param("p") Personnel personnel);
    
    /**
    * 更新人员信息
    * 
    * @param personnel
    */
    void update(@Param("p") Personnel personnel);
    
    /**
     * 查询用户信息
     * 
     * @param userId
     * @return Personnel
     */
    Personnel getById(@Param("userId") Long userId);
    
    /**
     * 删除人员
     * 
     * @param userId
     */
    int delById(@Param("userId") String userId);
    
    void changePartake(@Param("userId") Long userId, @Param("partake") String partake);
    
    /**
     * 需要把group_info中的对应记录清除，清除本周及之后的记录
     * 
     * @param userId
     * @param startDate
     */
    int delGroupInfoForUserDel(@Param("userId") Long userId, @Param("startDate") String startDate);
    
    /**
     * 把排版表sche_record中的对应记录清除，清除本周及之后的记录
     * 
     * @param userId
     * @param startDate
     * @return integer
     */
    int delDataForUserDel(@Param("userId") Long userId, @Param("startDate") String startDate);
    
    /**
     * 根据电话查询人员
     * 
     * @param tellphone
     * @return List
     */
    List<Personnel> getByTellphone(@Param("tellphone") String tellphone);
    
    /**
     * 查询指定开始时间的分组人员关联数据个数
     * 
     * @param userId
     * @param startDate
     * @return integer
     */
    int getGroupInfoCount(@Param("userId") Long userId, @Param("startDate") String startDate);
    
    /**
     * 获取当前周以后周的开始日期，包括当周
     * 
     * @param stratDate
     * @return List
     */
    List<String> getLaterStartDates(@Param("startDate") String stratDate);
    
    /**
     * 
     * 删除人员后，更新人员分组信息
     * @param userId
     * @param startDate
     * @return integer
     */
    int updateGroupInfoForUserDel(@Param("userId") Long userId, @Param("startDate") String startDate);
    
    /**
     * 查询出当前日期之后的排版数据的工作日期
     * 
     * @param userId
     * @param dateToString
     * @return
     */
    List<String> getWorkDateForUserDel(@Param("userId") Long userId, @Param("startDate") String startDate);
    
    /**
     * 判断该日期是否是节假日
     * 
     * @param workDate
     * @return
     */
    int getCountForWorkDateIsHoliday(@Param("workDate") String workDate);
    
    /**
     * 更新节假日工作次数
     * 
     * @param userId
     * @param count
     */
    int updateWorkDateIsHolidayCount(@Param("userId") Long userId, @Param("count") int count);
    
    /**
     * 
     * 根据当前用户获取该人员处在的分组信息
     * <功能详细描述>
     * @param bean
     * @return
     * @see [类、类#方法、类#成员]
     */
    ScheduleBean getGroupIdByUserId(@Param("userId") Long userId, @Param("validity_start") String validity_start, @Param("validity_end") String validity_end);
    
    /**
     * 
     * 更新一周总信息（当前周一至当天）不能参与排班的用户
     * <功能详细描述>
     * @param managerBean
     * @see [类、类#方法、类#成员]
     */
    int updateScheduleDataByUserId(ManagerBean managerBean);
    
    /**
     * 
     * 删除一周总信息（当天之后的信息）不能参与排班的用户
     * <功能详细描述>
     * @param userId
     * @param validity_start
     * @see [类、类#方法、类#成员]
     */
    int deleteScheduleDataByUserId(@Param("userId") Long userId, @Param("validity_start") String validity_start);
    
    /**
     * 
     * 获得班次类型
     * <功能详细描述>
     * @param userId
     * @param validity_start
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getWorkTypeByUserId(@Param("userId") Long userId, @Param("validity_start") String validity_start);
    
}
