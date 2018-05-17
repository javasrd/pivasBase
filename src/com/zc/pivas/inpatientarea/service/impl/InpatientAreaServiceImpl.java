package com.zc.pivas.inpatientarea.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.dao.InpatientAreaDAO;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 病区
 *
 * @author Ray
 * @version 1.0
 */
@Service("inpatientAreaService")
public class InpatientAreaServiceImpl implements InpatientAreaService {
    private InpatientAreaDAO inpatientAreaDAO;

    /**
     * 按条件分页查询病区列表
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    public JqueryStylePagingResults<InpatientAreaBean> getInpatientAreaList(InpatientAreaBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"deptCode", "deptName"};
        JqueryStylePagingResults<InpatientAreaBean> pagingResults = new JqueryStylePagingResults<InpatientAreaBean>(columns);

        // 总数
        int total;
        List<InpatientAreaBean> inpatientAreaBeanList = null;

        // 超级管理员
        inpatientAreaBeanList = inpatientAreaDAO.getInpatientAreaList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(inpatientAreaBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            inpatientAreaBeanList = inpatientAreaDAO.getInpatientAreaList(bean, jquryStylePaging);
        }

        total = inpatientAreaDAO.getInpatientAreaTotal(bean);

        pagingResults.setDataRows(inpatientAreaBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    @Autowired
    public void setInpatientAreaDAO(InpatientAreaDAO inpatientAreaDAO) {
        this.inpatientAreaDAO = inpatientAreaDAO;
    }

    @Override
    public List<InpatientAreaBean> getInpatientAreaBeanList(InpatientAreaBean inpatientArea, JqueryStylePaging jqueryStylePaging) {
        return inpatientAreaDAO.getInpatientAreaList(inpatientArea, jqueryStylePaging);
    }

    @Override
    public void addInpatientArea(InpatientAreaBean bean) {
        inpatientAreaDAO.addInpatientArea(bean);

    }

    @Override
    public void updateInpatientArea(InpatientAreaBean bean) {
        inpatientAreaDAO.updateInpatientArea(bean);

    }

    @Override
    public List<InpatientAreaBean> queryInpatientAreaAllList() {
        return inpatientAreaDAO.queryInpatientAreaAllList();
    }

    @Override
    public List<InpatientAreaBean> queryInpatientAreaInAll() {
        return inpatientAreaDAO.queryInpatientAreaInAll();
    }

    @Override
    public List<InpatientAreaBean> queryInpatientAreaAllListForYDStatistic() {
        return inpatientAreaDAO.queryInpatientAreaAllListForYDStatistic();
    }

    @Override
    public InpatientAreaBean getInpatientAreaBean(InpatientAreaBean bean) {
        return inpatientAreaDAO.getInpatientAreaBean(bean);
    }

    @Override
    public void synData(InpatientAreaBean bean) {
        inpatientAreaDAO.synData(bean);
    }

}
