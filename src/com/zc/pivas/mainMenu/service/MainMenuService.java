package com.zc.pivas.mainMenu.service;

import com.zc.pivas.mainMenu.bean.MainMenuBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "mainMenuService")
public interface MainMenuService
{
    
    /**
     * 
     * 获取病区信息
     * @param menu
     * @return
     */
    List<MainMenuBean> getMenuList(String menu) throws Exception;
    
}
