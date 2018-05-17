package com.zc.pivas.configfee.service.impl;

import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.entity.SynDoctorAdviceBean;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import com.zc.pivas.configfee.service.ChargeDetailsService;
import com.zc.pivas.configfee.bean.ConfigFeeBean;
import com.zc.pivas.configfee.bean.ConfigFeeChargeBean;
import com.zc.pivas.configfee.bean.ConfigFeeDetailBean;
import com.zc.pivas.configfee.bean.ConfigFeeRuleBean;
import com.zc.pivas.configfee.service.ConfigFeeChargeService;
import com.zc.pivas.configfee.service.ConfigFeeDetailService;
import com.zc.pivas.configfee.service.ConfigFeeRuleService;
import com.zc.pivas.configfee.service.ConfigFeeService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 配置费收费实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("ConfigFeeChargeService")
public class ConfigFeeChargeServiceImpl implements ConfigFeeChargeService {
    private static final Logger log = LoggerFactory.getLogger(ConfigFeeChargeServiceImpl.class);

    @Resource
    private MedicamentsDao medicamentsdao;

    @Resource
    private MedicCategoryService medicCategoryservice;

    @Resource
    private ConfigFeeRuleService configFeeRuleservice;

    @Resource
    private ConfigFeeDetailService configFeeDetailservice;

    @Resource
    private ChargeDetailsService chargeDetailsservice;

    @Resource
    private ConfigFeeService configFeeservice;

    @Resource
    private DoctorAdviceMainService yzMainService;

    /**
     * 配置费规则计算接口
     *
     * @param yzList 医嘱列表，只针对成组的一包水
     * @return
     */
    @Override
    public List<ConfigFeeChargeBean> findConfigCharge(List<SynDoctorAdviceBean> yzList) {
        if (null == yzList) {
            return null;
        }
        log.info("YZLIST SIZE: " + yzList.size() + " INFO: " + yzList.toString());

        // 输液量
        String transfusion = "";

        // 是否包含营养液
        boolean isNutrientSolution = false;

        // 是否存在营养液分类药品表中的分类编码
        boolean hasNutrient = false;

        // 药单中所有药品编码
        String drugCodes = "";

        // 最高优先级
        Integer priorityNow = null;

        // 营养液优先级
        Integer nutrientPriority = null;

        // 匹配配置费规则
        ConfigFeeRuleBean configRule = null;

        List<ConfigFeeChargeBean> configFeeChargeBeanInstance = new ArrayList<ConfigFeeChargeBean>();

        // 根据主医嘱号查询该药单的输液量
        Map<String, Object> yzConMap = new HashMap<String, Object>();
        yzConMap.put("parentNo", yzList.get(0).getOrderGroupNo());
        DoctorAdviceMain yzMain = yzMainService.getYzByCondition(yzConMap);

        if (null != yzMain) {
            transfusion = yzMain.getTransfusion();
        }

        // 根据输液量查询3L袋的配置费规则
        // 如果输液量>=750，药品中不包含特定药品，则按照3L袋收取配置费

        if (StringUtils.isNotEmpty(transfusion) && Float.valueOf(transfusion) >= 750 && !yzMain.getMedicamentsName().contains("卡文")) {
            configRule = getConfigRule("", "", transfusion);
        } else {
            // 判断医嘱中是否有营养液
            // 先寻找营养液 判断药物名字是否含有特定
            // 药品名称是否同时含有如下字段[默认:"脂肪乳,氨基酸"]
            String medicNameAllCode = Propertiesconfiguration.getStringProperty("yingyangye");

            log.info("YING MEDIALIST:" + medicNameAllCode);

            String[] medicNameList = medicNameAllCode.split(",");

            // 判断该药单中是否有营养液类药品
            for (SynDoctorAdviceBean yzbean : yzList) {
                // 获取药品名称            
                drugCodes = yzbean.getDrugCode() + "," + drugCodes;
                Medicaments medicElement = medicamentsdao.getMediclByCode(yzbean.getDrugCode());
                String medicName = medicElement.getMedicamentsName();

                for (int i = 0; i < medicNameList.length; i++) {
                    if (medicName.contains(medicNameList[i]) && !isNutrientSolution) {
                        isNutrientSolution = true;
                    }

                    if ("营".equals(medicElement.getCategoryCode()) && !hasNutrient) {
                        hasNutrient = true;
                    }
                }
            }

            drugCodes = drugCodes.substring(0, drugCodes.length() - 1);

            // 根据药品分类优先级匹配
            yzConMap.put("drugCodes", drugCodes.split(","));
            List<Map<String, Object>> medicamentList = medicamentsdao.qryMedForConfigFee(yzConMap);

            if (DefineCollectionUtil.isEmpty(medicamentList)) {
                return null;
            }

            // 判断优先级有没有大于营养液的药品分类
            // 查询药品分类表中营养液的药品分类优先级
            MedicCategory category = medicCategoryservice.findCategoryByName("营");

            // 如果没有找到营养液则不考虑
            if (null != category && isNutrientSolution) {
                // 营养液优先级
                nutrientPriority = NumberUtils.toInt(category.getCategoryPriority());
            }

            // 如果含有营养液，或者都不存在，则不需要单独考虑根据药品名匹配的营养液分类
            if (hasNutrient || !isNutrientSolution) {
                for (Map<String, Object> map : medicamentList) {
                    // 先按照输液量匹配
                    configRule =
                            getConfigRule(map.get("CATEGORY_ID").toString(), map.get("MEDICAMENTS_ID").toString(), transfusion);
                    if (null != configRule) {
                        break;
                    }
                }
            }
            // 在原始药品分类中没有根据药品分类能查询到则需要增加营养液的分类匹配
            else if (!hasNutrient && isNutrientSolution && null != nutrientPriority) {
                // 处理查询优先级比营养液高的
                for (Map<String, Object> map : medicamentList) {
                    // 根据药品分类匹配一半规则
                    priorityNow = NumberUtils.toInt(map.get("CATEGORY_PRIORITY").toString());

                    if (priorityNow > nutrientPriority) {
                        configRule =
                                getConfigRule(map.get("CATEGORY_ID").toString(),
                                        map.get("MEDICAMENTS_ID").toString(),
                                        transfusion);
                    }

                    if (null != configRule) {
                        break;
                    }
                }

                if (null == configRule) {
                    for (Map<String, Object> map : medicamentList) {
                        configRule =
                                getConfigRule(category.getCategoryId().toString(),
                                        map.get("MEDICAMENTS_ID").toString(),
                                        transfusion);

                        if (null != configRule) {
                            break;
                        }
                    }
                }

                // 处理查询优先级比营养液高的
                for (Map<String, Object> map : medicamentList) {
                    // 根据药品分类匹配一半规则
                    priorityNow = NumberUtils.toInt(map.get("CATEGORY_PRIORITY").toString());

                    if (priorityNow < nutrientPriority) {
                        configRule =
                                getConfigRule(map.get("CATEGORY_ID").toString(),
                                        map.get("MEDICAMENTS_ID").toString(),
                                        transfusion);
                    }

                    if (null != configRule) {
                        break;
                    }
                }
            }
        }

        if (null == configRule) {
            return null;
        }

        // 获取收费详情
        List<ConfigFeeDetailBean> beanlist =
                configFeeDetailservice.getConfigFeeDetailListByDetailCode(configRule.getGid());

        InsertByRate(beanlist, configFeeChargeBeanInstance, yzMain.getInpatientNo());
        return configFeeChargeBeanInstance;
    }

