package com.zc.pivas.docteradvice.service;

import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.repository.DoctorAdviceDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 医嘱service接口
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public interface DoctorAdviceService extends DoctorAdviceDao
{
    /**
     * 
     * 根据医嘱唯一标识查询医嘱信息
     * @param pidsj
     * @return
     */
    List<DoctorAdvice> qryBeanByPidsj(String pidsj);
    
    /**
     * 
     * 根据医嘱唯一标识查询医嘱信息
     * @param pidsj
     * @return
     */
    List<DoctorAdvice> qryBeanDistByPidsj(String pidsj, Integer yzzt);
    
    /**
     * 
     * 
     * 根据条件查询子医嘱
     * @param map 查询条件
     * @return 医嘱信息
     */
    DoctorAdvice getYzByCondition(Map<String, Object> map);
    
}
