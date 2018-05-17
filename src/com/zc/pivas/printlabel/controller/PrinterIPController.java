package com.zc.pivas.printlabel.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.common.util.DictUtil;
import com.zc.pivas.printlabel.entity.PrinterIPBean;
import com.zc.pivas.printlabel.service.PrinterIPService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 打印机配置控制类
 *
 * @author kunkka
 * @version 1.0
 */
@Controller()
@RequestMapping(value = "/printerIP")
public class PrinterIPController extends SdDownloadController {
    /**
     * 业务异常工厂
     */
    @Resource
    private SdExceptionFactory sdExceptionFactory;

    @Resource
    private PrinterIPService printerIPService;

    /**
     * 初始化页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/init")
    public String init(Model model) throws Exception {
        List<String> printerNames = DictUtil.getPrinterNames();
        model.addAttribute("printer", printerNames);
        return "pivas/bottleLabel/printerIP";
    }

    /**
     * 查询所有的打印机配置
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPrinterList", produces = "application/json")
    @ResponseBody
    public String printerIPList(PrinterIPBean bean, JqueryStylePaging jquryStylePaging)
            throws Exception {
        JqueryStylePagingResults<PrinterIPBean> pagingResults =
                printerIPService.queryPrinterIPList(bean, jquryStylePaging);
        return new Gson().toJson(pagingResults);
    }

    /**
     * 添加打印配置项
     *
     * @param bean 添加数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/addPrinter", produces = "application/json")
    @ResponseBody
    public String addPrinterIP(PrinterIPBean bean)
            throws Exception {
        // 判断名称是否存在
        List<PrinterIPBean> list = printerIPService.checkPrinterIP(bean);
        if (list.size() > 0) {
            return buildFailJsonMsg("IP地址重复");
        } else {
            int addPrinterIP = printerIPService.addPrinterIP(bean);
            return addPrinterIP > 0 ? buildSuccessJsonMsg("配置项添加成功") : buildFailJsonMsg("配置项添加失败");
        }
    }

    /**
     * 修改打印机配置
     *
     * @param bean 修改数据
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/modifyPrinter", produces = "application/json")
    @ResponseBody
    public String updatePrinterIP(PrinterIPBean bean)
            throws Exception {
        // 判断名称是否存在 
        PrinterIPBean printerIPBean = printerIPService.queryPrinterIPById(String.valueOf(bean.getGid()));
        if (printerIPBean == null) {
            return buildFailJsonMsg("该配置项已被删除");
        } else {
            List<PrinterIPBean> printerIP = printerIPService.checkPrinterIP(bean);
            if (printerIP.size() > 0) {
                return buildFailJsonMsg("IP地址重复");
            }
            int isSuccess = printerIPService.updatePrinterIP(bean);
            return isSuccess > 0 ? buildSuccessJsonMsg("配置项修改成功") : buildFailJsonMsg("配置项修改失败");
        }
    }

    /**
     * 删除打印机配置
     *
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/delPrinter", produces = "application/json")
    @ResponseBody
    public String delPrinterIP(String ids)
            throws Exception {
        printerIPService.delPrinterIP(ids.split(","));
        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }

    /**
     * 查询某个打印配置项
     *
     * @param gid 主键id
     * @return 操作结果
     * @throws Exception
     */
    @RequestMapping(value = "/getPrinterIPById", produces = "application/json")
    @ResponseBody
    public String queryMedicLabels(String gid)
            throws Exception {
        PrinterIPBean printerIPBean = printerIPService.queryPrinterIPById(gid);
        return new Gson().toJson(printerIPBean);
    }

}
