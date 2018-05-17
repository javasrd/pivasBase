package com.zc.pivas.common.entity;

import java.io.Serializable;
/**
 * 
 * 返回结果封装类
 *
 * @author  cacabin
 * @version  1.0
 */
public class ResultData implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    int code;
    
    String mess;
    
    public int getCode()
    {
        return code;
    }
    
    public void setCode(int code)
    {
        this.code = code;
    }
    
    public String getMess()
    {
        return mess;
    }
    
    public void setMess(String mess)
    {
        this.mess = mess;
    }
    
    Object data;
    
    public Object getData()
    {
        return data;
    }
    
    public void setData(Object data)
    {
        this.data = data;
    }
    
}
