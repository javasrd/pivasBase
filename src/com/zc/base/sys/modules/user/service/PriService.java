package com.zc.base.sys.modules.user.service;

import com.zc.base.sys.modules.user.entity.Privilege;
import com.zc.base.sys.modules.user.entity.User;

import java.util.List;
import java.util.Map;

public abstract interface PriService {
    public abstract List<Privilege> getPrivilegeListByRoleId(Long paramLong1, Long paramLong2, String paramString);

    public abstract void addRolePrivilegeBatch(Long paramLong, List<Long> paramList1, List<Long> paramList2);

    public abstract boolean checkExists(List<Long> paramList);

    public abstract List<Privilege> getMenuListByUser(Long paramLong, Integer paramInteger, String paramString);

    public abstract List<Privilege> getMenuListBySubSys(Integer paramInteger, String paramString);

    public abstract Map<Long, List<Privilege>> getMenuMapByUser(Long paramLong, Integer paramInteger, String paramString);

    public abstract List<Privilege> getPrivilegeListByUser(User paramUser);

    public abstract Privilege getMenuById(Long paramLong, String paramString);

    public abstract String getNavigationByMenuId(Long paramLong, String paramString);

    public abstract List<Integer> getSysCodeListByUser(Long paramLong);
}
