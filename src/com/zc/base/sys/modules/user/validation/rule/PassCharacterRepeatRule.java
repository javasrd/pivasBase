package com.zc.base.sys.modules.user.validation.rule;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.validation.BaseUserPasswordRule;

import java.util.HashMap;
import java.util.Map;


public class PassCharacterRepeatRule
        extends BaseUserPasswordRule {
    private Integer maxCharRepeat;

    public void validatePass(User user) {
        String password = user.getPassword();

        if (password != null) {

            Map<Character, Integer> countMap = new HashMap();
            char character;
            for (int i = 0; i < password.length(); i++) {
                character = password.charAt(i);
                Integer count = (Integer) countMap.get(Character.valueOf(character));
                if (count == null) {
                    count = Integer.valueOf(0);
                    countMap.put(Character.valueOf(character), count);
                }
                count = Integer.valueOf(count.intValue() + 1);
                countMap.put(Character.valueOf(character), count);
            }


            for (Integer count : countMap.values()) {
                if (count.intValue() > this.maxCharRepeat.intValue()) {
                    throw this.sdExceptionFactory.createSdException("00010",
                            new Object[]{this.maxCharRepeat},
                            null);
                }
            }
        }
    }


    public Integer getMaxCharRepeat() {
        return this.maxCharRepeat;
    }

    public void setMaxCharRepeat(Integer maxCharRepeat) {
        this.maxCharRepeat = maxCharRepeat;
    }
}
