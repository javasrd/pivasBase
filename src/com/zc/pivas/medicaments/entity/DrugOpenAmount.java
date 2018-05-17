package com.zc.pivas.medicaments.entity;

import com.zc.base.common.dao.AbstractDO;

/**
 * 药品拆药量实体类
 *
 * @author Ray
 * @version 1.0
 */
public class DrugOpenAmount extends AbstractDO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private long id;

    private String code;

    private String[] codes;

    /**
     * 药品名称
     */
    private String name;

    private String[] names;

    /**
     * 药品产地
     */
    private String place;

    private String[] places;

    /**
     * 药品产地编码
     */
    private String place_code;

    private String[] place_codes;

    /**
     * 拆药量
     */
    private String amount;

    /**
     * 拆药量
     */
    private String amountPlan;

    /**
     * 拆药量确认时间
     */
    private String openConfirmDate;

    /**
     * 拆药时间
     */
    private String openDate;

    /**
     * 拆药人
     */
    private String operator;

    private String[] operators;

    /**
     * 速查码
     */
    private String[] suchamas;

    public String getAmountPlan() {
        return amountPlan;
    }

    public void setAmountPlan(String amountPlan) {
        this.amountPlan = amountPlan;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public String[] getPlace_codes() {
        return place_codes;
    }

    public void setPlace_codes(String[] place_codes) {
        this.place_codes = place_codes;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String[] getPlaces() {
        return places;
    }

    public void setPlaces(String[] places) {
        this.places = places;
    }

    public String[] getOperators() {
        return operators;
    }

    public void setOperators(String[] operators) {
        this.operators = operators;
    }

    public String[] getSuchamas() {
        return suchamas;
    }

    public void setSuchamas(String[] suchamas) {
        this.suchamas = suchamas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace_code() {
        return place_code;
    }

    public void setPlace_code(String place_code) {
        this.place_code = place_code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOpenConfirmDate() {
        return openConfirmDate;
    }

    public void setOpenConfirmDate(String openConfirmDate) {
        this.openConfirmDate = openConfirmDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}