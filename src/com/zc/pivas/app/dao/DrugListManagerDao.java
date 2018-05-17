package com.zc.pivas.app.dao;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.app.bean.DrugListManagerBean;

/**
 * 
 * 药品列表dao
 *
 * @author  cacabin
 * @version  1.0
 */
@MyBatisRepository("DrugListManagerDao")
public interface DrugListManagerDao
{
    void updateDrugListManager(DrugListManagerBean drugListManagerBean);
    
    void updateDrugListMainManager(DrugListManagerBean drugListManagerBean);
}
