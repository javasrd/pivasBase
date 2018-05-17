package com.zc.pivas.checktype.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.checktype.bean.CheckTypeBean;
import com.zc.pivas.checktype.dao.CheckTypeDAO;
import com.zc.pivas.checktype.service.CheckTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 核对类型
 *
 * @author kunkka
 * @version 1.0
 */
@Service("checkTypeService")
public class CheckTypeServiceImpl implements CheckTypeService {
    private final static String PIVAS_CHECKTYPE_LEVEL = "pivas.checktype.type";

    private final static String PIVAS_COMMON_YESORNO = "pivas.common.yesorno";

    /**
     * 核对类型数据处理
     */
    private CheckTypeDAO checkTypeDAO;

    /**
     * 新增核对类型
     *
     * @param bean 核对类型
     */
    @Override
    public void addCheckType(CheckTypeBean bean) {
        checkTypeDAO.addCheckType(bean);

    }

    /**
     * 删除核对类型
     *
     * @param gid 主键id
     */
    @Override
    public void delCheckType(String gid) {
        String[] str = gid.split(",");
        Boolean flag = true;
        if (flag) {
            // 删除
            for (String st : str) {
                checkTypeDAO.delCheckType(st);
            }
        }

    }

    /**
     * 查询所有数据
     * 角色名称条件查询
     *
     * @param bean             对象
     * @param jquryStylePaging 分页参数
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<CheckTypeBean> getCheckTypeLsit(CheckTypeBean bean,
                                                                    JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns =
                new String[]{"orderID", "checkName", "checkType", "isShowResult", "isChargeResult", "isEffectResult", "isStockResult"};
        JqueryStylePagingResults<CheckTypeBean> pagingResults = new JqueryStylePagingResults<CheckTypeBean>(columns);

        // 总数
        int total;
        List<CheckTypeBean> checkTypeBeanList = checkTypeDAO.getCheckTypeList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(checkTypeBeanList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            checkTypeBeanList = checkTypeDAO.getCheckTypeList(bean, jquryStylePaging);
        }

        //获取状态对应的国际化
        if (DefineCollectionUtil.isNotEmpty(checkTypeBeanList)) {
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            for (CheckTypeBean checkType : checkTypeBeanList) {
                checkType.setCheckTypeName(DictFacade.getName(PIVAS_CHECKTYPE_LEVEL,
                        DefineStringUtil.parseNull(checkType.getCheckType()),
                        lang));

                checkType.setIsChargeResult(DictFacade.getName(PIVAS_COMMON_YESORNO,
                        DefineStringUtil.parseNull(checkType.getIsCharge()),
                        lang));

                checkType.setIsEffectResult(DictFacade.getName(PIVAS_COMMON_YESORNO,
                        DefineStringUtil.parseNull(checkType.getIsEffect()),
                        lang));

                checkType.setIsShowResult(DictFacade.getName(PIVAS_COMMON_YESORNO,
                        DefineStringUtil.parseNull(checkType.getIsShow()),
                        lang));

                checkType.setIsStockResult(DictFacade.getName(PIVAS_COMMON_YESORNO,
                        DefineStringUtil.parseNull(checkType.getIsStock()),
                        lang));
            }
        }

        total = checkTypeDAO.getCheckTypeTotal(bean);

        pagingResults.setDataRows(checkTypeBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 修改修改核对类型
     *
     * @param bean 需要修改的数据
     */
    @Override
    public void updateCheckType(CheckTypeBean bean) {
        checkTypeDAO.updateCheckType(bean);

    }

    /**
     * 查询类型数据
     *
     * @return 审核错误类型
     */
    public CheckTypeBean getCheckType(CheckTypeBean bean) {
        return checkTypeDAO.getCheckType(bean);
    }

    /**
     * 修改类型的时候判断名称是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkNameIsExitst(CheckTypeBean bean) {
        CheckTypeBean checkType = checkTypeDAO.checkNameIsExist(bean);

        if (null == checkType) {
            return false;
        }

        return true;
    }

    /**
     * 修改类型的时候判断顺序id是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkOrderIDIsExitst(CheckTypeBean bean) {
        CheckTypeBean checkType = checkTypeDAO.checkOrderIDIsExist(bean);

        if (null == checkType) {
            return false;
        }

        return true;
    }

    /**
     * 修改类型的时候判断和对类型是否存在
     *
     * @param bean 查询条件
     * @return 是否存在
     */
    public boolean checkTypeIsExitst(CheckTypeBean bean) {
        CheckTypeBean checkType = checkTypeDAO.checkTypeIsExitst(bean);

        if (null == checkType) {
            return false;
        }

        return true;
    }

    @Autowired
    public void setCheckTypeDAO(CheckTypeDAO checkTypeDAO) {
        this.checkTypeDAO = checkTypeDAO;
    }

}
