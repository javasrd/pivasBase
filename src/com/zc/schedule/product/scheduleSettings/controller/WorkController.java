package com.zc.schedule.product.scheduleSettings.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.scheduleSettings.entity.WorkBean;
import com.zc.schedule.product.scheduleSettings.service.WorkService;

/**
 * 
 * 班次设置控制器
 * 
 * @author  Justin
 * @version  v1.0
 */
@Controller
@RequestMapping("/workSetting")
public class WorkController extends BaseController
{
    private static Logger logger = Logger.getLogger(WorkController.class);
    
    @Resource(name = "workServiceImpl")
    private WorkService workService;
    /**
     * 
     * 初始化班次设置页面
     * @return String
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String initManager()
    {
        return "schedule/scheduleSettings/workSetting";
    }
    
    
    /**
     * 
     * 初始化班次信息
     * @param model
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/getWorkList", method = RequestMethod.POST)
    public ResultPage getWorkList(ModelMap model, HttpServletRequest request, @RequestBody Map<String,Object> reqParam) 
    {
        /*获取语言，设置语言*/
//        String language = RequestContextUtils.getLocale(request).getLanguage();
//        HttpSession session = request.getSession();
//        Object langu = session.getAttribute(SysConstant.sessionName.language);
//        logger.debug("language="+langu);
//        if(langu==null){
//            session.setAttribute(SysConstant.sessionName.language, language);
//        }
//        Object langu2 = session.getAttribute(SysConstant.sessionName.language);
//        logger.debug("language2="+langu2);
//        
//        Map<String,List<Dict>> dicMap1 = getDicMapByCateS(request,DicUtil.dicName.ageUnit);
//        System.out.println(dicMap1);
//        
//        List<Dict> dicList = getDicListByName(request, DicUtil.dicName.ageUnit);
//        System.out.println(dicList);
//        
//        //日志添加
//        addLogSucc(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试成功日志");
//        addLogFail(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试失败日志");
        
        
        /* 分页样例
        PageInfo page = new PageInfo(reqParam);
        List<User> list = userService.qryList(reqParam,page);
        return DataFormat.formatPageData(list,page) ;
        */
        /* 一页展示所有数据的分页数据返回
        List<User> list = userService.qryList(reqParam);
        return DataFormat.formatPageData(list) ;
        */
        //分页
        PageInfo page = new PageInfo(reqParam);
        
        List<WorkBean> list = workService.getWorkInfoList(reqParam, page);
        
