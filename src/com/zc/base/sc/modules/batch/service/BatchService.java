package com.zc.base.sc.modules.batch.service;

import com.zc.base.common.service.BaseService;
import com.zc.base.sc.modules.batch.entity.Batch;

import java.util.List;

public interface BatchService extends BaseService<Batch, Long> {
    int batchRefRule(Long paramLong);

    List<Batch> queryBatchAllList();

    int queryBatchRepeat(Long paramLong, String paramString1, String paramString2);

    List<Batch> queryBatchAllListForYDStatistic();

    List<Batch> queryBatchByVolume();

    List<Batch> queryBatchByForce();
}
