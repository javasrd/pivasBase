package com.zc.pivas.printlabel.controller;

import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.dao.InpatientAreaDAO;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.printlabel.entity.BottleLabel;
import com.zc.pivas.printlabel.service.PrintLabelService;
import com.zc.pivas.printlabel.service.impl.PrintLabelServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 瓶签下载控制类
 *
 * @author kunkka
 * @version 1.0
 */
@Controller()
@RequestMapping(value = "/print")
public class PrintReciverLabelController extends SdDownloadController {
    /**
     * 初始换病区
     */
    private static List<InpatientAreaBean> inpatientAreaList = new ArrayList<InpatientAreaBean>();

    @Resource
    private PrintLabelService printLabelService;

    @Resource
    private BatchService batchService;

    @Resource
    private InpatientAreaDAO inpatientAreaDAO;

    @Resource
    private MedicCategoryDao medicCategoryDao;

    @RequestMapping("/initReceived")
    public String init(Model model, HttpServletRequest request) {
        List<Batch> batchList = batchService.queryByPaging(null, null);
        model.addAttribute("batchList", batchList);

        InpatientAreaBean bean = new InpatientAreaBean();
        bean.setEnabled("1");

        inpatientAreaList = inpatientAreaDAO.getInpatientAreaList(bean, null);

        String inpatientString = request.getParameter("inpatientString");
        model.addAttribute("inpatientString", inpatientString);
        return "pivas/bottleLabel/printReciverLabel";
    }

    /**
     * 根据条件查询接收单信息
     *
     * @return
     */
    @RequestMapping("/qryReciverLabel")
    @ResponseBody
    public String queryReciverLabel(BottleLabel bottleLabel) {
        String returnStr = null;

        String warCodes = bottleLabel.getWardCode();

        if (!StringUtils.isNotEmpty(warCodes)) {
            if (inpatientAreaList == null || inpatientAreaList.size() == 0) {
                InpatientAreaBean bean = new InpatientAreaBean();
                bean.setEnabled("1");
                inpatientAreaList = inpatientAreaDAO.getInpatientAreaList(bean, null);
            }

            for (InpatientAreaBean bean : inpatientAreaList) {
                warCodes = warCodes + bean.getDeptCode() + ",";
            }

            warCodes = warCodes.substring(0, warCodes.lastIndexOf(","));

            bottleLabel.setWardCode(warCodes);
        }

        try {

            String specialBtach = bottleLabel.getSpecialBtach();
            boolean isDetail = bottleLabel.getIsDetail();

            if (StringUtils.isNotBlank(specialBtach) && !isDetail) {

                returnStr = printLabelService.queryYDReciverListFour(bottleLabel, getCurrentUser(), bottleLabel.getIsPrint());

            } else {

                returnStr = printLabelService.queryYDReciverList(bottleLabel, getCurrentUser(), bottleLabel.getIsPrint());
            }

        } catch (Exception e) {
            return getMessage("report.archEnergyConsCtatistics.noData");
        }

        if (StringUtils.isEmpty(returnStr)) {
            if (bottleLabel.getIsPrint()) {
                return buildFailJsonMsg("report.archEnergyConsCtatistics.noData");
            } else {
                return getMessage("report.archEnergyConsCtatistics.noData");
            }
        }

        if (bottleLabel.getIsPrint()) {
            return buildSuccessJsonMsg("ydReciver.pdf");
        } else {
            return returnStr.toString();
        }

    }

    /**
     * 下载的PDF保存路径，因为客户想直接用浏览器来打开PDF文件，所以将文件保存在根目录中
     *
     * @param currUser
     * @return
     */
    private String downloadPDFSavePath(User currUser) {
        return SdConstant.WEB_ROOT_PATH + File.separator + "printLabelDownLoad" + File.separator
                + currUser.getAccount() + File.separator;
    }

    /**
     * 生成PDF
     *
     * @param bottleLabel
     * @throws Exception
     */
    @RequestMapping("/printLabel")
    @ResponseBody
    public String printLabel(BottleLabel bottleLabel)
            throws Exception {
        return buildSuccessJsonMsg("");
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
