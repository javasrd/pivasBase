package com.zc.pivas.titileshow.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import com.zc.pivas.titileshow.bean.ConfigTitleSubBean;
import com.zc.pivas.titileshow.dao.ConfigTitleDAO;
import com.zc.pivas.titileshow.service.ConfigTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 医嘱/药单列表配置
 *
 * @author kunkka
 * @version 1.0
 */
@Service("configTitleService")
public class ConfigTitleServiceImpl implements ConfigTitleService {

    private ConfigTitleDAO configTitleDAO;

    @Autowired
    public void setConfigTitleDAO(ConfigTitleDAO configTitleDAO) {
        this.configTitleDAO = configTitleDAO;
    }

    /**
     * 分页获取医嘱/药单列表配置信息
     */
    @Override
    public JqueryStylePagingResults<ConfigTitleBean> getShowTilteList(ConfigTitleBean bean,
                                                                      JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"confId", "confName", "confType", "useBy", "createTime"};
        JqueryStylePagingResults<ConfigTitleBean> pagingResults = new JqueryStylePagingResults<ConfigTitleBean>(columns);

        // 总数
        int total;
        List<ConfigTitleBean> configTitleBeanList = configTitleDAO.getShowTilteList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(configTitleBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            configTitleBeanList = configTitleDAO.getShowTilteList(bean, jquryStylePaging);
        }

        total = configTitleDAO.getConfigTitleTotal(bean);

        pagingResults.setDataRows(configTitleBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 分页查询总数
     * @param bean 查询对象
     * @return
     */
    @Override
    public int getConfigTitleTotal(ConfigTitleBean bean) {
        return configTitleDAO.getConfigTitleTotal(bean);
    }

    @Override
    public List<ConfigTitleSubBean> displayTitleDetail(Long configId) {
        return configTitleDAO.displayTitleDetail(configId);
    }

    @Override
    public boolean checkConfigTitleName(ConfigTitleBean bean) {
        List<ConfigTitleBean> configTitle = configTitleDAO.getConfigTitleForUPdate(bean);

        if (null == configTitle || configTitle.size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<ConfigTitleBean> checkUpdateConfigTitleName(ConfigTitleBean bean) {
        return configTitleDAO.getConfigTitleForUPdate(bean);
    }

    @Override
    public void addConfigTitle(ConfigTitleBean bean) {
        String[] names = bean.getUseByNames();
        String namesStr = "";
        for (int i = 0; i < names.length; i++) {
            if (i != names.length - 1) {
                namesStr += names[i] + ",";
            } else {
                namesStr += names[i];
            }
        }
        bean.setUseBy(namesStr);
        configTitleDAO.addConfigTitle(bean);

        List<ConfigTitleSubBean> subBeanList = getSubBeanList(bean.getConfId(), bean.getTitleList());
        for (ConfigTitleSubBean subBean : subBeanList) {
            configTitleDAO.addConfigTitleSub(subBean);
        }
    }

    @Override
    public void updateConfTitle(ConfigTitleBean bean) {
        if (bean.getConfId() != null) {
            configTitleDAO.delConfigTitleSub(bean.getConfId());
        }
        List<ConfigTitleSubBean> subBeanList = getSubBeanList(bean.getConfId(), bean.getTitleList());
        for (ConfigTitleSubBean subBean : subBeanList) {
            configTitleDAO.addConfigTitleSub(subBean);
        }
        configTitleDAO.updateConfigTitle(bean);
    }

    public List<ConfigTitleSubBean> getSubBeanList(Long confId, String subListStr) {
        List<ConfigTitleSubBean> subList = new ArrayList<ConfigTitleSubBean>();
        String[] subTitleArray = subListStr.split("@@");
        if (subTitleArray.length != 0) {
            ConfigTitleSubBean subBean = null;
            for (int i = 0; i < subTitleArray.length; i++) {
                String[] subInfo = subTitleArray[i].split(":");
                String field = subInfo[0];
                Integer priority = Integer.parseInt(subInfo[1]);
                subBean = new ConfigTitleSubBean(confId, field, priority);
                subList.add(subBean);
            }
        }
        return subList;
    }

    @Override
    public void delConfigTitle(Long confId) {
        configTitleDAO.delConfigTitleSub(confId);
        configTitleDAO.delConfigTitle(confId);
    }

    @Override
    public List<ConfigTitleBean> getALlConfigTitle(Integer confType, String userName) {
        return configTitleDAO.getALlConfigTitle(confType, userName);
    }

    @Override
    public String getAllAccount() {
        return configTitleDAO.getAllAccounts();
    }

    @Override
    public List<String> getExitAccounts(Map<String, Object> map) {
        return configTitleDAO.getExitAccounts(map);
    }


}
