package com.zc.pivas.printlabel.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.zc.base.common.constant.SdConstant;
import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.FileUtil;
import com.zc.base.common.util.RegexUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sc.modules.batch.repository.BatchDao;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.web.i18n.MessageHolder;
import com.zc.pivas.docteradvice.entity.BatchBean;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.checktype.bean.CheckTypeBean;
import com.zc.pivas.checktype.service.CheckTypeService;
import com.zc.pivas.common.util.IsChineseOrNot;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.dao.InpatientAreaDAO;
import com.zc.pivas.medicamentscategory.repository.MedicCategoryDao;
import com.zc.pivas.medicamentslabel.repository.MedicLabelDao;
import com.zc.pivas.printlabel.entity.*;
import com.zc.pivas.printlabel.repository.BottleLabelDao;
import com.zc.pivas.printlabel.repository.PrintLoglDao;
import com.zc.pivas.printlabel.repository.PrinterIPDao;
import com.zc.pivas.printlabel.service.PrintLabelService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.pdfbox.PrintPDF;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sun.awt.AppContext;

import javax.annotation.Resource;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 瓶签打印接口实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Service("PrintLabelService")
public class PrintLabelServiceImpl implements PrintLabelService {
    private Logger logger = LoggerFactory.getLogger(PrintLabelServiceImpl.class);

    @Resource
    private BottleLabelDao bottleLabelDao;

    @Resource
    private PrintLoglDao printLoglDao;

    @Resource
    private BatchDao batchDao;

    @Resource
    private InpatientAreaDAO inpatientAreaDAO;

    @Resource
    private MessageHolder messageHolder;

    @Resource
    private PrescriptionMainService ydMainService;

    @Resource
    private DoctorAdviceMainService yzMainService;

    @Resource
    private MedicLabelDao medicLabelDao;

    @Resource
    private MedicCategoryDao MedicCategoryDao;

    @Resource
    private CheckTypeService checkTypeService;

    @Resource
    private PrinterIPDao printerIPDao;

    /**
     * 条形码
     */
    private static final String BARCODE_TYPE_1D = "1";

    /**
     * 二维码
     */
    private static final String BARCODE_TYPE_2D = "2";

    private static final int FIRSTPAGE = 5;

    private static final int SECONDPAGE = 8;

    private static final int THIRDPAGE = 4;

    private static final int DRUGNAMELENGTH = 22;

    private static final int DRUGNAMELENGTHTWO = 28;

