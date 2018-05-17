package com.zc.base.sys.modules.log.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.modules.log.bo.LogBo;
import com.zc.base.sys.modules.log.entity.Log;
import com.zc.base.sys.modules.log.service.LogService;
import com.zc.base.sys.modules.system.facade.DictFacade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping({"/log"})
public class LogController
        extends SdBaseController {
    @Resource
    private LogService logService;

    @RequestMapping({"/main"})
    @RequiresPermissions({"PIVAS_MENU_105"})
    public String mainView() {
        return "sys/log/logMain";
    }


    @RequestMapping(value = {"/qryModBySys"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_105"})
    @ResponseBody
    public String queryModuleBySystem(HttpServletRequest request, Integer subSysCode) {
        String language = RequestContextUtils.getLocale(request).getLanguage();

        String[][] modules = null;

        if (AmiLogConstant.BRANCH_SYSTEM.SM.equals(subSysCode)) {
            modules = DictFacade.getDictByCategory("SYS_LOG_OPER.module.sys", language);
        } else if (AmiLogConstant.BRANCH_SYSTEM.PM.equals(subSysCode)) {
            modules = DictFacade.getDictByCategory("SYS_LOG_OPER.module.pm", language);
        } else if (AmiLogConstant.BRANCH_SYSTEM.CF.equals(subSysCode)) {
            modules = DictFacade.getDictByCategory("SYS_LOG_OPER.module.cf", language);
        } else if (AmiLogConstant.BRANCH_SYSTEM.SC.equals(subSysCode)) {
            modules = DictFacade.getDictByCategory("SYS_LOG_OPER.module.sc", language);
        }
        return new Gson().toJson(modules);
    }


    @RequestMapping(value = {"/selectLog"}, produces = {"application/json"})
    @RequiresPermissions({"PIVAS_MENU_105"})
    @ResponseBody
    public String queryLog(LogBo logBo, JqueryStylePaging paging)
            throws Exception {
        JqueryStylePagingResults<Log> results = this.logService.queryLog(logBo, paging);

        return this.longDateJsonMapper.toJson(results);
    }
}
