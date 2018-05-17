package com.zc.pivas.statistics.service.impl;

import com.zc.pivas.statistics.repository.StaticDoctorDao;
import com.zc.pivas.statistics.service.StaticDoctorService;
import com.zc.pivas.statistics.bean.medicalAdvice.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 医嘱统计ervice实现
 *
 * @author jagger
 * @version 1.0
 */
@Service("staticDoctorService")
public class StaticDoctorServiceImpl implements StaticDoctorService {

    @Resource
    private StaticDoctorDao staticDoctorDao;

    @Override
    public StaticDoctorNameStatusBarBean queryDoctorNameStatusBar(StaticDoctorSearchBean configFeeSearch) {
        List<StaticDoctorStatusBean> doctorStatusList = staticDoctorDao.queryDoctorNameStatusList(configFeeSearch);
        if (doctorStatusList == null) {
            return null;
        }

        //格式化为医生-> 审方错误类型->数量 维度的统计
        List<StaticDoctorName2StatusBean> doctor2StatusList = formatDoctor2StatusStatic(doctorStatusList);
        //格式化 审方错误类型->医生-> 维度的统计
        // 医生名称列表
        StaticDoctorNameStatusBarBean doctorStatusBarBean = formatStatus2DoctorStatic(doctor2StatusList);
        return doctorStatusBarBean;
    }

