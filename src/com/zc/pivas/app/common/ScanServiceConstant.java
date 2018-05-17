package com.zc.pivas.app.common;


/**
 * APP请求交互bean
 *
 * @author kunkka
 * @version 1.0
 */
public class ScanServiceConstant {
    public interface ScanBottleRetConstant {
        public int ScanGetMsgError = -1;//获取信息失败
        public int ScanDoctorAdviceError = -2;//医嘱非正常
        public int ScanUpdateManError = -3;//更新药单配置人员失败
        public int ScanConfigFeeError = -4;//收取配置费失败
        public int ScanStockError = -5;//更新药品库存失败
        public int ScanOK = 0;//全部OK
    }

    public interface CheckOrderStatusConstant {
        public int Checkfailed = -1;//获取医嘱状态异常
        public int CheckErr = -2;//医嘱非正常
        public int CheckOK = 0;//医嘱正常
    }
}