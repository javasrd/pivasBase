package com.zc.pivas.statistics.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionNamePieBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionSearchBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionStatusBean;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审方工作量统计Dao
 *
 * @author jagger
 * @version 1.0
 */
@MyBatisRepository("staticCheckOrderDao")
public interface AuditPrescriptionStatsDao {
    /**
     * 医嘱审方结果（按医师和状态分组）
     *
     * @param StaticCheckOrder
     * @return
     */
    List<AuditPrescriptionStatusBean> queryDoctorNameStatusList(@Param("staticCheckOrder") AuditPrescriptionSearchBean StaticCheckOrder);

    /**
     * 查询医生对应医嘱统计信息
     *
     * @param StaticCheckOrder
     * @return
     */
    List<AuditPrescriptionNamePieBean> queryDoctorNamePieList(@Param("staticCheckOrder") AuditPrescriptionSearchBean StaticCheckOrder);

    /**
     * 根据批次ID 查询批次的药单状态统计
     *
     * @param StaticCheckOrder
     * @return
     */
    List<AuditPrescriptionStatusBean> queryDoctorNameStatusListByID(@Param("staticCheckOrder") AuditPrescriptionSearchBean StaticCheckOrder);

    /**
     * 查询医生列表
     *
     * @return
     */
    List<StaticDoctorNameBean> queryCheckerNameList();
}
