package com.zc.pivas.measureunit.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * SRVS_MEASUREMENT_UNIT:计量单位
 *
 * @author  cacabin
 * @version  1.0
 */
public class MeasureUnitBean implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    private String gid;
    
    /**
     * 编码
     */
    private String code;
    
    /**
     * 预留字段0
     */
    private String reserve0;
    
    /**
     * 预留字段1
     */
    private String reserve1;
    
    /**
     * 预留字段2
     */
    private String reserve2;
    
    /**
     * 计量单位
     */
    private String unity;
    
    /**
     * 计量单位
     */
    private String unityName;
    
    private String[] unityNames;
    
    /**
     * 产品类别
     */
    private String proType;
    
    private String proTypeName;
    
    private String[] proTypes;
    
    /**
     * 是否可用（0：可用，1：不可用）
     */
    private int isUse;
    
    private String isUserResult;
    
    /**
     * 类型
     */
    private int model;
    
    /**
     * 舍入精度
     */
    private String precis;
    
    /**
     * 比例
     */
    private String scale;
    
    public String getGid()
    {
        return gid;
    }
    
    public void setGid(String gid)
    {
        this.gid = gid;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getReserve0()
    {
        return reserve0;
    }
    
    public void setReserve0(String reserve0)
    {
        this.reserve0 = reserve0;
    }
    
    public String getReserve1()
    {
        return reserve1;
    }
    
    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }
    
    public String getReserve2()
    {
        return reserve2;
    }
    
    public void setReserve2(String reserve2)
    {
        this.reserve2 = reserve2;
    }
    
    public String getUnity()
    {
        return unity;
    }
    
    public void setUnity(String unity)
    {
        this.unity = unity;
    }
    
    public String getProType()
    {
        return proType;
    }
    
    public void setProType(String proType)
    {
        this.proType = proType;
    }
    
    public int getIsUse()
    {
        return isUse;
    }
    
    public void setIsUse(int isUse)
    {
        this.isUse = isUse;
    }
    
    public String getIsUserResult()
    {
        return isUserResult;
    }
    
    public void setIsUserResult(String isUserResult)
    {
        this.isUserResult = isUserResult;
    }
    
    public int getModel()
    {
        return model;
    }
    
    public void setModel(int model)
    {
        this.model = model;
    }
    
    public String getPrecis()
    {
        return precis;
    }
    
    public void setPrecis(String precis)
    {
        this.precis = precis;
    }
    
    public String getScale()
    {
        return scale;
    }
    
    public void setScale(String scale)
    {
        this.scale = scale;
    }
    
    public String getUnityName()
    {
        return unityName;
    }
    
    public void setUnityName(String unityName)
    {
        this.unityName = unityName;
    }
    
    @Override
    public String toString()
    {
        return "MeasureUnitBean [gid=" + gid + ", code=" + code + ", reserve0=" + reserve0 + ", reserve1=" + reserve1
            + ", reserve2=" + reserve2 + ", unity=" + unity + ", unityName=" + unityName + ", unityNames="
            + Arrays.toString(unityNames) + ", proType=" + proType + ", proTypeName=" + proTypeName + ", proTypes="
            + Arrays.toString(proTypes) + ", isUse=" + isUse + ", isUserResult=" + isUserResult + ", model=" + model
            + ", precis=" + precis + ", scale=" + scale + "]";
    }
    
    public String[] getProTypes()
    {
        return proTypes;
    }
    
    public void setProTypes(String[] proTypes)
    {
        this.proTypes = proTypes;
    }
    
    public String getProTypeName()
    {
        return proTypeName;
    }
    
    public void setProTypeName(String proTypeName)
    {
        this.proTypeName = proTypeName;
    }
    
    public String[] getUnityNames()
    {
        return unityNames;
    }
    
    public void setUnityNames(String[] unityNames)
    {
        this.unityNames = unityNames;
    }
    
}
