package com.zc.pivas.statistics.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.statistics.service.PrescriptionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 扫描工作量统计控制器
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics")
public class ScanWorkloadStatsController extends SdBaseController {
    @Resource
    private PrescriptionService prescriptionService;

    @Resource
    private BatchService batchService;

    @Resource
    InpatientAreaService inpatientAreaService;

    /**
     * 扫描工作量统计页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/scanWorkload")
    @RequiresPermissions(value = {"PIVAS_MENU_724"})
    public String initScan(Model model, HttpServletRequest request) {
        //获取批次数据，用于下拉框
        List<Batch> batchList = batchService.queryBatchAllListForYDStatistic();
        model.addAttribute("batchList", batchList);

        //获取病区数据，用于下拉框
        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.queryInpatientAreaAllListForYDStatistic();
        model.addAttribute("inpatientAreaList", inpatientAreaList);
        return "pivas/statistics/scanWorkload";
    }
}
