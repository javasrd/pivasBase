package com.zc.pivas.checktype.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.checktype.bean.ErrorTypeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审方错误类型DAO
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("errorTypeDAO")
public interface ErrorTypeDAO {
    /***
     * 分页查询审方错误类型
     * @param bean 对象
     * @return 分页数据
     * @exception Exception e
     */
    List<ErrorTypeBean> getErrorTypeList(@Param("errorType")
                                                 ErrorTypeBean bean, @Param("paging")
                                                 JqueryStylePaging jqueryStylePaging);

    /**
     * 查询总数
     *
     * @param bean 查询参数
     * @return 页码
     */
    int getErrorTypeTotal(@Param("errorType")
                                  ErrorTypeBean bean);

    /**
     * 添加审方错误类型
     *
     * @param bean 审方错误类型
     */
    void addErrorType(ErrorTypeBean bean);

    /**
     * 修改审方错误类型
     *
     * @param bean 审方错误类型
     */
    void updateErrorType(ErrorTypeBean bean);

    /***
     * 删除审方错误类型
     * @param gid 主键id
     */
    void delErrorType(String gid);

    /**
     * 查询审核类型
     *
     * @return 审核错误类型
     */
    ErrorTypeBean getErrorType(@Param("errorType")
                                       ErrorTypeBean bean);

    /**
     * 查询审核类型:用于修改时判断
     *
     * @return 审核错误类型
     */
    ErrorTypeBean getErrorTypeForUPdate(@Param("errorType")
                                                ErrorTypeBean bean);

    /**
     * 检查错误类型是否已被使用
     *
     * @return
     */
    int checkErrorTypeUsed(String gid);
}
