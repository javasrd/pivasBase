package com.zc.pivas.app.bean;

import java.util.Date;

/**
 * APP请求交互bean
 *
 * @author kunkka
 * @version 1.0
 */
public class BottleInfo {
    public BottleInfo() {
        bottleNum = "";
        wardName = "";
        patName = "";
        sex = -1;
        bedNo = "";
        supplyCode = "";
        age = "";
        ageUnit = -1;
        doctorAdviceType = -1;
        IsHard = -1;
        medicName = "";
        medicDose = "";
        medicNum = "";
        parentNo = "";
        batchID = "";
        batchName = "";
        medicSpec = "";
        medicDoseUnit = "";
        medicNumUnit = "";
        wardCode = "";
    }

    public String bottleNum;
    public String wardName;
    public Date medicationTime;
    public String patName;
    public int sex;
    public String bedNo;
    public String supplyCode;
    public String age;
    public int ageUnit;
    public int doctorAdviceType;
    public int ret;
    public String msg;
    public int IsHard;
    public String medicName;
    public String medicDose;
    public String medicNum;
    public String parentNo;
    public String batchID;
    public String batchName;
    public Long medicamentsID;
    public String medicSpec;
    public String medicDoseUnit;
    public String medicNumUnit;
    public String wardCode;
}