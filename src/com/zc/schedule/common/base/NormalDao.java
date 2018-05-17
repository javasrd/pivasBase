package com.zc.schedule.common.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zc.schedule.common.entity.PageInfo;

/**
 * 基础DAO层
 *
 * @author Justin
 * @version v1.0
 */
public interface NormalDao<T> {

    /**
     * 查询分页数据
     *
     * @param map
     * @param page
     * @return List
     */
    List<T> qryList(@Param("map") Map<String, Object> map, PageInfo page);

    /**
     * 查询数据
     *
     * @param map
     * @return List
     */
    List<T> qryList(@Param("map") Map<String, Object> map);

    /**
     * 添加一行数据
     *
     * @param t
     * @return Integer
     */
    Integer insert(@Param("row") T t);

    /**
     * 更新数据
     *
     * @param t
     * @return Integer
     */
    Integer update(@Param("row") Map<String, Object> t);

    /**
     * 删除数据
     *
     * @param t
     * @return Integer
     */
    Integer delete(@Param("row") Map<String, Object> t);

}
