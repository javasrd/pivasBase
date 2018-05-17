package com.zc.schedule.product.workCount.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleSettings.entity.WorkBean;
import com.zc.schedule.product.workCount.entity.SignBean;

@Repository(value = "workCountService")
public interface WorkCountService
{

    /**
     * 获取值班信息
     * @param reqParam
     * @return
     * 
     */
    List<SignBean> getSignList(Map<String, Object> reqParam);

    /**
     * 签到
     * @param signBean
     * @return
     * 
     */
    int addCheckSign(SignBean signBean);

    /**
     * 获取班次
     * @return
     * 
     */
    List<WorkBean> getWorkList();
    
}
