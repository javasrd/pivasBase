package com.zc.base.sys.modules.user.validation.rule;

import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.validation.BaseUserPasswordRule;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PassCharacterTypeRule
        extends BaseUserPasswordRule {
    private List<String> characterTypeRegexs;
    private Integer minCharType;

    public void validatePass(User user) {
        String password = user.getPassword();
        Boolean match = Boolean.valueOf(true);


        if (password != null) {
            Pattern blankPattern = Pattern.compile("\\s");
            Matcher blankMatcher = blankPattern.matcher(password);


            if (blankMatcher.find()) {
                match = Boolean.valueOf(false);

            } else {
                Integer count = Integer.valueOf(0);

                for (String characterTypeRegex : this.characterTypeRegexs) {

                    Pattern pattern = Pattern.compile(characterTypeRegex);
                    Matcher matcher = pattern.matcher(password);
                    if (matcher.find()) {
                        count = Integer.valueOf(count.intValue() + 1);
                    }
                }

                if (count.intValue() < this.minCharType.intValue()) {
                    match = Boolean.valueOf(false);
                }
            }
        }


        if (!match.booleanValue()) {
            throw this.sdExceptionFactory.createSdException("00008",
                    new Object[]{this.minCharType},
                    null);
        }
    }


    public List<String> getCharacterTypeRegexs() {
        return this.characterTypeRegexs;
    }

    public void setCharacterTypeRegexs(List<String> characterTypeRegexs) {
        this.characterTypeRegexs = characterTypeRegexs;
    }

    public Integer getMinCharType() {
        return this.minCharType;
    }

    public void setMinCharType(Integer minCharType) {
        this.minCharType = minCharType;
    }
}
