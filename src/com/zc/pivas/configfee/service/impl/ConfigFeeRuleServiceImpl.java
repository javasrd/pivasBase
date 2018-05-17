package com.zc.pivas.configfee.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.configfee.bean.ConfigFeeDetailBean;
import com.zc.pivas.configfee.bean.ConfigFeeRuleBean;
import com.zc.pivas.configfee.dao.ConfigFeeDetailDAO;
import com.zc.pivas.configfee.dao.ConfigFeeRuleDAO;
import com.zc.pivas.configfee.service.ConfigFeeRuleService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置费规则实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("configFeeRuleRuleService")
public class ConfigFeeRuleServiceImpl implements ConfigFeeRuleService {
    /**
     * 核对类型数据处理
     */
    private ConfigFeeRuleDAO configFeeRuleDAO;

    /**
     * 配置明细
     */
    private ConfigFeeDetailDAO configFeeDetailDAO;

    /**
     * 药品
     */
    private MedicamentsDao medicamentsDao;

    /**
     * 添加配置费
     * @param bean 配置费
     */
    @Override
    public void addConfigFeeRule(ConfigFeeRuleBean bean) {
        configFeeRuleDAO.addConfigFeeRule(bean);
    }

    /**
     * 删除配置费明细
     * @param gid
     */
    @Override
    public void delConfigFeeRule(String gid) {
        String[] str = gid.split(",");
        Boolean flag = true;
        if (flag) {
            // 删除
            for (String st : str) {
                configFeeRuleDAO.delConfigFeeRule(st);

                // 删除配置费明细
                configFeeDetailDAO.delConfigFeeDetail(st);
            }
        }

    }

    @Override
    public JqueryStylePagingResults<ConfigFeeRuleBean> getConfigFeeRuleLsit(ConfigFeeRuleBean bean,
                                                                            JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"ruleName", "drugTypeName", "drugName", "volume"};
        JqueryStylePagingResults<ConfigFeeRuleBean> pagingResults =
                new JqueryStylePagingResults<ConfigFeeRuleBean>(columns);

        // 总数
        int total;
        List<ConfigFeeRuleBean> configFeeRuleBeanList = configFeeRuleDAO.getConfigFeeRuleList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(configFeeRuleBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            configFeeRuleBeanList = configFeeRuleDAO.getConfigFeeRuleList(bean, jquryStylePaging);
        }

        // 遍历list获取药品名
        if (DefineCollectionUtil.isNotEmpty(configFeeRuleBeanList)) {
            StringBuffer drugNames = null;
            Medicaments medicaments = null;
            List<Medicaments> medicamentsList = null;
            for (ConfigFeeRuleBean configFeeRule : configFeeRuleBeanList) {
                drugNames = new StringBuffer();

                if (null != configFeeRule.getDrugCode() && !"".equals(configFeeRule.getDrugCode())) {
                    String[] drugCodes = configFeeRule.getDrugCode().split(",");

                    for (String drugCode : drugCodes) {
                        medicaments = new Medicaments();
                        medicaments.setMedicamentsId(NumberUtils.toLong(drugCode));
                        medicamentsList = medicamentsDao.queryAllByCondition(medicaments);

                        if (DefineCollectionUtil.isNotEmpty(medicamentsList)) {
                            drugNames.append(medicamentsList.get(0).getMedicamentsName()).append(",");
                        }
                    }

                    configFeeRule.setDrugName((drugNames.toString().equals("") || null == drugNames.toString() ? ""
                            : drugNames.toString().substring(0, drugNames.toString().length() - 1)));
                }
            }
        }

        total = configFeeRuleDAO.getConfigFeeRuleTotal(bean);

        pagingResults.setDataRows(configFeeRuleBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 修改配置费
     * @param bean 需要修改的数据
     */
    @Override
    public void updateConfigFeeRule(ConfigFeeRuleBean bean) {
        configFeeRuleDAO.updateConfigFeeRule(bean);

        // 先删除明细信息后插入
        configFeeDetailDAO.delConfigFeeDetail(bean.getGid());

        if (null != bean.getConfigFeeDetailList()) {
            // 增加
            for (ConfigFeeDetailBean configFeeDetailBean : bean.getConfigFeeDetailList()) {
                configFeeDetailBean.setDetailCode(bean.getGid());
                configFeeDetailDAO.addConfigFeeDetail(configFeeDetailBean);
            }
        }

    }

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    public ConfigFeeRuleBean getConfigFeeRule(ConfigFeeRuleBean bean) {
        bean = configFeeRuleDAO.getConfigFeeRule(bean);

        // 查询对应的药品名
        StringBuffer drugNames = null;
        Medicaments medicaments = null;
        List<Medicaments> medicamentsList = null;
        drugNames = new StringBuffer();
        if (null != bean.getDrugCode() && !"".equals(bean.getDrugCode())) {
            String[] drugCodes = bean.getDrugCode().split(",");

            for (String drugCode : drugCodes) {
                medicaments = new Medicaments();
                medicaments.setMedicamentsId(NumberUtils.toLong(drugCode));
                medicamentsList = medicamentsDao.queryAllByCondition(medicaments);

                if (DefineCollectionUtil.isNotEmpty(medicamentsList)) {
                    drugNames.append(medicamentsList.get(0).getMedicamentsName()).append(",");
                }
            }

            bean.setDrugName(drugNames.toString().substring(0, drugNames.toString().length() - 1));

        }

        // 查询对应的明细信息
        List<ConfigFeeDetailBean> configFeeDetailList =
                configFeeDetailDAO.getConfigFeeDetailListByDetailCode(bean.getGid());

        bean.setConfigFeeDetailList(configFeeDetailList);
        return bean;
    }

    /**
     * 查询配置费数据[根据药品分类]
     *
     * @return 审核错误配置费
     */
    public List<ConfigFeeRuleBean> getConfigFeeRuleByInfo(ConfigFeeRuleBean bean) {
        List<ConfigFeeRuleBean> configFeeRuleBeanList = configFeeRuleDAO.getConfigFeeRuleByInfo(bean);
        return configFeeRuleBeanList;
    }

    /**
     * 修改判断规则名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkRuleName(ConfigFeeRuleBean bean) {
        ConfigFeeRuleBean configFeeRule = configFeeRuleDAO.checkConfigFeeRuleName(bean);

        if (null != configFeeRule) {
            return true;
        }

        return false;
    }

    /**
     * 判断药品分类是否已被使用
     * @param bean 查询条件
     * @return
     */
    @Override
    public boolean checkDrugClass(ConfigFeeRuleBean bean) {
        ConfigFeeRuleBean configFeeRule = configFeeRuleDAO.checkDrugClass(bean);

        if (null != configFeeRule) {
            return true;
        }

        return false;
    }

    @Autowired
    public void setConfigFeeRuleDAO(ConfigFeeRuleDAO ConfigFeeRuleDAO) {
        this.configFeeRuleDAO = ConfigFeeRuleDAO;
    }

    @Autowired
    public void setConfigFeeDetailDAO(ConfigFeeDetailDAO configFeeDetailDAO) {
        this.configFeeDetailDAO = configFeeDetailDAO;
    }

    @Autowired
    public void setMedicamentsDao(MedicamentsDao medicamentsDao) {
        this.medicamentsDao = medicamentsDao;
    }

    @Override
    public List<ConfigFeeRuleBean> checkIsExist(ConfigFeeRuleBean bean) {
        return configFeeRuleDAO.checkIsExist(bean);
    }

}
