package com.zc.pivas.synresult.service.impl;

import com.zc.base.common.util.DateUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.synresult.bean.TaskResultBean;
import com.zc.pivas.synresult.dao.TaskResultDAO;
import com.zc.pivas.synresult.service.TaskResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步结果实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("taskResultService")
public class TaskResultServiceImpl implements TaskResultService {
    //任务类型，0,定时任务 1,一次性任务
    private final static String PIVAS_SYNSETTING_TASKTYPE = "pivas.synsetting.tasktype";

    /**
     * 内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
     */
    private final static String PIVAS_SYNSETTING_TASKCONTENTTYPE = "pivas.synsetting.taskcontenttype";

    /**
     * 执行结果  0成功  1失败
     */
    private final static String PIVAS_COMMON_SUSSESSORFAIL = "pivas.common.successorfail";

    private TaskResultDAO taskResultDAO;

    /***
     * 查询数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    public JqueryStylePagingResults<TaskResultBean> getTaskResultLsit(TaskResultBean bean,
                                                                      JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns =
                new String[]{"taskID", "taskName", "taskTypeName", "taskResultName", "taskStartTime", "taskStopTime",
                        "taskContentTypeName"};
        JqueryStylePagingResults<TaskResultBean> pagingResults = new JqueryStylePagingResults<TaskResultBean>(columns);

        // 总数
        int total;
        List<TaskResultBean> taskResultList = taskResultDAO.getTaskResultList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(taskResultList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            taskResultList = taskResultDAO.getTaskResultList(bean, jquryStylePaging);
        }

        //获取状态对应的国际化
        if (DefineCollectionUtil.isNotEmpty(taskResultList)) {
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            for (TaskResultBean taskResult : taskResultList) {
                // 任务类型，0,定时任务 1,一次性任务 
                taskResult.setTaskTypeName(DictFacade.getName(PIVAS_SYNSETTING_TASKTYPE,
                        DefineStringUtil.parseNull(taskResult.getTaskType()),
                        lang));

                // 任务执行内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
                taskResult.setTaskContentTypeName(DictFacade.getName(PIVAS_SYNSETTING_TASKCONTENTTYPE,
                        DefineStringUtil.parseNull(taskResult.getTaskContentType()),
                        lang));

                // 同步执行结果
                taskResult.setTaskResultName(DictFacade.getName(PIVAS_COMMON_SUSSESSORFAIL,
                        DefineStringUtil.parseNull(taskResult.getTaskResult()),
                        lang));

                taskResult.setTaskStartTime(DateUtil.format(taskResult.getTaskExecStartTime(), "yyyy-MM-dd HH:mm:ss"));

                taskResult.setTaskStopTime(DateUtil.format(taskResult.getTaskExecStopTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        }

        total = taskResultDAO.getTaskResultTotal(bean);

        pagingResults.setDataRows(taskResultList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    @Autowired
    public void setTaskResultDAO(TaskResultDAO taskResultDAO) {
        this.taskResultDAO = taskResultDAO;
    }

    /**
     * 新增数据
     *
     * @param bean
     */
    @Override
    public void addTaskResult(TaskResultBean bean) {
        taskResultDAO.addTaskResult(bean);

    }

    /**
     * 修改数据
     *
     * @param bean
     */
    @Override
    public void updateTaskResult(TaskResultBean bean) {
        taskResultDAO.updateTaskResult(bean);

    }

}
