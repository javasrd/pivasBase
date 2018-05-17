package com.zc.schedule.product.manager.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户月数据实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class UserMonthBean implements Serializable
{

    private static final long serialVersionUID = -1758954576842806542L;
    /**
     * 人员id
     */
    private String user_id;
    
    /**
     * 人员名称
     */
    private String user_name;
    
    /**
     * 一月排班数据集合
     * 
     */
    private List<MonthWorkBean> monthWorkList;
    

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public List<MonthWorkBean> getMonthWorkList()
    {
        return monthWorkList;
    }

    public void setMonthWorkList(List<MonthWorkBean> monthWorkList)
    {
        this.monthWorkList = monthWorkList;
    }

    

}
