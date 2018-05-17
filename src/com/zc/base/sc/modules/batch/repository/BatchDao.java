package com.zc.base.sc.modules.batch.repository;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sc.modules.batch.entity.Batch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public abstract interface BatchDao extends BaseMapper<Batch, Long> {
    public abstract int batchRefRule(@Param("batchId") Long paramLong);

    public abstract List<Batch> queryBatchAllList();

    public abstract List<Batch> queryBatchAllListForYDStatistic();

    public abstract int queryBatchRepeat(@Param("id") Long paramLong, @Param("num") String paramString1, @Param("name") String paramString2);

    public abstract List<Batch> queryBatchByVolume();

    public abstract List<Batch> queryBatchByForce();
}
