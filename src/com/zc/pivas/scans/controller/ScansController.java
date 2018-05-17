package com.zc.pivas.scans.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.repository.BatchDao;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.app.bean.DrugListManagerBean;
import com.zc.pivas.app.dao.DrugListManagerDao;
import com.zc.pivas.checktype.bean.CheckTypeBean;
import com.zc.pivas.checktype.dao.CheckTypeDAO;
import com.zc.pivas.common.util.SysConstant.trackingRecord;
import com.zc.pivas.printlabel.repository.BottleLabelDao;
import com.zc.pivas.scans.bean.*;
import com.zc.pivas.scans.constant.ScansConstant;
import com.zc.pivas.scans.repository.ScansDao;
import com.zc.pivas.scans.service.ScansService;
import com.zc.pivas.synresult.bean.CheckOrderStatusRespon;
import com.zc.pivas.synresult.service.SendToRestful;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 扫码
 *
 * @author cacabin
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/scans")
public class ScansController extends SdBaseController {
    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(ScansController.class);

    /**
     * 获取批次信息
     */
    @Resource
    private BatchDao batchDao;

    /**
     * 查询床位信息
     */
    @Resource
    private ScansDao scansDao;

    /**
     * 核对类型数据处理
     */
    @Resource
    private CheckTypeDAO checkTypeDAO;

    @Resource
    private SendToRestful sendToRestful;

    @Resource
    private BatchService batchService;

    /**
     * 获取扫描信息
     */
    @Resource
    private ScansService scansSerivce;

    @Resource
    private DoctorAdviceMainService yzMainService;

    @Resource
    private PrescriptionMainService ydMainService;

    @Resource
    private BottleLabelDao bottleLabelDao;

    private String httpPort;

    private String serverIP;

    /**
     * 是否支持usb扫码
     */
    private boolean usbScanCode;

    @Resource
    private DrugListManagerDao drugListManagerdao;

