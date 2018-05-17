package com.zc.base.sys.modules.user.service.impl;

import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.entity.UserGroup;
import com.zc.base.sys.modules.user.repository.UserDao;
import com.zc.base.sys.modules.user.repository.UserGroupDao;
import com.zc.base.sys.modules.user.service.UserGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


@Service("UserGroupService")
public class UserGroupServiceImpl
        implements UserGroupService {
    @Resource
    private UserGroupDao userGroupDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    public void addGroupInfo(UserGroup userGroup) {
        UserGroup userGroupinfo = this.userGroupDao.verifyGroupByname(userGroup);
        if (userGroupinfo != null) {
            throw this.sdExceptionFactory.createSdException("00003", null, null);
        }
        this.userGroupDao.addGroupInfo(userGroup);
    }


    public void editGroupInfo(UserGroup userGroup) {
        UserGroup userGroupInfo = queryGroupInfo(userGroup.getGroupId());
        if (userGroupInfo == null) {
            throw this.sdExceptionFactory.createSdException("00001", null, null);
        }
        UserGroup group = this.userGroupDao.verifyGroupByname(userGroup);
        if (group != null) {
            throw this.sdExceptionFactory.createSdException("00003", null, null);
        }
        userGroupInfo.setGroupName(userGroup.getGroupName());
        userGroupInfo.setDescription(userGroup.getDescription());
        this.userGroupDao.editGroupInfo(userGroupInfo);
    }


    public void deleteGroupInfo(Long[] ids) {
        this.userGroupDao.deleteGroupAndUserInfo(ids);

        this.userGroupDao.deleteGroupInfo(ids);
    }


    public UserGroup queryGroupInfo(Long id) {
        return this.userGroupDao.queryGroupInfo(id);
    }


    public JqueryStylePagingResults<UserGroup> getUserGroup(UserGroup userGroup, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if ((userGroup.getCreateName() != null) && (!userGroup.getCreateName().isEmpty())) {
            userGroup.setCreateName(DefineStringUtil.escapeAllLike(userGroup.getCreateName()));
        }
        if ((userGroup.getGroupName() != null) && (!userGroup.getGroupName().isEmpty())) {
            userGroup.setGroupName(DefineStringUtil.escapeAllLike(userGroup.getGroupName()));
        }

        String[] columns = {"groupId", "groupName", "createTime", "createName", "groupId", "description"};
        JqueryStylePagingResults<UserGroup> pagingResults = new JqueryStylePagingResults(columns);

        List<UserGroup> userGroupList = this.userGroupDao.getUserGroup(userGroup, paging);


        if ((DefineCollectionUtil.isEmpty(userGroupList)) && (paging.getPage().intValue() != 1)) {
            paging.setPage(Integer.valueOf(paging.getPage().intValue() - 1));
            userGroupList = this.userGroupDao.getUserGroup(userGroup, paging);
        }

        pagingResults.setDataRows(userGroupList);

        Integer total = this.userGroupDao.getUserGroupTotal(userGroup);
        pagingResults.setTotal(total);

        pagingResults.setPage(paging.getPage());
        return pagingResults;
    }


    public List<User> queryUserMember(UserGroup userGroup) {
        return this.userGroupDao.queryUserMember(userGroup);
    }


    public List<UserGroup> queryPartGroupInfo(Long userId, Long currentUserId) {
        return this.userGroupDao.queryPartGroupInfo(userId, currentUserId);
    }


    public void addGroupAndUser(Long userId, List<Long> demandGroup, List<Long> cannotAddGroup) {
        this.userGroupDao.deleteGroupAndUser(userId, cannotAddGroup);
        if ((demandGroup != null) && (demandGroup.size() > 0)) {
            this.userGroupDao.addGroupAndUser(userId, demandGroup);
        }
    }


    public Long[] queryAllGroupMemberId(Long userid, int type) {
        User user = this.userDao.getUserID(userid);
        if (SysConstant.SYSADMIN.equals(user.getAccount())) {
            return null;
        }
        List<Long> userIdList = new ArrayList();
        List<Long> groupIdList = new ArrayList();
        List<UserGroup> listUserGroup = this.userGroupDao.queryAllGroupMemberId(userid);
        if (listUserGroup != null && listUserGroup.size() > 0) {
            for (int i = 0; i < listUserGroup.size(); i++) {
                for (int j = 0; j < listUserGroup.get(i).getUser().size(); j++) {
                    if (!userIdList.contains((listUserGroup.get(i).getUser().get(j)).getUserId())) {
                        userIdList.add(listUserGroup.get(i).getUser().get(j).getUserId());
                    }
                }
                if (!userIdList.contains(listUserGroup.get(i).getGroupId())) {
                    groupIdList.add((listUserGroup.get(i)).getGroupId());
                }
            }
            Long[] userIds = userIdList.toArray(new Long[userIdList.size()]);
            Long[] groupIds = groupIdList.toArray(new Long[groupIdList.size()]);
            if (type == 1) {
                return userIds;
            }
            return groupIds;
        }

        if (type == 1) {
            Long[] userIds = {userid};
            return userIds;
        }

        return null;
    }
}
