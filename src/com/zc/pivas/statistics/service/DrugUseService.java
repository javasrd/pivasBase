package com.zc.pivas.statistics.service;

import com.zc.pivas.statistics.bean.druguse.DrugUseBatchBarBean;
import com.zc.pivas.statistics.bean.druguse.DrugUseDeptBarBean;
import com.zc.pivas.statistics.repository.DrugUseDAO;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;

/**
 * 
 * 药物使用统计
 *
 * @author  cacabin
 * @version  1.0
 */
public interface DrugUseService extends DrugUseDAO
{
    /**
     * 
     * 柱状图：查询批次统计信息
     * @return
     */
    DrugUseBatchBarBean queryBarGroupByBatchList(PrescriptionSearchBean condition);
    
    /**
     * 
     * 柱状图：查询病区统计信息
     * @return
     */
    DrugUseDeptBarBean queryBarGroupByDeptList(PrescriptionSearchBean condition);
}
