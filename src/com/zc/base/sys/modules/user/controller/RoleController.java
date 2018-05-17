package com.zc.base.sys.modules.user.controller;

import com.google.gson.Gson;
import com.zc.base.common.bo.Node;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.user.dto.RolePri;
import com.zc.base.sys.modules.user.entity.Privilege;
import com.zc.base.sys.modules.user.entity.Role;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.PriService;
import com.zc.base.sys.modules.user.service.RoleService;
import com.zc.base.sys.modules.user.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 角色管理
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller
@RequestMapping({"/sys/role"})
public class RoleController extends SdBaseController {
    @Resource
    private SdExceptionFactory sdExceptionFactory;
    @Autowired
    private PriService priService;
    private RoleService roleService;
    private UserService userService;

    @RequestMapping(value = {"/addRole"}, produces = {"application/json"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_125"})
    public String addRole(Role role)
            throws Exception {
        Role roleNumber = this.roleService.getRoleID(role);
        try {
            if (roleNumber != null) {

                if (role.getName().equals(roleNumber.getName())) {
                    throw this.sdExceptionFactory.createSdException("00003", null, null);
                }
            } else {
                this.roleService.addRole(role);
                addOperLog("SM_2",
                        AmiLogConstant.BRANCH_SYSTEM.SM,
                        getMessage("log.role.tip.addrole", new String[]{role.getName()}),
                        true);
            }

            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.role.tip.addrole", new String[]{role.getName()}),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/addRoleRefPrivilege"}, produces = {"application/json"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_128"})
    public String addRoleRefPrivilege(@RequestBody RolePri rp)
            throws Exception {
        Role role = new Role();
        role.setRoleId(Long.valueOf(rp.getRoleId()));
        Role u = this.roleService.getRoleID(role);
        try {
            if ((u == null) || (!this.priService.checkExists(rp.getPris()))) {
                throw this.sdExceptionFactory.createSdException("00001", null, null);
            }
            this.priService.addRolePrivilegeBatch(Long.valueOf(rp.getRoleId()), rp.getPris(), rp.getIgnorePris());
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.user.tip.roleImpowerLose", new String[]{u.getRoleId().toString(), u.getName()}),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.user.tip.roleImpowerLose", new String[]{rp.getRoleId().toString()}),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/delRole"}, produces = {"application/json"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_127"})
    public String delRole(String roleId)
            throws Exception {
        try {
            this.roleService.delRole(roleId);
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.role.tip.deleteRole", new String[]{roleId}),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.role.tip.deleteRole", new String[]{roleId}),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/rolelist"}, produces = {"application/json"})
    @ResponseBody
    public String getRole(Role role, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] namelist = role.getNameList();
        if (namelist != null) {
            for (int i = 0; i < namelist.length; i++) {
                namelist[i] = DefineStringUtil.escapeAllLike(namelist[i]);
            }
        }

        String[] desclist = role.getDescList();
        if (desclist != null) {
            for (int i = 0; i < desclist.length; i++) {
                desclist[i] = DefineStringUtil.escapeAllLike(desclist[i]);
            }
        }

        JqueryStylePagingResults<Role> rolePagingResults = this.roleService.getRole(role, jquryStylePaging);

        return new Gson().toJson(rolePagingResults);
    }


    public RoleService getRoleService() {
        return this.roleService;
    }


    public UserService getUserService() {
        return this.userService;
    }


    @RequestMapping({"/initRole"})
    @RequiresPermissions({"PIVAS_MENU_118"})
    public String initRole() {
        return "sys/user/roleList";
    }


    @RequestMapping(value = {"/initUpdateRole"}, produces = {"application/json"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_126"})
    public String initUpdateUser(Role role) {
        Role roleInfo = this.roleService.getRoleID(role);
        if (roleInfo == null) {
            throw this.sdExceptionFactory.createSdException("00001", null, null);
        }
        return new Gson().toJson(roleInfo);
    }


    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = {"/treeRole"}, produces = {"application/json"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_128"})
    public String treeRole(HttpServletRequest request, Long roleId) {
        List<Node> nodes = new ArrayList();
        List<Privilege> hasPri = null;

        User us = getCurrentUser();
        String userAccount = us.getAccount();

        boolean isAdmin = false;
        if (SysConstant.SYSADMIN.equals(userAccount)) {
            isAdmin = true;
        }


        String language = RequestContextUtils.getLocale(request).getLanguage();


        hasPri = this.priService.getPrivilegeListByRoleId(roleId, us.getUserId(), language);
        if ((hasPri != null) && (hasPri.size() > 0)) {
            Node node = null;
            for (Privilege p : hasPri) {
                node = new Node();
                node.setId(p.getPrivilegeId());
                node.setpId(p.getParentId());
                node.setChecked(Boolean.valueOf(p.isCheck()));
                if (isAdmin) {
                    node.setChkDisabled(Boolean.valueOf(false));
                } else {
                    node.setChkDisabled(Boolean.valueOf(!p.isUse()));
                }
                node.setName(p.getName());
                nodes.add(node);
            }
        }
        return new Gson().toJson(nodes);
    }


    @RequestMapping(value = {"/updateRole"}, produces = {"application/json"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_126"})
    public String updateRole(Role role)
            throws Exception {
        String roleName = role.getName();
        role.setName(null);
        Role r = this.roleService.getRoleID(role);
        try {
            if (r == null) {
                throw this.sdExceptionFactory.createSdException("00001", null, null);
            }
            role.setName(roleName);
            this.roleService.updateRole(role);
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.role.tip.updateRole",
                            new String[]{role.getRoleId().toString(), r.getName(), role.getName()}),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_2",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.role.tip.updateRoleLose", new String[]{role.getRoleId().toString(), role.getName()}),
                    false);
            throw e;
        }
    }
}
