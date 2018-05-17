package com.zc.base.sys.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
    protected static Log LOG = LogFactory.getLog(DateUtils.class);

    private static SimpleDateFormat YYYYMMDDHHMMSS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat YYYYMMDD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static synchronized SimpleDateFormat getYYYYMMDDHHMMSS_FORMAT() {
        return YYYYMMDDHHMMSS_FORMAT;
    }

    public static synchronized SimpleDateFormat getYYYYMMDD_FORMAT() {
        return YYYYMMDD_FORMAT;
    }


    public static Date parse(String strDate, DateFormat format)
            throws ParseException {
        return format.parse(strDate);
    }


    public static String format(Date date, DateFormat format) {
        return format.format(date);
    }

    public static String getCurrentDate() {
        Date d = new Date();
        return getYYYYMMDDHHMMSS_FORMAT().format(d);
    }

    public static Date add(Date date, int number, int flag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (flag) {
            case 1:
                calendar.add(1, number);
                break;
            case 2:
                calendar.add(2, number);
                break;
            case 3:
                calendar.add(5, number);
                break;
            case 4:
                calendar.add(10, number);
                break;
            default:
                throw new IllegalArgumentException(" flag " + flag + " can not be accept");
        }
        return calendar.getTime();
    }

    public static Date getCurrentDayMixDate(Date d) {
        Date currentDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        String dateStr = getYYYYMMDD_FORMAT().format(cal.getTime());
        dateStr = dateStr + " 00:00:00";
        try {
            currentDate = getYYYYMMDDHHMMSS_FORMAT().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return currentDate;
    }

    public static int getMonth(Date beginDate, Date endDate) {
        if (beginDate.after(endDate)) {
            Date temp = beginDate;
            beginDate = endDate;
            endDate = temp;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(beginDate);
        cal2.setTime(endDate);
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(endDate);
        tempCal.add(5, 1);
        int year = cal2.get(1) - cal1.get(1);
        int month = cal2.get(2) - cal1.get(2);
        if ((cal1.get(5) == 1) && (tempCal.get(5) == 1)) {
            return year * 12 + month + 1;
        }
        if ((cal1.get(5) != 1) && (tempCal.get(5) == 1)) {
            return year * 12 + month;
        }
        if ((cal1.get(5) == 1) && (tempCal.get(5) != 1)) {
            return year * 12 + month;
        }


        return year * 12 + month - 1 < 0 ? 0 : year * 12 + month;
    }

    public static Date getCurrentDate(String formatStr) {
        Date currentDate = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
        String dateStr = dateFormate.format(cal.getTime());

        try {
            currentDate = dateFormate.parse(dateStr);
            return currentDate;
        } catch (ParseException var6) {
            return null;
        }
    }

    public static Timestamp getSysdate() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getCurrentYear() {
        Calendar cal1 = Calendar.getInstance();
        Timestamp currentTime = getSysdate();
        cal1.setTime(new Date(currentTime.getTime()));
        Integer year = cal1.get(1);
        return year.toString();
    }


    public static String formatTime(Date time, String format) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(time);
        } else {
            return null;
        }
    }

    public static int getDateDiffDays(Date first, Date second) {
        long ONE_DATE_MILLES = 86400000L;
        long miles_first = first.getTime();
        long miles_second = second.getTime();
        miles_first = miles_first / 86400000L * 86400000L;
        miles_second = miles_second / 86400000L * 86400000L;
        return (int) ((miles_first - miles_second) / 86400000L);
    }
}
