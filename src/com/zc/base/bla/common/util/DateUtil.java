package com.zc.base.bla.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.*;
import java.util.*;


public class DateUtil {
    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public DateUtil() {
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

    public static String getYYYYMMDDFromDate(Date date) {
        if (date == null) {
            return null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(date);
        }
    }

    public static String getYYYYMMDDHHMMSSFromDate(Date date) {
        if (date == null) {
            return null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        }
    }

    public static String getYYYYMMDDDate(Date date) {
        if (date == null) {
            return null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(date);
        }
    }

    public static String getYYYYMMDDHHMMSSDate(Date date) {
        if (date == null) {
            return null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            return df.format(date);
        }
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

    public static int getNumberOfDays(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException(" startDate can not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException(" endDate can not be null");
        }
        int numberOfDays = 0;
        numberOfDays = (int) ((endDate.getTime() - startDate.getTime()) / 86400000L) + 1;

        return numberOfDays;
    }
}