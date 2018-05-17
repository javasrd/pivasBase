package com.zc.base.common.service;

import com.zc.base.common.dao.AbstractDO;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends AbstractDO, PK extends Serializable> {

    void deleteByPrimaryKey(PK paramPK);

    void insert(T paramT);

    void insertSelective(T paramT);

    T selectByPrimaryKey(PK paramPK);

    void updateByPrimaryKeySelective(T paramT);

    void updateByPrimaryKey(T paramT);

    List<T> queryByPaging(JqueryStylePaging paramJqueryStylePaging, T paramT);

    int getTotalCount(T paramT);

    List<T> queryAllByCondition(T paramT);
}
