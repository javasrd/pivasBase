package com.zc.pivas.medicaments.service.impl;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.common.service.BaseServiceImpl;
import com.zc.pivas.medicaments.entity.MedicamentsLog;
import com.zc.pivas.medicaments.repository.MedicamentsLogDao;
import com.zc.pivas.medicaments.service.MedicamentsLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 药品
 *
 * @author Jack.M
 * @version 1.0
 */
@Service("medicamentsLogService")
public class MedicamentsLogServiceImpl extends BaseServiceImpl<MedicamentsLog, Long> implements MedicamentsLogService
{
    
    @Resource
    private MedicamentsLogDao medicamentsLogDao;
    
    @Override
    public BaseMapper<MedicamentsLog, Long> getDao()
    {
        return medicamentsLogDao;
    }
    
}
