package com.zc.base.sys.modules.system.entity;

import java.io.Serializable;


public class SysDict implements Serializable {
    private static final long serialVersionUID = -2984538640889233136L;
    private Long id;
    private String category;
    private String desc;
    private String code;
    private String content;
    private String lang;
    private Integer sort;

    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getCategory() {
        return this.category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public String getDesc() {
        return this.desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getCode() {
        return this.code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getContent() {
        return this.content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getLang() {
        return this.lang;
    }


    public void setLang(String lang) {
        this.lang = lang;
    }


    public Integer getSort() {
        return this.sort;
    }


    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public String toString() {
        return
                "BLADict [id=" + this.id + ", category=" + this.category + ", desc=" + this.desc + ", code=" + this.code + ", content=" + this.content + ", lang=" + this.lang + ", sort=" + this.sort + "]";
    }
}
