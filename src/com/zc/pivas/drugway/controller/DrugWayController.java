package com.zc.pivas.drugway.controller;

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
import com.zc.pivas.drugway.bean.DrugWayBean;
import com.zc.pivas.drugway.service.DrugWayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用药途径
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/dw")
public class DrugWayController extends SdBaseController {

    private final static String TASK_SUCCESS = "200";

    //5用药途径
    private final static int ACTION_DRUGWAY = 5;

    private DrugWayService drugWayService;

    private AnalyResponMessage analyResponMessage;

    /**
     * 跳转到用药途径页面
     * @return
     */
    @RequestMapping(value = "/initDw")
    @RequiresPermissions(value = {"PIVAS_MENU_251"})
    public String initDrugWay() {
        return "pivas/drugWayList";
    }

    /**
     * 按条件分页查询
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dwList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_252"})
    public String getDrugWayList(DrugWayBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        String codeArray[] = bean.getCodes();
        if (codeArray != null) {
            for (int i = 0; i < codeArray.length; ++i) {
                codeArray[i] = DefineStringUtil.escapeAllLike(codeArray[i]);
            }
        }

        String nameArray[] = bean.getNames();
        if (nameArray != null) {
            for (int i = 0; i < nameArray.length; ++i) {
                nameArray[i] = DefineStringUtil.escapeAllLike(nameArray[i]);
            }
        }

        JqueryStylePagingResults<DrugWayBean> drugWayBeanPagingResults = drugWayService.getDrugWay(bean, jquryStylePaging);

        return new Gson().toJson(drugWayBeanPagingResults);
    }

    /**
     * 同步用药途径数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/synDw", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_254"})
    public String synDrugWay() throws Exception {
        try {
            // 调用数据同步接口
            JSONObject request = SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_DRUGWAY);

            JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                throw new Exception("Call gethisdata interface failure, this url:" + Propertiesconfiguration.getStringProperty("result.gethisdata"));
            }

            // 解析响应消息
            JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
            analyResponMessage.analyDrugWayRespon(paramJsonObject);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_WAY, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.drugway.syn"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_WAY, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.drugway.tip.syn"), false);
            throw e;
        }
    }

    @Autowired
    public void setDrugWayService(DrugWayService drugWayService) {
        this.drugWayService = drugWayService;
    }

    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage) {
        this.analyResponMessage = analyResponMessage;
    }
}
