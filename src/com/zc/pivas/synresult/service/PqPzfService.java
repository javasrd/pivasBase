package com.zc.pivas.synresult.service;

import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.scans.bean.PqRefFee;

import java.util.List;

/**
 * @author kunkka
 * @version 1.0
 */
public interface PqPzfService {
    /**
     * 查询瓶签和配置费的关系及结果
     */
    public List<PqRefFee> qryPqRefFee(PqRefFee pqRefFee);

    /**
     * 修改瓶签和配置费的关系及结果
     */
    public void updatePqRefFee(PqRefFee pqRefFee);

    public void updatePZFStatus(ChargeBean chargeBean);
}
