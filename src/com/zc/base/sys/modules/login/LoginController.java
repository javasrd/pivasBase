package com.zc.base.sys.modules.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.sys.common.constant.AmiLogConstant;
import com.zc.base.sys.common.constant.SysConstant;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.language.service.LanguageService;
import com.zc.base.sys.modules.login.entity.SubSysInfo;
import com.zc.base.sys.modules.sysconfig.entity.SystemConfig;
import com.zc.base.sys.modules.sysconfig.facade.SystemConfigFacade;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.sys.modules.user.entity.Privilege;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.entity.UserLogin;
import com.zc.base.sys.modules.user.service.PriService;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.base.web.Servlets;


@Controller
public class LoginController extends SdBaseController {
    private static final int DEFAULT_WIDTH = 90;
    private static final int DEFAULT_HEIGHT = 23;
    private static final int DEFAULT_LINESIZE = 40;
    private static final int DEFAULT_STRINGNUM = 5;
    private String httpPort;
    private String httpsPort;
    private Random random = new Random();

    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int width = 90;

    private int height = 23;

    private int lineSize = 40;

    private int stringNum = 5;

    private boolean enableCaptcha;

    private LanguageService languageService;

    @Resource
    private SdExceptionFactory sdExceptionFactory;


    @Resource
    private PriService priService;


    @Resource
    private UserService userService;


    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        Servlets.setNoCacheHeader(response);
        //系统配置
        Map<String, SystemConfig> map = SystemConfigFacade.getMap();
        model.addAttribute("sysConMap", map);

