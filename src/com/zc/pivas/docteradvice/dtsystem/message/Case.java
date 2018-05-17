package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * 
 * xml结果封装类
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Case
{
    private String case_code = "";
    
    private String case_name = "";
    
    public String getCase_code()
    {
        return case_code;
    }
    
    public void setCase_code(String case_code)
    {
        this.case_code = case_code;
    }
    
    public String getCase_name()
    {
        return case_name;
    }
    
    public void setCase_name(String case_name)
    {
        this.case_name = case_name;
    }
    
}
