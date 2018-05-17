package com.zc.schedule.product.report.dao;


import com.zc.schedule.product.report.entity.WorkReportBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "workReportDao")
public interface WorkReportDao
{

    List<WorkReportBean> getWorkList();
    
}
