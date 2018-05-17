package com.zc.schedule.product.manager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.manager.entity.ManagerBean;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.manager.entity.TempBean;
import com.zc.schedule.product.manager.entity.UserMonthBean;
import com.zc.schedule.product.manager.entity.WorkBean;
import com.zc.schedule.product.scheduleSettings.entity.PostBean;

@Repository(value = "managerService")
public interface ManagerService
{

    /**
     * 获取岗位
     * @return List
     */
    List<PostBean> getPostList();

    /**
     * 获取备注
     * @param dateList
     * @return String
     * @throws Exception 
     */
    String getRemarkStr(List<TempBean> dateList) throws Exception;

    /**
     * 保存备注
     * @param startDate
     * @param endDate
     * @return int
     */
    int addRemark(String startDate, String endDate, String remark);

    /**
     * 获取备注数量
     * @param startDate
     * @param endDate
     * @return int
     */
    int getCountRemark(String startDate, String endDate);

    /**
     * 更新备注
     * @param startDate
     * @param endDate
     * @param remark
     * @return int
     */
    int updateRemark(String startDate, String endDate, String remark);

    /**
     * 初始化备注
     * @param startDate
     * @param endDate
     * @return String
     */
    String initRemark(String startDate, String endDate);

    /**
     * 获取这周排班数据
     * @param dateList
     * @return List
     * @throws Exception 
     */
    List<ScheduleBean> getWeekList(List<TempBean> dateList) throws Exception;

    /**
     * 获取班次数据
     * @return List
     */
    List<WorkBean> getWorkList();

    /**
     * 保存排班数据
     * @param managerArray
     * @param request 
     * @throws Exception 
     */
    void saveSchedule(ArrayList<ManagerBean> managerArray, HttpServletRequest request) throws Exception;

    /**
     * 删除排班数据
     * @param startDate
     * @param endDate
     */
    void deleteScheduleData(String startDate, String endDate);

    /**
     * 删除排班班次数据
     * @param startDate
     * @param endDate
     */
    void deleteScheduleDataWeek(String startDate, String endDate);

    /**
     * 自动排班数据
     * @param dateList
     * @return
     * @throws Exception 
     */
    List<ScheduleBean> getAutoWeekList(List<TempBean> dateList) throws Exception;

    /**
     * 更新节假日次数
     * @param changeArray
     * @param dateList 
     * @param request 
     * @throws Exception 
     */
    void updateHolidayWork(ArrayList<TempBean> changeArray, List<TempBean> dateList, HttpServletRequest request) throws Exception;

    /**
     * 获取日视图数据
     * @param dateList
     * @param dayDate
     * @return List
     * @throws Exception 
     */
    List<ScheduleBean> getDayList(List<TempBean> dateList, String dayDate) throws Exception;

    /**
     * 导出周排班数据
     * @param date
     * @param response 
     * @param filePath 
     * @throws IOException 
     * @throws Exception 
     */
    void excelExportWeek(String date, HttpServletResponse response, String filePath) throws Exception;

    /**
     * 导出日视图
     * @param date
     * @param response
     * @param filePath
     */
    void excelExportDay(String date, HttpServletResponse response, String filePath) throws Exception;

    /**
     * 导出月视图
     * @param date
     * @param response
     * @param filePath
     */
    void excelExportMonth(String date, HttpServletResponse response, String filePath) throws Exception;

    /**
     * 打印周
     * @param dateList
     * @return List
     */
    List<ScheduleBean> getPrintWeek(List<TempBean> dateList) throws Exception;

    /**
     * 获取一月排班数据
     * @param monthDateList
     * @return List
     */
    List<UserMonthBean> getPrintMonth(List<TempBean> monthDateList) throws Exception;


}
