package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import com.zc.pivas.docteradvice.dtsystem.message.Diagnose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * xml数据诊断类
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Diagnose_data
{
    @XmlElement(name = "diagnose")
    private List<Diagnose> diagnoseList = new ArrayList<Diagnose>();
    
    public List<Diagnose> getDiagnoseList()
    {
        return diagnoseList;
    }
    
    public void setDiagnoseList(List<Diagnose> diagnoseList)
    {
        this.diagnoseList = diagnoseList;
    }
    
}
