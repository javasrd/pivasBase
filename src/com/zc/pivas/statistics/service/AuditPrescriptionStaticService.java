package com.zc.pivas.statistics.service;

import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionNamePieBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionNameStatusBarBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionSearchBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionStatusBean;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;

import java.util.List;

/**
 * 审方工作量统计Service
 *
 * @author jagger
 * @version 1.0
 */
public interface AuditPrescriptionStaticService {
    AuditPrescriptionNameStatusBarBean queryDoctorNameStatusBar(AuditPrescriptionSearchBean staticCheckOrderSearch);

    List<AuditPrescriptionNamePieBean> queryDoctorNamePieList(AuditPrescriptionSearchBean staticCheckOrderSearch);

    List<AuditPrescriptionStatusBean> queryDoctorNameStatusListByID(AuditPrescriptionSearchBean staticCheckOrderSearch);

    /**
     * 查询审方药师信息
     *
     * @return
     */
    List<StaticDoctorNameBean> queryCheckerNameList();
}
