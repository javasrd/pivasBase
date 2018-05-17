package com.zc.pivas.mainMenu.service.impl;

import com.zc.base.common.util.DateUtil;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.common.util.DataFormat;
import com.zc.pivas.mainMenu.bean.MainMenuBean;
import com.zc.pivas.mainMenu.dao.MainMenuDao;
import com.zc.pivas.mainMenu.service.MainMenuService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository(value = "mainMenuServiceImpl")
public class MainMenuServiceImpl implements MainMenuService {

    @Resource(name = "mainMenuDao")
    private MainMenuDao mainMenuDao;

    @Resource
    private DoctorAdviceMainService yzMainService;

    /**
     * 获取病区信息
     */
    @Override
    public List<MainMenuBean> getMenuList(String menu) throws Exception {
        List<MainMenuBean> menuList = new ArrayList<>();
        //格式化日期
        String yyrqStr = DateUtil.getCurrentDay8Str();
        Boolean flag = DataFormat.dateChange();

        if (flag) {
            yyrqStr = DateUtil.getDay8StrByDay(DateUtil.addDay(new Date(), 1));
        }

        String[] yyrqs = yzMainService.getDateRange(yyrqStr);
        String yyrqStart = null;
        String yyrqEnd = null;
        if (yyrqs != null) {
            yyrqStart = yyrqs[0];
            yyrqEnd = yyrqs[1];
        }

        if ("医嘱信息".equals(menu)) {
            menuList = mainMenuDao.getMenuListYZ();
        } else if ("审方核对".equals(menu) || "药师审方".equals(menu)) {
            menuList = mainMenuDao.getMenuListYSSF();
        } else if ("批次优化".equals(menu) || "批次管理".equals(menu)) {

            menuList = mainMenuDao.getMenuListPCYH(yyrqStart, yyrqEnd);
        } else if ("打印瓶签".equals(menu)) {

            menuList = mainMenuDao.getMenuListDYPQ(yyrqStart, yyrqEnd);
        } else if ("瓶签扫描".equals(menu)) {

            menuList = mainMenuDao.getMenuListPQSM(yyrqStart, yyrqEnd);

        } else if (menu.contains("装箱核对")) {
            menuList = mainMenuDao.getMenuListReciver();
        }
        /*else if (menu.contains("历史药单"))
        {
            menuList = mainMenuDao.getMenuListYdHis();
        }
        else if (menu.contains("配置费收费明细"))
        {
            menuList = mainMenuDao.getMenuListPZF();
        }*/
        querySubMenuList(menuList, menu);

        return menuList;
    }

    /**
     * 查询子病区
     *
     * @param menuList
     * @param menu
     * @throws Exception
     */
    private void querySubMenuList(List<MainMenuBean> menuList, String menu) throws Exception {

        int mainTotal1 = 0;
        int mainTotal2 = 0;

        for (MainMenuBean main : menuList) {

            String state = main.getState();

            //判断是否是自定义分组
            if ("1".equals(state)) {

                String parentid = main.getId();

                ArrayList<MainMenuBean> subMenuList = new ArrayList<>();
                //格式化时间
                String yyrqStr = DateUtil.getCurrentDay8Str();

                Boolean flag = DataFormat.dateChange();

                if (flag) {
                    yyrqStr = DateUtil.getDay8StrByDay(DateUtil.addDay(new Date(), 1));
                }
                String[] yyrqs = yzMainService.getDateRange(yyrqStr);
                String yyrqStart = null;
                String yyrqEnd = null;
                if (yyrqs != null) {
                    yyrqStart = yyrqs[0];
                    yyrqEnd = yyrqs[1];
                }
                if ("医嘱信息".equals(menu)) {
                    subMenuList = mainMenuDao.getSubMenuListYZ(parentid);
                } else if ("审方核对".equals(menu) || "药师审方".equals(menu)) {
                    subMenuList = mainMenuDao.getSubMenuListYSSF(parentid);
                } else if ("批次优化".equals(menu) || "批次管理".equals(menu)) {

                    subMenuList = mainMenuDao.getSubMenuListPCYH(parentid, yyrqStart, yyrqEnd);
                } else if ("打印瓶签".equals(menu)) {

                    subMenuList = mainMenuDao.getSubMenuListDYPQ(parentid, yyrqStart, yyrqEnd);
                } else if ("瓶签扫描".equals(menu)) {

                    subMenuList = mainMenuDao.getSubMenuListPQSM(parentid, yyrqStart, yyrqEnd);
                } else if (menu.contains("装箱核对")) {
                    subMenuList = mainMenuDao.getSubMenuListReciver(parentid);
                }
                /*else if (menu.contains("历史药单"))
                {
                    subMenuList = mainMenuDao.getSubMenuListYdHis(parentid);
                }
                else if (menu.contains("配置费收费明细"))
                {
                    subMenuList = mainMenuDao.getSubMenuListPZF(parentid);
                }*/

                //添加分组的子病区
                /*int size = subMenuList.size();
                if (size > 1)
                {
                    
                    MainMenuBean sub = subMenuList.get(0);
                    String num1 = sub.getNum1();
                    String num2 = sub.getNum2();
                    main.setNum1(num1);
                    main.setNum2(num2);
                    
                    subMenuList.remove(0);
                    main.setSubMenuList(subMenuList);
                    
                }*/

                //添加分组的子病区
                int size = subMenuList.size();
                if (size > 0) {
                    int total1 = 0;
                    int total2 = 0;
                    for (MainMenuBean suBean : subMenuList) {

                        int num1 = Integer.parseInt(suBean.getNum1());
                        int num2 = Integer.parseInt(suBean.getNum2());

                        total1 = total1 + num1;
                        total2 = total2 + num2;
                    }

                    main.setNum1(String.valueOf(total1));
                    main.setNum2(String.valueOf(total2));

                    main.setSubMenuList(subMenuList);
                }

            }

            String mainNum1 = main.getNum1();
            String mainNum2 = main.getNum2();

            mainTotal1 = mainTotal1 + Integer.parseInt(mainNum1);
            mainTotal2 = mainTotal2 + Integer.parseInt(mainNum2);

        }

        if (menuList.size() > 1) {
            MainMenuBean firstList = menuList.get(0);
            firstList.setNum1(String.valueOf(mainTotal1));
            firstList.setNum2(String.valueOf(mainTotal2));
        }

    }

}
