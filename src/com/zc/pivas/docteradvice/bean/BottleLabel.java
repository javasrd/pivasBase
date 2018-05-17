package com.zc.pivas.docteradvice.bean;

import com.zc.pivas.docteradvice.entity.PrescriptionMain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 瓶签实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class BottleLabel implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 父医嘱编号
     */
    String parentNo;

    /**
     * 执行批次
     */
    Integer zxbc;

    /**
     * 用药日期
     */
    Date yyrq;

    /**用药日期
     * 
     */
    String yyrqS;

    /**
     * 医嘱编号+日期
     */
    String pidRQ;

    /**
     * 药单状态
     */
    Integer ydzt;

    /**
     * 瓶签号
     */
    String ydpq;

    /**
     * 修改前瓶签
     */
    String ydpqLast;

    /**
     * 修改前批次
     */
    Integer zxbcLast;

    /**
     * 病区名称
     */
    String deptname;

    /**
     * 药品分类
     */
    String categoryCodeList;

    /**
     * 住院号
     */
    String inhospno;

    /**
     * 病人姓名
     */
    String patname;

    /**
     * 性别
     */
    Integer sex;

    /**
     * 唯一就诊号
     */
    String caseId;

    /**
     * 生日
     */
    Date birthday;

    /**
     * 年龄
     */
    String age;

    /**
     * 年龄单位
     */
    Integer ageunit;

    /**
     * 病人体重
     */
    String avdp;

    /**
     * 审方人
     */
    String sfysmc;

    /**
     * 床号
     */
    String bedno;

    /**
     * 执行日期
     */
    String zxrq;

    /**
     * 执行时间
     */
    String zxsj;

    /**
     * 父医嘱+执行时间
     */
    String pidsj;

    /**
     * 是否重复
     */
    Integer isrepeat;

    /**
     * 病区code
     */
    String deptcode;

    /**
     * 父医嘱+执行批次
     */
    String pidrqzxbc;

    /**
     * 医嘱类型
     */
    Integer yzlx;

    /**
     * 打印时间
     */
    Date printTime;
    
    String pcName;
    
    String pcNum;
    
    public String getPcNum()
    {
        return pcNum;
    }

    public void setPcNum(String pcNum)
    {
        this.pcNum = pcNum;
    }

    public BottleLabel()
    {
        
    }
    
    public BottleLabel(PrescriptionMain ydMain)
    {
        
        parentNo = ydMain.getParentNo();
        zxbc = ydMain.getZxbc();
        zxbcLast = zxbc;
        yyrq = ydMain.getYyrq();
        yyrqS = ydMain.getYyrqS();
        pidRQ = ydMain.getPidRQ();
        ydzt = 0;
        ydpq = ydMain.getBottleLabelNum();
        ydpqLast = ydpq;
        deptname = ydMain.getWardName();
        //categoryCodeList="" ;
        inhospno = ydMain.getInpatientNo();
        patname = ydMain.getPatname();
        sex = ydMain.getSex();
        caseId = ydMain.getCaseId();
        birthday = ydMain.getBirthday();
        age = ydMain.getAge();
        ageunit = ydMain.getAgeunit();
        avdp = ydMain.getAvdp();
        sfysmc = ydMain.getSfysmc();
        bedno = ydMain.getBedno();
        zxrq = ydMain.getZxrq();
        zxsj = ydMain.getZxsj();
        pidsj = ydMain.getPidsj();
        isrepeat = 0;
        deptcode = ydMain.getWardCode();
        pidrqzxbc = ydMain.getPidrqzxbc();
        yzlx = ydMain.getYzlx();
    }
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }
    
    public Date getYyrq()
    {
        return yyrq==null?null:(Date)yyrq.clone();
    }
    
    public void setYyrq(Date yyrq)
    {
        if(yyrq!=null){
            this.yyrq = (Date)yyrq.clone();
        }
    }
    
    public Integer getYdzt()
    {
        return ydzt;
    }
    
    public void setYdzt(Integer ydzt)
    {
        this.ydzt = ydzt;
    }
    
    public String getYdpq()
    {
        return ydpq;
    }
    
    public void setYdpq(String ydpq)
    {
        this.ydpq = ydpq;
    }
    
    public String getDeptname()
    {
        return deptname;
    }
    
    public void setDeptname(String deptname)
    {
        this.deptname = deptname;
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
    
    public String getPatname()
    {
        return patname;
    }
    
    public void setPatname(String patname)
    {
        this.patname = patname;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public String getCaseId()
    {
        return caseId;
    }
    
    public void setCaseId(String caseId)
    {
        this.caseId = caseId;
    }
    
    public Date getBirthday()
    {
        return birthday==null?null:(Date)birthday.clone();
    }
    
    public void setBirthday(Date birthday)
    {
        if(birthday!=null){
            this.birthday = (Date)birthday.clone();
        }
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public Integer getAgeunit()
    {
        return ageunit;
    }
    
    public void setAgeunit(Integer ageunit)
    {
        this.ageunit = ageunit;
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
    
    public String getBedno()
    {
        return bedno;
    }
    
    public void setBedno(String bedno)
    {
        this.bedno = bedno;
    }
    
    public String getZxrq()
    {
        return zxrq;
    }
    
    public void setZxrq(String zxrq)
    {
        this.zxrq = zxrq;
    }
    
    public String getZxsj()
    {
        return zxsj;
    }
    
    public void setZxsj(String zxsj)
    {
        this.zxsj = zxsj;
    }
    
    public String getPidsj()
    {
        return pidsj;
    }
    
    public void setPidsj(String pidsj)
    {
        this.pidsj = pidsj;
    }
    
    public Integer getIsrepeat()
    {
        return isrepeat;
    }
    
    public void setIsrepeat(Integer isrepeat)
    {
        this.isrepeat = isrepeat;
    }
    
    public String getDeptcode()
    {
        return deptcode;
    }
    
    public void setDeptcode(String deptcode)
    {
        this.deptcode = deptcode;
    }
    
    public String getPidrqzxbc()
    {
        return pidrqzxbc;
    }
    
    public void setPidrqzxbc(String pidrqzxbc)
    {
        this.pidrqzxbc = pidrqzxbc;
    }
    
    public Integer getYzlx()
    {
        return yzlx;
    }
    
    public void setYzlx(Integer yzlx)
    {
        this.yzlx = yzlx;
    }
    
    public String getYyrqS()
    {
        return yyrqS;
    }
    
    public void setYyrqS(String yyrqS)
    {
        this.yyrqS = yyrqS;
    }
    
    public String getPidRQ()
    {
        return pidRQ;
    }
    
    public void setPidRQ(String pidRQ)
    {
        this.pidRQ = pidRQ;
    }
    
    public String getYdpqLast()
    {
        return ydpqLast;
    }
    
    public void setYdpqLast(String ydpqLast)
    {
        this.ydpqLast = ydpqLast;
    }
    
    public Integer getZxbcLast()
    {
        return zxbcLast;
    }
    
    public void setZxbcLast(Integer zxbcLast)
    {
        this.zxbcLast = zxbcLast;
    }
    
    public Date getPrintTime()
    {
        return printTime==null?null:(Date)printTime.clone();
    }
    
    public void setPrintTime(Date printTime)
    {
        if(printTime!=null){
            this.printTime = (Date)printTime.clone();
        }
    }

    /**
     * @return 返回 pcName
     */
    public String getPcName()
    {
        return pcName;
    }

    /**
     * @param 对pcName进行赋值
     */
    public void setPcName(String pcName)
    {
        this.pcName = pcName;
    }
    
}
