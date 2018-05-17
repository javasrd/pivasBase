package com.zc.pivas.synresult.service.impl;

import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.configfee.dao.ChargeDetailsDao;
import com.zc.pivas.scans.bean.PqRefFee;
import com.zc.pivas.scans.repository.ScansDao;
import com.zc.pivas.synresult.service.PqPzfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kunkka
 * @version 1.0
 */
@Service("pqPzfService")
public class PqPzfServiceImpl implements PqPzfService {
    @Resource
    private ScansDao scansDao;

    @Resource
    private ChargeDetailsDao chargeDetailsDao;

    @Override
    public List<PqRefFee> qryPqRefFee(PqRefFee pqRefFee) {
        return scansDao.qryPqRefFee(pqRefFee);
    }

    @Override
    public void updatePqRefFee(PqRefFee pqRefFee) {
        scansDao.updatePqRefFee(pqRefFee);
    }

    @Override
    public void updatePZFStatus(ChargeBean chargeBean) {
        chargeDetailsDao.updatePZFStatus(chargeBean);
    }

}
