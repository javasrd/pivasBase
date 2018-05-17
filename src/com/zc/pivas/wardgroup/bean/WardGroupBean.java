package com.zc.pivas.wardgroup.bean;

import java.io.Serializable;

/**
 * 病区分组实体类
 *
 * @author kunkka
 * @version 1.0
 */
public class WardGroupBean implements Serializable {

    private static final long serialVersionUID = 7016866322705597815L;

    private String id;

    private String deptcode;

    private String deptname;

    private String parentid;

    private String enabled;

    private String state;

    private String levelnum;

    private String ordernum;

    private String wardgroup;

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 deptcode
     */
    public String getDeptcode() {
        return deptcode;
    }

    /**
     */
    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    /**
     * @return 返回 deptname
     */
    public String getDeptname() {
        return deptname;
    }

    /**
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    /**
     * @return 返回 parentid
     */
    public String getParentid() {
        return parentid;
    }

    /**
     */
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    /**
     * @return 返回 enabled
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return 返回 state
     */
    public String getState() {
        return state;
    }

    /**
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return 返回 levelnum
     */
    public String getLevelnum() {
        return levelnum;
    }

    /**
     */
    public void setLevelnum(String levelnum) {
        this.levelnum = levelnum;
    }

    /**
     * @return 返回 ordernum
     */
    public String getOrdernum() {
        return ordernum;
    }

    /**
     */
    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    /**
     * @return 返回 wardgroup
     */
    public String getWardgroup() {
        return wardgroup;
    }

    /**
     */
    public void setWardgroup(String wardgroup) {
        this.wardgroup = wardgroup;
    }

}
