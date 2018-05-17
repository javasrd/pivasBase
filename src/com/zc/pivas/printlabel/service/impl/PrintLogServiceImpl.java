package com.zc.pivas.printlabel.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.printlabel.entity.PrintLogBean;
import com.zc.pivas.printlabel.repository.PrintLoglDao;
import com.zc.pivas.printlabel.service.PrintLogService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 瓶签打印日志接口实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("printLogService")
public class PrintLogServiceImpl implements PrintLogService {
    private Logger logger = LoggerFactory.getLogger(PrintLogServiceImpl.class);

    @Resource
    private PrintLoglDao printLoglDao;

    @Override
    public JqueryStylePagingResults<PrintLogBean> getPrintLogList(PrintLogBean printLog, JqueryStylePaging jqueryStylePaging) throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"gid", "opreName", "printIndex", "printDate", "printCondition"};
        JqueryStylePagingResults<PrintLogBean> pagingResults = new JqueryStylePagingResults<PrintLogBean>(columns);

        List<PrintLogBean> printLogList = printLoglDao.qryPrintLog(printLog, jqueryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(printLogList) && jqueryStylePaging.getPage() != 1) {
            jqueryStylePaging.setPage(jqueryStylePaging.getPage() - 1);
            printLogList = printLoglDao.qryPrintLog(printLog, jqueryStylePaging);
        }


        int total = getPrintLogTotal(printLog);

        pagingResults.setDataRows(printLogList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jqueryStylePaging.getPage());
        return pagingResults;
    }

    @Override
    public int getPrintLogTotal(@Param("condition")
                                        PrintLogBean printLog) {
        return printLoglDao.getPrintLogTotal(printLog);
    }


    @Override
    public void addPrintLog(PrintLogBean printLogBean)
            throws Exception {
        printLoglDao.addPrintLog(printLogBean);
    }
}
