package com.zc.base.sys.modules.login;

import org.apache.shiro.authc.AuthenticationException;


public class CaptchaTimeoutException
        extends AuthenticationException {
    private static final long serialVersionUID = 1731950859858021893L;

    public CaptchaTimeoutException() {
    }

    public CaptchaTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }


    public CaptchaTimeoutException(String message) {
        super(message);
    }


    public CaptchaTimeoutException(Throwable cause) {
        super(cause);
    }
}
