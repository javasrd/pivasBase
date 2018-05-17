package com.zc.schedule.product.holidays.service;

import java.util.List;
import java.util.Map;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.product.holidays.entity.Holidays;
import com.zc.schedule.product.personnel.entity.Personnel;

public interface HolidaysService {

    /**
     * 列表
     *
     * @param params
     * @param page
     * @return List
     */
    List<Personnel> getForPage(Map<String, Object> params, PageInfo page);

    /**
     * 保存节假日的新增或修改
     *
     * @param holidays
     * @return ResultData
     */
    ResultData save(Holidays holidays);

    /**
     * 查询单个节假日
     *
     * @param id
     * @return ResultData
     */
    ResultData getById(Long id);

    /**
     * 删除节假日
     *
     * @param ids
     * @return ResultData
     */
    ResultData del(String ids);

}
