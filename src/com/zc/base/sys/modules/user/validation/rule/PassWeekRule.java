package com.zc.base.sys.modules.user.validation.rule;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.validation.BaseUserPasswordRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PassWeekRule extends BaseUserPasswordRule {
    private static final Logger logger = LoggerFactory.getLogger(PassWeekRule.class);
    private List<String> weekPasswords;

    public PassWeekRule() {
    }

    public void init() {
        BufferedReader br = null;
        this.weekPasswords = new ArrayList();

        try {
            br = new BufferedReader(new InputStreamReader(PassWeekRule.class.getResourceAsStream("/weekPassowrds.txt")));
            String temp = null;

            while ((temp = br.readLine()) != null) {
                this.weekPasswords.add(temp.trim());
            }
        } catch (IOException var11) {
            logger.error("read week passwords from weekPassowrds.txt error!", var11);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var10) {
                    logger.error(var10.getMessage(), var10);
                }

                br = null;
            }

        }

    }

    public void validatePass(User user) {
        String password = user.getPassword();
        if (password != null && this.weekPasswords.contains(password)) {
            throw this.sdExceptionFactory.createSdException("00012", (Object[]) null, (Throwable) null);
        }
    }

    public List<String> getWeekPasswords() {
        return this.weekPasswords;
    }

    public void setWeekPasswords(List<String> weekPasswords) {
        this.weekPasswords = weekPasswords;
    }
}