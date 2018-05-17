package com.zc.pivas.common.util;

import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.pivas.docteradvice.bean.ForceRulesWithArea;
import com.zc.pivas.docteradvice.bean.OtherRuleConfig;
import com.zc.pivas.docteradvice.bean.PriorityRulesWithArea;
import com.zc.pivas.docteradvice.bean.VolumeRulesWithBatch;
import com.zc.pivas.docteradvice.entity.OtherRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * 批次规则工具类
 */
public class BatchRuleTool {
    /**
     * 三大规则工具类
     */
    public BatchRuleTool(List<Batch> batchList, List<PriorityRulesWithArea> prioRulesListAll, List<ForceRulesWithArea> forceRulesListAll, List<VolumeRulesWithBatch> volumeRulesListAll, List<OtherRule> otherRuleListAll) {
        this.batchList = batchList;
        batchMapByEmptyTimeType = new HashMap<String, List<Batch>>();

        List<Batch> batchListTemp = null;
        batchTimeTypeMap = new HashMap<Long, String>();
        emptyBatch = new HashMap<String, Batch>();

        for (Batch batch : this.batchList) {
            if (batch.getIsEmptyBatch() == 1) {
                emptyBatch.put(batch.getNum(), batch);
            }
            batchTimeTypeMap.put(batch.getId(), batch.getTimeType());

            if (batchMapByEmptyTimeType.get("true_" + batch.getIsEmptyBatch() + "_" + batch.getTimeType()) == null) {
                batchListTemp = new ArrayList<Batch>();
                batchListTemp.add(batch);
                batchMapByEmptyTimeType.put("true_" + batch.getIsEmptyBatch() + "_" + batch.getTimeType(), batchListTemp);
            } else {
                batchListTemp = batchMapByEmptyTimeType.get("true_" + batch.getIsEmptyBatch() + "_" + batch.getTimeType());
                batchListTemp.add(batch);
            }
        }

        this.prioRulesListAll = prioRulesListAll;

        this.forceRulesListAll = forceRulesListAll;

        this.volumeRulesListAll = volumeRulesListAll;

        this.otherRuleListAll = otherRuleListAll;
        otherRuleConfig = new OtherRuleConfig(this.otherRuleListAll);
    }

    /**
     * 获取单个病区三大规则
     *
     * @param deptCode
     * @param deptName
     */
    public void initTreeRuleByDept(String deptCode, String deptName) {
        this.deptCode = deptCode;
        this.deptName = deptName;

        prioRulesListByDept = new ArrayList<PriorityRulesWithArea>();
        prioRulesMapByDept = new HashMap<String, PriorityRulesWithArea>();
        if (prioRulesListAll != null && prioRulesListAll.size() > 0) {
            for (PriorityRulesWithArea priorityRulesWithArea : prioRulesListAll) {
                if (priorityRulesWithArea.getDeptcode().equals(deptCode)) {
                    prioRulesListByDept.add(priorityRulesWithArea);
                    prioRulesMapByDept.put(priorityRulesWithArea.getMedicCode(), priorityRulesWithArea);
                }
            }
        }

        forceRulesListByDept = new ArrayList<ForceRulesWithArea>();
        forceRulesMapByDept = new HashMap<String, Long>();
        if (forceRulesListAll != null && forceRulesListAll.size() > 0) {
            for (ForceRulesWithArea forceRulesWithArea : forceRulesListAll) {
                if (forceRulesWithArea.getDeptcode().equals(deptCode)) {
                    forceRulesListByDept.add(forceRulesWithArea);
                    forceRulesMapByDept.put(forceRulesWithArea.getMedicCode(), forceRulesWithArea.getBatchId());
                }
            }
        }

        volumeRulesListByDept = new ArrayList<VolumeRulesWithBatch>();
        volumeRulesMapByDept = new HashMap<Long, VolumeRulesWithBatch>();
        if (volumeRulesListAll != null && volumeRulesListAll.size() > 0) {
            for (VolumeRulesWithBatch volumeRulesWithBatch : volumeRulesListAll) {
                if (volumeRulesWithBatch.getDeptcode().equals(deptCode)) {
                    volumeRulesListByDept.add(volumeRulesWithBatch);
                    volumeRulesMapByDept.put(volumeRulesWithBatch.getBatchId(), volumeRulesWithBatch);
                }
            }
        }

    }


    //原始数据
    private List<Batch> batchList;
    Map<String, List<Batch>> batchMapByEmptyTimeType;

    private List<PriorityRulesWithArea> prioRulesListAll;

    private List<ForceRulesWithArea> forceRulesListAll;

    private List<VolumeRulesWithBatch> volumeRulesListAll;

    private List<OtherRule> otherRuleListAll;
    OtherRuleConfig otherRuleConfig;

    //按照病区过滤后的数据
    String deptCode;
    String deptName;

    List<PriorityRulesWithArea> prioRulesListByDept;
    Map<String, PriorityRulesWithArea> prioRulesMapByDept;

    List<ForceRulesWithArea> forceRulesListByDept;
    Map<String, Long> forceRulesMapByDept;
    Map<Long, String> batchTimeTypeMap;

    List<VolumeRulesWithBatch> volumeRulesListByDept;
    Map<Long, VolumeRulesWithBatch> volumeRulesMapByDept;

    Map<String, Batch> emptyBatch;

}
