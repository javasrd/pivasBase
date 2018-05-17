package com.zc.schedule.product.workCount.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleSettings.entity.WorkBean;
import com.zc.schedule.product.workCount.dao.WorkCountDao;
import com.zc.schedule.product.workCount.entity.SignBean;
import com.zc.schedule.product.workCount.service.WorkCountService;

@Repository(value = "workCountServiceImpl")
public class WorkCountServiceImpl implements WorkCountService
{
    
    @Resource(name = "workCountDao")
    private WorkCountDao workCountDao;
     
    @Override
    public List<SignBean> getSignList(Map<String, Object> reqParam)
    {
        return workCountDao.getSignList(reqParam);
    }

    /**
     * @param reqParam
     * @return
     */
     
    @Override
    public int addCheckSign(SignBean signBean)
    {
        return workCountDao.addCheckSign(signBean);
    }

    /**
     * @return
     */
     
    @Override
    public List<WorkBean> getWorkList()
    {
        return workCountDao.getWorkList();
    }
    
}
