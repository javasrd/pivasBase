package com.zc.pivas.printlabel.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 瓶签实体
 *
 * @author kunkka
 * @version 1.0
 */
public class BottleLabel {
    /**
     * 药单瓶签的唯一编号
     */
    private String bottleNum;

    /**
     * 医嘱编码
     */
    private String actOrderNo;

    /**
     * 医嘱组编码
     */
    private String parentNo;

    /**
     * 用药日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date useDate;

    /**
     * 对应批次主键ID
     */
    private Long batchId;

    private String batchIds;

    private String[] batchIDList;

    /**
     * 对应批次号
     */
    private String batchNO;

    /**
     * 药品编码
     */
    private String medicamentsCode;

    /**
     * 病区(科室)编码
     */
    private String wardCode;

    /**
     * 病区编码数组
     */
    private String[] wardCodeArray;

    /**
     * 病区(科室)名称
     */
    private String wardName;

    /**
     * 病区别名
     */
    private String deptAliasName;

    /**
     * 医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.
     */
    private String freqCode;

    /**
     * 用药途径
     */
    private String supplyCode;

    /**
     * 医嘱类型 0:长期 1:短期
     */
    private Integer yzType;

    /**
     * 医嘱审核药师名名称，如[1001,詹姆斯]
     */
    private String docName;

    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 住院流水号，病人唯一标识
     */
    private String inhospno;

    /**
     * 性别：0女，1男，默认0
     */
    private Integer sex;

    /**
     * 患者住院期间，所住床位对应的编号
     */
    private String bedNo;

    /**
     * 病人唯一住院号
     */
    private String caseId;

    /**
     * 病人出生日期
     */
    private Date birthday;

    /**
     * 病人年龄
     */
    private String age;

    /**
     * 年龄单位，0天 1月 2年
     */
    private Integer ageUnit;

    /**
     * 病人体重
     */
    private String avdp;

    /**
     * 性别字符串
     */
    private String sexString;

    /**
     * 病人年龄字符串
     */
    private String ageString;

    /**
     * 医嘱类型字符串 0:长期 1:短期
     */
    private String yzTypeString;

    /**
     * 使用时间字符串
     */
    private String useDateString;

    /**
     * 批次名称
     */
    private String batchName;

    /**
     * 医嘱的药品名称
     */
    private String drugName;

    /**
     * 药品别名
     */
    private String drugAliasName;

    /**
     * 溶媒数量
     */
    private Integer menCount;

    /**
     * 溶媒编码
     */
    private String menstCode;

    /**
     * 货位号
     */
    private String shelfNo;

    /**
     * 医嘱的药品规格
     */
    private String specifications;

    /**
     * 医嘱的药品单次剂量
     */
    private String dose;

    /**
     * 医嘱的药品单次剂量单位
     */
    private String doseUnit;

    /**
     * 药品数量
     */
    private String quantity;

    /**
     * 药品包装单位
     */
    private String packingUnit;

    /**
     * 药品产地
     */
    private String medicamentsPlace;

    /**
     * 药品分类表ID
     */
    private String categoryId;

    private String categoryIdN;

    /**
     * 药品分类名称
     */
    private String categoryName;

    /**
     * 药品分类表 分类瓶签编码
     */
    private String categoryCode;

    /**
     * 药品分类优先级
     */
    private int categoryPriority;

    /**
     * 药品分类表 分类瓶签编码 用于去重
     */
    private Set<String> categoryCodeSet;

    /**
     * 药品
     */
    private List<BottleLabel> medicaments;

    /**
     * 是否按照药物分组，打印统排单用 0否，1是
     */
    private Integer groupByMedical = 0;

    /**
     * 瓶签编码集合，用于查询指定的瓶签下的药单
     */
    private List<String> bottleNumList;

    /**
     * 打印类型，打印瓶签、打印统排单
     */
    private Integer printType;

    /**
     * 执行日期
     */
    private String zxrq;

    /**
     * 执行时间
     */
    private String zxsj;

    private String pidsj;

    /**
     * 药单打印标志,0已打印 1未打印
     */
    private Integer dybz;

    /**
     * 高危药标志 1不是 0是
     */
    private Integer medicamentsDanger;

    /**
     * 医嘱滴速100ML/MIN
     */
    private String dropSpeed;

    /**
     * 皮试项PG( )替安( )曲松( )甲肟( )
     */
    private Set<String> skinTestSet;

