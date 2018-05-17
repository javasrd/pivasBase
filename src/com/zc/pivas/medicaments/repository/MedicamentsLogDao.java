package com.zc.pivas.medicaments.repository;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.medicaments.entity.MedicamentsLog;

/**
 * 药品dao
 *
 * @author Jack.M
 * @version 1.0
 */
@MyBatisRepository("medicamentsLogDao")
public interface MedicamentsLogDao extends BaseMapper<MedicamentsLog, Long>
{
    
}
