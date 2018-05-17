package com.zc.pivas.statistics.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import com.zc.pivas.statistics.bean.prescription.PrescriptionStatusBean;
import com.zc.pivas.statistics.bean.prescription.YDBatchPieBean;
import com.zc.pivas.statistics.bean.prescription.YDDeptPieBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药单统计DAO
 *
 * @author jagger
 * @version 1.0
 */
@MyBatisRepository("medicSingleDao")
public interface PrescriptionDao {

    /**
     * 查询药单状态
     *
     * @param medicSingle
     * @return
     */
    List<PrescriptionStatusBean> queryBatchStatusList(@Param("medicSingle") PrescriptionSearchBean medicSingle);

    /**
     * 查询批次统计信息
     *
     * @param medicSingle
     * @return
     */
    List<YDBatchPieBean> queryBatchPieList(@Param("medicSingle") PrescriptionSearchBean medicSingle);

    /**
     * 根据批次ID 查询批次的药单状态统计
     *
     * @param medicSingle
     * @return
     */
    List<PrescriptionStatusBean> queryBatchStatusListByID(@Param("medicSingle") PrescriptionSearchBean medicSingle);

    /**
     * 查询病区 药单状态统计信息
     *
     * @param medicSingle
     * @return
     */
    List<PrescriptionStatusBean> queryDeptStatusList(@Param("medicSingle") PrescriptionSearchBean medicSingle);

    /**
     * 查询病区统计信息
     *
     * @param medicSingle
     * @return
     */
    List<YDDeptPieBean> queryDeptPieList(@Param("medicSingle") PrescriptionSearchBean medicSingle);

    /**
     * 根据病区名称，查询病区 药单状态统计信息
     *
     * @param medicSingle
     * @return
     */
    List<PrescriptionStatusBean> queryDeptStatusListByName(@Param("medicSingle") PrescriptionSearchBean medicSingle);
}
