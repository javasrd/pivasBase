package com.zc.base.sys.common.tag.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sys.common.tag.entity.Vmapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public abstract interface SelectDao {
    public abstract List<Vmapping> selectByTableAndSearchCode(@Param("tableName") String paramString1, @Param("parentId") String paramString2, @Param("lauguage") String paramString3);
}
