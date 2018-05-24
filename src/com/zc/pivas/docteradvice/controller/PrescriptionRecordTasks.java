package com.zc.pivas.docteradvice.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.UniqueNumberGenerator;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.backup.bean.ServerNodeBean;
import com.zc.pivas.backup.service.ServerNodeService;
import com.zc.pivas.common.util.NumUtil;
import com.zc.pivas.common.util.PrescriptionAutoGen;
import com.zc.pivas.common.util.ServletContextUtil;
import com.zc.pivas.common.util.SysConstant.trackingRecord;
import com.zc.pivas.docteradvice.bean.BottleLabel;
import com.zc.pivas.docteradvice.bean.Frequency;
import com.zc.pivas.docteradvice.entity.BatchBean;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.PrescriptionRuleService;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.excuteRecord.service.SynYdRecordService;
import com.zc.pivas.patient.bean.PatientBean;
import com.zc.pivas.patient.service.PatientService;
import com.zc.pivas.printlabel.service.PrintLabelService;

public class PrescriptionRecordTasks extends QuartzJobBean
{
    
    /** 日志类 */
    private static final Logger log = LoggerFactory.getLogger(PrescriptionRecordTasks.class);
    
    public final static String SPLIT_MODE = Propertiesconfiguration.getStringProperty("pivas.yz.split.mode");//医嘱拆分规则 ydzxjl：药单执行记录  ybgz：根据一般规则
    
    final static String SPLIT_rule = Propertiesconfiguration.getStringProperty("pivas.ydzxjl.rule");//药单执行记录规则 allDay:全天用药次数 split：用药拆开
    
    final static String SPLIT_yysj = Propertiesconfiguration.getStringProperty("pivas.ydzxjl.yysj");//药单执行记录是否存在用药时间 exist
    
    final static String SPLIT_yyxx = Propertiesconfiguration.getStringProperty("pivas.ydzxjl.yyxx");//药单执行记录是否存在用药信息 exist
    
    final static String RETUFN_PREFIX = Propertiesconfiguration.getStringProperty("pivas.return.batch.prefix");
    
    protected void executeInternal(JobExecutionContext context)
        throws JobExecutionException
    {
        
        //备机状态下不执行任务
        ServerNodeService serverNodeService = ServletContextUtil.serverNodeService;
        if (serverNodeService != null)
        {
            String localip = Propertiesconfiguration.getStringProperty("localip");
            if (localip != null)
            {
                ServerNodeBean bean = serverNodeService.getServerNode(localip);
                if (bean != null)
                {
                    if (bean.getFlag() == 1)
                    {
                        log.info("Backup mode,do not exec time task YDRecordTasks");
                        return;
                    }
                }
            }
        }
        
        if ("ydzxjl".equals(SPLIT_MODE))
        {
            log.info("Ready to exec time task YDRecordTasks");
            taskStart(null);
        }
        
    }
    
