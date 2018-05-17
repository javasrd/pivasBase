package com.zc.base.common.service;

import com.zc.base.common.dao.AbstractDO;
import com.zc.base.common.dao.BaseMapper;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;

import java.io.Serializable;
import java.util.List;


public abstract class BaseServiceImpl<T extends AbstractDO, PK extends Serializable> implements BaseService<T, PK> {
    public abstract BaseMapper<T, PK> getDao();

    public List<T> queryByPaging(JqueryStylePaging paging, T condition) {
        return getDao().queryByPaging(paging, condition);
    }


    public int getTotalCount(T condition) {
        return getDao().getTotalCount(condition);
    }


    public List<T> queryAllByCondition(T condition) {
        return getDao().queryAllByCondition(condition);
    }


    public void deleteByPrimaryKey(PK modelPK) {
        getDao().deleteByPrimaryKey(modelPK);
    }


    public void insert(T model) {
        beforeInsert(model);
        getDao().insert(model);
        afterInsert(model);
    }


    public void insertSelective(T model) {
        beforeInsert(model);
        getDao().insertSelective(model);
        afterInsert(model);
    }


    public T selectByPrimaryKey(PK modelPK) {
        T d = getDao().selectByPrimaryKey(modelPK);
        return d;
    }


    public void updateByPrimaryKeySelective(T model) {
        beforeUpdate(model);
        getDao().updateByPrimaryKeySelective(model);
        afterUpdate(model);
    }


    public void updateByPrimaryKey(T model) {
        beforeUpdate(model);
        getDao().updateByPrimaryKey(model);
        afterUpdate(model);
    }

    public void beforeInsert(T model) {
    }

    public void afterInsert(T model) {
    }

    public void beforeUpdate(T model) {
    }

    public void afterUpdate(T model) {
    }
}
