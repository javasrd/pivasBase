package com.zc.base.sys.modules.system.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.base.sys.modules.system.facade.DictFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping({"/sys/dict"})
public class DictController
        extends SdBaseController {
    @RequestMapping(value = {"/getDictName"}, produces = {"application/json"})
    @ResponseBody
    public String getDictName(String category, String code, HttpServletRequest request) {
        String language = RequestContextUtils.getLocale(request).getLanguage();
        String dictName = DictFacade.getName(category, code, language);
        return this.longDateJsonMapper.toJson(dictName);
    }
}
