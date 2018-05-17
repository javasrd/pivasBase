package com.zc.pivas.scans.bean;

import java.util.List;
/**
 * 
 * 瓶签实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class BottleLabelBean
{
    /**
     * 父医嘱编码或组编码
     */
    private String parentNo;
    
    /**
     * 执行批次，对应批次表的主键
     */
    private Integer zXBC;
    
    /**
     * 用药日期  YYYY-MM-DD HH:MM:SS
     */
    private String yYRQ;
    
    /**
     * 药单状态步骤  0,执行  1,停止  2,撤销 3,未打印  4,已打印  5,入仓扫描核对完成  6,出仓扫描核对完成
     */
    private Integer yDZT;
    
    /**
     * 药单瓶签的唯一编号，审核通过后自动生成，打印时写入二维码并显示在瓶签上
     */
    private String yDPQ;
    
    /**
     * 病区名称
     */
    private String deptName;
    
    /**
     * 分类瓶签编码列表
     */
    private String categoryCodeList;
    
    /**
     * 住院流水号，病人唯一标识
     */
    private String inhospno;
    
    /**
     * 患者姓名
     */
    private String patName;
    
    /**
     * 性别：0女，1男，默认0
     */
    private Integer sex;
    
    /**
     * 病人唯一住院号
     */
    private String caseID;
    
    /**
     * 病人出生日期
     */
    private String birthday;
    
    /**
     * 病人年龄
     */
    private String age;
    
    /**
     * 年龄单位，0天 1月 2年
     */
    private String ageUnit;
    
    /**
     * 病人体重
     */
    private String avdp;
    
    /**
     * 医嘱审核药师名名称，如[1001,詹姆斯]
     */
    private String sfysmc;
    
    /**
     * 批次名称
     */
    private String batchName;
    
    /**
     * 批次的数量
     */
    private Integer batchCount;
    
    /**
     * 床号
     */
    private String bedNO;
    
    /**
     * 扫描日期
     */
    private String smRQ;
    
    /**
     * 扫描结果
     */
    private Integer smJG;
    
    /**
     * 扫描失败原因
     */
    private String smSBYY;
    
    
    private String configurator;
    /**
     * 药单列表
     */
    private List<MedicineBean> medicineBeanList;
    
    /**
     * 扫描类型 0：入仓扫描  1：出仓扫描
     */
    private String smLX;
    
    private String pidsj;
    
    /**
     * 病区编号
     */
    private String deptCode;
    
    /**
     * 核对扫描结果
     */
    private BottleLabelResult result;
    
    private String drugname;
    
    private String pcName;
    
    private Integer dybz;
    
    private String pzmc;
    
    public Integer getDybz()
    {
        return dybz;
    }
    
    public void setDybz(Integer dybz)
    {
        this.dybz = dybz;
    }
    
    public String getPcName()
    {
        return pcName;
    }
    
    public void setPcName(String pcName)
    {
        this.pcName = pcName;
    }
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public Integer getzXBC()
    {
        return zXBC;
    }
    
    public void setzXBC(Integer zXBC)
    {
        this.zXBC = zXBC;
    }
    
    public String getyYRQ()
    {
        return yYRQ;
    }
    
    public void setyYRQ(String yYRQ)
    {
        this.yYRQ = yYRQ;
    }
    
    public Integer getyDZT()
    {
        return yDZT;
    }
    
    public void setyDZT(Integer yDZT)
    {
        this.yDZT = yDZT;
    }
    
    public String getyDPQ()
    {
        return yDPQ;
    }
    
    public void setyDPQ(String yDPQ)
    {
        this.yDPQ = yDPQ;
    }
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public String getCategoryCodeList()
    {
        return categoryCodeList;
    }
    
    public void setCategoryCodeList(String categoryCodeList)
    {
        this.categoryCodeList = categoryCodeList;
    }
    
    public String getInhospno()
    {
        return inhospno;
    }
    
    public void setInhospno(String inhospno)
    {
        this.inhospno = inhospno;
    }
    
    public String getPatName()
    {
        return patName;
    }
    
    public void setPatName(String patName)
    {
        this.patName = patName;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public String getCaseID()
    {
        return caseID;
    }
    
    public void setCaseID(String caseID)
    {
        this.caseID = caseID;
    }
    
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public String getAgeUnit()
    {
        return ageUnit;
    }
    
    public void setAgeUnit(String ageUnit)
    {
        this.ageUnit = ageUnit;
    }
    
    public String getAvdp()
    {
        return avdp;
    }
    
    public void setAvdp(String avdp)
    {
        this.avdp = avdp;
    }
    
    public String getSfysmc()
    {
        return sfysmc;
    }
    
    public void setSfysmc(String sfysmc)
    {
        this.sfysmc = sfysmc;
    }
    
    public Integer getBatchCount()
    {
        return batchCount;
    }
    
    public void setBatchCount(Integer batchCount)
    {
        this.batchCount = batchCount;
    }
    
    public String getBatchName()
    {
        return batchName;
    }
    
    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }
    
    public String getBedNO()
    {
        return bedNO;
    }
    
    public void setBedNO(String bedNO)
    {
        this.bedNO = bedNO;
    }
    
    public String getSmRQ()
    {
        return smRQ;
    }
    
    public void setSmRQ(String smRQ)
    {
        this.smRQ = smRQ;
    }
    
    public Integer getSmJG()
    {
        return smJG;
    }
    
    public void setSmJG(Integer smJG)
    {
        this.smJG = smJG;
    }
    
    public List<MedicineBean> getMedicineBeanList()
    {
        return medicineBeanList;
    }
    
    public void setMedicineBeanList(List<MedicineBean> medicineBeanList)
    {
        this.medicineBeanList = medicineBeanList;
    }
    
    public String getSmSBYY()
    {
        return smSBYY;
    }
    
    public void setSmSBYY(String smSBYY)
    {
        this.smSBYY = smSBYY;
    }
    
    public String getSmLX()
    {
        return smLX;
    }
    
    public void setSmLX(String smLX)
    {
        this.smLX = smLX;
    }
    
    public String getPidsj()
    {
        return pidsj;
    }
    
    public void setPidsj(String pidsj)
    {
        this.pidsj = pidsj;
    }
    
    public String getDeptCode()
    {
        return deptCode;
    }
    
    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }
    
    public BottleLabelResult getResult()
    {
        return result;
    }
    
    public void setResult(BottleLabelResult result)
    {
        this.result = result;
    }
    
    public String getDrugname()
    {
        return drugname;
    }
    
    public void setDrugname(String drugname)
    {
        this.drugname = drugname;
    }

    /**
     * @return 返回 pzmc
     */
    public String getPzmc()
    {
        return pzmc;
    }

    /**
     * @param 对pzmc进行赋值
     */
    public void setPzmc(String pzmc)
    {
        this.pzmc = pzmc;
    }

    /**
     * @return 返回 configurator
     */
    public String getConfigurator()
    {
        return configurator;
    }

    /**
     * @param 对configurator进行赋值
     */
    public void setConfigurator(String configurator)
    {
        this.configurator = configurator;
    }
}