package com.zc.pivas.drugDamage.service.impl;

import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.drugDamage.bean.DrugDamageBean;
import com.zc.pivas.drugDamage.bean.DrugDamageProblemOrLink;
import com.zc.pivas.drugDamage.dao.DrugDamageDao;
import com.zc.pivas.drugDamage.service.DrugDamageService;
import com.zc.pivas.medicaments.entity.Medicaments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * DrugDamageService接口实现类
 *
 * @author Jack.M
 * @version 1.0
 */
@Service("drugDamageService")
public class DrugDamageServiceImpl implements DrugDamageService {

    @Resource
    private DrugDamageDao drugDamageDao;

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 分页查询所有破损药品
     *
     * @param bean
     * @param paging
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public JqueryStylePagingResults<DrugDamageBean> getAllDrugDamage(DrugDamageBean bean, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"gid", "drugCode", "drugName", "specifications", "drugPlace", "damageProblem", "damageLink", "quantity", "registName", "registTime"};
        JqueryStylePagingResults<DrugDamageBean> pagingResults = new JqueryStylePagingResults<DrugDamageBean>(columns);

        // 分页查询
        List<DrugDamageBean> damageList = drugDamageDao.getAllDrugDamage(bean, paging);

        pagingResults.setDataRows(damageList);
        // 总数
        Integer total = drugDamageDao.getDrugDamageTotal(bean);

        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());
        return pagingResults;
    }

    /**
     * 添加破损药品
     *
     * @param bean 破损药品实体bean
     */
    public void addDrugDamage(DrugDamageBean bean) {
        // 药品标签名称不相同
        List<DrugDamageBean> drugList = drugDamageDao.getDrugDamageByCode(bean.getDrugCode());
        if (DefineCollectionUtil.isNotEmpty(drugList)) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CODE_REPEAT, null, null);
        }
        drugDamageDao.addDrugDamage(bean);
    }

    /**
     * 修改破损药品
     *
     * @param bean 破损药品实体bean
     */
    public void updateDrugDamage(DrugDamageBean bean) {
        // 判断要修改的药品标签有没有删除
        DrugDamageBean isDrugDamageExit = drugDamageDao.isDrugDamageExit(bean.getGid());
        if (null != isDrugDamageExit) {
            drugDamageDao.updateDrugDamage(bean);
        } else {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }

    }

    /**
     * 删除破损药品
     *
     * @param gid 破损药品ID
     */
    public void deleteDrugDamage(Long gid) {
        // 判断是否有引用药品标签
        int count = drugDamageDao.getDrugDamageCount(gid);
        if (count == 1) {
            drugDamageDao.deleteDrugDamage(gid);
        } else {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_HAS_CHILD, null, null);
        }
    }

    /**
     * 根据破损药品ID查询药品详情
     *
     * @param gid 破损药品ID
     */
    @Override
    public DrugDamageBean displayDrugDamage(Long gid) {
        return drugDamageDao.displayDrugDamage(gid);
    }

    /**
     * 查询所有药师
     */
    @Override
    public List<User> getAllUsers() {
        return drugDamageDao.getAllUsers();
    }

    /**
     * 获取药品
     *
     * @param condition 药品信息
     */
    @Override
    public List<Medicaments> queryMedicByCondition(Medicaments condition) {
        return drugDamageDao.queryMedicByCondition(condition);
    }

    /**
     * 获取质量问题和破损环节
     *
     * @param type 查询数据的类型
     */
    @Override
    public List<DrugDamageProblemOrLink> getAlldamageProblemOrLink(String type) {

        return drugDamageDao.getAlldamageProblemOrLink(type);
    }

}
