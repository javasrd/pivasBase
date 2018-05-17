package com.zc.pivas.statistics.service;

import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sys.modules.user.entity.User;

import java.util.List;
import java.util.Map;


/**
 * 配置工作量统计Service接口
 *
 * @author jagger
 * @version 1.0
 */
public interface AllocationWorkService {
    /**
     * 获取所有的空批
     *
     * @return
     * @throws Exception
     */
    List<Batch> getBatchList() throws Exception;

    /**
     * 获取拆药统计的PDF文件路径
     *
     * @param param
     * @param currUser
     * @return
     * @throws Exception
     */
    String getWorkListPdfPath(Map<String, Object> param, User currUser) throws Exception;

    /**
     * 导出excel
     *
     * @param param
     * @param currUser
     * @return
     * @throws Exception
     */
    String toExportExcel(Map<String, Object> param, User currUser) throws Exception;
}
