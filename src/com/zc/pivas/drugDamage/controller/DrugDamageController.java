package com.zc.pivas.drugDamage.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.web.i18n.MessageHolder;
import com.zc.pivas.drugDamage.bean.DrugDamageBean;
import com.zc.pivas.drugDamage.service.DrugDamageService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 药品破损登记
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/dgDmg")
public class DrugDamageController extends SdBaseController {

    private static String DAMAGE_PROBLEM = "pivas.drugdamage.damageproblem";
    private static String DAMAGE_LINK = "pivas.drugdamage.damageLink";

    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    @Autowired
    private MessageHolder messageHolder;

    @Resource
    private DrugDamageService drugDamageService;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    /**
     * 初始化药品破损登记页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/initDrugDamage")
    @RequiresPermissions(value = {"PIVAS_MENU_941"})
    public String initDrugDamage(Model model) {
        model.addAttribute("allUsers", drugDamageService.getAllUsers());
        model.addAttribute("categorys", medicCategoryDao.qryMedicCategory());
        model.addAttribute("damageProblem",drugDamageService.getAlldamageProblemOrLink(DAMAGE_PROBLEM));
        model.addAttribute("damageLink",drugDamageService.getAlldamageProblemOrLink(DAMAGE_LINK));
        
        return "pivas/drugDamage/drugDamageList";
    }

    /**
     * 药品破损登记分页查询
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllDmg", produces = "application/json")
    @ResponseBody
    public String getAllDrugDamage(DrugDamageBean bean, JqueryStylePaging jquryStylePaging) throws Exception {
        try {
            //药品编码
            String drugCode = bean.getDrugCode();
            if (drugCode != null && drugCode.length() > 0) {
                String[] drugCodes = drugCode.split(",");
                bean.setDrugCodes(drugCodes);
            }

            //药品名称
            String drugName = bean.getDrugName();
            if (drugName != null && drugName.length() > 0) {
                String[] drugNames = drugName.split(",");
                bean.setDrugNames(drugNames);
            }

            //药品产地
            String drugPlace = bean.getDrugPlace();
            if (drugPlace != null && drugPlace.length() > 0) {
                String[] drugPlaces = drugPlace.split(",");
                bean.setDrugPlaces(drugPlaces);
            }

            JqueryStylePagingResults<DrugDamageBean> listMedicLabel = drugDamageService.getAllDrugDamage(bean, jquryStylePaging);
            return new Gson().toJson(listMedicLabel);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 新增药品破损登记
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addDgDmg", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_948"})
    public String addDrugDamage(DrugDamageBean bean) throws Exception {
        try {
            drugDamageService.addDrugDamage(bean);
            addOperLog(AmiLogConstant.MODULE_BRANCH.OC.DRUG_DAMAGE,
                    AmiLogConstant.BRANCH_SYSTEM.OC,
                    getMessage("log.drugDamage.tip.addDrugDamage", new String[]{bean.getDrugCode().toString(), bean.getDrugName().toString()}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            addOperLog(AmiLogConstant.MODULE_BRANCH.OC.DRUG_DAMAGE,
                    AmiLogConstant.BRANCH_SYSTEM.OC,
                    getMessage("log.drugDamage.tip.addDrugDamage", new String[]{bean.getDrugCode().toString(), bean.getDrugName().toString()}),
                    false);
            throw e;
        }
    }

    /**
     * 修改药品破损登记
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updDgDmg", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_946"})
    public String updateDrugDamage(DrugDamageBean bean)
            throws Exception {
        try {
            drugDamageService.updateDrugDamage(bean);

            // 记录成功的操作日志
            addOperLog(AmiLogConstant.MODULE_BRANCH.OC.DRUG_DAMAGE,
                    AmiLogConstant.BRANCH_SYSTEM.OC,
                    getMessage("log.drugDamage.tip.updateDrugDamage", new String[]{bean.getDrugCode().toString(),
                            bean.getDrugName().toString(),
                            bean.getDamageProblem().toString(),
                            bean.getDamageLink().toString(),
                            bean.getQuantity().toString()}),
                    true);
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.OC.DRUG_DAMAGE,
                    AmiLogConstant.BRANCH_SYSTEM.OC,
                    getMessage("log.drugDamage.tip.updateDrugDamage", new String[]{bean.getDrugCode().toString(),
                            bean.getDrugName().toString(),
                            bean.getDamageProblem().toString(),
                            bean.getDamageLink().toString(),
                            bean.getQuantity().toString()}),
                    false);
            throw e;
        }
    }

    /**
     * 删除药品破损登记信息
     *
     * @param gids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delDgDmg", produces = "application/json")
    @ResponseBody
    @RequiresPermissions(value = {"PIVAS_BTN_947"})
    public String deleteDrugDamage(String gids)
            throws Exception {
        try {
            if (gids != null && !"".equals(gids)) {
                String[] gidArray = gids.split(",");
                for (int i = 0; i < gidArray.length; i++) {
                    drugDamageService.deleteDrugDamage(Long.parseLong(gidArray[i]));
                }
                // 记录成功的操作日志
                addOperLog(AmiLogConstant.MODULE_BRANCH.OC.DRUG_DAMAGE,
                        AmiLogConstant.BRANCH_SYSTEM.OC,
                        getMessage("log.drugDamage.tip.deleteDrugDamage", new String[]{gids}),
                        true);
            }
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        } catch (Exception e) {
            // 异常继续抛出,不需要做处理
            addOperLog(AmiLogConstant.MODULE_BRANCH.OC.DRUG_DAMAGE,
                    AmiLogConstant.BRANCH_SYSTEM.OC,
                    getMessage("log.drugDamage.tip.deleteDrugDamage", new String[]{gids}),
                    false);
            throw e;
        }
    }

    /**
     * 药品标签详情
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dispDgDmg", produces = "application/json")
    @ResponseBody
    public String displayMedicLabel(DrugDamageBean bean)
            throws Exception {
        try {
            DrugDamageBean damageInfo = drugDamageService.displayDrugDamage(bean.getGid());
            return new Gson().toJson(damageInfo);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 查询所有药品
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/qryDgs")
    @ResponseBody
    public String queryDrugs(Medicaments bean) throws Exception {
        String[] columns = new String[]{"medicamentsCode", "medicamentsName", "medicamentsSpecifications", "medicamentsPlace"};

        JqueryStylePagingResults<Medicaments> results = new JqueryStylePagingResults<Medicaments>(columns);

        List<Medicaments> medicamentsList = drugDamageService.queryMedicByCondition(bean);
        results.setDataRows(null);

        Integer total = null;
        if (null != medicamentsList) {
            results.setDataRows(medicamentsList);
            total = medicamentsList.size();
        }

        results.setTotal(total);

        return new Gson().toJson(results);
    }
}
