package com.zc.base.sys.modules.sysconfig.service;

import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统配置接口类
 *
 * @author jagger
 * @version 1.0
 */
public abstract interface SystemConfigService {
    public abstract void addSystemConfig(SystemConfig paramSystemConfig) throws Exception;

    public abstract List<SystemConfig> getAllSystemConfig() throws Exception;

    public abstract void updateSystemConfigs(Map<String, String> paramMap) throws Exception;

    public abstract void updateSystemConfig(SystemConfig paramSystemConfig) throws Exception;

    public abstract Map<String, SystemConfig> getInitSystemConfig() throws Exception;
}
