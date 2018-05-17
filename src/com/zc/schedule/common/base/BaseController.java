package com.zc.schedule.common.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.JsonMapper;
import com.zc.schedule.common.util.Propertiesconfiguration;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.system.config.entity.Log;
import com.zc.schedule.system.config.service.CommService;

/**
 * Base Controller
 *
 * @author Justin
 * @version v1.0
 */
public class BaseController {

    @Resource
    protected CommService commService;

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

    protected String getLang(HttpServletRequest request) {
        Object lang = null;
        if (request != null) {
            HttpSession session = request.getSession();
            lang = session.getAttribute(SysConstant.sessionName.language);
            if (lang == null) {
                session.setAttribute(SysConstant.sessionName.language, SysConstant.DEFAULT_LANGUAGE);
            }

        }
        if (lang == null) {
            lang = SysConstant.DEFAULT_LANGUAGE;
        }
        return lang.toString();
    }

    protected void addLogSucc(HttpServletRequest request, Integer subSystem, String module, String content) {
        addLog(request, subSystem, module, SysConstant.SUCCESS, content);
    }

    protected void addLogFail(HttpServletRequest request, Integer subSystem, String module, String content) {
        addLog(request, subSystem, module, SysConstant.FAIL, content);
    }

    private void addLog(HttpServletRequest request, Integer subSystem, String module, Integer status, String content) {
        Long userAccount = Long.valueOf(0);
        if (request != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute(SysConstant.sessionName.userId) != null) {
                userAccount = DataFormat.getObjLong(session.getAttribute(SysConstant.sessionName.userId));
            }

        }
        Log log = new Log(userAccount, subSystem, module, content, status, new Date());
        commService.addLog(log);
    }
}
