package com.zc.pivas.statistics.service.impl;

import com.zc.pivas.statistics.bean.configFee.*;
import com.zc.pivas.statistics.repository.STConfigFeeDao;
import com.zc.pivas.statistics.service.ConfigFeeStatsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置费收费统计Service实现
 *
 * @author jagger
 * @version 1.0
 */
@Service("stConfigFeeService")
public class ConfigFeeStatsServiceImpl implements ConfigFeeStatsService {

    @Resource
    private STConfigFeeDao stConfigFeeDao;

    @Override
    public ConfigFeeBatchStatusBarBean queryBatchStatusBar(ConfigFeeSearchBean configFeeSearch) {
        List<ConfigFeeStatusBean> batchStatusList = stConfigFeeDao.queryBatchStatusList(configFeeSearch);
        if (batchStatusList == null) {
            return null;
        }

        //格式化为批次->药单状态->药单数量 维度的统计
        List<ConfigFeeBatch2StatusBean> batch2StatusList = formatBatch2StatusStatic(batchStatusList);
        //格式化 药单状态->批次-> 维度的统计
        // 批次名称列表
        ConfigFeeBatchStatusBarBean batchStatusBarBean =
                formatStatus2BatchStatic(batch2StatusList, configFeeSearch.getCompareResult());
        return batchStatusBarBean;
    }

