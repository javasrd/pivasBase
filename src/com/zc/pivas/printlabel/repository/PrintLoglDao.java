package com.zc.pivas.printlabel.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.printlabel.entity.PrintLogBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 瓶签日志DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("printLoglDao")
public interface PrintLoglDao {

    /**
     * 添加打印的操作记录
     *
     */
    void addPrintLog(PrintLogBean printLogBean);

    /**
     * 打印日志列表分页查询
     *
     * @param jqueryStylePaging 分页参数
     * @return 病人列表
     */
    List<PrintLogBean> qryPrintLog(@Param("condition") PrintLogBean printLog, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     *
     * @return 页码
     */
    int getPrintLogTotal(@Param("condition") PrintLogBean printLog);
}
