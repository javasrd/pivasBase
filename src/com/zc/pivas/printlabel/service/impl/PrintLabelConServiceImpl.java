package com.zc.pivas.printlabel.service.impl;

import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sc.modules.batch.repository.BatchDao;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.medicaments.service.MedicamentsService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import com.zc.pivas.printlabel.entity.PrintLabelConBean;
import com.zc.pivas.printlabel.repository.PrintLabelConDao;
import com.zc.pivas.printlabel.service.PrintLabelConService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 瓶签打印接口实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("printLabelConService")
public class PrintLabelConServiceImpl implements PrintLabelConService {
    private Logger logger = LoggerFactory.getLogger(PrintLabelConServiceImpl.class);

    /**
     * 打印状态 0：是 1：否
     */
    private static final int PRINT_STATE_YES = 1;

    /**
     * 打印状态 0：是 1：否
     */
    private static final int PRINT_STATE_NO = 0;

    /**
     * 用药日期： 0今日 1明日 2昨日
     */
    private static final int YYRQ_TODAY = 0;

    /**
     * 用药日期： 0今日 1明日 2昨日
     */
    private static final int YYRQ_TOMORROW = 1;

    /**
     * 打印瓶签条件设置
     */
    @Resource
    private PrintLabelConDao printLabelConDao;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    @Resource
    private MedicLabelDao medicLabelDao;

    /**
     * 批次信息
     */
    @Resource
    private BatchDao batchDao;

    /**
     * 药品
     */
    @Resource
    private MedicamentsService medicamentsService;

    @Resource
    private InpatientAreaService inpatientAreaService;

    @Override
    public void insert(PrintLabelConBean bean) {
        printLabelConDao.insert(bean);
    }

    @Override
    public void updatePrintLabelCon(PrintLabelConBean bean) {
        printLabelConDao.updatePrintLabelCon(bean);
    }

    @Override
    public void delPrintLabelCon(String[] gids) {
        printLabelConDao.delPrintLabelCon(gids);
    }

