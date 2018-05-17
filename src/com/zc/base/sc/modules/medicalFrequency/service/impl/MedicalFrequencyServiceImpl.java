package com.zc.base.sc.modules.medicalFrequency.service.impl;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.common.service.BaseServiceImpl;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.medicalFrequency.entity.MedicalFrequency;
import com.zc.base.sc.modules.medicalFrequency.repository.MedicalFrequencyDao;
import com.zc.base.sc.modules.medicalFrequency.service.MedicalFrequencyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MedicalFrequencyServiceImpl extends BaseServiceImpl<MedicalFrequency, Long> implements MedicalFrequencyService {
    @Resource
    private MedicalFrequencyDao medicalFrequencyDao;

    public BaseMapper<MedicalFrequency, Long> getDao() {
        return this.medicalFrequencyDao;
    }

    public List<MedicalFrequency> queryByPaging(JqueryStylePaging paging, MedicalFrequency condition) {
        return this.medicalFrequencyDao.queryByPaging(paging, condition);
    }

    public int getTotalCount(MedicalFrequency condition) {
        return this.medicalFrequencyDao.getTotalCount(condition);
    }

    public void synData(MedicalFrequency model) {
        this.medicalFrequencyDao.synData(model);
    }
}
