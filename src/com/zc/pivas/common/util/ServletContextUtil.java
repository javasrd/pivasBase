package com.zc.pivas.common.util;

import com.zc.base.sc.modules.batch.service.BatchService;
import com.zc.base.sys.modules.sysconfig.repository.SystemConfigDao;
import com.zc.pivas.docteradvice.service.PrescriptionMainService;
import com.zc.pivas.docteradvice.service.PrescriptionRuleService;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import com.zc.pivas.backup.service.ServerNodeService;
import com.zc.pivas.excuteRecord.service.SynYdRecordService;
import com.zc.pivas.patient.service.PatientService;
import com.zc.pivas.printlabel.service.PrintLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * <BLA启动类>
 *
 * @author  cacabin
 * @version  1.0
 */
public class ServletContextUtil extends HttpServlet
{
    
    public static DoctorAdviceMainService yzMainService;
    
    public static PrescriptionMainService ydMainService;
    
    public static DoctorAdviceService yzService;
    
    public static BatchService batchService;

    public static PrescriptionRuleService ydRuleService;
    
    public static ServerNodeService serverNodeService;
    
    public static PrintLabelService printLabelService;
    
    public static PatientService patientService;
    
    public static SystemConfigDao systemConfigDao;
    
    public static SynYdRecordService synYdRecordService;
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3712149552478793573L;
    
    /** 日志类 */
    private static final Logger log = LoggerFactory.getLogger(ServletContextUtil.class);
    
    /** {@inheritDoc} */
    
    @Override
    public void init()
            throws ServletException {
        try {
            WebApplicationContext wac =
                    WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

            yzService = (DoctorAdviceService) wac.getBean("yzService");
            yzMainService = (DoctorAdviceMainService) wac.getBean("yzMainService");
            ydMainService = (PrescriptionMainService) wac.getBean("YDMainService");
            batchService = (BatchService) wac.getBean("BatchService");
            ydRuleService = (PrescriptionRuleService) wac.getBean("ydRuleService");
            serverNodeService = (ServerNodeService) wac.getBean("ServerNodeService");
            printLabelService = (PrintLabelService) wac.getBean("PrintLabelService");
            patientService = (PatientService) wac.getBean("patientService");
            systemConfigDao = (SystemConfigDao) wac.getBean("systemConfigDao");
            synYdRecordService = (SynYdRecordService) wac.getBean("synYdRecordService");
            systemConfigDao = (SystemConfigDao) wac.getBean("systemConfigDao");
        } catch (Exception e) {
            log.error("start up servlet error ", e);
            throw new ServletException();
        }
    }
    
}
