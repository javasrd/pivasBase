package com.zc.pivas.medicamentslabel.service.impl;

import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import com.zc.pivas.medicamentslabel.service.MedicLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 药品标签接口实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("medicLabelService")
public class MedicLabelServImpl implements MedicLabelService {
    private static final Logger logger = LoggerFactory.getLogger(MedicLabelServImpl.class);

    @Resource
    private MedicLabelDao medicLabelDao;

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /**
     * 新增药品标签
     *
     * @param label
     */
    public void addMedicLabel(MedicLabel label) {
        // 药品标签名称不相同
        List<MedicLabel> labelList = medicLabelDao.listMedicLabelByName(label);
        for (MedicLabel myLabel : labelList) {
            if (label.getLabelName().equals(myLabel.getLabelName())) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.LABEL_NAME_REPEAT, null, null);
            } else if (label.getLabelNo().equals(myLabel.getLabelNo())) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.LABEL_NO_REPEAT, null, null);
            }
        }
        medicLabelDao.addMedicLabel(label);
    }

    /**
     * 查询药品标签
     *
     * @param label
     * @param paging
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public JqueryStylePagingResults<MedicLabel> listMedicLabel(MedicLabel label, JqueryStylePaging paging)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 用于和页面显示顺序一样
        String[] columns = new String[]{"labelId", "labelName", "labelNo", "isActiveResult", "isNull"};
        JqueryStylePagingResults<MedicLabel> pagingResults = new JqueryStylePagingResults<MedicLabel>(columns);

        // 分页查询
        List<MedicLabel> MedicLabelList = medicLabelDao.listMedicLabel(label, paging);

        pagingResults.setDataRows(MedicLabelList);
        // 总数
        Integer total = medicLabelDao.getMedicLabelTotal(label);
        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());
        return pagingResults;
    }

    /**
     * 删除一个药品标签
     */
    public void deleteMedicLabel(String labelId) {
        // 判断是否有引用药品标签
        int count = medicLabelDao.getLabelRefTotal(labelId);
        if (count == 0) {
            medicLabelDao.deleteMedicLabel(labelId);
        } else {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_HAS_CHILD, null, null);
        }
    }

    /**
     * 修改药品标签
     *
     * @param label
     */
    public void updateMedicLabel(MedicLabel label) {
        // 判断要修改的药品标签有没有删除
        MedicLabel categoryResult = medicLabelDao.displayMedicLabel(label.getLabelId());
        if (null != categoryResult) {
            List<MedicLabel> labelList = medicLabelDao.listMedicLabelByName(label);
            for (MedicLabel myLabel : labelList) {
                if (myLabel.getLabelId().longValue() != label.getLabelId().longValue()) {
                    if (label.getLabelName().equals(myLabel.getLabelName())) {
                        throw sdExceptionFactory.createSdException(ExceptionCodeConstants.LABEL_NAME_REPEAT, null, null);
                    } else if (label.getLabelNo().equals(myLabel.getLabelNo())) {
                        throw sdExceptionFactory.createSdException(ExceptionCodeConstants.LABEL_NO_REPEAT, null, null);
                    }
                }
            }
            medicLabelDao.updateMedicLabel(label);
        } else {
            throw sdExceptionFactory.createSdException(ExceptionCodeConstants.RECORD_DELETE, null, null);
        }
    }

    /**
     * 获取药品标签详情
     * @param labelId
     * @return
     */
    @Override
    public MedicLabel displayMedicLabel(Long labelId) {
        return medicLabelDao.displayMedicLabel(labelId);
    }
}
