package com.zc.base.sc.modules.generalRule.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sc.modules.generalRule.entity.RuleRefBatch;

import java.util.List;

@MyBatisRepository
public abstract interface RuleRefBatchDao {
    public abstract void deleteByRuleId(Long paramLong);

    public abstract void insert(RuleRefBatch paramRuleRefBatch);

    public abstract List<RuleRefBatch> selectByRuleId(Long paramLong);
}
