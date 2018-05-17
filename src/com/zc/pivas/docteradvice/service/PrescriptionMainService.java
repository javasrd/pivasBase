package com.zc.pivas.docteradvice.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.docteradvice.bean.BottleLabel;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.docteradvice.bean.TrackingRecord;
import com.zc.pivas.docteradvice.entity.BatchBean;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.repository.PrescriptionMainDao;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;

import java.util.List;
import java.util.Map;

/**
 * 
 * 药单service接口
 *
 * @author  cacabin
 * @version  0.1
 */
public interface PrescriptionMainService extends PrescriptionMainDao
{
    /**
    * 
    * 批量添加药单主表数据
    * @param ydMainList
    */
    void insertYDMainManyByBean(List<PrescriptionMain> ydMainList);
    
    /**
     * 
     * 批量添加药单数据
      * @param ydList
      */
    void insertYDManyByBean(List<Prescription> ydList);
    
    /**
     * 
     * 批量添加瓶签数据
      * @param pqList
      */
    void insertPQManyByBean(List<BottleLabel> pqList);
    
    /**
     * 
     * 分页查询药单数据
      * @param map
      */
    JqueryStylePagingResults<PrescriptionMain> qryPageBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
        throws Exception;
    
    /**
     * 保存批次操作信息
     */
    void saveOperationLog(String pidsj, String content, String account);
    /**
     * 删除批次操作信息
     */
    void delOperationLog(String pidsj);
	
	String qryBCTimeBYId(String string);
    
    List<TrackingRecord> getTrackingRecord(String pidsj);

    List<BLabelWithPrescription> qryPQList(String pqStr);

    void updatePQStatus(String pqStr);

    void addTrackingRecord(String pidsj, int typeNum, String TypeName, String account);

    List<BLabelWithPrescription> getYDMainList(Map<String, Object> param);

    List<BatchBean> getCustomBatch();

    BatchBean getBatchByID(String pcId);

    String[] getCheckYZList(String yyrq);

    List<ExcuteRecordBean> getDifferentYdForAll(String yyrq);

    List<PrescriptionMain> getYDMainListByParent(String groupNo, String yyrq, int type, String order);

    List<PrescriptionMain> getYDArrayOrderPCByParent(String groupNo, String yyrq);

    void updateYDMain(PrescriptionMain ydBean, int type);

    void updateYDChildren(PrescriptionMain ydBean, int type);

    void updateYDPQ(PrescriptionMain ydBean, int type);

    List<ExcuteRecordBean> getDifferentYdForSplit(String yyrq, String[] condition);

    void saveCheckYZTemp(String yyrq);

    void deleteCheckYZTemp();

    List<PrescriptionMain> getYDMainListByParentAndPC(String groupNo, String pc, String yyrq, int type);

    List<ExcuteRecordBean> getDifferentYdForSplitNotPC(String yyrq);

    List<BatchBean> queryKongBatch();

    List<PrescriptionMain> ydConfirmGetYdList(Map<String, Object> map)
        throws Exception;
    
    JqueryStylePagingResults<PrescriptionMain> ydConfirmGetYdCountList(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
        throws Exception;

    String qryBatchCode(String pcNum);

    void updateYDMainSignPC(Map<String, Object> updateYD);

    void updateYDSignPC(Map<String, Object> updateYD);

    void updatePQSignPC(Map<String, Object> updatePQ);

    String qryBatchNum(String zxbc);
    
    void updateYDInfo(PrescriptionMain ydBean, int type);

}
