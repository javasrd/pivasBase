package com.zc.pivas.patient.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.patient.bean.PatientBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 病人
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("patientDAO")
public interface PatientDAO {

    /**
     * 按条件分页查询
     *
     * @param patient
     * @param jqueryStylePaging
     * @return
     */
    List<PatientBean> getPatientList(@Param("patient") PatientBean patient, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     *
     * @param patient
     * @return
     */
    int getPatientTotal(@Param("patient") PatientBean patient);

    void addPatient(PatientBean patient);

    void updatePatient(PatientBean patient);

    void synData(PatientBean patient);
}
