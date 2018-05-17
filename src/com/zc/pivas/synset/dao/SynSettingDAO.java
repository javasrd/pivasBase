package com.zc.pivas.synset.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.synset.bean.SynSettingBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同步设置
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("synSettingDAO")
public interface SynSettingDAO {
    /***
     * 分页查询表数据
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<SynSettingBean> getSynSettingList(@Param("synSetting")
                                                   SynSettingBean bean, @Param("paging")
                                                   JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getSynSettingTotal(@Param("synSetting")
                                   SynSettingBean bean);

    /**
     * 添加表数据
     *
     * @param bean
     */
    void addSynSetting(SynSettingBean bean);

    /**
     * 修改表数据
     *
     * @param bean
     */
    void updateSynSetting(SynSettingBean bean);

    /***
     * 删除表数据
     * @param gid 主键id
     */
    void delSynSetting(String gid);

    /**
     * 查询数据
     *
     * @return 审核错误类型
     */
    SynSettingBean getSynSetting(@Param("synSetting")
                                         SynSettingBean bean);

    /**
     * 判断同步名称是否存在
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    SynSettingBean checkTaskNameIsExitst(@Param("synSetting")
                                                 SynSettingBean bean);

    /**
     * 判断同步类型内同是否存在
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    SynSettingBean checkContentTypeIsExitst(@Param("synSetting")
                                                    SynSettingBean bean);

    /**
     * 修改同步状态
     *
     */
    void updateSynState(SynSettingBean bean);
}
