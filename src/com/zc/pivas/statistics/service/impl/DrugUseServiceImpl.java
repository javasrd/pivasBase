package com.zc.pivas.statistics.service.impl;

import com.zc.pivas.statistics.repository.DrugUseDAO;
import com.zc.pivas.statistics.service.DrugUseService;
import com.zc.pivas.statistics.bean.druguse.*;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 药物使用统计
 *
 * @author cacabin
 * @version 1.0
 */
@Service("drugUseService")
public class DrugUseServiceImpl implements DrugUseService {
    /**
     * 药物使用统计
     */
    @Resource
    private DrugUseDAO drugUseDAO;

    /**
     * 所有药品分类
     */
    private List<String> drugClassList = new ArrayList<String>();

    @Override
    public List<DrugUseBatchPieBean> queryBatchPieList(PrescriptionSearchBean condition) {
        return drugUseDAO.queryBatchPieList(condition);
    }

    @Override
    public List<DrugUsePieDetailBean> queryBatchDrugClassPieList(PrescriptionSearchBean condition) {
        return drugUseDAO.queryBatchDrugClassPieList(condition);
    }

    @Override
    public List<DrugUseDeptPieBean> queryDeptPieList(PrescriptionSearchBean condition) {
        return drugUseDAO.queryDeptPieList(condition);
    }

    @Override
    public List<DrugUsePieDetailBean> queryDeptDrugClassPieList(PrescriptionSearchBean condition) {
        return drugUseDAO.queryDeptDrugClassPieList(condition);
    }

    @Override
    public List<DrugUseBarGroupByBatch> queryBatchBarList(PrescriptionSearchBean condition) {
        return drugUseDAO.queryBatchBarList(condition);
    }

    @Override
    public DrugUseBatchBarBean queryBarGroupByBatchList(PrescriptionSearchBean condition) {
        List<DrugUseBarGroupByBatch> drugUseBarGroupByBatchList = queryBatchBarList(condition);

        if (drugUseBarGroupByBatchList == null) {
            return null;
        }

        // 获取所有药品分类
        for (DrugUseBarGroupByBatch drugUseBarGroupByBatch : drugUseBarGroupByBatchList) {
            if (!drugClassList.contains(drugUseBarGroupByBatch.getDrugClassName())) {
                drugClassList.add(drugUseBarGroupByBatch.getDrugClassName());
            }
        }

        // 格式化 批次->药物使用->药品分类数量 维度的统计
        List<Batch2DrugUseBarBean> batch2DrugUseBarList = formatBatch2StatusStatic(drugUseBarGroupByBatchList);

        // 批次名称列表
        DrugUseBatchBarBean drugUseBatchBarBean = formatDrug2BatchStatic(batch2DrugUseBarList);
        drugUseBatchBarBean.setDrugClassList(drugClassList);
        drugUseBatchBarBean.setCostMap(getCostMap(drugUseBarGroupByBatchList));

        return drugUseBatchBarBean;
    }

    private Map<String, String> getCostMap(List<DrugUseBarGroupByBatch> drugUseBarGroupByBatchList) {
        DecimalFormat df = new DecimalFormat("#.##");

        // key = 批次+","+药品分类  value=金额
        Map<String, String> costMap = new HashMap<String, String>();

        String batchName = null;
        String drugClassName = null;
        String key = null;
        double cost = 0;
        for (DrugUseBarGroupByBatch drugUseBarGroupByBatch : drugUseBarGroupByBatchList) {
            batchName = drugUseBarGroupByBatch.getBatchNAME();
            drugClassName = drugUseBarGroupByBatch.getDrugClassName();
            key = batchName + "," + drugClassName;

            cost = drugUseBarGroupByBatch.getStstsCost();

            costMap.put(key, df.format(cost));

        }

        return costMap;
    }

