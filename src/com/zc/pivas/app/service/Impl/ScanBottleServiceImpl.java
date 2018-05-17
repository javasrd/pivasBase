package com.zc.pivas.app.service.Impl;

import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.base.web.Servlets;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.app.bean.BottleInfo;
import com.zc.pivas.app.bean.DrugListManagerBean;
import com.zc.pivas.app.bean.ScanOutputParamBean;
import com.zc.pivas.app.common.ScanServiceConstant;
import com.zc.pivas.app.dao.BottleInfoDao;
import com.zc.pivas.app.dao.DrugListManagerDao;
import com.zc.pivas.app.service.ScanBottleService;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import com.zc.pivas.scans.constant.ScansConstant;
import com.zc.pivas.scans.service.ScansService;
import com.zc.pivas.synresult.service.SendToRestful;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 瓶签扫描实现类
 * <功能详细描述>
 *
 * @author cacabin
 * @version 1.0
 */
@Service("ScanBottleService")
class ScanBottleServiceImpl implements ScanBottleService {

    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(ScanBottleServiceImpl.class);

    /**
     * 12点前允许扫描的批次名称
     */
    private static final String ALLOWBATCH_MORN = Propertiesconfiguration.getStringProperty("pivas.allowbatch.mon");

    /**
     * 12点后允许扫描的批次名称
     */
    private static final String ALLOWBATCH_AFTER = Propertiesconfiguration.getStringProperty("pivas.allowbatch.after");

    private static final String ALLOWBATCH_WARD_AFTER = Propertiesconfiguration.getStringProperty("pivas.allowbatch.ward.after");

    @Resource
    private BottleInfoDao bottleInfoDao;

    @Resource
    private SendToRestful sendRestful;

    @Resource
    private MedicCategoryService mcService;

    @Resource
    private DoctorAdviceMainService yzMainService;

    @Resource
    private ScansService scansService;

    @Resource
    private DrugListManagerDao drugListManagerdao;

    @Resource
    private UserService userService;

    @Resource
    private MedicamentsDao medicamentsDao;

    @Resource
    private BatchService batchService;

    /**
     * 处理扫描
     *
     * @param bottleNum 瓶签号码
     * @return 收费是否成功
     * @throws JSONException
     */
    @Override
    public ScanOutputParamBean scanBottleAction(String bottleNum, String strUser)
            throws Exception {
        ScanOutputParamBean retBean = new ScanOutputParamBean();

        if (StringUtils.isEmpty(bottleNum)) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanGetMsgError;
            retBean.msg = "无效瓶签号";
            return retBean;
        }

        //查找瓶签表
        BottleInfo bottleInfo = new BottleInfo();
        bottleInfo.bottleNum = bottleNum;
        List<BottleInfo> bottleLabelList = bottleInfoDao.queryBottleInfo(bottleInfo);

