package com.zc.base.sys.modules.login;

import org.apache.shiro.authc.AuthenticationException;


public class PasswordExpiredException
        extends AuthenticationException {
    private static final long serialVersionUID = -442759099818396007L;

    public PasswordExpiredException() {
    }

    public PasswordExpiredException(String message, Throwable cause) {
        super(message, cause);
    }


    public PasswordExpiredException(String message) {
        super(message);
    }


    public PasswordExpiredException(Throwable cause) {
        super(cause);
    }
}
