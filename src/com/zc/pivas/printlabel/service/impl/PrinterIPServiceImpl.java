package com.zc.pivas.printlabel.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.printlabel.entity.PrinterIPBean;
import com.zc.pivas.printlabel.repository.PrinterIPDao;
import com.zc.pivas.printlabel.service.PrinterIPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 打印机配置 接口实现类
 *
 * @author kunkka
 * @version 1.0
 */

@Service("printerIPService")
public class PrinterIPServiceImpl implements PrinterIPService {
    private Logger logger = LoggerFactory.getLogger(PrinterIPServiceImpl.class);

    @Resource
    private PrinterIPDao printerIPDao;

    @Override
    public JqueryStylePagingResults<PrinterIPBean> queryPrinterIPList(PrinterIPBean bean, JqueryStylePaging jqueryStylePaging) throws Exception {
        String[] columns = new String[]{"gid", "compName", "ipAddr", "prinName"};
        JqueryStylePagingResults<PrinterIPBean> pagingResults = new JqueryStylePagingResults<PrinterIPBean>(columns);

        List<PrinterIPBean> list = printerIPDao.queryPrinterIPList(bean, jqueryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(list) && jqueryStylePaging.getPage() != 1) {
            jqueryStylePaging.setPage(jqueryStylePaging.getPage() - 1);
            list = printerIPDao.queryPrinterIPList(bean, jqueryStylePaging);
        }

        int total = getPrinterIPCounts(bean);

        pagingResults.setDataRows(list);
        pagingResults.setTotal(total);
        pagingResults.setPage(jqueryStylePaging.getPage());
        return pagingResults;
    }

    @Override
    public PrinterIPBean queryPrinterIPById(String id)
            throws Exception {
        // TODO Auto-generated method stub

        return printerIPDao.queryPrinterIPById(id);
    }


    /**
     * 添加打印机配置项
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public int addPrinterIP(PrinterIPBean bean)
            throws Exception {
        return printerIPDao.addPrinterIP(bean);
    }

    /**
     * 更新打印机配置项
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public int updatePrinterIP(PrinterIPBean bean)
            throws Exception {
        return printerIPDao.updatePrinterIP(bean);
    }

    /**
     * 删除打印机配置项
     * @param gids
     * @return
     * @throws Exception
     */
    @Override
    public int delPrinterIP(String[] gids)
            throws Exception {
        return printerIPDao.delPrinterIP(gids);
    }

    /**
     * 查询打印机配置项总数
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public int getPrinterIPCounts(PrinterIPBean bean)
            throws Exception {
        return printerIPDao.getPrinterIPCounts(bean);
    }

    /**
     * 判断主机名/ip/打印机名称是否存在
     * @param bean 查询条件
     * @return
     * @throws Exception
     */
    @Override
    public List<PrinterIPBean> checkPrinterIP(PrinterIPBean bean)
            throws Exception {
        return printerIPDao.checkPrinterIP(bean);
    }

}
