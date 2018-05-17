package com.zc.pivas.app.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.common.util.FileUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.pivas.app.bean.UserDataBean;
import com.zc.pivas.common.util.DES3Util;
import com.zc.pivas.printlabel.service.impl.PrintLabelServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * APP请求交互
 *
 * @author kunkka
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/printQRCode")
public class PrintQRCode extends SdDownloadController {

    private static int barcodeWidth = 110;

    private static int barcodeHeight = 35;

    private Logger logger = LoggerFactory.getLogger(PrintQRCode.class);

    /**
     * 二维码
     */
    private static final String BARCODE_TYPE_2D = "2";

    @Resource
    UserService userservice;

    /**
     * 检查APP登录
     *
     * @return 视图
     */
    @RequestMapping(value = "/checkData")
    @ResponseBody
    public String checkData(String userdata) {
        if (null == userdata) {
            return buildFailJsonMsg("printQR.invaliduser");
        }

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<UserDataBean>() {
        }.getType();
        UserDataBean userBean = gson.fromJson(userdata, type);

        if (null == userBean) {
            return buildFailJsonMsg("printQR.invaliduser");
        }

        //组装PDF
        User currUser = new User();
        currUser.setAccount(userBean.User);

        String pwd = "";
        try {
            pwd = DES3Util.decode(userBean.Pwd);
        } catch (Exception e) {
            logger.error("decode failed", e);
            return buildFailJsonMsg("printQR.invaliduser");
        }

        currUser.setPassword(pwd);

        User user = userservice.checkUser(currUser);
        if (null == user) {
            return buildFailJsonMsg("printQR.invaliduser");
        }

        return buildSuccessJsonMsg("");
    }

    /**
     * 专门处理手持APP登录
     *
     * @return 视图
     */
    @RequestMapping(value = "/print")
    @ResponseBody
    public String print(String json) {
        //组装PDF
        User currUser = getCurrentUser();
        if (null == currUser) {
            return buildFailJsonMsg("printQR.invaliduser");
        }

        String savePath = getPdfSaveDirPath(currUser);
        String pdfPath = createBottleLabelPDF(savePath, json, currUser);

        return buildSuccessJsonMsg(pdfPath);
    }

    /**
     * 下载
     *
     * @param fileName
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    @ResponseBody
    public void downloadPdf(String fileName, HttpServletResponse response)
            throws Exception {
        fileName = URLDecoder.decode(fileName, "utf-8");

        String pdfDir = PrintLabelServiceImpl.getPdfSaveDirPath(getCurrentUser().getAccount());

        super.doDownloadFile(new File(pdfDir + fileName), fileName, FileType.PDF_TYPE, false);
    }

    /**
     * 得到保存PDF的目录
     *
     * @param currUser
     * @return
     */
    public static String getPdfSaveDirPath(User currUser) {
        String pdfSaveDir = getBasePath() + "temp" + File.separator + currUser.getAccount() + File.separator;

        //没有文件夹时创建
        FileUtil.mkdirs(pdfSaveDir);

        return pdfSaveDir;
    }

    /**
     * 得到打印标签的主目录路径
     *
     * @return
     */
    private static String getBasePath() {
        return SdConstant.WEB_ROOT_PATH + File.separator + "WEB-INF" + File.separator + "print" + File.separator;
    }

