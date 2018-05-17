package com.zc.base.sys.modules.system.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sys.modules.system.entity.SysDict;

import java.util.List;

@MyBatisRepository("blaDictDao")
public abstract interface SysDictDao {
    public abstract List<SysDict> getAllBLADict() throws Exception;
}
