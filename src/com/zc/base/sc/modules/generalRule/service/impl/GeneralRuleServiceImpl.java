package com.zc.base.sc.modules.generalRule.service.impl;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.common.service.BaseServiceImpl;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.generalRule.entity.GeneralRule;
import com.zc.base.sc.modules.generalRule.entity.RuleRefBatch;
import com.zc.base.sc.modules.generalRule.repository.GeneralRuleDao;
import com.zc.base.sc.modules.generalRule.repository.RuleRefBatchDao;
import com.zc.base.sc.modules.generalRule.service.GeneralRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GeneralRuleServiceImpl extends BaseServiceImpl<GeneralRule, Long> implements GeneralRuleService {
    @Resource
    private GeneralRuleDao generalRuleDao;
    @Resource
    private RuleRefBatchDao ruleRefBatchDao;

    public BaseMapper<GeneralRule, Long> getDao() {
        return this.generalRuleDao;
    }

    public List<GeneralRule> queryByPaging(JqueryStylePaging paging, GeneralRule condition) {
        List<GeneralRule> list = super.queryByPaging(paging, condition);
        for (GeneralRule generalRule : list) {
            List<RuleRefBatch> refList = this.ruleRefBatchDao.selectByRuleId(generalRule.getId());
            generalRule.setRefBatchNum(Integer.valueOf(refList.size()));
        }
        return list;
    }

    public GeneralRule selectByPrimaryKey(Long modelPK) {
        GeneralRule rule = (GeneralRule) super.selectByPrimaryKey(modelPK);
        if (rule != null) {
            List<RuleRefBatch> refList = this.ruleRefBatchDao.selectByRuleId(rule.getId());
            rule.setRuleRefBatchList(refList);
        }
        return rule;
    }

    public void afterInsert(GeneralRule model) {
        Long ruleId = model.getId();
        List<RuleRefBatch> ruleRefBatchs = model.getRuleRefBatchList();
        for (RuleRefBatch ruleRefBatch : ruleRefBatchs) {
            ruleRefBatch.setRuleId(ruleId);
            this.ruleRefBatchDao.insert(ruleRefBatch);
        }
    }

    public void beforeUpdate(GeneralRule model) {
        this.ruleRefBatchDao.deleteByRuleId(model.getId());
    }

    public void afterUpdate(GeneralRule model) {
        afterInsert(model);
    }

    public void deleteByPrimaryKey(Long modelPK) {
        this.ruleRefBatchDao.deleteByRuleId(modelPK);
        super.deleteByPrimaryKey(modelPK);
    }

    public boolean isRepeatFreqAndKeyword(GeneralRule rule) {
        int count = this.generalRuleDao.isRepeatFreqAndKeyword(rule);
        return count != 0;
    }
}
