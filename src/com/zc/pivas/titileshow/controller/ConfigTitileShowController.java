package com.zc.pivas.titileshow.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.pivas.drugDamage.service.DrugDamageService;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import com.zc.pivas.titileshow.bean.ConfigTitleSubBean;
import com.zc.pivas.titileshow.service.ConfigTitleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医嘱/批次列表配置Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/doctorShowTitle")
public class ConfigTitileShowController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 配医嘱/批次列表配置
     */
    private ConfigTitleService configTitleService;

    @Resource
    private DrugDamageService drugDamageService;


    @Autowired
    public void setConfigTitleService(ConfigTitleService configTitleService) {
        this.configTitleService = configTitleService;
    }

    /***
     *
     *跳转页面
     * @return 查询页面
     */
    @RequestMapping(value = "/initDdoctorShowTitle")
    @RequiresPermissions(value = {"PIVAS_MENU_925"})
    public String initConfigShowTitle(Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        String allAccount = configTitleService.getAllAccount();
        if (allAccount != null) {
            String[] allAccountArray = allAccount.split(",");
            if (allAccountArray.length != 0) {
                map.put("accounts", allAccountArray);
            }
        }
        List<String> accounts = configTitleService.getExitAccounts(map);
        model.addAttribute("allUsers", accounts);
        return "pivas/configShowTitleList";
    }

    /***
     *
     *查寻所有用户
     * @return 查询页面
     */
    @RequestMapping(value = "/getAccountList")
    @ResponseBody
    public String getAllAccounts() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String allAccount = configTitleService.getAllAccount();
        if (allAccount != null) {
            String[] allAccountArray = allAccount.split(",");
            if (allAccountArray.length != 0) {
                map.put("accounts", allAccountArray);
            }
        }
        List<String> accounts = configTitleService.getExitAccounts(map);
        return new Gson().toJson(accounts);
    }

    /***
     * 查询所有数据
     * 角色名称条件查询
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/getTilteList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_953"})
    public String getShowTilteList(ConfigTitleBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        if (bean.getConfName() != null && bean.getConfName().length() != 0) {
            String[] confNames = bean.getConfName().split(",");
            bean.setConfNames(confNames);
        }
        JqueryStylePagingResults<ConfigTitleBean> inpatientAreaBeanPagingResults =
                configTitleService.getShowTilteList(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 药品标签详情
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTitleDetail", produces = "application/json")
    @ResponseBody
    public String displayTitleDetail(ConfigTitleBean bean)
            throws Exception {
        try {
            List<ConfigTitleSubBean> titleDetail = configTitleService.displayTitleDetail(bean.getConfId());
            return new Gson().toJson(titleDetail);
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addData", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_951"})
    public String addConfTitle(ConfigTitleBean bean) throws Exception {

        boolean nameIsExist = configTitleService.checkConfigTitleName(bean);
        try {
            if (nameIsExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                configTitleService.addConfigTitle(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TITLE_SHOW,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.titleShow.tip.addShowConfig", new String[]{bean.getConfName()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TITLE_SHOW,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.titleShow.tip.addShowConfig", new String[]{bean.getConfName()}),
                    false);
            throw e;
        }
    }

    @RequestMapping(value = "/updateData", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_954"})
    public String updateConfTitle(ConfigTitleBean bean)
            throws Exception {
        String[] names = bean.getUseByNames();
        String namesStr = "";
        for (int i = 0; i < names.length; i++) {
            if (i != names.length - 1) {
                namesStr += names[i] + ",";
            } else {
                namesStr += names[i];
            }
        }
        bean.setUseBy(namesStr);
        List<ConfigTitleBean> beans = configTitleService.checkUpdateConfigTitleName(bean);
        boolean nameIsExist = false;
        if (beans != null && beans.size() > 0) {
            for (ConfigTitleBean titleBean : beans) {
                if (titleBean.getConfId() != bean.getConfId()) {
                    nameIsExist = true;
                    break;
                }
            }
        }

        try {
            if (nameIsExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                configTitleService.updateConfTitle(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TITLE_SHOW,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.titleShow.tip.updateShowConfig", new String[]{bean.getConfName(), bean.getConfType().toString(), bean.getUseBy().toString()}),
                        true);
                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TITLE_SHOW,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.titleShow.tip.updateShowConfig", new String[]{bean.getConfName(), bean.getConfType().toString(), bean.getUseBy().toString()}),
                    false);
            throw e;
        }
    }

    /**
     * 删除审核类型
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/delData", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_952"})
    public String delConfigTitle(String confIds)
            throws Exception {
        String[] confIdsArry = confIds.split(",");

        for (String str : confIdsArry) {
            try {
                configTitleService.delConfigTitle(Long.parseLong(str));
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TITLE_SHOW,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.titleShow.tip.deleteShowConfig", new String[]{str}),
                        true);
            } catch (Exception e) {
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TITLE_SHOW,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.titleShow.tip.deleteShowConfig", new String[]{str}),
                        false);
                throw e;
            }
        }
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }


}
