package com.zc.pivas.printlabel.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.printlabel.entity.BottleLabel;
import com.zc.pivas.printlabel.entity.PrintLabelBean;

import java.util.List;
import java.util.Map;

/**
 * 打印瓶签Service
 *
 * @author kunkka
 * @version 1.0
 */
public interface PrintLabelService {
    /**
     * 打印瓶签信息
     *
     * @param bottleLabel
     * @return 文件名或null(null时说明没有查询到数据)
     * @throws Exception
     */
    public String printBottleLabel(BottleLabel bottleLabel, User currUser)
            throws Exception;

    /**
     * 打印纺排单
     *
     * @param bottleLabel
     * @return 文件名或null(null时说明没有查询到数据)
     */
    String printYDList(BottleLabel bottleLabel, User currUser)
            throws Exception;

    /**
     * 药物接收单，统计单批次按药品分类的使用量
     *
     * @param bottleLabel
     * @return
     */
    List<BottleLabel> queryYDReciverCount(BottleLabel bottleLabel);

    /**
     * 药物接收单明细
     *
     * @param bottleLabel
     * @return
     */
    List<BottleLabel> queryYDReciverDetail(BottleLabel bottleLabel);

    /**
     * 打印药物接收单
     *
     * @param bottleLabel
     * @return 文件名或null(null时说明没有查询到数据)
     */
    String queryYDReciverList(BottleLabel bottleLabel, User currUser, boolean isPrint)
            throws Exception;

    /**
     * 设置瓶签信息
     *
     * @param bottleInfo
     */
    void setPQBottle(Map<String, Object> bottleInfo);

    void createBottleLabel(PrescriptionMain ydMain, String account) throws Exception;

    List<Map<String, String>> qryBottleLabel(BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
            throws Exception;


    List<PrintLabelBean> selectBottleLabel(PrintLabelBean printLabelBean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    List<PrintLabelBean> selectBottleLabelHistory(PrintLabelBean printLabelBean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    public String printBottleLabelTWO(PrintLabelBean printLabelBean, User currUser)
            throws Exception;

    public String changePrintMark(PrintLabelBean printLabelBean, User currUser, String ipAddress)
            throws Exception;

    public String printStatisicInfo(PrintLabelBean printLabelBean, User currUser)
            throws Exception;

    public List<String> printMedicTotSingCount(Map<String, String> map);

    /**
     * 删除瓶签文件
     */
    public void delPrintFile();

    /**
     * 根据条件查询药品统计
     *
     * @param param currUser
     * @return 统计单的文件路径
     * @throws Exception
     */
    public String callMedicStatisticHtml(Map<String, Object> param, User currUser) throws Exception;

    public String queryYDReciverListFour(BottleLabel bottleLabel, User currentUser, boolean isPrint) throws Exception;

}
