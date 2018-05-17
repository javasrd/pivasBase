package com.zc.pivas.checktype.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.pivas.checktype.bean.ErrorTypeBean;
import com.zc.pivas.checktype.service.ErrorTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 审方错误类型
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/errType")
public class ErrorTypeController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    private ErrorTypeService errorTypeService;

    /***
     *
     *初始化
     * @return 查询页面
     */
    @RequestMapping(value = "/initErrType")
    @RequiresPermissions(value = {"PIVAS_MENU_301"})
    public String initErrorType() {
        return "pivas/errorTypeList";
    }

    /***
     * 查询所有数据
     * 角色名称条件查询
     * @param bean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/errTypeList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_304"})
    public String getErrorTypeList(ErrorTypeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<ErrorTypeBean> inpatientAreaBeanPagingResults =
                errorTypeService.getErrorTypeLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 添加错误类型
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addErrType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_302"})
    public String addErrorType(ErrorTypeBean bean)
            throws Exception {
        // 判断名称是否存在
        ErrorTypeBean errorType = errorTypeService.getErrorType(bean);

        try {
            if (errorType != null) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                errorTypeService.addErrorType(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.errortype.tip.add", new String[]{bean.getName()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.errortype.tip.add", new String[]{bean.getName()}),
                    false);
            throw e;
        }
    }

    /***
     *
     * 初始化
     * @return json字符串
     */
    @RequestMapping(value = "/initErrTypeUpdate", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_305"})
    public String initUpdateUser(ErrorTypeBean bean) {
        ErrorTypeBean errorType = errorTypeService.getErrorType(bean);
        if (errorType == null) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        return new Gson().toJson(errorType);
    }

    /**
     * 修改错误类型
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/mdfErrType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_305"})
    public String updateErrorType(ErrorTypeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean isExist = errorTypeService.checkErrorTypeName(bean);

        try {
            if (isExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                errorTypeService.updateErrorType(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.errortype.tip.update", new String[]{bean.getName()}),
                        true);

                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.errortype.tip.update", new String[]{bean.getName()}),
                    false);
            throw e;
        }
    }

    /**
     * 删除错误类型
     *
     * @param gid 主键id
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/delErrType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_303"})
    public String delErrorType(String gid, String name)
            throws Exception {
        // 检查错误类型是否已被使用
        int count = errorTypeService.checkErrorTypeUsed(gid);
        if (count > 0) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.errortype.tip.del", new String[]{name}),
                    false);
            return buildFailJsonMsg("审方错误类型已被使用！");
        }
        try {
            errorTypeService.delErrorType(gid);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.errortype.tip.del", new String[]{name}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.errortype.tip.del", new String[]{name}),
                    false);
            throw e;
        }
    }

    @Autowired
    public void setErrorTypeService(ErrorTypeService errorTypeService) {
        this.errorTypeService = errorTypeService;
    }

}
