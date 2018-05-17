package com.zc.base.common.dao;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public abstract interface BaseMapper<T extends AbstractDO, PK extends Serializable> {
    public abstract void deleteByPrimaryKey(PK paramPK);

    public abstract void insert(T paramT);

    public abstract void insertSelective(T paramT);

    public abstract T selectByPrimaryKey(PK paramPK);

    public abstract void updateByPrimaryKeySelective(T paramT);

    public abstract void updateByPrimaryKey(T paramT);

    public abstract List<T> queryByPaging(@Param("paging") JqueryStylePaging paramJqueryStylePaging, @Param("condition") T paramT);

    public abstract int getTotalCount(@Param("condition") T paramT);

    public abstract List<T> queryAllByCondition(@Param("condition") T paramT);
}