    private String getMessage(String code, String defStr) {
        String str = defStr;
        try {
            str = messageHolder.getMessage(code);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return str;
    }

    /**
     * 得到保存PDF的目录
     *
     * @return
     */
    public static String getPdfSaveDirPath(String account) {
        String pdfSaveDir = getBasePath() + "temp" + File.separator + account + File.separator;

        //没有文件夹时创建
        FileUtil.mkdirs(pdfSaveDir);

        return pdfSaveDir;
    }

    /**
     * 二维码 和 瓶签html 文件存放路径
     *
     * @return
     */
    public static String getHtmlSaveDirPath() {
        String pdfSaveDir = getBasePath() + "temp" + File.separator;

        //没有文件夹时创建
        FileUtil.mkdirs(pdfSaveDir);

        return pdfSaveDir;
    }

    /**
     * 对比是否包含皮试项目
     *
     * @param label
     * @param medic
     */
    private void setSkipTest(BottleLabel label, BottleLabel medic) {
        if (DefineStringUtil.isNotEmpty(medic.getDrugName())) {
            Set<String> skinTestSet = new LinkedHashSet<String>();
            /*
            Map<String, String> keywords = new HashMap<String, String>();
            keywords.put("青霉素", "PG");
            keywords.put("头孢替安", "替安");
            keywords.put("头孢曲松", "曲松");
            keywords.put("头孢甲肟", "甲肟");
            
            for (Entry<String, String> word : keywords.entrySet())
            {
                String key = word.getKey();
                String value = word.getValue();
                if (medic.getDrugName().contains(key))
                {
                    skinTestSet.add(value);
                }
            }
            */
            skinTestSet.add("皮试");
            if (!skinTestSet.isEmpty()) {
                if (label.getSkinTestSet() == null) {
                    label.setSkinTestSet(skinTestSet);
                } else {
                    label.getSkinTestSet().addAll(skinTestSet);
                }
            }
        }
    }

    public void createBottleLabel(PrescriptionMain ydMain, String account)
            throws Exception {
        Map<String, Object> printLabelMap = new HashMap<String, Object>();

        BottleLabel bottleLabel = new BottleLabel();
        bottleLabel.setPidsj(ydMain.getPidsj());
        bottleLabel.setYdreorderStatusNull("N");
        List<BottleLabel> list = bottleLabelDao.queryBottleLabel(bottleLabel);

        Map<String, BottleLabel> labelMap = new HashMap<String, BottleLabel>();
        if (DefineCollectionUtil.isNotEmpty(list)) {
            String lang = "zh";
            //把数据按照医嘱组编号与批次进行分类  医嘱:{药单:[]}
            for (BottleLabel medica : list) {
                StringBuffer sb = new StringBuffer();
                sb.append(medica.getParentNo()).append("_").append(medica.getBatchId()).append(medica.getYzType());

                if (RegexUtil.isNumber(medica.getQuantity(), 6, 6)) {
                    medica.setQuantityLtOne(Double.valueOf(medica.getQuantity()) < 1 ? true : false);
                }

                if (null != medica.getDropSpeed()) {
                    medica.setDropSpeed(medica.getDropSpeed().trim());
                }

                //规格只取*号前部分 如0.1克/片*100片/瓶，得到0.1克/片
                if (null != medica.getSpecifications()) {
                    String[] specs = medica.getSpecifications().split("\\*");
                    if (specs.length > 0) {
                        medica.setSpecifications(specs[0]);
                    }
                }

                String key = sb.toString();
                if (labelMap.containsKey(key)) {
                    BottleLabel label = labelMap.get(key);
                    label.getMedicaments().add(medica);

                    // 获取已有的药品分类优先级
                    int categoryPriority = label.getCategoryPriority();

                    if (categoryPriority < medica.getCategoryPriority()) {
                        //药品分类信息
                        Set<String> categoryCode = new LinkedHashSet<String>();
                        categoryCode.add(medica.getCategoryCode());
                        label.setCategoryCodeSet(categoryCode);
                        label.setCategoryPriority(medica.getCategoryPriority());
                    }

                    //皮试项
                    setSkipTest(label, medica);
                } else {
                    BottleLabel label = new BottleLabel();
                    //复制医嘱信息
                    BeanUtils.copyProperties(label, medica);

                    //把药单信息放到医嘱中
                    List<BottleLabel> ls = new ArrayList<BottleLabel>();
                    ls.add(medica);
                    label.setMedicaments(ls);
                    labelMap.put(key, label);
                    //pidsjN.add(medica.getPidsj());

                    //药品分类信息
                    Set<String> categoryCode = new LinkedHashSet<String>();
                    categoryCode.add(label.getCategoryCode());
                    label.setCategoryCodeSet(categoryCode);
                    //皮试项
                    setSkipTest(label, medica);

                    // 拼接年龄
                    if (null != label.getAge()) {
                        label.setAgeString(label.getAge()
                                + (DictFacade.getName("pivas.patient.ageunit",
                                DefineStringUtil.parseNull(label.getAgeUnit()),
                                lang)));
                    }

                    if (null != label.getSex()) {
                        String str = "未知";
                        if (label.getSex() != null && 1 == label.getSex().intValue()) {
                            str = getMessage("user.sex.man", "男");
                        } else if (label.getSex() != null && 0 == label.getSex().intValue()) {
                            str = getMessage("user.sex.woman", "女");
                        }
                        label.setSexString(str);
                    }

                    if (DefineStringUtil.isNotEmpty(label.getAvdp())) {
                        label.setAvdp(label.getAvdp() + "kg");
                    }

                    if (null != label.getYzType()) {
                        String str = "临嘱";
                        if (0 == label.getYzType()) {
                            str = "长嘱";
                        }
                        label.setYzTypeString(str);
                    }

                    if (null != label.getUseDate()) {
                        label.setUseDateString(DateUtil.format(label.getUseDate(), DateUtil.DATE_TIME_FORMAT_DAY));
                    }
                    //某XXX医院定制
                    /*String transfusionS = "";
                    try
                    {
                        if (label.getTransfusion() != null)
                        {
                            Double transfusion = new Double(label.getTransfusion());
                            if (transfusion <= 500)
                            {
                                transfusionS = "S";
                            }
                            else if (transfusion <= 1000)
                            {
                                transfusionS = "M";
                            }
                            else if (transfusion <= 3000)
                            {
                                transfusionS = "L";
                            }
                            else if (transfusion > 3000)
                            {
                                transfusionS = "X";
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    label.setTransfusion(transfusionS);*/
                }

                //高危药标志 1不是 0是
                if (medica.getMedicamentsDanger() == 0) {
                    BottleLabel label = labelMap.get(key);
                    label.setMedicamentsDanger(medica.getMedicamentsDanger());
                }

                // 是否溶媒 0不是溶媒,1是溶媒
                if (null != medica.getMedicamentsMenstruum() && medica.getMedicamentsMenstruum() == 1) {
                    BottleLabel label = labelMap.get(key);
                    label.setMedicamentsMenstruum(medica.getMedicamentsMenstruum());
                }

                // 是否做主药 1不是主药,0主药
                if (null != medica.getMedicamentsIsmaindrug()) {
                    BottleLabel label = labelMap.get(key);
                    label.setMedicamentsIsmaindrug(medica.getMedicamentsIsmaindrug());
                }
            }

            list.clear();
            list.addAll(labelMap.values());

            Comparator<BottleLabel> cmp = new Comparator<BottleLabel>() {

                public int compare(BottleLabel o1, BottleLabel o2) {
                    return Collator.getInstance(Locale.CHINESE).compare(o1.getWardName() + "_" + o1.getBedNo(),
                            o2.getWardName() + "_" + o2.getBedNo());
                }
            };
            Collections.sort(list, cmp);
        }

        printLabelMap = createBottleLabelHtml(list, account, ydMain.getPidsj());

        //修改pq表中对应瓶签数据
        setPQBottle(printLabelMap);
    }

    private Map<String, Object> createBottleLabelHtml(List<BottleLabel> list, String account, String pidsj)
            throws Exception {
        Map<String, Object> printLabelMap = new HashMap<String, Object>();

        String savePath = getHtmlSaveDirPath();
        String firstHtml = null;
        String html = null;
        String menstruumHtml = null;
        try {
            DelFileTaskBean delFileTask = new DelFileTaskBean();
            delFileTask.setFilePath(savePath);
            delFileTask.setDay(3);
            bottleLabelDao.addDelFileTask(delFileTask);

            //删除超过3天的文件
            //            FileUtil.deleteFile(savePath, 3);

            boolean firstPage = true;
            boolean page = true;
            for (int i = 0; i < list.size(); i++) {
                BottleLabel bottle = list.get(i);

                //把药品分成小组,药品过多时需要打印多张
                List<List<BottleLabel>> medicList = new ArrayList<List<BottleLabel>>();

                // 溶媒需要打印到单独的瓶签上
                //List<List<BottleLabel>> menstruumList = new ArrayList<List<BottleLabel>>();
                //                List<BottleLabel> menstruums = null;
                List<BottleLabel> subMedicas = new ArrayList<BottleLabel>();
                medicList.add(subMedicas);
                List<BottleLabel> medicaments = bottle.getMedicaments();
                int drugLength = 0;
                int specLength = 0;
                int totaLength = 0;
                //int rowNum = 0;
                //                int rowNumMenstruum = 0;
                //int row = 0;
                //                int menstruumIndex = 0;
                for (int index = 0; index < medicaments.size(); index++) {
                    BottleLabel medica = medicaments.get(index);
                    String drugNameStr = StrUtil.getObjStr(medica.getDrugName(), "");
                    String specificationStr = "[" + StrUtil.getObjStr(medica.getSpecifications(), "") + "]";
                    drugLength = IsChineseOrNot.getEnLen(drugNameStr);
                    specLength = IsChineseOrNot.getEnLen(specificationStr);
                    totaLength = drugLength + specLength;
                    //长度截取
                    if (totaLength > DRUGNAMELENGTH) {
                        medica.setDrugName(IsChineseOrNot.mySubstring(drugNameStr + specificationStr, DRUGNAMELENGTH));
                    } else {
                        medica.setDrugName(drugNameStr + specificationStr);
                    }
                    if (index == (medicList.size() * SECONDPAGE)) {
                        subMedicas = new ArrayList<BottleLabel>();
                        medicList.add(subMedicas);
                    }
                    subMedicas.add(medica);

                }

                //二维码的内容(瓶签唯一标识+医嘱+患者住院号)
                String content = bottle.getBottleNum();

                //生成二维码,返回二维码的文件名
                String codeType = Propertiesconfiguration.getStringProperty("print.label.code.type");

                VelocityContext context = new VelocityContext();
                if (BARCODE_TYPE_1D.equals(codeType)) {
                    barcodeWidth = 133; // 图像宽度
                    barcodeHeight = 35; // 图像高度
                } else {
                    context.put("barcode", createBarcode(content, savePath, bottle.getBottleNum()));
                }
                context.put("label", bottle);
                //context.put("sysDate", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss"));
                //扫描码类型，条形码、二维码
                context.put("codeType", codeType);
                context.put("hospitalName", Propertiesconfiguration.getStringProperty("hospitalName"));
                context.put("printBag", getMessage("pivas.print.bag", "输液袋"));
                context.put("barcodeWidth", barcodeWidth);
                context.put("barcodeHeight", barcodeHeight);
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");//小写的mm表示的是分钟  
                String useTime = sdf.format(bottle.getUseDate());
                context.put("useTime", useTime);
                context.put("freqCode", bottle.getFreqCode());
                context.put("supplyCode", bottle.getSupplyCode());
                context.put("transfusion", bottle.getTransfusion());
                context.put("batchName", bottle.getBatchName());
                String inhospnoStr = bottle.getInhospno();
                if (inhospnoStr.length() > 7) {
                    inhospnoStr = inhospnoStr.substring(inhospnoStr.length() - 7, inhospnoStr.length());
                }
                context.put("inhospno", inhospnoStr);

                //标签提示 要显示的文字
                String remind = "";
                remind += (bottle.getMedicamentsDanger() == 0 ? "危、" : "");
                if (remind.length() > 0) {
                    remind = remind.substring(0, remind.length() - 1);
                }
                context.put("remind", remind);
                //是否为瓶签的主页，当药品太多时需要打印多个瓶签，子瓶签没有扫描码
                boolean mainPageOfLabel = true;
                if (medicList.size() == 1) {
                    if (medicList.get(0).size() <= FIRSTPAGE) {
                        /*System.out.println("生成第一种标签");*/
                        context.put("list", medicList.get(0));
                        context.put("mainPageOfLabel", mainPageOfLabel);
                        File filePath =
                                fillTemplate("bottleLabel.html", context, savePath + "bottle_label" + bottle.getBottleNum()
                                        + ".html");
                        firstHtml = com.zc.pivas.common.util.FileUtil.readFile(filePath);
                    } else {
                        
                        /* System.out.println("生成第二种标签");*/
                        List<BottleLabel> firstList = new ArrayList<BottleLabel>();
                        firstList = medicList.get(0).subList(0, medicList.get(0).size());
                        context.put("list", firstList);
                        context.put("mainPageOfLabel", mainPageOfLabel);
                        File filePathTwo =
                                fillTemplate("bottleLabelTwo.html",
                                        context,
                                        savePath + "bottle_label" + bottle.getBottleNum() + ".html");
                        firstHtml = com.zc.pivas.common.util.FileUtil.readFile(filePathTwo);
                        
                        /*System.out.println("生成第三种标签");*/
                        List<BottleLabel> thirdList = new ArrayList<BottleLabel>();
                        thirdList = medicList.get(0).subList(medicList.get(0).size(), medicList.get(0).size());
                        context.put("list", thirdList);
                        context.put("mainPageOfLabel", mainPageOfLabel);
                        File filePath =
                                fillTemplate("bottleLabelThree.html",
                                        context,
                                        savePath + "bottle_label" + bottle.getBottleNum() + ".html");
                        html = com.zc.pivas.common.util.FileUtil.readFile(filePath);
                    }
                } else {
                    for (int bb = 0; bb < medicList.size(); bb++) {
                        if (bb == medicList.size() - 1) {
                            if (medicList.get(bb).size() <= THIRDPAGE) {
                                /* System.out.println("生成第三种标签");*/
                                context.put("list", medicList.get(bb));
                                context.put("mainPageOfLabel", mainPageOfLabel);
                                File filePath =
                                        fillTemplate("bottleLabelThree.html",
                                                context,
                                                savePath + "bottle_label" + bottle.getBottleNum() + ".html");
                                if (page) {
                                    html = com.zc.pivas.common.util.FileUtil.readFile(filePath);
                                } else {
                                    html = html + "@@" + com.zc.pivas.common.util.FileUtil.readFile(filePath);
                                }
                            } else {
                                /*System.out.println("生成第二种标签");*/
                                List<BottleLabel> firstList = new ArrayList<BottleLabel>();
                                firstList = medicList.get(bb).subList(0, medicList.get(bb).size());
                                context.put("list", firstList);
                                context.put("mainPageOfLabel", mainPageOfLabel);
                                File filePath =
                                        fillTemplate("bottleLabelTwo.html",
                                                context,
                                                savePath + "bottle_label" + bottle.getBottleNum() + ".html");
                                if (page) {
                                    html = com.zc.pivas.common.util.FileUtil.readFile(filePath);
                                } else {
                                    html = html + "@@" + com.zc.pivas.common.util.FileUtil.readFile(filePath);
                                }
                                
                                /* System.out.println("生成第三种标签");*/
                                List<BottleLabel> thirdList = new ArrayList<BottleLabel>();
                                thirdList =
                                        medicList.get(bb).subList(medicList.get(bb).size(), medicList.get(bb).size());
                                context.put("list", thirdList);
                                context.put("mainPageOfLabel", mainPageOfLabel);
                                File filePathTwo =
                                        fillTemplate("bottleLabelThree.html",
                                                context,
                                                savePath + "bottle_label" + bottle.getBottleNum() + ".html");
                                html = html + "@@" + com.zc.pivas.common.util.FileUtil.readFile(filePathTwo);
                            }
                        } else {
                            /*System.out.println("生成第二种标签");*/
                            context.put("list", medicList.get(bb));
                            context.put("mainPageOfLabel", mainPageOfLabel);
                            File filePath =
                                    fillTemplate("bottleLabelTwo.html",
                                            context,
                                            savePath + "bottle_label" + bottle.getBottleNum() + ".html");
                            if (firstPage) {
                                firstHtml = com.zc.pivas.common.util.FileUtil.readFile(filePath);
                                firstPage = false;
                            } else if (!firstPage && page) {
                                html = com.zc.pivas.common.util.FileUtil.readFile(filePath);
                                page = false;
                            } else if (!firstPage && !page) {
                                html = html + "@@" + com.zc.pivas.common.util.FileUtil.readFile(filePath);
                            }
                        }
                    }
                }
                
                /* for (List<BottleLabel> ml : medicList)
                 {
                     context.put("list", ml);
                     context.put("mainPageOfLabel", mainPageOfLabel);
                     File filePath =fillTemplate("bottleLabel.html", context, savePath+"bottle_label"+bottle.getBottleNum()+".html");
                     
                     if (firstPage)
                     {
                         firstHtml = FileUtil.readFile(filePath);
                         firstPage = false;
                     }
                     else if (!firstPage && page)
                     {
                         html = FileUtil.readFile(filePath);
                         page = false;
                     }
                     else if (!firstPage && !page)
                     {
                         html = html + "@@" + FileUtil.readFile(filePath);
                     }
                 }*/
                
                /* medicList = new ArrayList<List<BottleLabel>>();
                 
                 firstPage = true;
                 if (DefineCollectionUtil.isNotEmpty(menstruumList))
                 {
                     for (List<BottleLabel> bo : menstruumList)
                     {
                         medicList.add(bo);
                     }
                 }
                 
                 for (List<BottleLabel> ml : medicList)
                 {
                     context.put("list", ml);
                     context.put("mainPageOfLabel", false);
                     
                     File filePath =
                         fillTemplate("bottleLabel.html", context, savePath + "bottle_label" + bottle.getBottleNum()
                             + ".html");
                     
                     if (firstPage)
                     {
                         menstruumHtml = FileUtil.readFile(filePath);
                         firstPage = false;
                     }
                     else
                     {
                         menstruumHtml =
                             menstruumHtml + "<br></br>" + FileUtil.readFile(filePath);
                     }
                 }*/
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        printLabelMap.put("firstHtml", firstHtml);
        printLabelMap.put("html", html);
        printLabelMap.put("menstruumHtml", menstruumHtml);
        printLabelMap.put("pidsj", pidsj);
        return printLabelMap;
    }

    /**
     * 下载的PDF保存路径，因为客户想直接用浏览器来打开PDF文件，所以将文件保存在根目录中
     *
     * @param currUser
     * @return
     */
    private String downloadPDFSavePath(User currUser) {
        String path =
                SdConstant.WEB_ROOT_PATH + File.separator + "printLabelDownLoad" + File.separator + currUser.getAccount()
                        + File.separator;

        //没有文件夹时创建
        FileUtil.mkdirs(path);

        return path;
    }

    /**
     * 得到打印标签的主目录路径
     *
     * @return
     */
    private static String getBasePath() {
        return SdConstant.WEB_ROOT_PATH + File.separator + "WEB-INF" + File.separator + "print" + File.separator;
    }

    private static String getQRImagePath() {
        return SdConstant.WEB_ROOT_PATH + File.separator + "static" + File.separator + "sysImage" + File.separator
                + "scans" + File.separator + "qrcode.png";
    }

    @Override
    public String printYDList(BottleLabel bottleLabel, User currUser)
            throws Exception {
        String pdfName = null;
        String html = null;
        List<BottleLabel> list = bottleLabelDao.queryYDList(bottleLabel);
        if (DefineCollectionUtil.isNotEmpty(list)) {
            String savePath = getPdfSaveDirPath(currUser.getAccount());
            ITextRenderer renderer = getITextRenderer(savePath);
            try {
                Integer totalQuantity = 0;
                Set<String> batchSet = new HashSet<String>();
                Set<String> categorySet = new HashSet<String>();
                Set<String> wardSet = new HashSet<String>();
                Map<String, BottleLabel> medicamentMap = new LinkedHashMap<String, BottleLabel>();
                List<BottleLabel> medicamentList = new ArrayList<BottleLabel>();

                Map<String, BottleLabel> wardCodeMap = new LinkedHashMap<String, BottleLabel>();
                Map<String, Integer> map = new HashMap<String, Integer>();
                List<BottleLabel> list2 = new ArrayList<BottleLabel>();

                for (BottleLabel b : list) {
                    totalQuantity = totalQuantity + NumberUtils.toInt(b.getQuantity());
                    batchSet.add(b.getBatchName());
                    categorySet.add(b.getCategoryCode());
                    wardSet.add(b.getWardName());

                    if (medicamentMap.get(b.getMedicamentsCode()) == null) {
                        medicamentMap.put(b.getMedicamentsCode(), b);
                    } else {
                        BottleLabel medicamentObj = medicamentMap.get(b.getMedicamentsCode());
                        medicamentObj.setQuantity(String.valueOf(NumberUtils.toInt(b.getQuantity())
                                + NumberUtils.toInt(medicamentObj.getQuantity())));
                    }
                    //5%葡萄糖氯化钠注射液 250毫升/瓶*1瓶/瓶 第一批 粤大冢
                    if (map.get(b.getWardCode() + b.getDrugName() + b.getBatchId() + b.getMedicamentsPlace()) == null) {
                        list2.add(b);
                        map.put(b.getWardCode() + b.getDrugName() + b.getBatchId() + b.getMedicamentsPlace(),
                                list2.size() - 1);
                    } else {
                        BottleLabel b1 =
                                list2.get(map.get(b.getWardCode() + b.getDrugName() + b.getBatchId()
                                        + b.getMedicamentsPlace()));
                        b1.setQuantity("" + (NumberUtils.toInt(b.getQuantity()) + NumberUtils.toInt(b1.getQuantity())));
                    }
                }

                for (Map.Entry<String, BottleLabel> entry : medicamentMap.entrySet()) {
                    medicamentList.add(entry.getValue());
                }

                for (BottleLabel b : list2) {
                    if (wardCodeMap.containsKey(b.getWardCode())) {
                        wardCodeMap.get(b.getWardCode()).getMedicaments().add(b);
                    } else {
                        List<BottleLabel> temp = new ArrayList<BottleLabel>();
                        temp.add(b);
                        b.setMedicaments(temp);
                        wardCodeMap.put(b.getWardCode(), b);
                    }
                }

                String sysDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT_DEFAULT);

                VelocityContext context = new VelocityContext();

                Long batchId = bottleLabel.getBatchId();
                if (batchId != null) {
                    List<Batch> batchList = batchDao.queryBatchAllList();
                    for (Batch batch : batchList) {
                        if (batch.getId().longValue() == batchId.longValue()) {
                            context.put("batchName", batch.getName());
                        }
                    }
                }
                String wardCode = bottleLabel.getWardCode();
                if (wardCode != null) {
                    List<InpatientAreaBean> areaList = inpatientAreaDAO.queryInpatientAreaAllList();
                    for (InpatientAreaBean area : areaList) {
                        if (area.getDeptCode().equals(wardCode)) {
                            context.put("areaName", area.getDeptName());
                        }
                    }
                }
                //batchSet categorySet wardSet medicamentList
                String wardStr = wardSet.toString();
                String batchStr = batchSet.toString();
                String categoryStr = categorySet.toString();

                context.put("sysDate", sysDate);
                context.put("totalQuantity", totalQuantity);
                context.put("wardStr", wardStr.substring(1, wardStr.length() - 1));
                context.put("batchStr", batchStr.substring(1, batchStr.length() - 1));
                context.put("categoryStr", categoryStr.substring(1, categoryStr.length() - 1));
                context.put("medicamentList", medicamentList);

                File file =
                        fillTemplate("bottleLabelTotal.html", context, savePath + "yd_list" + new Date().getTime()
                                + ".html");

                if (!bottleLabel.getIsPrint()) {
                    html = com.zc.pivas.common.util.FileUtil.readFile(file);
                } else {
                    pdfName = "yd_list.pdf";
                    String _pdfDir = downloadPDFSavePath(currUser);
                    FileUtil.mkdirs(_pdfDir);

                    String pdfPath = /*savePath*/_pdfDir + pdfName;
                    FileUtil.deleteFile(pdfPath);

                    renderer.setDocument(file);
                    renderer.layout();
                    renderer.createPDF(new FileOutputStream(pdfPath), false);
                }

            } finally {
                if (renderer != null) {
                    renderer.finishPDF();
                }
            }
        } else {
            return null;
        }
        if (bottleLabel.getIsPrint()) {
            return pdfName;
        } else {
            return html;
        }
    }

    private static int barcodeWidth = 110;

    private static int barcodeHeight = 35;

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @return 返回二维码文件名称
     * @throws Exception
     */
    private static String createBarcode(String content, String savePath, String bottleNum)
            throws Exception {
        String codeType = Propertiesconfiguration.getStringProperty("print.label.code.type");
        if (DefineStringUtil.isEmpty(codeType)) {
            codeType = "";
        }
        String format = "png";// 图像类型
        String fileName = "zxing" + codeType + "_" + bottleNum + "." + format;

        File file = new File(savePath + fileName);
        if (!file.exists()) {
            barcodeWidth = 110; // 图像宽度
            barcodeHeight = 35; // 图像高度

            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 0);

            //BarcodeFormat.QR_CODE 二维码
            BarcodeFormat barcode = BarcodeFormat.CODE_128;//条形码

            //二维码为60*60
            if (BARCODE_TYPE_2D.equals(codeType)) {
                barcodeWidth = 150;
                barcodeHeight = 150;
                barcode = BarcodeFormat.QR_CODE;
            }

            // 生成矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, barcode, barcodeWidth, barcodeHeight, hints);

            //bitMatrix = updateBit(bitMatrix);
            // 输出图像
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);
        }

        return fileName;
    }

