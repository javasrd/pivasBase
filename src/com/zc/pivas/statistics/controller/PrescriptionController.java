package com.zc.pivas.statistics.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.pivas.configfee.bean.ConfigFeeTypeBean;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.statistics.bean.prescription.*;
import com.zc.pivas.statistics.service.PrescriptionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 药单统计控制器
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics")
public class PrescriptionController extends SdBaseController {
    @Resource
    private PrescriptionService prescriptionService;

    @Resource
    private BatchService batchService;

    @Resource
    InpatientAreaService inpatientAreaService;

    /**
     * 药单统计页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/prescription")
    @RequiresPermissions(value = {"PIVAS_MENU_552"})
    public String initMedicSingle(Model model, HttpServletRequest request) {
        //药单批次数据
        List<Batch> batchList = batchService.queryBatchAllListForYDStatistic();
        model.addAttribute("batchList", batchList);
        //病区数据
        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.queryInpatientAreaAllListForYDStatistic();
        model.addAttribute("inpatientAreaList", inpatientAreaList);
        return "pivas/statistics/prescriptionStatistics";
    }

    /**
     * 按批次查询药单状态
     *
     * @param medicSingleSearch
     * @return
     */
    @RequestMapping("/queryBatchStatusBar")
    @ResponseBody
    public String queryBatchStatusBar(PrescriptionSearchBean medicSingleSearch) {
        BatchStatusBarBean batchStatusBarList = prescriptionService.queryBatchStatusBar(medicSingleSearch);
        return new Gson().toJson(batchStatusBarList);
    }

    /**
     * 按批次获取饼图数据
     *
     * @param medicSingleSearch
     * @return
     */
    @RequestMapping("/queryBatchPieList")
    @ResponseBody
    public String queryBatchPieList(PrescriptionSearchBean medicSingleSearch) {
        List<YDBatchPieBean> ydBatchPieList = prescriptionService.queryBatchPieList(medicSingleSearch);
        return new Gson().toJson(ydBatchPieList);

    }

    @RequestMapping("/queryBatchStatusPieList")
    @ResponseBody
    public String queryBatchStatusPieList(PrescriptionSearchBean medicSingleSearch) {

        List<YDBatchPieBean> ydBatchPieList = new ArrayList<YDBatchPieBean>();
        List<PrescriptionStatusBean> batchStatusList = prescriptionService.queryBatchStatusListByID(medicSingleSearch);
        for (PrescriptionStatusBean batchStatusItem : batchStatusList) {
            YDBatchPieBean ydBatchPieBean = new YDBatchPieBean();

            ydBatchPieBean.setStatusKey(batchStatusItem.getStatusKey());
            ydBatchPieBean.setValue(batchStatusItem.getStstsCount());
            ydBatchPieList.add(ydBatchPieBean);
        }
        return new Gson().toJson(ydBatchPieList);

    }

    /**
     * 按病区查询药单状态
     *
     * @param medicSingleSearch
     * @return
     */
    @RequestMapping("/queryDeptStatusBar")
    @ResponseBody
    public String queryDeptStatusBar(PrescriptionSearchBean medicSingleSearch) {
        DeptStatusBarBean deptStatusBarList = prescriptionService.queryDeptStatusBar(medicSingleSearch);
        return new Gson().toJson(deptStatusBarList);
    }

    @RequestMapping("/queryDeptPieList")
    @ResponseBody
    public String queryDeptPieList(PrescriptionSearchBean medicSingleSearch) {

        List<YDDeptPieBean> ydDeptPieList = prescriptionService.queryDeptPieList(medicSingleSearch);
        return new Gson().toJson(ydDeptPieList);

    }

    @RequestMapping("/queryDeptStatusPieList")
    @ResponseBody
    public String queryDeptStatusPieList(PrescriptionSearchBean medicSingleSearch) {
        List<YDDeptPieBean> ydDeptPieList = new ArrayList<YDDeptPieBean>();
        List<PrescriptionStatusBean> deptStatusList = prescriptionService.queryDeptStatusListByName(medicSingleSearch);
        for (PrescriptionStatusBean batchStatusItem : deptStatusList) {
            YDDeptPieBean ydDeptPieBean = new YDDeptPieBean();
            ydDeptPieBean.setStatusKey(batchStatusItem.getStatusKey());
            ydDeptPieBean.setValue(batchStatusItem.getStstsCount());
            ydDeptPieList.add(ydDeptPieBean);
        }
        return new Gson().toJson(ydDeptPieList);

    }

    /**
     * 查询所有药单状态
     *
     * @return
     */
    @RequestMapping("/queryYdStatus")
    @ResponseBody
    public String queryYdStatus() {
        Map<String, Object> map = new HashMap<String, Object>();

        // 查询所有材料费/配置费
        List<ConfigFeeTypeBean> ydStatusList = new ArrayList<ConfigFeeTypeBean>();

        ConfigFeeTypeBean bean = new ConfigFeeTypeBean();
        bean.setGid("0");
        bean.setTypeDesc("执行");
        ydStatusList.add(bean);

        bean = new ConfigFeeTypeBean();
        bean.setGid("1");
        bean.setTypeDesc("停止");
        ydStatusList.add(bean);

        bean = new ConfigFeeTypeBean();
        bean.setGid("2");
        bean.setTypeDesc("撤销");
        ydStatusList.add(bean);

        bean = new ConfigFeeTypeBean();
        bean.setGid("3");
        bean.setTypeDesc("退费");
        ydStatusList.add(bean);

        map.put("success", true);
        map.put("ydStatusList", ydStatusList);

        return new Gson().toJson(map);
    }
}