    /**
     * 格式化  药单状态->批次-> 维度的统计
     *
     * @param batch2StatusList
     * @param compareResult
     * @return
     */
    private ConfigFeeBatchStatusBarBean formatStatus2BatchStatic(List<ConfigFeeBatch2StatusBean> batch2StatusList,
                                                                 String compareResult) {
        ConfigFeeBatchStatusBarBean batchStatusBarBean = new ConfigFeeBatchStatusBarBean();
        List<ConfigFeeStatus2BatchBean> status2BatchList = batchStatusBarBean.getStatus2BatchList();
        List<Integer> configFeeType = stConfigFeeDao.getConfigFeeTypeList(compareResult);
        for (Integer statusKeyItem : configFeeType) {
            ConfigFeeStatus2BatchBean status2BatchBeanNew = new ConfigFeeStatus2BatchBean();
            status2BatchBeanNew.setStatusKey(statusKeyItem);
            status2BatchList.add(status2BatchBeanNew);
        }
        for (ConfigFeeBatch2StatusBean batch2StatusBean : batch2StatusList) {
            List<ConfigFeeStatusCount> cfStatusCountList = batch2StatusBean.getCfStatusCountList();
            for (ConfigFeeStatus2BatchBean status2BatchItem : status2BatchList) {
                ConfigFeeStatusCount cfStatusCountDesc = null;
                for (ConfigFeeStatusCount cfStatusCountItem : cfStatusCountList) {
                    if (status2BatchItem.getStatusKey() != null
                            && status2BatchItem.getStatusKey().equals(cfStatusCountItem.getStatusKey())) {
                        cfStatusCountDesc = cfStatusCountItem;
                        break;
                    }
                }

                if (cfStatusCountDesc == null) {
                    status2BatchItem.addCfBatchCountList((float) 0);
                } else {
                    status2BatchItem.addCfBatchCountList(cfStatusCountDesc.getStstsCount());
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
    private List<ConfigFeeBatch2StatusBean> formatBatch2StatusStatic(List<ConfigFeeStatusBean> batchStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计
        List<ConfigFeeBatch2StatusBean> batch2StatusList = new ArrayList<ConfigFeeBatch2StatusBean>();
        for (ConfigFeeStatusBean batchStatusItem : batchStatusList) {
            ConfigFeeBatch2StatusBean batch2StatusDesc = null;
            for (ConfigFeeBatch2StatusBean batch2StatusItem : batch2StatusList) {
                //批次是否相同
                if (batch2StatusItem.getBatchNAME().equals(batchStatusItem.getBatchNAME())) {
                    batch2StatusDesc = batch2StatusItem;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (batch2StatusDesc == null) {
                batch2StatusDesc = new ConfigFeeBatch2StatusBean();
                batch2StatusDesc.setBatchNAME(batchStatusItem.getBatchNAME());
                batch2StatusDesc.setZxbc(batchStatusItem.getZxbc());
                batch2StatusList.add(batch2StatusDesc);
            }

            ConfigFeeStatusCount cfStatusCount = new ConfigFeeStatusCount();
            cfStatusCount.setStatusKey(batchStatusItem.getStatusKey());
            cfStatusCount.setStstsCount(batchStatusItem.getStstsCount());
            batch2StatusDesc.addCfStatusCountList(cfStatusCount);
        }
        return batch2StatusList;
    }

    @Override
    public List<ConfigFeeBatchPieBean> queryBatchPieList(ConfigFeeSearchBean configFeeSearch)
    //public List<ConfigFeeSearchBean> queryBatchPieList(ConfigFeeSearchBean configFeeSearch)
    {
        return stConfigFeeDao.queryBatchPieList(configFeeSearch);
    }

    @Override
    public List<ConfigFeeStatusBean> queryBatchStatusListByID(ConfigFeeSearchBean configFeeSearch) {

        return stConfigFeeDao.queryBatchStatusListByID(configFeeSearch);
    }

    @Override
    public ConfigFeeDeptStatusBarBean queryDeptStatusBar(ConfigFeeSearchBean configFeeSearch) {
        List<ConfigFeeStatusBean> deptStatusList = stConfigFeeDao.queryDeptStatusList(configFeeSearch);
        if (deptStatusList == null) {
            return null;
        }

        //格式化为病区->药单状态->药单数量 维度的统计
        List<ConfigFeeDept2StatusBean> dept2StatusList = formatDept2StatusStatic(deptStatusList);
        //格式化 药单状态->病区-> 维度的统计
        // 批次名称列表
        ConfigFeeDeptStatusBarBean batchStatusBarBean =
                formatStatus2DeptStatic(dept2StatusList, configFeeSearch.getCompareResult());
        return batchStatusBarBean;
    }

    /**
     * 格式化 药单状态->病区-> 维度的统计
     *
     * @param dept2StatusList
     * @param compareResult
     * @return
     */
    private ConfigFeeDeptStatusBarBean formatStatus2DeptStatic(List<ConfigFeeDept2StatusBean> dept2StatusList,
                                                               String compareResult) {
        ConfigFeeDeptStatusBarBean deptStatusBarBean = new ConfigFeeDeptStatusBarBean();
        List<ConfigFeeStatus2DeptBean> status2DeptList = deptStatusBarBean.getStatus2DeptList();
        List<Integer> configFeeType = stConfigFeeDao.getConfigFeeTypeList(compareResult);
        for (Integer statusKeyItem : configFeeType) {
            ConfigFeeStatus2DeptBean status2DeptNew = new ConfigFeeStatus2DeptBean();
            status2DeptNew.setStatusKey(statusKeyItem);
            status2DeptList.add(status2DeptNew);
        }

        for (ConfigFeeDept2StatusBean dept2StatusBean : dept2StatusList) {
            if (dept2StatusBean.getDeptName() == null) {
                continue;
            }

            List<ConfigFeeStatusCount> cfStatusCountList = dept2StatusBean.getCfStatusCountList();
            for (ConfigFeeStatus2DeptBean status2DeptItem : status2DeptList) {
                ConfigFeeStatusCount cfStatusCountDesc = null;
                for (ConfigFeeStatusCount cfStatusCountItem : cfStatusCountList) {
                    if (status2DeptItem.getStatusKey() != null
                            && status2DeptItem.getStatusKey().equals(cfStatusCountItem.getStatusKey())) {
                        cfStatusCountDesc = cfStatusCountItem;
                    }
                }
                if (cfStatusCountDesc == null) {
                    status2DeptItem.addCfDeptCountList((float) 0);
                } else {
                    status2DeptItem.addCfDeptCountList(cfStatusCountDesc.getStstsCount());
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
    private List<ConfigFeeDept2StatusBean> formatDept2StatusStatic(List<ConfigFeeStatusBean> deptStatusList) {
        //格式化为批次->药单状态->药单数量 维度的统计 
        List<ConfigFeeDept2StatusBean> dept2StatusList = new ArrayList<ConfigFeeDept2StatusBean>();
        for (ConfigFeeStatusBean deptStatusItem : deptStatusList) {
            ConfigFeeDept2StatusBean dept2StatusDesc = null;
            for (ConfigFeeDept2StatusBean dept2StatusItem : dept2StatusList) {
                //病区是否相同
                if (deptStatusItem.getDeptName() != null
                        && deptStatusItem.getDeptName().equals(dept2StatusItem.getDeptName())) {
                    dept2StatusDesc = dept2StatusItem;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (dept2StatusDesc == null) {
                dept2StatusDesc = new ConfigFeeDept2StatusBean();
                dept2StatusDesc.setDeptName(deptStatusItem.getDeptName());
                dept2StatusList.add(dept2StatusDesc);
            }

            ConfigFeeStatusCount cfStatusCount = new ConfigFeeStatusCount();
            cfStatusCount.setStatusKey(deptStatusItem.getStatusKey());
            cfStatusCount.setStstsCount(deptStatusItem.getStstsCount());
            dept2StatusDesc.addCfStatusCountList(cfStatusCount);
        }
        return dept2StatusList;
    }

    @Override
    public List<ConfigFeeDeptPieBean> queryDeptPieList(ConfigFeeSearchBean configFeeSearch) {
        return stConfigFeeDao.queryDeptPieList(configFeeSearch);
    }

    @Override
    public List<ConfigFeeStatusBean> queryDeptStatusListByName(ConfigFeeSearchBean configFeeSearch) {

        return stConfigFeeDao.queryDeptStatusListByName(configFeeSearch);
    }

}
