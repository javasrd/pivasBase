package com.zc.base.sys.modules.user.service.impl;

import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.modules.user.entity.Role;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.repository.PrivilegeDao;
import com.zc.base.sys.modules.user.repository.RoleDao;
import com.zc.base.sys.modules.user.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service("roleService")
public class RoleServiceImpl
        implements RoleService {
    @Resource
    private PrivilegeDao privilegeDao;
    private RoleDao roleDao;
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    public JqueryStylePagingResults<Role> getRole(Role role, JqueryStylePaging jqueryStylePaging)
            throws Exception {
        String[] columns = {"roleId", "name", "description"};
        JqueryStylePagingResults<Role> pagingResults = new JqueryStylePagingResults(columns);

        Integer total = null;
        List<Role> roleList = null;


        User us = (User) SecurityUtils.getSubject().getPrincipal();

        if (us.getAccount().equals(SysConstant.SYSADMIN)) {
            roleList = this.roleDao.getRole(role, jqueryStylePaging);


            if ((DefineCollectionUtil.isEmpty(roleList)) && (jqueryStylePaging.getPage().intValue() != 1)) {
                jqueryStylePaging.setPage(Integer.valueOf(jqueryStylePaging.getPage().intValue() - 1));
                roleList = this.roleDao.getRole(role, jqueryStylePaging);
            }

            total = this.roleDao.getRoleTotal(role);

        } else {
            role.setRoleNumber(String.valueOf(us.getUserId()));

            roleList = this.roleDao.getRoleNumber(role, jqueryStylePaging);


            if ((DefineCollectionUtil.isEmpty(roleList)) && (jqueryStylePaging.getPage().intValue() != 1)) {
                jqueryStylePaging.setPage(Integer.valueOf(jqueryStylePaging.getPage().intValue() - 1));
                roleList = this.roleDao.getRoleNumber(role, jqueryStylePaging);
            }

            total = this.roleDao.getRoleNumberTotal(role);
        }

        pagingResults.setDataRows(roleList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jqueryStylePaging.getPage());
        return pagingResults;
    }


    public void addRole(Role role) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        role.setCreateTime(new Date());
        role.setRoleNumber(String.valueOf(user.getUserId()));
        this.roleDao.addRole(role);
    }


    public void updateRole(Role role) {
        Integer roleNumber = this.roleDao.getRoleListID(role);
        if (roleNumber.intValue() > 0) {

            throw this.sdExceptionFactory.createSdException("00003", null, null);
        }


        Role roleInfo = this.roleDao.queryRoleID(Long.valueOf(role.getRoleId().longValue()));
        if (roleInfo != null) {
            this.roleDao.updateRole(role);

        } else {
            throw this.sdExceptionFactory.createSdException("00001", null, null);
        }
    }


    public void delRole(String roleId) {
        String[] str = roleId.split(",");
        Boolean flag = Boolean.valueOf(true);
        if (flag.booleanValue()) {
            String[] arrayOfString1;
            int j = (arrayOfString1 = str).length;
            for (int i = 0; i < j; i++) {
                String st = arrayOfString1[i];


                this.privilegeDao.delRolePrivilegeId(Long.valueOf(st));
                this.roleDao.delRole(Long.valueOf(st));
            }
        }
    }

    public RoleDao getRoleDao() {
        return this.roleDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    public Role getRoleID(Role role) {
        return this.roleDao.getRoleID(role);
    }


    public List<Role> getRolesByUserId(Long userId, Long currentUserId) {
        return this.roleDao.getRolesByUserId(userId, currentUserId);
    }


    public boolean checkExists(List<Long> pris) {
        boolean flag = true;
        if ((pris != null) && (pris.size() > 0)) {
            int existCount = this.roleDao.getRoleCount(pris);
            if (existCount != pris.size()) {
                flag = false;
            }
        }
        return flag;
    }
}
