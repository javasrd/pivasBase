package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;

/**
 * 
 * 频次实体bean
 * <功能详细描述>
 *
 * @author  cacabin
 * @version  0.1
 */
public class Frequency implements Serializable
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
     * 一般规则id
     */
    Long ru_id;

    /**
     * 一般规则关键字
     */
    String ru_key;

    /**
     * 一般规则序号
     */
    Integer ru_serialNum;

    /**
     * 是否固定批次
     */
    Integer gl_fixe;

    /**
     * 用药频次id
     */
    Long pc_id;

    /**
     * 用药频次编码
     */
    String pc_num;

    /**
     * 用药频次名称
     */
    String pc_name;

    /**
     * 是否空批
     */
    Integer pc_empty;

    /**
     * 开始时间
     */
    String pc_start;

    /**
     * 结束时间
     */
    String pc_end;

    /**
     * 是否寄第二日
     */
    Integer pc_secend;

    /**
     * 批次颜色
     */
    String pc_color;

    /**
     * 是否启用
     */
    Integer pc_enabled;

    /**
     * 是否临批
     */
    Integer pc_0p;
    
    Integer order_num ;
    
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
    
    public Integer getGl_fixe()
    {
        return gl_fixe;
    }
    
    public void setGl_fixe(Integer gl_fixe)
    {
        this.gl_fixe = gl_fixe;
    }
    
    public Long getPc_id()
    {
        return pc_id;
    }
    
    public void setPc_id(Long pc_id)
    {
        this.pc_id = pc_id;
    }
    
    public String getPc_num()
    {
        return pc_num;
    }
    
    public void setPc_num(String pc_num)
    {
        this.pc_num = pc_num;
    }
    
    public String getPc_name()
    {
        return pc_name;
    }
    
    public void setPc_name(String pc_name)
    {
        this.pc_name = pc_name;
    }
    
    public Integer getPc_empty()
    {
        return pc_empty;
    }
    
    public void setPc_empty(Integer pc_empty)
    {
        this.pc_empty = pc_empty;
    }
    
    public String getPc_start()
    {
        return pc_start;
    }
    
    public void setPc_start(String pc_start)
    {
        this.pc_start = pc_start;
    }
    
    public String getPc_end()
    {
        return pc_end;
    }
    
    public void setPc_end(String pc_end)
    {
        this.pc_end = pc_end;
    }
    
    public Integer getPc_secend()
    {
        return pc_secend;
    }
    
    public void setPc_secend(Integer pc_secend)
    {
        this.pc_secend = pc_secend;
    }
    
    public String getPc_color()
    {
        return pc_color;
    }
    
    public void setPc_color(String pc_color)
    {
        this.pc_color = pc_color;
    }
    
    public Integer getPc_enabled()
    {
        return pc_enabled;
    }
    
    public void setPc_enabled(Integer pc_enabled)
    {
        this.pc_enabled = pc_enabled;
    }
    
    public Integer getPc_0p()
    {
        return pc_0p;
    }
    
    public void setPc_0p(Integer pc_0p)
    {
        this.pc_0p = pc_0p;
    }
    
    public Integer getRu_serialNum()
    {
        return ru_serialNum;
    }
    
    public void setRu_serialNum(Integer ru_serialNum)
    {
        this.ru_serialNum = ru_serialNum;
    }

    public Integer getOrder_num()
    {
        return order_num;
    }

    public void setOrder_num(Integer order_num)
    {
        this.order_num = order_num;
    }
    
}
