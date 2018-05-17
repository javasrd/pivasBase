package com.zc.schedule.system.user.controller;

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
import org.springframework.web.servlet.support.RequestContextUtils;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.system.user.entity.User;
import com.zc.schedule.system.user.service.ScheduleUserService;

/**
 * 人员控制类
 *
 * @author jagger
 * @version v1.0
 */
@Controller(value = "scheduleUserController")
@RequestMapping("/user")
public class UserController extends BaseController {
    private static Logger logger = Logger.getLogger(UserController.class);

    @Resource(name = "userServiceImpl")
    private ScheduleUserService<User> userService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "/schedule/user/main";
    }

    /**
     * 用户列表
     *
     * @param model
     * @param request
     * @param reqParam
     * @return ResultPage
     */
    @ResponseBody
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public ResultPage userList(ModelMap model, HttpServletRequest request, @RequestBody Map<String, Object> reqParam) {

    	
    	/*获取语言，设置语言*/
        String language = RequestContextUtils.getLocale(request).getLanguage();
        HttpSession session = request.getSession();
        Object langu = session.getAttribute(SysConstant.sessionName.language);
        logger.debug("language=" + langu);
        if (langu == null) {
            session.setAttribute(SysConstant.sessionName.language, language);
        }
        Object langu2 = session.getAttribute(SysConstant.sessionName.language);
        logger.debug("language2=" + langu2);


//    	Map<String,List<Dict>> dicMap1 = getDicMapByCateS(request,DicUtil.dicName.ageUnit);
//    	System.out.println(dicMap1);

//    	List<Dict> dicList = getDicListByName(request, DicUtil.dicName.ageUnit);
//    	System.out.println(dicList);

    	/*addLogSucc(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试成功日志");
    	addLogFail(request, SysConstant.subSys.排版管理, SysConstant.subModel.排版管理.个人设置, "测试失败日志");*/
    	
    	
    	/* 分页样例
    	PageInfo page = new PageInfo(reqParam);
        List<User> list = userService.qryList(reqParam,page);
        return DataFormat.formatPageData(list,page) ;
    	*/
    	/* 一页展示所有数据的分页数据返回
        List<User> list = userService.qryList(reqParam);
        return DataFormat.formatPageData(list) ;
    	*/
        PageInfo page = new PageInfo(reqParam);
        List<User> list = userService.qryList(reqParam, page);
        return DataFormat.formatPageData(list, page);
    }

    /**
     * 添加人员
     *
     * @param request
     * @param user
     * @return ResultData
     */
    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResultData addUser(HttpServletRequest request, User user) {
        /*
        User user = new User();
        user.setAccount(MyString.getObjString(reqParam.get("account")));
        user.setTelephone(MyString.getObjString(reqParam.get("telephone")));
        user.setDelFlag(MyInt.getObjInt(reqParam.get("telephone"),0));
        */
        int row = userService.insert(user);
        return DataFormat.formatAdd(row);
    }

    /**
     * 修改页面
     *
     * @return String
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit() {
        return "/schedule/user/edit";
    }

}
