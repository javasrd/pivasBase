package com.zc.base.sys.modules.user.validation.rule;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.validation.BaseUserPasswordRule;


public class PassLengthRule
        extends BaseUserPasswordRule {
    private Integer minLength;
    private Integer maxLength;

    public void validatePass(User user) {
        String password = user.getPassword();


        if ((password != null) && ((password.length() < this.minLength.intValue()) || (password.length() > this.maxLength.intValue()))) {
            throw this.sdExceptionFactory.createSdException("00007",
                    new Object[]{this.minLength, this.maxLength},
                    null);
        }
    }


    public Integer getMinLength() {
        return this.minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}
