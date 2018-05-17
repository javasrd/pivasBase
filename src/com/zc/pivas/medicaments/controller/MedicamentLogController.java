package com.zc.pivas.medicaments.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.exception.SdExceptionFactory;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.pivas.medicaments.entity.MedicamentsLog;
import com.zc.pivas.medicaments.service.MedicamentsLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 药品
 *
 * @author Jack.M
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/mdcmtLog")
public class MedicamentLogController extends SdBaseController
{
    //private static Logger log = LoggerFactory.getLogger(MedicamentLogController.class);
    
    /**
     * 业务异常工厂
     */ 
    @Resource
    private SdExceptionFactory exceptionFactory;
    
    /**
     * 药品Service
     */
    @Resource
    private MedicamentsLogService medicamentsLogService;
    
    /**
     * 初始化药品查询页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/toStkLog")
    @RequiresPermissions(value = {"PIVAS_MENU_635"})
    public String initMedicamentStock(Model model)
    {
        return "pivas/medicaments/medicamentLogList";
    }
    
    /**
     * 分页查询
     * 
     * @param paging
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mdcmtLogList", produces = "application/json")
    @ResponseBody
    public String queryMedicamentStockList(JqueryStylePaging paging, MedicamentsLog condition)
        throws Exception
    {
        String[] columns = new String[] {"id"};
        
        JqueryStylePagingResults<MedicamentsLog> pagingResults = new JqueryStylePagingResults<MedicamentsLog>(columns);
        
        condition.setDrugName(DefineStringUtil.escapeAllLike(condition.getDrugName()));
        condition.setCheckCode(DefineStringUtil.escapeAllLike(condition.getCheckCode()));
        
        List<MedicamentsLog> medicamentsList = medicamentsLogService.queryByPaging(paging, condition);
        
        pagingResults.setDataRows(medicamentsList);
        // 总数
        Integer total = medicamentsLogService.getTotalCount(condition);
        pagingResults.setTotal(total);
        // 当前页
        pagingResults.setPage(paging.getPage());
        
        return new Gson().toJson(pagingResults);
        
    }
}
