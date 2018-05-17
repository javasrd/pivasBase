package com.zc.base.sys.modules.login;

import com.zc.base.common.constant.SdConstant;
import com.zc.base.sys.modules.login.entity.LoginInfo;
import com.zc.base.sys.modules.login.repository.LoginInfoDao;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.service.SystemConfigService;
import com.zc.base.sys.modules.user.entity.Privilege;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.PriService;
import com.zc.base.sys.modules.user.service.UserGroupService;
import com.zc.base.sys.modules.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ShiroDbRealm
        extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);


    private PriService priService;


    private UserService userService;


    private LoginInfoDao loginInfoDao;


    private SystemConfigService systemConfigService;


    @Resource
    private UserGroupService userGroupService;


    private Integer captchaTimeout;


    private boolean enableCaptcha;


    private Boolean enableUserLock;


    private Integer failCountInterval;


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
        if (this.enableCaptcha) {
            checkCaptcha(authcToken.getCaptcha());
        }
        User user = this.userService.getUserByAccount(authcToken.getUsername());
        if (user != null) {
            //检测最大在线用户数
            checkMaxOnlineCount();
            //检测密码是否过期
            String pwdExpireStatus = this.userService.getPwdExpireStatus(authcToken.getUsername())[0];
            if (pwdExpireStatus.equals("pwd_expired")) {
                throw new PasswordExpiredException("Password is Expired");
            }
            if (this.enableUserLock.booleanValue()) {
                readUserLoginInfo(user);
            }
            user.setCreateIds(this.userGroupService.queryAllGroupMemberId(user.getUserId(), 1));

            AuthenticationInfo authenticationInfo = createAuthenticationInfo(user);
            return authenticationInfo;
        }
        throw new UnknownAccountException(authcToken.getUsername());
    }

    private void checkMaxOnlineCount() {
        Map<String, SystemConfig> map = null;
        try {
            map = this.systemConfigService.getInitSystemConfig();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Integer maxOnlineCount = Integer.valueOf(0);
        if (map != null && map.get("max_online_count") != null) {
            maxOnlineCount = Integer.valueOf(map.get("max_online_count").getSys_value());
        }
        if (maxOnlineCount.intValue() > 0 && UserService.ENTER_COUNT.get() >= maxOnlineCount.intValue()) {
            throw new MaxOnlineCountException("Exceed Max Online Count");
        }
    }


    protected void checkCaptcha(String fromCaptcha) {
        Session session = SecurityUtils.getSubject().getSession();

        Date captchaCodeGenTime = (Date) session.getAttribute("CAPTCHAE_CODE_GEN_TIME");
        session.removeAttribute("CAPTCHAE_CODE_GEN_TIME");


        String sessionCaptchaCode = (String) session.getAttribute("CAPTCHA_CODE_KEY");
        session.removeAttribute("CAPTCHA_CODE_KEY");


        if ((captchaCodeGenTime == null) ||
                (System.currentTimeMillis() - captchaCodeGenTime.getTime() > this.captchaTimeout.intValue() * SdConstant.THOUSAND.intValue())) {
            throw new CaptchaTimeoutException("captcha timeout!");
        }


        if (!sessionCaptchaCode.equalsIgnoreCase(fromCaptcha)) {
            throw new CaptchaIncorrectException("captcha incorrect!");
        }
    }


    protected AuthenticationInfo createAuthenticationInfo(User user) {
        ByteSource salt = ByteSource.Util.bytes(ByteSource.Util.bytes(user.getAccount()));
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        return info;
    }


    protected void readUserLoginInfo(User user) {
        //获取登录信息
        LoginInfo loginInfo = this.loginInfoDao.getLoginInfoByUserId(user.getUserId());
        LoginInfo processedLoginInfo = processLoginInfoBeforeAuthentication(loginInfo, user);
        CaptchaFormAuthentication.LOGININFO.set(processedLoginInfo);
    }


    protected LoginInfo processLoginInfoBeforeAuthentication(LoginInfo loginInfo, User user) {
        LoginInfo processedLoginInfo = null;
        if (loginInfo == null) {
            processedLoginInfo = new LoginInfo();
            processedLoginInfo.setAccount(user.getAccount());
            processedLoginInfo.setErrorCount(Integer.valueOf(0));
            processedLoginInfo.setLocked(Integer.valueOf(0));
            processedLoginInfo.setUserId(user.getUserId());
        } else {
            processedLoginInfo = loginInfo;
            if (processedLoginInfo.getLockLostEffTime() != null && System.currentTimeMillis() > processedLoginInfo.getLockLostEffTime().getTime()) {
                processedLoginInfo.setLocked(Integer.valueOf(0));
                processedLoginInfo.setErrorCount(Integer.valueOf(0));
                processedLoginInfo.setLockTime(null);
                processedLoginInfo.setLstErrLoginTime(null);
                processedLoginInfo.setLockLostEffTime(null);
            }
            if (processedLoginInfo.getLocked().intValue() == 1) {
                throw new LockedAccountException("user locked!");
            }
            Date lstErrLoginTime = processedLoginInfo.getLstErrLoginTime();
            if (lstErrLoginTime != null) {
                if (System.currentTimeMillis() - lstErrLoginTime.getTime() > this.failCountInterval.intValue() * SdConstant.THOUSAND.intValue()) {
                    processedLoginInfo.setErrorCount(Integer.valueOf(0));
                    processedLoginInfo.setLstErrLoginTime(null);
                }
            }
        }
        return processedLoginInfo;
    }

    public void onLogout(PrincipalCollection principals) {
    }


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();


        List<Privilege> privilegeists = this.priService.getPrivilegeListByUser(user);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


        for (Privilege privilege : privilegeists) {
            info.addStringPermission(privilege.getPrivilegeCode());
        }

        return info;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Integer getCaptchaTimeout() {
        return this.captchaTimeout;
    }

    public void setCaptchaTimeout(Integer captchaTimeout) {
        this.captchaTimeout = captchaTimeout;
    }

    public boolean isEnableCaptcha() {
        return this.enableCaptcha;
    }

    public void setEnableCaptcha(boolean enableCaptcha) {
        this.enableCaptcha = enableCaptcha;
    }

    public Boolean getEnableUserLock() {
        return this.enableUserLock;
    }

    public void setEnableUserLock(Boolean enableUserLock) {
        this.enableUserLock = enableUserLock;
    }

    public LoginInfoDao getLoginInfoDao() {
        return this.loginInfoDao;
    }

    public void setLoginInfoDao(LoginInfoDao loginInfoDao) {
        this.loginInfoDao = loginInfoDao;
    }

    public PriService getPriService() {
        return this.priService;
    }

    @Resource(name = "priService")
    public void setPriService(PriService priService) {
        this.priService = priService;
    }

    public Integer getFailCountInterval() {
        return this.failCountInterval;
    }

    public void setFailCountInterval(Integer failCountInterval) {
        this.failCountInterval = failCountInterval;
    }

    public void setSystemConfigService(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }
}
