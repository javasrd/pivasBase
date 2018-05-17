package com.zc.pivas.medicaments.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicaments.entity.MedicamentsRefLabel;

import java.util.List;

/**
 * 药品与标签
 *
 * @author Ray
 * @version 1.0
 */
public interface MedicamentsRefLabelService {

    /**
     * @param medicamentsRefLabel
     */
    void addMedicamentsRefLabel(MedicamentsRefLabel medicamentsRefLabel);

    /**
     * @param medicamentsRefLabel
     */
    void deleteMedicamentsRefLabel(MedicamentsRefLabel medicamentsRefLabel);

    /**
     * 根据条件分页查询药品标签
     *
     * @param paging
     * @param condition
     * @return
     */
    List<MedicamentsRefLabel> getMedicamentsRefLabel(JqueryStylePaging paging, MedicamentsRefLabel condition);

}