        return DataFormat.formatPageData(list, page) ;
    }
    
    /**
     * 
     * 新增班次
     * @param request
     * @param workBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/addWork", method = RequestMethod.POST)
    public ResultData addWork(HttpServletRequest request, WorkBean workBean) 
    {
        
        //判断班次名称是否重复
        int count = workService.checkWorkNameIsExitst(workBean.getWorkName());
        if (count == 0) 
        {
            //根据登录获取当前用户
            Long userAccount = Long.valueOf(0);
            if (request != null)
            {
                HttpSession session = request.getSession();
                
                if (session.getAttribute(SysConstant.sessionName.userId) != null)
                {
                    userAccount = DataFormat.getObjLong(session.getAttribute(SysConstant.sessionName.userId));
                }
                
            }
            workBean.setCreater(userAccount);
            int row = workService.addWorkInfo(workBean);
            //日志添加
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "新增班次[" + workBean.getWorkName() + "]成功");
            return DataFormat.formatAdd(row);
        }
        else 
        {
            //异常提示
            //日志添加
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "新增班次[" + workBean.getWorkName() + "]失败,班次名称已被使用过");
            return DataFormat.formatFail("班次名称已被使用过", null);
        }
    }
    
    
    /**
     * 
     * 修改班次
     * @param request
     * @param workBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/editWork", method = RequestMethod.POST)
    public ResultData editWork(HttpServletRequest request, WorkBean workBean) 
    {
        //修改时，未修改班次名称的情况
        WorkBean objWorkBean = workService.getWorkById(workBean.getWorkId());
        
        if (workBean.getWorkName().equals(objWorkBean.getWorkName())) 
        {
            //班次名称未做修改，不验证班次名是否重复
            int row = workService.updateWorkInfo(workBean, objWorkBean);
            //日志添加
          addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "修改班次[" + workBean.getWorkName() + "]成功");
          return DataFormat.formatUpd(row);
        }
        else 
        {
            //验证班次名称是否重复
            int count = workService.checkWorkNameIsExitst(workBean.getWorkName());
            if (count == 0) 
            {
                int row = workService.updateWorkInfo(workBean, objWorkBean);
                //日志添加
                addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "修改班次[" + workBean.getWorkName() + "]成功");
                return DataFormat.formatUpd(row);
            }
            else 
            {
                //异常提示
                //日志添加
                addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "修改班次[" + workBean.getWorkName() + "]失败,班次名称已被使用过");
                return DataFormat.formatFail("班次名称已被使用过", null);
            }
        }
    }
    
    
    /**
     * 
     * 获取当个班次信息
     * @param workId
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/getWorkById", method = RequestMethod.POST)
    public ResultData getWorkById(Integer workId) 
    {
        //先判断当前班次是否已存在
        WorkBean workBean = workService.getWorkById(workId);
        if (null == workBean) 
        {
            return DataFormat.formatFail("此记录不存在", null);
        } 
        else 
        {
            return DataFormat.formatSucc(workBean);
        }
    }
    
    
    /**
     * 
     * 删除班次信息
     * @param request
     * @param workId
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/deleteWork", method = RequestMethod.POST)
    public ResultData deleteWork(HttpServletRequest request, String workId) 
    {
        //判断班次是否已被使用
        boolean flag = workService.checkWorkIsExitst(workId);
        if (true == flag) 
        {
            //未使用
            int row = workService.delWorkInfo(workId);
            //日志添加
          addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "删除班次编号为" + workId + "成功");
            return DataFormat.formatDel(row);
        }
        else
        {
            //日志添加
          addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.班次设置, "删除班次编号为" + workId + "失败");
            //已被使用
            return DataFormat.formatFail("此班次已被使用", null);
        }
        
    }
    
    
    /**
     * 
     * 修改班次状态
     * 
     * @param request
     * @param workBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/changeWorkStatus", method = RequestMethod.POST)
    public ResultData changeWorkStatus(HttpServletRequest request, WorkBean workBean) 
    {
        //先判断当前班次是否已存在
        WorkBean work = workService.getWorkById(workBean.getWorkId());
        if (null == work) 
        {
            //日志添加
//          addLogFail(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试失败日志");
            return DataFormat.formatFail("此记录不存在", null);
        } 
        else 
        {
            workService.changeWorkStatus(workBean);
            return DataFormat.formatSucc();
        }
    }
    
    
    /**
     * 
     * 获得历史班次信息
     * @param model
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/getOldWorkInfo", method = RequestMethod.POST)
    public ResultPage getOldWorkInfo(ModelMap model, HttpServletRequest request, @RequestBody Map<String,Object> reqParam) 
    {
        /*获取语言，设置语言*/
//        String language = RequestContextUtils.getLocale(request).getLanguage();
//        HttpSession session = request.getSession();
//        Object langu = session.getAttribute(SysConstant.sessionName.language);
//        logger.debug("language="+langu);
//        if(langu==null){
//            session.setAttribute(SysConstant.sessionName.language, language);
//        }
//        Object langu2 = session.getAttribute(SysConstant.sessionName.language);
//        logger.debug("language2="+langu2);
//        
//        Map<String,List<Dict>> dicMap1 = getDicMapByCateS(request,DicUtil.dicName.ageUnit);
//        System.out.println(dicMap1);
//        
//        List<Dict> dicList = getDicListByName(request, DicUtil.dicName.ageUnit);
//        System.out.println(dicList);
//        
//        //日志添加
//        addLogSucc(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试成功日志");
//        addLogFail(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试失败日志");
        
        //分页
        PageInfo page = new PageInfo(reqParam);
        
        List<WorkBean> list = workService.queryOldWorkInfo(reqParam, page);
        
        return DataFormat.formatPageData(list, page);
    }
    
    
    /**
     * 
     * 修改班次配色
     * 
     * @param request
     * @param workBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/changeWorkColor", method = RequestMethod.POST)
    public ResultData changeWorkColor(HttpServletRequest request, WorkBean workBean) 
    {
        //先判断当前班次是否已存在
        WorkBean work = workService.getWorkById(workBean.getWorkId());
        if (null == work) 
        {
            //日志添加
//          addLogFail(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试失败日志");
            return DataFormat.formatFail("此记录不存在", null);
        } 
        else 
        {
            workService.changeWorkColor(workBean);
            return DataFormat.formatSucc();
        }
    }
}
