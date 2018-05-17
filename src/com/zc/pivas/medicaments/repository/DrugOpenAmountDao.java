package com.zc.pivas.medicaments.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicaments.entity.DrugOpenAmount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品DAO
 *
 * @author Jack.M
 * @version 1.0
 */
@MyBatisRepository("drugOpenAmountDao")
public interface DrugOpenAmountDao
{
    /**
     * 插入一条记录
     * 
     * @param model
     */
    void insert(DrugOpenAmount model);
    
    /**
     * 通过主键修改(全部修改)
     * 
     * @param model
     */
    void updateByPrimaryKey(DrugOpenAmount model);
    
    /**
     * 分页查询
     * 
     * @param paging
     * @param condition
     * @return
     */
    List<DrugOpenAmount> queryByPaging(@Param("paging")
    JqueryStylePaging paging, @Param("condition") DrugOpenAmount condition);
    
    /**
     * 查询总数
     * 
     * @param condition
     * @return
     */
    int getTotalCount(@Param("condition") DrugOpenAmount condition);
    
    /**
     * 根据条件查询所有
     * 
     * @param condition
     * @return
     */
    List<DrugOpenAmount> queryAllByCondition(@Param("condition") DrugOpenAmount condition);
    
    /**
     * 保存拆药量信息 
     * 
     * @param drugOpenAmount
     */
    void synData(DrugOpenAmount drugOpenAmount);
}
