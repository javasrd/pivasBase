package com.zc.base.common.util;

import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.user.entity.Privilege;
import com.zc.base.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


public class ExtranetPrivilegeUtil {
    public static List<Privilege> menuFilter(List<Privilege> priList) {
        HttpServletRequest request = Servlets.getCurrentRequest();

        boolean isOutSide = false;
        Object flag = request.getAttribute("sd_extranet_flag");
        if ((flag != null) && ("true".equals(String.valueOf(flag)))) {
            isOutSide = true;
        }
        if ((priList != null) && (isOutSide)) {

            String outsideDisplayMenu = Propertiesconfiguration.getStringProperty("outsideDisplayMenu");
            if (outsideDisplayMenu != null) {
                String[] menus = outsideDisplayMenu.split(",");
                List<Privilege> outSideMenu = new ArrayList();
                if ((menus != null) && (menus.length > 0)) {
                    String[] arrayOfString1;
                    int j = (arrayOfString1 = menus).length;
                    for (int i = 0; i < j; i++) {
                        String menuId = arrayOfString1[i];

                        for (Privilege pri : priList) {

                            if ((pri.getPrivilegeId() != null) &&
                                    (menuId.equalsIgnoreCase(pri.getPrivilegeId().toString()))) {
                                outSideMenu.add(pri);
                                break;
                            }
                        }
                    }
                }
                priList = outSideMenu;
            }
        }
        return priList;
    }
}
