package com.zc.base.sys.modules.user.validation.rule;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.validation.BaseUserPasswordRule;


public class UsernamePassNotSameRule
        extends BaseUserPasswordRule {
    public void validatePass(User user) {
        String username = user.getAccount();
        String password = user.getPassword();

        if ((username != null) && (password != null)) {

            String reversedUsername = new StringBuilder(username).reverse().toString();
            if ((username.equals(password)) || (reversedUsername.equals(password))) {
                throw this.sdExceptionFactory.createSdException("00006",
                        null,
                        null);
            }
        }
    }
}
