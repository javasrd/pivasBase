package com.zc.pivas.common.util;

import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.common.entity.ResultData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * 格式化工具类
 *
 * @author  cacabin
 * @version  1.0
 */
public class DataFormat
{
    public static final String formatDay8 = "yyyy-MM-dd";

    final static String tomorrowSwitch = Propertiesconfiguration.getStringProperty("pivas.tomorrow.switch");

    final static String tomorrowTime = Propertiesconfiguration.getStringProperty("pivas.tomorrow.time");

    public final static String TIME_START = Propertiesconfiguration.getStringProperty("pivas.date.start").trim();

    public final static String TIME_END = Propertiesconfiguration.getStringProperty("pivas.date.end").trim();

    public final static String DATEEN = "今天";

    public static String getNextDate(String dateNow)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(formatDay8);
        Date date = null;
        try
        {
            date = format.parse(dateNow);
            calendar.setTime(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        int day = calendar.get(Calendar.DATE);
        if(DATEEN.equals(TIME_END.substring(0,TIME_END.lastIndexOf(" ")).trim())){
            calendar.set(Calendar.DATE, day);
        }else{
            calendar.set(Calendar.DATE, day + 1);
        }

        String dayAfter = format.format(calendar.getTime());
        return dayAfter;
    }

    public static String getNextDate(Date dateNow)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(formatDay8);
        calendar.setTime(dateNow);
        int day = calendar.get(Calendar.DATE);
        if(DATEEN.equals(TIME_END.substring(0,TIME_END.lastIndexOf(" ")).trim())){
            calendar.set(Calendar.DATE, day);
        }else{
            calendar.set(Calendar.DATE, day + 1);
        }
        String dayAfter = format.format(calendar.getTime());
        return dayAfter;
    }

    public static String getTimeStart(){
        return TIME_START.substring(TIME_START.lastIndexOf(" "), TIME_START.length());
    }

    public static String getTimeEnd(){
        return TIME_END.substring(TIME_END.lastIndexOf(" "), TIME_END.length());
    }

    public static String formatYYYYMMDDByDate(Date date)
    {
        if (date != null)
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(formatDay8);
                return sdf.format(date);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ResultData formatSucc()
    {
        return formatSucc(null, null);
    }

    public static ResultData formatSucc(Object data)
    {
        return formatSucc(null, data);
    }

    public static ResultData formatSucc(String mess, Object data)
    {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.resultCode.OK);
        resultData.setMess(mess == null ? SysConstant.resultMess.OK : mess);
        resultData.setData(data);
        return resultData;
    }

    public static ResultData formatFail()
    {
        return formatFail(null, null);
    }

    public static ResultData formatFail(Object data)
    {
        return formatFail(null, data);
    }

    public static ResultData formatFail(String mess, Object data)
    {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.resultCode.Err);
        resultData.setMess(mess == null ? SysConstant.resultMess.Err : mess);
        resultData.setData(data);
        return resultData;
    }

    public static ResultData formatAdd(int row, String mess, Object data)
    {
        ResultData resultData = new ResultData();
        resultData.setCode(row > 0 ? SysConstant.resultCode.addOK : SysConstant.resultCode.addErr);
        resultData.setMess(mess == null ? (row > 0 ? SysConstant.resultMess.addOK : SysConstant.resultMess.addErr)
                : mess);
        resultData.setData(data);
        return resultData;
    }

    public static ResultData formatAdd(int row, String mess)
    {
        return formatAdd(row, mess, null);
    }

    public static ResultData formatAdd(int row, Object data)
    {
        return formatAdd(row, null, data);
    }

    public static ResultData formatAdd(int row)
    {
        return formatAdd(row, null, null);
    }

    public static ResultData formatUpd(int row, String mess, Object data)
    {
        ResultData resultData = new ResultData();
        resultData.setCode(row > 0 ? SysConstant.resultCode.updOK : SysConstant.resultCode.updErr);
        resultData.setMess(mess == null ? (row > 0 ? SysConstant.resultMess.updOK : SysConstant.resultMess.updErr)
                : mess);
        resultData.setData(data);
        return resultData;
    }

    public static ResultData formatUpd(int row, String mess)
    {
        return formatUpd(row, mess, null);
    }

    public static ResultData formatUpd(int row, Object data)
    {
        return formatUpd(row, null, data);
    }

    public static ResultData formatUpd(int row)
    {
        return formatUpd(row, null, null);
    }

    public static ResultData formatDel(int row, String mess, Object data)
    {
        ResultData resultData = new ResultData();
        resultData.setCode(row > 0 ? SysConstant.resultCode.delOK : SysConstant.resultCode.delErr);
        resultData.setMess(mess == null ? (row > 0 ? SysConstant.resultMess.delOK : SysConstant.resultMess.delErr)
                : mess);
        resultData.setData(data);
        return resultData;
    }

    public static ResultData formatDel(int row, String mess)
    {
        return formatDel(row, mess, null);
    }

    public static ResultData formatDel(int row, Object data)
    {
        return formatDel(row, null, data);
    }

    public static ResultData formatDel(int row)
    {
        return formatDel(row, null, null);
    }

    /**
     *
     * 查询时间改变开关
     * @return
     *      false 不改变  true 改变
     */
    public static Boolean dateChange(){

        if(!"Y".equals(tomorrowSwitch)){
            return false;
        }

        try{

            String[] time = tomorrowTime.trim().split(":");
            if(time == null || time.length == 0){
                return false;
            }

            GregorianCalendar nowCalendar = new GregorianCalendar();

            GregorianCalendar torCalendar = new GregorianCalendar();
            torCalendar.set(GregorianCalendar.HOUR_OF_DAY,Integer.parseInt((time[0].trim())));
            torCalendar.set(GregorianCalendar.MINUTE,Integer.parseInt((time[1].trim())));
            torCalendar.set(GregorianCalendar.MILLISECOND, Integer.parseInt((time[2].trim())));

            if(nowCalendar.after(torCalendar)){
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return false;
    }


}
