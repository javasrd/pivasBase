package com.zc.schedule.common.entity;

/**
 * 
 * 返回数据模板实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class ResultDataTemplate
{
    /**
     * 返回代码
     */
    int code;
    
    /**
     * 返回消息
     */
    String msg;
    
    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
    public String getMsg()
    {
        return msg;
    }
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    
}