    @Override
    public JqueryStylePagingResults<PrintLabelConBean> queryPrintLabelConList(PrintLabelConBean bean,
                                                                              JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns =
                new String[]{"id", "name", "yyrq", "batchid", "medicCategory", "mediclabel", "printState", "medical", "deptName"};
        JqueryStylePagingResults<PrintLabelConBean> pagingResults =
                new JqueryStylePagingResults<PrintLabelConBean>(columns);

        // 超级管理员
        List<PrintLabelConBean> printLabelConList = printLabelConDao.queryPrintLabelCon(bean, jquryStylePaging);
        int total = printLabelConDao.queryPrintLabelCon(bean, new JqueryStylePaging()).size();
        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(printLabelConList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            printLabelConList = printLabelConDao.queryPrintLabelCon(bean, jquryStylePaging);
        }

        if (DefineCollectionUtil.isNotEmpty(printLabelConList)) {
            String[] batchIDs;
            String batchName = "";
            for (PrintLabelConBean printLabelCon : printLabelConList) {
                if (null != printLabelCon.getPrintState()) {
                    switch (printLabelCon.getPrintState()) {
                        case PRINT_STATE_YES:
                            printLabelCon.setPrintStateName("未打印");
                            break;
                        case PRINT_STATE_NO:
                            printLabelCon.setPrintStateName("已打印");
                            break;
                        default:
                            printLabelCon.setPrintStateName("未打印");
                            break;
                    }
                }

                if (StrUtil.isNotNull(printLabelCon.getBatchid())) {
                    batchIDs = printLabelCon.getBatchid().split(",");
                    batchName = "";
                    for (String batch : batchIDs) {
                        batchName = batchDao.selectByPrimaryKey(Long.valueOf(batch)).getName() + "," + batchName;
                    }
                    printLabelCon.setBatchName(batchName.substring(0, batchName.length() - 1));
                }

                if (StrUtil.isNotNull(printLabelCon.getMedicCategoryID())) {
                    String[] medicCategoryIDs = printLabelCon.getMedicCategoryID().split(",");
                    String medicCategory = "";
                    MedicCategory category = null;
                    for (String medicCategoryID : medicCategoryIDs) {
                        category = new MedicCategory();
                        category.setCategoryId(Long.valueOf(medicCategoryID));
                        medicCategory = medicCategoryDao.listMedicCategory(category, new JqueryStylePaging()).get(0).getCategoryName() + "," + medicCategory;

                    }
                    printLabelCon.setMedicCategory(medicCategory.substring(0, medicCategory.length() - 1));
                }

                if (StrUtil.isNotNull(printLabelCon.getMedicLabelID())) {
                    String[] mediclabelIDs = printLabelCon.getMedicLabelID().split(",");
                    String mediclabelName = "";
                    MedicLabel mediclabel = null;
                    for (String mediclabelID : mediclabelIDs) {
                        mediclabel = new MedicLabel();
                        mediclabel.setLabelId(Long.valueOf(mediclabelID));
                        mediclabelName = medicLabelDao.listMedicLabel(mediclabel, new JqueryStylePaging()).get(0).getLabelName() + "," + mediclabelName;

                    }
                    printLabelCon.setMediclabel(mediclabelName.substring(0, mediclabelName.length() - 1));
                }

                if (StrUtil.isNotNull(printLabelCon.getMedicalID())) {
                    String[] medicalIDs = printLabelCon.getMedicalID().split(",");
                    String medicalIDName = "";
                    for (String medicalID : medicalIDs) {
                        medicalIDName = medicamentsService.getMediclByCode(medicalID).getMedicamentsName() + "," + medicalIDName;
                    }
                    printLabelCon.setMedical(medicalIDName.substring(0, medicalIDName.length() - 1));
                }

                if (StrUtil.isNotNull(printLabelCon.getYyrq())) {
                    switch (NumberUtils.toInt(printLabelCon.getYyrq())) {
                        case YYRQ_TODAY:
                            printLabelCon.setYyrq("今日");
                            break;
                        case YYRQ_TOMORROW:
                            printLabelCon.setYyrq("明日");
                            break;
                        default:
                            printLabelCon.setYyrq("昨日");
                            break;
                    }
                }

                if (StrUtil.isNotNull(printLabelCon.getDeptCode())) {
                    String[] deptCodes = printLabelCon.getDeptCode().split(",");
                    String deptName = "";

                    InpatientAreaBean inpatientArea = null;
                    for (String deptCode : deptCodes) {
                        inpatientArea = new InpatientAreaBean();
                        inpatientArea.setDeptCode(deptCode);
                        deptName = inpatientAreaService.getInpatientAreaBean(inpatientArea).getDeptName() + "," + deptName;
                    }
                    printLabelCon.setDeptName(deptName.substring(0, deptName.length() - 1));
                }
            }
        }

        pagingResults.setDataRows(printLabelConList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 查询打印瓶签条件设置
     * @param bean
     * @return
     */
    @Override
    public List<PrintLabelConBean> queryPrintLabelCon(PrintLabelConBean bean) {
        return printLabelConDao.queryPrintLabelCon(bean, new JqueryStylePaging());
    }

    /**
     * 查询总数
     * @param bean
     * @return
     */
    @Override
    public int getPrintLabelConTotal(PrintLabelConBean bean) {
        return printLabelConDao.getPrintLabelConTotal(bean);
    }

    @Override
    public boolean checkPrintLabelConName(PrintLabelConBean bean) {
        bean = printLabelConDao.getPrintLabelConForUPdate(bean);

        if (null == bean) {
            return false;
        }

        return true;
    }

    @Override
    public int getCountOrder(PrintLabelConBean bean) {
        return printLabelConDao.getCountOrder(bean);
    }

}
