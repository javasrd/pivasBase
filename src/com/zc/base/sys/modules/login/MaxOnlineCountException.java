package com.zc.base.sys.modules.login;

import org.apache.shiro.authc.AuthenticationException;


public class MaxOnlineCountException
        extends AuthenticationException {
    private static final long serialVersionUID = -5148530757111934317L;

    public MaxOnlineCountException() {
    }

    public MaxOnlineCountException(String message, Throwable cause) {
        super(message, cause);
    }


    public MaxOnlineCountException(String message) {
        super(message);
    }


    public MaxOnlineCountException(Throwable cause) {
        super(cause);
    }
}
