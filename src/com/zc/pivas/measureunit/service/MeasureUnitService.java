package com.zc.pivas.measureunit.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.measureunit.bean.MeasureUnitBean;

/**
 * 计量单位
 *
 * @author  cacabin
 * @version  1.0
 */
public interface MeasureUnitService {
    /***
     * 分页查询计量单位数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<MeasureUnitBean> getMeasureUnitLsit(MeasureUnitBean bean,
                                                                 JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加计量单位数据
     *
     * @param bean 核对类型
     */
    void addMeasureUnit(MeasureUnitBean bean);

    /**
     * 修改计量单位数据
     *
     * @param bean 修改数据
     */
    void updateMeasureUnit(MeasureUnitBean bean);

    /***
     * 删除计量单位数据
     * @param gid 编码
     */
    void delMeasureUnit(String gid);

    /**
     * 查询数据
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    MeasureUnitBean getMeasureUnit(MeasureUnitBean bean);

    /**
     * 查询编码是否存在
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    boolean checkMeasureUnitCode(MeasureUnitBean bean);
}
