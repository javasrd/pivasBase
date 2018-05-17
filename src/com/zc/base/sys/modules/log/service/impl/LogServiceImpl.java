package com.zc.base.sys.modules.log.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.log.bo.LogBo;
import com.zc.base.sys.modules.log.entity.Log;
import com.zc.base.sys.modules.log.repository.LogDao;
import com.zc.base.sys.modules.log.service.LogService;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("logService")
public class LogServiceImpl
        implements LogService {
    @Resource
    private LogDao logDao;
    private static final Logger loger = LoggerFactory.getLogger(LogServiceImpl.class);


    public JqueryStylePagingResults<Log> queryLog(LogBo bo, JqueryStylePaging paging)
            throws Exception {
        String[] columns =
                {"id", "account", "userName", "systemName", "moduleName", "content", "statusName", "time"};
        JqueryStylePagingResults<Log> results = new JqueryStylePagingResults(columns);

        bo.setAccount(DefineStringUtil.escapeAllLike(bo.getAccount()));


        List<Log> list = this.logDao.queryLog(bo, paging);


        if ((DefineCollectionUtil.isEmpty(list)) && (paging.getPage().intValue() != 1)) {
            paging.setPage(Integer.valueOf(paging.getPage().intValue() - 1));
            list = this.logDao.queryLog(bo, paging);
        }


        if (DefineCollectionUtil.isNotEmpty(list)) {
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            for (Log log : list) {
                log.setStatusName(DictFacade.getName("SYS_LOG_OPER.status",
                        DefineStringUtil.parseNull(log.getStatus()),
                        lang));

                log.setSystemName(DictFacade.getName("SYS_LOG_OPER.sub_system",
                        DefineStringUtil.parseNull(log.getSystem()),
                        lang));


                if (AmiLogConstant.BRANCH_SYSTEM.SM.equals(log.getSystem())) {
                    log.setModuleName(DictFacade.getName("SYS_LOG_OPER.module.sys",
                            DefineStringUtil.parseNull(log.getModule()),
                            lang));
                } else if (AmiLogConstant.BRANCH_SYSTEM.PM.equals(log.getSystem())) {
                    log.setModuleName(DictFacade.getName("SYS_LOG_OPER.module.pm",
                            DefineStringUtil.parseNull(log.getModule()),
                            lang));
                } else if (AmiLogConstant.BRANCH_SYSTEM.CF.equals(log.getSystem())) {
                    log.setModuleName(DictFacade.getName("SYS_LOG_OPER.module.cf",
                            DefineStringUtil.parseNull(log.getModule()),
                            lang));
                } else if (AmiLogConstant.BRANCH_SYSTEM.SC.equals(log.getSystem())) {
                    log.setModuleName(DictFacade.getName("SYS_LOG_OPER.module.sc",
                            DefineStringUtil.parseNull(log.getModule()),
                            lang));
                }
            }
        }

        results.setDataRows(list);

        Integer total = Integer.valueOf(this.logDao.queryLogTotal(bo));
        results.setTotal(total);

        results.setPage(paging.getPage());

        return results;
    }


    public void log(Log log) {
        if (log != null) {

            loger.info(log.getContent());


            this.logDao.insertLog(log);
        }
    }


    public void batchLog(List<Log> logs) {
        this.logDao.batchLog(logs);
    }
}
