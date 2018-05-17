package com.zc.base.common.util;

import com.zc.base.sys.modules.sysconfig.repository.SystemConfigDao;
import com.zc.pivas.common.util.ServletContextUtil;


public class UniqueNumberGenerator {
    public static String generate() {
        return generate(13);
    }


    public static String generate(int numDigit) {
        SystemConfigDao systemConfigDao = ServletContextUtil.systemConfigDao;

        String result = "";

        String completion = "";


        String ydSequence = String.valueOf(systemConfigDao.qrySysSequence());


        String date = DateUtil.getCurrentDateStr("yyyyMMdd");


        int numLegth = numDigit - date.length();


        if (ydSequence.length() < numLegth) {
            for (int i = 0; i < numLegth - ydSequence.length(); i++) {
                completion = completion + "0";
            }

            result = date + completion + ydSequence;
        }


        if (ydSequence.length() >= numLegth) {
            ydSequence = ydSequence.substring(0, numLegth);

            result = date + ydSequence;
        }

        return result;
    }
}
