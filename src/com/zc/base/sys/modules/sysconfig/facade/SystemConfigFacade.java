package com.zc.base.sys.modules.sysconfig.facade;

import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;

import java.util.Map;


public class SystemConfigFacade {
    private static Map<String, SystemConfig> map = null;

    public static Map<String, SystemConfig> getMap() {
        return map;
    }

    public static void setMap(Map<String, SystemConfig> m) {
        map = m;
    }

    public static boolean isLoaded() {
        return (map != null) && (!map.isEmpty());
    }
}
