package com.zc.pivas.synresult.service;

import com.zc.pivas.synresult.bean.CheckOrderStatusRespon;
import com.zc.pivas.synresult.bean.SetFymxDataReq;
import com.zc.pivas.synresult.bean.SetFymxDataResp;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

/**
 * Restful接口
 *
 * @author kunkka
 * @version 1.0
 */
public interface SendToRestful {
    /**
     * 检查医嘱状态
     *
     * @return
     */
    List<CheckOrderStatusRespon> checkOrderStatus(List<JSONObject> bottleInfoList)
            throws Exception;

    /**
     * 配置费收费
     *
     * @return
     */
    SetFymxDataResp setFymxData(SetFymxDataReq req)
            throws Exception;
}
