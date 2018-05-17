package com.zc.base.bla.common.util;


public class NumberUtil {
    private NumberUtil(int ord, String nm) {
        order = ord;
        this.name = nm;
    }

    public String toString() {
        return this.name;
    }

    public static final NumberUtil FIRST = new NumberUtil(1, "first");
    public static final NumberUtil SECOND = new NumberUtil(2, "second");
    public static final NumberUtil THIRD = new NumberUtil(3, "third");
    public static final NumberUtil FOURTH = new NumberUtil(4, "fourth");
    public static final NumberUtil FIFTH = new NumberUtil(5, "fifth");

    public static final NumberUtil[] day = {FIRST, SECOND, THIRD, FOURTH, FIFTH};
    private String name;
    private static int order;

    public static final NumberUtil number(int ord) {
        return day[(ord - 1)];
    }
}
