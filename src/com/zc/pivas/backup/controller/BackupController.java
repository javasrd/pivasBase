package com.zc.pivas.backup.controller;

import com.zc.base.common.controller.SdBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 服务器节点控制类
 *
 * @author  cacabin
 * @version  1.0
 */
@Controller
public class BackupController extends SdBaseController
{     
    @RequestMapping(value = "/backup")
    public String backUp(Model model, HttpServletRequest request)
    {
        return "backup";
    }
}