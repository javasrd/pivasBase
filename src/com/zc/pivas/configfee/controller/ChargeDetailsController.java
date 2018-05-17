package com.zc.pivas.configfee.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.NumberUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import com.zc.pivas.configfee.bean.ChargePzfDetailsBean;
import com.zc.pivas.configfee.dao.ChargeDetailsDao;
import com.zc.pivas.configfee.service.ChargeDetailsService;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.scans.constant.ScansConstant;
import com.zc.pivas.scans.service.ScansService;
import com.zc.pivas.synresult.bean.SetFymxDataReq;
import com.zc.pivas.synresult.bean.SetFymxDataResp;
import com.zc.pivas.synresult.service.SendToRestful;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 配置费详情控制类
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/chargedetails")
public class ChargeDetailsController extends SdBaseController {
    private static final Logger log = LoggerFactory.getLogger(ChargeDetailsController.class);

    @Resource
    private ChargeDetailsDao chargedetailsdao;

    @Resource
    private ChargeDetailsService chargeDetailsService;

    @Resource
    private ChargeDetailsDao chargeDetailsDao;

    @Resource
    private ScansService scansService;

    @Resource
    private PrescriptionMainService ydMainService;

    @Resource
    private SendToRestful sendToRestful;

    @Resource
    private DoctorAdviceService yzService;

    @Resource
    private InpatientAreaService inpAreaService;

