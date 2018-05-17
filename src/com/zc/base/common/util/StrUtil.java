package com.zc.base.common.util;


public class StrUtil {
    public StrUtil() {
    }

    public static String getObjStr(Object obj, String str) {
        if (obj != null && !obj.equals("") && !obj.equals("null")) {
            try {
                return obj.toString();
            } catch (Exception var3) {
                return str;
            }
        } else {
            return str;
        }
    }

    public static String getObjStr(Object obj) {
        return getObjStr(obj, (String) null);
    }

    public static boolean isNotNull(Object obj) {
        return obj != null && !obj.equals("") && !obj.equals("null");
    }

    public static String changeString(String[] t1, int index, String change, String format, boolean isRemove) {
        t1[index] = change;
        String result = "";
        int i = 0;
        String[] var10 = t1;
        int var9 = t1.length;

        for (int var8 = 0; var8 < var9; ++var8) {
            String str = var10[var8];
            if (isRemove) {
                if (i != index) {
                    result = result + str + format;
                }
            } else {
                result = result + str + format;
            }

            ++i;
        }

        if (result.length() > 0 && result.lastIndexOf(format) >= 0) {
            result = result.substring(0, result.lastIndexOf(format));
        }

        return result;
    }
}