    private static BitMatrix updateBit(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle(); //获取二维码图案的属性
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) { //循环，将二维码图案绘制到新的bitMatrix中
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * 填充模板
     *
     * @param templateName 模板名称
     * @param context      VelocityContext
     * @param savePath     模板保存的路径(需要有文件名c:/xx.html)
     * @return 返回文件路径
     */
    private File fillTemplate(String templateName, VelocityContext context, String savePath)
            throws Exception {
        String outFile = savePath;
        //FileWriter writer = new FileWriter(outFile);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
        try {
            //填充模板
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, getBasePath() + "velocity" + File.separator);

            ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");
            ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");

            ve.init();
            Template t = ve.getTemplate(templateName);

            //删除旧文件
            // 获取文件夹路径路径
            String deletePath = savePath.substring(0, savePath.lastIndexOf("\\"));

            DelFileTaskBean delFileTask = new DelFileTaskBean();
            delFileTask.setFilePath(deletePath);
            delFileTask.setDay(3);
            bottleLabelDao.addDelFileTask(delFileTask);
            //            FileUtil.deleteFile(deletePath, 3);

            t.merge(context, writer);
            writer.flush();
        } finally {
            writer.close();
        }

        return new File(outFile);
    }

    /**
     * 得到PDF绘制对象,设置字体与图片路径
     *
     * @return
     * @throws Exception
     */
    private ITextRenderer getITextRenderer(String resourcePath)
            throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        // 解决中文支持问题  
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(getBasePath() + "fonts" + File.separator + "simsun.ttc",
                BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);

        String baseUrl = new File(resourcePath).toURI().toString();
        // 解决图片的相对路径问题  
        renderer.getSharedContext().setBaseURL(baseUrl);

