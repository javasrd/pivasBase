package com.zc.pivas.printlabel.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.printlabel.entity.PrintLabelConBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 打印瓶签DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("printLabelConDao")
public interface PrintLabelConDao {
    /**
     * 查询打印瓶签条件设置
     *
     * @param bean
     * @return
     */
    public List<PrintLabelConBean> queryPrintLabelCon(@Param("printLabelCon") PrintLabelConBean bean, @Param("paging")
            JqueryStylePaging jqueryStylePaging);

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
    void delPrintLabelCon(@Param("gids") String[] gid);

    /**
     * 分页查询总数
     *
     * @return 页码
     */
    int getPrintLabelConTotal(PrintLabelConBean bean);

    PrintLabelConBean getPrintLabelConForUPdate(@Param("printLabelCon") PrintLabelConBean bean);

    public int getCountOrder(PrintLabelConBean bean);
}
