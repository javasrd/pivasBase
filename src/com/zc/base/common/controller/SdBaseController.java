package com.zc.base.common.controller;

import com.google.gson.Gson;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zc.base.common.exception.SdException;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.core.exception.DreambaseException;
import com.zc.base.core.mapper.JsonMapper;
import com.zc.base.sys.modules.log.entity.Log;
import com.zc.base.sys.modules.log.service.LogService;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.base.sys.modules.user.service.PriService;
import com.zc.base.sys.modules.user.service.UserService;
import com.zc.base.web.Servlets;
import com.zc.base.web.common.BaseController;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SdBaseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SdBaseController.class);


    private static String MYSQL_FOREIGN_KEY_MESSAGE = "a foreign key constraint fails";


    @Resource
    private LogService logService;


    @Resource
    private UserService userService;


    @Resource
    private PriService priService;


    protected void addOperLog(String module, Integer system, String content, boolean isSuccess) {
        Log log = handleLog(module, system, content, isSuccess);
        this.logService.log(log);
    }


    protected Log handleLog(String module, Integer system, String content, boolean isSuccess) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Log log = new Log();
        log.setAccountId(user.getUserId());
        if (isSuccess) {
            log.setStatus(Integer.valueOf(1));
        } else {
            log.setStatus(Integer.valueOf(0));
        }
        log.setContent(content);
        log.setSystem(system);
        log.setModule(module);
        log.setTime(new Date());

        return log;
    }


    protected void batchAddOperLog(List<Log> logs) {
        this.logService.batchLog(logs);
    }


    protected void addOperLog(Long userId, Integer module, Integer system, String content, boolean isSuccess) {
        Log log = new Log();
        log.setAccountId(userId);
        if (isSuccess) {
            log.setStatus(Integer.valueOf(1));
        } else {
            log.setStatus(Integer.valueOf(0));
        }
        log.setContent(content);
        log.setSystem(system);
        log.setModule(module.toString());
        log.setTime(new Date());
        this.logService.log(log);
    }


    protected void addOperLog(String account, Integer module, Integer system, String content, boolean isSuccess) {
        User user = this.userService.getUserByAccount(account);
        if (user != null) {
            Log log = new Log();
            log.setAccountId(user.getUserId());
            if (isSuccess) {
                log.setStatus(Integer.valueOf(1));
            } else {
                log.setStatus(Integer.valueOf(0));
            }
            log.setContent(content);
            log.setSystem(system);
            log.setModule(module.toString());
            log.setTime(new Date());
            this.logService.log(log);
        }
    }


    @ExceptionHandler
    @ResponseBody
    public String handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        Throwable exp = e;
        while (exp != null) {

            if ((exp instanceof MySQLIntegrityConstraintViolationException)) {
                String errorMessage = exp.getMessage();
                if (errorMessage.indexOf(MYSQL_FOREIGN_KEY_MESSAGE) > 0) {
                    BeanFactory beanFactory = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
                    SdExceptionFactory sdExceptionFactory = (SdExceptionFactory) beanFactory.getBean("sdExceptionFactory");
                    SdException sdException = sdExceptionFactory.createSdException("00101", null, e);
                    return baseHandle(request, response, sdException);
                }

                if (exp.getCause() == null) {
                    return baseHandle(request, response, e);
                }
            } else {
                exp = exp.getCause();
            }
        }

        return baseHandle(request, response, e);
    }


    private String baseHandle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        if ((e instanceof DreambaseException)) {
            DreambaseException exception = (DreambaseException) e;
            Throwable cause = exception.getCause();


            if (cause != null) {
                logger.error("error code:" + exception.getCode(), e);
            } else {
                logger.error("error code:" + exception.getCode() + " message:" + exception.getMessage());
            }

        } else {
            logger.error(e.getMessage(), e);
        }


        if (Servlets.isAjaxRequest(request)) {

            response.reset();

            response.setContentType("application/json; charset=UTF-8");


            if ((e instanceof DreambaseException)) {
                DreambaseException exception = (DreambaseException) e;
                Map<String, Object> msgMap = new HashMap();
                msgMap.put("success", Boolean.valueOf(false));
                msgMap.put("code", exception.getCode());
                msgMap.put("msg", exception.getMessage());
                msgMap.put("description", exception.getDescription());
                msgMap.put("solution", exception.getSolutionDesc());
                return new Gson().toJson(msgMap);
            }


            Map<String, Object> msgMap = new HashMap();
            msgMap.put("success", Boolean.valueOf(false));
            msgMap.put("code", "default");
            msgMap.put("msg", "系统异常");
            msgMap.put("description", "系统异常，请联系管理员");
            msgMap.put("solution", "请联系管理员");
            return new Gson().toJson(msgMap);
        }


        throw e;
    }


    public String buildSuccessJsonMsg(String msg, Object[] args, JsonMapper jsonMapper) {
        Map<String, Object> message = new HashMap();
        String msgStr = this.messageHolder.getMessage(msg, args, msg);
        message.put("success", Boolean.valueOf(true));
        message.put("msg", msgStr);
        message.put("description", msgStr);
        return jsonMapper.toJson(message);
    }


    public String buildFailJsonMsg(String msg, Object[] args, JsonMapper jsonMapper) {
        Map<String, Object> message = new HashMap();
        String msgStr = this.messageHolder.getMessage(msg, args, msg);
        message.put("success", Boolean.valueOf(false));
        message.put("msg", msgStr);
        message.put("description", msgStr);
        return jsonMapper.toJson(message);
    }


    public User getCurrentUser() {
        return (User) super.getCurrentUser();
    }


    public String getCurrentLanguage() {
        return Servlets.getCurrentRequestLocal().getLanguage();
    }


    public void resetNavigationName(Long menuId) {
        HttpServletRequest request = Servlets.getCurrentRequest();

        String language = RequestContextUtils.getLocale(request).getLanguage();

        request.getSession().setAttribute("NAVIGATION_NAME", this.priService.getNavigationByMenuId(menuId, language));
    }
}
