package com.zc.pivas.patient.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.patient.bean.PatientBean;

import java.util.List;

/**
 * 病人
 *
 * @author Ray
 * @version 1.0
 */
public interface PatientService {

    /**
     * 按条件分页查询
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<PatientBean> getPatientList(PatientBean bean, JqueryStylePaging jquryStylePaging) throws Exception;

    /**
     * 添加病人
     *
     * @param patient
     */
    void addPatient(PatientBean patient);

    /**
     * 修改病人
     *
     * @param patient
     */
    void updatePatient(PatientBean patient);

    void synData(PatientBean patient);

    List<PatientBean> qryPatientList(PatientBean bean);

}
