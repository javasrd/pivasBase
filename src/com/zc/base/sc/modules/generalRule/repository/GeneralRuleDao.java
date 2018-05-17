package com.zc.base.sc.modules.generalRule.repository;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sc.modules.generalRule.entity.GeneralRule;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public abstract interface GeneralRuleDao extends BaseMapper<GeneralRule, Long> {
    public abstract int isRepeatFreqAndKeyword(@Param("rule") GeneralRule paramGeneralRule);
}
