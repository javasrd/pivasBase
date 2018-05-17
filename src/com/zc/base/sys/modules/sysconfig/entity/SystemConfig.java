package com.zc.base.sys.modules.sysconfig.entity;

import java.io.Serializable;


public class SystemConfig
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sys_key;
    private String sys_value;
    private String type;
    private String description;

    public String getSys_key() {
        return this.sys_key;
    }

    public void setSys_key(String sys_key) {
        this.sys_key = sys_key;
    }

    public String getSys_value() {
        return this.sys_value;
    }

    public void setSys_value(String sys_value) {
        this.sys_value = sys_value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toString() {
        return
                "SystemConfig [sys_key=" + this.sys_key + ", sys_value=" + this.sys_value + ", type=" + this.type + ", description=" + this.description + "]";
    }
}
