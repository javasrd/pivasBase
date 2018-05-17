package com.zc.base.sys.modules.login;

import org.apache.shiro.authc.AuthenticationException;


public class NonCloudAccessException
        extends AuthenticationException {
    private static final long serialVersionUID = -203195871099120405L;

    public NonCloudAccessException() {
    }

    public NonCloudAccessException(String message, Throwable cause) {
        super(message, cause);
    }


    public NonCloudAccessException(String message) {
        super(message);
    }


    public NonCloudAccessException(Throwable cause) {
        super(cause);
    }
}
