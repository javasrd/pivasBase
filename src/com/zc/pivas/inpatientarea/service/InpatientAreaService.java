package com.zc.pivas.inpatientarea.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;

import java.util.List;

/**
 * 病区
 *
 * @author Ray
 * @version 1.0
 */
public interface InpatientAreaService {

    /**
     * 按条件分页查询病区列表
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<InpatientAreaBean> getInpatientAreaList(InpatientAreaBean bean, JqueryStylePaging jquryStylePaging) throws Exception;

    List<InpatientAreaBean> getInpatientAreaBeanList(InpatientAreaBean inpatientArea, JqueryStylePaging jqueryStylePaging);

    /**
     * 添加病区数据
     *
     * @param bean
     */
    void addInpatientArea(InpatientAreaBean bean);

    /**
     * 修改病区数据
     *
     * @param bean
     */
    void updateInpatientArea(InpatientAreaBean bean);

    /**
     * 获取所有病区信息
     *
     * @return
     */
    List<InpatientAreaBean> queryInpatientAreaAllList();

    List<InpatientAreaBean> queryInpatientAreaInAll();

    /**
     * 查询所有病区信息用于统计：药单
     *
     * @return
     */
    List<InpatientAreaBean> queryInpatientAreaAllListForYDStatistic();

    InpatientAreaBean getInpatientAreaBean(InpatientAreaBean bean);

    void synData(InpatientAreaBean bean);

}
