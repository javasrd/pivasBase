package com.zc.pivas.app.common;

import com.zc.pivas.synresult.bean.CheckOrderStatusRespon;

/**
 * 扫描服务公共类
 *
 * @author kunkka
 * @version 1.0
 */
public class ScanServiceUtil {

    /**
     * 获取医嘱状态
     *
     * @param respon 医嘱状态对象
     * @return 医嘱状态
     */
    public static String getDoctorAdviceStatusByRet(CheckOrderStatusRespon respon) {
        String strAdvice = "";
        if (null == respon) {
            return strAdvice;
        }

        String ret = respon.getResult();
        if (ret.equals("0")) {
            strAdvice = "医嘱正在执行";
        } else if (ret.equals("1")) {
            strAdvice = "医嘱已停止";
        } else if (ret.equals("2")) {
            strAdvice = "医嘱已撤销";
        } else if (ret.equals("3")) {
            strAdvice = "医嘱已变更:" + respon.getResultDesc();
        }

        return strAdvice;
    }
}

