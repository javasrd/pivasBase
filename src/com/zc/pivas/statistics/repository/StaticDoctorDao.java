package com.zc.pivas.statistics.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.statistics.bean.medicalAdvice.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询医嘱信息
 *
 * @author jagger
 * @version 1.0
 */
@MyBatisRepository("staticDoctorDao")
public interface StaticDoctorDao {

    /**
     * 按审核时间查询医嘱审方结果
     *
     * @param staticDoctor
     * @return
     */
    List<StaticDoctorStatusBean> queryDoctorNameStatusList(@Param("staticDoctor") StaticDoctorSearchBean staticDoctor);

    /**
     * 按审核时间和医师查询对应医嘱统计信息
     *
     * @param staticDoctor
     * @return
     */
    List<StaticDoctorNamePieBean> queryDoctorNamePieList(@Param("staticDoctor") StaticDoctorSearchBean staticDoctor);

    /**
     * 按医师编号查询医嘱审方结果
     *
     * @param staticDoctor
     * @return
     */
    List<StaticDoctorStatusBean> queryDoctorNameStatusListByID(@Param("staticDoctor") StaticDoctorSearchBean staticDoctor);

    /**
     * 按病区科室查询医嘱审方结果
     *
     * @param staticDoctor
     * @return
     */
    List<StaticDoctorStatusBean> queryDeptStatusList(@Param("staticDoctor") StaticDoctorSearchBean staticDoctor);

    /**
     * 按病区和审核时间查询医嘱审方结果
     *
     * @param staticDoctor
     * @return
     */
    List<StaticDoctorDeptPieBean> queryDeptPieList(@Param("staticDoctor") StaticDoctorSearchBean staticDoctor);

    /**
     * 根据病区名称查询医嘱信息
     *
     * @param staticDoctor
     * @return
     */
    List<StaticDoctorStatusBean> queryDeptStatusListByName(@Param("staticDoctor") StaticDoctorSearchBean staticDoctor);

    /**
     * 获取审方错误类型
     *
     * @return
     */
    List<StaticDoctorType> getDoctorTypeList();

    /**
     * 查询参与过审核的医生列表
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
