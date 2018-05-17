package com.zc.schedule.product.report.entity;

import java.io.Serializable;
import java.util.List;

public class ChartBean implements Serializable
{

    private static final long serialVersionUID = 5125557293976437131L;
    /**
     * 班次列表
     */
    private List<WorkBean> workList;
    
    /**
     * 用户列表
     */
    private List<UserBean> userList;


    public List<WorkBean> getWorkList()
    {
        return workList;
    }


    public void setWorkList(List<WorkBean> workList)
    {
        this.workList = workList;
    }


    public List<UserBean> getUserList()
    {
        return userList;
    }


    public void setUserList(List<UserBean> userList)
    {
        this.userList = userList;
    }

}
