package com.zc.schedule.product.report.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.manager.controller.ManagerController;
import com.zc.schedule.product.manager.entity.TempBean;
import com.zc.schedule.product.report.entity.ChartBean;
import com.zc.schedule.product.report.entity.UserBean;
import com.zc.schedule.product.report.entity.WorkBean;
import com.zc.schedule.product.report.service.ReportService;

import net.sf.json.JSONObject;

/**
 * 统计报表控制类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Controller
@RequestMapping("/stat")
public class ReportController extends BaseController
{
    
    @Resource(name = "reportServiceImpl") 
    private ReportService reportService;
    
    /**
     * 
     * 报表主界面
     * @param model
     * @param request
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/index")
    public String init(Model model, HttpServletRequest request) throws Exception
    {
        
        Date now = new Date();
        String minDateStr = DateUtils.lastMonth(now,5);
        model.addAttribute("minDateStr", minDateStr);
        
        return "/schedule/report/index";
    }
    
    
    /**
     * 
     * 休息图表
     * @param startDate
     * @param endDate
     * @param type
     * @param request
     * @return ResultData
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/resetCharts", method = RequestMethod.POST)
    public ResultData resetCharts(String startDate,String endDate,String type,HttpServletRequest request) throws Exception
    {
        
        try
        {
        
            ArrayList<ChartBean> resetArray = new ArrayList<ChartBean>();
        
            //获取其他和休假的班次
            List<WorkBean> workList = reportService.getResetWork();
            Date now = new Date();
            SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
            
            if("day".equals(type)){
                startDate = sf.format(now);
                endDate = sf.format(now);
                
            }else if ("week".equals(type)){
                
                List<TempBean> dataList = ManagerController.getweekDate(now);
                startDate = dataList.get(0).getValue();
                endDate = dataList.get(6).getValue();
                
            }else if("month".equals(type)){
                
                String[] dataArray = DateUtils.getMonthRange(now);
                startDate = dataArray[0];
                endDate = dataArray[1];
                
            }else if("quarter".equals(type)){
                
                Date[] seasonArray = DateUtils.getSeasonDate(now);
                Date StartSeason = seasonArray[0];
                Date endSeason = seasonArray[2];
                
                String[] startArray = DateUtils.getMonthRange(StartSeason);
                String[] endArray = DateUtils.getMonthRange(endSeason);
                
                startDate = startArray[0];
                endDate = endArray[1];
                
            }
            
            List<UserBean> userList = reportService.getUserWork(startDate,endDate,workList);
            
            int userSize = userList.size();
            Collections.sort(userList, new Comparator<UserBean>() {
                @Override
                public int compare(UserBean o1, UserBean o2)
                {
                    Float userTime1 = Float.parseFloat(o1.getTotaltime());
                    Float userTime2 = Float.parseFloat(o2.getTotaltime());
                    
                    return userTime2.compareTo(userTime1);
                }
             });

            List<UserBean> subList = new ArrayList<UserBean>();
            if(userSize > 10){
                subList = userList.subList(0, 10);
            }else{
                subList = userList;
            }
            
            ChartBean chart = new ChartBean();
            chart.setWorkList(workList);
            chart.setUserList(subList);
            resetArray.add(chart);
            
            
            String result = shortDateJsonMapper.toJson(resetArray);
        
            return DataFormat.formatSucc(result);
            
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.统计报表, "图表创建失败");
            throw e;
            
        }    
        
        
    }
    
    
    /**
     * 
     * 工作图表
     * @param startDate
     * @param endDate
     * @param type
     * @param request
     * @return ResultData
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/workCharts", method = RequestMethod.POST)
    public ResultData workCharts(String startDate,String endDate,String type,HttpServletRequest request) throws Exception
    {
        
        try
        {
        
            ArrayList<ChartBean> resetArray = new ArrayList<ChartBean>();
        
            //获取工作班次
            List<WorkBean> workList = reportService.getWorkList();
            Date now = new Date();
            SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
            
            if("day".equals(type)){
                startDate = sf.format(now);
                endDate = sf.format(now);
                
            }else if ("week".equals(type)){
                
                List<TempBean> dataList = ManagerController.getweekDate(now);
                startDate = dataList.get(0).getValue();
                endDate = dataList.get(6).getValue();
                
            }else if("month".equals(type)){
                
                String[] dataArray = DateUtils.getMonthRange(now);
                startDate = dataArray[0];
                endDate = dataArray[1];
                
            }else if("quarter".equals(type)){
                
                Date[] seasonArray = DateUtils.getSeasonDate(now);
                Date StartSeason = seasonArray[0];
                Date endSeason = seasonArray[2];
                
                String[] startArray = DateUtils.getMonthRange(StartSeason);
                String[] endArray = DateUtils.getMonthRange(endSeason);
                
                startDate = startArray[0];
                endDate = endArray[1];
                
            }
            
            List<UserBean> userList = reportService.getUserWork(startDate,endDate,workList);
            
            int userSize = userList.size();
            Collections.sort(userList, new Comparator<UserBean>() {
                @Override
                public int compare(UserBean o1, UserBean o2)
                {
                    Float userTime1 = Float.parseFloat(o1.getTotaltime());
                    Float userTime2 = Float.parseFloat(o2.getTotaltime());
                    
                    return userTime2.compareTo(userTime1);
                }
             });

            List<UserBean> subList = new ArrayList<UserBean>();
            if(userSize > 10){
                subList = userList.subList(0, 10);
            }else{
                subList = userList;
            }
            
            ChartBean chart = new ChartBean();
            chart.setWorkList(workList);
            chart.setUserList(subList);
            resetArray.add(chart);
            
            
            String result = shortDateJsonMapper.toJson(resetArray);
        
            return DataFormat.formatSucc(result);
            
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.统计报表, "图表创建失败");
            throw e;
            
        }    
        
        
    }
    
    /**
     * 
     * 下载excel
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/excelExport", method = RequestMethod.POST)
    public void excelExport(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        String fileName = "";
        
        try{
            
            String type = request.getParameter("type");  
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String reportType = request.getParameter("reportType");
            
            String downLoadPath = request.getSession().getServletContext().getRealPath("/downFile");
            
            File downFile = new File(downLoadPath);
            
            //判断上传文件夹是否存在
            if (!downFile.exists())
            {
                if(!downFile.mkdir()){
                    throw new Exception("无法创建下载文件");
                };
            }
            
            Date now = new Date();
            SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
            
            if("week".equals(type)){
                if("reset".equals(reportType)){
                    fileName = "本周休假报表.xls";
                }else if("work".equals(reportType)){
                    fileName = "本周工作报表.xls";
                }
                
                List<TempBean> dataList = ManagerController.getweekDate(now);
                startDate = dataList.get(0).getValue();
                endDate = dataList.get(6).getValue();
                
            }else if("day".equals(type)){
                if("reset".equals(reportType)){
                    fileName = "当日休假报表.xls";
                }else if("work".equals(reportType)){
                    fileName = "当日工作报表.xls";
                }
                
                startDate = sf.format(now);
                endDate = sf.format(now);
                
            }else if("month".equals(type)){
                if("reset".equals(reportType)){
                    fileName = "本月休假报表.xls";
                }else if("work".equals(reportType)){
                    fileName = "本月工作报表.xls";
                }
                
                String[] dataArray = DateUtils.getMonthRange(now);
                startDate = dataArray[0];
                endDate = dataArray[1];
                
            }else if("quarter".equals(type)){
                if("reset".equals(reportType)){
                    fileName = "本季度休假报表.xls";
                }else if("work".equals(reportType)){
                    fileName = "本季度工作报表.xls";
                }
                
                Date[] seasonArray = DateUtils.getSeasonDate(now);
                Date StartSeason = seasonArray[0];
                Date endSeason = seasonArray[2];
                
                String[] startArray = DateUtils.getMonthRange(StartSeason);
                String[] endArray = DateUtils.getMonthRange(endSeason);
                
                startDate = startArray[0];
                endDate = endArray[1];
                
            }else if("custom".equals(type)){
                if("reset".equals(reportType)){
                    fileName = "休假报表.xls";
                }else if("work".equals(reportType)){
                    fileName = "工作报表.xls";
                }
            }
            
            if("".equals(fileName)){
                throw new Exception();
            }
            
            String filePath = downLoadPath + "\\" + fileName;
            File file = new File(filePath);
            if(file.exists()){
                if(!file.delete()){
                    throw new Exception("文件无法删除");
                };
            }
            
            List<WorkBean> workList = new ArrayList<WorkBean>();
            if("reset".equals(reportType)){
                workList = reportService.getResetWork();
            }else if("work".equals(reportType)){
                workList = reportService.getWorkList();
            }
            
            
            List<UserBean> userList = reportService.getUserWork(startDate,endDate,workList);
            
            Collections.sort(userList, new Comparator<UserBean>() {
                @Override
                public int compare(UserBean o1, UserBean o2)
                {
                    Float userTime1 = Float.parseFloat(o1.getTotaltime());
                    Float userTime2 = Float.parseFloat(o2.getTotaltime());
                    
                    return userTime2.compareTo(userTime1);
                }
             });
            
            
            reportService.exportUserWork(workList,userList,filePath);
                
            downLoadExcel(filePath,fileName,request,response);
                
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.统计报表, "Excel"+fileName+"导出失败");
            throw e;
            
        }
        
    }

    /**
     * 下载excel
     * @param filePath
     * @param fileName 
     * @param request
     * @param response
     */
    private void downLoadExcel(String filePath, String fileName,HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
        
        File file = new File(filePath);
        //文件名
        response.setHeader("Content-Disposition",
            "attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + "\"");
            
        response.setContentLength((int)file.length());
        response.setContentType("application/x-msdownload;");  
        
        byte[] buffer = new byte[4096];// 缓冲区
        
        BufferedOutputStream output = null;
        
        BufferedInputStream input = null;
        
        try
        {
            
            output = new BufferedOutputStream(response.getOutputStream());
            
            input = new BufferedInputStream(new FileInputStream(file));
            
            int n = -1;
            //遍历，开始下载
            while ((n = input.read(buffer, 0, 4096)) > -1)
            {
                output.write(buffer, 0, n);
            }
            
            response.flushBuffer();
            
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.统计报表, "Excel"+fileName+"下载成功");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.统计报表, "Excel"+fileName+"下载失败");
            throw e;
        }
        finally
        {
            
            //关闭流，不可少
            if (input != null)
            {
                input.close();
            }
            
            if (output != null)
            {
                output.flush();
                output.close();
            }
            
        }
        
    }
    
    /**
     * 
     * 获取工作报表
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/workGrid", method = RequestMethod.POST)
    public ResultPage getWorkGrid(HttpServletRequest request,@RequestBody Map<String,Object> reqParam) throws Exception
    {
        String startDate = String.valueOf(reqParam.get("startDate"));
        String endDate = String.valueOf(reqParam.get("endDate"));
        String type = String.valueOf(reqParam.get("type"));
        String reportType= String.valueOf(reqParam.get("reportType"));
        
        Date now = new Date();
        SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
        
        if("week".equals(type)){
            
            List<TempBean> dataList = ManagerController.getweekDate(now);
            startDate = dataList.get(0).getValue();
            endDate = dataList.get(6).getValue();
            
        }else if("day".equals(type)){
            
            startDate = sf.format(now);
            endDate = sf.format(now);
            
        }else if("month".equals(type)){
            
            String[] dataArray = DateUtils.getMonthRange(now);
            startDate = dataArray[0];
            endDate = dataArray[1];
            
        }else if("quarter".equals(type)){
            
            Date[] seasonArray = DateUtils.getSeasonDate(now);
            Date StartSeason = seasonArray[0];
            Date endSeason = seasonArray[2];
            
            String[] startArray = DateUtils.getMonthRange(StartSeason);
            String[] endArray = DateUtils.getMonthRange(endSeason);
            
            startDate = startArray[0];
            endDate = endArray[1];
            
        }
        
        List<WorkBean> workList = new ArrayList<WorkBean>();
        if("reset".equals(reportType)){
            workList = reportService.getResetWork();
        }else if("work".equals(reportType)){
            workList = reportService.getWorkList();
        }
        
        List<UserBean> userList = reportService.getUserWork(startDate,endDate,workList);
        
        Collections.sort(userList, new Comparator<UserBean>() {
            @Override
            public int compare(UserBean o1, UserBean o2)
            {
                Float userTime1 = Float.parseFloat(o1.getTotaltime());
                Float userTime2 = Float.parseFloat(o2.getTotaltime());
                
                return userTime2.compareTo(userTime1);
            }
         });
        
        List<JSONObject> list = getJsonList(userList);
            
        
        return DataFormat.formatPageData(list) ;
    }
    
    
    /**
     * 将list变成jsonlist
     * @param userList
     * @return List
     */
    private List<JSONObject> getJsonList(List<UserBean> userList)
    {
        List<JSONObject> list= new ArrayList<>();
        
        for(UserBean user : userList){
            
            JSONObject object = new JSONObject();
            
            object.put("user_name", user.getUser_name());
            
            List<WorkBean> workTimeList = user.getWorkTimeList();
            
            int size = workTimeList.size();
            
            for(int i = 0 ; i < size ; i ++){
                
                WorkBean work = workTimeList.get(i);
                
                String time = work.getTotalTime();
                
                object.put("workNum"+i, time);
            }
            
            object.put("totaltime", user.getTotaltime());
            
            list.add(object);
            
        }
        
        return list;
    }


    /**
     * 
     * 获取班次
     * @param request
     * @param type
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/getClasses", method = RequestMethod.POST)
    public ResultData getClasses(HttpServletRequest request,String type) 
    {

        List<WorkBean> workList = new ArrayList<WorkBean>();
        
        if("reset".equals(type)){
            
            workList = reportService.getResetWork();
            
        }else if("work".equals(type)){
            
            workList = reportService.getWorkList();
            
        }
        
        String result = shortDateJsonMapper.toJson(workList);
        
        return DataFormat.formatSucc(result);
    }
    
    
}
