package com.zc.pivas.mainMenu.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MainMenuBean implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 8253280514484982061L;
    
    private String id;
    
    private String num1;
    
    private String num2;
    
    private String state;
    
    private String levelnum;
    
    private String deptname;
    
    private String deptcode;
    
    private ArrayList<MainMenuBean> subMenuList;

    public String getDeptcode()
    {
        return deptcode;
    }

    public void setDeptcode(String deptcode)
    {
        this.deptcode = deptcode;
    }

    /**
     * @return 返回 deptname
     */
    public String getDeptname()
    {
        return deptname;
    }

    /**
     * @param 对deptname进行赋值
     */
    public void setDeptname(String deptname)
    {
        this.deptname = deptname;
    }

    /**
     * @return 返回 num1
     */
    public String getNum1()
    {
        return num1;
    }

    /**
     * @param 对num1进行赋值
     */
    public void setNum1(String num1)
    {
        this.num1 = num1;
    }

    /**
     * @return 返回 num2
     */
    public String getNum2()
    {
        return num2;
    }

    /**
     * @param 对num2进行赋值
     */
    public void setNum2(String num2)
    {
        this.num2 = num2;
    }

    /**
     * @return 返回 state
     */
    public String getState()
    {
        return state;
    }

    /**
     * @param 对state进行赋值
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * @return 返回 levelnum
     */
    public String getLevelnum()
    {
        return levelnum;
    }

    /**
     * @param 对levelnum进行赋值
     */
    public void setLevelnum(String levelnum)
    {
        this.levelnum = levelnum;
    }

    /**
     * @return 返回 subMenuList
     */
    public ArrayList<MainMenuBean> getSubMenuList()
    {
        return subMenuList;
    }

    /**
     * @param 对subMenuList进行赋值
     */
    public void setSubMenuList(ArrayList<MainMenuBean> subMenuList)
    {
        this.subMenuList = subMenuList;
    }

    /**
     * @return 返回 id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id)
    {
        this.id = id;
    }

    
}
