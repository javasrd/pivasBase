package com.zc.pivas.app.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.app.bean.AppUserLoginBean;
import org.apache.ibatis.annotations.Param;

/**
 * APP登录dao
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("AppUserLoginDao")
public interface AppUserLoginDao {
    AppUserLoginBean QueryAppUserLoginInfo(@Param("useraccount") String useraccount);

    void InsertAppUser(@Param("bean") AppUserLoginBean bean);

    void UpdateAppUser(@Param("bean") AppUserLoginBean bean);

    void DeleteAppUser(@Param("useraccount") String useraccount);
}
