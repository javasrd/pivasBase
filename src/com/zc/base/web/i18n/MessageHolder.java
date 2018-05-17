package com.zc.base.web.i18n;

import com.zc.base.web.Servlets;
import org.springframework.context.MessageSource;


public class MessageHolder {
    private MessageSource messageSource;

    public String getMessage(String code) {
        return this.messageSource.getMessage(code, null, Servlets.getCurrentRequestLocal());
    }


    public String getMessage(String code, Object[] args) {
        return this.messageSource.getMessage(code, args, Servlets.getCurrentRequestLocal());
    }


    public String getMessage(String code, Object[] args, String defaultMessage) {
        return this.messageSource.getMessage(code, args, defaultMessage, Servlets.getCurrentRequestLocal());
    }

    public MessageSource getMessageSource() {
        return this.messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
