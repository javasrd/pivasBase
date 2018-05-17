package com.zc.pivas.medicamentslabel.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品标签
 *
 * @author Ray
 * @version 1.0
 */
@MyBatisRepository("medicLabelDao")
public interface MedicLabelDao {
    /**
     * 新增药品标签
     *
     * @param label
     */
    void addMedicLabel(MedicLabel label);

    /**
     * 分页查询药品标签
     *
     * @param label
     * @param jqueryStylePaging
     * @return
     */
    List<MedicLabel> listMedicLabel(@Param("mediclabel")
                                            MedicLabel label, @Param("paging")
                                            JqueryStylePaging jqueryStylePaging);

    /**
     * 统计标签总数
     *
     * @param label
     * @return
     */
    Integer getMedicLabelTotal(@Param("mediclabel") MedicLabel label);

    /**
     * 通过id查询药品标签
     *
     * @param labelId
     * @return
     */
    MedicLabel displayMedicLabel(Long labelId);

    /**
     * 通过名称查询药品标签
     *
     * @param label
     * @return
     */
    List<MedicLabel> listMedicLabelByName(@Param("label") MedicLabel label);

    /**
     * 修改药品标签
     *
     * @param label
     */
    void updateMedicLabel(MedicLabel label);

    /**
     * 删除药品标签
     *
     * @param labelId
     */
    void deleteMedicLabel(String labelId);

    /**
     * 统计药品标签引用
     * @param labelId
     * @return
     */
    Integer getLabelRefTotal(String labelId);
}
