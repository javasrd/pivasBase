package com.zc.schedule.common.base;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zc.schedule.common.util.DataFormat;
import org.springframework.stereotype.Service;

import com.zc.schedule.common.util.Propertiesconfiguration;
import com.zc.schedule.common.util.SpringUtil;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.system.config.dao.CommDao;
import com.zc.schedule.system.config.entity.Log;

/**
 * Base Log Class
 *
 * @author Justin
 * @version v1.0
 */
@Service
public class BaseLogService {

    @Resource(name = "commDao")
    private CommDao commDao;

    protected void addLogSucc(HttpServletRequest request, Integer subSystem, String module, String content) {
        addLog(request, subSystem, module, SysConstant.SUCCESS, content);
    }

    protected void addLogFail(HttpServletRequest request, Integer subSystem, String module, String content) {
        addLog(request, subSystem, module, SysConstant.FAIL, content);
    }

    private void addLog(HttpServletRequest request, Integer subSystem, String module, Integer status, String content) {
        Long userAccount = Long.valueOf(0);
        if (request != null) {
            HttpSession session = getScheduleSession(request);

            if (session.getAttribute(SysConstant.sessionName.userId) != null) {
                userAccount = DataFormat.getObjLong(session.getAttribute(SysConstant.sessionName.userId));
            }

        }
        Log log = new Log(userAccount, subSystem, module, content, status, new Date());
        commDao.addLog(log);
    }

    public Long getSessionUserId() {
        Long userAccount = Long.valueOf(0);
        HttpServletRequest request = SpringUtil.getRequest();
        if (request != null) {
            HttpSession session = getScheduleSession(request);

            if (session.getAttribute(SysConstant.sessionName.userId) != null) {
                userAccount = DataFormat.getObjLong(session.getAttribute(SysConstant.sessionName.userId));
            }

        }
        return userAccount;
    }

    public static HttpSession getScheduleSession(HttpServletRequest request) {

        HttpSession sessionSchedule = request.getSession();

        ServletContext ContextSchedule = sessionSchedule.getServletContext();

        String path = Propertiesconfiguration.getStringProperty("schedule.contextPath");

        ServletContext ContextPivas = ContextSchedule.getContext(path);

        if (ContextPivas != null) {

            HttpSession sessionPivas = (HttpSession) ContextPivas.getAttribute("sessionInfo");

            if (sessionPivas != null) {
                return sessionPivas;
            } else {
                return sessionSchedule;
            }
        }
        return sessionSchedule;

    }
}
