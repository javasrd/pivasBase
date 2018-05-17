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
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.scheduleSettings.entity.PostBean;
import com.zc.schedule.product.scheduleSettings.service.PostService;

@Controller
@RequestMapping("/position")
public class PostController extends BaseController
{
    private static Logger logger = Logger.getLogger(PostController.class);
    
    @Resource(name = "postServiceImpl")
    private PostService postService;
    
    /**
     * 初始化岗位设置页面
     * 
     * @return String
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String initManager()
    {
        return "/schedule/scheduleSettings/postSettings";
    }
    
    
    /**
     * 
     * 初始化岗位信息
     * @param model
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value="/getPostList", method = RequestMethod.POST)
    public ResultPage getPostList(ModelMap model,HttpServletRequest request,@RequestBody Map<String,Object> reqParam) 
    {
        List<PostBean> list = postService.getPostSettingsList(reqParam);
        return DataFormat.formatPageData(list) ;
    }
    
    /**
     * 
     * 新增岗位
     * @param request
     * @param postBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/addPost", method = RequestMethod.POST)
    public ResultData addPost(HttpServletRequest request, PostBean postBean) 
    {
        
        //判断岗位名称是否重复
        int count = postService.checkPostNameIsExitst(postBean.getPostName());
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
            postBean.setCreater(userAccount);
            int row = postService.addPostSettings(postBean);
            //日志添加
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "新增[" + postBean.getPostName() + "]成功");
            return DataFormat.formatAdd(row);
        }
        else 
        {
            //异常提示
            //日志添加
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "新增[" + postBean.getPostName() + "]失败");
            return DataFormat.formatFail("岗位名称重复", null);
        }
    }
    
    
    /**
     * 
     * 修改岗位
     * @param request
     * @param postBean
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/editPost", method = RequestMethod.POST)
    public ResultData editPost(HttpServletRequest request, PostBean postBean) 
    {
        //修改时，未修改岗位名称的情况
        PostBean objPostBean = postService.getPostById(postBean.getPostId());
        if (postBean.getPostName().equals(objPostBean.getPostName())) 
        {
            //岗位名称未做修改，不验证岗位名是否重复
            int row = postService.updatePostSettings(postBean);
            //日志添加
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "修改[" + postBean.getPostName() + "]成功");
            return DataFormat.formatUpd(row);
        }
        else 
        {
            //验证岗位名称是否重复
            int count = postService.checkPostNameIsExitst(postBean.getPostName());
            if (count == 0) 
            {
                int row = postService.updatePostSettings(postBean);
                //日志添加
                addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "修改[" + postBean.getPostName() + "]成功");
                return DataFormat.formatUpd(row);
            }
            else 
            {
                //异常提示
                //日志添加
                addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "修改[" + postBean.getPostName() + "]失败,岗位名称重复");
                return DataFormat.formatFail("岗位名称重复", null);
            }
        }
    }
    
    
    /**
     * 
     * 获取当个岗位信息
     * @param postId
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/getPostById", method = RequestMethod.POST)
    public ResultData getPostById(Integer postId) 
    {
        //先判断当前岗位是否已存在
        PostBean postBean = postService.getPostById(postId);
        if (null == postBean) 
        {
            return DataFormat.formatFail("此记录不存在", null);
        } 
        else 
        {
            return DataFormat.formatSucc(postBean);
        }
    }
    
    
    /**
     * 
     * 删除岗位信息
     * @param postId
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value="/deletePost", method = RequestMethod.POST)
    public ResultData deletePost(HttpServletRequest request, String postId) 
    {
        //判断岗位是否已被使用
        boolean flag = postService.checkPostIsExitst(postId);
        if (true == flag) 
        {
            //未使用
            int row = postService.delPostSettings(postId);
            //日志添加
            addLogSucc(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "删除岗位编号为" + postId + "成功");
            return DataFormat.formatDel(row);
        }
        else
        {
            //日志添加
            addLogFail(request, SysConstant.SCHEDULEMGR, SysConstant.岗位设置, "删除岗位编号为" + postId + "失败,岗位已被使用");
            //已被使用
            return DataFormat.formatFail("此岗位已被使用", null);
        }
        
    }
}
