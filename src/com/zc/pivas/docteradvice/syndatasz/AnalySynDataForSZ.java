package com.zc.pivas.docteradvice.syndatasz;

import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.entity.SynDoctorAdviceBean;
import com.zc.pivas.docteradvice.syndatasz.message.req.ESBEntry.Query;
import com.zc.pivas.docteradvice.syndatasz.message.resp.Msg;
import com.zc.pivas.docteradvice.syndatasz.message.resp.msg.YzRow;
import com.zc.pivas.common.util.FileUtil;
import com.zc.pivas.drugway.bean.DrugWayBean;
import com.zc.pivas.drugway.service.DrugWayService;
import com.zc.pivas.employee.bean.EmployeeInfoBean;
import com.zc.pivas.employee.service.EmployeeInfoService;
import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * 某XXX医院返回数据处理
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("analySynDataForSZ")
public class AnalySynDataForSZ
{
    /** 日志类 */
    private static final Logger log = LoggerFactory.getLogger(AnalySynDataForSZ.class);
    
    // 医嘱
    private static final String actionAdvice = "BS23002";
    
    /**
     * 员工信息
     */
    @Resource
    private EmployeeInfoService employeeInfoService;
    
    /**
     * 用药频次
     */
    @Resource
    private DrugWayService drugWayService;
    
    /**
     * 
     * 医嘱数据获取
     * @param condition
     * @return
     * @throws IOException 
     * @throws HttpException 
     * @throws ParseException
     */
    public List<Msg> getYzData(String condition)
        throws HttpException, IOException
    {
        String synRequest = SetMessageForSynSZ.setSynReq(actionAdvice, condition, new Query());
        
        // 调用接口
        String synRespon = "";
        if ("0".equals(Propertiesconfiguration.getStringProperty("syndata.mode")))
        {
            synRespon =
                FileUtil.readFileByLines(Propertiesconfiguration.getStringProperty("syndata.mode.local.path")
                    + "respon\\BS23002B.txt")
                    .replaceAll("<!\\[CDATA\\[", "")
                    .replaceAll("\\]\\]>", "");
        }
        else
        {
            synRespon =
                SetMessageForSynSZ.sendRequestMessage(actionAdvice, synRequest)
                    .replaceAll("<!\\[CDATA\\[", "")
                    .replaceAll("\\]\\]>", "");
        }
        
        List<Msg> msgList = SetMessageForSynSZ.analySynYZRespon(synRespon);
        
        return msgList;
    }
    
