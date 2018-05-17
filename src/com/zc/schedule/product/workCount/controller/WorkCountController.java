package com.zc.schedule.product.workCount.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.workCount.entity.SignBean;
import com.zc.schedule.product.workCount.service.WorkCountService;

/**
 * 签到
 *
 * @author Justin
 * @version v1.0
 */
@Controller
@RequestMapping("/signIn")
public class WorkCountController extends BaseController {

    @Resource
    private WorkCountService workCountService;


    /**
     * 签到页面
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/index")
    public String initSign(Model model, HttpServletRequest request) throws Exception {
        String todayDate = DateUtils.dateToString(new Date(), "yyyy-MM-dd");
        model.addAttribute("todayDate", todayDate);

        Date now = new Date();
        String minDateStr = DateUtils.lastMonth(now, 5);
        model.addAttribute("minDateStr", minDateStr);

        return "/schedule/workCount/sign";
    }

    /**
     * 查询签到列表
     *
     * @param request
     * @param reqParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/signList")
    public ResultPage signList(HttpServletRequest request, @RequestBody Map<String, Object> reqParam) {

        if (request != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute(SysConstant.sessionName.userId) != null) {
                Long userAccount = DataFormat.getObjLong(session.getAttribute(SysConstant.sessionName.userId));
                reqParam.put("userAccount", userAccount);
            }
        }

        List<SignBean> signList = workCountService.getSignList(reqParam);

        return DataFormat.formatPageData(signList);
    }


    /**
     * 签到
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkSign", method = RequestMethod.POST)
    public ResultData checkSign(HttpServletRequest request, SignBean signBean) {


        if (request != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute(SysConstant.sessionName.userId) != null) {
                Long userAccount = DataFormat.getObjLong(session.getAttribute(SysConstant.sessionName.userId));
                signBean.setAccount(userAccount.toString());
            }
        }

        int row = workCountService.addCheckSign(signBean);

        if (row > 0) {
            //日志添加
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.签到, "班次签到成功");
        } else {
            //日志添加
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.签到, "班次签到失败");
        }

        return DataFormat.formatAdd(row, "签到成功");
    }

}
