package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;

/**
 * 
 * 一般规则bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class GeneralRule implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 频次id
     */
    Long pinc_id;

    /**
     * 频次code
     */
    String pinc_code;

    /**
     * 频次名称
     */
    String pinc_name;

    /**
     * 规则id
     */
    Long ru_id;

    /**
     * 规则关键字
     */
    String ru_key;
    
    public Long getPinc_id()
    {
        return pinc_id;
    }
    
    public void setPinc_id(Long pinc_id)
    {
        this.pinc_id = pinc_id;
    }
    
    public String getPinc_code()
    {
        return pinc_code;
    }
    
    public void setPinc_code(String pinc_code)
    {
        this.pinc_code = pinc_code;
    }
    
    public String getPinc_name()
    {
        return pinc_name;
    }
    
    public void setPinc_name(String pinc_name)
    {
        this.pinc_name = pinc_name;
    }
    
    public Long getRu_id()
    {
        return ru_id;
    }
    
    public void setRu_id(Long ru_id)
    {
        this.ru_id = ru_id;
    }
    
    public String getRu_key()
    {
        return ru_key;
    }
    
    public void setRu_key(String ru_key)
    {
        this.ru_key = ru_key;
    }
    
}
