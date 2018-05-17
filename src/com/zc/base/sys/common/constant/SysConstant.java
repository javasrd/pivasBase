package com.zc.base.sys.common.constant;

import com.zc.base.sys.common.util.Propertiesconfiguration;

import java.io.File;


public abstract interface SysConstant {
    public static final String SYSADMIN = Propertiesconfiguration.getStringProperty("account.sysAdmin");


    public static abstract interface License {
        public static final String lICENSE_PATH = "WEB-INF" + File.separator + "licensefile";
    }

    public static abstract interface DicCode {
        public static final Integer ARCHITECTURE_TYPE_UNIT = Integer.valueOf(1);
        public static final Integer ARCHITECTURE_TYPE_BUILDING = Integer.valueOf(2);
        public static final Integer ARCHITECTURE_TYPE_SITE = Integer.valueOf(3);
        public static final Integer ARCHITECTURE_TYPE_FLOOR = Integer.valueOf(4);
        public static final Integer ARCHITECTURE_TYPE_ROOM = Integer.valueOf(5);
        public static final Integer ARCHITECTURE_TYPE_REGION = Integer.valueOf(6);
    }

    public static final Integer ACCOUNT_TYPE = Integer.valueOf(2);

}
