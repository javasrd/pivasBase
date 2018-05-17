package com.zc.schedule.product.personnelgroup.controller;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.product.personnelgroup.service.PersonnelGroupService;

/**
 * 员工分组控制器
 * 
 * @author  Justin
 * @version  v1.0
 */
@Controller
@RequestMapping("userGroup")
public class PersonnelGroupController extends BaseController
{
    
    /**
     * 分组管理接口
     */
    @Resource
    private PersonnelGroupService personnelGroupService;
    
    @RequestMapping("index")
    public String init(HttpServletRequest request)
    {
        return "schedule/personnelgroup/index";
    }
    
    /**
     * 获取指定周的分组信息
     * 
     * @param startDate
     * @return ResultData
     * @throws ParseException 
     */
    @RequestMapping("getGroupInfos")
    @ResponseBody
    public ResultData getGroupInfos(String startDate)
        throws ParseException
    {
        return personnelGroupService.getGroupInfos(startDate);
    }
    
    /**
     * 
     * 保存新的分组或修改分组名称
     * @param opr
     * @param groupId
     * @param groupName
     * @param startDate
     * @param endDate
     * @return ResultData
     */
    @RequestMapping("saveGroup")
    @ResponseBody
    public ResultData saveGroup(String opr, Long groupId, String groupName, String startDate, String endDate)
    {
        return personnelGroupService.saveGroup(opr, groupId, groupName, startDate, endDate);
    }
    
    /**
     * 
     * 保存人员分组关联关系
     * @param startDate
     * @param endDate
     * @param datas
     * @return ResultData
     */
    @RequestMapping("saveGroupInfos")
    @ResponseBody
    public ResultData saveGroupInfos(String startDate, String endDate, String datas)
    {
        return personnelGroupService.saveGroupInfos(startDate, endDate, datas);
    }
    
    /**
     * 
     * 删除分组
     * @param groupId
     * @param startDate
     * @param endDate
     * @return ResultData
     */
    @RequestMapping("del")
    @ResponseBody
    public ResultData del(Long groupId, String startDate, String endDate)
    {
        return personnelGroupService.del(groupId, startDate, endDate);
    }
    
}
