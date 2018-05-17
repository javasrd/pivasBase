package com.zc.base.sc.modules.medicalFrequency.repository;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sc.modules.medicalFrequency.entity.MedicalFrequency;

@MyBatisRepository
public abstract interface MedicalFrequencyDao extends BaseMapper<MedicalFrequency, Long> {
    public abstract void synData(MedicalFrequency paramMedicalFrequency);
}