    /**
     * 格式化  审方错误类型->医生-> 维度的统计
     *
     * @param doctor2StatusList
     * @return
     */
    private StaticDoctorNameStatusBarBean formatStatus2DoctorStatic(List<StaticDoctorName2StatusBean> doctor2StatusList) {
        StaticDoctorNameStatusBarBean doctorStatusBarBean = new StaticDoctorNameStatusBarBean();
        List<StaticDoctorStatus2NameBean> status2DoctorList = doctorStatusBarBean.getStatus2DoctorList();
        List<StaticDoctorType> doctorTypes = staticDoctorDao.getDoctorTypeList();
        for (StaticDoctorType statusKeyItem : doctorTypes) {
            StaticDoctorStatus2NameBean status2DoctorBeanNew = new StaticDoctorStatus2NameBean();
            status2DoctorBeanNew.setStatusKey(statusKeyItem.getStatusKey());
            status2DoctorBeanNew.setStatusName(statusKeyItem.getStatusName());

            status2DoctorList.add(status2DoctorBeanNew);
        }
        for (StaticDoctorName2StatusBean doctor2StatusBean : doctor2StatusList) {
            List<StaticDoctorStatusCount> dctrStatusCountList = doctor2StatusBean.getDctrStatusCountList();
            for (StaticDoctorStatus2NameBean status2doctorItem : status2DoctorList) {
                StaticDoctorStatusCount dctrStatusCountDesc = null;
                for (StaticDoctorStatusCount dctrStatusCountItem : dctrStatusCountList) {
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

    /**
     * 格式化为医生-> 审方错误类型->数量 维度的统计
     *
     * @param doctorStatusList
     * @return
     */
    private List<StaticDoctorName2StatusBean> formatDoctor2StatusStatic(List<StaticDoctorStatusBean> doctorStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计
        List<StaticDoctorName2StatusBean> doctor2StatusList = new ArrayList<StaticDoctorName2StatusBean>();
        for (StaticDoctorStatusBean doctorStatusItem : doctorStatusList) {
            StaticDoctorName2StatusBean doctor2StatusDesc = null;
            for (StaticDoctorName2StatusBean doctor2StatusItem : doctor2StatusList) {
                //批次是否相同
                if (doctor2StatusItem.getDoctorId() != null
                        && doctor2StatusItem.getDoctorId().equals(doctorStatusItem.getDoctorId())) {
                    doctor2StatusDesc = doctor2StatusItem;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (doctor2StatusDesc == null) {
                doctor2StatusDesc = new StaticDoctorName2StatusBean();
                doctor2StatusDesc.setDoctorName(doctorStatusItem.getDoctorName());
                doctor2StatusDesc.setDoctorId(doctorStatusItem.getDoctorId());
                doctor2StatusList.add(doctor2StatusDesc);
            }

            StaticDoctorStatusCount dctrStatusCount = new StaticDoctorStatusCount();
            dctrStatusCount.setStatusKey(doctorStatusItem.getStatusKey());
            dctrStatusCount.setStstsCount(doctorStatusItem.getStstsCount());
            doctor2StatusDesc.addDctrStatusCountList(dctrStatusCount);

        }
        return doctor2StatusList;
    }

    @Override
    public List<StaticDoctorNamePieBean> queryDoctorNamePieList(StaticDoctorSearchBean doctorSearch)
    //public List<ConfigFeeSearchBean> queryBatchPieList(ConfigFeeSearchBean configFeeSearch)
    {
        return staticDoctorDao.queryDoctorNamePieList(doctorSearch);
    }

    @Override
    public List<StaticDoctorStatusBean> queryDoctorNameStatusListByID(StaticDoctorSearchBean configFeeSearch) {

        return staticDoctorDao.queryDoctorNameStatusListByID(configFeeSearch);
    }

    @Override
    public StaticDoctorDeptStatusBarBean queryDeptStatusBar(StaticDoctorSearchBean doctorSearch) {
        List<StaticDoctorStatusBean> deptStatusList = staticDoctorDao.queryDeptStatusList(doctorSearch);
        if (deptStatusList == null) {
            return null;
        }

        //格式化为病区->审方错误类型->医嘱数量 维度的统计
        List<StaticDoctorDept2StatusBean> dept2StatusList = formatDept2StatusStatic(deptStatusList);
        //格式化 审方错误类型->病区-> 维度的统计
        // 医生名称列表
        StaticDoctorDeptStatusBarBean doctorStatusBarBean = formatStatus2DeptStatic(dept2StatusList);
        return doctorStatusBarBean;
    }

    /**
     * 格式化 审方错误类型->病区-> 维度的统计
     *
     * @param dept2StatusList
     * @return
     */
    private StaticDoctorDeptStatusBarBean formatStatus2DeptStatic(List<StaticDoctorDept2StatusBean> dept2StatusList) {
        StaticDoctorDeptStatusBarBean deptStatusBarBean = new StaticDoctorDeptStatusBarBean();
        List<StaticDoctorStatus2DeptBean> status2DeptList = deptStatusBarBean.getStatus2DeptList();

        List<StaticDoctorType> doctorTypes = staticDoctorDao.getDoctorTypeList();
        for (StaticDoctorType statusKeyItem : doctorTypes) {
            StaticDoctorStatus2DeptBean status2DoctorBeanNew = new StaticDoctorStatus2DeptBean();
            status2DoctorBeanNew.setStatusKey(statusKeyItem.getStatusKey());
            status2DoctorBeanNew.setStatusName(statusKeyItem.getStatusName());

            status2DeptList.add(status2DoctorBeanNew);
        }

        for (StaticDoctorDept2StatusBean dept2StatusBean : dept2StatusList) {
            if (dept2StatusBean.getDeptName() == null) {
                continue;
            }

            List<StaticDoctorStatusCount> dctrStatusCountList = dept2StatusBean.getDctrStatusCountList();
            for (StaticDoctorStatus2DeptBean status2DeptItem : status2DeptList) {
                StaticDoctorStatusCount dctrStatusCountDesc = null;
                for (StaticDoctorStatusCount cfStatusCountItem : dctrStatusCountList) {
                    if (status2DeptItem.getStatusKey() != null
                            && status2DeptItem.getStatusKey().equals(cfStatusCountItem.getStatusKey())) {
                        dctrStatusCountDesc = cfStatusCountItem;
                    }
                }
                if (dctrStatusCountDesc == null) {
                    status2DeptItem.addDctrDeptCountList(0);
                } else {
                    status2DeptItem.addDctrDeptCountList(dctrStatusCountDesc.getStstsCount());
                }

            }
            deptStatusBarBean.getDeptNameList().add(dept2StatusBean.getDeptName());

        }
        return deptStatusBarBean;
    }

    /**
     * 格式化为病区->审方错误类型->医嘱数量 维度的统计
     *
     * @param deptStatusList
     * @return
     */
    private List<StaticDoctorDept2StatusBean> formatDept2StatusStatic(List<StaticDoctorStatusBean> deptStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计 
        List<StaticDoctorDept2StatusBean> dept2StatusList = new ArrayList<StaticDoctorDept2StatusBean>();
        for (StaticDoctorStatusBean deptStatusItem : deptStatusList) {
            StaticDoctorDept2StatusBean dept2StatusDesc = null;
            for (StaticDoctorDept2StatusBean dept2StatusItem : dept2StatusList) {
                //病区是否相同
                if (deptStatusItem.getDeptName() != null
                        && deptStatusItem.getDeptName().equals(dept2StatusItem.getDeptName())) {
                    dept2StatusDesc = dept2StatusItem;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (dept2StatusDesc == null) {
                dept2StatusDesc = new StaticDoctorDept2StatusBean();
                dept2StatusDesc.setDeptName(deptStatusItem.getDeptName());
                dept2StatusList.add(dept2StatusDesc);
            }

            StaticDoctorStatusCount dctrStatusCount = new StaticDoctorStatusCount();
            dctrStatusCount.setStatusKey(deptStatusItem.getStatusKey());
            dctrStatusCount.setStstsCount(deptStatusItem.getStstsCount());
            dept2StatusDesc.addDctrStatusCountList(dctrStatusCount);
        }
        return dept2StatusList;
    }

    /**
     * 修改医嘱审方结果
     *
     * @param updateMap
     */
    public void updateYzCheckResult(Map<String, Object> updateMap) {
        staticDoctorDao.updateYzCheckResult(updateMap);
    }

    @Override
    public List<StaticDoctorDeptPieBean> queryDeptPieList(StaticDoctorSearchBean staticDoctorSearch) {
        return staticDoctorDao.queryDeptPieList(staticDoctorSearch);
    }

    /**
     * 根据病区名称查询医嘱信息
     *
     * @param staticDoctorSearch
     * @return
     */
    @Override
    public List<StaticDoctorStatusBean> queryDeptStatusListByName(StaticDoctorSearchBean staticDoctorSearch) {
        return staticDoctorDao.queryDeptStatusListByName(staticDoctorSearch);
    }

    /**
     * 从医嘱审核数据中提取医师数据
     *
     * @return
     */
    @Override
    public List<StaticDoctorNameBean> queryDoctorNameList() {
        return staticDoctorDao.queryDoctorNameList();
    }

}
