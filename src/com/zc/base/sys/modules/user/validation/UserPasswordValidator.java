package com.zc.base.sys.modules.user.validation;

import com.zc.base.common.validation.Validate;
import com.zc.base.sys.modules.user.entity.User;

import java.util.List;


public class UserPasswordValidator
        implements Validate<User> {
    private List<BaseUserPasswordRule> userPasswordRules;

    public void validate(User user) {
        for (BaseUserPasswordRule rule : this.userPasswordRules) {
            rule.validatePass(user);
        }
    }


    public List<BaseUserPasswordRule> getUserPasswordRules() {
        return this.userPasswordRules;
    }

    public void setUserPasswordRules(List<BaseUserPasswordRule> userPasswordRules) {
        this.userPasswordRules = userPasswordRules;
    }
}
