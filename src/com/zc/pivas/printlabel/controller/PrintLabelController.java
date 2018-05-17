package com.zc.pivas.printlabel.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.common.util.DataFormat;
import com.zc.pivas.common.util.SysConstant.trackingRecord;
import com.zc.pivas.drugDamage.service.DrugDamageService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.dao.InpatientAreaDAO;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.service.MedicamentsService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.printlabel.entity.BottleLabel;
import com.zc.pivas.printlabel.entity.PrintLabelBean;
import com.zc.pivas.printlabel.entity.PrintLabelConBean;
import com.zc.pivas.printlabel.repository.BottleLabelDao;
import com.zc.pivas.printlabel.service.PrintLabelService;
import com.zc.pivas.printlabel.service.PrintLogService;
import com.zc.pivas.printlabel.service.impl.PrintLabelServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 瓶签打印控制类
 *
 * @author kunkka
 * @version 1.0
 */
@Controller()
@RequestMapping(value = "/printLabel")
public class PrintLabelController extends SdDownloadController {
    @Resource
    private PrintLabelService printLabelService;

    @Resource
    private BatchService batchService;

    @Resource
    private InpatientAreaDAO inpatientAreaDAO;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    @Resource
    private MedicamentsService medicamentsService;

    @Resource
    private PrescriptionMainService ydMainService;

    @Resource
    private BottleLabelDao bottleLabelDao;

    @Resource
    private DrugDamageService drugDamageService;

    @RequestMapping("/init")
    public String init(Integer type, Model model, HttpServletRequest request) {
        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);

        List<Batch> batchList = batchService.queryByPaging(null, null);
        model.addAttribute("batchList", batchList);

        InpatientAreaBean bean = new InpatientAreaBean();
        bean.setEnabled("1");

        List<InpatientAreaBean> inpatientAreaList = inpatientAreaDAO.getInpatientAreaList(bean, null);
        model.addAttribute("inpatientAreaList", inpatientAreaList);

