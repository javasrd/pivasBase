package com.zc.pivas.synset.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.common.util.DateUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.pivas.synset.bean.SynSettingBean;
import com.zc.pivas.synset.service.SynSettingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 同步任务设置
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/synSet")
public class SynSettingController extends SdBaseController {
    /**
     * 启用
     */
    private final static String TASK_ENABLE = "0";

    /**
     * 停用
     */
    private final static String TASK_DISENABLE = "1";

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    private SynSettingService synSettingService;

    /***
     *
     * 查询初始化
     * @return 查询页面
     */
    @RequestMapping(value = "/initSynSet")
    @RequiresPermissions(value = {"PIVAS_MENU_342"})
    public String initSynSetting() {
        return "pivas/synSettingList";
    }

    /***
     * 查询
     *
     * @param bean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/synSetList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_345"})
    public String getCheckTypeList(SynSettingBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] taskNamesArray = bean.getTaskNames();
        if (taskNamesArray != null) {
            for (int i = 0; i < taskNamesArray.length; ++i) {
                taskNamesArray[i] = DefineStringUtil.escapeAllLike(taskNamesArray[i]);
            }
        }

        JqueryStylePagingResults<SynSettingBean> inpatientAreaBeanPagingResults =
                synSettingService.getSynSettingLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    /**
     * 增加同步设置
     *
     * @param bean 同步设置数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addSynSet", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_343"})
    public String addSynSetting(SynSettingBean bean)
            throws Exception {
        // 执行时间转换
        bean.setExecuteTime(new Timestamp(DateUtil.parse(bean.getTaskExecuteTime(), "yyyy-MM-dd HH:mm:ss").getTime()));

        // 判断同步名称是否存在
        boolean isNameExist = synSettingService.checkTaskNameIsExitst(bean);

        try {
            if (isNameExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            }

            else {
                synSettingService.addSynSetting(bean);
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.synsetting.tip.add", new String[]{bean.getTaskName()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.add", new String[]{bean.getTaskName()}),
                    false);
            throw e;
        }
    }

    /***
     * 修改同步前初始化数据
     *
     * @param bean 参数
     * @return json字符串
     */
    @RequestMapping(value = "/initUpdateSynSet", produces = "application/json")
    @ResponseBody
    public String initUpdateSynSetting(SynSettingBean bean) {
        SynSettingBean synSetting = synSettingService.getSynSetting(bean);
        if (synSetting == null) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
        synSetting.setTaskExecuteTime(DateUtil.format(synSetting.getExecuteTime(), "yyyy-MM-dd HH:mm:ss"));
        return new Gson().toJson(synSetting);
    }

    /**
     * 修改同步设置数据
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/updateSynSet", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_346"})
    public String updateSynSetting(SynSettingBean bean)
            throws Exception {
        // 执行时间转换
        bean.setExecuteTime(new Timestamp(DateUtil.parse(bean.getTaskExecuteTime(), "yyyy-MM-dd HH:mm:ss").getTime()));

        // 判断同步名称是否存在
        boolean isNameExist = synSettingService.checkTaskNameIsExitst(bean);

        try {
            if (isNameExist) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            }
            else {
                synSettingService.updateSynSetting(bean);

                // 启动
                if (0 == bean.getTaskStatus()) {
                    startSynSetting(bean.getTaskID());
                } else {
                    stopSynSetting(bean.getTaskID());
                }
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.synsetting.tip.update", new String[]{bean.getTaskName()}),
                        true);

                return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
            }
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.update", new String[]{bean.getTaskName()}),
                    false);
            throw e;
        }
    }

    /**
     * 删除一条同步设置
     *
     * @param taskID 主键id
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/delSynSet", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_344"})
    public String delSynSetting(String taskID)
            throws Exception {
        try {
            synSettingService.delSynSetting(taskID);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.del", new String[]{taskID}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.del", new String[]{taskID}),
                    false);
            throw e;
        }
    }

    /**
     * 启动一条同步
     *
     * @param taskID 主键id
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/startSynSet", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_347"})
    public String startSynSetting(String taskID)
            throws Exception {
        try {
            synSettingService.changeSynState(taskID, TASK_ENABLE);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.start", new String[]{taskID}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.start", new String[]{taskID}),
                    false);
            throw e;
        }
    }

    /**
     * 终止一条同步任务
     *
     * @param taskID 主键id
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/killsynSet", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_344"})
    public String stopSynSetting(String taskID)
            throws Exception {
        try {
            synSettingService.changeSynState(taskID, TASK_DISENABLE);
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.stop", new String[]{taskID}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.SYNCHRONOUS_TASK_SETTING,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.synsetting.tip.stop", new String[]{taskID}),
                    false);
            throw e;
        }
    }

    /**
     *  设置数据
     * @param synSettingService
     */
    @Autowired
    public void setXx(SynSettingService synSettingService) {
        this.synSettingService = synSettingService;
    }

}
