package com.zc.base.sys.modules.user.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.modules.user.dto.UserLoginVO;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.entity.UserHis;
import com.zc.base.sys.modules.user.entity.UserLogin;
import com.zc.base.sys.modules.user.entity.UserRefRole;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public abstract interface UserService {
    public static final AtomicInteger ENTER_COUNT = new AtomicInteger(0);


    public static final Map<String, HttpSession> MAP = new ConcurrentHashMap();

    public abstract void addUser(User paramUser);

    public abstract List<User> getUser(User paramUser, JqueryStylePaging paramJqueryStylePaging, Date paramDate, String paramString);

    public abstract JqueryStylePagingResults<User> getUser(User paramUser, JqueryStylePaging paramJqueryStylePaging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    public abstract List<User> getAllUser(String[] paramArrayOfString);

    public abstract User getUserByAccount(String paramString);

    public abstract void updateUser(User paramUser);

    public abstract void deleteUserById(String paramString);

    public abstract String updateUserPwd(User paramUser, String paramString1, String paramString2)
            throws Exception;

    public abstract User getUserID(Long paramLong);

    public abstract String addUserRefRole(String paramString1, String paramString2);

    public abstract List<UserHis> getUserHis(@Param("account") String paramString);

    public abstract Integer getUserListID(User paramUser);

    public abstract List<UserRefRole> getUserIdRole(Long paramLong);

    public abstract void addRoleByUser(Long paramLong, List<Long> paramList1, List<Long> paramList2);

    public abstract void addUserSickRoom(Long paramLong, List<Long> paramList);

    public abstract void unlockUser(Long paramLong);

    public abstract void updatePassword(User paramUser)
            throws Exception;

    public abstract boolean checkOldPassword(Long paramLong, String paramString1, String paramString2)
            throws Exception;

    public abstract void updateCurrUser(User paramUser);

    public abstract void updateUserHis(UserHis paramUserHis);

    public abstract void insertUserHis(UserHis paramUserHis);

    public abstract boolean isPwdRepeatByUser(String paramString1, String paramString2);

    public abstract UserLoginVO getUserLastLogin(Long paramLong);

    public abstract void insertUserLogin(UserLogin paramUserLogin);

    public abstract String[] getPwdExpireStatus(String paramString);

    public abstract User checkUser(@Param("user") User paramUser);

    public abstract List<Map<String, Object>> qryAreaListAll(Map<String, Object> paramMap);

    public abstract List<Map<String, Object>> qryGlAreaByUser(Map<String, Object> paramMap);

    public abstract Integer delGlAreaByUser(Map<String, Object> paramMap);

    public abstract Integer addGlAreaByUser(Map<String, Object> paramMap);
}
