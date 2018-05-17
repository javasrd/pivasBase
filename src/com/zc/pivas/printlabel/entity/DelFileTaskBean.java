package com.zc.pivas.printlabel.entity;

import java.io.Serializable;

/**
 * 文件删除任务表
 *
 * @author kunkka
 * @version 1.0
 */
public class DelFileTaskBean implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String filePath;
    
    private int day = 0;

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }
}
