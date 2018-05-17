package com.zc.schedule.product.personnel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zc.schedule.common.base.BaseLogService;
import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SpringUtil;
import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.manager.dao.ManagerDao;
import com.zc.schedule.product.manager.entity.GroupInfoBean;
import com.zc.schedule.product.manager.entity.ManagerBean;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.manager.entity.TempBean;
import com.zc.schedule.product.personnel.dao.PersonnelDao;
import com.zc.schedule.product.personnel.entity.Personnel;
import com.zc.schedule.product.personnel.service.PersonnelService;

@Service
public class PersonnelServiceImpl extends BaseLogService implements PersonnelService
{
    
    /**
     * 人员管理数据交互接口
     */
    @Resource
    private PersonnelDao personnelDao;
    
    @Resource(name = "managerDao")
    private ManagerDao managerDao;
    
    @Override
    public List<Personnel> getPersonnels(Map<String, Object> params, PageInfo page)
    {
        return personnelDao.getPersonnels(params, page);
    }
    
    @Override
    public ResultData save(Personnel personnel)
    {
        if ("add".equals(personnel.getOpr()))//新增人员
        {
            //查询相同电话的用户
            List<Personnel> personnels = personnelDao.getByTellphone(personnel.getTellphone());
            if (personnels != null && personnels.size() > 0)
            {
                return DataFormat.formatAdd(-1, "电话已存在");
            }
            
            personnel.setCreater(getSessionUserId());
            personnelDao.save(personnel);
        }
        else //更新人员
        {
            //查询相同电话的用户
            List<Personnel> personnels = personnelDao.getByTellphone(personnel.getTellphone());
            if (!personnelDao.getById(personnel.getUserId()).getTellphone().equals(personnel.getTellphone())
                && personnels != null && personnels.size() > 0)
            {
                return DataFormat.formatUpd(-1, "电话已存在");
            }
            
            personnelDao.update(personnel);
        }
        return DataFormat.formatAdd(1, "保存成功");
    }
    
    @Override
    public ResultData getById(Long userId)
    {
        Personnel personnel = personnelDao.getById(userId);
        if (personnel == null)
        {
            return DataFormat.formatFail("用户不存在！", null);
        }
        else
        {
            return DataFormat.formatSucc(personnel);
        }
    }
    
    @Override
    public String del(String userIds)
    {
        //记录标志值
        StringBuffer strFlag = new StringBuffer();
        
        StringBuffer userNames = new StringBuffer(128);
        Personnel personnel = null;
        
        for (String userId : userIds.split(","))
        {
            
            personnel = personnelDao.getById(Long.valueOf(userId));
            userNames.append(personnel.getUserName()).append(",");
            
            //删除相应的关联数据
            String flag = delRelData(Long.valueOf(userId));
            
            if(flag.equals("true")){
                strFlag.append("true");
            }else{
                strFlag.append("false");
            }
            
            //修改用户软删除
            int mark = personnelDao.delById(userId);
            if (mark > 0) 
            {
                //update成功
                strFlag.append("true");
            }
            else
            {
                strFlag.append("false");
            }
        }
        
        addLogSucc(SpringUtil.getRequest(),
            SysConstant.SCHEDULEMGR,
            SysConstant.人员信息,
            "删除人员[姓名=" + userNames.toString().substring(0, userNames.length() - 1) + "]");
        
        //若存在false，则false，无则true
        String flag;
        if ((strFlag.toString()).indexOf("false") != -1) 
        {
            flag = "false";
        }
        else 
        {
            flag = "true";
        }
        return flag;
//        return DataFormat.formatDel(1, "删除成功！");
    }
    
