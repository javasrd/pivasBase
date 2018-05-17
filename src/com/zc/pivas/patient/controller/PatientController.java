package com.zc.pivas.patient.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.common.util.EncodeUtil;
import com.zc.pivas.patient.bean.PatientBean;
import com.zc.pivas.patient.service.PatientService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 病人
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/pati")
public class PatientController extends SdBaseController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private final static String TASK_SUCCESS = "200";

    //0病人
    private final static int ACTION_PATIENT = 0;

    private PatientService patientService;

    //解析响应报文
    private AnalyResponMessage analyResponMessage;

    /**
     * 跳转到病人页面
     *
     * @return
     */
    @RequestMapping(value = "/initPati")
    @RequiresPermissions(value = {"PIVAS_MENU_241"})
    public String initPatient() {
        return "pivas/patientList";
    }

    /**
     * 按条件分页查询
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/patiList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_242"})
    public String getPatientList(PatientBean bean, JqueryStylePaging jquryStylePaging) throws Exception {

        String[] hospNoarray = bean.getInhospNos();
        if (hospNoarray != null) {
            for (int i = 0; i < hospNoarray.length; ++i) {
                hospNoarray[i] = DefineStringUtil.escapeAllLike(hospNoarray[i]);
            }
        }

        String[] patNamesarray = bean.getPatNames();
        if (patNamesarray != null) {
            for (int i = 0; i < patNamesarray.length; ++i) {
                patNamesarray[i] = DefineStringUtil.escapeAllLike(patNamesarray[i]);
            }
        }

        JqueryStylePagingResults<PatientBean> inpatientAreaBeanPagingResults = patientService.getPatientList(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 同步病人数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/synPati", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_244"})
    public String synPatient() throws Exception {
        try {
            // 调用数据同步接口
            JSONObject request = SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_PATIENT);

            log.info("Call gethisdata interface,request: " + request.toString());

            JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                log.info("Call gethisdata interface failure,respon: " + response);
                throw new Exception("Call gethisdata interface failure, this url:" + Propertiesconfiguration.getStringProperty("result.gethisdata"));
            }

            // 调用成功，解析响应，入表
            JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
            analyResponMessage.analyPatientRespon(paramJsonObject);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.PATIENT, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.patient.tip.syn"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.PATIENT, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.patient.tip.syn"), false);
            throw e;
        }
    }

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage) {
        this.analyResponMessage = analyResponMessage;
    }

}
