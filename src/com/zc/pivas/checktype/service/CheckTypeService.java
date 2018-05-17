package com.zc.pivas.checktype.service;


import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.checktype.bean.CheckTypeBean;

/**
 * 核对类型
 *
 * @author kunkka
 * @version 1.0
 */
public interface CheckTypeService {
    /***
     * 分页查询核对类型表数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<CheckTypeBean> getCheckTypeLsit(CheckTypeBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加核对类型
     *
     * @param bean 核对类型
     */
    void addCheckType(CheckTypeBean bean);

    /**
     * 修改核对类型
     *
     * @param bean 需要修改的数据
     */
    void updateCheckType(CheckTypeBean bean);

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    CheckTypeBean getCheckType(CheckTypeBean bean);

    /***
     * 删除核对类型
     * @param gid 主键id
     */
    void delCheckType(String gid);

    /**
     * 修改类型的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkNameIsExitst(CheckTypeBean bean);

    /**
     * 修改类型的时候判断顺序id是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkOrderIDIsExitst(CheckTypeBean bean);

    /**
     * 修改类型的时候判断和对类型是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkTypeIsExitst(CheckTypeBean bean);
}
