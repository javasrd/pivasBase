package com.zc.base.common.servlet;

import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;
import com.zc.base.sys.modules.sysconfig.service.SystemConfigService;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.sys.modules.system.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class StartUpServlet extends HttpServlet {
    private static final long serialVersionUID = 3712149552478793573L;
    private static final Logger log = LoggerFactory.getLogger(StartUpServlet.class);


    public void init() throws ServletException {
        try {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            loadDict(wac);
            loadSysConfig(wac);
        } catch (Exception e) {
            log.error("start up servlet error ", e);
            throw new ServletException();
        }
    }

    private void loadDict(WebApplicationContext wac) throws Exception {
        if (!DictFacade.isLoaded()) {

            SysDictService service = (SysDictService) wac.getBean("blaDictService");
            DictFacade.setMap(service.getAllBLADict());
        }
    }

    private void loadSysConfig(WebApplicationContext wac) throws Exception {
        if (!SystemConfigFacade.isLoaded()) {
            SystemConfigService service = (SystemConfigService) wac.getBean("systemConfigService");
            SystemConfigFacade.setMap(service.getInitSystemConfig());
        }
    }
}