    /**
     * 删除相应的关联数据
     * 
     * @param userId
     */
    private String delRelData(Long userId)
    {
        
        /**更改累计时长*/
        //1.获得当前周和上周的周一和周日的日期数值
        //2.获得当前人员在当前周的一周总信息数据
        //3.获得当前人员在当天到周日的一周详细信息数据，根据班次类型获得班次的总时间
        //4.修改sche_record表中当前人的本周、累计欠休，总时间三个字段值
        
        //标志update和delete删除是否成功
        boolean flag = true;
        StringBuffer smsMess = new StringBuffer();
        
        //定义本周的周一和周日的日期值
        String nowStartStr = "";
        String nowEndStr = "";
        
        //获取本周的所有日期
        Date now = new Date();
        List<TempBean> nowDateList = getweekDate(now);
        
        //获取本周一周至当天的所有日期
        List<TempBean> notWeekDateList = getNotWeekDate(now);
        
        //获取开始和结束日期
        try
        {
            if (nowDateList != null && nowDateList.size() == 7)
            {
                TempBean start = nowDateList.get(0);
                TempBean end = nowDateList.get(6);
                
                nowStartStr = start.getValue();
                nowEndStr = end.getValue();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            flag = false;
        }
        
        //本周排班信息
        ManagerBean mBean = null;
        //上周排班信息
        ManagerBean lastmBean = null;
        
        GroupInfoBean infoBean = new GroupInfoBean();
        //获取分组
        ScheduleBean bean = personnelDao.getGroupIdByUserId(userId, nowStartStr, nowEndStr);
        
        if (bean != null) 
        {
            infoBean.setGroupId(bean.getGroupId());
            infoBean.setUser_id(Integer.valueOf(userId.toString()));
            infoBean.setValidity_start(nowStartStr);
            infoBean.setValidity_end(nowEndStr);
            
            //获取人员一周的排班信息
            mBean = managerDao.getManagerData(infoBean);
            
            //获取上周的周一和周日的日期值
            String lastStart = selectWeekDay(nowStartStr, "last");
            String lastEnd = selectWeekDay(nowEndStr, "last");
            
            //获取上周排班信息
            lastmBean = managerDao.getLastManagerData(infoBean.getUser_id(), lastStart, lastEnd);
            
            //定义字符串 设置默认值，默认不存在上周排班总数据
            Float lastTotalOweTime = 0f;
            
            //存在上周的排班数据，获取上周/累计欠休、总工作小时
            if (lastmBean != null) 
            {
                lastTotalOweTime = Float.valueOf(lastmBean.getTotal_owetime());
            }
            
            //获得当天至本周末的工作数据
            //循环天数 遍历当前人现有的天数（周一~当天）
            float workTotalTime = 0;
            Long id = 0L;
            if (mBean != null) {
                id = mBean.getId();
                //当周一至当天存在数据时
                if (notWeekDateList != null && notWeekDateList.size() > 0) 
                {
                    for (TempBean dateTemp : notWeekDateList) 
                    {
                        //获得（周一~当天）的工作工时
                        workTotalTime = workTotalTime + managerDao.getWeekManagerDataTime(id, dateTemp.getValue());
                    }
                    
                    //更新后的周/累计欠休、总工作工时
                    float totalTime = workTotalTime;
                    float weekOweTime = totalTime - 40;
                    float totalOweTime = weekOweTime + lastTotalOweTime;
                    
                    //update 更新当前人的一周总信息
                    mBean.setWeek_owetime(DateUtils.subZeroAndDot(String.valueOf(weekOweTime)));
                    mBean.setTotal_owetime(DateUtils.subZeroAndDot(String.valueOf(totalOweTime)));
                    mBean.setTotal_time(DateUtils.subZeroAndDot(String.valueOf(totalTime)));
                    
                    int mark = personnelDao.updateScheduleDataByUserId(mBean);
                    if (mark < 0) {
                        flag = false;
                        smsMess.append("修改一周统计异常");
//                        smsMess = smsMess + "修改一周统计异常";
                    }
                }
            }
        }
        
        //查询出当前日期之后的排版数据的工作日期
        List<String> workDateList = personnelDao.getWorkDateForUserDel(userId, DateUtils.dateToString(new Date(), DateUtils.YYYYMMDD));
        if (workDateList != null && workDateList.size() > 0)
        {
            int count = 0;//待删除的数据中日期是节假日的个数
            int isHoliday = 0;
            for (String workDate : workDateList)
            {
                //判断该日期是否是节假日
                isHoliday = personnelDao.getCountForWorkDateIsHoliday(workDate);
                if (isHoliday > 0)
                {
                    //是节假日，则根据判断用户在此日期是否工作
                    //是则count++
                    String workType = personnelDao.getWorkTypeByUserId(userId, workDate);
                    if ("0".equals(workType)) 
                    {
                        //工作类型
                        count++;
                    }
                    if ("1".equals(workType)) 
                    {
                        //其他类型
                        count++;
                    }
                    
                }
            }
            
            //更新节假日工作次数
            if (count > 0)
            {
                int mark = personnelDao.updateWorkDateIsHolidayCount(userId, count);
                
                if (mark < 0) 
                {
                    flag = false;
                    smsMess.append("修改节假日次数异常");
//                    smsMess = smsMess + "修改节假日次数异常";
                }
                
            }
            
            //把排版表sche_record_WEEK中的对应记录清除，清除当前日期之后的排版数据
            int markweek = personnelDao.delDataForUserDel(userId, DateUtils.dateToString(new Date(), DateUtils.YYYYMMDD));
            
            if (markweek < 0) 
            {
                flag = false;
                smsMess.append("删除当前日期之后的排版详细信息异常");
//                smsMess = smsMess + "删除当前日期之后的排版详细信息异常";
            }
            
            //把排版表sche_record中的对应记录清除，清除当前日期之后的排版数据
            int markData = personnelDao.deleteScheduleDataByUserId(userId, DateUtils.dateToString(new Date(), DateUtils.YYYYMMDD));
            
            if (markData < 0) 
            {
                flag = false;
                smsMess.append("删除当前日期之后的排版数据异常");
//                smsMess = smsMess + "删除当前日期之后的排版数据异常"; 
            }
            
        }
        
        
        //获取当前周以后周的开始日期，包括当周
        List<String> startDateList = personnelDao.getLaterStartDates(DateUtils.getMondayOfThisWeek());
        for (String startDate : startDateList)
        {
            int mark = 0;
            int count = personnelDao.getGroupInfoCount(userId, startDate);
            if (count > 1)//需要把group_info中的对应记录清除，清除本周及之后的记录
            {
                mark = personnelDao.delGroupInfoForUserDel(userId, startDate);
                
                if (mark < 0) 
                {
                    flag = false;
                    smsMess.append("删除分组异常");
//                    smsMess = smsMess + "删除分组异常";
                }
                
            }
            else if (count == 1)
            {
                mark = personnelDao.updateGroupInfoForUserDel(userId, startDate);
                
                if (mark < 0) 
                {
                    flag = false;
                    smsMess.append("修改分组异常");
//                    smsMess = smsMess + "修改分组异常";
                }
                
            }
        }
        
        if (flag) 
        {
            return "true";
        } 
        else 
        {
            return smsMess.toString();
        }
        
    }
    
    @Override
    public ResultData changePartake(Long userId, String partake)
    {
        Personnel p = personnelDao.getById(userId);
        
        if ("0".equals(partake))
        {
            //删除相应的关联数据
            delRelData(userId);
        }
        
        personnelDao.changePartake(userId, partake);
        
        addLogSucc(SpringUtil.getRequest(),
            SysConstant.SCHEDULEMGR,
            SysConstant.人员信息,
            "修改人员[姓名=" + p.getUserName() + ("1".equals(partake) ? "]参与排班" : "]不参与排班"));
        return DataFormat.formatSucc("修改成功", null);
    }
    
    @Override
    public ResultData batchSave(List<Personnel> list)
        throws Exception
    {
        int index = 1;
        for (Personnel p : list)
        {
            //查询相同电话的用户
            List<Personnel> personnels = personnelDao.getByTellphone(p.getTellphone());
            if (personnels != null && personnels.size() > 0)
            {
                throw new Exception("序号" + index + "的电话已存在！");
            }
            
            p.setCreater(getSessionUserId());
            personnelDao.save(p);
            index++;
        }
        return DataFormat.formatAdd(1, "保存成功！");
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
     * 获取上周日期
     * @param date
     * @return String
     * @throws ParseException
     */
    private String selectWeekDay(String date, String flag)
    {
        
        String weekDay = "";
        
        try
        {
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return weekDay;
        
    }
    
    /**
     * 
     * 获取不是一周的时间段
     * <功能详细描述>
     * @param now
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<TempBean> getNotWeekDate (Date now)
    {
        List<TempBean> list = new ArrayList<TempBean>();
        
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
            
        Date weekDate = null;
        TempBean temp = null;
        
        //判断now是周几
        //将date转个string yyyy-mm-dd 格式
        int dayForWeek = 0;
        try
        {
            String nowTime = sf.format(now);
            Calendar c = Calendar.getInstance();
            c.setTime(sf.parse(nowTime));
            
            if(c.get(Calendar.DAY_OF_WEEK) - 1 == 1){
                dayForWeek = 7;
            }else{
                dayForWeek = c.get(Calendar.DAY_OF_WEEK);
            }
            
            for(int i = 1; i < dayForWeek ; i++){
                
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
            
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        return list;
    }
}
