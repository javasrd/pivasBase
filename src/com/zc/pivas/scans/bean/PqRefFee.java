package com.zc.pivas.scans.bean;
/**
 * 
 * 扫描结果实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class PqRefFee
{
    private Long gid;
    
    private String pidsj;
    
    /**
     * 收费结果，1 成功  0失败
     */
    private Integer result;
    
    /**
     * 计算详细结果 0,配置费收取失败 1,配置费收费成功 2,配置费退费失败  3、配置费退费成功 
     */
    private String result_details;
    
    /**
     * 收费日期详细信息
     */
    private String date_details;
    
    public Long getGid()
    {
        return gid;
    }
    
    public void setGid(Long gid)
    {
        this.gid = gid;
    }
    
    public String getPidsj()
    {
        return pidsj;
    }
    
    public void setPidsj(String pidsj)
    {
        this.pidsj = pidsj;
    }
    
    public Integer getResult()
    {
        return result;
    }
    
    public void setResult(Integer result)
    {
        this.result = result;
    }

    public String getResult_details()
    {
        return result_details;
    }

    public void setResult_details(String result_details)
    {
        this.result_details = result_details;
    }

    public String getDate_details()
    {
        return date_details;
    }

    public void setDate_details(String date_details)
    {
        this.date_details = date_details;
    }
    
}
