package com.zc.pivas.excuteRecord.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 医嘱
 * 
 * 
 * @author baoyun
 * @version 1.0
 */
public interface SynYdRecordService
{
    /**
     * 修改数据
     * <br>
     * @param bean
     */
    void synData(ExcuteRecordBean bean);
    
    /**
     * 根据用药时间匹配用用药批次
     *
     */
    String getBatchID(@Param("useTime") String useTime);

    List<ExcuteRecordBean> getRecordAllDay(String dayNowS, String parentNo);

    List<ExcuteRecordBean> getExcuteRecordBean(String parentNo, String actOrderNo);

    List<ExcuteRecordBean> getRecordByPC(String dayNow, String parentNo, String pcID);

    List<ExcuteRecordBean> getRecordSplit(String dayNow, String parentNo);

    List<ExcuteRecordBean> getRecordSplitExistYy(String dayNow, String parentNo);

    List<ExcuteRecordBean> getRecordYyDetail(String dayNow, String parentNo);

    List<ExcuteRecordBean> getExcuteRecordBeanByPC(String parentNo, String actOrderNo, Long pc_id,Date yyrq);
    
    /**
     * 检查不合理的药单
     * @param map
     * @return
     */
    void checkYdRefund(Map<String, Object> map);
    
    /**
     * 处理不合理医嘱对应不合理的药单
     * @param bean
     */
    void processYdRefund(ExcuteRecordBean bean);
    
    /**
     * 查询不合理医嘱对应药单
     * @param map
     * @return
     */
    JqueryStylePagingResults<ExcuteRecordBean> queryYdrefundList(Map<String, Object> map, JqueryStylePaging jquryStylePaging) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
