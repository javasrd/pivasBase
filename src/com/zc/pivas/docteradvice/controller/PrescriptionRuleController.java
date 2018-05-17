package com.zc.pivas.docteradvice.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.CommonUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.NumberUtil;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.docteradvice.bean.ForceRulesWithArea;
import com.zc.pivas.docteradvice.bean.PriorityRulesWithArea;
import com.zc.pivas.docteradvice.bean.VolumeRulesWithBatch;
import com.zc.pivas.docteradvice.entity.OtherRule;
import com.zc.pivas.docteradvice.entity.PriorityRules;
import com.zc.pivas.docteradvice.entity.VolumeRules;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.PrescriptionRuleService;
import com.zc.pivas.common.entity.ResultData;
import com.zc.pivas.common.util.DataFormat;
import com.zc.pivas.common.util.SysConstant;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一般规则控制类
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ydRule")
public class PrescriptionRuleController extends SdBaseController {


    @Resource
    private PrescriptionRuleService ydRuleService;

    @Resource
    private PrescriptionMainService ydMainService;

    @Resource
    private InpatientAreaService inpAreaService;

    @Resource
    private BatchService batchService;

    /**
     * 药物优先级-首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/mdcmRulePage")
    @RequiresPermissions(value = {"PIVAS_MENU_642"})
    public String medicRuleMain(Model model) {
        Integer prType = SysConstant.prType.medicSingRule;

        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled(SysConstant.areaEnabled.enable);
        List<InpatientAreaBean> inpAreaList = inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        if (inpAreaList != null && inpAreaList.size() > 0) {
            InpatientAreaBean areaTemp = inpAreaList.get(0);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("prType", prType);
            param.put("deptcode", areaTemp.getDeptCode());
            param.put("deptName", areaTemp.getDeptName());
            List<PriorityRulesWithArea> prioRulesList = ydRuleService.qryPrioRulesList(param);
            model.addAttribute("prioRulesList", prioRulesList);
            model.addAttribute("areaCodeNow", areaTemp.getDeptCode());
            model.addAttribute("areaNameNow", areaTemp.getDeptName());
        }
        model.addAttribute("prType", prType);
        return "pivas/ydRule/medicRuleMain";
    }

    /**
     * 容积规则首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/volRulePage")
    @RequiresPermissions(value = {"PIVAS_MENU_652"})
    public String volumeRuleMain(Model model) {
        Integer vrType = SysConstant.vrType.volumeRule;

        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled(SysConstant.areaEnabled.enable);
        List<InpatientAreaBean> inpAreaList = inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        List<Batch> batchList = batchService.queryBatchByVolume();
        model.addAttribute("batchList", batchList);

        if (inpAreaList != null && inpAreaList.size() > 0) {
            InpatientAreaBean areaTemp = inpAreaList.get(0);
            model.addAttribute("areaCodeNow", areaTemp.getDeptCode());
            model.addAttribute("areaNameNow", areaTemp.getDeptName());
        }
        model.addAttribute("vrType", vrType);
        return "pivas/ydRule/volumeRuleMain";
    }

    /**
     * 容积规则首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/forceRulePage")
    @RequiresPermissions(value = {"PIVAS_MENU_662"})
    public String forceRuleMain(Model model) {
        Integer prType = SysConstant.prType.forceSingRule;

        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled(SysConstant.areaEnabled.enable);
        List<InpatientAreaBean> inpAreaList = inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        List<Batch> batchList = batchService.queryBatchByForce();
        model.addAttribute("batchList", batchList);

        if (inpAreaList != null && inpAreaList.size() > 0) {
            InpatientAreaBean areaTemp = inpAreaList.get(0);
            model.addAttribute("areaCodeNow", areaTemp.getDeptCode());
            model.addAttribute("areaNameNow", areaTemp.getDeptName());
        }
        model.addAttribute("prType", prType);
        return "pivas/ydRule/forceRuleMain";
    }

    /**
     * 药物优先级-首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/otherRulePage")
    @RequiresPermissions(value = {"PIVAS_MENU_672"})
    public String otherRuleMain(Model model) {
        Integer orType = SysConstant.orType.otherRule;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orType", orType);
        List<OtherRule> list = ydRuleService.qryOtherRulesList(map);
        model.addAttribute("orType", orType);
        model.addAttribute("list", list);
        return "pivas/ydRule/otherRuleMain";
    }

    /**
     * 根据病区，获取病区下的药物优先级
     *
     * @param model
     * @param areaCode
     * @param prType
     * @return
     */
    @RequestMapping(value = "/getPLRuleByArea")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_MENU_502"})InpatientAreaService
    public List<PriorityRulesWithArea> qryRuleListByArea(Model model, @RequestParam String areaCode, @RequestParam Integer prType) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("prType", prType);
        param.put("deptcode", areaCode);
        List<PriorityRulesWithArea> ydRuleList = ydRuleService.qryPrioRulesList(param);
        return ydRuleList;
    }

    /**
     * 根据病区，获取病区下的容积规则
     *
     * @param model
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getVolRuleByArea")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_MENU_502"})InpatientAreaService
    public String qryVolRuleList(Model model, @RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {
        JqueryStylePagingResults<VolumeRulesWithBatch> pageResult = ydRuleService.qryVolRulePageByMap(param, jquryStylePaging);
        return new Gson().toJson(pageResult);
    }

    /**
     * 根据病区，获取病区下的容积规则
     *
     * @param model
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getForceRuleByArea")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_MENU_502"})InpatientAreaService
    public String qryForceRuleList(Model model, @RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {
        param.put("orderby", "desc");
        JqueryStylePagingResults<ForceRulesWithArea> pageResult = ydRuleService.qryForceRulePageByMap(param, jquryStylePaging);
        return new Gson().toJson(pageResult);
    }

    /**
     * 添加药物优先级，强制批次
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addPLRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_644", "PIVAS_BTN_664"})
    public ResultData addPrioRules(@RequestParam Map<String, Object> param)  {
        int row = -1;
        User user = getCurrentUser();
        String medIdS = StrUtil.getObjStr(param.get("medIdN"), "");
        Integer prType = NumberUtil.getObjInt(param.get("prType"));
        String deptcode = StrUtil.getObjStr(param.get("deptcode"));
        String[] medIdList = medIdS.split(",");
        String medicId = null;
        PriorityRules prioRules = null;
        List<PriorityRules> prioRulesList = new ArrayList<PriorityRules>();
        String pridS = "";
        if (medIdList != null && medIdList.length > 0 && prType != null && deptcode != null) {
            Integer maxVal = ydRuleService.qryPrioRulesVal(prType, deptcode);
            for (String medIdRow : medIdList) {
                medicId = StrUtil.getObjStr(medIdRow);
                if (medicId != null) {
                    prioRules = new PriorityRules(param);
                    prioRules.setPrUserId(user.getUserId());
                    prioRules.setMedicId(medicId);
                    prioRules.setPrOrder(++maxVal);
                    row = ydRuleService.addPrioRules(prioRules);
                    if (row > -1) {
                        prioRulesList.add(prioRules);
                        if (pridS.equals("")) {
                            pridS = pridS + prioRules.getPrId();
                        } else {
                            pridS = pridS + "," + prioRules.getPrId();
                        }
                    }
                }
            }
            addOperLog(((prType == SysConstant.prType.medicSingRule) ? AmiLogConstant.MODULE_BRANCH.CF.ROW_MEDIC_RULE : AmiLogConstant.MODULE_BRANCH.CF.ROW_FORCE_RULE), AmiLogConstant.BRANCH_SYSTEM.CF, param.get("deptname") + " 添加" + ((prType == SysConstant.prType.medicSingRule) ? "药物优先级" : "强制规则") + "[" + pridS + "]", row > -1 ? true : false);
        }
        return DataFormat.formatAdd(prioRulesList.size(), prioRulesList);
    }

    /**
     * 添加容积规则
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addVolRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_654"})
    public ResultData addVolRules(@RequestParam Map<String, Object> param) {
        int row = 0;
        User user = getCurrentUser();
        VolumeRules voluRules = new VolumeRules(param);
        if (voluRules.getBatchId() != null && voluRules.getDeptcode() != null && voluRules.getVrType() != null) {
            voluRules.setVrUserId(user.getUserId());
            row = ydRuleService.addVolumeRules(voluRules);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_VOLUME_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, param.get("deptname") + " 添加容积规则[" + voluRules.getVrId() + "]", row > -1 ? true : false);
        }
        return DataFormat.formatAdd(row);
    }

    /**
     * 修改药物优先级
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updPLRules", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_645", "PIVAS_BTN_646"})
    public ResultData updTwoPrioRules(@RequestParam Map<String, Object> param) throws Exception {
        int row = -1;
        if (param.get("prId1") != null && param.get("prId2") != null && param.get("prOrder1") != null && param.get("prOrder2") != null && param.get("type") != null) {
            User user = getCurrentUser();

            Map<String, Object> updMap1 = new HashMap<String, Object>();
            Map<String, Object> updMap2 = new HashMap<String, Object>();
            updMap1.put("prId", NumberUtil.getObjLong(param.get("prId1")));
            updMap1.put("prOrder", NumberUtil.getObjInt(param.get("prOrder1")));
            updMap1.put("prUserId", user.getUserId());

            updMap2.put("prId", NumberUtil.getObjLong(param.get("prId2")));
            updMap2.put("prOrder", NumberUtil.getObjInt(param.get("prOrder2")));
            updMap2.put("prUserId", user.getUserId());

            row = ydRuleService.updTwoPrioRules(updMap1, updMap2);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_MEDIC_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, param.get("deptname") + " " + ("up".equals(param.get("type")) ? "上移" : "下移") + "药物优先级[" + param.get("prId1") + "]", row > -1 ? true : false);
        }
        return DataFormat.formatUpd(row);
    }

    /**
     * 修改强制规则
     * @param param
     * @return
     */
    @RequestMapping(value = "/updForceRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_665"})
    public ResultData updForceRules(@RequestParam Map<String, Object> param)  {
        int row = -1;
        ForceRulesWithArea.format(param);
        if (param.get("prId") != null) {
            User user = getCurrentUser();
            param.put("prUserId", user.getUserId());
            row = ydRuleService.updForceRules(param);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_FORCE_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, param.get("deptname") + " 修改强制规则[" + param.get("prId") + "]", row > -1 ? true : false);
        }
        return DataFormat.formatUpd(row);
    }

    /**
     * 修改容积规则
     * @param param
     * @return
     */
    @RequestMapping(value = "/updVolRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_655"})
    public ResultData updVolRules(@RequestParam Map<String, Object> param)  {
        int row = 0;
        VolumeRules.format(param);
        if (param.get("vrId") != null && param.get("batchId") != null) {
            User user = getCurrentUser();
            param.put("vrUserId", user.getUserId());
            row = ydRuleService.updVolumeRules(param);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_VOLUME_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, param.get("deptname") + " 修改容积规则[" + param.get("vrId") + "]", row > -1 ? true : false);
        }
        return DataFormat.formatUpd(row);
    }

    /**
     * 修改其他规则
     * @param param
     * @return
     */
    @RequestMapping(value = "updOtherRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_673"})
    public ResultData updOtherRules(@RequestParam Map<String, Object> param)  {
        int row = -1;
        VolumeRules.format(param);
        if (param.get("orId") != null && param.get("enabled") != null) {
            row = ydRuleService.updOtherRules(param);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_OTHER_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, "修改其他规则[" + param.get("orId") + "]", row > -1 ? true : false);
        }
        return DataFormat.formatUpd(row);
    }

    /**
     * 删除药物优先级
     * @param param
     * @return
     */
    @RequestMapping(value = "/delPLRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_647", "PIVAS_BTN_666"})
    public ResultData delPrioRules(@RequestParam Map<String, Object> param) {
        Integer prType = NumberUtil.getObjInt(param.get("prType"));
        if (param.get("prId") != null) {
            int row = ydRuleService.delPrioRules(param.get("prId"));
            addOperLog(((prType == SysConstant.prType.medicSingRule) ? AmiLogConstant.MODULE_BRANCH.CF.ROW_MEDIC_RULE : AmiLogConstant.MODULE_BRANCH.CF.ROW_FORCE_RULE), AmiLogConstant.BRANCH_SYSTEM.CF, "删除" + ((prType == SysConstant.prType.medicSingRule) ? "药物优先级" : "强制规则") + "[id=" + param.get("prId") + "]", row > -1 ? true : false);
            return DataFormat.formatDel(row);
        } else {
            int row = 0;
            List<Long> prIdN = CommonUtil.getLongNbyStr(param.get("prIdN"));
            if (prIdN.size() > 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("prIdN", prIdN);
                row = ydRuleService.delPrioRules(map);
                addOperLog(((prType == SysConstant.prType.medicSingRule) ? AmiLogConstant.MODULE_BRANCH.CF.ROW_MEDIC_RULE : AmiLogConstant.MODULE_BRANCH.CF.ROW_FORCE_RULE), AmiLogConstant.BRANCH_SYSTEM.CF, "删除" + ((prType == SysConstant.prType.medicSingRule) ? "药物优先级" : "强制规则") + "[id=" + param.get("prIdN") + "]", row > -1 ? true : false);
            }
            return DataFormat.formatDel(row);
        }
    }

    /**
     * 删除容积规则
     * @param param
     * @return
     */
    @RequestMapping(value = "/delVolRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_656"})
    public ResultData delVolRules(@RequestParam Map<String, Object> param)  {
        int row = 0;
        List<Long> vrIdN = CommonUtil.getLongNbyStr(param.get("vrIdN"));
        if (vrIdN.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vrIdN", vrIdN);
            row = ydRuleService.delVolumeRules(map);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_FORCE_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, param.get("deptname") + " 删除容积规则[id=" + param.get("vrIdN") + "]", row > -1 ? true : false);
        }
        return DataFormat.formatDel(row);
    }

    /**
     * 同步当前病区药物优先级到其他病区
     * @param param
     * @return
     */
    @RequestMapping(value = "/synMdcmRuleToOtherArea", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_643", "PIVAS_BTN_663"})
    public ResultData synMedRuleToOther(@RequestParam Map<String, Object> param)  {
        int row = -1;
        String areaCodeNow = StrUtil.getObjStr(param.get("areaCodeNow"));
        List<String> areaCodeN = CommonUtil.getStrNbyStr(param.get("areaCodeN"));
        List<String> medicCodeList = CommonUtil.getStrNbyStr(param.get("medicCodeStr"));

        Integer prType = NumberUtil.getObjInt(param.get("prType"));
        if (areaCodeNow != null && areaCodeN.size() > 0 && prType != null) {
            User user = getCurrentUser();
            Map<String, Object> addMap = new HashMap<String, Object>();
            addMap.put("prType", prType);
            addMap.put("prUserId", user.getUserId());
            addMap.put("deptcodeNow", areaCodeNow);
            addMap.put("medicCodeList", medicCodeList);

            for (String areaCode : areaCodeN) {
                addMap.put("deptcode", areaCode);
                row = ydRuleService.addAndDelPrioRulesMany(addMap);
            }
        }
        addOperLog((prType == SysConstant.prType.medicSingRule) ? AmiLogConstant.MODULE_BRANCH.CF.ROW_MEDIC_RULE : AmiLogConstant.MODULE_BRANCH.CF.ROW_FORCE_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, ((prType == SysConstant.prType.medicSingRule) ? "药物优先级" : "强制规则") + "从 " + param.get("areaNameNow") + " 同步到 " + param.get("areaNameN"), row > -1 ? true : false);
        return DataFormat.formatAdd(row > -1 ? 1 : 0);
    }

    /**
     * 同步当前病区容积规则到其他病区
     * @param param
     * @return
     */
    @RequestMapping(value = "/synVolRuleToOtherArea", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_653"})
    public ResultData synVolRuleToOther(@RequestParam Map<String, Object> param)  {
        int row = -1;
        String areaCodeNow = StrUtil.getObjStr(param.get("areaCodeNow"));
        List<String> areaCodeN = CommonUtil.getStrNbyStr(param.get("areaCodeN"));
        List<String> vrBatchList = CommonUtil.getStrNbyStr(param.get("vrBatchStr"));
        Integer vrType = NumberUtil.getObjInt(param.get("vrType"));
        if (areaCodeNow != null && areaCodeN.size() > 0 && vrType != null) {
            User user = getCurrentUser();
            Map<String, Object> addMap = new HashMap<String, Object>();
            addMap.put("vrType", vrType);
            addMap.put("vrUserId", user.getUserId());
            addMap.put("deptcodeNow", areaCodeNow);
            addMap.put("vrBatchesList", vrBatchList);
            for (String areaCode : areaCodeN) {
                addMap.put("deptcode", areaCode);
                row = ydRuleService.addAndDeVolumeRulesMany(addMap);
            }
        }
        addOperLog(AmiLogConstant.MODULE_BRANCH.CF.ROW_VOLUME_RULE, AmiLogConstant.BRANCH_SYSTEM.CF, "容积规则从 " + param.get("areaNameNow") + " 同步到 " + param.get("areaNameN"), row > -1 ? true : false);
        return DataFormat.formatAdd(row > -1 ? 1 : 0);

    }

    /**
     * 查询所有的药品分类
     * @param model
     * @param param
     * @return
     */
    @RequestMapping(value = "/getMdcmCate")
    @ResponseBody
    public String qryMedicamentCategory(Model model, @RequestParam Map<String, Object> param)  {
        List<MedicCategory> medicamentCategory = ydRuleService.qryMedicamentCategory(param);
        return new Gson().toJson(medicamentCategory);
    }

}
