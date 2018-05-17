package com.zc.pivas.scans.service;

import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.web.i18n.MessageHolder;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import com.zc.pivas.scans.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 扫码业务
 *
 * @author  cacabin
 * @version  1.0
 */
public interface ScansService {
    /**
     * 获取扫码批次统计信息
     *
     * @return
     */
    List<ScansBatchBean> getScansBatchCountInfo();

    /**
     * 根据瓶签号获取挂水信息
     *
     * @param BtSn
     * @param batchID
     * @param qryRQ
     * @return
     */
    BottleLabelBean queryBottleLabelInfo(String BtSn, String batchID, String qryRQ);

    /**
     * 统计病区的完成/总数量
     *
     * @param scansSearch
     * @return
     */
    List<ScansBatchBean> scansDeptStatistics(ScansSearchBean scansSearch, MessageHolder messageHolder);

    /**
     * 获取瓶签列表
     *
     * @param scansSearch
     * @return
     */
    List<BottleLabelBean> queryBottleLabelList(ScansSearchBean scansSearch);

    /**
     * 收费接口
     *
     * @param scansConfigFeeBean
     * @return
     */
    Integer changCostCode(ScansConfigFeeBean scansConfigFeeBean);

    /**
     * 改变瓶签状态
     *
     * @param yzMap 医嘱信息
     */
    public void changePQStateByParentNo(Map<String, Object> yzMap);

    public Integer changCostCodeByDetai(ChargeDetailsBean chargeDetailsBean);

    //public Integer checkOrderStatus(List<MedicineBean> medicineBeanList);

    public List<Map<String, Object>> qryCountMain(Map<String, Object> map);

    public List<Map<String, Object>> qryCountMainWidthOutMid(Map<String, Object> map);

    public List<Map<String, Object>> qryCountByPcname(Map<String, Object> map);

    public List<Map<String, Object>> qryCountByPcnameWidthOutMid(Map<String, Object> map);

    public List<BLabelWithPrescription> queryPQYDListByMap(Map<String, Object> map);

    public BLabelWithPrescription queryPQYDListByBottNum(String ydpq2);

    /**
     * 改变瓶签床号
     *
     * @param yzMap 医嘱信息
     */
    public void changePQBedno(Map<String, Object> yzMap);

    public List<MedicineBean> queryMedicineList2(@Param("pidsj") String pidsj);

    public int changePQYdzt(Integer ydzt, String pidsj);

    public String in_out_pack(String scansType, List<MedicineBean> medicineBeanList, BLabelWithPrescription pqyd, String username);

    public BottleLabelResult queryBottleLabelResult2(@Param("pidsj") String pidsj);

    public class AppScanRet {
        public int ret;
        public String msg;
    }

    public AppScanRet appScan(String barcode, String scansType, String batchID, String qryRQ, String batchName, String username);


    /**
     * 查询瓶签列表
     *
     * @param param
     * @return
     */
    List<BottleLabelBean> getPQList(Map<String, Object> param);

    List<ScanResult> getSMResultList(String account, String yyrqStart, String yyrqEnd);

    List<BLabelWithPrescription> getYdDetailList(String pqStr);

    List<BottleLabelBean> getStopList(Map<String, Object> param);

    List<ScanPcNum> getBatchList(Map<String, Object> param);

    List<Map<String, Object>> getDeptList(Map<String, Object> param);

    List<String> getPCNumber(String deptcode, String pccode, Map<String, Object> param);

    AppScanRet appScanNew(String bottleNum, String inside, String tim, String strUser) throws Exception;

    void updateCheckType(String pq_pidsj, String pq_ydpq, String scansType);

    List<BottleLabelBean> getScanPQList(Map<String, Object> param, String[] lastNo);

    List<Batch> getScansBatchList();

    List<Map<String, Object>> getScanRestult(Map<String, Object> param);

}
