package com.zc.pivas.medicaments.service.impl;

import com.zc.base.common.dao.BaseMapper;
import com.zc.base.common.service.BaseServiceImpl;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.backup.bean.ServerNodeBean;
import com.zc.pivas.backup.service.ServerNodeService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.entity.MedicamentsRefLabel;
import com.zc.pivas.medicaments.repository.MedicamentsDao;
import com.zc.pivas.medicaments.repository.MedicamentsRefLabelDao;
import com.zc.pivas.medicaments.service.MedicamentsService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 药品
 *
 * @author Ray
 * @version 1.0
 */
@Service("medicamentsService")
public class MedicamentsServiceImpl extends BaseServiceImpl<Medicaments, Long> implements MedicamentsService {

    @Resource
    private MedicamentsDao medicamentsDao;

    @Resource
    private MedicamentsRefLabelDao medicamentsRefLabelDao;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    @Resource
    private MedicLabelDao medicLabelDao;

    @Resource
    private ServerNodeService serverNodeService;

    private static final Logger log = LoggerFactory.getLogger(MedicamentsServiceImpl.class);

    @Override
    public BaseMapper<Medicaments, Long> getDao() {
        return medicamentsDao;
    }

    @Override
    public void afterInsert(Medicaments model) {
        Long[] labelIds = model.getLabelIds();
        if (DefineStringUtil.isArrayNotEmpty(labelIds)) {
            for (Long labelId : labelIds) {
                MedicamentsRefLabel ref = new MedicamentsRefLabel();
                ref.setLabelId(labelId);
                ref.setMedicamentsId(model.getMedicamentsId());
                medicamentsRefLabelDao.addMedicamentsRefLabel(ref);
            }
        }
    }

    @Override
    public void beforeUpdate(Medicaments model) {
        MedicamentsRefLabel ref = new MedicamentsRefLabel();
        ref.setMedicamentsId(model.getMedicamentsId());
        medicamentsRefLabelDao.delMedicamentsRefLabel(ref);
    }

    @Override
    public void afterUpdate(Medicaments model) {
        this.afterInsert(model);
    }

    @Override
    public List<MedicCategory> listMedicCategory() {
        List<MedicCategory> list = medicCategoryDao.listMedicCategory(null, null);
        return list;
    }

    @Override
    public List<MedicLabel> listMedicLabel() {
        List<MedicLabel> list = medicLabelDao.listMedicLabel(null, null);
        return list;
    }

    @Override
    public void synData(Medicaments medicaments) {
        medicamentsDao.synData(medicaments);
    }

    @Override
    public Medicaments getMediclByCode(String medicamentsCode) {
        return medicamentsDao.getMediclByCode(medicamentsCode);
    }

    @Override
    public void checkMedicamentStock() {
        //备机状态下不执行任务
        if (serverNodeService != null) {
            String localip = Propertiesconfiguration.getStringProperty("localip");
            if (localip != null) {
                ServerNodeBean bean = serverNodeService.getServerNode(localip);
                if (bean != null) {
                    if (bean.getFlag() == 1) {
                        log.info("Backup mode,do not exec time task checkMedicamentStock");
                        return;
                    }
                }
            }
        }

        log.info("Ready to exec time task checkMedicamentStock");

        // 按照药品编码进行分组，统计出前一天药品的消耗量
        //List<YD> ydList = medicamentsDao.getMedicalStock();

        // 获取药品消耗量
        List<Medicaments> medicamentList = queryByPaging(new JqueryStylePaging(), new Medicaments());

        // 当前库存
        float stock_now;

        // 前一天药品消耗量
        float stock_last_day;

        // 对应上一周药品消耗量
        float stock_last_week;

        float stock_last;

        // 是否需要告警
        boolean stockWarn = false;

        // 系统设置库存最低量
        int limit = Integer.parseInt(Propertiesconfiguration.getStringProperty("pivas.drug.minimum.limit"));
        if (DefineCollectionUtil.isNotEmpty(medicamentList)) {
            for (Medicaments medicament : medicamentList) {
                stockWarn = false;
                limit = (medicament.getMedicamentsLimit() == null) ? limit : NumberUtils.toInt(medicament.getMedicamentsLimit());

                stock_now = Float.valueOf(medicament.getMedicamentsStock());
                stock_last_day =
                        Float.valueOf((StrUtil.isNotNull(medicament.getLastDayUsed())) ? medicament.getLastDayUsed() : "0");
                stock_last_week =
                        Float.valueOf((StrUtil.isNotNull(medicament.getLastWeekUsed())) ? medicament.getLastWeekUsed()
                                : "0");

                stock_last = (stock_last_week < stock_last_day) ? stock_last_day : stock_last_week;

                if (stock_last > 0) {
                    if (stock_now < limit || stock_now < stock_last
                            || stock_now < Float.valueOf(medicament.getMedicamentsLimit())) {
                        medicament.setMedicamentsOver("1");
                        medicamentsDao.updateByPrimaryKeySelective(medicament);

                        stockWarn = true;
                    }
                } else {
                    if (stock_now < limit || stock_now < Float.valueOf(medicament.getMedicamentsLimit())) {
                        medicament.setMedicamentsOver("1");
                        medicamentsDao.updateByPrimaryKeySelective(medicament);

                        stockWarn = true;
                    }
                }

                if (!stockWarn && medicament.getMedicamentsOver().equals("1")) {
                    medicament.setMedicamentsOver("0");
                    medicamentsDao.updateByPrimaryKeySelective(medicament);
                }
            }
        }

    }

    @Override
    public Medicaments getConsumption(@Param("condition")
                                              Medicaments condition) {
        return medicamentsDao.getConsumption(condition);
    }

    @Override
    public void updateMedicType(String id, String type) {
        medicamentsDao.updateMedicType(id, type);
    }

}
