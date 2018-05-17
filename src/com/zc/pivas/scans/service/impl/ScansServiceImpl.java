package com.zc.pivas.scans.service.impl;

import com.zc.base.common.util.DateUtil;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.repository.BatchDao;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.web.i18n.MessageHolder;
import com.zc.pivas.docteradvice.bean.BLabelWithPrescription;
import com.zc.pivas.docteradvice.entity.SynDoctorAdviceBean;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import com.zc.pivas.configfee.dao.ChargeDetailsDao;
import com.zc.pivas.checktype.bean.CheckTypeBean;
import com.zc.pivas.checktype.dao.CheckTypeDAO;
import com.zc.pivas.common.util.SysConstant.trackingRecord;
import com.zc.pivas.configfee.bean.ConfigFeeChargeBean;
import com.zc.pivas.configfee.service.ConfigFeeChargeService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import com.zc.pivas.printlabel.entity.BottleLabel;
import com.zc.pivas.printlabel.repository.BottleLabelDao;
import com.zc.pivas.scans.bean.*;
import com.zc.pivas.scans.constant.ScansConstant;
import com.zc.pivas.scans.repository.ScansDao;
import com.zc.pivas.scans.service.ScansService;
import com.zc.pivas.synresult.bean.CheckOrderStatusRespon;
import com.zc.pivas.synresult.bean.ConfigFeeTaskBean;
import com.zc.pivas.synresult.bean.SetFymxDataReq;
import com.zc.pivas.synresult.bean.SetFymxDataResp;
import com.zc.pivas.synresult.dao.ConfigFeeTaskDAO;
import com.zc.pivas.synresult.service.SendToRestful;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 扫码业务实现
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("scansService")
public class ScansServiceImpl implements ScansService
{
    /** 日志类 */
    private static final Logger log = LoggerFactory.getLogger(ScansServiceImpl.class);
    
    @Resource
    private ScansDao scansDao;
    
    @Resource
    private SendToRestful sendToRestful;
    
    @Resource
    private CheckTypeDAO checkTypeDAO;
    
    @Resource
    private ChargeDetailsDao chargeDetailsDao;
    
    @Resource
    private BatchDao batchDao;
    
    @Resource
    private ConfigFeeChargeService configFeeChargeService;
    
    @Resource
    private DoctorAdviceMainService yzMainService;
    
    @Resource
    private MedicamentsDao Medicamentsdao;
    
    @Resource
    private ConfigFeeTaskDAO configFeeTaskDAO;
    
    @Resource
    private BottleLabelDao bottleLabelDao;
    
    @Resource
    private PrescriptionMainService ydMainService;
    
    @Override
    public List<ScansBatchBean> getScansBatchCountInfo()
    {
        //获取瓶签 各状态的统计数据
        List<BottleLabelBean> BottleLabelList = scansDao.queryScansCountList();
        
        if (BottleLabelList == null)
        {
            return null;
        }
        
        List<ScansBatchBean> scansBatchList = new ArrayList<ScansBatchBean>();
        for (BottleLabelBean bottleLabelItem : BottleLabelList)
        {
            //执行批次 ID
            String batchID = bottleLabelItem.getzXBC().toString();
            
            String batchName = bottleLabelItem.getBatchName();
            
            List<String> amList = Arrays.asList(Propertiesconfiguration.getStringProperty("check.am").split(","));
            
            List<String> pmList = Arrays.asList(Propertiesconfiguration.getStringProperty("check.pm").split(","));
            
            //如果是特殊批次 置空批次id
            if (amList.contains(batchName))
            {
                batchName = "上午";
            }
            
            if (pmList.contains(batchName))
            {
                batchName = "下午";
            }
            
            //找查批次信息是否存在
            ScansBatchBean scansBatchDesc = null;
            for (ScansBatchBean scansBatchItem : scansBatchList)
            {
                
                if (batchName.equals(scansBatchItem.getBatchName()))
                {
                    if (!scansBatchItem.getzXBC().contains(batchID))
                    {
                        batchID = scansBatchItem.getzXBC() + "," + batchID;
                    }
                    scansBatchItem.setzXBC(batchID);
                    scansBatchDesc = scansBatchItem;
                    
                }
                
            }
            
            //如果不存，直接新增加批次信息
            if (scansBatchDesc == null)
            {
                scansBatchDesc = new ScansBatchBean();
                scansBatchDesc.setzXBC(batchID.toString());
                scansBatchDesc.setBatchName(batchName);
                
                scansBatchList.add(scansBatchDesc);
            }
            //获取 药单状态步骤  0,执行  1,停止  2,撤销 3,未打印  4,已打印  5,入仓扫描核对完成  6,仓内扫描核对完成 7,出仓扫描核对完成
            Integer bottleLabelStatus = bottleLabelItem.getyDZT();
            //获取统计数量
            Integer sumCount = bottleLabelItem.getBatchCount();
            
            //入仓总数量
            scansBatchDesc.addEnterSumScansCount(sumCount);
            
            //已打印
            if (bottleLabelStatus <= ScansConstant.BottleLabelStatus.PRINT)
            {
                //入仓未扫描
                scansBatchDesc.setEnterUnScansCount(scansBatchDesc.getEnterUnScansCount() + sumCount);
                
            }
            //入仓扫描核对完成 
            else if (ScansConstant.BottleLabelStatus.IN.equals(bottleLabelStatus))
            {
                //仓内总数量
                // scansBatchDesc.addOutSumScansCount(sumCount);
                //仓内未扫描
                //scansBatchDesc.setOutUnScansCount(sumCount);
            }
            //仓内扫描核对完成 
            else if (ScansConstant.BottleLabelStatus.INSIDE.equals(bottleLabelStatus))
            {
                //出仓总数量
                scansBatchDesc.addOutSumScansCount(sumCount);
                
                //出仓未扫描
                scansBatchDesc.setOutUnScansCount(sumCount);
            }
            //出仓扫描核对完成 
            else if (ScansConstant.BottleLabelStatus.OUT.equals(bottleLabelStatus))
            {
                //出仓总数量
                scansBatchDesc.addOutSumScansCount(sumCount);
            }
        }
        
        return scansBatchList;
    }
    
    @Override
    public BottleLabelBean queryBottleLabelInfo(String BtSn, String batchID, String qryRQ)
    {
        return scansDao.queryBottleLabelInfo(BtSn, batchID, qryRQ);
    }
    
