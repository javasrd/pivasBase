package com.zc.pivas.configfee.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.configfee.bean.ConfigFeeTypeBean;
import com.zc.pivas.configfee.dao.ConfigFeeTypeDao;
import com.zc.pivas.configfee.service.ConfigFeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置费类型实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("configFeeTypeService")
public class ConfigFeeTypeServiceImpl implements ConfigFeeTypeService {
    @Autowired
    private ConfigFeeTypeDao configFeeTypeDao;

    /**
     * 查询所有数据
     *
     * @param bean             对象
     * @param jquryStylePaging 分页参数
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<ConfigFeeTypeBean> getConfigFeeTypeLsit(ConfigFeeTypeBean bean,
                                                                            JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"typeDesc", "remark"};
        JqueryStylePagingResults<ConfigFeeTypeBean> pagingResults =
                new JqueryStylePagingResults<ConfigFeeTypeBean>(columns);

        // 总数
        int total;
        List<ConfigFeeTypeBean> ConfigFeeTypeBeanList = configFeeTypeDao.getConfigFeeTypeList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(ConfigFeeTypeBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            ConfigFeeTypeBeanList = configFeeTypeDao.getConfigFeeTypeList(bean, jquryStylePaging);
        }

        total = configFeeTypeDao.getConfigFeeTypeTotal(bean);

        pagingResults.setDataRows(ConfigFeeTypeBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 添加配置费类别
     *
     * @param bean 配置费/材料费
     */
    @Override
    public void addConfigFeeType(ConfigFeeTypeBean bean) {
        configFeeTypeDao.addConfigFeeType(bean);

    }

    /**
     * 根据bean查询对应的列表
     *
     * @param bean 查询条件
     * @return 列表数据
     */
    public ConfigFeeTypeBean getConfigFeeType(ConfigFeeTypeBean bean) {
        return configFeeTypeDao.getConfigFeeType(bean);
    }

    /**
     * 修改配置费类别
     *
     * @param bean 需要修改的数据
     */
    @Override
    public void updateConfigFeeType(ConfigFeeTypeBean bean) {
        configFeeTypeDao.updateConfigFeeType(bean);

    }

    /**
     * 删除配置费类别
     *
     * @param gid 主键id
     */
    @Override
    public void delConfigFeeType(String gid) {
        configFeeTypeDao.delConfigFeeType(gid);

    }

    /**
     * 根据bean查询对应的列表
     *
     * @param bean 查询条件
     * @return
     */
    @Override
    public List<ConfigFeeTypeBean> getConfigFeeTypes(ConfigFeeTypeBean bean) {
        return configFeeTypeDao.getConfigFeeTypes(bean);
    }

    /**
     * @param bean 查询条件
     * @return
     */
    @Override
    public boolean checkCondigFeeTypeName(ConfigFeeTypeBean bean) {
        ConfigFeeTypeBean condition = new ConfigFeeTypeBean();
        condition.setGid(bean.getGid());
        condition.setTypeDesc(bean.getTypeDesc());
        ConfigFeeTypeBean configFee = configFeeTypeDao.getConfigFeeTypeForUpdate(condition);

        if (null == configFee) {
            return false;
        }

        return true;
    }

}
