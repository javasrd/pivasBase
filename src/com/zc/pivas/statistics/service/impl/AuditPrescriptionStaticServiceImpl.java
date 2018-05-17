package com.zc.pivas.statistics.service.impl;

import com.zc.pivas.statistics.repository.AuditPrescriptionStatsDao;
import com.zc.pivas.statistics.service.AuditPrescriptionStaticService;
import com.zc.pivas.statistics.bean.auditPrescription.*;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 审方工作量统计Service实现类
 *
 * @author jagger
 * @version 1.0
 */
@Service("staticCheckOrderService")
public class AuditPrescriptionStaticServiceImpl implements AuditPrescriptionStaticService {
    /**
     * 审方统计Dao对象
     */
    @Resource
    private AuditPrescriptionStatsDao auditPrescriptionStatsDao;

    @Override
    public AuditPrescriptionNameStatusBarBean queryDoctorNameStatusBar(AuditPrescriptionSearchBean configFeeSearch) {
        List<AuditPrescriptionStatusBean> doctorStatusList = auditPrescriptionStatsDao.queryDoctorNameStatusList(configFeeSearch);
        if (doctorStatusList == null) {
            return null;
        }
        //格式化为医生-> 审方错误类型->数量 维度的统计
        List<AuditPrescriptionName2StatusBean> doctor2StatusList = formatDoctor2StatusStatic(doctorStatusList);
        //格式化 审方错误类型->医生-> 维度的统计
        // 医生名称列表
        AuditPrescriptionNameStatusBarBean doctorStatusBarBean = formatStatus2DoctorStatic(doctor2StatusList);
        return doctorStatusBarBean;
    }


    private AuditPrescriptionNameStatusBarBean formatStatus2DoctorStatic(List<AuditPrescriptionName2StatusBean> doctor2StatusList) {
        AuditPrescriptionNameStatusBarBean doctorStatusBarBean = new AuditPrescriptionNameStatusBarBean();
        List<AuditPrescriptionStatus2NameBean> status2DoctorList = doctorStatusBarBean.getStatus2DoctorList();

        List<AuditPrescriptionResultType> orderCheckResultTypes = setOrderCheckResult();

        for (AuditPrescriptionResultType statusKeyItem : orderCheckResultTypes) {
            AuditPrescriptionStatus2NameBean status2DoctorBeanNew = new AuditPrescriptionStatus2NameBean();
            status2DoctorBeanNew.setStatusKey(statusKeyItem.getStatusKey());
            status2DoctorBeanNew.setStatusName(statusKeyItem.getStatusName());

            status2DoctorList.add(status2DoctorBeanNew);
        }
        for (AuditPrescriptionName2StatusBean doctor2StatusBean : doctor2StatusList) {
            List<AuditPrescriptionStatusCount> dctrStatusCountList = doctor2StatusBean.getDctrStatusCountList();
            for (AuditPrescriptionStatus2NameBean status2doctorItem : status2DoctorList) {
                AuditPrescriptionStatusCount dctrStatusCountDesc = null;
                for (AuditPrescriptionStatusCount dctrStatusCountItem : dctrStatusCountList) {
                    if (status2doctorItem.getStatusKey() != null
                            && status2doctorItem.getStatusKey().equals(dctrStatusCountItem.getStatusKey())) {
                        dctrStatusCountDesc = dctrStatusCountItem;
                        break;
                    }
                }

                if (dctrStatusCountDesc == null) {
                    status2doctorItem.addDoctorNameCountList(0);
                } else {
                    status2doctorItem.addDoctorNameCountList(dctrStatusCountDesc.getStstsCount());
                    //status2doctorItem.setStatusName(dctrStatusCountDesc.getStatusName());
                }
            }
            doctorStatusBarBean.getDoctorNameList().add(doctor2StatusBean.getDoctorName());
        }
        return doctorStatusBarBean;
    }


    private List<AuditPrescriptionName2StatusBean> formatDoctor2StatusStatic(List<AuditPrescriptionStatusBean> doctorStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计
        List<AuditPrescriptionName2StatusBean> doctor2StatusList = new ArrayList<AuditPrescriptionName2StatusBean>();
        for (AuditPrescriptionStatusBean doctorStatusItem : doctorStatusList) {
            AuditPrescriptionName2StatusBean doctor2StatusDesc = null;
            for (AuditPrescriptionName2StatusBean doctor2StatusItem : doctor2StatusList) {
                //批次是否相同
                if (doctor2StatusItem.getDoctorId() != null
                        && doctor2StatusItem.getDoctorId().equals(doctorStatusItem.getDoctorId())) {
                    doctor2StatusDesc = doctor2StatusItem;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (doctor2StatusDesc == null) {
                doctor2StatusDesc = new AuditPrescriptionName2StatusBean();
                doctor2StatusDesc.setDoctorName(doctorStatusItem.getDoctorName());
                doctor2StatusDesc.setDoctorId(doctorStatusItem.getDoctorId());
                doctor2StatusList.add(doctor2StatusDesc);
            }

            AuditPrescriptionStatusCount dctrStatusCount = new AuditPrescriptionStatusCount();
            dctrStatusCount.setStatusKey(doctorStatusItem.getStatusKey());
            dctrStatusCount.setStstsCount(doctorStatusItem.getStstsCount());
            doctor2StatusDesc.addDctrStatusCountList(dctrStatusCount);

        }
        return doctor2StatusList;
    }

    @Override
    public List<AuditPrescriptionNamePieBean> queryDoctorNamePieList(AuditPrescriptionSearchBean doctorSearch) {
        return auditPrescriptionStatsDao.queryDoctorNamePieList(doctorSearch);
    }

    @Override
    public List<AuditPrescriptionStatusBean> queryDoctorNameStatusListByID(AuditPrescriptionSearchBean configFeeSearch) {

        return auditPrescriptionStatsDao.queryDoctorNameStatusListByID(configFeeSearch);
    }

    @Override
    public List<StaticDoctorNameBean> queryCheckerNameList() {

        return auditPrescriptionStatsDao.queryCheckerNameList();
    }


    private List<AuditPrescriptionResultType> setOrderCheckResult() {
        List<AuditPrescriptionResultType> orderCheckResultTypes = new ArrayList<AuditPrescriptionResultType>();

        AuditPrescriptionResultType checkResult = new AuditPrescriptionResultType();
        checkResult.setStatusKey(1);
        checkResult.setStatusName("通过");

        orderCheckResultTypes.add(checkResult);

        checkResult = new AuditPrescriptionResultType();
        checkResult.setStatusKey(2);
        checkResult.setStatusName("不通过");

        orderCheckResultTypes.add(checkResult);

        return orderCheckResultTypes;
    }
}