    /**
     * 
     * 医嘱数据转化
     * @param row
     * @return
     * @throws ParseException
     */
    public SynDoctorAdviceBean exchangeYzBean(YzRow row)
    {
        SynDoctorAdviceBean synYz = new SynDoctorAdviceBean();
        
        synYz.setAge(row.getAge());
        
        String ageUnit = row.getAge_Unit().trim();
        
        // -1 表示传过来的年龄为中文
        synYz.setAgeUnit(-1);
        
        if (!"".equals(ageUnit))
        {
            if ("D".equals(ageUnit))
            {
                synYz.setAgeUnit(0);
            }
            else if ("M".equals(ageUnit))
            {
                synYz.setAgeUnit(1);
            }
            else
            {
                synYz.setAgeUnit(2);
            }
        }
        synYz.setBedNo(row.getBed_No());
        
        // 出生日期格式化
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHH");
        try
        {
            synYz.setBirth(new Timestamp(sf1.parse(row.getDate_Birth()).getTime()));
        }
        catch (ParseException e1)
        {
            synYz.setBirth(new Timestamp(new Date().getTime()));
        }
        
        // 用药批次名称
        synYz.setDoseWayCode("");
        if (null != row.getDose_Way_Code() && !"".equals(row.getDose_Way_Code()))
        {
            DrugWayBean drugWay = new DrugWayBean();
            drugWay.setId(row.getDose_Way_Code());
            drugWay = drugWayService.getDrugWayByCondition(drugWay);
            synYz.setDoseWayCode((drugWay == null) ? "" : drugWay.getName());
        }
        
        synYz.setDropSpeed((row.getDrop_Speed() == null) ? "" : row.getDrop_Speed().trim());
        
        String drugAmount = row.getDrug_Amount();
        synYz.setDrugAmount(row.getDrug_Amount());
        if (drugAmount.startsWith("."))
        {
            synYz.setDrugAmount("0" + row.getDrug_Amount());
        }
        
        synYz.setDrugCode(row.getDrug_Code() + "_" + row.getDrug_Place_Code());
        synYz.setDrugName(row.getDrug_Name());
        synYz.setDrugPlaceCode(row.getDrug_Place_Code());
        
        synYz.setDrugUseOneDosAge(row.getDrug_Use_One_Dosage());
        if (row.getDrug_Use_One_Dosage().startsWith("."))
        {
            synYz.setDrugUseOneDosAge("0" + row.getDrug_Use_One_Dosage());
        }
        
        synYz.setDrugUseOneDosAgeUnit(row.getDrug_Use_One_Dosage_Unit());
        synYz.setInhospIndexNo(row.getInhosp_Index_No());
        synYz.setInhospNo(row.getInhosp_No());
        synYz.setNote(row.getNote());
        
        try
        {
            synYz.setNutritionliquidFlag(Integer.parseInt(row.getNutrition_Liquid_Flag()));
        }
        catch (NumberFormatException e)
        {
            synYz.setNutritionliquidFlag(0);
        }
        
        try
        {
            synYz.setYzlx(Integer.parseInt(row.getOrder_Categ_Code()));
        }
        catch (NumberFormatException e)
        {
            synYz.setYzlx(0);
        }
        
        synYz.setOrderFrequencyCode(row.getOrder_Frequency_Code());
        synYz.setOrderGroupNo(row.getOrder_Group_No());
        synYz.setOrderNo(row.getOrder_No());
        synYz.setOrderOpenDeptCode(row.getOrder_Open_Dept_Code());
        synYz.setOrderOpenDeptName(row.getOrder_Open_Dept_Name());
        synYz.setOrderOpendrCode(row.getOrder_Open_DR_Code());
        
        synYz.setOrderOpendrName(row.getOrder_Open_DR_Name());
        
        // 根据员工号查询员工名称
        String staff_Name = "";
        if (!"".equals(synYz.getOrderOpendrCode()) && "".equals(synYz.getOrderOpendrName().trim()))
        {
            EmployeeInfoBean employeeInfoBean = new EmployeeInfoBean();
            employeeInfoBean.setStaff_Code(synYz.getOrderOpendrCode());
            
            employeeInfoBean = employeeInfoService.getEmployeeInfo(employeeInfoBean);
            
            if (null != employeeInfoBean)
            {
                staff_Name = employeeInfoBean.getStaff_Name();
                
                synYz.setOrderOpendrName(staff_Name);
            }
        }
        
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        
        try
        {
            synYz.setOrderOrderDate(new Timestamp(sf2.parse(row.getOrder_Order_Date()).getTime()));
        }
        catch (ParseException e1)
        {
            synYz.setOrderOrderDate(new Timestamp(new Date().getTime()));
        }
        try
        {
            synYz.setOrderStopDate(new Timestamp(sf2.parse(row.getOrder_Stop_Date().trim()).getTime()));
        }
        catch (ParseException e1)
        {
            synYz.setOrderStopDate(null);
        }
        
        String orderExecuteStatus = row.getOrder_Execute_Status();
        
        // 医嘱状态为执行，且医嘱停止时间不为空，且当前系统时间大于等于停止时间，返回停止结果
        SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd");
        if (0 == synYz.getYzlx() && "0".equals(orderExecuteStatus) && null != synYz.getOrderStopDate())
        {
            try
            {
                Date orderStopDate = fromat.parse(fromat.format(synYz.getOrderStopDate()));
                Date now = fromat.parse(fromat.format(new Date()));
                
                if (!orderStopDate.after(now))
                {
                    orderExecuteStatus = "1";
                }
            }
            catch (ParseException e)
            {
                log.error(e.getMessage(), e);
            }
            
        }
        else if ("2".equals(orderExecuteStatus))
        {
            orderExecuteStatus = "1";
        }
        
        // 短嘱
        if (1 == synYz.getYzlx() && "0".equals(orderExecuteStatus))
        {
            SimpleDateFormat sf3 = new SimpleDateFormat("yyyyMMdd");
            
            try
            {
                if (!sf3.format(sf2.parse(row.getOrder_Order_Date())).equals(sf3.format(new Date())))
                {
                    orderExecuteStatus = "1";
                }
            }
            catch (ParseException e)
            {
                orderExecuteStatus = "0";
            }
        }
        
        try
        {
            synYz.setOrderExecuteStatus(Integer.parseInt(orderExecuteStatus));
        }
        catch (NumberFormatException e)
        {
            synYz.setOrderExecuteStatus(0);
        }
        
        synYz.setPatName((row.getPat_Name() == null) ? "" : row.getPat_Name());
        synYz.setSex((row.getPhysic_Sex_Code() == null) ? "" : row.getPhysic_Sex_Code());
        
        String sexCode = synYz.getSex();
        
        if (!"".equals(sexCode))
        {
            synYz.setSex(("1".equals(sexCode)) ? "1" : "0");
        }
        
        synYz.setRecordDrCode(row.getRecord_DR_Code());
        synYz.setRecordDrName(row.getRecord_DR_Name());
        // 录入医生姓名
        if (!"".equals(synYz.getRecordDrCode()) && "".equals(synYz.getRecordDrName()))
        {
            EmployeeInfoBean employeeInfoBean = new EmployeeInfoBean();
            employeeInfoBean.setStaff_Code(synYz.getRecordDrCode());
            
            employeeInfoBean = employeeInfoService.getEmployeeInfo(employeeInfoBean);
            
            if (null != employeeInfoBean)
            {
                staff_Name = employeeInfoBean.getStaff_Name();
                
                synYz.setRecordDrName(staff_Name);
            }
        }
        
        try
        {
            synYz.setSelfDrugFlag(Integer.parseInt(row.getSelf_Drug_Flag()));
        }
        catch (NumberFormatException e)
        {
            synYz.setSelfDrugFlag(0);
        }
        
        synYz.setSpecifications(row.getSpecifications());
        synYz.setAvdp(row.getWeight());
        
        // 同步时间
        SimpleDateFormat sf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        synYz.setSynData(sf3.format(new Date()));
        
        // 医嘱确认时间
        synYz.setConfirmDate("");
        if (null != row.getConfirm_date())
        {
            synYz.setConfirmDate(row.getConfirm_date().trim());
            
        }
        
        // 医嘱首日用药次数
        synYz.setFirstUseCount((row.getFirstUseCount() == null) ? "" : row.getFirstUseCount().trim());
        return synYz;
    }
}