        List<Integer> medicCountsList;
        try {
            medicCountsList = bottleLabelDao.getMedicamentsCounts();
            model.addAttribute("medicCountsList", medicCountsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MedicCategory> medicCategories = medicCategoryDao.queryAllMedicCategory();
        model.addAttribute("medicCategories", medicCategories);

        List<MedicLabel> allMedicLabel;
        try {
            allMedicLabel = bottleLabelDao.getAllMedicLabel();
            model.addAttribute("allMedicLabel", allMedicLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("allUsers", drugDamageService.getAllUsers());
        Medicaments drugCon = new Medicaments();

        // 主药
        drugCon.setMedicamentsIsmaindrug(0);
        List<Medicaments> mainDrugList = medicamentsService.queryAllByCondition(drugCon);
        model.addAttribute("mainDrugList", mainDrugList);

        //打印条件
        List<PrintLabelConBean> printLableLConfList = bottleLabelDao.queryPrintLabelConfig();
        model.addAttribute("printLableLConfList", printLableLConfList);

        Map<String, String> map = new HashMap<String, String>();
        map.put("category", "pivas.print.medictotalcount");

        List<String> medicTotalCount = printLabelService.printMedicTotSingCount(map);
        model.addAttribute("medicTotalCount", medicTotalCount);

        map.replace("category", "pivas.print.medicsinglecount");
        List<String> medicSingleCount = printLabelService.printMedicTotSingCount(map);
        model.addAttribute("medicSingleCount", medicSingleCount);

        Boolean dateChange = DataFormat.dateChange();
        model.addAttribute("dateChange", dateChange);

        return "pivas/bottleLabel/printLabelDetail";
    }

    /**
     * 生成PDF
     *
     * @throws Exception
     */
    @RequestMapping("/print")
    @ResponseBody
    public String printLabel(PrintLabelBean printLabelBean) throws Exception {
        String returnStr = null;
        String pidsj = printLabelBean.getPidsj();
        List<String> bottleNumList = new ArrayList<String>();
        if (pidsj != null && pidsj.length() > 0) {
            //pidsj = pidsj.substring(0, pidsj.length() - 1);
            String[] strings = pidsj.split(",");
            for (String bottleNum : strings) {
                bottleNumList.add(bottleNum);
            }
            printLabelBean.setBottleNumList(bottleNumList);
        }

        if ("1".equals(printLabelBean.getIsHistoryPrint())) {
            returnStr = printLabelService.printBottleLabel(printLabelBean, getCurrentUser());
        } else {
            returnStr = printLabelService.printBottleLabelTWO(printLabelBean, getCurrentUser());
        }

        if (null == returnStr) {
            if (printLabelBean.getIsPrint()) {
                return buildFailJsonMsg("请选择未打印瓶签");
            } else {
                return getMessage("report.archEnergyConsCtatistics.noData");
            }
        }
        if (bottleNumList.size() > 0) {
            for (String otherPidsj : bottleNumList) {
                ydMainService.addTrackingRecord(otherPidsj,
                        trackingRecord.dypq,
                        trackingRecord.dypqStr,
                        getCurrentUser().getAccount());
            }
        }
        if (printLabelBean.getIsPrint()) {
            return buildSuccessJsonMsg(returnStr);
        } else {
            return returnStr;
        }
    }

    /***
     * 查询主药所有数据
     * @param bean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/qryMainDrugList", produces = "application/json")
    @ResponseBody
    public String qryMainDrugList(BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] columns = new String[]{"medicamentsCode", "medicamentsName"};
        JqueryStylePagingResults<Medicaments> pagingResults = new JqueryStylePagingResults<Medicaments>(columns);
        bottleLabel.setMainDrug("0");
        // 总数
        int total = ydMainService.getMedicamentsUseInfoTotal(bottleLabel);
        List<Medicaments> medicamentsList = ydMainService.getMedicamentsUseInfo(bottleLabel, jquryStylePaging);

        if (total > 0) {
            for (Medicaments medicament : medicamentsList) {
                if (medicament.getMedicamentsDosage().startsWith(".")) {
                    medicament.setMedicamentsDosage("0" + medicament.getMedicamentsDosage());
                }

            }
        }
        pagingResults.setDataRows(medicamentsList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return new Gson().toJson(pagingResults);
    }

    /***
     * 查询辅药所有数据
     * @param bottleLabel 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/qrySupplyDrugList", produces = "application/json")
    @ResponseBody
    public String qrySupplyDrugList(BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] columns = new String[]{"medicamentsCode", "medicamentsName"};
        JqueryStylePagingResults<Medicaments> pagingResults = new JqueryStylePagingResults<Medicaments>(columns);
        bottleLabel.setMainDrug("1");
        // 总数
        int total = ydMainService.getMedicamentsUseInfoTotal(bottleLabel);
        List<Medicaments> medicamentsList = ydMainService.getMedicamentsUseInfo(bottleLabel, jquryStylePaging);

        if (total > 0) {
            for (Medicaments medicament : medicamentsList) {
                if (medicament.getMedicamentsDosage().startsWith(".")) {
                    medicament.setMedicamentsDosage("0" + medicament.getMedicamentsDosage());
                }

            }
        }
        pagingResults.setDataRows(medicamentsList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return new Gson().toJson(pagingResults);
    }

    /***
     * 查询辅药所有数据
     * 角色名称条件查询
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/qryBottList", produces = "application/json")
    @ResponseBody
    public String qryBottList(BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] columns = new String[]{"html"};
        JqueryStylePagingResults<Map<String, String>> pagingResults =
                new JqueryStylePagingResults<Map<String, String>>(columns);
        // 总数
        List<BottleLabel> bottleList = bottleLabelDao.queryBottleLabelList(bottleLabel);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("html", "");
        list.add(map);

        pagingResults.setDataRows(list);

        pagingResults.setTotal(DefineCollectionUtil.isEmpty(bottleList) ? 0 : bottleList.size());
        pagingResults.setPage(jquryStylePaging.getPage());
        return new Gson().toJson(pagingResults);
    }

    @RequestMapping(value = "/qryBottList2", produces = "application/json")
    @ResponseBody
    public List<Map<String, String>> qryBottList2(BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
            throws Exception {
        List<Map<String, String>> list = printLabelService.qryBottleLabel(bottleLabel, jquryStylePaging);

        return list;
    }

    /**
     * 下载
     *
     * @param fileName
     * @param response
     * @throws Exception
     */
    @RequestMapping("/downloadPdf")
    @ResponseBody
    public void downloadPdf(String fileName, HttpServletResponse response)
            throws Exception {
        fileName = URLDecoder.decode(fileName, "utf-8");

        String pdfDir = PrintLabelServiceImpl.getPdfSaveDirPath(getCurrentUser().getAccount());

        super.doDownloadFile(new File(pdfDir + fileName), fileName, FileType.PDF_TYPE, false);
    }

    /******************************************** 西 安  **************************************************************/
    /**
     * 获取到药单表中药单种类和数量
     *
     * @param bottleLabel
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */

    @Resource
    private PrintLogService printLogService;

    @RequestMapping(value = "/qryAllMdcs", produces = "application/json")
    @ResponseBody
    public List<PrintLabelBean> queryAllMedicaments(PrintLabelBean printLabelBean) throws Exception {

        //药品标签
        String labelNos = printLabelBean.getMedicamentsLabelNo();
        if (labelNos != null && labelNos.trim().length() > 0) {
            printLabelBean.setMedicamentLabelArray(labelNos.split(","));
        }

        //批次
        String batchIds = printLabelBean.getBatchIds();
        if (batchIds != null && batchIds.trim().length() > 0) {
            printLabelBean.setIsPack(null);
            Batch batch = null;
            if ("0".equals(batchIds)) {
                batchIds = "";
                batch = new Batch();
                batch.setIsEmptyBatch(1);
                batch.setEnabled(1);
                batch.setNum("0");
                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        batchIds = batchIds + "," + bean.getId();
                    }

                }
                batchIds = batchIds.substring(1, batchIds.length());
                printLabelBean.setBatchIDList(batchIds.split(","));
            } else if ("1".equals(batchIds)) {
                batchIds = "";
                batch = new Batch();
                batch.setIsEmptyBatch(0);
                batch.setEnabled(1);

                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        batchIds = batchIds + "," + bean.getId();
                    }

                }
                batchIds = batchIds.substring(1, batchIds.length());
                printLabelBean.setBatchIDList(batchIds.split(","));
            } else if ("-1".equals(batchIds)) {
                batchIds = "";
                batch = new Batch();
                batch.setIsEmptyBatch(1);
                batch.setEnabled(1);
                batch.setNum("-1");

                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        batchIds = batchIds + "," + bean.getId();
                    }

                }
                batchIds = batchIds.substring(1, batchIds.length());
                printLabelBean.setBatchIDList(batchIds.split(","));
            } else {
                printLabelBean.setBatchIDList(batchIds.split(","));
            }
        }

        //药品分类
        String categoryId = printLabelBean.getCategoryId();
        if (categoryId != null && categoryId.trim().length() > 0) {
            printLabelBean.setCategoryIdArray(categoryId.split(","));
        }

        //药单药数
        String medicamentsCountsStr = printLabelBean.getMedicamentsCountsStr();
        if (medicamentsCountsStr != null && medicamentsCountsStr.trim().length() > 0) {
            printLabelBean.setMedicamentsCountsArray(medicamentsCountsStr.split(","));
        }

        //病区
        String wardCodeArray = printLabelBean.getWardCode();
        if (wardCodeArray != null && wardCodeArray.trim().length() > 0) {
            printLabelBean.setWardCodeArray(wardCodeArray.split(","));
        }

        //容酶的药品Code
        String medicCodeList = printLabelBean.getMedicamentsCodeList();
        if (medicCodeList != null && medicCodeList.trim().length() > 0) {
            printLabelBean.setMedicamentsCodeListArray(medicCodeList.split(","));
        }


        String bednoStr = printLabelBean.getBednoS();
        if (StringUtils.isNotBlank(bednoStr)) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoArray = bednoStr.split(",");
            printLabelBean.setBednoArray(bednoArray);
        }

        String drugnameStr = printLabelBean.getDrugnameS();
        if (StringUtils.isNotBlank(drugnameStr)) {
            drugnameStr = DefineStringUtil.escapeAllLike(drugnameStr);
            String[] drugnameArray = drugnameStr.split(",");
            printLabelBean.setDrugnameyArray(drugnameArray);
        }

        return bottleLabelDao.queryAllMedicaments(printLabelBean);
    }

    /***
     * 查询所有的打印瓶签数据
     * @param printLabelBean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/qryPrintBottleLableList", produces = "application/json")
    @ResponseBody
    public String queryPrintBottleLableList(PrintLabelBean printLabelBean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] columns =
                new String[]{"wardCode", "wardName", "bedNo", "batchId", "batchName", "parentNo", "patName", "freqCode",
                        "transfusion", "drugName", "useDate", "dose", "doseUnit", "mainHtml", "dybz", "bottleNum",
                        "printTimeStr", "printName"};

        //批次
        String batchIds = printLabelBean.getBatchIds();
        if (batchIds != null && batchIds.trim().length() > 0) {
            printLabelBean.setIsPack(null);
            Batch batch = null;
            if ("0".equals(batchIds)) {
                batchIds = "";
                batch = new Batch();
                batch.setIsEmptyBatch(1);
                batch.setEnabled(1);
                batch.setNum("0");
                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        batchIds = batchIds + "," + bean.getId();
                    }

                }
                batchIds = batchIds.substring(1, batchIds.length());
                printLabelBean.setBatchIDList(batchIds.split(","));
            } else if ("-1".equals(batchIds)) {
                batchIds = "";
                batch = new Batch();
                batch.setIsEmptyBatch(1);
                batch.setEnabled(1);
                batch.setNum("-1");

                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        batchIds = batchIds + "," + bean.getId();
                    }

                }
                batchIds = batchIds.substring(1, batchIds.length());
                printLabelBean.setBatchIDList(batchIds.split(","));
            } else if ("1".equals(batchIds)) {
                batchIds = "";
                batch = new Batch();
                batch.setIsEmptyBatch(0);
                batch.setEnabled(1);

                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        batchIds = batchIds + "," + bean.getId();
                    }

                }
                batchIds = batchIds.substring(1, batchIds.length());
                printLabelBean.setBatchIDList(batchIds.split(","));
            } else {
                printLabelBean.setBatchIDList(batchIds.split(","));
            }
        }

        //药品数量
        String medicamentsCountsStr = printLabelBean.getMedicamentsCountsStr();
        if (medicamentsCountsStr != null && medicamentsCountsStr.trim().length() > 0) {
            printLabelBean.setMedicamentsCountsArray(medicamentsCountsStr.split(","));
        }

        //病区
        String wardCodeArray = printLabelBean.getWardCode();
        if (wardCodeArray != null && wardCodeArray.trim().length() > 0) {
            printLabelBean.setWardCodeArray(wardCodeArray.split(","));
        }

        //药品编码
        String medicamentsCodeList = printLabelBean.getMedicamentsCodeList();
        if (medicamentsCodeList != null && medicamentsCodeList.trim().length() > 0) {
            medicamentsCodeList = medicamentsCodeList.substring(0, medicamentsCodeList.length() - 1);
            printLabelBean.setMedicamentsCodeListArray(medicamentsCodeList.split(","));
        }

        String bednoStr = printLabelBean.getBednoS();
        if (StringUtils.isNotBlank(bednoStr)) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoArray = bednoStr.split(",");
            printLabelBean.setBednoArray(bednoArray);
        }

        String drugnameStr = printLabelBean.getDrugnameS();
        if (StringUtils.isNotBlank(drugnameStr)) {
            drugnameStr = DefineStringUtil.escapeAllLike(drugnameStr);
            String[] drugnameArray = drugnameStr.split(",");
            printLabelBean.setDrugnameyArray(drugnameArray);
        }

        JqueryStylePagingResults<PrintLabelBean> pagingResults = new JqueryStylePagingResults<PrintLabelBean>(columns);
        int total = 0;
        List<PrintLabelBean> labelList = null;
        if (null != printLabelBean.getPrintIndex() && !"".equals(printLabelBean.getPrintIndex())) {
            labelList = printLabelService.selectBottleLabelHistory(printLabelBean, jquryStylePaging);

        } else {
            labelList = printLabelService.selectBottleLabel(printLabelBean, jquryStylePaging);
        }

        total = DefineCollectionUtil.isEmpty(labelList) ? 0 : labelList.size();
        if (total > 0) {
            for (PrintLabelBean label : labelList) {
                if (label.getDose() != null) {
                    if (label.getDose().startsWith(".")) {
                        label.setDose("0" + label.getDose());
                    }
                }
            }
        }
        pagingResults.setDataRows(labelList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return new Gson().toJson(pagingResults);
    }

    /**
     * 获取预览信息
     * <br>
     *
     * @param printLabelBean
     * @throws Exception
     */
    @RequestMapping("/previewPrintLabel")
    @ResponseBody
    public String previewPrintLabel(PrintLabelBean printLabelBean)
            throws Exception {
        String pidsjStr = printLabelBean.getPidsj();
        if (pidsjStr != null && pidsjStr.length() > 0) {
            List<String> bottleNumList = new ArrayList<String>();
            String[] pidsjStrArray = pidsjStr.split(",");
            for (String pidsjstr : pidsjStrArray) {
                bottleNumList.add(pidsjstr);
            }
            printLabelBean.setBottleNumList(bottleNumList);
        }
        List<PrintLabelBean> labelList = null;
        if (null != printLabelBean.getPrintIndex() && !"".equals(printLabelBean.getPrintIndex())) {
            labelList = printLabelService.selectBottleLabelHistory(printLabelBean, null);
        } else {
            labelList = printLabelService.selectBottleLabel(printLabelBean, null);
        }
        if (!DefineCollectionUtil.isEmpty(labelList)) {
            for (PrintLabelBean label : labelList) {
                label.setMainHtml(label.getMainHtml().replaceAll("SYSDATE", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss")));
            }
        }
        return new Gson().toJson(labelList);
    }

    /**
     * 打印汇总单
     * <br>
     *
     * @param printLabelBean
     * @throws Exception
     */
    @RequestMapping("/printStatistics")
    @ResponseBody
    public String printStatistics(PrintLabelBean printLabelBean) throws Exception {
        String pidsj = printLabelBean.getPidsj();
        List<String> bottleNumList = new ArrayList<String>();
        if (pidsj != null && pidsj.length() > 0) {
            pidsj = pidsj.substring(0, pidsj.length() - 1);
            String[] strings = pidsj.split(",");
            for (String bottleNum : strings) {
                bottleNumList.add(bottleNum);
            }
            printLabelBean.setBottleNumList(bottleNumList);
        }

        String returnStr = null;
        returnStr = printLabelService.printStatisicInfo(printLabelBean, getCurrentUser());

        if (null == returnStr) {
            if (printLabelBean.getIsPrint()) {
                return buildFailJsonMsg("report.archEnergyConsCtatistics.noData");
            } else {
                return getMessage("report.archEnergyConsCtatistics.noData");
            }
        }

        if (printLabelBean.getIsPrint()) {
            return buildSuccessJsonMsg(returnStr);
        } else {
            return returnStr;
        }
    }

    /***
     *
     * 改变打印状态
     * @param printLabelBean
     * @return
     * @throws Exception
     */
    @RequestMapping("/changePrintMark")
    @ResponseBody
    public String changePrintMark(HttpServletRequest request, PrintLabelBean printLabelBean) throws Exception {
        String returnStr = null;

        String pidsj = printLabelBean.getPidsj();
        List<String> bottleNumList = new ArrayList<String>();
        if (pidsj != null && pidsj.length() > 0) {
            pidsj = pidsj.substring(0, pidsj.length() - 1);
            String[] strings = pidsj.split(",");
            for (String bottleNum : strings) {
                bottleNumList.add(bottleNum);
            }
            printLabelBean.setBottleNumList(bottleNumList);
        }
        String ipAddress = getIpAddress(request);
        returnStr = printLabelService.changePrintMark(printLabelBean, getCurrentUser(), ipAddress);

        if (bottleNumList.size() > 0) {
            for (String otherPidsj : bottleNumList) {
                ydMainService.addTrackingRecord(otherPidsj,
                        trackingRecord.dypq,
                        trackingRecord.dypqStr,
                        getCurrentUser().getAccount());
            }
        }
        //error_1：瓶签状态更新失败  
        //error_2：未能成功打印瓶签
        return "success".equals(returnStr) ? buildSuccessJsonMsg(returnStr)
                : ("error_1".equals(returnStr) ? buildFailJsonMsg("瓶签状态更新失败") : buildFailJsonMsg("未能成功打印瓶签"));
    }

    /***
     *
     * 获取客户端IP
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据条件获取到要查询的药单统计的页面
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/callMedicStatisticHtml")
    @ResponseBody
    public String callMedicStatisticHtml(@RequestParam Map<String, Object> param) throws Exception {
        String returnStr = null;
        Map<String, Object> paramContro = new HashMap<String, Object>();

        String type = StrUtil.getObjStr(param.get("type"));
        if (Integer.parseInt(type) == 0) {
            //药单的pidsj
            String pidsjStr = StrUtil.getObjStr(param.get("pidsj"));
            if (StringUtils.isNotBlank(pidsjStr)) {
                String[] pidsjArr = pidsjStr.split(",");
                paramContro.put("pidsjArr", pidsjArr);
                returnStr = printLabelService.callMedicStatisticHtml(paramContro, getCurrentUser());
            }
        } else {

            //用药日期
            String yyrq = StrUtil.getObjStr(param.get("yyrq"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date yyrqDate = format.parse(yyrq);
            paramContro.put("yyrq", yyrqDate);

            //药品标签
            String medLabelNos = StrUtil.getObjStr(param.get("medLabelNos"));
            if (StringUtils.isNotBlank(medLabelNos)) {
                String[] medLabelNoArr = medLabelNos.split(",");
                paramContro.put("medLabelNoArr", medLabelNoArr);
            }

            //是否打包   批次
            String isPackStr = StrUtil.getObjStr(param.get("isPack"));
            if (StringUtils.isNotBlank(isPackStr) && Integer.parseInt(isPackStr) == 1) {
                paramContro.put("batchIdSingle", 0);
            } else {
                String batchIdsStr = StrUtil.getObjStr(param.get("batchIds"));
                if (StringUtils.isNotBlank(batchIdsStr)) {
                    if ("0".equals(batchIdsStr)) {
                        paramContro.put("batchIdSingle", 0);
                    } else if ("1".equals(batchIdsStr)) {
                        paramContro.put("batchIdSingle", 1);
                    } else {
                        paramContro.put("batchIdArr", batchIdsStr.split(","));
                    }
                }
            }

            //药品分类
            String categoryIds = StrUtil.getObjStr(param.get("categoryIds"));
            if (StringUtils.isNotBlank(categoryIds)) {
                String[] categoryIdArr = categoryIds.split(",");
                paramContro.put("categoryIdArr", categoryIdArr);
            }

            //是否打印 dybz
            String dybzStr = StrUtil.getObjStr(param.get("dybz"));
            if (StringUtils.isNotBlank(dybzStr)) {
                int dybz = Integer.parseInt(dybzStr);
                paramContro.put("dybz", dybz);
            }

            //溶媒
            String menstruums = StrUtil.getObjStr(param.get("menstruums"));
            if (StringUtils.isNotBlank(menstruums)) {
                String[] menstruumArr = menstruums.split(",");
                paramContro.put("menstruumArr", menstruumArr);
            }

            //病区
            String wardCode = StrUtil.getObjStr(param.get("wardCode"));
            if (StringUtils.isNotBlank(wardCode)) {
                String[] wardCodeArr = wardCode.split(",");
                paramContro.put("wardCodeArr", wardCodeArr);
            }

            //打印开始时间
            String printStartTime = StrUtil.getObjStr(param.get("printStartTime"));
            if (StringUtils.isNotBlank(printStartTime)) {
                paramContro.put("printStartTime", printStartTime);
            }

            //打印结束时间
            String printEndTime = StrUtil.getObjStr(param.get("printEndTime"));
            if (StringUtils.isNotBlank(printEndTime)) {
                paramContro.put("printEndTime", printEndTime);
            }

            //查询的名称
            String selectName = StrUtil.getObjStr(param.get("selectName"));
            if (StringUtils.isNotBlank(selectName)) {
                paramContro.put("selectName", selectName);
            }

            returnStr = printLabelService.callMedicStatisticHtml(paramContro, getCurrentUser());
        }
        if (null == returnStr) {
            return getMessage("report.archEnergyConsCtatistics.noData");

        } else {
            return returnStr;
        }

    }

}
