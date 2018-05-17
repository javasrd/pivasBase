package com.zc.pivas.statistics.controller;

import com.zc.base.common.controller.SdBaseController;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.drugDamage.service.DrugDamageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 拆药使用统计
 *
 * @author jagger
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/statistics/drugOpenWorkload")
public class DrugOpenWorkloadController extends SdBaseController {
    @Resource
    private DrugDamageService drugDamageService;

    @RequestMapping(value = "")
    public String init(Model model, HttpServletRequest request) throws Exception {
        // 获取药品标签
        List<User> userList = drugDamageService.getAllUsers();

        model.addAttribute("userList", userList);

        return "pivas/statistics/drugOpenWorkload";
    }

}
