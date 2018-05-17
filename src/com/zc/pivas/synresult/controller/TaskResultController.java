package com.zc.pivas.synresult.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.pivas.synresult.bean.TaskResultBean;
import com.zc.pivas.synresult.service.TaskResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 同步结果查询
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/taskRsut")
public class TaskResultController extends SdBaseController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    private TaskResultService taskResultService;

    /***
     *
     * 页面跳转
     * @return 查询页面
     */
    @RequestMapping(value = "/initTask")
    @RequiresPermissions(value = {"PIVAS_MENU_349"})
    public String initTaskResult() {
        return "pivas/taskResultList";
    }

    /***
     * 获取数据
     *
     * @param bean 查询，参数
     * @param jquryStylePaging 分页参数
     * @return json字符串
     * @throws Exception 系统异常
     */
    @RequestMapping(value = "/getTaskResultList", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_350"})
    public String getCheckTypeList(TaskResultBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        String[] taskNameArray = bean.getTaskNames();
        if (taskNameArray != null) {
            for (int i = 0; i < taskNameArray.length; ++i) {
                taskNameArray[i] = DefineStringUtil.escapeAllLike(taskNameArray[i]);
            }
        }

        JqueryStylePagingResults<TaskResultBean> inpatientAreaBeanPagingResults =
                taskResultService.getTaskResultLsit(bean, jquryStylePaging);

        return new Gson().toJson(inpatientAreaBeanPagingResults);
    }

    @Autowired
    public void setTaskResultService(TaskResultService taskResultService) {
        this.taskResultService = taskResultService;
    }

}
