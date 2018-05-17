package com.zc.pivas.inpatientarea.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.common.util.EncodeUtil;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 病区
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ipta")
public class InpatientAreaController extends SdBaseController {
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 对接成功
     */
    private final static String TASK_SUCCESS = "200";

    /**
     * 3病区信息
     */
    private final static int ACTION_INPATIENT_AREA = 3;
    private InpatientAreaService inpatientAreaService;
    private AnalyResponMessage analyResponMessage;

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(value = "/initIpta")
    @RequiresPermissions(value = {"PIVAS_MENU_231"})
    public String initInpatientArea() {
        return "pivas/inpatientAreaList";
    }

    /**
     * 按条件分页查询病区列表
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/iptaList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_232"})
    public String getInpatientAreaList(InpatientAreaBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        String[] DeptCodeArray = bean.getDeptCodes();
        if (DeptCodeArray != null) {
            for (int i = 0; i < DeptCodeArray.length; ++i) {
                DeptCodeArray[i] = DefineStringUtil.escapeAllLike(DeptCodeArray[i]);
            }
        }

        String[] DeptNameArray = bean.getDeptNames();
        if (DeptNameArray != null) {
            for (int i = 0; i < DeptNameArray.length; ++i) {
                DeptNameArray[i] = DefineStringUtil.escapeAllLike(DeptNameArray[i]);
            }
        }

        JqueryStylePagingResults<InpatientAreaBean> inpatientAreaBeanPagingResults = inpatientAreaService.getInpatientAreaList(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 初始化
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/initIptaList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_305"})
    public String initInpatientAreaList(InpatientAreaBean bean) {

        inpatientAreaService.updateInpatientArea(bean);

        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }

    /**
     * 同步病区数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/synIpta", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_234"})
    public String synInpatientArea() throws Exception {
        try {
            JSONObject request = SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_INPATIENT_AREA);
            JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);
            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                throw new Exception("Call gethisdata interface failure, this url:" + Propertiesconfiguration.getStringProperty("result.gethisdata"));
            }

            JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
            analyResponMessage.analyInpatientAreaRespon(paramJsonObject);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.INPATIENT_AREA, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.inpatientarea.tip.syn"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.INPATIENT_AREA, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.inpatientarea.tip.syn"), false);
            throw e;
        }
    }

    /**
     * 修改病区数据
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updIptaList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_305"})
    public String updateInpatientAreaList(InpatientAreaBean bean) {
        try {
            inpatientAreaService.updateInpatientArea(bean);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.INPATIENT_AREA, AmiLogConstant.BRANCH_SYSTEM.CF, "修改病区", true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.INPATIENT_AREA, AmiLogConstant.BRANCH_SYSTEM.CF, "修改病区", false);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 新增病区数据
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/addIptaList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_305"})
    public String addInpatientAreaList(InpatientAreaBean bean) {
        inpatientAreaService.updateInpatientArea(bean);
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }

    /**
     * 修改病区状态
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/updIptaState", produces = "application/json")
    @ResponseBody
    public String changeInpatientAreaState(InpatientAreaBean bean) {
        try {
            if (StrUtil.isNotNull(bean.getDeptCode())) {
                String[] deptCodes = bean.getDeptCode().split(",");
                for (String deptCode : deptCodes) {
                    bean.setDeptCode(deptCode);
                    inpatientAreaService.updateInpatientArea(bean);
                }
            }
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.INPATIENT_AREA, AmiLogConstant.BRANCH_SYSTEM.CF, "修改病区状态成功：" + ("0".equals(bean.getEnabled()) ? "停止" : "启动"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.INPATIENT_AREA, AmiLogConstant.BRANCH_SYSTEM.CF, "修改病区状态失败" + ("0".equals(bean.getEnabled()) ? "停止" : "启动"), false);
            e.printStackTrace();
            throw e;
        }
    }

    @Autowired
    public void setInpatientAreaService(InpatientAreaService inpatientAreaService) {
        this.inpatientAreaService = inpatientAreaService;
    }

    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage) {
        this.analyResponMessage = analyResponMessage;
    }

}