        if (bottleLabelList.size() < 1) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanGetMsgError;
            retBean.msg = "没有找到符合条件的瓶签号";
            return retBean;
        }

        BottleInfo bottleLabelInfo = (BottleInfo) bottleLabelList.get(0);
        if (null == bottleLabelInfo) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanGetMsgError;
            retBean.msg = "没有找到符合条件的瓶签号";
            return retBean;
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        DateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        // 只能扫描当日药单
        if (!sdf.format(new Date()).equals(sdf.format(bottleLabelInfo.medicationTime))) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanDoctorAdviceError;
            retBean.msg = "只能扫描当日药单";
            log.error(retBean.msg);
            return retBean;
        }

        //更新药单的配置人员账号
        User user = userService.getUserByAccount(strUser);
        if (null == user) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanUpdateManError;
            retBean.msg = "无法查找到用户:" + strUser;
            log.error(retBean.msg);
            return retBean;
        }

        //获取病区
        retBean.wardName = bottleLabelInfo.wardName;

        //用药时间
        Date date = bottleLabelInfo.medicationTime;

        String tim = sdf.format(date);
        retBean.medicationTime = tim;

        //病人
        retBean.patName = bottleLabelInfo.patName;

        //性别
        retBean.sex = bottleLabelInfo.sex;

        //床号
        retBean.bedNo = bottleLabelInfo.bedNo;

        //年龄
        if (-1 == bottleLabelInfo.ageUnit) {
            retBean.age = bottleLabelInfo.age;
        } else {
            retBean.age =
                    bottleLabelInfo.age
                            + (DictFacade.getName("pivas.patient.ageunit",
                            DefineStringUtil.parseNull(bottleLabelInfo.ageUnit),
                            Servlets.getCurrentRequestLocal().getLanguage()));
        }

        //医嘱类型
        retBean.doctorAdviceType = bottleLabelInfo.doctorAdviceType;

        //是否难配
        retBean.isHard = bottleLabelInfo.IsHard;

        //供应方式
        retBean.supplyCode = bottleLabelInfo.supplyCode;

        //批次
        retBean.batchName = bottleLabelInfo.batchName;

        //药品列表
        for (BottleInfo Singlebean : bottleLabelList) {
            ScanOutputParamBean.medicParam param = retBean.new medicParam();
            param.medicName = Singlebean.medicName;
            param.medicDose = Singlebean.medicDose;
            param.medicNum = Singlebean.medicNum;
            param.medicDoseUnit = Singlebean.medicDoseUnit;
            param.medicSpec = Singlebean.medicSpec;
            param.medicNumUnit = Singlebean.medicNumUnit;
            retBean.medicList.add(param);
        }

        retBean.ret = 0;

        //用药时间
        tim = sdf.format(date);

        // 只允许扫描配置类批次，校验对应瓶签数据
        Batch batch = batchService.selectByPrimaryKey(Long.valueOf(bottleLabelInfo.batchID));

        if (batch.getIsEmptyBatch() != 0) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanGetMsgError;
            retBean.msg = "非配置类批次";
            return retBean;
        }

        // 1批和2批的瓶签，只能在上午（12点前）扫描通过。3批4批只能在当日12点后才可以扫描
        // 获取当前时间段允许的批次ID
        Date checkTime = sdf2.parse("12:00:00");
        Date now = sdf2.parse(sdf2.format(new Date()));

        // 大于0 ，表示当前时间大于12点，只允许3#，4#
        if (now.compareTo(checkTime) > 0) {
            if (!ALLOWBATCH_AFTER.contains(batch.getName()) && !ALLOWBATCH_WARD_AFTER.contains(bottleLabelInfo.wardCode)) {
                retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanGetMsgError;
                retBean.msg = "该批次不在扫描时间范围内";
                return retBean;
            }
        } else {
            if (!ALLOWBATCH_MORN.contains(batch.getName())) {
                retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanGetMsgError;
                retBean.msg = "该批次不在扫描时间范围内";
                return retBean;
            }
        }

        //收取配置费
        //AppScanRet scanRet = scansService.appScan( bottleNum, ScansConstant.BottleLabelAction.INSIDE,bottleLabelInfo.batchID,tim,bottleLabelInfo.batchName,strUser);

        ScansService.AppScanRet scanRet = scansService.appScanNew(bottleNum, ScansConstant.BottleLabelAction.INSIDE, tim, strUser);
        if (scanRet.ret == -1) {
            retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanConfigFeeError;
            retBean.msg = scanRet.msg;
            log.error("瓶签[" + bottleNum + "]扫描失败msg:" + scanRet.msg);
            return retBean;
        } else {
            retBean.doctorAdviceStatus = 0;
            DrugListManagerBean drugListBean = new DrugListManagerBean();
            drugListBean.bottleNum = bottleNum;
            drugListBean.pzCode = strUser;
            drugListBean.pzmc = user.getName();
            drugListBean.pzrq = new Date();

            try {
                drugListManagerdao.updateDrugListMainManager(drugListBean);
                drugListManagerdao.updateDrugListManager(drugListBean);
            } catch (Exception e) {
                log.error("Update YD failed", e);
                retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanUpdateManError;
                retBean.msg = "更新药单配置人员状态失败";
                return retBean;
            }
        }

        retBean.ret = ScanServiceConstant.ScanBottleRetConstant.ScanOK;

        return retBean;
    }

}