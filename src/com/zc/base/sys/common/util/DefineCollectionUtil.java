package com.zc.base.sys.common.util;

import java.util.*;


public class DefineCollectionUtil {
    public static String[] toStringArray(Collection<String> colls) {
        String[] stringArray = new String[0];
        if ((colls != null) && (!colls.isEmpty())) {
            Object[] objectArray = colls.toArray();
            int length = colls.size();
            stringArray = new String[length];

            for (int i = 0; i < length; i++) {
                if (objectArray[i] != null) {
                    stringArray[i] = objectArray[i].toString();
                } else {
                    stringArray[i] = "";
                }
            }
        }
        return stringArray;
    }


    public static <T> List<T> asList(T... a) {
        return Arrays.asList(a);
    }


    public static List<Long> asLongTypeList(String... strings) {
        List<Long> list = new ArrayList();
        if ((strings != null) && (strings.length > 0)) {
            String[] arrayOfString = strings;
            int j = strings.length;
            for (int i = 0; i < j; i++) {
                String str = arrayOfString[i];

                list.add(Long.valueOf(str));
            }
        }
        return list;
    }


    public static Object getFirstObject(List<?> vList) {
        if (!isEmpty(vList)) {
            return vList.get(0);
        }


        return null;
    }


    public static boolean isEmpty(Collection<?> list) {
        return (list == null) || (list.isEmpty());
    }


    public static boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }


    public static boolean isEmpty(Map map) {
        return (map == null) || (map.isEmpty());
    }


    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }


    public static void copyList(List dest, List src) {
        if (isNotEmpty(src)) {
            int i = 0;
            for (int size = src.size(); i < size; i++) {
                dest.add(src.get(i));
            }
        }
    }

    public static void main(String[] args) {
        List<String> src = new ArrayList();
        src.add("1");
        src.add("2");
        src.add("3");

        List<String> dest = new ArrayList();
        copyList(dest, src);
        dest.removeAll(src);
        System.out.println("777");
    }
}
