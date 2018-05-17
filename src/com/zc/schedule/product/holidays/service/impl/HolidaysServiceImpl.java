package com.zc.schedule.product.holidays.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zc.schedule.common.base.BaseLogService;
import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SpringUtil;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.holidays.dao.HolidaysDao;
import com.zc.schedule.product.holidays.entity.Holidays;
import com.zc.schedule.product.holidays.service.HolidaysService;
import com.zc.schedule.product.personnel.entity.Personnel;

@Service
public class HolidaysServiceImpl extends BaseLogService implements HolidaysService {

    /**
     * 节假日管理数据交互接口
     */
    @Resource
    private HolidaysDao holidaysDao;

    @Override
    public List<Personnel> getForPage(Map<String, Object> params, PageInfo page) {
        return holidaysDao.getForPage(params, page);
    }

    @Override
    public ResultData save(Holidays holidays) {
        String opr = "add".equals(holidays.getOpr()) ? "新增节假日" : "修改节假日";

        //判断名称是否重复
        Integer isRepeat = holidaysDao.checkName(holidays);
        if (isRepeat != null && isRepeat == 1) {

            addLogFail(SpringUtil.getRequest(),
                    SysConstant.SCHEDULEMGR,
                    SysConstant.节假日管理,
                    opr + "[名称=" + holidays.getName() + "]：名称重复");

            return DataFormat.formatAdd(-1, "节假日名称重复！");
        }

        //判断日期段是否重复
        isRepeat = holidaysDao.checkDate(holidays);
        if (isRepeat != null && isRepeat == 1) {

            addLogFail(SpringUtil.getRequest(),
                    SysConstant.SCHEDULEMGR,
                    SysConstant.节假日管理,
                    opr + "[名称=" + holidays.getName() + "]：时间重复");

            return DataFormat.formatAdd(-1, "节假日时间重复！");
        }

        if ("add".equals(holidays.getOpr())) //新增
        {
            holidaysDao.save(holidays);
        } else {
            holidaysDao.update(holidays);
        }

        addLogSucc(SpringUtil.getRequest(),
                SysConstant.SCHEDULEMGR,
                SysConstant.节假日管理,
                opr + "[名称=" + holidays.getName() + "]");

        return DataFormat.formatAdd(1, opr + "成功");
    }

    @Override
    public ResultData getById(Long id) {
        Holidays h = holidaysDao.getById(id);
        if (h != null) {
            return DataFormat.formatSucc(h);
        } else {
            return DataFormat.formatFail();
        }
    }

    @Override
    public ResultData del(String ids) {
        StringBuffer names = new StringBuffer(128);
        Holidays h = null;

        for (String id : ids.split(",")) {
            if (StringUtils.isNotEmpty(id)) {
                h = holidaysDao.getById(Long.valueOf(id));
                names.append(h.getName()).append(",");

                holidaysDao.delById(Long.valueOf(id));
            }
        }

        addLogSucc(SpringUtil.getRequest(),
                SysConstant.SCHEDULEMGR,
                SysConstant.节假日管理,
                "删除节假日[名称=" + names.toString().substring(0, names.length() - 1) + "]");

        return DataFormat.formatDel(1, "删除成功！");
    }

}
