package com.zc.pivas.synresult.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.synresult.bean.TaskResultBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同步设置
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("taskResultDAO")
public interface TaskResultDAO {
    /***
     * 分页查询
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<TaskResultBean> getTaskResultList(@Param("taskResult")
                                                   TaskResultBean bean, @Param("paging")
                                                   JqueryStylePaging jqueryStylePaging);

    /**
     * 分页总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getTaskResultTotal(@Param("taskResult")
                                   TaskResultBean bean);

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
