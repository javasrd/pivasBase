package com.zc.pivas.inpatientarea.bean;

import java.io.Serializable;
import java.util.Arrays;


/**
 * 病区实体类
 *
 * @author Ray
 * @version 1.0
 */
public class InpatientAreaBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String gid;

    /**
     * 病区编码
     */
    private String deptCode;

    private String[] deptCodes;

    /**
     * 病区名称
     */
    private String deptName;

    private String[] deptNames;

    /**
     * 预留字段0
     */
    private String reserve0;

    /**
     * 预留字段1
     */
    private String reserve1;

    /**
     * 预留字段2
     */
    private String reserve2;

    /**
     * 是否启用，0:不启用,1启用
     */
    private String enabled;

    /**
     * 病区别名
     */
    private String deptAliasName;

    private Long glUserId;

    public String getDeptAliasName() {
        return deptAliasName;
    }

    public void setDeptAliasName(String deptAliasName) {
        this.deptAliasName = deptAliasName;
    }

    public String[] getDeptCodes() {
        return deptCodes;
    }

    public void setDeptCodes(String[] deptCodes) {
        this.deptCodes = deptCodes;
    }

    public String[] getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String[] deptNames) {
        this.deptNames = deptNames;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReserve0() {
        return reserve0;
    }

    public void setReserve0(String reserve0) {
        this.reserve0 = reserve0;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Long getGlUserId() {
        return glUserId;
    }

    public void setGlUserId(Long glUserId) {
        this.glUserId = glUserId;
    }

    @Override
    public String toString() {
        return "InpatientAreaBean [gid=" + gid + ", deptCode=" + deptCode + ", deptCodes=" + Arrays.toString(deptCodes) + ", deptName=" + deptName + ", deptNames=" + Arrays.toString(deptNames) + ", reserve0=" + reserve0 + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + ", enabled=" + enabled + "]";
    }

}
