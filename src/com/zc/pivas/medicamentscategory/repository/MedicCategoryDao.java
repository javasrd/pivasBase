package com.zc.pivas.medicamentscategory.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品分类Dao
 *
 * @author Jack.M
 * @version 1.0
 */
@MyBatisRepository("medicCategoryDao")
public interface MedicCategoryDao {
    /**
     * 添加药品分类
     *
     * @param category
     */
    public void addMedicCategory(MedicCategory category);

    /**
     * 分页查询药品分类
     *
     * @param category
     * @param paging
     * @return
     */
    public List<MedicCategory> listMedicCategory(@Param("mediccategory")
                                                         MedicCategory category, @Param("paging")
                                                         JqueryStylePaging paging);

    /**
     * 查询所有药品分类
     *
     * @return
     */
    public List<MedicCategory> queryAllMedicCategory();

    /**
     * 统计分类数量
     *
     * @param category
     * @return
     */
    public Integer getMedicCategoryTotal(@Param("mediccategory")
                                                 MedicCategory category);

    /**
     * 通过药品分类id查询药品分类
     *
     * @param categoryId
     * @return
     */
    public MedicCategory displayMedicCategory(Long categoryId);

    /**
     * 查询所有药品分类
     *
     * @return
     */
    public List<MedicCategory> listMedicCategoryByName(@Param("categoryName")
                                                               String categoryName);

    /**
     * 修改药品分类信息
     *
     * @param category
     */
    public void updateMedicCategory(MedicCategory category);

    /**
     * 删除药品分类
     */
    public void deleteMedicCategory(String categoryId);

    /**
     * 查询所有药品分类(用于增加配置费规则)
     *
     * @return
     */
    public List<MedicCategory> queryAllForAddConfigFeeRule(@Param("configFeeRuleGid")
                                                                   String configFeeRuleGid);

    /**
     * 校验分类名称是否存在
     *
     * @param category
     * @return
     */
    MedicCategory checkMedicCategoryName(@Param("mediccategory")
                                                 MedicCategory category);

    /**
     * 校验分类编码是否存在
     *
     * @param category
     * @return
     */
    MedicCategory checkMedicCategoryCode(@Param("mediccategory")
                                                 MedicCategory category);

    /**
     * 根据分类瓶签编码 查找药品分类信息
     *
     * @return
     */
    MedicCategory findCategoryByName(@Param("categoryCode")
                                             String categoryCode);

    public List<MedicCategory> qryMedicCategory();

    public List<MedicCategory> checkMedicCategoryOrder(@Param("mediccategory") MedicCategory category);
}
