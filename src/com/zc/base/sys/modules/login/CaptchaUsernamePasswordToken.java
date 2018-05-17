package com.zc.base.sys.modules.login;

import org.apache.shiro.authc.UsernamePasswordToken;


public class CaptchaUsernamePasswordToken
        extends UsernamePasswordToken {
    private static final long serialVersionUID = 1190651606743676837L;
    private String captcha;

    public CaptchaUsernamePasswordToken() {
    }

    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);

        this.captcha = captcha;
    }


    public String getCaptcha() {
        return this.captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
