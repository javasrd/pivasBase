package com.zc.pivas.medicaments.repository;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.docteradvice.entity.Prescription;
import com.zc.pivas.medicaments.entity.Medicaments;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 药品
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("medicamentsDao")
public interface MedicamentsDao extends BaseMapper<Medicaments, Long> {
    /**
     * 根据药品编码查询药品
     * @param medicamentsCode
     * @return
     */
    Medicaments getMediclByCode(@Param("medicamentsCode")
    String medicamentsCode);

    /**
     * 同步数据
     * @param medicaments
     */
    void synData(Medicaments medicaments);

    /**
     * 获取前一天药单表中药品消耗量
     * @return
     */
    List<Prescription> getMedicalStock();

    /**
     * 根据药品编码获取数据
     * @param map
     * @return
     */
    List<Map<String, Object>> qryMedForConfigFee(@Param("qry") Map<String, Object> map);

    Medicaments getConsumption(@Param("condition") Medicaments condition);

    void updateMedicType(@Param("id") String id, @Param("type") String type);
}
