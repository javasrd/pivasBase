package com.zc.pivas.workReport.service.impl;

import com.zc.pivas.workReport.bean.WorkBean;
import com.zc.pivas.workReport.dao.WorkReportDao;
import com.zc.pivas.workReport.service.WorkReportService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository(value = "workReportServiceImpl")
public class WorkReportServiceImpl implements WorkReportService
{
    
    @Resource(name = "workReportDao")
    private WorkReportDao workReportDao;
    
    @Override
    public List<WorkBean> getWorkList()
    {
        return workReportDao.getWorkList();
    }
    
}
