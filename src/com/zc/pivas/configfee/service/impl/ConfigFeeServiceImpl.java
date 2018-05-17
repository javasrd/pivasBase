package com.zc.pivas.configfee.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.configfee.bean.ConfigFeeBean;
import com.zc.pivas.configfee.dao.ConfigFeeDAO;
import com.zc.pivas.configfee.service.ConfigFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置费/材料费实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("configFeeService")
public class ConfigFeeServiceImpl implements ConfigFeeService {
    /**
     * 核对类型数据处理
     */
    private ConfigFeeDAO configFeeDAO;

    /**
     * 添加配置费/材料费
     * @param bean 配置费/材料费
     */
    @Override
    public void addConfigFee(ConfigFeeBean bean) {
        configFeeDAO.addConfigFee(bean);

    }

    /**
     * 删除配置费/材料费
     * @param gid 主键id
     */
    @Override
    public void delConfigFee(String gid) {
        configFeeDAO.delConfigFee(gid);

    }

    @Override
    public JqueryStylePagingResults<ConfigFeeBean> getConfigFeeLsit(ConfigFeeBean bean,
                                                                    JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"costCode", "costName", "price", "configFeeType", "configFeeTypeName"};
        JqueryStylePagingResults<ConfigFeeBean> pagingResults = new JqueryStylePagingResults<ConfigFeeBean>(columns);

        // 总数
        int total;
        List<ConfigFeeBean> configFeeBeanList = configFeeDAO.getConfigFeeList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(configFeeBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            configFeeBeanList = configFeeDAO.getConfigFeeList(bean, jquryStylePaging);
        }

        total = configFeeDAO.getConfigFeeTotal(bean);

        pagingResults.setDataRows(configFeeBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 修改配置费/材料费
     * @param bean 需要修改的数据
     */
    @Override
    public void updateConfigFee(ConfigFeeBean bean) {
        configFeeDAO.updateConfigFee(bean);

    }

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    public ConfigFeeBean getConfigFee(ConfigFeeBean bean) {
        return configFeeDAO.getConfigFee(bean);
    }

    /**
     * 根据bean查询对应的列表信息
     *
     * @param bean 查询条件
     * @return 列表数据
     */
    public List<ConfigFeeBean> getConfigFees(ConfigFeeBean bean) {
        return configFeeDAO.getConfigFees(bean);
    }

    /**
     * 修改的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkCondigFeeName(ConfigFeeBean bean) {
        ConfigFeeBean condition = new ConfigFeeBean();
        condition.setGid(bean.getGid());
        condition.setCostName(bean.getCostName());
        ConfigFeeBean configFee = configFeeDAO.getConfigFeeForUPdate(condition);

        if (null == configFee) {
            return false;
        }

        return true;
    }

    /**
     * 修改的时候判断编码是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkCondigFeeCode(ConfigFeeBean bean) {
        ConfigFeeBean condition = new ConfigFeeBean();
        condition.setGid(bean.getGid());
        condition.setCostCode(bean.getCostCode());
        ConfigFeeBean configFee = configFeeDAO.getConfigFeeForUPdate(condition);

        if (null == configFee) {
            return false;
        }

        return true;
    }

    @Override
    public int getConfigFeeTotal(ConfigFeeBean bean) {
        return configFeeDAO.getConfigFeeTotal(bean);
    }

    @Autowired
    public void setConfigFeeDAO(ConfigFeeDAO ConfigFeeDAO) {
        this.configFeeDAO = ConfigFeeDAO;
    }

}
