package com.zc.pivas.measureunit.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.pivas.measureunit.bean.MeasureUnitBean;
import com.zc.pivas.measureunit.service.MeasureUnitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 计量单位
 *
 * @author  cacabin
 * @version  1.0
 */
@Controller
@RequestMapping(value = "/measureUnit")
public class MeasureUnitController extends SdBaseController
{
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;
    
    private MeasureUnitService measureUnitService;
    
    /***
     * 
     *初始化角色查询页面
     * @return 角色查询页面
     */
    @RequestMapping(value = "/initMeasureUnit")
    @RequiresPermissions(value = {"PIVAS_MENU_261"})
    public String initMeasureUnit()
    {
        return "pivas/MeasureUnitList";
    }
    
    /***
     * 查询所有角色数据
     * 
     * @param bean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/measureUnitList", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_262"})
    public String getMeasureUnitList(MeasureUnitBean bean, JqueryStylePaging jquryStylePaging) throws Exception
    {
        JqueryStylePagingResults<MeasureUnitBean> inpatientAreaBeanPagingResults =
            measureUnitService.getMeasureUnitLsit(bean, jquryStylePaging);
        
        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }
    
    /**
     * 添加核对类型
     * 
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception 
     */
    @RequestMapping(value = "/addMeasureUnit", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_265"})
    public String addMeasureUnit(MeasureUnitBean bean) throws Exception
    {
        // 判断编码是否存在
        boolean checkMeasureUnitCode = measureUnitService.checkMeasureUnitCode(bean);
        
        try
        {
            if (checkMeasureUnitCode)
            {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CODE_REPEAT, null, null);
            }
            else
            {
                measureUnitService.addMeasureUnit(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.MEASUREMENT_UNIT,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.measureunit.tip.add", new String[] {bean.getCode()}),
                    true);
            }
            
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        }
        catch (Exception e)
        {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.MEASUREMENT_UNIT,
                AmiLogConstant.BRANCH_SYSTEM.CF,
                getMessage("log.measureunit.tip.add", new String[] {bean.getCode()}),
                false);
            throw e;
        }
    }
    
    /***
     * 
     * 初始化
     * @param bean 参数
     * @return json字符串
     */
    @RequestMapping(value = "/initUpdateMeasureUnit", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_253"})
    public String initUpdateMeasureUnit(MeasureUnitBean bean)
    {
        MeasureUnitBean measureUnit = measureUnitService.getMeasureUnit(bean);
        if (measureUnit == null)
        {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        return new Gson().toJson(measureUnit);
    }
    
    /**
     * 修改核对类型
     * 
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception 
     */
    @RequestMapping(value = "/updateMeasureUnit", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_253"})
    public String updateMeasureUnit(MeasureUnitBean bean)
        throws Exception
    {
        // 判断名称是否存在
        boolean checkMeasureUnitCode = measureUnitService.checkMeasureUnitCode(bean);
        
        try
        {
            if (checkMeasureUnitCode)
            {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CODE_REPEAT, null, null);
            }
            else
            {
                measureUnitService.updateMeasureUnit(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.MEASUREMENT_UNIT,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.measureunit.tip.update", new String[] {bean.getCode()}),
                    true);
                
                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        }
        catch (Exception e)
        {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.MEASUREMENT_UNIT,
                AmiLogConstant.BRANCH_SYSTEM.CF,
                getMessage("log.measureunit.tip.update", new String[] {bean.getCode()}),
                false);
            throw e;
        }
    }
    
    /**
     * 
     * 删除计量单位
     * 
     * @param gid 主键id
     * @return 操作结果
     * @throws Exception 
     */
    @RequestMapping(value = "/delMeasureUnit", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_266"})
    public String delMeasureUnit(String gid)
        throws Exception
    {
        try
        {
            measureUnitService.delMeasureUnit(gid);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.MEASUREMENT_UNIT,
                AmiLogConstant.BRANCH_SYSTEM.CF,
                getMessage("log.measureunit.tip.del", new String[] {gid}),
                true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        }
        catch (Exception e)
        {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.MEASUREMENT_UNIT,
                AmiLogConstant.BRANCH_SYSTEM.CF,
                getMessage("log.measureunit.tip.del", new String[] {gid}),
                false);
            throw e;
        }
    }
    
    @Autowired
    public void setMeasureUnitService(MeasureUnitService measureUnitService)
    {
        this.measureUnitService = measureUnitService;
    }
}
