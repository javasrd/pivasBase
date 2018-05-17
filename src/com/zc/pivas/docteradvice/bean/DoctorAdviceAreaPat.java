package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;

/**
 * 
 * 病人医嘱分组实体类
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
public class DoctorAdviceAreaPat implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    String wardCode;

    /**
     * 病区名称
     */
    String wardName;

    /**
     * 住院号
     */
    String inpatientNo;

    /**
     * 医嘱类型
     */
    Integer yzlx;
    
    public String getWardCode()
    {
        return wardCode;
    }
    public void setWardCode(String wardCode)
    {
        this.wardCode = wardCode;
    }
    public String getWardName()
    {
        return wardName;
    }
    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }
    public String getInpatientNo()
    {
        return inpatientNo;
    }
    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }
    public Integer getYzlx()
    {
        return yzlx;
    }
    public void setYzlx(Integer yzlx)
    {
        this.yzlx = yzlx;
    }
    
    
}
