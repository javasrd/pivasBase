package com.zc.pivas.patient.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.patient.bean.PatientBean;
import com.zc.pivas.patient.dao.PatientDAO;
import com.zc.pivas.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 病人
 *
 * @author Ray
 * @version 1.0
 */
@Service("patientService")
public class PatientServiceImpl implements PatientService {
    private final static String PIVAS_PATIENT_AGEUNIT = "pivas.patient.ageunit";

    private PatientDAO patientDAO;

    @Override
    public JqueryStylePagingResults<PatientBean> getPatientList(PatientBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"inhospNo", "patName", "sex", "wardName", "state", "case_ID", "birthDay", "ageDetail", "avdp", "bedNo"};
        JqueryStylePagingResults<PatientBean> pagingResults = new JqueryStylePagingResults<PatientBean>(columns);

        // 总数
        int total;
        List<PatientBean> patientList = null;

        // 转译like查询条件
        bean.setInhospNo(DefineStringUtil.escapeAllLike(bean.getInhospNo()));
        bean.setPatName(DefineStringUtil.escapeAllLike(bean.getPatName()));

        // 超级管理员
        patientList = patientDAO.getPatientList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(patientList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            patientList = patientDAO.getPatientList(bean, jquryStylePaging);
        }

        if (DefineCollectionUtil.isNotEmpty(patientList)) {
            // 出生日期格式化
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            String birthday;
            for (PatientBean patient : patientList) {
                // 拼接年龄
                if (null != patient.getAge() && !"".equals(patient.getAge())) {
                    patient.setAgeDetail(patient.getAge() + (DictFacade.getName(PIVAS_PATIENT_AGEUNIT, DefineStringUtil.parseNull(patient.getAgeUnit()), lang)));

                }

                if (null != patient.getAvdp() && !"".equals(patient.getAvdp())) {
                    patient.setAvdp(patient.getAvdp() + "kg");
                }

                if (null != patient.getBirth()) {
                    birthday = format.format(format.parse(patient.getBirth().toString()));
                    patient.setBirthDay(birthday);
                }
            }
        }

        total = patientDAO.getPatientTotal(bean);

        pagingResults.setDataRows(patientList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    @Autowired
    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    public void addPatient(PatientBean patient) {
        patientDAO.addPatient(patient);

    }

    @Override
    public void updatePatient(PatientBean patient) {
        patientDAO.updatePatient(patient);

    }

    @Override
    public void synData(PatientBean patient) {
        patientDAO.synData(patient);
    }

    @Override
    public List<PatientBean> qryPatientList(PatientBean bean) {
        return patientDAO.getPatientList(bean, new JqueryStylePaging());
    }
}
