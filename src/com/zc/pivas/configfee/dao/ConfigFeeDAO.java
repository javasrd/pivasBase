package com.zc.pivas.configfee.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.configfee.bean.ConfigFeeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置费/材料费DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("configFeeDAO")
public interface ConfigFeeDAO {
    /***
     * 分页查询配置费/材料费
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<ConfigFeeBean> getConfigFeeList(@Param("configFee")
                                                 ConfigFeeBean bean, @Param("paging")
                                                 JqueryStylePaging jqueryStylePaging);

    /**
     * 查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getConfigFeeTotal(@Param("configFee")
                                  ConfigFeeBean bean);

    /**
     * 添加配置费/材料费
     *
     * @param bean 配置费/材料费
     */
    void addConfigFee(ConfigFeeBean bean);

    /**
     * 修改配置费/材料费
     *
     * @param bean 配置费/材料费
     */
    void updateConfigFee(ConfigFeeBean bean);

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    ConfigFeeBean getConfigFee(@Param("configFee")
                                       ConfigFeeBean bean);

    /***
     * 删除配置费/材料费
     * @param costcode 主键id
     */
    void delConfigFee(String costcode);

    /**
     * 根据bean查询对应的列表
     *
     * @param bean 查询条件
     * @return 列表数据
     */
    List<ConfigFeeBean> getConfigFees(@Param("configFee")
                                              ConfigFeeBean bean);

    /**
     * getConfigFeeForUPdate
     *
     * @param bean
     * @return
     */
    ConfigFeeBean getConfigFeeForUPdate(@Param("configFee")
                                                ConfigFeeBean bean);
}
