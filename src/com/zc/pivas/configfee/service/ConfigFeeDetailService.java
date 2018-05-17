package com.zc.pivas.configfee.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.configfee.bean.ConfigFeeDetailBean;

import java.util.List;

/**
 * 配置费明细用规则Service
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigFeeDetailService {
    /***
     * 查询配置费明细
     *
     * @param bean
     *            对象
     * @param jquryStylePaging
     *            分页参数
     * @return 分页数据
     * @exception Exception
     *                e
     */
    JqueryStylePagingResults<ConfigFeeDetailBean> getConfigFeeDetailLsit(ConfigFeeDetailBean bean,
                                                                         JqueryStylePaging jquryStylePaging)
            throws Exception;

    /***
     * 查询配置费明细表数据[DETAILCODE]
     *
     * @param detailcode
     *            String
     * @return 数据
     * @exception Exception
     *                e
     */
    public List<ConfigFeeDetailBean> getConfigFeeDetailListByDetailCode(String detailcode);

    /**
     * 添加配置费明细
     *
     * @param bean 配置费明细
     */
    void addConfigFeeDetail(ConfigFeeDetailBean bean);

    /**
     * 修改配置费明细
     *
     * @param bean 需要修改的数据
     */
    void updateConfigFeeDetail(ConfigFeeDetailBean bean);

    /**
     * 根据条件查询数量
     *
     * @param bean 查询对象
     * @return 数量
     */
    int getConfigFeeDetailTotal(ConfigFeeDetailBean bean);

    /**
     * 查询配置费明细
     *
     * @return 审核错误配置费明细
     */
    ConfigFeeDetailBean getConfigFeeDetail(ConfigFeeDetailBean bean);

    /***
     * 删除配置费明细
     *
     */
    void delConfigFeeDetail(String detailCode);
}
