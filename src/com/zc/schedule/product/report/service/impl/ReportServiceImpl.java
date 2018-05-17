package com.zc.schedule.product.report.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.springframework.stereotype.Repository;

import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.product.report.dao.ReportDao;
import com.zc.schedule.product.report.entity.UserBean;
import com.zc.schedule.product.report.entity.WorkBean;
import com.zc.schedule.product.report.service.ReportService;

@Repository(value = "reportServiceImpl")
public class ReportServiceImpl implements ReportService
{
    
    @Resource(name = "reportDao")
    private ReportDao reportDao;

     
    @Override
    public List<WorkBean> getResetWork()
    {
        return reportDao.getResetWork();
    }
    
    
    @Override
    public List<WorkBean> getWorkList()
    {
        return reportDao.getWorkList();
    }

    @Override
    public List<UserBean> getUserWork(String startDate, String endDate, List<WorkBean> workList) throws Exception
    {
        
        List<UserBean> userWorkList = reportDao.getUser();
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        for(UserBean per : userWorkList){
            
            double totaltime = 0;
            
            String userid = per.getUser_id();
            
            List<WorkBean> workTimeList = new ArrayList<WorkBean>();
            
            for(WorkBean work : workList){
                
                String workid = work.getWorkId();
                String workname = work.getWorkName();
                
                WorkBean time = new WorkBean();
                
                time.setWorkId(workid);
                time.setWorkName(workname);
                
                double workTime = reportDao.getWorkTotal(workid,userid,startDate,endDate);
                
                totaltime = totaltime + workTime;

                String workTimeStr = df.format(workTime);
                
                time.setTotalTime(DateUtils.subZeroAndDot(workTimeStr));
                
                workTimeList.add(time);
                
            }
            
            String  totalStr = df.format(totaltime);
            per.setTotaltime(DateUtils.subZeroAndDot(totalStr));
            per.setWorkTimeList(workTimeList);
            
        }
        
        
        
        return userWorkList;
    }


    @Override
    public void exportUserWork(List<WorkBean> workList, List<UserBean> userList, String filePath)
        throws Exception
    {
        
        String title = "报表";
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        HSSFSheet sheet = workbook.createSheet(title);
        
        sheet.setDefaultRowHeightInPoints(15);
        //列宽
        sheet.setDefaultColumnWidth(16);
        
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)12);//字体
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
        
        //产生一行  
        HSSFRow row = sheet.createRow(0);
        
        //产生第一个单元格  
        HSSFCell cell = row.createCell(0);
        //设置单元格内容为字符串型  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //往第一个单元格中写入信息  
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        
        int oneRow = 1;
        for(WorkBean work : workList){
            
            String workName = work.getWorkName();
            
            cell = row.createCell(oneRow);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(workName);
            cell.setCellStyle(style);
            
            oneRow++;
            
        }
        
        cell = row.createCell(oneRow);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("总时间（小时）");
        cell.setCellStyle(style);
        
        
        int rowNum = 1;
        for(UserBean per : userList){
            
            String total = per.getTotaltime();
            
            row = sheet.createRow(rowNum);
            cell = row.createCell(0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(per.getUser_name());
            cell.setCellStyle(style);
            
            int cellNum = 1;
            
            List<WorkBean> userWorkList = per.getWorkTimeList();
            
            for(WorkBean work : userWorkList){
                
                String worktime = work.getTotalTime();
                
                cell = row.createCell(cellNum);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(worktime);
                cell.setCellStyle(style);
                
                cellNum ++;
            }
            
            rowNum ++;
            
            cell = row.createCell(cellNum);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(total);
            cell.setCellStyle(style);
            
        }
        
        
        FileOutputStream fileOut = null;
        try
        {
            
            fileOut = new FileOutputStream(filePath);
            
            workbook.write(fileOut);
            //workbook.close();
            
            fileOut.flush();
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            
            IOUtils.closeQuietly(fileOut);
        }
        
    }



}
