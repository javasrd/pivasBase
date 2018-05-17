package com.zc.pivas.printlabel.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.repository.BatchDao;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.dao.InpatientAreaDAO;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.service.MedicamentsService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import com.zc.pivas.printlabel.entity.PrintLabelConBean;
import com.zc.pivas.printlabel.service.PrintLabelConService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 瓶签条件配置控制类
 *
 * @author kunkka
 * @version 1.0
 */
@Controller()
@RequestMapping(value = "/printLabelCon")
public class PrintLabelConController extends SdDownloadController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    @Resource
    private BatchService batchService;

    @Resource
    private InpatientAreaDAO inpatientAreaDAO;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    @Resource
    private MedicamentsService medicamentsService;

    @Resource
    private MedicLabelDao medicLabelDao;

    @Resource
    private PrintLabelConService printLabelConService;

    @Resource
    private BatchDao batchDao;

    @Resource
    private InpatientAreaService inpatientAreaService;

    @RequestMapping("/initBottleLabelCon")
    public String init(Model model) {
        // 批次列表
        List<Batch> batchList = batchService.queryByPaging(null, null);

        // 药品分类列表
        List<MedicCategory> medicCategories = medicCategoryDao.queryAllMedicCategory();

        // 获取药品标签
        List<MedicLabel> medicLabelList = medicLabelDao.listMedicLabel(null, new JqueryStylePaging());

        model.addAttribute("batchList", batchList);
        model.addAttribute("medicCategoryList", medicCategories);
        model.addAttribute("medicLabelList", medicLabelList);

        return "pivas/bottleLabel/printLabelConList";
    }

    /**
     * 初始化所有下拉列表数据
     *
     * @return
     */
    @RequestMapping("/querySelectDatareq")
    @ResponseBody
    public String querySelectDatareq() {
        Map<String, Object> map = new HashMap<String, Object>();

//        // 批次列表
//        List<Batch> batchList = batchService.queryByPaging(null, null);
//        
//        // 药品分类列表
//        List<MedicCategory> medicCategories = medicCategoryDao.queryAllMedicCategory();
//        
//        // 获取药品标签
//        List<MedicLabel> medicLabelList = medicLabelDao.listMedicLabel(null, new JqueryStylePaging());
//        
//        InpatientAreaBean bean = new InpatientAreaBean();
//        bean.setEnabled("1");
//        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.getInpatientAreaBeanList(bean,new JqueryStylePaging());
//        
//        map.put("batchList", batchList);
//        map.put("medicCategoryList", medicCategories);
//        map.put("medicLabelList", medicLabelList);
//        map.put("inpatientAreaList", inpatientAreaList);

        map.put("success", true);

        return new Gson().toJson(map);
    }

    /***
     * 查询所有数据
     * @param
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/printBottleLabelList", produces = "application/json")
    @ResponseBody
    public String printLabelConList(PrintLabelConBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<PrintLabelConBean> pagingResults =
                printLabelConService.queryPrintLabelConList(bean, jquryStylePaging);

        return new Gson().toJson(pagingResults);
    }

    /**
     * 添加
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addPrtLabelCon", produces = "application/json")
    @ResponseBody
    public String addPrintLabelCon(PrintLabelConBean bean)
            throws Exception {
        // 判断名称是否存在
        List<PrintLabelConBean> printLabelConList = printLabelConService.queryPrintLabelCon(bean);

        try {
            if (DefineCollectionUtil.isNotEmpty(printLabelConList)) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {

                int count = printLabelConService.getCountOrder(bean);
                if (count > 0) {

                    return buildFailJsonMsg("优先级重复");
                }

                printLabelConService.insert(bean);
//                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
//                    AmiLogConstant.BRANCH_SYSTEM.CF,
//                    getMessage("log.errortype.tip.add", new String[] {bean.getName()}),
//                    true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
//            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
//                AmiLogConstant.BRANCH_SYSTEM.CF,
//                getMessage("log.errortype.tip.add", new String[] {bean.getName()}),
//                false);
            throw e;
        }
    }

    /***
     *
     * 初始化
     * @return json字符串
     */
    @RequestMapping(value = "/initUpdateprtLabelCon", produces = "application/json")
    @ResponseBody
    public String initUpdateprintLabelCon(PrintLabelConBean bean) {
        List<PrintLabelConBean> printLabelConList = printLabelConService.queryPrintLabelCon(bean);
        if (DefineCollectionUtil.isEmpty(printLabelConList)) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }

        PrintLabelConBean printLabelCon = printLabelConList.get(0);

        if (StrUtil.isNotNull(printLabelCon.getBatchid())) {
            String[] batchIDs = printLabelCon.getBatchid().split(",");
            String batchName = "";
            for (String batch : batchIDs) {
                batchName = batchDao.selectByPrimaryKey(Long.valueOf(batch)).getName() + "," + batchName;
            }
            printLabelCon.setBatchName(batchName.substring(0, batchName.length() - 1));
        }

        if (StrUtil.isNotNull(printLabelCon.getMedicCategoryID())) {
            String[] medicCategoryIDs = printLabelCon.getMedicCategoryID().split(",");
            String medicCategory = "";
            MedicCategory category = null;
            for (String medicCategoryID : medicCategoryIDs) {
                category = new MedicCategory();
                category.setCategoryId(Long.valueOf(medicCategoryID));
                medicCategory = medicCategoryDao.listMedicCategory(category, new JqueryStylePaging()).get(0).getCategoryName() + "," + medicCategory;

            }
            printLabelCon.setMedicCategory(medicCategory.substring(0, medicCategory.length() - 1));
        }

        if (StrUtil.isNotNull(printLabelCon.getMedicLabelID())) {
            String[] mediclabelIDs = printLabelCon.getMedicLabelID().split(",");
            String mediclabelName = "";
            MedicLabel mediclabel = null;
            for (String mediclabelID : mediclabelIDs) {
                mediclabel = new MedicLabel();
                mediclabel.setLabelId(Long.valueOf(mediclabelID));
                mediclabelName = medicLabelDao.listMedicLabel(mediclabel, new JqueryStylePaging()).get(0).getLabelName() + "," + mediclabelName;

            }
            printLabelCon.setMediclabel(mediclabelName.substring(0, mediclabelName.length() - 1));
        }

        if (StrUtil.isNotNull(printLabelCon.getMedicalID())) {
            String[] medicalIDs = printLabelCon.getMedicalID().split(",");
            String medicalIDName = "";
            for (String medicalID : medicalIDs) {
                medicalIDName = medicamentsService.getMediclByCode(medicalID).getMedicamentsName() + "," + medicalIDName;
            }
            printLabelCon.setMedical(medicalIDName.substring(0, medicalIDName.length() - 1));
        }

        if (StrUtil.isNotNull(printLabelCon.getDeptCode())) {
            String[] deptCodes = printLabelCon.getDeptCode().split(",");
            String deptName = "";

            InpatientAreaBean inpatientArea = null;
            for (String deptCode : deptCodes) {
                inpatientArea = new InpatientAreaBean();
                inpatientArea.setDeptCode(deptCode);
                deptName = inpatientAreaService.getInpatientAreaBean(inpatientArea).getDeptName() + "," + deptName;
            }
            printLabelCon.setDeptName(deptName.substring(0, deptName.length() - 1));
        }

        return new Gson().toJson(printLabelCon);
    }

    /**
     * 修改
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/updatePrtLabelCon", produces = "application/json")
    @ResponseBody
    public String updatePrintLabelCon(PrintLabelConBean bean)
            throws Exception {
        // 判断名称是否存在
        boolean isExist = printLabelConService.checkPrintLabelConName(bean);

        try {
            if (isExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else {
                if ("false".equals(bean.getIsSame())) {
                    int count = printLabelConService.getCountOrder(bean);
                    if (count > 0) {
                        return buildFailJsonMsg("优先级重复");
                    }
                }

                printLabelConService.updatePrintLabelCon(bean);
//                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
//                    AmiLogConstant.BRANCH_SYSTEM.CF,
//                    getMessage("log.errortype.tip.update", new String[] {bean.getName()}),
//                    true);

                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
//            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
//                AmiLogConstant.BRANCH_SYSTEM.CF,
//                getMessage("log.errortype.tip.update", new String[] {bean.getName()}),
//                false);
            throw e;
        }
    }

    /**
     * 删除
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/delprtLabelCon", produces = "application/json")
    @ResponseBody
    public String delprintLabelCon(String id)
            throws Exception {
        try {
            printLabelConService.delPrintLabelCon(id.split(","));
//            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
//                AmiLogConstant.BRANCH_SYSTEM.CF,
//                getMessage("log.errortype.tip.del", new String[] {gid}),
//                true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
//            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.TRIAL_ERROR_TYPE,
//                AmiLogConstant.BRANCH_SYSTEM.CF,
//                getMessage("log.errortype.tip.del", new String[] {gid}),
//                false);
            throw e;
        }
    }

    /**
     * 查询批次信息
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/qryBatchs", produces = "application/json")
    @ResponseBody
    public String queryBatchs()
            throws Exception {
        String[] columns = new String[]{"num", "name"};
        JqueryStylePagingResults<Batch> results = new JqueryStylePagingResults<Batch>(columns);

        List<Batch> datas = batchService.queryByPaging(new JqueryStylePaging(), new Batch());

        // 总数
        Integer total = null;
        if (null != datas) {
            results.setDataRows(datas);
            total = datas.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /**
     * 查询药品分类信息
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/qryMedicCategorys", produces = "application/json")
    @ResponseBody
    public String queryMedicCategorys()
            throws Exception {
        String[] columns = new String[]{"categoryId", "categoryName"};
        JqueryStylePagingResults<MedicCategory> results = new JqueryStylePagingResults<MedicCategory>(columns);

        List<MedicCategory> datas = medicCategoryDao.qryMedicCategory();

        // 总数
        Integer total = null;
        if (null != datas) {
            results.setDataRows(datas);
            total = datas.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /**
     * 查询药品标签信息
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/qryMedicLabels", produces = "application/json")
    @ResponseBody
    public String queryMedicLabels()
            throws Exception {
        String[] columns = new String[]{"labelId", "labelName"};
        JqueryStylePagingResults<MedicLabel> results = new JqueryStylePagingResults<MedicLabel>(columns);

        List<MedicLabel> datas = medicLabelDao.listMedicLabel(new MedicLabel(), new JqueryStylePaging());

        // 总数
        Integer total = null;
        if (null != datas) {
            results.setDataRows(datas);
            total = datas.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /**
     * 查询药品溶媒信息
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/qryMedicals", produces = "application/json")
    @ResponseBody
    public String queryMedicals() throws Exception {
        String[] columns = new String[]{"medicamentsCode", "medicamentsName"};
        JqueryStylePagingResults<Medicaments> results = new JqueryStylePagingResults<Medicaments>(columns);
        Medicaments medicament = new Medicaments();
        medicament.setMedicamentsMenstruum(1);
        List<Medicaments> datas = medicamentsService.queryByPaging(new JqueryStylePaging(), medicament);

        // 总数
        Integer total = null;
        if (null != datas) {
            results.setDataRows(datas);
            total = datas.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }

    /**
     * 查询病区信息
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/qryDepts", produces = "application/json")
    @ResponseBody
    public String queryDepts() throws Exception {
        String[] columns = new String[]{"deptCode", "deptName"};
        JqueryStylePagingResults<InpatientAreaBean> results = new JqueryStylePagingResults<InpatientAreaBean>(columns);
        InpatientAreaBean bean = new InpatientAreaBean();
        bean.setEnabled("1");
        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.getInpatientAreaBeanList(bean, new JqueryStylePaging());

        // 总数
        Integer total = null;
        if (null != inpatientAreaList) {
            results.setDataRows(inpatientAreaList);
            total = inpatientAreaList.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }
}
