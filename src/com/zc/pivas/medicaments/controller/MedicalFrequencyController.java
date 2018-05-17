package com.zc.pivas.medicaments.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.medicalFrequency.entity.MedicalFrequency;
import com.zc.base.sc.modules.medicalFrequency.service.MedicalFrequencyService;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.common.util.EncodeUtil;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用药频次
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping("/cm/mdcFreq")
public class MedicalFrequencyController extends SdBaseController {

    private final static String TASK_SUCCESS = "200";

    //4医嘱频次
    private final static int ACTION_ORDER_FREQUENCY = 4;

    @Resource
    private MedicalFrequencyService frequencyService;

    @Resource
    private SdExceptionFactory exceptionFactory;

    private AnalyResponMessage analyResponMessage;

    /**
     * 跳转到用药频次页面
     * @return
     */
    @RequestMapping("")
    public String toMain() {
        return "cm/medical_freq/medicalFreqMain";
    }

    /**
     * 按条件分页查询
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping("/mdcFreqList")
    @ResponseBody
    public String queryByPaging(JqueryStylePaging paging, MedicalFrequency condition) throws Exception {
        condition.setCode(DefineStringUtil.escapeAllLike(condition.getCode()));
        condition.setName(DefineStringUtil.escapeAllLike(condition.getName()));

        String[] codeArray = condition.getCodes();
        if (codeArray != null) {
            for (int i = 0; i < codeArray.length; ++i) {
                codeArray[i] = DefineStringUtil.escapeAllLike(codeArray[i]);
            }
        }

        String[] nameArray = condition.getNames();
        if (nameArray != null) {
            for (int i = 0; i < nameArray.length; ++i) {
                nameArray[i] = DefineStringUtil.escapeAllLike(nameArray[i]);
            }
        }

        String[] columns = new String[]{"code", "name"};
        JqueryStylePagingResults<MedicalFrequency> results = new JqueryStylePagingResults<MedicalFrequency>(columns);

        List<MedicalFrequency> datas = frequencyService.queryByPaging(paging, condition);
        int total = frequencyService.getTotalCount(condition);
        results.setDataRows(datas);
        results.setPage(paging.getPage());
        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /**
     * 新增用药频次
     * @param frequency
     * @return
     */
    @RequestMapping("/addMdcFreq")
    @ResponseBody
    public String addMedicalFrequency(MedicalFrequency frequency) {
        frequencyService.insert(frequency);
        return buildSuccessJsonMsg("common.op.success");
    }

    /**
     * 同步医嘱频次数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/synMdcFreq", produces = "application/json")
    @ResponseBody
    public String synDrugWay() throws Exception {
        try {
            // 调用数据同步接口
            JSONObject request = SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_ORDER_FREQUENCY);

            JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                throw new Exception("Call gethisdata interface failure, this url:" + Propertiesconfiguration.getStringProperty("result.gethisdata"));
            }

            // 解析响应消息
            JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
            analyResponMessage.analyMedicalFrequencyRespon(paramJsonObject);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_FREQUENCY, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medicalfrequency.tip.syn"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_FREQUENCY, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medicalfrequency.tip.syn"), false);
            throw e;
        }
    }

    /**
     * 根据ID查询用药频次
     * @param id
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public String get(Long id) {
        MedicalFrequency frequency = frequencyService.selectByPrimaryKey(id);
        if (frequency == null) {
            exceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        return new Gson().toJson(frequency);
    }

    /**
     * 修改用药频次
     * @param frequency
     * @return
     * @throws Exception
     */
    @RequestMapping("/updMdcFreq")
    @ResponseBody
    public String updateMedicalFrequency(MedicalFrequency frequency) throws Exception {
        User user = getCurrentUser();
        try {
            if (frequencyService.selectByPrimaryKey(frequency.getId()) == null) {
                exceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
            }
            frequencyService.updateByPrimaryKeySelective(frequency);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_FREQUENCY, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medical.freq.update", new String[]{user.getAccount(), frequency.getName()}), true);

            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_FREQUENCY, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medical.freq.update", new String[]{user.getAccount(), frequency.getName()}), false);
            throw e;
        }
    }

    /**
     * 删除用药频次
     * @param id
     * @return
     */
    @RequestMapping("/delMdcFreq")
    @ResponseBody
    public String deleteMedicalFrequency(Long id) {
        frequencyService.deleteByPrimaryKey(id);
        return buildSuccessJsonMsg("common.op.success");
    }

    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage) {
        this.analyResponMessage = analyResponMessage;
    }

}
