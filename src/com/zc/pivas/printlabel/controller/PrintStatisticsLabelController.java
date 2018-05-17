package com.zc.pivas.printlabel.controller;

import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.dao.InpatientAreaDAO;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.printlabel.entity.BottleLabel;
import com.zc.pivas.printlabel.service.PrintLabelService;
import com.zc.pivas.printlabel.service.impl.PrintLabelServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.List;

/**
 * 瓶签生成pdf控制类
 *
 * @author kunkka
 * @version 1.0
 */
@Controller()
@RequestMapping(value = "/printStatisticsLabel")
public class PrintStatisticsLabelController extends SdDownloadController {
    @Resource
    private PrintLabelService printLabelService;

    @Resource
    private BatchService batchService;

    @Resource
    private InpatientAreaDAO inpatientAreaDAO;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    @RequestMapping("/init")
    public String init(Integer type, Model model) {
        List<Batch> batchList = batchService.queryByPaging(null, null);
        model.addAttribute("batchList", batchList);

        InpatientAreaBean bean = new InpatientAreaBean();
        bean.setEnabled("1");

        List<InpatientAreaBean> inpatientAreaList = inpatientAreaDAO.getInpatientAreaList(bean, null);

        model.addAttribute("inpatientAreaList", inpatientAreaList);

        return "pivas/bottleLabel/printStatisticLabel";
    }

    /**
     * 生成PDF
     *
     * @param bottleLabel
     * @throws Exception
     */
    @RequestMapping("/print")
    @ResponseBody
    public String printLabel(BottleLabel bottleLabel)
            throws Exception {
        String returnStr = null;
        returnStr = printLabelService.printYDList(bottleLabel, getCurrentUser());

        if (null == returnStr) {
            if (bottleLabel.getIsPrint()) {
                return buildFailJsonMsg("report.archEnergyConsCtatistics.noData");
            } else {
                return getMessage("report.archEnergyConsCtatistics.noData");
            }
        }

        if (bottleLabel.getIsPrint()) {
            return buildSuccessJsonMsg(returnStr);
        } else {
            return returnStr;
        }
    }

    /**
     * 下载
     *
     * @param fileName
     * @param response
     * @throws Exception
     */
    @RequestMapping("/downloadPdf")
    @ResponseBody
    public void downloadPdf(String fileName, HttpServletResponse response)
            throws Exception {
        fileName = URLDecoder.decode(fileName, "utf-8");

        String pdfDir = PrintLabelServiceImpl.getPdfSaveDirPath(getCurrentUser().getAccount());

        super.doDownloadFile(new File(pdfDir + fileName), fileName, FileType.PDF_TYPE, false);
    }
}
