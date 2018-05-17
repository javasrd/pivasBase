package com.zc.pivas.statistics.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.StrUtil;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.pivas.statistics.service.AllocationWorkService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 配置工作量统计
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics")
public class AllocationWorkloadStatsController extends SdBaseController {

    @Resource
    private AllocationWorkService allocationWorkService;

    @RequestMapping(value = "allocationWorkload")
    public String init(Model model, HttpServletRequest request) throws Exception {
        List<Batch> batchList = allocationWorkService.getBatchList();
        model.addAttribute("batchList", batchList);
        return "pivas/statistics/allocationWorkload";
    }

    @RequestMapping(value = "/getWorkloadList")
    @ResponseBody
    public String getWorkList(@RequestParam Map<String, Object> param) throws Exception {
        String batchList = StrUtil.getObjStr(param.get("batchList"));
        if (StringUtils.isNotBlank(batchList)) {
            String[] batchArray = batchList.split(",");
            param.put("batchArray", batchArray);
        }
        String htmlAndPdfPath = allocationWorkService.getWorkListPdfPath(param, getCurrentUser());
        return htmlAndPdfPath;
    }

    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public String exportExcel(@RequestParam Map<String, Object> param) throws Exception {

        String batchList = StrUtil.getObjStr(param.get("batchList"));
        if (StringUtils.isNotBlank(batchList)) {
            String[] batchArray = batchList.split(",");
            param.put("batchArray", batchArray);
        }
        String excelPath = allocationWorkService.toExportExcel(param, getCurrentUser());
        if ("error_1".equals(excelPath)) {
            return buildFailJsonMsg("没有可导出的文件");
        } else if ("error_2".equals(excelPath)) {
            return buildFailJsonMsg("请先关闭打开的文件");
        }
        return buildSuccessJsonMsg(excelPath);
    }

}
