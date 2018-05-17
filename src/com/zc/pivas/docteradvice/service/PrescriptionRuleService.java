package com.zc.pivas.docteradvice.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.docteradvice.bean.ForceRulesWithArea;
import com.zc.pivas.docteradvice.bean.VolumeRulesWithBatch;
import com.zc.pivas.docteradvice.repository.PrescriptionRuleDao;

import java.util.Map;


/**
 * 批次优化
 *
 * @author Ray
 * @version 1.0
 */
public interface PrescriptionRuleService extends PrescriptionRuleDao {
    /**
     * 删除药物优先级
     *
     * @param prId
     * @return
     */
    Integer delPrioRules(Object prId);

    /**
     * 查询药物优先级下一个序号
     *
     * @param prType
     * @param deptcode
     * @return
     */
    Integer qryPrioRulesVal(Integer prType, String deptcode);

    /**
     * 调整优先级顺序
     *
     * @param map1
     * @param map2
     * @return
     */
    Integer updTwoPrioRules(Map<String, Object> map1, Map<String, Object> map2);

    /**
     * 批量添加药物优先级
     *
     * @param addMap
     * @return
     */
    Integer addAndDelPrioRulesMany(Map<String, Object> addMap);

    /**
     * 分页查询容积规则
     *
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<VolumeRulesWithBatch> qryVolRulePageByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging) throws Exception;

    /**
     * 批量添加容积规则
     * @param addMap
     * @return
     */
    Integer addAndDeVolumeRulesMany(Map<String, Object> addMap);

    /**
     * 分页查询强制规则
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<ForceRulesWithArea> qryForceRulePageByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging) throws Exception;

}