    /**
     * 药品标签编码
     */
    private String medicamentsLabelNo;

    /**
     * 药品数量是否小于1，小于1的打印瓶签时需要加上下划线
     */
    private boolean quantityLtOne;

    private String printTimeS;

    private String serialNumber;

    /**
     * 输液量
     **/
    private String transfusion;

    /**
     * 是否打印操作
     */
    private boolean isPrint = true;

    /**
     * 瓶签打印人
     */
    private String printer;

    /**
     * 瓶签的pdf 路径
     */
    private String bottleNumPDFPath;

    /**
     * 用于打印瓶签为空则查询辅要信息
     */
    private String mainDrug = null;

    /**
     * 是否溶媒 0不是溶媒,1是溶媒
     */
    private Integer medicamentsMenstruum;

    /**
     * 是否做主药 1不是主药,0主药
     */
    private Integer medicamentsIsmaindrug;

    /**
     * 同种分类的数量
     */
    private List<BottleLabel> categoryCounts;

    private String mainHtml;

    private String mainDrugHtml;

    private String menstruumHtml;

    private String ydreorderStatusNull;

    /**
     * 是否历史药单打印，有值则为历史药单打印
     */
    private String isHistoryPrint;

    //打印序号
    private String printPageIndex;

    //打印页码
    private String printPageNumber;

    //打印总页数
    private String printTotalCount;

    //查询条件
    private String printConfigName;

    private Boolean isDetail = true;

    private String specialBtach;

    private String yyrqStart;

    private String yyrqEnd;

    //打印时间
    private String printTimeStr;

    //打印人员名字
    private String printName;

    //查询所有的药品
    private String statisticTwo;

    /**
     * 是否有进仓扫描环节 0：有 1：无
     */
    private String hasScanIn = "0";

    /**
     * 是否打印2#  不为空
     */
    private String isFourReciver = "1";

    public String getIsFourReciver() {
        return isFourReciver;
    }

    public void setIsFourReciver(String isFourReciver) {
        this.isFourReciver = isFourReciver;
    }

    public String getStatisticTwo() {
        return statisticTwo;
    }

    public void setStatisticTwo(String statisticTwo) {
        this.statisticTwo = statisticTwo;
    }

    public String getBottleNumPDFPath() {
        return bottleNumPDFPath;
    }

    public void setBottleNumPDFPath(String bottleNumPDFPath) {
        this.bottleNumPDFPath = bottleNumPDFPath;
    }

    public String getHasScanIn() {
        return hasScanIn;
    }

    public void setHasScanIn(String hasScanIn) {
        this.hasScanIn = hasScanIn;
    }

    public String getPrintTimeStr() {
        return printTimeStr;
    }

