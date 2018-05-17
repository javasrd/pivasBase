package com.zc.base.sys.modules.login;

import com.zc.base.bla.common.util.DateUtil;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.sys.modules.login.entity.LoginInfo;
import com.zc.base.sys.modules.login.repository.LoginInfoDao;
import com.zc.base.sys.modules.user.entity.Reply;
import com.zc.base.sys.modules.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


public class CaptchaFormAuthentication
        extends FormAuthenticationFilter {
    public static final ThreadLocal<LoginInfo> LOGININFO = new ThreadLocal();


    private static Logger logger = LoggerFactory.getLogger(CaptchaFormAuthentication.class);


    private LoginInfoDao loginInfoDao;


    private String captchaParam = "captcha";


    private String proxParam = "proxFlag";


    private Boolean enableUserLock;


    private Integer failLockCount;


    private Integer userLockTime;


    protected String reLoginUrl;


    public void persistLoginInfo(LoginInfo loginInfo) {
        if (loginInfo != null) {
            Long infoId = loginInfo.getLoginInfoId();

            if (infoId == null) {
                this.loginInfoDao.addLoginInfo(loginInfo);

            } else {
                this.loginInfoDao.updateLoginInfo(loginInfo);
            }
        }
    }


    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String host = usernamePasswordToken.getHost();
        String username = usernamePasswordToken.getUsername();


        if ("com.zc.base.sys.modules.login.CaptchaTimeoutException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "captcha timeout!"));


        } else if ("com.zc.base.sys.modules.login.CaptchaIncorrectException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "captcha incorrect!"));


        } else if ("org.apache.shiro.authc.UnknownAccountException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "user doesn't exsits!"));


        } else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "username or password Incorrect!"));

            if (this.enableUserLock.booleanValue()) {
                long now = System.currentTimeMillis();


                LoginInfo loginInfo = (LoginInfo) LOGININFO.get();


                Integer errorCount = loginInfo.getErrorCount();
                errorCount = Integer.valueOf(errorCount.intValue() + 1);
                loginInfo.setErrorCount(errorCount);


                loginInfo.setLstErrLoginTime(new Date(now));


                if (errorCount.intValue() >= this.failLockCount.intValue()) {


                    loginInfo.setLocked(Integer.valueOf(1));


                    loginInfo.setLockTime(new Date(now));


                    loginInfo.setLockLostEffTime(new Date(now + this.userLockTime.intValue() * SdConstant.THOUSAND.intValue()));
                }

                persistLoginInfo(loginInfo);
            }


        } else if ("org.apache.shiro.authc.LockedAccountException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "user locked!"));
        } else if ("com.zc.base.sys.modules.login.MaxOnlineCountException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "exceed max online count!"));
        } else if ("com.zc.base.sys.modules.login.PasswordExpiredException".equals(e.getClass().getName())) {
            logger.info(getLoginFaildInfo(host, username, "Password is expired!"));
        } else {
            logger.info(getLoginFaildInfo(host, username, e.getMessage()), e);
        }

        return super.onLoginFailure(token, e, request, response);
    }


    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        createNewSession(req, resp);

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        if (this.enableUserLock.booleanValue()) {

            LoginInfo loginInfo = (LoginInfo) LOGININFO.get();

            loginInfo.setErrorCount(Integer.valueOf(0));

            loginInfo.setLstErrLoginTime(null);

            loginInfo.setLockTime(null);

            loginInfo.setLockLostEffTime(null);
            if (loginInfo.getFstSucLoginTime() == null) {

                loginInfo.setFstSucLoginTime(new Date());
            }


            persistLoginInfo(loginInfo);
        }


        UserService.ENTER_COUNT.addAndGet(1);


        String userAccount = usernamePasswordToken.getUsername();
        String ipAdd = usernamePasswordToken.getHost();
        String loginDate = DateUtil.getYYYYMMDDHHMMSSFromDate(new Date());
        HttpSession session = req.getSession();

        UserService.MAP.put(userAccount, session);
        Reply reply = new Reply(userAccount, ipAdd, loginDate);

        session.setAttribute("reply_Info", reply);
        session.setAttribute("current_user", userAccount);


        logger.info(userAccount + " successfully login !" + "|request host:" + ipAdd);
        return super.onLoginSuccess(token, subject, request, response);
    }


    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
            throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl());
    }


    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha);
    }


    protected String getProxFlag(ServletRequest request) {
        return WebUtils.getCleanParam(request, getProxParam());
    }


    public String getProxParam() {
        return this.proxParam;
    }


    private static String getLoginFaildInfo(String host, String username, String message) {
        return username + " login failed ! |request host:" + host + "|msg:" + (message == null ? "" : message);
    }


    private static void createNewSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession oldSession = request.getSession();


        Map<String, Object> oldSessionAttrMap = new HashMap();
        Enumeration attributeNames = oldSession.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String key = (String) attributeNames.nextElement();
            Object value = oldSession.getAttribute(key);
            oldSessionAttrMap.put(key, value);
        }


        oldSession.invalidate();


        HttpSession newSession = request.getSession();


        Set<String> keys = oldSessionAttrMap.keySet();
        for (String key : keys) {
            newSession.setAttribute(key, oldSessionAttrMap.get(key));
        }


        Cookie cookie = new Cookie("JSESSIONID", newSession.getId());
        cookie.setSecure(false);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }


    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            }


            if (logger.isTraceEnabled()) {
                logger.trace("Login page view.");
            }

            return true;
        }


        if (logger.isTraceEnabled()) {
            logger.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" +
                    getLoginUrl() + "]");
        }
        WebUtils.issueRedirect(request, response, getReLoginUrl());
        return false;
    }


    public String getCaptchaParam() {
        return this.captchaParam;
    }


    protected String getPassword(ServletRequest request) {
        return request.getParameter(getPasswordParam());
    }


    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
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

    public Integer getFailLockCount() {
        return this.failLockCount;
    }

    public void setFailLockCount(Integer failLockCount) {
        this.failLockCount = failLockCount;
    }

    public Integer getUserLockTime() {
        return this.userLockTime;
    }

    public void setUserLockTime(Integer userLockTime) {
        this.userLockTime = userLockTime;
    }

    public String getReLoginUrl() {
        return this.reLoginUrl;
    }

    public void setReLoginUrl(String reLoginUrl) {
        this.reLoginUrl = reLoginUrl;
    }
}
