package com.zc.base.sc.modules.generalRule.service;

import com.zc.base.common.service.BaseService;
import com.zc.base.sc.modules.generalRule.entity.GeneralRule;

public interface GeneralRuleService extends BaseService<GeneralRule, Long> {
    boolean isRepeatFreqAndKeyword(GeneralRule paramGeneralRule);
}
