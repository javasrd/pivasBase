package com.zc.pivas.drugDamage.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.drugDamage.bean.DrugDamageBean;
import com.zc.pivas.drugDamage.bean.DrugDamageProblemOrLink;
import com.zc.pivas.medicaments.entity.Medicaments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品破损登记Dao
 *
 * @author Jack.M
 * @version 1.0
 */
@MyBatisRepository("drugDamageDao")
public interface DrugDamageDao
{
    
    /**
     * 分页查询所有破损药品
     *
     * @param drugDamage
     * @param jqueryStylePaging
     * @return
     */
    public List<DrugDamageBean> getAllDrugDamage(@Param("drugDamage")DrugDamageBean drugDamage, @Param("paging")JqueryStylePaging jqueryStylePaging);
    
    /**
     * 破损药品数量
     * 
     * @param drugDamage
     * @return
     */
    public Integer getDrugDamageTotal(@Param("drugDamage")DrugDamageBean drugDamage);
    
    /**
     * 添加破损药品
     * 
     * @param bean
     */
    public void addDrugDamage(DrugDamageBean bean);
    
    /**
     * 判断药品是否存在
     * 
     * @param gid
     * @return
     */
    public DrugDamageBean isDrugDamageExit(Long gid);
    
    /**
     * 修改破损药品
     * 
     * @param bean
     */
    public void updateDrugDamage(DrugDamageBean bean);
    
    /**
     * 根据药品编码查询破损药品
     *
     * @param drugCode
     * @return
     */
    public List<DrugDamageBean> getDrugDamageByCode(@Param("drugCode")String drugCode);

    /**
     * 删除破损药品
     * 
     * @param gid
     */
    public void deleteDrugDamage(Long gid);
    
    /**
     * 根据破损药品的ID获取破损药品的数量
     * 
     * @param gid
     * @return
     */
    public Integer getDrugDamageCount(Long gid);
    
    /**
     * 根据破损药品的ID获取破损药品的详情
     * 
     * @param gid
     * @return
     */
    public DrugDamageBean displayDrugDamage(@Param("gid")Long gid);
    
    /**
     * 获取所有的药师
     * 
     * @return
     */
    public List<User> getAllUsers();
    
    /**
     * 获取所有的药品药品
     *
     * @param condition
     * @return
     */
    public List<Medicaments> queryMedicByCondition(@Param("condition")Medicaments condition);
    
    /**
     * 获取质量问题和破损环节
     * 
     * @param type
     * @return
     */
    public List<DrugDamageProblemOrLink> getAlldamageProblemOrLink(@Param("type")String type);
}
