package com.zc.base.sys.modules.user.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.modules.user.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository("roleDao")
public abstract interface RoleDao {
    public abstract List<Role> getRole(@Param("role") Role paramRole, @Param("paging") JqueryStylePaging paramJqueryStylePaging);

    public abstract void addRole(Role paramRole);

    public abstract void updateRole(Role paramRole);

    public abstract void delRole(Long paramLong);

    public abstract Integer getRoleTotal(@Param("role") Role paramRole);

    public abstract Role getRoleID(@Param("role") Role paramRole);

    public abstract Integer getRoleListID(@Param("role") Role paramRole);

    public abstract List<Role> getRoleNumber(@Param("role") Role paramRole, @Param("paging") JqueryStylePaging paramJqueryStylePaging);

    public abstract Integer getRoleNumberTotal(@Param("role") Role paramRole);

    public abstract Role queryRoleID(Long paramLong);

    public abstract List<Role> getRolesByUserId(Long paramLong1, Long paramLong2);

    public abstract int getRoleCount(List<Long> paramList);
}
