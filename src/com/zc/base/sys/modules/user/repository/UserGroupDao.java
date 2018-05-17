package com.zc.base.sys.modules.user.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.entity.UserGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository("userGroupDao")
public abstract interface UserGroupDao {
    public abstract void addGroupInfo(UserGroup paramUserGroup);

    public abstract void editGroupInfo(UserGroup paramUserGroup);

    public abstract void editGroupInfo(Long[] paramArrayOfLong);

    public abstract UserGroup queryGroupInfo(@Param("id") Long paramLong);

    public abstract List<UserGroup> getUserGroup(@Param("userGroup") UserGroup paramUserGroup, @Param("paging") JqueryStylePaging paramJqueryStylePaging);

    public abstract Integer getUserGroupTotal(@Param("userGroup") UserGroup paramUserGroup);

    public abstract void deleteGroupInfo(Long[] paramArrayOfLong);

    public abstract void deleteGroupAndUserInfo(Long[] paramArrayOfLong);

    public abstract List<User> queryUserMember(@Param("userGroup") UserGroup paramUserGroup);

    public abstract UserGroup verifyGroupByname(@Param("userGroup") UserGroup paramUserGroup);

    public abstract List<UserGroup> queryPartGroupInfo(Long paramLong1, Long paramLong2);

    public abstract void deleteGroupAndUser(@Param("userId") Long paramLong, @Param("cannotAddGroup") List<Long> paramList);

    public abstract void addGroupAndUser(@Param("userId") Long paramLong, @Param("demandGroup") List<Long> paramList);

    public abstract List<UserGroup> queryAllGroupMemberId(@Param("userId") Long paramLong);
}
