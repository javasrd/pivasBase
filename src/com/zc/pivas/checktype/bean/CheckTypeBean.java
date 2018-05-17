package com.zc.pivas.checktype.bean;

import java.io.Serializable;

/**
 * 核对类型实体类
 *
 * @author kunkka
 * @version 1.0
 */
public class CheckTypeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String gid;

    /**
     * 顺序id
     */
    private String orderID;

    /**
     * 核对步骤名
     */
    private String checkName;

    /**
     * 是否默认显示所有的瓶签（0：显示，1：否）
     */
    private int isShow;

    private String isShowResult;

    /**
     * 是否收取配置费（0：是，1：否）
     */
    private int isCharge;

    private String isChargeResult;

    /**
     * 是否生效等字段信息（0：是，1：否）
     */
    private Integer isEffect;

    private String isEffectResult;

    /**
     * 是否减少库存（0：是，1：否）
     */
    private int isStock;

    private String isStockResult;

    /**
     * 核对类型：0  入仓核对  1 出仓核对
     */
    private Integer checkType;

    /**
     * 核对类型名称：0  入仓核对  1 出仓核对
     */
    private String checkTypeName;

    /**
     * 预留字段0
     */
    private String reserve0;

    /**
     * 预留字段1
     */
    private String reserve1;

    /**
     * 预留字段2
     */
    private String reserve2;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getIsShowResult() {
        return isShowResult;
    }

    public void setIsShowResult(String isShowResult) {
        this.isShowResult = isShowResult;
    }

    public int getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(int isCharge) {
        this.isCharge = isCharge;
    }

    public String getIsChargeResult() {
        return isChargeResult;
    }

    public void setIsChargeResult(String isChargeResult) {
        this.isChargeResult = isChargeResult;
    }

    public String getIsEffectResult() {
        return isEffectResult;
    }

    public void setIsEffectResult(String isEffectResult) {
        this.isEffectResult = isEffectResult;
    }

    public int getIsStock() {
        return isStock;
    }

    public void setIsStock(int isStock) {
        this.isStock = isStock;
    }

    public String getIsStockResult() {
        return isStockResult;
    }

    public void setIsStockResult(String isStockResult) {
        this.isStockResult = isStockResult;
    }

    public String getReserve0() {
        return reserve0;
    }

    public void setReserve0(String reserve0) {
        this.reserve0 = reserve0;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect) {
        this.isEffect = isEffect;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getCheckTypeName() {
        return checkTypeName;
    }

    public void setCheckTypeName(String checkTypeName) {
        this.checkTypeName = checkTypeName;
    }

    @Override
    public String toString() {
        return "CheckTypeBean [checkName=" + checkName + ", checkType=" + checkType + ", checkTypeName="
                + checkTypeName + ", gid=" + gid + ", isCharge=" + isCharge + ", isChargeResult=" + isChargeResult
                + ", isEffect=" + isEffect + ", isEffectResult=" + isEffectResult + ", isShow=" + isShow
                + ", isShowResult=" + isShowResult + ", orderID=" + orderID + ", reserve0=" + reserve0 + ", reserve1="
                + reserve1 + ", reserve2=" + reserve2 + "]";
    }

}
