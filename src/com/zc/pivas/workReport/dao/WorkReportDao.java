package com.zc.pivas.workReport.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.workReport.bean.WorkBean;

import java.util.List;

@MyBatisRepository(value = "workReportDao")
public interface WorkReportDao
{

    List<WorkBean> getWorkList();
    
}
