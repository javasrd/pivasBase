package com.zc.pivas.docteradvice.service.impl;

import com.zc.base.bla.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;
import com.zc.pivas.docteradvice.bean.*;
import com.zc.pivas.docteradvice.dtsystem.JaxbBinder;
import com.zc.pivas.docteradvice.dtsystem.autocheck.req.*;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.repository.DoctorAdviceDao;
import com.zc.pivas.docteradvice.repository.DoctorAdviceMainDao;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.checktype.bean.ErrorTypeBean;
import com.zc.pivas.checktype.dao.ErrorTypeDAO;
import com.zc.pivas.redis.RedisService;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import org.apache.axis.client.Call;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 医嘱主表service实现类
 * 
 * 
 * @author  cacabin
 * @version  1.0
 */
@Service("yzMainService")
public class DoctorAdviceMainServiceImpl implements DoctorAdviceMainService

{
    
    public final static String DATE_START = Propertiesconfiguration.getStringProperty("pivas.date.start");
    
    public final static String DATE_END = Propertiesconfiguration.getStringProperty("pivas.date.end");
    
    private final static String TODAY = "今天";
    
    private final static String TOMORROW = "明天";
    
    @Resource
    private DoctorAdviceMainDao yzMainDao;
    
    @Resource
    private DoctorAdviceDao yzDao;
    
    @Resource
    private ErrorTypeDAO errorTypeDAO;
    
    @Resource
    private RedisService redisService;
    
    