    public static synchronized void taskStart(String checkDate)
    {
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
        DoctorAdviceService yzService = ServletContextUtil.yzService;
        if (yzMainService == null || ydMainService == null || yzService == null)
        {
            log.error("Service is null");
            return;
        }
        
        Date dayNow = new Date();
        
        // 第二天
        Date tomorrow = DateUtil.addDay(dayNow, 1);
        
        // 第三天
        Date ThirdDay = DateUtil.addDay(dayNow, 2);
        
        String yyrq = DateUtil.getCurrentDay8Str();//系统日期
        
        String[] needCheckYD = ydMainService.getCheckYZList(yyrq);//根据医嘱状态为0，医嘱审核状态为1查询医嘱主表parent_no字段，并根据parent_no字段执行去重操作
        
        if (needCheckYD.length == 0)
        {
            return;
        }
        
        ydMainService.deleteCheckYZTemp();//删除定时任务检查YZ临时表内容
        ydMainService.saveCheckYZTemp(yyrq);
        
        Boolean resultF = false;
        
        if ("allDay".equals(SPLIT_rule))
        {
            // 药单执行记录走的全天用药次数
            checkYdByAllDay(dayNow, yyrq, resultF);
        }
        else if ("split".equals(SPLIT_rule))
        {
            
            if ("exist".equals(SPLIT_yysj))
            {
                if (null == checkDate)
                {
                    // 检查当天药单数据
                    checkYdForSplitYY(ydMainService, dayNow, true);
                    
                    // 检查第二天的药单数据                    
                    checkYdForSplitYY(ydMainService, tomorrow, true);
                    
                    // 检查第三天的药单数据 
                    checkYdForSplitYY(ydMainService, ThirdDay, true);
                    
                }
                else
                {
                    String today = DateUtil.getDay8StrByDay(dayNow);
                    
                    if (today.equals(checkDate))
                    {
                        // 检查当天药单数据
                        checkYdForSplitYY(ydMainService, dayNow, false);
                        
                        // 检查第二天的药单数据                    
                        checkYdForSplitYY(ydMainService, tomorrow, false);
                    }
                    else
                    {
                        // 检查第二天的药单数据                    
                        checkYdForSplitYY(ydMainService, tomorrow, false);
                        
                        // 检查第三天的药单数据 
                        checkYdForSplitYY(ydMainService, ThirdDay, false);
                    }
                }
            }
            else if ("noexist".equals(SPLIT_yysj))
            {
                
                List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForSplitNotPC(yyrq);
                
                if (differentYd == null || differentYd.size() == 0)
                {
                    return;
                }
                
                for (ExcuteRecordBean bean : differentYd)
                {
                    
                    String groupNo = bean.getGroupNo();
                    
                    List<PrescriptionMain> ydMain = ydMainService.getYDMainListByParent(groupNo, yyrq, 0, "asc");
                    //查看是否有停止状态的
                    List<PrescriptionMain> ydMainStop = ydMainService.getYDMainListByParent(groupNo, yyrq, 1, "desc");
                    
                    int ydStopSize = ydMainStop.size();
                    int ydSize = ydMain.size();
                    
                    if (ydSize == 0 && ydStopSize == 0)
                    {
                        
                        Map<String, Object> qry = new HashMap<String, Object>();
                        qry.put("parentNo", groupNo);
                        qry.put("yzztLowN", 1);// 药单状态 医嘱过来的数据，医嘱状态是执行中 才可以审核
                        qry.put("leftWithPati", "Y");
                        qry.put("qry.yzshzt_1", "1");
                        List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(qry, new JqueryStylePaging());
                        
                        if (yzMainList == null || yzMainList.size() == 0)
                        {
                            continue;
                        }
                        
                        List<String> pidrqNRucangOk = new ArrayList<String>();
                        for (DoctorAdviceMain yzMain : yzMainList)
                        {
                            pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + yyrq);
                        }
                        
                        Map<String, Object> qryYZ = new HashMap<String, Object>();
                        qryYZ.put("parentNo", groupNo);
                        qryYZ.put("leftWithPati", 1);
                        List<DoctorAdvice> yzList = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                        
                        List<DoctorAdviceMain> yzMainListTemp = yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                        List<String> pidsjRuCang = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk);
                        
                        resultF = PrescriptionAutoGen.genPC(1, yzMainListTemp, yzList, pidsjRuCang, dayNow, dayNow, "system");
                        
                        if (!resultF)
                        {
                            continue;
                        }
                        
                    }
                    else
                    {
                        int recordAmount = Integer.valueOf(bean.getUseCount());
                        
                        String recordState = bean.getState();
                        
                        Map<String, Object> qry = new HashMap<String, Object>();
                        qry.put("parentNo", groupNo);
                        //qry.put("ydztLowN", 5);// 关联药单表，并且药单状态 小于5 未执行的，才可以审核
                        qry.put("yzztLowN", 1);// 药单状态 医嘱过来的数据，医嘱状态是执行中 才可以审核
                        qry.put("leftWithPati", "Y");
                        qry.put("qry.yzshzt_1", "1");
                        List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(qry, new JqueryStylePaging());
                        if (yzMainList == null || yzMainList.size() == 0)
                        {
                            continue;
                        }
                        
                        if ("0".equals(recordState))
                        {
                            
                            if (recordAmount > ydSize)
                            {
                                
                                int differValue = recordAmount - ydSize;
                                
                                if (differValue >= ydStopSize)
                                {
                                    differValue = ydStopSize;
                                }
                                
                                PrescriptionMain ydBean = new PrescriptionMain();
                                for (int i = 0; i < differValue; i++)
                                {
                                    ydBean = ydMainStop.get(i);
                                    ydMainService.updateYDMain(ydBean, 0);
                                    ydMainService.updateYDChildren(ydBean, 0);
                                    ydMainService.updateYDPQ(ydBean, 0);
                                    ydSize = ydSize + 1;
                                }
                                
                                if (recordAmount == ydSize)
                                {
                                    continue;
                                }
                                
                                List<String> pidrqNRucangOk = new ArrayList<String>();
                                for (DoctorAdviceMain yzMain : yzMainList)
                                {
                                    pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + yyrq);
                                }
                                
                                Map<String, Object> qryYZ = new HashMap<String, Object>();
                                qryYZ.put("parentNo", groupNo);
                                qryYZ.put("leftWithPati", 1);
                                
                                List<DoctorAdvice> yzList = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                                List<DoctorAdviceMain> yzMainListTemp =
                                    yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                                List<String> pidsjRuCang = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk);
                                
                                resultF =
                                    genPC(yzMainListTemp, yzList, pidsjRuCang, dayNow, dayNow, ydSize, recordAmount, "");
                                
                                if (!resultF)
                                {
                                    continue;
                                }
                                
                            }
                            else if (recordAmount < ydSize)
                            {
                                
                                List<PrescriptionMain> ydArray = ydMainService.getYDArrayOrderPCByParent(groupNo, yyrq);
                                
                                int defferValue = ydSize - recordAmount;
                                
                                PrescriptionMain ydBean = new PrescriptionMain();
                                
                                for (int i = 0; i < defferValue; i++)
                                {
                                    ydBean = ydArray.get(i);
                                    ydMainService.updateYDMain(ydBean, 1);
                                    ydMainService.updateYDChildren(ydBean, 1);
                                    ydMainService.updateYDPQ(ydBean, 1);
                                }
                                
                            }
                            
                        }
                        else if ("1".equals(recordState))
                        {
                            
                            PrescriptionMain ydBean = new PrescriptionMain();
                            ydBean.setParentNo(groupNo);
                            ydMainService.updateYDMain(ydBean, 1);
                            ydMainService.updateYDChildren(ydBean, 1);
                            ydMainService.updateYDPQ(ydBean, 1);
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        log.info("End exec time task YDRecordTasks");
        
    }
    
    /**
     * 检查制定日期的药单
     * 
     * @param ydMainService
     * @param date
     */
    private static void checkYdForSplitYY(final PrescriptionMainService ydMainService, final Date date,
                                          final boolean isCheckYDState)
    {
        final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        final String yyrq = sdf.format(date);
        ydMainService.deleteCheckYZTemp();
        ydMainService.saveCheckYZTemp(sdf.format(date));
        
        Thread task1 = new Thread(new Runnable()
        {
            public void run()
            {
                List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForSplit(yyrq, "0,1".split(","));
                
                checkPC(date, sdf.format(date), differentYd, isCheckYDState);
            }
        });
        task1.start();
        
        Thread task2 = new Thread(new Runnable()
        {
            public void run()
            {
                List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForSplit(yyrq, "2,3".split(","));
                
                checkPC(date, sdf.format(date), differentYd, isCheckYDState);
            }
        });
        task2.start();
        
        Thread task3 = new Thread(new Runnable()
        {
            public void run()
            {
                List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForSplit(yyrq, "4,5".split(","));
                
                checkPC(date, sdf.format(date), differentYd, isCheckYDState);
            }
        });
        task3.start();
        
        Thread task4 = new Thread(new Runnable()
        {
            public void run()
            {
                List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForSplit(yyrq, "6,7".split(","));
                
                checkPC(date, sdf.format(date), differentYd, isCheckYDState);
            }
        });
        task4.start();
        
        Thread task5 = new Thread(new Runnable()
        {
            public void run()
            {
                List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForSplit(yyrq, "8,9".split(","));
                
                checkPC(date, sdf.format(date), differentYd, isCheckYDState);
            }
        });
        task5.start();
        
        while (task1.isAlive() || task2.isAlive() || task3.isAlive() || task4.isAlive() || task5.isAlive())
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                log.error("生成药单瓶签线程错误！", e.getMessage());
            }
        }
        
    }
    
    /**
     * 药单执行记录走的全天用药次数
     * @param dayNow
     * @param yyrq
     * @param resultF
     */
    private static void checkYdByAllDay(Date dayNow, String yyrq, Boolean resultF)
    {
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
        DoctorAdviceService yzService = ServletContextUtil.yzService;
        
        List<ExcuteRecordBean> differentYd = ydMainService.getDifferentYdForAll(yyrq);
        
        if (differentYd == null || differentYd.size() == 0)
        {
            return;
        }
        
        for (ExcuteRecordBean bean : differentYd)
        {
            
            String groupNo = bean.getGroupNo();
            
            List<PrescriptionMain> ydMain = ydMainService.getYDMainListByParent(groupNo, yyrq, 0, "asc");
            //查看是否有停止状态的
            List<PrescriptionMain> ydMainStop = ydMainService.getYDMainListByParent(groupNo, yyrq, 1, "desc");
            
            int ydStopSize = ydMainStop.size();
            int ydSize = ydMain.size();
            
            if (ydSize == 0 && ydStopSize == 0)
            {
                
                Map<String, Object> qry = new HashMap<String, Object>();
                qry.put("parentNo", groupNo);
                qry.put("yzztLowN", 1);// 药单状态 医嘱过来的数据，医嘱状态是执行中 才可以审核
                qry.put("leftWithPati", "Y");
                qry.put("qry.yzshzt_1", "1");
                List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(qry, new JqueryStylePaging());
                
                if (yzMainList == null || yzMainList.size() == 0)
                {
                    continue;
                }
                List<String> pidrqNRucangOk = new ArrayList<String>();
                for (DoctorAdviceMain yzMain : yzMainList)
                {
                    pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + yyrq);
                }
                
                Map<String, Object> qryYZ = new HashMap<String, Object>();
                qryYZ.put("parentNo", groupNo);
                qryYZ.put("leftWithPati", 1);
                List<DoctorAdvice> yzList = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                
                List<DoctorAdviceMain> yzMainListTemp = yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                List<String> pidsjRuCang = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk);
                
                resultF = PrescriptionAutoGen.genPC(1, yzMainListTemp, yzList, pidsjRuCang, dayNow, dayNow, "system");
                
                if (!resultF)
                {
                    continue;
                }
                
            }
            else
            {
                
                int recordAmount = Integer.valueOf(bean.getUseCount());
                String recordState = bean.getState();
                
                Map<String, Object> qry = new HashMap<String, Object>();
                qry.put("parentNo", groupNo);
                //qry.put("ydztLowN", 5);// 关联药单表，并且药单状态 小于5 未执行的，才可以审核
                qry.put("yzztLowN", 1);// 药单状态 医嘱过来的数据，医嘱状态是执行中 才可以审核
                qry.put("leftWithPati", "Y");
                qry.put("qry.yzshzt_1", "1");
                List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(qry, new JqueryStylePaging());
                if (yzMainList == null || yzMainList.size() == 0)
                {
                    continue;
                }
                
                if ("0".equals(recordState))
                {
                    
                    if (recordAmount > ydSize)
                    {
                        
                        int differValue = recordAmount - ydSize;
                        
                        if (differValue >= ydStopSize)
                        {
                            differValue = ydStopSize;
                        }
                        
                        PrescriptionMain ydBean = new PrescriptionMain();
                        for (int i = 0; i < differValue; i++)
                        {
                            ydBean = ydMainStop.get(i);
                            ydMainService.updateYDMain(ydBean, 0);
                            ydMainService.updateYDChildren(ydBean, 0);
                            ydMainService.updateYDPQ(ydBean, 0);
                            ydSize = ydSize + 1;
                        }
                        
                        if (recordAmount == ydSize)
                        {
                            continue;
                        }
                        
                        List<String> pidrqNRucangOk = new ArrayList<String>();
                        for (DoctorAdviceMain yzMain : yzMainList)
                        {
                            pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + yyrq);
                        }
                        
                        Map<String, Object> qryYZ = new HashMap<String, Object>();
                        qryYZ.put("parentNo", groupNo);
                        qryYZ.put("leftWithPati", 1);
                        
                        List<DoctorAdvice> yzList = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                        List<DoctorAdviceMain> yzMainListTemp = yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                        List<String> pidsjRuCang = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk);
                        
                        resultF = genPC(yzMainListTemp, yzList, pidsjRuCang, dayNow, dayNow, ydSize, recordAmount, "");
                        
                        if (!resultF)
                        {
                            continue;
                        }
                        
                    }
                    else if (recordAmount < ydSize)
                    {
                        
                        List<PrescriptionMain> ydArray = ydMainService.getYDArrayOrderPCByParent(groupNo, yyrq);
                        
                        int defferValue = ydSize - recordAmount;
                        
                        PrescriptionMain ydBean = new PrescriptionMain();
                        
                        for (int i = 0; i < defferValue; i++)
                        {
                            ydBean = ydArray.get(i);
                            ydMainService.updateYDMain(ydBean, 1);
                            ydMainService.updateYDChildren(ydBean, 1);
                            ydMainService.updateYDPQ(ydBean, 1);
                        }
                        
                    }
                    
                }
                else if ("1".equals(recordState))
                {
                    
                    PrescriptionMain ydBean = new PrescriptionMain();
                    ydBean.setParentNo(groupNo);
                    ydMainService.updateYDMain(ydBean, 1);
                    ydMainService.updateYDChildren(ydBean, 1);
                    ydMainService.updateYDPQ(ydBean, 1);
                    
                }
                
            }
            
        }
    }
    
    private static void checkPC(Date checkDay, String yyrq, List<ExcuteRecordBean> differentYd, boolean isCheckYDState)
    {
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
        DoctorAdviceService yzService = ServletContextUtil.yzService;
        
        boolean resultF = false;
        
        if (differentYd == null || differentYd.size() == 0)
        {
            return;
        }
        
        for (ExcuteRecordBean bean : differentYd)
        {
            String groupNo = bean.getGroupNo();
            String state = bean.getState();
            String pc = bean.getBatchID();
            
            if (StringUtils.isBlank(pc))
            {
                continue;
            }
            
            if ("0".equals(state) && !isCheckYDState)
            {
                
                List<PrescriptionMain> ydMainStop = ydMainService.getYDMainListByParentAndPC(groupNo, pc, yyrq, 1);
                if (ydMainStop == null || ydMainStop.size() > 1)
                {
                    continue;
                }
                
                if (ydMainStop.size() == 1)
                {
                    
                    PrescriptionMain ydBean = ydMainStop.get(0);
                    ydMainService.updateYDMain(ydBean, 0);
                    ydMainService.updateYDChildren(ydBean, 0);
                    ydMainService.updateYDPQ(ydBean, 0);
                    continue;
                }
                
                List<PrescriptionMain> ydMainTF = ydMainService.getYDMainListByParentAndPC(groupNo, pc, yyrq, 3);
                if (ydMainTF == null || ydMainTF.size() > 1)
                {
                    continue;
                }
                
                if (ydMainTF.size() == 1)
                {
                    
                    PrescriptionMain ydBean = ydMainTF.get(0);
//                    ydMainService.updateYDMain(ydBean, 0);
//                    ydMainService.updateYDChildren(ydBean, 0);
//                    ydMainService.updateYDPQ(ydBean, 0);
                    
                    ydMainService.updateYDInfo(ydBean, 0);
                    continue;
                }
                
                Map<String, Object> qry = new HashMap<String, Object>();
                qry.put("parentNo", groupNo);
                //qry.put("yzztLowN", 1);// 药单状态 医嘱过来的数据，医嘱状态是执行中 才可以审核
                qry.put("leftWithPati", "Y");
                qry.put("qry.yzshzt_1", "1");
                List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(qry, new JqueryStylePaging());
                
                if (yzMainList == null || yzMainList.size() == 0)
                {
                    continue;
                }
                
                List<String> pidrqNRucangOk = new ArrayList<String>();
                for (DoctorAdviceMain yzMain : yzMainList)
                {
                    pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + yyrq);
                }
                
                Map<String, Object> qryYZ = new HashMap<String, Object>();
                qryYZ.put("parentNo", groupNo);
                qryYZ.put("leftWithPati", 1);
                
                List<DoctorAdvice> yzList = yzService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                List<DoctorAdviceMain> yzMainListTemp = yzMainService.qryListBeanByMap(qryYZ, new JqueryStylePaging());
                List<String> pidsjRuCang = ydMainService.queryPidsjRuCangOK(pidrqNRucangOk);
                
                resultF = genPC(yzMainListTemp, yzList, pidsjRuCang, checkDay, checkDay, 0, 0, pc);
                
                if (!resultF)
                {
                    continue;
                }
                
            }
            else if ("1".equals(state))
            {
                
                List<PrescriptionMain> ydMain = ydMainService.getYDMainListByParentAndPC(groupNo, pc, yyrq, 0);
                if (ydMain == null || ydMain.size() != 1)
                {
                    continue;
                }
                
                PrescriptionMain ydBean = ydMain.get(0);
//                ydMainService.updateYDMain(ydBean, 1);
//                ydMainService.updateYDChildren(ydBean, 1);
//                ydMainService.updateYDPQ(ydBean, 1);
                
                ydMainService.updateYDInfo(ydBean, 1); 
                
            }
            else if ("2".equals(state))
            {
                
                List<PrescriptionMain> ydMain = ydMainService.getYDMainListByParentAndPC(groupNo, pc, yyrq, 0);
                if (ydMain == null || ydMain.size() != 1)
                {
                    continue;
                }
                
                try
                {
                    String pidrqzxbc = ydMain.get(0).getPidrqzxbc().trim();
                    String zxbc = ydMain.get(0).getZxbc() + "";
                    
                    String t_num = ydMainService.qryBatchNum(zxbc);
                    String t_pc = ydMainService.qryBatchCode(RETUFN_PREFIX + t_num);
                    PrescriptionMain ydBean = ydMain.get(0);
                    if (StringUtils.isNotEmpty(t_pc))
                    {
                        pidrqzxbc = pidrqzxbc.replace("_" + zxbc, "_" + t_pc);
                        
                        String bottLab = UniqueNumberGenerator.generate(13);
                        
                        ydBean.setBottleLabelNum(bottLab);
                        ydBean.setPidrqzxbc(pidrqzxbc);
                        ydBean.setZxbc(Integer.valueOf(t_pc));
                    }
//                    ydMainService.updateYDMain(ydBean, 3);
//                    ydMainService.updateYDChildren(ydBean, 3);
//                    ydMainService.updateYDPQ(ydBean, -1);
                    
                    ydMainService.updateYDInfo(ydBean, 3);
                }
                catch (Exception e)
                {
                    log.error("药单退费失败  pidrqzxbc：" + ydMain.get(0).getPidrqzxbc().trim());
                }
            }
            
        }
    }
    
    private static Boolean genPC(List<DoctorAdviceMain> yzMainList, List<DoctorAdvice> yzList, List<String> pidsjRuCang, Date scrqD,
                                 Date yyrqD, int ydSize, int recordAmount, String pcID)
    {
        
        try
        {
            DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
            PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
            PrescriptionRuleService ydRuleService = ServletContextUtil.ydRuleService;
            BatchService batchService = ServletContextUtil.batchService;
            PrintLabelService printLabelService = ServletContextUtil.printLabelService;
            if (yzMainService == null || ydMainService == null || ydRuleService == null || batchService == null
                || printLabelService == null)
            {
                log.error("Service is null");
                return false;
            }
            
            //排除已入仓的药单，不生成也不删除
            Map<String, Object> pidsjRuCangMap = new HashMap<String, Object>();
            
            if (pidsjRuCang != null)
            {
                for (String pidjsj : pidsjRuCang)
                {
                    pidsjRuCangMap.put(pidjsj, 1);
                }
            }
            
            /*//不带关键词的一般规则
            Map<String, List<PC>> map_NoKeyPC = new HashMap<String, List<PC>>();
            //带关键词的一般规则
            Map<String, List<PC>> map_HasKeyPC = new HashMap<String, List<PC>>();
            
            List<PC> pcTempList = null;
            //一般规则
            List<PC> pcList = yzMainService.qryBatchRuleEnabled(false);
            if (pcList != null && pcList.size() > 0)
            {
                for (PC pc : pcList)
                {
                    if (pc.getRu_key() != null)
                    {
                        pcTempList = map_HasKeyPC.get(pc.getPinc_code().toUpperCase());
                        if (pcTempList == null)
                        {
                            pcTempList = new ArrayList<PC>();
                            map_HasKeyPC.put(pc.getPinc_code().toUpperCase(), pcTempList);
                        }
                        pcTempList.add(pc);
                    }
                    else
                    {
                        if (null != pc.getPinc_code())
                        {
                            pcTempList = map_NoKeyPC.get(pc.getPinc_code().toUpperCase());
                            if (pcTempList == null)
                            {
                                pcTempList = new ArrayList<PC>();
                                map_NoKeyPC.put(pc.getPinc_code().toUpperCase(), pcTempList);
                            }
                            pcTempList.add(pc);
                        }
                    }
                }
            }*/
            
            List<PrescriptionMain> ydMainList = null;
            List<Prescription> ydList = null;
            List<PrescriptionMain> ydMainListTemp = null;
            List<Prescription> ydListTemp = null;
            Map<String, String> pidPC_bottLab = null;
            List<BottleLabel> pqList = null;
            BottleLabel pqTemp = null;
            //List<PC> pcListTemp = null;
            //Long ruleId = null;
            List<Frequency> pcListAll = null;
            List<Frequency> pcListOK = null;
            
            for (DoctorAdviceMain yzMain : yzMainList)
            {
                
                ydMainList = new ArrayList<PrescriptionMain>();//每一条执行一次
                ydList = new ArrayList<Prescription>();
                pcListOK = new ArrayList<Frequency>();
                //ruleId = null;
                
                pidPC_bottLab = new HashMap<String, String>();
                //pcListTemp = null;
                
                //根据一般规则找到对应的批次
                /*if (yzMain.getDrugname() != null && yzMain.getFreqCode() != null)
                {
                    pcListAll = new ArrayList<PC>();
                    pcListTemp = map_HasKeyPC.get(yzMain.getFreqCode().toUpperCase());
                    if (pcListTemp != null)
                    {
                        for (PC pc : pcListTemp)
                        {
                            if (ruleId == null)
                            {
                                if (yzMain.getDrugname().contains(pc.getRu_key()))
                                {
                                    ruleId = pc.getRu_id();
                                    pcListAll.add(pc);
                                }
                            }
                            else if (ruleId.equals(pc.getRu_id()))
                            {
                                pcListAll.add(pc);
                            }
                        }
                    }
                    if (pcListAll.size() == 0)
                    {
                        pcListAll = map_NoKeyPC.get(yzMain.getFreqCode().toUpperCase());
                    }
                }*/
                
                if ((pcListAll != null && pcListAll.size() > 0)
                    || ("split".equals(SPLIT_rule) && "exist".equals(SPLIT_yysj)))
                {
                    
                    ydMainListTemp = genYDMainList(yzMain, pcListAll, pidPC_bottLab, pidsjRuCangMap, pcListOK,//确定生成的批次
                        scrqD,
                        yyrqD,
                        ydSize,
                        recordAmount,
                        pcID);
                    
                    if (ydMainListTemp.size() > 0)
                    {
                        for (PrescriptionMain ydMainTemp : ydMainListTemp)
                        {
                            ydMainList.add(ydMainTemp);
                        }
                    }
                    
                    if (ydMainList != null && ydMainList.size() > 0)
                    {
                        ydList = new ArrayList<Prescription>();
                        for (DoctorAdvice yzTmep : yzList)
                        {
                            if (yzTmep.getPidsj().equals(yzMain.getPidsj()))
                            {
                                String pidsjTemp = ydMainListTemp.get(0).getPidsj();
                                ydListTemp = genYDList(yzTmep, 1, pcListOK, pidPC_bottLab, scrqD, yyrqD, pidsjTemp);
                                if (ydListTemp.size() > 0)
                                {
                                    for (Prescription ydTemp : ydListTemp)
                                    {
                                        ydList.add(ydTemp);
                                    }
                                }
                            }
                        }
                        pqList = new ArrayList<BottleLabel>();
                        for (PrescriptionMain ydMain : ydMainList)
                        {
                            pqTemp = new BottleLabel(ydMain);
                            pqList.add(pqTemp);
                            
                        }
                        
                        ydMainService.insertPQAndPQ(yzMain.getParentNo(), ydMainList, pqList, ydList);
                        
                        String sfCode = "";
                        for (PrescriptionMain ydMain : ydMainList)
                        {
                            sfCode = ydMain.getSfyscode();
                            if (StringUtils.isEmpty(ydMain.getSfyscode()))
                            {
                                sfCode = "admin";
                            }
                            
                            printLabelService.createBottleLabel(ydMain, sfCode);
                            //药单追踪记录
                            ydMainService.addTrackingRecord(ydMain.getPidsj(),
                                trackingRecord.yssh,
                                trackingRecord.ysshStr,
                                "system");
                        }
                        
                    }
                    
                }
                
            }
            
            return true;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private static List<PrescriptionMain> genYDMainList(DoctorAdviceMain yzMain, List<Frequency> pcListAll, Map<String, String> pidPC_bottLab,
                                                        Map<String, Object> pidsjRuCangMap, List<Frequency> pcListOK, Date scrqD, Date yyrqD, int ydSize, int recordAmount,
                                                        String pcID)
        throws Exception
    {
        
        PatientService patientService = ServletContextUtil.patientService;
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        PrescriptionMainService ydMainService = ServletContextUtil.ydMainService;
        BatchService batchService = ServletContextUtil.batchService;
        SynYdRecordService synYdRecordService = ServletContextUtil.synYdRecordService;
        if (yzMainService == null || ydMainService == null || batchService == null || synYdRecordService == null
            || patientService == null)
        {
            return new ArrayList<PrescriptionMain>();
        }
        
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
        
        if (DefineCollectionUtil.isNotEmpty(patientList))
        {
            patientState = patientList.get(0).getState();
            
            // -1:预出院
            if ("-1".equals(patientState) && null != patientList.get(0).getHosOutTime()
                && !"".equals(patientList.get(0).getHosOutTime()))
            {
                hosOutTime = DateUtil.getDateFromYYYYMMDD(patientList.get(0).getHosOutTime());
                
                DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
                Date yyrqDay = datef.parse(datef.format(yyrqD) + " 00:00:00");//datef.parse(datef.format(new Date())+" 00:00:00")
                if (yyrqDay.compareTo(hosOutTime) >= 0)
                {
                    patientIsOut = true;
                }
            }
            
            // 1:已出院
            if ("1".equals(patientState))
            {
                patientIsOut = true;
            }
            
        }
        
        if (CollectionUtils.isNotEmpty(pcListAll))
        {
            Collections.sort(pcListAll, new Comparator<Frequency>()
            {
                public int compare(Frequency pc1, Frequency pc2)
                {
                    //如果 两个批次比较，将后面的批次放到前面，倒序排列
                    if (pc1.getOrder_num() > pc2.getOrder_num())
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
                }
            });
        }
        
        PrescriptionMain ydMain = null;
        List<PrescriptionMain> ydMainList = new ArrayList<PrescriptionMain>();
        if ((yzMain != null && pcListAll != null && !patientIsOut)
            || ("split".equals(SPLIT_rule) && "exist".equals(SPLIT_yysj)))
        {
            
            List<BatchBean> batchList = ydMainService.getCustomBatch();
            
            int existCount = 0;
            
            if ("allDay".equals(SPLIT_rule))
            {
                int pcNum = pcListAll.size();
                List<ExcuteRecordBean> record =
                    synYdRecordService.getRecordAllDay(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo());
                
                int addNum = recordAmount - ydSize;
                
                int specialPC = 0;
                
                for (int n = 1; n < addNum + 1; n++)
                {
                    
                    if (ydSize + n <= pcNum)
                    {
                        
                        //一般规则的批次
                        Frequency pcTemp = pcListAll.get(ydSize + n - 1);
                        
                        existCount =
                            yzMainService.judgePCExistBy(yzMain.getParentNo(),
                                DateUtil.getDay8StrByDay(yyrqD),
                                pcTemp.getPc_id().intValue());
                        if (existCount > 0)
                        {
                            addNum++;
                            continue;
                        }
                        
                        int time = pcTemp.getRu_serialNum();
                        
                        Boolean result =
                            createPC(pcTemp,
                                yzMain,
                                ydMain,
                                pidsjRuCangMap,
                                scrqD,
                                yyrqD,
                                ydMainList,
                                pcListOK,
                                pidPC_bottLab,
                                time,
                                record);
                        
                        if (!result)
                        {
                            continue;
                        }
                        
                    }
                    else
                    {
                        
                        int differNum = ydSize - pcNum;
                        int totalNum = 0;
                        if (differNum > 0)
                        {
                            
                            totalNum = specialPC + differNum;
                        }
                        else
                        {
                            totalNum = specialPC;
                        }
                        
                        if (totalNum > 5)
                        {
                            return ydMainList;
                        }
                        
                        BatchBean bacth = batchList.get(totalNum);
                        
                        existCount =
                            yzMainService.judgePCExistBy(yzMain.getParentNo(),
                                DateUtil.getDay8StrByDay(yyrqD),
                                bacth.getId_().intValue());
                        
                        if (existCount > 0)
                        {
                            addNum++;
                            specialPC++;
                            continue;
                        }
                        
                        int time = ydSize + n;
                        
                        int pidsjTemp = bacth.getId_().intValue();
                        Boolean result =
                            createPCCustom(bacth,
                                yzMain,
                                ydMain,
                                pidsjRuCangMap,
                                scrqD,
                                yyrqD,
                                ydMainList,
                                pcListOK,
                                pidPC_bottLab,
                                time,
                                record,
                                pidsjTemp);
                        if (!result)
                        {
                            continue;
                        }
                        specialPC++;
                        
                    }
                }
            }
            else if ("split".equals(SPLIT_rule))
            {
                
                List<ExcuteRecordBean> recordYY =
                    synYdRecordService.getRecordYyDetail(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo());
                
                if ("exist".equals(SPLIT_yysj))
                {
                    
                    List<ExcuteRecordBean> record =
                        synYdRecordService.getRecordByPC(DateUtil.getDay8StrByDay(yyrqD), yzMain.getParentNo(), pcID);
                    if (record == null || record.size() == 0)
                    {
                        return new ArrayList<PrescriptionMain>();
                    }
                    
                    for (ExcuteRecordBean recordBean : record)
                    {
                        
                        String pcId = recordBean.getBatchID();
                        if (StringUtils.isBlank(pcId))
                        {
                            continue;
                        }
                        BatchBean batchBean = ydMainService.getBatchByID(pcId);
                        if (batchBean == null)
                        {
                            continue;
                        }
                        
                        boolean checkOk = true;
                        
                        int pidsjTemp = Integer.valueOf(pcId);
                        
                        existCount =
                            yzMainService.judgePCExistBy(yzMain.getParentNo(),
                                DateUtil.getDay8StrByDay(yyrqD),
                                Integer.valueOf(pcId));
                        
                        if (existCount > 0)
                        {
                            checkOk = false;
                            for (int n = 0; n < batchList.size(); n++)
                            {
                                BatchBean batchTemp = batchList.get(n);
                                existCount =
                                    yzMainService.judgePCExistBy(yzMain.getParentNo(),
                                        DateUtil.getDay8StrByDay(yyrqD),
                                        batchTemp.getId_().intValue());
                                if (existCount > 0)
                                {
                                    continue;
                                }
                                
                                batchBean = batchTemp;
                                checkOk = true;
                                break;
                            }
                        }
                        
                        if (!checkOk)
                        {
                            continue;
                        }
                        
                        int time = Integer.valueOf(recordBean.getSchedule());
                        
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String yyrqStr = recordBean.getOccDT();
                        try
                        {
                            yyrqD = formatter.parse(yyrqStr);
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                        
                        Boolean result =
                            createPCCustom(batchBean,
                                yzMain,
                                ydMain,
                                pidsjRuCangMap,
                                scrqD,
                                yyrqD,
                                ydMainList,
                                pcListOK,
                                pidPC_bottLab,
                                time,
                                record,
                                pidsjTemp);
                        
                        if (!result)
                        {
                            continue;
                        }
                        
                    }
                    
                }
                else if ("noexist".equals(SPLIT_yysj))
                {
                    int pcNum = pcListAll.size();
                    int addNum = recordAmount - ydSize;
                    
                    int specialPC = 0;
                    
                    for (int n = 1; n < addNum + 1; n++)
                    {
                        
                        if (ydSize + n <= pcNum)
                        {
                            
                            //一般规则的批次
                            Frequency pcTemp = pcListAll.get(ydSize + n - 1);
                            existCount =
                                yzMainService.judgePCExistBy(yzMain.getParentNo(),
                                    DateUtil.getDay8StrByDay(yyrqD),
                                    pcTemp.getPc_id().intValue());
                            if (existCount > 0)
                            {
                                addNum++;
                                continue;
                            }
                            int time = pcTemp.getRu_serialNum();
                            
                            Boolean result =
                                createPC(pcTemp,
                                    yzMain,
                                    ydMain,
                                    pidsjRuCangMap,
                                    scrqD,
                                    yyrqD,
                                    ydMainList,
                                    pcListOK,
                                    pidPC_bottLab,
                                    time,
                                    recordYY);
                            
                            if (!result)
                            {
                                continue;
                            }
                            
                        }
                        else
                        {
                            
                            int differNum = ydSize - pcNum;
                            int totalNum = 0;
                            if (differNum > 0)
                            {
                                
                                totalNum = specialPC + differNum;
                            }
                            else
                            {
                                totalNum = specialPC;
                            }
                            
                            if (totalNum > 5)
                            {
                                return ydMainList;
                            }
                            
                            BatchBean bacth = batchList.get(totalNum);
                            existCount =
                                yzMainService.judgePCExistBy(yzMain.getParentNo(),
                                    DateUtil.getDay8StrByDay(yyrqD),
                                    bacth.getId_().intValue());
                            if (existCount > 0)
                            {
                                addNum++;
                                specialPC++;
                                continue;
                            }
                            int time = ydSize + n;
                            int pidsjTemp = bacth.getId_().intValue();
                            Boolean result =
                                createPCCustom(bacth,
                                    yzMain,
                                    ydMain,
                                    pidsjRuCangMap,
                                    scrqD,
                                    yyrqD,
                                    ydMainList,
                                    pcListOK,
                                    pidPC_bottLab,
                                    time,
                                    recordYY,
                                    pidsjTemp);
                            if (!result)
                            {
                                continue;
                            }
                            specialPC++;
                            
                        }
                    }
                    
                }
                
            }
        }
        
        return ydMainList;
    }
    
    private static Boolean createPCCustom(BatchBean bacth, DoctorAdviceMain yzMain, PrescriptionMain ydMain,
                                          Map<String, Object> pidsjRuCangMap, Date scrqD, Date yyrqD, List<PrescriptionMain> ydMainList, List<Frequency> pcListOK,
                                          Map<String, String> pidPC_bottLab, int time, List<ExcuteRecordBean> record, int pidsjTemp)
    {
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        if (yzMainService == null)
        {
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
                + "_" + pidsjTemp) != null)
        {
            return false;
        }
        
        ydMain = new PrescriptionMain(yzMain, scrqD, yyrqD);
        ydMain.setYzshzt(1);
        ydMain.setSerialNumber(NumUtil.getObjInt(pcTemp.getRu_serialNum()));
        ydMain.setZxbc(pcTemp.getPc_id().intValue());
        ydMain.setPidrqzxbc(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
            + pcTemp.getPc_id().intValue());
        ydMain.setPidsj(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
            + pidsjTemp);
        
        // 拼接子医嘱号
        ydMain.setYdzxzt(0);
        
        // 设置床号
        if(CollectionUtils.isNotEmpty(record))
        {
            ydMain.setBedno(record.get(0).getBedNo());
        }
        
        boolean f = true;
        for (PrescriptionMain ydMainTemp : ydMainList)
        {
            //防止pidsj重复，生成相同批次
            if (ydMainTemp.getPidsj().equals(ydMain.getPidsj()))
            {
                f = false;
            }
        }
        if (f)
        {
            ydMainList.add(ydMain);
            pcListOK.add(pcTemp);
            pidPC_bottLab.put(ydMain.getPidrqzxbc(), ydMain.getBottleLabelNum());
            
        }
        
        return f;
    }
    
    private static Boolean createPC(Frequency pcTemp, DoctorAdviceMain yzMain, PrescriptionMain ydMain, Map<String, Object> pidsjRuCangMap,
                                    Date scrqD, Date yyrqD, List<PrescriptionMain> ydMainList, List<Frequency> pcListOK, Map<String, String> pidPC_bottLab,
                                    int time, List<ExcuteRecordBean> record)
    {
        DoctorAdviceMainService yzMainService = ServletContextUtil.yzMainService;
        if (yzMainService == null)
        {
            return false;
        }
        
        if (pcTemp == null)
        {
            return false;
        }
        if (pidsjRuCangMap != null
            && pidsjRuCangMap.get(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD)
                + "_" + pcTemp.getPc_id().intValue()) != null)
        {
            return false;
        }
        
        ydMain = new PrescriptionMain(yzMain, scrqD, yyrqD);
        ydMain.setYzshzt(1);
        ydMain.setSerialNumber(NumUtil.getObjInt(pcTemp.getRu_serialNum()));
        ydMain.setZxbc(pcTemp.getPc_id().intValue());
        ydMain.setPidrqzxbc(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
            + pcTemp.getPc_id().intValue());
        ydMain.setPidsj(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
            + pcTemp.getPc_id().intValue());
        
        if ("exist".equals(SPLIT_yyxx) && record != null && record.size() != 0)
        {
            
            StringBuilder drugCode = new StringBuilder();
            StringBuilder drugName = new StringBuilder();
            StringBuilder dose = new StringBuilder();
            StringBuilder doseUnit = new StringBuilder();
            StringBuilder quantity = new StringBuilder();
            StringBuilder packUnit = new StringBuilder();
            
            ExcuteRecordBean detail = new ExcuteRecordBean();
            
            for (int i = 0; i < record.size(); i++)
            {
                
                detail = record.get(i);
                
                List<DoctorAdvice> yzDetail = yzMainService.getYZDetail(detail.getGroupNo(), detail.getDrugCode());
                if (yzDetail == null || yzDetail.size() != 1)
                {
                    continue;
                }
                
                drugCode.append(detail.getDrugCode() == null ? "" : detail.getDrugCode());
                drugName.append(detail.getDrugName() == null ? "" : detail.getDrugName());
                quantity.append(detail.getQuantity() == null ? "" : detail.getQuantity());
                packUnit.append(yzDetail.get(0).getMedicamentsPackingUnit() == null ? "" : yzDetail.get(0)
                    .getMedicamentsPackingUnit());
                dose.append(yzDetail.get(0).getDose() == null ? "" : yzDetail.get(0).getDose());
                doseUnit.append(yzDetail.get(0).getDoseUnit() == null ? "" : yzDetail.get(0).getDoseUnit());
                
                if (i < record.size() - 1)
                {
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
        for (PrescriptionMain ydMainTemp : ydMainList)
        {
            //防止pidsj重复，生成相同批次
            if (ydMainTemp.getPidsj().equals(ydMain.getPidsj()))
            {
                f = false;
            }
        }
        if (f)
        {
            ydMainList.add(ydMain);
            pcListOK.add(pcTemp);
            pidPC_bottLab.put(ydMain.getPidrqzxbc(), ydMain.getBottleLabelNum());
        }
        
        return f;
        
    }
    
    public static List<Prescription> genYDList(DoctorAdvice yz, Integer newYzshzt, List<Frequency> pcListOK, Map<String, String> pidPC_bottLab,
                                               Date scrqD, Date yyrqD, String pcID)
    {
        Prescription yd = null;
        List<Prescription> ydList = new ArrayList<Prescription>();
        
        SynYdRecordService synYdRecordService = ServletContextUtil.synYdRecordService;
        if (synYdRecordService == null)
        {
            return ydList;
        }
        
        if (yz != null && pcListOK != null && pcListOK.size() > 0)
        {
            for (Frequency pcTemp : pcListOK)
            {
                if ("ydzxjl".equals(SPLIT_MODE))
                {
                    
                    if ("exist".equals(SPLIT_yyxx))
                    {
                        
                        List<ExcuteRecordBean> record =
                            synYdRecordService.getExcuteRecordBeanByPC(yz.getParentNo(),
                                yz.getActOrderNo(),
                                pcTemp.getPc_id(),
                                yyrqD);
                        if (record == null || record.size() == 0)
                        {
                            yd = new Prescription(yz, scrqD, yyrqD);
                        }
                        else
                        {
                            ExcuteRecordBean recordData = record.get(0);
                            String yyrqStr = recordData.getOccDT();
                            if (StringUtils.isNotBlank(yyrqStr))
                            {
                                
                                Date yyDate = null;
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                try
                                {
                                    yyDate = formatter.parse(yyrqStr);
                                    yd = new Prescription(yz, scrqD, yyDate, recordData);
                                }
                                catch (ParseException e)
                                {
                                    e.printStackTrace();
                                    yd = new Prescription(yz, scrqD, yyrqD, recordData);
                                }
                                
                            }
                            else
                            {
                                yd = new Prescription(yz, scrqD, yyrqD, recordData);
                            }
                            
                        }
                        yd.setYdzxzt(0);
                    }
                    else
                    {
                        yd = new Prescription(yz, scrqD, yyrqD);
                    }
                    
                }
                else
                {
                    yd = new Prescription(yz, scrqD, yyrqD);
                }
                yd.setYzshzt(newYzshzt);
                yd.setSerialNumber(NumUtil.getObjInt(pcTemp.getRu_serialNum()));
                yd.setZxbc(pcTemp.getPc_id().intValue());
                yd.setPidrqzxbc(yz.getParentNo() + "_" + yz.getYzlx() + "_" + DateUtil.getDay8StrByDay(scrqD) + "_"
                    + pcTemp.getPc_id().intValue());
                yd.setPidsj(pcID);
                yd.setBottleLabelNum(pidPC_bottLab.get(yd.getPidrqzxbc()));
                ydList.add(yd);
            }
        }
        return ydList;
    }
}
