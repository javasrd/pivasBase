package com.zc.pivas.statistics.service.impl;

import com.itextpdf.text.pdf.BaseFont;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.util.FileUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.statistics.bean.allocationWork.AllocationSQLBean;
import com.zc.pivas.statistics.bean.allocationWork.AllocationWorkBean;
import com.zc.pivas.statistics.repository.AllocationWorkDAO;
import com.zc.pivas.statistics.service.AllocationWorkService;
import com.zc.pivas.printlabel.entity.DelFileTaskBean;
import com.zc.pivas.printlabel.repository.BottleLabelDao;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置工作量统计Service接口实现
 *
 * @author jagger
 * @version 1.0
 */
@Service("allocationWorkService")
public class AllocationWorkServiceImpl implements AllocationWorkService {

    private final static String BASEPATH = SdConstant.WEB_ROOT_PATH + File.separator + "WEB-INF" + File.separator + "print" + File.separator;

    private final static String EXCELPATH = SdConstant.WEB_ROOT_PATH + File.separator + "WEB-INF" + File.separator + "formatFile" + File.separator;

    private final static String FILEPATH = BASEPATH + "temp" + File.separator;

    private final static String EXPORTFILENAME = "配置工作量统计.xls";

    @Resource
    private AllocationWorkDAO allocationWorkDAO;

    @Resource
    private BottleLabelDao bottleLabelDao;

    @Override
    public List<Batch> getBatchList() throws Exception {
        return allocationWorkDAO.getBatchList();
    }

    /**
     * 获取文件的路径
     *
     * @param param
     * @param currUser
     * @return
     * @throws Exception
     */
    @Override
    public String getWorkListPdfPath(Map<String, Object> param, User currUser) throws Exception {
        String htmlAndPdfPath = "";
        List<AllocationSQLBean> workList = allocationWorkDAO.getWorkList(param);

        if (workList != null && workList.size() == 0) {
            htmlAndPdfPath = "error_1";
            return htmlAndPdfPath;
        }

        List<AllocationWorkBean> finalWorkList = rebuildAllocationList(workList);

        String pdfSaveDir = FILEPATH + currUser.getAccount() + File.separator;
        FileUtil.mkdirs(pdfSaveDir);
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(BASEPATH + "fonts" + File.separator + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.getSharedContext().setBaseURL(new File(pdfSaveDir).toURI().toString());

        String pdfName = "";
        try {
            VelocityContext context = new VelocityContext();
            context.put("workList", finalWorkList);
            String timeFrame = "";

            String startTime = StrUtil.getObjStr(param.get("startTime"));
            String endTime = StrUtil.getObjStr(param.get("endTime"));
            String startMonth = StrUtil.getObjStr(param.get("startMonth"));

            if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                if (startTime.equals(endTime)) {
                    timeFrame = startTime;
                } else {
                    timeFrame = startTime + "-" + endTime;
                }
            } else if (StringUtils.isNotBlank(startMonth)) {
                timeFrame = startMonth;
            }
            context.put("timeFrame", timeFrame);

            String htmlName = "allocation_" + System.currentTimeMillis();
            File file = fillTemplate("allocationWork.html", context, pdfSaveDir + htmlName + ".html");
            pdfName = htmlName + ".pdf";
            String downloadPath =
                    SdConstant.WEB_ROOT_PATH + File.separator + "alloDownLoad" + File.separator + currUser.getAccount()
                            + File.separator;
            FileUtil.mkdirs(downloadPath);
            String pdfPath = downloadPath + pdfName;
            FileUtil.deleteFile(pdfPath);

            renderer.setDocument(file);
            renderer.layout();
            renderer.createPDF(new FileOutputStream(pdfPath), false);
            htmlAndPdfPath = com.zc.pivas.common.util.FileUtil.readFile(file);
            htmlAndPdfPath += "@@@@" + pdfName;
        } finally {
            if (renderer != null) {
                renderer.finishPDF();
            }
        }

        return htmlAndPdfPath;
    }

