package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * 
 * xml格式基础bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlRootElement(name = "base_xml")
public class Base_xml implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String source = "his";
    
    /**
     * 医院编码
     */
    private String hosp_code = "";
    
    /**
     * 科室代码
     */
    private String dept_code = "";
    
    /**
     * 医生信息
     */
    private Doct doct = new Doct();
    
    public String getSource()
    {
        return source;
    }
    
    public void setSource(String source)
    {
        this.source = source;
    }
    
    public String getHosp_code()
    {
        return hosp_code;
    }
    
    public void setHosp_code(String hosp_code)
    {
        this.hosp_code = hosp_code;
    }
    
    public String getDept_code()
    {
        return dept_code;
    }
    
    public void setDept_code(String dept_code)
    {
        this.dept_code = dept_code;
    }
    
    public Doct getDoct()
    {
        return doct;
    }
    
    public void setDoct(Doct doct)
    {
        this.doct = doct;
    }
    
}
