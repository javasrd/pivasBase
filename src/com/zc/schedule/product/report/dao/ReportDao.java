package com.zc.schedule.product.report.dao;

import java.util.List;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.product.report.entity.UserBean;
import com.zc.schedule.product.report.entity.WorkBean;

@MyBatisRepository(value = "reportDao")
public interface ReportDao
{

    /**
     * 获取休假班次
     * @return List
     */
    List<WorkBean> getResetWork();

    /**
     * 获取人员
     * @return List
     */
    List<UserBean> getUser();

    /**
     * 获取班次总共时
     * @param workid
     * @param user_id
     * @param startDate
     * @param endDate
     * @return double
     */
    double getWorkTotal(@Param("workid") String workid,@Param("userid") String user_id,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 获取工作列表
     * @return List
     */
    List<WorkBean> getWorkList();

}

