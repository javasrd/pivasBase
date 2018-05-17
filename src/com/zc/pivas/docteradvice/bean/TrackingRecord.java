package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;

public class TrackingRecord implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2664119747162670727L;
    
    String id;       
    
    String operator;  
    
    String operation_time;
    
    String type_num;
    
    String type_name;
    
    String relevance;

    /**
     * @return 返回 id
     */
    public String getId()
    {
        return id;
    }

    /**
     * 对id进行赋值
     * @param
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return 返回 operator
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * 对operator进行赋值
     * @param
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * @return 返回 operation_time
     */
    public String getOperation_time()
    {
        return operation_time;
    }

    /**
     * 对operation_time进行赋值
     * @param
     */
    public void setOperation_time(String operation_time)
    {
        this.operation_time = operation_time;
    }

    /**
     * @return 返回 type_num
     */
    public String getType_num()
    {
        return type_num;
    }

    /**
     * 对type_num进行赋值
     * @param
     */
    public void setType_num(String type_num)
    {
        this.type_num = type_num;
    }

    /**
     * @return 返回 type_name
     */
    public String getType_name()
    {
        return type_name;
    }

    /**
     * 对type_name进行赋值
     * @param
     */
    public void setType_name(String type_name)
    {
        this.type_name = type_name;
    }

    /**
     * @return 返回 relevance
     */
    public String getRelevance()
    {
        return relevance;
    }

    /**
     * 对relevance进行赋值
     * @param
     */
    public void setRelevance(String relevance)
    {
        this.relevance = relevance;
    }
    
    
    
    
}
