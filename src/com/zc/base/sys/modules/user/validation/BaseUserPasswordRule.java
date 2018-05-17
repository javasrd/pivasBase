package com.zc.base.sys.modules.user.validation;

import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.sys.modules.user.entity.User;


public abstract class BaseUserPasswordRule {
    protected SdExceptionFactory sdExceptionFactory;

    public abstract void validatePass(User paramUser);

    public SdExceptionFactory getSdExceptionFactory() {
        return this.sdExceptionFactory;
    }

    public void setSdExceptionFactory(SdExceptionFactory sdExceptionFactory) {
        this.sdExceptionFactory = sdExceptionFactory;
    }
}
