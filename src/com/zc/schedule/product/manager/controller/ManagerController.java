package com.zc.schedule.product.manager.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zc.schedule.product.manager.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.manager.entity.ManagerBean;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.manager.entity.TempBean;
import com.zc.schedule.product.manager.entity.UserMonthBean;
import com.zc.schedule.product.manager.entity.WeekDataBean;
import com.zc.schedule.product.manager.entity.WorkBean;
import com.zc.schedule.product.scheduleSettings.entity.PostBean;

import net.sf.json.JSONArray;


/**
 * 排班管理控制类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Controller
@RequestMapping("/scheduleMgr")
public class ManagerController extends BaseController
{

    @Resource(name = "managerServiceImpl") 
    private ManagerService managerService;
    
    /**
     * 
     * 排班页面展示
     * @return String
     */
    @RequestMapping(value = "/index")
    public String initManager(Model model, HttpServletRequest request) throws Exception
    {
        
        //获取岗位列表
        List<PostBean> postList = managerService.getPostList();
        
        //获取一周日期
        Date now = new Date();
        List<TempBean> dateList = getweekDate(now);
        
        //获取备注
        String remark = managerService.getRemarkStr(dateList);
        
        //获取排班数据
        List<ScheduleBean> scheduleList = managerService.getWeekList(dateList);
        //获取班次设置
        List<WorkBean> workLiist = managerService.getWorkList();
        
        model.addAttribute("workLiist",workLiist);
        model.addAttribute("scheduleList",scheduleList);
        model.addAttribute("remark", remark);
        model.addAttribute("dateList", dateList);
        model.addAttribute("postList", postList);
        
        String minDateStr = DateUtils.lastMonth(now,5);
        model.addAttribute("minDateStr", minDateStr);
        
        return "/schedule/manager/index";
    }
    
    /**
     * 
     * 获取自动排班数据
     * @param model
     * @param request
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/autoSchedule")
    public String autoSchedule(Model model, String startDate, HttpServletRequest request) throws Exception
    {
        
        //HttpSession session = request.getSession();
        
        //获取岗位列表
        List<PostBean> postList = managerService.getPostList();
        
        //获取一周日期
        Date sDate = DateUtils.strParseDate(startDate);

        List<TempBean> dateList = getweekDate(sDate);
        
        //获取自动排班数据
        List<ScheduleBean> scheduleList = managerService.getAutoWeekList(dateList);
        
        //获取备注
        String remark = managerService.getRemarkStr(dateList);
        
        //获取班次设置
        List<WorkBean> workLiist = managerService.getWorkList();
        
        model.addAttribute("workLiist",workLiist);
        model.addAttribute("scheduleList",scheduleList);
        model.addAttribute("remark", remark);
        model.addAttribute("dateList", dateList);
        model.addAttribute("postList", postList);
        
        return "/schedule/manager/gridView";
    }
    
    
    /**
     * 获取一周日期
     * @param now 
     * @return List
     */
    public static List<TempBean> getweekDate(Date now)
    {
        
        List<TempBean> list = new ArrayList<TempBean>();
        
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
            
        Date weekDate = null;
        TempBean temp = null;
        
        for(int i = 1; i < 8 ; i++){
            
            int number = now.getDay();
            if(number == 0){
                number = 7;
            }
            
            weekDate = new Date(now.getYear(),now.getMonth(),now.getDate() - number + i);
            
            String wDate = sf.format(weekDate);
            
            String[] dateArr = wDate.split("-");
            
            temp = new TempBean();
            
            temp.setName(dateArr[2]);
            temp.setValue(wDate);
            
            list.add(temp);
            
        }
        
        return list;
    }
    

    /**
     * 
     * 周视图加载
     * @param model
     * @param request
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/reviewWeek")
    public String reviewWeek(Model model, HttpServletRequest request,String date) throws Exception
    {
        
        //HttpSession session = request.getSession();
        //获取岗位列表
        List<PostBean> postList = managerService.getPostList();
        
        Date sDate = DateUtils.strParseDate(date);
        
        List<TempBean> dateList = getweekDate(sDate);
        
        //获取排班数据
        List<ScheduleBean> scheduleList = managerService.getWeekList(dateList);
        
        //获取班次设置
        List<WorkBean> workLiist = managerService.getWorkList();
        
        model.addAttribute("dateList", dateList);
        model.addAttribute("postList", postList);
        model.addAttribute("workLiist",workLiist);
        model.addAttribute("scheduleList",scheduleList);
        
        return "/schedule/manager/gridView";
        
    }
    
    /**
     * 
     * 日视图加载
     * @param model
     * @param request
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/reviewDay")
    public String reviewDay(Model model, String dayDate, HttpServletRequest request) throws Exception
    {
        
        Date sDate =  DateUtils.strParseDate(dayDate);
        
        //获取一周日期
        List<TempBean> dateList = getweekDate(sDate);
        
        //获取排班数据
        List<ScheduleBean> dayList = managerService.getDayList(dateList,dayDate);
        
        model.addAttribute("dayList", dayList);
        
        model.addAttribute("dayDate", dayDate);
        
        return "/schedule/manager/dayView";
    }
    
    /**
     * 
     * 保存备注
     * @param startDate
     * @param endDate
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveRemark", method = RequestMethod.POST)
    public ResultData saveRemark(String startDate,String endDate,String remark,HttpServletRequest request) throws Exception
    {
        
        try{
        
                int count = managerService.getCountRemark(startDate,endDate);
                
                if(count > 0){
                    
                    int row = managerService.updateRemark(startDate,endDate,remark);
                    addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "更新"+startDate+"到"+endDate+"期间的备注成功");
                    return DataFormat.formatUpd(row);
                }else{
                    
                    int row = managerService.addRemark(startDate,endDate,remark);
                    addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "添加"+startDate+"到"+endDate+"期间的备注成功");
                    return DataFormat.formatAdd(row);
                }
                
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, startDate+"到"+endDate+"添加备注失败");
            throw e;
        }
            
    }
    
    /**
     * 
     * 保存排班管理数据
     * @param jsonStr
     * @param startDate
     * @param endDate
     * @param request
     * @return ResultData
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveSchedule", method = RequestMethod.POST)
    public ResultData saveSchedule(String jsonStr,String startDate,String endDate,String changeStr ,HttpServletRequest request) throws Exception
    {
        
        try
        {
        
        //排班json字符串转化为arraylist对象
        JSONArray json = JSONArray.fromObject(jsonStr);
        
        ArrayList<ManagerBean> managerArray = (ArrayList<ManagerBean>)JSONArray.toCollection(json, ManagerBean.class);
        for(ManagerBean manager : managerArray){
            
            JSONArray jsonManager = JSONArray.fromObject(manager.getWeekDataBeanList());
            
            ArrayList<WeekDataBean> weekDataArray = (ArrayList<WeekDataBean>)JSONArray.toCollection(jsonManager, WeekDataBean.class);
           
            manager.setWeekDataBeanList(weekDataArray);
            
        }
        //班次变化json字符串转化为arraylist对象
        JSONArray changeJson = JSONArray.fromObject(changeStr);
        ArrayList<TempBean> changeArray = (ArrayList<TempBean>)JSONArray.toCollection(changeJson, TempBean.class);
        for(TempBean tBean : changeArray){
            
            JSONArray jsonManager = JSONArray.fromObject(tBean.getChangeList());
            
            ArrayList<TempBean> changeList = (ArrayList<TempBean>)JSONArray.toCollection(jsonManager, TempBean.class);
           
            tBean.setChangeList(changeList);
            
        }
        
        
        //删除旧数据
        managerService.deleteScheduleData(startDate,endDate);
        managerService.deleteScheduleDataWeek(startDate,endDate);
        //保存新数据
        managerService.saveSchedule(managerArray,request);
        
        
        Date sDate = DateUtils.strParseDate(startDate);
        
        List<TempBean> dateList = getweekDate(sDate);
        //更新人员节假日上班次数
        managerService.updateHolidayWork(changeArray,dateList,request);
        
        addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "保存排班管理"+startDate+"到"+endDate+"期间数据成功");
        return DataFormat.formatSucc("保存成功");
        
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "保存排班管理"+startDate+"到"+endDate+"期间数据失败");
            throw e;
            
        }
       
            
    }
    
    
    /**
     * 
     * 初始化备注
     * @param startDate
     * @param endDate
     * @param request
     * @return ResultData
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/initRemark", method = RequestMethod.POST)
    public ResultData initRemark(String startDate,String endDate,HttpServletRequest request) throws Exception
    {
        try
        {
            String remark = managerService.initRemark(startDate,endDate);
        
            return DataFormat.formatSucc(remark);
            
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "初始化备注失败");
            throw e;
        }
        
    }
    
    
    /**
     * 
     * 下载excel
     * @param model
     * @param request
     * @param response
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/printSchedule")
    public String printSchedule(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        
        String type = request.getParameter("type");  
        String date = request.getParameter("date");
        
        
        Date sDate = DateUtils.strParseDate(date);
        List<TempBean> dateList = getweekDate(sDate);
        
        List<ScheduleBean> scheduleList = null;
        String url = "";
        if("week".equals(type)){
            
            //获取排班数据
            scheduleList = managerService.getPrintWeek(dateList);
            
            model.addAttribute("dateList", dateList);
            model.addAttribute("scheduleList",scheduleList);
            
            String startD = dateList.get(0).getValue();
            String endD = dateList.get(6).getValue();
            String[] startArr = startD.split("-");
            String[] endArr = endD.split("-");
            
            String timeStr = "本周 ("+startArr[0]+" 年 "+startArr[1]+" 月 "+startArr[2]+" 日 - "+endArr[0]+" 年 "+endArr[1]+" 月 "+endArr[2]+" 日)排班表";
            
            model.addAttribute("timeStr",timeStr);

            url="/schedule/manager/printWeek";
        }else if("day".equals(type)){
            
            //获取排班数据
            scheduleList = managerService.getDayList(dateList,date);
            
            model.addAttribute("dayList", scheduleList);
            model.addAttribute("dayDate", date);
            
            String[] startArr = date.split("-");
            String timeStr = "今日 "+startArr[0]+" 年 "+startArr[1]+" 月 "+startArr[2]+" 日排班表";
            
            model.addAttribute("timeStr",timeStr);
            
            url="/schedule/manager/printDay";
        }else if("month".equals(type)){
            
            List<TempBean> monthDateList = DateUtils.getMonthDate(date);
            
            //获取排班数据
            List<UserMonthBean> userMonthList = managerService.getPrintMonth(monthDateList);
            
            model.addAttribute("userMonthList",userMonthList);
            model.addAttribute("monthDateList", monthDateList);
            
            int length = monthDateList.size();
            String startD = monthDateList.get(0).getValue();
            String endD = monthDateList.get(length-1).getValue();
            
            String[] startArr = startD.split("-");
            String[] endArr = endD.split("-");
            String timeStr = "本月 ("+startArr[0]+" 年 "+startArr[1]+" 月 "+startArr[2]+" 日 - "+endArr[0]+" 年 "+endArr[1]+" 月 "+endArr[2]+" 日)排班表";
            
            model.addAttribute("timeStr",timeStr);
            model.addAttribute("monthLength",length);

            url="/schedule/manager/printMonth";
        }
        
        
        return url;
        
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
            String date = request.getParameter("date");
            
            String downLoadPath = request.getSession().getServletContext().getRealPath("/downFile");
            
            File downFile = new File(downLoadPath);
            
            //判断上传文件夹是否存在
            if (!downFile.exists())
            {
                if(!downFile.mkdir()){
                    throw new Exception("无法创建下载文件");
                };
            }
            
            
            if("week".equals(type)){
                fileName = "周排班数据.xls";
                
            }else if("day".equals(type)){
                
                fileName = "日排班数据.xls";
            }else if("month".equals(type)){
                
                fileName = "月排班数据.xls";
            }
            
            if("".equals(fileName)){
                throw new Exception();
            }
            
            String filePath = downLoadPath + "\\" + fileName;
            File file = new File(filePath);
            if(file.exists()){
                if(!file.delete()){
                    throw new Exception("文件无法删除");
                }
            }
            
            if("week".equals(type)){
                managerService.excelExportWeek(date,response,filePath);
                downLoadExcel(filePath,fileName,response,request);
            }else if("day".equals(type)){
                managerService.excelExportDay(date,response,filePath);
                downLoadExcel(filePath,fileName,response,request);
            }else if("month".equals(type)){
                managerService.excelExportMonth(date,response,filePath);
                downLoadExcel(filePath,fileName,response,request);
            }
            
           
        }catch(Exception e){
            
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "Excel"+fileName+"导出失败");
            throw e;
            
        }
        
    }

    /**
     * 下载excel
     * @param filePath
     * @param fileName 
     * @param response
     */
    private void downLoadExcel(String filePath, String fileName, HttpServletResponse response, HttpServletRequest request) throws Exception
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
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "下载"+fileName+"成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.排班管理, "下载"+fileName+"失败");
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
    
}
