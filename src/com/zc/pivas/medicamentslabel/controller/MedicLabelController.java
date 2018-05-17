package com.zc.pivas.medicamentslabel.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.web.i18n.MessageHolder;
import com.zc.pivas.medicamentslabel.entity.MedicLabel;
import com.zc.pivas.medicamentslabel.service.MedicLabelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 药品标签Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/drugslabel")
public class MedicLabelController extends SdBaseController {
    private static Log log = LogFactory.getLog(MedicLabelController.class);

    @Resource
    private MedicLabelService medicLabelService;

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    @Autowired
    private MessageHolder messageHolder;

    /***
     *
     * 初始化页面
     *
     * @return 药品标签查询页面
     * @param model
     *            Model
     */
    @RequestMapping(value = "/initDrugsLabel")
    @RequiresPermissions(value = {"PIVAS_MENU_203"})
    public String initMedicCategory(Model model) {
        return "pivas/mediclabel/mediclabelList";
    }

    /**
     * 新增标签
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addDrugsLabel", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_204"})
    public String addMedicLabel(MedicLabel label)
            throws Exception {
        try {
            medicLabelService.addMedicLabel(label);

            // 记录成功的操作日志
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_LABELS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.addLabel", new String[]{label.getLabelName().toString(),
                            label.getIsActive() + ""}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_LABELS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.addLabel", new String[]{label.getLabelName().toString(),
                            label.getIsActive() + ""}),
                    false);
            throw e;
        }
    }

    /**
     * 删除标签
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteDrugsLabel", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_205"})
    public String deleteMedicLabel(String labels)
            throws Exception {
        try {
            if (labels != null && !"".equals(labels)) {
                String[] label = labels.split(",");
                for (int i = 0; i < label.length; i++) {
                    medicLabelService.deleteMedicLabel(label[i]);
                }
                // 记录成功的操作日志
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_LABELS,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.medic.tip.deleteLabel", new String[]{labels}),
                        true);
            }
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_LABELS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.deleteLabel", new String[]{labels}),
                    false);
            throw e;
        }
    }

    /**
     * 修改标签
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDrugsLabel", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_207"})
    public String updateMedicLabel(MedicLabel label)
            throws Exception {
        try {
            medicLabelService.updateMedicLabel(label);

            // 记录成功的操作日志
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_LABELS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.updateLabel", new String[]{label.getLabelId().toString(),
                            label.getLabelName().toString(), label.getIsActive() + ""}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_LABELS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.updateLabel", new String[]{label.getLabelId().toString(),
                            label.getLabelName().toString(), label.getIsActive() + ""}),
                    false);
            throw e;
        }
    }

    /**
     * 标签查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listDrugsLabel", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_206"})
    public String listMedicLabel(MedicLabel label, JqueryStylePaging jquryStylePaging)
            throws Exception {
        try {
            String[] labelNameStrings = label.getLabelNames();
            if (labelNameStrings != null) {
                for (int i = 0; i < labelNameStrings.length; ++i) {
                    labelNameStrings[i] = DefineStringUtil.escapeAllLike(labelNameStrings[i]);
                }
            }
            label.setLabelName(DefineStringUtil.escapeAllLike(label.getLabelName()));
            JqueryStylePagingResults<MedicLabel> listMedicLabel =
                    medicLabelService.listMedicLabel(label, jquryStylePaging);

            // 记录成功的操作日志

            return new Gson().toJson(listMedicLabel);
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理

            throw e;
        }
    }

    /**
     * 标签详情
     *
     * @param label
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/displayDrugsLabel", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_207"})
    public String displayMedicLabel(MedicLabel label)
            throws Exception {
        try {
            MedicLabel labelInfo = medicLabelService.displayMedicLabel(label.getLabelId());

            // 记录成功的操作日志

            return new Gson().toJson(labelInfo);
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理

            throw e;
        }
    }
}
