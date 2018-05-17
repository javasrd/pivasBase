package com.zc.schedule.product.workCount.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleSettings.entity.WorkBean;
import com.zc.schedule.product.workCount.entity.SignBean;

@MyBatisRepository(value = "workCountDao")
public interface WorkCountDao
{

    /**
     * 获取值班信息
     * @param params
     * @return
     * 
     */
    List<SignBean> getSignList(@Param("params") Map<String, Object> params);

    /**
     * 添加签到记录
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
