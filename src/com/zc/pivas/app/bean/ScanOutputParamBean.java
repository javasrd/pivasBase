package com.zc.pivas.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * APP请求交互bean
 *
 * @author kunkka
 * @version 1.0
 */
public class ScanOutputParamBean {
    public ScanOutputParamBean() {
        doctorAdviceStatus = -1;
        batchName = "";
        wardName = "";
        medicationTime = "";
        patName = "";
        sex = -1;
        bedNo = "";
        supplyCode = "";
        age = "";
        doctorAdviceType = -1;
        ret = -1;
        msg = "";
        isHard = -1;
        medicList = new ArrayList<ScanOutputParamBean.medicParam>();
    }

    public int doctorAdviceStatus;
    public String batchName;
    public String wardName;
    public String medicationTime;
    public String patName;
    public int sex;
    public String bedNo;
    public String supplyCode;
    public String age;
    public int doctorAdviceType;
    public int ret;
    public String msg;
    public int isHard;

    public List<medicParam> medicList;

    public class medicParam {
        public medicParam() {
            medicName = "";
            medicSpec = "";
            medicDose = "";
            medicDoseUnit = "";
            medicNum = "";
            medicNumUnit = "";
        }

        public String medicName;
        public String medicSpec;
        public String medicDose;
        public String medicDoseUnit;
        public String medicNum;
        public String medicNumUnit;
    }
}