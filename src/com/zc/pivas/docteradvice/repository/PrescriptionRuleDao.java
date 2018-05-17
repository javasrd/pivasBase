package com.zc.pivas.docteradvice.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.docteradvice.bean.ForceRulesWithArea;
import com.zc.pivas.docteradvice.bean.PriorityRulesWithArea;
import com.zc.pivas.docteradvice.bean.VolumeRulesWithBatch;
import com.zc.pivas.docteradvice.entity.OtherRule;
import com.zc.pivas.docteradvice.entity.PriorityRules;
import com.zc.pivas.docteradvice.entity.VolumeRules;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 批次优化
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("ydRuleDao")
public interface PrescriptionRuleDao {

    /**
     * 药物优先级查询
     *
     * @param map
     * @return
     */
    List<PriorityRulesWithArea> qryPrioRulesList(@Param("qry") Map<String, Object> map);

    /**
     * 添加药物优先级
     *
     * @param prioRules
     * @return
     */
    Integer addPrioRules(@Param("map") PriorityRules prioRules);

    /**
     * 更新药物优先级
     *
     * @param map
     * @return
     */
    Integer updPrioRules(@Param("map") Map<String, Object> map);

    /**
     * 删除药物优先级
     *
     * @param map
     * @return
     */
    Integer delPrioRules(@Param("map") Map<String, Object> map);

    /**
     * 查询药物优先级下一个序号
     *
     * @param map
     * @return
     */
    Integer qryPrioRulesVal(@Param("map") Map<String, Object> map);

    /**
     * 批量添加药物优先级
     *
     * @param map
     * @return
     */
    Integer addPrioRulesMany(@Param("map") Map<String, Object> map);

    /**
     * 查询容积规则
     *
     * @param map
     * @param jquryStylePaging
     * @return
     */
    List<VolumeRulesWithBatch> qryVolumeRulesList(@Param("qry") Map<String, Object> map, @Param("paging") JqueryStylePaging jquryStylePaging);

    /**
     * 查询容积规则总数
     *
     * @param map
     * @return
     */
    Integer qryVolumeRulesCount(@Param("qry") Map<String, Object> map);

    /**
     * 添加容积规则
     *
     * @param prioRules
     * @return
     */
    Integer addVolumeRules(@Param("map") VolumeRules prioRules);

    /**
     * 更新容积规则
     *
     * @param map
     * @return
     */
    Integer updVolumeRules(@Param("map") Map<String, Object> map);

    /**
     * 删除容积规则
     *
     * @param map
     * @return
     */
    Integer delVolumeRules(@Param("map") Map<String, Object> map);

    /**
     * 批量添加容积规则
     *
     * @param map
     * @return
     */
    Integer addVolumeRulesMany(@Param("map") Map<String, Object> map);

    /**
     * 查询强制批次
     *
     * @param map
     * @param jquryStylePaging
     * @return
     */
    List<ForceRulesWithArea> qryForceRulesList(@Param("qry") Map<String, Object> map, @Param("paging") JqueryStylePaging jquryStylePaging);

    /**
     * 查询强制批次总数
     *
     * @param map
     * @return
     */
    Integer qryForceRulesCount(@Param("qry") Map<String, Object> map);

    /**
     * 更新强制批次
     *
     * @param map
     * @return
     */
    Integer updForceRules(@Param("map") Map<String, Object> map);

    /**
     * 查询其他规则
     *
     * @param map
     * @return
     */
    List<OtherRule> qryOtherRulesList(@Param("qry") Map<String, Object> map);

    /**
     * 更新其他规则
     *
     * @param map
     * @return
     */
    Integer updOtherRules(@Param("map") Map<String, Object> map);

    /**
     * 查询所有的药品分类
     *
     * @param map
     * @return
     */
    List<MedicCategory> qryMedicamentCategory(@Param("qry") Map<String, Object> map);

    /**
     * 检查是否存在相同的容积规则
     *
     * @param volumeRules
     * @return
     */
    List<VolumeRules> checkVolumRuleExist(@Param("volumeRules") VolumeRules volumeRules);
}
