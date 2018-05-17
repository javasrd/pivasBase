package com.zc.pivas.printlabel.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdDownloadController;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.printlabel.entity.PrintLogBean;
import com.zc.pivas.printlabel.service.PrintLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 瓶签打印日志控制类
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller()
@RequestMapping(value = "/printLog")
public class PrintLogController extends SdDownloadController
{
    /**
     * 打印日志
     */
    @Resource
    private PrintLogService printLogService;
    
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/toPrintLog")
    public String init(Model model)
    {
        return "pivas/bottleLabel/printLogList";
    }
    
    /**
     * 查询所有数据
     *
     * @param bean
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/printBottleLabelList", produces = "application/json")
    @ResponseBody
    public String printLabelConList(PrintLogBean bean, JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        JqueryStylePagingResults<PrintLogBean> pagingResults = printLogService.getPrintLogList(bean, jquryStylePaging);
        
        return new Gson().toJson(pagingResults);
    }
}
