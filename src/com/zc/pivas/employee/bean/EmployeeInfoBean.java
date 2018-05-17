package com.zc.pivas.employee.bean;

import java.io.Serializable;

/**
 * 员工实体类
 *
 * @author Ray
 * @version 1.0
 */
public class EmployeeInfoBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private String gid;

    /**
     * 员工编码
     */
    private String staff_Code;

    /**
     * 员工编码（查询）
     */
    private String[] staff_Codes;

    /**
     * 员工名册
     */
    private String staff_Name;

    /**
     * 员工名册（查询）
     */
    private String[] staff_Names;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getStaff_Code() {
        return staff_Code;
    }

    public void setStaff_Code(String staff_Code) {
        this.staff_Code = staff_Code;
    }

    public String[] getStaff_Codes() {
        return staff_Codes;
    }

    public void setStaff_Codes(String[] staff_Codes) {
        this.staff_Codes = staff_Codes;
    }

    public String getStaff_Name() {
        return staff_Name;
    }

    public void setStaff_Name(String staff_Name) {
        this.staff_Name = staff_Name;
    }

    public String[] getStaff_Names() {
        return staff_Names;
    }

    public void setStaff_Names(String[] staff_Names) {
        this.staff_Names = staff_Names;
    }

}
