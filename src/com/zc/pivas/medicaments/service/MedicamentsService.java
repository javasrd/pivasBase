package com.zc.pivas.medicaments.service;

import com.zc.base.common.service.BaseService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品
 *
 * @author Ray
 * @version 1.0
 */
public interface MedicamentsService extends BaseService<Medicaments, Long> {
    /**
     * 查询药品分类
     *
     * @return
     */
    List<MedicCategory> listMedicCategory();

    /**
     * 查询药品标签
     *
     * @return
     */
    List<MedicLabel> listMedicLabel();

    /**
     * 同步数据
     *
     * @param medicaments
     */
    void synData(Medicaments medicaments);

    /**
     * 根据药品编码查询药品
     *
     * @param medicamentsCode
     * @return
     */
    Medicaments getMediclByCode(String medicamentsCode);

    /**
     * 药品库存告警
     */
    void checkMedicamentStock();

    Medicaments getConsumption(@Param("condition") Medicaments condition);

    void updateMedicType(String id, String type);
}