    @Override
    public String in_out_pack(String scansType, final List<MedicineBean> medicineBeanList, final BLabelWithPrescription pqyd, final String username)
    {
        if (medicineBeanList != null)
        {
            for (MedicineBean bean : medicineBeanList)
            {
                String medicCode = bean.getMedicamentsCode();
                Medicaments Singlebean = Medicamentsdao.getMediclByCode(medicCode);
                if (Singlebean == null)
                {
                    return "药品" + bean.getDrugName() + "不存在，请检查";
                }
            }
        }
        
        List<CheckTypeBean> checkTypeList = null;
        int ydzt_row = 0;
        if (scansType.equals(ScansConstant.BottleLabelAction.IN))
        {
            ydzt_row = scansDao.changePQYdzt(ScansConstant.BottleLabelStatus.IN, pqyd.getPq_pidsj());
            checkTypeList = checkTypeDAO.queryCheckTypeAllList("0");
        }
        else if (scansType.equals(ScansConstant.BottleLabelAction.OUT))
        {
            ydzt_row = scansDao.changePQYdzt(ScansConstant.BottleLabelStatus.OUT, pqyd.getPq_pidsj());
            checkTypeList = checkTypeDAO.queryCheckTypeAllList("2");
        }
        else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
        {
            ydzt_row = scansDao.changePQYdzt(ScansConstant.BottleLabelStatus.INSIDE, pqyd.getPq_pidsj());
            checkTypeList = checkTypeDAO.queryCheckTypeAllList("1");
        }
        
        if (ydzt_row > 0)
        {
            if (checkTypeList != null && checkTypeList.size() > 0)
            {
                //是否减库存
                log.info("BOTTLE NUM:[" + pqyd.getPq_ydpq() + "] SCANTYPE:[" + scansType + "] CHARGE:["
                    + checkTypeList.get(0).getIsCharge() + "] STOCK:[" + checkTypeList.get(0).getIsStock() + "]");
                if (checkTypeList.get(0).getIsStock() == 0)
                {
                    Integer ret = ChangeStock(medicineBeanList, pqyd.getPq_ydpq());
                    if (ret != 0)
                    {
                        log.error("bottleNum:[" + pqyd.getPq_ydpq() + "] change stock failed");
                        
                        if (ret == -2)
                        {
                            return "药品不存在";
                        }
                        else
                        {
                            return "扣减库存失败";
                        }
                    }
                }
                
                if (!ScansConstant.BatchType.EMPTY_BATCH.equals(pqyd.getPb_isEmptyBatch())
                    && checkTypeList.get(0).getIsCharge() == 0)
                {
                    if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
                    {
                        if (IsInOutPackageCharged(pqyd))
                        {
                            log.info("瓶签:[" + pqyd.getPq_ydpq() + "]存在其他收费项目");
                            return null;
                        }
                    }
                    
                    // 收取配置费
                    Thread task = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            SetConfigFeeThreed(medicineBeanList, pqyd, username, pqyd.getPq_pidsj());
                        }
                    });
                    task.start();
                    
                }
            }
            return "";
        }
        else
        {
            return "更新瓶签状态失败";
        }
    }
    
    private Integer ChangeStock(List<MedicineBean> medicineBeanList, String bottleNum)
    {
        for (MedicineBean bean : medicineBeanList)
        {
            String medicCode = bean.getMedicamentsCode();
            Medicaments Singlebean = Medicamentsdao.getMediclByCode(medicCode);
            
            if (Singlebean == null)
            {
                log.error("药品不存在");
                return -2;
            }
            
            Long medicamentsId = Singlebean.getMedicamentsId();
            String stockStr = Singlebean.getMedicamentsStock();
            //String consumptionStr = Singlebean.getConsumption();
            //String usedStr = Singlebean.getUsed();
            String quantity = bean.getQuantityOnly();
            log.info("瓶签[" + bottleNum + "] MEDICAID[" + medicamentsId + "] BEFORE STOCK:[" + stockStr + "] NUM:["
                + quantity + "]");
            
            Integer stock = 0;
            //Double consumption = null;
            //Integer used = 0;
            Double medicNum = null;
            
            try
            {
                BigDecimal num = new BigDecimal(stockStr);
                //stock = Integer.valueOf(stockStr);
                stock = num.intValue();
                //consumption = Double.valueOf(consumptionStr);
                //used = Integer.valueOf(usedStr);
                medicNum = Double.valueOf(quantity);
            }
            catch (NumberFormatException e)
            {
                log.error("cast stock failed", e);
                log.error("瓶签[" + bottleNum + "]更新库存失败");
                return -1;
            }
            
            //STOCK-CEIL CONSUMPTION+NONCEIL USED+CEIL
            Double dminus = Math.ceil(medicNum);
            int iminus = dminus.intValue();
            stock -= iminus;
            //consumption += medicNum;
            //used += iminus;
            
            log.info("瓶签[" + bottleNum + "] MEDICAID[" + medicamentsId + "] AFTER STOCK:[" + stock + "] NUM:["
                + medicNum + "]");
            
            Singlebean.setMedicamentsStock(stock.toString());
            //Singlebean.setUsed(used.toString());
            
            try
            {
                Medicamentsdao.updateByPrimaryKeySelective(Singlebean);
            }
            catch (Exception e)
            {
                log.error("Update medicaments failed", e);
                return -1;
            }
        }
        
        return 0;
    }
    
    private boolean IsInOutPackageCharged(BLabelWithPrescription pqyd)
    {
        List<CheckTypeBean> checkTypeList = null;
        
        checkTypeList = checkTypeDAO.queryCheckTypeAllList("0");
        
        if (checkTypeList != null && checkTypeList.size() > 0)
        {
            if (!ScansConstant.BatchType.EMPTY_BATCH.equals(pqyd.getPb_isEmptyBatch())
                && checkTypeList.get(0).getIsCharge() == 0)
            {
                return true;
            }
        }
        
        checkTypeList = checkTypeDAO.queryCheckTypeAllList("2");
        
        if (checkTypeList != null && checkTypeList.size() > 0)
        {
            if (!ScansConstant.BatchType.EMPTY_BATCH.equals(pqyd.getPb_isEmptyBatch())
                && checkTypeList.get(0).getIsCharge() == 0)
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public List<ScansBatchBean> scansDeptStatistics(ScansSearchBean scansSearch, MessageHolder messageHolder)
    {
        
        //获取瓶签 各状态的统计数据
        List<BottleLabelBean> BottleLabelList = scansDao.queryScansCountByDeptList(scansSearch);
        if (BottleLabelList == null)
        {
            return null;
        }
        
        List<ScansBatchBean> scansBatchList = new ArrayList<ScansBatchBean>();
        ScansBatchBean scansBatchAll = new ScansBatchBean();
        scansBatchList.add(scansBatchAll);
        scansBatchAll.setDeptName(messageHolder.getMessage("scans.deptName.all"));
        
        for (BottleLabelBean bottleLabelItem : BottleLabelList)
        {
            //执行批次 ID
            //Integer batchID = bottleLabelItem.getzXBC();
            String deptName = bottleLabelItem.getDeptName();
            //找查批次信息是否存在
            ScansBatchBean scansBatchDesc = null;
            for (ScansBatchBean scansBatchItem : scansBatchList)
            {
                if (deptName.equals(scansBatchItem.getDeptName()))
                {
                    scansBatchDesc = scansBatchItem;
                }
            }
            //如果不存，直接新增加批次信息
            if (scansBatchDesc == null)
            {
                scansBatchDesc = new ScansBatchBean();
                //scansBatchDesc.setBatchName(bottleLabelItem.getBatchName());
                scansBatchDesc.setDeptName(deptName);
                scansBatchList.add(scansBatchDesc);
            }
            //获取 药单状态步骤  0,执行  1,停止  2,撤销 3,未打印  4,已打印  5,入仓扫描核对完成  6,出仓扫描核对完成
            Integer bottleLabelStatus = bottleLabelItem.getyDZT();
            //获取统计数量
            Integer sumCount = bottleLabelItem.getBatchCount();
            
            //已打印和未打印 小于状态5的都做统计
            //if (ScansConstant.BottleLabelStatus.PRINT.equals(bottleLabelStatus))
            
            //入仓总数量
            scansBatchDesc.addEnterSumScansCount(sumCount);
            scansBatchAll.addEnterSumScansCount(sumCount);
            
            if (bottleLabelStatus < ScansConstant.BottleLabelStatus.IN)
            {
                //入仓未扫描
                //scansBatchDesc.setEnterUnScansCount(sumCount);
                //入仓已扫描
                //scansBatchDesc.setEnterFinishScansCount(sumCount);
                //入仓总数量
                
            }
            //入仓扫描核对完成 
            else if (ScansConstant.BottleLabelStatus.IN.equals(bottleLabelStatus))
            {
                //出仓总数量
                scansBatchDesc.addOutSumScansCount(sumCount);
                scansBatchAll.addOutSumScansCount(sumCount);
                //入仓已扫描
                scansBatchDesc.addEnterFinishScansCount(sumCount);
                scansBatchAll.addEnterFinishScansCount(sumCount);
            }
            //出仓扫描核对完成 
            else if (ScansConstant.BottleLabelStatus.OUT.equals(bottleLabelStatus))
            {
                //入仓已扫描
                scansBatchDesc.addEnterFinishScansCount(sumCount);
                scansBatchAll.addEnterFinishScansCount(sumCount);
                
                //出仓总数量
                scansBatchDesc.addOutSumScansCount(sumCount);
                scansBatchAll.addOutSumScansCount(sumCount);
                
                //出仓已扫描
                scansBatchDesc.addOutFinishScansCount(sumCount);
                scansBatchAll.addOutFinishScansCount(sumCount);
                
            }
            
        }
        return scansBatchList;
    }
    
    @Override
    public List<BottleLabelBean> queryBottleLabelList(ScansSearchBean scansSearch)
    {
        
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList)
        {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }
        
        List<BottleLabelBean> bottleLabelList = new ArrayList<BottleLabelBean>();
        
        if (midFlag)
        {
            bottleLabelList = scansDao.queryBottleLabelList(scansSearch);
        }
        else
        {
            
            bottleLabelList = scansDao.queryBottleLabelListWidthOutMid(scansSearch);
        }
        
        /*
        for (BottleLabelBean bottleLabelBeanItem : bottleLabelList)
        {
            Integer zxbc = bottleLabelBeanItem.getzXBC();
            //查询入仓瓶签结果
            BottleLabelResult bottleLabelResult =
                scansDao.queryBottleLabelResult(bottleLabelBeanItem.getParentNo(), scansSearch.getSmLX(), zxbc);
            if (bottleLabelResult != null)
            {
                bottleLabelBeanItem.setSmSBYY(bottleLabelResult.getSmSBYY());
                bottleLabelBeanItem.setSmRQ(bottleLabelResult.getSmRQ());
                bottleLabelBeanItem.setSmJG(bottleLabelResult.getSmJG());
            }
        }
        */
        return bottleLabelList;
    }
    
    public void chargeInfo(ScansConfigFeeBean scansConfigFeeBean)
    {
        
        if (scansConfigFeeBean.getConfigFeeRuleId() != null)
        {
            List<String> costCode =
                scansDao.queryConfigfeeDetailList(scansConfigFeeBean.getConfigFeeRuleId().toString());
            for (String costCodeItem : costCode)
            {
                scansConfigFeeBean.setCostcode(costCodeItem);
                changCostCode(scansConfigFeeBean);
            }
        }
        
    }
    
    @Override
    public Integer changCostCode(ScansConfigFeeBean scansConfigFeeBean)
    {
        Integer iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_SUCESS;
        SetFymxDataReq req = new SetFymxDataReq();
        //住院流水号
        req.setAlzyh(scansConfigFeeBean.getCaseID());
        //计费项目医院内部序号
        req.setAlfyxh(scansConfigFeeBean.getCostcode());
        //执行工号
        req.setAszxgh(Propertiesconfiguration.getStringProperty("fee.zxgh"));
        //数量
        req.setAdfysl(scansConfigFeeBean.getAmount().toString());
        String pzfsbyy = "";
        try
        {
            SetFymxDataResp setFymxDataResp = sendToRestful.setFymxData(req);
            //返回成功失败标志 1  成功  -1 失败
            String sRst = setFymxDataResp.getAlret();
            if (!"1".equals(sRst))
            {
                iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_FAIL;
                pzfsbyy = setFymxDataResp.getAserrtext();
            }
        }
        catch (Exception e)
        {
            iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_FAIL;
        }
        scansConfigFeeBean.setPzFZT(iRst);
        scansConfigFeeBean.setPzFSBYY(pzfsbyy);
        
        return iRst;
    }
    
    @Override
    public Integer changCostCodeByDetai(ChargeDetailsBean chargeDetailsBean)
    {
        //待开发 定时任务执行：Propertiesconfiguration.getStringProperty("password.minLength");
        
        Integer iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_SUCESS;
        SetFymxDataReq req = new SetFymxDataReq();
        //住院流水号
        req.setAlzyh(chargeDetailsBean.getcASE_ID());
        //计费项目医院内部序号
        req.setAlfyxh(chargeDetailsBean.getCostcode());
        //执行工号
        req.setAszxgh(Propertiesconfiguration.getStringProperty("fee.zxgh"));
        //药品数量
        req.setAdfysl(chargeDetailsBean.getqUANTITY());
        
        String pzfsbyy = "";
        try
        {
            SetFymxDataResp setFymxDataResp = sendToRestful.setFymxData(req);
            //返回成功失败标志 1  成功  -1 失败
            String sRst = setFymxDataResp.getAlret();
            if (!"1".equals(sRst))
            {
                iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_FAIL;
                pzfsbyy = setFymxDataResp.getAserrtext();
            }
        }
        catch (Exception e)
        {
            iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_FAIL;
        }
        chargeDetailsBean.setpZFZT(iRst);
        chargeDetailsBean.setpZFSBYY(pzfsbyy);
        chargeDetailsDao.insertPzf(chargeDetailsBean);
        return iRst;
    }
    
    @Override
    public void changePQStateByParentNo(Map<String, Object> yzMap)
    {
        scansDao.changePQStateByParentNo(yzMap);
    }
    
    /**
     * 
     * 发送配置费线程 内部类
     *
     * @author  cacabin
     * @version  1.0
     */
    private void SetConfigFeeThreed(List<MedicineBean> medicineBeanList, BLabelWithPrescription pqWidthYD, String ygbh, String pidsj)
    {
        
        log.info("start send pzf threed");
        
        try
        {
            List<SynDoctorAdviceBean> yzList = new ArrayList<SynDoctorAdviceBean>();
            
            List<ScansConfigFeeBean> scansConfigFeeBeanList = new ArrayList<ScansConfigFeeBean>();
            
            //计费list
            List<ConfigFeeChargeBean> feeList = null;
            
            for (MedicineBean medicineBean : medicineBeanList)
            {
                SynDoctorAdviceBean syb = new SynDoctorAdviceBean();
                //病人编号
                syb.setInhospNo(pqWidthYD.getYd_inpatientNo());
                //药品编码
                syb.setDrugCode(medicineBean.getMedicamentsCode());
                //药品数量
                syb.setDrugAmount(medicineBean.getQuantityOnly());
                syb.setDrugUseOneDosAge(medicineBean.getDoseOnly());
                //药剂单位
                syb.setDrugUseOneDosAgeUnit(medicineBean.getDoseUnit());
                
                syb.setOrderNo(medicineBean.getActOrderNo());
                
                syb.setOrderGroupNo(medicineBean.getParentNo());
                yzList.add(syb);
            }
            
            if (!yzList.isEmpty())
            {
                
                feeList = configFeeChargeService.findConfigCharge(yzList);
                
                if (feeList != null && !feeList.isEmpty())
                {
                    ConfigFeeTaskBean configFeeTaskBean = null;
                    for (ConfigFeeChargeBean configFeeChargeBean : feeList)
                    {
                        configFeeTaskBean = new ConfigFeeTaskBean();
                        ScansConfigFeeBean scansConfigFeeBean = new ScansConfigFeeBean();
                        
                        scansConfigFeeBean.setConfigFeeRuleId(Integer.valueOf(configFeeChargeBean.getconfigfeeruleid()));
                        // scansConfigFeeBean.setPidrqzxbc(medicineBean.getPidrqzxbc());
                        scansConfigFeeBean.setCzYMC(ygbh);
                        scansConfigFeeBean.setCaseID(pqWidthYD.getYd_inpatientNo());
                        scansConfigFeeBean.setCostcode(configFeeChargeBean.getCostCode());
                        scansConfigFeeBean.setPzFSBYY("");
                        scansConfigFeeBean.setPrice(configFeeChargeBean.getprice());
                        scansConfigFeeBean.setAmount(Integer.valueOf(configFeeChargeBean.getAmount()));
                        scansConfigFeeBean.setDeptCode(pqWidthYD.getYd_wardCode());
                        scansConfigFeeBean.setPqId(pqWidthYD.getYd_zxbc());
                        
                        // 保存需要发送的数据到配置费任务表
                        configFeeTaskBean = new ConfigFeeTaskBean();
                        configFeeTaskBean.setYd_pidsj(pidsj);
                        configFeeTaskBean.setAccount(ygbh);
                        configFeeTaskBean.setAmount(configFeeChargeBean.getAmount());
                        configFeeTaskBean.setCaseID(pqWidthYD.getYd_inpatientNo());
                        configFeeTaskBean.setCostCode(configFeeChargeBean.getCostCode());
                        
                        try
                        {
                            configFeeTaskDAO.inster(configFeeTaskBean);
                        }
                        catch (Exception e)
                        {
                            log.error("pidsj:" + pidsj + "配置费任务已存在");
                        }
                        scansConfigFeeBeanList.add(scansConfigFeeBean);
                    }
                    
                    Date date = new Date();
                    String strData = DateUtil.format(new Date(), "yyyy-MM-dd");
                    
                    //保存瓶签与配置费的关系
                    PqRefFee prf = new PqRefFee();
                    prf.setPidsj(pqWidthYD.getYd_pidsj());
                    prf.setResult(null);
                    prf.setDate_details(strData);
                    prf.setResult_details("");
                    scansDao.savePqRefFee(prf);
                    
                    // 保存配置费的结果
                    for (ScansConfigFeeBean scansConfigFeeBean : scansConfigFeeBeanList)
                    {
                        scansConfigFeeBean.setPqRefFeeId(prf.getGid());
                        scansConfigFeeBean.setChargeDate(date);
                        scansDao.insertConfigFee(scansConfigFeeBean);
                    }
                }
                else
                {
                    log.info("feeList is empty");
                }
            }
            else
            {
                log.info("medicineBeanList is empty");
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
    }
    
    public List<Map<String, Object>> qryCountMain(Map<String, Object> map)
    {
        return scansDao.qryCountMain(map);
    }
    
    public List<Map<String, Object>> qryCountMainWidthOutMid(Map<String, Object> map)
    {
        return scansDao.qryCountMainWidthOutMid(map);
    }
    
    public List<Map<String, Object>> qryCountByPcname(Map<String, Object> map)
    {
        return scansDao.qryCountByPcname(map);
    }
    
    public List<Map<String, Object>> qryCountByPcnameWidthOutMid(Map<String, Object> map)
    {
        return scansDao.qryCountByPcnameWidthOutMid(map);
    }
    
    @Override
    public void changePQBedno(Map<String, Object> yzMap)
    {
        // 获取需要修改瓶签信息
        List<BottleLabel> bottleList = bottleLabelDao.queryPQBedNoForUpdate(yzMap);
        
        if (DefineCollectionUtil.isNotEmpty(bottleList))
        {
            // 修改前床号
            String bedNoOld = "";
            
            // 修改后床号
            String bedNoNew = "";
            
            String mainHtml = "";
            String mainDrugHtml = "";
            String menstruumHtml = "";
            for (BottleLabel label : bottleList)
            {
                mainHtml = "";
                mainDrugHtml = "";
                menstruumHtml = "";
                
                bedNoOld = label.getBedNo().replaceAll("\\+", "X");
                bedNoNew = String.valueOf(yzMap.get("bedNo"));
                
                if (null != label.getMainHtml() && !"".equals(label.getMainHtml()))
                {
                    mainHtml = label.getMainHtml().replaceAll("\\+", "X");
                    mainHtml = mainHtml.replaceAll(bedNoOld, bedNoNew);
                }
                
                if (null != label.getMainDrugHtml() && !"".equals(label.getMainDrugHtml()))
                {
                    mainDrugHtml = label.getMainDrugHtml().replaceAll("\\+", "X");
                    mainDrugHtml = mainDrugHtml.replaceAll(bedNoOld, bedNoNew);
                }
                
                if (null != label.getMenstruumHtml() && !"".equals(label.getMenstruumHtml()))
                {
                    menstruumHtml = label.getMenstruumHtml().replaceAll("\\+", "X");
                    menstruumHtml = menstruumHtml.replaceAll(bedNoOld, bedNoNew);
                }
                
                yzMap.put("firstHtml", mainHtml);
                yzMap.put("html", mainDrugHtml);
                yzMap.put("menstruumHtml", menstruumHtml);
                yzMap.put("pidsj", label.getPidsj());
                scansDao.changePQBedno(yzMap);
            }
        }
        
    }
        
    
    
    @Override
    public List<BLabelWithPrescription> queryPQYDListByMap(Map<String, Object> map)
    {
        return scansDao.queryPQYDListByMap(map);
    }
    
    @Override
    public BLabelWithPrescription queryPQYDListByBottNum(String ydpq2)
    {
        if (ydpq2 != null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pq_ydpq2", ydpq2);
            List<BLabelWithPrescription> list = scansDao.queryPQYDListByBottNum(map);
            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
        }
        return null;
    }
    
    @Override
    public List<MedicineBean> queryMedicineList2(String pidsj)
    {
        return scansDao.queryMedicineList2(pidsj);
    }
    
    public int changePQYdzt(Integer ydzt, String pidsj)
    {
        return scansDao.changePQYdzt(ydzt, pidsj);
    }
    
    public BottleLabelResult queryBottleLabelResult2(String pidsj)
    {
        return scansDao.queryBottleLabelResult2(pidsj);
    }
    
    @Override
    public AppScanRet appScanNew(String bottleNum, String scansType, String tim, String strUser)
        throws Exception
    {
        AppScanRet appScanRet = new AppScanRet();
        appScanRet.ret = -1;
        appScanRet.msg = "";
        
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList)
        {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }
        
        String errMess = "";
        
        if (StringUtils.isNotBlank(bottleNum) && StringUtils.isNotBlank(scansType))
        {
            
            BLabelWithPrescription pqyd = this.queryPQYDListByBottNum(bottleNum);
            
            if (pqyd != null)
            {
                
                //判断瓶签是否变动
                if (!bottleNum.equals(pqyd.getPq_ydpq()))
                {
                    //因批次变动
                    if (StringUtils.isNotBlank(pqyd.getPq_zxbc().toString())
                        && StringUtils.isNotBlank(pqyd.getPq_zxbcLast().toString())
                        && pqyd.getPq_zxbc().intValue() != pqyd.getPq_zxbcLast().intValue())
                    {
                        errMess = "批次已变更，新批次为" + pqyd.getPb_name();
                    }//因瓶签号变动
                    else
                    {
                        errMess = "瓶签已变更，新瓶签为" + pqyd.getPq_ydpq();
                    }
                    
                    appScanRet.msg = errMess;
                    return appScanRet;
                }
                
                //药单状态是否执行
                if (pqyd.getYd_ydzxzt() != null && pqyd.getYd_ydzxzt().intValue() == 0)
                {
                    
                    if (pqyd.getYd_dybz() == null || pqyd.getYd_dybz().intValue() != 0)
                    {
                        errMess = "瓶签未打印";
                        
                    }
                    else
                    {
                        
                        String yyrq = DateUtil.getDay8StrByDay(pqyd.getYd_yyrq());
                        
                        if (!tim.equals(yyrq))
                        {
                            //errMess = "非当日药单";
                            
                        }
                        else if (pqyd.getYyrqDelToday().intValue() >= 0)
                        {
                            
                            if (scansType.equals(ScansConstant.BottleLabelAction.IN))
                            {
                                if (pqyd.getPq_ydzt().intValue() > 4)
                                {
                                    errMess = "重复扫描，瓶签号[" + bottleNum + "]";
                                }
                            }
                            else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
                            {
                                if (pqyd.getPq_ydzt().intValue() > 5)
                                {
                                    errMess = "重复扫描，瓶签号[" + bottleNum + "]";
                                }
                                else if (pqyd.getPq_ydzt().intValue() < 5)
                                {
                                    errMess = "瓶签号[" + bottleNum + "]还未入仓";
                                }
                            }
                            else if (scansType.equals(ScansConstant.BottleLabelAction.OUT))
                            {
                                if (pqyd.getPq_ydzt().intValue() > 6)
                                {
                                    errMess = "重复扫描，瓶签号[" + bottleNum + "]";
                                }
                                else if (pqyd.getPq_ydzt().intValue() < 5)
                                {
                                    errMess = "瓶签号[" + bottleNum + "]还未入仓";
                                    
                                }
                                else if (midFlag && pqyd.getPq_ydzt().intValue() < 6)
                                {
                                    
                                    errMess = "瓶签号[" + bottleNum + "]还未仓内扫描";
                                }
                            }
                            
                        }
                        else
                        {
                            //errMess = "非当日或明日药单";
                        }
                    }
                }
                else if (pqyd.getYd_ydzxzt().intValue() == 1)
                {
                    
                    errMess = "医嘱状态已停止";
                    scansDao.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    
                }
                else if (pqyd.getYd_ydzxzt().intValue() == 3)
                {
                    
                    errMess = "医嘱状态已退费";
                    scansDao.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                }
                
                if (StringUtils.isNotBlank(errMess))
                {
                    
                    saveSmResult(scansType, errMess, pqyd, false);
                    
                    appScanRet.msg = errMess;
                    return appScanRet;
                }
                
                Date start = new Date();
                
                JSONObject data = new JSONObject();
                List<JSONObject> bottleNumList = new ArrayList<JSONObject>();
                
                data.put("parentNo", pqyd.getYd_parentNo());
                data.put("bottleNo", bottleNum);
                bottleNumList.add(data);
                
                List<CheckOrderStatusRespon> checkOrderStatusList = sendToRestful.checkOrderStatus(bottleNumList);
                
                log.info("调用检查医嘱状态接口：" + String.valueOf(new Date().getTime() - start.getTime()) + "\n");
                
                if (checkOrderStatusList != null && checkOrderStatusList.size() > 0)
                {
                    
                    CheckOrderStatusRespon checkOrderStatusRespon = checkOrderStatusList.get(0);
                    if (ScansConstant.BottleLabelStatus.STOP.toString().equals(checkOrderStatusRespon.getResult()))
                    {
                        Map<String, Object> updateMap = new HashMap<String, Object>();
                        updateMap.put("parentNo", pqyd.getYd_parentNo());
                        yzMainService.resetYZCheckStatus(updateMap);
                        errMess = "医嘱已停止";
                        scansDao.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    }
                    else if ("3".equals(checkOrderStatusRespon.getResult()))
                    {//如果调用接口，返回状态3，表示药品有变化，需要特殊处理
                        if (scansType.equals(ScansConstant.BottleLabelAction.IN))
                        {
                            if (pqyd.getScrqDelToday() >= 0)
                            {//当天审核的医嘱，如果药品数据有变化，提示重新审核医嘱 排批次
                                Map<String, Object> updateMap = new HashMap<String, Object>();
                                //updateMap.put("yzlx", pqyd.getYd_yzlx());
                                updateMap.put("parentNo", pqyd.getYd_parentNo());
                                yzMainService.resetYZCheckStatus(updateMap);
                                //清空 医嘱 审核状态
                                errMess = checkOrderStatusRespon.getResultDesc() + "，需重新审核医嘱";
                            }
                            else
                            {
                                //errMess = checkOrderStatusRespon.getResultDesc() + "，非当日药单，无法入仓扫描";
                            }
                        }
                        else if (scansType.equals(ScansConstant.BottleLabelAction.OUT))
                        {
                            errMess = checkOrderStatusRespon.getResultDesc() + "，无法出仓";
                        }
                    }
                    else if ("4".equals(checkOrderStatusRespon.getResult()))
                    {
                        
                        errMess = "药单已停止";
                        scansDao.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    }
                    else if ("5".equals(checkOrderStatusRespon.getResult()))
                    {
                        
                        errMess = "药单已退费";
                        scansDao.updateCheckType(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), scansType);
                    }
                }
                else
                {
                    
                    errMess = "扫描失败";
                }
                
                if (StringUtils.isNotBlank(errMess))
                {
                    
                    saveSmResult(scansType, errMess, pqyd, false);
                    appScanRet.msg = errMess;
                    return appScanRet;
                }
                
                List<MedicineBean> medicineBeanList = scansDao.queryMedicineList2(pqyd.getPq_pidsj());
                
                errMess = this.in_out_pack(scansType, medicineBeanList, pqyd, strUser);
                
                if (StringUtils.isNotBlank(errMess))
                {
                    saveSmResult(scansType, errMess, pqyd, false);
                    appScanRet.msg = errMess;
                    return appScanRet;
                }
                
                //将rucangNeedCheck 重置成0 【 医嘱变化更新成1后 入仓成功 要重置成0】
                Map<String, Object> updateMap = new HashMap<String, Object>();
                updateMap.put("rucangNeedCheck", 0);
                updateMap.put("yzlx", pqyd.getYd_yzlx());
                updateMap.put("parentNo", pqyd.getYd_parentNo());
                yzMainService.updateCheckYZMain(updateMap);
                
                saveSmResult(scansType, "", pqyd, true);
                
                int recordNum = 0;
                String recordMsg = "";
                if (scansType.equals(ScansConstant.BottleLabelAction.IN))
                {
                    recordNum = trackingRecord.rcsm;
                    recordMsg = trackingRecord.rcsmStr;
                }
                else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
                {
                    
                    recordNum = trackingRecord.cnsm;
                    recordMsg = trackingRecord.cnsmStr;
                }
                else if (scansType.equals(ScansConstant.BottleLabelAction.OUT))
                {
                    
                    recordNum = trackingRecord.ccsm;
                    recordMsg = trackingRecord.ccsmStr;
                }
                
                ydMainService.addTrackingRecord(pqyd.getPq_pidsj(), recordNum, recordMsg, strUser);
                
                if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
                {
                    
                    scansDao.addPQConfigurator(pqyd.getPq_pidsj(), pqyd.getPq_ydpq(), strUser);
                }
                
                appScanRet.msg = "扫描成功";
                appScanRet.ret = 1;
                return appScanRet;
                
            }
            else
            {
                
                appScanRet.msg = "瓶签不存在";
                return appScanRet;
            }
            
        }
        else
        {
            
            log.error("扫描瓶签获取瓶签号或扫描类型信息失败");
            appScanRet.msg = "扫描失败";
            return appScanRet;
        }
        
    }
    
    public AppScanRet appScan(String barcode, String scansType, String batchID, String qryRQ, String batchName,
        String username)
    {
        AppScanRet appScanRet = new AppScanRet();
        appScanRet.ret = -1;
        appScanRet.msg = "";
        
        if (!scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
        {
            appScanRet.msg = "只允许在PDA APP上进行仓内扫描";
            return appScanRet;
        }
        
        List<CheckTypeBean> checkTypeList = checkTypeDAO.queryCheckTypeAllList(null);
        boolean midFlag = false;//是否存在仓内核对
        for (CheckTypeBean type : checkTypeList)
        {
            int checkType = type.getCheckType();
            if (checkType == 1)
                midFlag = true;
        }
        
        Date excute = new Date();
        String errMess = null;
        String succMess = null;
        BLabelWithPrescription pqyd = null;
        boolean recordErr = false;
        if (barcode != null && scansType != null && batchName != null)
        {
            pqyd = this.queryPQYDListByBottNum(barcode);
            if (pqyd == null)
            {
                errMess = "瓶签不存在";
            }
            else
            {
                try
                {
                    JSONObject data = new JSONObject();
                    List<JSONObject> bottleNumList = new ArrayList<JSONObject>();
                    data.put("parentNo", pqyd.getYd_parentNo());
                    data.put("bottleNo", barcode);
                    bottleNumList.add(data);
                    
                    //正式
                    Date start = new Date();
                    List<CheckOrderStatusRespon> checkOrderStatusList = sendToRestful.checkOrderStatus(bottleNumList);
                    
                    log.info("调用检查医嘱状态接口：" + String.valueOf(new Date().getTime() - start.getTime()) + "\n");
                    
                    //测试q
                    /*List<CheckOrderStatusRespon> checkOrderStatusList =
                        new ArrayList<CheckOrderStatusRespon>();
                    CheckOrderStatusRespon res = new CheckOrderStatusRespon();
                    res.setResult("0");
                    checkOrderStatusList.add(res);
                    */
                    for (CheckOrderStatusRespon checkOrderStatusRespon : checkOrderStatusList)
                    {
                        if (ScansConstant.BottleLabelStatus.STOP.toString().equals(checkOrderStatusRespon.getResult()))
                        {
                            Map<String, Object> updateMap = new HashMap<String, Object>();
                            //updateMap.put("yzlx", pqyd.getYd_yzlx());
                            updateMap.put("parentNo", pqyd.getYd_parentNo());
                            yzMainService.resetYZCheckStatus(updateMap);
                            /*
                            List<String> deletePidsjN = new ArrayList<String>();
                            deletePidsjN.add(pqyd.getYd_parentNo() + "_" + 1 + "_"
                                + DateUtil.getCurrentDay8Str());
                            deletePidsjN.add(pqyd.getYd_parentNo() + "_" + 0 + "_"
                                + DateUtil.getCurrentDay8Str());
                            List<String> YZUpdatePidsjN = new ArrayList<String>();// 更新医嘱状态--医嘱pidsj
                            YZUpdatePidsjN.add(pqyd.getYd_parentNo() + "_0_0_0_0");
                            YZUpdatePidsjN.add(pqyd.getYd_parentNo() + "_1_0_0_0");
                            Map<String, Object> updateMap2 = new HashMap<String, Object>();
                            updateMap.put("yzshzt", "0");
                            updateMap.put("yzshbtglx", null);
                            updateMap.put("yzshbtgyy", null);
                            updateMap.put("sfysmc", null);
                            updateMap.put("sfyscode", null);
                            updateMap.put("sfrq", new Date());
                            updateMap.put("pidsjN", YZUpdatePidsjN);
                            yzMainService.updateCheckYZMain(updateMap2, deletePidsjN);
                            */
                            errMess = "医嘱已停止";
                            recordErr = true;
                        }
                        else if ("3".equals(checkOrderStatusRespon.getResult()))
                        {//如果调用接口，返回状态3，表示药品有变化，需要特殊处理
                            if (scansType.equals(ScansConstant.BottleLabelAction.IN))
                            {
                                if (pqyd.getScrqDelToday() >= 0)
                                {//当天审核的医嘱，如果药品数据有变化，提示重新审核医嘱 排批次
                                    Map<String, Object> updateMap = new HashMap<String, Object>();
                                    //updateMap.put("yzlx", pqyd.getYd_yzlx());
                                    updateMap.put("parentNo", pqyd.getYd_parentNo());
                                    yzMainService.resetYZCheckStatus(updateMap);
                                    //清空 医嘱 审核状态
                                    errMess = checkOrderStatusRespon.getResultDesc() + "，需重新审核医嘱";
                                    recordErr = true;
                                }
                                else
                                {
                                    //errMess = checkOrderStatusRespon.getResultDesc() + "，非当日药单，无法入仓扫描";
                                    recordErr = true;
                                }
                            }
                            else if (scansType.equals(ScansConstant.BottleLabelAction.OUT))
                            {
                                errMess = checkOrderStatusRespon.getResultDesc() + "，无法出仓";
                                recordErr = true;
                            }
                            else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
                            {
                                errMess = checkOrderStatusRespon.getResultDesc() + "，无法仓内扫描";
                                recordErr = true;
                            }
                        }
                        break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    errMess = "扫描错误，请联系管理员";
                    recordErr = true;
                    
                    log.error("调用检查医嘱状态接口：" + String.valueOf(new Date().getTime() - excute.getTime()) + "\n");
                    
                }
            }
            pqyd = this.queryPQYDListByBottNum(barcode);
            if (errMess == null)
            {
                if (!pqyd.getPq_ydpq().equals(barcode))
                {
                    if (pqyd.getPq_zxbc() != null && pqyd.getPq_zxbcLast() != null
                        && pqyd.getPq_zxbc().intValue() != pqyd.getPq_zxbcLast().intValue())
                    {
                        errMess = "批次已变更，新批次为" + pqyd.getPb_name();
                    }
                    else
                    {
                        errMess = "瓶签已变更，新瓶签为" + pqyd.getPq_ydpq();
                    }
                }
            }
            if (errMess == null)
            {
                if (pqyd.getYd_ydzxzt() != null && pqyd.getYd_ydzxzt().intValue() == 0)
                {//执行中
                    if (!DateUtil.getDay8StrByDay(pqyd.getYd_yyrq()).equals(qryRQ))
                    {
                        //errMess = "非当日药单";
                    }
                    //                    else if (!batchName.equals(pqyd.getPbname2()))
                    //                    {
                    //                        errMess = "当前药品批次是" + pqyd.getPb_name();
                    //                    }
                    else if (pqyd.getYyrqDelToday().intValue() >= 0)
                    {
                        log.info("当前瓶签:[" + barcode + "]执行状态:" + scansType);
                        if (scansType.equals(ScansConstant.BottleLabelAction.IN))
                        {
                            if (pqyd.getPq_ydzt().intValue() > 4)
                            {
                                errMess = "重复扫描，瓶签号[" + barcode + "]";
                            }
                        }
                        else if (scansType.equals(ScansConstant.BottleLabelAction.INSIDE))
                        {
                            if (pqyd.getPq_ydzt().intValue() > 5)
                            {
                                errMess = "重复扫描，瓶签号[" + barcode + "]";
                            }
                            else if (pqyd.getPq_ydzt().intValue() < 5)
                            {
                                errMess = "瓶签号[" + barcode + "]还未入仓";
                            }
                        }
                        else if (scansType.equals(ScansConstant.BottleLabelAction.OUT))
                        {
                            if (pqyd.getPq_ydzt().intValue() > 6)
                            {
                                errMess = "重复扫描，瓶签号[" + barcode + "]";
                            }
                            else if (pqyd.getPq_ydzt().intValue() < 5)
                            {
                                errMess = "瓶签号[" + barcode + "]还未入仓";
                            }
                            
                            if (midFlag && pqyd.getPq_ydzt().intValue() < 6)
                            {
                                
                                errMess = "瓶签号[" + barcode + "]还未仓内扫描";
                            }
                        }
                        else
                        {
                            errMess = "扫描类型有误";
                        }
                        if (errMess == null)
                        {
                            if (pqyd.getYd_dybz() != null && pqyd.getYd_dybz().intValue() == 0)
                            {
                            }
                            else
                            {
                                errMess = "瓶签未打印";
                            }
                        }
                        if (errMess == null)
                        {
                            /*
                            if (!pqyd.getPq_bedno().equals(pqyd.getYd_bedno()))
                            {
                                errMess = "床号已变更为" + pqyd.getYd_bedno() + "，请重新打印";
                                recordErr = true;
                            }*/
                        }
                    }
                    else
                    {
                        //errMess = "非当日或明日药单";
                    }
                }
                else
                {
                    errMess = "医嘱状态已停止";
                    recordErr = true;
                }
            }
            
        }
        else
        {
            errMess = "扫描信息有误，请检查";
        }
        if (errMess == null)
        {
            List<MedicineBean> medicineBeanList = scansDao.queryMedicineList2(pqyd.getPq_pidsj());
            errMess = this.in_out_pack(scansType, medicineBeanList, pqyd, username);
            if (errMess == null)
            {
                succMess = "";//
            }
        }
        if (pqyd != null && pqyd.getPq_ydpq().equals(barcode))
        {
            BottleLabelResult bottleLabelResult = this.queryBottleLabelResult2(pqyd.getPq_pidsj());
            boolean fExists = true;
            if (bottleLabelResult == null)
            {
                fExists = false;
                bottleLabelResult = new BottleLabelResult();
            }
            bottleLabelResult.setSmLX(scansType);
            bottleLabelResult.setSmJG("".equals(succMess) ? 0 : 1);
            bottleLabelResult.setSmRQ(DateUtil.getCurrentDay8Str());
            bottleLabelResult.setSmSBYY(recordErr ? errMess : "");
            bottleLabelResult.setZxbc(pqyd.getPq_zxbc());
            bottleLabelResult.setYdID(pqyd.getPq_pidsj());
            if (!fExists)
            {
                scansDao.insertBottleLabelRst(bottleLabelResult);
            }
            else
            {
                scansDao.updateBottleLabelRst(bottleLabelResult);
            }
        }
        if ("".equals(succMess))
        {
            //将rucangNeedCheck 重置成0 【 医嘱变化更新成1后 入仓成功 要重置成0】
            Map<String, Object> updateMap = new HashMap<String, Object>();
            updateMap.put("rucangNeedCheck", 0);
            updateMap.put("yzlx", pqyd.getYd_yzlx());
            updateMap.put("parentNo", pqyd.getYd_parentNo());
            yzMainService.updateCheckYZMain(updateMap);
            
            log.info("调用检查医嘱状态接口2：" + String.valueOf(new Date().getTime() - excute.getTime()) + "\n");
            
            appScanRet.ret = 0;
            appScanRet.msg = succMess;
            return appScanRet;
        }
        else
        {
            if (errMess == null)
            {
                errMess = "扫描失败，请联系管理员";
            }
            log.info("调用检查医嘱状态接口3：" + String.valueOf(new Date().getTime() - excute.getTime()) + "\n");
            
            appScanRet.ret = -1;
            appScanRet.msg = errMess;
            return appScanRet;
        }
        
    }
    
    /**
     * 
     * 保存扫描结果
     * @param scansType
     * @param errMess
     * @param pqyd
     * @param isSucc
     */
    public void saveSmResult(String scansType, String errMess, BLabelWithPrescription pqyd, boolean isSucc)
    {
        BottleLabelResult bottleLabelExtis = this.queryBottleLabelResult2(pqyd.getPq_pidsj());
        boolean fExists = true;
        if (bottleLabelExtis == null)
        {
            fExists = false;
        }
        
        BottleLabelResult bottleLabelResult = new BottleLabelResult();
        bottleLabelResult.setSmLX(scansType);
        bottleLabelResult.setSmJG(isSucc ? 0 : 1);
        bottleLabelResult.setSmRQ(DateUtil.getCurrentDay8Str());
        bottleLabelResult.setSmSBYY(isSucc ? "" : errMess);
        bottleLabelResult.setZxbc(pqyd.getPq_zxbc());
        bottleLabelResult.setYdID(pqyd.getPq_pidsj());
        bottleLabelResult.setYdpq(pqyd.getPq_ydpq());
        if (fExists)
        {
            scansDao.updateBottleLabelRst(bottleLabelResult);
        }
        else
        {
            scansDao.insertBottleLabelRst(bottleLabelResult);
        }
        
    }
    
    @Override
    public List<BottleLabelBean> getPQList(Map<String, Object> param)
    {
        return scansDao.getPQList(param);
    }
    
    @Override
    public List<ScanResult> getSMResultList(String account, String yyrqStart, String yyrqEnd)
    {
        return scansDao.getSMResultList(account, yyrqStart, yyrqEnd);
    }
    
    @Override
    public List<BLabelWithPrescription> getYdDetailList(String pqStr)
    {
        return scansDao.getYdDetailList(pqStr);
    }
    
    @Override
    public List<BottleLabelBean> getStopList(Map<String, Object> param)
    {
        return scansDao.getStopList(param);
    }
    
    @Override
    public List<ScanPcNum> getBatchList(Map<String, Object> param)
    {
        return scansDao.getBatchList(param);
    }
    
    @Override
    public List<Map<String, Object>> getDeptList(Map<String, Object> param)
    {
        return scansDao.getDeptList(param);
    }
    
    @Override
    public List<String> getPCNumber(String deptcode, String pccode, Map<String, Object> param)
    {
        return scansDao.getPCNumber(deptcode, pccode, param);
    }
    
    @Override
    public void updateCheckType(String pq_pidsj, String pq_ydpq, String scansType)
    {
        scansDao.updateCheckType(pq_pidsj, pq_ydpq, scansType);
    }
    
    @Override
    public List<BottleLabelBean> getScanPQList(Map<String, Object> param,String[] lastNo)
    {
        return scansDao.getScanPQList(param,lastNo);
    }

    @Override
    public List<Batch> getScansBatchList()
    {
        return scansDao.getScansBatchList();
    }

    @Override
    public List<Map<String, Object>> getScanRestult(Map<String, Object> param)
    {
        return scansDao.getScanRestult(param);
    }
    
}
