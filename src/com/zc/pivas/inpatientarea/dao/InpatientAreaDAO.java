package com.zc.pivas.inpatientarea.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 病区
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("inpatientAreaDAO")
public interface InpatientAreaDAO {
    /**
     * 按条件分页查询病区列表
     *
     * @param inpatientArea
     * @param jqueryStylePaging
     * @return
     */
    List<InpatientAreaBean> getInpatientAreaList(@Param("inpatientArea") InpatientAreaBean inpatientArea, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 获取分页总数
     *
     * @param inpatientArea
     * @return
     */
    int getInpatientAreaTotal(@Param("inpatientArea") InpatientAreaBean inpatientArea);

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

    void synData(InpatientAreaBean bean);

    /**
     * 获取所有病区
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

    /**
     * 查询审核类型数据
     *
     * @param bean
     * @return
     */
    InpatientAreaBean getInpatientAreaBean(@Param("inpatientArea") InpatientAreaBean bean);


}
