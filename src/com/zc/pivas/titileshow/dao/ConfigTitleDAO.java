package com.zc.pivas.titileshow.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import com.zc.pivas.titileshow.bean.ConfigTitleSubBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 医嘱/药单列表配置DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("configTitleDAO")
public interface ConfigTitleDAO {
    /***
     * 分页查询医嘱/药单列表配置数据
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<ConfigTitleBean> getShowTilteList(@Param("configTitle") ConfigTitleBean bean, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getConfigTitleTotal(@Param("configTitle") ConfigTitleBean bean);

    List<ConfigTitleSubBean> displayTitleDetail(@Param("configId") Long configId);

    List<ConfigTitleBean> getConfigTitleForUPdate(@Param("configTitle") ConfigTitleBean bean);

    void addConfigTitle(ConfigTitleBean titleBean);

    void addConfigTitleSub(@Param("subBean") ConfigTitleSubBean subBean);

    void updateConfigTitle(@Param("titleBean") ConfigTitleBean titleBean);

    void delConfigTitle(@Param("confId") Long confId);

    void delConfigTitleSub(@Param("confId") Long confId);

    List<ConfigTitleBean> getALlConfigTitle(@Param("confType") Integer confType, @Param("userName") String userName);

    String getAllAccounts();

    List<String> getExitAccounts(@Param("map") Map<String, Object> map);
}
