package com.zc.pivas.configfee.bean;

import java.util.Date;
/**
 * 
 * 收费详情实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class ChargeDetailsBean
{
    private int yDPZFID;// 药单配置费表主键，自增长 NUMBER
    
    private int yDID;// 药单表的主键 NUMBER
    
    private int pZFZT;// 药单配置费收取、退费状态，0,配置费收取失败 1,配置费收费成功 2,配置费退费失败 3、配置费退费成功
                      // NUMBER
    
    private String pZFSBYY; // 药单配置费收取、退费失败原因 VARCHAR2
    
    private String pZFSQRQ; // 药单配置费收取、退费时间 YYYY-MM-DD HH:MM:SS DATE
    
    private int cONFIGFEERULEID;// 对应配置费规则表的主键 NUMBER
    
    private String cZYMC; // 收费、退费操作员名称 VARCHAR2
    
    private int gID; // 对应核对步骤表的主键;// NUMBER
    
    private String cASE_ID; // 病人唯一住院号[新增] VARCHAR2
    
    private String costcode;
    
    private String pidrqzxbc;
    
    private String doctor;
    
    private String doctorName;
    
    // 20PIVAS_YD药单表
    // private int YD_ID;// 主键标识，自增长 NUMBER
    private int aCTORDER_NO;// 医嘱编码,此字段须与HIS医嘱信息中编码一致 NUMBER
    
    private int pARENT_NO;// 父医嘱编码或组编码 NUMBER
    
    private int dYBZ;// 药单打印标志,0已打印 1未打印 NUMBER
    
    Date yYRQ; // 用药日期 YYYY-MM-DD HH:MM:SS DATE
    
    Date dATESCRQ;// 药单生成日期 YYYY-MM-DD HH:MM:SS DATE
    
    private int zXBC;// 执行批次，对应批次表的主键 NUMBER
    
    private int yDZXZT;// 药单执行状态 0,执行 1,停止 2,撤销 NUMBER
    
    private int yDZT;// 药单状态步骤 0,执行 1,停止 2,撤销 3,未打印 4,已打印 5,入仓扫描核对完成
                     // 6,出仓扫描核对完成[移到公共表] NUMBER
    
    private String yDPQ;// 药单瓶签的唯一编号，审核通过后自动生成，打印时写入二维码并显示在瓶签上[移到公共表] NUMBER
    
    private String mEDICAMENTS_CODE;// 药品编码 VARCHAR2
    
    private String cHARGE_CODE;// 医嘱的药品编码。 VARCHAR2
    
    private String dRUGNAME;// 医嘱的药品名称。 VARCHAR2
    
    private String sPECIFICATIONS;// 医嘱的药品规格。 VARCHAR2
    
    private String dOSE;// 医嘱的药品单次剂量。 VARCHAR2
    
    private String dOSE_UNIT;// 医嘱的药品单次剂量单位。 VARCHAR2
    
    private String qUANTITY;// 药品数量 VARCHAR2
    
    private String rESERVE1;// 备用字段1 VARCHAR2
    
    private String rESERVE2;// 备用字段2 VARCHAR2
    
    private String rESERVE3;// 备用字段3 VARCHAR2
    
    private String nAME_;
    
    private Long pqRefFeeID;
    
    public String getnAME_()
    {
        return nAME_;
    }
    
    public void setnAME_(String nAME_)
    {
        this.nAME_ = nAME_;
    }
    
    public String getwARDNAME()
    {
        return wARDNAME;
    }
    
    public void setwARDNAME(String wARDNAME)
    {
        this.wARDNAME = wARDNAME;
    }
    
    private String mEDICAMENTS_PACKING_UNIT;// 包装单位 VARCHAR2
    
    private String wARDNAME;
    
    // SRVS_LABEL药单瓶签表
    private String dEPTNAME;// 病区名称 VARCHAR2
    
    private String cATEGORY_CODE_LIST;// 分类瓶签编码列表 VARCHAR2
    
    public int getpZFZT()
    {
        return pZFZT;
    }
    
    public void setpZFZT(int pZFZT)
    {
        this.pZFZT = pZFZT;
    }
    
    public String getpZFSBYY()
    {
        return pZFSBYY;
    }
    
    public void setpZFSBYY(String pZFSBYY)
    {
        this.pZFSBYY = pZFSBYY;
    }
    
    public int getcONFIGFEERULEID()
    {
        return cONFIGFEERULEID;
    }
    
    public void setcONFIGFEERULEID(int cONFIGFEERULEID)
    {
        this.cONFIGFEERULEID = cONFIGFEERULEID;
    }
    
    public String getcZYMC()
    {
        return cZYMC;
    }
    
    public void setcZYMC(String cZYMC)
    {
        this.cZYMC = cZYMC;
    }
    
    public String getpZFSQRQ()
    {
        return pZFSQRQ;
    }
    
    public void setpZFSQRQ(String pZFSQRQ)
    {
        this.pZFSQRQ = pZFSQRQ;
    }
    
    public int getgID()
    {
        return gID;
    }
    
    public void setgID(int gID)
    {
        this.gID = gID;
    }
    
    public String getcASE_ID()
    {
        return cASE_ID;
    }
    
    public void setcASE_ID(String cASE_ID)
    {
        this.cASE_ID = cASE_ID;
    }
    
    public int getyDPZFID()
    {
        return yDPZFID;
    }
    
    public void setyDPZFID(int yDPZFID)
    {
        this.yDPZFID = yDPZFID;
    }
    
    public int getyDID()
    {
        return yDID;
    }
    
    public void setyDID(int yDID)
    {
        this.yDID = yDID;
    }
    
    public int getaCTORDER_NO()
    {
        return aCTORDER_NO;
    }
    
    public void setaCTORDER_NO(int aCTORDER_NO)
    {
        this.aCTORDER_NO = aCTORDER_NO;
    }
    
    public int getpARENT_NO()
    {
        return pARENT_NO;
    }
    
    public void setpARENT_NO(int pARENT_NO)
    {
        this.pARENT_NO = pARENT_NO;
    }
    
    public int getdYBZ()
    {
        return dYBZ;
    }
    
    public void setdYBZ(int dYBZ)
    {
        this.dYBZ = dYBZ;
    }
    
    public Date getyYRQ()
    {
        Date date = new Date(yYRQ.getTime());
        return date;
    }
    
    public void setyYRQ(Date yYRQ)
    {
        Date date = new Date(yYRQ.getTime());
        this.yYRQ = date;
    }
    
    public Date getdATESCRQ()
    {
        Date date = new Date(dATESCRQ.getTime());
        return date;
    }
    
    public void setdATESCRQ(Date dATESCRQ)
    {
       Date date = new Date(dATESCRQ.getTime());
       this.dATESCRQ = date;
    }
    
    public int getzXBC()
    {
        return zXBC;
    }
    
    public void setzXBC(int zXBC)
    {
        this.zXBC = zXBC;
    }
    
    public int getyDZXZT()
    {
        return yDZXZT;
    }
    
    public void setyDZXZT(int yDZXZT)
    {
        this.yDZXZT = yDZXZT;
    }
    
    public int getyDZT()
    {
        return yDZT;
    }
    
    public void setyDZT(int yDZT)
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
    
    public String getmEDICAMENTS_CODE()
    {
        return mEDICAMENTS_CODE;
    }
    
    public void setmEDICAMENTS_CODE(String mEDICAMENTS_CODE)
    {
        this.mEDICAMENTS_CODE = mEDICAMENTS_CODE;
    }
    
    public String getcHARGE_CODE()
    {
        return cHARGE_CODE;
    }
    
    public void setcHARGE_CODE(String cHARGE_CODE)
    {
        this.cHARGE_CODE = cHARGE_CODE;
    }
    
    public String getdRUGNAME()
    {
        return dRUGNAME;
    }
    
    public void setdRUGNAME(String dRUGNAME)
    {
        this.dRUGNAME = dRUGNAME;
    }
    
    public String getsPECIFICATIONS()
    {
        return sPECIFICATIONS;
    }
    
    public void setsPECIFICATIONS(String sPECIFICATIONS)
    {
        this.sPECIFICATIONS = sPECIFICATIONS;
    }
    
    public String getdOSE()
    {
        return dOSE;
    }
    
    public void setdOSE(String dOSE)
    {
        this.dOSE = dOSE;
    }
    
    public String getdOSE_UNIT()
    {
        return dOSE_UNIT;
    }
    
    public void setdOSE_UNIT(String dOSE_UNIT)
    {
        this.dOSE_UNIT = dOSE_UNIT;
    }
    
    public String getqUANTITY()
    {
        return qUANTITY;
    }
    
    public void setqUANTITY(String qUANTITY)
    {
        this.qUANTITY = qUANTITY;
    }
    
    public String getrESERVE1()
    {
        return rESERVE1;
    }
    
    public void setrESERVE1(String rESERVE1)
    {
        this.rESERVE1 = rESERVE1;
    }
    
    public String getrESERVE2()
    {
        return rESERVE2;
    }
    
    public void setrESERVE2(String rESERVE2)
    {
        this.rESERVE2 = rESERVE2;
    }
    
    public String getrESERVE3()
    {
        return rESERVE3;
    }
    
    public void setrESERVE3(String rESERVE3)
    {
        this.rESERVE3 = rESERVE3;
    }
    
    public String getmEDICAMENTS_PACKING_UNIT()
    {
        return mEDICAMENTS_PACKING_UNIT;
    }
    
    public void setmEDICAMENTS_PACKING_UNIT(String mEDICAMENTS_PACKING_UNIT)
    {
        this.mEDICAMENTS_PACKING_UNIT = mEDICAMENTS_PACKING_UNIT;
    }
    
    public String getdEPTNAME()
    {
        return dEPTNAME;
    }
    
    public void setdEPTNAME(String dEPTNAME)
    {
        this.dEPTNAME = dEPTNAME;
    }
    
    public String getcATEGORY_CODE_LIST()
    {
        return cATEGORY_CODE_LIST;
    }
    
    public void setcATEGORY_CODE_LIST(String cATEGORY_CODE_LIST)
    {
        this.cATEGORY_CODE_LIST = cATEGORY_CODE_LIST;
    }
    
    public String getiNHOSPNO()
    {
        return iNHOSPNO;
    }
    
    public void setiNHOSPNO(String iNHOSPNO)
    {
        this.iNHOSPNO = iNHOSPNO;
    }
    
    public String getpATNAME()
    {
        return pATNAME;
    }
    
    public void setpATNAME(String pATNAME)
    {
        this.pATNAME = pATNAME;
    }
    
    public int getsEX()
    {
        return sEX;
    }
    
    public void setsEX(int sEX)
    {
        this.sEX = sEX;
    }
    
    public Date getbIRTHDAY()
    {
        Date date = new Date(bIRTHDAY.getTime());
        return date;
    }
    
    public void setbIRTHDAY(Date bIRTHDAY)
    {
        Date date = new Date(bIRTHDAY.getTime());
        this.bIRTHDAY = date;
    }
    
    public String getaGE()
    {
        return aGE;
    }
    
    public void setaGE(String aGE)
    {
        this.aGE = aGE;
    }
    
    public int getaGEUNIT()
    {
        return aGEUNIT;
    }
    
    public void setaGEUNIT(int aGEUNIT)
    {
        this.aGEUNIT = aGEUNIT;
    }
    
    public String getaVDP()
    {
        return aVDP;
    }
    
    public void setaVDP(String aVDP)
    {
        this.aVDP = aVDP;
    }
    
    public String getsFYSMC()
    {
        return sFYSMC;
    }
    
    public void setsFYSMC(String sFYSMC)
    {
        this.sFYSMC = sFYSMC;
    }
    
    public String getbEDNO()
    {
        return bEDNO;
    }
    
    public void setbEDNO(String bEDNO)
    {
        this.bEDNO = bEDNO;
    }
    
    private String iNHOSPNO;// 住院流水号，病人唯一标识 VARCHAR2
    
    private String pATNAME;// 患者姓名 VARCHAR2
    
    private int sEX;// 性别：0女，1男，默认0 NUMBER
    
    Date bIRTHDAY; // 病人出生日期 DATE
    
    private String aGE; // 病人年龄 //VARCHAR2
    
    private int aGEUNIT; // 年龄单位，0天 1月 2年 NUMBER
    
    private String aVDP; // 病人体重 VARCHAR2
    
    private String sFYSMC; // 医嘱审核药师名名称，如[1001,詹姆斯] VARCHAR2
    
    private String bEDNO; // 患者住院期间，所住床位对应的编号 VARCHAR2
    
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
    
    public String getDoctor()
    {
        return doctor;
    }
    
    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }
    
    public String getDoctorName()
    {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }
    
    public Long getPqRefFeeID()
    {
        return pqRefFeeID;
    }
    
    public void setPqRefFeeID(Long pqRefFeeID)
    {
        this.pqRefFeeID = pqRefFeeID;
    }
    
}
