package com.zc.pivas.statistics.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.statistics.bean.configFee.*;
import com.zc.pivas.statistics.service.ConfigFeeStatsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 药单统计控制器
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics")
public class ConfigFeeStatsController extends SdBaseController {
    @Resource
    private ConfigFeeStatsService configFeeStatsService;

    @Resource
    private BatchService batchService;

    @Resource
    InpatientAreaService inpatientAreaService;

    @RequestMapping(value = "/configFee")
    @RequiresPermissions(value = {"PIVAS_MENU_561"})
    public String initConfigFee(Model model, HttpServletRequest request) {
        //药单批次数据
        List<Batch> batchList = batchService.queryBatchAllListForYDStatistic();
        model.addAttribute("batchList", batchList);
        //病区数据
        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.queryInpatientAreaAllListForYDStatistic();
        model.addAttribute("inpatientAreaList", inpatientAreaList);
        return "pivas/statistics/configFeeStatistics";
    }

    /**
     * 按批次获取柱状图数据
     *
     * @param configFeeSearch
     * @return
     */
    @RequestMapping("/queryConfigFeeBatchStatusBar")
    @ResponseBody
    public String queryConfigFeeBatchStatusBar(ConfigFeeSearchBean configFeeSearch) {
        ConfigFeeBatchStatusBarBean batchStatusBarList = configFeeStatsService.queryBatchStatusBar(configFeeSearch);
        return new Gson().toJson(batchStatusBarList);
    }

    /**
     * 按批次获取饼图数据
     *
     * @param configFeeSearch
     * @return
     */
    @RequestMapping("/queryConfigFeeBatchPieList")
    @ResponseBody
    public String queryConfigFeeBatchPieList(ConfigFeeSearchBean configFeeSearch) {
        List<ConfigFeeBatchPieBean> ydBatchPieList = configFeeStatsService.queryBatchPieList(configFeeSearch);
        return new Gson().toJson(ydBatchPieList);
    }

    @RequestMapping("/queryConfigFeeBatchStatusPieList")
    @ResponseBody
    public String queryConfigFeeBatchStatusPieList(ConfigFeeSearchBean configFeeSearch) {
        List<ConfigFeeStatusBean> batchStatusList = configFeeStatsService.queryBatchStatusListByID(configFeeSearch);
        return new Gson().toJson(batchStatusList);
    }

    /**
     * 按病区获取柱状图数据
     *
     * @param configFeeSearch
     * @return
     */
    @RequestMapping("/queryConfigFeeDeptStatusBarData")
    @ResponseBody
    public String queryConfigFeeDeptStatusBarData(ConfigFeeSearchBean configFeeSearch) {
        ConfigFeeDeptStatusBarBean deptStatusBarList = configFeeStatsService.queryDeptStatusBar(configFeeSearch);
        return new Gson().toJson(deptStatusBarList);
    }

    /**
     * 按病区获取饼图数据
     *
     * @param configFeeSearch
     * @return
     */
    @RequestMapping("/queryConfigFeeDeptPieList")
    @ResponseBody
    public String queryConfigFeeDeptPieList(ConfigFeeSearchBean configFeeSearch) {
        List<ConfigFeeDeptPieBean> cfDeptPieList = configFeeStatsService.queryDeptPieList(configFeeSearch);
        return new Gson().toJson(cfDeptPieList);
    }

    @RequestMapping("/queryConfigFeeDeptStatusPieList")
    @ResponseBody
    public String queryConfigFeeDeptStatusPieList(ConfigFeeSearchBean configFeeSearch) {
        List<ConfigFeeStatusBean> deptStatusList = configFeeStatsService.queryDeptStatusListByName(configFeeSearch);
        return new Gson().toJson(deptStatusList);
    }
}
