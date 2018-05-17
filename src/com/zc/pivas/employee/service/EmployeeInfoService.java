package com.zc.pivas.employee.service;


import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.employee.bean.EmployeeInfoBean;
import com.zc.pivas.employee.dao.EmployeeInfoDAO;

/**
 * 员工
 *
 * @author Ray
 * @version 1.0
 */
public interface EmployeeInfoService extends EmployeeInfoDAO {

    /**
     * 同步数据
     * @param bean
     */
    void synData(EmployeeInfoBean bean);

    /**
     * 按条件分页查询员工数据
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<EmployeeInfoBean> getEmployeeList(EmployeeInfoBean bean, JqueryStylePaging jquryStylePaging) throws Exception;
}
