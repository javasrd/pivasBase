package com.zc.base.sys.modules.user.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.modules.user.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisRepository("userDao")
public abstract interface UserDao {
    public abstract void addUser(User paramUser);

    public abstract void addUserHis(UserHis paramUserHis);

    public abstract void addUserLogin(UserLogin paramUserLogin);

    public abstract List<User> getUser(@Param("user") User paramUser, @Param("paging") JqueryStylePaging paramJqueryStylePaging, @Param("date") Date paramDate, @Param("sysAdmin") String paramString);

    public abstract List<User> getAllUser(@Param("exclusiveAccount") String[] paramArrayOfString);

    public abstract Integer getUserTotal(@Param("user") User paramUser, @Param("date") Date paramDate, @Param("sysAdmin") String paramString);

    public abstract User getUserByAccount(String paramString);

    public abstract User getUserWithRolePrivsByAccount(String paramString);

    public abstract void updateUser(User paramUser);

    public abstract void deleteUserById(Long paramLong);

    public abstract void deleteUserHis(Long paramLong);

    public abstract void deleteUserLogin(Long paramLong);

    public abstract void addUserRole(Long paramLong1, Long paramLong2);

    public abstract void addArchitectureRefUser(String paramString);

    public abstract void updateUserPwd(Map<Object, Object> paramMap)
            throws Exception;

    public abstract User getUserID(Long paramLong);

    public abstract List<ArchitectureRefUser> getRefUserID(@Param("userId") Long paramLong);

    public abstract void addUserRefRole(UserRefRole paramUserRefRole);

    public abstract Integer getUserRefRole(@Param("userId") Long paramLong1, @Param("roleId") Long paramLong2);

    public abstract void deleteUserRefRole(@Param("userId") Long paramLong1, @Param("roleId") Long paramLong2);

    public abstract Integer getUserListID(@Param("user") User paramUser);

    public abstract List<User> getUserList(@Param("account") String paramString);

    public abstract void updateArchitectrueRefUser(ArchitectureRefUser paramArchitectureRefUser);

    public abstract void delUserId(@Param("userId") Long paramLong);

    public abstract List<UserRefRole> getUserIdRole(@Param("userId") Long paramLong);

    public abstract List<UserHis> getUserHis(@Param("account") String paramString);

    public abstract List<UserLogin> getUserLastLogin(@Param("userId") Long paramLong);

    public abstract void delUserRole(@Param("userId") Long paramLong, @Param("roleIds") List<Long> paramList);

    public abstract void addUserRoleBatch(@Param("userId") Long paramLong, @Param("roles") List<Long> paramList);

    public abstract void addUserSickRoomBatch(@Param("userId") Long paramLong, @Param("sickRoomIds") List<Long> paramList);

    public abstract void updatePassword(@Param("user") User paramUser);

    public abstract int checkOldPassword(Long paramLong, String paramString);

    public abstract void updateCurrUser(@Param("user") User paramUser);

    public abstract void updateUserHis(@Param("userHis") UserHis paramUserHis);

    public abstract User checkUser(@Param("user") User paramUser);

    public abstract List<Map<String, Object>> qryAreaListAll(@Param("map") Map<String, Object> paramMap);

    public abstract List<Map<String, Object>> qryGlAreaByUser(@Param("map") Map<String, Object> paramMap);

    public abstract Integer delGlAreaByUser(@Param("map") Map<String, Object> paramMap);

    public abstract Integer addGlAreaByUser(@Param("map") Map<String, Object> paramMap);
}
