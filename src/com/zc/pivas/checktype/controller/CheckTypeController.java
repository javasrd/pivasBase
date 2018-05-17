package com.zc.pivas.checktype.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.pivas.checktype.bean.CheckTypeBean;
import com.zc.pivas.checktype.service.CheckTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 核对类型Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/inspectType")
public class CheckTypeController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    private CheckTypeService checkTypeService;

    /***
     *
     *初始化
     * @return 查询页面
     */
    @RequestMapping(value = "/initInspectType")
    @RequiresPermissions(value = {"PIVAS_MENU_271"})
    public String initCheckType() {
        return "pivas/checkTypeList";
    }

    /***
     * 查询所有数据
     * 角色名称条件查询
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/inspectTypeList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_274"})
    public String getCheckTypeList(CheckTypeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<CheckTypeBean> inpatientAreaBeanPagingResults =
                checkTypeService.getCheckTypeLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 新增核对类型
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addInspectType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_272"})
    public String addCheckType(CheckTypeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean isNameExist = checkTypeService.checkNameIsExitst(bean);

        // 判断顺序id是否存在
        boolean isOrderIDExist = checkTypeService.checkOrderIDIsExitst(bean);

        // 判断核对类型是否存在
        boolean isCheckTypeExist = checkTypeService.checkTypeIsExitst(bean);

        try {
            if (isNameExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CHECKTYPE_CHECKNAME_USED, null, null);
            } else if (isOrderIDExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CHECKTYPE_ORDERID_USED, null, null);
            } else if (isCheckTypeExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CHECKTYPE_CHECKTYPE_USED, null, null);
            } else {
                checkTypeService.addCheckType(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CHECK_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.checktype.add", new String[]{bean.getCheckName()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CHECK_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.checktype.add", new String[]{bean.getCheckName()}),
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
    @RequestMapping(value = "/initUpdateInspectType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_275"})
    public String initUpdateCheckType(CheckTypeBean bean) {
        CheckTypeBean checkType = checkTypeService.getCheckType(bean);
        if (checkType == null) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        return new Gson().toJson(checkType);
    }

    /**
     * 修改核对类型
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/updateInspectType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_275"})
    public String updateCheckType(CheckTypeBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean isNameExist = checkTypeService.checkNameIsExitst(bean);

        // 判断顺序id是否存在
        boolean isOrderIDExist = checkTypeService.checkOrderIDIsExitst(bean);

        // 判断核对类型是否存在
        boolean isCheckTypeExist = checkTypeService.checkTypeIsExitst(bean);

        try {
            if (isNameExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CHECKTYPE_CHECKNAME_USED, null, null);
            } else if (isOrderIDExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CHECKTYPE_ORDERID_USED, null, null);
            } else if (isCheckTypeExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CHECKTYPE_CHECKTYPE_USED, null, null);
            } else {
                checkTypeService.updateCheckType(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CHECK_TYPE,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.checktype.update", new String[]{bean.getOrderID()}),
                        true);

                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CHECK_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.checktype.update", new String[]{bean.getOrderID()}),
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
    @RequestMapping(value = "/delInspectType", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_273"})
    public String delCheckType(String gid)
            throws Exception {
        try {
            checkTypeService.delCheckType(gid);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CHECK_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.checktype.del", new String[]{gid}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.CHECK_TYPE,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.checktype.del", new String[]{gid}),
                    false);
            throw e;
        }
    }

    @Autowired
    public void setCheckTypeService(CheckTypeService checkTypeService) {
        this.checkTypeService = checkTypeService;
    }

}
