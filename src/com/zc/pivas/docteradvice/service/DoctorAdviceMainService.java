package com.zc.pivas.docteradvice.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.docteradvice.bean.OprLog;
import com.zc.pivas.docteradvice.bean.Frequency;
import com.zc.pivas.docteradvice.bean.BottleLabel;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.repository.DoctorAdviceMainDao;
import com.zc.pivas.checktype.bean.ErrorTypeBean;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;

import java.util.List;
import java.util.Map;

/**
 * 医嘱主表service接口
 * @author  cacabin
 * @version  0.1
 */
public interface DoctorAdviceMainService extends DoctorAdviceMainDao
{
    /**
     * 分頁查詢医嘱列表
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<DoctorAdviceMain> qryPageBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
        throws Exception;
   
    /**
     * 根据唯一编号查询医嘱bean
     * @param pidsj
     * @param rucangOKNum
     * @return
     */
    DoctorAdviceMain qryBeanByPidsj(String pidsj, boolean rucangOKNum);

    /**
     * 查询医嘱关联的频次
     * @param pidsjN
     * @return
     */
    String qryYZNoPinCi(String pidsjN);


    /**
     * 
     * 查询医嘱关联的频次
     * @param pidsjN
     * @return
     */
    List<String> qryYZNoPinCi(List<String> pidsjN);

    /**
     * 
     * 查询审方错误类型
     * @return
     */
    List<ErrorTypeBean> getErrorTypeList();

    /**
     * 更新医嘱审方状态
     * @param updateMap
     * @param delByPidsjN
     * @return
     */
    Integer updateCheckYZMain(Map<String, Object> updateMap, List<String> delByPidsjN);

    /**
     * 查询可用的一般规则
     * @param filterRuKeyNull
     * @return
     */
    List<Frequency> qryBatchRuleEnabled(boolean filterRuKeyNull);

    /**
     * 分页查询病人列表
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<Map<String, Object>> qryPagePatByMap(Map<String, Object> map,
        JqueryStylePaging jquryStylePaging)
        throws Exception;

    /**
     * 添加审方日志
     * @param oprLogList
     */
    void addOprLogMany(List<OprLog> oprLogList);
    

    /**
     * 
     * 自动检查医嘱状态
     */
    void autoCheckYzByDT();
    
    /**
     * 自我知识库数据对比
     */
    void autoCheckYzByLocal();

    /**
     * 
     * 预审核更新医嘱审核状态
     * @param map
     * @return
     */
    Integer updateYZAndMainSHZTByYSH(Map<String, Object> map);

    List<ConfigTitleBean> qryTitleList();

    void updateRecheckState(String parentNo, Integer newYzshzt);

    List<DoctorAdvice> getYZDetail(String groupNo, String drugCode);

    int judgePCExistBy(String parentNo, String dayDate, int pcID);

    String[] qryShowTitle(Long id);
    
    String[] getDateRange(String dayDate) throws Exception;

    BottleLabel getPqInfo(String parentStr, String pidsjStr);

    int getPcIsExist(BottleLabel pqBean);

}