    /***
     *
     * 初始化角色查询页面
     *
     * @return 角色查询页面
     */
    @RequestMapping(value = "/initScans")
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String initPatient(Model model, HttpServletRequest request) {
        String qryRQ = request.getParameter("qryRQ");
        if (qryRQ == null) {
            qryRQ = DateUtil.getCurrentDay8Str();
        }
        model.addAttribute("qryRQ", qryRQ);
        List<Batch> batchList = batchDao.queryBatchAllList();
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        List<ScansBatchBean> scansBatchList = scansSerivce.getScansBatchCountInfo();/*2016-0905  此处代码已经废弃*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qryRQ", qryRQ);

        boolean inFlag = false;//是否存在进仓核对
        boolean outFlag = false;//是否存在出仓核对
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 0)
                inFlag = true;
            if (checkType == 1)
                midFlag = true;
            if (checkType == 2)
                outFlag = true;
        }

        List<Map<String, Object>> scansBatchList2 = new ArrayList<Map<String, Object>>();

        if (midFlag) {

            scansBatchList2 = scansSerivce.qryCountMain(map);

        } else {
            scansBatchList2 = scansSerivce.qryCountMainWidthOutMid(map);

        }

        if (batchList != null) {
            // model.addAttribute("batchList",
            // new Gson().toJson(batchList));
            model.addAttribute("batchList", batchList);
        }

        int typeSize = 0;//有几个核对类型
        if (checkTypeList != null) {
            // model.addAttribute("checkTypeList",
            // new Gson().toJson(checkTypeList));
            model.addAttribute("checkTypeList", checkTypeList);
            typeSize = checkTypeList.size();
        }

        model.addAttribute("typeSize", typeSize);

        model.addAttribute("inFlag", inFlag);
        model.addAttribute("outFlag", outFlag);
        model.addAttribute("midFlag", midFlag);

        if (scansBatchList != null && !scansBatchList.isEmpty()) {
            model.addAttribute("scansBatchList", scansBatchList);
        }

        if (scansBatchList2 != null && !scansBatchList2.isEmpty()) {
            model.addAttribute("scansBatchList2", scansBatchList2);
        }

        return "pivas/scans/scansMain";
    }

    /**
     * 主页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/init")
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String initScans(Model model, HttpServletRequest request)
            throws Exception {

        String inpatientString = request.getParameter("inpatientString");

        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        List<Batch> batchList = scansSerivce.getScansBatchList();


        model.addAttribute("checkTypeList", checkTypeList);
        model.addAttribute("batchList", batchList);
        model.addAttribute("inpatientString", inpatientString);

//        Boolean flag = DataFormat.dateChange();
//        
//        if(flag){
//            model.addAttribute("day", "tomorrow");
//        }else{
        model.addAttribute("day", "today");
//        }

        return "pivas/scans/scans";

    }

    /**
     * 瓶签列表展示
     *
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPagePQ")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String getPagePQ(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {

        String inpatientStr = StrUtil.getObjStr(param.get("inpatientStr"));
        if (StringUtils.isNotBlank(inpatientStr) && inpatientStr != null) {
            String[] inpatientArray = inpatientStr.split(",");
            param.replace("inpatientStr", inpatientArray);
        }

        String pcStr = StrUtil.getObjStr(param.get("pcStr"));
        if (pcStr != null && StringUtils.isNotBlank(pcStr)) {
            String[] pcS = pcStr.split(",");
            param.replace("pcStr", pcS);
        }
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                param.put("midFlag", "1");
        }

        String yyrq = StrUtil.getObjStr(param.get("yyrq"));
        if (yyrq != null && StringUtils.isNotBlank(yyrq)) {
            String[] yyrqs = yzMainService.getDateRange(yyrq);
            if (yyrqs != null) {
                param.put("yyrqStart", yyrqs[0]);
                param.put("yyrqEnd", yyrqs[1]);
            }
        }

        List<BottleLabelBean> pqList = scansSerivce.getPQList(param);

        String[] columns = new String[]{"yDZT", "deptName", "bedNO", "patName", "pcName", "yDPQ", "pzmc", "smRQ", "smSBYY", "configurator"};

        JqueryStylePagingResults<BottleLabelBean> pagingResults = new JqueryStylePagingResults<BottleLabelBean>(columns);
        pagingResults.setDataRows(pqList);
        pagingResults.setTotal(pqList.size());
        pagingResults.setPage(1);

        return new Gson().toJson(pagingResults);

    }

    /**
     * 扫描追踪信息查询
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSmResultList")
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String getSmResultList(Model model, HttpServletRequest request, String yyrq) throws Exception {

        User user = getCurrentUser();
        String account = user.getAccount();

        String[] yyrqs = yzMainService.getDateRange(yyrq);
        String yyrqStart = null;
        String yyrqEnd = null;
        if (yyrqs != null) {
            yyrqStart = yyrqs[0];
            yyrqEnd = yyrqs[1];
        }

        List<ScanResult> result = scansSerivce.getSMResultList(account, yyrqStart, yyrqEnd);

        model.addAttribute("result", result);

        return "pivas/scans/table_sm";

    }

    /**
     * 药单的药品信息
     *
     * @param model
     * @param request
     * @param pqStr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydDetail")
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String ydDetail(Model model, HttpServletRequest request, String pqStr)
            throws Exception {

        List<BLabelWithPrescription> result = scansSerivce.getYdDetailList(pqStr);

        model.addAttribute("result", result);

        return "pivas/scans/table_yd";

    }

    /**
     * 停退信息
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/initStop")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String initStop(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {

        String inpatientStr = StrUtil.getObjStr(param.get("inpatientStr"));
        if (StringUtils.isNotBlank(inpatientStr) && inpatientStr != null) {
            String[] inpatientArray = inpatientStr.split(",");
            param.replace("inpatientStr", inpatientArray);
        }

        String pcStr = StrUtil.getObjStr(param.get("pcStr"));
        if (pcStr != null && StringUtils.isNotBlank(pcStr)) {
            String[] pcS = pcStr.split(",");
            param.replace("pcStr", pcS);
        }

        String yyrq = StrUtil.getObjStr(param.get("yyrq"));
        if (yyrq != null && StringUtils.isNotBlank(yyrq)) {
            String[] yyrqs = yzMainService.getDateRange(yyrq);
            if (yyrqs != null) {
                param.put("yyrqStart", yyrqs[0]);
                param.put("yyrqEnd", yyrqs[1]);
            }
        }

        List<BottleLabelBean> pqList = scansSerivce.getStopList(param);

        String[] columns = new String[]{"yDZT", "deptName", "bedNO", "patName", "pcName", "yDPQ", "pzmc", "smRQ", "smSBYY"};

        JqueryStylePagingResults<BottleLabelBean> pagingResults = new JqueryStylePagingResults<BottleLabelBean>(columns);
        pagingResults.setDataRows(pqList);
        pagingResults.setTotal(pqList.size());
        pagingResults.setPage(1);

        return new Gson().toJson(pagingResults);
    }

    /**
     * 批次界面信息
     *
     * @param param
     * @param model
     * @param request
     * @param pqStr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/initPC")
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String initPC(@RequestParam Map<String, Object> param, Model model, HttpServletRequest request, String pqStr) throws Exception {

        String inpatientStr = StrUtil.getObjStr(param.get("inpatientStr"));
        if (StringUtils.isNotBlank(inpatientStr) && inpatientStr != null) {
            String[] inpatientArray = inpatientStr.split(",");
            param.replace("inpatientStr", inpatientArray);
        }
        model.addAttribute("inpatientStr", inpatientStr);

        String pcStr = StrUtil.getObjStr(param.get("pcStr"));
        if (pcStr != null && StringUtils.isNotBlank(pcStr)) {
            String[] pcS = pcStr.split(",");
            param.replace("pcStr", pcS);
        }
        model.addAttribute("pcStr", pcStr);

        String yyrq = StrUtil.getObjStr(param.get("yyrq"));
        model.addAttribute("yyrq", yyrq);

        String checkType = StrUtil.getObjStr(param.get("checkType"));
        model.addAttribute("checkType", 7);

        List<ScanPcNum> pcTitle = scansSerivce.getBatchList(param);

        model.addAttribute("pcTitle", new Gson().toJson(pcTitle));

        return "pivas/scans/table_pc";

    }

    /**
     * 查询批次信息
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryPC")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String queryPC(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {

        String inpatientStr = StrUtil.getObjStr(param.get("inpatientStr"));
        if (StringUtils.isNotBlank(inpatientStr) && inpatientStr != null) {
            String[] inpatientArray = inpatientStr.split(",");
            param.replace("inpatientStr", inpatientArray);
        }

        String pcStr = StrUtil.getObjStr(param.get("pcStr"));
        if (pcStr != null && StringUtils.isNotBlank(pcStr)) {
            String[] pcS = pcStr.split(",");
            param.replace("pcStr", pcS);
        }

        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                param.put("midFlag", "1");
        }

        String yyrq = StrUtil.getObjStr(param.get("yyrq"));
        if (yyrq != null && StringUtils.isNotBlank(yyrq)) {
            String[] yyrqs = yzMainService.getDateRange(yyrq);
            if (yyrqs != null) {
                param.put("yyrqStart", yyrqs[0]);
                param.put("yyrqEnd", yyrqs[1]);
            }
        }

        List<ScanPcNum> pcTitle = scansSerivce.getBatchList(param);

        StringBuilder columnStr = new StringBuilder();
        columnStr.append("deptname");
        int pcLength = pcTitle.size();

        for (int i = 0; i < pcLength; i++) {
            columnStr.append(",pcnumber_" + i);
        }
        String[] columns = columnStr.toString().split(",");

        List<Map<String, Object>> resultBean = new ArrayList<>();

        //病区列表
        List<Map<String, Object>> deptList = scansSerivce.getDeptList(param);

        //批次数据
        List<String> numberList = new ArrayList<>();
        Map<String, Object> totalResult = new HashMap<>();
        totalResult.put("deptname", "总计");

        for (int i = 0; i < pcLength; i++) {

            ScanPcNum pc = pcTitle.get(i);
            String pccode = pc.getPccode();

            columnStr.append(",pcnumber_" + i);

            numberList = scansSerivce.getPCNumber(null, pccode, param);

            if (numberList.size() < 3) {

                log.error("扫描获取批次数据错误");
                throw new Exception("扫描获取批次数据错误");
            }

            String value = numberList.get(0) + "(" + numberList.get(1) + "/" + numberList.get(2) + ")";
            totalResult.put("pcnumber_" + i, value);
        }

        resultBean.add(totalResult);

        //遍历病区，获取每个病区，每一个批次打印数据
        for (Map<String, Object> dept : deptList) {
            boolean hasData = false;
            Map<String, Object> result = new HashMap<>();

            String deptcode = (String) dept.get("DEPTCODE");
            String deptname = (String) dept.get("DEPTNAME");
            result.put("deptname", deptname);

            for (int i = 0; i < pcLength; i++) {

                ScanPcNum pc = pcTitle.get(i);
                String pccode = pc.getPccode();

                columnStr.append(",pcnumber_" + i);

                numberList = scansSerivce.getPCNumber(deptcode, pccode, param);

                if (numberList.size() < 3) {

                    log.error("扫描获取批次数据错误");
                    throw new Exception("扫描获取批次数据错误");
                }

                if (!numberList.toString().equals("[0, 0, 0]")) {
                    hasData = true;
                }

                String value = numberList.get(0) + "(" + numberList.get(1) + "/" + numberList.get(2) + ")";
                result.put("pcnumber_" + i, value);
            }

            if (hasData) {
                resultBean.add(result);
            }

        }

        JqueryStylePagingResults<Map<String, Object>> pagingResults =
                new JqueryStylePagingResults<Map<String, Object>>(columns);

        pagingResults.setDataRows(resultBean);
        pagingResults.setTotal(resultBean.size());
        pagingResults.setPage(1);

        return new Gson().toJson(pagingResults);

    }

    /**
     * 一键
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ScansResultAll")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String ScansResultAll(@RequestParam final Map<String, Object> param) throws Exception {

        String inpatientStr = StrUtil.getObjStr(param.get("inpatientStr"));
        if (StringUtils.isNotBlank(inpatientStr) && inpatientStr != null) {
            String[] inpatientArray = inpatientStr.split(",");
            param.replace("inpatientStr", inpatientArray);
        }

        String pcStr = StrUtil.getObjStr(param.get("pcStr"));
        if (pcStr != null && StringUtils.isNotBlank(pcStr)) {
            String[] pcS = pcStr.split(",");
            param.replace("pcStr", pcS);
        }
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1) {
                param.put("midFlag", "1");
                midFlag = true;
            }
        }

        final boolean isMidFlag = midFlag;

        log.error("扫描开始:" + DateUtil.getYYYYMMDDHHMMSSDate(new Date()) + "\n");

        String resultMsg = "总共扫描：" + scansSerivce.getScanPQList(param, null).size();
        Thread task1 = new Thread(new Runnable() {
            public void run() {
                List<BottleLabelBean> pqList = scansSerivce.getScanPQList(param, "0,1".split(","));

                for (BottleLabelBean pqBean : pqList) {
                    // 瓶签扫描
                    try {
                        scanYD(isMidFlag, param, pqBean);
                    } catch (Exception e) {
                        log.error("扫描失败，瓶签号：" + pqBean.getyDPQ(), e);
                    }
                }
            }
        });
        task1.start();

        Thread task2 = new Thread(new Runnable() {
            public void run() {
                List<BottleLabelBean> pqList = scansSerivce.getScanPQList(param, "2,3".split(","));

                for (BottleLabelBean pqBean : pqList) {
                    // 瓶签扫描
                    try {
                        scanYD(isMidFlag, param, pqBean);
                    } catch (Exception e) {
                        log.error("扫描失败，瓶签号：" + pqBean.getyDPQ(), e);
                    }
                }
            }
        });
        task2.start();

        Thread task3 = new Thread(new Runnable() {
            public void run() {
                List<BottleLabelBean> pqList = scansSerivce.getScanPQList(param, "4,5".split(","));

                for (BottleLabelBean pqBean : pqList) {
                    // 瓶签扫描
                    try {
                        scanYD(isMidFlag, param, pqBean);
                    } catch (Exception e) {
                        log.error("扫描失败，瓶签号：" + pqBean.getyDPQ(), e);
                    }
                }
            }
        });
        task3.start();

        Thread task4 = new Thread(new Runnable() {
            public void run() {
                List<BottleLabelBean> pqList = scansSerivce.getScanPQList(param, "6,7".split(","));

                for (BottleLabelBean pqBean : pqList) {
                    // 瓶签扫描
                    try {
                        scanYD(isMidFlag, param, pqBean);
                    } catch (Exception e) {
                        log.error("扫描失败，瓶签号：" + pqBean.getyDPQ(), e);
                    }
                }
            }
        });
        task4.start();

        Thread task5 = new Thread(new Runnable() {
            public void run() {
                List<BottleLabelBean> pqList = scansSerivce.getScanPQList(param, "8,9".split(","));

                for (BottleLabelBean pqBean : pqList) {
                    // 瓶签扫描
                    try {
                        scanYD(isMidFlag, param, pqBean);
                    } catch (Exception e) {
                        log.error("扫描失败，瓶签号：" + pqBean.getyDPQ(), e);
                    }
                }
            }
        });
        task5.start();

        while (task1.isAlive() || task2.isAlive() || task3.isAlive() || task4.isAlive() || task5.isAlive()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("扫描瓶签线程错误！", e.getMessage());
            }
        }

        log.error("扫描结束:" + DateUtil.getYYYYMMDDHHMMSSDate(new Date()) + "\n");

        //查询扫描结果
        List<Map<String, Object>> resultList = scansSerivce.getScanRestult(param);


        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map<String, Object> map : resultList) {
                // 成功
                if (map.get("SMJG").toString().equals("0")) {
                    resultMsg = resultMsg + "  成功：" + map.get("COUNT").toString() + "条  ";
                }
                if (map.get("SMJG").toString().equals("1")) {
                    resultMsg = resultMsg + "  失败：" + map.get("COUNT").toString() + "条  ";
                }
            }
        }

        return buildSuccessJsonMsg(resultMsg);
    }

    /**
     * 开始扫描
     *
     * @param midFlag
     * @param pqBean
     * @throws Exception
     * @throws ParseException
     * @throws JSONException
     */
    private void scanYD(boolean midFlag, Map<String, Object> param, BottleLabelBean pqBean) throws Exception, ParseException, JSONException {
        String scansType = StrUtil.getObjStr(param.get("scansType"));
        String qryYyrq = StrUtil.getObjStr(param.get("yyrq"));

        String errMess = "";

        String pqstr = pqBean.getyDPQ();
        BLabelWithPrescription pqyd = scansSerivce.queryPQYDListByBottNum(pqstr);

        if (pqyd != null) {

            //药单状态是否执行
            if (pqyd.getYd_ydzxzt() != null && pqyd.getYd_ydzxzt().intValue() == 0) {

                if (pqyd.getYd_dybz() == null || pqyd.getYd_dybz().intValue() != 0) {
                    errMess = "瓶签未打印";

                } else {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date yyrq = pqyd.getYd_yyrq();
                    String[] qryRange = yzMainService.getDateRange(qryYyrq);
                    Date yyrqS = sdf.parse(qryRange[0]);
                    Date yyrqE = sdf.parse(qryRange[1]);

                    String yyrqToday = DateUtil.getCurrentDay8Str();
                    String[] todayRange = yzMainService.getDateRange(yyrqToday);
                    Date todayS = sdf.parse(todayRange[0]);

                    if (yyrq.getTime() == todayS.getTime()) {

                        pqyd.setYyrqDelToday(0);

                    } else if (yyrq.getTime() > todayS.getTime()) {

                        pqyd.setYyrqDelToday(1);
                    } else {

                        pqyd.setYyrqDelToday(-1);
                    }

                    Date scrq = pqyd.getYd_scrq();
                    if (scrq.getTime() == todayS.getTime()) {

                        pqyd.setScrqDelToday(0);

                    } else if (scrq.getTime() > todayS.getTime()) {

                        pqyd.setScrqDelToday(1);
                    } else {

                        pqyd.setScrqDelToday(-1);
                    }

                    if (!(yyrq.getTime() <= yyrqE.getTime() && yyrq.getTime() >= yyrqS.getTime())) {
                        errMess = "非当日药单";
                    } else if (pqyd.getYyrqDelToday().intValue() >= 0) {

                        if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                            if (pqyd.getPq_ydzt().intValue() > 4) {
                                errMess = "重复扫描，瓶签号[" + pqstr + "]";
                            }
                        } else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {
                            if (pqyd.getPq_ydzt().intValue() > 5) {
                                errMess = "重复扫描，瓶签号[" + pqstr + "]";
                            } else if (pqyd.getPq_ydzt().intValue() < 5) {
                                errMess = "瓶签号[" + pqstr + "]还未入仓";
                            }
                        } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {
                            if (pqyd.getPq_ydzt().intValue() > 6) {
                                errMess = "重复扫描，瓶签号[" + pqstr + "]";
                            } else if (pqyd.getPq_ydzt().intValue() < 5) {
                                errMess = "瓶签号[" + pqstr + "]还未入仓";

                            } else if (midFlag && pqyd.getPq_ydzt().intValue() < 6) {

                                errMess = "瓶签号[" + pqstr + "]还未仓内扫描";
                            }
                        }

                    } else {
                        errMess = "非当日或明日药单";
                    }
                }
            } else if (pqyd.getYd_ydzxzt().intValue() == 1) {

                errMess = "医嘱状态已停止";
                scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);

            } else if (pqyd.getYd_ydzxzt().intValue() == 3) {

                errMess = "医嘱状态已退费";
                scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
            }

            if (StringUtils.isNotBlank(errMess)) {
                saveSmResult(scansType, errMess, pqyd, false);
            }

            Date start = new Date();

            JSONObject data = new JSONObject();
            List<JSONObject> bottleNumList = new ArrayList<JSONObject>();
            data.put("parentNo", pqyd.getYd_parentNo());
            data.put("bottleNo", pqstr);
            bottleNumList.add(data);

            List<CheckOrderStatusRespon> checkOrderStatusList = sendToRestful.checkOrderStatus(bottleNumList);

            log.info("调用检查医嘱状态接口：" + String.valueOf(new Date().getTime() - start.getTime()) + "\n");

            if (checkOrderStatusList != null && checkOrderStatusList.size() > 0) {
                CheckOrderStatusRespon checkOrderStatusRespon = checkOrderStatusList.get(0);
                if (ScansConstant.BottleLabelStatus.STOP.toString().equals(checkOrderStatusRespon.getResult())) {
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("parentNo", pqyd.getYd_parentNo());
                    yzMainService.resetYZCheckStatus(updateMap);
                    errMess = "医嘱已停止";
                    scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                } else if ("3".equals(checkOrderStatusRespon.getResult())) {//如果调用接口，返回状态3，表示药品有变化，需要特殊处理
                    if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                        if (pqyd.getScrqDelToday() >= 0) {//当天审核的医嘱，如果药品数据有变化，提示重新审核医嘱 排批次
                            Map<String, Object> updateMap = new HashMap<String, Object>();
                            updateMap.put("parentNo", pqyd.getYd_parentNo());
                            yzMainService.resetYZCheckStatus(updateMap);
                            //清空 医嘱 审核状态
                            errMess = checkOrderStatusRespon.getResultDesc() + "，需重新审核医嘱";
                        } else {
                            errMess = checkOrderStatusRespon.getResultDesc() + "，非当日药单，无法入仓扫描";
                        }
                    } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {
                        errMess = checkOrderStatusRespon.getResultDesc() + "，无法出仓";
                    }
                } else if ("4".equals(checkOrderStatusRespon.getResult())) {

                    errMess = "药单已停止";
                    scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                } else if ("5".equals(checkOrderStatusRespon.getResult())) {

                    errMess = "药单已退费";
                    scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                }

            } else {

                errMess = "扫描失败";
            }

