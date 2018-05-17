package com.zc.pivas.employee.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.employee.bean.EmployeeInfoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("employeeInfoDAO")
public interface EmployeeInfoDAO {
    /**
     * 添加
     *
     * @param bean
     */
    void addEmployeeInfo(EmployeeInfoBean bean);

    /**
     * 修改
     *
     * @param bean
     */
    void updateEmployeeInfo(EmployeeInfoBean bean);

    void synData(EmployeeInfoBean bean);

    /**
     * 根据员工编码查询员工信息
     *
     * @param bean
     * @return
     */
    EmployeeInfoBean getEmployeeInfo(@Param("employeeInfo") EmployeeInfoBean bean);

    /**
     * 按条件分页查询员工数据
     * @param employeeInfo
     * @param jqueryStylePaging
     * @return
     */
    List<EmployeeInfoBean> getEmployeeInfoList(@Param("employeeInfo") EmployeeInfoBean employeeInfo, @Param("paging") JqueryStylePaging jqueryStylePaging);

    /**
     * 分页查询总数
     * @param employeeInfo
     * @return
     */
    int getEmployeeTotal(@Param("employeeInfo") EmployeeInfoBean employeeInfo);
}
