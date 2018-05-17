package com.zc.pivas.common.util;

import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.NumberUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.bean.Frequency;
import com.zc.pivas.docteradvice.entity.*;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.excuteRecord.service.SynYdRecordService;
import com.zc.pivas.patient.bean.PatientBean;
import com.zc.pivas.patient.service.PatientService;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 医嘱工具类
 *
 * @author  cacabin
 * @version  1.0
 */
public class DoctorAdviceTool
{
    
    // opRecord表示执行记录 drugTimes表示用药次数
    static String config_firstDayYZBaseOn = Propertiesconfiguration.getStringProperty("sysconfig.firstDayYZBaseOn");
    
    final static String SPLIT_MODE = Propertiesconfiguration.getStringProperty("pivas.yz.split.mode");
    
    final static String SPLIT_rule = Propertiesconfiguration.getStringProperty("pivas.ydzxjl.rule");
    
    final static String SPLIT_yysj = Propertiesconfiguration.getStringProperty("pivas.ydzxjl.yysj");
    
    final static String SPLIT_yyxx = Propertiesconfiguration.getStringProperty("pivas.ydzxjl.yyxx");
    
    /**
     * 
     * 初始化医嘱查询参数
     * 初始化医嘱查询参数
     * @param param
     * @return
     */
    public static Map<String, Object> initYZParam(Map<String, Object> param) {
        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        if (bednoStr != null) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoS = bednoStr.split(",");
            param.put("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        if (patnameStr != null) {
            patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
            String[] patnameS = patnameStr.split(",");
            param.put("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        if (parentNoStr != null) {
            parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
            String[] parentNoS = parentNoStr.split(",");
            param.put("parentNoS", parentNoS);
        }
        String wardNameStr = StrUtil.getObjStr(param.get("wardNameS"));
        if (wardNameStr != null) {
            wardNameStr = DefineStringUtil.escapeAllLike(wardNameStr);
            String[] wardNameS = wardNameStr.split(",");
            param.put("wardNameS", wardNameS);
        }
        String freqCodeStr = StrUtil.getObjStr(param.get("freqCodeS"));
        if (freqCodeStr != null) {
            freqCodeStr = DefineStringUtil.escapeAllLike(freqCodeStr);
            String[] freqCodeS = freqCodeStr.split(",");
            param.put("freqCodeS", freqCodeS);
        }
        String patNamesStr = StrUtil.getObjStr(param.get("patNames"));
        if (patNamesStr != null) {
            String[] patNames = patNamesStr.split(",");
            param.put("patNames", patNames);
        }
        String drugnamesStr = StrUtil.getObjStr(param.get("drugnames"));
        if (drugnamesStr != null) {
            String[] drugnames = drugnamesStr.split(",");
            param.put("drugnames", drugnames);
        }
        String drugnameQryStrs = StrUtil.getObjStr(param.get("drugnameQry"));
        if (drugnameQryStrs != null) {
            drugnameQryStrs = DefineStringUtil.escapeAllLike(drugnameQryStrs);
            String[] drugnameQry = drugnameQryStrs.split(",");
            param.put("drugnameQry", drugnameQry);
        }
        String doctorsStr = StrUtil.getObjStr(param.get("doctors"));
        if (doctorsStr != null) {
            String[] doctors = doctorsStr.split(",");
            param.put("doctors", doctors);
        }
        return param;
    }
    
    /**
     * 
     * 根据医嘱数据生成药单数据
     * 根据医嘱数据生成药单数据
     * @param yzMain
     * @param newYzshzt
     * @param pcListALL
     * @param pidPC_bottLab
     * @param pcListOK
     * @param scrqD
     * @param pidsjRuCangMap
     * @param yyrqD
     * @return
     * @throws Exception 
     */
    public static List<PrescriptionMain> genYDMainList(DoctorAdviceMain yzMain, Integer newYzshzt, List<Frequency> pcListALL,
                                                       Map<String, String> pidPC_bottLab, Map<String, Object> pidsjRuCangMap, List<Frequency> pcListOK, Date scrqD, Date yyrqD)
        throws Exception
    {
        PatientService patientService = ServletContextUtil.patientService;
        PatientBean patientBean = new PatientBean();
        
        // 病人预出院状态
        String patientState = "";
        
        // 预出院时间
        Date hosOutTime = null;
        // 住院流水号
        patientBean.setInhospNos(new String[] {yzMain.getInpatientNo()});
        
        // 判断病人是否第二日出院:依据状态为预出院，且当前出院时间为第二天
        boolean patientIsOut = false;
        
        // 获取医嘱对应病人的预出院状态
        List<PatientBean> patientList = patientService.qryPatientList(patientBean);

        if (DefineCollectionUtil.isNotEmpty(patientList)) {
            patientState = patientList.get(0).getState();

            // -1:预出院
            if ("-1".equals(patientState) && null != patientList.get(0).getHosOutTime()
                    && !"".equals(patientList.get(0).getHosOutTime())) {
                hosOutTime = DateUtil.getDateFromYYYYMMDD(patientList.get(0).getHosOutTime());

                DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
                Date yyrqDay = datef.parse(datef.format(yyrqD) + " 00:00:00");//datef.parse(datef.format(new Date())+" 00:00:00")
                if (yyrqDay.compareTo(hosOutTime) >= 0) {
                    patientIsOut = true;
                }
            }
            // 1:已出院
            if ("1".equals(patientState)) {
                patientIsOut = true;
            }
        }

        Collections.sort(pcListALL, new Comparator<Frequency>() {
            public int compare(Frequency pc1, Frequency pc2) {
                //如果 两个批次比较，将后面的批次放到前面，倒序排列
                if (pc1.getOrder_num() > pc2.getOrder_num()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        
        PrescriptionMain ydMain = new PrescriptionMain();
        Frequency pcTemp = new Frequency();
        List<PrescriptionMain> ydMainList = new ArrayList<PrescriptionMain>();
        if (yzMain != null && pcListALL != null && !patientIsOut) {
            int pcNum = pcListALL.size();
            String dayNowS = DateUtil.getYYYYMMDDDate(new Date());
            //生成当日药单
            if (DateUtil.getYYYYMMDDDate(yzMain.getStartTime()).equals(dayNowS)
                    && DateUtil.getYYYYMMDDDate(scrqD).equals(dayNowS)) {
                switch (SPLIT_MODE) {
                    case "ybgz":
                        if ("Y".equals(config_firstDayYZBaseOn)) {
                            String firstUseCountS = yzMain.getFirstUseCount();
                            pcNum =
                                    NumberUtil.getObjInt(firstUseCountS, 0) > pcNum ? pcNum
                                            : NumberUtil.getObjInt(firstUseCountS, 0);
                        }

                        if (pcNum > 0) {
                            for (int i = 0; i < pcNum; i++) {
                                //一般规则的批次
                                pcTemp = pcListALL.get(i);

                                Boolean result =
                                        createPC(pcTemp,
                                                yzMain,
                                                ydMain,
                                                pidsjRuCangMap,
                                                newYzshzt,
                                                scrqD,
                                                yyrqD,
                                                ydMainList,
                                                pcListOK,
                                                pidPC_bottLab,
                                                null);

                                if (!result) {
                                    continue;
                                }
                            }
                        }

                        break;
                    case "ydzxjl":
                        return buildYdMainListBaseOnOpRecord(pcListALL,
                                yzMain,
                                scrqD,
                                yyrqD,
                                pidPC_bottLab,
                                pidsjRuCangMap,
                                pcListOK,
                                newYzshzt);
                    default:
                        break;
                }

            } else {//生成明日药单

                if ("ydzxjl".equals(SPLIT_MODE)) {

                    return buildYdMainListBaseOnOpRecord(pcListALL,
                            yzMain,
                            scrqD,
                            yyrqD,
                            pidPC_bottLab,
                            pidsjRuCangMap,
                            pcListOK,
                            newYzshzt);

                } else if ("ybgz".equals(SPLIT_MODE)) {

                    if (pcNum > 0) {
                        for (int i = 0; i < pcNum; i++) {
                            //一般规则的批次
                            pcTemp = pcListALL.get(i);

                            Boolean result =
                                    createPC(pcTemp,
                                            yzMain,
                                            ydMain,
                                            pidsjRuCangMap,
                                            newYzshzt,
                                            scrqD,
                                            yyrqD,
                                            ydMainList,
                                            pcListOK,
                                            pidPC_bottLab,
                                            null);

                            if (!result) {
                                continue;
                            }

                        }
                    }

                }
            }

        }
        return ydMainList;
    }
    
    /**
     * 
     * 根据执行记录生成药单数据
     * @param pcListALL
     * @param yzMain 
     * @param pidPC_bottLab
     * @param newYzshzt
     * @param pidPC_bottLab
     * @param pcListOK 
     * @param pidsjRuCangMap 
     * @param pcListOK
     * @param newYzshzt 
     * @return
     */
    public static List<PrescriptionMain> buildYdMainListBaseOnOpRecord(List<Frequency> pcListALL, DoctorAdviceMain yzMain, Date scrqD, Date yyrqD,
                                                                       Map<String, String> pidPC_bottLab, Map<String, Object> pidsjRuCangMap, List<Frequency> pcListOK, Integer newYzshzt)
    {
        
        List<PrescriptionMain> ydMainList = new ArrayList<PrescriptionMain>();
        
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
        BatchService batchService = ServletContextUtil.batchService;
        SynYdRecordService synYdRecordService = ServletContextUtil.synYdRecordService;
        if (yzMainService == null || ydMainService == null || batchService == null || synYdRecordService == null) {

            return new ArrayList<PrescriptionMain>();
        }

        List<BatchBean> batchList = ydMainService.getCustomBatch();
        List<ExcuteRecordBean> record = new ArrayList<ExcuteRecordBean>();

        PrescriptionMain ydMain = null;

        int pcNum = pcListALL.size();

        if ("allDay".equals(SPLIT_rule)) {

            record = synYdRecordService.getRecordAllDay(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo());

            if (record == null || record.size() == 0) {
                return ydMainList;
            }

            String acount = record.get(0).getUseCount();
            if (StringUtils.isBlank(acount)) {
                return ydMainList;
            }
            
            int acountNum = Integer.valueOf(acount);
            
            //药单执行记录次数等于或小于一般规则次数
            if (acountNum <= pcNum) {
                
                for (int i = 0; i < acountNum; i++) {
                    //一般规则的批次
                    Frequency pcTemp = pcListALL.get(i);
                    Boolean result = createPC(pcTemp,yzMain,ydMain,pidsjRuCangMap,newYzshzt,scrqD,yyrqD,ydMainList,pcListOK,pidPC_bottLab,record);
                    if (!result) {
                        continue;
                    }
                }
            } else {
                for (int i = 0; i < pcNum; i++) {
                    //一般规则的批次
                    Frequency pcTemp = pcListALL.get(i);
                    Boolean result = createPC(pcTemp,yzMain,ydMain,pidsjRuCangMap,newYzshzt,scrqD,yyrqD,ydMainList,pcListOK,pidPC_bottLab,record);
                    if (!result){
                        continue;
                    }
                }
                
                if (batchList == null || batchList.size() == 0){
                    return ydMainList;
                }
                
                int difference = acountNum - pcNum;
                if (difference > 5) {
                    difference = 5;
                }
                for (int i = 0; i < difference; i++) {
                    BatchBean bacth = batchList.get(i);
                    int time = pcNum + i + 1;
                    Boolean result = createPCCustom(bacth,yzMain,ydMain,pidsjRuCangMap,newYzshzt,scrqD,yyrqD,ydMainList,pcListOK,pidPC_bottLab,time,record);
                    if (!result) {
                        continue;
                    }
                }
                
            }
            
        } else if ("split".equals(SPLIT_rule)) {
            
            List<ExcuteRecordBean> recordYY = synYdRecordService.getRecordYyDetail(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo());
            if ("exist".equals(SPLIT_yysj)) {
                
                record = synYdRecordService.getRecordSplitExistYy(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo());
                
                if (record == null || record.size() == 0) {
                    return ydMainList;
                }
                
                for (ExcuteRecordBean recordBean : record) {

                    String pcId = recordBean.getBatchID();
                    if (StringUtils.isBlank(pcId)) {
                        continue;
                    }
                    BatchBean batchBean = ydMainService.getBatchByID(pcId);
                    if (batchBean == null) {
                        continue;
                    }

                    int time = Integer.valueOf(recordBean.getSchedule());

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String yyrqStr = recordBean.getOccDT();
                    try {
                        yyrqD = formatter.parse(yyrqStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Boolean result =
                            createPCCustom(batchBean,
                                    yzMain,
                                    ydMain,
                                    pidsjRuCangMap,
                                    newYzshzt,
                                    scrqD,
                                    yyrqD,
                                    ydMainList,
                                    pcListOK,
                                    pidPC_bottLab,
                                    time,
                                    recordYY);
                    if (!result) {
                        continue;
                    }
                }

            } else {

                record = synYdRecordService.getRecordSplit(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo());

                if (record == null || record.size() == 0) {
                    return ydMainList;
                }
                
                int recordNum = record.size();
                
                //药单执行记录次数等于或小于一般规则次数
                if (recordNum <= pcNum)
                {
                    
                    for (int i = 0; i < recordNum; i++)
                    {
                        //一般规则的批次
                        Frequency pcTemp = pcListALL.get(i);
                        
                        Boolean result =
                            createPC(pcTemp,
                                yzMain,
                                ydMain,
                                pidsjRuCangMap,
                                newYzshzt,
                                scrqD,
                                yyrqD,
                                ydMainList,
                                pcListOK,
                                pidPC_bottLab,
                                recordYY);

                        if (!result) {
                            continue;
                        }

                    }

                } else {

                    for (int i = 0; i < pcNum; i++) {
                        //一般规则的批次
                        Frequency pcTemp = pcListALL.get(i);
                        Boolean result =
                                createPC(pcTemp,
                                        yzMain,
                                        ydMain,
                                        pidsjRuCangMap,
                                        newYzshzt,
                                        scrqD,
                                        yyrqD,
                                        ydMainList,
                                        pcListOK,
                                        pidPC_bottLab,
                                        recordYY);

                        if (!result) {
                            continue;
                        }

                    }
                    
                    if (batchList == null || batchList.size() == 0)
                    {
                        return ydMainList;
                    }
                    
                    int difference = recordNum - pcNum;
                    if (difference > 5)
                    {
                        difference = 5;
                    }
                    for (int i = 0; i < difference; i++) {

                        BatchBean bacth = batchList.get(i);
                        int time = pcNum + i + 1;

                        Boolean result =
                                createPCCustom(bacth,
                                        yzMain,
                                        ydMain,
                                        pidsjRuCangMap,
                                        newYzshzt,
                                        scrqD,
                                        yyrqD,
                                        ydMainList,
                                        pcListOK,
                                        pidPC_bottLab,
                                        time,
                                        recordYY);
                        if (!result) {
                            continue;
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        return ydMainList;
    }

    private static Boolean createPCCustom(BatchBean bacth, DoctorAdviceMain yzMain, PrescriptionMain ydMain,
                                          Map<String, Object> pidsjRuCangMap, Integer newYzshzt, Date scrqD, Date yyrqD, List<PrescriptionMain> ydMainList,
                                          List<Frequency> pcListOK, Map<String, String> pidPC_bottLab, int time, List<ExcuteRecordBean> record) {

        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        if (yzMainService == null) {
            return false;
        }

        Frequency pcTemp = new Frequency();
        pcTemp.setPc_id(bacth.getId_());
        pcTemp.setPc_secend(bacth.getIs_secend_advice_());
        pcTemp.setPc_0p(bacth.getIs0p());
        pcTemp.setPc_empty(bacth.getIs_empty_batch_());
        pcTemp.setPc_name(bacth.getName_());
        pcTemp.setPc_num(bacth.getNum_());
        pcTemp.setRu_serialNum(time);

        if (pidsjRuCangMap != null
                && pidsjRuCangMap.get(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD)
                + "_" + pcTemp.getPc_id().intValue()) != null) {
            return false;
        }

        ydMain = new PrescriptionMain(yzMain, scrqD, yyrqD);
        ydMain.setYzshzt(newYzshzt);
        ydMain.setSerialNumber(NumUtil.getObjInt(pcTemp.getRu_serialNum()));
        ydMain.setZxbc(pcTemp.getPc_id().intValue());
        ydMain.setPidrqzxbc(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                + pcTemp.getPc_id().intValue());
        ydMain.setPidsj(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                + pcTemp.getPc_id().intValue());

        if ("exist".equals(SPLIT_yyxx) && record != null && record.size() != 0) {

            StringBuilder drugCode = new StringBuilder();
            StringBuilder drugName = new StringBuilder();
            StringBuilder dose = new StringBuilder();
            StringBuilder doseUnit = new StringBuilder();
            StringBuilder quantity = new StringBuilder();
            StringBuilder packUnit = new StringBuilder();

            ExcuteRecordBean detail = new ExcuteRecordBean();

            for (int i = 0; i < record.size(); i++) {

                detail = record.get(i);

                List<DoctorAdvice> yzDetail = yzMainService.getYZDetail(detail.getGroupNo(), detail.getDrugCode());
                if (yzDetail == null || yzDetail.size() != 1) {
                    continue;
                }

                drugCode.append(detail.getDrugCode() == null ? "" : detail.getDrugCode());
                drugName.append(detail.getDrugName() == null ? "" : detail.getDrugName());
                quantity.append(detail.getQuantity() == null ? "" : detail.getQuantity());
                packUnit.append(detail.getQuantityUnit() == null ? "" : detail.getQuantityUnit());

                dose.append(yzDetail.get(0).getDose() == null ? "" : yzDetail.get(0).getDose());
                doseUnit.append(yzDetail.get(0).getDoseUnit() == null ? "" : yzDetail.get(0).getDoseUnit());
                //packUnit.append(yzDetail.get(0).getMedicamentsPackingUnit()==null?"":yzDetail.get(0).getMedicamentsPackingUnit());

                if (i < record.size() - 1) {
                    drugCode.append("@@");
                    drugName.append("@@");
                    quantity.append("@@");
                    dose.append("@@");
                    doseUnit.append("@@");
                    packUnit.append("@@");
                }

            }

            String bedNo = record.get(0).getBedNo();
            if (StringUtils.isNotBlank(bedNo)) {
                ydMain.setBedno(bedNo);
            }
            String patname = record.get(0).getPatName();
            if (StringUtils.isNotBlank(patname)) {
                ydMain.setPatname(patname);
            }

            ydMain.setChargeCode(drugCode.toString().trim());
            ydMain.setMedicamentsCode(drugCode.toString().trim());
            ydMain.setDrugname(drugName.toString().trim());
            ydMain.setQuantity(quantity.toString().trim());
            ydMain.setDose(dose.toString().trim());
            ydMain.setDoseUnit(doseUnit.toString().trim());
            ydMain.setMedicamentsPackingUnit(packUnit.toString().trim());
            ydMain.setSpecifications(packUnit.toString().trim());

        }

        boolean f = true;
        for (PrescriptionMain ydMainTemp : ydMainList) {
            //防止pidsj重复，生成相同批次
            if (ydMainTemp.getPidsj().equals(ydMain.getPidsj())) {
                f = false;
            }
        }
        if (f) {
            ydMainList.add(ydMain);
            pcListOK.add(pcTemp);
            pidPC_bottLab.put(ydMain.getPidrqzxbc(), ydMain.getBottleLabelNum());
        }

        return f;
    }

    private static Boolean createPC(Frequency pcTemp, DoctorAdviceMain yzMain, PrescriptionMain ydMain, Map<String, Object> pidsjRuCangMap,
                                    Integer newYzshzt, Date scrqD, Date yyrqD, List<PrescriptionMain> ydMainList, List<Frequency> pcListOK,
                                    Map<String, String> pidPC_bottLab, List<ExcuteRecordBean> record) {

        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        if (yzMainService == null) {
            return false;
        }

        if (pcTemp == null) {
            return false;
        }
        if (pidsjRuCangMap != null
                && pidsjRuCangMap.get(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD)
                + "_" + pcTemp.getPc_id().intValue()) != null) {
            return false;
        }

        ydMain = new PrescriptionMain(yzMain, scrqD, yyrqD);
        ydMain.setYzshzt(newYzshzt);
        ydMain.setSerialNumber(NumUtil.getObjInt(pcTemp.getRu_serialNum()));
        ydMain.setZxbc(pcTemp.getPc_id().intValue());
        ydMain.setPidrqzxbc(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                + pcTemp.getPc_id().intValue());
        ydMain.setPidsj(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                + pcTemp.getPc_id().intValue());

        if ("exist".equals(SPLIT_yyxx) && record != null && record.size() != 0) {

            StringBuilder drugCode = new StringBuilder();
            StringBuilder drugName = new StringBuilder();
            StringBuilder dose = new StringBuilder();
            StringBuilder doseUnit = new StringBuilder();
            StringBuilder quantity = new StringBuilder();
            StringBuilder packUnit = new StringBuilder();

            ExcuteRecordBean detail = new ExcuteRecordBean();

            for (int i = 0; i < record.size(); i++) {

                detail = record.get(i);

                List<DoctorAdvice> yzDetail = yzMainService.getYZDetail(detail.getGroupNo(), detail.getDrugCode());
                if (yzDetail == null || yzDetail.size() != 1) {
                    continue;
                }

                drugCode.append(detail.getDrugCode() == null ? "" : detail.getDrugCode());
                drugName.append(detail.getDrugName() == null ? "" : detail.getDrugName());
                quantity.append(detail.getQuantity() == null ? "" : detail.getQuantity());
                packUnit.append(detail.getQuantityUnit() == null ? "" : detail.getQuantityUnit());

                dose.append(yzDetail.get(0).getDose() == null ? "" : yzDetail.get(0).getDose());
                doseUnit.append(yzDetail.get(0).getDoseUnit() == null ? "" : yzDetail.get(0).getDoseUnit());
                //packUnit.append(yzDetail.get(0).getMedicamentsPackingUnit()==null?"":yzDetail.get(0).getMedicamentsPackingUnit());

                if (i < record.size() - 1) {
                    drugCode.append("@@");
                    drugName.append("@@");
                    quantity.append("@@");
                    dose.append("@@");
                    doseUnit.append("@@");
                    packUnit.append("@@");
                }

            }

            ydMain.setChargeCode(drugCode.toString().trim());
            ydMain.setMedicamentsCode(drugCode.toString().trim());
            ydMain.setDrugname(drugName.toString().trim());
            ydMain.setQuantity(quantity.toString().trim());
            ydMain.setDose(dose.toString().trim());
            ydMain.setDoseUnit(doseUnit.toString().trim());
            ydMain.setMedicamentsPackingUnit(packUnit.toString().trim());
            ydMain.setSpecifications(packUnit.toString().trim());

        }

        boolean f = true;
        for (PrescriptionMain ydMainTemp : ydMainList) {
            //防止pidsj重复，生成相同批次
            if (ydMainTemp.getPidsj().equals(ydMain.getPidsj())) {
                f = false;
            }
        }
        if (f) {
            ydMainList.add(ydMain);
            pcListOK.add(pcTemp);
            pidPC_bottLab.put(ydMain.getPidrqzxbc(), ydMain.getBottleLabelNum());
        }

        return f;

    }
    
    /**
     * 
     * 根据医嘱数据生成药单子表数据
     * @param yz
     * @param newYzshzt
     * @param pidPC_bottLab
     * @param pcListOK
     * @return
     */
    public static List<Prescription> genYDList(DoctorAdvice yz, Integer newYzshzt, List<Frequency> pcListOK, Map<String, String> pidPC_bottLab,
                                               Date scrqD, Date yyrqD) {
        Prescription yd = null;
        List<Prescription> ydList = new ArrayList<Prescription>();

        SynYdRecordService synYdRecordService = ServletContextUtil.synYdRecordService;
        if (synYdRecordService == null) {
            return ydList;
        }

        if (yz != null && pcListOK != null && pcListOK.size() > 0) {
            for (Frequency pcTemp : pcListOK) {
                if ("ydzxjl".equals(SPLIT_MODE)) {

                    if ("exist".equals(SPLIT_yyxx)) {

                        List<ExcuteRecordBean> record =
                                synYdRecordService.getExcuteRecordBeanByPC(yz.getParentNo(),
                                        yz.getActOrderNo(),
                                        pcTemp.getPc_id(),
                                        yyrqD);
                        if (record == null || record.size() == 0) {
                            yd = new Prescription(yz, scrqD, yyrqD);
                        } else {

                            ExcuteRecordBean recordData = record.get(0);
                            String yyrqStr = recordData.getOccDT();
                            if (StringUtils.isNotBlank(yyrqStr)) {

                                Date yyDate = null;
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                try {
                                    yyDate = formatter.parse(yyrqStr);
                                    yd = new Prescription(yz, scrqD, yyDate, recordData);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    yd = new Prescription(yz, scrqD, yyrqD, recordData);
                                }

                            } else {
                                yd = new Prescription(yz, scrqD, yyrqD, recordData);
                            }

                        }

                    } else {
                        yd = new Prescription(yz, scrqD, yyrqD);
                    }

                } else {
                    yd = new Prescription(yz, scrqD, yyrqD);
                }
                yd.setYzshzt(newYzshzt);
                yd.setSerialNumber(NumUtil.getObjInt(pcTemp.getRu_serialNum()));
                yd.setZxbc(pcTemp.getPc_id().intValue());
                yd.setPidrqzxbc(yz.getParentNo() + "_" + yz.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                        + pcTemp.getPc_id().intValue());
                yd.setPidsj(yz.getParentNo() + "_" + yz.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                        + pcTemp.getPc_id().intValue());
                yd.setBottleLabelNum(pidPC_bottLab.get(yd.getPidrqzxbc()));
                ydList.add(yd);
            }
        }
        return ydList;
    }
}
