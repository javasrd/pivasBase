package com.zc.pivas.configfee.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.configfee.bean.ConfigFeeDetailBean;
import com.zc.pivas.configfee.dao.ConfigFeeDetailDAO;
import com.zc.pivas.configfee.service.ConfigFeeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置费实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("configFeeDetailDetailService")
public class ConfigFeeDetailServiceImpl implements ConfigFeeDetailService {
    /**
     * 核对类型数据处理
     */
    private ConfigFeeDetailDAO configFeeDetailDAO;

    /**
     * 添加配置费明细
     * @param bean 配置费明细
     */
    @Override
    public void addConfigFeeDetail(ConfigFeeDetailBean bean) {
        configFeeDetailDAO.addConfigFeeDetail(bean);

    }

    /**
     * 删除配置费明细
     * @param detailCode
     */
    @Override
    public void delConfigFeeDetail(String detailCode) {
        configFeeDetailDAO.delConfigFeeDetail(detailCode);
    }

    /**
     * 查询配置费明细表数据
     * @param bean
     *            对象
     * @param jquryStylePaging
     *            分页参数
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<ConfigFeeDetailBean> getConfigFeeDetailLsit(ConfigFeeDetailBean bean,
                                                                                JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"costName", "amount", "rate"};
        JqueryStylePagingResults<ConfigFeeDetailBean> pagingResults =
                new JqueryStylePagingResults<ConfigFeeDetailBean>(columns);

        // 总数
        int total;
        List<ConfigFeeDetailBean> configFeeDetailBeanList =
                configFeeDetailDAO.getConfigFeeDetailList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(configFeeDetailBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            configFeeDetailBeanList = configFeeDetailDAO.getConfigFeeDetailList(bean, jquryStylePaging);
        }

        total = configFeeDetailDAO.getConfigFeeDetailTotal(bean);

        pagingResults.setDataRows(configFeeDetailBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /***
     * 查询配置费明细[DETAILCODE]
     *
     * @param detailcode
     *            String
     * @return 数据
     * @exception Exception
     *                e
     */
    public List<ConfigFeeDetailBean> getConfigFeeDetailListByDetailCode(String detailcode) {
        return configFeeDetailDAO.getConfigFeeDetailListByDetailCode(detailcode);
    }

    /**
     * 修改配置费明细表
     * @param bean 需要修改的数据
     */
    @Override
    public void updateConfigFeeDetail(ConfigFeeDetailBean bean) {
        configFeeDetailDAO.updateConfigFeeDetail(bean);

    }

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    public ConfigFeeDetailBean getConfigFeeDetail(ConfigFeeDetailBean bean) {
        return configFeeDetailDAO.getConfigFeeDetail(bean);
    }

    @Autowired
    public void setConfigFeeDetailDAO(ConfigFeeDetailDAO ConfigFeeDetailDAO) {
        this.configFeeDetailDAO = ConfigFeeDetailDAO;
    }

    @Override
    public int getConfigFeeDetailTotal(ConfigFeeDetailBean bean) {
        return configFeeDetailDAO.getConfigFeeDetailTotal(bean);
    }
}
