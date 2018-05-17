package com.zc.pivas.medicamentscategory.service.impl;

import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 药品分类接口实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("medicCategoryService")
public class MedicCategoryServImpl implements MedicCategoryService {
    @Resource
    private MedicCategoryDao medicCategoryDao;

    @Resource
    private MedicamentsDao medicamentsDao;

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 添加药品分类
     *
     * @param category
     */
    public void addMedicCategory(MedicCategory category) {
        // 药品分类名称不相同
        List<MedicCategory> categoryList = medicCategoryDao.listMedicCategoryByName(category.getCategoryName());
        if (DefineCollectionUtil.isNotEmpty(categoryList)) {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
        }
        medicCategoryDao.addMedicCategory(category);
    }

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
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"categoryId", "categoryName", "categoryCode", "categoryPriority", "categoryIsHard"};
        JqueryStylePagingResults<MedicCategory> pagingResults = new JqueryStylePagingResults<MedicCategory>(columns);

        // 分页查询
        List<MedicCategory> medicCategoryList = medicCategoryDao.listMedicCategory(category, paging);

        pagingResults.setDataRows(medicCategoryList);
        // 总数
        Integer total = medicCategoryDao.getMedicCategoryTotal(category);
        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());
        return pagingResults;
    }

    /**
     * 删除药品分类
     */
    public void deleteMedicCategory(String categoryId) {
        // 判断是否有药品在该药品分类下
        Medicaments medicaments = new Medicaments();
        medicaments.setCategoryId(categoryId);
        int medicamentsCount = medicamentsDao.getTotalCount(medicaments);

        if (medicamentsCount == 0) {
            medicCategoryDao.deleteMedicCategory(categoryId);
        } else {
            throw sdExceptionFactory.createSdException("70011", null, null);
        }
    }

    /**
     * 修改药品分类
     *
     * @param category
     */
    public void updateMedicCategory(MedicCategory category) {
        // 判断要修改的药品分类有没有删除
        MedicCategory categoryResult = medicCategoryDao.displayMedicCategory(category.getCategoryId());
        if (null != categoryResult) {
            medicCategoryDao.updateMedicCategory(category);
        } else {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }

    }

    @Override
    public MedicCategory displayCategory(Long categoryId) {
        return medicCategoryDao.displayMedicCategory(categoryId);
    }

    @Override
    public boolean checkMedicCategoryName(MedicCategory category) {
        MedicCategory result = medicCategoryDao.checkMedicCategoryName(category);

        if (null == result) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkMedicCategoryCode(MedicCategory category) {
        MedicCategory result = medicCategoryDao.checkMedicCategoryCode(category);

        if (null == result) {
            return false;
        }
        return true;
    }

    public MedicCategory findCategoryByName(String categoryCode) {
        MedicCategory result = medicCategoryDao.findCategoryByName(categoryCode);
        return result;
    }

    @Override
    public List<MedicCategory> qryMedicCategory() {
        return medicCategoryDao.qryMedicCategory();
    }

    @Override
    public boolean checkMedicCategoryOrder(MedicCategory category) {
        List<MedicCategory> result = medicCategoryDao.checkMedicCategoryOrder(category);

        if (result.size() == 0) {
            return false;
        }
        return true;
    }
}