    /**
     * 得到PDF绘制对象,设置字体与图片路径
     *
     * @return
     * @throws Exception
     */
    private ITextRenderer getITextRenderer(String resourcePath) {
        ITextRenderer renderer = new ITextRenderer();
        // 解决中文支持问题  
        ITextFontResolver fontResolver = renderer.getFontResolver();
        try {
            fontResolver.addFont(getBasePath() + "fonts" + File.separator + "simhei.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            logger.error("getITextRenderer failed", e);
            return null;
        }

        String baseUrl = new File(resourcePath).toURI().toString();
        // 解决图片的相对路径问题  
        renderer.getSharedContext().setBaseURL(baseUrl);

        return renderer;
    }

    private static String createBarcode(String content, String savePath, String date) {
        String codeType = Propertiesconfiguration.getStringProperty("print.label.code.type");
        if (DefineStringUtil.isEmpty(codeType)) {
            codeType = "";
        }
        String format = "png";// 图像类型
        String fileName = "zxing" + codeType + "_" + date + "." + format;

        File file = new File(savePath + fileName);
        if (!file.exists()) {
            barcodeWidth = 200; // 图像宽度
            barcodeHeight = 200; // 图像高度

            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);
            hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);

            //BarcodeFormat.QR_CODE 二维码
            BarcodeFormat barcode = BarcodeFormat.QR_CODE;//条形码

            //二维码为60*60
            if (BARCODE_TYPE_2D.equals(codeType)) {
                barcodeWidth = 100;
                barcodeHeight = 100;
                barcode = BarcodeFormat.QR_CODE;
            }

            // 生成矩阵
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = new MultiFormatWriter().encode(content, barcode, barcodeWidth, barcodeHeight, hints);
                bitMatrix = updateBit(bitMatrix, 2);
                // 输出图像
                MatrixToImageWriter.writeToFile(bitMatrix, format, file);
            } catch (WriterException | IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        return fileName;
    }

    private static BitMatrix updateBit(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle(); //获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) { //循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    private File fillTemplate(String templateName, VelocityContext context, String savePath) {
        String outFile = savePath;
        //FileWriter writer = new FileWriter(outFile);
        OutputStreamWriter writer = null;
        try {
            //填充模板
            writer = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, getBasePath() + "velocity" + File.separator);

            ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");
            ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");

            ve.init();
            Template t = ve.getTemplate(templateName);

            //删除旧文件
            FileUtil.deleteFile(outFile);

            t.merge(context, writer);
            writer.flush();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            logger.error("fillTemplate failed", e);
            return null;
        } catch (IOException e) {
            logger.error("fillTemplate failed", e);
            return null;
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("fillTemplate failed", e);
                return null;
            }
        }

        return new File(outFile);
    }

    /**
     * 创建瓶签PDF
     *
     * @param savePath
     * @return
     * @throws Exception
     */
    private String createBottleLabelPDF(String savePath, String json, User currUser) {
        String pdfName = null;
        ITextRenderer renderer = getITextRenderer(savePath);

        FileUtil.deleteFile(savePath, 3);
        VelocityContext context = new VelocityContext();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String str = sdf.format(new Date());

        context.put("barcode", createBarcode(json, savePath, str));
        context.put("mainPageOfLabel", true);
        String userAccount = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            userAccount = jsonObject.optString("User");
        } catch (Exception e2) {
            logger.error("JsonTransform failed", e2);
        }
        //获取用户输入的账号
        if (StringUtils.isNotBlank(userAccount)) {
            User account = userservice.getUserByAccount(userAccount);
            context.put("account", account.getName());
        } else {
            context.put("account", "");
        }
        File filePath = fillTemplate("printQR.html", context, savePath + "printQR.html");

        pdfName = "printQR.pdf";
        //PDF文件保存的目录
        String _pdfDir = downloadPDFSavePath(currUser);
        FileUtil.mkdirs(_pdfDir);

        String pdfPath = _pdfDir + pdfName;
        try {
            FileUtil.deleteFile(pdfPath);
            renderer.setDocument(filePath);
            renderer.layout();
            renderer.createPDF(new FileOutputStream(pdfPath), false);
        } catch (IOException | DocumentException e) {
            logger.error("fillTemplate failed", e);
            return null;
        }

        if (renderer != null) {
            renderer.finishPDF();
        }

        return pdfName;
    }

    private String downloadPDFSavePath(User currUser) {
        return SdConstant.WEB_ROOT_PATH + File.separator + "printQRCode" + File.separator + "download" + File.separator
                + currUser.getAccount() + File.separator;
    }

}