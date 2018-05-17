package com.zc.schedule.product.holidays.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.product.holidays.entity.Holidays;
import com.zc.schedule.product.personnel.entity.Personnel;

@MyBatisRepository
public interface HolidaysDao {
    /**
     * 列表
     *
     * @param params
     * @param page
     * @return List
     */
    List<Personnel> getForPage(@Param("params") Map<String, Object> params, PageInfo page);

    /**
     * 判断日期段是否重复
     *
     * @param holidays
     * @return Integer
     */
    Integer checkDate(@Param("h") Holidays holidays);

    /**
     * 保存新的节假日
     *
     * @param holidays
     * @return int
     */
    int save(@Param("h") Holidays holidays);

    /**
     * 更新节假日信息
     *
     * @param holidays
     * @return int
     */
    int update(@Param("h") Holidays holidays);

    /**
     * 查询单个节假日
     *
     * @param id
     * @return Holidays
     */
    Holidays getById(@Param("id") Long id);

    /**
     * 根据id删除节假日
     *
     * @param id
     */
    int delById(@Param("id") Long id);

    /**
     * 检查名称是否重复
     *
     * @param holidays
     * @return Integer
     */
    Integer checkName(@Param("h") Holidays holidays);

}
