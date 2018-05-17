package com.zc.schedule.system.config.entity;

import java.io.Serializable;

/**
 * 数据字典实体类
 *
 * @author Justin
 * @version v1.0
 */
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据字典id
     */
    Long dictId;

    /**
     * 分类
     */
    String category;

    /**
     * 描述
     */
    String description;

    /**
     * 代码
     */
    String code;

    /**
     * 内容
     */
    String content;

    /**
     * 语言
     */
    String lang;

    /**
     * 序列
     */
    Integer sort;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


}
