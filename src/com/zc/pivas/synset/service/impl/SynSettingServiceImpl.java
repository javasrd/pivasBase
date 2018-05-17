package com.zc.pivas.synset.service.impl;

import com.zc.base.common.util.DateUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.common.client.SynTaskClient;
import com.zc.pivas.synset.bean.SynSettingBean;
import com.zc.pivas.synset.dao.SynSettingDAO;
import com.zc.pivas.synset.service.SynSettingService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 同步任务设置业务实现
 *
 * @author kunkka
 * @version 1.0
 */
@Service("synSettingService")
public class SynSettingServiceImpl implements SynSettingService {
    /**
     * 任务状态  0,启动  1,停止
     */
    private final static String PIVAS_SYNSETTING_TASKSTATUS = "pivas.synsetting.taskstatus";

    /**
     * 任务执行模式名称，仅选择定时任务时有效, 0,分钟 1,天
     */
    private final static String PIVAS_SYNSETTING_INTERVALUNIT = "pivas.synsetting.intervalunit";

    /**
     * 任务类型，0,定时任务 1,一次性任务
     */
    private final static String PIVAS_SYNSETTING_TASKTYPE = "pivas.synsetting.tasktype";

    /**
     * 任务执行内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
     */
    private final static String PIVAS_SYNSETTING_TASKCONTENTTYPE = "pivas.synsetting.taskcontenttype";

    /**
     * 状态：0 有效 1无效
     */
    private final static int SYN_STATE_START = 0;

    /**
     * 状态：0 有效 1无效
     */
    private final static int SYN_STATE_STOP = 1;

    /**
     * 停用
     */
    private final static String TASK_DISENABLE = "1";

    /**
     * 任务类型，0,定时任务 1,一次性任务
     */
    private final static int PIVAS_TASKTYPE_ONCE = 1;

    /**
     * 任务执行模式名称，仅选择定时任务时有效, 0,分钟 1,天
     */
    private final static int PIVAS_TASKTYPE_MINUTE = 0;

    /**
     * 任务执行模式名称，仅选择定时任务时有效, 0,分钟 1,天
     */
    private final static int PIVAS_TASKTYPE_DAY = 1;

    /**
     * 一次性任务
     */
    private final static String TAKSTYPE_ONCE = "once";

    /**
     * 任务执行模式:分钟
     */
    private final static String TAKSTYPE_MINUTE = "minute";

    /**
     * 任务执行模式:天
     */
    private final static String TAKSTYPE_DAY = "day";

    /**
     * 对接客户端成功
     */
    private final static String TASK_SUCCESS = "200";

    /**
     * 启用
     */
    private final static String TASK_ENABLE = "0";

    /**
     * 停用
     */
    //private final static String TASK_DISENABLE = "1";

    private SynSettingDAO synSettingDAO;

    /**
     * 添加表数据
     * @param bean
     * @throws Exception
     */
    public void addSynSetting(SynSettingBean bean)
            throws Exception {
        if (null == bean.getTaskInteval()) {
            bean.setTaskInteval(0);
        }
        // 拼接请求消息
        JSONObject request = createRequest(bean, TASK_ENABLE);
        JSONObject response =
                SynTaskClient.sendToDemServer(Propertiesconfiguration.getStringProperty("syntask.create"), request);

        if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
            throw new Exception("Call server failure, this url:"
                    + Propertiesconfiguration.getStringProperty("syntask.create"));
        }

