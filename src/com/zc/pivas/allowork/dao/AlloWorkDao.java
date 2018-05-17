package com.zc.pivas.allowork.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.allowork.bean.AlloWorkPieBean;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置工作量统计
 *
 * @author jagger
 * @version 1.0
 */
@MyBatisRepository("alloWorkDao")
public interface AlloWorkDao {
    /**
     * 查询配置药师信息
     *
     * @return
     */
    List<StaticDoctorNameBean> queryAlloWorkNameList();

    /**
     * 饼图
     *
     * @param condition
     * @return
     */
    List<AlloWorkPieBean> queryPieList(@Param("condition") PrescriptionSearchBean condition);


    /**
     * 饼图,详情
     *
     * @param condition
     * @return
     */
    List<AlloWorkPieBean> queryDetailPieList(@Param("condition") PrescriptionSearchBean condition);

    /**
     * 查询所有配置费分类
     *
     * @param condition
     * @return
     */
    List<String> queryAllRuleNames(@Param("condition") PrescriptionSearchBean condition);

    /**
     * 查询饼状图列表
     *
     * @param condition
     * @return
     */
    List<AlloWorkPieBean> queryBarList(@Param("condition") PrescriptionSearchBean condition);

}
