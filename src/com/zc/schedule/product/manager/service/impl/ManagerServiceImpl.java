package com.zc.schedule.product.manager.service.impl;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zc.schedule.product.manager.controller.ManagerController;
import com.zc.schedule.product.manager.dao.ManagerDao;
import com.zc.schedule.product.manager.service.ManagerService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.product.manager.entity.GroupInfoBean;
import com.zc.schedule.product.manager.entity.ManagerBean;
import com.zc.schedule.product.manager.entity.MonthWorkBean;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.manager.entity.TempBean;
import com.zc.schedule.product.manager.entity.UserMonthBean;
import com.zc.schedule.product.manager.entity.WeekBean;
import com.zc.schedule.product.manager.entity.WeekDataBean;
import com.zc.schedule.product.manager.entity.WorkBean;
import com.zc.schedule.product.personnelgroup.service.PersonnelGroupService;
import com.zc.schedule.product.scheduleSettings.entity.PostBean;

@Repository(value = "managerServiceImpl")
public class ManagerServiceImpl implements ManagerService
{
    @Resource(name = "managerDao")
    private ManagerDao managerDao;
    
    /**
     * 人员分组管理接口
     */
    @Resource()
    private PersonnelGroupService personnelGroupService;
    
    
    @Override
    public List<PostBean> getPostList()
    {
        return managerDao.getPostList();
    }
    
    @Override
    public String getRemarkStr(List<TempBean> dateList)
        throws Exception
    {
        
        String startStr = "";
        String endStr = "";
        
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        
        return managerDao.getRemarkStr(startStr, endStr);
        
    }
    
    @Override
    public int addRemark(String startDate, String endDate, String remark)
    {
        
        return managerDao.addRemark(startDate, endDate, remark);
        
    }
    
    @Override
    public int getCountRemark(String startDate, String endDate)
    {
        return managerDao.getCountRemark(startDate, endDate);
    }
    
    @Override
    public int updateRemark(String startDate, String endDate, String remark)
    {
        
        return managerDao.updateRemark(startDate, endDate, remark);
        
    }
    
    @Override
    public String initRemark(String startDate, String endDate)
    {
        return managerDao.getRemarkStr(startDate, endDate);
    }
    
