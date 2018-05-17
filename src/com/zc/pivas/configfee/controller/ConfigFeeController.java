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
import com.zc.pivas.configfee.service.ConfigFeeDetailService;
import com.zc.pivas.configfee.service.ConfigFeeService;
import com.zc.pivas.configfee.bean.ConfigFeeTypeBean;
import com.zc.pivas.configfee.service.ConfigFeeTypeService;
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
 * 配置费/材料费Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/configFee")
public class ConfigFeeController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 配置费/材料费
     */
    private ConfigFeeService configFeeService;

    /**
     * 配置费规则明细
     */
    private ConfigFeeDetailService configFeeDetailService;

    /**
     * 配置费类别
     */
    private ConfigFeeTypeService configFeeTypeService;

    /***
     *
     *跳转页面
     * @return 查询页面
     */
    @RequestMapping(value = "/init")
    @RequiresPermissions(value = {"PIVAS_MENU_291"})
    public String initConfigFee() {
        return "pivas/configFeeList";
    }

    /***
     * 查询所有数据
     * 角色名称条件查询
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/getAllFeeList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_294"})
    public String getConfigFeeList(ConfigFeeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] costNamesArray = bean.getCostNames();
        if (costNamesArray != null) {
            for (int i = 0; i < costNamesArray.length; ++i) {
                costNamesArray[i] = DefineStringUtil.escapeAllLike(costNamesArray[i]);
            }
        }

        String[] costCodeArray = bean.getCostCodes();
        if (costCodeArray != null) {
            for (int i = 0; i < costCodeArray.length; ++i) {
                costCodeArray[i] = DefineStringUtil.escapeAllLike(costCodeArray[i]);
            }
        }

        JqueryStylePagingResults<ConfigFeeBean> inpatientAreaBeanPagingResults =
                configFeeService.getConfigFeeLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 添加核对类型
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addConfigFee", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_292"})
    public String addConfigFee(ConfigFeeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean nameIsExist = configFeeService.checkCondigFeeName(bean);

        // 判断编码是否重复
        boolean codeIsExist = configFeeService.checkCondigFeeCode(bean);
        try {
            if (nameIsExist) {
                return buildFailJsonMsg("配置费/材料费名称:" + bean.getCostName() + "重复！");
            } else if (codeIsExist) {
                return buildFailJsonMsg("配置费/材料费收费编码:" + bean.getCostCode() + "重复！");
            } else {
                configFeeService.addConfigFee(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIGFEE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configFee.tip.add", new String[]{bean.getCostName()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIGFEE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configFee.tip.add", new String[]{bean.getCostName()}),
                    false);
            throw e;
        }
    }

    /***
     *
     * 初始化
     * @param bean 参数
     * @return json字符串
     */
    @RequestMapping(value = "/initUpdateConfigFee", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_295"})
    public String initUpdateConfigFee(ConfigFeeBean bean) {
        ConfigFeeBean configFee = configFeeService.getConfigFee(bean);
        if (configFee == null) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        return new Gson().toJson(configFee);
    }

    /**
     * 修改核对类型
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/mdfConfigFee", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_295"})
    public String updateConfigFee(ConfigFeeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean nameIsExist = configFeeService.checkCondigFeeName(bean);

        // 判断编码是否重复
        boolean codeIsExist = configFeeService.checkCondigFeeCode(bean);
        try {
            if (nameIsExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else if (codeIsExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CODE_REPEAT, null, null);
            } else {
                configFeeService.updateConfigFee(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIGFEE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configFee.tip.update", new String[]{bean.getCostName()}),
                        true);

                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIGFEE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configFee.tip.update", new String[]{bean.getCostName()}),
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
    @RequestMapping(value = "/delConfigFee", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_293"})
    public String delConfigFee(String gid)
            throws Exception {
        String[] gids = gid.split(",");

        for (String str : gids) {
            ConfigFeeBean configFee = new ConfigFeeBean();
            configFee.setGid(str);
            configFee = configFeeService.getConfigFee(configFee);

            ConfigFeeDetailBean configFeeDetail = new ConfigFeeDetailBean();
            configFeeDetail.setCostCode(configFee.getCostCode());

            // 判断该配置费有没被使用
            int total = configFeeDetailService.getConfigFeeDetailTotal(configFeeDetail);

            try {
                if (total > 0) {
                    throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CONFIGFEE_USED, null, null);
                } else {
                    configFeeService.delConfigFee(str);
                    addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIGFEE,
                            AmiLogConstant.BRANCH_SYSTEM.CF,
                            getMessage("log.configfee.tip.del", new String[]{str}),
                            true);
                    // return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
                }

            } catch (Exception e) {
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIGFEE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configfee.tip.del", new String[]{str}),
                        false);
                throw e;
            }
        }
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));

    }

    /**
     * 查询所有配置费类别
     *
     * @return
     */
    @RequestMapping("/querySelectDatareq")
    @ResponseBody
    public String querySelectDatareq() {
        Map<String, Object> map = new HashMap<String, Object>();

        // 查询所有材料费/配置费
        List<ConfigFeeTypeBean> configFeeTypeList = configFeeTypeService.getConfigFeeTypes(null);

        map.put("success", true);
        map.put("configFeeTypeList", configFeeTypeList);

        return new Gson().toJson(map);
    }

    @Autowired
    public void setConfigFeeService(ConfigFeeService configFeeService) {
        this.configFeeService = configFeeService;
    }

    @Autowired
    public void setConfigFeeDetailService(ConfigFeeDetailService configFeeDetailService) {
        this.configFeeDetailService = configFeeDetailService;
    }

    @Autowired
    public void setConfigFeeTypeService(ConfigFeeTypeService configFeeTypeService) {
        this.configFeeTypeService = configFeeTypeService;
    }

}
