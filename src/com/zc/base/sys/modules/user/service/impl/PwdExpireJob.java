package com.zc.base.sys.modules.user.service.impl;

import com.zc.base.bla.common.util.I18nUtil;
import com.zc.base.common.util.MailUtil;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class PwdExpireJob
        extends QuartzJobBean
        implements StatefulJob {
    private static final Logger logger = LoggerFactory.getLogger(PwdExpireJob.class);

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        String isSendMail = ((SystemConfig) SystemConfigFacade.getMap().get("emailType")).getSys_value();
        if ("on".equalsIgnoreCase(isSendMail)) {
            String[] exclusiveAccount = {SysConstant.SYSADMIN};
            List<User> allUser = this.userService.getAllUser(exclusiveAccount);
            for (User user : allUser) {
                String[] pwdExpireStatus = this.userService.getPwdExpireStatus(user.getAccount());
                if ("notice_modify_pwd".equals(pwdExpireStatus[0])) {
                    String subject = I18nUtil.getString("MailPwdExpiredSubject");
                    String content =
                            MessageFormat.format(I18nUtil.getString("MailPwdExpiredContent"), new Object[]{pwdExpireStatus[1]});

                    List<String> address = new ArrayList();
                    address.add(user.getEmail());
                    try {
                        MailUtil.mail(address, content, subject);
                    } catch (Exception e) {
                        logger.error(">>> PwdExpireJob send password expired prompt mail to " + user.getEmail() +
                                " failed", e);
                    }
                }
            }
        } else {
            logger.info(">>> PwdExpireJob perform failed, because the is_send_mail switch is off in system config");
        }
    }
}
