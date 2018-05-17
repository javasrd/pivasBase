package com.zc.pivas.configfee.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.configfee.bean.ConfigFeeTypeBean;

import java.util.List;

/**
 * 配置费类别service
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigFeeTypeService {
    /***
     * 查询配置费/材料费
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<ConfigFeeTypeBean> getConfigFeeTypeLsit(ConfigFeeTypeBean bean,
                                                                     JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加配置费/材料费
     *
     * @param bean 配置费/材料费
     */
    void addConfigFeeType(ConfigFeeTypeBean bean);

    /**
     * 查询类型
     *
     * @return 审核错误类型
     */
    ConfigFeeTypeBean getConfigFeeType(ConfigFeeTypeBean bean);

    /**
     * 修改配置费/材料费
     *
     * @param bean 需要修改的数据
     */
    void updateConfigFeeType(ConfigFeeTypeBean bean);

    /***
     * 删除配置费/材料费
     * @param gid 主键id
     */
    void delConfigFeeType(String gid);

    /**
     * 根据bean查询对应的列表
     *
     * @param bean 查询条件
     * @return 列表数据
     */
    List<ConfigFeeTypeBean> getConfigFeeTypes(ConfigFeeTypeBean bean);

    /**
     * 修改的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkCondigFeeTypeName(ConfigFeeTypeBean bean);

}
