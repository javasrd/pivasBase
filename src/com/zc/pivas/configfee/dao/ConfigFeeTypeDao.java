package com.zc.pivas.configfee.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.configfee.bean.ConfigFeeTypeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置费类别
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("configFeeTypeDao")
public interface ConfigFeeTypeDao {
    /***
     * 查询配置费类别
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<ConfigFeeTypeBean> getConfigFeeTypeList(@Param("configFeeType") ConfigFeeTypeBean bean,
                                                 @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getConfigFeeTypeTotal(@Param("configFeeType") ConfigFeeTypeBean bean);

    /**
     * 添加配置费类别
     *
     * @param bean 配置费/材料费
     */
    void addConfigFeeType(ConfigFeeTypeBean bean);

    /**
     * 修改配置费类别
     *
     * @param bean 配置费/材料费
     */
    void updateConfigFeeType(ConfigFeeTypeBean bean);

    /***
     * 删除配置费类别
     */
    void delConfigFeeType(String gid);

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    ConfigFeeTypeBean getConfigFeeType(@Param("configFeeType") ConfigFeeTypeBean bean);

    /**
     * 根据bean查询对应的列表
     *
     * @param bean 查询条件
     * @return 列表数据
     */
    List<ConfigFeeTypeBean> getConfigFeeTypes(@Param("configFeeType") ConfigFeeTypeBean bean);

    /**
     * @param bean
     * @return
     */
    ConfigFeeTypeBean getConfigFeeTypeForUpdate(@Param("configFeeType") ConfigFeeTypeBean bean);
}
