package com.zc.pivas.medicaments.controller;

import com.google.gson.Gson;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.common.client.AnalyResponMessage;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.common.entity.ResultData;
import com.zc.pivas.common.util.DataFormat;
import com.zc.pivas.common.util.EncodeUtil;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.entity.MedicamentsRefLabel;
import com.zc.pivas.medicaments.service.MedicamentsRefLabelService;
import com.zc.pivas.medicaments.service.MedicamentsService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 药品
 *
 * @author Ray
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/mdcm")
public class MedicamentsController extends SdBaseController {
    private static Logger log = LoggerFactory.getLogger(MedicamentsController.class);

    /**
     * 对接成功
     */
    private final static String TASK_SUCCESS = "200";

    /**
     * 2药品字典
     */
    private final static int ACTION_DRUG = 2;


    @Resource
    private SdExceptionFactory exceptionFactory;


    @Resource
    private MedicamentsService medicamentsService;

    @Resource
    private MedicamentsRefLabelService refLabelService;

    /**
     * 解析响应报文
     */
    private AnalyResponMessage analyResponMessage;

    @Resource
    private MedicCategoryService medicCategoryService;

    /**
     * 跳转页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/initMdcms")
    @RequiresPermissions(value = {"PIVAS_MENU_221"})
    public String initMedicaments(Model model) {
        List<MedicCategory> list = medicamentsService.listMedicCategory();
        model.addAttribute("medicCategoryList", list);

        List<MedicCategory> medicList = medicCategoryService.qryMedicCategory();
        model.addAttribute("medicList", medicList);

        return "pivas/medicaments/medicamentsList";
    }

    /**
     * 按条件分页查询
     *
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mdcmslist", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_222"})
    public String queryMedicaments(JqueryStylePaging paging, Medicaments condition) throws Exception {
        if (StrUtil.isNotNull((condition.getMedicamentsSuchamas()))) {
            StringBuffer suchmas = new StringBuffer();
            for (String suchama : condition.getMedicamentsSuchamas()) {
                suchmas = suchmas.append(suchama.toUpperCase()).append(",");
            }

            condition.setMedicamentsSuchamas(suchmas.toString().split(","));
        }

        String[] categoryNames = condition.getCategoryNames();
        if (categoryNames != null) {
            for (int i = 0; i < categoryNames.length; ++i) {
                categoryNames[i] = DefineStringUtil.escapeAllLike(categoryNames[i]);
            }
        }

        String[] medicamentsNames = condition.getMedicamentsNames();
        if (medicamentsNames != null) {
            for (int i = 0; i < medicamentsNames.length; ++i) {
                medicamentsNames[i] = DefineStringUtil.escapeAllLike(medicamentsNames[i]);
            }
        }

        String[] medicamentsNamesPlaces = condition.getMedicamentsPlaces();
        if (medicamentsNamesPlaces != null) {
            for (int i = 0; i < medicamentsNamesPlaces.length; ++i) {
                medicamentsNamesPlaces[i] = DefineStringUtil.escapeAllLike(medicamentsNamesPlaces[i]);
            }
        }

        String[] phyFunctiys = condition.getPhyFunctiys();
        if (phyFunctiys != null) {
            for (int i = 0; i < phyFunctiys.length; ++i) {
                phyFunctiys[i] = DefineStringUtil.escapeAllLike(phyFunctiys[i]);
            }
        }


        condition.setMedicamentsName(DefineStringUtil.escapeAllLike(condition.getMedicamentsName()));
        condition.setMedicamentsPlace(DefineStringUtil.escapeAllLike(condition.getMedicamentsPlace()));

        String[] columns = new String[]{"medicamentsId"};

        JqueryStylePagingResults<Medicaments> pagingResults = new JqueryStylePagingResults<Medicaments>(columns);

        List<Medicaments> medicamentsList = medicamentsService.queryByPaging(paging, condition);

        MedicamentsRefLabel ref = new MedicamentsRefLabel();
        for (Medicaments medicaments : medicamentsList) {
            ref.setMedicamentsId(medicaments.getMedicamentsId());

            List<MedicamentsRefLabel> medicLabels = refLabelService.getMedicamentsRefLabel(null, ref);
            List<String> labelNames = new ArrayList<String>();
            for (MedicamentsRefLabel refLabel : medicLabels) {
                labelNames.add(refLabel.getLabelName());
            }

            medicaments.setLabelNames(StringUtils.join(labelNames, SdConstant.COMMON_COMMA));
        }

        pagingResults.setDataRows(medicamentsList);
        // 总数
        Integer total = medicamentsService.getTotalCount(condition);
        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());

        return new Gson().toJson(pagingResults);

    }

    /**
     * 根据ID查询药品
     *
     * @param medicamentsId
     * @return
     */
    @RequestMapping(value = "/initUpdMdcm", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_222"})
    public String initUpdateMedicaments(Long medicamentsId) {
        Medicaments medicaments = medicamentsService.selectByPrimaryKey(medicamentsId);

        if (medicaments == null) {
            throw exceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }

        MedicamentsRefLabel ref = new MedicamentsRefLabel();
        ref.setMedicamentsId(medicaments.getMedicamentsId());

        List<MedicamentsRefLabel> medicLabels = refLabelService.getMedicamentsRefLabel(null, ref);
        List<Long> labelIds = new ArrayList<Long>();
        List<String> labelNames = new ArrayList<String>();
        for (MedicamentsRefLabel refLabel : medicLabels) {
            labelIds.add(refLabel.getLabelId());
            labelNames.add(refLabel.getLabelName());
        }

        medicaments.setLabelIds(labelIds.toArray(new Long[]{}));
        medicaments.setLabelNames(StringUtils.join(labelNames, SdConstant.COMMON_COMMA));

        return new Gson().toJson(medicaments);
    }

    @RequestMapping(value = "/addMdcm")
    @ResponseBody
    public String addMedicaments(Medicaments medicaments) {
        medicamentsService.insert(medicaments);
        return buildSuccessJsonMsg("common.op.success");
    }

    /**
     * 更新药品
     *
     * @param medicamentse
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updMdctse", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_223"})
    public String updateMedicamentse(Medicaments medicamentse) {
        Medicaments medicamentseInfo = medicamentsService.selectByPrimaryKey(medicamentse.getMedicamentsId());
        try {
            if (medicamentseInfo == null) {
                throw exceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
            }

            medicamentsService.updateByPrimaryKeySelective(medicamentse);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUGS, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medicaments.tip.updateMedicaments", new String[]{medicamentse.getMedicamentsName()}), true);

            return buildSuccessJsonMsg("common.op.success");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUGS, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medicaments.tip.updateMedicaments", new String[]{medicamentse.getMedicamentsName()}), false);
            throw e;
        }
    }

    /**
     * 查询所有药品标签
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryMdcLbl")
    @ResponseBody
    public String queryMediclLabel() throws Exception {
        String[] columns = new String[]{"labelId"};

        JqueryStylePagingResults<MedicLabel> results = new JqueryStylePagingResults<MedicLabel>(columns);

        List<MedicLabel> medicLabels = medicamentsService.listMedicLabel();
        results.setDataRows(medicLabels);
        // 总数
        Integer total = medicLabels.size();
        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /**
     * 同步药品数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/synMdcms", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_224"})
    public String synMedicaments() throws Exception {
        try {
            // 调用数据同步接口
            JSONObject request = SynTaskClient.setRequestMsgToRestful("his", "getHisData", ACTION_DRUG);

            JSONObject response = SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("result.gethisdata"), request);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                throw new Exception("Call gethisdata interface failure, this url:" + Propertiesconfiguration.getStringProperty("result.gethisdata"));
            }

            // 解析响应消息
            JSONObject paramJsonObject = new JSONObject(EncodeUtil.unGZip(EncodeUtil.decode(response.getString("param"))));
            analyResponMessage.analyMedicamnetRespon(paramJsonObject);

            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUGS, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medicaments.tip.syn"), true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUGS, AmiLogConstant.BRANCH_SYSTEM.CF, getMessage("log.medicaments.tip.syn"), false);
            throw e;
        }
    }

    @Autowired
    public void setAnalyResponMessage(AnalyResponMessage analyResponMessage) {
        this.analyResponMessage = analyResponMessage;
    }


    /**
     * 保存药品分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveCategory")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_223"})
    public ResultData saveType(@RequestParam Map<String, Object> param) {

        String ids = StrUtil.getObjStr(param.get("ids")).trim();
        String type = StrUtil.getObjStr(param.get("type")).trim();

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            medicamentsService.updateMedicType(id, type);
        }

        return DataFormat.formatSucc();
    }

}
