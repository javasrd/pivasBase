package com.zc.base.sys.modules.user.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.modules.user.entity.Role;

import java.util.List;

public abstract interface RoleService {
    public abstract JqueryStylePagingResults<Role> getRole(Role paramRole, JqueryStylePaging paramJqueryStylePaging)
            throws Exception;

    public abstract void addRole(Role paramRole);

    public abstract void updateRole(Role paramRole);

    public abstract void delRole(String paramString);

    public abstract Role getRoleID(Role paramRole);

    public abstract List<Role> getRolesByUserId(Long paramLong1, Long paramLong2);

    public abstract boolean checkExists(List<Long> paramList);
}
