package com.zc.pivas.docteradvice.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.common.util.UniqueNumberGenerator;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.*;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.pivas.checktype.bean.ErrorTypeBean;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.common.entity.ResultData;
import com.zc.pivas.common.util.*;
import com.zc.pivas.common.util.SysConstant.trackingRecord;
import com.zc.pivas.docteradvice.bean.*;
import com.zc.pivas.docteradvice.dtsystem.JaxbBinder;
import com.zc.pivas.docteradvice.dtsystem.message.*;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.syndatasz.AnalySynDataForSZ;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import com.zc.pivas.patient.service.PatientService;
import com.zc.pivas.printlabel.service.PrintLabelService;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 医嘱控制类
 *
 * @author cacabin
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/doctorAdvice")
public class DoctorAdviceController extends SdBaseController {
    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(DoctorAdviceController.class);

    final static String shortTime = Propertiesconfiguration.getStringProperty("pivas.pcchange.short.limit");

    final static String longTime = Propertiesconfiguration.getStringProperty("pivas.pcchange.long.limit");

    final static String tnpStr = Propertiesconfiguration.getStringProperty("pivas.tpn.medic");

    final static String RETUFN_PREFIX = Propertiesconfiguration.getStringProperty("pivas.return.batch.prefix");

    final static int ACTION_EXCUTERECORD = 6;

    /**
     * 1:医嘱
     */
    private final static int ACTION_ADVICE = 1;

    /**
     * 对接成功
     */
    private final static String SYN_SUCCESS = "200";

    @Resource
    private DoctorAdviceMainService yzMainService;

    @Resource
    private DoctorAdviceService yzService;

    @Resource
    private PrescriptionMainService ydMainService;

    @Resource
    private UserService userService;

    @Resource
    private InpatientAreaService inpAreaService;

    @Resource
    private BatchService batchService;

    @Resource
    private PatientService patientService;

    /**
     * 同步医嘱数据
     */
    @Resource
    private AnalySynDataForSZ analySynDataForSZ;

    @Resource
    private AnalyResponMessage analyResponMessage;

    @Resource
    private PrintLabelService printLabelService;

    @Resource
    private MedicCategoryService medicCategoryService;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    public final static String SPLIT_MODE = Propertiesconfiguration.getStringProperty("pivas.yz.split.mode");

    /**
     * 医嘱首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/main")
    @RequiresPermissions(value = {"PIVAS_MENU_502"})
    public String main(Model model, HttpServletRequest request) {
        String inpatientString = request.getParameter("inpatientString");

        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());
        model.addAttribute("batchRuleList", yzMainService.qryBatchRuleEnabled(true));
        model.addAttribute("ruleList", yzMainService.qryGeneralRule(new HashMap<String, Object>()));
        model.addAttribute("SYN_YZ_DATA_MODE", AnalyResponMessage.SYN_YZ_DATA_MODE);
        model.addAttribute("inpatientString", inpatientString);

        User currentUser = (User) getCurrentUser();
        String account = currentUser.getAccount();
        List<ConfigTitleBean> showList = yzMainService.qryTitleList();

        Long id = null;

        mainfor:
        for (ConfigTitleBean bean : showList) {
            String user = bean.getUseBy().trim();
            String[] userArray = user.split(",");

            for (String name : userArray) {
                String nameStr = name.trim();
                if (nameStr.equals(account)) {
                    id = bean.getConfId();
                    break mainfor;
                }
            }
        }
        String titleStrs = "";
        if (id != null) {
            String[] titleList = yzMainService.qryShowTitle(id);
            int size = titleList.length;
            for (int i = 0; i < size; i++) {
                titleStrs = titleStrs + titleList[i].trim();
                if (i < size - 1) {
                    titleStrs = titleStrs + ",";
                }
            }
        }

        if (StringUtils.isBlank(titleStrs)) {
            titleStrs = "bedno,wardname,patname,age,freqcode,drugname,dose,doseunit,quantity,medicamentspackingunit,yzlx,yzzt,sfysmc,sfrq,yzshzt,yzshbtglx";
        }

        model.addAttribute("titleStrs", titleStrs);

        return "pivas/doctorAdvice/damain";
    }

    /**
     * 药单历史首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/prescriptionHis")
    @RequiresPermissions(value = {"PIVAS_MENU_531"})
    public String ydHistory(Model model, HttpServletRequest request) {
        List<Batch> batchList = batchService.queryBatchAllList();
        model.addAttribute("batchList", batchList);

        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList =
                inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);
        
        /*String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);*/

