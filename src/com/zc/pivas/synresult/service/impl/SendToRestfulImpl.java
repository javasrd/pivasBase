package com.zc.pivas.synresult.service.impl;

import com.zc.base.common.util.DateUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.docteradvice.repository.PrescriptionMainDao;
import com.zc.pivas.docteradvice.repository.DoctorAdviceDao;
import com.zc.pivas.docteradvice.repository.DoctorAdviceMainDao;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.syndatasz.AnalySynDataForSZ;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.excuteRecord.dao.SynYdRecordDAO;
import com.zc.pivas.scans.repository.ScansDao;
import com.zc.pivas.synresult.bean.CheckOrderStatusRespon;
import com.zc.pivas.synresult.bean.SetFymxDataReq;
import com.zc.pivas.synresult.bean.SetFymxDataResp;
import com.zc.pivas.synresult.service.SendToRestful;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Restful接口实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("sendToRestful")
public class SendToRestfulImpl implements SendToRestful {
    private static final Logger log = LoggerFactory.getLogger(SendToRestfulImpl.class);

    /**
     * 对接状态码
     */
    private final static String TASK_SUCCESS = "200";

    @Resource
    private DoctorAdviceMainDao yzMainDao;

    @Resource
    private DoctorAdviceDao yzDao;

    @Resource
    private ScansDao scansDao;

    @Resource
    private AnalySynDataForSZ analySynDataForSZ;

    @Resource
    private AnalyResponMessage analyResponMessage;

    @Resource
    private PrescriptionMainDao ydMainDao;

    @Resource
    private SynYdRecordDAO synYdRecordDAO;

    @Resource
    private PrescriptionMainService ydMainService;

    /**
     * 检查医嘱状态
     *
     * @param bottleInfoList
     * @return
     * @throws Exception
     */
    @Override
    public List<CheckOrderStatusRespon> checkOrderStatus(List<JSONObject> bottleInfoList)
            throws Exception {
        List<CheckOrderStatusRespon> responList = new ArrayList<CheckOrderStatusRespon>();

        CheckOrderStatusRespon respon = null;
        JSONObject bottleInfo = null;

        // 主医嘱号
        String parentNo = "";

        // 瓶签号
        String bottleNum = "";
        for (int i = 0; i < bottleInfoList.size(); i++) {
            bottleInfo = bottleInfoList.get(i);

            parentNo = bottleInfo.getString("parentNo");
            bottleNum = bottleInfo.getString("bottleNo");

            // 根据瓶签号获取用药日期
            List<BLabelWithPrescription> ydList = ydMainService.qryPQList(bottleNum);

            respon = new CheckOrderStatusRespon();
            respon.setOrderGroupNo(parentNo);

            respon.setResult("0");

            // 调用数据同步接口
            JSONObject request = new JSONObject();

            JSONObject param = new JSONObject();

            JSONObject con = new JSONObject();

            con.put("order_group_no", parentNo);
            con.put("occdt", DateUtil.getYYYYMMDDHHMMSSDate(ydList.get(0).getYd_yyrq()));

            param.put("condition", con.toString());

            request.put("param", param.toString());
            request.put("type", "his");
            request.put("function", "checkOrderStatus");

            log.info("Call gethisdata interface,request: " + request.toString());

            JSONObject response =
                    SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.checkorderstatus"), request);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                log.error("Call gethisdata interface failure,respon: " + response);

                respon.setResult("0");
            } else {
                String result = new JSONObject(response.getString("param")).getString("result");

                if ("1".equals(result)) {
                    // 0：执行 1：停止 2：撤销 3:医嘱变动 4:药单撤销 5：药单退费
                    respon.setResult("4");
                    respon.setResultDesc("药单撤销");
                }

                if ("2".equals(result)) {
                    // 0：执行 1：停止 2：撤销 3:医嘱变动 4:药单撤销 5：药单退费
                    respon.setResult("5");
                    respon.setResultDesc("药单退费");
                }
            }
            responList.add(respon);
        }
        return responList;
    }

    /**
     * 设置配置费
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public SetFymxDataResp setFymxData(SetFymxDataReq req)
            throws Exception {
        SetFymxDataResp resp = null;
        // 调用数据同步接口 
        JSONObject request = new JSONObject();

        JSONObject data = null;

        if (null != req) {
            data = new JSONObject();

            data.put("alzyh", req.getAlzyh());
            data.put("alfyxh", req.getAlfyxh());
            data.put("adfysl", req.getAdfysl());
            data.put("aszxgh", req.getAszxgh());
        }

        request.put("param", data == null ? "" : data.toString());
        request.put("type", "his");
        request.put("function", "setFymxData");

        log.info("Call setFymxData interface,request: " + request.toString());

        JSONObject response =
                SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.setfymxdata"), request);

        if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
            log.info("Call gethisdata interface failure,respon: " + response);
            throw new Exception("Call gethisdata interface failure, this url:"
                    + Propertiesconfiguration.getStringProperty("result.gethisdata"));
        }

        // 解析响应消息
        JSONObject responData = new JSONObject(response.getString("param"));

        if (null != responData && 0 != responData.length()) {
            resp = new SetFymxDataResp();
            resp.setAlret(responData.getString("alret"));

            resp.setAserrtext(responData.getString("aserrtext"));
        }

        return resp;
    }

}
