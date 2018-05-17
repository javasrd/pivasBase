package com.zc.pivas.configfee.bean;

import java.io.Serializable;

/**
 * 配置费类别实体类
 *
 * @author kunkka
 * @version 1.0
 */
public class ConfigFeeTypeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键 与配置费/材料费中的配置费的类别 关联
     */
    private String gid;

    /**
     * 配置费名称
     */
    private String typeDesc;

    private String[] typeDescs;

    /**
     * 备注
     */
    private String remark;

    private String[] remarks;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String[] getTypeDescs() {
        return typeDescs;
    }

    public void setTypeDescs(String[] typeDescs) {
        this.typeDescs = typeDescs;
    }

    public String[] getRemarks() {
        return remarks;
    }

    public void setRemarks(String[] remarks) {
        this.remarks = remarks;
    }

}
