package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 处方信息集合bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Prescriptions
{
    private List<Prescription> prescription = new ArrayList<Prescription>();
    
    public List<Prescription> getPrescription()
    {
        return prescription;
    }
    
    public void setPrescription(List<Prescription> prescription)
    {
        this.prescription = prescription;
    }
}
