package com.zc.pivas.app.bean;

/**
 * 医嘱状态请求交互bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class CheckOrderStatusBean 
{
    public CheckOrderStatusBean()
    {
        doctorAdviceStatus = -1;
        ret = -1;
        msg = "";
    }
    
    public int doctorAdviceStatus;
    public int ret;
    public String msg;


}