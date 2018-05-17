package com.zc.pivas.drugway.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.drugway.bean.DrugWayBean;
import com.zc.pivas.drugway.dao.DrugWayDAO;
import com.zc.pivas.drugway.service.DrugWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用药途径
 *
 * @author Ray
 * @version 1.0
 */
@Service("drugWayService")
public class DrugWayServiceImpl implements DrugWayService {

    private DrugWayDAO drugWayDAO;

    /**
     * 按条件分页查询
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<DrugWayBean> getDrugWay(DrugWayBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"id", "name", "code"};
        JqueryStylePagingResults<DrugWayBean> pagingResults = new JqueryStylePagingResults<DrugWayBean>(columns);

        List<DrugWayBean> drugWayList = null;

        drugWayList = drugWayDAO.getDrugWayList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(drugWayList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            drugWayList = drugWayDAO.getDrugWayList(bean, jquryStylePaging);
        }

        // 总数
        int total = drugWayDAO.getDrugWayTotal(bean);

        pagingResults.setDataRows(drugWayList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    @Override
    public void addDrugWay(DrugWayBean bean) {
        drugWayDAO.addDrugWay(bean);
    }

    @Override
    public void updateDrugWay(DrugWayBean bean) {
        drugWayDAO.updateDrugWay(bean);
    }

    @Autowired
    public void setDrugWayDAO(DrugWayDAO drugWayDAO) {
        this.drugWayDAO = drugWayDAO;
    }

    @Override
    public DrugWayBean getDrugWayByCondition(DrugWayBean bean) {
        return drugWayDAO.getDrugWayByCondition(bean);
    }

    @Override
    public void synData(DrugWayBean bean) {
        drugWayDAO.synData(bean);
    }

}
