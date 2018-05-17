package com.zc.base.bla.modules.menu.entity;

import java.io.Serializable;


public class RoleRefPrivilege implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long roleId;
    private Long privilegeId;

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return this.privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RoleRefPrivilege [roleId=");
        builder.append(this.roleId);
        builder.append(", privilegeId=");
        builder.append(this.privilegeId);
        builder.append("]");
        return builder.toString();
    }
}
