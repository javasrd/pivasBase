package com.zc.pivas.measureunit.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.measureunit.bean.MeasureUnitBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * SRVS_MEASUREMENT_UNIT:计量单位
 *
 * @author  cacabin
 * @version  1.0
 */
@MyBatisRepository("measureUnitDAO")
public interface MeasureUnitDAO
{
    /***
     * 分页查询表数据
     * @param bean 对象
     * @param jqueryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    List<MeasureUnitBean> getMeasureUnitList(@Param("measureUnit")
    MeasureUnitBean bean, @Param("paging")
    JqueryStylePaging jqueryStylePaging);
    
    /**
     * 分页查询总数
     * @param bean 查询参数
     * @return 页码
     */
    int getMeasureUnitTotal(@Param("measureUnit")
    MeasureUnitBean bean);
    
    /**
     *添加表数据
     * @param bean 
     */
    void addMeasureUnit(MeasureUnitBean bean);
    
    /**
     *修改表数据
     * @param bean 
     */
    void updateMeasureUnit(MeasureUnitBean bean);
    
    /***
     * 删除表数据
     * @param gid 主键id
     */
    void delMeasureUnit(String gid);
    
    /**
     * 查询数据
     * @param bean 名称
     * @return 审核错误类型
     */
    MeasureUnitBean getMeasureUnit(@Param("measureUnit")
    MeasureUnitBean bean);
    
    /**
     * 查询编码是否存在
     * @param bean 名称
     * @return 审核错误类型
     */
    MeasureUnitBean checkMeasureUnitCode(@Param("measureUnit")
    MeasureUnitBean bean);
}
