package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 自动检查历史bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Allergic_history
{
    @XmlElement(name = "case")
    private List<Case> caseList = new ArrayList<Case>();
    
    public List<Case> getCaseList()
    {
        return caseList;
    }
    
    public void setCaseList(List<Case> caseList)
    {
        this.caseList = caseList;
    }
    
    public void setCaseListDefault()
    {
        this.caseList.add(new Case());
        this.caseList.add(new Case());
        this.caseList.add(new Case());
    }
}
