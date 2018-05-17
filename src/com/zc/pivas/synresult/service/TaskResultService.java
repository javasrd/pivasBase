package com.zc.pivas.synresult.service;


import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.synresult.bean.TaskResultBean;

/**
 * 同步设置接口
 *
 * @author kunkka
 * @version 1.0
 */
public interface TaskResultService {
    /***
     * 查询数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<TaskResultBean> getTaskResultLsit(TaskResultBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 新增数据
     *
     * @param bean
     */
    void addTaskResult(TaskResultBean bean);

    /**
     * 修改数据
     *
     * @param bean
     */
    void updateTaskResult(TaskResultBean bean);
}
