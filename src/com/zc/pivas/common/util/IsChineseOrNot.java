package com.zc.pivas.common.util;

/**
 * 
 * 中英文工具类
 *
 * @author  cacabin
 * @version  1.0
 */
public class IsChineseOrNot {
    public static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前字符串 长度（中文算2个字符）
     *
     * @param str
     * @return
     */
    public static final int getEnLen(String str) {
        int num = 0;
        if (str != null && !str.equals("")) {
            char[] ch = str.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                if (isChinese(ch[i])) {
                    num = num + 2;
                } else {
                    num = num + 1;
                }
            }
        }
        return num;
    }

    /**
     * @deprecated; 弃用。和方法isChineseCharacter比效率太低。
     */
    public static final boolean isChineseCharacter_f2() {
        String str = "！？";
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 中文字符串截取
     *
     * @param substring
     * @param length
     * @return
     */
    public static String mySubstring(String substring, int length) {
        String str = "";
        int CNlength = IsChineseOrNot.getEnLen(substring);
        if (CNlength <= length) {
            return substring;
        }
        char[] charArray = substring.toCharArray();
        int i = 0;
        int a = 0;
        boolean kaiguan = true;
        while (kaiguan) {
            if (a > charArray.length - 1)
                break;
            if (IsChineseOrNot.isChinese(charArray[a])) {
                i += 2;
            } else {
                i++;
            }
            if (i <= length) {
                str += charArray[a];
                a++;
            } else {
                kaiguan = false;
            }
        }
        return str;
    }
}