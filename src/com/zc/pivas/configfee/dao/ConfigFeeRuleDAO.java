package com.zc.pivas.configfee.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.configfee.bean.ConfigFeeRuleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置费规则DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("configFeeRuleDAO")
public interface ConfigFeeRuleDAO {
    /***
     * 分页查询配置费
     *
     * @param bean
     *            对象
     * @return 分页数据
     * @exception Exception
     *                e
     */
    List<ConfigFeeRuleBean> getConfigFeeRuleList(@Param("configFeeRule")
                                                         ConfigFeeRuleBean bean, @Param("paging")
                                                         JqueryStylePaging jqueryStylePaging);

    /**
     * 查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getConfigFeeRuleTotal(@Param("configFeeRule")
                                      ConfigFeeRuleBean bean);

    /**
     * 添加配置费
     *
     * @param bean 配置费
     */
    void addConfigFeeRule(ConfigFeeRuleBean bean);

    /**
     * 修改配置费
     *
     * @param bean 配置费
     */
    void updateConfigFeeRule(ConfigFeeRuleBean bean);

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    ConfigFeeRuleBean getConfigFeeRule(@Param("configFeeRule")
                                               ConfigFeeRuleBean bean);

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    List<ConfigFeeRuleBean> getConfigFeeRuleByInfo(@Param("configFeeRule")
                                                           ConfigFeeRuleBean bean);

    /***
     * 删除配置费表数据
     *
     * @param gid
     *            主键id
     */
    void delConfigFeeRule(String gid);

    /**
     * 修改判断规则名称是否存在
     *
     * @param bean 查询条件
     * @return ConfigFeeRuleBean
     */
    ConfigFeeRuleBean checkConfigFeeRuleName(@Param("configFeeRule")
                                                     ConfigFeeRuleBean bean);

    /**
     * 判断药品分类是否已被使用
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    ConfigFeeRuleBean checkDrugClass(@Param("configFeeRule")
                                             ConfigFeeRuleBean bean);

    List<ConfigFeeRuleBean> checkIsExist(@Param("configFeeRule") ConfigFeeRuleBean bean);
}
