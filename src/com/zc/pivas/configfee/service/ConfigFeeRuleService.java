package com.zc.pivas.configfee.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.configfee.bean.ConfigFeeRuleBean;

import java.util.List;

/**
 * 配置费用规则Service
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigFeeRuleService {
    /***
     * 分页查询配置费
     *
     * @param bean
     *            对象
     * @param jquryStylePaging
     *            分页参数
     * @return 分页数据
     * @exception Exception
     *                e
     */
    JqueryStylePagingResults<ConfigFeeRuleBean> getConfigFeeRuleLsit(ConfigFeeRuleBean bean,
                                                                     JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加配置费
     *
     * @param bean 配置费
     */
    void addConfigFeeRule(ConfigFeeRuleBean bean);

    /**
     * 修改配置费
     *
     * @param bean 需要修改的数据
     */
    void updateConfigFeeRule(ConfigFeeRuleBean bean);

    /**
     * 查询配置费数据
     *
     * @return 审核错误配置费
     */
    ConfigFeeRuleBean getConfigFeeRule(ConfigFeeRuleBean bean);

    /**
     * 查询配置费数据[根据药品分类]
     *
     * @return 审核错误配置费
     */
    List<ConfigFeeRuleBean> getConfigFeeRuleByInfo(ConfigFeeRuleBean bean);

    /***
     * 删除配置费表数据
     *
     * @param id
     *            主键id
     */
    void delConfigFeeRule(String id);

    /**
     * 修改判断规则名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkRuleName(ConfigFeeRuleBean bean);

    /**
     * 判断药品分类是否已被使用
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkDrugClass(ConfigFeeRuleBean bean);

    List<ConfigFeeRuleBean> checkIsExist(ConfigFeeRuleBean bean);
}
