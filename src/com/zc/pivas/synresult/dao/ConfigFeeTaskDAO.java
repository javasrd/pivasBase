package com.zc.pivas.synresult.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.synresult.bean.ConfigFeeTaskBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("configFeeTaskDAO")
public interface ConfigFeeTaskDAO {
    /**
     * 新增数据
     *
     * @param bean
     */
    void inster(ConfigFeeTaskBean bean);

    /**
     * 修改数据
     *
     * @param bean
     */
    void update(ConfigFeeTaskBean bean);

    /**
     * 删除数据
     *
     * @param bean
     */
    void delete(ConfigFeeTaskBean bean);

    /**
     * 查询特定数据
     *
     * @param condition 条件
     * @return
     */
    List<ConfigFeeTaskBean> qryTaskList(@Param("condition") String[] condition);
}
