package com.zc.pivas.medicaments.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.medicaments.entity.MedicamentsRefLabel;
import com.zc.pivas.medicaments.repository.MedicamentsRefLabelDao;
import com.zc.pivas.medicaments.service.MedicamentsRefLabelService;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 药品与标签
 *
 * @author Ray
 * @version 1.0
 *
 */
@Service("medicamentsRefLabelService")
public class MedicamentsRefLabelServiceImpl implements MedicamentsRefLabelService
{
    
    @Resource
    private MedicamentsRefLabelDao medicamentsRefLabelDao;
    
    @Resource
    private MedicLabelDao medicLabelDao;
    
    @Override
    public void addMedicamentsRefLabel(MedicamentsRefLabel medicamentsRefLabel)
    {
        medicamentsRefLabelDao.addMedicamentsRefLabel(medicamentsRefLabel);
    }
    
    @Override
    public void deleteMedicamentsRefLabel(MedicamentsRefLabel medicamentsRefLabel)
    {
        medicamentsRefLabelDao.delMedicamentsRefLabel(medicamentsRefLabel);
    }

    /**
     * 根据条件分页查询药品标签
     * @param paging
     * @param condition
     * @return
     */
    @Override
    public List<MedicamentsRefLabel> getMedicamentsRefLabel(JqueryStylePaging paging, MedicamentsRefLabel condition)
    {
        
        List<MedicamentsRefLabel> medicamentsRefLabelList =
            medicamentsRefLabelDao.getMedicamentsRefLabel(paging, condition);
        
        for (MedicamentsRefLabel ref : medicamentsRefLabelList)
        {
            MedicLabel medicLabel = medicLabelDao.displayMedicLabel(ref.getLabelId());
            
            ref.setLabelId(medicLabel.getLabelId());
            ref.setLabelName(medicLabel.getLabelName());
            ref.setIsActive(medicLabel.getIsActive());
            if (null != condition)
            {
                ref.setMedicamentsName(condition.getMedicamentsName());
            }
        }
        
        return medicamentsRefLabelList;
    }
    
}
