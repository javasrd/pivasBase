package com.zc.pivas.medicaments.entity;

import com.zc.base.common.dao.AbstractDO;

import java.util.Arrays;
import java.util.List;


/**
 * 药品实体类
 *
 * @author Ray
 * @version 1.0
 */
public class Medicaments extends AbstractDO {

    private static final long serialVersionUID = 1L;

    /**
     * 药品ID
     */
    private Long medicamentsId;

    private String medicamentIDs;

    private List<String> medicamentIDList;

    /**
     * 药品名称
     */
    private String medicamentsName;

    private String[] medicamentsNames;

    /**
     * 药品编码
     */
    private String medicamentsCode;

    /**
     * 药品编码
     */
    private String[] medicamentsCodes;

    /**
     * 药品分类ID
     */
    private String categoryId;

    /**
     * 药品速查码
     */
    private String medicamentsSuchama;

    /**
     * 药品速查码
     */
    private String[] medicamentsSuchamas;

    /**
     * 药品规格
     */
    private String medicamentsSpecifications;

    /**
     * 药品体积
     */
    private String medicamentsVolume;

    /**
     * 药品体积单位
     */
    private String medicamentsVolumeUnit;

    /**
     * 药品使用次剂量
     */
    private String medicamentsDosage;

    /**
     * 药品使用次剂量单位
     */
    private String medicamentsDosageUnit;

    /**
     * 包装单位
     */
    private String medicamentsPackingUnit;

    /**
     * 皮试标志 0不需要,1需要
     */
    private Integer medicamentsTestFlag;

    /**
     * 药品产地
     */
    private String medicamentsPlace;

    private String[] medicamentsPlaces;

    /**
     * 单价
     */
    private String medicamentsPrice;

    /**
     * 是否溶媒 0不是溶媒,1是溶媒
     */
    private Integer medicamentsMenstruum;

    /**
     * 是否可用 0不可用,1可用
     */
    private Integer medicamentsIsactive;

    /**
     * 是否做主药 1不是主药,0主药
     */
    private Integer medicamentsIsmaindrug;

    /**
     * 药品关联的标签ID
     */
    private Long[] labelIds;

    /**
     * 药品关联的标签名
     */
    private String labelNames;

    /**
     * 药品分类名称
     */
    private String categoryName;

    private String[] categoryNames;

    private Integer medicamentsDanger;

    /**
     * 药品产地编码
     */
    private String medicamentsPlaceCode;

    /**
     * 货架号
     */
    private String shelfNo;

    /**
     * 药品别名
     */
    private String aliasName;

    /**
     * 单位转换前的值
     */
    private String unitChangeBefore;

    /**
     * 单位转换后的值
     */
    private String unitChangeAfter;

    /**
     * 药品库存
     */
    private String medicamentsStock;

    /**
     * 药品消耗量
     */
    private String consumption;

    /**
     * 药品消耗量:实际使用
     */
    private String used;

    /**
     * 对应上周药品消耗量
     */
    private String lastWeekUsed;

    /**
     * 前一天药品消耗量
     */
    private String lastDayUsed;

    /**
     * 库存修改人
     */
    private String operator;

    /**
     * 库存量超过最低限制0：未1：是
     */
    private String medicamentsOver;

    /**
     * 错误记录
     */
    private String operatorLog;

    /**
     * 条件查询，是否有库存0：有 1：无
     */
    private String stockStatus;

    /**
     * 过滤病区下的药品
     */
    private String filterMedicArea;

    /**
     * 过滤病区下的药品:类型
     */
    private Integer prType;

    /**
     * 药品库存下限
     */
    private String medicamentsLimit;

    /**
     * 有效期
     */
    private String effective_date;

    /**
     * 药品配置难度系数
     */
    private String difficulty_degree;

    /**
     * 使用信息：用于打印瓶签
     */
    private String useInfo;

    /**
     * 用药日期
     */
    private String yyrq;

    /**
     * 分类瓶签编码
     */
    private String categoryCode;

    /**
     * 药理作用
     */
    private String phyFunctiy;

    private String[] phyFunctiys;

    public String getPhyFunctiy() {
        return phyFunctiy;
    }

