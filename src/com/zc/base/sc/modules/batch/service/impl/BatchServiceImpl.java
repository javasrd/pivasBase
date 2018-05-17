package com.zc.base.sc.modules.batch.service.impl;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.common.service.BaseServiceImpl;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.repository.BatchDao;
import com.zc.base.sc.modules.batch.service.BatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("BatchService")
public class BatchServiceImpl extends BaseServiceImpl<Batch, Long> implements BatchService {
    @Resource
    private BatchDao batchDao;

    public BaseMapper<Batch, Long> getDao() {
        return this.batchDao;
    }

    public List<Batch> queryByPaging(JqueryStylePaging paging, Batch condition) {
        return this.batchDao.queryByPaging(paging, condition);
    }

    public int getTotalCount(Batch condition) {
        return this.batchDao.getTotalCount(condition);
    }

    public int batchRefRule(Long batchId) {
        return this.batchDao.batchRefRule(batchId);
    }

    public List<Batch> queryBatchAllList() {
        return this.batchDao.queryBatchAllList();
    }

    public int queryBatchRepeat(Long batchId, String num, String name) {
        return this.batchDao.queryBatchRepeat(batchId, num, name);
    }

    public List<Batch> queryBatchAllListForYDStatistic() {
        return this.batchDao.queryBatchAllListForYDStatistic();
    }


    public List<Batch> queryBatchByVolume() {
        return this.batchDao.queryBatchByVolume();
    }


    public List<Batch> queryBatchByForce() {
        return this.batchDao.queryBatchByForce();
    }
}
