package com.zc.pivas.titileshow.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import com.zc.pivas.titileshow.bean.ConfigTitleSubBean;

import java.util.List;
import java.util.Map;

/**
 * 医嘱/药单列表配置
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigTitleService {
    /***
     * 查询医嘱/药单列表
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<ConfigTitleBean> getShowTilteList(ConfigTitleBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 根据条件查询数量
     *
     * @param bean 查询对象
     * @return 数量
     */
    int getConfigTitleTotal(ConfigTitleBean bean);

    /**
     * 药品标签详情
     *
     * @return
     */
    List<ConfigTitleSubBean> displayTitleDetail(Long configId);

    boolean checkConfigTitleName(ConfigTitleBean bean);

    List<ConfigTitleBean> checkUpdateConfigTitleName(ConfigTitleBean bean);

    void addConfigTitle(ConfigTitleBean bean);

    /**
     * 修改配置费/材料费表数据
     *
     * @param bean 需要修改的数据
     */
    void updateConfTitle(ConfigTitleBean bean);

    void delConfigTitle(Long confId);

    List<ConfigTitleBean> getALlConfigTitle(Integer confType, String userName);

    String getAllAccount();

    List<String> getExitAccounts(Map<String, Object> map);


}
