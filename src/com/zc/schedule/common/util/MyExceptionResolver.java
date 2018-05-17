package com.zc.schedule.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.google.gson.Gson;
import com.zc.schedule.common.base.BaseController;
import com.zc.schedule.system.config.entity.ErrLog;

/**
 * 异常解析类
 *
 * @author Justin
 * @version v1.0
 */
public class MyExceptionResolver extends SimpleMappingExceptionResolver {

    private static Logger logger = Logger.getLogger(MyExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object arg2,
                                              Exception ex) {
        logger.debug("进入 异常捕获统一接口......");
        Long userId = 0L;
        HttpSession sessionSchedule = request.getSession();
        if (sessionSchedule != null) {
            userId = (Long) sessionSchedule.getAttribute(SysConstant.sessionName.userId);
        }

        String errParam = null;
        if (request.getParameterMap() != null) {
            errParam = new Gson().toJson(request.getParameterMap(), Map.class);
        }
        ErrLog errLog = new ErrLog(request.getRequestURI(), ex.getCause() == null ? "" : ex.getCause().toString(), ex.getMessage().toString(), stackToString(ex.getStackTrace()), userId, errParam);

        String viewName = determineViewName(ex, request);
        if (viewName != null) {// JSP格式返回
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With")
                    .indexOf("XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, ex, request);
            } else {
                // JSON格式返回
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", SysConstant.RET_ERR);
                map.put("mess", errLog.getErrId() == null ? "" : errLog.getErrId());
                return new ModelAndView(viewName, map);
            }
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", SysConstant.RET_ERR);
            map.put("mess", errLog.getErrId() == null ? "" : errLog.getErrId());
            return new ModelAndView("common/exception", map);
        }
    }

    /**
     * 堆转换为字符串
     *
     * @param stackTraceElement
     * @return String
     */
    public static String stackToString(StackTraceElement[] stackTraceElement) {
        StringBuffer mess = new StringBuffer();
        if (stackTraceElement != null) {
            for (int i = 0; i < stackTraceElement.length; i++) {
                mess.append(stackTraceElement[i].toString() + "\n");
            }
            return mess.toString();
        }
        return null;
    }

}
