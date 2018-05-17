package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 处方集合信息
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Prescription_data
{
    @XmlElement(name = "prescription")
    private List<Prescription> prescriptionList = new ArrayList<Prescription>();
    
    public List<Prescription> getPrescriptionList()
    {
        return prescriptionList;
    }
    
    public void setPrescriptionList(List<Prescription> prescriptionList)
    {
        this.prescriptionList = prescriptionList;
    }
    
}