    @Override
    public List<ScheduleBean> getWeekList(List<TempBean> dateList)
        throws Exception
    {
        List<ScheduleBean> dataList = new ArrayList<ScheduleBean>();
        
        String startStr = "";
        String endStr = "";
        
        String nowStartStr = "";
        String nowEndStr = "";
        
        //获取当前周开始和结束日期
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        //获取本周日期
        Date now = new Date();
        List<TempBean> nowDateList = ManagerController.getweekDate(now);
        //获取开始和结束日期
        if (nowDateList != null && nowDateList.size() == 7)
        {
            TempBean start = nowDateList.get(0);
            TempBean end = nowDateList.get(6);
            
            nowStartStr = start.getValue();
            nowEndStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        ScheduleBean bean = new ScheduleBean();
        bean.setValidity_end(endStr);
        bean.setValidity_start(startStr);
        
        Date sDate = DateUtils.strParseDate(startStr);
        Date nDate = DateUtils.strParseDate(nowStartStr);
        
        //获取有效时间范围内的分组
        dataList = managerDao.getGroupList(bean);
        
        //处理过去周没有人员分组，使用当前周，未来周没有分组复制当前周
        boolean state = true;
        if (dataList.size() == 0)
        {
            int compareDate = sDate.compareTo(nDate);
            if (compareDate < 0)
            {
                bean.setValidity_end(nowEndStr);
                bean.setValidity_start(nowStartStr);
                
                dataList = managerDao.getGroupList(bean);
                state = false;
            }
            else if (compareDate > 0)
            {
                //复制上一周分组信息
                personnelGroupService.copyGroup(nowStartStr, nowEndStr, startStr, endStr);
                
                dataList = managerDao.getGroupList(bean);
            }
            
        }
        
        //遍历分组，获取人员
        for (ScheduleBean data : dataList)
        {
            
            //获取每个分组中的人员信息列表
            List<GroupInfoBean> groupInfo = managerDao.getGroupInfoList(data);
            
            //本周排班信息
            ManagerBean mBean = null;
            //上周排班信息
            ManagerBean lastmBean = null;
            
            //遍历人员，获取排班数据信息
            for (GroupInfoBean infoBean : groupInfo)
            {
                //获取人员一周的排班信息
                mBean = managerDao.getManagerData(infoBean);
                
                String lastStart = selectWeekDay(startStr, "last");
                String lastEnd = selectWeekDay(endStr, "last");
                //获取上周排班信息
                lastmBean = managerDao.getLastManagerData(infoBean.getUser_id(), lastStart, lastEnd);
                
                //本周的排班数据
                List<WeekBean> week = new ArrayList<WeekBean>();
                //本周每天的安排的班次
                List<WeekDataBean> weekDate = new ArrayList<WeekDataBean>();
                //上周每天的安排的排次
                List<TempBean> lastWeekList = new ArrayList<TempBean>();
                
                //本周排班信息
                boolean mBeanFlag = true;
                Long id = 0L;
                if (mBean != null && state)
                {
                    id = mBean.getId();
                    mBean.setGroupName(infoBean.getGroupName());
                    
                    /* String tOwntime = mBean.getTotal_owetime();
                    if(tOwntime != null && tOwntime != "" ){
                        float tOwntimef = Float.parseFloat(tOwntime);
                        mBean.setTotal_owetime(subZeroAndDot(String.valueOf(tOwntimef)));
                    }
                    */
                }
                else
                {//没有本周排班信息，预设空的本周排班信息
                    mBeanFlag = false;
                    mBean = new ManagerBean();
                    mBean.setUser_id(infoBean.getUser_id());
                    mBean.setUser_name(infoBean.getUser_name());
                    mBean.setEndDate(endStr);
                    mBean.setStartDate(startStr);
                    mBean.setGroupId(infoBean.getGroupId());
                    mBean.setGroupName(infoBean.getGroupName());
                }
                //上周排班信息
                boolean lastmBeanFlag = true;
                Long lastId = 0L;
                if (lastmBean != null && state)
                {
                    lastId = lastmBean.getId();
                    lastmBean.setGroupName(infoBean.getGroupName());
                }
                else
                {//没有上周排班信息，预设空的上周排班信息
                    lastmBeanFlag = false;
                    lastmBean = new ManagerBean();
                    lastmBean.setUser_id(infoBean.getUser_id());
                    lastmBean.setUser_name(infoBean.getUser_name());
                    lastmBean.setEndDate(lastEnd);
                    lastmBean.setStartDate(lastStart);
                    lastmBean.setGroupId(infoBean.getGroupId());
                    lastmBean.setGroupName(infoBean.getGroupName());
                }
                
                //遍历一周日期
                for (TempBean dateTemp : dateList)
                {
                    
                    float workTotalTime = 0;
                    //获取每天的排班数据
                    if (mBeanFlag)
                    {
                        weekDate = managerDao.getWeekManagerData(id, dateTemp.getValue());
                        //获取每天的工作工时
                        workTotalTime = managerDao.getWeekManagerDataTime(id, dateTemp.getValue());
                    }
                    
                    WeekBean weekBean = new WeekBean();
                    weekBean.setWorkDate(dateTemp.getValue());
                    weekBean.setUser_id(mBean.getUser_id());
                    weekBean.setGroupId(mBean.getGroupId());
                    weekBean.setWeekDataBean(weekDate);
                    weekBean.setGroupName(mBean.getGroupName());
                    weekBean.setUser_name(mBean.getUser_name());
                    weekBean.setWorkTotalTime(workTotalTime);
                    
                    week.add(weekBean);
                    
                    //获取上周每天的排班数据
                    String lastDate = selectWeekDay(dateTemp.getValue(), "last");
                    
                    String lastWeekStr = "";
                    if (lastmBeanFlag)
                    {
                        lastWeekStr = managerDao.getLastWeekManagerData(lastId, lastDate);
                    }
                    
                    TempBean temp = new TempBean();
                    temp.setName(lastDate);
                    temp.setValue(lastWeekStr);
                    lastWeekList.add(temp);
                    
                } //遍历一周日期-end
                
                //存入一周每天的排班列表到人员排班数据列表中
                mBean.setWeekBeanList(week);
                //存入人员排班数据信息到人员信息列表中
                infoBean.setManagerBean(mBean);
                
                //存入上周每天的排班到上周人员排班数据列表中
                lastmBean.setLastWeekList(lastWeekList);
                //存入上周人员排班数据信息到人员信息列表中
                infoBean.setLastManagerBean(lastmBean);
                
            } //遍历人员-end
            
            //存入人员信息到分组列表中
            data.setGroupInfoList(groupInfo);
            
        } //遍历分组-end
        
        return dataList;
    }
    
    @Override
    public List<ScheduleBean> getAutoWeekList(List<TempBean> dateList)
        throws Exception
    {
        List<ScheduleBean> dataList = null;
        
        String startStr = "";
        String endStr = "";
        
        //获取开始和结束日期
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        ArrayList<Integer> resetDays = new ArrayList<Integer>();
        int dateSzie = dateList.size();
        //获取一周内是否存在节假日
        for (int l = 0; l < dateSzie; l++)
        {
            
            TempBean dateTBean = dateList.get(l);
            String dateDay = dateTBean.getValue();
            
            int isHoliday = managerDao.isHoliday(dateDay);
            if (isHoliday > 0)
            {
                dateTBean.setHoliday(true);
            }
            else
            {
                dateTBean.setHoliday(false);
            }
            resetDays.add(l);
        }
        
        ScheduleBean bean = new ScheduleBean();
        bean.setValidity_end(endStr);
        bean.setValidity_start(startStr);
        
        //获取有效时间范围内的分组
        dataList = managerDao.getGroupList(bean);
        
        //遍历分组，获取人员
        for (ScheduleBean data : dataList)
        {
            
            //获取每个分组中的人员信息列表
            List<GroupInfoBean> groupInfo = managerDao.getGroupInfoList(data);
            
            //本周排班信息
            ManagerBean mBean = null;
            //上周排班信息
            ManagerBean lastmBean = null;
            
            //获取分组指定班次
            ArrayList<WorkBean> groupWorkList = managerDao.getGroupWorkList(data.getGroupId());
            //获取工作类型的班次
            ArrayList<WorkBean> workList = managerDao.getWorkTypeList();
            
            //获取休息班次
            WorkBean resetWork = managerDao.getRestWork();
            if (resetWork == null)
            {
                throw new Exception("没有设定休息班次");
            }
            
            List<TempBean> tempList = new ArrayList<TempBean>();
            if (workList.size() != 0)
            {
                TempBean workBean = null;
                //获取班次指定次数   
                for (int n = 0; n < 7; n++)
                {
                    workBean = new TempBean();
                    workBean.setName(String.valueOf(n));
                    //如果分组没有指定班次，就使用所有工作类型的班次
                    if (groupWorkList.size() != 0)
                    {
                        ArrayList<WorkBean> cloneWork = cloneList(groupWorkList);
                        workBean.setWorkList(cloneWork);
                    }
                    else
                    {
                        ArrayList<WorkBean> cloneWork = cloneList(workList);
                        workBean.setWorkList(cloneWork);
                    }
                    
                    tempList.add(workBean);
                }
                
            }
            
            //获取节假日少的人
            int userSize = groupInfo.size();
            int userNum = (int)Math.round((double)userSize / 2);
            ArrayList<WorkBean> workArray = new ArrayList<WorkBean>();
            if (groupWorkList.size() != 0)
            {
                workArray = groupWorkList;
            }
            else
            {
                workArray = workList;
            }
            //如果班次次数少于节假日人员，则节假日人数变更为班次次数
            int totalC = 0;
            for (WorkBean beanA : workArray)
            {
                int countA = beanA.getCount();
                if (countA != -1)
                {
                    totalC = totalC + countA;
                }
                else
                {
                    totalC = -1;
                    break;
                }
            }
            
            if (totalC != -1)
            {
                boolean flag = userNum - totalC > 0 ? true : false;
                if (flag)
                {
                    userNum = totalC;
                }
            }
            
            List<Integer> userIDList = managerDao.getUserHolidayWorkCount(groupInfo, userNum);
            
            //遍历人员，获取排班数据信息
            for (GroupInfoBean infoBean : groupInfo)
            {
                
                //预设本周排班
                mBean = new ManagerBean();
                mBean.setUser_id(infoBean.getUser_id());
                mBean.setUser_name(infoBean.getUser_name());
                mBean.setEndDate(endStr);
                mBean.setStartDate(startStr);
                mBean.setGroupId(infoBean.getGroupId());
                mBean.setGroupName(infoBean.getGroupName());
                
                //本周的排班数据
                List<WeekBean> week = new ArrayList<WeekBean>();
                
                //获取上周排班数据 -start
                String lastStart = selectWeekDay(infoBean.getValidity_start(), "last");
                String lastEnd = selectWeekDay(infoBean.getValidity_end(), "last");
                
                //获取上周排班信息
                lastmBean = managerDao.getLastManagerData(infoBean.getUser_id(), lastStart, lastEnd);
                //上周每天的安排的排次
                List<TempBean> lastWeekList = new ArrayList<TempBean>();
                
                //上周排班信息
                boolean lastmBeanFlag = true;
                Long lastId = 0L;
                if (lastmBean != null)
                {
                    lastId = lastmBean.getId();
                    lastmBean.setGroupName(infoBean.getGroupName());
                }
                else
                {//没有上周排班信息
                    lastmBeanFlag = false;
                    lastmBean = new ManagerBean();
                    lastmBean.setUser_id(infoBean.getUser_id());
                    lastmBean.setUser_name(infoBean.getUser_name());
                    lastmBean.setEndDate(lastEnd);
                    lastmBean.setStartDate(lastStart);
                    lastmBean.setGroupId(infoBean.getGroupId());
                    lastmBean.setGroupName(infoBean.getGroupName());
                }
                //获取上周排班数据 -end
                
                //本周自动排班--start
                
                int resetDayL = resetDays.size();
                int resetDate = -1;
                Random r = new Random();
                if (resetDayL != 0)
                {
                    int resetNum = r.nextInt(resetDayL);
                    resetDate = resetDays.get(resetNum);
                }
                
                //休息计数
                //int restCount = 0;
                
                TempBean dateTemp = null;
                //遍历一周日期
                for (int i = 0; i < 7; i++)
                {
                    
                    //本周每天的安排的班次
                    List<WeekDataBean> weekDate = new ArrayList<WeekDataBean>();
                    
                    dateTemp = dateList.get(i);
                    
                    //获取上周排班数据 -start
                    
                    //获取上周每天的排班数据
                    String lastDate = selectWeekDay(dateTemp.getValue(), "last");
                    
                    String lastWeekStr = "";
                    if (lastmBeanFlag)
                    {
                        lastWeekStr = managerDao.getLastWeekManagerData(lastId, lastDate);
                    }
                    
                    TempBean temp = new TempBean();
                    temp.setName(lastDate);
                    temp.setValue(lastWeekStr);
                    lastWeekList.add(temp);
                    
                    //获取上周排班数据 -end
                    
                    //获取本周排班数据 -start
                    
                    String dayDate = dateTemp.getValue();
                    
                    boolean isHoliday = dateTemp.isHoliday();
                    
                    //是节假日,并且有班次
                    if (isHoliday && workList.size() != 0)
                    {
                        
                        int user_ID = infoBean.getUser_id();
                        //休息
                        if (resetDate == i)
                        {
                            weekDate = selectRest(dayDate, resetWork, i + 1);
                        }else{
                            
                            //人员节假日工作少则安排工作，不然则休息
                            if (userIDList.contains(user_ID))
                            {
                                weekDate = selectWork(dayDate, i + 1, tempList.get(i));
                                //如果没有班次安排则安排休息
                                if (weekDate.size() == 0)
                                {
                                    weekDate = selectRest(dayDate, resetWork, i + 1);
                                }
                            }
                            else
                            {
                                weekDate = selectRest(dayDate, resetWork, i + 1);
                            }
                            
                        }
                        
                    }
                    else if (!isHoliday && workList.size() != 0)
                    {//不是节假日，并且有班次
                        
                        //int random =  Math.random() > 0.5 ? 1 : 0;//1为工作，0为休息
                        
                        //休息
                        if (resetDate == i)
                        {
                            weekDate = selectRest(dayDate, resetWork, i + 1);
                        }
                        else
                        {//工作
                            
                            weekDate = selectWork(dayDate, i + 1, tempList.get(i));
                            //如果没有班次安排则安排休息
                            if (weekDate.size() == 0)
                            {
                                weekDate = selectRest(dayDate, resetWork, i + 1);
                            }
                        }
                        
                    }
                    else
                    {//没有班次
                        weekDate = selectRest(dayDate, resetWork, i + 1);
                    } //判断节假日 -end
                    
                    //获取每天的工作工时
                    float oldWorkTotalTime = 0;
                    ManagerBean oldMBean = managerDao.getManagerData(infoBean);
                    //获取每天的排班数据
                    if (oldMBean != null)
                    {
                        oldWorkTotalTime = managerDao.getWeekManagerDataTime(oldMBean.getId(), dateTemp.getValue());
                        
                        mBean.setTotal_owetime(oldMBean.getTotal_owetime());
                        mBean.setWeek_owetime(oldMBean.getWeek_owetime());
                        mBean.setTotal_time(oldMBean.getTotal_time());
                    }
                    
                    //设置班次数据
                    WeekBean weekBean = new WeekBean();
                    weekBean.setWorkDate(dateTemp.getValue());
                    weekBean.setUser_id(mBean.getUser_id());
                    weekBean.setGroupId(mBean.getGroupId());
                    weekBean.setWeekDataBean(weekDate);
                    weekBean.setGroupName(mBean.getGroupName());
                    weekBean.setUser_name(mBean.getUser_name());
                    weekBean.setWorkTotalTime(oldWorkTotalTime);
                    week.add(weekBean);
                    //获取本周排班数据 -end 
                    
                } //遍历一周日期 -end
                
                //存入一周每天的排班列表到人员排班数据列表中
                mBean.setWeekBeanList(week);
                //存入人员排班数据信息到人员信息列表中
                infoBean.setManagerBean(mBean);
                
                //存入上周每天的排班到上周人员排班数据列表中
                lastmBean.setLastWeekList(lastWeekList);
                //存入上周人员排班数据信息到人员信息列表中
                infoBean.setLastManagerBean(lastmBean);
                
            } //遍历人员 -end
            
            //存入人员信息到分组列表中
            data.setGroupInfoList(groupInfo);
            
        } //遍历分组 -end
        
        return dataList;
    }
    
    /**
     * 复制数组list
     * @param list
     * @return ArrayList
     */
    private ArrayList<WorkBean> cloneList(ArrayList<WorkBean> list)
        throws Exception
    {
        
        ArrayList<WorkBean> wBean = new ArrayList<WorkBean>();
        
        for (WorkBean b : list)
        {
            try
            {
                WorkBean bean = (WorkBean)b.deepClone();
                wBean.add(bean);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            
            /*bean.setCopyId(b.getCopyId());
            bean.setCount(b.getCount());
            bean.setCreater(b.getCreater());
            bean.setCreationTime(b.getCreationTime());
            bean.setDefineType(b.getDefineType());
            bean.setStatus(b.getStatus());
            bean.setTime_interval(b.getTime_interval());
            bean.setTotalTime(b.getTotalTime());
            bean.setWorkColour(b.getWorkColour());
            bean.setWorkId(b.getWorkId());
            bean.setWorkName(b.getWorkName());
            bean.setWorkStatus(b.getWorkStatus());
            bean.setWorkType(b.getWorkType());
            bean.setSplitWork(b.getSplitWork());*/
            
            
        }
        
        return wBean;
    }
    
    /**
     * 安排工作班次
     * @param dayDate
     * @param index
     * @param tempBean
     * @return List
     */
    private List<WeekDataBean> selectWork(String dayDate, int index, TempBean tempBean)
    {
        List<WeekDataBean> week = new ArrayList<WeekDataBean>();
        
        List<WorkBean> workList = tempBean.getWorkList();
        
        ArrayList<Integer> workArray = new ArrayList<Integer>();
        int length = workList.size();
        //遍历班次列表，去除次数为0的班次
        for (int n = 0; n < length; n++)
        {
            WorkBean bean = workList.get(n);
            
            int count = bean.getCount();
            if (count == 0)
            {
                workList.remove(n);
                n--;
                length--;
            }
            else
            {
                workArray.add(n);
            }
        }
        //如果没有可用班次，返回
        if (workList.size() == 0)
        {
            return week;
        }
        
        /*int num = workList.size();//获取可以使用的班次数量
        int time = 1;//可用班次大于2时，一天可以最多安排两个班次，不然只能安排一个班次
        if(num > 1){//随机判断是安排一个班次还是两个
            time = Math.random() > 0.5 ? 1 : 2;
        }
        
        if(oneDayFlag){
            time = 1 ;
        }*/
        //随机获取班次
        /* for(int i = 0 ; i < time ; i++){*/
        
        int listIndex = 0;
        int randomNum = 0;
        //随机选择班次
        if (workArray.size() != 1)
        {
            randomNum = (int)(Math.random() * workArray.size());
            listIndex = workArray.get(randomNum);
        }
        else
        {
            listIndex = workArray.get(randomNum);
        }
        
        //获取第几个班次
        WorkBean Bbean = workList.get(listIndex);
        
        WeekDataBean weekData = new WeekDataBean();
        weekData.setSort(1);
        weekData.setTotalTime(Bbean.getTotalTime());
        weekData.setWeekType(String.valueOf(index));
        weekData.setWorkColour(Bbean.getWorkColour());
        weekData.setWorkDate(dayDate);
        weekData.setWorkId(Bbean.getWorkId());
        weekData.setWorkName(Bbean.getWorkName());
        weekData.setWorkType(Bbean.getWorkType());
        week.add(weekData);
        
        //班次次数-1
        int workcount = Bbean.getCount();
        if (workcount != -1)
        {
            Bbean.setCount(workcount - 1);
        }
        /*//剔除已选择的班次
        if(workArray.size() != 1){
            
            workArray.remove(randomNum);
        }else{
            
            return week;
        }*/
        
        //}
        
        return week;
    }
    
    /**
     * 安排休息类型工作
     * @param dayDate
     * @param resetWork 
     * @param i 
     * @return List
     */
    private List<WeekDataBean> selectRest(String dayDate, WorkBean resetWork, int i)
    {
        
        List<WeekDataBean> week = new ArrayList<WeekDataBean>();
        
        WeekDataBean weekData = new WeekDataBean();
        
        weekData.setSort(1);
        weekData.setTotalTime(resetWork.getTotalTime());
        weekData.setWeekType(String.valueOf(i));
        weekData.setWorkColour(resetWork.getWorkColour());
        weekData.setWorkDate(dayDate);
        weekData.setWorkId(resetWork.getWorkId());
        weekData.setWorkName(resetWork.getWorkName());
        weekData.setWorkType(resetWork.getWorkType());
        
        week.add(weekData);
        
        return week;
    }
    
    /**
     * 
     * 获取上周日期
     * @param date
     * @return String
     * @throws ParseException
     */
    private String selectWeekDay(String date, String flag)
        throws ParseException
    {
        
        String weekDay = "";
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date wDate = DateUtils.strParseDate(date);
        
        Date weekDate = null;
        if ("last".equals(flag))
        {
            weekDate = new Date(wDate.getYear(), wDate.getMonth(), wDate.getDate() - 7);
        }
        else if ("next".equals(flag))
        {
            weekDate = new Date(wDate.getYear(), wDate.getMonth(), wDate.getDate() + 7);
        }
        
        weekDay = sf.format(weekDate);
        
        return weekDay;
        
    }
    
    @Override
    public List<WorkBean> getWorkList()
    {
        return managerDao.getWorkList();
    }
    
    @Override
    public void saveSchedule(ArrayList<ManagerBean> managerArray, HttpServletRequest request)
        throws Exception
    {
        
        try
        {
            //遍历人员排班数据
            for (ManagerBean manager : managerArray)
            {
                //保存人员排班信息
                managerDao.saveSchedule(manager);
                
                float change = manager.getChangeTotalOwnTime();
                int userId = manager.getUser_id();
                //String name = manager.getUser_name();
                String sDate = manager.getStartDate();
                //是否更改累计欠休
                if (change != 0)
                {
                    String nextDate = selectWeekDay(sDate, "next");
                    updateTotlalOwnTime(userId, nextDate, change);
                    //addLogSucc(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.排班管理, "人员"+name+"修改了累计欠休");
                }
                
                Long id = manager.getId();
                List<WeekDataBean> weekData = manager.getWeekDataBeanList();
                
                //遍历排班数据
                for (WeekDataBean week : weekData)
                {
                    week.setId(id);
                }
                
                //保存一周数据
                if (weekData.size() != 0)
                {
                    managerDao.saveScheduleWeekData(weekData);
                }
                
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
    }
    
    /**
     * 递归更新累计欠休
     * @param userId
     * @param startDate
     * @param change 
     * @throws ParseException 
     */
    private void updateTotlalOwnTime(int userId, String startDate, float change)
        throws ParseException
    {
        
        ManagerBean existManger = managerDao.existSheduleData(userId, startDate);
        
        if (existManger != null)
        {
            
            Double num = Double.parseDouble(existManger.getTotal_owetime());
            
            num = num + change;
            
            DecimalFormat df = new DecimalFormat("0.00");
            
            String numStr = String.valueOf(df.format(num));
            
            managerDao.updateTotlalOwnTime(userId, startDate, DateUtils.subZeroAndDot(numStr));
            
            String nextDate = selectWeekDay(startDate, "next");
            
            updateTotlalOwnTime(userId, nextDate, change);
            
        }
        
        return;
        
    }
    
    @Override
    public void deleteScheduleData(String startDate, String endDate)
    {
        managerDao.deleteScheduleData(startDate, endDate);
        
    }
    
    @Override
    public void deleteScheduleDataWeek(String startDate, String endDate)
    {
        managerDao.deleteScheduleDataWeek(startDate, endDate);
        
    }
    
    @Override
    public void updateHolidayWork(ArrayList<TempBean> changeArray, List<TempBean> dateList, HttpServletRequest request)
        throws Exception
    {
        
        try
        {
            int dateSzie = dateList.size();
            //获取一周内是否存在节假日
            for (int l = 0; l < dateSzie; l++)
            {
                
                TempBean dateTBean = dateList.get(l);
                String dateDay = dateTBean.getValue();
                
                int isHoliday = managerDao.isHoliday(dateDay);
                if (isHoliday > 0)
                {
                    dateTBean.setHoliday(true);
                }
                else
                {
                    dateTBean.setHoliday(false);
                }
                
            }
            
            //遍历人员排班数据
            for (TempBean temp : changeArray)
            {
                
                List<TempBean> changeList = temp.getChangeList();
                int id = temp.getId();
                String name = temp.getName();
                
                int length = changeList.size();
                for (int i = 0; i < length; i++)
                {
                    String flag = changeList.get(i).getChange();
                    String date = changeList.get(i).getDate();
                    
                    boolean isHoliday = dateList.get(i).isHoliday();
                    if (isHoliday)
                    {
                        //节假日工作是否有该人
                        int userExist = managerDao.getUserHolidayWorkTimes(id);
                        
                        if (userExist == 0)
                        {
                            managerDao.addUserHolidayWorkTime(id, name, date);
                        }
                        
                        if ("add".equals(flag))
                        {
                            managerDao.updateHolidayWork(id, "add", date);
                        }
                        else if ("sub".equals(flag))
                        {
                            managerDao.updateHolidayWork(id, "sub", date);
                        }
                        
                    }
                    
                }
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //addLogFail(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.排班管理, "更新节假日次数失败");
            throw e;
        }
        
    }
    
    @Override
    public List<ScheduleBean> getDayList(List<TempBean> dateList, String dayDate)
        throws Exception
    {
        
        List<ScheduleBean> dayList = null;
        
        String startStr = "";
        String endStr = "";
        
        String nowStartStr = "";
        String nowEndStr = "";
        
        //获取当前周开始和结束日期
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        //获取本周日期
        Date now = new Date();
        List<TempBean> nowDateList = ManagerController.getweekDate(now);
        //获取开始和结束日期
        if (nowDateList != null && nowDateList.size() == 7)
        {
            TempBean start = nowDateList.get(0);
            TempBean end = nowDateList.get(6);
            
            nowStartStr = start.getValue();
            nowEndStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        Date sDate = DateUtils.strParseDate(startStr);
        Date nDate = DateUtils.strParseDate(nowStartStr);
        
        ScheduleBean bean = new ScheduleBean();
        bean.setValidity_end(endStr);
        bean.setValidity_start(startStr);
        
        //获取有效时间范围内的分组
        dayList = managerDao.getGroupList(bean);
        
        //处理过去周没有人员分组，使用当前周，未来周没有分组复制当前周
        boolean state = true;
        if (dayList.size() == 0)
        {
            
            int compareDate = sDate.compareTo(nDate);
            
            if (compareDate != 0)
            {
                bean.setValidity_end(nowEndStr);
                bean.setValidity_start(nowStartStr);
                
                dayList = managerDao.getGroupList(bean);
                state = false;
            }
            
        }
        
        //本周排班信息
        ManagerBean mBean = null;
        
        //遍历分组，获取人员
        for (ScheduleBean dayData : dayList)
        {
            //获取每个分组中的人员信息列表
            List<GroupInfoBean> groupInfo = managerDao.getGroupInfoList(dayData);
            
            //遍历人员，获取排班数据信息
            for (GroupInfoBean infoBean : groupInfo)
            {
                //获取人员一周的排班信息
                mBean = managerDao.getManagerData(infoBean);
                
                //本周排班信息
                boolean mBeanFlag = true;
                Long id = 0L;
                if (mBean != null && state)
                {
                    id = mBean.getId();
                    mBean.setGroupName(infoBean.getGroupName());
                }
                else
                {//没有本周排班信息，预设空的本周排班信息
                    mBeanFlag = false;
                    mBean = new ManagerBean();
                    mBean.setUser_id(infoBean.getUser_id());
                    mBean.setUser_name(infoBean.getUser_name());
                    mBean.setEndDate(endStr);
                    mBean.setStartDate(startStr);
                    mBean.setGroupId(infoBean.getGroupId());
                    mBean.setGroupName(infoBean.getGroupName());
                }
                
                List<WorkBean> dayWorkListTemp = null;
                
                List<WorkBean> dayWorkList = new ArrayList<WorkBean>();
                
                if (mBeanFlag)
                {
                    dayWorkListTemp = managerDao.getDayWorkList(id, dayDate);
                    
                    //两头班处理，如是两头班则则分成两条数据
                    for (WorkBean work : dayWorkListTemp)
                    {
                        String splitWork = work.getSplitWork();
                        dayWorkList.add(work);
                        if ("1".equals(splitWork))
                        {
                            WorkBean newWork = cloneWork(work);
                            dayWorkList.add(newWork);
                        }
                    }
                    //更新日视图图片方位
                    for (WorkBean work : dayWorkList)
                    {
                        updateWorkPosition(work);
                    }
                    
                }
                
                //存入一天的排班列表到人员排班数据列表中
                mBean.setDayWorkList(dayWorkList);
                //存入人员排班数据信息到人员信息列表中
                infoBean.setManagerBean(mBean);
                
            } //遍历人员end
            
            //存入人员信息到分组列表中
            dayData.setGroupInfoList(groupInfo);
            
        } //遍历分组end
        
        return dayList;
    }
    
    /**
     * cloneWork
     * @param work
     * @return WorkBean
     */
    private WorkBean cloneWork(WorkBean work)
        throws Exception
    {
        
        WorkBean bean = new WorkBean();
        
        try
        {
            bean = (WorkBean)work.deepClone();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        bean.setSplitWork("0");
        
        String[] timeArry = work.getTime_interval().split(",");
        if (timeArry.length != 2)
        {
            throw new Exception();
        }
        
        bean.setTime_interval(timeArry[1]);
        work.setTime_interval(timeArry[0]);
        
        return bean;
    }
    
    /**
     * 更新日视图图片方位
     * @param work
     */
    private void updateWorkPosition(WorkBean work)
        throws Exception
    {
        
        String time = work.getTime_interval();
        String[] timeArr = time.trim().split("-");
        
        double num = 4.2;
        
        String[] sNumArr = timeArr[0].trim().split(":");
        
        int sHour = Integer.parseInt(sNumArr[0].trim());
        int sMinute = Integer.parseInt(sNumArr[1].trim());
        double start = 0;
        if (sMinute == 30)
        {
            start = sHour * num + 0.5 * num;
        }
        else if (sMinute == 0)
        {
            start = sHour * num;
        }
        
        String left = String.valueOf(start);
        work.setLeft(left);
        
        String[] eNumArr = timeArr[1].trim().split(":");
        int eHour = Integer.parseInt(eNumArr[0].trim());
        int eMinute = Integer.parseInt(eNumArr[1].trim());
        double end = 0;
        if (eMinute == 30)
        {
            end = eHour * num + 0.5 * num;
        }
        else if (eMinute == 0)
        {
            end = eHour * num;
        }
        
        double widthD = 0;
        if (start <= end)
        {
            widthD = end - start;
        }
        else
        {
            widthD = 100 - start;
        }
        
        String width = String.valueOf(widthD);
        work.setWidth(width);
        
    }
    
    @Override
    public void excelExportWeek(String date, HttpServletResponse response, String filePath)
        throws Exception
    {
        
        String title = "一周排班数据";
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        HSSFSheet sheet = workbook.createSheet(title);
        
        sheet.setDefaultRowHeightInPoints(15);
        //列宽
        sheet.setDefaultColumnWidth(12);
        
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
        cell.setCellValue("基本信息");
        cell.setCellStyle(style);
        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        
        Date weekDate = DateUtils.strParseDate(date);
        List<TempBean> dateList = ManagerController.getweekDate(weekDate);
        
        for (int i = 0; i < dateList.size(); i++)
        {
            TempBean temp = dateList.get(i);
            
            cell = row.createCell(2 + i);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(temp.getValue());
            cell.setCellStyle(style);
        }
        
        cell = row.createCell(9);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("欠修（小时）");
        cell.setCellStyle(style);
        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
        
        row = sheet.createRow(1);
        
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        
        cell = row.createCell(1);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("岗位");
        cell.setCellStyle(style);
        
        for (int i = 0; i < 7; i++)
        {
            
            cell = row.createCell(2 + i);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            
            switch (i)
            {
                case 0:
                    cell.setCellValue("星期一");
                    break;
                case 1:
                    cell.setCellValue("星期二");
                    break;
                case 2:
                    cell.setCellValue("星期三");
                    break;
                case 3:
                    cell.setCellValue("星期四");
                    break;
                case 4:
                    cell.setCellValue("星期五");
                    break;
                case 5:
                    cell.setCellValue("星期六");
                    break;
                case 6:
                    cell.setCellValue("星期日");
                    break;
                default:
                    break;
                    
            }
            
            cell.setCellStyle(style);
        }
        
        cell = row.createCell(9);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("本周");
        cell.setCellStyle(style);
        
        cell = row.createCell(10);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("累计");
        cell.setCellStyle(style);
        
        
        List<ScheduleBean> dataList = new ArrayList<ScheduleBean>();
        
        String startStr = "";
        String endStr = "";
        
        String nowStartStr = "";
        String nowEndStr = "";
        
        //获取当前周开始和结束日期
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        
        //获取本周日期
        Date now = new Date();
        List<TempBean> nowDateList = ManagerController.getweekDate(now);
        //获取开始和结束日期
        if (nowDateList != null && nowDateList.size() == 7)
        {
            TempBean start = nowDateList.get(0);
            TempBean end = nowDateList.get(6);
            
            nowStartStr = start.getValue();
            nowEndStr = end.getValue();
        }
        
        ScheduleBean bean = new ScheduleBean();
        bean.setValidity_end(endStr);
        bean.setValidity_start(startStr);
        
        Date sDate = DateUtils.strParseDate(startStr);
        Date nDate = DateUtils.strParseDate(nowStartStr);
        
        //获取有效时间范围内的分组
        dataList = managerDao.getGroupList(bean);
        
        //处理过去周没有人员分组，使用当前周，未来周没有分组复制当前周
        boolean state = true;
        if (dataList.size() == 0)
        {
            
            int compareDate = sDate.compareTo(nDate);
            
            if (compareDate != 0)
            {
                bean.setValidity_end(nowEndStr);
                bean.setValidity_start(nowStartStr);
                
                dataList = managerDao.getGroupList(bean);
                state = false;
            }
            
        }
        
        
        int rowNum = 1 ;
        //遍历分组，获取人员
        for (ScheduleBean data : dataList)
        {
            
            rowNum ++;
            row = sheet.createRow(rowNum);
            
            cell = row.createCell(0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(data.getGroupName());
            cell.setCellStyle(style);
            
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 10));
            
            //获取每个分组中的人员信息列表
            List<GroupInfoBean> groupInfo = managerDao.getGroupInfoList(data);
            
            //本周排班信息
            ManagerBean mBean = new ManagerBean();
            
            //遍历人员，获取排班数据信息
            for (GroupInfoBean infoBean : groupInfo)
            {
                
                //获取人员一周的排班信息
                mBean = managerDao.getManagerData(infoBean);
                
                //本周排班信息
                boolean mBeanFlag = true;
                Long id = 0L;
                if (mBean != null && state)
                {
                    id = mBean.getId();
                }
                else
                {
                    mBeanFlag = false;
                    mBean = new ManagerBean();
                }
                
                
                rowNum ++;
                row = sheet.createRow(rowNum);
                
                cell = row.createCell(0);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(infoBean.getUser_name()==null?"":infoBean.getUser_name());
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(mBean.getPostName()==null?"":mBean.getPostName());
                cell.setCellStyle(style);
                
                
                int cellNum = 1;
                
                List<WorkBean> dayWorkList = new ArrayList<WorkBean>();
                //遍历一周日期
                for (TempBean dateTemp : dateList)
                {
                    
                    cellNum ++ ;
                    //获取每天的排班数据
                    if (mBeanFlag)
                    {
                        dayWorkList = managerDao.getDayWorkList(id, dateTemp.getValue());
                    }
                    
                    StringBuffer workStr = new StringBuffer();
                    //处理每天班次
                    int dayLength = dayWorkList.size();
                    for (int i = 0 ; i < dayLength; i ++ )
                    {
                        
                        WorkBean work = dayWorkList.get(i);
                        
                        workStr.append(work.getWorkName());
                        
                        if(i < dayLength - 1){
                            workStr.append(",");
                        }
                        
                    }
                    
                    cell = row.createCell(cellNum);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(workStr.toString());
                    cell.setCellStyle(style);
                    
                } //遍历一周日期-end
                
                
                cell = row.createCell(9);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(mBean.getWeek_owetime()==null?"":mBean.getWeek_owetime());
                cell.setCellStyle(style);
                
                cell = row.createCell(10);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(mBean.getTotal_owetime()==null?"":mBean.getTotal_owetime());
                cell.setCellStyle(style);
                
            } //遍历人员-end
            
        } //遍历分组-end
        
        
        
        rowNum = rowNum + 2;
        
        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("备注：");
        cell.setCellStyle(style);
        
        rowNum ++;
        
        HSSFCellStyle remarkStyle = workbook.createCellStyle();
        HSSFFont remarkfont = workbook.createFont();
        remarkfont.setFontName("宋体");
        remarkfont.setFontHeightInPoints((short)12);//字体
        remarkStyle.setFont(font);
        remarkStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        remarkStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        remarkStyle.setWrapText(true);
        
        row = sheet.createRow(rowNum);
        String remark = managerDao.getRemarkStr(startStr, endStr);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(remark==null?"":StringEscapeUtils.unescapeHtml(remark).replaceAll("<br />", "\r\n"));
        cell.setCellStyle(remarkStyle);
        
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, 0, 10));
        
        
        FileOutputStream fileOut = null;
        try
        {
            
            fileOut = new FileOutputStream(filePath);
            
            workbook.write(fileOut);
            //workbook.close();
            
            fileOut.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            IOUtils.closeQuietly(fileOut);
        }
        
    }

     
    @Override
    public void excelExportDay(String date, HttpServletResponse response, String filePath)
        throws Exception
    {
        
        
        String title = "日排班数据";
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        HSSFSheet sheet = workbook.createSheet(title);
        
        sheet.setDefaultRowHeightInPoints(15);
        //列宽
        sheet.setDefaultColumnWidth(12);
        
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
        cell.setCellValue("基本信息");
        cell.setCellStyle(style);
        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        
        cell = row.createCell(2);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(date);
        cell.setCellStyle(style);
        
        row = sheet.createRow(1);
        
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        
        cell = row.createCell(1);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("岗位");
        cell.setCellStyle(style);
        
        
        String dayWeek = DateUtils.whichWeek(date);
        
        cell = row.createCell(2);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(dayWeek);
        cell.setCellStyle(style);
        
        Date weekDate = DateUtils.strParseDate(date);
        List<TempBean> dateList = ManagerController.getweekDate(weekDate);
        
        List<ScheduleBean> dayList = null;
        String startStr = "";
        String endStr = "";
        
        String nowStartStr = "";
        String nowEndStr = "";
        
        //获取当前周开始和结束日期
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        
        //获取本周日期
        Date now = new Date();
        List<TempBean> nowDateList = ManagerController.getweekDate(now);
        //获取开始和结束日期
        if (nowDateList != null && nowDateList.size() == 7)
        {
            TempBean start = nowDateList.get(0);
            TempBean end = nowDateList.get(6);
            
            nowStartStr = start.getValue();
            nowEndStr = end.getValue();
        }
        
        Date sDate = DateUtils.strParseDate(startStr);
        Date nDate = DateUtils.strParseDate(nowStartStr);
        
        ScheduleBean bean = new ScheduleBean();
        bean.setValidity_end(endStr);
        bean.setValidity_start(startStr);
        
        //获取有效时间范围内的分组
        dayList = managerDao.getGroupList(bean);
        
        //处理过去周没有人员分组，使用当前周，未来周没有分组复制当前周
        boolean state = true;
        if (dayList.size() == 0)
        {
            
            int compareDate = sDate.compareTo(nDate);
            
            if (compareDate != 0)
            {
                bean.setValidity_end(nowEndStr);
                bean.setValidity_start(nowStartStr);
                
                dayList = managerDao.getGroupList(bean);
                state = false;
            }
            
        }
        
        //本周排班信息
        ManagerBean mBean = null;
        
        int rowNum = 1 ;
        //遍历分组，获取人员
        for (ScheduleBean dayData : dayList)
        {
            rowNum ++;
            row = sheet.createRow(rowNum);
            
            cell = row.createCell(0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(dayData.getGroupName());
            cell.setCellStyle(style);
            
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
            
            //获取每个分组中的人员信息列表
            List<GroupInfoBean> groupInfo = managerDao.getGroupInfoList(dayData);
            
            //遍历人员，获取排班数据信息
            for (GroupInfoBean infoBean : groupInfo)
            {
                
                //获取人员一周的排班信息
                mBean = managerDao.getManagerData(infoBean);
                
                //本周排班信息
                boolean mBeanFlag = true;
                Long id = 0L;
                if (mBean != null && state)
                {
                    id = mBean.getId();
                }
                else
                {//没有本周排班信息，预设空的本周排班信息
                    mBeanFlag = false;
                    mBean = new ManagerBean();
                }
                
                rowNum ++;
                row = sheet.createRow(rowNum);
                
                cell = row.createCell(0);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(infoBean.getUser_name()==null?"":infoBean.getUser_name());
                cell.setCellStyle(style);
                
                cell = row.createCell(1);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(mBean.getPostName()==null?"":mBean.getPostName());
                cell.setCellStyle(style);
                
                
                int cellNum = 2;
                
                List<WorkBean> dayWorkList = null;
                
                if (mBeanFlag)
                {
                    dayWorkList = managerDao.getDayWorkList(id, date);
                    
                    StringBuffer workStr = new StringBuffer();
                    //处理每天班次
                    int dayLength = dayWorkList.size();
                    for (int i = 0 ; i < dayLength; i ++ )
                    {
                        
                        WorkBean work = dayWorkList.get(i);
                        
                        workStr.append(work.getWorkName());
                        
                        if(i < dayLength - 1){
                            workStr.append(",");
                        }
                        
                    }
                    
                    cell = row.createCell(cellNum);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(workStr.toString());
                    cell.setCellStyle(style);
                    
                }
                
            } //遍历人员end
            
            
        } //遍历分组end
        
        
        rowNum = rowNum + 2;
        
        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("备注：");
        cell.setCellStyle(style);
        
        rowNum ++;
        
        HSSFCellStyle remarkStyle = workbook.createCellStyle();
        HSSFFont remarkfont = workbook.createFont();
        remarkfont.setFontName("宋体");
        remarkfont.setFontHeightInPoints((short)12);//字体
        remarkStyle.setFont(font);
        remarkStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        remarkStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        remarkStyle.setWrapText(true);
        
        row = sheet.createRow(rowNum);
        String remark = managerDao.getRemarkStr(startStr, endStr);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(remark==null?"":StringEscapeUtils.unescapeHtml(remark).replaceAll("<br />", "\r\n"));
        cell.setCellStyle(remarkStyle);
        
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, 0, 5));
        
        FileOutputStream fileOut = null;
        try
        {
            
            fileOut = new FileOutputStream(filePath);
            
            workbook.write(fileOut);
            //workbook.close();
            
            fileOut.flush();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            IOUtils.closeQuietly(fileOut);
        }
        
    }

     
    @Override
    public void excelExportMonth(String date, HttpServletResponse response, String filePath) throws Exception
    {
        
        String title = "月排班数据";
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        HSSFSheet sheet = workbook.createSheet(title);
        
        sheet.setDefaultRowHeightInPoints(15);
        //列宽
        sheet.setDefaultColumnWidth(12);
        
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
        cell.setCellValue("基本信息");
        cell.setCellStyle(style);
        
        
        HSSFRow rowTwo = sheet.createRow(1);
        HSSFCell cellTwo = rowTwo.createCell(0);
        cellTwo.setCellType(HSSFCell.CELL_TYPE_STRING);
        cellTwo.setCellValue("姓名");
        cellTwo.setCellStyle(style);
        
        
        ArrayList<TempBean> monthList = DateUtils.getMonthDate(date);
        int dateSize = monthList.size();
        for(int i  = 0 ; i < dateSize; i ++ ){
            
            TempBean temp = monthList.get(i);
            String dateStr = temp.getValue();
            
            cell = row.createCell(1+i);
            
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(dateStr);
            cell.setCellStyle(style);
            
            cellTwo = rowTwo.createCell(1+i);
            cellTwo.setCellType(HSSFCell.CELL_TYPE_STRING);

            String whichWeekStr = DateUtils.whichWeek(dateStr);
            cellTwo.setCellValue(whichWeekStr);
            cellTwo.setCellStyle(style);
            
        }
        
        int rowNum = 1 ;
        List<UserMonthBean> userList = managerDao.getUser();
        
        for (UserMonthBean infoBean : userList)
        {
            
            rowNum ++;
            row = sheet.createRow(rowNum);
            
            cell = row.createCell(0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(infoBean.getUser_name()==null?"":infoBean.getUser_name());
            cell.setCellStyle(style);
            
            int cellNum = 0;
            
            List<WorkBean> dayWorkList = null;
            
            int user_id = Integer.parseInt(infoBean.getUser_id());
            
            //遍历一月日期
            for (TempBean dateTemp : monthList)
            {
                
                cellNum ++ ;
                
                //获取每天的排班数据
                dayWorkList = managerDao.getDayWorkListByUser( user_id, dateTemp.getValue());
                
                StringBuffer workStr = new StringBuffer();
                //处理每天班次
                int dayLength = dayWorkList.size();
                for (int i = 0 ; i < dayLength; i ++ )
                {
                    
                    WorkBean work = dayWorkList.get(i);
                    
                    workStr.append(work.getWorkName());
                    
                    if(i < dayLength - 1){
                        workStr.append(",");
                    }
                    
                }
                
                cell = row.createCell(cellNum);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(workStr.toString());
                cell.setCellStyle(style);
                
            } //遍历一月日期-end
           
        } //遍历人员end
              
        
        FileOutputStream fileOut = null;
        try
        {
            
            fileOut = new FileOutputStream(filePath);
            
            workbook.write(fileOut);
           // workbook.close();
            fileOut.flush();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            IOUtils.closeQuietly(fileOut);
        }
        
    }
     
    @Override
    public List<ScheduleBean> getPrintWeek(List<TempBean> dateList)
        throws Exception
    {
        List<ScheduleBean> dataList = null;
        
        String startStr = "";
        String endStr = "";
        
        String nowStartStr = "";
        String nowEndStr = "";
        
        //获取当前周开始和结束日期
        if (dateList != null && dateList.size() == 7)
        {
            TempBean start = dateList.get(0);
            TempBean end = dateList.get(6);
            
            startStr = start.getValue();
            endStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        //获取本周日期
        Date now = new Date();
        List<TempBean> nowDateList = ManagerController.getweekDate(now);
        //获取开始和结束日期
        if (nowDateList != null && nowDateList.size() == 7)
        {
            TempBean start = nowDateList.get(0);
            TempBean end = nowDateList.get(6);
            
            nowStartStr = start.getValue();
            nowEndStr = end.getValue();
        }
        else
        {
            throw new Exception();
        }
        
        ScheduleBean bean = new ScheduleBean();
        bean.setValidity_end(endStr);
        bean.setValidity_start(startStr);
        
        Date sDate = DateUtils.strParseDate(startStr);
        Date nDate = DateUtils.strParseDate(nowStartStr);
        
        //获取有效时间范围内的分组
        dataList = managerDao.getGroupList(bean);
        
        //处理过去周没有人员分组，使用当前周，未来周没有分组复制当前周
        boolean state = true;
        if (dataList.size() == 0)
        {
            int compareDate = sDate.compareTo(nDate);
            if (compareDate != 0)
            {
                bean.setValidity_end(nowEndStr);
                bean.setValidity_start(nowStartStr);
                
                dataList = managerDao.getGroupList(bean);
                state = false;
            }
            
        }
        
        //遍历分组，获取人员
        for (ScheduleBean data : dataList)
        {
            
            //获取每个分组中的人员信息列表
            List<GroupInfoBean> groupInfo = managerDao.getGroupInfoList(data);
            
            //本周排班信息
            ManagerBean mBean = null;
            
            //遍历人员，获取排班数据信息
            for (GroupInfoBean infoBean : groupInfo)
            {
                //获取人员一周的排班信息
                mBean = managerDao.getManagerData(infoBean);
                
                //本周的排班数据
                List<WeekBean> week = new ArrayList<WeekBean>();
                //本周每天的安排的班次
                List<WeekDataBean> weekDate = new ArrayList<WeekDataBean>();
                
                //本周排班信息
                boolean mBeanFlag = true;
                Long id = 0L;
                if (mBean != null && state)
                {
                    id = mBean.getId();
                    mBean.setGroupName(infoBean.getGroupName());
                }
                else
                {//没有本周排班信息，预设空的本周排班信息
                    mBeanFlag = false;
                    mBean = new ManagerBean();
                    mBean.setUser_id(infoBean.getUser_id());
                    mBean.setUser_name(infoBean.getUser_name());
                    mBean.setEndDate(endStr);
                    mBean.setStartDate(startStr);
                    mBean.setGroupId(infoBean.getGroupId());
                    mBean.setGroupName(infoBean.getGroupName());
                }
                
                //遍历一周日期
                for (TempBean dateTemp : dateList)
                {
                    
                    //获取每天的排班数据
                    if (mBeanFlag)
                    {
                        weekDate = managerDao.getWeekManagerData(id, dateTemp.getValue());
                    }
                    
                    WeekBean weekBean = new WeekBean();
                    weekBean.setWorkDate(dateTemp.getValue());
                    weekBean.setUser_id(mBean.getUser_id());
                    weekBean.setGroupId(mBean.getGroupId());
                    weekBean.setWeekDataBean(weekDate);
                    weekBean.setGroupName(mBean.getGroupName());
                    weekBean.setUser_name(mBean.getUser_name());
                    
                    week.add(weekBean);
                    
                } //遍历一周日期-end
                
                //存入一周每天的排班列表到人员排班数据列表中
                mBean.setWeekBeanList(week);
                //存入人员排班数据信息到人员信息列表中
                infoBean.setManagerBean(mBean);
                
            } //遍历人员-end
            
            //存入人员信息到分组列表中
            data.setGroupInfoList(groupInfo);
            
        } //遍历分组-end
        
        return dataList;
    }

     
    @Override
    public List<UserMonthBean> getPrintMonth(List<TempBean> monthDateList)
        throws Exception
    {
        
        List<UserMonthBean> userList = managerDao.getUser();
        
        //遍历人员
        for (UserMonthBean user : userList)
        {
            
            //一月排班数据
            List<MonthWorkBean> monthWorkList = new ArrayList<MonthWorkBean>();
            
            int user_id = Integer.parseInt(user.getUser_id());
            
            //遍历一月日期
            for (TempBean dateTemp : monthDateList)
            {
                //获取每天的排班数据
                List<WeekDataBean> workList = managerDao.getWeekManagerDataByUser(user_id, dateTemp.getValue());
                
                MonthWorkBean month = new MonthWorkBean();
                month.setWorkDate(dateTemp.getValue());
                month.setWorkList(workList);
                
                monthWorkList.add(month);
            } //遍历一月日期-end
            
            
            //存入一月排班数据到人员列表中
            user.setMonthWorkList(monthWorkList);
                
        } //遍历人员-end
        
        
        return userList;
    }

     
}
