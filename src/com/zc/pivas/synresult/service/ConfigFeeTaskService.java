package com.zc.pivas.synresult.service;

import com.zc.pivas.synresult.dao.ConfigFeeTaskDAO;

/**
 * 配置费收费接口
 *
 * @author kunkka
 * @version 1.0
 */
public interface ConfigFeeTaskService extends ConfigFeeTaskDAO {
    /**
     * 执行任务
     */
    void excute();
}
