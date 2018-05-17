package com.zc.pivas.printlabel.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.printlabel.entity.PrintLabelConBean;

import java.util.List;

/**
 * 打印瓶签Service
 *
 * @author kunkka
 * @version 1.0
 */
public interface PrintLabelConService {
    /***
     * 分页查询表数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     */
    JqueryStylePagingResults<PrintLabelConBean> queryPrintLabelConList(PrintLabelConBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 查询打印瓶签条件设置
     *
     * @param bean
     * @return
     */
    public List<PrintLabelConBean> queryPrintLabelCon(PrintLabelConBean bean);

    /**
     * 插入打印瓶签条件设置
     *
     * @param bean
     */
    void insert(PrintLabelConBean bean);

    /**
     * 更新打印瓶签条件设置
     *
     * @param bean
     */
    void updatePrintLabelCon(PrintLabelConBean bean);

    /**
     * 删除打印瓶签条件设置
     */
    void delPrintLabelCon(String[] gids);

    /**
     * 分页查询总数
     *
     * @return 页码
     */
    int getPrintLabelConTotal(PrintLabelConBean bean);

    /**
     * 修改类型的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    boolean checkPrintLabelConName(PrintLabelConBean bean);

    int getCountOrder(PrintLabelConBean bean);
}
