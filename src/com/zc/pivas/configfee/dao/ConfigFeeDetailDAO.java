package com.zc.pivas.configfee.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.configfee.bean.ConfigFeeDetailBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置费明细规则DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("configFeeDetailDAO")
public interface ConfigFeeDetailDAO {
    /***
     * 分页查询配置费明细
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<ConfigFeeDetailBean> getConfigFeeDetailList(@Param("configFeeDetail")
                                                             ConfigFeeDetailBean bean, @Param("paging")
                                                             JqueryStylePaging jqueryStylePaging);

    /**
     * 根据规则编码查询对应的规则明细
     *
     * @param detailCode 规则编码
     * @return 明细列表
     */
    List<ConfigFeeDetailBean> getConfigFeeDetailListByDetailCode(String detailCode);

    /**
     * 查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getConfigFeeDetailTotal(@Param("configFeeDetail")
                                        ConfigFeeDetailBean bean);

    /**
     * 添加配置费明细
     *
     * @param bean 配置费明细
     */
    void addConfigFeeDetail(ConfigFeeDetailBean bean);

    /**
     * 修改配置费明细表
     *
     * @param bean 配置费明细
     */
    void updateConfigFeeDetail(ConfigFeeDetailBean bean);

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    ConfigFeeDetailBean getConfigFeeDetail(@Param("configFeeDetail")
                                                   ConfigFeeDetailBean bean);

    /***
     * 删除配置费明细
     */
    void delConfigFeeDetail(String detailCode);
}
