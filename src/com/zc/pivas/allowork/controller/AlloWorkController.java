package com.zc.pivas.allowork.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.pivas.allowork.bean.AlloWorkBarBean;
import com.zc.pivas.allowork.bean.AlloWorkPieBean;
import com.zc.pivas.allowork.service.AlloWorkService;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 药物使用统计
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/alloWork")
public class AlloWorkController extends SdBaseController {
    @Resource
    private AlloWorkService alloWorkService;

    @RequestMapping(value = "/initAlloWork")
    @RequiresPermissions(value = {"PIVAS_MENU_721"})
    public String initAlloWork(Model model, HttpServletRequest request) {
        // 获取所有配置人员信息
        List<StaticDoctorNameBean> doctorNameList = alloWorkService.queryAlloWorkNameList();
        model.addAttribute("doctorNameList", doctorNameList);
        return "pivas/statistics/alloWork";
    }

    /**
     * 饼图：查询数据
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryPieList")
    @ResponseBody
    public String queryBatchPieList(PrescriptionSearchBean condition) {
        List<AlloWorkPieBean> allowWorkPieList = alloWorkService.queryPieList(condition);

        return new Gson().toJson(allowWorkPieList);
    }

    /**
     * 饼图：详情查询
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryDetailPieList")
    @ResponseBody
    public String queryDetailPieList(PrescriptionSearchBean condition) {
        List<AlloWorkPieBean> alloWorkDetailPieList = alloWorkService.queryDetailPieList(condition);

        return new Gson().toJson(alloWorkDetailPieList);
    }

    /**
     * 柱状图
     *
     * @param condition
     * @return
     */
    @RequestMapping("/queryBar")
    @ResponseBody
    public String queryAlloWorkBar(PrescriptionSearchBean condition) {

        AlloWorkBarBean alloWorkBar = alloWorkService.querybarList(condition);
        return new Gson().toJson(alloWorkBar);
    }
}
