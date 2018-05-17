package com.zc.pivas.workReport.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.DateUtil;
import com.zc.pivas.workReport.bean.WorkBean;
import com.zc.pivas.workReport.service.WorkReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Controller
@RequestMapping(value = "/workReport")
public class WorkReportController extends SdBaseController
{
    
    
    @Resource
    private WorkReportService workReportService;
    /**
     * 
     * 班次统计
     * @param model
     * @param request
     * @return
     * @throws Exception
     *
     */
    @RequestMapping(value = "/initCount")
    public String initCount(Model model, HttpServletRequest request) throws Exception
    {
        
        String todayDate = DateUtil.getCurrentDay8Str();
        model.addAttribute("todayDate",todayDate);
        
        List<WorkBean> workList = workReportService.getWorkList();
        model.addAttribute("workList",workList);
        
        
        return "/pivas/workReport";
    }

    
}
