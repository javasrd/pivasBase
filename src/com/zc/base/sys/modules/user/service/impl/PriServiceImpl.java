package com.zc.base.sys.modules.user.service.impl;

import com.zc.base.common.util.ExtranetPrivilegeUtil;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.modules.user.entity.Privilege;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.repository.PrivilegeDao;
import com.zc.base.sys.modules.user.service.PriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("priService")
public class PriServiceImpl
        implements PriService {
    @Autowired
    private PrivilegeDao privilegeDao;

    public List<Privilege> getPrivilegeListByRoleId(Long roleId, Long userId, String language) {
        return this.privilegeDao.getPrivilegeListByRoleId(roleId, userId, language);
    }


    public void addRolePrivilegeBatch(Long roleId, List<Long> pris, List<Long> ignorePris) {
        this.privilegeDao.delRolePrivilege(roleId, ignorePris);
        if ((pris != null) && (pris.size() > 0)) {
            this.privilegeDao.addRolePrivilegeBatch(roleId, pris);
        }
    }


    public boolean checkExists(List<Long> pris) {
        boolean flag = true;
        if ((pris != null) && (pris.size() > 0)) {
            int existCount = this.privilegeDao.getPrisCount(pris);
            if (existCount != pris.size()) {
                flag = false;
            }
        }
        return flag;
    }


    public List<Privilege> getMenuListByUser(Long userId, Integer sysCode, String language) {
        return this.privilegeDao.getMenuListByUser(userId, sysCode, language);
    }


    public List<Privilege> getMenuListBySubSys(Integer sysCode, String language) {
        return this.privilegeDao.getMenuListBySubSys(sysCode, language);
    }


    public Map<Long, List<Privilege>> getMenuMapByUser(Long userId, Integer sysCode, String language) {
        Map<Long, List<Privilege>> priMap = null;


        List<Privilege> priList = null;


        if (userId == null) {
            priList = getMenuListBySubSys(sysCode, language);
        } else {
            priList = getMenuListByUser(userId, sysCode, language);
        }


        if (DefineCollectionUtil.isNotEmpty(priList)) {

            priList = ExtranetPrivilegeUtil.menuFilter(priList);

            priMap = new HashMap();
            for (Privilege pri : priList) {

                if (!priMap.containsKey(pri.getParentId())) {
                    priMap.put(pri.getParentId(), new ArrayList());
                }


                ((List) priMap.get(pri.getParentId())).add(pri);
            }
        }

        return priMap;
    }


    public List<Privilege> getPrivilegeListByUser(User user) {
        List<Privilege> privilegeList = null;

        if ((user != null) && (user.getAccount() != null)) {
            Long userId = null;


            if (!user.getAccount().equals(SysConstant.SYSADMIN)) {
                userId = user.getUserId();
            }


            privilegeList = this.privilegeDao.getPrivilegeListByUser(userId);
        }


        privilegeList = ExtranetPrivilegeUtil.menuFilter(privilegeList);

        return privilegeList;
    }


    public Privilege getMenuById(Long id, String language) {
        return this.privilegeDao.getMenuById(id, language);
    }


    public String getNavigationByMenuId(Long id, String language) {
        List<String> menuNameList = null;


        StringBuilder navigation = new StringBuilder();


        Privilege menu = getMenuById(id, language);
        if (menu != null) {
            menuNameList = new ArrayList();

            menuNameList.add(menu.getName());


            int level = menu.getLevel().intValue();
            if (level > 0) {
                for (int i = level; i >= 1; i--) {
                    menu = getMenuById(menu.getParentId(), language);
                    if (menu != null) {
                        menuNameList.add(menu.getName());
                    }
                }
            }
        }


        if (DefineCollectionUtil.isNotEmpty(menuNameList)) {
            for (int i = menuNameList.size() - 1; i >= 0; i--) {
                navigation.append((String) menuNameList.get(i));
                if (i != 0) {
                    navigation.append(" > ");
                }
            }
        }

        return navigation.toString();
    }


    public List<Integer> getSysCodeListByUser(Long userId) {
        List<Integer> list = this.privilegeDao.getSysCodeListByUser(userId);
        if (DefineCollectionUtil.isNotEmpty((Collection) list)) {
            List<Privilege> privilegeList = new ArrayList();
            Iterator var5 = ((List) list).iterator();

            while (var5.hasNext()) {
                Integer id = (Integer) var5.next();
                Privilege pri = new Privilege();
                pri.setPrivilegeId(id.longValue());
                privilegeList.add(pri);
            }

            List<Privilege> privileges = ExtranetPrivilegeUtil.menuFilter(privilegeList);
            List<Integer> tempList = new ArrayList();
            Iterator var10 = privileges.iterator();

            while (var10.hasNext()) {
                Privilege pri = (Privilege) var10.next();
                tempList.add(pri.getPrivilegeId().intValue());
            }

            list = tempList;
        }

        return (List) list;
    }
}
