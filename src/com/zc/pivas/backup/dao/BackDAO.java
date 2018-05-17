package com.zc.pivas.backup.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.backup.bean.ServerNodeBean;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 核对类型:SRVS_CHECKTYPE
 *
 * @author  cacabin
 * @version  1.0
 */
@MyBatisRepository("BackDAO")
public interface BackDAO
{
    /**
     * 获取当前服务器结点
     * @param ip 查询参数
     * @return 页码
     */
    ServerNodeBean getServerNode(@Param("ip") String ip);
}