        //if ("https".equals(request.getScheme())) {
            model.addAttribute("enableCaptcha", Boolean.valueOf(this.enableCaptcha));
            try {
                //支持的语言
                model.addAttribute("languages", this.languageService.getAllLanguages());
            } catch (Exception e) {
                request.setAttribute("shiroLoginFailure", e);
            }

            Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
            cookie.setSecure(false);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);

            if (SecurityUtils.getSubject().isAuthenticated()) {
                return "redirect: /main";
            }
            return "login";
        //}

        /*if ("http".equals(request.getScheme())) {
            String httpsLoginUrl = "https://" + request.getServerName() + ":" + this.httpsPort + request.getContextPath() + "/login";
            return "redirect:" + httpsLoginUrl;
        }

        return "/";*/
    }


    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    public String fail(@RequestParam("username") String userName, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect: /main";
        }
        Servlets.setNoCacheHeader(response);
        model.addAttribute("username", userName);
        model.addAttribute("enableCaptcha", Boolean.valueOf(this.enableCaptcha));
        try {
            model.addAttribute("languages", this.languageService.getAllLanguages());
        } catch (Exception e) {
            request.setAttribute("shiroLoginFailure", e);
        }
        return "login";
    }

    @RequestMapping({"/"})
    public String root() {
        return "redirect:/main";
    }

    @RequestMapping({"/main"})
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
        if ("https".equals(request.getScheme())) {
            User currentUser = getCurrentUser();
            String httpsLoginUrl = null;
            if (SysConstant.ACCOUNT_TYPE.equals(currentUser.getAccountType())) {
                httpsLoginUrl = "http://" + request.getServerName() + ":" + this.httpPort + request.getContextPath() + "/billing/energyInfoQuery/energyInfo";
            } else {
                httpsLoginUrl = "http://" + request.getServerName() + ":" + this.httpPort + request.getContextPath() + "/main";
            }
            return "redirect:" + httpsLoginUrl;
        }
        User currentUser = getCurrentUser();
        String[] expireStatus = this.userService.getPwdExpireStatus(currentUser.getAccount());
        String pwdExpireStatus = expireStatus[0];
        if (pwdExpireStatus.equals("notice_modify_pwd")) {
            request.setAttribute("notice_modify_pwd", "1_" + getMessage("user.PwdModifyNotice", new String[]{expireStatus[1]}));
        } else {
            request.setAttribute("notice_modify_pwd", "0");
        }

        String language = RequestContextUtils.getLocale(request).getLanguage();
        Long userId = null;

        String sysAdmin = Propertiesconfiguration.getStringProperty("account.sysAdmin");

        if (!sysAdmin.equals(currentUser.getAccount())) {
            userId = currentUser.getUserId();
        }

        Long menuId = null;
        if (request.getParameter("menuId") != null) {
            menuId = new Long(request.getParameter("menuId"));
        }
        List<Privilege> priList = null;

        if (userId == null) {
            priList = this.priService.getMenuListBySubSys(null, language);
        } else {
            priList = this.priService.getMenuListByUser(userId, null, language);
        }
        model.addAttribute("priList", priList);

        List<SubSysInfo> subSysList = getSubSysList(userId, language);
        model.addAttribute("subSysList", subSysList);

        if (menuId != null) {
            request.getSession().setAttribute("NAVIGATION_NAME", this.priService.getNavigationByMenuId(menuId, language));
        } else {
            request.getSession().setAttribute("NAVIGATION_NAME", null);
        }

        request.setAttribute("LastLoginInfo", this.userService.getUserLastLogin(currentUser.getUserId()));

        UserLogin userLogin = new UserLogin(currentUser.getUserId(), request.getRemoteAddr(), new Date());
        this.userService.insertUserLogin(userLogin);
        addOperLog("SM_100", AmiLogConstant.BRANCH_SYSTEM.SM, getMessage("log.login", new String[]{currentUser.getAccount()}), true);
        request.setAttribute("minLength", Propertiesconfiguration.getStringProperty("password.minLength"));
        request.setAttribute("maxLength", Propertiesconfiguration.getStringProperty("password.maxLength"));
        request.setAttribute("serverName", request.getServerName());
        request.setAttribute("httpPort", this.httpPort);

        HttpSession sessionPivas = request.getSession();
        sessionPivas.setAttribute("session_userId", currentUser.getUserId());
        sessionPivas.setAttribute("session_userName", currentUser.getAccount());
        ServletContext ContextPivas = sessionPivas.getServletContext();
        ContextPivas.setAttribute("sessionInfo", sessionPivas);
        return "main";
    }


    @RequestMapping(value = {"/reLogin"}, headers = {"X-Requested-With=XMLHttpRequest"})
    @ResponseBody
    public void reLoginAjax(HttpServletRequest request, HttpServletResponse response) {
        throw this.sdExceptionFactory.createSdException("00000", null, null);
    }


    @RequestMapping({"/reLogin"})
    public String reLogin() {
        return "redirect:/login";
    }


    @RequestMapping({"/captchaCode"})
    public void genValidateVode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Servlets.setNoCacheHeader(response);
        HttpSession session = request.getSession();
        if ("https".equals(request.getScheme())) {

            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setSecure(false);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
        BufferedImage image = new BufferedImage(this.width, this.height, 4);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, this.width, this.height);
        g.setFont(new Font("Times New Roman", 0, 18));
        g.setColor(getRandColor(110, 133));

        for (int i = 0; i <= this.lineSize; i++) {
            drowLine(g);
        }

        String randomString = "";
        for (int i = 1; i <= this.stringNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        session.removeAttribute("CAPTCHA_CODE_KEY");
        session.removeAttribute("CAPTCHAE_CODE_GEN_TIME");
        session.setAttribute("CAPTCHA_CODE_KEY", randomString);
        session.setAttribute("CAPTCHAE_CODE_GEN_TIME", new Date());
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(this.random.nextInt(101), this.random.nextInt(111), this.random.nextInt(121)));
        String rand = String.valueOf(getRandomString(this.random.nextInt(this.randString.length())));
        randomString = randomString + rand;
        g.translate(this.random.nextInt(3), this.random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    private void drowLine(Graphics g) {
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        int xl = this.random.nextInt(13);
        int yl = this.random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    public String getRandomString(int num) {
        return String.valueOf(this.randString.charAt(num));
    }

    private Font getFont() {
        return new Font("Fixedsys", 1, 16);
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + this.random.nextInt(bc - fc - 16);
        int g = fc + this.random.nextInt(bc - fc - 14);
        int b = fc + this.random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }


    private List<SubSysInfo> getSubSysList(Long userId, String language) {
        List<SubSysInfo> subSysList = null;
        List<Integer> sysCodeList = this.priService.getSysCodeListByUser(userId);
        if (DefineCollectionUtil.isNotEmpty(sysCodeList)) {
            subSysList = new ArrayList();
            SubSysInfo subSys = null;
            for (Integer sysCode : sysCodeList) {
                String sysName =
                        DictFacade.getName("SYS_LOG_OPER.sub_system", DefineStringUtil.parseNull(sysCode), language);
                if (DefineStringUtil.isNotEmpty(sysName)) {
                    subSys = new SubSysInfo();
                    subSys.setSysCode(sysCode);
                    subSys.setSysName(sysName);
                    subSysList.add(subSys);
                }
            }
        }

        return subSysList;
    }

    public String getHttpPort() {
        return this.httpPort;
    }

    @Value("#{propertiesConfig['webapp.httpPort']}")
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    public String getHttpsPort() {
        return this.httpsPort;
    }

    @Value("#{propertiesConfig['webapp.httpsPort']}")
    public void setHttpsPort(String httpsPort) {
        this.httpsPort = httpsPort;
    }

    public boolean isEnableCaptcha() {
        return this.enableCaptcha;
    }

    @Value("#{propertiesConfig['login.enableCaptcha']}")
    public void setEnableCaptcha(boolean enableCaptcha) {
        this.enableCaptcha = enableCaptcha;
    }

    public LanguageService getLanguageService() {
        return this.languageService;
    }

    @Resource(name = "languageService")
    public void setLanguageService(LanguageService languageService) {
        this.languageService = languageService;
    }

}