        String scheduleId = response.getString("scheduleId").toString();
        // 成功，保存数据
        bean.setTaskID(scheduleId);
        bean.setCreateTime(new Timestamp(new Date().getTime()));
        synSettingDAO.addSynSetting(bean);
    }

    /**
     * 封装请求消息
     *
     * @param bean 对象
     * @return 请求消息
     * @throws JSONException
     */
    private JSONObject createRequest(SynSettingBean bean, String isEnabled)
            throws JSONException {
        // 调用同步任务创建借口
        JSONObject headJson = new JSONObject();

        JSONObject execModeJson = new JSONObject();
        execModeJson.put("retryTimes", bean.getRetryTimes());
        execModeJson.put("retryInterval", bean.getRetryInterval());
        execModeJson.put("taskPriority", String.valueOf(bean.getTaskTaskPriority()));

        // 执行时间
        JSONObject scheduleJson = new JSONObject();
        scheduleJson.put("taskStartTime", bean.getExecuteTime().toString());

        headJson.put("name", bean.getTaskName());
        headJson.put("enable", isEnabled);
        headJson.put("enable", isEnabled);
        // 任务类型1,一次性任务
        if (PIVAS_TASKTYPE_ONCE == bean.getTaskType()) {
            headJson.put("cycleType", TAKSTYPE_ONCE);
        } else {
            scheduleJson.put("interval", bean.getTaskInteval());
            switch (bean.getTaskExecuteMode()) {
                case PIVAS_TASKTYPE_MINUTE:

                    headJson.put("cycleType", TAKSTYPE_MINUTE);
                    break;

                case PIVAS_TASKTYPE_DAY:

                    headJson.put("cycleType", TAKSTYPE_DAY);
                    break;
                default:

                    headJson.put("cycleType", TAKSTYPE_MINUTE);
                    break;
            }
        }

        headJson.put("schedule", scheduleJson);
        headJson.put("execMode", execModeJson);
        headJson.put("action", bean.getTaskContentType());

        return headJson;
    }

    /**
     * 判断同步名称是否存在
     * @param bean 名称
     * @return
     */
    public boolean checkTaskNameIsExitst(SynSettingBean bean) {
        // 校验同步名称是否存在
        SynSettingBean synSetting = synSettingDAO.checkTaskNameIsExitst(bean);

        if (null != synSetting) {
            return true;
        }
        return false;
    }

    /**
     *  判断同步类型内同是否存在
     * @param bean 名称
     * @return
     */
    public boolean checkContentTypeIsExitst(SynSettingBean bean) {
        // 校验同步内容类型是否存在
        SynSettingBean synSetting = synSettingDAO.checkContentTypeIsExitst(bean);

        if (null != synSetting) {
            return true;
        }
        return false;
    }

    /**
     * 删除一条同步设置
     * @param gid 主键id
     * @throws Exception
     */
    public void delSynSetting(String gid)
            throws Exception {
        String[] gids = gid.split(",");

        String synUrl = "";
        for (String str : gids) {
            // 先停止定时任务
            changeSynState(str, TASK_DISENABLE);

            synUrl = Propertiesconfiguration.getStringProperty("syntask.del").replace("%", str);
            // 调用任务删除接口            
            JSONObject response = SynTaskClient.sendToDemServer(synUrl, null);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                throw new Exception("Call server failure, this url:" + synUrl);
            }
            // 成功则删除表数据
            synSettingDAO.delSynSetting(str);
        }

    }

    public SynSettingBean getSynSetting(SynSettingBean bean) {
        return synSettingDAO.getSynSetting(bean);
    }

    /**
     *  分页查询表数据
     * @param bean 对象
     * @param jquryStylePaging 分页参数
     * @return
     * @throws Exception
     */
    public JqueryStylePagingResults<SynSettingBean> getSynSettingLsit(SynSettingBean bean,
                                                                      JqueryStylePaging jquryStylePaging)
            throws Exception {
        // 用于和页面显示顺序一样
        String[] columns =
                new String[]{"taskID", "taskName", "taskTypeName", "taskExecuteModeName", "taskTaskPriority",
                        "taskInteval", "taskExecuteTime", "taskContentTypeName", "taskStatusName"};
        JqueryStylePagingResults<SynSettingBean> pagingResults = new JqueryStylePagingResults<SynSettingBean>(columns);

        // 总数
        int total;
        List<SynSettingBean> synSettingList = synSettingDAO.getSynSettingList(bean, jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(synSettingList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            synSettingList = synSettingDAO.getSynSettingList(bean, jquryStylePaging);
        }

        //获取状态对应的国际化
        if (DefineCollectionUtil.isNotEmpty(synSettingList)) {
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            for (SynSettingBean synSetting : synSettingList) {
                // 任务状态  0,启动  1,停止
                synSetting.setTaskStatusName(DictFacade.getName(PIVAS_SYNSETTING_TASKSTATUS,
                        DefineStringUtil.parseNull(synSetting.getTaskStatus()),
                        lang));

                // 任务执行模式，仅选择定时任务时有效, 0,分钟 1,天
                synSetting.setTaskExecuteModeName(DictFacade.getName(PIVAS_SYNSETTING_INTERVALUNIT,
                        DefineStringUtil.parseNull(synSetting.getTaskExecuteMode()),
                        lang));

                // 任务类型，0,定时任务 1,一次性任务 
                synSetting.setTaskTypeName(DictFacade.getName(PIVAS_SYNSETTING_TASKTYPE,
                        DefineStringUtil.parseNull(synSetting.getTaskType()),
                        lang));

                // 任务执行内容类型  0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
                synSetting.setTaskContentTypeName(DictFacade.getName(PIVAS_SYNSETTING_TASKCONTENTTYPE,
                        DefineStringUtil.parseNull(synSetting.getTaskContentType()),
                        lang));

                synSetting.setTaskExecuteTime(DateUtil.format(synSetting.getExecuteTime(), "yyyy-MM-dd HH:mm:ss"));

            }
        }

        total = synSettingDAO.getSynSettingTotal(bean);

        pagingResults.setDataRows(synSettingList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 更新同步设置
     * @param bean 需要修改的数据
     * @throws Exception
     */
    public void updateSynSetting(SynSettingBean bean)
            throws Exception {
        // 调用修改同步任务接口
        String synUrl = Propertiesconfiguration.getStringProperty("syntask.update").replace("%", bean.getTaskID());
        JSONObject request = null;

        // 任务状态  0,启动  1,停止
        request = createRequest(bean, String.valueOf(bean.getTaskStatus()));

        JSONObject response = SynTaskClient.sendToDemServer(synUrl, request);

        if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
            throw new Exception("Call server failure, this url:" + synUrl);
        }
        // 成功，修改表数据
        bean.setUpdateTime(new Timestamp(new Date().getTime()));
        synSettingDAO.updateSynSetting(bean);

    }

    /**
     * 修改同步状态
     * ：启动、停止
     *
     * @param taskID   任务id
     * @param isEnable 状态
     * @throws Exception
     */
    public void changeSynState(String taskID, String isEnable)
            throws Exception {
        String[] taskIDs = taskID.split(",");
        String synUrl = "";
        JSONObject request = null;
        SynSettingBean bean = null;
        for (String str : taskIDs) {
            // 调用同步任务停止接口
            synUrl = Propertiesconfiguration.getStringProperty("syntask.update").replace("%", str);
            bean = new SynSettingBean();
            bean.setTaskID(str);
            bean.setTaskStatus(SYN_STATE_START);
            bean = synSettingDAO.getSynSetting(bean);
            request = createRequest(bean, isEnable);
            JSONObject response = SynTaskClient.sendToDemServer(synUrl, request);

            if (null == response || !TASK_SUCCESS.equals(response.get("result").toString())) {
                throw new Exception("Call server failure, this url:" + synUrl);
            }

            // 成功，修改同步状态字段
            if (TASK_ENABLE.equals(isEnable)) {
                bean.setTaskStatus(SYN_STATE_START);
            } else {
                bean.setTaskStatus(SYN_STATE_STOP);
            }

            synSettingDAO.updateSynState(bean);
        }

    }

    @Autowired
    public void setSynSettingDAO(SynSettingDAO synSettingDAO) {
        this.synSettingDAO = synSettingDAO;
    }

}
