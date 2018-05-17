package com.zc.pivas.drugway.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.drugway.bean.DrugWayBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用药途径
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("drugWayDAO")
public interface DrugWayDAO {

    /**
     * 按条件分页查询
     *
     * @param bean
     * @param jqueryStylePaging
     * @return
     */
    List<DrugWayBean> getDrugWayList(@Param("drugWay") DrugWayBean bean, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     *
     * @param bean
     * @return
     */
    int getDrugWayTotal(@Param("drugWay") DrugWayBean bean);

    void addDrugWay(DrugWayBean bean);

    void updateDrugWay(DrugWayBean bean);

    DrugWayBean getDrugWayByCondition(@Param("drugWay") DrugWayBean bean);

    void synData(DrugWayBean bean);
}
