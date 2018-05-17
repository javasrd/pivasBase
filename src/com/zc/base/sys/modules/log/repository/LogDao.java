package com.zc.base.sys.modules.log.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.modules.log.bo.LogBo;
import com.zc.base.sys.modules.log.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository("logDao")
public abstract interface LogDao {
    public abstract List<Log> queryLog(@Param("bo") LogBo paramLogBo, @Param("paging") JqueryStylePaging paramJqueryStylePaging)
            throws Exception;

    public abstract int queryLogTotal(@Param("bo") LogBo paramLogBo)
            throws Exception;

    public abstract void insertLog(Log paramLog);

    public abstract void batchLog(List<Log> paramList);
}
