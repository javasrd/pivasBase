package com.zc.pivas.statistics.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.statistics.bean.druguse.*;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 药物使用统计
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
@MyBatisRepository("drugUseDAO")
public interface DrugUseDAO
{
    /**
     * 
     * 饼图：查询批次统计信息
     * 
     * @return
     */
    List<DrugUseBatchPieBean> queryBatchPieList(@Param("condition")
                                                        PrescriptionSearchBean condition);
    
    /**
     * 
     * 饼图：中某一批次后，数据统计
     * 
     * @param condition
     * @return
     */
    List<DrugUsePieDetailBean> queryBatchDrugClassPieList(@Param("condition")
                                                                  PrescriptionSearchBean condition);
    
    /**
     * 
     * 饼图：查询病区统计信息
     * 
     * @return
     */
    List<DrugUseDeptPieBean> queryDeptPieList(@Param("condition")
                                                      PrescriptionSearchBean condition);
    
    /**
     * 
     * 饼图：中某一病区后，数据统计
     * 
     * @param condition
     * @return
     */
    List<DrugUsePieDetailBean> queryDeptDrugClassPieList(@Param("condition")
                                                                 PrescriptionSearchBean condition);
    
    /**
     * 
     * 柱状图：查询批次统计信息
     * 
     * @return
     */
    List<DrugUseBarGroupByBatch> queryBatchBarList(@Param("condition")
                                                           PrescriptionSearchBean condition);
    
    /**
     * 
     * 柱状图：查询病区统计信息
     * 
     * @return
     */
    List<DrugUseBarGroupByDept> queryDeptBarList(@Param("condition")
                                                         PrescriptionSearchBean condition);
    
}