        return renderer;
    }

    @Override
    public String queryYDReciverList(BottleLabel bottleLabel, User currUser, boolean isPrint)
            throws Exception {
        boolean isFirstPage = true;

        boolean isDetail = bottleLabel.getIsDetail();

        String pdfName = null;

        String html = "";

        String reciverLabel = "";

        pdfName = "ydReciver.pdf";
        String _pdfDir = downloadPDFSavePath(currUser);
        FileUtil.mkdirs(_pdfDir);

        String pdfPath = _pdfDir + pdfName;
        FileUtil.deleteFile(pdfPath);

        // 打印时间
        String sysDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT_DEFAULT);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date useDate = bottleLabel.getUseDate();

        String yyrq = sdf.format(useDate);
        if (yyrq != null && StringUtils.isNotBlank(yyrq)) {
            String[] yyrqs = yzMainService.getDateRange(yyrq);
            if (yyrqs != null) {
                bottleLabel.setYyrqStart(yyrqs[0]);
                bottleLabel.setYyrqEnd(yyrqs[1]);
            }
        }

        VelocityContext context = new VelocityContext();
        String savePath = getPdfSaveDirPath(currUser.getAccount());
        ITextRenderer renderer = getITextRenderer(savePath);

        String specialBtach = bottleLabel.getSpecialBtach();
        if (StringUtils.isNotBlank(specialBtach)) {
            String pc = bottleLabelDao.qryBtachCode(specialBtach);

            if (StringUtils.isBlank(pc)) {

                throw new Exception(getMessage("report.archEnergyConsCtatistics.noData", pdfName));

            }

            bottleLabel.setBatchIds(pc);
        }

        if (!"".equals(bottleLabel.getBatchIds()) && null != bottleLabel.getBatchIds()) {
            bottleLabel.setBatchIDList(bottleLabel.getBatchIds().split(","));

            // 判断是否未2#打印
            if (bottleLabel.getBatchIds().equals("2006")) {
                bottleLabel.setIsFourReciver(null);
            }
        }

        String[] wardCodes = bottleLabel.getWardCode().split(",");

        bottleLabel.setWardCodeArray(wardCodes);

        ArrayList<ReceiveBean> ydMainWardCode = bottleLabelDao.qryYdWardCode(bottleLabel);

        int ydLength = ydMainWardCode.size();

        if (ydLength > 0) {

            for (ReceiveBean wardCode : ydMainWardCode) {

                bottleLabel.setWardCode(wardCode.getWardCode());
                ArrayList<BatchBean> ydMainCount = bottleLabelDao.queryYDCount(bottleLabel);
                wardCode.setPcList(ydMainCount);

                if (isDetail) {

                    ArrayList<PrintPqBean> pqCount = bottleLabelDao.queryPQCount(bottleLabel);

                    for (PrintPqBean pq : pqCount) {

                        String[] pqArray = pq.getYdpq().trim().split(",");

                        int total = pqArray.length;

                        pq.setTotal(total);

                        pq.setPqArray(pqArray);
                    }

                    wardCode.setPqList(pqCount);

                }

            }

            context.put("wardCodes", ydMainWardCode);
            context.put("sysDate", sysDate);
            context.put("isDetail", isDetail);

            File file =
                    fillTemplate("reciverBottleLabel.html", context, savePath + "ydReciver" + new Date().getTime()
                            + ".html");

            if (isPrint) {
                if (isFirstPage) {
                    renderer.setDocument(file);
                    renderer.layout();
                    renderer.createPDF(new FileOutputStream(pdfPath), false);
                    isFirstPage = false;
                } else {
                    renderer.setDocument(file);
                    renderer.layout();
                    renderer.writeNextDocument();
                }
            } else {
                html = com.zc.pivas.common.util.FileUtil.readFile(file);

                reciverLabel = reciverLabel + html;
            }

        } else {
            throw new Exception(getMessage("report.archEnergyConsCtatistics.noData", pdfName));
        }

        if (renderer != null) {
            renderer.finishPDF();
        }

        if (isPrint) {
            return pdfName;
        } else {
            return reciverLabel;
        }
    }

    @Override
    public String queryYDReciverListFour(BottleLabel bottleLabel, User currUser, boolean isPrint)
            throws Exception {
        bottleLabel.setIsFourReciver("1");
        boolean isFirstPage = true;

        String pdfName = null;

        String html = "";

        String reciverLabel = "";

        pdfName = "ydReciver.pdf";
        String _pdfDir = downloadPDFSavePath(currUser);
        FileUtil.mkdirs(_pdfDir);

        String pdfPath = _pdfDir + pdfName;
        FileUtil.deleteFile(pdfPath);

        // 打印时间
        String sysDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT_DAY);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date useDate = bottleLabel.getUseDate();

        String yyrq = sdf.format(useDate);
        if (yyrq != null && StringUtils.isNotBlank(yyrq)) {
            String[] yyrqs = yzMainService.getDateRange(yyrq);
            if (yyrqs != null) {
                bottleLabel.setYyrqStart(yyrqs[0]);
                bottleLabel.setYyrqEnd(yyrqs[1]);
            }
        }

        VelocityContext context = new VelocityContext();
        String savePath = getPdfSaveDirPath(currUser.getAccount());
        ITextRenderer renderer = getITextRenderer(savePath);

        String specialBtach = bottleLabel.getSpecialBtach();
        if (StringUtils.isNotBlank(specialBtach)) {
            String pc = bottleLabelDao.qryBtachCode(specialBtach);

            if (StringUtils.isBlank(pc)) {

                throw new Exception(getMessage("report.archEnergyConsCtatistics.noData", pdfName));

            }

            bottleLabel.setBatchIds(pc);
        }

        if (!"".equals(bottleLabel.getBatchIds()) && null != bottleLabel.getBatchIds()) {
            bottleLabel.setBatchIDList(bottleLabel.getBatchIds().split(","));
        }

        String[] wardCodes = bottleLabel.getWardCode().split(",");

        bottleLabel.setWardCodeArray(wardCodes);

        ArrayList<ReceiveBean> ydMainWardCode = bottleLabelDao.qryYdWardCode(bottleLabel);

        int ydLength = ydMainWardCode.size();

        if (ydLength > 0) {

            for (ReceiveBean wardCode : ydMainWardCode) {

                bottleLabel.setWardCode(wardCode.getWardCode());
                ArrayList<BatchBean> ydMainCount = bottleLabelDao.queryYDCount(bottleLabel);
                wardCode.setPcList(ydMainCount);

                context.put("wardName", wardCode.getWardName());
                context.put("sysDate", sysDate);
                context.put("ydMainCount", ydMainCount);

                File file =
                        fillTemplate("reciverBottleLabel4.html", context, savePath + "ydReciver" + new Date().getTime()
                                + ".html");

                if (isPrint) {
                    if (isFirstPage) {
                        renderer.setDocument(file);
                        renderer.layout();
                        renderer.createPDF(new FileOutputStream(pdfPath), false);
                        isFirstPage = false;
                    } else {
                        renderer.setDocument(file);
                        renderer.layout();
                        renderer.writeNextDocument();
                    }
                } else {
                    html = com.zc.pivas.common.util.FileUtil.readFile(file);

                    reciverLabel = reciverLabel + html;
                }

            }

        } else {
            throw new Exception(getMessage("report.archEnergyConsCtatistics.noData", pdfName));
        }

        if (renderer != null) {
            renderer.finishPDF();
        }

        if (isPrint) {
            return pdfName;
        } else {
            return reciverLabel;
        }

    }

    @Override
    public List<BottleLabel> queryYDReciverCount(BottleLabel bottleLabel) {
        return bottleLabelDao.queryYDReciverCount(bottleLabel);
    }

    @Override
    public List<BottleLabel> queryYDReciverDetail(BottleLabel bottleLabel) {
        return bottleLabelDao.queryYDReciverDetail(bottleLabel);
    }

    @Override
    public void setPQBottle(Map<String, Object> bottleInfo) {
        bottleLabelDao.setPQBottle(bottleInfo);
    }

    @Override
    public String printBottleLabel(BottleLabel bottleLabel, User currUser)
            throws Exception {
        //删除超过3天的文件
        String path = getPdfSaveDirPath(currUser.getAccount());
        //        FileUtil.deleteFile(path, 3);

        DelFileTaskBean delFileTask = new DelFileTaskBean();
        delFileTask.setFilePath(path);
        delFileTask.setDay(3);
        bottleLabelDao.addDelFileTask(delFileTask);

        String pdfName = "bottleLabel" + new Date().getTime() + ".pdf";

        //PDF文件保存的目录
        String _pdfDir = downloadPDFSavePath(currUser);
        //        FileUtil.deleteFile(_pdfDir, 3);

        delFileTask = new DelFileTaskBean();
        delFileTask.setFilePath(_pdfDir);
        delFileTask.setDay(3);
        bottleLabelDao.addDelFileTask(delFileTask);

        String pdfPath = _pdfDir + pdfName;
        ITextRenderer renderer = getITextRenderer(path);
        List<BottleLabel> list = bottleLabelDao.queryBottleLabelList(bottleLabel);
        List<String> menstruumDrugList = new ArrayList<String>();
        boolean isFirstPage = true;

        String menstruumHtml = "";
        if (DefineCollectionUtil.isNotEmpty(list)) {
            for (BottleLabel label : list) {
                if (bottleLabel.getIsPrint()) {
                    if (isFirstPage) {
                        FileUtil.mkdirs(_pdfDir);

                        //                        FileUtil.deleteFile(pdfPath);

                        delFileTask = new DelFileTaskBean();
                        delFileTask.setFilePath(pdfPath);
                        bottleLabelDao.addDelFileTask(delFileTask);

                        // 设置单个药单的第一页
                        setLabelHtml(renderer, label, pdfPath, isFirstPage, false, "");

                        isFirstPage = false;

                    } else {
                        // 设置单个药单的第一页
                        setLabelHtml(renderer, label, pdfPath, isFirstPage, false, "");
                    }
                }
                if (null != label.getMenstruumHtml() && !"".equals(label.getMenstruumHtml())) {
                    menstruumHtml =
                            label.getMenstruumHtml().replaceAll("SYSDATE", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss"));
                    ;
                    if (4 == label.getDybz()) {
                        menstruumHtml =
                                menstruumHtml.replaceAll("<label style=\"display:none\" >补</label>", "<label>补</label>");
                    }
                    menstruumDrugList.add(menstruumHtml);
                }
            }

            if (bottleLabel.getIsPrint() && menstruumDrugList.size() > 0) {
                for (String str : menstruumDrugList) {
                    if (str != null && !str.equals("")) {

                        renderer.setDocumentFromString(str);
                        renderer.layout();
                        renderer.writeNextDocument();
                    }
                }
            }
            if (renderer != null) {
                renderer.finishPDF();
            }

            if (bottleLabel.getIsHistoryPrint() == null || !"1".equals(bottleLabel.getIsHistoryPrint())) {
                ydMainService.updatePrintTimeByPidsjN(bottleLabel);

                List<String> bottLabN = ydMainService.qryChangeBedPidsjN(bottleLabel);
                if (bottLabN != null && bottLabN.size() > 0) {
                    Map<String, Object> updateMap = null;
                    for (String pidsj : bottLabN) {
                        updateMap = new HashMap<String, Object>();
                        updateMap.put("pidsj", pidsj);
                        bottleLabelDao.updateBedByBottNum(updateMap);
                    }
                }

                for (BottleLabel label : list) {
                    bottleLabelDao.updateYDBottleNum(label);
                }
            }
            return pdfName;
        }

        return null;
    }

    @Override
    public List<Map<String, String>> qryBottleLabel(BottleLabel bottleLabel, JqueryStylePaging jquryStylePaging)
            throws Exception {
        List<Map<String, String>> htmlMapList = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        List<BottleLabel> list = bottleLabelDao.queryBottleLabelList(bottleLabel);
        List<String> htmlList = new ArrayList<String>();
        List<String> menstruumDrugList = new ArrayList<String>();

        String mainHtml = "";

        String mainDrugHtml = "";

        String menstruumHtml = "";
        if (DefineCollectionUtil.isNotEmpty(list)) {
            for (BottleLabel label : list) {
                if (null != label.getMainHtml() && !"".equals(label.getMainHtml())) {
                    mainHtml = label.getMainHtml().replaceAll("SYSDATE", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss"));
                    if (4 == label.getDybz()) {
                        mainHtml = mainHtml.replaceAll("<label style=\"display:none\" >补</label>", "<label>补</label>");
                    }
                    htmlList.add(mainHtml);
                }
                if (null != label.getMainDrugHtml() && !"".equals(label.getMainDrugHtml())) {
                    mainDrugHtml =
                            label.getMainDrugHtml().replaceAll("SYSDATE", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss"));
                    if (4 == label.getDybz()) {
                        mainDrugHtml =
                                mainDrugHtml.replaceAll("<label style=\"display:none\" >补</label>", "<label>补</label>");
                    }
                    htmlList.add(mainDrugHtml);
                }
                if (null != label.getMenstruumHtml() && !"".equals(label.getMenstruumHtml())) {
                    menstruumHtml =
                            label.getMenstruumHtml().replaceAll("SYSDATE", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss"));
                    if (4 == label.getDybz()) {
                        menstruumHtml =
                                menstruumHtml.replaceAll("<label style=\"display:none\" >补</label>", "<label>补</label>");
                    }
                    menstruumDrugList.add(menstruumHtml);
                }
            }

            for (String str : htmlList) {
                map = new HashMap<String, String>();
                map.put("html", str);
                htmlMapList.add(map);
            }
            for (String str : menstruumDrugList) {
                map = new HashMap<String, String>();
                map.put("html", str);
                htmlMapList.add(map);
            }
        }
        return htmlMapList;
    }

    /**
     * 校验html
     */
    private boolean checkHtml(BottleLabel label) {
        ITextRenderer renderer = new ITextRenderer();

        String mainDrugHtml = label.getMainDrugHtml();
        String mainHtml = label.getMainHtml();

        // 第一张瓶签
        try {
            renderer.setDocumentFromString(mainHtml);
        } catch (Exception e) {
            return false;
        }

        if (StrUtil.isNotNull(mainDrugHtml)) {
            for (String str : mainDrugHtml.split("@@")) {
                try {
                    renderer.setDocumentFromString(str);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return true;
    }

    private BottleLabel resetLabel(BottleLabel label)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pidsj", label.getPidsj());

        List<PrescriptionMain> ydMainList = ydMainService.qryListBeanByMap(map, new JqueryStylePaging());

        if (DefineCollectionUtil.isNotEmpty(ydMainList)) {
            createBottleLabel(ydMainList.get(0), ydMainList.get(0).getSfyscode());
        }

        List<BottleLabel> list = bottleLabelDao.queryBottleLabelList(label);

        if (list != null) {
            label = list.get(0);
        }
        return label;
    }

    @Override
    public List<PrintLabelBean> selectBottleLabel(PrintLabelBean printLabelBean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        List<PrintLabelBean> list = bottleLabelDao.queryShowBottleLabelList(printLabelBean);
        return list;
    }

    @Override
    public List<PrintLabelBean> selectBottleLabelHistory(PrintLabelBean printLabelBean,
                                                         JqueryStylePaging jquryStylePaging)
            throws Exception {
        List<PrintLabelBean> list = bottleLabelDao.queryShowBottleLabelHistoryList(printLabelBean);
        String mainHtml = "";
        if (DefineCollectionUtil.isNotEmpty(list)) {
            for (PrintLabelBean label : list) {
                if (null != label.getMainHtml() && !"".equals(label.getMainHtml())) {
                    mainHtml = label.getMainHtml().replaceAll("SYSDATE", DateUtil.sysdate("yyyy-MM-dd HH:mm:ss"));
                    mainHtml = mainHtml.replaceAll("<label style=\"display:none\" >补</label>", "<label>补</label>");
                    label.setMainHtml(mainHtml);
                }
            }
        }
        return list;
    }

    /**
     * // 设置单个药单的第一页
     *
     * @param renderer
     * @param pdfPath
     * @throws Exception
     */
    private void setLabelHtml(ITextRenderer renderer, BottleLabel label, String pdfPath, boolean isFirstPage,
                              boolean isPrintStastics, String putDrugName)
            throws Exception {
        // 校验瓶签内容是否匹配html格式
        boolean isHtml = checkHtml(label);

        if (!isHtml) {
            label = resetLabel(label);
        }

        String mainDrugHtml = label.getMainDrugHtml();
        //        String mainHtml = label.getMainHtml().replaceAll("SYSDATE", DateUtil.sysdate("yy-MM-dd HH:mm:ss"));
        String mainHtml = label.getMainHtml();
        if (putDrugName != null) {
            mainHtml = mainHtml.replaceAll("id=\"putdrug\">", "id=\"putdrug\">" + putDrugName);
        }
        if (label.getPrintPageNumber() != null && label.getPrintPageNumber().length() > 0) {
            mainHtml =
                    mainHtml.replaceAll("id=\"pageIndex\">",
                            ("id=\"pageIndex\">" + label.getPrintPageIndex() + "-" + label.getPrintPageNumber() + "/" + label.getPrintTotalCount()));
        }

        if ("1".equals(label.getIsHistoryPrint())) {
            mainHtml = mainHtml.replaceAll("style=\"display:none\">补", ">补");
        }

        renderer.setDocumentFromString(mainHtml);
        renderer.layout();

        if (isFirstPage) {
            renderer.createPDF(new FileOutputStream(pdfPath), false);
        } else {
            renderer.writeNextDocument();
        }
        /*  if (!isPrintStastics)
          {*/
        PdfWriter writer = renderer.getWriter();
        PdfContentByte cb = writer.getDirectContent();
        String codeType = Propertiesconfiguration.getStringProperty("print.label.code.type");
        Image image = null;
        if (BARCODE_TYPE_1D.equals(codeType)) {
            Barcode128 code128 = new Barcode128();
            code128.setCode(label.getBottleNum());
            code128.setX(20f);//线的宽度
            code128.setBarHeight(600f);//线的高度
            code128.setSize(200f);
            code128.setBaseline(180f);
            image = code128.createImageWithBarcode(cb, null, null);
        } else {
            String myPath =
                    isPrintStastics ? getQRImagePath() : getHtmlSaveDirPath() + "zxing2_" + label.getBottleNum() + ".png";
            //String myPath = getPdfSaveDirPath(newPath) + "zxing2_" + "2017072060878" + ".png";
            try {
                image = Image.getInstance(myPath);
            } catch (Exception e) {
                createBarcode(label.getBottleNum(), getHtmlSaveDirPath(), label.getBottleNum());
                image = Image.getInstance(myPath);
            }
            image.scaleAbsolute(1100, 1100);
            image.setAbsolutePosition(1100, 1100);
            cb.addImage(image);
        }
        ITextFSImage img = new ITextFSImage(image);
        ;
        if (!isPrintStastics) {
            renderer.getOutputDevice().drawImage(img, 2163, 623);//设置位置 
        } else {
            renderer.getOutputDevice().drawImage(img, -2213, -633);//设置位置
        }

        if (null != mainDrugHtml && !"".equals(mainDrugHtml)) {
            if ("1".equals(label.getIsHistoryPrint())) {
                mainDrugHtml = mainDrugHtml.replaceAll("style=\"display:none\">补", ">补");
            }
            if (putDrugName != null) {
                mainDrugHtml = mainDrugHtml.replaceAll("id=\"putdrug\">", "id=\"putdrug\">" + putDrugName);
            }
            String[] mainDrugHtmlArr = mainDrugHtml.split("@@");
            for (int i = 0; i < mainDrugHtmlArr.length; i++) {
                mainDrugHtmlArr[i] =
                        mainDrugHtmlArr[i].replaceAll("id=\"pageIndex\">", ("id=\"pageIndex\">" + label.getPrintPageIndex()
                                + "-" + label.getPrintPageNumber() + "/" + label.getPrintTotalCount()));
                renderer.setDocumentFromString(mainDrugHtmlArr[i]);
                renderer.layout();
                renderer.writeNextDocument();
                ITextFSImage imga = new ITextFSImage(image);
                renderer.getOutputDevice().drawImage(imga, -2213, -633);//设置位置
            }
        }
        /*   }*/
    }

    /**
     * 打印瓶签
     */
    @Override
    public String printBottleLabelTWO(PrintLabelBean printLabelBean, User currUser)
            throws Exception {
        //删除超过3天的文件
        String path = getPdfSaveDirPath(currUser.getAccount());
        DelFileTaskBean delFileTask = new DelFileTaskBean();
        delFileTask.setDay(3);
        delFileTask.setFilePath(path);
        bottleLabelDao.addDelFileTask(delFileTask);

        String pdfName = "bottleLabel" + new Date().getTime() + ".pdf";

        //PDF文件保存的目录
        String _pdfDir = downloadPDFSavePath(currUser);
        delFileTask = new DelFileTaskBean();
        delFileTask.setDay(3);
        delFileTask.setFilePath(_pdfDir);
        bottleLabelDao.addDelFileTask(delFileTask);

        String pdfPath = _pdfDir + pdfName;

        ITextRenderer renderer = getITextRenderer(path);
        List<PrintLabelBean> list = new ArrayList<PrintLabelBean>();

        //查看批次是否为空批
        String batchId = printLabelBean.getBatchIds();
        Batch batch = null;
        if (StringUtils.isNotBlank(batchId) && !"0".equals(batchId) && !"1".equals(batchId)) {
            batch = batchDao.selectByPrimaryKey(Long.valueOf(batchId));
        }

        List<String> bottleNumList = printLabelBean.getBottleNumList();
        try {

            Integer pidsjIndex = bottleLabelDao.getPidsjIndex();
            Map<String, Object> pidsjMap = null;

            for (String pidsjStr : bottleNumList) {
                pidsjMap = new HashMap<String, Object>();
                pidsjMap.put("gid", pidsjIndex);
                pidsjMap.put("pidsj", pidsjStr);
                bottleLabelDao.addPidsjToTmp(pidsjMap);
            }

            printLabelBean.setPidsjIndex(pidsjIndex);

            if (printLabelBean.getIsPack() != null && printLabelBean.getIsPack().equals("0")
                    || (batch != null && batch.getIsEmptyBatch() == 0) || "1".equals(batchId)) {
                list.addAll(bottleLabelDao.queryPrintBottleLabelListKong(printLabelBean));
            } else {
                list.addAll(bottleLabelDao.queryPrintBottleLabelList(printLabelBean));
            }
            //        List<String> menstruumDrugList = new ArrayList<String>();
            boolean isFirstPage = true;
            boolean printEmptyPage = false;
            boolean hasPrintCollection = false;
            /*String menstruumHtml = "";*/
            if (DefineCollectionUtil.isNotEmpty(list)) {
                Map<String, List<BottleLabel>> statisicInfoMap = null;
                Map<String, BottleLabel> menstrumMap = null;
                int printHistoryGid = bottleLabelDao.getPrintHistoryGid();
                printHistoryGid = printHistoryGid % 10000;

                PrintHistoryBean printHistoryBean = null;
                FileUtil.mkdirs(_pdfDir);
                FileUtil.deleteFile(pdfPath);

                for (int i = 0; i < list.size(); i++) {
                    //如果是通过历史打印  那就不用添加到打印历史表
                    if (null == printLabelBean.getPrintIndex() || "".equals(printLabelBean.getPrintIndex())) {
                        printHistoryBean = new PrintHistoryBean();
                        printHistoryBean.setGid(printHistoryGid);
                        printHistoryBean.setPidsj(list.get(i).getPidsj());
                        printHistoryBean.setPageIndex(i + 1);
                        printHistoryBean.setPrintInfo(list.get(i).getMainHtml());
                        bottleLabelDao.addPrintInfoToHistory(printHistoryBean);
                        list.get(i).setPrintTotalCount(String.valueOf(list.size()));
                        list.get(i).setPrintPageIndex(String.valueOf(printHistoryGid));
                        list.get(i).setPrintPageNumber(String.valueOf(i + 1));
                        if (null != printLabelBean.getPrintAgain() && printLabelBean.getPrintAgain().length() > 0) {
                            list.get(i).setIsHistoryPrint("1");
                        }
                    } else {
                        list.get(i).setIsHistoryPrint("1");
                    }

                    if (printLabelBean.getIsPrintCollection()) {
                        if ((printLabelBean.getIsPack() != null && printLabelBean.getIsPack().equals("0"))
                                || (batch != null && batch.getIsEmptyBatch() == 0) || "1".equals(batchId)) {
                            if (menstrumMap == null) {
                                menstrumMap = rebuildPrintMenstrumInfo(getPrintStatisicList(printLabelBean));
                            }
                            if (i > 0) {
                                if (list.get(i).getMenCount() == 1 && list.get(i - 1).getMenCount() == 1
                                        && !list.get(i).getMenstCode().equals(list.get(i - 1).getMenstCode())) {
                                    PrintLabelBean bean = new PrintLabelBean();
                                    if (printEmptyPage) {
                                        bean.setMainHtml(createEmptyLabel(currUser));
                                        setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                    }

                                    BottleLabel printMenstrBean = menstrumMap.get(list.get(i).getMenstCode());
                                    List<BottleLabel> printMenstrList = new ArrayList<BottleLabel>();
                                    printMenstrList.add(printMenstrBean);

                                    if (StringUtils.isBlank(printLabelBean.getPrintConfigName())) {
                                        if (StringUtils.isNotBlank(printLabelBean.getBatchIds())) {
                                            if ("1".equals(printLabelBean.getBatchIds())) {
                                                printLabelBean.setPrintConfigName("#");
                                            } else {
                                                printLabelBean.setPrintConfigName(batch.getName());
                                            }
                                        }
                                    }
                                    String html =
                                            createPrintMenstrumHtml(printMenstrList,
                                                    printLabelBean.getPrintConfigName(),
                                                    false,
                                                    currUser);

                                    bean.setMainHtml(html);
                                    setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");

                                } else if ((list.get(i).getMenCount() != 1 && list.get(i - 1).getMenCount() == 1)) {
                                    PrintLabelBean bean = new PrintLabelBean();
                                    if (StringUtils.isBlank(printLabelBean.getPrintConfigName())) {
                                        if (StringUtils.isNotBlank(printLabelBean.getBatchIds())) {
                                            if ("1".equals(printLabelBean.getBatchIds())) {
                                                printLabelBean.setPrintConfigName("#");
                                            } else {
                                                printLabelBean.setPrintConfigName(batch.getName());
                                            }
                                        }
                                    }
                                    bean.setMainHtml(rebulidMenListHtml(printLabelBean, currUser));
                                    setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");

                                    if (printEmptyPage) {
                                        bean.setMainHtml(createEmptyLabel(currUser));
                                        setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                    }
                                    hasPrintCollection = true;
                                }
                            } else {
                                if (list.get(i).getMenCount() == 1) {
                                    PrintLabelBean bean = new PrintLabelBean();
                                    if (printEmptyPage) {
                                        bean.setMainHtml(createEmptyLabel(currUser));
                                        setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                    }

                                    BottleLabel printMenstrBean = menstrumMap.get(list.get(i).getMenstCode());
                                    List<BottleLabel> printMenstrList = new ArrayList<BottleLabel>();
                                    printMenstrList.add(printMenstrBean);
                                    if (StringUtils.isBlank(printLabelBean.getPrintConfigName())) {
                                        if (StringUtils.isNotBlank(printLabelBean.getBatchIds())) {
                                            if ("1".equals(printLabelBean.getBatchIds())) {
                                                printLabelBean.setPrintConfigName("#");
                                            } else {
                                                printLabelBean.setPrintConfigName(batch.getName());
                                            }
                                        }
                                    }
                                    String html =
                                            createPrintMenstrumHtml(printMenstrList,
                                                    printLabelBean.getPrintConfigName(),
                                                    false,
                                                    currUser);
                                    bean.setMainHtml(html);
                                    setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");

                                    isFirstPage = false;
                                }
                            }
                        } else {
                            if (statisicInfoMap == null) {
                                statisicInfoMap = rebuildPrintStatisicInfo(getPrintStatisicList(printLabelBean));
                            }
                            if (i > 0) {
                                if (!list.get(i).getDeptAliasName().equals(list.get(i - 1).getDeptAliasName())) {
                                    PrintLabelBean bean = new PrintLabelBean();
                                    if (printEmptyPage) {
                                        bean.setMainHtml(createEmptyLabel(currUser));
                                        setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                    }

                                    List<BottleLabel> printStatisicList =
                                            statisicInfoMap.get(list.get(i).getDeptAliasName());
                                    Map<String, List<BottleLabel>> printStatisicListMap =
                                            new HashMap<String, List<BottleLabel>>();
                                    printStatisicListMap.put(list.get(i).getDeptAliasName(), printStatisicList);
                                    String html =
                                            createPrintStatisicHtml(printStatisicListMap,
                                                    false,
                                                    currUser,
                                                    true,
                                                    printLabelBean.getIsPack());
                                    bean.setMainHtml(html);
                                    setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                }
                            } else {
                                PrintLabelBean bean = new PrintLabelBean();
                                if (printEmptyPage) {
                                    bean.setMainHtml(createEmptyLabel(currUser));
                                    setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                }

                                List<BottleLabel> printStatisicList =
                                        statisicInfoMap.get(list.get(i).getDeptAliasName());
                                Map<String, List<BottleLabel>> printStatisicListMap =
                                        new HashMap<String, List<BottleLabel>>();
                                printStatisicListMap.put(list.get(i).getDeptAliasName(), printStatisicList);
                                String html =
                                        createPrintStatisicHtml(printStatisicListMap,
                                                false,
                                                currUser,
                                                true,
                                                printLabelBean.getIsPack());
                                bean.setMainHtml(html);
                                setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                                isFirstPage = false;
                            }
                        }
                        printEmptyPage = true;
                    }

                    setLabelHtml(renderer,
                            list.get(i),
                            pdfPath,
                            isFirstPage,
                            false,
                            printLabelBean.getPutDrugPersonName());

                    if (!hasPrintCollection
                            && printLabelBean.getIsPrintCollection()
                            && (i == list.size() - 1)
                            && (
                            (printLabelBean.getIsPack() != null && "0".equals(printLabelBean.getIsPack())) ||
                                    (batch != null && batch.getIsEmptyBatch() == 0) || "1".equals(batchId))) {
                        if (menstrumMap == null) {
                            menstrumMap = rebuildPrintMenstrumInfo(getPrintStatisicList(printLabelBean));
                        }
                        PrintLabelBean bean = new PrintLabelBean();
                        if (StringUtils.isBlank(printLabelBean.getPrintConfigName())) {
                            if (StringUtils.isNotBlank(printLabelBean.getBatchIds())) {
                                if ("1".equals(printLabelBean.getBatchIds())) {
                                    printLabelBean.setPrintConfigName("#");
                                } else {
                                    printLabelBean.setPrintConfigName(batch.getName());
                                }
                            }
                        }

                        bean.setMainHtml(rebulidMenListHtml(printLabelBean, currUser));
                        setLabelHtml(renderer, bean, pdfPath, isFirstPage, true, "");
                    }

                    isFirstPage = false;
                }

                if (renderer != null) {
                    renderer.finishPDF();
                }
                if (printLabelBean.getPidsjIndex() != null) {
                    bottleLabelDao.deleteAllPidsj(printLabelBean.getPidsjIndex());
                }
                return pdfName;
            }
            return null;
        } catch (Exception e) {
            if (printLabelBean.getPidsjIndex() != null) {
                bottleLabelDao.deleteAllPidsj(printLabelBean.getPidsjIndex());
            }
            throw e;
        }
    }

    private String rebulidMenListHtml(PrintLabelBean printLabelBean, User currUser)
            throws Exception {
        List<BottleLabel> printMenstrList = new ArrayList<BottleLabel>();
        printLabelBean.setStatisticTwo("statisticTwo");
        List<BottleLabel> printStatisicList = getPrintStatisicList(printLabelBean);
        for (BottleLabel bottleLabel : printStatisicList) {
            int drugLength = 0;
            int specLength = 0;
            int totaLength = 0;
            drugLength = IsChineseOrNot.getEnLen(StrUtil.getObjStr(bottleLabel.getDrugAliasName(), ""));
            specLength = IsChineseOrNot.getEnLen("[" + StrUtil.getObjStr(bottleLabel.getSpecifications(), "") + "]");
            totaLength = drugLength + specLength;
            //长度截取
            if (totaLength > DRUGNAMELENGTHTWO) {
                bottleLabel.setDrugAliasName(IsChineseOrNot.mySubstring(StrUtil.getObjStr(bottleLabel.getDrugAliasName(),
                        ""),
                        DRUGNAMELENGTHTWO - specLength));
            }

            //货位号截取
            String shelfNo = bottleLabel.getShelfNo();

            if (shelfNo != null && shelfNo.length() > 0) {
                bottleLabel.setShelfNo(shelfNo.substring(0, 1));
            }

            printMenstrList.add(bottleLabel);
        }

        Map<String, List<BottleLabel>> printMenstrMap = new HashMap<String, List<BottleLabel>>();
        printMenstrMap.put(printLabelBean.getPrintConfigName(), printMenstrList);
        String html = createPrintStatisicHtml(printMenstrMap, false, currUser, false, printLabelBean.getIsPack());
        return html;
    }

    @Override
    public String printStatisicInfo(PrintLabelBean printLabelBean, User currUser)
            throws Exception {
        List<String> bottleNumList = printLabelBean.getBottleNumList();
        try {

            Integer pidsjIndex = bottleLabelDao.getPidsjIndex();
            Map<String, Object> pidsjMap = null;

            if (bottleNumList != null) {
                for (String pidsjStr : bottleNumList) {
                    pidsjMap = new HashMap<String, Object>();
                    pidsjMap.put("gid", pidsjIndex);
                    pidsjMap.put("pidsj", pidsjStr);
                    bottleLabelDao.addPidsjToTmp(pidsjMap);
                }
            }

            printLabelBean.setPidsjIndex(pidsjIndex);

            List<BottleLabel> list = getPrintStatisicList(printLabelBean);

            if (DefineCollectionUtil.isNotEmpty(list)) {
                if (printLabelBean.getIsPack() != null && printLabelBean.getIsPack().equals("0")) {
                    Map<String, BottleLabel> printMenstrumInfo = rebuildPrintMenstrumInfo(list);
                    List<BottleLabel> printMenstrList = new ArrayList<BottleLabel>();
                    for (Map.Entry<String, BottleLabel> entry : printMenstrumInfo.entrySet()) {
                        printMenstrList.add(entry.getValue());
                    }
                    return createPrintMenstrumHtml(printMenstrList,
                            printLabelBean.getPrintConfigName(),
                            printLabelBean.getIsPrint(),
                            currUser);
                } else {
                    Map<String, List<BottleLabel>> rebuildPrintStatisicInfo = rebuildPrintStatisicInfo(list);
                    return createPrintStatisicHtml(rebuildPrintStatisicInfo,
                            printLabelBean.getIsPrint(),
                            currUser,
                            true,
                            printLabelBean.getIsPack());
                }
            }
            return null;
        } catch (Exception e) {
            if (printLabelBean.getPidsjIndex() != null) {
                bottleLabelDao.deleteAllPidsj(printLabelBean.getPidsjIndex());
            }
            throw e;
        }

    }

    public List<BottleLabel> getPrintStatisicList(PrintLabelBean printLabelBean)
            throws Exception {
        List<BottleLabel> list = new ArrayList<BottleLabel>();
        //查看批次是否为空批
        String batchId = printLabelBean.getBatchIds();
        Batch batch = null;
        if (StringUtils.isNotBlank(batchId) && !"0".equals(batchId) && !"1".equals(batchId)) {
            batch = batchDao.selectByPrimaryKey(Long.valueOf(batchId));
        }
        if (CollectionUtils.isNotEmpty(printLabelBean.getBottleNumList())) {
            if (printLabelBean.getIsPack() != null && printLabelBean.getIsPack().equals("0")
                    || (batch != null && batch.getIsEmptyBatch() == 0) || "1".equals(batchId)) {
                list.addAll(bottleLabelDao.printStatisicInfo(printLabelBean));
            } else {
                list.addAll(bottleLabelDao.printStatisicInfoKong(printLabelBean));
            }

        } else {
            if (printLabelBean.getIsPack() != null && printLabelBean.getIsPack().equals("0")) {
                list.addAll(bottleLabelDao.printStatisicInfo(printLabelBean));
            } else {
                list.addAll(bottleLabelDao.printStatisicInfoKong(printLabelBean));
            }
        }

        return list;
    }

    public String createPrintStatisicHtml(Map<String, List<BottleLabel>> inBottleLabelListMap, boolean isPrint,
                                          User currUser, boolean showFoot, String isPack)
            throws Exception {
        String pdfName = "";
        String html = "";

        String savePath = getPdfSaveDirPath(currUser.getAccount());
        ITextRenderer renderer = getITextRenderer(savePath);
        try {
            String sysDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT_DEFAULT);
            VelocityContext context = new VelocityContext();

            boolean isPDFCreated = false;
            for (Map.Entry<String, List<BottleLabel>> entry : inBottleLabelListMap.entrySet()) {
                List<BottleLabel> medicListOne = entry.getValue();

                context.put("sysDate", sysDate);
                context.put("wardName", entry.getKey());
                context.put("medicamentList", medicListOne);
                context.put("footIsShow", showFoot);
                context.put("isPack", isPack);
                File file = fillTemplate("bottleLabelTotal.html", context, savePath + "yd_list.html");

                if (isPrint) {
                    pdfName = "yd_list.pdf";
                    String _pdfDir = downloadPDFSavePath(currUser);
                    FileUtil.mkdirs(_pdfDir);

                    String pdfPath = _pdfDir + pdfName;
                    FileUtil.deleteFile(pdfPath);
                    renderer.setDocument(file);
                    renderer.layout();

                    if (!isPDFCreated) {
                        renderer.createPDF(new FileOutputStream(pdfPath), false);
                        isPDFCreated = true;
                    } else {
                        renderer.writeNextDocument();
                    }
                } else {
                    html += com.zc.pivas.common.util.FileUtil.readFile(file);
                }
            }
        } finally {
            if (renderer != null) {
                renderer.finishPDF();
            }
        }

        if (isPrint) {
            return pdfName.length() == 0 ? null : pdfName;
        } else {
            return html.length() == 0 ? null : html;
        }
    }

    public String createPrintMenstrumHtml(List<BottleLabel> bottleLabelList, String conditionName, boolean isPrint,
                                          User currUser)
            throws Exception {
        String pdfName = "";
        String html = "";

        String savePath = getPdfSaveDirPath(currUser.getAccount());
        ITextRenderer renderer = getITextRenderer(savePath);
        try {
            String sysDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT_DEFAULT);
            VelocityContext context = new VelocityContext();

            boolean isPDFCreated = false;
            for (int i = 0; i < bottleLabelList.size(); i++) {
                context.put("sysDate", sysDate);
                context.put("conditionName", conditionName);
                context.put("drugAliasName", bottleLabelList.get(i).getDrugAliasName());
                context.put("specifications", bottleLabelList.get(i).getSpecifications());
                context.put("quantity", bottleLabelList.get(i).getQuantity() + bottleLabelList.get(i).getPackingUnit());
                File file = fillTemplate("bottleLabelTotalTwo.html", context, savePath + "menstrum.html");

                if (isPrint) {
                    pdfName = "menstrum.pdf";
                    String _pdfDir = downloadPDFSavePath(currUser);
                    FileUtil.mkdirs(_pdfDir);

                    String pdfPath = _pdfDir + pdfName;
                    FileUtil.deleteFile(pdfPath);
                    renderer.setDocument(file);
                    renderer.layout();

                    if (!isPDFCreated) {
                        renderer.createPDF(new FileOutputStream(pdfPath), false);
                        isPDFCreated = true;
                    } else {
                        renderer.writeNextDocument();
                    }
                } else {
                    html += com.zc.pivas.common.util.FileUtil.readFile(file);
                }
            }
        } finally {
            if (renderer != null) {
                renderer.finishPDF();
            }
        }

        if (isPrint) {
            return pdfName.length() == 0 ? null : pdfName;
        } else {
            return html.length() == 0 ? null : html;
        }
    }

    public String createEmptyLabel(User currUser)
            throws Exception {
        String html = "";
        String savePath = getPdfSaveDirPath(currUser.getAccount());
        VelocityContext context = new VelocityContext();
        File emptyFile = new File(savePath + "label_empty.html");
        if (emptyFile.exists()) {
            html += com.zc.pivas.common.util.FileUtil.readFile(emptyFile);
        } else {
            File file = fillTemplate("bottleLabelTotalEmpty.html", context, savePath + "label_empty.html");
            html += com.zc.pivas.common.util.FileUtil.readFile(file);
        }
        return html;
    }

    public Map<String, List<BottleLabel>> rebuildPrintStatisicInfo(List<BottleLabel> inBottleLabelList) {

        Map<String, List<BottleLabel>> medicamentMap = new LinkedHashMap<String, List<BottleLabel>>();

        int drugLength = 0;
        int specLength = 0;
        int totaLength = 0;

        //讲药单 安装病区进行分组
        for (BottleLabel b : inBottleLabelList) {
            drugLength = 0;
            specLength = 0;
            totaLength = 0;
            drugLength = IsChineseOrNot.getEnLen(StrUtil.getObjStr(b.getDrugAliasName(), ""));
            specLength = IsChineseOrNot.getEnLen("[" + StrUtil.getObjStr(b.getSpecifications(), "") + "]");
            totaLength = drugLength + specLength;
            //长度截取
            if (totaLength > DRUGNAMELENGTHTWO) {
                b.setDrugAliasName(IsChineseOrNot.mySubstring(StrUtil.getObjStr(b.getDrugAliasName(), ""),
                        DRUGNAMELENGTHTWO - specLength));
            }

            //货位号截取
            String shelfNo = b.getShelfNo();
            if (shelfNo != null && shelfNo.length() > 0) {
                b.setShelfNo(shelfNo.substring(0, 1));
            }
            if (medicamentMap.get(b.getDeptAliasName()) == null) {
                List<BottleLabel> bottleLabelList = new ArrayList<BottleLabel>();
                bottleLabelList.add(b);
                medicamentMap.put(b.getDeptAliasName(), bottleLabelList);
            } else {
                List<BottleLabel> bottleLabelList = medicamentMap.get(b.getDeptAliasName());
                bottleLabelList.add(b);
            }
        }

        return medicamentMap;
    }

    public Map<String, BottleLabel> rebuildPrintMenstrumInfo(List<BottleLabel> inBottleLabelList) {

        Map<String, BottleLabel> menstrumMap = new LinkedHashMap<String, BottleLabel>();
        //讲药单 安装病区进行分组
        for (BottleLabel b : inBottleLabelList) {
            if (menstrumMap.get(b.getMedicamentsCode()) == null) {
                menstrumMap.put(b.getMedicamentsCode(), b);
            }
        }

        return menstrumMap;
    }

    public void delPrintFile() {
        List<DelFileTaskBean> list = bottleLabelDao.queryDelFileTask();

        if (DefineCollectionUtil.isNotEmpty(list)) {
            for (DelFileTaskBean bean : list) {
                try {
                    if (bean.getDay() > 0) {
                        FileUtil.deleteFile(bean.getFilePath(), bean.getDay());
                    } else {
                        FileUtil.deleteFile(bean.getFilePath());
                    }
                } catch (Exception e) {
                    logger.error("filePath:" + bean.getFilePath() + " delete failed!");
                }
            }
        }
    }

    @Override
    public List<String> printMedicTotSingCount(Map<String, String> map) {
        return bottleLabelDao.printMedicTotSingCount(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changePrintMark(final PrintLabelBean printLabelBean, final User currUser, String ipAddress)
            throws Exception {
        String result = null;

        String pdfPath = downloadPDFSavePath(currUser) + printLabelBean.getBottleNumPDFPath();
        String printerName = printerIPDao.getAllPrinterByName(ipAddress);
        if (StringUtils.isBlank(printerName)) {
            logger.error("ip地址没有配");
            return "error_2";
        }
        //用于判断是否是早上首次启动
        AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        int retryCount = 0;

        while (!getPrintServerByName(services, printerName)) {
            if (retryCount == 10) {
                logger.error("ip对应的打印机配置不正确");
                return "error_2";
            }
            Thread.sleep(2000);
            AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);
            services = PrintServiceLookup.lookupPrintServices(null, null);
            retryCount++;
        }

        try {
            PrintPDF.main(new String[]{"-silentPrint",//静默打印
                    "-printerName", printerName,//指定打印机名
                    "-orientation", "auto",//打印方向，三种可选
                    pdfPath//打印PDF文档的路径
            });
        } catch (Exception e) {
            logger.error("最终打印问题    " + e.getMessage());
            result = "error_2";
        }
        if (printLabelBean.getPrintAgain() == null && !"error_2".equals(result)) {
            result = realChangePrintMark(printLabelBean, currUser);
        } else {
            result = "success";
        }

        return result;
    }

    /**
     * 根据IP 获取打印机
     *
     * @param services
     * @param printerName
     * @return
     */
    public boolean getPrintServerByName(PrintService[] services, String printerName) {
        boolean printerExist = false;

        for (int i = 0; i < services.length; i++) {
            printerExist = (services[i].getName()).equals(printerName);
            if (printerExist)
                break;
        }

        return printerExist;
    }

    /***
     *
     * 改变打印状态 并记录打印记录
     * @param printLabelBean
     * @param currUser
     * @return
     * @throws Exception
     */
    private String realChangePrintMark(PrintLabelBean printLabelBean, User currUser)
            throws Exception {
        //药品标签
        if (printLabelBean.getMedicamentsLabelNo() != null) {
            printLabelBean.setMedicamentsLabelNo(printLabelBean.getMedicamentsLabelNo().trim());
        }

        //药品分类
        if (printLabelBean.getCategoryId() != null) {
            printLabelBean.setCategoryId(printLabelBean.getCategoryId().trim());
        }

        List<PrintLabelBean> list = new ArrayList<PrintLabelBean>();
        List<String> bottleNumList = printLabelBean.getBottleNumList();
        List<String> tempList = new ArrayList<String>();

        Integer pidsjIndex = bottleLabelDao.getPidsjIndex();
        printLabelBean.setPidsjIndex(pidsjIndex);
        Map<String, Object> pidsjMap = null;
        for (String pidsjStr : bottleNumList) {
            pidsjMap = new HashMap<String, Object>();
            pidsjMap.put("gid", pidsjIndex);
            pidsjMap.put("pidsj", pidsjStr);
            bottleLabelDao.addPidsjToTmp(pidsjMap);
        }

        if (printLabelBean.getIsPack() != null && printLabelBean.getIsPack().equals("0")) {
            list.addAll(bottleLabelDao.queryPrintBottleLabelListKong(printLabelBean));
        } else {
            list.addAll(bottleLabelDao.queryPrintBottleLabelList(printLabelBean));
        }

        bottleLabelDao.deleteAllPidsj(printLabelBean.getPidsjIndex());

        if (DefineCollectionUtil.isNotEmpty(list)) {
            //更新打印时间
            int printHistoryGid = bottleLabelDao.getCurrHistoryGid();
            for (int a = 0; a < bottleNumList.size(); a++) {
                if (a != 0 && a % 1000 == 0) {
                    printLabelBean.setBottleNumList(tempList);
                    printLabelBean.setPrintName(currUser.getAccount());
                    bottleLabelDao.updatePrintTime(printLabelBean);
                    tempList.clear();
                }
                tempList.add(bottleNumList.get(a));
            }

            printLabelBean.setBottleNumList(tempList);
            printLabelBean.setPrintName(currUser.getAccount());
            bottleLabelDao.updatePrintTime(printLabelBean);
            printLabelBean.setBottleNumList(bottleNumList);

            List<String> bottLabN = new ArrayList<String>();
            tempList.clear();

            //改变床号
            for (int a = 0; a < bottleNumList.size(); a++) {
                if (a != 0 && a % 1000 == 0) {
                    printLabelBean.setBottleNumList(tempList);
                    bottLabN.addAll(ydMainService.qryChangeBedPidsjN(printLabelBean));
                    tempList.clear();
                }
                tempList.add(bottleNumList.get(a));
            }
            printLabelBean.setBottleNumList(tempList);
            bottLabN.addAll(ydMainService.qryChangeBedPidsjN(printLabelBean));
            printLabelBean.setBottleNumList(bottleNumList);

            if (bottLabN != null && bottLabN.size() > 0) {
                Map<String, Object> updateMap = null;
                for (String pidsj : bottLabN) {
                    updateMap = new HashMap<String, Object>();
                    updateMap.put("pidsj", pidsj);
                    bottleLabelDao.updateBedByBottNum(updateMap);
                }
            }

            //添加打印日志
            PrintLogBean printLogBean = new PrintLogBean();
            printLogBean.setOpreName(currUser.getAccount());

            String printCondition = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            printCondition += "用药时间：" + format.format(printLabelBean.getUseDate()) + "@@";

            if (printLabelBean.getMedicamentsLabelNo() != null
                    && printLabelBean.getMedicamentsLabelNo().trim().length() != 0) {
                String medicLabelNoStr = printLabelBean.getMedicamentsLabelNo();
                String[] medicLabelArray = medicLabelNoStr.split(",");
                String medicLabelByCode = bottleLabelDao.getMedicLabelByCode(medicLabelArray);
                printCondition += "药品标签：" + medicLabelByCode + "@@";
            }

            if (printLabelBean.getBatchIds() != null && printLabelBean.getBatchIds().trim().length() != 0) {
                String batchIds = printLabelBean.getBatchIds();
                String[] batchIdsArray = batchIds.split(",");
                String batchNameById = bottleLabelDao.getBatchNameById(batchIdsArray);
                printCondition += "批次：" + batchNameById + "@@";
            }

            if (printLabelBean.getCategoryId() != null && printLabelBean.getCategoryId().trim().length() != 0) {
                String categoryIdStr = printLabelBean.getCategoryId();
                String[] categoryArray = categoryIdStr.split(",");
                String categoryName = bottleLabelDao.getCategoryByCode(categoryArray);
                printCondition += "药品分类：" + categoryName + "@@";
            }

            if (printLabelBean.getWardCode() != null && printLabelBean.getWardCode().trim().length() != 0) {
                String wardCodes = printLabelBean.getWardCode();
                String[] wardCodeArray = wardCodes.split(",");
                String wardNameByCode = bottleLabelDao.getWardNameByCode(wardCodeArray);
                printCondition += "病区：" + wardNameByCode + "@@";
            }

            if (printLabelBean.getMedicamentsCountsStr() != null
                    && printLabelBean.getMedicamentsCountsStr().trim().length() != 0) {
                printCondition += "药单/药数：" + printLabelBean.getMedicamentsCountsStr() + "@@";
            }

            if (printLabelBean.getDybz() != null) {
                String dayingType = "";
                if (printLabelBean.getDybz() == 0) {
                    dayingType = "已打印";
                } else {
                    dayingType = "未打印";
                }
                printCondition += "打印状态：" + dayingType + "@@";
            }

            if (printLabelBean.getPutDrugPersonName() != null
                    && printLabelBean.getPutDrugPersonName().trim().length() != 0) {
                printCondition += "摆药人员：" + printLabelBean.getPutDrugPersonName() + "@@";
            }

            if (printLabelBean.getSortType() != null) {
                String sortType = "";
                if (printLabelBean.getSortType() == 0) {
                    sortType = "批次";
                } else if (printLabelBean.getSortType() == 1) {
                    sortType = "病区";
                }
                printCondition += "排序：" + sortType + "@@";
            }

            String drugNameList = printLabelBean.getDrugNameList();
            if (drugNameList != null && drugNameList.trim().length() > 0) {
                drugNameList = drugNameList.substring(0, drugNameList.length() - 1);
                printCondition += "溶媒：" + drugNameList + "@@";
            }

            String isPackStr = printLabelBean.getIsPack();
            if (isPackStr != null && isPackStr.equals("1")) {
                printCondition += "是否打包：是" + "@@";
            } else {
                printCondition += "是否打包：否" + "@@";
            }
            printCondition = printCondition.substring(0, printCondition.length() - 2);
            printLogBean.setPrintCondition(printCondition);
            printLogBean.setPrintIndex(printHistoryGid);
            printLoglDao.addPrintLog(printLogBean);

            // 判断是否有进仓扫描环节
            CheckTypeBean checkType = new CheckTypeBean();
            checkType.setCheckType(0);
            checkType.setIsEffect(0);
            checkType = checkTypeService.getCheckType(checkType);

            boolean hasScanIn = true;
            if (null == checkType) {
                hasScanIn = false;
            }
            for (BottleLabel label : list) {
                if (!hasScanIn) {
                    label.setHasScanIn("1");
                }
                bottleLabelDao.updateYDBottleNum(label);
            }
            return "success";
        }
        return "error_1";
    }

    /**
     * 根据条件查询药品统计
     */
    @Override
    public String callMedicStatisticHtml(Map<String, Object> param, User currUser)
            throws Exception {

        String returnStr = null;
        List<BottleLabel> bottleLabelList = new ArrayList<BottleLabel>();
        String[] pidsjArr = (String[]) param.get("pidsjArr");
        if (pidsjArr != null && pidsjArr.length > 0) {
            List<String> tempList = new ArrayList<String>();
            for (int a = 0; a < pidsjArr.length; a++) {
                if (a != 0 && a % 1000 == 0) {
                    param.replace("pidsjArr", tempList.toArray(new String[tempList.size()]));
                    bottleLabelList.addAll(bottleLabelDao.callMedicStatisticList(param));
                    tempList.clear();
                }
                tempList.add(pidsjArr[a]);
            }

            param.replace("pidsjArr", tempList.toArray(new String[tempList.size()]));
            bottleLabelList.addAll(bottleLabelDao.callMedicStatisticList(param));
        } else {
            bottleLabelList = bottleLabelDao.callMedicStatisticList(param);
        }

        if (bottleLabelList != null && bottleLabelList.size() != 0) {
            String pdfName = "";
            String savePath = getPdfSaveDirPath(currUser.getAccount());
            ITextRenderer renderer = getITextRenderer(savePath);
            try {
                String sysDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT_DEFAULT);
                VelocityContext context = new VelocityContext();

                context.put("medicamentList", bottleLabelList);
                context.put("sysDate", sysDate);

                String selectName = StrUtil.getObjStr(param.get("selectName"));
                if (StringUtils.isNotBlank(selectName)) {
                    context.put("selectName", selectName);
                }
                String fileName = "medicStatistic" + System.currentTimeMillis();
                File file = fillTemplate("medicStatistic.html", context, savePath + fileName + ".html");

                pdfName = fileName + ".pdf";
                String _pdfDir = downloadPDFSavePath(currUser);
                FileUtil.mkdirs(_pdfDir);

                String pdfPath = _pdfDir + pdfName;
                FileUtil.deleteFile(pdfPath);
                renderer.setDocument(file);
                renderer.layout();
                renderer.createPDF(new FileOutputStream(pdfPath), false);
                returnStr = com.zc.pivas.common.util.FileUtil.readFile(file);

                returnStr += "!!@@##" + pdfName;
            } finally {
                if (renderer != null) {
                    renderer.finishPDF();
                }
            }

        }
        return returnStr;
    }

}
