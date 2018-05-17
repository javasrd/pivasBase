package com.zc.pivas.configfee.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.pivas.configfee.bean.ConfigFeeBean;
import com.zc.pivas.configfee.bean.ConfigFeeDetailBean;
import com.zc.pivas.configfee.bean.ConfigFeeRuleBean;
import com.zc.pivas.configfee.service.ConfigFeeDetailService;
import com.zc.pivas.configfee.service.ConfigFeeRuleService;
import com.zc.pivas.configfee.service.ConfigFeeService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置费规则Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/configFeeRule")
public class ConfigFeeRuleController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 配置费规则
     */
    private ConfigFeeRuleService configFeeRuleService;

    /**
     * 配置费明细
     */
    private ConfigFeeDetailService configFeeDetailService;

    /**
     * 配置费/材料费
     */
    private ConfigFeeService configFeeService;

    /**
     * 药品分类
     */
    private MedicCategoryDao medicCategoryDao;

    /**
     * 药品
     */
    private MedicamentsDao medicamentsDao;

    /***
     *
     *跳转页面
     * @return 查询页面
     */
    @RequestMapping(value = "/initFeeRule")
    @RequiresPermissions(value = {"PIVAS_MENU_281"})
    public String initConfigFeeRule() {
        return "pivas/configFeeRuleList";
    }

    /***
     * 查询所有数据
     * 角色名称条件查询
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/getFeeRuleList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_284"})
    public String getConfigFeeRuleList(ConfigFeeRuleBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] ruleNameArray = bean.getRuleNames();
        if (ruleNameArray != null) {
            for (int i = 0; i < ruleNameArray.length; ++i) {
                ruleNameArray[i] = DefineStringUtil.escapeAllLike(ruleNameArray[i]);
            }
        }

        String[] drugTypeNameArray = bean.getDrugTypeNames();
        if (drugTypeNameArray != null) {
            for (int i = 0; i < drugTypeNameArray.length; ++i) {
                drugTypeNameArray[i] = DefineStringUtil.escapeAllLike(drugTypeNameArray[i]);
            }
        }

        String[] volumeArray = bean.getVolumes();
        if (volumeArray != null) {
            for (int i = 0; i < volumeArray.length; ++i) {
                volumeArray[i] = DefineStringUtil.escapeAllLike(volumeArray[i]);
            }
        }

        JqueryStylePagingResults<ConfigFeeRuleBean> inpatientAreaBeanPagingResults =
                configFeeRuleService.getConfigFeeRuleLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /***
     * 查询所有明细数据
     * @param bean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/getCostDetailList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_285"})
    public String getConfigFeeDetailList(ConfigFeeDetailBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<ConfigFeeDetailBean> inpatientAreaBeanPagingResults =
                configFeeDetailService.getConfigFeeDetailLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 添加
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addcfgFeeRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_282"})
    public String addConfigFeeRule(ConfigFeeRuleBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean isExist = configFeeRuleService.checkRuleName(bean);

        try {
            if (isExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {

                List<ConfigFeeRuleBean> ruleList = configFeeRuleService.checkIsExist(bean);

                if (ruleList.size() > 0) {
                    throw sdExceptionFactory.createSdException("70010", null, null);
                }

                configFeeRuleService.addConfigFeeRule(bean);
                String detailCode = configFeeRuleService.getConfigFeeRule(bean).getGid();

                // 先删除明细信息后插入
                configFeeDetailService.delConfigFeeDetail(bean.getGid());

                if (null != bean.getConfigFeeDetailList()) {
                    // 增加
                    for (ConfigFeeDetailBean configFeeDetailBean : bean.getConfigFeeDetailList()) {
                        configFeeDetailBean.setDetailCode(detailCode);
                        configFeeDetailService.addConfigFeeDetail(configFeeDetailBean);
                    }
                }

                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_RULE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configfeerule.tip.add", new String[]{bean.getRuleName()}),
                        true);
            }

            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_RULE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configfeerule.tip.add", new String[]{bean.getRuleName()}),
                    false);
            throw e;
        }
    }

    /**
     * 查询所有药品分类，配置费/材料费
     *
     * @return
     */
    @RequestMapping("/querySelectDatareq")
    @ResponseBody
    public String querySelectDatareq() {
        Map<String, Object> map = new HashMap<String, Object>();

        // 查询所有材料费/配置费
        List<ConfigFeeBean> configFeeList = configFeeService.getConfigFees(null);

        // 查询所有药品分类
        List<MedicCategory> medicCategoryList = medicCategoryDao.queryAllForAddConfigFeeRule(null);

        // 根据上述药品分类中第一条数据获取对应的药品列表
        List<Medicaments> medicamentsList = null;
        Medicaments condition = new Medicaments();
        condition.setCategoryId(String.valueOf(medicCategoryList.get(0).getCategoryId()));

        medicamentsList = medicamentsDao.queryAllByCondition(condition);

        map.put("success", true);
        map.put("configFeeList", configFeeList);
        map.put("mediCategoryList", medicCategoryList);
        map.put("medicamentsList", medicamentsList);

        return new Gson().toJson(map);
    }

    /**
     * 查询所有药品
     *
     * @return
     */
    @RequestMapping("/qryDgs")
    @ResponseBody
    public String queryDrugs(Medicaments bean)
            throws Exception {
        String[] columns = new String[]{"medicamentsCode", "medicamentsName"};

        JqueryStylePagingResults<Medicaments> results = new JqueryStylePagingResults<Medicaments>(columns);

        String name = bean.getMedicamentsName();
        if (StringUtils.isNotBlank(name)) {
            name = DefineStringUtil.escapeAllLike(name);
            bean.setMedicamentsName(name);
        }

        // 根据上述药品分类中第一条数据获取对应的药品列表
        List<Medicaments> medicamentsList = medicamentsDao.queryAllByCondition(bean);
        results.setDataRows(null);

        // 总数
        Integer total = null;
        if (null != medicamentsList) {
            results.setDataRows(medicamentsList);
            total = medicamentsList.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /***
     *
     * 初始化
     * @param bean 参数
     * @return json字符串
     */
    @RequestMapping(value = "/initUpdateFeeRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_285"})
    public String initUpdateConfigFeeRule(ConfigFeeRuleBean bean) {
        ConfigFeeRuleBean configFeeRule = configFeeRuleService.getConfigFeeRule(bean);
        if (configFeeRule == null) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        return new Gson().toJson(configFeeRule);
    }

    /**
     * 修改核对类型
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/mdfFeeRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_285"})
    public String updateConfigFeeRule(ConfigFeeRuleBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean isExist = configFeeRuleService.checkRuleName(bean);

        String oldName = bean.getIsSameName();
        boolean ischeck = true;
        if (StringUtils.isNotBlank(oldName)) {
            ischeck = false;
        }

        // 判断药品分类是否已被使用
        //        boolean drugClassUsed = configFeeRuleService.checkDrugClass(bean);
        try {
            if (isExist && ischeck) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {

                List<ConfigFeeRuleBean> ruleList = configFeeRuleService.checkIsExist(bean);

                if (ruleList.size() > 0) {
                    throw sdExceptionFactory.createSdException("70010", null, null);
                }

                configFeeRuleService.updateConfigFeeRule(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_RULE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configfeerule.tip.update", new String[]{bean.getRuleName()}),
                        true);

                return buildSuccessJsonMsg("common.op.success");
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_RULE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configfeerule.tip.update", new String[]{bean.getRuleName()}),
                    false);
            throw e;
        }
    }

    /**
     * 删除审核类型
     *
     * @param gid 主键id
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/delFeeRule", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_283"})
    public String delConfigFeeRule(String gid)
            throws Exception {
        try {

            String[] gidStr = gid.trim().split(",");

            for (String id : gidStr) {

                configFeeRuleService.delConfigFeeRule(id);

                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_RULE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configfeerule.tip.del", new String[]{id}),
                        true);
            }

        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_RULE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configfeerule.tip.del", new String[]{gid}),
                    false);
            throw e;
        }

        return buildSuccessJsonMsg("common.op.success");
    }

    @Autowired
    public void setConfigFeeRuleService(ConfigFeeRuleService configFeeRuleService) {
        this.configFeeRuleService = configFeeRuleService;
    }

    @Autowired
    public void setConfigFeeDetailService(ConfigFeeDetailService configFeeDetailService) {
        this.configFeeDetailService = configFeeDetailService;
    }

    @Autowired
    public void setConfigFeeService(ConfigFeeService configFeeService) {
        this.configFeeService = configFeeService;
    }

    @Autowired
    public void setMedicCategoryDao(MedicCategoryDao medicCategoryDao) {
        this.medicCategoryDao = medicCategoryDao;
    }

    @Autowired
    public void setMedicamentsDao(MedicamentsDao medicamentsDao) {
        this.medicamentsDao = medicamentsDao;
    }

}
