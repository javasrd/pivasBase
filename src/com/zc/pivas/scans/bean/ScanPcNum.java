package com.zc.pivas.scans.bean;

import java.io.Serializable;

public class ScanPcNum implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 5462339155212869527L;
    
    private String pccode;
    
    private String pcname;
    
    private String totalnum;
    
    private String donenum;
    
    private String undonenum;

    /**
     * @return 返回 pccode
     */
    public String getPccode()
    {
        return pccode;
    }

    /**
     * @param 对pccode进行赋值
     */
    public void setPccode(String pccode)
    {
        this.pccode = pccode;
    }

    /**
     * @return 返回 pcname
     */
    public String getPcname()
    {
        return pcname;
    }

    /**
     * @param 对pcname进行赋值
     */
    public void setPcname(String pcname)
    {
        this.pcname = pcname;
    }

    /**
     * @return 返回 totalnum
     */
    public String getTotalnum()
    {
        return totalnum;
    }

    /**
     * @param 对totalnum进行赋值
     */
    public void setTotalnum(String totalnum)
    {
        this.totalnum = totalnum;
    }

    /**
     * @return 返回 donenum
     */
    public String getDonenum()
    {
        return donenum;
    }

    /**
     * @param 对donenum进行赋值
     */
    public void setDonenum(String donenum)
    {
        this.donenum = donenum;
    }

    /**
     * @return 返回 undonenum
     */
    public String getUndonenum()
    {
        return undonenum;
    }

    /**
     * @param 对undonenum进行赋值
     */
    public void setUndonenum(String undonenum)
    {
        this.undonenum = undonenum;
    }
    
    
}