    /**
     * 
     * 查询医嘱列表
     * @param map
     * @param jquryStylePaging
     * @return
     */
    @Override
    public List<DoctorAdviceMain> qryListBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return yzMainDao.qryListBeanByMap(map, jquryStylePaging);
    }

    /**
     * 
     * 查询医嘱总数
     * @param map
     * @return
     */
    @Override
    public Integer qryCountByMap(Map<String, Object> map)
    {
        return yzMainDao.qryCountByMap(map);
    }

    /**
     * 
     *
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<DoctorAdviceMain> qryPageBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        List<DoctorAdviceMain> listBean = qryListBeanByMap(map, jquryStylePaging);
        int rowCount = qryCountByMap(map);
        
        String[] columns =
            new String[] {"yzMainId", "parentNo", "yzlx", "yzzt", "yzshzt", "rucangOKNum", "pidsj", "zxrq", "zxsj",
                "yzResource", "freqCode", "drugname", "startTimeS", "sfrqS", "bedno", "wardName", "patname", "age",
                "specifications", "dose", "doseUnit", "quantity", "sfysmc", "sfrq", "yzshbtgyy",
                "medicamentsPackingUnit", "patname", "sex", "birthday", "age", "ageunit", "avdp", "transfusion",
                "dropSpeed", "bedno", "startDayDelNum", "startHour","yzzdshzt","yzshbtglx","yzshbtglxS","yzshbtglxColor","recheckstate","recheckcause"};
        JqueryStylePagingResults<DoctorAdviceMain> pagingResults = new JqueryStylePagingResults<DoctorAdviceMain>(columns);
        pagingResults.setDataRows(listBean);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }
    /**
     * 
     * 根据唯一编号查询医嘱bean
     * @param pidsj
     * @param rucangOKNum
     * @return
     */
    @Override
    public DoctorAdviceMain qryBeanByPidsj(String pidsj, boolean rucangOKNum)
    {
        if (StrUtil.getObjStr(pidsj) != null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pidsj", pidsj);
            map.put("leftWithPati", "Y");
            map.put("qryYYTJ", "1");
            if (rucangOKNum)
            {
                map.put("rucangOKNum", "Y");
            }
            List<DoctorAdviceMain> list = yzMainDao.qryListBeanByMap(map, new JqueryStylePaging());
            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 
     * 查询医嘱关联的频次
     * @param pidsj
     * @return
     */
    @Override
    public String qryYZNoPinCi(String pidsj)
    {
        if (pidsj != null)
        {
            List<String> pidsjN = new ArrayList<String>();
            pidsjN.add(pidsj);
            List<String> result = yzMainDao.qryYZNoPinCi(pidsjN);
            if (result != null && result.size() > 0)
            {
                return result.get(0);
            }
        }
        return null;
    }


    /**
     * 
     * 查询医嘱关联的频次
     * @param pidsjN
     * @return
     */
    @Override
    public List<String> qryYZNoPinCi(List<String> pidsjN)
    {
        if (pidsjN != null && pidsjN.size() > 0)
        {
            return yzMainDao.qryYZNoPinCi(pidsjN);
        }
        return null;
    }

    /**
     * 
     * 根据pidsj查询药单
     * @param pidsj
     * @return
     */
    @Override
    public List<PrescriptionBLabel> qryYdMoreByPidsj(String pidsj)
    {
        return yzMainDao.qryYdMoreByPidsj(pidsj);
    }

    /**
     * 
     * 统计医嘱数量
     * @param map
     * @return
     */
    @Override
    public List<DoctorAdviceGroupBean> groupByMap(@Param("qry")
    Map<String, Object> map)
    {
        return yzMainDao.groupByMap(map);
    }

    /**
     * 
     * 查询审方错误类型
     * @return
     */
    @Override
    public List<ErrorTypeBean> getErrorTypeList()
    {
        return errorTypeDAO.getErrorTypeList(new ErrorTypeBean(), new JqueryStylePaging());
    }

    /**
     * 
     * 更新医嘱审方状态
     * @param updateMap
     * @return
     */
    @Override
    public Integer updateCheckYZMain(Map<String, Object> updateMap)
    {
        return yzMainDao.updateCheckYZMain(updateMap);
    }

    /**
     * 
     * 更新医嘱状态
     * @param updateMap
     * @return
     */
    @Override
    public Integer updateCheckYZ(Map<String, Object> updateMap)
    {
        return yzMainDao.updateCheckYZ(updateMap);
    }

    /**
     * 
     * 删除药单数据
     * @param pidsjN
     */
    @Override
    public void deleteYDMainByPidsjN(List<String> pidsjN)
    {
        yzMainDao.deleteYDMainByPidsjN(pidsjN);
    }

    /**
     * 
     * 删除子药单数据
     * @param pidsjN
     */
    @Override
    public void deleteYDByPidsjN(List<String> pidsjN)
    {
        yzMainDao.deleteYDByPidsjN(pidsjN);
    }

    /**
     * 
     * 删除批瓶签数据
     * @param pidsjN
     */
    @Override
    public void deletePQByPidsjN(List<String> pidsjN)
    {
        yzMainDao.deletePQByPidsjN(pidsjN);
    }

    /**
     * 
     * 更新医嘱审方状态
     * @param updateMap
     * @param delByPidsjN
     * @return
     */
    @Override
    public Integer updateCheckYZMain(Map<String, Object> updateMap, List<String> delByPidsjN)
    {
        if (delByPidsjN != null && delByPidsjN.size() > 0)
        {
            yzMainDao.deletePQByPidsjN(delByPidsjN);
            yzMainDao.deleteYDMainByPidsjN(delByPidsjN);
            yzMainDao.deleteYDByPidsjN(delByPidsjN);
        }
        int row1 = yzMainDao.updateCheckYZMain(updateMap);
        int row2 = yzMainDao.updateCheckYZ(updateMap);
        
        yzMainDao.insertCheckResultMany(updateMap);
        return row1 > -1 && row2 > -1 ? 1 : 0;
    }
    
    /**
     * 
     * 根据病区统计总数
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> groupNumByInpArea(Map<String, Object> map)
    {
        return yzMainDao.groupNumByInpArea(map);
    }

    /**
     * 
     * 根据条件查询子医嘱
     * @param map
     * @return
     */
    @Override
    public DoctorAdviceMain getYzByCondition(@Param("qry")
    Map<String, Object> map)
    {
        return yzMainDao.getYzByCondition(map);
    }
    
    /**
     * 
     * 增加医嘱表数据
     * @param addMap
     */
    @Override
    public void addYZ(Map<String, Object> addMap)
    {
        yzMainDao.addYZ(addMap);
        
    }
    
    /**
     * 
     * 修改医嘱表数据
     * @param updateMap
     */
    @Override
    public void updateYZ(Map<String, Object> updateMap)
    {
        yzMainDao.updateYZ(updateMap);
        
    }

    /**
     * 
     * 修改医嘱主表状态
     * @param yzMap
     */
    @Override
    public void changeYzMainStatus(Map<String, Object> yzMap)
    {
        yzMainDao.changeYzMainStatus(yzMap);
    }

    /**
     * 
     * 修改药单表状态
     * @param yzMap
     */
    @Override
    public void changeYDStateByParentNo(Map<String, Object> yzMap)
    {
        yzMainDao.changeYDStateByParentNo(yzMap);
        
    }

    /**
     * 
     * 查询批次列表
     * 
     * @param map
     * @return
     */
    @Override
    public List<Frequency> qryBatchRule(Map<String, Object> map)
    {
        return yzMainDao.qryBatchRule(map);
    }
    /**
     * 
     * 查询可用的一般规则
     * @param filterRuKeyNull
     * @return
     */
    @Override
    public List<Frequency> qryBatchRuleEnabled(boolean filterRuKeyNull)
    {
        Map<String, Object> qry = new HashMap<String, Object>();
        qry.put("pc_enabled", 1);
        qry.put("pc_0p", 1);
        //是否过滤掉空批的
        if (filterRuKeyNull)
        {
            qry.put("ru_key_null", 1);
        }
        return yzMainDao.qryBatchRule(qry);
    }

    /**
     * 
     * 查询一般规则
     * @param map
     * @return
     */
    @Override
    public List<GeneralRule> qryGeneralRule(Map<String, Object> map)
    {
        return yzMainDao.qryGeneralRule(map);
    }
    /**
     * 
     * 查询病人相关医嘱
     * @param map
     * @param jquryStylePaging
     * @return
     */
    @Override
    public List<Map<String, Object>> qryPatListByYZ(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return yzMainDao.qryPatListByYZ(map, jquryStylePaging);
    }

    /**
     * 
     * 查询病人总数
     * 
     * @param map
     * @return
     */
    @Override
    public Integer qryPatListSize(Map<String, Object> map)
    {
        return yzMainDao.qryPatListSize(map);
    }

    /**
     * 
     * 分页查询病人列表
     * @param map
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<Map<String, Object>> qryPagePatByMap(Map<String, Object> map,
        JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        List<Map<String, Object>> listBean = qryPatListByYZ(map, jquryStylePaging);
        
        boolean pageExist = false;
        String pageParams = StrUtil.getObjStr(map.get("pageParams"));
        if (StringUtils.isNotBlank(pageParams))
        {
            pageExist = true;
        }
        
        String[] columns =
            new String[] {"inhospno", "parentNo","pidsj", "DRUGNAME","freqCode",  "patname", "sexName", "state", "caseId", "birthday", "age", "ageunit", "avdp",
                "bedno","sfrqS","yzzdshzt","yzzt","yzshbtglx","yzzdshbtglxS","recheckstate","recheckcause"};
        
        int count  = 0;
        if(pageExist){
            count = qryPatListSize(map);
        }
        
        JqueryStylePagingResults<Map<String, Object>> pagingResults =
            new JqueryStylePagingResults<Map<String, Object>>(columns);
        pagingResults.setDataRows(listBean);
        pagingResults.setTotal(listBean.size());
        if(pageExist){
            pagingResults.setTotal(count);
        }
        pagingResults.setPage(1);
        if(pageExist){
        pagingResults.setPage(jquryStylePaging.getPage());
        }
        
        return pagingResults;
    }

    /**
     * 
     * 更新自动检查医嘱状态
     * @param map
     * @return
     */
    @Override
    public Integer updateYZAutoCheckN(Map<String, Object> map)
    {
        return yzMainDao.updateYZAutoCheckN(map);
    }

    /**
     * 
     * 更新主医嘱检查状态
     * @param map
     * @return
     */
    @Override
    public Integer updateYZMainAutoCheckN(Map<String, Object> map)
    {
        return yzMainDao.updateYZMainAutoCheckN(map);
    }

    /**
     * 
     * 重置医嘱审核状态
     * @param updateMap
     */
    @Override
    public void resetYZSH(Map<String, Object> updateMap)
    {
        yzMainDao.resetYZSH(updateMap);
    }

    /**
     * 
     * 查询医嘱列表
     * @param map
     * @return
     */
    @Override
    public List<DoctorAdviceMainBean> qryListWithYZByMap(@Param("qry")
    Map<String, Object> map)
    {
        return yzMainDao.qryListWithYZByMap(map);
    }

    /**
     * 
     * 修改药单主表状态
     * @param yzMap
     */
    @Override
    public void changeYDMainStateByParentNo(Map<String, Object> yzMap)
    {
        yzMainDao.changeYDMainStateByParentNo(yzMap);
    }

    /**
     * 
     * 修改药单主表状态
     * @param yzMap
     */
    @Override
    public void changeYDBedno(Map<String, Object> yzMap)
    {
        yzMainDao.changeYDBedno(yzMap);
        
    }

    /**
     * 
     * 修改药单主表状态
     * @param yzMap
     */
    @Override
    public void changeYDMainBedno(Map<String, Object> yzMap)
    {
        yzMainDao.changeYDMainBedno(yzMap);
    }

    /**
     * 
     * 重置医嘱审核状态
     * @param map
     * @return
     */
    @Override
    public Integer resetYZCheckStatus(Map<String, Object> map)
    {
        return yzMainDao.resetYZCheckStatus(map);
    }

    /**
     * 
     * 停止非当日开立的临时医嘱
     * @return
     */
    @Override
    public Integer stopTempYZNotToday()
    {
        return yzMainDao.stopTempYZNotToday();
    }

    /**
     * 
     * 修改医嘱主表床号
     * @param yzMap
     */
    @Override
    public void changeBedno(Map<String, Object> yzMap)
    {
        yzMainDao.changeBedno(yzMap);
    }

    /**
     * 
     * 添加医嘱审核日志
     * 
     * @param map
     * @return
     */
    @Override
    public void addOprLogMany(Map<String, Object> map)
    {
        yzMainDao.addOprLogMany(map);
    }

    /**
     * 
     * 添加医嘱审核日志
     * 
     * @param oprLogList
     * @return
     */
    @Override
    public void addOprLogMany(List<OprLog> oprLogList)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", oprLogList);
        yzMainDao.addOprLogMany(map);
    }

    /**
     * 
     * 自动检查医嘱状态
     */
    @Override
    public void autoCheckYzByDT()
    {
        Map<String, Object> qryYZ = new HashMap<String, Object>();
        qryYZ.put("yzzt", "0");
        qryYZ.put("yzshzt", "0");
        
        // 获取当前医嘱表中执行中未审核的医嘱数据
        List<DoctorAdviceMain> yzMainList = qryListBeanByMap(qryYZ, new JqueryStylePaging());
        
        if (DefineCollectionUtil.isNotEmpty(yzMainList))
        {
            String base_xml = "";
            String details_xml = "";
            
            //0 分析结果中无合理用药问题,1 分析结果中有合理用药提示类问题  ,2 分析结果中有合理用药慎用类问题 ,3 分析结果中有合理用药禁用类问题
            int result = 0;
            
            // 遍历医嘱
            for (DoctorAdviceMain yzMain : yzMainList)
            {
                // 根据获取的医嘱数据分装请求报文
                base_xml = setBaseXml(yzMain);
                
                details_xml = setDetailsXml(yzMain);
                
                // 对接大通
                try
                {
                    result = sendToDT(base_xml, details_xml);
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
                catch (ServiceException e)
                {
                    e.printStackTrace();
                }
                
                Map<String, Object> updateMap = null;
                List<String> pidsjN = null;
                if (result != 0)
                {
                    // 设置医嘱的审核状态为预审核不通过,医嘱审核状态：-2
                    updateMap = new HashMap<String, Object>();
                    pidsjN = new ArrayList<String>();
                    pidsjN.add(yzMain.getPidsj());
                    updateMap.put("pidsjN", pidsjN);
                    updateMap.put("yzshzt", -2);
                    if (updateCheckYZ(updateMap) > -1 && updateCheckYZMain(updateMap) > -1)
                    {
                        
                    }
                }
                else
                {
                    // 设置医嘱的审核状态为预审核通过,医嘱审核状态：-1
                    updateMap = new HashMap<String, Object>();
                    pidsjN = new ArrayList<String>();
                    pidsjN.add(yzMain.getPidsj());
                    updateMap.put("pidsjN", pidsjN);
                    updateMap.put("yzshzt", -1);
                    if (updateCheckYZ(updateMap) > -1 && updateCheckYZMain(updateMap) > -1)
                    {
                        
                    }
                }
            }
            
        }
    }
    /**
     * 
     * 配置xml样式
     * @param yzMain
     * @return
     */
    private String setBaseXml(DoctorAdviceMain yzMain)
    {
        Base_xml base_xml = new Base_xml();
        
        base_xml.setDept_code(yzMain.getWardCode());
        Doct doct = new Doct();
        doct.setCode(yzMain.getDoctor());
        doct.setName(yzMain.getDoctorName());
        base_xml.setDoct(doct);
        
        JaxbBinder jaxbBinder = new JaxbBinder(Base_xml.class);
        
        return jaxbBinder.toXml(base_xml, "utf-8")
            .replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "");
    }
    /**
     * 
     * 生成xml数据
     * @param yzMain
     * @return
     */
    private String setDetailsXml(DoctorAdviceMain yzMain)
    {
        Details_xml details_xml = new Details_xml();
        details_xml.setHis_time(DateUtil.getYYYYMMDDHHMMSSFromDate(yzMain.getStartTime()));
        details_xml.setHosp_flag("ip");
        details_xml.setTreat_code(yzMain.getCaseId());
        details_xml.setTreat_type("400");
        details_xml.setBed_no(yzMain.getBedno());
        
        Patient patient = new Patient();
        patient.setBirth(DateUtil.getYYYYMMDDFromDate(yzMain.getBirthday()));
        patient.setName(yzMain.getPatname());
        int sex = yzMain.getSex();
        switch (sex)
        {
            case 0:
                patient.setSex("女");
                break;
            
            case 1:
                patient.setSex("男");
                break;
            
            default:
                patient.setSex("未知");
                break;
        }
        patient.setWeight(yzMain.getAvdp());
        patient.setMedical_record(yzMain.getInpatientNo());
        details_xml.setPatient(patient);
        
        Prescription_data prescription_data = new Prescription_data();
        List<Prescription> prescriptionList = new ArrayList<Prescription>();
        Prescription prescription = new Prescription();
        prescription.setId(yzMain.getParentNo());
        prescription.setIs_current("1");
        switch (yzMain.getYzlx())
        {
            case 0:
                prescription.setPres_type("T");
                break;
            
            case 1:
                prescription.setPres_type("L");
                break;
            
            default:
                prescription.setPres_type("");
                break;
        }
        prescription.setPres_time(DateUtil.getYYYYMMDDHHMMSSFromDate(yzMain.getStartTime()));
        
        Medicine_data medicine_data = new Medicine_data();
        List<Medicine> medicineList = setMedicineList(yzMain);
        medicine_data.setMedicineList(medicineList);
        prescription.setMedicine_data(medicine_data);
        prescriptionList.add(prescription);
        prescription_data.setPrescriptionList(prescriptionList);
        details_xml.setPrescription_data(prescription_data);
        
        JaxbBinder jaxbBinder = new JaxbBinder(Details_xml.class);
        
        return jaxbBinder.toXml(details_xml, "utf-8")
            .replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "");
    }
    
    /**
     * 
     * 对接大通检查处方接口
     * @param base_xml
     * @param details_xml
     * @return
     * @throws RemoteException 
     * @throws NumberFormatException 
     * @throws ServiceException 
     * @throws MalformedURLException
     */
    private int sendToDT(String base_xml, String details_xml)
        throws ServiceException, MalformedURLException, RemoteException
    {
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        
        //请求地址
        String url = Propertiesconfiguration.getStringProperty("pivas.yz.autocheck.DT.url");
        
        String namespace = "http://www.w3.org/2003/05/soap-envelope";
        
        //Action路径  
        String actionUri = "ElsevierDatong";
        
        //要调用的方法名
        String op = "IMDS_WEBSRV";
        Call call = (Call)service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(url));
        //call.setUsername("your username"); // 用户名（如果需要验证）  
        //call.setPassword("your password"); // 密码  
        call.setUseSOAPAction(true);
        
        // action uri 
        call.setSOAPActionURI(namespace + actionUri);
        
        // 设置要调用哪个方法  
        call.setOperationName(new QName(namespace, op));
        
        // 设置参数名称，具体参照从浏览器中看到的  
        call.addParameter(new QName(namespace, "fun_id"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(namespace, "base_xml"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(namespace, "details_xml"), XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(namespace, "ui_results_xml"), XMLType.XSD_STRING, ParameterMode.OUT);
        call.addParameter(new QName(namespace, "his_results_xml"), XMLType.XSD_STRING, ParameterMode.OUT);
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT); // 要返回的数据类型  
        Object[] params = new Object[] {"1006", base_xml, details_xml, "", ""};
        int result = 0;
        try
        {
            result = Integer.parseInt((String)call.invoke(params));
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    /**
     * 
     * 根据医嘱获取相关药单列表
     * @param yzMain
     * @return
     */
    private List<Medicine> setMedicineList(DoctorAdviceMain yzMain)
    {
        // 药品信息
        String[] drugName = yzMain.getDrugname().split("@@");
        String[] drugCode = yzMain.getChargeCode().split("@@");
        String[] specification = yzMain.getSpecifications().split("@@");
        String[] doseUnit = yzMain.getDoseUnit().split("@@");
        String[] dose = yzMain.getDose().split("@@");
        String freqCode = yzMain.getFreqCode();
        String administer = yzMain.getSupplyCode();
        
        // 计算服药天数
        int days = DateUtil.getNumberOfDays(new Date(), yzMain.getStartTime());
        List<Medicine> medicineList = new ArrayList<Medicine>();
        Medicine medicine = null;
        for (int i = 0; i < drugCode.length; i++)
        {
            if (drugName[i] != null)
            {
                medicine = new Medicine();
                medicine.setAdminister(administer);
                medicine.setName(drugName[i]);
                medicine.setHis_code(drugCode[i].split("_")[0]);
                medicine.setSpec(specification[i]);
                medicine.setGroup(yzMain.getParentNo());
                medicine.setDose_unit(doseUnit[i]);
                medicine.setDose(dose[i]);
                medicine.setBegin_time(DateUtil.getYYYYMMDDHHMMSSFromDate(yzMain.getStartTime()));
                medicine.setDays(days + "");
                medicine.setFreq(freqCode);
                medicineList.add(medicine);
            }
        }
        
        return medicineList;
    }
    /**
     * 
     * 查询知识库表中所有数据
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> selectKnowledgeData(Map<String, Object> map)
    {
        return yzMainDao.selectKnowledgeData(map);
    }

    /**
     * 
     * 自我知识库数据对比
     */
    @Override
    public void autoCheckYzByLocal()
    {
        // 查询当前医嘱表中所有未审核的执行态
        Map<String, Object> qryMap = new HashMap<String, Object>();
        qryMap.put("running", "0");
        qryMap.put("unCheck", "0");
        List<DoctorAdviceMain> yzMainList = qryListBeanByMap(qryMap, new JqueryStylePaging());
        
        // 获取匹配字段
        String checkParam = SystemConfigFacade.getMap().get("yz_localKnowledge_params").getSys_value();
        
        List<String> checkParams = new ArrayList<String>();
        
        if(null != checkParam)
        {
            for(String param: checkParam.split(";"))
            {
                checkParams.add(param);
            }
        }
        qryMap.put("checkParams", checkParams);
        
        // 拼接自我知识库key值
        String key = "";
        
        // 对比结果:历史数据中审核结果，-1:未审核 0：通过，1：不通过
        String result = "";
        
        String yzzdshzt = "";
        
        Map<String, Object> updateMap = null;
        
        List<String> pidsjN = null;
        
        if (DefineCollectionUtil.isNotEmpty(yzMainList))
        {
            for (DoctorAdviceMain yzMain : yzMainList)
            {
                qryMap.put("parentNo", yzMain.getParentNo());
                
                key = selectKnowledgeKey(qryMap);
                result = redisService.get(key);
                
                // 审核通过
                if("0".equals(result))
                {
                    // 审核通过待确认
                    yzzdshzt = "1";
                }
                
                //审核不通过
                if("1".equals(result))
                {
                    yzzdshzt = "2";
                }
                
                if(null != result)
                {
                    updateMap = new HashMap<String, Object>();
                    
                    pidsjN = new ArrayList<String>();
                    pidsjN.add(yzMain.getPidsj());
                    updateMap.put("pidsjN", pidsjN);
                    updateMap.put("yzzdshzt", yzzdshzt);
                    updateCheckYZ(updateMap);
                    updateCheckYZMain(updateMap);
                }
                
            }
        }
    }

    /**
     * 
     * 设置知识库表主键
     * 
     * @param map
     * @return
     */
    @Override
    public String selectKnowledgeKey(Map<String, Object> map)
    {
        return yzMainDao.selectKnowledgeKey(map);
    }

    /**
     * 
     * 根据病人病区分组
     * 
     * @param map
     * @return
     */
    @Override
    public List<DoctorAdviceAreaPat> groupByAreaPat(Map<String, Object> map){
        //病区
        String inpatientString=(String)map.get("inpatientString");
        if(inpatientString !=null && inpatientString.length() !=0 ){
            String[] wardCodeArray = inpatientString.split(",");
            map.replace("inpatientString", wardCodeArray);
        }
        
        //唯一住院号
        String inpatientNo= (String)map.get("inpatientNo");
        if(inpatientNo !=null && inpatientNo.length() !=0 ){
            String[] inpatientNoArray = inpatientNo.split(",");
            map.replace("inpatientNo", inpatientNoArray);
        }
        
        List<DoctorAdviceAreaPat> groupByAreaPat = new ArrayList<DoctorAdviceAreaPat>();
        List<String> tempInpatientNo =  new ArrayList<String>();
       
        if(map.get("inpatientNo") instanceof String[]){
            String[] inpatinentNoArray=(String[])map.get("inpatientNo");
            for(int a = 0;a < inpatinentNoArray.length; a++){
                if(a != 0 && a%1000 == 0 ){
                    map.replace("inpatientNo", tempInpatientNo);
                    groupByAreaPat.addAll(yzMainDao.groupByAreaPat(map));
                    tempInpatientNo.clear();
                } 
                tempInpatientNo.add(inpatinentNoArray[a]);
            }
            map.replace("inpatientNo", tempInpatientNo);
            
        }
        groupByAreaPat.addAll(yzMainDao.groupByAreaPat(map));
        return groupByAreaPat;
    }

    /**
     * 
     * 删除药学知识库
     */
    @Override
    public void deleteKnowledge()
    {
        yzMainDao.deleteKnowledge();
    }

    /**
     * 
     * 根据自学习状态 添加审方状态
     * 
     * @param map
     * @return
     */
    @Override
    public Integer insertCheckResultMany(Map<String, Object> map)
    {
        return yzMainDao.insertCheckResultMany(map);
    }

    /**
     * 
     * 根据自学习状态 确认审方状态
     * @param map
     * @return
     */
    @Override
    public Integer updateYZMainSHZTByYSH(Map<String, Object> map)
    {
        return yzMainDao.updateYZMainSHZTByYSH(map);
    }
    
    /**
     * 
     * 根据自学习状态 确认审方状态
     * @param map
     * @return
     */
    @Override
    public Integer updateYZSHZTByYSH(Map<String, Object> map)
    {
        return yzMainDao.updateYZSHZTByYSH(map);
    }
    
    /**
     * 
     * 根据自学习状态 确认审方状态
     * @param map
     * @return
     */
    @Override
    public Integer updateYZAndMainSHZTByYSH(Map<String, Object> map)
    {
        int row = yzMainDao.updateYZMainSHZTByYSH(map);
        yzMainDao.updateYZSHZTByYSH(map);
        return row;
    }

    @Override
    public List<ConfigTitleBean> qryTitleList()
    {
        return yzMainDao.qryTitleList();
    }

    @Override
    public void updateRecheckState(String parentNo, Integer newYzshzt)
    {
        yzMainDao.updateRecheckState(parentNo,newYzshzt);
    }

    @Override
    public List<DoctorAdvice> getYZDetail(String groupNo, String drugCode)
    {
        return yzMainDao.getYZDetail(groupNo,drugCode);
    }

    @Override
    public int judgePCExistBy(String parentNo, String dayDate, int pcID)
    {
        return yzMainDao.judgePCExistBy(parentNo,dayDate,pcID);
    }

    @Override
    public String[] qryShowTitle(Long id)
    {
        return yzMainDao.qryShowTitle(id);
    }

    @Override
    public String getTransfusion(Map<String, Object> map)
    {
        return yzMainDao.getTransfusion(map);
    }

    @Override
    public String[] getDateRange(String dayDate) throws Exception
    {
        
        String[] range = new String[2];
        
        if (StringUtils.isBlank(dayDate))
        {
            return null;
        }
        
        if (DATE_START.indexOf(TODAY) < 0 || DATE_END.indexOf(TODAY) < 0 && DATE_END.indexOf(TOMORROW) < 0)
        {
            throw new Exception("配置的时间格式不正确");
        }
        
        String startStr = DATE_START.replace(TODAY, dayDate);
        String endStr = DATE_END.replace(TODAY, dayDate).replace(TOMORROW, dayDate);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Date startD = sdf.parse(startStr);
        Date endD = sdf.parse(endStr);
        
        if (DATE_END.indexOf(TOMORROW) > -1)
        {
            GregorianCalendar gre = new GregorianCalendar();
            gre.setTime(endD);
            gre.add(GregorianCalendar.DAY_OF_MONTH, 1);
            endD = gre.getTime();
        }
        
        range[0] = sdf.format(startD);
        range[1] = sdf.format(endD);
        
        return range;
    }

    @Override
    public BottleLabel getPqInfo(String parentStr, String pidsjStr)
    {
        return yzMainDao.getPqInfo(parentStr,pidsjStr);
    }

    @Override
    public int getPcIsExist(BottleLabel pqBean)
    {
        return yzMainDao.getPcIsExist(pqBean);
    }

}
