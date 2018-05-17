package com.zc.pivas.configfee.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.configfee.bean.ConfigFeeBean;

import java.util.List;

/**
 * 配置费/材料费Service
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigFeeService {
    /***
     * 分页查询配置费/材料费表数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<ConfigFeeBean> getConfigFeeLsit(ConfigFeeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加配置费/材料费表数据
     *
     * @param bean 配置费/材料费
     */
    void addConfigFee(ConfigFeeBean bean);

    /**
     * 修改配置费/材料费表数据
     *
     * @param bean 需要修改的数据
     */
    void updateConfigFee(ConfigFeeBean bean);

    /**
     * 根据条件查询数量
     *
     * @param bean 查询对象
     * @return 数量
     */
    int getConfigFeeTotal(ConfigFeeBean bean);

    /**
     * 查询配置费/材料费
     *
     * @return 审核错误配置费/材料费
     */
    ConfigFeeBean getConfigFee(ConfigFeeBean bean);

    /***
     * 删除配置费/材料费表数据
     * @param gid 主键id
     */
    void delConfigFee(String gid);

    /**
     * 根据bean查询对应的列表信息
     *
     * @param bean 查询条件
     * @return 列表数据
     */
    List<ConfigFeeBean> getConfigFees(ConfigFeeBean bean);

    /**
     * 修改的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkCondigFeeName(ConfigFeeBean bean);

    /**
     * 修改的时候判断编码是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkCondigFeeCode(ConfigFeeBean bean);
}
