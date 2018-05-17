package com.zc.pivas.medicamentscategory.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 药品分类实体
 *
 * @author kunkka
 * @version 1.0
 */
public class MedicCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;


    private String[] categoryNames;

    /**
     * 分类瓶签编码
     */
    private String categoryCode;

    /**
     * 优先级
     */
    private String categoryPriority;

    /**
     * 是否难配药
     */
    private Integer categoryIsHard;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryPriority() {
        return categoryPriority;
    }

    public void setCategoryPriority(String categoryPriority) {
        this.categoryPriority = categoryPriority;
    }

    public String[] getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String[] categoryNames) {
        this.categoryNames = categoryNames;
    }

    public Integer getCategoryIsHard() {
        return categoryIsHard;
    }

    public void setCategoryIsHard(Integer categoryIsHard) {
        this.categoryIsHard = categoryIsHard;
    }

    @Override
    public String toString() {
        return "MedicCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryNames="
                + Arrays.toString(categoryNames) + ", categoryCode=" + categoryCode + ", categoryPriority="
                + categoryPriority + ",categoryIsHard=" + categoryIsHard + "]";
    }


}
