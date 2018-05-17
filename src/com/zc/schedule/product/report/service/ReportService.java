package com.zc.schedule.product.report.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.report.entity.UserBean;
import com.zc.schedule.product.report.entity.WorkBean;

@Repository(value = "reportService")
public interface ReportService
{

    /**
     * 获取休假班次
     * @return List
     */
    List<WorkBean> getResetWork();


    /**
     * 获取人员班次数据
     * @param startDate
     * @param endDate
     * @param workList
     * @return List
     * @throws Exception 
     */
    List<UserBean> getUserWork(String startDate, String endDate, List<WorkBean> workList) throws Exception;


    /**
     * 获取工作班次
     * @return List
     */
    List<WorkBean> getWorkList();


    /**
     * 导出报表
     * @param workList
     * @param userList
     * @param filePath 
     */
    void exportUserWork(List<WorkBean> workList, List<UserBean> userList, String filePath) throws Exception;

    
}
