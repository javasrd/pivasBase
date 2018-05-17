package com.zc.base.sys.common.util;

import java.util.HashMap;
import java.util.Map;

public class MyMap {
    public static Map<?, ?> quickInitMap(Object... args) {
        Map<Object, Object> map = new HashMap();
        for (int i = 0; i < args.length; i += 2) {
            if (i + 1 < args.length) {
                map.put(args[i], args[(i + 1)]);
            }
        }
        return map;
    }
}
