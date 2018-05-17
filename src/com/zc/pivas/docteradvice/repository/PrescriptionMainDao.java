package com.zc.pivas.docteradvice.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.docteradvice.bean.BottleLabel;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.docteradvice.bean.TrackingRecord;
import com.zc.pivas.docteradvice.entity.BatchBean;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.medicaments.entity.Medicaments;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 药单DAO
 *
 * @author  cacabin
 * @version  0.1
 */
@MyBatisRepository("ydMainDao")
public interface PrescriptionMainDao
{
    
    /**
     * 
     * 查询药单
     * 
     * @param map
     * @return
     */
    List<PrescriptionMain> qryListBeanByMap(@Param("qry")
    Map<String, Object> map);

    /**
     * 
     * 查询药单
     * @param map
     * @param jquryStylePaging
     * @return
     */
    List<PrescriptionMain> qryListBeanByMap(@Param("qry")
    Map<String, Object> map, @Param("paging")
    JqueryStylePaging jquryStylePaging);

    /**
     * 
     * 根据搜索条件查询医嘱
     * @param map
     * @param jquryStylePaging
     * @return
     */
    List<PrescriptionMain> getYDMainbySearchParam(@Param("qry")
    Map<String, Object> map, @Param("paging")
    JqueryStylePaging jquryStylePaging);

    /**
    * 
    * 统计总数
    * @param map
    * @return
    */
    Integer qryCountByMap(@Param("qry")
    Map<String, Object> map);