    /**
     * 重构查询出的数据
     *
     * @param workList
     * @return
     */
    private List<AllocationWorkBean> rebuildAllocationList(List<AllocationSQLBean> workList) {
        Map<String, AllocationWorkBean> workListMap = new HashMap<String, AllocationWorkBean>();

        //对数据进行归类
        for (AllocationSQLBean sqlBean : workList) {

            if (workListMap.get(sqlBean.getWorkName()) == null) {
                AllocationWorkBean workBean = new AllocationWorkBean();
                workBean.setWorkName(sqlBean.getWorkName());
                fillAlloWorkBean(sqlBean, workBean);
                workListMap.put(sqlBean.getWorkName(), workBean);
            } else {
                AllocationWorkBean allocationWorkBean = workListMap.get(sqlBean.getWorkName());
                fillAlloWorkBean(sqlBean, allocationWorkBean);
            }
        }

        //统计总量
        for (Map.Entry<String, AllocationWorkBean> entry : workListMap.entrySet()) {

            AllocationWorkBean workBean = entry.getValue();
            int totalCount = 0;

            if (workBean.getAntibioticCount() != null) {
                totalCount += workBean.getAntibioticCount().intValue();
            }
            if (workBean.getChemotherapyCount() != null) {
                totalCount += workBean.getChemotherapyCount().intValue();
            }
            if (workBean.getNutritionCount() != null) {
                totalCount += workBean.getNutritionCount().intValue();
            }
            if (workBean.getGeneralCount() != null) {
                totalCount += workBean.getGeneralCount().intValue();
            }
            workBean.setTotalCount(totalCount);

        }
        List<AllocationWorkBean> finalWorkList = new ArrayList<AllocationWorkBean>();

        NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(2);

        for (AllocationWorkBean bean : workListMap.values()) {
            Double workload = bean.getWorkload();
            if (workload != null) {

                String workloadStr = "";

                String numberStr = workload.toString();

                int a = numberStr.indexOf(".");

                int indexNum = numberStr.length();
                indexNum = indexNum >= a + 5 ? a + 5 : indexNum;
                String numAfter = numberStr.substring(a + 1, indexNum);
                String numBefore = numberStr.substring(0, a);

                if (a != -1) {
                    if (Integer.parseInt(numAfter) == 0) {
                        workloadStr = numBefore;
                    } else {
                        workloadStr = numFormat.format(workload.doubleValue());
                    }
                }
                bean.setWorkloadStr(workloadStr);
            } else {
                bean.setWorkloadStr("0");
            }

            finalWorkList.add(bean);

        }
        return finalWorkList;
    }

    /**
     * 填充html
     *
     * @param templateName
     * @param context
     * @param savePath
     * @return
     * @throws Exception
     */
    private File fillTemplate(String templateName, VelocityContext context, String savePath)
            throws Exception {
        String outFile = savePath;
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
        try {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, BASEPATH + "velocity" + File.separator);
            ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");
            ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
            ve.init();

            Template t = ve.getTemplate(templateName);

            String deletePath = savePath.substring(0, savePath.lastIndexOf("\\"));

            DelFileTaskBean delFileTask = new DelFileTaskBean();
            delFileTask.setFilePath(deletePath);
            delFileTask.setDay(3);
            bottleLabelDao.addDelFileTask(delFileTask);

            t.merge(context, writer);
            writer.flush();
        } finally {
            writer.close();
        }

