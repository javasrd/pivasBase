package com.zc.base.sys.modules.sysconfig.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.MailUtil;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;
import com.zc.base.sys.modules.sysconfig.service.SystemConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping({"/systemConfig"})
public class SystemConfigController
        extends SdBaseController {
    private static final Logger log = LoggerFactory.getLogger(SystemConfigController.class);


    private SystemConfigService systemConfigService;


    @Resource
    private SdExceptionFactory sdExceptionFactory;


    @RequestMapping({"/SecurityConfig"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    public String toSysConMain(Model model, String pageFlag) {
        try {
            if (!SystemConfigFacade.isLoaded()) {
                SystemConfigFacade.setMap(this.systemConfigService.getInitSystemConfig());
            }
            Map<String, SystemConfig> map = SystemConfigFacade.getMap();
            model.addAttribute("sysConMap", map);
            if ((pageFlag == null) || ("".equals(pageFlag))) {
                pageFlag = "1";
            }
            model.addAttribute("pageFlag", pageFlag);
            return "sys/systemconfig/sysConMain";
        } catch (Exception e) {
            log.error("SystemConfigController.toSysConMain", e);
        }
        return null;
    }


    @RequestMapping(value = {"/getAllSysCon"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    public String getAllSysCon() {
        try {
            List<SystemConfig> sysConList = this.systemConfigService.getAllSystemConfig();
            return new Gson().toJson(sysConList);

        } catch (Exception e) {
            log.error("SystemConfigController.getAllSysCon", e);
        }
        return null;
    }


    @RequestMapping(value = {"/addSystemConfig"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    @ResponseBody
    public void addSystemConfig(SystemConfig systemConfig)
            throws Exception {
        systemConfig = new SystemConfig();

        systemConfig.setSys_key("");
        systemConfig.setSys_value("");
        systemConfig.setType("");
        systemConfig.setDescription("");

        this.systemConfigService.addSystemConfig(systemConfig);
        buildSuccessJsonMsg("success");
    }


    @RequestMapping(value = {"/updateUserPolicy"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    @ResponseBody
    public String updateUserPolicySystemConfig(String error_count, String clear_time, String error_time, String max_online_count, String pwd_not_repeat_count, String user_pwd_indate, String pwd_expire_notice_days)
            throws Exception {
        try {
            Map<String, String> systemConfigMap = new HashMap();
            systemConfigMap.put("error_count", error_count);
            systemConfigMap.put("clear_time", clear_time);
            systemConfigMap.put("error_time", error_time);
            systemConfigMap.put("max_online_count", max_online_count);
            systemConfigMap.put("pwd_not_repeat_count", pwd_not_repeat_count);
            systemConfigMap.put("user_pwd_indate", user_pwd_indate);
            systemConfigMap.put("pwd_expire_notice_days", pwd_expire_notice_days);
            this.systemConfigService.updateSystemConfigs(systemConfigMap);

            SystemConfigFacade.setMap(this.systemConfigService.getInitSystemConfig());
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateUserInfoLockTactics", new String[0]),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateUserInfoLockTactics", new String[0]),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/updateJournalingPolicy"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    @ResponseBody
    public String updateJournalingPolicySystemConfig(String reportPath, String exportExcelLanguage)
            throws Exception {
        try {
            Map<String, String> systemConfigMap = new HashMap();
            systemConfigMap.put("reportFilePath", reportPath);
            systemConfigMap.put("exportExcelLanguage", exportExcelLanguage);
            this.systemConfigService.updateSystemConfigs(systemConfigMap);

            SystemConfigFacade.setMap(this.systemConfigService.getInitSystemConfig());

            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateJournalingParameter", new String[0]),
                    true);
            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateJournalingParameter", new String[0]),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/updateMail"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    @ResponseBody
    public String updateMailSystemConfig(String smtpServer, String smtpServerUserName, String smtpServerUserPassword, String sendFrom, String emailType)
            throws Exception {
        try {
            Map<String, String> systemConfigMap = new HashMap();
            systemConfigMap.put("smtpServer", smtpServer);
            systemConfigMap.put("smtpServerUserName", smtpServerUserName);
            systemConfigMap.put("smtpServerUserPassword", smtpServerUserPassword);
            systemConfigMap.put("emailType", emailType);
            systemConfigMap.put("sendFrom", sendFrom);
            this.systemConfigService.updateSystemConfigs(systemConfigMap);

            SystemConfigFacade.setMap(this.systemConfigService.getInitSystemConfig());
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateMailServerInfo", new String[]{smtpServer, smtpServerUserName}),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateMailServerInfo", new String[]{smtpServer, smtpServerUserName}),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/checkoutMail"}, produces = {"application/json"})
    @ResponseBody
    public String checkoutMail(String smtpServer, String smtpServerUserName, String smtpServerUserPassword, String novelEmail) {
        try {
            String title = "测试信息";
            String content = "这是在测试,邮件服务器配置时，自动发送的电子邮件";
            MailUtil.checkoutMail(smtpServer, smtpServerUserName, smtpServerUserPassword, novelEmail, title, content);
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.testeMailServerInfo", new String[]{smtpServer, smtpServerUserName}),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));

        } catch (Exception e) {
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.testeMailServerInfo", new String[]{smtpServer, smtpServerUserName}),
                    false);
            throw this.sdExceptionFactory.createSdException("00104",
                    null,
                    null);
        }
    }


    @Resource
    public void setSystemConfigService(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }


    @RequestMapping(value = {"/updateRouterConfig"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    @ResponseBody
    public String updateRouterSystemConfig(String router_flag, @RequestParam(value = "router_ip", required = false) String router_ip)
            throws Exception {
        try {
            Map<String, String> systemConfigMap = new HashMap();
            systemConfigMap.put("router_flag", router_flag);
            systemConfigMap.put("router_ip", router_ip);
            this.systemConfigService.updateSystemConfigs(systemConfigMap);

            SystemConfigFacade.setMap(this.systemConfigService.getInitSystemConfig());

            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateRouteCollocate", new String[0]),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateRouteCollocate", new String[0]),
                    false);
            throw e;
        }
    }


    @RequestMapping(value = {"/updateOtherConfig"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_114"})
    @ResponseBody
    public String updateOtherSystemConfig(String serviceUrl)
            throws Exception {
        try {
            Map<String, String> systemConfigMap = new HashMap();
            systemConfigMap.put("serviceUrl", serviceUrl);
            this.systemConfigService.updateSystemConfigs(systemConfigMap);

            SystemConfigFacade.setMap(this.systemConfigService.getInitSystemConfig());

            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateOtherConfig", new String[0]),
                    true);
            return buildSuccessJsonMsg(this.messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog("SM_3",
                    AmiLogConstant.BRANCH_SYSTEM.SM,
                    getMessage("log.system.tip.updateOtherConfig", new String[0]),
                    false);
            throw e;
        }
    }
}
