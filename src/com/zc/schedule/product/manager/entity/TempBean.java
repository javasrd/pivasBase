package com.zc.schedule.product.manager.entity;

import java.io.Serializable;
import java.util.List;

public class TempBean implements Serializable
{

    private static final long serialVersionUID = 4571931819764873606L;
    private String name;
    
    private String value;
    
    private boolean holiday;
    
    private Integer id;
    
    private List<TempBean> changeList;
    
    private String date;
    
    private String change;

    private List<WorkBean> workList;
    
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public List<WorkBean> getWorkList()
    {
        return workList;
    }

    public void setWorkList(List<WorkBean> workList)
    {
        this.workList = workList;
    }

    public boolean isHoliday()
    {
        return holiday;
    }

    public void setHoliday(boolean holiday)
    {
        this.holiday = holiday;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<TempBean> getChangeList()
    {
        return changeList;
    }

    public void setChangeList(List<TempBean> changeList)
    {
        this.changeList = changeList;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getChange()
    {
        return change;
    }

    public void setChange(String change)
    {
        this.change = change;
    }

}
