package com.zc.pivas.printlabel.entity;

/**
 * 打印瓶签  对应bean
 *
 * @author kunkka
 * @version 1.0
 */
public class PrintLabelBean extends BottleLabel {
    //是否空包
    private String isPack;

    //单个药品总数量
    private Integer singleDrugTotalCount;

    //起始页数
    private Integer printStartNumber;

    //结束页数
    private Integer printEndNumber;

    //摆药人
    private String putDrugPersonName;

    //排序方式
    private Integer sortType;

    //打印序号
    private String printIndex;

    //药单药数
    private Integer medicamentsCounts;

    //药单药数 字符串
    private String medicamentsCountsStr;

    //药单药数  数组
    private String[] medicamentsCountsArray;

    //快速查询用的
    private String medicTotal;

    //药品编码集合
    private String medicamentsCodeList;

    ///药品编码集合 数组
    private String[] medicamentsCodeListArray;

    //是否是重新打印
    private String printAgain;

    //药品标签名称
    private String medicamentsLableName;

    //药品名称
    private String drugNameList;

    //是否接着打印汇总单
    private boolean isPrintCollection = false;

    //药品标签数组
    private String[] medicamentLabelArray;

    //药品分类数组
    private String[] categoryIdArray;

    //是否是预览
    private Integer isPreview;

    private String bednoS;

    private String[] bednoArray;

    private String drugnameS;

    private String[] drugnameyArray;

    private Integer pidsjIndex;

    public Integer getPidsjIndex() {
        return pidsjIndex;
    }

    public void setPidsjIndex(Integer pidsjIndex) {
        this.pidsjIndex = pidsjIndex;
    }

    public String getIsPack() {
        return isPack;
    }

    public void setIsPack(String isPack) {
        this.isPack = isPack;
    }

    public Integer getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Integer isPreview) {
        this.isPreview = isPreview;
    }

    public String getDrugNameList() {
        return drugNameList;
    }

    public void setDrugNameList(String drugNameList) {
        this.drugNameList = drugNameList;
    }

    public String[] getMedicamentsCodeListArray() {
        return medicamentsCodeListArray;
    }

    public void setMedicamentsCodeListArray(String[] medicamentsCodeListArray) {
        this.medicamentsCodeListArray = medicamentsCodeListArray;
    }

    public String getMedicamentsCountsStr() {
        return medicamentsCountsStr;
    }

    public void setMedicamentsCountsStr(String medicamentsCountsStr) {
        this.medicamentsCountsStr = medicamentsCountsStr;
    }

    public String[] getMedicamentsCountsArray() {
        return medicamentsCountsArray;
    }

    public void setMedicamentsCountsArray(String[] medicamentsCountsArray) {
        this.medicamentsCountsArray = medicamentsCountsArray;
    }

    public String[] getCategoryIdArray() {
        return categoryIdArray;
    }

    public void setCategoryIdArray(String[] categoryIdArray) {
        this.categoryIdArray = categoryIdArray;
    }

    public String[] getMedicamentLabelArray() {
        return medicamentLabelArray;
    }

    public void setMedicamentLabelArray(String[] medicamentLabelArray) {
        this.medicamentLabelArray = medicamentLabelArray;
    }

    public boolean getIsPrintCollection() {
        return isPrintCollection;
    }

    public void setIsPrintCollection(boolean isPrintCollection) {
        this.isPrintCollection = isPrintCollection;
    }

    public String getMedicamentsLableName() {
        return medicamentsLableName;
    }

    public void setMedicamentsLableName(String medicamentsLableName) {
        this.medicamentsLableName = medicamentsLableName;
    }

    public String getPrintAgain() {
        return printAgain;
    }

    public void setPrintAgain(String printAgain) {
        this.printAgain = printAgain;
    }

    public String getMedicTotal() {
        return medicTotal;
    }

    public void setMedicTotal(String medicTotal) {
        this.medicTotal = medicTotal;
    }

    public Integer getSingleDrugTotalCount() {
        return singleDrugTotalCount;
    }

    public void setSingleDrugTotalCount(Integer singleDrugTotalCount) {
        this.singleDrugTotalCount = singleDrugTotalCount;
    }

    public Integer getPrintStartNumber() {
        return printStartNumber;
    }

    public void setPrintStartNumber(Integer printStartNumber) {
        this.printStartNumber = printStartNumber;
    }

    public Integer getPrintEndNumber() {
        return printEndNumber;
    }

    public void setPrintEndNumber(Integer printEndNumber) {
        this.printEndNumber = printEndNumber;
    }

    public String getPutDrugPersonName() {
        return putDrugPersonName;
    }

    public void setPutDrugPersonName(String putDrugPersonName) {
        this.putDrugPersonName = putDrugPersonName;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getPrintIndex() {
        return printIndex;
    }

    public void setPrintIndex(String printIndex) {
        this.printIndex = printIndex;
    }

    public Integer getMedicamentsCounts() {
        return medicamentsCounts;
    }

    public void setMedicamentsCounts(Integer medicamentsCounts) {
        this.medicamentsCounts = medicamentsCounts;
    }

    public String getMedicamentsCodeList() {
        return medicamentsCodeList;
    }

    public void setMedicamentsCodeList(String medicamentsCodeList) {
        this.medicamentsCodeList = medicamentsCodeList;
    }

    /**
     * @return 返回 bednoS
     */
    public String getBednoS() {
        return bednoS;
    }

    /**
     * @param 对bednoS进行赋值
     */
    public void setBednoS(String bednoS) {
        this.bednoS = bednoS;
    }

    /**
     * @return 返回 bednoArray
     */
    public String[] getBednoArray() {
        return bednoArray;
    }

    /**
     * @param 对bednoArray进行赋值
     */
    public void setBednoArray(String[] bednoArray) {
        this.bednoArray = bednoArray;
    }

    /**
     * @return 返回 drugnameS
     */
    public String getDrugnameS() {
        return drugnameS;
    }

    /**
     * @param 对drugnameS进行赋值
     */
    public void setDrugnameS(String drugnameS) {
        this.drugnameS = drugnameS;
    }

    /**
     * @return 返回 drugnameyArray
     */
    public String[] getDrugnameyArray() {
        return drugnameyArray;
    }

    /**
     * @param 对drugnameyArray进行赋值
     */
    public void setDrugnameyArray(String[] drugnameyArray) {
        this.drugnameyArray = drugnameyArray;
    }

}
