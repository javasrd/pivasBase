package com.zc.base.common.exception;

import com.zc.base.web.i18n.MessageHolder;


public class SdExceptionFactory {
    private MessageHolder messageHolder;
    private String i18nMsgPrefix;
    private String i18nMsgSuffix;
    private String i18nDescPrefix;
    private String i18nDescSuffix;
    private String i18nSolPrefix;
    private String i18nSolSuffix;

    public SdException createSdException(String code, Object[] args, Throwable cause) {
        SdException sdException = null;

        if ((code != null) && (code.trim().length() > 0)) {
            String message = this.messageHolder.getMessage(this.i18nMsgPrefix + code + this.i18nMsgSuffix, args, "");

            sdException = new SdException(message, cause);

            sdException.setCode(code);
            sdException.setSolutionDesc(this.messageHolder.getMessage(this.i18nSolPrefix + code + this.i18nSolSuffix, args, ""));
            sdException.setDescription(this.messageHolder.getMessage(this.i18nDescPrefix + code + this.i18nDescSuffix, args, ""));
        } else {
            sdException = new SdException(cause);
        }

        return sdException;
    }

    public String getErrMsg(String code) {
        String message = this.messageHolder.getMessage(this.i18nMsgPrefix + code + this.i18nMsgSuffix);
        return message;
    }

    public String getErrSol(String code) {
        String message = this.messageHolder.getMessage(this.i18nSolPrefix + code + this.i18nSolSuffix);
        return message;
    }

    public String getErrDesc(String code) {
        String message = this.messageHolder.getMessage(this.i18nDescPrefix + code + this.i18nDescSuffix);
        return message;
    }

    public MessageHolder getMessageHolder() {
        return this.messageHolder;
    }

    public void setMessageHolder(MessageHolder messageHolder) {
        this.messageHolder = messageHolder;
    }

    public String getI18nMsgPrefix() {
        return this.i18nMsgPrefix;
    }

    public void setI18nMsgPrefix(String i18nMsgPrefix) {
        this.i18nMsgPrefix = i18nMsgPrefix;
    }

    public String getI18nMsgSuffix() {
        return this.i18nMsgSuffix;
    }

    public void setI18nMsgSuffix(String i18nMsgSuffix) {
        this.i18nMsgSuffix = i18nMsgSuffix;
    }

    public String getI18nSolPrefix() {
        return this.i18nSolPrefix;
    }

    public void setI18nSolPrefix(String i18nSolPrefix) {
        this.i18nSolPrefix = i18nSolPrefix;
    }

    public String getI18nSolSuffix() {
        return this.i18nSolSuffix;
    }

    public void setI18nSolSuffix(String i18nSolSuffix) {
        this.i18nSolSuffix = i18nSolSuffix;
    }

    public String getI18nDescPrefix() {
        return this.i18nDescPrefix;
    }

    public void setI18nDescPrefix(String i18nDescPrefix) {
        this.i18nDescPrefix = i18nDescPrefix;
    }

    public String getI18nDescSuffix() {
        return this.i18nDescSuffix;
    }

    public void setI18nDescSuffix(String i18nDescSuffix) {
        this.i18nDescSuffix = i18nDescSuffix;
    }
}
