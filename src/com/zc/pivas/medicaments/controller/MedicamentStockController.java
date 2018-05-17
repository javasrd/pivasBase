package com.zc.pivas.medicaments.controller;

import com.google.gson.Gson;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.FileUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.medicaments.entity.DrugOpenAmount;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.entity.MedicamentsLog;
import com.zc.pivas.medicaments.service.DrugOpenAmountService;
import com.zc.pivas.medicaments.service.MedicamentsLogService;
import com.zc.pivas.medicaments.service.MedicamentsService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 药品Controller
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/mdictStk")
public class MedicamentStockController extends SdBaseController
{
    private static Logger log = LoggerFactory.getLogger(MedicamentStockController.class);
    
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory exceptionFactory;
    
    /**
     * 药品Service
     */
    @Resource
    private MedicamentsService medicamentsService;
    
    /**
     * 药品修改日志
     */
    @Resource
    private MedicamentsLogService medicamentsLogService;
    
    /**
     * 拆药量
     */
    @Resource
    private DrugOpenAmountService drugOpenAmountService;
    
    /**
     * 初始化药品查询页面 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/initStock")
    @RequiresPermissions(value = {"PIVAS_MENU_632"})
    public String initMedicamentStock(Model model)
    {
        return "pivas/medicaments/medicamentStockList";
    }
    
    /**
     * 库存管理分页查询
     *
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mdictStkList", produces = "application/json")
    @ResponseBody
    public String queryMedicamentStockList(JqueryStylePaging paging, Medicaments condition)
        throws Exception
    {
        
        String[] categoryNames = condition.getCategoryNames();
        if (categoryNames != null)
        {
            for (int i = 0; i < categoryNames.length; ++i)
            {
                categoryNames[i] = DefineStringUtil.escapeAllLike(categoryNames[i]);
            }
        }
        
        condition.setMedicamentsName(DefineStringUtil.escapeAllLike(condition.getMedicamentsName()));
        //condition.setMedicamentsCode(DefineStringUtil.escapeAllLike(condition.getMedicamentsCode()));
        condition.setMedicamentsPlace(DefineStringUtil.escapeAllLike(condition.getMedicamentsPlace()));
        condition.setMedicamentsSuchama(DefineStringUtil.escapeAllLike(condition.getMedicamentsSuchama()));
        
        String[] columns = new String[] {"medicamentsId"};
        
        JqueryStylePagingResults<Medicaments> pagingResults = new JqueryStylePagingResults<Medicaments>(columns);
        
        List<Medicaments> medicamentsList = medicamentsService.queryByPaging(paging, condition);
        
        // 总数
        Integer total = medicamentsService.getTotalCount(condition);
        if (DefineCollectionUtil.isNotEmpty(medicamentsList))
        {
            Medicaments medicament = null;
            for (Medicaments bean : medicamentsList)
            {
                condition.setMedicamentsCode(bean.getMedicamentsCode());
                
                medicament = medicamentsService.getConsumption(condition);
                
                if (null != medicament)
                {
                    bean.setConsumption(medicament.getConsumption());
                    bean.setLastWeekUsed(medicament.getLastWeekUsed());
                    bean.setLastDayUsed(medicament.getLastDayUsed());
                    bean.setUsed(medicament.getUsed());
                }
            }
        }
        pagingResults.setDataRows(medicamentsList);
        
        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());
        
        return new Gson().toJson(pagingResults);
        
    }
    
    /**
     * 更新药品
     *
     * @param medicamentse
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updMdctse", produces = "application/json")
    @ResponseBody
    public String updateMedicamentse(Medicaments medicamentse)
        throws Exception
    {
        Medicaments condition = new Medicaments();
        condition.setMedicamentsId(medicamentse.getMedicamentsId());
        List<Medicaments> medicaments = medicamentsService.queryByPaging(new JqueryStylePaging(), condition);
        
        try
        {
            if (DefineCollectionUtil.isEmpty(medicaments))
            {
                throw exceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
            }
            Medicaments medicamentseInfo = medicaments.get(0);
            String stock_last = medicamentseInfo.getMedicamentsStock();
            medicamentseInfo.setMedicamentsStock(medicamentse.getMedicamentsStock());
            medicamentseInfo.setMedicamentsLimit(medicamentse.getMedicamentsLimit());
            medicamentseInfo.setOperator(getCurrentUser().getAccount());
            medicamentse.setOperator(getCurrentUser().getAccount());
            medicamentsService.updateByPrimaryKeySelective(medicamentse);
            addOperatorLog(medicamentse.getMedicamentsStock(), stock_last, medicamentseInfo);
            
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUGS,
                AmiLogConstant.BRANCH_SYSTEM.CF,
                getMessage("log.medicaments.tip.updateMedicaments",
                    new String[] {String.valueOf(medicamentse.getMedicamentsId())}),
                true);
            
            return buildSuccessJsonMsg("common.op.success");
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUGS,
                AmiLogConstant.BRANCH_SYSTEM.CF,
                getMessage("log.medicaments.tip.updateMedicaments", new String[] {medicamentse.getMedicamentsName()}),
                false);
            throw e;
        }
    }
    
    /**
     * 自动检查药品库存
     *
     * @return
     */
    @RequestMapping(value = "/chkMdctStk")
    @ResponseBody
    public String checkMedicamentStock()
    {
        medicamentsService.checkMedicamentStock();
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }
    
