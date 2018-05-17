package com.zc.pivas.docteradvice.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.NumberUtil;
import com.zc.pivas.docteradvice.bean.ForceRulesWithArea;
import com.zc.pivas.docteradvice.bean.PriorityRulesWithArea;
import com.zc.pivas.docteradvice.bean.VolumeRulesWithBatch;
import com.zc.pivas.docteradvice.entity.OtherRule;
import com.zc.pivas.docteradvice.entity.PriorityRules;
import com.zc.pivas.docteradvice.entity.VolumeRules;
import com.zc.pivas.docteradvice.repository.PrescriptionRuleDao;
import com.zc.pivas.docteradvice.service.PrescriptionRuleService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批次优化
 *
 * @author Ray
 * @version 1.0
 */
@Service("ydRuleService")
public class PrescriptionRuleServiceImpl implements PrescriptionRuleService {

    @Resource
    private PrescriptionRuleDao ydRuleDao;

    /**
     * 药物优先级查询
     *
     * @param map
     * @return
     */
    @Override
    public List<PriorityRulesWithArea> qryPrioRulesList(Map<String, Object> map) {
        return ydRuleDao.qryPrioRulesList(map);
    }

    /**
     * 添加药物优先级
     *
     * @param prioRules
     * @return
     */
    @Override
    public Integer addPrioRules(PriorityRules prioRules) {
        return ydRuleDao.addPrioRules(prioRules);
    }

    /**
     * 更新药物优先级
     *
     * @param map
     * @return
     */
    @Override
    public Integer updPrioRules(Map<String, Object> map) {
        return ydRuleDao.updPrioRules(map);
    }

    @Override
    public Integer updTwoPrioRules(Map<String, Object> map1, Map<String, Object> map2) {
        int row1 = ydRuleDao.updPrioRules(map1);
        int row2 = ydRuleDao.updPrioRules(map2);
        return row1 + row2;
    }

    /**
     * 删除药物优先级
     *
     * @param map
     * @return
     */
    @Override
    public Integer delPrioRules(Map<String, Object> map) {
        return ydRuleDao.delPrioRules(map);
    }

    @Override
    public Integer delPrioRules(Object prId) {
        Long prIdL = NumberUtil.getObjLong(prId);
        if (prIdL != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("prId", prIdL);
            return delPrioRules(map);
        }
        return 0;
    }

    /**
     * 查询药物优先级下一个序号
     *
     * @param map
     * @return
     */
    @Override
    public Integer qryPrioRulesVal(Map<String, Object> map) {
        return ydRuleDao.qryPrioRulesVal(map);
    }

    /**
     * 查询药物优先级下一个序号
     *
     * @param prType
     * @param deptcode
     * @return
     */
    @Override
    public Integer qryPrioRulesVal(Integer prType, String deptcode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("prType", prType);
        map.put("deptcode", deptcode);
        Integer maxVal = ydRuleDao.qryPrioRulesVal(map);
        if (maxVal != null) {
            return maxVal;
        }
        return 1;
    }

    /**
     * 批量添加药物优先级
     *
     * @param map
     * @return
     */
    @Override
    public Integer addPrioRulesMany(Map<String, Object> map) {
        return ydRuleDao.addPrioRulesMany(map);
    }

    /**
     * 批量添加药物优先级
     *
     * @param addMap
     * @return
     */
    @Override
    public Integer addAndDelPrioRulesMany(Map<String, Object> addMap) {
        /* int row = ydRuleDao.delPrioRules(delMap);*/
        return ydRuleDao.addPrioRulesMany(addMap);
    }

    /**
     * 查询容积规则
     *
     * @param map
     * @param jquryStylePaging
     * @return
     */
    @Override
    public List<VolumeRulesWithBatch> qryVolumeRulesList(Map<String, Object> map, JqueryStylePaging jquryStylePaging) {
        return ydRuleDao.qryVolumeRulesList(map, jquryStylePaging);
    }

    /**
     * 查询容积规则总数
     *
     * @param map
     * @return
     */
    @Override
    public Integer qryVolumeRulesCount(Map<String, Object> map) {
        return ydRuleDao.qryVolumeRulesCount(map);
    }

    /**
     * 添加容积规则
     *
     * @param prioRules
     * @return
     */
    @Override
    public Integer addVolumeRules(VolumeRules prioRules) {
        return ydRuleDao.addVolumeRules(prioRules);
    }

