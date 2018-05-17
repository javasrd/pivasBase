package com.zc.schedule.product.holidays.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.product.holidays.entity.Holidays;
import com.zc.schedule.product.holidays.service.HolidaysService;
import com.zc.schedule.product.personnel.entity.Personnel;

/**
 * 节假日管理Controller
 *
 * @author Justin
 * @version v1.0
 */
@Controller
@RequestMapping("holidayMgr")
public class HolidaysController extends BaseController {

    /**
     * 节假日管理业务接口
     */
    @Resource
    private HolidaysService holidaysService;

    /**
     * 节假日管理首页
     *
     * @return String
     */
    @RequestMapping("index")
    public String init() {
        return "schedule/holiday/index";
    }

    /**
     * 列表查询
     *
     * @param params
     * @return ResultPage
     */
    @RequestMapping("getForPage")
    @ResponseBody
    public ResultPage getForPage(@RequestBody Map<String, Object> params) {
        PageInfo page = new PageInfo(params);
        List<Personnel> list = holidaysService.getForPage(params, page);
        return DataFormat.formatPageData(list, page);
    }

    /**
     * 保存节假日的新增或修改
     *
     * @param holidays
     * @return ResultData
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultData save(Holidays holidays) {
        return holidaysService.save(holidays);
    }

    /**
     * 查询单个节假日
     *
     * @param id
     * @return ResultData
     */
    @RequestMapping("getById")
    @ResponseBody
    public ResultData getById(Long id) {
        if (id == null || id < 0) {
            return DataFormat.formatFail();
        }
        return holidaysService.getById(id);

    }

    /**
     * 删除节假日
     *
     * @return ResultData
     */
    @RequestMapping("del")
    @ResponseBody
    public ResultData del(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return DataFormat.formatDel(-1, "参数错误");
        }
        return holidaysService.del(ids);
    }
}
