package com.zc.pivas.medicaments.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicaments.entity.MedicamentsRefLabel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * 药品与标签
 *
 * @author Ray
 * @version 1.0
 *
 */
@MyBatisRepository("medicamentsRefLabelDao")
public interface MedicamentsRefLabelDao
{
    /**
     * 根据条件分页查询药品标签
     * @param paging
     * @param medicamentsRefLabel
     * @return
     */
    List<MedicamentsRefLabel> getMedicamentsRefLabel(@Param("paging")
    JqueryStylePaging paging, @Param("medicamentsRefLabel")
    MedicamentsRefLabel medicamentsRefLabel);

    /**
     * 获取分页总数
     * @param medicamentsRefLabel
     * @param date
     * @return
     */
    Integer getMedicamentsRefLabelTotal(@Param("medicamentsRefLabel")
    MedicamentsRefLabel medicamentsRefLabel, @Param("date")
    Date date);

    /**
     * 删除药品标签
     * @param medicamentsRefLabel
     */
    void delMedicamentsRefLabel(MedicamentsRefLabel medicamentsRefLabel);

    /**
     * 添加药品标签
     * @param medicamentsRefLabel
     */
    void addMedicamentsRefLabel(MedicamentsRefLabel medicamentsRefLabel);
    
}
