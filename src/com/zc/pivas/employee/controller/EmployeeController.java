package com.zc.pivas.employee.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.common.util.EncodeUtil;
import com.zc.pivas.employee.bean.EmployeeInfoBean;
import com.zc.pivas.employee.service.EmployeeInfoService;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 员工
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/empy")
public class EmployeeController extends SdBaseController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    //7员工
    private static final int ACTION_EMPLOYEE = 7;

    private static final String TASK_SUCCESS = "200";

    @Resource
    private EmployeeInfoService employeeInfoService;

    //解析同步数据
    @Resource
    private AnalyResponMessage analyResponMessage;

    /**
     * 跳转到员工页面
     *
     * @return
     */
    @RequestMapping(value = "/initEmpy")
    public String initPatient() {
        return "pivas/employeeList";
    }

    /**
     * 按条件分页查询员工数据
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/empyList", produces = "application/json")
    @ResponseBody
    public String getPatientList(EmployeeInfoBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        JqueryStylePagingResults<EmployeeInfoBean> employeePagingResults = employeeInfoService.getEmployeeList(bean, jquryStylePaging);

        return new Gson().toJson(employeePagingResults);
    }

    /***
     * 同步员工数据
     *
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/synEmpy", produces = "application/json")
    @ResponseBody
    public String synEmployee() throws Exception {
        try {
            // 调用数据同步接口
            JSONObject request = SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_EMPLOYEE);
            log.info("Call gethisdata interface,request: " + request.toString());
            JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);
            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                log.info("Call gethisdata interface failure,respon: " + response);
                throw new Exception("Call gethisdata interface failure, this url:" + Propertiesconfiguration.getStringProperty("result.gethisdata"));
            }
            // 调用成功，解析响应，入表
            JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
            analyResponMessage.analyEmployeeInfoRespon(paramJsonObject);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.PATIENT, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.employee.tip.syn"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.PATIENT, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.patient.tip.syn"), false);
            throw e;
        }
    }
}