            if (StringUtils.isNotBlank(errMess)) {
                addOperLog(AmiLogConstant.MODULE_BRANCH.PM.BOTTLE_SCAN, AmiLogConstant.BRANCH_SYSTEM.PM, errMess, false);

                saveSmResult(scansType, errMess, pqyd, false);
            }

            List<MedicineBean> medicineBeanList = scansDao.queryMedicineList2(pqyd.getPq_pidsj());
            User currUser = getCurrentUser();
            String username = "";
            if (currUser != null) {
                username = currUser.getAccount();
            }
            errMess = scansSerivce.in_out_pack(scansType, medicineBeanList, pqyd, username);

            if (StringUtils.isNotBlank(errMess)) {
                saveSmResult(scansType, errMess, pqyd, false);
            }

            //将rucangNeedCheck 重置成0 【 医嘱变化更新成1后 入仓成功 要重置成0】
            Map<String, Object> updateMap = new HashMap<String, Object>();
            updateMap.put("rucangNeedCheck", 0);
            updateMap.put("yzlx", pqyd.getYd_yzlx());
            updateMap.put("parentNo", pqyd.getYd_parentNo());
            yzMainService.updateCheckYZMain(updateMap);

            saveSmResult(scansType, "", pqyd, true);

            int recordNum = 0;
            String recordMsg = "";
            if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                recordNum = trackingRecord.rcsm;
                recordMsg = trackingRecord.rcsmStr;
            } else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {

                recordNum = trackingRecord.cnsm;
                recordMsg = trackingRecord.cnsmStr;
            } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {

                recordNum = trackingRecord.ccsm;
                recordMsg = trackingRecord.ccsmStr;
            }

            User currentUser = (User) getCurrentUser();

            ydMainService.addTrackingRecord(pqyd.getPq_pidsj(), recordNum, recordMsg, currentUser.getAccount());

            if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {

                scansDao.addPQConfigurator(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), currentUser.getAccount());

                DrugListManagerBean drugListBean = new DrugListManagerBean();
                drugListBean.bottleNum = pqyd.getPq_ydpq();
                drugListBean.pzCode = currentUser.getAccount();
                drugListBean.pzmc = currentUser.getName();
                drugListBean.pzrq = new Date();

                try {
                    drugListManagerdao.updateDrugListMainManager(drugListBean);
                    drugListManagerdao.updateDrugListManager(drugListBean);
                } catch (Exception e) {
                    log.error("更新药单配置人员状态失败", e);
                }
            }
        }
    }

    /**
     * 扫描结果新
     * @param pqstr
     * @param scansType
     * @param qryYyrq
     * @param pcStr
     * @param inpatientStr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ScansResult")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String ScansResult(String pqstr, String scansType, String qryYyrq, String pcStr, String inpatientStr) throws Exception {
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }

        String errMess = "";

        if (StringUtils.isNotBlank(pqstr) && StringUtils.isNotBlank(scansType)) {
            BLabelWithPrescription pqyd = scansSerivce.queryPQYDListByBottNum(pqstr);
            if (pqyd != null) {

                if (StringUtils.isNotBlank(inpatientStr) && inpatientStr != null) {
                    String[] inpatientArray = inpatientStr.split(",");
                    String wardcode = pqyd.getYd_wardCode();
                    int count = Arrays.binarySearch(inpatientArray, wardcode);
                    if (count < 0) {
                        return buildFailJsonMsg("非当前选择病区");
                    }
                }

                if (StringUtils.isNotBlank(pcStr)) {

                    String pcid = pqyd.getPq_zxbc().toString().trim();
                    String[] pcArray = pcStr.split(",");

                    int count = Arrays.binarySearch(pcArray, pcid);
                    if (count < 0) {
                        return buildFailJsonMsg("非当前选择批次");
                    }
                }

                //判断瓶签是否变动
                if (!pqstr.equals(pqyd.getPq_ydpq())) {
                    //因批次变动
                    if (StringUtils.isNotBlank(pqyd.getPq_zxbc().toString())
                            && StringUtils.isNotBlank(pqyd.getPq_zxbcLast().toString())
                            && pqyd.getPq_zxbc().intValue() != pqyd.getPq_zxbcLast().intValue()) {
                        errMess = "批次已变更，新批次为" + pqyd.getPb_name();
                    }//因瓶签号变动
                    else {
                        errMess = "瓶签已变更，新瓶签为" + pqyd.getPq_ydpq();
                    }

                    return buildFailJsonMsg(errMess);
                }

                //药单状态是否执行
                if (pqyd.getYd_ydzxzt() != null && pqyd.getYd_ydzxzt().intValue() == 0) {

                    if (pqyd.getYd_dybz() == null || pqyd.getYd_dybz().intValue() != 0) {
                        errMess = "瓶签未打印";

                    } else {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date yyrq = pqyd.getYd_yyrq();
                        String[] qryRange = yzMainService.getDateRange(qryYyrq);
                        Date yyrqS = sdf.parse(qryRange[0]);
                        Date yyrqE = sdf.parse(qryRange[1]);

                        String yyrqToday = DateUtil.getCurrentDay8Str();
                        String[] todayRange = yzMainService.getDateRange(yyrqToday);
                        Date todayS = sdf.parse(todayRange[0]);

                        if (yyrq.getTime() == todayS.getTime()) {

                            pqyd.setYyrqDelToday(0);

                        } else if (yyrq.getTime() > todayS.getTime()) {

                            pqyd.setYyrqDelToday(1);
                        } else {

                            pqyd.setYyrqDelToday(-1);
                        }

                        Date scrq = pqyd.getYd_scrq();
                        if (scrq.getTime() == todayS.getTime()) {

                            pqyd.setScrqDelToday(0);

                        } else if (scrq.getTime() > todayS.getTime()) {

                            pqyd.setScrqDelToday(1);
                        } else {

                            pqyd.setScrqDelToday(-1);
                        }


                        if (!(yyrq.getTime() <= yyrqE.getTime() && yyrq.getTime() >= yyrqS.getTime())) {
                            errMess = "非当日药单";
                        } else if (pqyd.getYyrqDelToday().intValue() >= 0) {

                            if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                                if (pqyd.getPq_ydzt().intValue() > 4) {
                                    errMess = "重复扫描，瓶签号[" + pqstr + "]";
                                }
                            } else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {
                                if (pqyd.getPq_ydzt().intValue() > 5) {
                                    errMess = "重复扫描，瓶签号[" + pqstr + "]";
                                } else if (pqyd.getPq_ydzt().intValue() < 5) {
                                    errMess = "瓶签号[" + pqstr + "]还未入仓";
                                }
                            } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {
                                if (pqyd.getPq_ydzt().intValue() > 6) {
                                    errMess = "重复扫描，瓶签号[" + pqstr + "]";
                                } else if (pqyd.getPq_ydzt().intValue() < 5) {
                                    errMess = "瓶签号[" + pqstr + "]还未入仓";

                                } else if (midFlag && pqyd.getPq_ydzt().intValue() < 6) {

                                    errMess = "瓶签号[" + pqstr + "]还未仓内扫描";
                                }
                            }

                        } else {
                            errMess = "非当日或明日药单";
                        }
                    }
                } else if (pqyd.getYd_ydzxzt().intValue() == 1) {

                    errMess = "医嘱状态已停止";
                    scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);

                } else if (pqyd.getYd_ydzxzt().intValue() == 3) {

                    errMess = "医嘱状态已退费";
                    scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                }

                if (StringUtils.isNotBlank(errMess)) {

                    saveSmResult(scansType, errMess, pqyd, false);

                    addOperLog(AmiLogConstant.MODULE_BRANCH.PM.BOTTLE_SCAN, AmiLogConstant.BRANCH_SYSTEM.PM, errMess, false);
                    return buildFailJsonMsg(errMess);
                }

                JSONObject data = new JSONObject();
                List<JSONObject> bottleNumList = new ArrayList<JSONObject>();
                data.put("parentNo", pqyd.getYd_parentNo());
                data.put("bottleNo", pqstr);
                bottleNumList.add(data);
                List<CheckOrderStatusRespon> checkOrderStatusList = sendToRestful.checkOrderStatus(bottleNumList);

                if (checkOrderStatusList != null && checkOrderStatusList.size() > 0) {

                    CheckOrderStatusRespon checkOrderStatusRespon = checkOrderStatusList.get(0);
                    if (ScansConstant.BottleLabelStatus.STOP.toString().equals(checkOrderStatusRespon.getResult())) {
                        Map<String, Object> updateMap = new HashMap<String, Object>();
                        updateMap.put("parentNo", pqyd.getYd_parentNo());
                        yzMainService.resetYZCheckStatus(updateMap);
                        errMess = "医嘱已停止";
                        scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    } else if ("3".equals(checkOrderStatusRespon.getResult())) {//如果调用接口，返回状态3，表示药品有变化，需要特殊处理
                        if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                            if (pqyd.getScrqDelToday() >= 0) {//当天审核的医嘱，如果药品数据有变化，提示重新审核医嘱 排批次
                                Map<String, Object> updateMap = new HashMap<String, Object>();
                                //updateMap.put("yzlx", pqyd.getYd_yzlx());
                                updateMap.put("parentNo", pqyd.getYd_parentNo());
                                yzMainService.resetYZCheckStatus(updateMap);
                                //清空 医嘱 审核状态
                                errMess = checkOrderStatusRespon.getResultDesc() + "，需重新审核医嘱";
                            } else {
                                errMess = checkOrderStatusRespon.getResultDesc() + "，非当日药单，无法入仓扫描";
                            }
                        } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {
                            errMess = checkOrderStatusRespon.getResultDesc() + "，无法出仓";
                        }
                    } else if ("4".equals(checkOrderStatusRespon.getResult())) {

                        errMess = "药单已停止";
                        scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    } else if ("5".equals(checkOrderStatusRespon.getResult())) {

                        errMess = "药单已退费";
                        scansSerivce.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    }

                } else {

                    errMess = "扫描失败";
                }

                if (StringUtils.isNotBlank(errMess)) {
                    saveSmResult(scansType, errMess, pqyd, false);

                    addOperLog(AmiLogConstant.MODULE_BRANCH.PM.BOTTLE_SCAN, AmiLogConstant.BRANCH_SYSTEM.PM, errMess, false);
                    return buildFailJsonMsg(errMess);
                }

                List<MedicineBean> medicineBeanList = scansDao.queryMedicineList2(pqyd.getPq_pidsj());
                User currUser = getCurrentUser();
                String username = "";
                if (currUser != null) {
                    username = currUser.getAccount();
                }
                errMess = scansSerivce.in_out_pack(scansType, medicineBeanList, pqyd, username);

                if (StringUtils.isNotBlank(errMess)) {
                    saveSmResult(scansType, errMess, pqyd, false);
                    return buildFailJsonMsg(errMess);
                }

                //将rucangNeedCheck 重置成0 【 医嘱变化更新成1后 入仓成功 要重置成0】
                Map<String, Object> updateMap = new HashMap<String, Object>();
                updateMap.put("rucangNeedCheck", 0);
                updateMap.put("yzlx", pqyd.getYd_yzlx());
                updateMap.put("parentNo", pqyd.getYd_parentNo());
                yzMainService.updateCheckYZMain(updateMap);

                saveSmResult(scansType, "", pqyd, true);

                int recordNum = 0;
                String recordMsg = "";
                if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                    recordNum = trackingRecord.rcsm;
                    recordMsg = trackingRecord.rcsmStr;
                } else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {

                    recordNum = trackingRecord.cnsm;
                    recordMsg = trackingRecord.cnsmStr;
                } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {

                    recordNum = trackingRecord.ccsm;
                    recordMsg = trackingRecord.ccsmStr;
                }

                User currentUser = (User) getCurrentUser();

                ydMainService.addTrackingRecord(pqyd.getPq_pidsj(), recordNum, recordMsg, currentUser.getAccount());

                if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {

                    scansDao.addPQConfigurator(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), currentUser.getAccount());

                    DrugListManagerBean drugListBean = new DrugListManagerBean();
                    drugListBean.bottleNum = pqyd.getPq_ydpq();
                    drugListBean.pzCode = currentUser.getAccount();
                    drugListBean.pzmc = currentUser.getName();
                    drugListBean.pzrq = new Date();

                    try {
                        drugListManagerdao.updateDrugListMainManager(drugListBean);
                        drugListManagerdao.updateDrugListManager(drugListBean);
                    } catch (Exception e) {
                        log.error("更新药单配置人员状态失败", e);
                    }
                }

                return buildSuccessJsonMsg(pqyd.getPq_ydpq());

            } else {
                addOperLog(AmiLogConstant.MODULE_BRANCH.PM.BOTTLE_SCAN, AmiLogConstant.BRANCH_SYSTEM.PM, "瓶签不存在", false);
                return buildFailJsonMsg("瓶签不存在");
            }

        } else {

            log.error("扫描瓶签获取瓶签号或扫描类型信息失败");

            addOperLog(AmiLogConstant.MODULE_BRANCH.PM.BOTTLE_SCAN, AmiLogConstant.BRANCH_SYSTEM.PM, "扫描失败", false);
            return buildFailJsonMsg("扫描失败");
        }

    }

    /**
     * 保存扫描结果
     *
     * @param scansType
     * @param errMess
     * @param pqyd
     * @param isSucc
     */
    private void saveSmResult(String scansType, String errMess, BLabelWithPrescription pqyd, boolean isSucc) {
        BottleLabelResult bottleLabelExtis = scansSerivce.queryBottleLabelResult2(pqyd.getPq_pidsj());
        boolean fExists = true;
        if (bottleLabelExtis == null) {
            fExists = false;
        }

        User user = getCurrentUser();
        String account = user.getAccount();

        BottleLabelResult bottleLabelResult = new BottleLabelResult();
        bottleLabelResult.setSmLX(scansType);
        bottleLabelResult.setSmJG(isSucc ? 0 : 1);
        bottleLabelResult.setSmRQ(DateUtil.getCurrentDay8Str());
        bottleLabelResult.setSmSBYY(isSucc ? "" : errMess);
        bottleLabelResult.setZxbc(pqyd.getPq_zxbc());
        bottleLabelResult.setYdID(pqyd.getPq_pidsj());
        bottleLabelResult.setOperator(account);
        bottleLabelResult.setYdpq(pqyd.getPq_ydpq());

        if (fExists) {
            scansDao.updateBottleLabelRst(bottleLabelResult);
        } else {
            scansDao.insertBottleLabelRst(bottleLabelResult);
        }

    }


    /**
     * 扫描结果
     * @param barcode
     * @param scansType
     * @param batchID
     * @param qryRQ
     * @param batchName
     * @return
     */
    @RequestMapping(value = "/usbScansIn", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String usbScansIn(String barcode, String scansType, String batchID, String qryRQ, String batchName) {
        /*if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
        {
            return buildFailJsonMsg("仓内扫描只允许在PDA APP上进行");
        }*/

        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }

        Date excute = new Date();
        String errMess = null;
        String succMess = null;
        BLabelWithPrescription pqyd = null;
        boolean recordErr = false;
        if (barcode != null && scansType != null && batchName != null) {
            pqyd = scansSerivce.queryPQYDListByBottNum(barcode);
            if (pqyd == null) {
                errMess = "瓶签不存在";
            } else {
                try {
                    JSONObject data = new JSONObject();
                    List<JSONObject> bottleNumList = new ArrayList<JSONObject>();
                    data.put("parentNo", pqyd.getYd_parentNo());
                    data.put("bottleNo", barcode);
                    bottleNumList.add(data);

                    //正式
                    Date start = new Date();
                    List<CheckOrderStatusRespon> checkOrderStatusList = sendToRestful.checkOrderStatus(bottleNumList);

                    log.info("调用检查医嘱状态接口：" + String.valueOf(new Date().getTime() - start.getTime()) + "\n");

                    for (CheckOrderStatusRespon checkOrderStatusRespon : checkOrderStatusList) {
                        if (ScansConstant.BottleLabelStatus.STOP.toString().equals(checkOrderStatusRespon.getResult())) {
                            Map<String, Object> updateMap = new HashMap<String, Object>();
                            //updateMap.put("yzlx", pqyd.getYd_yzlx());
                            updateMap.put("parentNo", pqyd.getYd_parentNo());
                            yzMainService.resetYZCheckStatus(updateMap);
                            errMess = "医嘱已停止";
                            recordErr = true;
                        } else if ("3".equals(checkOrderStatusRespon.getResult())) {//如果调用接口，返回状态3，表示药品有变化，需要特殊处理
                            if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                                if (pqyd.getScrqDelToday() >= 0) {//当天审核的医嘱，如果药品数据有变化，提示重新审核医嘱 排批次
                                    Map<String, Object> updateMap = new HashMap<String, Object>();
                                    //updateMap.put("yzlx", pqyd.getYd_yzlx());
                                    updateMap.put("parentNo", pqyd.getYd_parentNo());
                                    yzMainService.resetYZCheckStatus(updateMap);
                                    //清空 医嘱 审核状态
                                    errMess = checkOrderStatusRespon.getResultDesc() + "，需重新审核医嘱";
                                    recordErr = true;
                                } else {
                                    errMess = checkOrderStatusRespon.getResultDesc() + "，非当日药单，无法入仓扫描";
                                    recordErr = true;
                                }
                            } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {
                                errMess = checkOrderStatusRespon.getResultDesc() + "，无法出仓";
                                recordErr = true;
                            }
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    errMess = "扫描错误，请联系管理员";
                    recordErr = true;

                    log.error("调用检查医嘱状态接口：" + String.valueOf(new Date().getTime() - excute.getTime()) + "\n");

                }
            }
            pqyd = scansSerivce.queryPQYDListByBottNum(barcode);
            if (errMess == null) {
                if (!pqyd.getPq_ydpq().equals(barcode)) {
                    if (pqyd.getPq_zxbc() != null && pqyd.getPq_zxbcLast() != null
                            && pqyd.getPq_zxbc().intValue() != pqyd.getPq_zxbcLast().intValue()) {
                        errMess = "批次已变更，新批次为" + pqyd.getPb_name();
                    } else {
                        errMess = "瓶签已变更，新瓶签为" + pqyd.getPq_ydpq();
                    }
                }
            }
            if (errMess == null) {
                if (pqyd.getYd_ydzxzt() != null && pqyd.getYd_ydzxzt().intValue() == 0) {//执行中
                    if (!DateUtil.getDay8StrByDay(pqyd.getYd_yyrq()).equals(qryRQ)) {
                        errMess = "非当日药单";
                    } else if (!batchName.equals(pqyd.getPbname2())) {
                        errMess = "当前药品批次是" + pqyd.getPb_name();
                    } else if (pqyd.getYyrqDelToday().intValue() >= 0) {
                        log.info("当前瓶签:[" + barcode + "]执行状态:" + scansType);
                        if (scansType.equals(ScansConstant.BottleLabelAction.IN)) {
                            if (pqyd.getPq_ydzt().intValue() > 4) {
                                errMess = "重复扫描，瓶签号[" + barcode + "]";
                            }
                        } else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE)) {
                            if (pqyd.getPq_ydzt().intValue() > 5) {
                                errMess = "重复扫描，瓶签号[" + barcode + "]";
                            } else if (pqyd.getPq_ydzt().intValue() < 5) {
                                errMess = "瓶签号[" + barcode + "]还未入仓";
                            }
                        } else if (scansType.equals(ScansConstant.BottleLabelAction.OUT)) {
                            if (pqyd.getPq_ydzt().intValue() > 6) {
                                errMess = "重复扫描，瓶签号[" + barcode + "]";
                            } else if (pqyd.getPq_ydzt().intValue() < 5) {
                                errMess = "瓶签号[" + barcode + "]还未入仓";
                            }

                            if (midFlag && pqyd.getPq_ydzt().intValue() < 6) {

                                errMess = "瓶签号[" + barcode + "]还未仓内扫描";
                            }

                        } else {
                            errMess = "扫描类型有误";
                        }
                        if (errMess == null) {
                            if (pqyd.getYd_dybz() != null && pqyd.getYd_dybz().intValue() == 0) {
                            } else {
                                errMess = "瓶签未打印";
                            }
                        }
                        if (errMess == null) {
                            /*
                            if (!pqyd.getPq_bedno().equals(pqyd.getYd_bedno()))
                            {
                                errMess = "床号已变更为" + pqyd.getYd_bedno() + "，请重新打印";
                                recordErr = true;
                            }*/
                        }
                    } else {
                        errMess = "非当日或明日药单";
                    }
                } else {
                    errMess = "医嘱状态已停止";
                    recordErr = true;
                }
            }

        } else {
            errMess = "扫描信息有误，请检查";
        }
        if (errMess == null) {
            List<MedicineBean> medicineBeanList = scansDao.queryMedicineList2(pqyd.getPq_pidsj());
            User currUser = getCurrentUser();
            String username = "";
            if (currUser != null) {
                username = currUser.getAccount();
            }
            errMess = scansSerivce.in_out_pack(scansType, medicineBeanList, pqyd, username);
            if (errMess == null) {
                succMess = "";//
            }
        }
        if (pqyd != null && pqyd.getPq_ydpq().equals(barcode)) {
            BottleLabelResult bottleLabelResult = scansSerivce.queryBottleLabelResult2(pqyd.getPq_pidsj());
            boolean fExists = true;
            if (bottleLabelResult == null) {
                fExists = false;
                bottleLabelResult = new BottleLabelResult();
            }
            bottleLabelResult.setSmLX(scansType);
            bottleLabelResult.setSmJG("".equals(succMess) ? 0 : 1);
            bottleLabelResult.setSmRQ(DateUtil.getCurrentDay8Str());
            bottleLabelResult.setSmSBYY(recordErr ? errMess : "");
            bottleLabelResult.setZxbc(pqyd.getPq_zxbc());
            bottleLabelResult.setYdID(pqyd.getPq_pidsj());
            if (!fExists) {
                scansDao.insertBottleLabelRst(bottleLabelResult);
            } else {
                scansDao.updateBottleLabelRst(bottleLabelResult);
            }
        }
        if ("".equals(succMess)) {
            //将rucangNeedCheck 重置成0 【 医嘱变化更新成1后 入仓成功 要重置成0】
            Map<String, Object> updateMap = new HashMap<String, Object>();
            updateMap.put("rucangNeedCheck", 0);
            updateMap.put("yzlx", pqyd.getYd_yzlx());
            updateMap.put("parentNo", pqyd.getYd_parentNo());
            yzMainService.updateCheckYZMain(updateMap);

            log.info("调用检查医嘱状态接口2：" + String.valueOf(new Date().getTime() - excute.getTime()) + "\n");

            return buildSuccessJsonMsg(succMess);
        } else {
            if (errMess == null) {
                errMess = "扫描失败，请联系管理员";
            }
            log.info("调用检查医嘱状态接口3：" + String.valueOf(new Date().getTime() - excute.getTime()) + "\n");

            return buildFailJsonMsg(errMess);
        }

    }

    @RequestMapping(value = "/scansCheck")
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String initCheck(Model model, HttpServletRequest request) {

        List<Batch> batchList = batchDao.queryBatchAllList();
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        List<ScansBatchBean> scansBatchList = scansSerivce.getScansBatchCountInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("batchName", request.getParameter("batchName"));
        map.put("qryRQ", request.getParameter("qryRQ"));
        List<Map<String, Object>> scansBatchList2 = new ArrayList<Map<String, Object>>();

        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }
        if (midFlag) {

            scansBatchList2 = scansSerivce.qryCountByPcname(map);
        } else {

            scansBatchList2 = scansSerivce.qryCountByPcnameWidthOutMid(map);
        }

        if (batchList != null) {
            // model.addAttribute("batchList",
            // new Gson().toJson(batchList));
            model.addAttribute("batchList", batchList);
        }

        if (checkTypeList != null) {
            // model.addAttribute("checkTypeList",
            // new Gson().toJson(checkTypeList));
            model.addAttribute("checkTypeList", checkTypeList);
        }

        if (scansBatchList != null) {
            model.addAttribute("scansBatchList", scansBatchList);
        }

        if (scansBatchList2 != null) {
            model.addAttribute("scansBatchList2", scansBatchList2);
        }

        String checkName = request.getParameter("checkName");
        model.addAttribute("checkName", checkName);

        String checkType = request.getParameter("checkType");
        model.addAttribute("checkType", checkType);

        String batchName = request.getParameter("batchName");
        model.addAttribute("batchName", batchName);

        String batchID = request.getParameter("batchID");
        if (batchID == null && batchList != null) {//bianxw
            for (Batch batch : batchList) {
                if (batch.getName().equals(request.getParameter("batchName"))) {
                    batchID = "" + batch.getId();
                    break;
                }
            }
        }
        model.addAttribute("batchID", batchID);
        model.addAttribute("qryRQ", request.getParameter("qryRQ"));
        model.addAttribute("httpPort", httpPort);
        model.addAttribute("serverIP", serverIP);
        model.addAttribute("usbScanCode", usbScanCode);

        return "pivas/scans/scansCheck";
    }

    /**
     * 病区统计 统计各病区 已完成入仓或出仓 完成的数量，没有完成的数量
     *
     * @param scansSearch
     * @return
     */
    @RequestMapping(value = "/scansDeptStatistics", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String scansDeptStatistics(ScansSearchBean scansSearch) {
        List<ScansBatchBean> scansBatchList = scansSerivce.scansDeptStatistics(scansSearch, messageHolder);
        return buildSuccessJsonMsg(new Gson().toJson(scansBatchList));
    }

    /**
     * 病区统计 统计各病区 已完成入仓或出仓 完成的数量，没有完成的数量
     *
     * @param scansSearch
     * @return
     */
    @RequestMapping(value = "/scansDeptStatistics2", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String scansDeptStatistics2(ScansSearchBean scansSearch) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("batchName", scansSearch.getBatchName());
        map.put("qryRQ", scansSearch.getQryRQ());

        List<Map<String, Object>> scansBatchList = new ArrayList<Map<String, Object>>();

        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }
        if (midFlag) {

            scansBatchList = scansSerivce.qryCountByPcname(map);
        } else {

            scansBatchList = scansSerivce.qryCountByPcnameWidthOutMid(map);
        }

        return buildSuccessJsonMsg(new Gson().toJson(scansBatchList));
    }

    /**
     * 病区统计 统计各病区 已完成入仓或出仓 完成的数量，没有完成的数量
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/qryCountMain", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String qryCountMain(Map<String, Object> map) {
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList) {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }

        List<Map<String, Object>> scansBatchList = new ArrayList<Map<String, Object>>();

        if (midFlag) {

            scansBatchList = scansSerivce.qryCountMain(map);

        } else {
            scansBatchList = scansSerivce.qryCountMainWidthOutMid(map);

        }

        return buildSuccessJsonMsg(new Gson().toJson(scansBatchList));
    }

    /**
     * 查询瓶签列表
     *
     * @param scansSearch
     * @return
     */
    @RequestMapping(value = "/queryBottleLabelList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_592"})
    public String queryBottleLabelList(ScansSearchBean scansSearch) {
        if (messageHolder.getMessage("scans.deptName.all").equals(scansSearch.getDeptName())) {
            scansSearch.setDeptName(null);
        }
        List<BottleLabelBean> bottleLabelList = scansSerivce.queryBottleLabelList(scansSearch);
        return new Gson().toJson(bottleLabelList);
    }

    @Value(value = "#{propertiesConfig['webapp.httpPort']}")
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    public String getServerIP() {
        return serverIP;
    }

    @Value(value = "#{propertiesConfig['server.ip']}")
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public ScansService getScansSerivce() {
        return scansSerivce;
    }

    public void setScansSerivce(ScansService scansSerivce) {
        this.scansSerivce = scansSerivce;
    }

    public boolean isUsbScanCode() {
        return usbScanCode;
    }

    @Value(value = "#{propertiesConfig['usbScanCode']}")
    public void setUsbScanCode(boolean usbScanCode) {
        this.usbScanCode = usbScanCode;
    }

}
