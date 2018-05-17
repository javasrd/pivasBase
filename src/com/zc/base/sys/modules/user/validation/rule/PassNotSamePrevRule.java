package com.zc.base.sys.modules.user.validation.rule;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.repository.UserDao;
import com.zc.base.sys.modules.user.validation.BaseUserPasswordRule;
import org.apache.shiro.crypto.hash.SimpleHash;


public class PassNotSamePrevRule
        extends BaseUserPasswordRule {
    private UserDao userDao;
    private String hashAlgorithm;
    private Integer hashIterations;

    public void validatePass(User user) {
        String account = user.getAccount();
        String password = user.getPassword();


        if ((account != null) && (password != null)) {
            User prevUser = this.userDao.getUserByAccount(account);


            if (prevUser != null) {
                SimpleHash simpleHash =
                        new SimpleHash(this.hashAlgorithm, user.getPassword(), user.getAccount(), this.hashIterations.intValue());
                String newPass = simpleHash.toHex();
                if (newPass.equals(prevUser.getPassword())) {
                    throw this.sdExceptionFactory.createSdException("00009",
                            null,
                            null);
                }
            }
        }
    }


    public UserDao getUserDao() {
        return this.userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public Integer getHashIterations() {
        return this.hashIterations;
    }

    public void setHashIterations(Integer hashIterations) {
        this.hashIterations = hashIterations;
    }
}
