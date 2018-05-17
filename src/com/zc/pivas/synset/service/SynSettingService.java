package com.zc.pivas.synset.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.synset.bean.SynSettingBean;

/**
 * 同步任务设置service
 *
 * @author kunkka
 * @version 1.0
 */
public interface SynSettingService {
    /***
     * 分页查询表数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    JqueryStylePagingResults<SynSettingBean> getSynSettingLsit(SynSettingBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception;

    /**
     * 添加表数据
     *
     * @param bean
     */
    void addSynSetting(SynSettingBean bean)
            throws Exception;

    /**
     * 修改表数据
     *
     * @param bean 需要修改的数据
     */
    void updateSynSetting(SynSettingBean bean)
            throws Exception;

    /**
     * 查询数据
     *
     * @return 审核错误
     */
    SynSettingBean getSynSetting(SynSettingBean bean);

    /***
     * 删除表数据
     * @param gid 主键id
     */
    void delSynSetting(String gid)
            throws Exception;

    /**
     * 修改同步状态：启动、停止
     *
     * @param taskID   任务id
     * @param isEnable 状态
     * @throws Exception
     */
    void changeSynState(String taskID, String isEnable)
            throws Exception;

    /**
     * 判断同步名称是否存在
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    boolean checkTaskNameIsExitst(SynSettingBean bean);

    /**
     * 判断同步类型内同是否存在
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    boolean checkContentTypeIsExitst(SynSettingBean bean);
}
