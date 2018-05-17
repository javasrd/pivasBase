package com.zc.base.sc.modules.medicalFrequency.service;

import com.zc.base.common.service.BaseService;
import com.zc.base.sc.modules.medicalFrequency.entity.MedicalFrequency;

public interface MedicalFrequencyService extends BaseService<MedicalFrequency, Long> {
    void synData(MedicalFrequency paramMedicalFrequency);
}