    /**
     * 
     * 查询药单主表
     * @param map
     */
    void insertYDMainManyByBean(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 查询药单主表
     * @param map
     */
    void insertYDManyByBean(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 插入瓶签数据
     * @param map
     */
    void insertPQManyByBean(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 更新药单数据
     * @param map
     * @return
     */
    Integer updateYDPiN(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 更新药单数据
     * @param map
     * @return
     */
    Integer updateYDMainPiN(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 更新瓶签数据
     * 
     * @param map
     * @return
     */
    Integer updatePQPiN(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 获取查询总条数
     * @param map
     * @return
     */
    Integer getYDMainbySearchParamCount(@Param("qry")
    Map<String, Object> map);
    
    /**
     * 
     * 获取已入仓的药单
     * @param pidsjN
     * @return
     */
    List<String> queryPidsjRuCangOK(@Param("pidsjN")
    List<String> pidsjN);

    /**
     * 
     * 获取已改床号的数据
     * @param bottleLabel
     * @return
     */
    List<String> qryChangeBedPidsjN(com.zc.pivas.printlabel.entity.BottleLabel bottleLabel);

    /**
     * 
     * 更新打印时间
     * @param bottleLabel
     * @return
     */
    int updatePrintTimeByPidsjN(com.zc.pivas.printlabel.entity.BottleLabel bottleLabel);

    /**
     * 
     * 插入药单和瓶签数据
     * @param parentNo
     * @param listYDMainTemp
     * @param pqList
     * @param ydListTemp
     */
    void insertPQAndPQ(String parentNo, List<PrescriptionMain> listYDMainTemp, List<BottleLabel> pqList, List<Prescription> ydListTemp);

    /**
    * 
    * 更新药单确认状态
    * @param ydMain
    * @return
    */
    int updateYDReOrder(PrescriptionMain ydMain);

    /**
     * 
     * 查询药品使用信息
     * @param bottleLabel
     * @param jqueryStylePaging
     * @return
     */
    List<Medicaments> getMedicamentsUseInfo(@Param("bottleLabel") com.zc.pivas.printlabel.entity.BottleLabel bottleLabel, @Param("paging")JqueryStylePaging jqueryStylePaging);

    /**
    * 
    * 获取药品使用信息
    * @param bottleLabel
    * @return
    */
    int getMedicamentsUseInfoTotal(@Param("bottleLabel") com.zc.pivas.printlabel.entity.BottleLabel bottleLabel);

    /**
     * 
     * 更新药单确认状态
     * @param map
     * @return
     */
    int updateYDReOrderStatus(@Param("map")
    Map<String, Object> map);

    /**
     * 
     * 查询药单数据
     * @param map
     * @return
     */
    List<Prescription> qryYDListByMap(@Param("qry")
    Map<String, Object> map);

    /**
     * 
     * 按照病区分组
     * @param map
     * @return
     */
    List<Map<String, Object>> groupAreaByMap(@Param("qry")
    Map<String, Object> map);
    
    /**
     * 
     * 保存批次操作
     * @param pidsj
     * @param content
     * @param account
     */
    void saveOperationLog(@Param("pidsj") String pidsj,@Param("content") String content,@Param("account") String account);
    
    /**
     * 
     * 删除批次操作
     * @param pidsj
     */
    void delOperationLog(@Param("pidsj") String pidsj);

	String qryBCTimeBYId(@Param("id") String id);

    List<TrackingRecord> getTrackingRecord(@Param("pidsj") String pidsj);

    List<BLabelWithPrescription> qryPQList(@Param("pqStr") String pqStr);

    void updatePQStatus(@Param("pqStr") String pqStr);

    void addTrackingRecord(@Param("pidsj") String pidsj,@Param("typeNum") int typeNum,@Param("typeName") String typeName,@Param("account") String account);

    List<BLabelWithPrescription> getYDMainList(@Param("qry") Map<String, Object> param);

    List<BatchBean> getCustomBatch();

    BatchBean getBatchByID(@Param("pcId") String pcId);

    String[] getCheckYZList(@Param("yyrq") String yyrq);

    List<ExcuteRecordBean> getDifferentYdForAll(@Param("yyrq") String yyrq);

    List<PrescriptionMain> getYDMainListByParent(@Param("groupNo") String groupNo, @Param("yyrq") String yyrq, @Param("type") int type, @Param("order")  String order);

    List<PrescriptionMain> getYDArrayOrderPCByParent(@Param("groupNo") String groupNo, @Param("yyrq") String yyrq);

    void updateYDMain(@Param("ydBean") PrescriptionMain ydBean, @Param("type") int type);

    void updateYDChildren(@Param("ydBean") PrescriptionMain ydBean, @Param("type") int type);

    void updateYDPQ(@Param("ydBean") PrescriptionMain ydBean, @Param("type") int type);

    void saveCheckYZTemp(@Param("yyrq") String yyrq);

    void deleteCheckYZTemp();

    List<ExcuteRecordBean> getDifferentYdForSplit(@Param("yyrq") String yyrq,@Param("condition") String[] condition);

    List<PrescriptionMain> getYDMainListByParentAndPC(@Param("groupNo") String groupNo, @Param("pc") String pc, @Param("yyrq") String yyrq, @Param("type") int type);

    List<ExcuteRecordBean> getDifferentYdForSplitNotPC(@Param("yyrq") String yyrq);

    List<BatchBean> queryKongBatch();

    /**
     * 
     * 查询药单
     * @param map
     * @param jquryStylePaging
     * @return
     */
    List<PrescriptionMain> ydConfirmGetYdCountsList(@Param("qry") Map<String, Object> map, @Param("paging") JqueryStylePaging jquryStylePaging);
     
    
    /**
     * 
     * 查询药单详情
     * @param map
     * @return
     */
    List<PrescriptionMain> ydConfirmQryYdList(@Param("qry") Map<String, Object> map);
    
    /**
     * 
     * 统计总数
     * @param map
     * @return
     */
     Integer ydConfirmQryCount(@Param("qry")Map<String, Object> map);
     
     /**
      * 获取药品的类别
      * <一句话功能简述>
       * @param map
      * @return
       */
     String getCategoryCodeByMedicId(@Param("medicamentCode")String medicamentCode);
     
     /**
      * 获取化疗分类的ID
      * <一句话功能简述>
       * @param map
      * @return
       */
     String getCategoryNameByHUA();
     
     /**
      * 获取溶媒的code
      * <一句话功能简述>
       * @param pidsj
      * @return
       */
     List<String> getMenstruumCodeByQuantity(@Param("pidsj")String pidsj);
     
     /***
      * 
      * 获取药品标签
      * @param pidsj
      * @return
       */
     List<Integer> getLabelCode(@Param("pidsj")String pidsj);
     
     /**
      * 获取溶媒的药品Id
      * @param pidsj
      * @return
       */
     List<Integer> checkYDMenstruum(@Param("pidsj")String pidsj);
     
     /**
      * 
      *获取优化的批次
      * @param map
      * @return
       */
     List<PrescriptionMain> getYouHuaDePiCi(@Param("qry")Map<String, Object> map);

    String qryBatchCode(@Param("pcNum") String pcNum);

    void updateYDMainSignPC(Map<String, Object> updateYD);

    void updateYDSignPC(Map<String, Object> updateYD);

    void updatePQSignPC(Map<String, Object> updatePQ);

    String qryBatchNum(@Param("zxbc") String zxbc);
}
