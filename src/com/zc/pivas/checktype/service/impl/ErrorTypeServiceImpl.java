package com.zc.pivas.checktype.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.checktype.bean.ErrorTypeBean;
import com.zc.pivas.checktype.dao.ErrorTypeDAO;
import com.zc.pivas.checktype.service.ErrorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审方错误类型
 *
 * @author kunkka
 * @version 1.0
 */
@Service("errorTypeService")
public class ErrorTypeServiceImpl implements ErrorTypeService {
    private static final String PIVAS_ERRORTYPE_LEVEL = "pivas.errortype.level";

    private ErrorTypeDAO errorTypeDAO;

    /**
     * 添加审方错误类型
     *
     * @param bean 审方错误类型
     */
    @Override
    public void addErrorType(ErrorTypeBean bean) {
        errorTypeDAO.addErrorType(bean);
    }

    /**
     * 删除审方错误类型
     *
     * @param gid 主键id
     */
    @Override
    public void delErrorType(String gid) {
        String[] str = gid.split(",");
        Boolean flag = true;
        if (flag) {
            // 删除
            for (String st : str) {
                errorTypeDAO.delErrorType(st);
            }
        }

    }

    /**
     * 分页查询审方错误类型表数据
     *
     * @param bean             对象
     * @param jquryStylePaging 分页参数
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<ErrorTypeBean> getErrorTypeLsit(ErrorTypeBean bean,
                                                                    JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"name", "level", "color"};
        JqueryStylePagingResults<ErrorTypeBean> pagingResults = new JqueryStylePagingResults<ErrorTypeBean>(columns);

        // 总数
        int total;
        List<ErrorTypeBean> errorTypeBeanList = null;

        // 超级管理员
        errorTypeBeanList = errorTypeDAO.getErrorTypeList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(errorTypeBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            errorTypeBeanList = errorTypeDAO.getErrorTypeList(bean, jquryStylePaging);
        }

        //获取状态对应的国际化
        if (DefineCollectionUtil.isNotEmpty(errorTypeBeanList)) {
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            for (ErrorTypeBean errorType : errorTypeBeanList) {
                errorType.setLevelName(DictFacade.getName(PIVAS_ERRORTYPE_LEVEL,
                        DefineStringUtil.parseNull(errorType.getLevel()),
                        lang));

            }
        }

        total = errorTypeDAO.getErrorTypeTotal(bean);

        pagingResults.setDataRows(errorTypeBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 查询审核类型
     *
     * @param bean
     * @return
     */
    @Override
    public ErrorTypeBean getErrorType(ErrorTypeBean bean) {
        return errorTypeDAO.getErrorType(bean);
    }

    /**
     * 修改审方错误类型
     *
     * @param bean
     */
    @Override
    public void updateErrorType(ErrorTypeBean bean) {
        errorTypeDAO.updateErrorType(bean);
    }

    /**
     * 修改类型的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkErrorTypeName(ErrorTypeBean bean) {
        ErrorTypeBean errorType = errorTypeDAO.getErrorTypeForUPdate(bean);

        if (null == errorType) {
            return false;
        }

        return true;
    }

    @Autowired
    public void setErrorTypeDAO(ErrorTypeDAO errorTypeDAO) {
        this.errorTypeDAO = errorTypeDAO;
    }

    @Override
    public int checkErrorTypeUsed(String gid) {
        return errorTypeDAO.checkErrorTypeUsed(gid);
    }
}
