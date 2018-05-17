package com.zc.pivas.printlabel.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.printlabel.entity.PrintLogBean;

/**
 * 瓶签日志 Service
 *
 * @author kunkka
 * @version 1.0
 */
public interface PrintLogService {
    /**
     * 添加打印的操作记录
     */
    void addPrintLog(PrintLogBean printLogBean) throws Exception;

    /**
     * 打印日志列表分页查询
     *
     * @param jqueryStylePaging 分页参数
     * @return 病人列表
     */
    JqueryStylePagingResults<PrintLogBean> getPrintLogList(PrintLogBean printLog, JqueryStylePaging jqueryStylePaging) throws Exception;

    /**
     * 分页查询总数
     *
     * @return 页码
     */
    int getPrintLogTotal(PrintLogBean printLog);
}
