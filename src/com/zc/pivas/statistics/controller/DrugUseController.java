package com.zc.pivas.statistics.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.pivas.statistics.service.DrugUseService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import com.zc.pivas.statistics.bean.druguse.*;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 药物使用统计
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics/drugUse")
public class DrugUseController extends SdBaseController {
    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(DrugUseController.class);

    /**
     * 批次数据
     */
    @Resource
    private BatchService batchService;

    /**
     * 病区service
     */
    @Resource
    private InpatientAreaService inpatientAreaService;

    /**
     * 药物使用统计
     */
    @Resource
    private DrugUseService drugUseService;

    /**
     * 药品分类
     */
    @Resource
    private MedicCategoryDao medicCategoryDao;

    /**
     * 药品标签
     */
    @Resource
    private MedicLabelDao medicLabelDao;

    @RequestMapping(value = "")
    @RequiresPermissions(value = {"PIVAS_MENU_581"})
    public String initDrugUse(Model model, HttpServletRequest request) {
        // 获取所有批次信息
        List<Batch> batchList = batchService.queryBatchAllListForYDStatistic();
        // 获取开启病区信息
        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.queryInpatientAreaAllListForYDStatistic();
        // 获取所有药品分类
        List<MedicCategory> medicCategoryList = medicCategoryDao.listMedicCategory(null, new JqueryStylePaging());
        // 获取药品标签
        List<MedicLabel> medicLabelList = medicLabelDao.listMedicLabel(null, new JqueryStylePaging());
        model.addAttribute("batchList", batchList);
        model.addAttribute("inpatientAreaList", inpatientAreaList);
        model.addAttribute("medicCategoryList", medicCategoryList);
        model.addAttribute("medicLabelList", medicLabelList);
        return "pivas/statistics/drugUse";
    }

    /**
     * 饼图：根据批次查询数据
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryBatchPieList")
    @ResponseBody
    public String queryBatchPieList(PrescriptionSearchBean condition) {
        List<DrugUseBatchPieBean> drugUseBatchPieList = drugUseService.queryBatchPieList(condition);
        return new Gson().toJson(drugUseBatchPieList);

    }

    /**
     * 饼图：选中某一批次后，数据统计
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryBatchDrugClassPieList")
    @ResponseBody
    public String queryBatchDrugClassPieList(PrescriptionSearchBean condition) {
        List<DrugUseBatchPieBean> drugUseBatchPieList = new ArrayList<DrugUseBatchPieBean>();

        List<DrugUsePieDetailBean> drugUseBatchPieDetailList = drugUseService.queryBatchDrugClassPieList(condition);

        // 不为空，拼接饼图展示数据
        if (null != drugUseBatchPieDetailList) {
            DrugUseBatchPieBean drugUseBatchPieBean = null;

            DecimalFormat df = new DecimalFormat("#.##");

            for (DrugUsePieDetailBean bean : drugUseBatchPieDetailList) {
                drugUseBatchPieBean = new DrugUseBatchPieBean();
                drugUseBatchPieBean.setName(bean.getDrugClassName());
                drugUseBatchPieBean.setValue(bean.getStstsCount());
                drugUseBatchPieBean.setZxbc(condition.getBatchID());
                drugUseBatchPieBean.setStstsCost(df.format(bean.getStstsCost()));
                drugUseBatchPieList.add(drugUseBatchPieBean);
            }
        }
        return new Gson().toJson(drugUseBatchPieList);
    }

    /**
     * 饼图：根据病区查询数据
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryDeptPieList")
    @ResponseBody
    public String queryDeptPieList(PrescriptionSearchBean condition) {
        List<DrugUseDeptPieBean> drugUseDeptPieList = drugUseService.queryDeptPieList(condition);

        return new Gson().toJson(drugUseDeptPieList);

    }

    /**
     * 饼图：中某一批次后，数据统计
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryDeptDrugClassPieList")
    @ResponseBody
    public String queryDeptDrugClassPieList(PrescriptionSearchBean condition) {
        List<DrugUseDeptPieBean> drugUseDeptPieList = new ArrayList<DrugUseDeptPieBean>();

        List<DrugUsePieDetailBean> drugUseBatchPieDetailList = drugUseService.queryDeptDrugClassPieList(condition);

        // 不为空，拼接饼图展示数据
        if (null != drugUseBatchPieDetailList) {
            DrugUseDeptPieBean drugUseDeptPieBean = null;

            DecimalFormat df = new DecimalFormat("#.##");

            for (DrugUsePieDetailBean bean : drugUseBatchPieDetailList) {
                drugUseDeptPieBean = new DrugUseDeptPieBean();
                drugUseDeptPieBean.setName(bean.getDrugClassName());
                drugUseDeptPieBean.setValue(bean.getStstsCount());
                drugUseDeptPieBean.setStstsCost(df.format(bean.getStstsCost()));
                drugUseDeptPieList.add(drugUseDeptPieBean);
            }
        }
        return new Gson().toJson(drugUseDeptPieList);

    }

    /**
     * 柱状图：批次
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryBatchBar")
    @ResponseBody
    public String queryBatchDrugUseBar(PrescriptionSearchBean condition) {
        DrugUseBatchBarBean drugUseBatchBarList = drugUseService.queryBarGroupByBatchList(condition);
        return new Gson().toJson(drugUseBatchBarList);
    }

    /**
     * 柱状图：病区
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryDeptBar")
    @ResponseBody
    public String queryDeptDrugUseBar(PrescriptionSearchBean condition) {
        DrugUseDeptBarBean drugUseDeptBarList = drugUseService.queryBarGroupByDeptList(condition);
        return new Gson().toJson(drugUseDeptBarList);
    }
}
