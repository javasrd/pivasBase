package com.zc.pivas.wardgroup.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.pivas.common.entity.ResultData;
import com.zc.pivas.common.util.DataFormat;
import com.zc.pivas.common.util.SysConstant.operLogRecord;
import com.zc.pivas.wardgroup.bean.WardGroupBean;
import com.zc.pivas.wardgroup.service.WardGroupService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.eclipse.birt.report.model.api.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 病区分组Controller类
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/wardRegionGroup")
public class WardGroupController extends SdBaseController {

    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(WardGroupController.class);

    @Resource
    private WardGroupService wardGroupService;

    /**
     * 主页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/init")
    @RequiresPermissions(value = {"PIVAS_MENU_924"})
    public String init(Model model, HttpServletRequest request) {

        return "pivas/wardGroup";
    }

    /**
     * 查询分组
     *
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qryList")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_274"})
    public String getCheckTypeList(JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<WardGroupBean> pagingResults =
                wardGroupService.getGroupLsit(jquryStylePaging);

        return new Gson().toJson(pagingResults);
    }

    /**
     * 初始化病区
     *
     * @param model
     * @param request
     * @param wardId
     * @return
     */
    @RequestMapping(value = "/initSelWard")
    public String initPatient(Model model, HttpServletRequest request, String wardId) throws Exception {

        List<WardGroupBean> wardList = wardGroupService.getWardList(wardId);

        model.addAttribute("wardList", wardList);

        return "pivas/wardGroupSelect";
    }


    /**
     * 保存分组
     *
     * @param groupName
     * @param groupOrder
     * @param idStrs
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveWardGroup")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_981"})
    public ResultData saveWard(String groupName, String groupOrder, String idStrs)
            throws Exception {

        String id = null;

        if (StringUtils.isNotBlank(groupName) && StringUtils.isNotBlank(groupOrder)) {

            int num = 0;
            num = wardGroupService.checkParam("name", groupName);
            if (num >= 1) {
                addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "添加分组失败,组名重复", false);
                return DataFormat.formatFail("组名重复", null);
            }

            num = wardGroupService.checkParam("order", groupOrder);
            if (num >= 1) {
                addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "添加分组失败,优先级重复", false);
                return DataFormat.formatFail("优先级重复", null);
            }

            WardGroupBean ward = new WardGroupBean();
            ward.setDeptname(groupName);
            ward.setOrdernum(groupOrder);

            //保存创建的分组
            wardGroupService.saveGroup(ward);
            id = ward.getId();

            if (id == null || StringUtil.isBlank(id)) {
                addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "保存分组失败", false);
                return DataFormat.formatFail("保存分组失败", null);
            }

        } else {

            log.error("分组功能保存分组获取信息失败");
            return DataFormat.formatFail("数据错误", null);
        }

        if (StringUtils.isNotBlank(idStrs)) {

            //保存分组的子病区
            String[] wardIdArray = idStrs.split(",");
            wardGroupService.saveSubWard(id, wardIdArray);
        }

        addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "保存分组成功", true);

        return DataFormat.formatSucc();

    }

    /**
     * 更新分组
     *
     * @param groupOrder
     * @param idStrs
     * @param parentid
     * @param checkOrder
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateGroup")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_982"})
    public ResultData updateWardGroup(String groupOrder, String idStrs, String parentid, Boolean checkOrder)
            throws Exception {

        if (StringUtils.isNotBlank(groupOrder)) {
            //判断是否修改了优先级，并且修改后的优先级是否重复
            if (checkOrder) {
                int num = 0;
                num = wardGroupService.checkParam("order", groupOrder);
                if (num >= 1) {
                    addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "更新分组失败,优先级重复", false);
                    return DataFormat.formatFail("优先级重复", null);
                }
            }
        } else {
            log.error("分组功能更新分组获取信息失败");
            return DataFormat.formatFail("数据错误", null);
        }

        String[] wardIdArray = idStrs.split(",");

        //更新分组
        wardGroupService.updateWardGroup(parentid, wardIdArray, groupOrder);

        addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "更新分组成功", true);

        return DataFormat.formatSucc();

    }


    /**
     * 删除分组
     *
     * @param idStrs
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delGroup")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_983"})
    public ResultData delWardGroup(String idStrs) throws Exception {

        if (StringUtils.isBlank(idStrs)) {
            log.error("分组功能删除分组获取信息失败");
            addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "删除分组失败", false);
            return DataFormat.formatFail("数据错误", null);
        }

        String[] idArray = idStrs.split(",");

        wardGroupService.delWardGroup(idArray);
        addOperLog(operLogRecord.wardGroup, AmiLogConstant.BRANCH_SYSTEM.CF, "删除分组成功", true);
        return DataFormat.formatSucc();
    }


}
