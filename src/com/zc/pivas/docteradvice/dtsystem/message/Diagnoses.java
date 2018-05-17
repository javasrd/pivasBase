package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * xml数据诊断集合类
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Diagnoses
{
    private List<Diagnose> diagnose = new ArrayList<Diagnose>();
    
    public List<Diagnose> getDiagnose()
    {
        return diagnose;
    }
    
    public void setDiagnose(List<Diagnose> diagnose)
    {
        this.diagnose = diagnose;
    }
    
}
