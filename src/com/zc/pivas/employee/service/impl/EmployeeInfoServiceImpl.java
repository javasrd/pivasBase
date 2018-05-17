package com.zc.pivas.employee.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.employee.bean.EmployeeInfoBean;
import com.zc.pivas.employee.dao.EmployeeInfoDAO;
import com.zc.pivas.employee.service.EmployeeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工
 *
 * @author Ray
 * @version 1.0
 */
@Service("employeeInfoService")
public class EmployeeInfoServiceImpl implements EmployeeInfoService {
    @Resource
    private EmployeeInfoDAO employeeInfoDAO;

    @Override
    public void addEmployeeInfo(EmployeeInfoBean bean) {
        employeeInfoDAO.addEmployeeInfo(bean);
    }

    @Override
    public void updateEmployeeInfo(EmployeeInfoBean bean) {
        employeeInfoDAO.updateEmployeeInfo(bean);

    }

    @Override
    public EmployeeInfoBean getEmployeeInfo(EmployeeInfoBean bean) {
        return employeeInfoDAO.getEmployeeInfo(bean);
    }

    @Override
    public void synData(EmployeeInfoBean bean) {
        employeeInfoDAO.synData(bean);
    }

    @Override
    public List<EmployeeInfoBean> getEmployeeInfoList(EmployeeInfoBean employeeInfo, JqueryStylePaging jqueryStylePaging) {

        return employeeInfoDAO.getEmployeeInfoList(employeeInfo, jqueryStylePaging);
    }

    /**
     * 按条件分页查询员工数据
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<EmployeeInfoBean> getEmployeeList(EmployeeInfoBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"staff_Code", "staff_Name"};
        JqueryStylePagingResults<EmployeeInfoBean> pagingResults = new JqueryStylePagingResults<EmployeeInfoBean>(columns);

        // 总数
        int total = 0;

        // 超级管理员
        List<EmployeeInfoBean> employeeList = employeeInfoDAO.getEmployeeInfoList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(employeeList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            employeeList = employeeInfoDAO.getEmployeeInfoList(bean, jquryStylePaging);
        }

        total = employeeInfoDAO.getEmployeeTotal(bean);

        pagingResults.setDataRows(employeeList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;

    }

    @Override
    public int getEmployeeTotal(EmployeeInfoBean employeeInfo) {
        return employeeInfoDAO.getEmployeeTotal(employeeInfo);
    }

}
