package com.zc.base.sc.modules.generalRule.contorller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sc.modules.generalRule.entity.GeneralRule;
import com.zc.base.sc.modules.generalRule.service.GeneralRuleService;
import com.zc.base.sc.modules.medicalFrequency.entity.MedicalFrequency;
import com.zc.base.sc.modules.medicalFrequency.service.MedicalFrequencyService;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一般规则
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping("/sc/genlRule")
public class GeneralRuleController extends SdBaseController {
    @Resource
    private GeneralRuleService generalRuleService;
    @Resource
    private BatchService batchService;
    @Resource
    private MedicalFrequencyService medicalFrequencyService;
    @Resource
    private SdExceptionFactory exceptionFactory;


    /**
     * 跳转到一般规则页面
     *
     * @return
     */
    @RequestMapping("")
    public String toMain() {
        return "cm/rule/ruleMain";
    }


    /**
     * 按条件分页查询
     *
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping("/genlRuleList")
    @ResponseBody
    public String queryByPaging(JqueryStylePaging paging, GeneralRule condition) throws Exception {
        condition.setMedicalKeyword(DefineStringUtil.escapeAllLike(condition.getMedicalKeyword()));

        String[] medicalKeywordArray = condition.getMedicalKeywords();
        if (medicalKeywordArray != null) {
            for (int i = 0; i < medicalKeywordArray.length; i++) {
                medicalKeywordArray[i] = DefineStringUtil.escapeAllLike(medicalKeywordArray[i]);
            }
        }
        String[] columns = {"id"};
        JqueryStylePagingResults<GeneralRule> results = new JqueryStylePagingResults(columns);
        List<GeneralRule> list = this.generalRuleService.queryByPaging(paging, condition);
        results.setDataRows(list);
        results.setPage(paging.getPage());
        int total = this.generalRuleService.getTotalCount(condition);
        results.setTotal(Integer.valueOf(total));
        return new Gson().toJson(results);
    }


    /**
     * 查询频次
     *
     * @return
     */
    @RequestMapping("/queryBatFreq")
    @ResponseBody
    public String queryBatchFreq() {
        Map<String, Object> map = new HashMap();
        List<MedicalFrequency> freqList = this.medicalFrequencyService.queryByPaging(null, null);
        Batch condition = new Batch();
        condition.setEnabled(Integer.valueOf(1));
        List<Batch> batchList = this.batchService.queryByPaging(null, condition);
        map.put("success", Boolean.valueOf(true));
        map.put("freqList", freqList);
        map.put("batchList", batchList);
        return new Gson().toJson(map);
    }


    /**
     * 添加规则
     *
     * @param rule
     * @return
     */
    @RequestMapping("/addRule")
    @ResponseBody
    public String addRule(GeneralRule rule) {
        User user = getCurrentUser();
        try {
            if (this.generalRuleService.isRepeatFreqAndKeyword(rule)) {
                if (DefineStringUtil.isEmpty(rule.getMedicalKeyword())) {
                    return buildFailJsonMsg("rule.freqRepeat");
                }
                return buildFailJsonMsg("rule.freqAndKeywordRepeat");
            }
            this.generalRuleService.insert(rule);
            addOperLog("CF_22", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.rule.add", new String[]{user.getAccount(), rule.getMedicalKeyword()}), true);
            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog("CF_22", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.rule.add", new String[]{user.getAccount(), rule.getMedicalKeyword()}), false);
            throw e;
        }
    }


    /**
     * 获取规则
     *
     * @param id
     * @return
     */
    @RequestMapping("/getRule")
    @ResponseBody
    public String getRule(Long id) {
        GeneralRule rule = this.generalRuleService.selectByPrimaryKey(id);
        if (rule == null) {
            throw this.exceptionFactory.createSdException("00001", null, null);
        }
        return new Gson().toJson(rule);
    }


    /**
     * 更新规则
     *
     * @param rule
     * @return
     */
    @RequestMapping("/updRule")
    @ResponseBody
    public String updateRule(GeneralRule rule) {
        User user = getCurrentUser();
        try {
            if (this.generalRuleService.selectByPrimaryKey(rule.getId()) == null) {
                throw this.exceptionFactory.createSdException("00001", null, null);
            }
            if (this.generalRuleService.isRepeatFreqAndKeyword(rule)) {
                if (DefineStringUtil.isEmpty(rule.getMedicalKeyword())) {
                    return buildFailJsonMsg("rule.freqRepeat");
                }
                return buildFailJsonMsg("rule.freqAndKeywordRepeat");
            }
            this.generalRuleService.updateByPrimaryKey(rule);
            addOperLog("CF_22", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.rule.update", new String[]{user.getAccount(), rule.getMedicalKeyword()}), true);
            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog("CF_22", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.rule.update", new String[]{user.getAccount(), rule.getMedicalKeyword()}), false);
            throw e;
        }
    }


    /**
     * 删除规则
     *
     * @param id
     * @return
     */
    @RequestMapping("/delRule")
    @ResponseBody
    public String deleteRule(String id) {
        String[] gids = id.split(",");
        User user = getCurrentUser();
        String[] arrayOfString1;
        int j = (arrayOfString1 = gids).length;
        for (int i = 0; i < j; i++) {
            String gid = arrayOfString1[i];
            GeneralRule rule = this.generalRuleService.selectByPrimaryKey(Long.valueOf(Long.parseLong(gid)));
            try {
                if (rule == null) {
                    throw this.exceptionFactory.createSdException("00001", null, null);
                }
                this.generalRuleService.deleteByPrimaryKey(Long.valueOf(Long.parseLong(gid)));
                addOperLog("CF_22", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.rule.del", new String[]{user.getAccount() == null ? "" : user.getAccount(), rule == null ? "" : rule.getMedicalKeyword()}), true);
            } catch (Exception e) {
                addOperLog("CF_22", AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.rule.del", new String[]{user.getAccount() == null ? "" : user.getAccount(), rule == null ? "" : rule.getMedicalKeyword()}), false);
                throw e;
            }
        }
        return buildSuccessJsonMsg("common.op.success");
    }
}
