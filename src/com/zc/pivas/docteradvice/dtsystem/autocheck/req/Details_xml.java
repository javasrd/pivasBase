package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * xml内容实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlRootElement(name = "details_xml")
public class Details_xml
{
    /**
     * 是否上传0/1
     */
    @XmlAttribute
    private String is_upload = "1";
    
    /**
     * HIS系统时间（YYYY-MM-DD HH:mm:SS）
     */
    private String his_time = "";
    
    /**
     * 门诊住院标识 op/ip
     */
    private String hosp_flag = "";
    
    /**
     * 就诊类型
     */
    private String treat_type = "";
    
    /**
     * 就诊号
     */
    private String treat_code = "";
    
    /**
     * 床号
     */
    private String bed_no = "";
    
    /**
     * 病人信息
     */
    private Patient patient = new Patient();
    
    /**
     * 处方信息
     */
    private Prescription_data prescription_data = new Prescription_data();
    
    public String getHis_time()
    {
        return his_time;
    }
    
    public void setHis_time(String his_time)
    {
        this.his_time = his_time;
    }
    
    public String getHosp_flag()
    {
        return hosp_flag;
    }
    
    public void setHosp_flag(String hosp_flag)
    {
        this.hosp_flag = hosp_flag;
    }
    
    public String getTreat_type()
    {
        return treat_type;
    }
    
    public void setTreat_type(String treat_type)
    {
        this.treat_type = treat_type;
    }
    
    public String getTreat_code()
    {
        return treat_code;
    }
    
    public void setTreat_code(String treat_code)
    {
        this.treat_code = treat_code;
    }
    
    public String getBed_no()
    {
        return bed_no;
    }
    
    public void setBed_no(String bed_no)
    {
        this.bed_no = bed_no;
    }
    
    public Patient getPatient()
    {
        return patient;
    }
    
    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }
    
    public Prescription_data getPrescription_data()
    {
        return prescription_data;
    }
    
    public void setPrescription_data(Prescription_data prescription_data)
    {
        this.prescription_data = prescription_data;
    }
}
