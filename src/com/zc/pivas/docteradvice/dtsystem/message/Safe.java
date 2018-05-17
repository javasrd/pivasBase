package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * 病历bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "safe")
public class Safe
{
    /**
     * 医生信息
     */
    private Doctor_information doctor_information = new Doctor_information();
    
    /**
     * 医生名
     */
    private String doctor_name = "";
    
    /**
     * 医生级别代码
     */
    private String doctor_type = "";
    
    /**
     * 科室代码
     */
    private String department_code = "";
    
    /**
     * 科室名称
     */
    private String department_name = "";
    
    /**
     * 病历卡号
     */
    private String case_id = "";
    
    /**
     * 住院号
     */
    private String inhos_code = "";
    
    /**
     * 床号
     */
    private String bed_no = "";
    
    /**
     * 病人信息
     */
    private Patient_information patient_information = new Patient_information();
    
    private Prescriptions prescriptions = new Prescriptions();
    
    public Doctor_information getDoctor_information()
    {
        return doctor_information;
    }
    
    public void setDoctor_information(Doctor_information doctor_information)
    {
        this.doctor_information = doctor_information;
    }
    
    public String getDoctor_name()
    {
        return doctor_name;
    }
    
    public void setDoctor_name(String doctor_name)
    {
        this.doctor_name = doctor_name;
    }
    
    public String getDoctor_type()
    {
        return doctor_type;
    }
    
    public void setDoctor_type(String doctor_type)
    {
        this.doctor_type = doctor_type;
    }
    
    public String getDepartment_code()
    {
        return department_code;
    }
    
    public void setDepartment_code(String department_code)
    {
        this.department_code = department_code;
    }
    
    public String getDepartment_name()
    {
        return department_name;
    }
    
    public void setDepartment_name(String department_name)
    {
        this.department_name = department_name;
    }
    
    public String getCase_id()
    {
        return case_id;
    }
    
    public void setCase_id(String case_id)
    {
        this.case_id = case_id;
    }
    
    public String getInhos_code()
    {
        return inhos_code;
    }
    
    public void setInhos_code(String inhos_code)
    {
        this.inhos_code = inhos_code;
    }
    
    public String getBed_no()
    {
        return bed_no;
    }
    
    public void setBed_no(String bed_no)
    {
        this.bed_no = bed_no;
    }
    
    public Patient_information getPatient_information()
    {
        return patient_information;
    }
    
    public void setPatient_information(Patient_information patient_information)
    {
        this.patient_information = patient_information;
    }
    
    public Prescriptions getPrescriptions()
    {
        return prescriptions;
    }
    
    public void setPrescriptions(Prescriptions prescriptions)
    {
        this.prescriptions = prescriptions;
    }
    
}
