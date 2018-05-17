package com.zc.base.sys.modules.language.entity;


public class Language {
    private Long languageId;


    private String code;


    private String description;


    public Long getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
