package com.zc.base.web.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zc.base.core.exception.DreambaseException;
import com.zc.base.core.mapper.JsonMapper;
import com.zc.base.web.Servlets;
import com.zc.base.web.i18n.MessageHolder;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);


    protected MessageHolder messageHolder;


    protected final JsonMapper defaultJsonMapper = new JsonMapper();


    protected final JsonMapper shortDateJsonMapper = new JsonMapper();


    protected final JsonMapper longDateJsonMapper = new JsonMapper();


    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";


    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public BaseController() {
        this.defaultJsonMapper.getMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        this.defaultJsonMapper.getMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.shortDateJsonMapper.getMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.shortDateJsonMapper.getMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.shortDateJsonMapper.getMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.shortDateJsonMapper.getMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        this.longDateJsonMapper.getMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.longDateJsonMapper.getMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.longDateJsonMapper.getMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.longDateJsonMapper.getMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }


    @ExceptionHandler
    @ResponseBody
    public String handle(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws Exception {
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
                return this.defaultJsonMapper.toJson(msgMap);
            }


            return buildFailJsonMsg(e.getMessage());
        }


        throw e;
    }


    public String getMessage(String code) {
        return this.messageHolder.getMessage(code);
    }


    public String getMessage(String code, Object[] args) {
        return this.messageHolder.getMessage(code, args);
    }


    public String getMessage(String code, Object[] args, String defaultMessage) {
        return this.messageHolder.getMessage(code, args, defaultMessage);
    }


    public Object getCurrentUser() {
        return SecurityUtils.getSubject().getPrincipal();
    }


    public String buildSuccessJsonMsg(String msg) {
        return buildSuccessJsonMsg(msg, null);
    }


    public String buildSuccessJsonMsg(String msg, Object[] args) {
        return buildSuccessJsonMsg(msg, null, this.defaultJsonMapper);
    }


    public String buildSuccessJsonMsg(String msg, Object[] args, JsonMapper jsonMapper) {
        Map<String, Object> message = new HashMap();
        message.put("success", Boolean.valueOf(true));
        message.put("msg", this.messageHolder.getMessage(msg, args, msg));
        return jsonMapper.toJson(message);
    }


    public String buildFailJsonMsg(String msg) {
        return buildFailJsonMsg(msg, null);
    }


    public String buildFailJsonMsg(String msg, Object[] args) {
        return buildFailJsonMsg(msg, null, this.defaultJsonMapper);
    }


    public String buildFailJsonMsg(String msg, Object[] args, JsonMapper jsonMapper) {
        Map<String, Object> message = new HashMap();
        message.put("success", Boolean.valueOf(false));
        message.put("msg", this.messageHolder.getMessage(msg, args, msg));
        return jsonMapper.toJson(message);
    }


    public String buildJsonResults(Object results) {
        return buildJsonResults(results, this.defaultJsonMapper);
    }


    public String buildJsonResults(Object results, JsonMapper jsonMapper) {
        Map<String, Object> resultsMap = new HashMap();
        resultsMap.put("results", results);
        return jsonMapper.toJson(resultsMap);
    }

    public MessageHolder getMessageHolder() {
        return this.messageHolder;
    }

    @Resource(name = "messageHolder")
    public void setMessageHolder(MessageHolder messageHolder) {
        this.messageHolder = messageHolder;
    }

    public JsonMapper getShortDateJsonMapper() {
        return this.shortDateJsonMapper;
    }

    public JsonMapper getLongDateJsonMapper() {
        return this.longDateJsonMapper;
    }
}
