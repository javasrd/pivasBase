package com.zc.base.common.util;

import com.zc.base.sys.common.util.NumberUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class CommonUtil {
    private static String dataBaseType;

    public static String getDataBaseType() {
        if (StringUtils.isEmpty(dataBaseType)) {
            String mysql = "mysql";
            String oracle = "oracle";
            String jdbcDriver = Propertiesconfiguration.getStringProperty("jdbc.driver");
            if (jdbcDriver.toLowerCase().contains(mysql)) {
                dataBaseType = mysql;
            } else if (jdbcDriver.toLowerCase().contains(oracle)) {
                dataBaseType = oracle;
            }
            return dataBaseType;
        }
        return dataBaseType;
    }

    public static List<String> getStrNbyStr(Object str, String split) {
        List<String> list = new ArrayList();
        if ((str != null) && (split != null) && (str.toString().length() > 0)) {
            String[] strN = str.toString().split(split);
            String[] arrayOfString1;
            int j = (arrayOfString1 = strN).length;
            for (int i = 0; i < j; i++) {
                String strTemp = arrayOfString1[i];

                if (strTemp != null) {
                    list.add(strTemp);
                }
            }
        }
        return list;
    }

    public static List<String> getStrNbyStr(Object str) {
        return getStrNbyStr(str, ",");
    }

    public static List<Long> getLongNbyStr(Object str, String split) {
        List<Long> list = new ArrayList();
        if ((str != null) && (split != null) && (str.toString().length() > 0)) {
            String[] strN = str.toString().split(split);
            String[] arrayOfString1;
            int j = (arrayOfString1 = strN).length;
            for (int i = 0; i < j; i++) {
                String strTemp = arrayOfString1[i];

                if ((strTemp != null) && (NumberUtil.getObjLong(strTemp) != null)) {
                    list.add(NumberUtil.getObjLong(strTemp));
                }
            }
        }
        return list;
    }

    public static List<Long> getLongNbyStr(Object str) {
        return getLongNbyStr(str, ",");
    }

    public static List<Integer> getIntNbyStr(Object str, String split) {
        List<Integer> list = new ArrayList();
        if ((str != null) && (split != null) && (str.toString().length() > 0)) {
            String[] strN = str.toString().split(split);
            String[] arrayOfString1;
            int j = (arrayOfString1 = strN).length;
            for (int i = 0; i < j; i++) {
                String strTemp = arrayOfString1[i];

                if ((strTemp != null) && (NumberUtil.getObjInt(strTemp) != null)) {
                    list.add(NumberUtil.getObjInt(strTemp));
                }
            }
        }
        return list;
    }
}
