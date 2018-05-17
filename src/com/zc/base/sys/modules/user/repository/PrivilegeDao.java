package com.zc.base.sys.modules.user.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sys.modules.user.entity.Privilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository("privilegeDao")
public abstract interface PrivilegeDao {
    public abstract void delRolePrivilegeId(@Param("roleId") Long paramLong);

    public abstract void delRolePrivilege(@Param("roleId") Long paramLong, @Param("pris") List<Long> paramList);

    public abstract List<Privilege> getPrivilegeListByRoleId(Long paramLong1, Long paramLong2, String paramString);

    public abstract void addRolePrivilegeBatch(@Param("roleId") Long paramLong, @Param("pris") List<Long> paramList);

    public abstract int getPrisCount(List<Long> paramList);

    public abstract List<Privilege> getMenuListByUser(@Param("userId") Long paramLong, @Param("sysCode") Integer paramInteger, @Param("language") String paramString);

    public abstract List<Privilege> getMenuListBySubSys(@Param("sysCode") Integer paramInteger, @Param("language") String paramString);

    public abstract List<Privilege> getPrivilegeListByUser(@Param("userId") Long paramLong);

    public abstract Privilege getMenuById(@Param("id") Long paramLong, @Param("language") String paramString);

    public abstract List<Integer> getSysCodeListByUser(@Param("userId") Long paramLong);

    public abstract int getCountById(Long paramLong)
            throws Exception;
}
