package com.zc.pivas.app.service;

import com.zc.pivas.app.bean.ScanOutputParamBean;

/**
 * 配置费计算服务接口
 *
 * @author kunkka
 * @version 1.0
 */
public interface ScanBottleService {
    /**
     * 处理扫描
     *
     * @param bottleNum 瓶签号码
     * @return 收费是否成功
     * @throws Exception
     */
    ScanOutputParamBean scanBottleAction(String bottleNum, String strUser) throws Exception;
}
