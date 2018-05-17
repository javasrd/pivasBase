package com.zc.pivas.scans.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.scans.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 
 * 扫描接口dao类
 *
 */
@MyBatisRepository("scansDao")
public interface ScansDao
{
    /**
     * 
     * 获取扫码批次信息
     * @return
     */
    public List<BottleLabelBean> queryScansCountList();
    
    /**
     * 
     * 根据瓶签号获取挂水信息
     * @param btSn
     * @return
     */
    public BottleLabelBean queryBottleLabelInfo(@Param("btSn")
    String btSn, @Param("batchID")
    String batchID, @Param("qryRQ")
    String qryRQ);
    
    /**
     * 
     * 按医嘱 和类型查询扫描结果
     * @param parENTNo
     * @param scansType
     * @return
     */
    public BottleLabelResult queryBottleLabelResult(@Param("parentNo")
    String parENTNo, @Param("scansType")
    String scansType, @Param("zxbc")
    Integer zxbc);
    
    /**
     * 
     * 插入扫描结果
     */
    public void insertBottleLabelRst(@Param("bottleLabelRst")
    BottleLabelResult bottleLabelRst);
    
    /**
     * 
     * 更新扫描结果
     */
    public void updateBottleLabelRst(@Param("bottleLabelRst")
    BottleLabelResult bottleLabelRst);
    
    /**
     * 
     * 根据父医嘱ID，查询药单信息
     * @param parentNo 父医嘱编码或组编码
     * @param batch 批次
     * @param useDate 使用日期
     * @return
     */
    public List<MedicineBean> queryMedicineList(@Param("parentNo")
    String parentNo, @Param("batch")
    Integer batch, @Param("useDate")
    String useDate);
    
    /**
     * 
     * 按病区统计结果
     * @param scansSearch
     * @return
     */
    public List<BottleLabelBean> queryScansCountByDeptList(@Param("scansSearch")
    ScansSearchBean scansSearch);
    
    /**
     * 
     * 查询瓶签列表
     * <功能详细描述>
     * @param scansSearch
     * @return
     */
    public List<BottleLabelBean> queryBottleLabelList(@Param("scansSearch")
    ScansSearchBean scansSearch);
    
    public List<BottleLabelBean> queryBottleLabelListWidthOutMid(@Param("scansSearch")
    ScansSearchBean scansSearch);
    
    
    /**
     * 
     * 改变瓶签状态
     * <功能详细描述>
     * @param yDZTDesc
     * @param bottleLabelBean
     */
    public void changePQState(@Param("yDZTDesc")
    Integer yDZTDesc, @Param("bottleLabelBean")
    BottleLabelBean bottleLabelBean);
    
    /**
     * 
     * 获取配置费用规则ID
     * @param medicamentsCode
     * @return
     */
    public Integer getConfigFeeRuleId(@Param("medicamentsCode")
    String medicamentsCode);
    
    /**
     * 
     * 插入药单配置费收取、退费数据表
     */
    public void insertConfigFee(@Param("configFee")
    ScansConfigFeeBean configFee);
    
    /**
     * 
     * 查询配置费明细
     * @param detailCode
     * @return
     */
    public List<String> queryConfigfeeDetailList(@Param("detailCode")
    String detailCode);
    
    /**
     * 
     * 改变瓶签状态
     * @param yzMap 医嘱信息
     */
    public void changePQStateByParentNo(Map<String, Object> yzMap);
    
    /**
     * 
     * 改变瓶签床号
     * @param yzMap 医嘱信息
     */
    public void changePQBedno(Map<String, Object> yzMap);
    
    /**
     * 
     * 保存瓶签和配置费的关系及结果
     */
    public void savePqRefFee(@Param("pqRefFee")
    PqRefFee pqRefFee);
    
    /**
     * 
     * 查询瓶签条码号对应病人的床位是否有变更
     * @param barcode
     */
    public Integer queryCountbySN(@Param("barcode")
    String barcode);
    
    public List<Map<String, Object>> qryCountMain(@Param("map")
    Map<String, Object> map);
    
    public List<Map<String, Object>> qryCountMainWidthOutMid(@Param("map")
    Map<String, Object> map);
    
    public List<Map<String, Object>> qryCountByPcname(@Param("map")
    Map<String, Object> map);
    
    public List<Map<String, Object>> qryCountByPcnameWidthOutMid(@Param("map")
    Map<String, Object> map);
    
    public List<BLabelWithPrescription> queryPQYDListByMap(@Param("map")
    Map<String, Object> map);
    
    public List<BLabelWithPrescription> queryPQYDListByBottNum(@Param("map") Map<String, Object> map);
    
    public List<MedicineBean> queryMedicineList2(@Param("pidsj")
    String pidsj);
    
    public int changePQYdzt(@Param("ydzt")
    Integer ydzt, @Param("pidsj")
    String pidsj);
    
    public BottleLabelResult queryBottleLabelResult2(@Param("pidsj")
    String pidsj);
    
    /**
     * 查询瓶签和配置费的关系及结果
     */
    public List<PqRefFee> qryPqRefFee(@Param("pqRefFee")
    PqRefFee pqRefFee);
    
    /**
     * 修改瓶签和配置费的关系及结果
     */
    public void updatePqRefFee(PqRefFee pqRefFee);

    public List<BottleLabelBean> getPQList(@Param("param") Map<String, Object> param);

    public List<ScanResult> getSMResultList(@Param("account") String account, @Param("yyrqStart") String yyrqStart, @Param("yyrqEnd") String yyrqEnd);

    public List<BLabelWithPrescription> getYdDetailList(@Param("pqstr") String pqStr);

    public List<BottleLabelBean> getStopList(@Param("param") Map<String, Object> param);

    public List<ScanPcNum> getBatchList(@Param("param") Map<String, Object> param);

    public List<Map<String, Object>> getDeptList(@Param("param") Map<String, Object> param);

    public List<String> getPCNumber(@Param("deptcode") String deptcode,@Param("pccode") String pccode, @Param("param") Map<String, Object> param);

    public void addPQConfigurator(@Param("pidsj") String pidsj,@Param("ydpq") String ydpq,@Param("account") String account);

    public void updateCheckType(@Param("pidsj") String pq_pidsj,@Param("ydpq") String pq_ydpq,@Param("scansType") String scansType);

    public List<BottleLabelBean> getScanPQList(@Param("param") Map<String, Object> param,@Param("lastNo") String[] lastNo);

    public List<Batch> getScansBatchList();
    
    List<Map<String, Object>> getScanRestult(@Param("param") Map<String, Object> param);

}
