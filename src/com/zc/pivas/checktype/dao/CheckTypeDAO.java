package com.zc.pivas.checktype.dao;


import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.checktype.bean.CheckTypeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 核对类型DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("checkTypeDAO")
public interface CheckTypeDAO {
    /***
     * 分页查询核对类型
     * @param bean 对象
     * @param jqueryStylePaging 分页参数
     * @return 分页数据
     * @exception Exception e
     */
    List<CheckTypeBean> getCheckTypeList(@Param("checkType")
                                                 CheckTypeBean bean, @Param("paging")
                                                 JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     * 查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getCheckTypeTotal(@Param("checkType")
                                  CheckTypeBean bean);

    /**
     * 添加核对类型表数据
     * <p>
     * 新增核对类型
     *
     * @param bean 核对类型
     */
    void addCheckType(CheckTypeBean bean);

    /**
     * 修改核对类型表数据
     * <p>
     * 修改核对类型
     *
     * @param bean 核对类型
     */
    void updateCheckType(CheckTypeBean bean);

    /**
     * 查询类型数据
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    CheckTypeBean getCheckType(@Param("checkType")
                                       CheckTypeBean bean);

    /***
     * 删除核对类型
     * @param gid 主键id
     */
    void delCheckType(String gid);

    /**
     * 查询审核类型:用于修改判断checkName
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    CheckTypeBean checkOrderIDIsExist(@Param("checkType")
                                              CheckTypeBean bean);

    /**
     * 查询审核类型数据:用于修改时判断orderid
     * 查询审核类型:用于修改时判断orderid
     *
     * @param bean 名称
     * @return 审核错误类型
     */
    CheckTypeBean checkNameIsExist(@Param("checkType")
                                           CheckTypeBean bean);

    /**
     * 查询所有生效的核对列表
     *
     * @return
     */
    List<CheckTypeBean> queryCheckTypeAllList(@Param("checkType")
                                                      String checkType);

    /**
     * 修改类型的时候判断和对类型是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    CheckTypeBean checkTypeIsExitst(@Param("checkType")
                                            CheckTypeBean bean);
}