    /**
     * 根据收费频率收费
     *
     * @param beanlist
     * @param ConfigFeeChargeBeanInstance
     * @param hosNO
     */
    private void InsertByRate(List<ConfigFeeDetailBean> beanlist,
                              List<ConfigFeeChargeBean> ConfigFeeChargeBeanInstance, String hosNO) {
        if ((beanlist != null) && (!beanlist.isEmpty())) {
            Iterator<ConfigFeeDetailBean> iter = beanlist.iterator();
            while (iter.hasNext()) {
                ConfigFeeDetailBean ConfigFeeDetailbean = iter.next();
                int rate = ConfigFeeDetailbean.getRate();
                Boolean isAdd = false;
                if (1 == rate) {
                    isAdd = true;
                } else if (0 == rate) {
                    // 化4.RATE=0情况下
                    // 根据CASE_ID[住院号],PZFSQRQ[今天],CONFIGFEERULEID[GID[DETAILCODE]]查找是否有PZFZT[药单配置费收取情况]
                    // 有的话不返回
                    ChargeDetailsBean chargedetailsbean = new ChargeDetailsBean();
                    chargedetailsbean.setcASE_ID(hosNO);
                    String costCode = ConfigFeeDetailbean.getCostCode();
                    chargedetailsbean.setCostcode(costCode);

                    Date date = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatdate = df.format(date);

                    chargedetailsbean.setpZFSQRQ(formatdate);

                    int count = chargeDetailsservice.getChargeCount(chargedetailsbean);
                    if (count == 0) {
                        isAdd = true;
                    }
                    log.info("CHECK " + ConfigFeeDetailbean.getCostCode() + "RATE1 COUNT:" + count);
                }

                if (isAdd) {
                    // 根据COSTCODE查找PIVAS_CONFIG_FEE-PRICE
                    ConfigFeeBean feebean = new ConfigFeeBean();
                    feebean.setCostCode(ConfigFeeDetailbean.getCostCode());
                    feebean = configFeeservice.getConfigFee(feebean);
                    if (feebean.getPrice().isEmpty()) {
                        continue;
                    }
                    ConfigFeeChargeBean chargebean = new ConfigFeeChargeBean();
                    chargebean.setInpatientNo(hosNO);
                    chargebean.setAmount(ConfigFeeDetailbean.getAmount());
                    chargebean.setCostCode(ConfigFeeDetailbean.getCostCode());
                    chargebean.setconfigfeeruleid(ConfigFeeDetailbean.getDetailCode());
                    chargebean.setprice(feebean.getPrice());
                    ConfigFeeChargeBeanInstance.add(chargebean);
                }
            }

        }
    }

    /**
     *
     * @param categoryId
     * @param drugCode
     * @param volume
     * @return
     */
    private ConfigFeeRuleBean getConfigRule(String categoryId, String drugCode, String volume) {
        ConfigFeeRuleBean bean = new ConfigFeeRuleBean();
        bean.setDrugTypeCode(categoryId);
        bean.setVolume(volume);
        bean.setDrugCode(drugCode);
        List<ConfigFeeRuleBean> retbean = configFeeRuleservice.getConfigFeeRuleByInfo(bean);

        if (DefineCollectionUtil.isEmpty(retbean)) {
            bean.setVolume(volume);
            bean.setDrugCode("");
            retbean = configFeeRuleservice.getConfigFeeRuleByInfo(bean);

            if (DefineCollectionUtil.isEmpty(retbean)) {
                bean.setVolume("");
                bean.setDrugCode(drugCode);
                retbean = configFeeRuleservice.getConfigFeeRuleByInfo(bean);

                if (DefineCollectionUtil.isEmpty(retbean)) {
                    bean.setVolume("");
                    bean.setDrugCode("");
                    retbean = configFeeRuleservice.getConfigFeeRuleByInfo(bean);
                }
            }
        }

        if (DefineCollectionUtil.isEmpty(retbean)) {
            return null;
        }

        return retbean.get(0);
    }
}
