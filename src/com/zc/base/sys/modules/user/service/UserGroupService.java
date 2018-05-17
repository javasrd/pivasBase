package com.zc.base.sys.modules.user.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.entity.UserGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract interface UserGroupService {
    public abstract void addGroupInfo(UserGroup paramUserGroup);

    public abstract void editGroupInfo(UserGroup paramUserGroup);

    public abstract void deleteGroupInfo(Long[] paramArrayOfLong);

    public abstract UserGroup queryGroupInfo(Long paramLong);

    public abstract JqueryStylePagingResults<UserGroup> getUserGroup(UserGroup paramUserGroup, JqueryStylePaging paramJqueryStylePaging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    public abstract List<User> queryUserMember(UserGroup paramUserGroup);

    public abstract List<UserGroup> queryPartGroupInfo(Long paramLong1, Long paramLong2);

    public abstract void addGroupAndUser(Long paramLong, List<Long> paramList1, List<Long> paramList2);

    public abstract Long[] queryAllGroupMemberId(Long paramLong, int paramInt);
}
