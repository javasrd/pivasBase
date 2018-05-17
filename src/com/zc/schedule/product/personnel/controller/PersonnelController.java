package com.zc.schedule.product.personnel.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.entity.ResultPage;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.Propertiesconfiguration;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.personnel.entity.Personnel;
import com.zc.schedule.product.personnel.service.PersonnelService;

import net.sf.json.JSONArray;
/**
 * 
 * 人员管理控制类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Controller
@RequestMapping("user")
public class PersonnelController extends BaseController
{
    
    /**
     * 人员管理接口
     */
    @Resource
    private PersonnelService personnelService;
    
    /**
     * 人员管理首页
     * 
     * @return String
     */
    @RequestMapping("index")
    public String init(ModelMap map)
    {
        //人员来源
        String resource = Propertiesconfiguration.getStringProperty("user.resource");
        map.addAttribute("resource", resource);
        
        return "/schedule/personnel/index";
    }
    
    /**
     * 分页查询人员列表
     * 
     * @param params
     * @return ResultPage
     */
    @RequestMapping("getPersonnels")
    @ResponseBody
    public ResultPage getPersonnels(@RequestBody Map<String, Object> params)
    {
        PageInfo page = new PageInfo(params);
        List<Personnel> personnelList = personnelService.getPersonnels(params, page);
        return DataFormat.formatPageData(personnelList, page);
    }
    
    /**
     * 保存新人员或者更新人员信息
     * 
     * @return ResultData
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultData save(Personnel personnel, HttpServletRequest request)
    {
        ResultData data = personnelService.save(personnel);
        String opr = "add".equals(personnel.getOpr()) ? "新增人员" : "修改人员";
        if (1 == data.getCode())
        {
            addLogSucc(request,
                SysConstant.SCHEDULEMGR,
                SysConstant.人员信息,
                opr + "[姓名=" + personnel.getUserName() + "]");
        }
        else
        {
            addLogFail(request,
                SysConstant.SCHEDULEMGR,
                SysConstant.人员信息,
                opr + "[姓名=" + personnel.getUserName() + "]：" + data.getMsg());
        }
        
        return data;
    }
    
    /**
     * 批量保存新人员
     * 
     * @return ResultData
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("batchSave")
    @ResponseBody
    public ResultData batchSave(String json, HttpServletRequest request)
    {
        JSONArray js = JSONArray.fromObject(json);
        List<Personnel> list = (List<Personnel>)JSONArray.toCollection(js, Personnel.class);
        
        StringBuffer userNames = new StringBuffer(128);
        for (Personnel p : list)
        {
            userNames.append(p.getUserName()).append(",");
        }
        try
        {
            ResultData data = personnelService.batchSave(list);
            
            addLogSucc(request,
                SysConstant.SCHEDULEMGR,
                SysConstant.人员信息,
                "批量添加[姓名=" + userNames.toString().substring(0, userNames.length()) + "]");
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            addLogFail(request,
                SysConstant.SCHEDULEMGR,
                SysConstant.人员信息,
                "批量添加[姓名=" + userNames.toString().substring(0, userNames.length() - 1) + "]：" + e.getMessage());
            return DataFormat.formatAdd(-1, e.getMessage());
        }
    }
    
    /**
     * 查询用户信息
     * 
     * @return ResultData
     */
    @RequestMapping("getById")
    @ResponseBody
    public ResultData getById(Long userId)
    {
        return personnelService.getById(userId);
    }
    
    /**
     * 删除用户
     * 
     * @return String
     */
    @RequestMapping("del")
    @ResponseBody
    public String del(String userIds,Map<String,Object> map)
    {
        return personnelService.del(userIds);
    }
    
    /**
     * 删除用户
     * 
     * @return String
     */
    @RequestMapping("delJson")
    @ResponseBody
    public String delJson(String userIds,Map<String,Object> map)
    {
        return personnelService.del(userIds);
    }
    
    /**
     * 切换参与排版状态
     * 
     * @return ResultData
     */
    @RequestMapping("changePartake")
    @ResponseBody
    private ResultData changePartake(Long userId, String partake)
    {
        if (userId == null || userId <= 0)
        {
            return DataFormat.formatFail("参数错误", null);
        }
        return personnelService.changePartake(userId, partake);
    }
}
