package com.zc.pivas.statistics.service.impl;

import com.zc.pivas.statistics.bean.prescription.*;
import com.zc.pivas.statistics.repository.PrescriptionDao;
import com.zc.pivas.statistics.service.PrescriptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 药单统计Service实现
 *
 * @author jagger
 * @version 1.0
 */
@Service("medicSingleService")
public class PrescriptionServiceImpl implements PrescriptionService {

    @Resource
    private PrescriptionDao prescriptionDao;

    /**
     * 按批次查询药单状态
     *
     * @param medicSingleSearch
     * @return
     */
    @Override
    public BatchStatusBarBean queryBatchStatusBar(PrescriptionSearchBean medicSingleSearch) {
        List<PrescriptionStatusBean> batchStatusList = prescriptionDao.queryBatchStatusList(medicSingleSearch);
        if (batchStatusList == null) {
            return null;
        }
        //格式化为批次->药单状态->药单数量 维度的统计
        List<Batch2StatusBean> batch2StatusList = formatBatch2StatusStatic(batchStatusList);
        //格式化 药单状态->批次-> 维度的统计
        // 批次名称列表
        BatchStatusBarBean batchStatusBarBean = formatStatus2BatchStatic(batch2StatusList);
        return batchStatusBarBean;
    }

    /**
     * 格式化  药单状态->批次-> 维度的统计
     *
     * @param Batch2StatusList
     * @return
     */
    private BatchStatusBarBean formatStatus2BatchStatic(List<Batch2StatusBean> Batch2StatusList) {
        BatchStatusBarBean batchStatusBarBean = new BatchStatusBarBean();
        List<Status2BatchBean> Status2BatchList = batchStatusBarBean.getStatus2BatchList();
        for (int i = 0; i < 4; i++) {
            Status2BatchBean status2BatchNew = new Status2BatchBean();
            status2BatchNew.setStatusKey(i);
            Status2BatchList.add(status2BatchNew);
        }
        for (Batch2StatusBean batch2StatusBean : Batch2StatusList) {
            List<YDStatusCount> ydStatusCountList = batch2StatusBean.getYdStatusCountList();

            for (Status2BatchBean status2BatchItem : Status2BatchList) {
                YDStatusCount ydStatusCountDesc = null;
                for (YDStatusCount ydStatusCountItem : ydStatusCountList) {
                    if (status2BatchItem.getStatusKey() != null
                            && status2BatchItem.getStatusKey().equals(ydStatusCountItem.getStatusKey())) {
                        ydStatusCountDesc = ydStatusCountItem;
                    }
                }

                if (ydStatusCountDesc == null) {

                    status2BatchItem.addYdBatchCountList(0);

                } else {
                    status2BatchItem.addYdBatchCountList(ydStatusCountDesc.getStstsCount());
                }

            }
            batchStatusBarBean.getBatchNameList().add(batch2StatusBean.getBatchNAME());
        }
        return batchStatusBarBean;
    }