    public void setPrintTimeStr(String printTimeStr) {
        this.printTimeStr = printTimeStr;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getPrintConfigName() {
        return printConfigName;
    }

    public void setPrintConfigName(String printConfigName) {
        this.printConfigName = printConfigName;
    }

    public Integer getMenCount() {
        return menCount;
    }

    public void setMenCount(Integer menCount) {
        this.menCount = menCount;
    }

    public String getMenstCode() {
        return menstCode;
    }

    public void setMenstCode(String menstCode) {
        this.menstCode = menstCode;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public String getDrugAliasName() {
        return drugAliasName;
    }

    public void setDrugAliasName(String drugAliasName) {
        this.drugAliasName = drugAliasName;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public String getDeptAliasName() {
        return deptAliasName;
    }

    public void setDeptAliasName(String deptAliasName) {
        this.deptAliasName = deptAliasName;
    }

    public String getPrintTotalCount() {
        return printTotalCount;
    }

    public void setPrintTotalCount(String printTotalCount) {
        this.printTotalCount = printTotalCount;
    }

    public String getPrintPageIndex() {
        return printPageIndex;
    }

    public void setPrintPageIndex(String printPageIndex) {
        this.printPageIndex = printPageIndex;
    }

    public String getPrintPageNumber() {
        return printPageNumber;
    }

    public void setPrintPageNumber(String printPageNumber) {
        this.printPageNumber = printPageNumber;
    }

    public String getIsHistoryPrint() {
        return isHistoryPrint;
    }

    public void setIsHistoryPrint(String isHistoryPrint) {
        this.isHistoryPrint = isHistoryPrint;
    }

    public String getYdreorderStatusNull() {
        return ydreorderStatusNull;
    }

    public void setYdreorderStatusNull(String ydreorderStatusNull) {
        this.ydreorderStatusNull = ydreorderStatusNull;
    }

    public String getMainHtml() {
        return mainHtml;
    }

    public void setMainHtml(String mainHtml) {
        this.mainHtml = mainHtml;
    }

    public String getMainDrugHtml() {
        return mainDrugHtml;
    }

    public void setMainDrugHtml(String mainDrugHtml) {
        this.mainDrugHtml = mainDrugHtml;
    }

    public String getMenstruumHtml() {
        return menstruumHtml;
    }

    public void setMenstruumHtml(String menstruumHtml) {
        this.menstruumHtml = menstruumHtml;
    }

    public String[] getBatchIDList() {
        return batchIDList;
    }

    public void setBatchIDList(String[] batchIDList) {
        this.batchIDList = batchIDList;
    }

    public String getBatchIds() {
        return batchIds;
    }

    public void setBatchIds(String batchIds) {
        this.batchIds = batchIds;
    }

    public Integer getMedicamentsMenstruum() {
        return medicamentsMenstruum;
    }

    public void setMedicamentsMenstruum(Integer medicamentsMenstruum) {
        this.medicamentsMenstruum = medicamentsMenstruum;
    }

    public Integer getMedicamentsIsmaindrug() {
        return medicamentsIsmaindrug;
    }

    public void setMedicamentsIsmaindrug(Integer medicamentsIsmaindrug) {
        this.medicamentsIsmaindrug = medicamentsIsmaindrug;
    }

    public String getMainDrug() {
        return mainDrug;
    }

    public void setMainDrug(String mainDrug) {
        this.mainDrug = mainDrug;
    }

    public List<BottleLabel> getCategoryCounts() {
        return categoryCounts;
    }

    public void setCategoryCounts(List<BottleLabel> categoryCounts) {
        this.categoryCounts = categoryCounts;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(boolean isPrint) {
        this.isPrint = isPrint;
    }

    public boolean getIsDetail() {
        return isDetail;
    }

    public void setIsDetail(boolean isDetail) {
        this.isDetail = isDetail;
    }

    public String getBottleNum() {
        return bottleNum;
    }

    public void setBottleNum(String bottleNum) {
        this.bottleNum = bottleNum;
    }

    public String getActOrderNo() {
        return actOrderNo;
    }

    public void setActOrderNo(String actOrderNo) {
        this.actOrderNo = actOrderNo;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public Date getUseDate() {
        if (null == useDate) {
            return null;
        }
        Date date = new Date(useDate.getTime());
        return date;
    }

    public void setUseDate(Date useDate) {
        Date date = new Date(useDate.getTime());
        this.useDate = date;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getMedicamentsCode() {
        return medicamentsCode;
    }

    public void setMedicamentsCode(String medicamentsCode) {
        this.medicamentsCode = medicamentsCode;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getFreqCode() {
        return freqCode;
    }

    public void setFreqCode(String freqCode) {
        this.freqCode = freqCode;
    }

    public Integer getYzType() {
        return yzType;
    }

    public void setYzType(Integer yzType) {
        this.yzType = yzType;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getInhospno() {
        return inhospno;
    }

    public void setInhospno(String inhospno) {
        this.inhospno = inhospno;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Date getBirthday() {
        if (birthday == null) {
            return new Date();
        }
        Date date = new Date(birthday.getTime());
        return date;
    }

    public void setBirthday(Date birthday) {
        if (birthday != null) {
            Date date = new Date(birthday.getTime());
            this.birthday = date;
        } else {
            this.birthday = new Date();
        }
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(Integer ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getAvdp() {
        return avdp;
    }

    public void setAvdp(String avdp) {
        this.avdp = avdp;
    }

    public String getSexString() {
        return sexString;
    }

    public void setSexString(String sexString) {
        this.sexString = sexString;
    }

    public String getAgeString() {
        return ageString;
    }

    public void setAgeString(String ageString) {
        this.ageString = ageString;
    }

    public String getYzTypeString() {
        return yzTypeString;
    }

    public void setYzTypeString(String yzTypeString) {
        this.yzTypeString = yzTypeString;
    }

    public String getUseDateString() {
        return useDateString;
    }

    public void setUseDateString(String useDateString) {
        this.useDateString = useDateString;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPackingUnit() {
        return packingUnit;
    }

    public void setPackingUnit(String packingUnit) {
        this.packingUnit = packingUnit;
    }

    public String getMedicamentsPlace() {
        return medicamentsPlace;
    }

    public void setMedicamentsPlace(String medicamentsPlace) {
        this.medicamentsPlace = medicamentsPlace;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Set<String> getCategoryCodeSet() {
        return categoryCodeSet;
    }

    public void setCategoryCodeSet(Set<String> categoryCodeSet) {
        this.categoryCodeSet = categoryCodeSet;
    }

    public List<BottleLabel> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<BottleLabel> medicaments) {
        this.medicaments = medicaments;
    }

    public Integer getGroupByMedical() {
        return groupByMedical;
    }

    public void setGroupByMedical(Integer groupByMedical) {
        this.groupByMedical = groupByMedical;
    }

    public List<String> getBottleNumList() {
        return bottleNumList;
    }

    public void setBottleNumList(List<String> bottleNumList) {
        this.bottleNumList = bottleNumList;
    }

    public Integer getPrintType() {
        return printType;
    }

    public void setPrintType(Integer printType) {
        this.printType = printType;
    }

    public String getZxrq() {
        return zxrq;
    }

    public void setZxrq(String zxrq) {
        this.zxrq = zxrq;
    }

    public String getZxsj() {
        return zxsj;
    }

    public void setZxsj(String zxsj) {
        this.zxsj = zxsj;
    }

    public String getPidsj() {
        return pidsj;
    }

    public void setPidsj(String pidsj) {
        this.pidsj = pidsj;
    }

    public Integer getDybz() {
        return dybz;
    }

    public void setDybz(Integer dybz) {
        this.dybz = dybz;
    }

    public Integer getMedicamentsDanger() {
        return medicamentsDanger;
    }

    public void setMedicamentsDanger(Integer medicamentsDanger) {
        this.medicamentsDanger = medicamentsDanger;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public Set<String> getSkinTestSet() {
        return skinTestSet;
    }

    public void setSkinTestSet(Set<String> skinTestSet) {
        this.skinTestSet = skinTestSet;
    }

    public String getMedicamentsLabelNo() {
        return medicamentsLabelNo;
    }

    public void setMedicamentsLabelNo(String medicamentsLabelNo) {
        this.medicamentsLabelNo = medicamentsLabelNo;
    }

    public boolean isQuantityLtOne() {
        return quantityLtOne;
    }

    public void setQuantityLtOne(boolean quantityLtOne) {
        this.quantityLtOne = quantityLtOne;
    }

    public String getPrintTimeS() {
        return printTimeS;
    }

    public void setPrintTimeS(String printTimeS) {
        this.printTimeS = printTimeS;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTransfusion() {
        return transfusion;
    }

    public void setTransfusion(String transfusion) {
        this.transfusion = transfusion;
    }

    public String getBatchNO() {
        return batchNO;
    }

    public void setBatchNO(String batchNO) {
        this.batchNO = batchNO;
    }

    public String getCategoryIdN() {
        return categoryIdN;
    }

    public void setCategoryIdN(String categoryIdN) {
        this.categoryIdN = categoryIdN;
    }

    public int getCategoryPriority() {
        return categoryPriority;
    }

    public void setCategoryPriority(int categoryPriority) {
        this.categoryPriority = categoryPriority;
    }

    public String[] getWardCodeArray() {
        return wardCodeArray;
    }

    public void setWardCodeArray(String[] wardCodeArray) {
        this.wardCodeArray = wardCodeArray;
    }

    /**
     * @return 返回 specialBtach
     */
    public String getSpecialBtach() {
        return specialBtach;
    }

    /**
     * @param 对specialBtach进行赋值
     */
    public void setSpecialBtach(String specialBtach) {
        this.specialBtach = specialBtach;
    }

    /**
     * @return 返回 yyrqStart
     */
    public String getYyrqStart() {
        return yyrqStart;
    }

    /**
     * @param 对yyrqStart进行赋值
     */
    public void setYyrqStart(String yyrqStart) {
        this.yyrqStart = yyrqStart;
    }

    /**
     * @return 返回 yyrqEnd
     */
    public String getYyrqEnd() {
        return yyrqEnd;
    }

    /**
     * @param 对yyrqEnd进行赋值
     */
    public void setYyrqEnd(String yyrqEnd) {
        this.yyrqEnd = yyrqEnd;
    }

}
