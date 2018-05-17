package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 医嘱自动检查data封装类
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Allergic_data
{
    @XmlElement(name = "allergic")
    private List<Allergic> allergicList = new ArrayList<Allergic>();
    
    public List<Allergic> getAllergicList()
    {
        return allergicList;
    }
    
    public void setAllergicList(List<Allergic> allergicList)
    {
        this.allergicList = allergicList;
    }
    
}
