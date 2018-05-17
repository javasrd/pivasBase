package com.zc.pivas.excuteRecord.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 药单执行记录
 *
 * @author  cacabin
 * @version  1.0
 */
@MyBatisRepository("synYdRecordDAO")
public interface SynYdRecordDAO
{
    /**
     *  同步数据
     * <br>
     * <功能详细描述>
     * @param model
     */
    void synData(ExcuteRecordBean bean);
    
    /**
     * 根据用药时间匹配用用药批次
     *
     */
    String getBatchID(@Param("useTime") String useTime);

    
    /**
     * 获取全天用药次数
     * 
     */
    List<ExcuteRecordBean> getRecordAllDay(@Param("dayDate")String dayNowS,@Param("parentNo") String parentNo);

    List<ExcuteRecordBean> getExcuteRecordBean(@Param("parentNo") String parentNo,@Param("actOrderNo") String actOrderNo);

    List<ExcuteRecordBean> getRecordByPC(@Param("dayDate") String dayNow,@Param("parentNo") String parentNo,@Param("pcID") String pcID);

    List<ExcuteRecordBean> getRecordSplit(@Param("dayDate") String dayNow,@Param("parentNo") String parentNo);

    List<ExcuteRecordBean> getRecordSplitExistYy(@Param("dayDate") String dayNow,@Param("parentNo") String parentNo);

    List<ExcuteRecordBean> getRecordYyDetail(@Param("dayDate") String dayNow,@Param("parentNo") String parentNo);
    
    List<ExcuteRecordBean> getRecordForCheck(@Param("dayDate") String dayNow,@Param("parentNo") String parentNo,@Param("pcID") String pcID);

    List<ExcuteRecordBean> getExcuteRecordBeanByPC(@Param("parentNo") String parentNo,@Param("actOrderNo") String actOrderNo,@Param("pcid") Long pc_id,@Param("yyrq") Date yyrq);
    
    /**
     * 检查不合理的药单
     * @param map
     * @return
     */
    List<ExcuteRecordBean> checkYdRefund(@Param("qry") Map<String, Object> map);
    
    /**
     * 查询不合理医嘱对应药单
     * @param map
     * @return
     */
    List<ExcuteRecordBean>  queryYdrefundList(@Param("qry") Map<String, Object> map,@Param("paging")
    JqueryStylePaging jquryStylePaging);
    
    /**
     * 保存不合理的药单
     * @param bean
     */
    void insertYdRefund(ExcuteRecordBean bean);
    
    int queryYdrefundTotal(@Param("qry") Map<String, Object> map);
    
    /**
     * 处理不合理医嘱对应不合理的药单
     * @param bean
     */
    void processYdRefund(ExcuteRecordBean bean);
}
