package com.zc.pivas.printlabel.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.printlabel.entity.PrinterIPBean;

import java.util.List;

/**
 * 打印机配置 接口
 *
 * @author kunkka
 * @version 1.0
 */
public interface PrinterIPService {
    /***
     * 分页查询数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     */
    public JqueryStylePagingResults<PrinterIPBean> queryPrinterIPList(PrinterIPBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 查询某条数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public PrinterIPBean queryPrinterIPById(String id) throws Exception;

    /**
     * 添加打印机配置项
     *
     * @param bean
     */
    public int addPrinterIP(PrinterIPBean bean) throws Exception;

    /**
     * 更新打印机配置项
     *
     * @param bean
     */
    public int updatePrinterIP(PrinterIPBean bean) throws Exception;

    /**
     * 删除打印机配置项
     */
    public int delPrinterIP(String[] gids) throws Exception;

    /**
     * 查询打印机配置项总数
     *
     * @param bean
     * @return
     */
    public int getPrinterIPCounts(PrinterIPBean bean) throws Exception;

    /**
     * 判断主机名/ip/打印机名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public List<PrinterIPBean> checkPrinterIP(PrinterIPBean bean) throws Exception;


}
