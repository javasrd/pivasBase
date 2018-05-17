package com.zc.pivas.statistics.service;

import com.zc.pivas.statistics.bean.medicalAdvice.*;

import java.util.List;
import java.util.Map;

/**
 * 医嘱统计Service
 *
 * @author jagger
 * @version 1.0
 */
public interface StaticDoctorService {

    StaticDoctorNameStatusBarBean queryDoctorNameStatusBar(StaticDoctorSearchBean staticDoctorSearch);

    List<StaticDoctorNamePieBean> queryDoctorNamePieList(StaticDoctorSearchBean staticDoctorSearch);

    List<StaticDoctorStatusBean> queryDoctorNameStatusListByID(StaticDoctorSearchBean staticDoctorSearch);

    StaticDoctorDeptStatusBarBean queryDeptStatusBar(StaticDoctorSearchBean staticDoctorSearch);

    List<StaticDoctorDeptPieBean> queryDeptPieList(StaticDoctorSearchBean staticDoctorSearch);

    /**
     * 根据病区名称查询医嘱信息
     *
     * @param staticDoctorSearch
     * @return
     */
    List<StaticDoctorStatusBean> queryDeptStatusListByName(StaticDoctorSearchBean staticDoctorSearch);

    /**
     * 从医嘱审核数据中提取医师数据
     *
     * @return
     */
    List<StaticDoctorNameBean> queryDoctorNameList();

    /**
     * 修改医嘱审方结果
     *
     * @param updateMap
     */
    void updateYzCheckResult(Map<String, Object> updateMap);
}
