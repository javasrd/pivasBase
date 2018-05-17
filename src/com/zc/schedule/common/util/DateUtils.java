package com.zc.schedule.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zc.schedule.product.manager.entity.TempBean;

/**
 * 日期工具类
 *
 * @author Justin
 * @version v1.0
 */
public class DateUtils {

    /**
     * 日期格式
     */
    public static final String YYYYMMDD = "yyyy-MM-dd";

    /**
     * 日期格式
     */
    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd hh:mm:ss";

    /**
     * 日期格式
     */
    public static final String YYYYMM = "yyyy-MM";


    /**
     * date转换为string
     *
     * @param date
     * @param format
     * @return String
     */
    public static String dateToString(Date date, String format) {
        if (date == null || StringUtils.isBlank(format)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format.trim());
        return df.format(date);
    }

    /**
     * 字符串转date
     *
     * @param date
     * @param format
     * @return Date
     * @throws ParseException
     */
    public static Date stringToDate(String date, String format)
            throws ParseException {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(format)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format.trim());
        return df.parse(date);
    }

    /**
     * 获取本周一
     *
     * @param format
     * @return String
     */
    public static String getMondayOfThisWeek(String format) {
        if (StringUtils.isBlank(format)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return dateToString(cal.getTime(), format.trim());
    }

    /**
     * 获取本周一
     *
     * @return String
     */
    public static String getMondayOfThisWeek() {
        return getMondayOfThisWeek(YYYYMMDD);
    }

    /**
     * 获取上周一
     *
     * @param format
     * @return String
     */
    public static String getMondayOfLastWeek(String format) {
        if (StringUtils.isBlank(format)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        return dateToString(cal.getTime(), format.trim());
    }

    /**
     * 获取本周日
     *
     * @return String
     */
    public static String getSundayOfThisWeek() {
        return getSundayOfThisWeek(YYYYMMDD);
    }

    public static String getCurrentDay8Str() {
        return getCurrentDateStr("yyyy-MM-dd");
    }

    public static String getCurrentDateStr(String formatStr) {
        SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
        return dateFormate.format(new Date());
    }

    /**
     * 获取本周日
     *
     * @param format
     * @return String
     */
    public static String getSundayOfThisWeek(String format) {
        if (StringUtils.isBlank(format)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return DateUtils.dateToString(cal.getTime(), format.trim());
    }

    /**
     * 转换字符串为日期
     *
     * @param date
     * @return Date
     * @throws ParseException
     */
    public static Date strParseDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(YYYYMMDD);

        Date sDate = null;
        try {
            sDate = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }

        return sDate;
    }

    /**
     * 判断日期是周几
     *
     * @param date
     * @return String
     * @throws ParseException
     */
    public static String whichWeek(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(YYYYMMDD);

        Date sDate = null;
        try {
            sDate = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }

        int num = sDate.getDay();
        String dayStr = "";
        switch (num) {
            case 0:
                dayStr = "星期日";
                break;
            case 1:
                dayStr = "星期一";
                break;
            case 2:
                dayStr = "星期二";
                break;
            case 3:
                dayStr = "星期三";
                break;
            case 4:
                dayStr = "星期四";
                break;
            case 5:
                dayStr = "星期五";
                break;
            case 6:
                dayStr = "星期六";
                break;
            default:
                break;

        }

        return dayStr;
    }

    /**
     * 获取一月日期
     *
     * @param date
     * @return String
     * @throws Exception
     */
    public static String[] getMonthRange(Date date) throws Exception {

        SimpleDateFormat sf = new SimpleDateFormat(YYYYMMDD);

        String dateStr = sf.format(date);

        String[] dateArray = dateStr.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);

        String[] rangeArray = new String[2];

        String dayDate = dateArray[0] + "-" + dateArray[1] + "-" + String.format("%02d", 1);
        rangeArray[0] = dayDate;

        dayDate = dateArray[0] + "-" + dateArray[1] + "-" + String.format("%02d", maxDate);
        rangeArray[1] = dayDate;

        return rangeArray;
    }

    /**
     * 获取一月日期
     *
     * @param date
     * @return ArrayList
     * @throws Exception
     */
    public static ArrayList<TempBean> getMonthDate(String date) throws Exception {

        String[] dateArray = date.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);

        ArrayList<TempBean> dateList = new ArrayList<TempBean>();

        for (int i = 1; i < maxDate + 1; i++) {

            TempBean temp = new TempBean();

            String dayDate = dateArray[0] + "-" + dateArray[1] + "-" + String.format("%02d", i);

            temp.setName(String.valueOf(i));
            temp.setValue(dayDate);
            temp.setDate(whichWeek(dayDate));

            dateList.add(temp);

        }

        return dateList;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return Date
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度  
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度  
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度  
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度  
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return int
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 去除多余0
     *
     * @param s
     * @return String
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
        return s;
    }

    /**
     * 前几个月日期
     *
     * @param date
     * @param num
     * @return String
     */
    public static String lastMonth(Date date, int num) {
        SimpleDateFormat sf = new SimpleDateFormat(YYYYMM);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - num);

        String time = sf.format(c.getTime()).trim() + "-01";

        return time;
    }

}
