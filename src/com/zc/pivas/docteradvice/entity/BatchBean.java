package com.zc.pivas.docteradvice.entity;

import java.io.Serializable;

public class BatchBean implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1723240587681017411L;
    
    private Long id_;
    
    private String num_;
    
    private String name_;
    
    private Integer is_empty_batch_;
    
    private String start_time_;
    
    private String end_time_;
    
    private Integer is_secend_advice_;
    
    private String color_;
    
    private Integer enabled_;
    
    private String reserve1_;
    
    private String reserve2_;
    
    private String reserve3_;
    
    private Integer is0p;
    
    private String timetype;
    
    private Integer order_num;

    /**
     * @return 返回 id_
     */
    public Long getId_()
    {
        return id_;
    }

    /**
     * 对id_进行赋值
     * @param
     */
    public void setId_(Long id_)
    {
        this.id_ = id_;
    }

    /**
     * @return 返回 num_
     */
    public String getNum_()
    {
        return num_;
    }

    /**
     * 对num_进行赋值
     * @param
     */
    public void setNum_(String num_)
    {
        this.num_ = num_;
    }

    /**
     * @return 返回 name_
     */
    public String getName_()
    {
        return name_;
    }

    /**
     * 对name_进行赋值
     * @param
     */
    public void setName_(String name_)
    {
        this.name_ = name_;
    }

    /**
     * @return 返回 is_empty_batch_
     */
    public Integer getIs_empty_batch_()
    {
        return is_empty_batch_;
    }

    /**
     * @param
     */
    public void setIs_empty_batch_(Integer is_empty_batch_)
    {
        this.is_empty_batch_ = is_empty_batch_;
    }

    /**
     * @return 返回 start_time_
     */
    public String getStart_time_()
    {
        return start_time_;
    }

    /**
     * @param
     */
    public void setStart_time_(String start_time_)
    {
        this.start_time_ = start_time_;
    }

    /**
     * @return 返回 end_time_
     */
    public String getEnd_time_()
    {
        return end_time_;
    }

    /**
     * @param
     */
    public void setEnd_time_(String end_time_)
    {
        this.end_time_ = end_time_;
    }

    /**
     * @return 返回 is_secend_advice_
     */
    public Integer getIs_secend_advice_()
    {
        return is_secend_advice_;
    }

    /**
     * @param
     */
    public void setIs_secend_advice_(Integer is_secend_advice_)
    {
        this.is_secend_advice_ = is_secend_advice_;
    }

    /**
     * @return 返回 color_
     */
    public String getColor_()
    {
        return color_;
    }

    /**
     * @param
     */
    public void setColor_(String color_)
    {
        this.color_ = color_;
    }

    /**
     * @return 返回 enabled_
     */
    public Integer getEnabled_()
    {
        return enabled_;
    }

    /**
     * @param
     */
    public void setEnabled_(Integer enabled_)
    {
        this.enabled_ = enabled_;
    }

    /**
     * @return 返回 reserve1_
     */
    public String getReserve1_()
    {
        return reserve1_;
    }

    /**
     * @param
     */
    public void setReserve1_(String reserve1_)
    {
        this.reserve1_ = reserve1_;
    }

    /**
     * @return 返回 reserve2_
     */
    public String getReserve2_()
    {
        return reserve2_;
    }

    /**
     * @param
     */
    public void setReserve2_(String reserve2_)
    {
        this.reserve2_ = reserve2_;
    }

    /**
     * @return 返回 reserve3_
     */
    public String getReserve3_()
    {
        return reserve3_;
    }

    /**
     * @param
     */
    public void setReserve3_(String reserve3_)
    {
        this.reserve3_ = reserve3_;
    }

    /**
     * @return 返回 is0p
     */
    public Integer getIs0p()
    {
        return is0p;
    }

    /**
     * @param
     */
    public void setIs0p(Integer is0p)
    {
        this.is0p = is0p;
    }

    /**
     * @return 返回 timetype
     */
    public String getTimetype()
    {
        return timetype;
    }

    /**
     * @param
     */
    public void setTimetype(String timetype)
    {
        this.timetype = timetype;
    }

    /**
     * @return 返回 order_num
     */
    public Integer getOrder_num()
    {
        return order_num;
    }

    /**
     * @param
     */
    public void setOrder_num(Integer order_num)
    {
        this.order_num = order_num;
    }

    
}
