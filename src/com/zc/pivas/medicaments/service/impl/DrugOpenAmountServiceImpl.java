package com.zc.pivas.medicaments.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicaments.entity.DrugOpenAmount;
import com.zc.pivas.medicaments.repository.DrugOpenAmountDao;
import com.zc.pivas.medicaments.service.DrugOpenAmountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 拆药量
 *
 * @author Jack.M
 * @version 1.0
 */
@Service("drugOpenAmountService")
public class DrugOpenAmountServiceImpl implements DrugOpenAmountService
{
    @Resource
    private DrugOpenAmountDao drugOpenAmountDao;
    
    @Override
    public void insert(DrugOpenAmount model)
    {
        drugOpenAmountDao.insert(model);
    }
    
    @Override
    public void updateByPrimaryKey(DrugOpenAmount model)
    {
        drugOpenAmountDao.updateByPrimaryKey(model);
    }
    
    @Override
    public List<DrugOpenAmount> queryByPaging(JqueryStylePaging paging, DrugOpenAmount condition)
    {
        return drugOpenAmountDao.queryByPaging(paging, condition);
    }
    
    @Override
    public int getTotalCount(DrugOpenAmount condition)
    {
        return drugOpenAmountDao.getTotalCount(condition);
    }
    
    @Override
    public List<DrugOpenAmount> queryAllByCondition(DrugOpenAmount condition)
    {
        return drugOpenAmountDao.queryAllByCondition(condition);
    }

    @Override
    public void synData(DrugOpenAmount drugOpenAmount)
    {
        drugOpenAmountDao.synData(drugOpenAmount);
    }
}