    /**
     * 格式化  药物使用统计->批次-> 维度的统计
     * 批次名称列表
     *
     * @param batch2DrugUseBarList
     * @return
     */
    private DrugUseBatchBarBean formatDrug2BatchStatic(List<Batch2DrugUseBarBean> batch2DrugUseBarList) {
        DrugUseBatchBarBean drugUseBatchBarBean = new DrugUseBatchBarBean();

        List<DrugUse2BatchBarBean> drugUse2BatchList = drugUseBatchBarBean.getDrugUse2BatchList();
        List<String> batchNameList = null;
        for (Batch2DrugUseBarBean batch2DrugUseBarBean : batch2DrugUseBarList) {
            // 批次药品分类数量列表
            List<DrugClassCount> drugClassBatchCountList = batch2DrugUseBarBean.getDrugClassBatchCountList();

            for (DrugClassCount drugClassCount : drugClassBatchCountList) {

                DrugUse2BatchBarBean drugUse2BatchBar = null;
                for (DrugUse2BatchBarBean drugUse2BatchBarItem : drugUse2BatchList) {
                    if (drugUse2BatchBarItem.getDrugClassName().equals(drugClassCount.getDrugClassName())) {
                        drugUse2BatchBar = drugUse2BatchBarItem;
                    }
                }
                if (drugUse2BatchBar == null) {
                    drugUse2BatchBar = new DrugUse2BatchBarBean();
                    drugUse2BatchBar.setDrugClassName(drugClassCount.getDrugClassName());
                    drugUse2BatchList.add(drugUse2BatchBar);
                }

                if (drugClassCount.getStstsCount() == null) {
                    drugUse2BatchBar.addYdCountList(0);
                } else {
                    drugUse2BatchBar.addYdCountList(drugClassCount.getStstsCount());
                }
                batchNameList = drugUseBatchBarBean.getBatchNameList();

                if (!batchNameList.contains(batch2DrugUseBarBean.getBatchNAME())) {
                    drugUseBatchBarBean.getBatchNameList().add(batch2DrugUseBarBean.getBatchNAME());
                }
            }
        }

        return drugUseBatchBarBean;
    }

