package com.zc.base.sys.modules.log.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.modules.log.bo.LogBo;
import com.zc.base.sys.modules.log.entity.Log;

import java.util.List;

public abstract interface LogService {
    public abstract JqueryStylePagingResults<Log> queryLog(LogBo paramLogBo, JqueryStylePaging paramJqueryStylePaging)
            throws Exception;

    public abstract void log(Log paramLog);

    public abstract void batchLog(List<Log> paramList);
}
