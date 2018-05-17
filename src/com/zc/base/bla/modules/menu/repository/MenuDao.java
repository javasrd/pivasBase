package com.zc.base.bla.modules.menu.repository;

import com.zc.base.bla.modules.menu.entity.Privilege;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;

import java.util.List;

@MyBatisRepository("menuDao")
public abstract interface MenuDao {
    public abstract List<Privilege> getAllPrivileges() throws Exception;

    public abstract List<Privilege> getPrivilegesByUserId(String paramString) throws Exception;

    public abstract List<Privilege> getAllPrivilegesByParentId(String paramString) throws Exception;
}