        return "pivas/doctorAdvice/prescriptionHis";
    }

    /**
     * 不合理医嘱报表页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/yzUnRsnbReport")
    // @RequiresPermissions(value = {"PIVAS_MENU_502"})
    public String yzUnRsnbReport(Model model) {
        //model.addAttribute("errTypeList", yzMainService.getErrorTypeList());
        return "pivas/doctorAdvice/yzUnCheckReport";
    }

    /**
     * 医嘱列表查询
     *
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzPageData", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String yzPageData(@RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging)
            throws Exception {

        if (param.get("filter") != null && param.get("filter").toString().length() > 0) {
            String[] filterN = param.get("filter").toString().split(",");
            for (String filter : filterN) {
                if (filter != null && !filter.equals("") && !filter.equals("null")) {
                    param.put(filter, "1");
                }
            }
        }
        param.put("leftWithPati", "Y");

        String inpatientString = (String) param.get("inpatientString");
        ArrayList<String> inpatientArr = new ArrayList<>();
        if (!"".equals(inpatientString) && inpatientString != null) {
            String[] inpatientArray = inpatientString.split(",");
            for (String v : inpatientArray) {
                inpatientArr.add(v);
            }
            param.replace("inpatientString", inpatientArr);
        }

        DoctorAdviceTool.initYZParam(param);
        // param.put("rucangOKNum", "Y");
        JqueryStylePagingResults<DoctorAdviceMain> pageResult = yzMainService.qryPageBeanByMap(param, jquryStylePaging);

        return new Gson().toJson(pageResult);
    }

    /**
     * 药师审方医嘱不分页列表
     *
     * @param param
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toYzList", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String toYzList(@RequestParam Map<String, Object> param, Model model) throws Exception {

        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        if (StringUtils.isNotBlank(bednoStr)) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoS = bednoStr.split(",");
            param.replace("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        if (StringUtils.isNotBlank(patnameStr)) {
            patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
            String[] patnameS = patnameStr.split(",");
            param.replace("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        if (StringUtils.isNotBlank(parentNoStr)) {
            parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
            String[] parentNoS = parentNoStr.split(",");
            param.replace("parentNoS", parentNoS);
        }
        String freqCodeStr = StrUtil.getObjStr(param.get("freqCodeS"));
        if (StringUtils.isNotBlank(freqCodeStr)) {
            freqCodeStr = DefineStringUtil.escapeAllLike(freqCodeStr);
            String[] freqCodeS = freqCodeStr.split(",");
            param.replace("freqCodeS", freqCodeS);
        }
        String drugnameQryStrs = StrUtil.getObjStr(param.get("drugnameQry"));
        if (StringUtils.isNotBlank(drugnameQryStrs)) {
            drugnameQryStrs = DefineStringUtil.escapeAllLike(drugnameQryStrs);
            String[] drugnameQry = drugnameQryStrs.split(",");
            param.replace("drugnameQry", drugnameQry);
        }

        String inpatientString = StrUtil.getObjStr(param.get("inpatientString"));
        if (StringUtils.isNotBlank(inpatientString) || "-1".equals(inpatientString)) {
            String[] inpatientArray = inpatientString.split(",");
            param.replace("inpatientString", inpatientArray);
        }

        String ydflStr = StrUtil.getObjStr(param.get("ydflStr"));
        if (StringUtils.isNotBlank(ydflStr)) {
            String[] ydflArray = ydflStr.split(",");
            param.replace("ydflStr", ydflArray);
        }

        String[] tpnArr = tnpStr.split(",");
        param.put("tpnArr", tpnArr);

        param.put("change", null);

        if (DataFormat.dateChange() && !param.get("sfrqSelect").toString().equals("today")) {
            param.remove("change");
            param.put("change", "1");
        }

        // 如果为不通过有药单，不判断医嘱状态
        if (param.containsKey("state") && StringUtils.isNotEmpty(param.get("state").toString()) && (param.get("state").toString().equals("checkNOHasYD") || param.get("state").toString().equals("mismatchYD"))) {
            param.remove("yzztLowN");
            param.put("yzztLowN ", null);
        }

        List<DoctorAdviceMainBean> yzMainList = yzMainService.qryListWithYZByMap(param);

        model.addAttribute("yzMainList", yzMainList == null ? new ArrayList<DoctorAdviceMainBean>() : yzMainList);
        model.addAttribute("yzMainListSize", yzMainList == null ? 0 : yzMainList.size());
        model.addAttribute("success", true);

        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());
        model.addAttribute("showError", true);

        return "pivas/doctorAdvice/yzRowInfo";
    }

    /**
     * 医嘱按照病区分组
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzGroupByArea", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String yzGroupByArea(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        int group = 0;
        if (param.get("groupBy") != null && param.get("groupBy").toString().contains("area")) {
            group = 1;
        }
        if (param.get("groupBy") != null && param.get("groupBy").toString().contains("patient")) {
            group = group + 2;
        }
        if (param.get("filter") != null && param.get("filter").toString().length() > 0) {
            String[] filterN = param.get("filter").toString().split(",");
            for (String filter : filterN) {
                if (filter != null && !filter.equals("") && !filter.equals("null")) {
                    param.put(filter, "1");
                }
            }
        }
        DoctorAdviceTool.initYZParam(param);
        if (group > 0) {
            List<ErrorTypeBean> listErrType = yzMainService.getErrorTypeList();
            Map<Integer, String> mapErrType = new HashMap<Integer, String>();
            for (ErrorTypeBean errType : listErrType) {
                mapErrType.put(Integer.parseInt(errType.getGid()), errType.getName());
            }

            param.put("leftWithPati", "Y");
            List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(param, new JqueryStylePaging());

            List<DoctorAdviceMain> group1 = new ArrayList<DoctorAdviceMain>();
            Map<String, Integer> areaIndex = new HashMap<String, Integer>();
            Map<String, Integer> patientIndex = new HashMap<String, Integer>();
            Map<String, Integer> areaPatientIndex = new HashMap<String, Integer>();
            DoctorAdviceMain yzGroup = null;
            DoctorAdviceMain areaPati = null;
            List<DoctorAdviceMain> children1 = null;
            List<DoctorAdviceMain> children2 = null;
            if (group == 1) {//只根据病区分组
                for (DoctorAdviceMain yzMain : yzMainList) {
                    Integer index1 = areaIndex.get(yzMain.getWardCode());
                    if (index1 == null) {
                        yzGroup = new DoctorAdviceMain();
                        yzGroup.setExpanded(true);
                        group1.add(yzGroup);
                        areaIndex.put(yzMain.getWardCode(), group1.size() - 1);
                    } else {
                        yzGroup = group1.get(index1);
                    }
                    yzGroup.setChildNum(yzGroup.getChildNum() + 1);
                    yzGroup.setGroupName(yzMain.getWardName() + "[" + (yzGroup.getChildNum()) + "]");
                    children1 = yzGroup.getChildren();
                    children1.add(yzMain);
                }
            } else if (group == 2) {//只根据病人分组
                for (DoctorAdviceMain yzMain : yzMainList) {
                    Integer index1 = patientIndex.get(yzMain.getInpatientNo());
                    if (index1 == null) {
                        yzGroup = new DoctorAdviceMain();
                        yzGroup.setExpanded(true);
                        group1.add(yzGroup);
                        patientIndex.put(yzMain.getInpatientNo(), group1.size() - 1);
                    } else {
                        yzGroup = group1.get(index1);
                    }
                    yzGroup.setChildNum(yzGroup.getChildNum() + 1);
                    yzGroup.setGroupName(yzMain.getPatname() + "[" + (yzGroup.getChildNum()) + "]");
                    children1 = yzGroup.getChildren();
                    children1.add(yzMain);
                }
            } else if (group == 3) {//根据 病区，病人 分组
                String areaPatientId = null;
                for (DoctorAdviceMain yzMain : yzMainList) {
                    Integer index1 = areaIndex.get(yzMain.getWardCode());
                    if (index1 == null) {
                        yzGroup = new DoctorAdviceMain();
                        yzGroup.setExpanded(true);
                        group1.add(yzGroup);
                        areaIndex.put(yzMain.getWardCode(), group1.size() - 1);
                    } else {
                        yzGroup = group1.get(index1);//第一层分组中 对象
                    }
                    //如果找不到病人，说明有新的病人，增加病人序号
                    yzGroup.setChildNum(yzGroup.getChildNum() + 1);
                    yzGroup.setGroupName(yzMain.getWardName() + "[" + (yzGroup.getChildNum()) + "]");

                    areaPatientId = yzMain.getWardCode() + "," + yzMain.getInpatientNo();
                    Integer index2 = areaPatientIndex.get(areaPatientId);//获取 医嘱 在 【病区-病人】 中的序号，如果没有，病人不存在，添加病人映射
                    if (index2 == null) {
                        areaPati = new DoctorAdviceMain();
                        areaPati.setExpanded(true);

                        children1 = yzGroup.getChildren();//获取第一层中 病人列表
                        children1.add(areaPati);
                        areaPatientIndex.put(areaPatientId, children1.size() - 1);
                    } else {
                        children1 = yzGroup.getChildren();//获取第一层中 病人列表
                        areaPati = children1.get(index2);
                    }
                    areaPati.setChildNum(areaPati.getChildNum() + 1);
                    areaPati.setGroupName(yzMain.getPatname() + "[" + (areaPati.getChildNum()) + "]");

                    children2 = areaPati.getChildren();
                    children2.add(yzMain);
                }
            }
            return new Gson().toJson(group1);
        } else {
            return new Gson().toJson(new ArrayList<DoctorAdviceMain>());
        }
    }

    /**
     * 医嘱详情页
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toYzInfo", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String toYzInfo(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        String pidsj = StrUtil.getObjStr(reqParam.get("pidsj"));
        if (pidsj != null) {
            DoctorAdviceMain yzMain = yzMainService.qryBeanByPidsj(pidsj, true);
            if (yzMain != null) {
                yzMain.setFreqCode(yzMain.getFreqCode() + "\\" + yzMain.getSupplyCode());
                List<PrescriptionBLabel> ydpqList = yzMainService.qryYdMoreByPidsj(yzMain.getParentNo() + "_" + yzMain.getYzlx());
                List<DoctorAdvice> yzList = yzService.qryBeanDistByPidsj(pidsj, null);
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
                model.addAttribute("yzMain", yzMain);
                model.addAttribute("ydpqList", ydpqList == null ? new ArrayList<PrescriptionBLabel>() : ydpqList);
                model.addAttribute("yzList", yzList == null ? new ArrayList<DoctorAdviceMain>() : yzList);
                model.addAttribute("errTypeList", yzMainService.getErrorTypeList());

                model.addAttribute("success", true);
            } else {
                model.addAttribute("success", false);
            }
        } else {
            model.addAttribute("success", false);
        }
        return "pivas/doctorAdvice/yzInfo";
    }

    /**
     * 医嘱详情新页面
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/dctrAdvcInfo", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public ResultData dctrAdvcInfo(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        DoctorAdviceMain yzMain = null;
        String pidsj = StrUtil.getObjStr(reqParam.get("pidsj"));
        if (pidsj != null) {
            yzMain = yzMainService.qryBeanByPidsj(pidsj, true);
            if (yzMain != null) {
                yzMain.setFreqCode(yzMain.getFreqCode() + " " + yzMain.getSupplyCode());
                List<DoctorAdvice> yzList = yzService.qryBeanDistByPidsj(pidsj, 0);
                Map<String, Integer> medMap = new HashMap<String, Integer>();
                String medNames = "";
                if (yzList != null && yzList.size() > 0) {
                    for (DoctorAdvice yz : yzList) {
                        if (yz.getCategoryName() != null && !"null".equals(yz.getCategoryName())
                                && medMap.get(yz.getCategoryName()) == null && yz.getYzzt() == 0) {
                            if (medNames.equals("")) {
                                medNames = yz.getCategoryName();
                            } else {
                                medNames = medNames + "," + yz.getCategoryName();
                            }
                            medMap.put(yz.getCategoryName(), 1);
                        }
                    }
                }
                result.put("medNames", medNames);
                result.put("yzMain", yzMain);
                result.put("yzList", yzList == null ? new ArrayList<DoctorAdviceMain>() : yzList);
            }
        }
        return DataFormat.formatAdd(yzMain != null ? 1 : 0, yzMain != null ? "医嘱查询成功" : "医嘱查询失败", result);
    }

    @RequestMapping(value = "/initYP")
    @ResponseBody
    public String initYP(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        String pidsj = StrUtil.getObjStr(param.get("pidsj"));
        List<DoctorAdvice> yzList = new ArrayList<>();

        if (StringUtils.isNotBlank(pidsj)) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pidsj", pidsj);
            map.put("leftWithMediCati", 1);
            List<DoctorAdvice> yzList1 = yzService.qryListBeanByMap(map, new JqueryStylePaging());

            Map<String, Integer> actOrderNoMap = new HashMap<String, Integer>();
            if (yzList1 != null && yzList1.size() > 0) {
                for (int i = 0; i < yzList1.size(); i++) {
                    if (actOrderNoMap.get(yzList1.get(i).getActOrderNo()) == null) {
                        yzList.add(yzList1.get(i));
                        actOrderNoMap.put(yzList1.get(i).getActOrderNo(), 1);
                    }
                }
            }
        }

        String[] columns = new String[]{"chargeCode", "medicamentsName", "specifications2", "dose", "doseUnit", "quantity", "medicamentsPackingUnit"};
        JqueryStylePagingResults<DoctorAdvice> pagingResults = new JqueryStylePagingResults<DoctorAdvice>(columns);
        pagingResults.setDataRows(yzList);
        pagingResults.setTotal(yzList.size());
        pagingResults.setPage(1);

        return new Gson().toJson(pagingResults);

    }

    /**
     * 审方生成批次
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkMany", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_504", "PIVAS_BTN_513", "PIVAS_BTN_516"})
    public Map<?, ?> checkMany(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<?, ?> result = MyMap.quickInitMap("code", 1, "msg", getMessage("comm.err.param"), "data", null);
        User currentUser = (User) getCurrentUser();
        Object checkAccount = reqParam.get("checkAccount");
        Object checkPass = reqParam.get("checkPass");

        boolean check = false;
        if ("1".equals(reqParam.get("yzParea"))) {
            check = true;
        } else if (checkAccount != null && checkPass != null) {
            check = userService.checkOldPassword(currentUser.getUserId(), checkAccount.toString(), checkPass.toString());
        }
        if (!check) {
            result = MyMap.quickInitMap("code", 1, "msg", getMessage("comm.err.param1"), "data", null);
            return result;
        }
        boolean resultF = true;
        String pidsjNS = reqParam.get("pidsjN") != null ? reqParam.get("pidsjN").toString() : "";
        String[] pidsjN = pidsjNS.split(",");
        String repeatCheck = reqParam.get("repeatCheck") != null ? reqParam.get("repeatCheck").toString() : "";
        Integer newYzshzt = NumUtil.getObjInt(reqParam.get("newYzshzt"), -1);
        Long yzshbtglx = NumUtil.getObjLong(reqParam.get("yzshbtglx"));
        String checkType = reqParam.get("checkType") != null ? reqParam.get("checkType").toString() : "";
        String yzshbtgyy = reqParam.get("yzshbtgyy") != null ? reqParam.get("yzshbtgyy").toString() : "";
        String fhCheck = reqParam.get("fhCheck") != null ? reqParam.get("fhCheck").toString() : "";

        if (pidsjN != null && pidsjN.length > 0 && (newYzshzt == 1 || newYzshzt == 2)) {
            Map<String, Object> qry = new HashMap<String, Object>();
            qry.put("pidsjN", pidsjN);
            qry.put("ydztLowN", 5);// 关联药单表，并且药单状态 小于5 未执行的，才可以审核
            //qry.put("yzztLowN", 1);// 药单状态 医嘱过来的数据，医嘱状态是执行中 才可以审核
            qry.put("leftWithPati", "Y");

            List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(qry, new JqueryStylePaging());

            if (yzMainList != null && yzMainList.size() > 0) {
                //Date dayNow = new Date();
                String day8Str = DateUtil.getCurrentDay8Str();
                List<String> YZUpdatePidsjN = new ArrayList<String>();// 更新医嘱状态--医嘱pidsj
                List<String> deletePidN = new ArrayList<String>();// 删除已生成药单--医嘱pidsj
                List<String> createYDPidsjN = new ArrayList<String>();// 需要重新生成药单--医嘱pidsj
                List<String> pidrqNRucangOk = new ArrayList<String>();// 是否已经入仓的药单
                List<OprLog> oprLogList = new ArrayList<OprLog>();
                OprLog oprLog = null;

                for (DoctorAdviceMain yzMain : yzMainList) {
                    if (newYzshzt == 1)// 新的状态是 审核通过，则可能需要重建，删除原来的
                    {
                        if (yzMain.getYzshzt() == 1) {//如果之前审核过，判断是否需要删除重新生成数据
                            if ("Y".equals(repeatCheck))//是否需要删除 重新生成
                            {
                                deletePidN.add(yzMain.getParentNo() + "_" + yzMain.getYzlx());
                                createYDPidsjN.add(yzMain.getPidsj());
                            }
                        } else {
                            createYDPidsjN.add(yzMain.getPidsj());
                            deletePidN.add(yzMain.getParentNo() + "_" + yzMain.getYzlx());
                        }
                        pidrqNRucangOk.add(yzMain.getParentNo() + "_" + yzMain.getYzlx() + "_" + day8Str);
                    } else {// 新的状态是 审核不通过，则可能删除原来已审核通过的
                        deletePidN.add(yzMain.getParentNo() + "_" + yzMain.getYzlx());
                    }
                    YZUpdatePidsjN.add(yzMain.getPidsj());

                    oprLog = new OprLog();
                    oprLog.initYZLog(currentUser.getUserId(),
                            currentUser.getAccount(),
                            yzMain.getPidsj(),
                            "" + yzMain.getYzshzt(),
                            "" + newYzshzt);
                    oprLogList.add(oprLog);
                }
                Map<String, Object> updateMap = new HashMap<String, Object>();
                updateMap.put("yzshzt", newYzshzt);
                updateMap.put("yzshbtglx", yzshbtglx);
                updateMap.put("yzshbtgyy", yzshbtgyy);
                updateMap.put("sfysmc", currentUser.getName());
                updateMap.put("sfyscode", currentUser.getAccount());
                updateMap.put("sfrq", new Date());
                updateMap.put("pidsjN", YZUpdatePidsjN);
                int row1 = yzMainService.updateCheckYZMain(updateMap, deletePidN);
                if (row1 > -1) {
                    yzMainService.addOprLogMany(oprLogList);
                }

                if (createYDPidsjN != null && createYDPidsjN.size() > 0) {
                    Map<String, Object> qryYZ = new HashMap<String, Object>();
                    qryYZ.put("pidsjN", createYDPidsjN);
                    qryYZ.put("leftWithPati", 1);
                }
                for (DoctorAdviceMain yzMain : yzMainList) {
                    addOperLog("yz".equals(checkType) ? AmiLogConstant.MODULE_BRANCH.PM.Doctor
                                    : AmiLogConstant.MODULE_BRANCH.PM.PHARMACIST_REVIEW,
                            AmiLogConstant.BRANCH_SYSTEM.PM,
                            currentUser.getAccount() + " 审核 " + yzMain.getD_deptname() + " " + yzMain.getBedno() + " "
                                    + yzMain.getP_patname() + " 医嘱[" + yzMain.getParentNo() + "]"
                                    + (newYzshzt == 1 ? "审方通过" : "审方不通过"),
                            true);

                    if ("true".equals(fhCheck)) {

                        yzMainService.updateRecheckState(yzMain.getParentNo(), newYzshzt);
                    }
                    //审核医嘱后，如果是批量审核，需要循环每个医嘱，记录操作日志，操作日志内容包括  谁+审了哪个病区+审了哪一床+病人姓名+审核结果+医嘱组号
                }
                // 生成药单时 ，需要先判断 是否有频次等条件
                if (resultF) {
                    result = MyMap.quickInitMap("code", 0, "msg", getMessage("pivas.yz1.shenfangOk"), "data", null);
                } else {
                    result = MyMap.quickInitMap("code", 1, "msg", "医嘱审核异常，请重试", "data", null);
                }
            } else {
                result = MyMap.quickInitMap("code", 1, "msg", "医嘱已停止或全部入仓，无法审核", "data", null);
            }

        } else {
            result = MyMap.quickInitMap("code", 1, "msg", "无可审核的医嘱", "data", null);
            addOperLog(AmiLogConstant.MODULE_BRANCH.PM.Doctor,
                    AmiLogConstant.BRANCH_SYSTEM.PM,
                    getMessage("log.yz.tip.checkYZ", new String[]{""}),
                    false);
        }
        return result;
    }

    /**
     * 药单自动检查方法
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/autoCheckRun", produces = "application/json")
    @ResponseBody
    public String autoCheckRun(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        //return PrescriptionAutoGen.autoGenYDRun(new Date());
        // 出发药单执行记录任务
        try {
            SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_EXCUTERECORD));
        } catch (Exception e) {
            log.error("ydrecordTask 触发药单执行记录同步任务失败", e);
        }
        try {
            //检查当天数据
            PrescriptionRecordTasks.taskStart(String.valueOf(reqParam.get("yyrq")));
        } catch (Exception e) {
            log.error("自动落批次检查失败", e.getMessage());
            return "药单生成失败";
        }

        return "药单生成成功！";
    }

    /**
     * 药师审方核對
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzPCheck")
    @RequiresPermissions(value = {"PIVAS_MENU_511"})
    public String mainPArea(Model model, HttpServletRequest request) throws Exception {
        /*InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList =
            inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);
        String deptCodeFirst = "-1";
        if (inpAreaList != null && inpAreaList.size() > 0)
        {
            deptCodeFirst = inpAreaList.get(0).getDeptCode();
        }
        model.addAttribute("deptCodeFirst", deptCodeFirst);
        model.addAttribute("batchRuleList", yzMainService.qryBatchRuleEnabled(true));
        model.addAttribute("ruleList", yzMainService.qryGeneralRule(new HashMap<String, Object>()));*/

        List<MedicCategory> medicList = medicCategoryService.qryMedicCategory();
        model.addAttribute("medicList", medicList);

        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);
        model.addAttribute("SYN_YZ_DATA_MODE", AnalyResponMessage.SYN_YZ_DATA_MODE);
        model.addAttribute("batchRuleList", yzMainService.qryBatchRuleEnabled(true));
        model.addAttribute("ruleList", yzMainService.qryGeneralRule(new HashMap<String, Object>()));

        return "pivas/doctorAdvice/yzPArea";
    }

    /**
     * 药师审方首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzPCheckback")
    @RequiresPermissions(value = {"PIVAS_MENU_511"})
    public String yzPCheckback(Model model, HttpServletRequest request) throws Exception {
        List<MedicCategory> medicList = medicCategoryService.qryMedicCategory();
        model.addAttribute("medicList", medicList);

        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);
        model.addAttribute("SYN_YZ_DATA_MODE", AnalyResponMessage.SYN_YZ_DATA_MODE);

        return "pivas/doctorAdvice/yzPArea-back";
    }

    /**
     * 医嘱按照病区分组
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/groupNumByInpArea", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String groupNumByInpArea(@RequestParam Map<String, Object> param) throws Exception {
        List<Map<String, Object>> groupNumList = yzMainService.groupNumByInpArea(param);
        return new Gson().toJson(groupNumList);
    }

    /**
     * 医嘱 病人查询
     *
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qryPtPageByYZ", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String qryPtPageByYZ(@RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {
        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        if (StringUtils.isNotBlank(bednoStr)) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoS = bednoStr.split(",");
            param.replace("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        if (StringUtils.isNotBlank(patnameStr)) {
            patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
            String[] patnameS = patnameStr.split(",");
            param.replace("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        if (StringUtils.isNotBlank(parentNoStr)) {
            parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
            String[] parentNoS = parentNoStr.split(",");
            param.replace("parentNoS", parentNoS);
        }
        String freqCodeStr = StrUtil.getObjStr(param.get("freqCodeS"));
        if (StringUtils.isNotBlank(freqCodeStr)) {
            freqCodeStr = DefineStringUtil.escapeAllLike(freqCodeStr);
            String[] freqCodeS = freqCodeStr.split(",");
            param.replace("freqCodeS", freqCodeS);
        }
        String drugnameQryStrs = StrUtil.getObjStr(param.get("drugnameQry"));
        if (StringUtils.isNotBlank(drugnameQryStrs)) {
            drugnameQryStrs = DefineStringUtil.escapeAllLike(drugnameQryStrs);
            String[] drugnameQry = drugnameQryStrs.split(",");
            param.replace("drugnameQry", drugnameQry);
        }

        String inpatientString = StrUtil.getObjStr(param.get("inpatientString"));
        if (StringUtils.isNotBlank(inpatientString) || "-1".equals(inpatientString)) {
            String[] inpatientArray = inpatientString.split(",");
            param.replace("inpatientString", inpatientArray);
        } else {
            InpatientAreaBean areaBean = new InpatientAreaBean();
            areaBean.setEnabled("1");
            areaBean.setGlUserId(getCurrentUser().getUserId());
            List<InpatientAreaBean> inpAreaList =
                    inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());

            if (DefineCollectionUtil.isNotEmpty(inpAreaList)) {
                inpatientString = "";
                for (InpatientAreaBean bean : inpAreaList) {
                    inpatientString = inpatientString + "," + bean.getDeptCode();
                }
                String[] inpatientArray = inpatientString.substring(1, inpatientString.length()).split(",");
                param.replace("inpatientString", inpatientArray);
            }
        }

        String ydflStr = StrUtil.getObjStr(param.get("ydflStr"));
        if (StringUtils.isNotBlank(ydflStr)) {
            String[] ydflArray = ydflStr.split(",");
            param.replace("ydflStr", ydflArray);
        }

        String[] tpnArr = tnpStr.split(",");
        param.put("tpnArr", tpnArr);

        if (DataFormat.dateChange() && param.containsKey("sfrqSelect") && !param.get("sfrqSelect").toString().equals("today")) {
            param.remove("change");
            param.put("change", "1");
        }

        // 如果为不通过有药单，不判断医嘱状态
        if (param.containsKey("state") && StringUtils.isNotEmpty(param.get("state").toString()) && (param.get("state").toString().equals("checkNOHasYD") || param.get("state").toString().equals("mismatchYD"))) {
            param.remove("yzztLowN");
            param.put("yzztLowN ", null);
        }
        JqueryStylePagingResults<Map<String, Object>> patPage = yzMainService.qryPagePatByMap(param, new JqueryStylePaging());

        return new Gson().toJson(patPage);
    }

    /**
     * 医嘱详细查询方法
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzRowInfo", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String yzRowInfo(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        String pidsj = StrUtil.getObjStr(reqParam.get("pidsj"));
        if (pidsj != null) {
            DoctorAdviceMain yzMain = yzMainService.qryBeanByPidsj(pidsj, true);
            //List<YdPq> ydpqList = yzMainService.qryYdMoreByPidsj(pidsj);
            List<DoctorAdvice> yzList = yzService.qryBeanDistByPidsj(pidsj, null);
            model.addAttribute("yzMain", yzMain == null ? new DoctorAdviceMain() : yzMain);
            model.addAttribute("yzList", yzList == null ? new ArrayList<DoctorAdviceMain>() : yzList);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());
        return "pivas/doctorAdvice/yzRowInfo";
    }

    /**
     * 单个病人相关医嘱查询
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzListByPati", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String yzListByPatient(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        String inpatientNo = StrUtil.getObjStr(reqParam.get("inpatientNo"));
        if (inpatientNo != null) {
            Map<String, Object> yzQryMap = new HashMap<String, Object>();
            yzQryMap.put("leftWithPati", "Y");
            yzQryMap.put("inpatientNo", inpatientNo);
            yzQryMap.put("ydztLowN", 5);
            yzQryMap.put("yzztLowN", 1);
            List<DoctorAdviceMain> yzMain = yzMainService.qryListBeanByMap(yzQryMap, new JqueryStylePaging());
            List<DoctorAdvice> yzList = yzService.qryListBeanByMap(yzQryMap, new JqueryStylePaging());
            model.addAttribute("yzMainList", yzMain == null ? new ArrayList<DoctorAdviceMain>() : yzMain);
            model.addAttribute("yzList", yzList == null ? new ArrayList<DoctorAdvice>() : yzList);
            model.addAttribute("yzMainListSize", yzMain == null ? 0 : yzMain.size());

            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());
        return "pivas/doctorAdvice/yzRowInfo";
    }

    /***
     * 同步病人数据
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/synYz", produces = "application/json")
    @ResponseBody
    public String synYz() throws Exception {
        try {
            InpatientAreaBean inPatientArea = new InpatientAreaBean();
            inPatientArea.setEnabled("1");

            // 获取启动的病区
            List<InpatientAreaBean> inpAreaList =
                    inpAreaService.getInpatientAreaBeanList(inPatientArea, new JqueryStylePaging());

            if (DefineCollectionUtil.isNotEmpty(inpAreaList)) {
                StringBuffer deptCodes = new StringBuffer();
                for (InpatientAreaBean bean : inpAreaList) {
                    deptCodes.append(bean.getDeptCode()).append(",");
                }

                String condidtion = deptCodes.toString().substring(0, deptCodes.toString().length() - 1);

                // 调用数据同步接口
                JSONObject request = new JSONObject();

                JSONObject param = new JSONObject();

                param.put("dateType", ACTION_ADVICE);
                param.put("deptCode", condidtion);

                request.put("param", param.toString());
                request.put("type", "his");
                request.put("function", "getHisData");

                log.info("Call gethisdata interface,request: " + request.toString());

                JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);

                if (null == response || !SYN_SUCCESS.equals(response.get("result").toString())) {
                    log.info("Call gethisdata interface failure,respon: " + response);
                    throw new Exception("Call gethisdata interface failure, this url:"
                            + Propertiesconfiguration.getStringProperty("result.gethisdata"));
                }

                // 调用成功，解析响应，入表
                JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
                analyResponMessage.analyYZRespon(paramJsonObject);

                addOperLog(AmiLogConstant.MODULE_BRANCH.PM.Doctor,
                        AmiLogConstant.BRANCH_SYSTEM.PM,
                        getMessage("pivas.yz.syn"),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.PM.Doctor,
                    AmiLogConstant.BRANCH_SYSTEM.PM,
                    getMessage("pivas.yz.syn"),
                    false);
            throw e;
        }
    }

    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage) {
        this.analyResponMessage = analyResponMessage;
    }

    /**
     * 药单首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/ydMain")
    @RequiresPermissions(value = {"PIVAS_MENU_521"})
    public String ydMain(Model model, HttpServletRequest request) {
        List<Batch> batchList = batchService.queryBatchAllList();
        model.addAttribute("batchList", batchList);
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList =
                inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);

        return "pivas/doctorAdvice/ydMain";
    }

    /**
     * 药单列表查询
     *
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qryYdPage", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String qryYdPage(@RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {
        param.put("leftWithPB", 1);
        param.put("rucangOKNum", 1);
        param.put("chucangOKNum", 1);

        if (param.get("filter") != null && param.get("filter").toString().length() > 0) {
            String[] filterN = param.get("filter").toString().split(",");
            for (String filter : filterN) {
                if (filter != null && !filter.equals("") && !filter.equals("null")) {
                    param.put(filter, "1");
                }
            }
        }
        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        if (bednoStr != null) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoS = bednoStr.split(",");
            param.put("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        if (patnameStr != null) {
            patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
            String[] patnameS = patnameStr.split(",");
            param.put("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        if (parentNoStr != null) {
            parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
            String[] parentNoS = parentNoStr.split(",");
            param.put("parentNoS", parentNoS);
        }
        String wardNameStr = StrUtil.getObjStr(param.get("wardNameS"));
        if (wardNameStr != null) {
            wardNameStr = DefineStringUtil.escapeAllLike(wardNameStr);
            String[] wardNameS = wardNameStr.split(",");
            param.put("wardNameS", wardNameS);
        }
        String freqCodeStr = StrUtil.getObjStr(param.get("freqCodeS"));
        if (freqCodeStr != null) {
            freqCodeStr = DefineStringUtil.escapeAllLike(freqCodeStr);
            String[] freqCodeS = freqCodeStr.split(",");
            param.put("freqCodeS", freqCodeS);
        }
        String drugnameQryStrs = StrUtil.getObjStr(param.get("drugnameQry"));
        if (drugnameQryStrs != null) {
            drugnameQryStrs = DefineStringUtil.escapeAllLike(drugnameQryStrs);
            String[] drugnameQry = drugnameQryStrs.split(",");
            param.put("drugnameQry", drugnameQry);
        }
        String zxbcStr = StrUtil.getObjStr(param.get("zxbcS"));
        if (zxbcStr != null) {
            zxbcStr = DefineStringUtil.escapeAllLike(zxbcStr);
            String[] zxbcS = zxbcStr.split(",");
            param.put("zxbcS", zxbcS);
        }
        String areaS = StrUtil.getObjStr(param.get("areaS"));

        String ydStatus = StrUtil.getObjStr(param.get("ydStatus"));
        param.remove("areaS");
        if (areaS != null && areaS.length() > 0) {
            String[] areaN = areaS.split(",");
            param.put("areaN", areaN);
        } else {
            if (StringUtils.isNotBlank(ydStatus)) {
                User user = getCurrentUser();
                InpatientAreaBean areaBean = new InpatientAreaBean();
                areaBean.setEnabled("1");
                areaBean.setGlUserId(user.getUserId());
                List<InpatientAreaBean> inpAreaList =
                        inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
                ArrayList<String> inpatientArr = new ArrayList<>();
                for (InpatientAreaBean area : inpAreaList) {
                    inpatientArr.add(area.getDeptCode());
                }
                param.put("areaN", inpatientArr);
            }
        }

        String pcS = StrUtil.getObjStr(param.get("pcS"));
        param.remove("pcS");
        if (pcS != null) {
            List<Integer> pcN = new ArrayList<Integer>();

            Batch batch = null;
            if ("0".equals(pcS)) {
                batch = new Batch();
                batch.setIsEmptyBatch(1);
                batch.setEnabled(1);
                batch.setNum("0");
                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        pcN.add(Integer.valueOf(bean.getId().toString()));
                    }
                }
            } else if ("1".equals(pcS)) {
                batch = new Batch();
                batch.setIsEmptyBatch(0);
                batch.setEnabled(1);

                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        pcN.add(Integer.valueOf(bean.getId().toString()));
                    }
                }
            } else if ("-1".equals(pcS)) {
                batch = new Batch();
                batch.setIsEmptyBatch(1);
                batch.setEnabled(1);
                batch.setNum("-1");

                List<Batch> batchList = batchService.queryByPaging(new JqueryStylePaging(), batch);

                if (CollectionUtils.isNotEmpty(batchList)) {
                    for (Batch bean : batchList) {
                        pcN.add(Integer.valueOf(bean.getId().toString()));
                    }

                }
            } else {
                String[] pcSN = pcS.split(",");
                for (String pc : pcSN) {
                    if (pc != null && !pc.equals("")) {
                        pcN.add(Integer.parseInt(pc));
                    }
                }
            }

            if (pcN.size() > 0) {
                param.put("pcN", pcN);
            }
        }

        String inpatientString = (String) param.get("inpatientString");
        ArrayList<String> inpatientArr = new ArrayList<>();
        if (!"".equals(inpatientString) && inpatientString != null) {
            String[] inpatientArray = inpatientString.split(",");
            for (String v : inpatientArray) {
                inpatientArr.add(v);
            }
            param.replace("inpatientString", inpatientArr);
        }
        JqueryStylePagingResults<PrescriptionMain> pageResult = ydMainService.qryPageBeanByMap(param, jquryStylePaging);
        return new Gson().toJson(pageResult);
    }

    /**
     * 药单列表查询
     *
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydList1", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String ydList1(@RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {

        String bednoSStr = (String) param.get("bednoS");
        if (bednoSStr != null && bednoSStr.length() > 0) {
            String[] bednoS = bednoSStr.split(",");
            param.replace("bednoS", bednoS);
        }
        String patnameSStr = (String) param.get("patnameS");
        if (patnameSStr != null && patnameSStr.length() > 0) {
            String[] patnameS = patnameSStr.split(",");
            param.replace("patnameS", patnameS);
        }

        String parentNoSStr = (String) param.get("parentNoS");
        if (parentNoSStr != null && parentNoSStr.length() > 0) {
            String[] parentNoS = parentNoSStr.split(",");
            param.replace("parentNoS", parentNoS);
        }
        String freqCodeSStr = (String) param.get("freqCodeS");
        if (freqCodeSStr != null && freqCodeSStr.length() > 0) {
            String[] freqCodeS = freqCodeSStr.split(",");
            param.replace("freqCodeS", freqCodeS);
        }

        String inpatientString = (String) param.get("inpatientString");
        if (inpatientString != null && inpatientString.length() > 0) {
            String[] inpatientArray = inpatientString.split(",");
            param.replace("inpatientString", inpatientArray);
        }

        String medicamentType = (String) param.get("medicamentType");
        if (medicamentType != null && medicamentType.length() > 0) {
            String[] medicamentTypeArray = medicamentType.split(",");
            param.replace("medicamentType", medicamentTypeArray);
        }

        String batchId = (String) param.get("batchId");
        if (batchId != null && batchId.length() > 0) {
            String[] batchIdArray = batchId.split(",");
            param.replace("batchId", batchIdArray);
        }

        String drugnameQryStrs = StrUtil.getObjStr(param.get("drugnameQry"));
        if (StringUtils.isNotBlank(drugnameQryStrs)) {
            drugnameQryStrs = DefineStringUtil.escapeAllLike(drugnameQryStrs);
            String[] drugnameQry = drugnameQryStrs.split(",");
            param.replace("drugnameQry", drugnameQry);
        }

        JqueryStylePagingResults<PrescriptionMain> pageResult = ydMainService.ydConfirmGetYdCountList(param, jquryStylePaging);
        return new Gson().toJson(pageResult);
    }

    /**
     * 药单详情列表查询
     *
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydListInfol", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String ydListDetail(@RequestParam Map<String, Object> param, JqueryStylePaging jquryStylePaging) throws Exception {
        param.put("leftWithPB", 1);
        param.put("rucangOKNum", 1);
        param.put("chucangOKNum", 1);

        String bednoSStr = (String) param.get("bednoS");
        if (bednoSStr != null && bednoSStr.length() > 0) {
            String[] bednoS = bednoSStr.split(",");
            param.replace("bednoS", bednoS);
        }
        String patnameSStr = (String) param.get("patnameS");
        if (patnameSStr != null && patnameSStr.length() > 0) {
            String[] patnameS = patnameSStr.split(",");
            param.replace("patnameS", patnameS);
        }

        String parentNoSStr = (String) param.get("parentNoS");
        if (parentNoSStr != null && parentNoSStr.length() > 0) {
            String[] parentNoS = parentNoSStr.split(",");
            param.replace("parentNoS", parentNoS);
        }
        String freqCodeSStr = (String) param.get("freqCodeS");
        if (freqCodeSStr != null && freqCodeSStr.length() > 0) {
            String[] freqCodeS = freqCodeSStr.split(",");
            param.replace("freqCodeS", freqCodeS);
        }

        String inpatientString = (String) param.get("inpatientString");
        if (inpatientString != null && inpatientString.length() > 0) {
            String[] inpatientArray = inpatientString.split(",");
            param.replace("inpatientString", inpatientArray);
        }

        String medicamentType = (String) param.get("medicamentType");
        if (medicamentType != null && medicamentType.length() > 0) {
            String[] medicamentTypeArray = medicamentType.split(",");
            param.replace("medicamentType", medicamentTypeArray);
        }

        String batchId = (String) param.get("batchId");
        if (batchId != null && batchId.length() > 0) {
            String[] batchIdArray = batchId.split(",");
            param.replace("batchId", batchIdArray);
        }

        String inpatientNo = (String) param.get("inpatientNo");
        if (inpatientNo != null && inpatientNo.length() > 0) {
            String[] inpatientNoArray = inpatientNo.split(",");
            param.replace("inpatientNo", inpatientNoArray);
        }

        List<PrescriptionMain> ydList = ydMainService.ydConfirmGetYdList(param);
        return new Gson().toJson(ydList);
    }

    /**
     * 药单批次检查
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydPCCheck", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public Map<?, ?> ydPCCheck(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        //ydMainService.qryListBeanByMap(map, jquryStylePaging);
        Map<?, ?> result = MyMap.quickInitMap("code", 1, "msg", getMessage("comm.err.param"), "data", null);
        return result;
    }

    /**
     * 药单批次修改
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydPCSubmit", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public Map<?, ?> ydPCSubmit(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<?, ?> result = MyMap.quickInitMap("code", 1, "msg", getMessage("comm.err.param"), "data", null);
        if (reqParam.get("pcDataList") != null && reqParam.get("piId") != null) {
            Long piId = NumberUtil.getObjLong(reqParam.get("piId"));
            String pcDataListS = reqParam.get("pcDataList").toString();
            String oldPcIdListS = reqParam.get("oldPcIdListS") != null ? reqParam.get("oldPcIdListS").toString() : "";
            String oldPcNameS = reqParam.get("oldPcNameS") != null ? reqParam.get("oldPcNameS").toString() : "";
            String newPcNameS = reqParam.get("newPcNameS") != null ? reqParam.get("newPcNameS").toString() : "";
            String[] pcDataList = pcDataListS.split(";");
            String[] oldPcIdList = oldPcIdListS.split(";");
            String[] oldPcNameList = oldPcNameS.split(";");
            String[] newPcNameList = newPcNameS.split(";");
            //病区管理界面操作
            String from = reqParam.get("from") != null ? reqParam.get("from").toString() : "";

            User currentUser = (User) getCurrentUser();
            //判断用户类型，如果是护士,并且为修改批次界面病区管理则保存信息
            int userType = currentUser.getUserType();

            if (userType == 6 && "management".equals(from)) {

                String yyrq = reqParam.get("yyrq").toString();
                String yzlx = reqParam.get("yzlx").toString();

                Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                //int second = c.get(Calendar.SECOND);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date yyrqTime = sdf.parse(yyrq);
                Date sysTime = sdf.parse(sdf.format(new Date()));

                int value = sysTime.compareTo(yyrqTime);
                if (value == 0) {

                    String number = ydMainService.qryBCTimeBYId(reqParam.get("piId").toString());

                    int shortT = Integer.valueOf(shortTime);
                    int LongT = Integer.valueOf(longTime);

                    if ("0".equals(yzlx)) {
                        hour = hour + LongT;
                    } else {
                        hour = hour + shortT;
                    }

                    String[] numberArr = number.split(":");
                    int hourT = Integer.parseInt(numberArr[0]);
                    int minuteT = Integer.parseInt(numberArr[1]);

                    boolean flag = false;
                    if (hourT == hour && minuteT >= minute) {
                        flag = true;
                    } else if (hourT > hour) {
                        flag = true;
                    }

                    if (!flag) {
                        if ("0".equals(yzlx)) {
                            result = MyMap.quickInitMap("code", 1, "msg", "长嘱只能修改为当前时间" + LongT + "小时之后的批次", "data", null);
                        } else if ("1".equals(yzlx)) {
                            result = MyMap.quickInitMap("code", 1, "msg", "临嘱只能修改为当前时间" + shortT + "小时之后的批次", "data", null);
                        }
                        return result;
                    }
                }
            }

            if (pcDataList != null && pcDataList.length > 0) {

                Map<String, Object> mapYD = new HashMap<String, Object>();
                List<Map<String, Object>> listYD = new ArrayList<Map<String, Object>>();
                List<OprLog> oprLogList = new ArrayList<OprLog>();
                Map<String, Object> updateYD = null;
                String bottLab = null;
                Map<String, String> pidPC_bottLab = new HashMap<String, String>();
                String pidsj = null;
                String oldpcid = null;
                OprLog oprLog = null;

                for (int i = 0; i < pcDataList.length; i++) {
                    pidsj = pcDataList[i];
                    if (oldPcIdList != null && oldPcIdList.length > i) {
                        oldpcid = oldPcIdList[i];
                    } else {
                        oldpcid = "";
                    }
                    bottLab = UniqueNumberGenerator.generate(13);

                    updateYD = new HashMap<String, Object>();
                    updateYD.put("pidsj", pidsj);
                    updateYD.put("bottleLabelNum", bottLab);
                    updateYD.put("zxbc", piId);
                    /* if (reqParam.get("isSecendAdvice") != null && reqParam.get("isSecendAdvice").toString().equals("1"))
                     {
                         //2016-0910 取消用药日期变化情况
                         //updateYD.put("yyrq", c2.getTime());
                         //updateYD.put("pidRQ", DateUtil.getCurrentDay8Str());
                     }
                     else
                     {
                         //2016-0910 取消用药日期变化情况
                         //updateYD.put("yyrq", c1.getTime());
                         //updateYD.put("pidRQ", DateUtil.getCurrentDay8Str());
                     }*/

                    pidPC_bottLab.put(pidsj, bottLab);
                    listYD.add(updateYD);

                    oprLog = new OprLog();
                    oprLog.initYDLog(currentUser.getUserId(), currentUser.getAccount(), pidsj, oldpcid, "" + piId);
                    oprLogList.add(oprLog);
                }
                mapYD.put("listYD", listYD);
                try {
                    ydMainService.updateYDMainPiN(mapYD);
                    ydMainService.updateYDPiN(mapYD);

                    Map<String, Object> mapPQ = new HashMap<String, Object>();
                    List<Map<String, Object>> listPQ = new ArrayList<Map<String, Object>>();
                    Map<String, Object> updatePQ = null;
                    for (int i = 0; i < pcDataList.length; i++) {
                        pidsj = pcDataList[i];
                        updatePQ = new HashMap<String, Object>();
                        updatePQ.put("pidsj", pidsj);
                        updatePQ.put("ydpq", pidPC_bottLab.get(pidsj));
                        updatePQ.put("zxbc", piId);
                        /*if (reqParam.get("isSecendAdvice") != null
                            && reqParam.get("isSecendAdvice").toString().equals("1"))
                        {
                            //2016-0910 取消用药日期变化情况
                            //updatePQ.put("yyrq", c2.getTime());
                            //updatePQ.put("pidRQ", DateUtil.getCurrentDay8Str());
                        }
                        else
                        {
                            //2016-0910 取消用药日期变化情况
                            //updatePQ.put("yyrq", c1.getTime());
                            //updatePQ.put("pidRQ", DateUtil.getCurrentDay8Str());
                        }*/
                        listPQ.add(updatePQ);
                    }
                    mapPQ.put("listPQ", listPQ);
                    ydMainService.updatePQPiN(mapPQ);

                    yzMainService.addOprLogMany(oprLogList);

                    result = MyMap.quickInitMap("code", 0, "msg", getMessage("pivas.yz1.piciOk"), "data", null);

                    Map<String, Object> qryMap = new HashMap<String, Object>();
                    qryMap.put("pidsjN", pcDataList);
                    List<PrescriptionMain> ydMainList = ydMainService.qryListBeanByMap(qryMap, new JqueryStylePaging());

                    for (PrescriptionMain ydMain : ydMainList) {
                        printLabelService.createBottleLabel(ydMain, ydMain.getSfyscode());
                    }

                    for (int i = 0; i < pcDataList.length; i++) {
                        for (PrescriptionMain ydMain : ydMainList) {
                            if (ydMain.getPidsj().equals(pcDataList[i])) {
                                addOperLog(AmiLogConstant.MODULE_BRANCH.PM.ROW_BATCH_REORDER,
                                        AmiLogConstant.BRANCH_SYSTEM.PM,
                                        currentUser.getAccount() + " 调整 " + ydMain.getWardName() + " " + ydMain.getBedno()
                                                + " " + ydMain.getPatname() + " 批次，组号" + ydMain.getParentNo() + " 原批次："
                                                + oldPcNameList[i].trim() + " 新批次：" + newPcNameList[i].trim(),
                                        true);
                                //判断用户类型，如果是护士,并且为修改批次界面病区管理则保存信息
                                if (userType == 6 && "management".equals(from)) {
                                    String content =
                                            currentUser.getAccount() + " 调整 " + ydMain.getWardName() + " "
                                                    + ydMain.getBedno() + " " + ydMain.getPatname() + " 批次；" + " 原批次："
                                                    + oldPcNameList[i].trim() + " 新批次：" + newPcNameList[i].trim() + "；";
                                    ydMainService.delOperationLog(pcDataList[i]);
                                    ydMainService.saveOperationLog(pcDataList[i], content, currentUser.getAccount());
                                }

                                ydMainService.addTrackingRecord(ydMain.getPidsj(),
                                        trackingRecord.pctz,
                                        trackingRecord.pctzStr,
                                        currentUser.getAccount());
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    result = MyMap.quickInitMap("code", 1, "msg", getMessage("comm.err.param2"), "data", null);

                    for (String row : pcDataList) {
                        addOperLog(AmiLogConstant.MODULE_BRANCH.PM.ROW_BATCH_REORDER,
                                AmiLogConstant.BRANCH_SYSTEM.PM,
                                getMessage("log.yz.tip.pcSumit", new String[]{row}),
                                false);
                    }
                }

            }
        }

        return result;
    }

    /**
     * 批量修改批次
     *
     * @param reqParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchChanges", produces = "application/json")
    @ResponseBody
    public ResultData batchChanges(@RequestParam Map<String, Object> reqParam) throws Exception {

        String pidsj = reqParam.get("pidsj").toString().trim();
        String parentNo = reqParam.get("parentNo").toString().trim();
        String pcId = reqParam.get("pcId").toString().trim();
        String pcName = reqParam.get("pcName").toString().trim();

        if (StringUtils.isBlank(pidsj) || StringUtils.isBlank(pcId) || StringUtils.isBlank(parentNo)) {
            return DataFormat.formatFail("参数错误", null);
        }

        User currentUser = (User) getCurrentUser();
        String[] pidsjArray = pidsj.split(",");
        String[] parentNoArray = parentNo.split(",");

        ArrayList<String> resultPidsj = new ArrayList<>();
        ArrayList<String> oldPCName = new ArrayList<>();

        List<OprLog> oprLogList = new ArrayList<OprLog>();

        for (int i = 0; i < pidsjArray.length; i++) {
            String parentStr = parentNoArray[i];
            String pidsjStr = pidsjArray[i];
            BottleLabel pqBean = yzMainService.getPqInfo(parentStr, pidsjStr);
            if (pqBean == null) {
                continue;
            }

            Integer zxbc = pqBean.getZxbc();
            if (zxbc.intValue() == Integer.valueOf(pcId).intValue()) {
                continue;
            }

            pqBean.setZxbc(Integer.valueOf(pcId));

            int isExist = yzMainService.getPcIsExist(pqBean);

            if (isExist > 0) {
                continue;
            }

            String bottLab = UniqueNumberGenerator.generate(13);

            Map<String, Object> updateYD = new HashMap<String, Object>();
            updateYD.put("pidsj", pidsjStr);
            updateYD.put("bottleLabelNum", bottLab);
            updateYD.put("zxbc", pcId);
            updateYD.put("dybz", 1);
            ydMainService.updateYDMainSignPC(updateYD);
            ydMainService.updateYDSignPC(updateYD);


            Map<String, Object> updatePQ = new HashMap<String, Object>();
            updatePQ.put("pidsj", pidsjStr);
            updatePQ.put("ydpq", bottLab);
            updatePQ.put("zxbc", pcId);
            updatePQ.put("ydzt", 1);
            ydMainService.updatePQSignPC(updatePQ);

            OprLog oprLog = new OprLog();
            oprLog.initYDLog(currentUser.getUserId(), currentUser.getAccount(), pidsjStr, String.valueOf(zxbc), pcId);
            oprLogList.add(oprLog);

            resultPidsj.add(pidsjStr);

            String oldName = pqBean.getPcName();
            oldPCName.add(oldName);

        }

        if (resultPidsj.size() > 0) {
            yzMainService.addOprLogMany(oprLogList);

            Map<String, Object> qryMap = new HashMap<String, Object>();
            qryMap.put("pidsjN", resultPidsj);

            List<PrescriptionMain> ydMainList = ydMainService.qryListBeanByMap(qryMap, new JqueryStylePaging());

            for (PrescriptionMain ydMain : ydMainList) {
                printLabelService.createBottleLabel(ydMain, ydMain.getSfyscode());
            }

            for (int i = 0; i < resultPidsj.size(); i++) {
                for (PrescriptionMain ydMain : ydMainList) {
                    if (ydMain.getPidsj().equals(resultPidsj.get(i))) {
                        addOperLog(AmiLogConstant.MODULE_BRANCH.PM.ROW_BATCH_REORDER,
                                AmiLogConstant.BRANCH_SYSTEM.PM,
                                currentUser.getAccount() + " 调整 " + ydMain.getWardName() + " " + ydMain.getBedno() + " "
                                        + ydMain.getPatname() + " 批次，组号" + ydMain.getParentNo() + " 原批次："
                                        + oldPCName.get(i).trim() + " 新批次：" + pcName,
                                true);
                        ydMainService.addTrackingRecord(ydMain.getPidsj(),
                                trackingRecord.pctz,
                                trackingRecord.pctzStr,
                                currentUser.getAccount());
                    }
                }
            }

        }

        return DataFormat.formatSucc(resultPidsj);

    }


    /**
     * 药师自动审方
     *
     * @param inHospno
     * @param yzlx
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/autoApprovalByInHospno", produces = "application/json")
    @ResponseBody
    public String autoApprovalByInHospno(String inHospno, String yzlx) throws JSONException {
        List<Map<String, String>> autoApprovalReqList = new ArrayList<Map<String, String>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("inpatientNo", inHospno);
        map.put("ydztLowN", 5);
        map.put("yzztLowN", 1);
        map.put("areaEnabled", 1);
        map.put("yzlx", yzlx);
        if ("0".equals(yzlx)) {
            map.put("yzResource", 1);
        } else {
            map.put("yzResourceUpEQN", 2);
        }
        getYzMainList(autoApprovalReqList, map);

        return new Gson().toJson(autoApprovalReqList);
    }

    /**
     * 药师自动审方
     *
     * @param pidsj
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/autoApprovalByPidsj", produces = "application/json")
    @ResponseBody
    public String autoApprovalByPidsj(String pidsj) throws JSONException {
        List<Map<String, String>> autoApprovalReqList = new ArrayList<Map<String, String>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pidsj", pidsj);
        getYzMainList(autoApprovalReqList, map);

        return new Gson().toJson(autoApprovalReqList);
    }

    private void getYzMainList(List<Map<String, String>> autoApprovalReqList, Map<String, Object> map) {
        List<DoctorAdviceMain> yzMainList = yzMainService.qryListBeanByMap(map, new JqueryStylePaging());

        if (DefineCollectionUtil.isNotEmpty(yzMainList)) {
            Map<String, String> reqInfoMap = null;
            Safe safe = null;

            JaxbBinder jaxbBinder = new JaxbBinder(Safe.class);

            for (DoctorAdviceMain yzMain : yzMainList) {
                if (null != yzMain) {
                    reqInfoMap = new HashMap<String, String>();
                    reqInfoMap.put("id", yzMain.getPidsj());

                    // 设置大通请求
                    safe = setDTSystemReq(yzMain);
                    reqInfoMap.put("data",
                            jaxbBinder.toXml(safe, "utf-8")
                                    .replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", ""));
                    autoApprovalReqList.add(reqInfoMap);
                }

            }

        }
    }

    private Safe setDTSystemReq(DoctorAdviceMain yzMain) {
        // 获取当前用户
        User currentUser = (User) getCurrentUser();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd dd:mm:ss");
        SimpleDateFormat birthformat = new SimpleDateFormat("yyyy-MM-dd");

        Safe safe = new Safe();

        // 医生信息
        Doctor_information doctor_information = new Doctor_information();
        doctor_information.setJobNumber(currentUser.getAccount());
        doctor_information.setDate(format.format(new Date()));
        safe.setDoctor_information(doctor_information);

        safe.setDoctor_name((yzMain.getDoctorName() == null) ? "" : yzMain.getDoctorName());
        safe.setDepartment_code((yzMain.getWardCode() == null) ? "" : yzMain.getWardCode());
        safe.setDepartment_name((yzMain.getWardName() == null) ? "" : yzMain.getWardName());
        safe.setCase_id((yzMain.getCaseId() == null) ? "" : yzMain.getCaseId());
        safe.setInhos_code((yzMain.getInpatientNo() == null) ? "" : yzMain.getInpatientNo());
        safe.setBed_no((yzMain.getBedno() == null) ? "" : yzMain.getBedno());

        // 病人信息
        Patient_information patient_information = new Patient_information();
        patient_information.setBirth((yzMain.getBirthday() == null) ? "" : birthformat.format(yzMain.getBirthday()));
        patient_information.setPatient_name((yzMain.getPatname() == null) ? "" : yzMain.getPatname());
        patient_information.setPatient_sex((yzMain.getSex() == 1) ? "男" : "女");

        Allergic_history allergic_history = new Allergic_history();
        allergic_history.setCaseListDefault();
        patient_information.setAllergic_history(allergic_history);

        Diagnoses diagnoses = new Diagnoses();
        List<Diagnose> diagnoseList = new ArrayList<Diagnose>();
        diagnoseList.add(new Diagnose());
        diagnoseList.add(new Diagnose());
        diagnoseList.add(new Diagnose());
        diagnoses.setDiagnose(diagnoseList);
        patient_information.setDiagnoses(diagnoses);
        safe.setPatient_information(patient_information);

        Prescriptions prescriptions = new Prescriptions();

        List<com.zc.pivas.docteradvice.dtsystem.message.Prescription> prescriptionList = new ArrayList<com.zc.pivas.docteradvice.dtsystem.message.Prescription>();

        String[] actOrderNos = yzMain.getActOrderNo().split(",");
        com.zc.pivas.docteradvice.dtsystem.message.Prescription prescription = null;
        Medicine medicine = null;
        Single_dose single_dose = null;

        String general_name = "";
        int i = 0;
        for (String actOrderNo : actOrderNos) {
            prescription = new com.zc.pivas.docteradvice.dtsystem.message.Prescription();
            prescription.setId(actOrderNo);
            prescription.setType((yzMain.getYzlx() == 0) ? "L" : "T");

            medicine = new Medicine();
            medicine.setGroup_number(String.valueOf(yzMain.getParentNo()));

            general_name =
                    yzMain.getDrugname().split("@@")[i].split(" ")[0].replaceAll("<", "(")
                            .replaceAll(">", ")")
                            .replaceAll("◎", "")
                            .replaceAll("&", "");
            medicine.setGeneral_name(general_name);

            medicine.setLicense_number(yzMain.getChargeCode().split("@@")[i]);

            medicine.setMedicine_name(general_name);

            single_dose = new Single_dose();
            single_dose.setDose(yzMain.getDose().split("@@")[i]);
            medicine.setSingle_dose(single_dose);

            medicine.setFrequency((yzMain.getFreqCode() == null) ? "" : yzMain.getFreqCode());
            medicine.setUnit(yzMain.getDoseUnit().split("@@")[i]);
            medicine.setAdminister_drugs((yzMain.getSupplyCode() == null) ? "" : yzMain.getSupplyCode());
            medicine.setBegin_time(format.format(yzMain.getStartTime()));

            medicine.setEnd_time("");
            if (null != yzMain.getEndTime()) {
                medicine.setEnd_time(format.format(yzMain.getEndTime()));
            }

            medicine.setPrescription_time(yzMain.getZxrq() + " 00:00:00");
            prescription.setMedicine(medicine);
            prescriptionList.add(prescription);

            i++;
        }

        prescriptions.setPrescription(prescriptionList);
        safe.setPrescriptions(prescriptions);
        return safe;
    }

    @RequestMapping(value = "/updateAutoCheck", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public Map<?, ?> updateAutoCheck(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<?, ?> result = MyMap.quickInitMap("code", 1, "msg", getMessage("comm.err.param"), "data", null);
        String pidsjNS = reqParam.get("pidsjN") == null ? null : reqParam.get("pidsjN").toString();
        String yzzdshztNS = reqParam.get("yzzdshztN") == null ? null : reqParam.get("yzzdshztN").toString();
        if (yzzdshztNS != null && pidsjNS != null) {
            String[] pidsjN = pidsjNS.split(",");
            String[] yzzdshztN = yzzdshztNS.split(",");
            if (pidsjN.length == yzzdshztN.length) {
                List<Map<String, Object>> updList = new ArrayList<Map<String, Object>>();
                Map<String, Object> udpRow = null;
                for (int i = 0; i < pidsjN.length; i++) {
                    if (pidsjN[i] != null) {
                        udpRow = new HashMap<String, Object>();
                        udpRow.put("pidsj", pidsjN[i]);
                        udpRow.put("yzzdshzt", yzzdshztN[i] == null ? 0 : Integer.parseInt(yzzdshztN[i]));
                        updList.add(udpRow);
                    }
                }
                if (updList.size() > 0) {
                    Map<String, Object> udp = new HashMap<String, Object>();
                    udp.put("dataList", updList);
                    yzMainService.updateYZAutoCheckN(udp);
                    yzMainService.updateYZMainAutoCheckN(udp);
                    result = MyMap.quickInitMap("code", 0, "msg", "更新成功", "data", null);
                }
            }
        }
        return result;
    }

    /**
     * 药师审方新页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzPCheck2")
    @RequiresPermissions(value = {"PIVAS_MENU_514"})
    public String yzPArea2(Model model, HttpServletRequest request) throws Exception {
        /*InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList =
            inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);*/
        /*String deptCodeFirst = "-1";
        if (inpAreaList != null && inpAreaList.size() > 0)
        {
            deptCodeFirst = inpAreaList.get(0).getDeptCode();
        }
        model.addAttribute("deptCodeFirst", deptCodeFirst);*/
        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);
        model.addAttribute("batchRuleList", yzMainService.qryBatchRuleEnabled(true));
        model.addAttribute("ruleList", yzMainService.qryGeneralRule(new HashMap<String, Object>()));
        model.addAttribute("SYN_YZ_DATA_MODE", AnalyResponMessage.SYN_YZ_DATA_MODE);

        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());

        List<MedicCategory> medicList = medicCategoryService.qryMedicCategory();
        model.addAttribute("medicList", medicList);

        return "pivas/doctorAdvice/yzPArea2";
    }

    /**
     * 药师审方新页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yzPCheck2back")
    @RequiresPermissions(value = {"PIVAS_MENU_514"})
    public String yzPAreaback(Model model)
            throws Exception {
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList =
                inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);
        String deptCodeFirst = "-1";
        if (inpAreaList != null && inpAreaList.size() > 0) {
            deptCodeFirst = inpAreaList.get(0).getDeptCode();
        }
        model.addAttribute("deptCodeFirst", deptCodeFirst);
        model.addAttribute("batchRuleList", yzMainService.qryBatchRuleEnabled(true));
        model.addAttribute("ruleList", yzMainService.qryGeneralRule(new HashMap<String, Object>()));
        model.addAttribute("SYN_YZ_DATA_MODE", AnalyResponMessage.SYN_YZ_DATA_MODE);

        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());

        return "pivas/doctorAdvice/yzPArea2-back";
    }

    /**
     * 批次管理首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchManage")
    @RequiresPermissions(value = {"PIVAS_MENU_628"
    })
    public String batchManage(Model model, HttpServletRequest request) throws Exception {
        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);

        List<Batch> batchList = batchService.queryBatchAllList();

        model.addAttribute("batchList", batchList);

        Boolean flag = DataFormat.dateChange();

        if (flag) {
            model.addAttribute("day", "tomorrow");
        } else {
            model.addAttribute("day", "today");
        }

        return "pivas/doctorAdvice/batchManage";
    }

    /**
     * 输液单管理首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/infusionSheet")
    @RequiresPermissions(value = {"PIVAS_MENU_962"})
    public String ydStatusMain(Model model) throws Exception {
        User user = getCurrentUser();
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        areaBean.setGlUserId(user.getUserId());
        List<InpatientAreaBean> inpAreaList = inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        String deptCodeFirst = "-1";
        if (inpAreaList != null && inpAreaList.size() > 0) {
            deptCodeFirst = inpAreaList.get(0).getDeptCode();
        }

        List<Batch> batchList = batchService.queryBatchAllList();

        model.addAttribute("inpAreaList", inpAreaList);
        model.addAttribute("deptCodeFirst", deptCodeFirst);
        model.addAttribute("batchList", batchList);
        return "pivas/doctorAdvice/infusionSheet";
    }

    /**
     * 药单根据病区分组
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/groupAreaByMap", produces = "application/json")
    @ResponseBody
    public ResultData groupAreaByMap(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        List<Map<String, Object>> groupAreaList = ydMainService.groupAreaByMap(reqParam);
        return DataFormat.formatSucc(groupAreaList);
    }

    /**
     * 药单详情
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/prescriptionDetail", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public ResultData prescriptionDetail(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        PrescriptionMain ydMain = null;
        String pidsj = StrUtil.getObjStr(reqParam.get("pidsj"));
        if (pidsj != null) {
            reqParam.put("leftWithPB", "1");
            reqParam.put("pq_ydzt", "1");
            reqParam.put("ydHistory", "1");

            List<PrescriptionMain> ydMainList = ydMainService.qryListBeanByMap(reqParam, new JqueryStylePaging());
            if (ydMainList != null && ydMainList.size() > 0) {
                ydMain = ydMainList.get(0);
                List<Prescription> ydList = ydMainService.qryYDListByMap(reqParam);
                String medNames = "";
                if (ydList != null && ydList.size() > 0) {
                    Map<String, Integer> medMap = new HashMap<String, Integer>();
                    for (Prescription yd : ydList) {
                        if (yd.getCategoryName() != null && !"null".equals(yd.getCategoryName())
                                && medMap.get(yd.getCategoryName()) == null) {
                            if (medNames.equals("")) {
                                medNames = yd.getCategoryName();
                            } else {
                                medNames = medNames + "," + yd.getCategoryName();
                            }
                            medMap.put(yd.getCategoryName(), 1);
                        }
                    }
                }
                result.put("ydMain", ydMain);
                result.put("ydList", ydList == null ? new ArrayList<Prescription>() : ydList);
                result.put("medNames", medNames);
            }

            //查询追踪记录
            List<TrackingRecord> recordList = ydMainService.getTrackingRecord(pidsj);
            result.put("recordList", recordList);

        }
        return DataFormat.formatAdd(ydMain != null ? 1 : 0, ydMain != null ? "药单查询成功" : "药单查询失败", result);
    }

    /**
     * 查询用户相关联病区
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qryAreaByUser", produces = "application/json")
    @ResponseBody
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public ResultData qryAreaByUser(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        PrescriptionMain ydMain = null;
        String pidsj = StrUtil.getObjStr(reqParam.get("pidsj"));
        if (pidsj != null) {
            reqParam.put("leftWithPB", "1");
            reqParam.put("pq_ydzt", "1");
            List<PrescriptionMain> ydMainList = ydMainService.qryListBeanByMap(reqParam, new JqueryStylePaging());
            if (ydMainList != null && ydMainList.size() > 0) {
                ydMain = ydMainList.get(0);
                List<Prescription> ydList = ydMainService.qryYDListByMap(reqParam);
                String medNames = "";
                if (ydList != null && ydList.size() > 0) {
                    Map<String, Integer> medMap = new HashMap<String, Integer>();
                    for (Prescription yd : ydList) {
                        if (yd.getCategoryName() != null && !"null".equals(yd.getCategoryName())
                                && medMap.get(yd.getCategoryName()) == null) {
                            if (medNames.equals("")) {
                                medNames = yd.getCategoryName();
                            } else {
                                medNames = medNames + "," + yd.getCategoryName();
                            }
                            medMap.put(yd.getCategoryName(), 1);
                        }
                    }
                }
                result.put("ydMain", ydMain);
                result.put("ydList", ydList == null ? new ArrayList<Prescription>() : ydList);
                result.put("medNames", medNames);
            }
        }
        return DataFormat.formatAdd(ydMain != null ? 1 : 0, ydMain != null ? "药单查询成功" : "药单查询失败", result);
    }

    /**
     * 批次优化页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchOptn")
    @RequiresPermissions(value = {"PIVAS_MENU_517"})
    public String batchOptn(Model model, HttpServletRequest request) {
        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);
        List<Batch> batchList = batchService.queryBatchAllList();
        model.addAttribute("batchList", batchList);
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");

        List<MedicCategory> medicamentTypeList = medicCategoryDao.queryAllMedicCategory();
        model.addAttribute("medicamentTypeList", medicamentTypeList);

        List<InpatientAreaBean> inpAreaList =
                inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        Boolean dateChange = DataFormat.dateChange();
        model.addAttribute("dateChange", dateChange);
        return "pivas/doctorAdvice/batchOptn";
    }

    /**
     * 批次优化
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydRuleRun", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_518"})
    public ResultData ydRuleRun(@RequestParam Map<String, Object> param) throws Exception {
        if (param.get("yyrq") == null) {
            return DataFormat.formatFail("参数缺失，无法处理");
        }
        boolean reorder = new BatchUtil().reorder(false, param);

        addOperLog(AmiLogConstant.MODULE_BRANCH.PM.ROW_BATCH_REORDER,
                AmiLogConstant.BRANCH_SYSTEM.PM,
                "执行批次优化",
                reorder);
        return DataFormat.formatAdd(reorder ? 1 : 0);
    }

    /**
     * 批次确认
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydRuleConfirm", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_518"})
    public ResultData ydRuleConfirm(@RequestParam Map<String, Object> param) throws Exception {
        if (param.get("yyrq") == null) {
            return DataFormat.formatFail("参数缺失，无法处理");
        }
        /* new BatchUtil().reorder(true, param);*/
        User user = getCurrentUser();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> qryMap = new HashMap<String, Object>();

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

        map.put("yzconfig_uid", user.getUserId());
        map.put("yzconfig_uname", user.getAccount());
        map.put("yyrq", param.get("yyrq"));
        map.put("inpatientString", param.get("inpatientString"));
        map.put("inpatientNo", param.get("inpatientNo"));

        List<String> tempInpatientNo = new ArrayList<String>();
        int row = -1;
        if (map.get("inpatientNo") instanceof String[]) {
            String[] inpatientNoArray = (String[]) map.get("inpatientNo");
            for (int i = 0; i < inpatientNoArray.length; i++) {
                if (i != 0 && i % 1000 == 0) {
                    map.replace("inpatientNo", tempInpatientNo);
                    row = ydMainService.updateYDReOrderStatus(map);
                    tempInpatientNo.clear();
                }
                tempInpatientNo.add(inpatientNoArray[i]);
            }
            map.replace("inpatientNo", tempInpatientNo);
        }
        row = ydMainService.updateYDReOrderStatus(map);

        qryMap.put("yyrq", param.get("yyrq"));
        if (param.get("inpatientNo").toString().length() == 0) {
            qryMap.put("inpatientString", param.get("inpatientString"));
        } else {
            qryMap.put("inpatientNoArray", param.get("inpatientNo"));
        }

        tempInpatientNo.clear();
        List<PrescriptionMain> ydMainList = new ArrayList<PrescriptionMain>();
        if (qryMap.get("inpatientNoArray") != null && qryMap.get("inpatientNoArray") instanceof String[]) {
            String[] inpatientNoArray = (String[]) qryMap.get("inpatientNoArray");
            for (int i = 0; i < inpatientNoArray.length; i++) {
                if (i != 0 && i % 1000 == 0) {
                    qryMap.replace("inpatientNoArray", tempInpatientNo);
                    ydMainList.addAll(ydMainService.qryListBeanByMap(qryMap, new JqueryStylePaging()));
                    tempInpatientNo.clear();
                }
                tempInpatientNo.add(inpatientNoArray[i]);
            }
            qryMap.replace("inpatientNoArray", tempInpatientNo);
        }
        ydMainList.addAll(ydMainService.qryListBeanByMap(qryMap, new JqueryStylePaging()));
        for (PrescriptionMain ydMain : ydMainList) {
            ydMainService.addTrackingRecord(ydMain.getPidsj(),
                    trackingRecord.pcpd,
                    trackingRecord.pcpdStr,
                    getCurrentUser().getAccount());
            Integer ydreorderCode = ydMain.getYdreorderCode();
            if (ydreorderCode == SysConstant.ydreorderCode.i22HasChangeSucc
                    || ydreorderCode == SysConstant.ydreorderCode.i12HasChangeErr) {
                printLabelService.createBottleLabel(ydMain, ydMain.getSfyscode());
            }
        }
        addOperLog(AmiLogConstant.MODULE_BRANCH.PM.ROW_BATCH_CONFIG,
                AmiLogConstant.BRANCH_SYSTEM.PM,
                "批次确认",
                row > -1 ? true : false);
        return DataFormat.formatAdd(row > -1 ? 1 : 0);
    }

    /**
     * 医嘱预审核确认
     *
     * @param reqParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/yshCfm", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_519"})
    public ResultData yshCfm(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        String pidsjSSucc = reqParam.get("pidsjNSucc") != null ? reqParam.get("pidsjNSucc").toString() : "";
        String pidsjSFail = reqParam.get("pidsjNFail") != null ? reqParam.get("pidsjNFail").toString() : "";
        String[] pidsjNSucc = pidsjSSucc.split(",");
        String[] pidsjNFail = pidsjSFail.split(",");
        List<String> pidsjNAll = new ArrayList<String>();
        if (pidsjNSucc.length > 0) {
            for (String pidsj : pidsjNSucc) {
                if (pidsj != null && !pidsj.equals("")) {
                    pidsjNAll.add(pidsj);
                }
            }
            for (String pidsj : pidsjNFail) {
                if (pidsj != null && !pidsj.equals("")) {
                    pidsjNAll.add(pidsj);
                }
            }
        }
        int row = -1;
        if (pidsjNAll.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pidsjN", pidsjNAll);
            row = yzMainService.updateYZAndMainSHZTByYSH(map);
            if (pidsjSSucc.length() > 0) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("yzParea", "1");
                map2.put("pidsjN", pidsjSSucc);
                map2.put("repeatCheck", "Y");
                map2.put("newYzshzt", 1);
                //map2.put("yzshbtglx", "");
                map2.put("checkType", "yz");
                //map2.put("yzshbtgyy", "");
                Map<?, ?> result = checkMany(map2, model);
            }
        }
        return DataFormat.formatAdd(row > 0 ? 1 : 0, row > -1 ? "审方确认成功" : "无待确认的医嘱");
    }

    /**
     * 签收
     *
     * @param pqStr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signFor")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_964"})
    public ResultData signFor(String pqStr) throws Exception {

        User user = getCurrentUser();

        if (StringUtils.isBlank(pqStr)) {
            log.error("签收功能获取瓶签号失败");
            return DataFormat.formatFail("数据错误", null);
        }

        List<BLabelWithPrescription> pqList = ydMainService.qryPQList(pqStr);
        if (pqList == null || pqList.size() == 0) {
            log.error("签收功能获取瓶签号失败");
            return DataFormat.formatFail("瓶签号[" + pqStr + "]不存在", null);
        }

        String pidsj = pqList.get(0).getYd_pidsj();

        int ydzxzt = pqList.get(0).getYd_ydzxzt();
        if (ydzxzt == 1) {
            return DataFormat.formatFail("药单已停止", null);
        } else if (ydzxzt == 2) {
            return DataFormat.formatFail("药单已撤销", null);
        }

        int ydzt = pqList.get(0).getPq_ydzt();
        if (ydzt <= 6) {
            return DataFormat.formatFail("瓶签号[" + pqStr + "]还未出仓", null);
        }

        //更新瓶签状态为已签收
        ydMainService.updatePQStatus(pqStr);
        //药单追踪记录
        ydMainService.addTrackingRecord(pidsj, trackingRecord.qsqr, trackingRecord.qsqrStr, user.getAccount());

        return DataFormat.formatSucc();
    }

    /**
     * 批量签收
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signForAll")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_964"})
    public ResultData signForAll(@RequestParam Map<String, Object> param) throws Exception {
        User user = getCurrentUser();

        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        if (bednoStr != null) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoS = bednoStr.split(",");
            param.put("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        if (patnameStr != null) {
            patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
            String[] patnameS = patnameStr.split(",");
            param.put("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        if (parentNoStr != null) {
            parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
            String[] parentNoS = parentNoStr.split(",");
            param.put("parentNoS", parentNoS);
        }

        String freqCodeStr = StrUtil.getObjStr(param.get("freqCodeS"));
        if (freqCodeStr != null) {
            freqCodeStr = DefineStringUtil.escapeAllLike(freqCodeStr);
            String[] freqCodeS = freqCodeStr.split(",");
            param.put("freqCodeS", freqCodeS);
        }
        String drugnameQryStr = StrUtil.getObjStr(param.get("drugnameQry"));
        if (drugnameQryStr != null) {
            String[] drugnameQry = drugnameQryStr.split(",");
            param.put("drugnameQry", drugnameQry);
        }

        String pcStr = StrUtil.getObjStr(param.get("pcS"));
        if (pcStr != null && StringUtils.isNotBlank(pcStr)) {
            String[] pcS = pcStr.split(",");
            param.put("pcS", pcS);
        }

        String areaStr = StrUtil.getObjStr(param.get("areaS"));
        if (areaStr != null && StringUtils.isNotBlank(areaStr)) {
            String[] areaS = areaStr.split(",");
            param.put("areaS", areaS);
        } else {
            InpatientAreaBean areaBean = new InpatientAreaBean();
            areaBean.setEnabled("1");
            areaBean.setGlUserId(user.getUserId());
            List<InpatientAreaBean> inpAreaList =
                    inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
            ArrayList<String> inpatientArr = new ArrayList<>();
            for (InpatientAreaBean area : inpAreaList) {
                inpatientArr.add(area.getDeptCode());
            }
            param.put("areaN", inpatientArr);

        }

        List<BLabelWithPrescription> ydMainList = ydMainService.getYDMainList(param);

        for (BLabelWithPrescription yd : ydMainList) {

            String pidsj = yd.getYd_pidsj();
            String pqStr = yd.getYd_bottleLabelNum();

            ydMainService.updatePQStatus(pqStr);
            ydMainService.addTrackingRecord(pidsj, trackingRecord.qsqr, trackingRecord.qsqrStr, user.getAccount());

        }
        int num = ydMainList.size();

        return DataFormat.formatSucc("共有" + String.valueOf(num) + "个瓶签已签收", null);
    }

    /**
     * 医嘱详情页
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ydInfo", produces = "application/json")
    // @RequiresPermissions(value = {"PIVAS_BTN_503"})
    public String ydInfo(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        String pidsj = StrUtil.getObjStr(reqParam.get("pidsj"));
        if (pidsj != null) {
            reqParam.put("ydHistory", 1);
            List<Prescription> ydList = ydMainService.qryYDListByMap(reqParam);
            List<TrackingRecord> trackingRecordList = ydMainService.getTrackingRecord(pidsj);
            // 查看药单跟踪状态

            if (DefineCollectionUtil.isNotEmpty(ydList)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("parentNo", ydList.get(0).getParentNo());
                DoctorAdviceMain yzMain = yzMainService.getYzByCondition(map);

                Map<String, Integer> medMap = new HashMap<String, Integer>();
                String medNames = "";

                for (Prescription yd : ydList) {
                    if (yd.getCategoryName() != null && !"null".equals(yd.getCategoryName())
                            && medMap.get(yd.getCategoryName()) == null) {
                        if (medNames.equals("")) {
                            medNames = yd.getCategoryName();
                        } else {
                            medNames = medNames + "," + yd.getCategoryName();
                        }
                        medMap.put(yd.getCategoryName(), 1);
                    }
                }

                model.addAttribute("medNames", medNames);
                model.addAttribute("yzMain", yzMain);
                model.addAttribute("ydList", ydList == null ? new ArrayList<Prescription>() : ydList);
                model.addAttribute("trackingRecordList", trackingRecordList == null ? new ArrayList<TrackingRecord>()
                        : trackingRecordList);
                model.addAttribute("success", true);
            } else {
                model.addAttribute("success", false);
            }
        } else {
            model.addAttribute("success", false);
        }
        return "pivas/doctorAdvice/ydInfo";
    }

    /**
     * 医嘱管理首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doctorAdviceMgr")
    @RequiresPermissions(value = {"PIVAS_MENU_963"})
    public String doctorAdviceMgr(Model model)
            throws Exception {
        User user = getCurrentUser();
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        areaBean.setGlUserId(user.getUserId());
        List<InpatientAreaBean> inpAreaList =
                inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        String deptCodeFirst = "-1";
        if (inpAreaList != null && inpAreaList.size() > 0) {
            deptCodeFirst = inpAreaList.get(0).getDeptCode();
        }

        model.addAttribute("inpAreaList", inpAreaList);
        model.addAttribute("deptCodeFirst", deptCodeFirst);
        model.addAttribute("errTypeList", yzMainService.getErrorTypeList());
        return "pivas/doctorAdvice/doctorAdviceMgr";
    }

    @RequestMapping(value = "/fonticon")
    public String fontIcon(){

            return "pivas/doctorAdvice/fonticon";
    }
    /**
     * 不合理医嘱复核
     *
     * @param reqParam
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toReview", produces = "application/json")
    @ResponseBody
    public Map<?, ?> toReview(@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
        Map<?, ?> result = MyMap.quickInitMap("code", 0, "msg", "复核成功", "data", null);

        String pidsj = String.valueOf(reqParam.get("pidsj"));
        String yzshbtgyy = String.valueOf(reqParam.get("yzshbtgyy"));

        Map<String, Object> updateMap = new HashMap<String, Object>();

        updateMap.put("yzshbtgyy", yzshbtgyy);
        updateMap.put("recheckuser", getCurrentUser().getAccount());
        updateMap.put("pidsj", pidsj);
        updateMap.put("recheckstate", 0);
        yzMainService.updateYZ(updateMap);

        return result;
    }

    /**
     * 批量退费
     *
     * @param reqParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doMultTF", produces = "application/json")
    @ResponseBody
    public ResultData doMultipleTF(@RequestParam Map<String, Object> reqParam) throws Exception {
        String pidsj = reqParam.get("pidsj").toString().trim();

        User currentUser = (User) getCurrentUser();
        String[] pidsjArray = pidsj.split(",");

        ArrayList<String> resultPidsj = new ArrayList<>();
        ArrayList<String> oldPCName = new ArrayList<>();

        List<OprLog> oprLogList = new ArrayList<OprLog>();

        Batch batch = null;
        String pcId = "";
        String pcName = "";

        for (int i = 0; i < pidsjArray.length; i++) {
            batch = new Batch();
            String pidsjStr = pidsjArray[i];
            BottleLabel pqBean = yzMainService.getPqInfo(null, pidsjStr);
            if (pqBean == null) {
                continue;
            }
            // 获取原始药单状态
            pcName = pqBean.getPcName();

            if (pcName.contains(RETUFN_PREFIX)) {
                continue;
            }
            // 获取对应的退费批次
            batch.setName(RETUFN_PREFIX + pqBean.getPcName());

            pcId = ydMainService.qryBatchCode(RETUFN_PREFIX + pqBean.getPcNum());

            String bottLab = UniqueNumberGenerator.generate(13);

            Map<String, Object> updateYD = new HashMap<String, Object>();
            updateYD.put("pidsj", pidsjStr);
            updateYD.put("bottleLabelNum", bottLab);
            updateYD.put("zxbc", pcId);
            updateYD.put("dybz", 1);
            ydMainService.updateYDMainSignPC(updateYD);
            ydMainService.updateYDSignPC(updateYD);

            Map<String, Object> updatePQ = new HashMap<String, Object>();
            updatePQ.put("pidsj", pidsjStr);
            updatePQ.put("ydpq", bottLab);
            updatePQ.put("zxbc", pcId);
            updatePQ.put("ydzt", 1);
            ydMainService.updatePQSignPC(updatePQ);

            OprLog oprLog = new OprLog();
            oprLog.initYDLog(currentUser.getUserId(), currentUser.getAccount(), pidsjStr, String.valueOf(pqBean.getZxbc()), pcId);
            oprLogList.add(oprLog);

            resultPidsj.add(pidsjStr);

            String oldName = pqBean.getPcName();
            oldPCName.add(oldName);
        }

        if (resultPidsj.size() > 0) {
            yzMainService.addOprLogMany(oprLogList);

            Map<String, Object> qryMap = new HashMap<String, Object>();
            qryMap.put("pidsjN", resultPidsj);

            List<PrescriptionMain> ydMainList = ydMainService.qryListBeanByMap(qryMap, new JqueryStylePaging());

            for (PrescriptionMain ydMain : ydMainList) {
                printLabelService.createBottleLabel(ydMain, ydMain.getSfyscode());
            }

            for (int i = 0; i < resultPidsj.size(); i++) {
                for (PrescriptionMain ydMain : ydMainList) {
                    if (ydMain.getPidsj().equals(resultPidsj.get(i))) {
                        addOperLog(AmiLogConstant.MODULE_BRANCH.PM.ROW_BATCH_REORDER,
                                AmiLogConstant.BRANCH_SYSTEM.PM,
                                currentUser.getAccount() + " 调整 " + ydMain.getWardName() + " " + ydMain.getBedno() + " "
                                        + ydMain.getPatname() + " 批次，组号" + ydMain.getParentNo() + " 原批次："
                                        + oldPCName.get(i).trim() + " 新批次：" + pcName,
                                true);
                        ydMainService.addTrackingRecord(ydMain.getPidsj(),
                                trackingRecord.pctz,
                                trackingRecord.pctzStr,
                                currentUser.getAccount());
                    }
                }
            }

        }

        return DataFormat.formatSucc(resultPidsj);
    }


    /**
     * 批量退费确认
     *
     * @param reqParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doMultTFYdCfm", produces = "application/json")
    @ResponseBody
    public ResultData doMultTFYdCfm(@RequestParam Map<String, Object> reqParam) throws Exception {
        String pidsj = reqParam.get("pidsj").toString().trim();

        User currentUser = (User) getCurrentUser();
        String[] pidsjArray = pidsj.split(",");

        ArrayList<String> resultPidsj = new ArrayList<>();

        Map<String, Object> updatePQ = null;
        String pidsjStr = "";
        for (int i = 0; i < pidsjArray.length; i++) {
            updatePQ = new HashMap<String, Object>();

            pidsjStr = pidsjArray[i];

            updatePQ.put("pidsj", pidsjStr);
            updatePQ.put("tfAccount", currentUser.getAccount());
            updatePQ.put("ydzt", 1);
            ydMainService.updatePQSignPC(updatePQ);
        }

        return DataFormat.formatSucc(resultPidsj);

    }
}
