package com.zc.pivas.statistics.service;

import com.zc.pivas.statistics.bean.prescription.*;

import java.util.List;

/**
 * 药单统计Service
 *
 * @author jagger
 * @version 1.0
 */
public interface PrescriptionService {
    /**
     * 按批次查询药单状态
     *
     * @param medicSingleSearch
     * @return
     */
    BatchStatusBarBean queryBatchStatusBar(PrescriptionSearchBean medicSingleSearch);

    List<YDBatchPieBean> queryBatchPieList(PrescriptionSearchBean medicSingleSearch);

    List<PrescriptionStatusBean> queryBatchStatusListByID(PrescriptionSearchBean medicSingleSearch);

    /**
     * 按病区查询药单状态
     *
     * @param medicSingleSearch
     * @return
     */
    DeptStatusBarBean queryDeptStatusBar(PrescriptionSearchBean medicSingleSearch);

    List<YDDeptPieBean> queryDeptPieList(PrescriptionSearchBean medicSingleSearch);

    List<PrescriptionStatusBean> queryDeptStatusListByName(PrescriptionSearchBean medicSingleSearch);
}
