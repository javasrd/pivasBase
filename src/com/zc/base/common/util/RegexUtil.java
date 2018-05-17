package com.zc.base.common.util;

public class RegexUtil {
    private static final String ALPHANUMERIC = "^[a-zA-Z0-9_]+$";
    private static final String ALPHANUMERIC_LEN = "^[_0-9a-zA-Z]{%d,%d}$";
    private static final String INTEGER = "\\d+";
    private static final String INTEGER_LEN = "\\d{%d,%d}";
    private static final String LETTERSONLY = "^[a-z]+$";
    private static final String LETTERSONLY_LEN = "^[a-z]{%d,%d}$";
    private static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private static final String NUMBER = "^\\d{1,%d}+$|^\\d{1,%d}+\\.{1}\\d{1,%d}+$";
    private static final String PHONE = "^[0-9-]+$";
    private static final String PHONE_LEN = "^[0-9-]{%d,%d}$";

    public static boolean isAlphaNumeric(String str) {
        return test(str, "^[a-zA-Z0-9_]+$");
    }


    public static boolean isAlphaNumericLen(String str, int minLen, int maxLen) {
        String regex = String.format("^[_0-9a-zA-Z]{%d,%d}$", new Object[]{Integer.valueOf(minLen), Integer.valueOf(maxLen)});
        return test(str, regex);
    }


    public static boolean isLettersOnly(String str) {
        return test(str, "^[a-z]+$");
    }


    public static boolean isLettersOnlyLen(String str, int minLen, int maxLen) {
        String regex = String.format("^[a-z]{%d,%d}$", new Object[]{Integer.valueOf(minLen), Integer.valueOf(maxLen)});
        return test(str, regex);
    }


    public static boolean isInteger(String str) {
        return test(str, "\\d+");
    }


    public static boolean isIntegerLen(String str, int minLen, int maxLen) {
        String regex = String.format("\\d{%d,%d}", new Object[]{Integer.valueOf(minLen), Integer.valueOf(maxLen)});
        return test(str, regex);
    }


    public static boolean isNumber(String str, int intLen, int decLen) {
        String regex = String.format("^\\d{1,%d}+$|^\\d{1,%d}+\\.{1}\\d{1,%d}+$", new Object[]{Integer.valueOf(intLen), Integer.valueOf(intLen), Integer.valueOf(decLen)});
        return test(str, regex);
    }


    public static boolean isEMail(String str) {
        return test(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }


    public static boolean isPhone(String str) {
        return test(str, "^[0-9-]+$");
    }


    public static boolean isPhoneLen(String str, int minLen, int maxLen) {
        String regex = String.format("^[0-9-]{%d,%d}$", new Object[]{Integer.valueOf(minLen), Integer.valueOf(maxLen)});
        return test(str, regex);
    }


    public static boolean test(String str, String regex) {
        if (str != null) {
            return str.matches(regex);
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(isPhone("123123"));
    }
}
