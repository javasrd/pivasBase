package com.zc.pivas.printlabel.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.printlabel.entity.PrinterIPBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 打印机配置 DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("printerIPDao")
public interface PrinterIPDao {

    /**
     * 分页查询数据
     *
     * @param bean 对象
     * @return 分页数据
     */
    public List<PrinterIPBean> queryPrinterIPList(@Param("bean") PrinterIPBean bean, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * @return
     */
    public PrinterIPBean queryPrinterIPById(@Param("id") String id);

    /**
     * 添加打印机配置项
     *
     * @param bean
     */
    public int addPrinterIP(@Param("bean") PrinterIPBean bean) throws Exception;

    /**
     * 更新打印机配置项
     *
     * @param bean
     */
    public int updatePrinterIP(@Param("bean") PrinterIPBean bean) throws Exception;

    /**
     * 删除打印机配置项
     */
    public int delPrinterIP(@Param("gids") String[] gids) throws Exception;

    /**
     * 查询打印机配置项总数
     *
     * @param bean
     * @return
     */
    public int getPrinterIPCounts(@Param("bean") PrinterIPBean bean) throws Exception;

    /**
     * 判断主机名/ip/打印机名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public List<PrinterIPBean> checkPrinterIP(@Param("bean") PrinterIPBean bean) throws Exception;

    /**
     * 根据IP获取打印机名称
     *
     * @return
     */
    public String getAllPrinterByName(@Param("ipAddress") String ipAddress);

}
