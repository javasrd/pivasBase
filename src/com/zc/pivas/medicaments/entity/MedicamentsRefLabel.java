package com.zc.pivas.medicaments.entity;

import java.io.Serializable;

/**
 * 药品与药品标签关联关系
 *
 * @author Ray
 * @version 1.0
 */
public class MedicamentsRefLabel implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 药品ID
     */
    private Long medicamentsId;

    /**
     * 药品ID
     */
    private String medicamentsName;

    /**
     * 标签ID
     */
    private Long labelId;

    /**
     * 标签Name
     */
    private String labelName;

    /**
     * 是否可用
     */
    private int isActive;

    public Long getMedicamentsId() {
        return medicamentsId;
    }

    public void setMedicamentsId(Long medicamentsId) {
        this.medicamentsId = medicamentsId;
    }

    public String getMedicamentsName() {
        return medicamentsName;
    }

    public void setMedicamentsName(String medicamentsName) {
        this.medicamentsName = medicamentsName;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

}
