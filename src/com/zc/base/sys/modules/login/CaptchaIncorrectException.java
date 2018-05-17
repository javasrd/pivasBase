package com.zc.base.sys.modules.login;

import org.apache.shiro.authc.AuthenticationException;


public class CaptchaIncorrectException
        extends AuthenticationException {
    private static final long serialVersionUID = 1731950859858021893L;

    public CaptchaIncorrectException() {
    }

    public CaptchaIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }


    public CaptchaIncorrectException(String message) {
        super(message);
    }


    public CaptchaIncorrectException(Throwable cause) {
        super(cause);
    }
}