    /**
     * 格式化 批次->药物使用->药品分类数量 维度的统计
     *
     * @param drugUseBarGroupByBatchList
     * @return
     */
    private List<Batch2DrugUseBarBean> formatBatch2StatusStatic(List<DrugUseBarGroupByBatch> drugUseBarGroupByBatchList) {
        //格式化为批次->药单状态->药单数量 维度的统计
        List<Batch2DrugUseBarBean> batch2DrugUseBarList = new ArrayList<Batch2DrugUseBarBean>();
        for (DrugUseBarGroupByBatch drugUseBarGroupByBatch : drugUseBarGroupByBatchList) {
            Batch2DrugUseBarBean batch2DrugUseBar = null;
            for (Batch2DrugUseBarBean batch2DrugUseBarBean : batch2DrugUseBarList) {
                //批次是否相同
                if (batch2DrugUseBarBean.getBatchNAME().equals(drugUseBarGroupByBatch.getBatchNAME())) {
                    batch2DrugUseBar = batch2DrugUseBarBean;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (batch2DrugUseBar == null) {
                batch2DrugUseBar = new Batch2DrugUseBarBean();
                batch2DrugUseBar.setBatchNAME(drugUseBarGroupByBatch.getBatchNAME());
                batch2DrugUseBarList.add(batch2DrugUseBar);
            }

            DrugClassCount drugClassCount = new DrugClassCount();
            drugClassCount.setDrugClassName(drugUseBarGroupByBatch.getDrugClassName());
            drugClassCount.setStstsCount(drugUseBarGroupByBatch.getStstsCount());
            batch2DrugUseBar.addDrugClassBatchCountList(drugClassCount);
        }

        // 数据转换完成后，对某一批次不存在的分类，数量默认0
        for (Batch2DrugUseBarBean bean : batch2DrugUseBarList) {
            List<DrugClassCount> drugClassBatchCountList = bean.getDrugClassBatchCountList();

            // 获取当前所有的分类
            List<String> drugList = new ArrayList<String>();

            for (DrugClassCount drugClassCount : drugClassBatchCountList) {
                drugList.add(drugClassCount.getDrugClassName());
            }

            // 获取缺失的药品分类
            List<String> drugAllList = new ArrayList<String>();
            for (String drugClassName : drugClassList) {
                drugAllList.add(drugClassName);
            }

            drugAllList.removeAll(drugList);
            DrugClassCount drugClassCount = null;

            for (String drugClassName : drugAllList) {
                drugClassCount = new DrugClassCount();
                drugClassCount.setDrugClassName(drugClassName);
                drugClassCount.setStstsCount(0);
                bean.addDrugClassBatchCountList(drugClassCount);
            }
        }

        return batch2DrugUseBarList;
    }

    @Override
    public List<DrugUseBarGroupByDept> queryDeptBarList(@Param("condition")
                                                                PrescriptionSearchBean condition) {
        return drugUseDAO.queryDeptBarList(condition);
    }

    @Override
    public DrugUseDeptBarBean queryBarGroupByDeptList(PrescriptionSearchBean condition) {
        List<DrugUseBarGroupByDept> drugUseBarGroupByDeptList = queryDeptBarList(condition);

        if (drugUseBarGroupByDeptList == null) {
            return null;
        }
        drugClassList = new ArrayList<String>();
        // 获取所有药品分类
        for (DrugUseBarGroupByDept drugUseBarGroupByDept : drugUseBarGroupByDeptList) {
            if (!drugClassList.contains(drugUseBarGroupByDept.getDrugClassName())) {
                drugClassList.add(drugUseBarGroupByDept.getDrugClassName());
            }
        }

        // 格式化 批次->药物使用->药品分类数量 维度的统计
        List<Dept2DrugUseBarBean> dept2DrugUseBarList = formatDept2DrugUseStatic(drugUseBarGroupByDeptList);

        // 批次名称列表
        DrugUseDeptBarBean drugUseDeptBarBean = formatDrug2DeptStatic(dept2DrugUseBarList);
        drugUseDeptBarBean.setDrugClassList(drugClassList);
        drugUseDeptBarBean.setCostMap(getDeptCostMap(drugUseBarGroupByDeptList));
        return drugUseDeptBarBean;
    }

    private Map<String, String> getDeptCostMap(List<DrugUseBarGroupByDept> drugUseBarGroupByDeptList) {
        DecimalFormat df = new DecimalFormat("#.##");

        // key = 病区+","+药品分类  value=金额
        Map<String, String> costMap = new HashMap<String, String>();

        String wardName = null;
        String drugClassName = null;
        String key = null;
        double cost = 0;
        for (DrugUseBarGroupByDept drugUseBarGroupByDept : drugUseBarGroupByDeptList) {
            wardName = drugUseBarGroupByDept.getWardName();
            drugClassName = drugUseBarGroupByDept.getDrugClassName();
            key = wardName + "," + drugClassName;

            cost = drugUseBarGroupByDept.getStstsCost();

            costMap.put(key, df.format(cost));

        }

        return costMap;
    }

    /**
     * 格式化  药物使用统计->病区-> 维度的统计
     * 病区名称列表
     *
     * @param dept2DrugUseBarList
     * @return
     */
    private DrugUseDeptBarBean formatDrug2DeptStatic(List<Dept2DrugUseBarBean> dept2DrugUseBarList) {
        DrugUseDeptBarBean drugUseDeptBarBean = new DrugUseDeptBarBean();

        List<DrugUse2DeptBarBean> drugUse2DeptList = drugUseDeptBarBean.getDrugUse2DeptList();
        List<String> deptNameList = null;
        for (Dept2DrugUseBarBean dept2DrugUseBarBean : dept2DrugUseBarList) {
            // 病区药品分类数量列表
            List<DrugClassCount> drugClassDeptCountList = dept2DrugUseBarBean.getDrugClassDeptCountList();

            for (DrugClassCount drugClassCount : drugClassDeptCountList) {

                DrugUse2DeptBarBean drugUse2DeptBar = null;
                for (DrugUse2DeptBarBean drugUse2DeptBarItem : drugUse2DeptList) {
                    if (drugUse2DeptBarItem.getDrugClassName().equals(drugClassCount.getDrugClassName())) {
                        drugUse2DeptBar = drugUse2DeptBarItem;
                    }
                }
                if (drugUse2DeptBar == null) {
                    drugUse2DeptBar = new DrugUse2DeptBarBean();
                    drugUse2DeptBar.setDrugClassName(drugClassCount.getDrugClassName());
                    drugUse2DeptList.add(drugUse2DeptBar);
                }

                if (drugClassCount.getStstsCount() == null) {
                    drugUse2DeptBar.addYdCountList(0);
                } else {
                    drugUse2DeptBar.addYdCountList(drugClassCount.getStstsCount());
                }
                deptNameList = drugUseDeptBarBean.getWardNameList();

                if (!deptNameList.contains(dept2DrugUseBarBean.getWardName())) {
                    drugUseDeptBarBean.getWardNameList().add(dept2DrugUseBarBean.getWardName());
                }
            }
        }

        return drugUseDeptBarBean;
    }

    /**
     * 格式化 病区->药物使用->药品分类数量 维度的统计
     *
     * @param drugUseBarGroupByDeptList
     * @return
     */
    private List<Dept2DrugUseBarBean> formatDept2DrugUseStatic(List<DrugUseBarGroupByDept> drugUseBarGroupByDeptList) {
        //格式化为病区->药单状态->药单数量 维度的统计
        List<Dept2DrugUseBarBean> dept2DrugUseBarList = new ArrayList<Dept2DrugUseBarBean>();
        for (DrugUseBarGroupByDept drugUseBarGroupByDept : drugUseBarGroupByDeptList) {
            Dept2DrugUseBarBean dept2DrugUseBar = null;
            for (Dept2DrugUseBarBean dept2DrugUseBarBean : dept2DrugUseBarList) {
                //病区是否相同
                if (dept2DrugUseBarBean.getWardName().equals(drugUseBarGroupByDept.getWardName())) {
                    dept2DrugUseBar = dept2DrugUseBarBean;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (dept2DrugUseBar == null) {
                dept2DrugUseBar = new Dept2DrugUseBarBean();
                dept2DrugUseBar.setWardName(drugUseBarGroupByDept.getWardName());
                dept2DrugUseBarList.add(dept2DrugUseBar);
            }

            DrugClassCount drugClassCount = new DrugClassCount();
            drugClassCount.setDrugClassName(drugUseBarGroupByDept.getDrugClassName());
            drugClassCount.setStstsCount(drugUseBarGroupByDept.getStstsCount());
            dept2DrugUseBar.addDrugClassDeptCountList(drugClassCount);
        }

        // 数据转换完成后，对某一病区不存在的分类，数量默认0
        for (Dept2DrugUseBarBean bean : dept2DrugUseBarList) {
            List<DrugClassCount> drugClassDeptCountList = bean.getDrugClassDeptCountList();

            // 获取当前所有的分类
            List<String> drugList = new ArrayList<String>();

            for (DrugClassCount drugClassCount : drugClassDeptCountList) {
                drugList.add(drugClassCount.getDrugClassName());
            }

            // 获取缺失的药品分类
            List<String> drugAllList = new ArrayList<String>();
            for (String drugClassName : drugClassList) {
                drugAllList.add(drugClassName);
            }

            drugAllList.removeAll(drugList);
            DrugClassCount drugClassCount = null;

            for (String drugClassName : drugAllList) {
                drugClassCount = new DrugClassCount();
                drugClassCount.setDrugClassName(drugClassName);
                drugClassCount.setStstsCount(0);
                bean.addDrugClassDeptCountList(drugClassCount);
            }
        }

        return dept2DrugUseBarList;
    }
}
