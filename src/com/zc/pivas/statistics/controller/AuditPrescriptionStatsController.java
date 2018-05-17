package com.zc.pivas.statistics.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionNamePieBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionNameStatusBarBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionSearchBean;
import com.zc.pivas.statistics.bean.auditPrescription.AuditPrescriptionStatusBean;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;
import com.zc.pivas.statistics.service.AuditPrescriptionStaticService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 审方工作量统计控制器
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics/auditPrescription")
public class AuditPrescriptionStatsController extends SdBaseController {
    @Resource
    private AuditPrescriptionStaticService auditPrescriptionStaticService;

    @Resource
    InpatientAreaService inpatientAreaService;

    @RequestMapping(value = "")
    @RequiresPermissions(value = {"PIVAS_MENU_711"})
    public String initPatient(Model model, HttpServletRequest request) {
        List<StaticDoctorNameBean> doctorNameList = auditPrescriptionStaticService.queryCheckerNameList();
        model.addAttribute("doctorNameList", doctorNameList);
        return "pivas/statistics/auditPrescriptionWLStatic";
    }

    @RequestMapping("/queryAuditPrescriptionBar")
    @ResponseBody
    public String queryStaticDoctorStatusBar(AuditPrescriptionSearchBean staticDoctorSearch) {
        AuditPrescriptionNameStatusBarBean doctorNameStatusBarList = auditPrescriptionStaticService.queryDoctorNameStatusBar(staticDoctorSearch);
        return new Gson().toJson(doctorNameStatusBarList);
    }

    @RequestMapping("/queryDoctorPieList")
    @ResponseBody
    public String queryDoctorPieList(AuditPrescriptionSearchBean staticCheckOrderSearch) {
        List<AuditPrescriptionNamePieBean> doctorNamePieList = auditPrescriptionStaticService.queryDoctorNamePieList(staticCheckOrderSearch);
        return new Gson().toJson(doctorNamePieList);

    }

    @RequestMapping("/queryDoctorStatusPieList")
    @ResponseBody
    public String queryDoctorStatusPieList(AuditPrescriptionSearchBean staticCheckOrderSearch) {
        List<AuditPrescriptionStatusBean> doctorStatusList = auditPrescriptionStaticService.queryDoctorNameStatusListByID(staticCheckOrderSearch);
        return new Gson().toJson(doctorStatusList);
    }
}
