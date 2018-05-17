package com.zc.pivas.docteradvice.controller;

import com.google.gson.Gson;
import com.zc.base.common.controller.SdBaseController;
import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.user.entity.User;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.excuteRecord.service.SynYdRecordService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工作查询-退费药单控制类
 *
 * @author cacabin
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/refundRP")
public class RefundRPController extends SdBaseController {
    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(RefundRPController.class);

    /**
     * 查询起始时间
     */
    private final static String START_TIME = Propertiesconfiguration.getStringProperty("pivas.date.start");

    /**
     * 查询结束时间
     */
    private final static String END_TIME = Propertiesconfiguration.getStringProperty("pivas.date.end");

    @Resource
    private SynYdRecordService synYdRecordService;

    @Resource
    private InpatientAreaService inpAreaService;

    /**
     * 初始化药品查询页面
     *
     * @param model
     * @return 药品查询页面
     */
    @RequestMapping(value = "/index")
    public String initYdRefund(Model model) {
        InpatientAreaBean areaBean = new InpatientAreaBean();
        areaBean.setEnabled("1");
        List<InpatientAreaBean> inpAreaList = inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
        model.addAttribute("inpAreaList", inpAreaList);

        return "pivas/doctorAdvice/refundRPList";
    }

    /**
     * 不合理药单分页查询
     *
     * @param param
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public String list(@RequestParam
                               Map<String, Object> param, JqueryStylePaging jquryStylePaging)
            throws Exception {
        exchangeTime(param);

        String bednoStr = StrUtil.getObjStr(param.get("bednoS"));
        if (bednoStr != null) {
            bednoStr = DefineStringUtil.escapeAllLike(bednoStr);
            String[] bednoS = bednoStr.split(",");
            param.put("bednoS", bednoS);
        }
        String patnameStr = StrUtil.getObjStr(param.get("patnameS"));
        if (patnameStr != null) {
            patnameStr = DefineStringUtil.escapeAllLike(patnameStr);
            String[] patnameS = patnameStr.split(",");
            param.put("patnameS", patnameS);
        }
        String parentNoStr = StrUtil.getObjStr(param.get("parentNoS"));
        if (parentNoStr != null) {
            parentNoStr = DefineStringUtil.escapeAllLike(parentNoStr);
            String[] parentNoS = parentNoStr.split(",");
            param.put("parentNoS", parentNoS);
        }
        String wardNameStr = StrUtil.getObjStr(param.get("wardNameS"));
        if (wardNameStr != null) {
            wardNameStr = DefineStringUtil.escapeAllLike(wardNameStr);
            String[] wardNameS = wardNameStr.split(",");
            param.put("wardNameS", wardNameS);
        }

        String areaS = StrUtil.getObjStr(param.get("areaS"));
        param.remove("areaS");
        if (areaS != null && areaS.length() > 0) {
            String[] areaN = areaS.split(",");
            param.put("areaN", areaN);
        } else {

            User user = getCurrentUser();
            InpatientAreaBean areaBean = new InpatientAreaBean();
            areaBean.setEnabled("1");
            areaBean.setGlUserId(user.getUserId());
            List<InpatientAreaBean> inpAreaList =
                    inpAreaService.getInpatientAreaBeanList(areaBean, new JqueryStylePaging());
            ArrayList<String> inpatientArr = new ArrayList<>();
            for (InpatientAreaBean area : inpAreaList) {
                inpatientArr.add(area.getDeptCode());
            }
            param.put("areaN", inpatientArr);
        }

        JqueryStylePagingResults<ExcuteRecordBean> pageResult = synYdRecordService.queryYdrefundList(param, jquryStylePaging);

        return new Gson().toJson(pageResult);

    }

    private void exchangeTime(Map<String, Object> param) {
        // 起始时间
        String yyrqStart = String.valueOf(param.get("yyrqStart"));

        // 结束日期
        String yyrqEnd = String.valueOf(param.get("yyrqEnd"));

        if (START_TIME.startsWith("今天")) {
            yyrqStart = START_TIME.replaceAll("今天", yyrqStart);

            param.put("yyrqStart", DateUtil.parse(yyrqStart, "yyyy-MM-dd HH:mm:ss"));
        }

        if (END_TIME.startsWith("今天")) {
            yyrqEnd = END_TIME.replaceAll("今天", yyrqEnd);

            param.put(yyrqStart, DateUtil.parse(yyrqEnd, "yyyy-MM-dd HH:mm:ss"));
        } else {
            yyrqEnd = END_TIME.replaceAll("明天", yyrqEnd);

            param.put("yyrqEnd", DateUtil.addDay(DateUtil.parse(yyrqEnd, "yyyy-MM-dd HH:mm:ss"), 1));
        }
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping(value = "/checkRefund")
    @ResponseBody
    public String checkRefund(@RequestParam
                                      Map<String, Object> param) {
        exchangeTime(param);

        try {
            synYdRecordService.checkYdRefund(param);
        } catch (Exception e) {
            log.error("检查不合理医嘱对应药单失败", e);
            return buildFailJsonMsg("检查不合理医嘱对应药单失败");
        }

        return buildSuccessJsonMsg(messageHolder.getMessage("common.op.success"));
    }

    /**
     * 处理不合理医嘱对应不合理的药单
     *
     * @return
     */
    @RequestMapping(value = "/processRefund")
    @ResponseBody
    public String processRefund(ExcuteRecordBean bean) throws Exception {
        try {
            synYdRecordService.processYdRefund(bean);
        } catch (Exception e) {
            log.error("不合理医嘱对应药单处理失败", e);
            return buildFailJsonMsg("不合理医嘱对应药单处理失败");
        }

        return buildSuccessJsonMsg("不合理医嘱对应药单处理成功！");
    }
}
