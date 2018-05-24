package com.zc.pivas.common.util;

import com.zc.base.common.util.DateUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.bean.Frequency;
import com.zc.pivas.docteradvice.bean.BottleLabel;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import com.zc.pivas.backup.bean.ServerNodeBean;
import com.zc.pivas.backup.service.ServerNodeService;
import com.zc.pivas.common.util.SysConstant.trackingRecord;
import com.zc.pivas.printlabel.service.PrintLabelService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * 
 * 药单生成工具类
 *
 * @author  cacabin
 * @version  1.0
 */
public class PrescriptionAutoGen extends QuartzJobBean {
    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(PrescriptionAutoGen.class);

    private static boolean fStatus = true;

    public final static String SPLIT_MODE = Propertiesconfiguration.getStringProperty("pivas.yz.split.mode");


    /**
     * 定时执行药单自动生成任务
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //备机状态下不执行任务
        ServerNodeService serverNodeService = ServletContextUtil.serverNodeService;
        if (serverNodeService != null) {
            String localip = Propertiesconfiguration.getStringProperty("localip");
            if (localip != null) {
                ServerNodeBean bean = serverNodeService.getServerNode(localip);
                if (bean != null) {
                    if (bean.getFlag() == 1) {
                        log.info("Backup mode,do not exec time task autoGenYDRun");
                        return;
                    }
                }
            }
        }

        log.info("Ready to exec time task autoGenYDRun");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        autoGenYDRun(c.getTime());

    }

    //生成批次 commented by jch 
    public static boolean genPC(Integer newYzshzt, List<DoctorAdviceMain> yzMainList, List<DoctorAdvice> yzList, List<String> pidsjRuCang,
                                Date scrqD, Date yyrqD, String account) {

        try {
            DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
            PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
            PrintLabelService printLabelService = ServletContextUtil.printLabelService;

            if (yzMainService == null || ydMainService == null || printLabelService == null) {

                return false;
            }

            //不带关键词的一般规则
            Map<String, List<Frequency>> map_NoKeyPC = new HashMap<String, List<Frequency>>();
            //带关键词的一般规则
            Map<String, List<Frequency>> map_HasKeyPC = new HashMap<String, List<Frequency>>();

            //排除已入仓的药单，不生成也不删除
            Map<String, Object> pidsjRuCangMap = new HashMap<String, Object>();
            if (pidsjRuCang != null) {
                for (String pidjsj : pidsjRuCang) {
                    pidsjRuCangMap.put(pidjsj, 1);
                }
            }

            List<Frequency> pcTempList = null;
            //一般规则
            List<Frequency> pcList = yzMainService.qryBatchRuleEnabled(false);
            if (pcList != null && pcList.size() > 0) {
                for (Frequency pc : pcList) {
                    if (pc.getRu_key() != null) {
                        pcTempList = map_HasKeyPC.get(pc.getPinc_code().toUpperCase());
                        if (pcTempList == null) {
                            pcTempList = new ArrayList<Frequency>();
                            map_HasKeyPC.put(pc.getPinc_code().toUpperCase(), pcTempList);
                        }
                        pcTempList.add(pc);
                    } else {
                        if (null != pc.getPinc_code()) {
                            pcTempList = map_NoKeyPC.get(pc.getPinc_code().toUpperCase());
                            if (pcTempList == null) {
                                pcTempList = new ArrayList<Frequency>();
                                map_NoKeyPC.put(pc.getPinc_code().toUpperCase(), pcTempList);
                            }
                            pcTempList.add(pc);
                        }
                    }
                }
            }

            List<PrescriptionMain> ydMainList = null;
            List<Prescription> ydList = null;
            
            List<PrescriptionMain> ydMainListTemp = null;
            List<Prescription> ydListTemp = null;
            
            Map<String, String> pidPC_bottLab = null;
            List<BottleLabel> pqList = null;
            
            BottleLabel pqTemp = null;
            List<Frequency> pcListTemp = null;
            
            Long ruleId = null;
            List<Frequency> pcListAll = null;
            List<Frequency> pcListOK = null;

            for (DoctorAdviceMain yzMain : yzMainList) {
                ydMainList = new ArrayList<PrescriptionMain>();//每一条执行一次
                ydList = new ArrayList<Prescription>();
                pcListOK = new ArrayList<Frequency>();
                ruleId = null;
                pidPC_bottLab = new HashMap<String, String>();
                pcListTemp = null;
                //根据一般规则找到对应的批次
                if (yzMain.getDrugname() != null && yzMain.getFreqCode() != null) {
                    pcListAll = new ArrayList<Frequency>();
                    pcListTemp = map_HasKeyPC.get(yzMain.getFreqCode().toUpperCase());
                    if (pcListTemp != null) {
                        for (Frequency pc : pcListTemp) {
                            if (ruleId == null) {
                                if (yzMain.getDrugname().contains(pc.getRu_key())) {
                                    ruleId = pc.getRu_id();
                                    pcListAll.add(pc);
                                }
                            } else if (ruleId.equals(pc.getRu_id())) {
                                pcListAll.add(pc);
                            }
                        }
                    }
                    if (pcListAll.size() == 0) {
                        pcListAll = map_NoKeyPC.get(yzMain.getFreqCode().toUpperCase());
                    }
                }
                if (pcListAll != null && pcListAll.size() > 0) {

                    ydMainListTemp =
                            DoctorAdviceTool.genYDMainList(yzMain,
                                    newYzshzt == null ? yzMain.getYzshzt() : newYzshzt,
                                    pcListAll,
                                    pidPC_bottLab,
                                    pidsjRuCangMap,
                                    pcListOK,//确定生成的批次
                                    scrqD,
                                    yyrqD);

                    if (ydMainListTemp.size() > 0) {
                        for (PrescriptionMain ydMainTemp : ydMainListTemp) {
                            ydMainList.add(ydMainTemp);
                        }
                    }

                    if (ydMainList != null && ydMainList.size() > 0) {
                        ydList = new ArrayList<Prescription>();
                        for (DoctorAdvice yzTmep : yzList) {
                            if (yzTmep.getPidsj().equals(yzMain.getPidsj())) {
                                ydListTemp =
                                        DoctorAdviceTool.genYDList(yzTmep,
                                                newYzshzt == null ? yzMain.getYzshzt() : newYzshzt,
                                                pcListOK,
                                                pidPC_bottLab,
                                                scrqD,
                                                yyrqD);
                                if (ydListTemp.size() > 0) {
                                    for (Prescription ydTemp : ydListTemp) {
                                        ydList.add(ydTemp);
                                    }
                                }
                            }
                        }
                        pqList = new ArrayList<BottleLabel>();
                        for (PrescriptionMain ydMain : ydMainList) {
                            pqTemp = new BottleLabel(ydMain);
                            pqList.add(pqTemp);

                        }
                        try {
                            ydMainService.insertPQAndPQ(yzMain.getParentNo(), ydMainList, pqList, ydList);
                            for (PrescriptionMain ydMain : ydMainList) {
                                printLabelService.createBottleLabel(ydMain, ydMain.getSfyscode());
                                //药单追踪记录
                                ydMainService.addTrackingRecord(ydMain.getPidsj(), trackingRecord.yssh, trackingRecord.ysshStr, account);
                            }
                        } catch (Exception e) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("pidsjLike",
                                    yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getCurrentDay8Str());
                            int row = ydMainService.qryCountByMap(map);
                            if (row == 0) {
                                Map<String, Object> updateMap = new HashMap<String, Object>();

                                List<String> pidsjN = new ArrayList<String>();
                                pidsjN.add(yzMain.getPidsj());
                                updateMap.put("pidsjN", pidsjN);
                                updateMap.put("yzshzt", 0);
                                yzMainService.updateCheckYZ(updateMap);
                                yzMainService.updateCheckYZMain(updateMap);
                            }

                            log.error("生成药单瓶签数据异常，parentNo=" + yzMain.getParentNo());
                            log.error(e.getMessage(), e);
                        }
                    }
                }

            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成药单瓶签数据异常");
            log.error(e.getMessage(), e);
        }

        return false;
    }

    /**
     * 生成药单:  commented by jch 2018/05/23
     * 定时任务执行调度方法
     *
     * @return
     */
    public static String autoGenYDRun(Date dayNow) {
        boolean isTodayNew = false;//是否当日新开立医嘱检查，默认是fasle
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if (hour >= 21) {
            isTodayNew = true;
        }

        if (fStatus) {
            fStatus = !fStatus;
            try {
                //String day8Str = DateUtil.getCurrentDay8Str();
                //Date dayNow = new Date();
                String day8Str = DataFormat.formatYYYYMMDDByDate(dayNow);

                DoctorAdviceService yzService = ServletContextUtil.yzService;
                DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
                
                PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
                
                if (yzMainService != null) {
                    //System.out.println("stopTempYZNotToday");
                    yzMainService.stopTempYZNotToday();
                }
                if (yzMainService != null && yzService != null) {
                    Map<String, Object> qryYZ = new HashMap<String, Object>();
                    qryYZ.put("leftWithPati", 1);//关联病人
                    qryYZ.put("running", 0);//执行中的医嘱
                    qryYZ.put("yzNotInYD", day8Str);//检查日期药单是否存在
                    qryYZ.put("yzshzt_1", 1);//审核状态
                    if (isTodayNew) {
                        qryYZ.put("todayNew", 1);//判断是否当日开立，只需要检查当日开立是否有药单
                    }
                    qryYZ.put("yzlx", 0);//医嘱类型
                    List<DoctorAdvice> yzList1 = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                    List<DoctorAdviceMain> yzMainList1 = yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());

                    //已入仓的不重新生成
                    List<String> pidrqNRucangOk = new ArrayList<String>();
                    for (DoctorAdviceMain yzMain : yzMainList1) {
                        pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + day8Str);
                    }
                    List<String> pidsjRuCang = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk);
                    genPC(null, yzMainList1, yzList1, pidsjRuCang, dayNow, dayNow, "system");

                    qryYZ.put("yzlx", 1);
                    qryYZ.put("todayNew", 1);

                    List<DoctorAdvice> yzList2 = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                    List<DoctorAdviceMain> yzMainList2 = yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());

                    //临嘱值生成今天的
                    if (DataFormat.formatYYYYMMDDByDate(dayNow).equals(DataFormat.formatYYYYMMDDByDate(new Date()))) {
                        List<String> pidrqNRucangOk2 = new ArrayList<String>();
                        for (DoctorAdviceMain yzMain : yzMainList2) {
                            pidrqNRucangOk2.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + day8Str);
                        }
                        List<String> pidsjRuCang2 = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk2);
                        genPC(null, yzMainList2, yzList2, pidsjRuCang2, dayNow, dayNow, "system");
                    }

                } else {
                    //                    System.out.println("获取 yzMainService等 失败");
                }
                fStatus = true;
                return "药单自动生成任务成功";
            } catch (Exception e) {
                fStatus = true;
                e.printStackTrace();
            } finally {
                fStatus = true;
            }
        }
        return "药单自动生成任务失败";
    }
}