    public void setPhyFunctiy(String phyFunctiy) {
        this.phyFunctiy = phyFunctiy;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getLastWeekUsed() {
        return lastWeekUsed;
    }

    public void setLastWeekUsed(String lastWeekUsed) {
        this.lastWeekUsed = lastWeekUsed;
    }

    public String getLastDayUsed() {
        return lastDayUsed;
    }

    public void setLastDayUsed(String lastDayUsed) {
        this.lastDayUsed = lastDayUsed;
    }

    public String getYyrq() {
        return yyrq;
    }

    public void setYyrq(String yyrq) {
        this.yyrq = yyrq;
    }

    public String getMedicamentIDs() {
        return medicamentIDs;
    }

    public void setMedicamentIDs(String medicamentIDs) {
        this.medicamentIDs = medicamentIDs;
    }

    public List<String> getMedicamentIDList() {
        return medicamentIDList;
    }

    public void setMedicamentIDList(List<String> medicamentIDList) {
        this.medicamentIDList = medicamentIDList;
    }

    public String getUseInfo() {
        return useInfo;
    }

    public void setUseInfo(String useInfo) {
        this.useInfo = useInfo;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getDifficulty_degree() {
        return difficulty_degree;
    }

    public void setDifficulty_degree(String difficulty_degree) {
        this.difficulty_degree = difficulty_degree;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getMedicamentsLimit() {
        return medicamentsLimit;
    }

    public void setMedicamentsLimit(String medicamentsLimit) {
        this.medicamentsLimit = medicamentsLimit;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getOperatorLog() {
        return operatorLog;
    }

    public void setOperatorLog(String operatorLog) {
        this.operatorLog = operatorLog;
    }

    public String getMedicamentsOver() {
        return medicamentsOver;
    }

    public void setMedicamentsOver(String medicamentsOver) {
        this.medicamentsOver = medicamentsOver;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMedicamentsStock() {
        return medicamentsStock;
    }

    public void setMedicamentsStock(String medicamentsStock) {
        this.medicamentsStock = medicamentsStock;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getUnitChangeBefore() {
        return unitChangeBefore;
    }

    public void setUnitChangeBefore(String unitChangeBefore) {
        this.unitChangeBefore = unitChangeBefore;
    }

    public String getUnitChangeAfter() {
        return unitChangeAfter;
    }

    public void setUnitChangeAfter(String unitChangeAfter) {
        this.unitChangeAfter = unitChangeAfter;
    }

    public String getMedicamentsPlaceCode() {
        return medicamentsPlaceCode;
    }

    public void setMedicamentsPlaceCode(String medicamentsPlaceCode) {
        this.medicamentsPlaceCode = medicamentsPlaceCode;
    }

    public Long getMedicamentsId() {
        return medicamentsId;
    }

    public void setMedicamentsId(Long medicamentsId) {
        this.medicamentsId = medicamentsId;
    }

    public String getMedicamentsName() {
        return medicamentsName;
    }

    public void setMedicamentsName(String medicamentsName) {
        this.medicamentsName = medicamentsName == null ? null : medicamentsName.trim();
    }

    public String getMedicamentsCode() {
        return medicamentsCode;
    }

    public void setMedicamentsCode(String medicamentsCode) {
        this.medicamentsCode = medicamentsCode == null ? null : medicamentsCode.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMedicamentsSuchama() {
        return medicamentsSuchama;
    }

    public void setMedicamentsSuchama(String medicamentsSuchama) {
        this.medicamentsSuchama = medicamentsSuchama == null ? null : medicamentsSuchama.trim();
    }

    public String getMedicamentsSpecifications() {
        return medicamentsSpecifications;
    }

    public void setMedicamentsSpecifications(String medicamentsSpecifications) {
        this.medicamentsSpecifications = medicamentsSpecifications == null ? null : medicamentsSpecifications.trim();
    }

    public String getMedicamentsVolume() {
        return medicamentsVolume;
    }

    public void setMedicamentsVolume(String medicamentsVolume) {
        this.medicamentsVolume = medicamentsVolume == null ? null : medicamentsVolume.trim();
    }

    public String getMedicamentsVolumeUnit() {
        return medicamentsVolumeUnit;
    }

    public void setMedicamentsVolumeUnit(String medicamentsVolumeUnit) {
        this.medicamentsVolumeUnit = medicamentsVolumeUnit == null ? null : medicamentsVolumeUnit.trim();
    }

    public String getMedicamentsDosage() {
        return medicamentsDosage;
    }

    public void setMedicamentsDosage(String medicamentsDosage) {
        this.medicamentsDosage = medicamentsDosage == null ? null : medicamentsDosage.trim();
    }

    public String getMedicamentsDosageUnit() {
        return medicamentsDosageUnit;
    }

    public void setMedicamentsDosageUnit(String medicamentsDosageUnit) {
        this.medicamentsDosageUnit = medicamentsDosageUnit == null ? null : medicamentsDosageUnit.trim();
    }

    public String getMedicamentsPackingUnit() {
        return medicamentsPackingUnit;
    }

    public void setMedicamentsPackingUnit(String medicamentsPackingUnit) {
        this.medicamentsPackingUnit = medicamentsPackingUnit == null ? null : medicamentsPackingUnit.trim();
    }

    public Integer getMedicamentsTestFlag() {
        return medicamentsTestFlag;
    }

    public void setMedicamentsTestFlag(Integer medicamentsTestFlag) {
        this.medicamentsTestFlag = medicamentsTestFlag;
    }

    public String getMedicamentsPlace() {
        return medicamentsPlace;
    }

    public void setMedicamentsPlace(String medicamentsPlace) {
        this.medicamentsPlace = medicamentsPlace == null ? null : medicamentsPlace.trim();
    }

    public String getMedicamentsPrice() {
        return medicamentsPrice;
    }

    public void setMedicamentsPrice(String medicamentsPrice) {
        this.medicamentsPrice = medicamentsPrice == null ? null : medicamentsPrice.trim();
    }

    public Integer getMedicamentsMenstruum() {
        return medicamentsMenstruum;
    }

    public void setMedicamentsMenstruum(Integer medicamentsMenstruum) {
        this.medicamentsMenstruum = medicamentsMenstruum;
    }

    public Integer getMedicamentsIsactive() {
        return medicamentsIsactive;
    }

    public void setMedicamentsIsactive(Integer medicamentsIsactive) {
        this.medicamentsIsactive = medicamentsIsactive;
    }

    public Integer getMedicamentsIsmaindrug() {
        return medicamentsIsmaindrug;
    }

    public void setMedicamentsIsmaindrug(Integer medicamentsIsmaindrug) {
        this.medicamentsIsmaindrug = medicamentsIsmaindrug;
    }

    public Long[] getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(Long[] labelIds) {
        this.labelIds = labelIds;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String[] getMedicamentsNames() {
        return medicamentsNames;
    }

    public void setMedicamentsNames(String[] medicamentsNames) {
        this.medicamentsNames = medicamentsNames;
    }

    public String[] getMedicamentsPlaces() {
        return medicamentsPlaces;
    }

    public void setMedicamentsPlaces(String[] medicamentsPlaces) {
        this.medicamentsPlaces = medicamentsPlaces;
    }

    public String[] getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String[] categoryNames) {
        this.categoryNames = categoryNames;
    }

    public Integer getMedicamentsDanger() {
        return medicamentsDanger;
    }

    public void setMedicamentsDanger(Integer medicamentsDanger) {
        this.medicamentsDanger = medicamentsDanger;
    }

    public String getFilterMedicArea() {
        return filterMedicArea;
    }

    public void setFilterMedicArea(String filterMedicArea) {
        this.filterMedicArea = filterMedicArea;
    }

    public Integer getPrType() {
        return prType;
    }

    public void setPrType(Integer prType) {
        this.prType = prType;
    }

    public String[] getMedicamentsCodes() {
        return medicamentsCodes;
    }

    public void setMedicamentsCodes(String[] medicamentsCodes) {
        this.medicamentsCodes = medicamentsCodes;
    }

    public String[] getMedicamentsSuchamas() {
        return medicamentsSuchamas;
    }

    public void setMedicamentsSuchamas(String[] medicamentsSuchamas) {
        this.medicamentsSuchamas = medicamentsSuchamas;
    }

    @Override
    public String toString() {
        return "Medicaments [medicamentsId=" + medicamentsId + ", medicamentsName=" + medicamentsName + ", medicamentsNames=" + Arrays.toString(medicamentsNames) + ", medicamentsCode=" + medicamentsCode + ", categoryId=" + categoryId + ", medicamentsSuchama=" + medicamentsSuchama + ", medicamentsSpecifications=" + medicamentsSpecifications + ", medicamentsVolume=" + medicamentsVolume + ", medicamentsVolumeUnit=" + medicamentsVolumeUnit + ", medicamentsDosage=" + medicamentsDosage + ", medicamentsDosageUnit=" + medicamentsDosageUnit + ", medicamentsPackingUnit=" + medicamentsPackingUnit + ", medicamentsTestFlag=" + medicamentsTestFlag + ", medicamentsPlace=" + medicamentsPlace + ", medicamentsPlaces=" + Arrays.toString(medicamentsPlaces) + ", medicamentsPrice=" + medicamentsPrice + ", medicamentsMenstruum=" + medicamentsMenstruum + ", medicamentsIsactive=" + medicamentsIsactive + ", medicamentsIsmaindrug=" + medicamentsIsmaindrug + ", labelIds=" + Arrays.toString(labelIds) + ", labelNames=" + labelNames + ", categoryName=" + categoryName + ", medicamentsDanger=" + medicamentsDanger + ", categoryNames=" + Arrays.toString(categoryNames) + "]";
    }

    /**
     * @return 返回 phyFunctiys
     */
    public String[] getPhyFunctiys() {
        return phyFunctiys;
    }

    /**
     * @param phyFunctiys 对phyFunctiys进行赋值
     */
    public void setPhyFunctiys(String[] phyFunctiys) {
        this.phyFunctiys = phyFunctiys;
    }

}