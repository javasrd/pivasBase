package com.zc.pivas.common.util;

import sun.awt.AppContext;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 字典工具类
 *
 * @author  cacabin
 * @version  1.0
 */
public class DictUtil {
    public static String ageUnit(String language, Integer age) {
        if (age == 0) {
            return "天";
        } else if (age == 1) {
            return "月";
        } else if (age == 2) {
            return "岁";
        }
        return "";
    }

    public static String sex(String language, Integer age) {
        if (age == 0) {
            return "女";
        } else if (age == 1) {
            return "男";
        }
        return "";
    }

    public static String yzlx(String language, Integer age) {
        if (age == 0) {
            return "长期";
        } else if (age == 1) {
            return "临嘱";
        }
        return "";
    }

    public static String yzshzt(String language, Integer age) {
        if (age == 0) {
            return "未审核";
        } else if (age == 1) {
            return "审核通过";
        } else if (age == 2) {
            return "审核不通过";
        }
        return "";
    }

    public static String yzzt(String language, Integer age) {
        if (age == 0) {
            return "执行";
        } else if (age == 1) {
            return "停止";
        } else if (age == 2) {
            return "撤销";
        }
        return "";
    }


    public static List<String> getPrinterNames() {
        List<String> printerList = new ArrayList<String>();

        AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < printService.length; i++) {
            printerList.add(printService[i].getName());
        }
        return printerList;
    }
}
