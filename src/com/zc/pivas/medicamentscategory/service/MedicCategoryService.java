package com.zc.pivas.medicamentscategory.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 药品分类接口类
 *
 * @author kunkka
 * @version 1.0
 */
public interface MedicCategoryService {
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
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public JqueryStylePagingResults<MedicCategory> listMedicCategory(MedicCategory category, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * 修改药品分类信息
     *
     * @param category
     */
    public void updateMedicCategory(MedicCategory category);

    /**
     * 删除药品分类
     *
     */
    public void deleteMedicCategory(String categoryId);

    /**
     * 查询分类详情
     *
     * @param categoryId
     * @return
     */
    public MedicCategory displayCategory(Long categoryId);

    /**
     * 校验分类名称是否存在
     *
     * @param category
     * @return
     */
    boolean checkMedicCategoryName(MedicCategory category);

    /**
     * 校验分类编码是否存在
     *
     * @param category
     * @return
     */
    boolean checkMedicCategoryCode(MedicCategory category);

    /**
     * 根据分类瓶签编码查找药品分类信息
     *
     * @return
     */
    public MedicCategory findCategoryByName(String categoryCode);


    public List<MedicCategory> qryMedicCategory();

    public boolean checkMedicCategoryOrder(MedicCategory category);
}