        return new File(outFile);
    }

    /**
     * 数据清洗
     *
     * @param sqlBean
     * @param allocationWorkBean
     */
    private void fillAlloWorkBean(AllocationSQLBean sqlBean, AllocationWorkBean allocationWorkBean) {
        String medicCateName = sqlBean.getMedicCateName();
        if ("antibiotic".equals(medicCateName)) {
            allocationWorkBean.setAntibioticCount(sqlBean.getMedicCount());
        } else if ("chemotherapy".equals(medicCateName)) {
            allocationWorkBean.setChemotherapyCount(sqlBean.getMedicCount());
        } else if ("nutrition".equals(medicCateName)) {
            allocationWorkBean.setNutritionCount(sqlBean.getMedicCount());
        } else {
            allocationWorkBean.setGeneralCount(sqlBean.getMedicCount());
        }
        Double workload1 = allocationWorkBean.getWorkload();
        Double workload2 = sqlBean.getWorkload();
        if (workload1 == null) {
            workload1 = workload2;
        } else {
            workload1 = workload1.doubleValue() + workload2.doubleValue();
        }
        allocationWorkBean.setWorkload(workload1);
    }

    /**
     * 到处excel
     *
     * @param param
     * @param user
     * @return
     * @throws Exception
     */
    public String toExportExcel(Map<String, Object> param, User user) throws Exception {
        String excelPath = "";
        List<AllocationSQLBean> workList = allocationWorkDAO.getWorkList(param);

        if (workList != null && workList.size() == 0) {
            excelPath = "error_1";
            return excelPath;
        }

        List<AllocationWorkBean> finalWorkList = rebuildAllocationList(workList);
        // 导出文件名
        String fileName = EXPORTFILENAME;
        String templateFilePath = EXCELPATH + fileName;

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templateFilePath));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        sheet.setAutobreaks(true);
        sheet.protectSheet("");
        setRowValue(finalWorkList, sheet, param, wb);

        String exportDirPath =
                SdConstant.WEB_ROOT_PATH + File.separator + "allocationExport" + File.separator + user.getAccount()
                        + File.separator;
        FileUtil.mkdirs(exportDirPath);
        String exportFilePath = exportDirPath + fileName;
        excelPath = fileName;
        FileOutputStream fileout = null;
        try {
            fileout = new FileOutputStream(exportFilePath);
            wb.write(fileout);
        } catch (Exception e) {
            excelPath = "error_2";
            e.printStackTrace();
        } finally {
            if (fileout != null) {
                fileout.flush();
                fileout.close();
            }
        }
        return excelPath;
    }

    private void setRowValue(List<AllocationWorkBean> workList, HSSFSheet sheet, Map<String, Object> param, HSSFWorkbook wb) {
        String timeFrame = "";

        String startTime = StrUtil.getObjStr(param.get("startTime"));
        String endTime = StrUtil.getObjStr(param.get("endTime"));
        String startMonth = StrUtil.getObjStr(param.get("startMonth"));

        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            if (startTime.equals(endTime)) {
                timeFrame = startTime;
            } else {
                timeFrame = startTime + " —— " + endTime;
            }
        } else if (StringUtils.isNotBlank(startMonth)) {
            timeFrame = startMonth;
        }

        HSSFRow timeRow = sheet.getRow(1);
        HSSFCell timeCell = timeRow.getCell(0);
        timeCell.setCellValue("时间范围（" + timeFrame + ")");

        HSSFCellStyle style = wb.createCellStyle();

        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        AllocationWorkBean workBean = null;
        HSSFRow row = null;
        for (int i = 0; i < workList.size(); i++) {
            workBean = workList.get(i);

            row = sheet.createRow(i + 3);
            row.setHeightInPoints((short) 20);
            HSSFCell cell = null;

            // 员工   
            row.createCell(0);
            cell = row.getCell(0);
            cell.setCellValue(workBean.getWorkName());
            cell.setCellStyle(style);

            // 总数
            row.createCell(1);
            cell = row.getCell(1);
            cell.setCellValue(workBean.getTotalCount());
            cell.setCellStyle(style);

            // 普通药
            row.createCell(2);
            cell = row.getCell(2);
            cell.setCellValue(workBean.getGeneralCount() == null ? 0 : workBean.getGeneralCount().intValue());
            cell.setCellStyle(style);

            // 抗生素
            row.createCell(3);
            cell = row.getCell(3);
            cell.setCellValue(workBean.getAntibioticCount() == null ? 0 : workBean.getAntibioticCount().intValue());
            cell.setCellStyle(style);

            // 化疗药
            row.createCell(4);
            cell = row.getCell(4);
            cell.setCellValue(workBean.getChemotherapyCount() == null ? 0 : workBean.getChemotherapyCount().intValue());
            cell.setCellStyle(style);

            //  营养药 
            row.createCell(5);
            cell = row.getCell(5);
            cell.setCellValue(workBean.getNutritionCount() == null ? 0 : workBean.getNutritionCount().intValue());
            cell.setCellStyle(style);

            // 工作量系数汇总
            row.createCell(6);
            cell = row.getCell(6);
            cell.setCellValue(workBean.getWorkload() == null ? 0 : workBean.getWorkload().doubleValue());
            cell.setCellStyle(style);
        }

    }

}
