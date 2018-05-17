package com.zc.schedule.product.manager.entity;

import java.io.Serializable;
import java.util.List;

public class MonthWorkBean implements Serializable
{

    private static final long serialVersionUID = -3902246473451051348L;
    private String workDate;

    private List<WeekDataBean> workList;
    

    public String getWorkDate()
    {
        return workDate;
    }

    public void setWorkDate(String workDate)
    {
        this.workDate = workDate;
    }

    public List<WeekDataBean> getWorkList()
    {
        return workList;
    }

    public void setWorkList(List<WeekDataBean> workList)
    {
        this.workList = workList;
    }

   

}
