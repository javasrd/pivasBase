package com.zc.base.sys.modules.user.entity;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.*;


public class User
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String account;
    private String[] accounts;
    private String tempAccount;
    private String password;
    private String name;
    private String[] names;
    private Integer gender;
    private String sickRoomIds;
    private String telephone;
    private String[] telephones;
    private String email;
    private Date createTime;
    private String description;
    private Long unitId;
    private String unitIds;
    private String unitName;
    private String departmentName;
    private List<Role> roles;
    private String sortname;
    private String sortorder;
    private Integer resetPwd;
    private String locked;
    private Long creator;
    private Integer accountType;
    private String userGroupNames;
    private List<UserGroup> userGroup = new ArrayList();


    private Long[] createIds;


    private Integer userType;


    private List<Map<String, Object>> glAreaList;

    private String glAreaAddN;

    private String glAreaDel;


    public Long[] getCreateIds() {
        return this.createIds;
    }

    public void setCreateIds(Long[] createIds) {
        this.createIds = createIds;
    }

    public String getUserGroupNames() {
        return this.userGroupNames;
    }

    public void setUserGroupNames(String userGroupNames) {
        this.userGroupNames = userGroupNames;
    }


    public String toString() {
        return


                "User [userId=" + this.userId + ", account=" + this.account + ", accounts=" + Arrays.toString(this.accounts) + ", tempAccount=" + this.tempAccount + ", password=" + this.password + ", name=" + this.name + ", names=" + Arrays.toString(this.names) + ", gender=" + this.gender + ", sickRoomIds=" + this.sickRoomIds + ", telephone=" + this.telephone + ", telephones=" + Arrays.toString(this.telephones) + ", email=" + this.email + ", createTime=" + this.createTime + ", description=" + this.description + ", unitId=" + this.unitId + ", unitIds=" + this.unitIds + ", unitName=" + this.unitName + ", departmentName=" + this.departmentName + ", roles=" + this.roles + ", sortname=" + this.sortname + ", sortorder=" + this.sortorder + ", resetPwd=" + this.resetPwd + ", locked=" + this.locked + ", creator=" + this.creator + ", accountType=" + this.accountType + ", userGroupNames=" + this.userGroupNames + ", userGroup=" + this.userGroup + ", createIds=" + Arrays.toString(this.createIds) + "]";
    }

    public String[] getAccounts() {
        return this.accounts;
    }

    public void setAccounts(String[] accounts) {
        this.accounts = accounts;
    }

    public String[] getNames() {
        return this.names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String[] getTelephones() {
        return this.telephones;
    }

    public void setTelephones(String[] telephones) {
        this.telephones = telephones;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public List<UserGroup> getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(List<UserGroup> userGroup) {
        this.userGroup = userGroup;
    }

    public String getLocked() {
        return this.locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSortname() {
        return this.sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return this.sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    public Long getCreator() {
        return this.creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getTempAccount() {
        return this.tempAccount;
    }

    public void setTempAccount(String tempAccount) {
        this.tempAccount = tempAccount;
    }


    public int hashCode() {
        return Objects.hashCode(new Object[]{this.account});
    }


    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (this.account == null) {
            if (other.account != null) {
                return false;
            }
        } else if (!this.account.equals(other.account))
            return false;
        return true;
    }

    public Integer getResetPwd() {
        return this.resetPwd;
    }

    public void setResetPwd(Integer resetPwd) {
        this.resetPwd = resetPwd;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountType() {
        return this.accountType;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getSickRoomIds() {
        return this.sickRoomIds;
    }

    public void setSickRoomIds(String sickRoomIds) {
        this.sickRoomIds = sickRoomIds;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<Map<String, Object>> getGlAreaList() {
        return this.glAreaList;
    }

    public void setGlAreaList(List<Map<String, Object>> glAreaList) {
        this.glAreaList = glAreaList;
    }

    public String getGlAreaAddN() {
        return this.glAreaAddN;
    }

    public void setGlAreaAddN(String glAreaAddN) {
        this.glAreaAddN = glAreaAddN;
    }

    public String getGlAreaDel() {
        return this.glAreaDel;
    }

    public void setGlAreaDel(String glAreaDel) {
        this.glAreaDel = glAreaDel;
    }
}
