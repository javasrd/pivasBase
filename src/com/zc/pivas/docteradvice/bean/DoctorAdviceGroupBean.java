package com.zc.pivas.docteradvice.bean;

/**
 * 
 * 医嘱分组后实体类
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
public class DoctorAdviceGroupBean
{

    /**
     * 审方状态
     */
    Integer yzshzt;

    /**
     * 审方人
     */
    String sfyscode;

    /**
     * 医嘱状态
     */
    Integer yzzt;

    /**
     * 医生工号
     */
    String drawer;

    /**
     * 病区编码
     */
    String wardCode;

    /**
     * 病区编码
     */
    String wardCodeS;

    /**
     * 住院号
     */
    String inpatientNo;

    /**
     * 执行频率
     */
    String freqCode;

    /**
     * 数量
     */
    Integer num;
    
    public Integer getYzshzt()
    {
        return yzshzt;
    }
    
    public void setYzshzt(Integer yzshzt)
    {
        this.yzshzt = yzshzt;
    }
    
    public String getSfyscode()
    {
        return sfyscode;
    }
    
    public void setSfyscode(String sfyscode)
    {
        this.sfyscode = sfyscode;
    }
    
    public Integer getYzzt()
    {
        return yzzt;
    }
    
    public void setYzzt(Integer yzzt)
    {
        this.yzzt = yzzt;
    }
    
    public String getDrawer()
    {
        return drawer;
    }
    
    public void setDrawer(String drawer)
    {
        this.drawer = drawer;
    }
    
    public String getWardCode()
    {
        return wardCode;
    }
    
    public void setWardCode(String wardCode)
    {
        this.wardCode = wardCode;
    }
    
    public String getWardCodeS()
    {
        return wardCodeS;
    }
    
    public void setWardCodeS(String wardCodeS)
    {
        this.wardCodeS = wardCodeS;
    }
    
    public String getInpatientNo()
    {
        return inpatientNo;
    }
    
    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }
    
    public String getFreqCode()
    {
        return freqCode;
    }
    
    public void setFreqCode(String freqCode)
    {
        this.freqCode = freqCode;
    }
    
    public Integer getNum()
    {
        return num;
    }
    
    public void setNum(Integer num)
    {
        this.num = num;
    }
    
}