    /**
     * 格式化 批次->药单状态->药单数量 维度的统计
     *
     * @param batchStatusList
     * @return
     */
    private List<Batch2StatusBean> formatBatch2StatusStatic(List<PrescriptionStatusBean> batchStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计
        List<Batch2StatusBean> Batch2StatusList = new ArrayList<Batch2StatusBean>();
        for (PrescriptionStatusBean batchStatusItem : batchStatusList) {
            Batch2StatusBean batch2StatusDesc = null;
            for (Batch2StatusBean batch2StatusItem : Batch2StatusList) {
                //批次是否相同
                if (batch2StatusItem.getZxbc() != null && batchStatusItem.getZxbc() != null
                        && batch2StatusItem.getZxbc().intValue() == batchStatusItem.getZxbc().intValue()) {
                    batch2StatusDesc = batch2StatusItem;
                    break;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (batch2StatusDesc == null) {
                batch2StatusDesc = new Batch2StatusBean();
                batch2StatusDesc.setBatchNAME(batchStatusItem.getBatchNAME());
                batch2StatusDesc.setZxbc(batchStatusItem.getZxbc());
                Batch2StatusList.add(batch2StatusDesc);
            }

            YDStatusCount ydStatusCount = new YDStatusCount();
            ydStatusCount.setStatusKey(batchStatusItem.getStatusKey());
            ydStatusCount.setStstsCount(batchStatusItem.getStstsCount());
            batch2StatusDesc.addYdStatusCountList(ydStatusCount);
        }
        return Batch2StatusList;
    }

    @Override
    public List<YDBatchPieBean> queryBatchPieList(PrescriptionSearchBean medicSingleSearch) {
        return prescriptionDao.queryBatchPieList(medicSingleSearch);
    }

    @Override
    public List<PrescriptionStatusBean> queryBatchStatusListByID(PrescriptionSearchBean medicSingleSearch) {

        return prescriptionDao.queryBatchStatusListByID(medicSingleSearch);
    }

    /**
     * 按病区查询药单状态
     *
     * @param medicSingleSearch
     * @return
     */
    @Override
    public DeptStatusBarBean queryDeptStatusBar(PrescriptionSearchBean medicSingleSearch) {
        List<PrescriptionStatusBean> deptStatusList = prescriptionDao.queryDeptStatusList(medicSingleSearch);
        if (deptStatusList == null) {
            return null;
        }
        //格式化为病区->药单状态->药单数量 维度的统计
        List<Dept2StatusBean> dept2StatusList = formatDept2StatusStatic(deptStatusList);
        //格式化 药单状态->病区-> 维度的统计
        // 批次名称列表
        DeptStatusBarBean batchStatusBarBean = formatStatus2DeptStatic(dept2StatusList);
        return batchStatusBarBean;
    }

    /**
     * 格式化 药单状态->病区-> 维度的统计
     *
     * @param dept2StatusList
     * @return
     */
    private DeptStatusBarBean formatStatus2DeptStatic(List<Dept2StatusBean> dept2StatusList) {
        DeptStatusBarBean deptStatusBarBean = new DeptStatusBarBean();
        List<Status2DeptBean> status2DeptList = deptStatusBarBean.getStatus2DeptList();
        for (int i = 0; i < 4; i++) {
            Status2DeptBean status2DeptNew = new Status2DeptBean();
            status2DeptNew.setStatusKey(i);
            status2DeptList.add(status2DeptNew);
        }

        for (Dept2StatusBean dept2StatusBean : dept2StatusList) {
            if (dept2StatusBean.getDeptName() == null) {
                continue;
            }

            List<YDStatusCount> ydStatusCountList = dept2StatusBean.getYdStatusCountList();

            for (Status2DeptBean status2DeptItem : status2DeptList) {
                // Status2DeptBean status2DeptDesc = null;
                YDStatusCount ydStatusCountDesc = null;
                for (YDStatusCount ydStatusCountItem : ydStatusCountList) {
                    if (status2DeptItem.getStatusKey() != null
                            && status2DeptItem.getStatusKey().equals(ydStatusCountItem.getStatusKey())) {
                        ydStatusCountDesc = ydStatusCountItem;
                        break;
                    }
                }
                if (ydStatusCountDesc == null) {
                    status2DeptItem.addYdDeptCountList(0);
                } else {
                    status2DeptItem.addYdDeptCountList(ydStatusCountDesc.getStstsCount());
                }

            }
            deptStatusBarBean.getDeptNameList().add(dept2StatusBean.getDeptName());

        }
        return deptStatusBarBean;
    }

    /**
     * 格式化为批次->药单状态->药单数量 维度的统计
     *
     * @param deptStatusList
     * @return
     */
    private List<Dept2StatusBean> formatDept2StatusStatic(List<PrescriptionStatusBean> deptStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计 
        List<Dept2StatusBean> dept2StatusList = new ArrayList<Dept2StatusBean>();
        for (PrescriptionStatusBean deptStatusItem : deptStatusList) {
            Dept2StatusBean dept2StatusDesc = null;
            for (Dept2StatusBean dept2StatusItem : dept2StatusList) {
                //病区是否相同
                if (deptStatusItem.getDeptName() != null
                        && deptStatusItem.getDeptName().equals(dept2StatusItem.getDeptName())) {
                    dept2StatusDesc = dept2StatusItem;
                    break;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (dept2StatusDesc == null) {
                dept2StatusDesc = new Dept2StatusBean();
                dept2StatusDesc.setDeptName(deptStatusItem.getDeptName());
                dept2StatusList.add(dept2StatusDesc);
            }

            YDStatusCount ydStatusCount = new YDStatusCount();
            ydStatusCount.setStatusKey(deptStatusItem.getStatusKey());
            ydStatusCount.setStstsCount(deptStatusItem.getStstsCount());
            dept2StatusDesc.addYdStatusCountList(ydStatusCount);
        }
        return dept2StatusList;
    }

    @Override
    public List<YDDeptPieBean> queryDeptPieList(PrescriptionSearchBean medicSingleSearch) {
        return prescriptionDao.queryDeptPieList(medicSingleSearch);
    }

    @Override
    public List<PrescriptionStatusBean> queryDeptStatusListByName(PrescriptionSearchBean medicSingleSearch) {

        return prescriptionDao.queryDeptStatusListByName(medicSingleSearch);
    }

}
