package com.zc.pivas.drugway.service;


import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.drugway.bean.DrugWayBean;

/**
 * 用药途径
 *
 * @author Ray
 * @version 1.0
 */
public interface DrugWayService {

    /**
     * 按条件分页查询
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<DrugWayBean> getDrugWay(DrugWayBean bean, JqueryStylePaging jquryStylePaging) throws Exception;

    DrugWayBean getDrugWayByCondition(DrugWayBean bean);

    void addDrugWay(DrugWayBean bean);

    void updateDrugWay(DrugWayBean bean);

    void synData(DrugWayBean bean);
}
