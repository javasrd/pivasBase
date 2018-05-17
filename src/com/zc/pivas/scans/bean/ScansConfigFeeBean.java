package com.zc.pivas.scans.bean;

import java.util.Date;

/**
 * 
 * 药单配置费收取、退费数据表
 *
 * @author  cacabin
 * @version  1.0
 */
public class ScansConfigFeeBean
{
    /**
     * 药单配置费表主键
     */
    private Integer ysPZFId;
    
    /**
     * 药单ID
     */
    private Integer ydId;
    
    /**
     * 药单配置费收取、退费状态，0,配置费收取失败 1,配置费收费成功 2,配置费退费失败  3、配置费退费成功
     */
    private Integer pzFZT;
    
    /**
     * 药单配置费收取、退费失败原因
     */
    private String pzFSBYY;
    
    /**
     * 药单配置费收取、退费时间  YYYY-MM-DD HH:MM:SS
     */
    private String pzFSQRQ;
    
    /**
     * 对应配置费规则表的主键
     */
    private Integer configFeeRuleId;
    
    /**
     * 收费、退费操作员名称
     */
    private String czYMC;
    
    /**
     * 对应核对步骤表的主键
     */
    private String gid;
    
    /**
     * 配置费/材料费 编码
     */
    private String costcode;
    
    /**
     * 病人唯一住院号 
     */
    private String caseID;
    
    private String pidrqzxbc;
    
    /**
     * 瓶签跟配置费关系
     */
    private Long pqRefFeeId;
    
    /**
     * 收费编码价格
     */
    private String price;
    
    /**
     * 数量
     */
    private Integer amount;
    
    /**
     * 瓶签id
     */
    private Integer pqId;
    
    /**
     * 病区编码
     */
    private String deptCode;
    
    /**
     * 收费日期
     */
    private Date ChargeDate;
    
    public Integer getYsPZFId()
    {
        return ysPZFId;
    }
    
    public void setYsPZFId(Integer ysPZFId)
    {
        this.ysPZFId = ysPZFId;
    }
    
    public Integer getYdId()
    {
        return ydId;
    }
    
    public void setYdId(Integer ydId)
    {
        this.ydId = ydId;
    }
    
    public Integer getPzFZT()
    {
        return pzFZT;
    }
    
    public void setPzFZT(Integer pzFZT)
    {
        this.pzFZT = pzFZT;
    }
    
    public String getPzFSBYY()
    {
        return pzFSBYY;
    }
    
    public void setPzFSBYY(String pzFSBYY)
    {
        this.pzFSBYY = pzFSBYY;
    }
    
    public String getPzFSQRQ()
    {
        return pzFSQRQ;
    }
    
    public void setPzFSQRQ(String pzFSQRQ)
    {
        this.pzFSQRQ = pzFSQRQ;
    }
    
    public Integer getConfigFeeRuleId()
    {
        return configFeeRuleId;
    }
    
    public void setConfigFeeRuleId(Integer configFeeRuleId)
    {
        this.configFeeRuleId = configFeeRuleId;
    }
    
    public String getCzYMC()
    {
        return czYMC;
    }
    
    public void setCzYMC(String czYMC)
    {
        this.czYMC = czYMC;
    }
    
    public String getGid()
    {
        return gid;
    }
    
    public void setGid(String gid)
    {
        this.gid = gid;
    }
    
    public String getCaseID()
    {
        return caseID;
    }
    
    public void setCaseID(String caseID)
    {
        this.caseID = caseID;
    }
    
    public String getCostcode()
    {
        return costcode;
    }
    
    public void setCostcode(String costcode)
    {
        this.costcode = costcode;
    }
    
    public String getPidrqzxbc()
    {
        return pidrqzxbc;
    }
    
    public void setPidrqzxbc(String pidrqzxbc)
    {
        this.pidrqzxbc = pidrqzxbc;
    }
    
    public Long getPqRefFeeId()
    {
        return pqRefFeeId;
    }
    
    public void setPqRefFeeId(Long pqRefFeeId)
    {
        this.pqRefFeeId = pqRefFeeId;
    }
    
    public String getPrice()
    {
        return price;
    }
    
    public void setPrice(String price)
    {
        this.price = price;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public Integer getPqId()
    {
        return pqId;
    }
    
    public void setPqId(Integer pqId)
    {
        this.pqId = pqId;
    }
    
    public String getDeptCode()
    {
        return deptCode;
    }
    
    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    public Date getChargeDate()
    {
        return ChargeDate;
    }

    public void setChargeDate(Date chargeDate)
    {
        ChargeDate = chargeDate;
    }
    
}
