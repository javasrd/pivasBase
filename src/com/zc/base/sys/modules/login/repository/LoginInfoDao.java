package com.zc.base.sys.modules.login.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sys.modules.login.entity.LoginInfo;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository("loginInfoDao")
public abstract interface LoginInfoDao {
    public abstract void addLoginInfo(LoginInfo paramLoginInfo);

    public abstract LoginInfo getLoginInfoByUserId(@Param("userId") Long paramLong);

    public abstract void updateLoginInfo(LoginInfo paramLoginInfo);

    public abstract void unlockUser(Long paramLong);
}
