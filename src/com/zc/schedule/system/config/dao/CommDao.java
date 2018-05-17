package com.zc.schedule.system.config.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.system.config.entity.Dict;
import com.zc.schedule.system.config.entity.ErrLog;
import com.zc.schedule.system.config.entity.Log;

/**
 * 排班模块公共DAO
 *
 * @author Justin
 * @version v1.0
 */
@MyBatisRepository("commDao")
public interface CommDao {

    /**
     * 字典查询
     *
     * @param map
     * @return List
     */
    List<Dict> qryDicByMap(@Param("map") Map<String, Object> map);

    /**
     * 记录操作日志
     *
     * @param log
     */
    void addLog(Log log);

    /**
     * 记录错误日志到数据表
     *
     * @param errLog
     * @return Integer
     */
    Integer addErrLog(ErrLog errLog);

}
