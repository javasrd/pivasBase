package com.zc.pivas.statistics.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.statistics.bean.configFee.ConfigFeeBatchPieBean;
import com.zc.pivas.statistics.bean.configFee.ConfigFeeDeptPieBean;
import com.zc.pivas.statistics.bean.configFee.ConfigFeeSearchBean;
import com.zc.pivas.statistics.bean.configFee.ConfigFeeStatusBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置费报表DAO
 *
 * @author jagger
 * @version 1.0
 */
@MyBatisRepository("configFeeDao")
public interface STConfigFeeDao {

    /**
     * 查询
     *
     * @param medicSingle
     * @return
     */
    List<ConfigFeeStatusBean> queryBatchStatusList(@Param("configFee") ConfigFeeSearchBean medicSingle);

    /**
     * 查询批次统计信息
     *
     * @param configFee
     * @return
     */
    List<ConfigFeeBatchPieBean> queryBatchPieList(@Param("configFee") ConfigFeeSearchBean configFee);

    /**
     * 根据批次ID 查询批次的药单状态统计
     *
     * @param configFee
     * @return
     */
    List<ConfigFeeStatusBean> queryBatchStatusListByID(@Param("configFee") ConfigFeeSearchBean configFee);

    /**
     * 查询病区 药单状态统计信息
     *
     * @param configFee
     * @return
     */
    List<ConfigFeeStatusBean> queryDeptStatusList(@Param("configFee") ConfigFeeSearchBean configFee);

    /**
     * 查询病区统计信息
     *
     * @return
     */
    List<ConfigFeeDeptPieBean> queryDeptPieList(@Param("configFee") ConfigFeeSearchBean configFee);

    /**
     * 根据病区名称，查询病区 药单状态统计信息
     *
     * @param configFee
     * @return
     */
    List<ConfigFeeStatusBean> queryDeptStatusListByName(@Param("configFee") ConfigFeeSearchBean configFee);

    /**
     * 获取用到的配置费类型列表
     *
     * @param compareResult
     * @return
     */
    List<Integer> getConfigFeeTypeList(@Param("compareResult") String compareResult);

}
