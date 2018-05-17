package com.zc.pivas.configfee.service;

import com.zc.pivas.docteradvice.entity.SynDoctorAdviceBean;
import com.zc.pivas.configfee.bean.ConfigFeeChargeBean;

import java.util.List;

/**
 * 配置费计算服务接口
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigFeeChargeService {
    /**
     * 配置费规则计算接口
     *
     * @param yzList 医嘱列表，只针对成组的一包水
     * @return 需要收费的收费编码
     */
    List<ConfigFeeChargeBean> findConfigCharge(List<SynDoctorAdviceBean> yzList);
}
