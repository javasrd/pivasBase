package com.zc.schedule.product.scheduleRule.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.scheduleRule.entity.CountBean;
import com.zc.schedule.product.scheduleRule.entity.GroupBean;
import com.zc.schedule.product.scheduleRule.service.RuleService;

/**
 * 
 * 排班规则控制类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Controller
@RequestMapping("/scheduleRule")
public class RuleController extends BaseController
{
    private static Logger logger = Logger.getLogger(RuleController.class);
    
    /**
     * 排班规则service层
     */
    @Resource(name = "ruleServiceImpl")
    private RuleService ruleService;
    
    
    /**
     * 
     * 初始化排班规则页面
     * @return String
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String initManager()
    {
        return "/schedule/scheduleRule/RuleSetting";
    }
    
    
    /**
     * 
     * 初始化排班规则数据
     * @param model
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/getWorkCountInfo", method = RequestMethod.POST)
    public ResultPage getWorkCountInfo(ModelMap model, HttpServletRequest request, @RequestBody Map<String,Object> reqParam) 
    {
        
        List<CountBean> list = ruleService.getWorkCountInfo(reqParam);
        
        return DataFormat.formatPageData(list) ;
        
    }
    
    
    /**
     * 
     * 更新每日次数
     * 
     * @param request
     * @param countBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/updateCount", method = RequestMethod.POST)
    public ResultData updateCount(HttpServletRequest request,CountBean countBean) 
    {
        //判断班次是否存在
        int row = ruleService.updateCount(countBean);
        //日志添加
        addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.排班规则, "修改每日次数成功");
        return DataFormat.formatUpd(row) ;
    }
    
    
    /**
     * 
     * 初始化分组规则数据
     * @param model
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/getGroupInfo", method = RequestMethod.POST)
    public ResultPage getGroupInfo(ModelMap model, HttpServletRequest request, @RequestBody Map<String,Object> reqParam) 
    {
        List<GroupBean> list = ruleService.getGroupInfo(reqParam);
        return DataFormat.formatPageData(list);
    }
    
    
    /**
     * 
     * 根据分组实时刷新班次
     * 
     * @param request
     * @param groupBean
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/getWorkInfoByGroup", method = RequestMethod.POST)
    public ResultPage getWorkInfoByGroup(HttpServletRequest request, GroupBean groupBean) {
        List<GroupBean> list = ruleService.getWorkInfo(groupBean);
        return DataFormat.formatPageData(list);
    }
    
    /**
     * 
     * 更新分组与班次的信息
     * 
     * @param request
     * @param groupBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/updateGroup", method = RequestMethod.POST)
    public ResultData updateGroup(HttpServletRequest request, GroupBean groupBean )
    {
        int row = ruleService.updateGroup(groupBean);
        return DataFormat.formatUpd(row);
    }
}
