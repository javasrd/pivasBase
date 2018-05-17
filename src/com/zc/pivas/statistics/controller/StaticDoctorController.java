package com.zc.pivas.statistics.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.statistics.service.StaticDoctorService;
import com.zc.pivas.statistics.bean.medicalAdvice.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不合理医嘱统计控制器
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics")
public class StaticDoctorController extends SdBaseController {
    @Resource
    private StaticDoctorService staticDoctorService;

    @Resource
    InpatientAreaService inpatientAreaService;

    /**
     * 不合理医嘱统计页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/doctorAdvice")
    @RequiresPermissions(value = {"PIVAS_MENU_571"})
    public String initStaticDoctor(Model model, HttpServletRequest request) {
        //医生数据
        List<StaticDoctorNameBean> doctorNameList = staticDoctorService.queryDoctorNameList();
        model.addAttribute("doctorNameList", doctorNameList);
        //病区数据
        //List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.queryInpatientAreaAllList();
        List<InpatientAreaBean> inpatientAreaList = inpatientAreaService.queryInpatientAreaInAll();
        model.addAttribute("inpatientAreaList", inpatientAreaList);
        return "pivas/statistics/irrationalDoctorAdvice";
    }

    /**
     * 按医生获取柱状图数据
     *
     * @param staticDoctorSearch
     * @return
     */
    @RequestMapping("/queryDoctorAdviceStatusBarData")
    @ResponseBody
    public String queryDoctorAdviceStatusBarData(StaticDoctorSearchBean staticDoctorSearch) {
        StaticDoctorNameStatusBarBean doctorNameStatusBarList = staticDoctorService.queryDoctorNameStatusBar(staticDoctorSearch);
        return new Gson().toJson(doctorNameStatusBarList);
    }

    /**
     * 获取医嘱饼图数据
     *
     * @param staticDoctorSearch
     * @return
     */
    @RequestMapping("/queryDoctorAdvicePieList")
    @ResponseBody
    public String queryDoctorAdvicePieList(StaticDoctorSearchBean staticDoctorSearch) {
        List<StaticDoctorNamePieBean> doctorNamePieList = staticDoctorService.queryDoctorNamePieList(staticDoctorSearch);
        return new Gson().toJson(doctorNamePieList);
    }

    @RequestMapping("/queryDoctorAdviceStatusPieData")
    @ResponseBody
    public String queryDoctorAdviceStatusPieData(StaticDoctorSearchBean staticDoctorSearch) {
        List<StaticDoctorStatusBean> doctorStatusList = staticDoctorService.queryDoctorNameStatusListByID(staticDoctorSearch);
        return new Gson().toJson(doctorStatusList);
    }

    @RequestMapping("/queryDoctorAdviceDeptStatusBar")
    @ResponseBody
    public String queryDoctorAdviceDeptStatusBar(StaticDoctorSearchBean staticDoctorSearch) {
        StaticDoctorDeptStatusBarBean deptStatusBarList = staticDoctorService.queryDeptStatusBar(staticDoctorSearch);
        return new Gson().toJson(deptStatusBarList);

    }

    @RequestMapping("/queryDoctorAdviceDeptPieList")
    @ResponseBody
    public String queryDoctorAdviceDeptPieList(StaticDoctorSearchBean staticDoctorSearch) {
        List<StaticDoctorDeptPieBean> dctrDeptPieList = staticDoctorService.queryDeptPieList(staticDoctorSearch);
        return new Gson().toJson(dctrDeptPieList);

    }

    @RequestMapping("/queryDoctorAdviceDeptStatusPieList")
    @ResponseBody
    public String queryDoctorAdviceDeptStatusPieList(StaticDoctorSearchBean staticDoctorSearch) {
        List<StaticDoctorStatusBean> deptStatusList = staticDoctorService.queryDeptStatusListByName(staticDoctorSearch);
        return new Gson().toJson(deptStatusList);
    }

    /**
     * 导入不合理医嘱点评
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/importDianp")
    @ResponseBody
    public String importDianp(MultipartFile file) throws IOException {
        boolean hasError = false;
        // 导入发生的错误信息
        StringBuffer errorLog = new StringBuffer();
        errorLog.append("导入异常日志如下：\n");

        Map<String, Object> updateMap = new HashMap<String, Object>();

        String fileName = file.getOriginalFilename();
        try {
            boolean oldVersion = true;
            if (fileName.matches("^.+\\.(?i)(xls)$")) {
                oldVersion = true;
            } else if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                oldVersion = false;
            } else {
                return buildSuccessJsonMsg(errorLog.toString());
            }

            Workbook workbook = null;
            if (oldVersion) {
                // 2003版本Excel(.xls)
                workbook = new HSSFWorkbook(file.getInputStream());
            } else {
                // 2007版本Excel或更高版本(.xlsx)
                workbook = new XSSFWorkbook(file.getInputStream());
            }

            if (null != workbook) {
                String operatorLog = "";

                for (int m = 0; m < workbook.getNumberOfSheets(); m++) {
                    Sheet sheet = workbook.getSheetAt(m);

                    int n = 4;
                    Row row = null;
                    if (m != 0) {
                        n = 1;
                    }

                    row = sheet.getRow(n - 1);
                    // pivas_yz_checkresult id
                    String id = "";

                    // 不合理医嘱点评
                    String dianp = "";

                    // 根据导入列表数据判断获取的记录异常数据的模板
                    if (row.getCell(16) != null && "点评".equals(row.getCell(16).toString())) {
                        for (int i = n; i <= sheet.getLastRowNum(); i++) {
                            row = sheet.getRow(i);

                            if (!StringUtils.isEmpty(row.getCell(17).toString())) {
                                // 点评
                                dianp = row.getCell(16).toString();

                                id = row.getCell(17).toString();

                                if (dianp.getBytes().length > 1024) {
                                    operatorLog =
                                            "第" + String.valueOf(i + 1) + "行" + " 点评内容：" + dianp + " 超长";

                                    errorLog.append(operatorLog).append("\n");

                                    hasError = true;
                                } else {
                                    updateMap.put("id", id);
                                    updateMap.put("dianp", dianp);
                                    staticDoctorService.updateYzCheckResult(updateMap);
                                }

                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }

        if (hasError) {
            return buildFailJsonMsg(errorLog.toString());
        } else {
            return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
        }

    }
}
