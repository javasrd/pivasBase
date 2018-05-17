package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 药品信息
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicine_data
{
    @XmlElement(name = "medicine")
    private List<Medicine> medicineList = new ArrayList<Medicine>();
    
    public List<Medicine> getMedicineList()
    {
        return medicineList;
    }
    
    public void setMedicineList(List<Medicine> medicineList)
    {
        this.medicineList = medicineList;
    }
}
