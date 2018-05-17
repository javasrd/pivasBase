package com.zc.pivas.workReport.service;

import com.zc.pivas.workReport.bean.WorkBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "workReportService")
public interface WorkReportService
{

    List<WorkBean> getWorkList();
    
}
