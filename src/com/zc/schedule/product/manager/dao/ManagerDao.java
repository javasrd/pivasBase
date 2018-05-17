package com.zc.schedule.product.manager.dao;

import java.util.ArrayList;
import java.util.List;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.product.manager.entity.GroupInfoBean;
import com.zc.schedule.product.manager.entity.ManagerBean;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.manager.entity.UserMonthBean;
import com.zc.schedule.product.manager.entity.WeekDataBean;
import com.zc.schedule.product.manager.entity.WorkBean;
import com.zc.schedule.product.scheduleSettings.entity.PostBean;

/**
 * 
 * 排班管理DAO
 * @author  Justin
 * @version  v1.0
 */
@MyBatisRepository(value = "managerDao")
public interface ManagerDao
{
    /**
     * 获取岗位
     * @return List
     */
    List<PostBean> getPostList();

    /**
     * 获取备注
     * @param startStr
     * @param endStr
     * @return String
     */
    String getRemarkStr(@Param("startDate") String startStr,@Param("endDate") String endStr);

    /**
     * 保存备注
     * @param startDate
     * @param endDate
     * @param remark 
     * @return int
     */
    int addRemark(@Param("startDate")String startDate, @Param("endDate")String endDate,  @Param("remark") String remark);

    /**
     * 更新备注
     * @param startDate
     * @param endDate
     * @param remark
     * @return int
     */
    int updateRemark(@Param("startDate")String startDate, @Param("endDate")String endDate,  @Param("remark") String remark);

    /**
     * 判断是否存在备注
     * @param startDate
     * @param endDate
     * @return int
     */
    int getCountRemark(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 获取排班数据
     * @param bean
     * @return List
     */
    List<ScheduleBean> getGroupList(ScheduleBean bean);

    /**
     * 获取分组中人员的信息
     * @param data
     * @return List
     */
    List<GroupInfoBean> getGroupInfoList(ScheduleBean data);

    /**
     * 获取人员排班信息
     * @param infoBean
     * @return ManagerBean
     */
    ManagerBean getManagerData(GroupInfoBean infoBean);

    /**
     * 获取人员一周排班数据
     * @param id
     * @param date
     * @return List
     */
    List<WeekDataBean> getWeekManagerData(@Param("id") Long id,@Param("date") String date);

    /**
     * 获取上周人员排班数据
     * @param user_id
     * @param lastStart
     * @param lastEnd
     * @return ManagerBean
     */
    ManagerBean getLastManagerData(@Param("user_id") Integer user_id, @Param("validity_start") String lastStart, @Param("validity_end") String lastEnd);

    /**
     * 获取上周人员一周排班数据
     * @param lastId
     * @param lastDate
     * @return String
     */
    String getLastWeekManagerData(@Param("id") Long lastId, @Param("date") String lastDate);

    /**
     * 获取班次数据
     * @return List
     */
    List<WorkBean> getWorkList();

    /**
     * 删除排班数据
     * @param startDate
     * @param endDate
     */
    void deleteScheduleData(@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 删除排班班次数据
     * @param startDate
     * @param endDate
     */
    void deleteScheduleDataWeek(@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 保存一周排班数据
     * @param manager
     */
    void saveSchedule(ManagerBean manager);

    /**
     * 保存一周每天班次数据
     * @param weekData
     */
    void saveScheduleWeekData(List<WeekDataBean> weekData);

    /**
     * 判断是否是节假日
     * @param date
     * @return int
     */
    int isHoliday(@Param("date") String date);

    /**
     * 获取分组指定班次
     * @param groupId
     * @return ArrayList
     */
    ArrayList<WorkBean> getGroupWorkList(@Param("groupId") Long groupId);

    /**
     * 获取休息班次
     * @return WorkBean
     */
    WorkBean getRestWork();

    /**
     * 获取工作类型的班次
     * @return ArrayList
     */
    ArrayList<WorkBean> getWorkTypeList();

    /**
     * 获取节假日工作多的人员
     * @param groupInfo
     * @param userNum 
     * @return List
     */
    List<Integer> getUserHolidayWorkCount(@Param("list") List<GroupInfoBean> groupInfo, @Param("num") int userNum);

    /**
     * 获取每天工作时间
     * @param id
     * @param date
     * @return float
     */
    float getWeekManagerDataTime(@Param("id") Long id,@Param("date") String date);

    /**
     * 更新节假日工作次数
     * @param id 
     * @param type
     * @param date
     */
    void updateHolidayWork(@Param("id") int id, @Param("type") String type,@Param("date") String date);

    /**
     * 是否存在该人员节假日工作数据
     * @param id
     * @return int
     */
    int getUserHolidayWorkTimes(@Param("id") int id);

    /**
     * 添加人员节假日工作计数
     * @param id
     * @param name
     * @param date
     */
    void addUserHolidayWorkTime(@Param("id") int id,@Param("name") String name,@Param("date") String date);

    /**
     * 修改累计欠休
     * @param userId
     * @param sDate
     * @param numStr 
     */
    void updateTotlalOwnTime(@Param("id")int userId,@Param("date") String sDate,@Param("change") String numStr);

    /**
     * 是否存在周排版数据
     * @param userId
     * @param sDate
     * @return ManagerBean
     */
	ManagerBean existSheduleData(@Param("id") int userId,@Param("date") String sDate);

    /**
     * 获取日班次数据
     * @param id
     * @param dayDate
     * @return List
     */
    List<WorkBean> getDayWorkList(@Param("id") Long id,@Param("date") String dayDate);

    /**
     * 根据用户获取每日班次
     * @param user_id
     * @param value
     * @return List
     */
    List<WorkBean> getDayWorkListByUser(@Param("id") int user_id,@Param("date") String value);

    /**
     * 获取一月每日排班数据
     * @param user_id
     * @param value
     * @return List
     */
    List<WeekDataBean> getWeekManagerDataByUser(@Param("id") int user_id,@Param("date") String value);

    /**
     * 获取人员信息
     * @return List
     */
    List<UserMonthBean> getUser();

}
