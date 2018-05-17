package com.zc.pivas.drugDamage.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.drugDamage.bean.DrugDamageBean;
import com.zc.pivas.drugDamage.bean.DrugDamageProblemOrLink;
import com.zc.pivas.medicaments.entity.Medicaments;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 *
 * @author Jack.M
 * @version 1.0
 */
public interface DrugDamageService {

    /**
     * 分页查询所有破损药品
     *
     * @param bean
     * @param paging
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public JqueryStylePagingResults<DrugDamageBean> getAllDrugDamage(DrugDamageBean bean, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * 添加破损药品
     *
     * @param bean
     */
    public void addDrugDamage(DrugDamageBean bean);

    /**
     * 修改破损药品
     *
     * @param bean
     */
    public void updateDrugDamage(DrugDamageBean bean);

    /**
     * 删除破损药品
     *
     * @param gid
     */
    public void deleteDrugDamage(Long gid);

    /**
     * 根据破损药品ID查询药品详情
     *
     * @param gid
     * @return
     */
    public DrugDamageBean displayDrugDamage(Long gid);

    /**
     * 查询所有药师
     *
     * @return
     */
    public List<User> getAllUsers();

    /**
     * 获取药品
     *
     * @param condition
     * @return
     */
    public List<Medicaments> queryMedicByCondition(Medicaments condition);

    /**
     * 获取质量问题和破损环节
     *
     * @param type
     * @return
     */
    public List<DrugDamageProblemOrLink> getAlldamageProblemOrLink(String type);
}