    /**
     * 更新容积规则
     *
     * @param map
     * @return
     */
    @Override
    public Integer updVolumeRules(Map<String, Object> map) {
        return ydRuleDao.updVolumeRules(map);
    }

    /**
     * 删除容积规则
     *
     * @param map
     * @return
     */
    @Override
    public Integer delVolumeRules(Map<String, Object> map) {
        return ydRuleDao.delVolumeRules(map);
    }

    /**
     * 批量添加容积规则
     *
     * @param map
     * @return
     */
    @Override
    public Integer addVolumeRulesMany(Map<String, Object> map) {
        return ydRuleDao.addVolumeRulesMany(map);
    }

    /**
     * 批量添加容积规则
     *
     * @param addMap
     * @return
     */
    @Override
    public Integer addAndDeVolumeRulesMany(Map<String, Object> addMap) {

        /*int row = ydRuleDao.delVolumeRules(delMap);*/

        return ydRuleDao.addVolumeRulesMany(addMap);
    }

    /**
     * 分页查询容积规则
     *
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<VolumeRulesWithBatch> qryVolRulePageByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging) throws Exception {
        List<VolumeRulesWithBatch> listBean = ydRuleDao.qryVolumeRulesList(map, jquryStylePaging);
        int rowCount = ydRuleDao.qryVolumeRulesCount(map);
        String[] columns = {"vrId", "vrType", "deptcode", "batchId", "overrun", "maxval", "minval", "vrUserId", "vrTime", "deptname", "batchName"};
        JqueryStylePagingResults<VolumeRulesWithBatch> pagingResults = new JqueryStylePagingResults<VolumeRulesWithBatch>(columns);
        pagingResults.setDataRows(listBean);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 查询强制批次
     *
     * @param map
     * @param jquryStylePaging
     * @return
     */
    @Override
    public List<ForceRulesWithArea> qryForceRulesList(Map<String, Object> map, JqueryStylePaging jquryStylePaging) {
        return ydRuleDao.qryForceRulesList(map, jquryStylePaging);
    }

    /**
     * 查询强制批次总数
     *
     * @param map
     * @return
     */
    @Override
    public Integer qryForceRulesCount(Map<String, Object> map) {
        return ydRuleDao.qryForceRulesCount(map);
    }

    /**
     * 更新强制批次
     *
     * @param map
     * @return
     */
    @Override
    public Integer updForceRules(Map<String, Object> map) {
        return ydRuleDao.updForceRules(map);
    }

    /**
     * 分页查询强制规则
     *
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<ForceRulesWithArea> qryForceRulePageByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging) throws Exception {
        List<ForceRulesWithArea> listBean = ydRuleDao.qryForceRulesList(map, jquryStylePaging);
        int rowCount = ydRuleDao.qryForceRulesCount(map);
        String[] columns = {"prId", "prType", "deptcode", "medicId", "medicType", "batchId", "prOrder", "prUserId", "prTime", "deptname", "medicName", "batchName"};
        JqueryStylePagingResults<ForceRulesWithArea> pagingResults = new JqueryStylePagingResults<ForceRulesWithArea>(columns);
        pagingResults.setDataRows(listBean);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 查询其他规则
     *
     * @param map
     * @return
     */
    @Override
    public List<OtherRule> qryOtherRulesList(@Param("qry") Map<String, Object> map) {
        return ydRuleDao.qryOtherRulesList(map);
    }

    /**
     * 更新其他规则
     *
     * @param map
     * @return
     */
    @Override
    public Integer updOtherRules(@Param("map") Map<String, Object> map) {
        return ydRuleDao.updOtherRules(map);
    }

    /**
     * 获取药品种类
     *
     * @param map
     * @return
     */
    @Override
    public List<MedicCategory> qryMedicamentCategory(@Param("qry") Map<String, Object> map) {
        return ydRuleDao.qryMedicamentCategory(map);
    }

    /**
     * 检查容积规则是否存在
     *
     * @param volumeRules
     * @return
     */
    @Override
    public List<VolumeRules> checkVolumRuleExist(@Param("volumeRules") VolumeRules volumeRules) {
        // TODO Auto-generated method stub
        return ydRuleDao.checkVolumRuleExist(volumeRules);
    }

}
