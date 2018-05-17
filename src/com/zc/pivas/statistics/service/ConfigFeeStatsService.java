package com.zc.pivas.statistics.service;

import com.zc.pivas.statistics.bean.configFee.*;

import java.util.List;

/**
 * 配置费收费统计Service
 *
 * @author jagger
 * @version 1.0
 */
public interface ConfigFeeStatsService {

    ConfigFeeBatchStatusBarBean queryBatchStatusBar(ConfigFeeSearchBean configFeeSearch);

    List<ConfigFeeBatchPieBean> queryBatchPieList(ConfigFeeSearchBean configFeeSearch);

    List<ConfigFeeStatusBean> queryBatchStatusListByID(ConfigFeeSearchBean configFeeSearch);

    ConfigFeeDeptStatusBarBean queryDeptStatusBar(ConfigFeeSearchBean ConfigFeeSearch);

    List<ConfigFeeDeptPieBean> queryDeptPieList(ConfigFeeSearchBean configFeeSearch);

    List<ConfigFeeStatusBean> queryDeptStatusListByName(ConfigFeeSearchBean configFeeSearch);
}
