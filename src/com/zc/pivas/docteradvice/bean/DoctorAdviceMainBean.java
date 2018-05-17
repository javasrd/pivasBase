package com.zc.pivas.docteradvice.bean;

import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 医嘱主表实体类
 * <功能详细描述>
 *
 * @author  cacabin
 * @version  0.1
 */
public class DoctorAdviceMainBean extends DoctorAdviceMain implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 子医嘱列表
     */
    List<DoctorAdviceMinBean> yzList = null;
    
    public List<DoctorAdviceMinBean> getYzList()
    {
        return yzList;
    }
    
    public void setYzList(List<DoctorAdviceMinBean> yzList)
    {
        this.yzList = yzList;
    }
    
}
