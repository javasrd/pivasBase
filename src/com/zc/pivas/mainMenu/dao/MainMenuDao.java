package com.zc.pivas.mainMenu.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.mainMenu.bean.MainMenuBean;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;



@MyBatisRepository(value = "mainMenuDao")
public interface MainMenuDao
{

    List<MainMenuBean> getMenuListYZ();

    List<MainMenuBean> getMenuListPC();

    List<MainMenuBean> getMenuListYSSF();
    
    List<MainMenuBean> getMenuListPQSM(@Param("yyrqStart") String yyrqStart,@Param("yyrqEnd") String yyrqEnd);
    
    List<MainMenuBean> getMenuListPCYH(@Param("yyrqStart") String yyrqStart,@Param("yyrqEnd") String yyrqEnd);
    
    List<MainMenuBean> getMenuListDYPQ(@Param("yyrqStart") String yyrqStart,@Param("yyrqEnd") String yyrqEnd);

    ArrayList<MainMenuBean> getSubMenuListYZ(@Param("parentid") String parentid);

    ArrayList<MainMenuBean> getSubMenuListYSSF(@Param("parentid") String parentid);

    ArrayList<MainMenuBean> getSubMenuListPQSM(@Param("parentid") String parentid,@Param("yyrqStart") String yyrqStart,@Param("yyrqEnd") String yyrqEnd);
    
    ArrayList<MainMenuBean> getSubMenuListPCYH(@Param("parentid")String parentid,@Param("yyrqStart") String yyrqStart,@Param("yyrqEnd") String yyrqEnd);
    
    ArrayList<MainMenuBean> getSubMenuListDYPQ(@Param("parentid")String parentid,@Param("yyrqStart") String yyrqStart,@Param("yyrqEnd") String yyrqEnd);
    

    /**
     * 打印药物接收单
     * 
     * @return
     */
    List<MainMenuBean> getMenuListReciver();
    
    ArrayList<MainMenuBean> getSubMenuListReciver(String parentid);
    
    /**
     * 历史药单
     * 
     * @return
     */
    List<MainMenuBean> getMenuListYdHis();
    
    ArrayList<MainMenuBean> getSubMenuListYdHis(String parentid);
    /**
     * 配置费收费明细
     * 
     * @return
     */
    List<MainMenuBean> getMenuListPZF();
    
    ArrayList<MainMenuBean> getSubMenuListPZF(String parentid);
}
