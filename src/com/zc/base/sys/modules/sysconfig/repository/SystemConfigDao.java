package com.zc.base.sys.modules.sysconfig.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository("systemConfigDao")
public abstract interface SystemConfigDao {
    public abstract void addSystemConfig(@Param("systemConfig") SystemConfig paramSystemConfig) throws Exception;

    public abstract void updateSystemConfig(@Param("systemConfig") SystemConfig paramSystemConfig) throws Exception;

    public abstract void updateSysValue(@Param("systemConfig") SystemConfig paramSystemConfig) throws Exception;

    public abstract SystemConfig getSystemConfig(@Param("sysKey") String paramString) throws Exception;

    public abstract List<SystemConfig> getAllSystemConfig() throws Exception;

    public abstract int qrySysSequence();
}
