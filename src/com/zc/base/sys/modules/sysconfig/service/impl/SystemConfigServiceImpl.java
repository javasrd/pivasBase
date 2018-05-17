package com.zc.base.sys.modules.sysconfig.service.impl;

import com.zc.base.sys.common.util.AESUtil;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.repository.SystemConfigDao;
import com.zc.base.sys.modules.sysconfig.service.SystemConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置实现类
 *
 * @author jagger
 * @version 1.0
 */
@Service("systemConfigService")
public class SystemConfigServiceImpl implements SystemConfigService {
    @Resource
    private SystemConfigDao systemConfigDao;

    public void addSystemConfig(SystemConfig sysCon)
            throws Exception {
        this.systemConfigDao.addSystemConfig(sysCon);
    }

    public List<SystemConfig> getAllSystemConfig()
            throws Exception {
        return this.systemConfigDao.getAllSystemConfig();
    }

    public void updateSystemConfigs(Map<String, String> sysCons)
            throws Exception {
        for (String key : sysCons.keySet()) {
            SystemConfig sysCon = new SystemConfig();
            sysCon.setSys_key(key);

            if ("smtpServerUserPassword".equals(key)) {
                sysCon.setSys_value(AESUtil.Encrypt((String) sysCons.get(key)));
            } else {
                sysCon.setSys_value((String) sysCons.get(key));
            }
            this.systemConfigDao.updateSysValue(sysCon);
        }
    }

    public void updateSystemConfig(SystemConfig sysCon)
            throws Exception {
        this.systemConfigDao.updateSystemConfig(sysCon);
    }

    public Map<String, SystemConfig> getInitSystemConfig() throws Exception {
        Map<String, SystemConfig> map = null;

        List<SystemConfig> list = this.systemConfigDao.getAllSystemConfig();

        if ((list != null) && (list.size() > 0)) {
            map = new HashMap();
            SystemConfig config = null;
            for (int i = 0; i < list.size(); i++) {
                config = (SystemConfig) list.get(i);

                if ("smtpServerUserPassword".equals(config.getSys_key())) {
                    config.setSys_value(AESUtil.Decrypt(config.getSys_value()));
                }
                map.put(config.getSys_key(), config);
            }
        }
        
        return map;
    }
}
