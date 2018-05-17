package com.zc.pivas.allowork.service;

import com.zc.pivas.allowork.bean.AlloWorkBarBean;
import com.zc.pivas.allowork.dao.AlloWorkDao;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;

/**
 * 配置工作量统计服务层接口
 *
 * @author jagger
 * @version 1.0
 */
public interface AlloWorkService extends AlloWorkDao {
    /**
     * 查询柱状图数据
     *
     * @param condition
     * @return
     */
    AlloWorkBarBean querybarList(PrescriptionSearchBean condition);
}
