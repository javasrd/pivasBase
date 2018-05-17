package com.zc.pivas.docteradvice.bean;

import com.zc.base.sys.common.util.NumberUtil;
import com.zc.pivas.docteradvice.entity.PriorityRules;

import java.util.Map;

/**
 * 
 * 当个病区强制规则bean
 * 
 * 
 * @author  cacabin
 * @version  0.1
 */
public class ForceRulesWithArea extends PriorityRules
{

    public static void format(Map<String, Object> map)
    {
        if (map.get("prId") != null)
        {
            map.put("prId", NumberUtil.getObjLong(map.get("prId")));
        }
        if (map.get("prType") != null)
        {
            map.put("prType", NumberUtil.getObjLong(map.get("prType")));
        }
        if (map.get("deptcode") != null)
        {
            map.put("deptcode", NumberUtil.getObjLong(map.get("deptcode")));
        }
        if (map.get("medicId") != null)
        {
            map.put("medicId", NumberUtil.getObjLong(map.get("medicId")));
        }
        if (map.get("batchId") != null)
        {
            map.put("batchId", NumberUtil.getObjLong(map.get("batchId")));
        }
        if (map.get("prOrder") != null)
        {
            map.put("prOrder", NumberUtil.getObjInt(map.get("prOrder")));
        }
    }
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 病区名称
     */
    String deptname;
    
    /**
     * 药品名称
     */
    String medicName;

    /**
     * 批次名称
     */
    String batchName;

    /**
     * 药品code
     */
    String medicCode;

    /**
     * 药品分类id
     */
    String categoryId;
    
    public String getDeptname()
    {
        return deptname;
    }
    
    public void setDeptname(String deptname)
    {
        this.deptname = deptname;
    }
    
    public String getMedicName()
    {
        return medicName;
    }
    
    public void setMedicName(String medicName)
    {
        this.medicName = medicName;
    }

    public String getBatchName()
    {
        return batchName;
    }

    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }

    public String getMedicCode()
    {
        return medicCode;
    }

    public void setMedicCode(String medicCode)
    {
        this.medicCode = medicCode;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }
    
}
