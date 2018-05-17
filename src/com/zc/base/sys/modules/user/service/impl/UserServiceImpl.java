package com.zc.base.sys.modules.user.service.impl;

import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.*;
import com.zc.base.sys.modules.login.repository.LoginInfoDao;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.service.SystemConfigService;
import com.zc.base.sys.modules.user.dto.UserLoginVO;
import com.zc.base.sys.modules.user.entity.*;
import com.zc.base.sys.modules.user.repository.UserDao;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.base.sys.modules.user.validation.UserPasswordValidator;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("userService")
public class UserServiceImpl
        implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private UserDao userDao;


    @Autowired
    private LoginInfoDao loginInfoDao;


    @Resource
    private SystemConfigService systemConfigService;


    private String hashAlgorithm;


    private Integer hashIterations;


    @Resource
    private SdExceptionFactory sdExceptionFactory;


    private UserPasswordValidator userPasswordValidator;


    public void addUser(User user) {
        List<User> userList = this.userDao.getUserList(user.getAccount());
        if (DefineCollectionUtil.isNotEmpty(userList)) {
            throw this.sdExceptionFactory.createSdException("10000", null, null);
        }


        this.userPasswordValidator.validate(user);


        SimpleHash simpleHash = new SimpleHash(this.hashAlgorithm, user.getPassword(), user.getAccount(), this.hashIterations.intValue());
        user.setPassword(simpleHash.toHex());
        user.setCreateTime(new Date());
        this.userDao.addUser(user);


        UserHis userHis = new UserHis(user.getUserId(), user.getPassword(), new Date());
        insertUserHis(userHis);
    }


    public List<User> getUser(User user, JqueryStylePaging paging, Date date, String sysAdmin) {
        return this.userDao.getUser(user, paging, date, sysAdmin);
    }


    public JqueryStylePagingResults<User> getUser(User user, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (user.getTempAccount().equals(SysConstant.SYSADMIN)) {
            user.setTempAccount(null);
        }

        user.setAccount(DefineStringUtil.escapeAllLike(user.getAccount()));
        user.setName(DefineStringUtil.escapeAllLike(user.getName()));
        user.setTelephone(DefineStringUtil.escapeAllLike(user.getTelephone()));

        String[] columns =
                {"userId", "account", "name", "locked", "userGroupNames", "telephone", "email", "description",
                        "userType"};
        JqueryStylePagingResults<User> pagingResults = new JqueryStylePagingResults(columns);

        List<User> userList = getUser(user, paging, new Date(), SysConstant.SYSADMIN);


        if ((DefineCollectionUtil.isEmpty(userList)) && (paging.getPage().intValue() != 1)) {
            paging.setPage(Integer.valueOf(paging.getPage().intValue() - 1));
            userList = getUser(user, paging, new Date(), SysConstant.SYSADMIN);
        }
        for (int i = 0; i < userList.size(); i++) {
            String userGroupNames = "";
            for (int j = 0; j < ((User) userList.get(i)).getUserGroup().size(); j++) {
                userGroupNames = userGroupNames + ((UserGroup) ((User) userList.get(i)).getUserGroup().get(j)).getGroupName() + ",";
            }
            if (userGroupNames.length() > 0) {
                userGroupNames = userGroupNames.substring(0, userGroupNames.length() - 1);
            }
            ((User) userList.get(i)).setUserGroupNames(userGroupNames);
        }
        pagingResults.setDataRows(userList);

        Integer total = this.userDao.getUserTotal(user, new Date(), SysConstant.SYSADMIN);
        pagingResults.setTotal(total);

        pagingResults.setPage(paging.getPage());
        return pagingResults;
    }


    public List<User> getAllUser(String[] exclusiveAccount) {
        return this.userDao.getAllUser(exclusiveAccount);
    }


    public User getUserByAccount(String account) {
        return this.userDao.getUserByAccount(account);
    }


    public UserLoginVO getUserLastLogin(Long userId) {
        List<UserLogin> list = this.userDao.getUserLastLogin(userId);
        UserLoginVO userLoginVO = null;
        if (list.isEmpty()) {
            userLoginVO = new UserLoginVO();
        } else {
            UserLogin userLogin = (UserLogin) list.get(0);
            Date loginDate = userLogin.getLoginDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String loginDateStr = sdf.format(loginDate);
            userLoginVO =
                    new UserLoginVO(userLogin.getId(), userLogin.getUserId(), userLogin.getIpAddr(), loginDateStr);
        }
        return userLoginVO;
    }


    public void updateUser(User user) {
        User us = this.userDao.getUserID(user.getUserId());
        if (us != null) {
            this.userDao.updateUser(user);
        } else {
            throw this.sdExceptionFactory.createSdException("00001", null, null);
        }
    }


    public void deleteUserById(String userId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String[] str = userId.split(",");
        Boolean flag = Boolean.valueOf(true);
        if (flag.booleanValue()) {
            String[] arrayOfString1;
            int j = (arrayOfString1 = str).length;
            for (int i = 0; i < j; i++) {
                String st = arrayOfString1[i];


                User usInfo = this.userDao.getUserID(Long.valueOf(st));
                if ((usInfo != null) && (usInfo.getAccount().equals(user.getAccount()))) {
                    throw this.sdExceptionFactory.createSdException("20006", null, null);
                }


                this.userDao.delUserId(Long.valueOf(st));


                this.userDao.deleteUserHis(Long.valueOf(st));

                this.userDao.deleteUserLogin(Long.valueOf(st));

                this.userDao.deleteUserById(Long.valueOf(st));
            }
        }
    }


    public UserDao getUserDao() {
        return this.userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    @Value("#{propertiesConfig['shiro.hashAlgorithm']}")
    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public Integer getHashIterations() {
        return this.hashIterations;
    }

    @Value("#{propertiesConfig['shiro.hashIterations']}")
    public void setHashIterations(Integer hashIterations) {
        this.hashIterations = hashIterations;
    }


    public String updateUserPwd(User user, String oldPwd, String pwd)
            throws Exception {
        Map<Object, Object> mp = new HashMap();
        String shaOldPwd = SHA256Util.SHA256Encrypt(oldPwd);
        String dataToShaPwd = AESUtil.Decrypt(user.getPassword());
        if (shaOldPwd.equals(dataToShaPwd)) {
            String aesuPwd = encrypt(pwd);
            mp.put("user_id", user.getUserId());
            mp.put("pwd", aesuPwd);
            this.userDao.updateUserPwd(mp);
        } else {
            return "oldPwdError";
        }
        return "success";
    }


    public User getUserID(Long userId) {
        return this.userDao.getUserID(userId);
    }


    public List<ArchitectureRefUser> getRefUserID(Long userId) {
        return this.userDao.getRefUserID(userId);
    }


    public String addUserRefRole(String userId, String roleId) {
        User us = this.userDao.getUserID(Long.valueOf(userId));
        if (us == null) {
            throw this.sdExceptionFactory.createSdException("00001", null, null);
        }

        this.userDao.deleteUserRefRole(Long.valueOf(userId), null);
        if ((roleId != null) && (!"".equals(roleId))) {
            try {
                String[] role = roleId.split(",");

                String[] arrayOfString1;
                int j = (arrayOfString1 = role).length;
                for (int i = 0; i < j; i++) {
                    String roleS = arrayOfString1[i];

                    if (!"0".equals(roleS)) {
                        UserRefRole ufr = new UserRefRole();
                        ufr.setUserId(Long.valueOf(userId));
                        ufr.setRoleId(Long.valueOf(roleS));
                        this.userDao.addUserRefRole(ufr);
                    }

                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return "oldPwdError";
            }
        }

        return "success";
    }


    public Integer getUserListID(User user) {
        return this.userDao.getUserListID(user);
    }


    public List<UserRefRole> getUserIdRole(Long userId) {
        return this.userDao.getUserIdRole(userId);
    }


    public List<UserHis> getUserHis(String account) {
        return this.userDao.getUserHis(account);
    }


    public void addRoleByUser(Long userId, List<Long> roleId, List<Long> ignoreRoleId) {
        this.userDao.delUserRole(userId, ignoreRoleId);
        if ((roleId != null) && (roleId.size() > 0)) {
            this.userDao.addUserRoleBatch(userId, roleId);
        }
    }


    public void addUserSickRoom(Long userId, List<Long> sickRoomIds) {
        if ((sickRoomIds != null) && (sickRoomIds.size() > 0)) {
            this.userDao.addUserSickRoomBatch(userId, sickRoomIds);
        }
    }


    public void unlockUser(Long userId) {
        this.loginInfoDao.unlockUser(userId);
    }

    public UserPasswordValidator getUserPasswordValidator() {
        return this.userPasswordValidator;
    }

    @Resource(name = "userAddPasswordValidator")
    public void setUserPasswordValidator(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }


    public void updatePassword(User user) throws Exception {
        user.setPassword(new SimpleHash(this.hashAlgorithm, user.getPassword(), user.getAccount(), this.hashIterations.intValue()).toHex());

        List<UserHis> userHisList = this.userDao.getUserHis(user.getAccount());
        Map<String, SystemConfig> map = this.systemConfigService.getInitSystemConfig();
        //前N个口令不重复次数
        Integer pwdNotRepeatCount = Integer.valueOf(Integer.parseInt(map.get("pwd_not_repeat_count").getSys_value()));
        if (pwdNotRepeatCount == 0 || pwdNotRepeatCount.intValue() > userHisList.size()) {
            UserHis userHis = new UserHis(user.getUserId(), user.getPassword(), new Date());
            this.userDao.addUserHis(userHis);
        } else {
            UserHis oldestUserHis = userHisList.get(userHisList.size() - 1);
            oldestUserHis.setPassword(user.getPassword());
            oldestUserHis.setModifyTime(new Date());
            this.userDao.updateUserHis(oldestUserHis);
        }

        this.userDao.updatePassword(user);
    }


    public boolean checkOldPassword(Long userId, String account, String oldPassword)
            throws Exception {
        SimpleHash simpleHash = new SimpleHash(this.hashAlgorithm, oldPassword, account, this.hashIterations.intValue());
        return this.userDao.checkOldPassword(userId, simpleHash.toHex()) != 0;
    }

    private String encrypt(String oldPassword)
            throws Exception {
        String shaPwd = SHA256Util.SHA256Encrypt(oldPassword);

        String aesuPwd = AESUtil.Encrypt(shaPwd);
        return aesuPwd;
    }


    public void updateCurrUser(User user1) {
        this.userDao.updateCurrUser(user1);
    }


    public void updateUserHis(UserHis userHis) {
        this.userDao.updateUserHis(userHis);
    }


    public void insertUserHis(UserHis userHis) {
        this.userDao.addUserHis(userHis);
    }


    public void insertUserLogin(UserLogin userLogin) {
        this.userDao.addUserLogin(userLogin);
    }


    public boolean isPwdRepeatByUser(String account, String password) {
        try {
            List<UserHis> userHisList = this.userDao.getUserHis(account);
            Map<String, SystemConfig> map = this.systemConfigService.getInitSystemConfig();
            Integer pwdNotRepeatCount = Integer.valueOf(Integer.parseInt(((SystemConfig) map.get("pwd_not_repeat_count")).getSys_value()));
            if ((userHisList != null) && (!userHisList.isEmpty())) {
                String encryptNewPwd = new SimpleHash(this.hashAlgorithm, password, account, this.hashIterations.intValue()).toHex();
                for (int i = 0; i < userHisList.size(); i++) {
                    if (i > pwdNotRepeatCount.intValue() - 1)
                        break;
                    UserHis userHistory = (UserHis) userHisList.get(i);
                    if (userHistory.getPassword().equals(encryptNewPwd)) {
                        return true;
                    }

                }

            }


        } catch (Exception e) {

            logger.error(e.getMessage(), e);
        }
        return false;
    }


    public String[] getPwdExpireStatus(String account) {
        try {
            if ("admin".equalsIgnoreCase(account)) {
                return new String[]{"admin", ""};
            }
            //查询用户密码历史数据
            List<UserHis> userHis = getUserHis(account);
            UserHis userHistory = null;
            if (userHis != null && userHis.size() > 0) {
                userHistory = userHis.get(0);
            } else {
                return new String[]{"not_notice_modify_pwd", "0"};
            }

            Calendar calendar = Calendar.getInstance();
            Date curDate = DateUtils.getCurrentDayMixDate(calendar.getTime());
            Date modifyTime = DateUtils.getCurrentDayMixDate(userHistory.getModifyTime());
            Map<String, SystemConfig> map = null;

            map = this.systemConfigService.getInitSystemConfig();

            Integer amount = 0;
            if (map != null && map.get("user_pwd_indate") != null) {
                //普通用户口令有效期
                amount = Integer.valueOf(map.get("user_pwd_indate").getSys_value());
            }
            Date pwdIndate = org.apache.commons.lang.time.DateUtils.addDays(modifyTime, amount.intValue());
            if (curDate.compareTo(pwdIndate) > 0) {
                return new String[]{"pwd_expired", "0"};
            }
            if (curDate.compareTo(pwdIndate) < 0) {
                //口令到期提前通知天数（天）
                Integer noticeAmount = Integer.valueOf(map.get("pwd_expire_notice_days").getSys_value());
                Date tmpDate = org.apache.commons.lang.time.DateUtils.addDays(curDate, noticeAmount.intValue());
                if (tmpDate.compareTo(pwdIndate) < 0) {
                    return new String[]{"not_notice_modify_pwd", "0"};
                }
                return new String[]{"notice_modify_pwd", String.valueOf(DateUtils.getDateDiffDays(pwdIndate, curDate))};
            }
            return new String[]{"notice_modify_pwd", "0"};

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public User checkUser(@Param("user") User user) {
        SimpleHash simpleHash = new SimpleHash(this.hashAlgorithm, user.getPassword(), user.getAccount(), this.hashIterations.intValue());
        user.setPassword(simpleHash.toHex());
        return this.userDao.checkUser(user);
    }


    public List<Map<String, Object>> qryGlAreaByUser(Map<String, Object> map) {
        return this.userDao.qryGlAreaByUser(map);
    }


    public Integer delGlAreaByUser(Map<String, Object> map) {
        return this.userDao.delGlAreaByUser(map);
    }


    public Integer addGlAreaByUser(Map<String, Object> map) {
        return this.userDao.addGlAreaByUser(map);
    }

    public List<Map<String, Object>> qryAreaListAll(Map<String, Object> map) {
        return this.userDao.qryAreaListAll(map);
    }
}
