package com.zc.pivas.mainMenu.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.pivas.mainMenu.bean.MainMenuBean;
import com.zc.pivas.mainMenu.service.MainMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/mainMenu")
public class MainMenuController extends SdBaseController {

    @Resource
    private MainMenuService mainMenuService;

    @RequestMapping(value = "/init")
    public String initPatient(Model model, HttpServletRequest request, String menu) throws Exception {
        List<MainMenuBean> menuList = mainMenuService.getMenuList(menu);
        model.addAttribute("menuList", menuList);
        return "pivas/mainMenuTree";
    }

}
