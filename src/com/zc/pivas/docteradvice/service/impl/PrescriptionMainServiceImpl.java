package com.zc.pivas.docteradvice.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.docteradvice.bean.BottleLabel;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.docteradvice.bean.TrackingRecord;
import com.zc.pivas.docteradvice.entity.BatchBean;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.repository.PrescriptionMainDao;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.medicaments.entity.Medicaments;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 药单service实现类
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("YDMainService")
public class PrescriptionMainServiceImpl implements PrescriptionMainService

{
    /** 日志类 */
    private static final Logger log = LoggerFactory.getLogger(PrescriptionMainServiceImpl.class);
    
    @Resource
    private PrescriptionMainDao YDMainDao;
    
    /**
     * 
     * 查询药单
     * 
     * @param map
     * @return
      */
    @Override
    public List<PrescriptionMain> qryListBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return YDMainDao.qryListBeanByMap(map, jquryStylePaging);
    }
    
    /**
     * 
     * 统计总数
     * @param map
     * @return
      */
    @Override
    public Integer qryCountByMap(Map<String, Object> map)
    {
        return YDMainDao.qryCountByMap(map);
    }
    
    /**
     * 
     * 分页查询药单数据
     * @param map
      */
    @Override
    public JqueryStylePagingResults<PrescriptionMain> qryPageBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        List<PrescriptionMain> listBean = qryListBeanByMap(map, jquryStylePaging);
        int rowCount = qryCountByMap(map);
        
        String[] columns =
            new String[] {"ydMainId", "parentNo", "actOrderNo", "ydzxzt", "yzshzt", "freqCode", "zxrq", "zxsj", "zxbc",
                "rucangOKNum", "pidsj", "caseId", "startTimeS", "endTimeS", "pb_name", "bedno", "wardName", "freqCode",
                "patname", "age", "ageunit", "chargeCode", "drugname", "specifications", "dose", "doseUnit",
                "quantity", "medicamentsPackingUnit", "dybz", "yyrq", "yyrqS", "avdp", "transfusion", "dropSpeed",
                "yzResource", "bottleLabelNum", "startDayDelNum", "startHour", "scrqS", "ydreorderStatus",
                "ydreorderCode", "ydreorderTimeS", "ydreorderMess", "zxbcChangeBeforeS", "yzconfigUname",
                "yzconfigTime", "operationLog"};
        
        JqueryStylePagingResults<PrescriptionMain> pagingResults = new JqueryStylePagingResults<PrescriptionMain>(columns);
        pagingResults.setDataRows(listBean);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }
    
    /**
     * 
     * 批量添加药单主表数据
     * @param ydMainList
      */
    @Override
    public void insertYDMainManyByBean(List<PrescriptionMain> ydMainList)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ydMainList", ydMainList);
        YDMainDao.insertYDMainManyByBean(map);
    }
    
    /**
     * 
     * 批量添加药单相关数据
     * @param map
      */
    @Override
    public void insertYDMainManyByBean(Map<String, Object> map)
    {
        YDMainDao.insertYDMainManyByBean(map);
    }
    
    /**
     * 
     * 批量添加药单数据
     * @param ydList
      */
    @Override
    public void insertYDManyByBean(List<Prescription> ydList)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ydList", ydList);
        YDMainDao.insertYDManyByBean(map);
    }
    
    /**
     * 
     * 批量添加药单数据
     * @param map
      */
    @Override
    public void insertYDManyByBean(Map<String, Object> map)
    {
        YDMainDao.insertYDManyByBean(map);
    }
    
    /**
     * 
     * 批量添加瓶签数据
     * @param pqList
      */
    @Override
    public void insertPQManyByBean(List<BottleLabel> pqList)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pqList", pqList);
        YDMainDao.insertPQManyByBean(map);
    }
    
    /**
     * 
     * 批量添加瓶签数据
     * @param map
      */
    @Override
    public void insertPQManyByBean(Map<String, Object> map)
    {
        YDMainDao.insertPQManyByBean(map);
    }
    
    /**
     * 
     * 更新药单数据
     * @param map
     * @return
      */
    @Override
    public Integer updateYDPiN(Map<String, Object> map)
    {
        return YDMainDao.updateYDPiN(map);
    }
    
    /**
     * 
     * 更新药单数据
     * @param map
     * @return
      */
    @Override
    public Integer updateYDMainPiN(Map<String, Object> map)
    {
        return YDMainDao.updateYDMainPiN(map);
    }
    
    /**
     * 
     * 更新瓶签数据
     * 
     * @param map
     * @return
      */
    @Override
    public Integer updatePQPiN(@Param("map")
    Map<String, Object> map)
    {
        return YDMainDao.updatePQPiN(map);
    }
    
    /**
     * 
     * 获取药单列表数据
     * @param map
     * @return
      */
    @Override
    public List<PrescriptionMain> getYDMainbySearchParam(@Param("qry")
    Map<String, Object> map, @Param("paging")
    JqueryStylePaging jquryStylePaging)
    {
        return YDMainDao.getYDMainbySearchParam(map, jquryStylePaging);
    }
    
    /**
     * 
     * 获取查询总条数
     * @param map
     * @return
      */
    @Override
    public Integer getYDMainbySearchParamCount(Map<String, Object> map)
    {
        return YDMainDao.getYDMainbySearchParamCount(map);
    }
    
    /**
     * 
     * 获取已入仓的药单
     * @param pidsjN
     * @return
      */
    @Override
    public List<String> queryPidsjRuCangOK(List<String> pidsjN)
    {
        if (pidsjN != null && pidsjN.size() > 0)
        {
            return YDMainDao.queryPidsjRuCangOK(pidsjN);
        }
        return null;
    }
    
    /**
     * 
     * 获取已改床号的数据
     * @param bottleLabel
     * @return
      */
    @Override
    public List<String> qryChangeBedPidsjN(com.zc.pivas.printlabel.entity.BottleLabel bottleLabel)
    {
        return YDMainDao.qryChangeBedPidsjN(bottleLabel);
    }
    
    /**
     * 
     * 更新打印时间
     * @param bottleLabel
     * @return
      */
    @Override
    public int updatePrintTimeByPidsjN(com.zc.pivas.printlabel.entity.BottleLabel bottleLabel)
    {
        return YDMainDao.updatePrintTimeByPidsjN(bottleLabel);
    }
    
    /**
     * 
     * 插入药单和瓶签数据
     * @param parentNo
     * @param listYDMainTemp
     * @param pqList
     * @param ydListTemp
      */
    @Override
    public void insertPQAndPQ(String parentNo, List<PrescriptionMain> listYDMainTemp, List<BottleLabel> pqList, List<Prescription> ydListTemp)
    {
        try
        {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("pqList", pqList);
            YDMainDao.insertPQManyByBean(map1);
            
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("ydList", ydListTemp);
            YDMainDao.insertYDManyByBean(map2);
            
            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("ydMainList", listYDMainTemp);
            YDMainDao.insertYDMainManyByBean(map3);
        }
        catch (Exception e)
        {
            log.error("药单已存在！");
        }
        
    }
    
    /**
     * 
     * 查询药单
     * 
     * @param map
     * @return
      */
    @Override
    public List<PrescriptionMain> qryListBeanByMap(Map<String, Object> map)
    {
        return YDMainDao.qryListBeanByMap(map);
    }
    
    /**
     * 
     * 更新药单确认状态
     * @param ydMain
     * @return
      */
    @Override
    public int updateYDReOrder(PrescriptionMain ydMain)
    {
        return YDMainDao.updateYDReOrder(ydMain);
    }
    
    /**
     * 
     * 查询药品使用信息
     * @param bottleLabel
     * @param jquryStylePaging
     * @return
      */
    @Override
    public List<Medicaments> getMedicamentsUseInfo(com.zc.pivas.printlabel.entity.BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
    {
        return YDMainDao.getMedicamentsUseInfo(bottleLabel, jquryStylePaging);
    }
    
    /**
     * 
     * 获取药品使用信息
     * @param bottleLabel
     * @return
      */
    @Override
    public int getMedicamentsUseInfoTotal(@Param("bottleLabel")
                                                  com.zc.pivas.printlabel.entity.BottleLabel bottleLabel)
    {
        return YDMainDao.getMedicamentsUseInfoTotal(bottleLabel);
    }
    
    /**
     * 
     * 更新药单确认状态
     * @param map
     * @return
      */
    @Override
    public int updateYDReOrderStatus(Map<String, Object> map)
    {
        return YDMainDao.updateYDReOrderStatus(map);
    }
    
    /**
    * 
    * 查询药单数据
    * @param map
    * @return
    */
    @Override
    public List<Prescription> qryYDListByMap(Map<String, Object> map)
    {
        return YDMainDao.qryYDListByMap(map);
    }
    
    /**
     * 
     * 按照病区分组
     * @param map
     * @return
      */
    @Override
    public List<Map<String, Object>> groupAreaByMap(@Param("qry")
    Map<String, Object> map)
    {
        return YDMainDao.groupAreaByMap(map);
    }
    
    /**
     * 保存批次操作信息
     */
    @Override
    public void saveOperationLog(String pidsj, String content, String account)
    {
        YDMainDao.saveOperationLog(pidsj, content, account);
    }
    
    /**
     * 删除批次操作信息
     */
    @Override
    public void delOperationLog(String pidsj)
    {
        YDMainDao.delOperationLog(pidsj);
    }
    
    @Override
    public String qryBCTimeBYId(String id)
    {
        return YDMainDao.qryBCTimeBYId(id);
    }
    
    /**
     * 查询追踪记录
     */
    @Override
    public List<TrackingRecord> getTrackingRecord(String pidsj)
    {
        return YDMainDao.getTrackingRecord(pidsj);
    }
    
    /**
     * 查询瓶签
     */
    @Override
    public List<BLabelWithPrescription> qryPQList(String pqStr)
    {
        return YDMainDao.qryPQList(pqStr);
    }
    
    @Override
    public void updatePQStatus(String pqStr)
    {
        YDMainDao.updatePQStatus(pqStr);
    }
    
    @Override
    public void addTrackingRecord(String pidsj, int typeNum, String typeName, String account)
    {
        YDMainDao.addTrackingRecord(pidsj, typeNum, typeName, account);
    }
    
    @Override
    public List<BLabelWithPrescription> getYDMainList(Map<String, Object> param)
    {
        return YDMainDao.getYDMainList(param);
    }
    
    @Override
    public List<BatchBean> getCustomBatch()
    {
        return YDMainDao.getCustomBatch();
    }
    
    @Override
    public BatchBean getBatchByID(String pcId)
    {
        return YDMainDao.getBatchByID(pcId);
    }
    
    @Override
    public String[] getCheckYZList(String yyrq)
    {
        return YDMainDao.getCheckYZList(yyrq);
    }
    
    @Override
    public List<ExcuteRecordBean> getDifferentYdForAll(String yyrq)
    {
        return YDMainDao.getDifferentYdForAll(yyrq);
    }
    
    @Override
    public List<PrescriptionMain> getYDMainListByParent(String groupNo, String yyrq, int type, String order)
    {
        return YDMainDao.getYDMainListByParent(groupNo, yyrq, type, order);
    }
    
    @Override
    public List<PrescriptionMain> getYDArrayOrderPCByParent(String groupNo, String yyrq)
    {
        return YDMainDao.getYDArrayOrderPCByParent(groupNo, yyrq);
    }
    
    @Override
    public void updateYDMain(PrescriptionMain ydBean, int type)
    {
        YDMainDao.updateYDMain(ydBean, type);
    }
    
    @Override
    public void updateYDChildren(PrescriptionMain ydBean, int type)
    {
        YDMainDao.updateYDChildren(ydBean, type);
    }
    
    @Override
    public void updateYDPQ(PrescriptionMain ydBean, int type)
    {
        YDMainDao.updateYDPQ(ydBean, type);
    }
    
    @Override
    public List<ExcuteRecordBean> getDifferentYdForSplit(String yyrq, String[] condition)
    {
        return YDMainDao.getDifferentYdForSplit(yyrq, condition);
    }
    
    @Override
    public void saveCheckYZTemp(String yyrq)
    {
        YDMainDao.saveCheckYZTemp(yyrq);
    }
    
    @Override
    public void deleteCheckYZTemp()
    {
        YDMainDao.deleteCheckYZTemp();
    }
    
    @Override
    public List<PrescriptionMain> getYDMainListByParentAndPC(String groupNo, String pc, String yyrq, int type)
    {
        return YDMainDao.getYDMainListByParentAndPC(groupNo, pc, yyrq, type);
    }
    
    @Override
    public List<ExcuteRecordBean> getDifferentYdForSplitNotPC(String yyrq)
    {
        return YDMainDao.getDifferentYdForSplitNotPC(yyrq);
    }
    
    @Override
    public List<BatchBean> queryKongBatch()
    {
        return YDMainDao.queryKongBatch();
    }
    
    @Override
    public List<PrescriptionMain> ydConfirmGetYdCountsList(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return YDMainDao.ydConfirmGetYdCountsList(map, jquryStylePaging);
    }
    
    @Override
    public List<PrescriptionMain> ydConfirmQryYdList(Map<String, Object> map)
    {
        return YDMainDao.ydConfirmQryYdList(map);
    }
    
    @Override
    public Integer ydConfirmQryCount(Map<String, Object> map)
    {
        return YDMainDao.ydConfirmQryCount(map);
    }
    
    @Override
    public String getCategoryCodeByMedicId(String medicamentCode)
    {
        return YDMainDao.getCategoryCodeByMedicId(medicamentCode);
    }
    
    @Override
    public String getCategoryNameByHUA()
    {
        return YDMainDao.getCategoryNameByHUA();
    }
    
    @Override
    public List<String> getMenstruumCodeByQuantity(String pidsj)
    {
        return YDMainDao.getMenstruumCodeByQuantity(pidsj);
    }
    
    @Override
    public List<Integer> getLabelCode(String pidsj)
    {
        return YDMainDao.getLabelCode(pidsj);
    }
    
    /**
     * 获取溶媒的药品Id
     */
    @Override
    public List<Integer> checkYDMenstruum(String pidsj)
    {
        return YDMainDao.checkYDMenstruum(pidsj);
    }
    
    @Override
    public List<PrescriptionMain> getYouHuaDePiCi(@Param("qry")
    Map<String, Object> map)
    {
        return YDMainDao.getYouHuaDePiCi(map);
    }
    
    private final static String PIVAS_PATIENT_AGEUNIT = "pivas.patient.ageunit";
    
    @Override
    public JqueryStylePagingResults<PrescriptionMain> ydConfirmGetYdCountList(Map<String, Object> map,
                                                                              JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        List<PrescriptionMain> listBean = ydConfirmGetYdCountsList(map, jquryStylePaging);
        int rowCount = ydConfirmQryCount(map);
        String[] columns = new String[] {"bedno", "patname", "ydCount", "inpatientNo"};
        JqueryStylePagingResults<PrescriptionMain> pagingResults = new JqueryStylePagingResults<PrescriptionMain>(columns);
        pagingResults.setDataRows(listBean);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }
    
    @Override
    public List<PrescriptionMain> ydConfirmGetYdList(Map<String, Object> map)
        throws Exception
    {
        List<PrescriptionMain> listBean = ydConfirmQryYdList(map);
        String lang = Servlets.getCurrentRequestLocal().getLanguage();
        for (PrescriptionMain ydMain : listBean)
        {
            ydMain.setAge(ydMain.getAge()
                + DictFacade.getName(PIVAS_PATIENT_AGEUNIT, DefineStringUtil.parseNull(ydMain.getAgeunit()), lang));
        }
        return listBean;
    }
    
    @Override
    public String qryBatchCode(String pcNum)
    {
        return YDMainDao.qryBatchCode(pcNum);
    }
    
    @Override
    public void updateYDMainSignPC(Map<String, Object> updateYD)
    {
        YDMainDao.updateYDMainSignPC(updateYD);
    }
    
    @Override
    public void updateYDSignPC(Map<String, Object> updateYD)
    {
        YDMainDao.updateYDSignPC(updateYD);
    }
    
    @Override
    public void updatePQSignPC(Map<String, Object> updatePQ)
    {
        YDMainDao.updatePQSignPC(updatePQ);
    }
    
    @Override
    public String qryBatchNum(String zxbc)
    {
        return YDMainDao.qryBatchNum(zxbc);
    }

    @Override
    public void updateYDInfo(PrescriptionMain ydBean, int type)
    {
        YDMainDao.updateYDMain(ydBean, type);
        YDMainDao.updateYDChildren(ydBean, type);
        
        if(3 == type)
        {
            YDMainDao.updateYDPQ(ydBean, -1);
        }
        else
        {
            YDMainDao.updateYDPQ(ydBean, type);
        }
    }
    
}
