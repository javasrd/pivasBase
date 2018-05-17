package com.zc.pivas.barcode.controller;

import com.zc.base.common.controller.SdBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 二维码控制类
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/qrcode")
public class BarcodeCreateController extends SdBaseController {
    @RequestMapping(value = "/CreateQrcode")
    @RequiresPermissions(value = { "PIVAS_MENU_108" })
    public String chargeMain(Model model, HttpServletRequest request) {
	return "sys/barcode/barcodeCreate";
    }
}
