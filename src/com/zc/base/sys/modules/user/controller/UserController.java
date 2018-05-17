package com.zc.base.sys.modules.user.controller;

import com.google.gson.Gson;
import com.zc.base.common.bo.Node;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.FileUtil;
import com.zc.base.common.util.HttpSend;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.user.dto.RolePri;
import com.zc.base.sys.modules.user.entity.Role;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.RoleService;
import com.zc.base.sys.modules.user.service.UserGroupService;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.base.sys.modules.user.validation.UserPasswordValidator;
import com.zc.base.web.i18n.MessageHolder;
import com.zc.pivas.medicaments.controller.MedicamentStockController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;


/**
 * 用户管理
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller(value = "pivasUserController")
@RequestMapping({"/sys/user"})
public class UserController extends SdBaseController {
    @Resource
    private UserGroupService userGroupService;
    @Resource
    private SdExceptionFactory sdExceptionFactory;
    private UserPasswordValidator changePasswordValidator;
    @Autowired
    private MessageHolder messageHolder;
    private RoleService roleService;
    private User user;
    private UserPasswordValidator userAddPasswordValidator;
    private UserService userService;

    public UserController() {
    }

    @RequestMapping(
            value = {"/addTreeUserRole"},
            produces = {"application/json"}
    )
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_122"})
    public String addTreeUserRole(@RequestBody RolePri rp) throws Exception {
        String roleIds = rp.getRoleId();
        String[] var6;
        int var5 = (var6 = roleIds.split(",")).length;

        for (int var4 = 0; var4 < var5; ++var4) {
            String roleID = var6[var4];
            User u = this.userService.getUserID(Long.valueOf(roleID));

            try {
                if (u == null || !this.roleService.checkExists(rp.getPris())) {
                    throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
                }

                this.userService.addRoleByUser(Long.valueOf(roleID), rp.getPris(), rp.getIgnorePris());
                this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.userImpower", new String[]{rp.getRoleId().toString(), u.getName()}), true);
            } catch (Exception var9) {
                this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.userImpowerLose", new String[]{rp.getRoleId().toString()}), false);
                throw var9;
            }
        }

        return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
    }

    @RequestMapping(
            value = {"/addUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_119"})
    public String addUser(User user1) throws Exception {
        try {
            user1.setAccountType(1);
            user1.setCreator(this.getCurrentUser().getUserId());
            this.userService.addUser(user1);
            User userTemp = this.userService.getUserByAccount(user1.getAccount());
            String sickRoomIds = user1.getSickRoomIds();
            String areaCode;
            int var7;
            int var8;
            String[] var9;
            if (!StringUtils.isEmpty(sickRoomIds)) {
                String[] strs = sickRoomIds.split(",");
                List<Long> sickRoomIdList = new ArrayList();
                var9 = strs;
                var8 = strs.length;

                for (var7 = 0; var7 < var8; ++var7) {
                    areaCode = var9[var7];
                    sickRoomIdList.add(Long.valueOf(areaCode));
                }

                this.userService.addUserSickRoom(userTemp.getUserId(), sickRoomIdList);
            }

            Map<String, Object> map = new HashMap();
            map.put("userId", userTemp.getUserId());
            map.put("account", userTemp.getAccount());
            if (user1.getUserType() != null && user1.getUserType() == 6) {
                if ("Y".equals(user1.getGlAreaDel())) {
                    this.userService.delGlAreaByUser(map);
                }

                if (user1.getGlAreaAddN() != null && user1.getGlAreaAddN().length() > 0) {
                    String[] areaCodeN = user1.getGlAreaAddN().split(",");
                    var9 = areaCodeN;
                    var8 = areaCodeN.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        areaCode = var9[var7];
                        if (areaCode != null) {
                            map.put("deptCode", areaCode);
                            this.userService.addGlAreaByUser(map);
                        }
                    }
                }
            } else {
                this.userService.delGlAreaByUser(map);
            }

            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.addUser", new String[]{user1.getName()}), true);
            return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception var10) {
            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.addUser", new String[]{user1.getName()}), false);
            throw var10;
        }
    }

    @RequestMapping(
            value = {"/deleteUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_121"})
    public String deleteUser(String userId) throws Exception {
        try {
            this.userService.deleteUserById(userId);
            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.deleteUser", new String[]{userId}), true);

            return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception var7) {
            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.deleteUser", new String[]{userId}), false);
            throw var7;
        }
    }

    public UserPasswordValidator getChangePasswordValidator() {
        return this.changePasswordValidator;
    }

    public RoleService getRoleService() {
        return this.roleService;
    }

    public User getUser() {
        return this.user;
    }

    @RequestMapping(
            value = {"/userlist"},
            produces = {"application/json"}
    )
    @ResponseBody
    public String getUser(User user1, JqueryStylePaging jquryStylePaging) throws Exception {
        String[] accounts = user1.getAccounts();
        if (accounts != null) {
            for (int i = 0; i < accounts.length; ++i) {
                accounts[i] = DefineStringUtil.escapeAllLike(accounts[i]);
            }
        }

        String[] names = user1.getNames();
        if (names != null) {
            for (int i = 0; i < names.length; ++i) {
                names[i] = DefineStringUtil.escapeAllLike(names[i]);
            }
        }

        String[] telephones = user1.getTelephones();
        if (telephones != null) {
            for (int i = 0; i < telephones.length; ++i) {
                telephones[i] = DefineStringUtil.escapeAllLike(telephones[i]);
            }
        }

        User user = this.getCurrentUser();
        Long[] createIds = this.userGroupService.queryAllGroupMemberId(user.getUserId(), 1);
        user1.setCreateIds(createIds);
        user1.setTempAccount(user.getAccount());
        JqueryStylePagingResults<User> userPagingResults = this.userService.getUser(user1, jquryStylePaging);
        return (new Gson()).toJson(userPagingResults);
    }

    public UserPasswordValidator getUserAddPasswordValidator() {
        return this.userAddPasswordValidator;
    }

    public UserService getUserService() {
        return this.userService;
    }

    @RequestMapping({"/initAddUser"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_119"})
    public String initAddUser() {
        return "user/addUser";
    }

    @RequestMapping(
            value = {"/initUpdateUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    public String initUpdateUser(Long userId) throws Exception {
        User userInfo = this.userService.getUserID(userId);
        if (userInfo == null) {
            throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
        } else {
            Map<String, Object> map = new HashMap();
            map.put("userId", userInfo.getUserId());
            List<Map<String, Object>> glAreaList = this.userService.qryGlAreaByUser(map);
            userInfo.setGlAreaList(glAreaList);
            return (new Gson()).toJson(userInfo);
        }
    }

    @RequestMapping(
            value = {"/initUpdateCurrUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    public String initUpdateCurrUser(Long userId) throws Exception {
        User userInfo = this.userService.getUserID(userId);
        if (userInfo == null) {
            throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
        } else {
            return (new Gson()).toJson(userInfo);
        }
    }

    @RequestMapping({"/initUser"})
    @RequiresPermissions({"PIVAS_MENU_117"})
    public String initUser(Model model) {
        model.addAttribute("minLength", Propertiesconfiguration.getStringProperty("password.minLength"));
        model.addAttribute("maxLength", Propertiesconfiguration.getStringProperty("password.maxLength"));
        model.addAttribute("currUserId", this.getCurrentUser().getUserId());
        List<Map<String, Object>> inpAreaList = this.userService.qryAreaListAll(new HashMap());
        model.addAttribute("inpAreaList", inpAreaList);
        return "sys/user/userList";
    }

    @RequestMapping({"/resetUpdatePassword"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_124"})
    public String resetUpdatePassword(User user1, HttpServletRequest request) throws Exception {
        User u = this.userService.getUserID(user1.getUserId());
        String modulesType = "SM_1";
        Integer subSysType = AmiLogConstant.BRANCH_SYSTEM.SM;

        try {
            if (u == null) {
                throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
            } else {
                boolean isPwdRepeat = this.userService.isPwdRepeatByUser(u.getAccount(), user1.getPassword());
                if (isPwdRepeat) {
                    throw this.sdExceptionFactory.createSdException("00016", (Object[]) null, (Throwable) null);
                } else {
                    u.setPassword(user1.getPassword());
                    this.changePasswordValidator.validate(u);
                    this.userService.updatePassword(u);
                    this.addOperLog(modulesType, subSysType, this.getMessage("log.user.tip.userPasswordReset", new String[]{user1.getUserId().toString(), u.getName()}), true);
                    return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
                }
            }
        } catch (Exception var7) {
            this.addOperLog(modulesType, subSysType, this.getMessage("log.user.tip.userPasswordResetLose", new String[]{user1.getUserId().toString()}), false);
            throw var7;
        }
    }

    @Resource(
            name = "changePasswordValidator"
    )
    public void setChangePasswordValidator(UserPasswordValidator changePasswordValidator) {
        this.changePasswordValidator = changePasswordValidator;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Resource(
            name = "userAddPasswordValidator"
    )
    public void setUserAddPasswordValidator(UserPasswordValidator userAddPasswordValidator) {
        this.userAddPasswordValidator = userAddPasswordValidator;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            value = {"/treeUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_122"})
    public String treeUser(String ids) {
        List<Node> nodes = new ArrayList();
        User us = this.getCurrentUser();
        String account = us.getAccount();
        boolean isAdmin = false;
        if (SysConstant.SYSADMIN.equals(account)) {
            isAdmin = true;
        }

        String[] userIDs = ids.split(",");
        List<Role> roleList = this.roleService.getRolesByUserId(Long.valueOf(userIDs[0]), us.getUserId());
        int maxCheckedNode = 0;
        if (roleList != null && roleList.size() > 0) {
            Node node1 = new Node();
            node1.setId(0L);
            node1.setpId(-1L);
            node1.setName(this.messageHolder.getMessage("role.label.role"));
            node1.setOpen(true);

            Node node;
            for (Iterator var11 = roleList.iterator(); var11.hasNext(); nodes.add(node)) {
                Role r = (Role) var11.next();
                node = new Node();
                node.setId(r.getRoleId());
                node.setpId(Long.valueOf(node1.getId().toString()));
                node.setChecked(r.isCheck());
                if (isAdmin) {
                    node.setChkDisabled(false);
                } else {
                    node.setChkDisabled(!r.isUse());
                }

                node.setName(r.getName());
                if (r.isCheck()) {
                    ++maxCheckedNode;
                }
            }

            if (roleList.size() == maxCheckedNode) {
                node1.setChecked(true);
            }

            nodes.add(node1);
        }

        return (new Gson()).toJson(nodes);
    }

    @RequestMapping({"/unlock"})
    @ResponseBody
    @RequiresPermissions({"PIVAS_BTN_123"})
    public String unlockUser(Long userId, HttpServletRequest request) throws Exception {
        User u = this.userService.getUserID(userId);
        String modulesType = "SM_1";
        Integer subSysType = AmiLogConstant.BRANCH_SYSTEM.SM;

        try {
            if (u == null) {
                throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
            } else {
                this.userService.unlockUser(userId);
                this.addOperLog(modulesType, subSysType, this.getMessage("log.user.tip.userUnbindKey", new String[]{u.getUserId().toString(), u.getName()}), true);
                return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception var7) {
            this.addOperLog(modulesType, subSysType, this.getMessage("log.user.tip.userUnbindKeyLose", new String[]{userId.toString()}), false);
            throw var7;
        }
    }

    @RequestMapping({"/checkPWD"})
    @ResponseBody
    public String checkPWD(String password) throws Exception {
        User user1 = this.getCurrentUser();
        if (!this.userService.checkOldPassword(user1.getUserId(), user1.getAccount(), password)) {
            throw this.sdExceptionFactory.createSdException("00019", (Object[]) null, (Throwable) null);
        } else {
            return this.buildSuccessJsonMsg("common.op.success");
        }
    }

    @RequestMapping({"/updatePassword"})
    @ResponseBody
    public String updatePwd(String password, String oldPassword) throws Exception {
        User user1 = this.getCurrentUser();

        try {
            if (!this.userService.checkOldPassword(user1.getUserId(), user1.getAccount(), oldPassword)) {
                throw this.sdExceptionFactory.createSdException("00011", (Object[]) null, (Throwable) null);
            } else {
                boolean isPwdRepeat = this.userService.isPwdRepeatByUser(user1.getAccount(), password);
                if (isPwdRepeat) {
                    throw this.sdExceptionFactory.createSdException("00016", (Object[]) null, (Throwable) null);
                } else {
                    user1.setPassword(password);
                    this.changePasswordValidator.validate(user1);
                    this.userService.updatePassword(user1);
                    this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.userPasswordEditLose", new String[]{user1.getUserId().toString(), user1.getName()}), true);
                    return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
                }
            }
        } catch (Exception var5) {
            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.userPasswordEditLose", new String[]{user1.getUserId().toString(), user1.getName()}), false);
            throw var5;
        }
    }

    @RequestMapping(
            value = {"/updateUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    public String updateUser(User user1) throws Exception {
        User u = this.userService.getUserID(user1.getUserId());

        try {
            if (u == null) {
                throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
            } else {
                user1.setAccountType(1);
                this.userService.updateUser(user1);
                Map<String, Object> map = new HashMap();
                map.put("userId", u.getUserId());
                map.put("account", u.getAccount());
                if (user1.getUserType() != null && user1.getUserType() == 6) {
                    if ("Y".equals(user1.getGlAreaDel())) {
                        this.userService.delGlAreaByUser(map);
                    }

                    if (user1.getGlAreaAddN() != null && user1.getGlAreaAddN().length() > 0) {
                        String[] areaCodeN = user1.getGlAreaAddN().split(",");
                        String[] var8 = areaCodeN;
                        int var7 = areaCodeN.length;

                        for (int var6 = 0; var6 < var7; ++var6) {
                            String areaCode = var8[var6];
                            if (areaCode != null) {
                                map.put("deptCode", areaCode);
                                this.userService.addGlAreaByUser(map);
                            }
                        }
                    }
                } else {
                    this.userService.delGlAreaByUser(map);
                }

                this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.updateUser", new String[]{user1.getUserId().toString(), u.getName(), user1.getName()}), true);
                return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception var9) {
            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.updateUserLose", new String[]{user1.getUserId().toString(), user1.getName()}), false);
            throw var9;
        }
    }

    @RequestMapping(
            value = {"/updateCurrUser"},
            produces = {"application/json"}
    )
    @ResponseBody
    public String updateCurrUser(User user1) throws Exception {
        User u = this.userService.getUserID(this.getCurrentUser().getUserId());

        try {
            if (u == null) {
                throw this.sdExceptionFactory.createSdException("00001", (Object[]) null, (Throwable) null);
            } else {
                user1.setUserId(this.getCurrentUser().getUserId());
                this.userService.updateCurrUser(user1);
                this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.userInfoEdit", new String[]{user1.getUserId().toString(), u.getName()}), true);
                return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception var4) {
            this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.user.tip.userInfoEditLose", new String[]{user1.getUserId().toString()}), true);
            throw var4;
        }
    }

    @RequestMapping(
            value = {"/userDetail"},
            produces = {"application/json"}
    )
    @ResponseBody
    @RequiresPermissions({"PIVAS_MENU_117"})
    public String userDetail(Long userId) throws Exception {
        User userInfo = this.userService.getUserID(userId);
        return (new Gson()).toJson(userInfo);
    }

    @RequestMapping({"/importUser"})
    @ResponseBody
    public String importUser(MultipartFile file) throws IOException {
        StringBuffer errorLog = new StringBuffer();
        errorLog.append("导入异常日志如下：\n");
        String fileName = file.getOriginalFilename();

        try {
            boolean oldVersion = true;
            if (fileName.matches("^.+\\.(?i)(xls)$")) {
                oldVersion = true;
            } else {
                if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    this.addOperLog("SM_1", AmiLogConstant.BRANCH_SYSTEM.SM, this.getMessage("log.medicamnets.import.filetype", new String[]{fileName}), true);
                    return this.buildSuccessJsonMsg(errorLog.toString());
                }

                oldVersion = false;
            }

            Workbook workbook = null;
            if (oldVersion) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else {
                workbook = new XSSFWorkbook(file.getInputStream());
            }

            if (workbook != null) {
                Sheet sheet = ((Workbook) workbook).getSheetAt(0);
                Row row = null;
                String userAcount = "";
                String userName = "";
                String sex = "";
                User user = null;

                for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
                    row = sheet.getRow(i);
                    if (!StringUtils.isEmpty(row.getCell(0).toString())) {
                        userAcount = row.getCell(0).toString();
                        if (userAcount.contains(".")) {
                            userAcount = userAcount.substring(0, userAcount.lastIndexOf(46));
                        }

                        userName = row.getCell(2).toString();
                        sex = row.getCell(3).toString();
                        user = this.userService.getUserByAccount(userAcount);
                        if (user != null) {
                            user.setName(userName);
                            user.setGender("男".equals(sex) ? 1 : 0);

                            try {
                                this.updateUser(user);
                            } catch (Exception var15) {
                                var15.printStackTrace();
                            }
                        } else {
                            user = new User();
                            user.setAccount(userAcount);
                            user.setName(userName);
                            user.setPassword("123456");
                            user.setAccountType(1);
                            user.setGender("男".equals(sex) ? 1 : 0);
                            user.setUserType(1);

                            try {
                                this.addUser(user);
                            } catch (Exception var14) {
                                var14.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException var16) {
            this.addOperLog("OC_1", AmiLogConstant.BRANCH_SYSTEM.OC, this.getMessage("log.medicaments.import", new String[]{fileName}), false);
            throw var16;
        }

        return this.buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
    }

    @RequestMapping({"/exportUser"})
    @ResponseBody
    public String exportUser(User condition) {
        String[] exclusiveAccount = new String[]{SysConstant.SYSADMIN};
        List<User> userList = this.userService.getAllUser(exclusiveAccount);
        String saveDir = "";
        if (CollectionUtils.isNotEmpty(userList)) {
            try {
                saveDir = this.exportExcel(userList);
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        }

        return this.buildSuccessJsonMsg(saveDir);
    }

    public String exportExcel(List<User> userList) throws FileNotFoundException, IOException {
        String fileName = "药学人员信息.xls";
        String exportFilePath = MedicamentStockController.class.getResource("/").getPath().replaceAll("/classes", "") + "formatFile/药学人员信息.xls";
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(exportFilePath));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        sheet.setAutobreaks(true);
        sheet.protectSheet("");
        this.setRowValue(userList, sheet);
        String saveDir = this.getSaveDirPath() + File.separator + fileName;
        FileOutputStream fileout = null;

        try {
            fileout = new FileOutputStream(saveDir);
            wb.write(fileout);
        } catch (Exception var13) {
            throw var13;
        } finally {
            if (fileout != null) {
                fileout.flush();
                fileout.close();
            }

        }

        return fileName;
    }

    private void setRowValue(List<User> userList, HSSFSheet sheet) {
        User user = null;
        HSSFRow row = null;

        for (int i = 0; i < userList.size(); ++i) {
            user = (User) userList.get(i);
            row = sheet.createRow(i + 1);
            HSSFCell cell = null;
            row.createCell(0);
            cell = row.getCell(0);
            cell.setCellValue(user.getAccount());
            row.createCell(2);
            cell = row.getCell(2);
            cell.setCellValue(user.getName());
            row.createCell(3);
            cell = row.getCell(3);
            cell.setCellValue(user.getGender() == 1 ? "男" : "女");
        }

    }

    private String getSaveDirPath() {
        String saveDir = SdConstant.WEB_ROOT_PATH + File.separator + "export" + File.separator + this.getCurrentUser().getAccount() + File.separator;
        FileUtil.mkdirs(saveDir);
        return saveDir;
    }
}