    @RequestMapping(value = "/index")
    @RequiresPermissions(value = {"PIVAS_MENU_541"})
    public String chargeMain(Model model, HttpServletRequest request) {
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList = inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        return "pivas/chargedetails/chargeMain";
    }

    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"PIVAS_MENU_541"})
    @ResponseBody
    public String chargeList(@RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {
        String[] columns =
                new String[]{"gid", "ydMainId", "parentNo", "actOrderNo", "ydzxzt", "yzshzt", "freqCode", "zxrq", "zxsj",
                        "zxbc", "rucangOKNum", "pidsj", "pb_name", "bedno", "wardName", "freqCode", "patname", "age",
                        "ageunit", "chargeCode", "drugname", "specifications", "dose", "doseUnit", "quantity",
                        "medicamentsPackingUnit", "dybz", "yyrq"};
        JqueryStylePagingResults<PrescriptionMain> pagingResults = new JqueryStylePagingResults<PrescriptionMain>(columns);
        // List<YDMain> ydmList = new ArrayList<YDMain>();
        int rowCount = 0;

        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
        if (bednoStr != null) {
            String[] bednoS = bednoStr.split(",");
            param.put("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
        if (patnameStr != null) {
            String[] patnameS = patnameStr.split(",");
            param.put("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
        if (parentNoStr != null) {
            String[] parentNoS = parentNoStr.split(",");
            param.put("parentNoS", parentNoS);
        }
        String wardNameStr = StrUtil.getObjStr(param.get("wardNameS"));
        wardNameStr = DefineStringUtil.escapeAllLike(wardNameStr);
        if (wardNameStr != null) {
            String[] wardNameS = wardNameStr.split(",");
            param.put("wardNameS", wardNameS);
        }
        String freqCodeStr = StrUtil.getObjStr(param.get("freqCodeS"));
        freqCodeStr = DefineStringUtil.escapeAllLike(freqCodeStr);
        if (freqCodeStr != null) {
            String[] freqCodeS = freqCodeStr.split(",");
            param.put("freqCodeS", freqCodeS);
        }
        String chargeStateStr = StrUtil.getObjStr(param.get("chargeStateS"));
        if (chargeStateStr != null) {
            String[] chargeStateS = chargeStateStr.split(",");
            param.put("chargeStateS", chargeStateS);
        }

        String freqZXBCStr = StrUtil.getObjStr(param.get("freqZXBCS"));
        freqZXBCStr = DefineStringUtil.escapeAllLike(freqZXBCStr);
        if (freqZXBCStr != null) {
            String[] freqZXBCS = freqZXBCStr.split(",");
            param.put("freqZXBCS", freqZXBCS);
        }

        String chargeDateStr = StrUtil.getObjStr(param.get("chargeDate"));
        chargeDateStr = DefineStringUtil.escapeAllLike(chargeDateStr);
        if (chargeDateStr != null) {
            param.put("chargeDate", chargeDateStr);
        }

        String areaS = StrUtil.getObjStr(param.get("areaS"));
        param.remove("areaS");
        if (areaS != null && areaS.length() > 0) {
            String[] areaN = areaS.split(",");
            param.put("areaN", areaN);
        }

        List<PrescriptionMain> listYDMain = ydMainService.getYDMainbySearchParam(param, jquryStylePaging);

        if (listYDMain != null) {
            rowCount = ydMainService.getYDMainbySearchParamCount(param);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (PrescriptionMain ydmain : listYDMain) {
                if (ydmain.getGid() != null) {
                    Date lastChargeDate = chargeDetailsService.queryLastChargeState(ydmain.getGid());
                    if (lastChargeDate != null) {
                        String sDate = format.format(lastChargeDate);
                        ydmain.setChargeLastTime(sDate);
                    }
                }

                if (ydmain.getYyrq() != null) {
                    String sYYdate = format.format(ydmain.getYyrq());
                    ydmain.setYyrqS(sYYdate);
                }
            }
        }

        pagingResults.setDataRows(listYDMain);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return new Gson().toJson(pagingResults);
    }

    @RequestMapping(value = "/info", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String info(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        List<PrescriptionMain> ydmList = new ArrayList<PrescriptionMain>();
        List<ChargeBean> chargeList = new ArrayList<ChargeBean>();
        HashMap<String, ChargeBean> uniqueCostCodechargeList = new HashMap<String, ChargeBean>();
        List<ChargeBean> chargeListAll = new ArrayList<ChargeBean>();

        String parentNo = StrUtil.getObjStr(reqParam.get("parentNo"));
        String zxrq = StrUtil.getObjStr(reqParam.get("zxrq"));
        Integer zxbc = NumberUtil.getObjInt(reqParam.get("zxbc"));
        Long gid = NumberUtil.getObjLong(reqParam.get("gid"));
        if (parentNo != null && zxrq != null && zxbc != null && gid != null) {
            Map<String, Object> qry = new HashMap<String, Object>();
            qry.put("parentNo", parentNo);
            qry.put("zxrq", zxrq);
            qry.put("zxbc", zxbc);
            ydmList = ydMainService.getYDMainbySearchParam(qry, new JqueryStylePaging());
            if (ydmList != null && ydmList.size() > 0) {
                qry.put("gid", gid);
                chargeListAll = chargeDetailsService.qryListChargeByMap2(qry, new JqueryStylePaging());

                if (chargeListAll != null) {
                    for (ChargeBean chargeBean : chargeListAll) {
                        if (chargeBean.getCostcode() != null) {
                            // uniqueCostCodechargeList存储不同costcode的最新的一条收费信息
                            if (uniqueCostCodechargeList.get(chargeBean.getCostcode()) == null) {
                                uniqueCostCodechargeList.put(chargeBean.getCostcode(), chargeBean);
                            } else {
                                if (chargeBean.getPzfsqrq()
                                        .compareTo(uniqueCostCodechargeList.get(chargeBean.getCostcode()).getPzfsqrq()) > 0) {
                                    uniqueCostCodechargeList.put(chargeBean.getCostcode(), chargeBean);
                                }
                            }
                        }
                    }

                    // 找出配置费表中不同costcode的最晚的一条收费信息，页面仅展示最后一条信息
                    for (Map.Entry<String, ChargeBean> entry : uniqueCostCodechargeList.entrySet()) {
                        chargeList.add(entry.getValue());
                    }
                }

                model.addAttribute("success", true);
            } else {
                ydmList = new ArrayList<PrescriptionMain>();
            }

            List<DoctorAdvice> yzList = yzService.qryBeanDistByPidsj(parentNo + "_" + "0_0" + "_" + ydmList.get(0).getYzlx().toString() + "_" + "0", 0);
            Map<String, Integer> medMap = new HashMap<String, Integer>();
            String medNames = "";
            if (yzList != null && yzList.size() > 0) {
                for (DoctorAdvice yz : yzList) {
                    if (yz.getCategoryName() != null && !"null".equals(yz.getCategoryName())
                            && medMap.get(yz.getCategoryName()) == null) {
                        if (medNames.equals("")) {
                            medNames = yz.getCategoryName();
                        } else {
                            medNames = medNames + "," + yz.getCategoryName();
                        }
                        medMap.put(yz.getCategoryName(), 1);
                    }
                }
            }
            model.addAttribute("medNames", medNames);

            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }

        model.addAttribute("ydMain", ydmList.get(0));
        model.addAttribute("chargeList", chargeList);
        model.addAttribute("chargeListAll", chargeListAll);
        return "pivas/chargedetails/chargeInfo";
    }

    @RequestMapping(value = "/changOne")
    @RequiresPermissions(value = {"PIVAS_MENU_511"})
    @ResponseBody
    public Integer changOne(String ydPzfId, String costCode, String charge) throws Exception {

        Integer iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_SUCESS;

        if (ydPzfId != null && costCode != null) {
            ChargeBean chargeBean = chargeDetailsService.qryChargeDetail(ydPzfId);

            if (chargeBean != null) {

                SetFymxDataReq req = new SetFymxDataReq();

                // 住院流水号
                req.setAlzyh(chargeBean.getCaseID());

                // 计费项目医院内部序号
                req.setAlfyxh(chargeBean.getCostcode());

                // 执行工号
                req.setAszxgh(Propertiesconfiguration.getStringProperty("fee.zxgh"));

                // 药品数量
                // charge为0表示收费失败，需要收费，数量为正
                if (charge.equals("0")) {
                    iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_SUCESS;
                    req.setAdfysl(String.valueOf(chargeBean.getAmount()));
                }
                // charge为1或2表示是收费成功或退费失败，可以退费，数量为负
                else if (charge.equals("1") || charge.equals("2")) {
                    iRst = ScansConstant.ConfigFreeResult.REFUND_CHARGE_SUCESS;
                    req.setAdfysl("-" + String.valueOf(chargeBean.getAmount()));
                    Integer refundAmount = NumberUtils.toInt("-" + String.valueOf(chargeBean.getAmount()));
                    chargeBean.setAmount(refundAmount);
                }

                String pzfsbyy = "";
                try {
                    SetFymxDataResp setFymxDataResp = sendToRestful.setFymxData(req);

                    // 返回成功失败标志 1 成功 -1 失败
                    String sRst = setFymxDataResp.getAlret();
                    if (!"1".equals(sRst)) {
                        // charge为0表示是退费失败
                        if (charge.equals("0")) {
                            iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_FAIL;
                        }
                        // charge为1或2表示是退费失败
                        else if (charge.equals("1") || charge.equals("2")) {
                            iRst = ScansConstant.ConfigFreeResult.REFUND_CHARGE_FAIL;
                        }

                        pzfsbyy = setFymxDataResp.getAserrtext();
                    }
                } catch (Exception e) {
                    // charge为0表示是退费失败
                    if (charge.equals("0")) {
                        iRst = ScansConstant.ConfigFreeResult.CONFIG_FEE_COLLECT_FAIL;
                    }
                    // charge为1或2表示是退费失败
                    else if (charge.equals("1") || charge.equals("2")) {
                        iRst = ScansConstant.ConfigFreeResult.REFUND_CHARGE_FAIL;
                    }
                }

                chargeBean.setPzfzt(iRst);
                chargeBean.setPzfsbyy(pzfsbyy);
                chargeBean.setCzymc(getCurrentUser().getName());
                chargeDetailsDao.insertPzf1(chargeBean);

                List<ChargePzfDetailsBean> ChargePzfDetailsBeanList = chargeDetailsDao.qryPzfPQRefInfo(chargeBean.getPqRefFeeID());
                List<String> retList = new ArrayList<String>();
                List<String> dateList = new ArrayList<String>();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
                for (ChargePzfDetailsBean bean : ChargePzfDetailsBeanList) {
                    Integer pzfzt = bean.getPzfzt();
                    String pzfztStr = pzfzt.toString();
                    if (!retList.contains(pzfztStr)) {
                        retList.add(pzfztStr);
                    }

                    Date date = bean.getPzfSQRQ();
                    String dateStr = dateFormater.format(date);

                    if (!dateList.contains(dateStr)) {
                        dateList.add(dateStr);
                    }
                }

                String rstAll = "";
                for (String tmp : retList) {
                    rstAll += tmp;
                    rstAll += ",";
                }
                if (!rstAll.isEmpty()) {
                    rstAll = rstAll.substring(0, rstAll.length() - 1);
                }

                String dateAll = "";
                for (String tmp : dateList) {
                    dateAll += tmp;
                    dateAll += ",";
                }
                if (!dateAll.isEmpty()) {
                    dateAll = dateAll.substring(0, dateAll.length() - 1);
                }

                chargeDetailsDao.updatePPRCF(chargeBean.getPqRefFeeID(), null, rstAll, dateAll);
                log.info("UPDATE PRF GID:[" + chargeBean.getPqRefFeeID() + "]  RESULT_DETAILS:[" + rstAll + "]"
                        + " DATE_DETAILS:[" + dateAll + "]");
            }
        }

        return iRst;
    }

    //

    /**
     * 查询药单列表
     *
     * @param chargedetailsbean
     * @return
     */
    @RequestMapping(value = "/chargeDetailList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_MENU_501"})
    public String chargeDetailList(ChargeDetailsBean chargedetailsbean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<ChargeDetailsBean> chargedetailbeanlist =
                chargeDetailsService.queryChargeDetailsList(chargedetailsbean, jquryStylePaging);
        return new Gson().toJson(chargedetailbeanlist);
    }

    /**
     * 查询药单列表
     *
     * @param model
     * @return
     */

    @RequestMapping(value = "/dbqueryChargeDetailsList", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String dbqueryChargeDetailsList(Model model, HttpServletRequest request)
            throws Exception {

        return "pivas/chargedetails/dbqueryChargeDetailsList";
    }

}
