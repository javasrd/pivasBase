package com.zc.pivas.synresult.service.impl;

import com.zc.base.bla.common.util.DateUtil;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.scans.bean.PqRefFee;
import com.zc.pivas.synresult.bean.ConfigFeeTaskBean;
import com.zc.pivas.synresult.bean.SetFymxDataReq;
import com.zc.pivas.synresult.bean.SetFymxDataResp;
import com.zc.pivas.synresult.dao.ConfigFeeTaskDAO;
import com.zc.pivas.synresult.service.ConfigFeeTaskService;
import com.zc.pivas.synresult.service.PqPzfService;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置费收费
 *
 * @author kunkka
 * @version 1.0
 */
@Service("configFeeTaskService")
public class ConfigFeeTaskServiceImpl implements ConfigFeeTaskService {
    private static final Logger log = LoggerFactory.getLogger(SendToRestfulImpl.class);

    /**
     * 对接成功
     */
    private final static String TASK_SUCCESS = "200";

    @Resource
    private ConfigFeeTaskDAO configFeeTaskDAO;

    @Resource
    private PqPzfService pqPzfService;

    /**
     * 新增
     *
     * @param bean
     */
    @Override
    public void inster(ConfigFeeTaskBean bean) {
        configFeeTaskDAO.inster(bean);
    }

    /**
     * 修改
     *
     * @param bean
     */
    @Override
    public void update(ConfigFeeTaskBean bean) {
        configFeeTaskDAO.update(bean);
    }

    /**
     * 删除
     *
     * @param bean
     */
    @Override
    public void delete(ConfigFeeTaskBean bean) {
        configFeeTaskDAO.delete(bean);
    }

    /**
     * 获取数据
     *
     * @param condition 条件
     * @return
     */
    @Override
    public List<ConfigFeeTaskBean> qryTaskList(String[] condition) {
        return configFeeTaskDAO.qryTaskList(condition);
    }

    /**
     * 执行任务
     */
    @Override
    public void excute() {
        // 根据任务表中pidsj字段的第六位获取数据
        Thread task1 = new Thread(new Runnable() {
            public void run() {
                String[] conditon = "0,1".split(",");

                excuteData(conditon);
            }
        });
        task1.start();

        Thread task2 = new Thread(new Runnable() {
            public void run() {
                String[] conditon = "2,3".split(",");
                excuteData(conditon);
            }
        });
        task2.start();

        Thread task3 = new Thread(new Runnable() {
            public void run() {
                String[] conditon = "4,5".split(",");
                excuteData(conditon);
            }
        });
        task3.start();

        Thread task4 = new Thread(new Runnable() {
            public void run() {
                String[] conditon = "6,7".split(",");
                excuteData(conditon);
            }
        });
        task4.start();

        Thread task5 = new Thread(new Runnable() {
            public void run() {
                String[] conditon = "8,9".split(",");
                excuteData(conditon);
            }
        });
        task5.start();

        // 删除历史结果数据并转移到结果表
        ConfigFeeTaskBean condition = new ConfigFeeTaskBean();
        condition.setAddDate(DateUtil.getYYYYMMDDDate(new Date()));
        delete(condition);
    }

    /**
     * 解析数据
     *
     * @param conditon
     */
    private void excuteData(String[] conditon) {
        List<ConfigFeeTaskBean> configFeeTaskList = configFeeTaskDAO.qryTaskList(conditon);

        if (DefineCollectionUtil.isNotEmpty(configFeeTaskList)) {
            SetFymxDataReq req = null;
            SetFymxDataResp resp = null;
            for (ConfigFeeTaskBean bean : configFeeTaskList) {
                req = new SetFymxDataReq();

                req.setAdfysl(bean.getAmount());
                req.setAlfyxh(bean.getCostCode());
                req.setAlzyh(bean.getCaseID());
                req.setAszxgh(bean.getAccount());

                try {
                    resp = setFymxData(req);

                    if (null != resp) {
                        bean.setResult(NumberUtils.toInt(resp.getAlret()));
                        bean.setResultDesc(resp.getAserrtext());
                        analyPzfResult(bean);

                        update(bean);
                    } else {
                        bean.setResult(0);
                        bean.setResultDesc("对接配置费收取服务异常！");
                        analyPzfResult(bean);
                        update(bean);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    bean.setResult(0);
                    bean.setResultDesc("对接配置费收取服务异常！");
                    analyPzfResult(bean);
                    update(bean);
                }
            }
        }
    }

    private void analyPzfResult(ConfigFeeTaskBean bean) {
        List<PqRefFee> pqRefFeeList = null;
        PqRefFee pqRefFee = null;
        ChargeBean chargeBean = null;

        // 根据返回结果，修改配置费明细表中配置费状态
        // 根据pidsj查询SRVS_LABEL_ref_config_fee表
        pqRefFee = new PqRefFee();
        pqRefFee.setPidsj(bean.getYd_pidsj());
        pqRefFeeList = pqPzfService.qryPqRefFee(pqRefFee);

        if (DefineCollectionUtil.isNotEmpty(pqRefFeeList)) {
            pqRefFee = pqRefFeeList.get(0);

            if (null == pqRefFee.getResult()) {
                pqRefFee.setResult(1);
                pqRefFee.setResult_details("1");
                if (1 != bean.getResult()) {
                    pqRefFee.setResult(0);
                    pqRefFee.setResult_details("0");
                }
                pqPzfService.updatePqRefFee(pqRefFee);
            } else if (pqRefFee.getResult() != bean.getResult() && 1 == pqRefFee.getResult()) {
                pqRefFee.setResult(0);
                pqRefFee.setResult_details("0");
                pqPzfService.updatePqRefFee(pqRefFee);
            } else if (pqRefFee.getResult() == bean.getResult() && 0 == pqRefFee.getResult()) {
                pqRefFee.setResult(0);

                if (null == pqRefFee.getDate_details() || "".equals(pqRefFee.getDate_details())) {
                    pqRefFee.setResult_details(bean.getResult().toString());
                } else {
                    pqRefFee.setResult_details(pqRefFee.getDate_details() + "," + bean.getResult().toString());
                }

                pqPzfService.updatePqRefFee(pqRefFee);
            }

            // 修改PIVAS_YD_PZF，根据SRVS_LABEL_ref_config_fee中gid + costcode
            chargeBean = new ChargeBean();
            chargeBean.setCostcode(bean.getCostCode());
            chargeBean.setPqRefFeeID(pqRefFee.getGid());
            chargeBean.setPzfsbyy(bean.getResultDesc());

            chargeBean.setPzfzt(bean.getResult());
            if (1 != bean.getResult()) {
                chargeBean.setPzfzt(0);
            }

            pqPzfService.updatePZFStatus(chargeBean);
        }
    }

    /**
     * 设置收取配置数据
     *
     * @param req
     * @return
     * @throws Exception
     */
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

        request.put("param", data.toString());
        request.put("type", "his");
        request.put("function", "setFymxData");

        log.info("Call setFymxData interface,request: " + request.toString());

        JSONObject response =
                SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.setfymxdata"), request);

        if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
            log.info("Call gethisdata interface failure,respon: " + response);
            throw new Exception("Call setConfigfee interface failure, this url:"
                    + Propertiesconfiguration.getStringProperty("result.setfymxdata"));
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
