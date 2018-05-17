package com.zc.pivas.medicamentscategory.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.ExceptionCodeConstants;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 药品分类Controller
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/drugscategory")
public class MedicCategoryController extends SdBaseController {
    private static Log log = LogFactory.getLog(MedicCategoryController.class);

    @Resource
    private MedicCategoryService medicCategoryService;

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    /***
     *
     * 初始化页面
     *
     * @return 药品分类查询页面
     * @param model
     *            Model
     */
    @RequestMapping(value = "/initDrugsCategory")
    @RequiresPermissions(value = {"PIVAS_MENU_211"})
    public String initMedicCategory(Model model) {
        return "pivas/mediccategory/mediccategoryList";
    }

    /**
     * 新增药品分类
     *
     * @param category
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addDrugsCategory", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_212"})
    public String addMedicCategory(MedicCategory category)
            throws Exception {
        // 校验名称是否使用
        boolean checkName = medicCategoryService.checkMedicCategoryName(category);

        // 校验编码是否使用
        boolean checkCode = false;
        // 校验优先级
        boolean checkOrder = medicCategoryService.checkMedicCategoryOrder(category);

        if (null != category.getCategoryCode() && !"".equals(category.getCategoryCode())) {
            checkCode = medicCategoryService.checkMedicCategoryCode(category);
        }

        try {
            if (checkName) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else if (checkCode) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CODE_REPEAT, null, null);
            } else if (checkOrder) {
                throw sdExceptionFactory.createSdException("70012", null, null);
            }


            medicCategoryService.addMedicCategory(category);

            // 记录成功的操作日志
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_CLASS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.addCategory", new String[]{category.getCategoryName().toString(),
                            category.getCategoryCode().toString(), category.getCategoryPriority().toString(), category.getCategoryIsHard().toString()}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_CLASS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.addCategory", new String[]{category.getCategoryName().toString(),
                            category.getCategoryCode().toString(), category.getCategoryPriority().toString(), category.getCategoryIsHard().toString()}),
                    false);
            throw e;
        }
    }

    /**
     * 删除药品分类
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delDrugsCategory", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_213"})
    public String deleteMedicCategory(String categorys)
            throws Exception {
        try {
            if (categorys != null && !"".equals(categorys)) {
                String[] category = categorys.split(",");
                for (int i = 0; i < category.length; i++) {
                    medicCategoryService.deleteMedicCategory(category[i]);
                }
                // 记录成功的操作日志
                addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_CLASS,
                        AmiLogConstant.BRANCH_SYSTEM.CF,
                        getMessage("log.medic.tip.deleteCategory", new String[]{categorys.toString()}),
                        true);
            }

            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_CLASS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.deleteCategory", new String[]{categorys.toString()}),
                    false);
            throw e;
        }
    }

    /**
     * 修改药品分类
     *
     * @param category
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDrugsCategory", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_215"})
    public String updateMedicCategory(MedicCategory category)
            throws Exception {
        // 校验名称是否使用
        boolean checkName = medicCategoryService.checkMedicCategoryName(category);

        // 校验编码是否使用
        boolean checkCode = false;

        // 校验优先级
        boolean checkOrder = medicCategoryService.checkMedicCategoryOrder(category);

        if (null != category.getCategoryCode() && !"".equals(category.getCategoryCode())) {
            checkCode = medicCategoryService.checkMedicCategoryCode(category);
        }
        try {
            if (checkName) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.NAME_REPEAT, null, null);
            } else if (checkCode) {
                throw sdExceptionFactory.createSdException(ExceptionCodeConstants.CODE_REPEAT, null, null);
            } else if (checkOrder) {
                throw sdExceptionFactory.createSdException("70012", null, null);
            }
            medicCategoryService.updateMedicCategory(category);

            // 记录成功的操作日志
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_CLASS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.updateCategory", new String[]{category.getCategoryId().toString(),
                            category.getCategoryName().toString(), category.getCategoryCode().toString(),
                            category.getCategoryPriority().toString(), category.getCategoryIsHard().toString()}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.CF.DRUG_CLASS,
                    AmiLogConstant.BRANCH_SYSTEM.CF,
                    getMessage("log.medic.tip.updateCategory", new String[]{category.getCategoryId().toString(),
                            category.getCategoryName().toString(), category.getCategoryCode().toString(),
                            category.getCategoryPriority().toString(), category.getCategoryIsHard().toString()}),
                    false);
            throw e;
        }
    }

    /**
     * 查询药品分类
     *
     * @param category
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listDrugsCategory", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_214"})
    public String listMedicCategory(MedicCategory category, JqueryStylePaging jquryStylePaging)
            throws Exception {
        try {
            String[] categoryNames = category.getCategoryNames();
            if (categoryNames != null) {
                for (int i = 0; i < categoryNames.length; ++i) {
                    categoryNames[i] = DefineStringUtil.escapeAllLike(categoryNames[i]);
                }
            }
            category.setCategoryName(DefineStringUtil.escapeAllLike(category.getCategoryName()));
            JqueryStylePagingResults<MedicCategory> listMedicCategory =
                    medicCategoryService.listMedicCategory(category, jquryStylePaging);

            return new Gson().toJson(listMedicCategory);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 查询药品分类display
     *
     * @param category
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/displayDrugsCategory", produces = "application/json")
    @ResponseBody
    //@RequiresPermissions(value = {"PIVAS_BTN_216"})
    public String displayMedicCategory(MedicCategory category)
            throws Exception {
        try {
            MedicCategory categoryInfo = medicCategoryService.displayCategory(category.getCategoryId());

            return new Gson().toJson(categoryInfo);
        } catch (Exception e) {
            throw e;
        }
    }

}