    /**
     * 导出选中的药品信息（当没有选中任何时，导出所有的药品信息）
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/iptMdctStk")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_634"})
    public String importMedicaments(MultipartFile file)
        throws IOException
    {
        // 药品库存导入错误信息
        StringBuffer errorLog = new StringBuffer();
        errorLog.append("导入异常日志如下：\n");
        
        List<Medicaments> medicamnetErrorList = new ArrayList<Medicaments>();
        
        String fileName = file.getOriginalFilename();
        
        boolean hasPower = false;
        try
        {
            boolean oldVersion = true;
            if (fileName.matches("^.+\\.(?i)(xls)$"))
            {
                oldVersion = true;
            }
            else if (fileName.matches("^.+\\.(?i)(xlsx)$"))
            {
                oldVersion = false;
            }
            else
            {
                addOperLog(AmiLogConstant.MODULE_BRANCH.OC.STOCK_LIST,
                    AmiLogConstant.BRANCH_SYSTEM.OC,
                    getMessage("log.medicamnets.import.filetype", new String[] {fileName}),
                    false);
                return buildSuccessJsonMsg(errorLog.toString());
            }
            
            Workbook workbook = null;
            if (oldVersion)
            {
                // 2003版本Excel(.xls)
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            else
            {
                // 2007版本Excel或更高版本(.xlsx)
                workbook = new XSSFWorkbook(file.getInputStream());
            }
            
            if (null != workbook)
            {
                Sheet sheet = workbook.getSheetAt(0);
                
                Row row = sheet.getRow(5);
                
                // 根据导入列表数据判断获取的记录异常数据的模板
                if (row.getCell(11) != null && "药品消耗量".equals(row.getCell(11).toString()))
                {
                    hasPower = true;
                }
                
                // 药品编码
                String drugCode = "";
                
                // 药品库存
                String drugStock = "";
                String drugStockLast = "";
                String drugStockLimit = "";
                Medicaments medicament = null;
                
                String operatorLog = "";
                for (int i = 6; i <= sheet.getLastRowNum(); i++)
                {
                    row = sheet.getRow(i);
                    
                    if (!StringUtils.isEmpty(row.getCell(0).toString()))
                    {
                        // 药品编码
                        drugCode = row.getCell(2).toString();
                        
                        if (drugCode.contains("."))
                        {
                            drugCode = drugCode.substring(0, drugCode.lastIndexOf('.'));
                        }
                        
                        // 药品库存
                        drugStock = row.getCell(8).toString();
                        
                        drugStockLimit = row.getCell(9).toString();
                        
                        // 校验库存字段是否有效
                        if (!checkIsNum(drugStock))
                        {
                            operatorLog =
                                "第" + String.valueOf(i + 1) + "行" + " 药品编码：" + drugCode + " 药品名称："
                                    + row.getCell(1).getStringCellValue() + " 库存值：" + drugStock + "异常";
                            errorLog.append(operatorLog).append("\n");
                            
                            medicament = setMedicament(row, hasPower);
                            
                            medicament.setOperatorLog(operatorLog);
                            
                            // 记录错误列表中
                            medicamnetErrorList.add(medicament);
                        }
                        else if (!checkIsNum(drugStockLimit))
                        {
                            operatorLog =
                                "第" + String.valueOf(i + 1) + "行" + " 药品编码：" + drugCode + " 药品名称："
                                    + row.getCell(1).getStringCellValue() + " 最低量值：" + drugStockLimit + "异常";
                            errorLog.append(operatorLog).append("\n");
                            
                            medicament = setMedicament(row, hasPower);
                            
                            medicament.setOperatorLog(operatorLog);
                            
                            // 记录错误列表中
                            medicamnetErrorList.add(medicament);
                        }
                        else
                        {
                            medicament = medicamentsService.getMediclByCode(drugCode);
                            
                            if (medicament != null)
                            {
                                drugStockLast = medicament.getMedicamentsStock();
                                medicament.setOperator(getCurrentUser().getAccount());
                                medicament.setMedicamentsStock(drugStock);
                                medicament.setMedicamentsLimit(drugStockLimit);
                                medicament.setConsumption(null);
                                medicament.setMedicamentsOver(null);
                                
                                // 修改药品表数据
                                medicamentsService.updateByPrimaryKeySelective(medicament);
                                
                                // 记录操作日志
                                addOperatorLog(drugStock, drugStockLast, medicament);
                            }
                            else
                            {
                                medicament = setMedicament(row, hasPower);
                                operatorLog =
                                    "第" + String.valueOf(i + 1) + "行" + " 药品编码：" + drugCode + " 药品名称："
                                        + row.getCell(1).getStringCellValue() + " 药品不存在";
                                errorLog.append(operatorLog).append("\n");
                                medicament.setOperatorLog(operatorLog);
                                
                                // 记录错误列表中
                                medicamnetErrorList.add(medicament);
                            }
                        }
                    }
                    
                }
                
                // 导出错误表单
                if (medicamnetErrorList.size() > 0)
                {
                    exportExcel(medicamnetErrorList, true, hasPower);
                    return buildFailJsonMsg(errorLog.toString());
                }
            }
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            addOperLog(AmiLogConstant.MODULE_BRANCH.OC.STOCK_LIST,
                AmiLogConstant.BRANCH_SYSTEM.OC,
                getMessage("log.medicaments.import", new String[] {fileName}),
                false);
            throw e;
        }
        
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }
    
    private void addOperatorLog(String drugStock, String drugStockLast, Medicaments medicament)
    {
        MedicamentsLog medicamentLog = new MedicamentsLog();
        medicamentLog.setCategoryId(medicament.getCategoryId());
        medicamentLog.setCategoryName(medicament.getCategoryName());
        medicamentLog.setCheckCode(medicament.getMedicamentsSuchama());
        medicamentLog.setDrugCode(medicament.getMedicamentsCode());
        medicamentLog.setDrugName(medicament.getMedicamentsName());
        medicamentLog.setDrugPlace(medicament.getMedicamentsPlace());
        medicamentLog.setDrugPlaceCode(medicament.getMedicamentsPlaceCode());
        medicamentLog.setOperator(getCurrentUser().getAccount());
        medicamentLog.setStock_last(drugStockLast);
        medicamentLog.setStock_now(drugStock);
        medicamentsLogService.insert(medicamentLog);
    }

    /**
     * 校验是否为数字
     *
     * @param param
     * @return
     */
    private boolean checkIsNum(String param)
    {
        try
        {
            Float.valueOf(param);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * 导出选中的药品信息（当没有选中任何时，导出所有的药品信息）
     * 
     * @param condition
     * @return
     */
    @RequestMapping(value = "/expMdcts")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_633"})
    public String exportMedicaments(Medicaments condition)
    {
        String medicamentIDs = condition.getMedicamentIDs();
        // 判断是否有获取药品消耗量的权限
        boolean hasPower = false;
        
        // 1:有查看药品消耗量的权限
        if ("1".equals(medicamentIDs.substring(medicamentIDs.length() - 1)))
        {
            hasPower = true;
        }
        
        List<Medicaments> medicamentList = null;
        
        List<String> medicamentIDList = new ArrayList<String>();
        
        medicamentIDs = medicamentIDs.split("_")[0];
        if (null != medicamentIDs && !"".equals(medicamentIDs))
        {
            for (String id : medicamentIDs.split(","))
            {
                medicamentIDList.add(id);
            }
            condition.setMedicamentIDList(medicamentIDList);
        }
        
        // 如果药品id为空，则查询所有药品信息
        medicamentList = medicamentsService.queryByPaging(new JqueryStylePaging(), condition);
        
        String saveDir = "";
        // 如果查询到的药品信息为空，则导出查询到的数据
        if (null != medicamentList)
        {
            try
            {
                saveDir = exportExcel(medicamentList, false, hasPower);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return buildSuccessJsonMsg(saveDir);
    }
    
    /**
     * 导出 Excel
     * 
     * @param medicamentList
     * @param isError
     * @param hasPower
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String exportExcel(List<Medicaments> medicamentList, boolean isError, boolean hasPower)
        throws FileNotFoundException, IOException
    {
        // 导出文件名
        String fileName = "Drug_Inventory.xls";
        String exportFilePath = "";
        if (hasPower)
        {
            // 获取导出数据模板路径
            exportFilePath =
                MedicamentStockController.class.getResource("/").getPath().replaceAll("/classes", "")
                    + "formatFile/药品库存信息A.xls";
            
            if (isError)
            {
                fileName = "Drug_Inventory_Error.xls";
                
                exportFilePath =
                    MedicamentStockController.class.getResource("/").getPath().replaceAll("/classes", "")
                        + "formatFile/药品库存信息A_Error.xls";
            }
        }
        else
        {
            // 获取导出数据模板路径
            exportFilePath =
                MedicamentStockController.class.getResource("/").getPath().replaceAll("/classes", "")
                    + "formatFile/药品库存信息B.xls";
            
            if (isError)
            {
                fileName = "Drug_Inventory_Error.xls";
                
                exportFilePath =
                    MedicamentStockController.class.getResource("/").getPath().replaceAll("/classes", "")
                        + "formatFile/药品库存信息B_Error.xls";
            }
        }
        
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(exportFilePath));
        
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        
        HSSFSheet sheet = wb.getSheetAt(0);
        sheet.setAutobreaks(true);
        sheet.protectSheet("");
        setRowValue(medicamentList, sheet, isError, hasPower);
        
        // 创建导出目录
        String saveDir = getSaveDirPath() + File.separator + fileName;
        // 保存导出文件  
        FileOutputStream fileout = null;
        try
        {
            fileout = new FileOutputStream(saveDir);
            wb.write(fileout);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (fileout != null)
            {
                fileout.flush();
                fileout.close();
            }
        }
        return fileName;
    }
    
    private Medicaments setMedicament(Row row, boolean hasPower)
    {
        Medicaments medicament = new Medicaments();
        
        // 药品分类名称
        medicament.setCategoryName(row.getCell(0).toString());
        
        // 药品名称
        medicament.setMedicamentsName(row.getCell(1).toString());
        
        // 药品编码
        medicament.setMedicamentsCode(row.getCell(2).toString());
        
        // 规格
        medicament.setMedicamentsSpecifications(row.getCell(3).toString());
        
        // 剂量
        medicament.setMedicamentsDosage(row.getCell(4).toString());
        
        // 速查码
        medicament.setMedicamentsSuchama(row.getCell(5).toString());
        
        // 包装单位
        medicament.setMedicamentsPackingUnit(row.getCell(6).toString());
        
        // 药品产地
        if (row.getCell(7) != null)
        {
            
            medicament.setMedicamentsPlace(row.getCell(7).toString());
        }
        else
        {
            medicament.setMedicamentsPlace(null);
        }
        
        // 药品库存
        medicament.setMedicamentsStock(row.getCell(8).toString());
        
        // 药品库存最低量
        medicament.setMedicamentsLimit(row.getCell(9).toString());
        
        // 库存修改人
        if (row.getCell(10) != null)
        {
            medicament.setOperator(row.getCell(10).toString());
        }
        else
        {
            medicament.setOperator(getCurrentUser().getAccount());
        }
        
        if (hasPower)
        {
            if (row.getCell(11) != null)
            {
                // 药品消耗量
                medicament.setConsumption(row.getCell(11).toString());
            }
            else
            {
                medicament.setConsumption("0");
            }
            
        }
        
        return medicament;
    }
    
    private void setRowValue(List<Medicaments> medicamentList, HSSFSheet sheet, boolean isError, boolean hasPower)
    {
        Medicaments medicament = null;
        
        HSSFRow row = null;
        
        // 首行记录从第7行开始
        for (int i = 0; i < medicamentList.size(); i++)
        {
            medicament = medicamentList.get(i);
            
            row = sheet.createRow(i + 6);
            HSSFCell cell = null;
            
            // 药品分类名称
            row.createCell(0);
            cell = row.getCell(0);
            cell.setCellValue(medicament.getCategoryName());
            
            // 药品名称
            row.createCell(1);
            cell = row.getCell(1);
            cell.setCellValue(medicament.getMedicamentsName());
            
            // 药品编码
            row.createCell(2);
            cell = row.getCell(2);
            cell.setCellValue(medicament.getMedicamentsCode());
            
            // 规格
            row.createCell(3);
            cell = row.getCell(3);
            cell.setCellValue(medicament.getMedicamentsSpecifications());
            
            // 剂量
            row.createCell(4);
            cell = row.getCell(4);
            cell.setCellValue(medicament.getMedicamentsDosage());
            
            // 速查码
            row.createCell(5);
            cell = row.getCell(5);
            cell.setCellValue(medicament.getMedicamentsSuchama());
            
            // 包装单位
            row.createCell(6);
            cell = row.getCell(6);
            cell.setCellValue(medicament.getMedicamentsPackingUnit());
            
            // 药品产地
            row.createCell(7);
            cell = row.getCell(7);
            cell.setCellValue(medicament.getMedicamentsPlace());
            
            // 药品库存
            row.createCell(8);
            cell = row.getCell(8);
            cell.setCellValue(medicament.getMedicamentsStock());
            
            // 药品库存最低量
            row.createCell(9);
            cell = row.getCell(9);
            cell.setCellValue(medicament.getMedicamentsLimit());
            
            // 库存修改人
            row.createCell(10);
            cell = row.getCell(10);
            cell.setCellValue(medicament.getOperator());
            
            // 根据权限判断是否需要
            if (hasPower)
            {
                // 药品消耗量
                row.createCell(11);
                cell = row.getCell(11);
                cell.setCellValue(medicament.getConsumption());
                
                // 数据异常原因
                if (isError)
                {
                    row.createCell(12);
                    cell = row.getCell(12);
                    cell.setCellValue(medicament.getOperatorLog());
                }
            }
            else
            {
                // 数据异常原因
                if (isError)
                {
                    row.createCell(11);
                    cell = row.getCell(11);
                    cell.setCellValue(medicament.getOperatorLog());
                }
            }
            
        }
        
    }
    
    /**
     * 得到保存的目录
     *
     * @return
     */
    private String getSaveDirPath()
    {
        String saveDir =
            SdConstant.WEB_ROOT_PATH + File.separator + "export" + File.separator + getCurrentUser().getAccount()
                + File.separator;
        
        //没有文件夹时创建
        FileUtil.mkdirs(saveDir);
        
        return saveDir;
    }
    
    /**
     * 初始化药品查询页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/initDrugOpen")
    public String initDrugOpen(Model model)
    {
        return "pivas/medicaments/medicamentOpenList";
    }
    
    /**
     * 拆药量分页查询
     *
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dgOpenAmtList", produces = "application/json")
    @ResponseBody
    public String drugOpenAmountList(JqueryStylePaging paging, DrugOpenAmount condition)
        throws Exception
    {
        String[] columns = new String[] {"id"};
        
        JqueryStylePagingResults<DrugOpenAmount> pagingResults = new JqueryStylePagingResults<DrugOpenAmount>(columns);
        
        List<DrugOpenAmount> medicamentsList = drugOpenAmountService.queryByPaging(paging, condition);
        
        pagingResults.setDataRows(medicamentsList);
        // 总数
        Integer total = drugOpenAmountService.getTotalCount(condition);
        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());
        
        return new Gson().toJson(pagingResults);
    }
    
    /**
     * 拆药量检查
     * 
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/chkOpenDg")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_943"})
    public String checkOpenDrug(DrugOpenAmount condition)
        throws Exception
    {
        Medicaments medicaments = new Medicaments();
        medicaments.setYyrq(condition.getOpenConfirmDate());
        
        int total = medicamentsService.getTotalCount(medicaments);
        if (total > 0)
        {
            return buildSuccessJsonMsg("拆药量检查成功！");
        }
        
        return buildSuccessJsonMsg("拆药量检查失败！");
        
    }
    
    /**
     * 获取需要拆药的药品
     * 
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qryOpenDg", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_943"})
    public String queryOpenDrug(JqueryStylePaging paging, DrugOpenAmount condition)
        throws Exception
    {
        
        String[] columns = new String[] {"id"};
        
        Medicaments medicaments = new Medicaments();
        medicaments.setYyrq(condition.getOpenConfirmDate());
        
        JqueryStylePagingResults<DrugOpenAmount> pagingResults = new JqueryStylePagingResults<DrugOpenAmount>(columns);
        
        List<Medicaments> medicamentsList = medicamentsService.queryByPaging(paging, medicaments);
        List<DrugOpenAmount> drugOpenAmountList = new ArrayList<DrugOpenAmount>();
        DrugOpenAmount drugOpenAmount = null;
        if (DefineCollectionUtil.isNotEmpty(medicamentsList))
        {
            // 前一天药品消耗量
            float stock_last_day;
            
            // 对应上一周药品消耗量
            float stock_last_week;
            
            float stock_last;
            
            for (Medicaments medicament : medicamentsList)
            {
                drugOpenAmount = new DrugOpenAmount();
                stock_last_day =
                    Float.valueOf((StrUtil.isNotNull(medicament.getLastDayUsed())) ? medicament.getLastDayUsed() : "0");
                stock_last_week =
                    Float.valueOf((StrUtil.isNotNull(medicament.getLastWeekUsed())) ? medicament.getLastWeekUsed()
                        : "0");
                
                stock_last = (stock_last_day > stock_last_week) ? stock_last_day : stock_last_week;
                
                drugOpenAmount.setAmountPlan(String.valueOf((int)stock_last));
                drugOpenAmount.setAmount(String.valueOf((int)stock_last));
                drugOpenAmount.setCode(medicament.getMedicamentsCode());
                drugOpenAmount.setName(medicament.getMedicamentsName());
                drugOpenAmount.setPlace(medicament.getMedicamentsPlace());
                drugOpenAmount.setPlace_code(medicament.getMedicamentsPlaceCode());
                drugOpenAmountList.add(drugOpenAmount);
            }
        }
        pagingResults.setDataRows(drugOpenAmountList);
        
        // 总数
        pagingResults.setTotal(medicamentsService.getTotalCount(medicaments));
        
        // 当前页
        pagingResults.setPage(paging.getPage());
        
        return new Gson().toJson(pagingResults);
    }
    
    /**
     * 保存需要拆药的药品
     * 
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dgOpenCfmAdmin")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_943"})
    public String drugOpenConfirmAdmin(DrugOpenAmount condition)
        throws Exception
    {
        Medicaments medicaments = new Medicaments();
        medicaments.setYyrq(condition.getOpenConfirmDate());
        List<Medicaments> medicamentsList = medicamentsService.queryByPaging(new JqueryStylePaging(), medicaments);
        
        DrugOpenAmount drugOpenAmount = null;
        if (DefineCollectionUtil.isNotEmpty(medicamentsList))
        {
            // 前一天药品消耗量
            float stock_last_day;
            
            // 对应上一周药品消耗量
            float stock_last_week;
            
            float stock_last;
            
            for (Medicaments medicament : medicamentsList)
            {
                drugOpenAmount = new DrugOpenAmount();
                stock_last_day =
                    Float.valueOf((StrUtil.isNotNull(medicament.getLastDayUsed())) ? medicament.getLastDayUsed() : "0");
                stock_last_week =
                    Float.valueOf((StrUtil.isNotNull(medicament.getLastWeekUsed())) ? medicament.getLastWeekUsed()
                        : "0");
                
                stock_last = (stock_last_day > stock_last_week) ? stock_last_day : stock_last_week;
                drugOpenAmount.setAmountPlan(String.valueOf((int)stock_last));
                drugOpenAmount.setAmount(String.valueOf((int)stock_last));
                drugOpenAmount.setCode(medicament.getMedicamentsCode());
                drugOpenAmount.setOpenConfirmDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
                drugOpenAmount.setName(medicament.getMedicamentsName());
                drugOpenAmount.setPlace(medicament.getMedicamentsPlace());
                drugOpenAmount.setPlace_code(medicament.getMedicamentsPlaceCode());
                
                drugOpenAmountService.synData(drugOpenAmount);
            }
        }
        
        return buildSuccessJsonMsg("拆药量保存成功！");
    }
    
    /**
     * 保存需要拆药的药品
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/drgOpenCfm")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_944"})
    public String drugOpenConfirm(Long[] ids)
        throws Exception
    {
        User user = getCurrentUser();
        
        try
        {
            if (DefineStringUtil.isArrayNotEmpty(ids))
            {
                DrugOpenAmount drugOpenAmount = null;
                for (Long id : ids)
                {
                    drugOpenAmount = new DrugOpenAmount();
                    
                    drugOpenAmount.setId(id);
                    drugOpenAmount.setOpenDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
                    drugOpenAmount.setOperator(user.getAccount());
                    drugOpenAmountService.updateByPrimaryKey(drugOpenAmount);
                }
            }
            return buildSuccessJsonMsg("common.op.success");
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    /**
     * 保存需要拆药的药品
     * 
     * @param drugOpenAmount
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updOpenAmt")
    @ResponseBody
    public String updateOpenAmount(DrugOpenAmount drugOpenAmount)
        throws Exception
    {
        try
        {
            drugOpenAmount.setOpenDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
            drugOpenAmountService.updateByPrimaryKey(drugOpenAmount);
            return buildSuccessJsonMsg("common.op.success");
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
