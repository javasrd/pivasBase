package com.zc.pivas.medicamentslabel.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;

import java.lang.reflect.InvocationTargetException;

/**
 * 药品标签接口类
 *
 * @author kunkka
 * @version 1.0
 */
public interface MedicLabelService {
    /**
     * 新增药品标签
     */
    public void addMedicLabel(MedicLabel label);

    /**
     * 查询药品标签
     *
     * @param paging
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public JqueryStylePagingResults<MedicLabel> listMedicLabel(MedicLabel label, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * 修改药品标签
     *
     * @param label
     */
    public void updateMedicLabel(MedicLabel label);

    /**
     * 删除药品标签
     *
     * @param labelId
     */
    public void deleteMedicLabel(String labelId);

    /**
     * 获取药品标签详情
     *
     * @param labelId
     * @return
     */
    public MedicLabel displayMedicLabel(Long labelId);
}
