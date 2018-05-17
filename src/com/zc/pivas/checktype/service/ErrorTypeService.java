package com.zc.pivas.checktype.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.checktype.bean.ErrorTypeBean;

/**
 * 审方错误类型
 *
 * @author kunkka
 * @version 1.0
 */
public interface ErrorTypeService {
    /***
     * 分页查询审方错误类型
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<ErrorTypeBean> getErrorTypeLsit(ErrorTypeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加审方错误类型
     *
     * @param bean 审方错误类型
     */
    void addErrorType(ErrorTypeBean bean);

    /**
     * 修改审方错误类型
     */
    void updateErrorType(ErrorTypeBean bean);

    /***
     * 删除审方错误类型
     * @param gid 主键id
     */
    void delErrorType(String gid);

    /**
     * 根据名称查询审核类型
     *
     * @return 审核错误类型
     */
    ErrorTypeBean getErrorType(ErrorTypeBean bean);

    /**
     * 修改类型的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkErrorTypeName(ErrorTypeBean bean);

    /**
     * 检查错误类型是否已被使用
     *
     * @return
     */
    int checkErrorTypeUsed(String gid);
}
