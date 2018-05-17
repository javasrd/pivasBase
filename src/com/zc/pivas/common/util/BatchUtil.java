package com.zc.pivas.common.util;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.util.NumberUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.bean.*;
import com.zc.pivas.docteradvice.entity.OtherRule;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.PrescriptionRuleService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.printlabel.service.PrintLabelService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 批次重排工具类
 *
 * @author  cacabin
 * @version  1.0
 */
public class BatchUtil
{
    
    /*
    1 药师审方，生成药单初始化数据
    2 审方确认OK 将审方通过的医嘱，药单 确认状态置为 YES，
    3 3.1 查询确认状态的药单数据，按照 病区，病人，上下午，是否空批进行分组【分页，防止数据量过大】，
      3.2 初始化 批次信息，一般规则信息，四种规则信息
      3.3 
    */
    private static final String EMPTY_BATCH_NAME = Propertiesconfiguration.getStringProperty("sysconfig.kongEndStr");
    
    public static DoctorAdviceMainService yzMainService;
    
    public static PrescriptionMainService ydMainService;
    
    public static PrescriptionRuleService ydRuleService;
    
    public static BatchService batchService;

    public static PrintLabelService printLabelService;
    
    /**
     *
     * 批次优化调用接口
     * @param checkOnly
     * @return
     */
    public boolean reorder(boolean checkOnly, Map<String, Object> param) {
        if (yzMainService == null) {
            yzMainService = ServletContextUtil.yzMainService;
            ydMainService = ServletContextUtil.ydMainService;
            ydRuleService = ServletContextUtil.ydRuleService;
            batchService = ServletContextUtil.batchService;
            printLabelService = ServletContextUtil.printLabelService;
        }
        String yyrq = param.get("yyrq").toString();
        //病区
        String inpatientString = (String) param.get("inpatientString");
        if (inpatientString != null && inpatientString.length() != 0) {
            String[] wardCodeArray = inpatientString.split(",");
            param.replace("inpatientString", wardCodeArray);
        }

        //住院号
        String inpatientNo = (String) param.get("inpatientNo");
        if (inpatientNo != null && inpatientNo.length() != 0) {
            String[] inpatientNoArray = inpatientNo.split(",");
            param.replace("inpatientNo", inpatientNoArray);
        }

        Map<String, Object> groupByAreaPatMap = new HashMap<String, Object>();
        groupByAreaPatMap.put("containUnConfig", yyrq);//调整优化批次时，根据未确认的药单找到相关联的药单一起查询出来 
        //groupByAreaPatMap.put("running", 1);
        groupByAreaPatMap.put("yzshzt_1", 1);
        groupByAreaPatMap.put("inpatientString", inpatientString);
        groupByAreaPatMap.put("inpatientNo", inpatientNo);

        List<DoctorAdviceAreaPat> YZAreaPatList = yzMainService.groupByAreaPat(groupByAreaPatMap);
        //如果没有满足条件的数据 不执行
        if (YZAreaPatList == null || YZAreaPatList.size() < 1) {
            return false;
        }

        List<Batch> batchList = batchService.queryBatchAllList();

        Map<String, Object> prioRulesMap = new HashMap<String, Object>();
        prioRulesMap.put("prType", SysConstant.prType.medicSingRule);
        List<PriorityRulesWithArea> prioRulesList = ydRuleService.qryPrioRulesList(prioRulesMap);

        Map<String, Object> forceRulesMap = new HashMap<String, Object>();
        forceRulesMap.put("prType", SysConstant.prType.forceSingRule);
        forceRulesMap.put("enabled", SysConstant.prEnabled.abled);
        List<ForceRulesWithArea> forceRulesList =
                ydRuleService.qryForceRulesList(forceRulesMap, new JqueryStylePaging());

        List<VolumeRulesWithBatch> volumeRulesList =
                ydRuleService.qryVolumeRulesList(new HashMap<String, Object>(), new JqueryStylePaging());

        List<OtherRule> otherRuleList = ydRuleService.qryOtherRulesList(new HashMap<String, Object>());

        BatchRuleTool batchRuleTool =
                new BatchRuleTool(batchList, prioRulesList, forceRulesList, volumeRulesList, otherRuleList);

        Map<String, Object> ydMainQry = null;
        List<PrescriptionMain> ydMainList = null;
        Map<String, Object> map = null;
        for (DoctorAdviceAreaPat yzAreaPat : YZAreaPatList) {
            map = new HashMap<String, Object>();
            map.put("inpatientSingle", yzAreaPat.getInpatientNo());
            map.put("yyrq", yyrq);
            map.put("wardCode", yzAreaPat.getWardCode());

            batchRuleTool.initTreeRuleByDept(yzAreaPat.getWardCode(), yzAreaPat.getWardName());
            ydMainQry = new HashMap<String, Object>();
            ydMainQry.put("yzlx", yzAreaPat.getYzlx());
            ydMainQry.put("wardCode", yzAreaPat.getWardCode());
            ydMainQry.put("inpatientNo", yzAreaPat.getInpatientNo());
            ydMainQry.put("ydzxzt", 0);
            ydMainQry.put("ydreorderStatus", "0");
            ydMainQry.put("leftWithPB", 1);
            ydMainQry.put("orderByPB", 1);
            ydMainQry.put("yyrq", yyrq);
            ydMainList = ydMainService.qryListBeanByMap(ydMainQry, new JqueryStylePaging());

            //判断该床号的病人的医嘱是否重审
            boolean isYouHua = false;

            Map<String, String> categoryCodeMap = new HashMap<String, String>();

            for (PrescriptionMain ydMain : ydMainList) {
                ydMain.setYdreorderMess("");
                ydMain.setYdreorderCode(SysConstant.ydreorderCode.i0YDInit);

                String categoryCodes = "";
                String categoryCodeByMap = categoryCodeMap.get(ydMain.getParentNo());
                if (categoryCodeByMap == null) {
                    String[] chargeCodes = ydMain.getChargeCode().split("@@");
                    for (String chargeCode : chargeCodes) {
                        String categoryCode = ydMainService.getCategoryCodeByMedicId(chargeCode);
                        categoryCodes += categoryCode + "@@";
                    }
                    categoryCodes = categoryCodes.substring(0, categoryCodes.length() - 2);
                    categoryCodeMap.put(ydMain.getParentNo(), categoryCodes);
                } else {
                    categoryCodes = categoryCodeByMap;
                }
                ydMain.setCategoryCode(categoryCodes);
            }

            //获取化疗类药物的CategoryId
            String huaCategoryId = ydMainService.getCategoryNameByHUA();

            for (PrescriptionMain ydMain : ydMainList) {
                if (batchRuleTool.otherRuleConfig.isOneMedToKong() && !ydMain.getChargeCode().contains("@@")) {
                    changeBatchToKong(batchRuleTool, ydMain);
                } else {
                    List<Integer> labelCode = ydMainService.getLabelCode(ydMain.getPidsj());
                    List<Integer> ydMenstruum = ydMainService.checkYDMenstruum(ydMain.getPidsj());
                    if ((labelCode != null && labelCode.size() > 0) ||
                            (ydMenstruum == null || ydMenstruum.size() == 0)) {
                        changeBatchToKong(batchRuleTool, ydMain);
                    }

                    if (StringUtils.isNotBlank(ydMain.getQuantity()) && (ydMenstruum != null && ydMenstruum.size() > 0)
                            && NumberUtils.toInt(ydMain.getTransfusion()) < 850) {
                        if (ydMain.getQuantity().indexOf(".") > -1) {
                            List<String> menstCodeList = ydMainService.getMenstruumCodeByQuantity(ydMain.getPidsj());
                            if (menstCodeList != null && menstCodeList.size() > 0 && ydMain.getCategoryCode().indexOf(huaCategoryId) == -1) {
                                changeBatchToKong(batchRuleTool, ydMain);
                            }
                        }
                    }
                }

                if (isYouHua) {
                    if (ydMain.getYdreorderCode() != SysConstant.ydreorderCode.i22HasChangeSucc) {
                        ydMain.setYdreorderCode(SysConstant.ydreorderCode.i21NoChangeSucc);
                    }
                    ydMain.setYdreorderMess(ydMain.getYdreorderMess() + "[药单重审,无法再次优化]");
                    ydMainService.updateYDReOrder(ydMain);
                }
            }

            if (!isYouHua) {
                List<List<PrescriptionMain>> list = getList(ydMainList, yyrq);//****按照 日期，上下午，是否空批 拆分成多组数据
                if (list.size() > 0) {
                    for (List<PrescriptionMain> list2 : list) {
                        run(list2, batchRuleTool, checkOnly, list2.get(0).getPb_timetype());
                    }
                }
            }

            //是否有其他处理业务？？
            ydMainService.updateYDReOrderStatus(map);
            if (!isYouHua) {
                for (PrescriptionMain ydMain : ydMainList) {
                    Integer ydreorderCode = ydMain.getYdreorderCode();
                    if (ydreorderCode == SysConstant.ydreorderCode.i22HasChangeSucc
                            || ydreorderCode == SysConstant.ydreorderCode.i12HasChangeErr) {
                        try {
                            printLabelService.createBottleLabel(ydMain, ydMain.getSfyscode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 
     * 改为空批
     * 
     * @param batchRuleTool
     * @param ydMain
     */
    private void changeBatchToKong(BatchRuleTool batchRuleTool, PrescriptionMain ydMain)
    {
        Batch batch = batchRuleTool.emptyBatch.get(ydMain.getPb_num() + EMPTY_BATCH_NAME);
        if (batch != null)
        {
            ydMain.setZxbcChangeBefore(ydMain.getZxbc());
            ydMain.setZxbcChangeBeforeS(ydMain.getPb_name());
            ydMain.setZxbc(batch.getId().intValue());
            ydMain.setPb_id(batch.getId());
            ydMain.setPb_num(batch.getNum());
            ydMain.setPb_name(batch.getName());
            ydMain.setPb_isEmptyBatch(batch.getIsEmptyBatch());
            ydMain.setPb_startTime(batch.getStartTime());
            ydMain.setPb_endTime(batch.getEndTime());
            ydMain.setPb_isSecendAdvice(batch.getIsSecendAdvice());
            ydMain.setPb_isSecendAdvice(batch.getIsSecendAdvice());
            ydMain.setPb_color(batch.getColor());
            ydMain.setPb_enabled(batch.getEnabled());
            ydMain.setPb_is0p(batch.getIs0P());
            ydMain.setPb_timetype(batch.getTimeType());
            ydMain.setPb_orderNum(batch.getOrderNum());
            ydMain.setPidrqzxbc(ydMain.getPidrqzxbc().substring(0, ydMain.getPidrqzxbc().lastIndexOf("_") + 1) + ydMain.getZxbc());
            ydMain.setYdreorderCode(SysConstant.ydreorderCode.i22HasChangeSucc);
            ydMain.setYdreorderMess(ydMain.getYdreorderMess() + "单个药品落空批[批次改为" + ydMain.getPb_name() + "空批]");
        }
    }
    
    /**
     *
     * 批次优化执行方法,调整批次核心方法
     * @param list
     * @param batchRuleTool
     * @param checkOnly
     */
    public static void run(List<PrescriptionMain> list, BatchRuleTool batchRuleTool, boolean checkOnly, String timeType) {
        PrescriptionMain ydFirst = list.get(0);
        if (batchRuleTool.otherRuleConfig.isTreeRuleRun()
                && !(!batchRuleTool.otherRuleConfig.isKongPCRun() && ydFirst.getPb_isEmptyBatch().intValue() == 1)) {
            if (ydFirst.getYdreorderCode().intValue() == SysConstant.ydreorderCode.i24NotReorder) {
                for (PrescriptionMain ydMain : list) {
                    ydMain.setYdreorderMess("当前批次不参与调整");
                    ydMain.setYdreorderCode(SysConstant.ydreorderCode.i21NoChangeSucc);
                    ydMainService.updateYDReOrder(ydMain);
                }
            } else {
                //如启用三大规则
                List<Batch> batchList = batchRuleTool.batchMapByEmptyTimeType.get(ydFirst.getEmptyBatchTimeType());
                Batch batchFirst = batchList.get(0);
                if (!checkOnly) {
                    PrescriptionMain ydTemp = null;
                    //--------------------------强制批次        开始---------------------------------------
                    Map<String, Boolean> hasBeenForced = new HashMap<String, Boolean>();
                    //判断强制规则中是否有强制的药物并且批次一样
                    for (int i = 0; i < list.size(); i++) {
                        ydTemp = list.get(i);
                        boolean forceOK = false;
                        String forceCode = "";
                        if (ydTemp.getChargeCode() != null) {
                            String chargeCodeS = ydTemp.getChargeCode();
                            String[] chargeCodeN = chargeCodeS.split("@@");
                            for (ForceRulesWithArea forceRulesWithArea : batchRuleTool.forceRulesListByDept) {
                                for (String chargeCode : chargeCodeN) {
                                    if (chargeCode != null &&
                                            chargeCode.equals(forceRulesWithArea.getMedicCode()) &&
                                            ydTemp.getZxbc().intValue() == forceRulesWithArea.getBatchId().intValue()) {
                                        forceOK = true;
                                        forceCode = chargeCode;
                                    }
                                }
                            }
                            if (forceOK) {
                                ydTemp.setYdreorderMess(ydTemp.getYdreorderMess() + "[强制规则：将批次" + ydTemp.getPb_name() + "固定]");
                                ydTemp.setZxbcIsForce(1);
                                ydTemp.setZxbcChangeBefore(ydTemp.getZxbc());
                                ydTemp.setZxbcChangeBeforeS(ydTemp.getPb_name());
                                ydTemp.setZxbc(batchRuleTool.forceRulesMapByDept.get(forceCode).intValue());
                            }

                            if (hasBeenForced.get(ydTemp.getParentNo()) == null) {
                                hasBeenForced.put(ydTemp.getParentNo(), forceOK);
                            } else {
                                if (!hasBeenForced.get(ydTemp.getParentNo())) {
                                    hasBeenForced.replace(ydTemp.getParentNo(), forceOK);
                                }
                            }
                        }
                    }
                    //判断是否有强制的药物  但是批次不对应
                    for (int i = 0; i < list.size(); i++) {
                        ydTemp = list.get(i);
                        boolean forceOK = false;
                        String forceCode = "";
                        if (ydTemp.getChargeCode() != null && !hasBeenForced.get(ydTemp.getParentNo())) {
                            String chargeCodeS = ydTemp.getChargeCode();
                            String[] chargeCodeN = chargeCodeS.split("@@");
                            for (ForceRulesWithArea forceRulesWithArea : batchRuleTool.forceRulesListByDept) {
                                for (String chargeCode : chargeCodeN) {
                                    if (chargeCode != null &&
                                            chargeCode.equals(forceRulesWithArea.getMedicCode())) {
                                        String forceTimeType = batchRuleTool.batchTimeTypeMap.get(forceRulesWithArea.getBatchId());
                                        if (timeType.equals(forceTimeType)) {
                                            forceOK = true;
                                            forceCode = chargeCode;
                                        }
                                    }
                                }
                            }
                            if (forceOK) {
                                ydTemp.setYdreorderMess(ydTemp.getYdreorderMess() + "[强制规则：将批次" + ydTemp.getPb_name() + "固定]");
                                ydTemp.setZxbcIsForce(1);
                                ydTemp.setZxbcChangeBefore(ydTemp.getZxbc());
                                ydTemp.setZxbcChangeBeforeS(ydTemp.getPb_name());
                                ydTemp.setZxbc(batchRuleTool.forceRulesMapByDept.get(forceCode).intValue());
                            }


                            if (!hasBeenForced.get(ydTemp.getParentNo())) {
                                hasBeenForced.replace(ydTemp.getParentNo(), forceOK);
                            }

                        }
                    }

                    //判段药品分类 是否有满足的强制规则  批次相同
                    for (int i = 0; i < list.size(); i++) {
                        ydTemp = list.get(i);
                        boolean forceOK = false;
                        String forceCode = "";
                        if (ydTemp.getChargeCode() != null && !hasBeenForced.get(ydTemp.getParentNo())) {
                            String categoryCodes = ydTemp.getCategoryCode();
                            String[] categoryCodesArray = categoryCodes.split("@@");
                            for (ForceRulesWithArea forceRulesWithArea : batchRuleTool.forceRulesListByDept) {
                                for (String category : categoryCodesArray) {
                                    if (category != null &&
                                            category.equals(forceRulesWithArea.getMedicCode()) &&
                                            ydTemp.getZxbc().intValue() == forceRulesWithArea.getBatchId().intValue()) {
                                        forceOK = true;
                                        forceCode = category;
                                    }
                                }
                            }
                            if (forceOK) {
                                ydTemp.setYdreorderMess(ydTemp.getYdreorderMess() + "[强制规则：将批次" + ydTemp.getPb_name() + "固定]");
                                ydTemp.setZxbcIsForce(1);
                                ydTemp.setZxbcChangeBefore(ydTemp.getZxbc());
                                ydTemp.setZxbcChangeBeforeS(ydTemp.getPb_name());
                                ydTemp.setZxbc(batchRuleTool.forceRulesMapByDept.get(forceCode).intValue());
                            }


                            if (!hasBeenForced.get(ydTemp.getParentNo())) {
                                hasBeenForced.replace(ydTemp.getParentNo(), forceOK);
                            }

                        }
                    }

                    //判段药品分类 是否有满足的强制规则  批次不同
                    for (int i = 0; i < list.size(); i++) {
                        ydTemp = list.get(i);
                        boolean forceOK = false;
                        String forceCode = "";
                        if (ydTemp.getChargeCode() != null && !hasBeenForced.get(ydTemp.getParentNo())) {
                            String categoryCodes = ydTemp.getCategoryCode();
                            String[] categoryCodesArray = categoryCodes.split("@@");
                            for (ForceRulesWithArea forceRulesWithArea : batchRuleTool.forceRulesListByDept) {
                                for (String category : categoryCodesArray) {
                                    if (category != null && category.equals(forceRulesWithArea.getMedicCode())) {
                                        String forceTimeType = batchRuleTool.batchTimeTypeMap.get(forceRulesWithArea.getBatchId());
                                        if (timeType.equals(forceTimeType)) {
                                            forceOK = true;
                                            forceCode = category;
                                        }
                                    }
                                }
                            }
                            if (forceOK) {
                                ydTemp.setYdreorderMess(ydTemp.getYdreorderMess() + "[强制规则：将批次" + ydTemp.getPb_name() + "固定]");
                                ydTemp.setZxbcIsForce(1);
                                ydTemp.setZxbcChangeBefore(ydTemp.getZxbc());
                                ydTemp.setZxbcChangeBeforeS(ydTemp.getPb_name());
                                ydTemp.setZxbc(batchRuleTool.forceRulesMapByDept.get(forceCode).intValue());
                            }

                            if (!hasBeenForced.get(ydTemp.getParentNo())) {
                                hasBeenForced.replace(ydTemp.getParentNo(), forceOK);
                            }

                        }
                    }

                    //-------------------------- 强制批次     结束  ---------------------------------------

                    //-------------------------- 药物优先级  开始 ---------------------------------------
                    if (batchRuleTool.prioRulesListByDept != null && batchRuleTool.prioRulesListByDept.size() > 0) {
                        PriorityRulesWithArea priorityRulesWithArea = null;
                        String[] medCodeN = null;
                        String[] categoryCodeArray = null;
                        Map<String, Boolean> isForcedMap = new HashMap<String, Boolean>();
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).getZxbcIsForce() == 1) {
                                if (isForcedMap.get(list.get(j).getParentNo()) == null) {
                                    isForcedMap.put(list.get(j).getParentNo(), true);
                                }
                            }
                        }
                        for (int i = 0; i < batchRuleTool.prioRulesListByDept.size(); i++) {
                            priorityRulesWithArea = batchRuleTool.prioRulesListByDept.get(i);

                            for (int j = 0; j < list.size(); j++) {
                                ydTemp = list.get(j);
                                if (isForcedMap.get(ydTemp.getParentNo()) == null) {
                                    if (!ydTemp.isPrioCheck() && ydTemp.getMedicamentsCode() != null) {
                                        medCodeN = ydTemp.getMedicamentsCode().split("@@");
                                        for (String medCode : medCodeN) {
                                            if (priorityRulesWithArea.getMedicCode().equals(medCode)) {
                                                ydTemp.setPrioCheck(true);//下面更新批次条件--不在第一批 会调到第一批。这个地方设置优先级 ，容积规则时，可能也会用到优先级
                                                ydTemp.setPrioSeriNum(i);
                                                break;
                                            }
                                        }
                                    }

                                    if (!ydTemp.isPrioCheck() && ydTemp.getCategoryCode() != null) {
                                        categoryCodeArray = ydTemp.getCategoryCode().split("@@");
                                        for (String categoryCode : categoryCodeArray) {
                                            if (priorityRulesWithArea.getMedicCode().equals(categoryCode)) {
                                                ydTemp.setPrioCheck(true);//下面更新批次条件--不在第一批 会调到第一批。这个地方设置优先级 ，容积规则时，可能也会用到优先级
                                                ydTemp.setPrioSeriNum(i);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Map<String, Integer> mapTemp = new HashMap<String, Integer>();
                        for (int i = 0; i < list.size(); i++) {
                            ydTemp = list.get(i);
                            if (isForcedMap.get(ydTemp.getParentNo()) == null) {
                                if (ydTemp.getPrioSeriNum() == null) {
                                    ydTemp.setPrioSeriNum(999999);
                                }
                                if (ydTemp.isPrioCheck() && mapTemp.get(ydTemp.getParentNo()) == null) {
                                    if (ydTemp.getZxbc().longValue() != batchFirst.getId().longValue()) {//如果当前批次就是首个批次，就不需要变更
                                        boolean check = true;
                                        PrescriptionMain ydTemp2 = null;
                                        for (int j = 0; j < list.size(); j++) {
                                            ydTemp2 = list.get(j);
                                            if (i != j && ydTemp.getParentNo().equals(ydTemp2.getParentNo())) {
                                                if (batchFirst.getId().longValue() == ydTemp2.getZxbc().longValue()) {
                                                    check = false;
                                                }
                                            }
                                        }
                                        if (check) {
                                            ydTemp.setYdreorderMess(ydTemp.getYdreorderMess() + "[药物优先级：将批次"
                                                    + ydTemp.getPb_name() + "调整为" + batchFirst.getName() + "]");
                                            if (ydTemp.getZxbcChangeBefore() == null) {
                                                ydTemp.setZxbcChangeBefore(ydTemp.getZxbc());
                                                ydTemp.setPb_name(batchFirst.getName());//记录原批次批次名称，防止修改后，名称不对应
                                                ydTemp.setZxbcChangeBeforeS(ydTemp.getPb_name());
                                            }
                                            ydTemp.setZxbc(batchFirst.getId().intValue());
                                        }
                                    } else {
                                        ydTemp.setYdreorderMess(ydTemp.getYdreorderMess() + "[药物优先级：批次" + ydTemp.getPb_name()
                                                + "已在首个批次]");
                                    }
                                    mapTemp.put(ydTemp.getParentNo(), 0);
                                }
                            }
                        }
                    }
                    //-------------------------- 药物优先级  开始 ---------------------------------------
                }

                //设置容积规则上下线
                List<VolumeBatchBean> VolumeBatchList = new ArrayList<VolumeBatchBean>();
                VolumeBatchBean volumeBatchBean = null;
                List<PrescriptionMain> ydMainListTemp = null;

                //容积规则处理前，先根据 批次将药单数据分组，并统计每组容积的总量
                for (Batch batch : batchList) {
                    volumeBatchBean = new VolumeBatchBean(batch, batchRuleTool.volumeRulesMapByDept.get(batch.getId()));
                    for (PrescriptionMain ydMain : list) {
                        if (ydMain.getZxbc().intValue() == batch.getId().intValue()) {
                            if (volumeBatchBean.getYdMainList() == null) {
                                ydMainListTemp = new ArrayList<PrescriptionMain>();
                                volumeBatchBean.setYdMainList(ydMainListTemp);
                            } else {
                                ydMainListTemp = volumeBatchBean.getYdMainList();
                            }
                            ydMain.setPcnow_maxval(volumeBatchBean.getMaxval());
                            ydMain.setPcnow_minval(volumeBatchBean.getMinval());
                            ydMainListTemp.add(ydMain);
                            volumeBatchBean.setNowval(volumeBatchBean.getNowval()
                                    + NumberUtil.getObjDou(ydMain.getTransfusion(), 0));
                        }
                    }
                    VolumeBatchList.add(volumeBatchBean);
                }

                if (!checkOnly) {
                    //如果是空批 并且空批不启用，则跳过容积规则
                    if (!(ydFirst.getPb_isEmptyBatch().intValue() == 1 && !batchRuleTool.otherRuleConfig.isKongPCVolRun())) {
                        //根据批次分组后，将批次对应的药单进行排序，1.强制规则，2.药物优先级，3.容积
                        for (VolumeBatchBean volumeBatchBeanTemp : VolumeBatchList) {
                            if (volumeBatchBeanTemp.getYdMainList() != null
                                    && volumeBatchBeanTemp.getYdMainList().size() > 0) {
                                Collections.sort(volumeBatchBeanTemp.getYdMainList(), new Comparator<PrescriptionMain>() {
                                    public int compare(PrescriptionMain yd1, PrescriptionMain yd2) {
                                        //如果 两个批次比较，有强制的放在前面，非强制放后面
                                        if (yd1.getZxbcIsForce() > 0 && yd2.getZxbcIsForce() <= 0) {
                                            return -1;
                                        } else if (yd1.getZxbcIsForce() <= 0 && yd2.getZxbcIsForce() > 0) {
                                            return 1;
                                        }

                                        //药物优先,药物优先级高的在前面
                                        if (yd1.isPrioCheck() && !yd2.isPrioCheck()) {
                                            return -1;
                                        } else if (!yd1.isPrioCheck() && yd2.isPrioCheck()) {
                                            return 1;
                                        } else if (yd1.getPrioSeriNum().intValue() != yd2.getPrioSeriNum().intValue()) {
                                            return yd1.getPrioSeriNum() - yd2.getPrioSeriNum() < 0 ? -1 : 1;
                                        } else {
                                            //将容积大的放前面
                                            return (Double.parseDouble(yd1.getTransfusion())
                                                    - Double.parseDouble(yd2.getTransfusion()) > 0) ? -1 : 1;
                                        }
                                    }
                                });
                            }
                        }
                        //循环结束
                        int volumeBatchSize = VolumeBatchList.size();
                        VolumeBatchBean volumeBatchBeanTemp = null;
                        VolumeBatchBean volumeBatchBeanTemp2 = null;

                        boolean jumpUpDown = false;//上移的时候，是否跨批次移动

                        //如果容积不满足上限，将其他批次中 除强制批次 ，移动到该批次,按照药物优先级等排序 优先级低的先移动
                        if (batchRuleTool.otherRuleConfig.isVolAutoUpDown()) {

                            for (int i = 0; i < volumeBatchSize; i++) {
                                volumeBatchBeanTemp = VolumeBatchList.get(i);
                                //如果容积不足上限,查找其他所有批次往上移动
                                if (volumeBatchBeanTemp.getMaxval() > 0 && volumeBatchBeanTemp.getNowval() < volumeBatchBeanTemp.getMaxval()) {
                                    //如果可以跨批次上移，则可以遍历后面所有批次
                                    int volumeBatchSizeTemp = (jumpUpDown ? volumeBatchSize - 1 : (i + 1 < volumeBatchSize ? i + 1 : 0));
                                    for (int k = i + 1; k <= volumeBatchSizeTemp; k++) {
                                        volumeBatchBeanTemp2 = VolumeBatchList.get(k);
                                        if (volumeBatchBeanTemp2.getYdMainList() != null && volumeBatchBeanTemp2.getYdMainList().size() > 0) {
                                            PrescriptionMain ydMainTemp2 = null;
                                            int list2Size = volumeBatchBeanTemp2.getYdMainList().size();

                                            for (int j = 0; j < list2Size; j++) {
                                                ydMainTemp2 = volumeBatchBeanTemp2.getYdMainList().get(j);
                                                if (ydMainTemp2.getZxbcIsForce().intValue() == 0) {
                                                    boolean parentNoExist = false;
                                                    for (PrescriptionMain ydMainTemp1 : volumeBatchBeanTemp.getYdMainList()) {
                                                        if (ydMainTemp2.getParentNo().equals(ydMainTemp1.getParentNo())) {
                                                            parentNoExist = true;
                                                        }
                                                    }
                                                    if (!parentNoExist) {
                                                        if (volumeBatchBeanTemp.getMaxval() > 0
                                                                && volumeBatchBeanTemp.getNowval()
                                                                + NumberUtil.getObjDou(ydMainTemp2.getTransfusion()) <= volumeBatchBeanTemp.getMaxval()) {
                                                            ydMainTemp2.setYdreorderMess(ydMainTemp2.getYdreorderMess()
                                                                    + "[容积规则：" + volumeBatchBeanTemp.getBatchName()
                                                                    + "低于上限，将批次" + ydMainTemp2.getPb_name() + "调整为"
                                                                    + volumeBatchBeanTemp.getBatchName() + "]");
                                                            if (ydMainTemp2.getZxbcChangeBefore() == null) {
                                                                ydMainTemp2.setZxbcChangeBefore(ydMainTemp2.getZxbc());
                                                                ydMainTemp2.setZxbcChangeBeforeS(ydMainTemp2.getPb_name());
                                                            }
                                                            ydMainTemp2.setZxbc(volumeBatchBeanTemp.getBatchId());

                                                            volumeBatchBeanTemp2.setNowval(volumeBatchBeanTemp2.getNowval()
                                                                    - NumberUtil.getObjDou(ydMainTemp2.getTransfusion(), 0));
                                                            volumeBatchBeanTemp.setNowval(volumeBatchBeanTemp.getNowval()
                                                                    + NumberUtil.getObjDou(ydMainTemp2.getTransfusion(), 0));
                                                            volumeBatchBeanTemp.getYdMainList().add(ydMainTemp2);
                                                            volumeBatchBeanTemp2.getYdMainList().remove(j);
                                                            j = j - 1;
                                                            list2Size = list2Size - 1;
                                                            continue;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            double forceDou = 0D;
                            double prioDou = 0D;
                            List<Integer> forceIndex = new ArrayList<Integer>();
                            List<Integer> prioIndex = new ArrayList<Integer>();
                            List<Integer> otherIndex = new ArrayList<Integer>();
                            List<Integer> changeIndex = new ArrayList<Integer>();
                            PrescriptionMain ydMain = null;
                            for (int i = 0; i < volumeBatchSize; i++) {
                                volumeBatchBeanTemp = VolumeBatchList.get(i);
                                if (volumeBatchBeanTemp.getYdMainList() != null
                                        && volumeBatchBeanTemp.getYdMainList().size() > 0) {
                                    if (NumberUtil.getObjDou(volumeBatchBeanTemp.getMaxval(), 0) > 0
                                            && volumeBatchBeanTemp.getNowval() > volumeBatchBeanTemp.getMaxval()) {
                                        forceIndex = new ArrayList<Integer>();
                                        prioIndex = new ArrayList<Integer>();
                                        otherIndex = new ArrayList<Integer>();
                                        changeIndex = new ArrayList<Integer>();
                                        for (int j = 0; j < volumeBatchBeanTemp.getYdMainList().size(); j++) {
                                            ydMain = volumeBatchBeanTemp.getYdMainList().get(j);
                                            if (ydMain.getZxbcIsForce().intValue() == 0) {
                                                if (ydMain.isPrioCheck()) {
                                                    prioIndex.add(j);
                                                    prioDou =
                                                            prioDou + NumberUtil.getObjDou(ydMain.getTransfusion(), 0);
                                                } else {
                                                    otherIndex.add(j);
                                                }
                                            } else {
                                                forceIndex.add(j);
                                                forceDou = forceDou + NumberUtil.getObjDou(ydMain.getTransfusion(), 0);
                                            }
                                        }

                                        if (forceDou > volumeBatchBeanTemp.getMaxval()) {
                                            continue;

                                            //如果总的容积超出上限，并且药物优先级+强制批次  总和不超出容积上限，则移动 其他批次,用二叉树算法 比对各种可能性，满足最优
                                        } else if (forceDou + prioDou <= volumeBatchBeanTemp.getMaxval()) {

                                            if (otherIndex.size() > 0) {
                                                List<String> strList = new ArrayList<String>();
                                                String str = "";
                                                String[] strN = null;
                                                genRealignment(0, str, otherIndex, strList);

                                                PrescriptionMain ydMainTemp = null;
                                                double delMinDou = 999999;
                                                double nowVal = 0D;
                                                String strOK = null;
                                                for (String strS : strList) {
                                                    strN = strS.split(",");
                                                    nowVal = 0D;
                                                    for (String indexs : strN) {
                                                        if (indexs != null && !indexs.equals("")) {
                                                            ydMainTemp =
                                                                    volumeBatchBeanTemp.getYdMainList()
                                                                            .get(Integer.parseInt(indexs));
                                                            nowVal =
                                                                    nowVal
                                                                            + NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                            0);
                                                        }
                                                    }
                                                    if ((forceDou + prioDou + nowVal) <= volumeBatchBeanTemp.getMaxval()
                                                            && (volumeBatchBeanTemp.getMaxval() - nowVal - forceDou - prioDou) < delMinDou) {
                                                        delMinDou = volumeBatchBeanTemp.getMaxval() - nowVal;
                                                        strOK = strS;
                                                    }
                                                }
                                                //找到最匹配的容积规则
                                                if (strOK != null) {
                                                    strN = strOK.split(",");
                                                    Map<String, Integer> mapOK = new HashMap<String, Integer>();
                                                    for (String indexs : strN) {
                                                        if (indexs != null && !indexs.equals("")) {
                                                            mapOK.put(indexs, 1);
                                                        }
                                                    }
                                                    for (Integer index : otherIndex) {
                                                        if (mapOK.get("" + index.intValue()) == null) {
                                                            changeIndex.add(index);
                                                        }
                                                    }
                                                    Collections.sort(changeIndex, new Comparator<Integer>() {
                                                        @Override
                                                        public int compare(Integer o1, Integer o2) {
                                                            return o1.intValue() > o2.intValue() ? -1 : 1;
                                                        }
                                                    });
                                                    for (Integer index : otherIndex) {
                                                        if (mapOK.get("" + index.intValue()) != null) {
                                                            changeIndex.add(index);
                                                        }
                                                    }
                                                } else {
                                                    changeIndex = otherIndex;
                                                }
                                                if (changeIndex != null && changeIndex.size() > 0) {
                                                    int index;
                                                    for (int m = 0; m < changeIndex.size(); m++) {
                                                        index = changeIndex.get(m);
                                                        ydMainTemp = volumeBatchBeanTemp.getYdMainList().get(index);
                                                        if (NumberUtil.getObjDou(volumeBatchBeanTemp.getMaxval(), 0) > 0
                                                                && volumeBatchBeanTemp.getNowval() > volumeBatchBeanTemp.getMaxval()) {
                                                            //遍历其他批次，将当前多出的药单放入其他批次中
                                                            for (int k = i + 1; k < volumeBatchSize; k++) {
                                                                volumeBatchBeanTemp2 = VolumeBatchList.get(k);
                                                                // if (volumeBatchBeanTemp2.getYdMainList() != null && volumeBatchBeanTemp2.getYdMainList().size() > 0) {
                                                                if (NumberUtil.getObjDou(volumeBatchBeanTemp2.getMaxval(),
                                                                        0) <= 0
                                                                        || (volumeBatchBeanTemp2.getMaxval() > 0 && volumeBatchBeanTemp2.getNowval()
                                                                        + NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                        0) <= volumeBatchBeanTemp2.getMaxval()
                                                                        .floatValue())) {
                                                                    boolean parentNoExist = false;
                                                                    //判断当前医嘱是否已在后面的批次中，如果已存在则不能调整顺序
                                                                    for (PrescriptionMain ydMainTemp2 : volumeBatchBeanTemp2.getYdMainList()) {
                                                                        if (ydMainTemp2.getParentNo()
                                                                                .equals(ydMainTemp.getParentNo())) {
                                                                            parentNoExist = true;
                                                                        }
                                                                    }
                                                                    if (!parentNoExist) {
                                                                        ydMainTemp.setYdreorderMess(ydMainTemp.getYdreorderMess()
                                                                                + "[容积规则：超出上限，将批次"
                                                                                + ydMainTemp.getPb_name()
                                                                                + "调整为"
                                                                                + volumeBatchBeanTemp2.getBatchName() + "]");
                                                                        if (ydMainTemp.getZxbcChangeBefore() == null) {
                                                                            ydMainTemp.setZxbcChangeBefore(ydMainTemp.getZxbc());
                                                                            ydMainTemp.setZxbcChangeBeforeS(ydMainTemp.getPb_name());
                                                                        }
                                                                        ydMainTemp.setZxbc(volumeBatchBeanTemp2.getBatchId());

                                                                        volumeBatchBeanTemp2.setNowval(volumeBatchBeanTemp2.getNowval()
                                                                                + NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                                0));
                                                                        volumeBatchBeanTemp.setNowval(volumeBatchBeanTemp.getNowval()
                                                                                - NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                                0));
                                                                        volumeBatchBeanTemp2.getYdMainList()
                                                                                .add(ydMainTemp);

                                                                        volumeBatchBeanTemp.getYdMainList()
                                                                                .remove(index);

                                                                        for (int n = 0; n < changeIndex.size(); n++) {
                                                                            if (changeIndex.get(n).intValue() > index) {
                                                                                changeIndex.set(n,
                                                                                        changeIndex.get(n) - 1);
                                                                            }
                                                                        }

                                                                        break;//不能删掉，如果批次移动，则不需要往后查找移动了，只是跳出volumeBatchBeanTemp2的循环，不是跳出 volumeBatchBeanTemp循环
                                                                    }
                                                                }
                                                                //}
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            //如果总的容积超出上限，并且药物优先级+强制批次  总和已超出容积上限，则
                                        } else {// 等同于forceDou+prioDou>volumeBatchBeanTemp.getMaxval()
                                            int ydMainSize = volumeBatchBeanTemp.getYdMainList().size();
                                            PrescriptionMain ydMainTemp = null;
                                            for (int j = ydMainSize - 1; j >= 0; j--) {
                                                //如果容积超出上限
                                                if (NumberUtil.getObjDou(volumeBatchBeanTemp.getMaxval(), 0) > 0
                                                        && volumeBatchBeanTemp.getNowval() > volumeBatchBeanTemp.getMaxval()) {
                                                    ydMainTemp = volumeBatchBeanTemp.getYdMainList().get(j);
                                                    //如果是非强制批次
                                                    if (ydMainTemp.getZxbcIsForce().intValue() == 0) {
                                                        //遍历其他批次，将当前多出的药单放入其他批次中
                                                        for (int k = i + 1; k < volumeBatchSize; k++) {
                                                            volumeBatchBeanTemp2 = VolumeBatchList.get(k);
                                                            //if(volumeBatchBeanTemp2.getYdMainList()==null || volumeBatchBeanTemp2.getYdMainList().size()<1){//与上面的代码区别，处理完批次不为空，还是超出，就处理空批次的
                                                            if (NumberUtil.getObjDou(volumeBatchBeanTemp2.getMaxval(),
                                                                    0) <= 0
                                                                    || (volumeBatchBeanTemp2.getMaxval() > 0 && volumeBatchBeanTemp2.getNowval()
                                                                    + NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                    0) <= volumeBatchBeanTemp2.getMaxval()
                                                                    .floatValue())) {
                                                                boolean parentNoExist = false;
                                                                //判断当前医嘱是否已在后面的批次中，如果已存在则不能调整顺序
                                                                if (volumeBatchBeanTemp2.getYdMainList() != null
                                                                        && volumeBatchBeanTemp2.getYdMainList().size() > 0) {
                                                                    for (PrescriptionMain ydMainTemp2 : volumeBatchBeanTemp2.getYdMainList()) {
                                                                        if (ydMainTemp2.getParentNo()
                                                                                .equals(ydMainTemp.getParentNo())) {
                                                                            parentNoExist = true;
                                                                        }
                                                                    }
                                                                }
                                                                if (!parentNoExist) {
                                                                    ydMainTemp.setYdreorderMess(ydMainTemp.getYdreorderMess()
                                                                            + "[容积规则：超出上限2，将批次"
                                                                            + ydMainTemp.getPb_name()
                                                                            + "调整为"
                                                                            + volumeBatchBeanTemp2.getBatchName()
                                                                            + "]");
                                                                    if (ydMainTemp.getZxbcChangeBefore() == null) {
                                                                        ydMainTemp.setZxbcChangeBefore(ydMainTemp.getZxbc());
                                                                        ydMainTemp.setZxbcChangeBeforeS(ydMainTemp.getPb_name());
                                                                    }
                                                                    ydMainTemp.setZxbc(volumeBatchBeanTemp2.getBatchId());

                                                                    volumeBatchBeanTemp2.setNowval(volumeBatchBeanTemp2.getNowval()
                                                                            + NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                            0));
                                                                    volumeBatchBeanTemp.setNowval(volumeBatchBeanTemp.getNowval()
                                                                            - NumberUtil.getObjDou(ydMainTemp.getTransfusion(),
                                                                            0));
                                                                    volumeBatchBeanTemp2.getYdMainList()
                                                                            .add(ydMainTemp);
                                                                    volumeBatchBeanTemp.getYdMainList().remove(j);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }

                                    }
                                }
                            }
                        }
                    }

                }

                String errMess = null;
                boolean isOK = true;
                for (VolumeBatchBean volumeBatchBeanUpdate : VolumeBatchList) {
                    errMess = "";
                    isOK = true;
                    //如果是空批 并且空批不启用，则跳过容积规则
                    if (!(ydFirst.getPb_isEmptyBatch().intValue() == 1 && !batchRuleTool.otherRuleConfig.isKongPCVolRun())) {
                        if (NumberUtil.getObjDou(volumeBatchBeanUpdate.getMaxval(), 0) > 0) {
                            if (volumeBatchBeanUpdate.getNowval() > volumeBatchBeanUpdate.getMaxval()) {
                                isOK = false;
                                errMess = "[当前批次容积超出上限]";
                            }
                        }
                        if (NumberUtil.getObjDou(volumeBatchBeanUpdate.getMinval(), 0) > 0) {
                            if (volumeBatchBeanUpdate.getNowval() < volumeBatchBeanUpdate.getMinval()) {
                                isOK = false;
                                errMess = "[当前批次容积低于下限]";
                            }
                        }
                    }
                    if (volumeBatchBeanUpdate.getYdMainList() != null && volumeBatchBeanUpdate.getYdMainList().size() > 0) {
                        for (PrescriptionMain ydMain : volumeBatchBeanUpdate.getYdMainList()) {
                            if (ydMain.getZxbcChangeBefore() != null) {//为空 说明批次有变化
                                ydMain.setPidrqzxbc(ydMain.getPidrqzxbc().substring(0, ydMain.getPidrqzxbc().lastIndexOf("_") + 1) + ydMain.getZxbc());
                                if (!isOK) {
                                    if (!(ydMain.getYdreorderMess() != null && ydMain.getYdreorderMess().contains(errMess))) {
                                        ydMain.setYdreorderMess(ydMain.getYdreorderMess() + errMess);
                                    }
                                }
                                //0待处理，1批次未调整-有异常，2批次有调整-有异常，3批次未调整-无异常，4批次有调整-无异常，
                                ydMain.setYdreorderCode(isOK == true ? SysConstant.ydreorderCode.i22HasChangeSucc : SysConstant.ydreorderCode.i12HasChangeErr);
                            } else {
                                if (!isOK) {
                                    if (!(ydMain.getYdreorderMess() != null && ydMain.getYdreorderMess().contains(errMess))) {
                                        ydMain.setYdreorderMess(ydMain.getYdreorderMess() + errMess);
                                    }
                                }
                                ydMain.setYdreorderCode(isOK == true ? SysConstant.ydreorderCode.i21NoChangeSucc : SysConstant.ydreorderCode.i11NoChangeErr);
                            }
                            ydMainService.updateYDReOrder(ydMain);
                        }
                    }
                }
            }
        } else {
            for (PrescriptionMain ydMain : list) {
                if (!ydMain.getYdreorderMess().contains("落空批")) {
                    ydMain.setYdreorderMess(!batchRuleTool.otherRuleConfig.isKongPCRun()
                            && ydFirst.getPb_isEmptyBatch().intValue() == 1 ? "空批不参与调整，直接通过" : "三大规则未启用，直接通过");
                    ydMain.setYdreorderCode(SysConstant.ydreorderCode.i21NoChangeSucc);
                }
                ydMainService.updateYDReOrder(ydMain);
            }
        }
    }
    
    /**
     *
     * 调整批次前,获取当个批次药单数据
     * @param ydMainList
     * @return
     */
    public static List<List<PrescriptionMain>> getList(List<PrescriptionMain> ydMainList, String yyrq) {
        List<List<PrescriptionMain>> list = new ArrayList<List<PrescriptionMain>>();
        List<PrescriptionMain> list1 = new ArrayList<PrescriptionMain>();//今日，非空批，上午
        List<PrescriptionMain> list2 = new ArrayList<PrescriptionMain>();//今日，非空批，下午
        List<PrescriptionMain> list3 = new ArrayList<PrescriptionMain>();//今日，空批，上午
        List<PrescriptionMain> list4 = new ArrayList<PrescriptionMain>();//今日，空批，下午
        List<PrescriptionMain> list5 = new ArrayList<PrescriptionMain>();//明日，非空批，上午
        List<PrescriptionMain> list6 = new ArrayList<PrescriptionMain>();//明日，非空批，下午
        List<PrescriptionMain> list7 = new ArrayList<PrescriptionMain>();//明日，空批，上午
        List<PrescriptionMain> list8 = new ArrayList<PrescriptionMain>();//明日，空批，下午
        List<PrescriptionMain> list9 = new ArrayList<PrescriptionMain>();//其他
        //用药日期：今天yyrqToday,明天yyrqNextDay
        //是否空批  isEmptyBatch=1 空批，=0非空批
        //上下午分组timetype    morning/morning2  afternoon/afternoon2   timetype_like_mor,timetype_like_aft

        if (ydMainList != null && ydMainList.size() > 0) {
            for (PrescriptionMain ydMain : ydMainList) {
                ydMain.setEmptyBatchTimeType("true" + "_" + ydMain.getPb_isEmptyBatch() + "_"
                        + ydMain.getPb_timetype());
                //今日，非空批，上午
                if (isToday(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 0
                        && ydMain.getPb_timetype().contains("morning")) {
                    list1.add(ydMain);
                } else if (isToday(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 0
                        && ydMain.getPb_timetype().contains("afternoon")) {
                    list2.add(ydMain);
                } else if (isToday(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 1
                        && ydMain.getPb_timetype().contains("morning")) {
                    list3.add(ydMain);
                } else if (isToday(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 1
                        && ydMain.getPb_timetype().contains("afternoon")) {
                    list4.add(ydMain);
                } else if (isTomorrow(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 0
                        && ydMain.getPb_timetype().contains("morning")) {
                    list5.add(ydMain);
                } else if (isTomorrow(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 0
                        && ydMain.getPb_timetype().contains("afternoon")) {
                    list6.add(ydMain);
                } else if (isTomorrow(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 1
                        && ydMain.getPb_timetype().contains("morning")) {
                    list7.add(ydMain);
                } else if (isTomorrow(ydMain.getYyrq(), yyrq) && ydMain.getPb_isEmptyBatch().intValue() == 1
                        && ydMain.getPb_timetype().contains("afternoon")) {
                    list8.add(ydMain);
                } else {
                    System.out.println();
                    ydMain.setYdreorderCode(SysConstant.ydreorderCode.i24NotReorder);
                    list9.add(ydMain);
                }
            }
            if (list1.size() > 0)
                list.add(list1);
            if (list2.size() > 0)
                list.add(list2);
            if (list3.size() > 0)
                list.add(list3);
            if (list4.size() > 0)
                list.add(list4);
            if (list5.size() > 0)
                list.add(list5);
            if (list6.size() > 0)
                list.add(list6);
            if (list7.size() > 0)
                list.add(list7);
            if (list8.size() > 0)
                list.add(list8);
            if (list9.size() > 0)
                list.add(list9);
        }
        return list;
    }

    /**
     * 内部方法:判断当前时间是否当日
     *
     * @param date
     * @return
     */
    private static boolean isToday(Date date, String yyrq) {
        SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
        String time1 = formatDate1.format(date);
        if (time1.equals(yyrq)) {
            return true;
        }
        return false;
    }

    public static boolean isTomorrow(Date date, String yyrq) {
        SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String time1 = formatDate1.format(calendar.getTime());
        if (time1.equals(yyrq)) {
            return true;
        }
        return false;
    }

    /*排列组合，返回到 List<String> strList*/
    private static void genRealignment(int i, String str, List<Integer> num, List<String> strList) {
        if (i == num.size()) {
            strList.add(str);
            //System.out.println(str);
            return;
        }
        genRealignment(i + 1, str, num, strList);
        genRealignment(i + 1, str + num.get(i) + ",", num, strList);
    }
}
