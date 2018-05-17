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
import com.zc.pivas.configfee.service.ConfigFeeService;
import com.zc.pivas.configfee.bean.ConfigFeeTypeBean;
import com.zc.pivas.configfee.service.ConfigFeeTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 配置费类别Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/configFeeType")
public class ConfigFeeTypeController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory cbemsExceptionFactory;

    /**
     * 配置费类别
     */
    private ConfigFeeTypeService configFeeTypeService;

    /**
     * 配置费
     */
    private ConfigFeeService configFeeService;

    /***
     *初始化
     * @return 查询页面
     */
    @RequestMapping(value = "/init")
    @RequiresPermissions(value = {"PIVAS_MENU_306"})
    public String initConfigFeeType() {
        return "pivas/configFreeTypeList";
    }

    /***
     * 查询所有数据
     * 角色名称条件查询
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/getFeeTypeList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_309"})
    public String getConfigFeeList(ConfigFeeTypeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] typeDescArray = bean.getTypeDescs();
        if (typeDescArray != null) {
            for (int i = 0; i < typeDescArray.length; ++i) {
                typeDescArray[i] = DefineStringUtil.escapeAllLike(typeDescArray[i]);
            }
        }

        String[] remarkArray = bean.getRemarks();
        if (remarkArray != null) {
            for (int i = 0; i < remarkArray.length; ++i) {
                remarkArray[i] = DefineStringUtil.escapeAllLike(remarkArray[i]);
            }
        }

        JqueryStylePagingResults<ConfigFeeTypeBean> inpatientAreaBeanPagingResults =
                configFeeTypeService.getConfigFeeTypeLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 新增核对类型
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addConfigFeeType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_307"})
    public String addConfigFeeType(ConfigFeeTypeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean nameIsExist = configFeeTypeService.checkCondigFeeTypeName(bean);
        try {
            if (nameIsExist) {
                throw cbemsExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                configFeeTypeService.addConfigFeeType(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configFeeType.tip.add", new String[]{bean.getTypeDesc()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configFeeType.tip.add", new String[]{bean.getTypeDesc()}),
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
    @RequestMapping(value = "/initUpdatecfgFeeType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_310"})
    public String initUpdateConfigFeeType(ConfigFeeTypeBean bean) {
        ConfigFeeTypeBean configFee = configFeeTypeService.getConfigFeeType(bean);
        if (configFee == null) {
            throw cbemsExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
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
    @RequestMapping(value = "/mdfConfigFeeType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_310"})
    public String updateConfigFeeType(ConfigFeeTypeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean nameIsExist = configFeeTypeService.checkCondigFeeTypeName(bean);

        // 判断编码是否重复
        try {
            if (nameIsExist) {
                throw cbemsExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                configFeeTypeService.updateConfigFeeType(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configFeeType.tip.update", new String[]{bean.getTypeDesc()}),
                        true);

                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.configFeeType.tip.update", new String[]{bean.getTypeDesc()}),
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
    @RequestMapping(value = "/delConfigFeeType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_308"})
    public String delConfigFeeType(String gid)
            throws Exception {
        String[] gids = gid.split(",");

        for (String str : gids) {
            ConfigFeeTypeBean configFeeType = new ConfigFeeTypeBean();
            configFeeType.setGid(str);
            configFeeType = configFeeTypeService.getConfigFeeType(configFeeType);

            ConfigFeeBean configFee = new ConfigFeeBean();
            configFee.setConfigFeeType(Integer.parseInt(str));

            // 判断该配置费类别有没被使用
            int total = configFeeService.getConfigFeeTotal(configFee);

            try {
                if (total > 0) {
                    throw cbemsExceptionFactory.createSdException(ExceptionCodeConstants.CONFIGFEETYPE_USED,
                            null,
                            null);
                } else {
                    configFeeTypeService.delConfigFeeType(str);
                    addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_TYPE,
                            AmiLogConstant.BRANCH_SYSTEM.CF,
                            getMessage("log.configfeeType.tip.del", new String[]{str}),
                            true);
                }

            } catch (Exception e) {
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CONFIG_FEE_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.configfeeType.tip.del", new String[]{str}),
                        false);
                throw e;
            }
        }
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));

    }

    @Autowired
    public void setConfigFeeTypeService(ConfigFeeTypeService configFeeTypeService) {
        this.configFeeTypeService = configFeeTypeService;
    }

    @Autowired
    public void setConfigFeeService(ConfigFeeService configFeeService) {
        this.configFeeService = configFeeService;
    }

}
