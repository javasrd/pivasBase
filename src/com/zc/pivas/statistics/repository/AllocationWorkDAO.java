package com.zc.pivas.statistics.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.pivas.statistics.bean.allocationWork.AllocationSQLBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 配置工作量Dao
 *
 * @author jagger
 * @version 1.0
 */
@MyBatisRepository("allocationWorkDAO")
public interface AllocationWorkDAO {
    /**
     * 获取所有的空批
     *
     * @return
     * @throws Exception
     */
    List<Batch> getBatchList() throws Exception;

    /**
     * 获取拆药数量
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<AllocationSQLBean> getWorkList(@Param("param") Map<String, Object> param) throws Exception;

}
