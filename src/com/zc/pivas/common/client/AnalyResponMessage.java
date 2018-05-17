package com.zc.pivas.common.client;

import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.sc.modules.medicalFrequency.entity.MedicalFrequency;
import com.zc.base.sc.modules.medicalFrequency.service.MedicalFrequencyService;
import com.zc.base.sys.common.util.Propertiesconfiguration;
import com.zc.pivas.docteradvice.entity.SynDoctorAdviceBean;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.docteradvice.service.DoctorAdviceMainService;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import com.zc.pivas.common.util.PinYin;
import com.zc.pivas.drugway.bean.DrugWayBean;
import com.zc.pivas.drugway.service.DrugWayService;
import com.zc.pivas.employee.bean.EmployeeInfoBean;
import com.zc.pivas.employee.service.EmployeeInfoService;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.excuteRecord.service.SynYdRecordService;
import com.zc.pivas.inpatientarea.bean.InpatientAreaBean;
import com.zc.pivas.inpatientarea.service.InpatientAreaService;
import com.zc.pivas.medicaments.entity.Medicaments;
import com.zc.pivas.medicaments.service.MedicamentsService;
import com.zc.pivas.medicamentscategory.entity.MedicCategory;
import com.zc.pivas.medicamentscategory.service.MedicCategoryService;
import com.zc.pivas.patient.bean.PatientBean;
import com.zc.pivas.patient.service.PatientService;
import com.zc.pivas.scans.service.ScansService;
import com.zc.pivas.synresult.bean.TaskResultBean;
import com.zc.pivas.synresult.service.TaskResultService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析响应报文
 *
 * @author Ray
 * @version 1.0
 */
@Service("analyResponMessage")
public class AnalyResponMessage {

    private static final Logger log = LoggerFactory.getLogger(AnalyResponMessage.class);

    /**
     * 医嘱状态。0：执行 1：停止 2：撤销
     */
    private final static int YZZT_NORMAL = 0;

    public final static String SYN_YZ_DATA_MODE = Propertiesconfiguration.getStringProperty("synyz.datamode");

    //病人
    private PatientService patientService;

    //医嘱
    @Resource
    private DoctorAdviceService yzService;

    //药品
    @Resource
    private MedicamentsService medicamentsService;

    //病区
    private InpatientAreaService inpatientAreaService;

    //用药频次
    @Resource
    private MedicalFrequencyService frequencyService;

    private DrugWayService drugWayService;

    //医嘱
    @Resource
    private DoctorAdviceMainService yzMainService;

    //同步结果
    private TaskResultService taskResultService;

    //瓶签
    @Resource
    private ScansService scansService;

    //药品分类
    @Resource
    private MedicCategoryService medicCategoryService;

    //员工信息
    @Resource
    private EmployeeInfoService employeeInfoService;

    //药单执行记录
    @Resource
    private SynYdRecordService synYdRecordService;

    /**
     * 同步任务结果
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyTaskResultRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                TaskResultBean bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new TaskResultBean();
                    // 将json对象中的数据转成对应的bean

                    // 定时任务唯一标识
                    bean.setTaskID(data.getString("scheduleId"));

                    // 任务名称
                    bean.setTaskName(data.getString("name"));

                    // 任务类型，0,定时任务 1,一次性任务
                    bean.setTaskType(data.getInt("TYPE"));

                    // 执行结果
                    bean.setTaskResult(data.getInt("result"));

                    // 执行开始时间
                    bean.setTaskExecStartTime(new Timestamp(DateUtil.parse(data.getString("taskExecStartTime"), "yyyy-MM-dd HH:mm:ss").getTime()));

                    // 执行结束时间
                    bean.setTaskExecStopTime(new Timestamp(DateUtil.parse(data.getString("taskExecStopTime"), "yyyy-MM-dd HH:mm:ss").getTime()));

                    // 任务执行内容类型 0,病人、1,医嘱、2,病区、3,用药方式、4,医嘱频次、5,药品
                    bean.setTaskContentType(data.getInt("CONTENT_TYPE"));

                    taskResultService.addTaskResult(bean);
                }
            }

        }
    }

    /**
     * 用药途径调用成功，解析响应，入表
     *
     * @param response
     * @throws JSONException
     * @throws ParseException
     */
    public void analyDrugWayRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                DrugWayBean bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new DrugWayBean();
                    // 将json对象中的数据转成对应的bean

                    // 用药方法id
                    bean.setId(data.getString("ID"));

                    // 用药方法编码
                    bean.setCode(data.getString("code"));

                    // 用药方法名字
                    bean.setName(data.getString("name"));

                    drugWayService.synData(bean);
                }
            }

        }
    }

    /**
     * 病人调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyPatientRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                PatientBean bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new PatientBean();
                    // 将json对象中的数据转成对应的bean
                    transformPatientData(data, bean);
                    patientService.synData(bean);
                }
            }

        }
    }

    /**
     * 医嘱调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws Exception
     */
    public void analyYZRespon(JSONObject paramJsonObject) throws Exception {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                SynDoctorAdviceBean bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new SynDoctorAdviceBean();

                    try {
                        // 将json对象中的数据转成对应的bean
                        transformYZData(data, bean);

                        // 同步数据
                        synYZData(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            yzMainService.stopTempYZNotToday();

            // 医嘱自动审方（对接）
            //获取当前支持模式:默认不支持自动审方
            int autoCheckMode = -1;
            try {
                autoCheckMode = Integer.parseInt(Propertiesconfiguration.getStringProperty("pivas.yz.autocheck.mode"));
            } catch (Exception e) {
                autoCheckMode = -1;
            }

            switch (autoCheckMode) {
                // 本地知识库对比
                case 0:
                    yzMainService.autoCheckYzByLocal();
                    break;

                // 对接大通
                case 1:
                    yzMainService.autoCheckYzByDT();
                    break;

                // 对接美康
                case 2:

                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 病区调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyInpatientAreaRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                InpatientAreaBean bean = null;

                String enabled = "";
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new InpatientAreaBean();
                    // 将json对象中的数据转成对应的bean
                    // 病区编码
                    bean.setDeptCode(data.getString("deptCode"));

                    // 病区名称
                    bean.setDeptName(data.getString("deptName"));

                    List<InpatientAreaBean> list = inpatientAreaService.getInpatientAreaBeanList(bean, new JqueryStylePaging());

                    if (null != list && list.size() != 0) {
                        enabled = list.get(0).getEnabled();
                    }

                    bean.setEnabled((enabled == null) ? "0" : enabled);

                    inpatientAreaService.synData(bean);
                }
            }

        }
    }

    /**
     * 药品调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyMedicamnetRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                Medicaments bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new Medicaments();
                    // 将json对象中的数据转成对应的bean
                    transformYPData(data, bean);

                    medicamentsService.synData(bean);
                }
            }

        }
    }

    /**
     * 调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyMedicalFrequencyRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                MedicalFrequency bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new MedicalFrequency();
                    // 将json对象中的数据转成对应的bean
                    // 编码
                    bean.setCode(data.getString("orderFrequencyCode"));

                    // 名称
                    bean.setName(data.getString("orderFrequencyName"));

                    frequencyService.synData(bean);
                    // 判断同步操作0：增加 1：变更
                    //                    int action = data.getInt("action");
                    //                    
                    //                    if (0 == action)
                    //                    {
                    //                        frequencyService.insert(bean);
                    //                    }
                    //                    else
                    //                    {
                    //                        frequencyService.updateByPrimaryKeySelective(bean);
                    //                    }
                }
            }

        }
    }

    /**
     * 将json对象中的数据转成对应的bean
     *
     * @param data json对象
     * @param bean
     * @throws JSONException
     * @throws ParseException
     */
    private void transformYPData(JSONObject data, Medicaments bean) throws JSONException, ParseException {
        // 药品代码
        bean.setMedicamentsCode(data.has("drugCode") ? data.getString("drugCode") : "");

        // 药品名称
        bean.setMedicamentsName(data.has("drugName") ? data.getString("drugName") : "");

        // 规格
        bean.setMedicamentsSpecifications(data.has("specifications") ? data.getString("specifications") : "");

        // 药品使用次剂量
        bean.setMedicamentsDosage(data.has("drugUseOneDosage") ? data.getString("drugUseOneDosage") : "");

        // 药品使用次剂量单位
        bean.setMedicamentsDosageUnit(data.has("drugUseOneDosageUnit") ? data.getString("drugUseOneDosageUnit") : "");

        // 拼音码
        bean.setMedicamentsSuchama(data.has("pinyinCode") ? data.getString("pinyinCode") : "");

        // 体积
        bean.setMedicamentsVolume(data.has("drugVolume") ? data.getString("drugVolume") : "");

        // 体积单位
        bean.setMedicamentsVolumeUnit(data.has("drugVolumeUnit") ? data.getString("drugVolumeUnit") : "");

        // 包装单位
        bean.setMedicamentsPackingUnit(data.has("drugPackingUnit") ? data.getString("drugPackingUnit") : "");

        // 皮试标志,0需要，1不需要，默认不需要
        bean.setMedicamentsTestFlag(data.has("skinTestFlag") ? data.getInt("skinTestFlag") : 0);

        // 货架号
        if (data.has("shelfNo")) {
            bean.setShelfNo(data.getString("shelfNo").trim());
        }

        // 药品产地
        bean.setMedicamentsPlace(data.has("drugPlace") ? data.getString("drugPlace") : "");

        // 药品产地编码
        if (data.has("drugPlaceCode")) {
            bean.setMedicamentsPlaceCode(data.getString("drugPlaceCode"));
        }
        // 高危药品标识0:高危，1非高危
        bean.setMedicamentsDanger(data.getInt("high_risk"));

        //单价
        bean.setMedicamentsPrice((data.has("price") ? data.getString("price") : "0"));

        // 药品分类 默认 ：ALL
        bean.setCategoryId((data.has("Special_Drug") ? data.getString("Special_Drug") : null));

        // 是否溶媒
        bean.setMedicamentsMenstruum((data.has("solvent_flag") ? data.getInt("solvent_flag") : null));

        // 是否主药
        bean.setMedicamentsIsmaindrug((data.has("main_drug_flag") ? data.getInt("main_drug_flag") : null));

        // 有效期
        bean.setEffective_date((data.has("effective_date") ? data.getString("effective_date") : null));

        bean.setPhyFunctiy(data.has("phyFunctiy") ? data.getString("phyFunctiy") : "");

        // 难度系数
        bean.setDifficulty_degree((data.has("difficulty_degree") ? data.getString("difficulty_degree") : null));
    }

    /**
     * 判断该条医嘱在医嘱表中是否存在：数据拆分情况
     *
     * @param bean 同步数据
     * @return 是否存在
     */
    public void synYZData(SynDoctorAdviceBean bean) throws Exception {
        // 转换医嘱数据
        Map<String, Object> map = setYzData(bean);

        // 医嘱拆分的模式，0：不拆分   1：拆分，用医嘱数据里面的执行时间来进行拆分，不需要找频次表    2：拆分，依据频次表来进行拆分 3:某XXX医院。
        if ("0".equals(SYN_YZ_DATA_MODE)) {
            yzSynDataHandle(bean, map);
        } else if ("1".equals(SYN_YZ_DATA_MODE)) {
            // 获取执行时间
            String[] zxsjs = bean.getZxsj().split(",");

            for (String zxsj : zxsjs) {
                bean.setZxsj(zxsj);

                yzSynDataHandle(bean, map);
            }
        } else if ("2".equals(SYN_YZ_DATA_MODE)) {
            // 依据频次表来进行拆分,获取执行时间
            String orderFrequencyCode = bean.getOrderFrequencyCode();

            MedicalFrequency condition = new MedicalFrequency();
            condition.setName(orderFrequencyCode);

            List<MedicalFrequency> medicalFrequencylist = frequencyService.queryAllByCondition(condition);

            // 频次数据不存在，将该同步数据插入到异常表
            if (null == medicalFrequencylist) {
                yzService.addYZExceptionData(map);
            } else {
                // 获取频次信息
                MedicalFrequency medicalFrequency = medicalFrequencylist.get(0);

                // 起止时间
                Date startTime = DateUtil.parse(medicalFrequency.getStartTime(), "HH:mm");

                // 频次
                int timeOfDay = medicalFrequency.getTimeOfDay();

                // 间隔时间(小时)
                int interval = medicalFrequency.getInterval();

                // 执行时间
                String zxsj = "";
                for (int i = 0; i < timeOfDay; i++) {
                    // 计算执行执行时间
                    zxsj = DateUtil.addMinute(startTime, i * 60 * interval);
                    bean.setZxsj(zxsj);
                    yzSynDataHandle(bean, map);
                }
            }
        }

        // 某XXX医院
        else {
            yzSynDataHandleForSZ(bean, map);

        }
    }

    /**
     * 某XXX医院数据处理
     *
     * @param bean
     * @param map
     * @throws Exception
     */
    private void yzSynDataHandleForSZ(SynDoctorAdviceBean bean, Map<String, Object> map) throws Exception {
        // 根据药品编码查询药品表中对应的药品名称
        Medicaments medicaments = medicamentsService.getMediclByCode(bean.getDrugCode());
        map.put("medicaments_name", (medicaments == null) ? bean.getDrugName().split("/")[0] : medicaments.getMedicamentsName());
        map.put("medicamentsPackingUnit", (medicaments == null) ? bean.getMedicamentsPackingUnit() : medicaments.getMedicamentsPackingUnit());

        bean.setMedicamentsPackingUnit(String.valueOf(map.get("medicamentsPackingUnit")));
        // 拼接药品信息：药品+规格
        map.put("drugName", String.valueOf(map.get("medicaments_name")) + " " + bean.getSpecifications().split("\\*")[0]);
        map.put("synDate", bean.getSynData());

        String pidsj = bean.getOrderGroupNo() + "_" + "0_0" + "_" + String.valueOf(bean.getYzlx()) + "_" + "0";
        String pidsjCopy = bean.getOrderGroupNo() + "__" + "0_0" + "_" + String.valueOf(bean.getYzlx()) + "_" + "0";

        // 是否化疗类医嘱
        boolean isChemotherapy = false;

        // 判断是否为营养液
        boolean isNutrientSolution = false;
        if (null != medicaments) {
            isChemotherapy = checkIsChemotherapy(medicaments);
            isNutrientSolution = checkIsNutrientSolution(medicaments);
        }

        // 临时医嘱来源 0临时医嘱  1长嘱  2 pivas 3 医院过来临时 化疗
        String yzReasource = "";
        if (1 == bean.getYzlx()) {
            // 暂时屏蔽费化疗类临嘱不展示的问题
            //            if (isChemotherapy || isNutrientSolution)
            //            {
            yzReasource = "3";
            map.put("yzResourceCopy", "3");
            //            }
            //            else
            //            {
            //                yzReasource = "0";
            //                map.put("yzResourceCopy", "0");
            //            }
        } else if (0 == bean.getYzlx() && (isChemotherapy || isNutrientSolution)) {
            yzReasource = "1";
            map.put("yzResourceCopy", "2");
        } else {
            yzReasource = "1";
            map.put("yzResourceCopy", "1");
        }

        String yzResourceCopy = String.valueOf(map.get("yzResourceCopy"));

        // 判断当前医嘱状态
        int orderExecuteStatus = bean.getOrderExecuteStatus();

        // 判断是否需要复制医嘱
        //boolean isNeedCopy = checkIsNeedCopy(bean, yzResourceCopy);某XXX医院版本暂时屏蔽
        boolean isNeedCopy = false;
        // 拼接主表唯一id：pidsj
        map.put("pidsj", pidsj);

        // 获取医嘱主表信息
        DoctorAdviceMain yzMain = yzMainService.getYzByCondition(map);

        DoctorAdvice yz = yzService.getYzByCondition(map);

        map.put("zxrq", bean.getSynData());
        map.put("zxsj", "0");

        // 判断是否需要重置医嘱状态
        //int isNeedRest = 0;

        // 是否需要修改
        boolean needUpdate = false;

        // 如果不存在新增
        if (null == yz) {
            //添加源数据
            map.put("yzResource", yzReasource);

            yzService.addYZ(map);

            // 重置审核状态
            //            isNeedRest = 1;

            // 判断是否有复制过得数据
            List<DoctorAdvice> yzCopyList = yzService.qryBeanByPidsj(pidsjCopy);

            // 如果为化疗类长期医嘱，则需要复制一条
            if (isNeedCopy || (null != yzCopyList && yzCopyList.size() != 0)) {
                copyYzInfo(bean, map, pidsj, pidsjCopy);
            }
        } else {
            //            String dropSpeed =
            //                (yz.getDropSpeed() == null || "".equals(yz.getDropSpeed())) ? "" : yz.getDropSpeed().trim();
            //            String synDropSpeed =
            //                (bean.getDropSpeed() == null || "".equals(bean.getDropSpeed())) ? "" : bean.getDropSpeed().trim();
            //
            //            if (yz.getYzzt().intValue() != bean.getOrderExecuteStatus().intValue())
            //            {
            //                isNeedRest = 1;
            //            }

            // 根据护士审核时间或者医生确认提交时间判断是否允许修改医嘱信息
            String timeType = Propertiesconfiguration.getStringProperty("pivas.yz.update.time.base");

            String oldTime = "";

            String newTime = "";
            // 医生提交时间
            if ("0".equals(timeType)) {
                oldTime = (yz.getConfirmDate() == null) ? "" : yz.getConfirmDate();
                newTime = (bean.getConfirmDate() == null) ? "" : bean.getConfirmDate();
            }
            // 护士审核时间
            else if ("1".equals(timeType)) {
                oldTime = (yz.getCheckDate() == null) ? "" : yz.getCheckDate();
                newTime = (bean.getCheckDate() == null) ? "" : bean.getCheckDate();
            }

            if (!newTime.equals(oldTime) || "".equals(newTime)) {
                //                needUpdate = true;
                //
                //                if (!yz.getChargeCode().equals(bean.getDrugCode()) || !yz.getDose().equals(bean.getDrugUseOneDosAge())
                //                    || !yz.getQuantity().equals(bean.getDrugAmount())
                //                    || !yz.getFreqCode().equals(bean.getOrderFrequencyCode()) || !dropSpeed.equals(synDropSpeed)
                //                    || !bean.getFirstUseCount().equals(yz.getFirstUseCount()))
                //                {
                //                    isNeedRest = 1;
                //                }

                yzService.updateYZ(map);

                // 判断是否有复制过得数据
                List<DoctorAdvice> yzCopyList = yzService.qryBeanByPidsj(pidsjCopy);

                // 如果为化疗类长期医嘱，则需要复制一条
                if (isNeedCopy || (null != yzCopyList && yzCopyList.size() != 0)) {
                    copyYzInfo(bean, map, pidsj, pidsjCopy);
                }

                map.put("actOrderNo", bean.getOrderNo() + "_");
                map.put("parentNo", bean.getOrderGroupNo() + "_");
                map.put("pidsj", pidsjCopy);
                map.put("yzlx", 1);
                yzService.updateYZ(map);
            } else {
                yzService.changeYzStatus(map);
            }
        }

        map.put("actOrderNo", bean.getOrderNo());
        map.put("parentNo", bean.getOrderGroupNo());
        map.put("yzlx", bean.getYzlx());
        // 拼接主表唯一id：pidsj
        map.put("pidsj", pidsj);

        //        if (1 == isNeedRest)
        //        {
        //            yzService.resetYZSH(map);
        //            yzMainService.resetYZSH(map);
        //        }

        // 判断主主医嘱表是否存在
        if (null == yzMain) {
            map.put("yzResource", yzReasource);
            yzMainService.addYZ(map);

            // 如果为化疗类长期医嘱，则需要复制一条
            if (isNeedCopy) {
                // 如果为化疗类长期医嘱，则需要复制一条
                map.put("yzlx", 1);
                map.put("actOrderNo", bean.getOrderNo() + "_");
                map.put("parentNo", bean.getOrderGroupNo() + "_");
                map.put("yzResource", yzResourceCopy);
                map.put("pidsj", pidsjCopy);
                yzMainService.addYZ(map);
            }

        } else if (null != yzMain) {
            // 获取当前输液量
            String transfusion = StrUtil.isNotNull(yzMainService.getTransfusion(map)) ? yzMainService.getTransfusion(map) : "0";

            // 获取当前医嘱主表中的输液量
            //            String transfusionNow =
            //                StrUtil.isNotNull(yzMain.getTransfusion()) ? yzMain.getTransfusion() : "0";

            //            if (Float.parseFloat(transfusion) != Float.parseFloat(transfusionNow) && (1 != isNeedRest))
            //            {
            //                yzService.resetYZSH(map);
            //                yzMainService.resetYZSH(map);
            //            }

            // 判断该子医嘱在主表中是否存在，不存在拼接
            String actOrderNo = (String) map.get("actOrderNo");

            if ((null == yzMain.getActOrderNo() || "".equals(yzMain.getActOrderNo()) || !yzMain.getActOrderNo().contains(actOrderNo)) && orderExecuteStatus == 0) {
                Map<String, Object> yzMainMap = setYzMainData(map, yzMain, bean);
                yzMainMap.put("transfusion", transfusion);
                yzMainMap.put("yzResource", yzReasource);
                yzMainService.updateYZ(yzMainMap);

                // 判断是否有复制过得数据
                Map<String, Object> needCopyMap = new HashMap<String, Object>();
                needCopyMap.put("pidsj", pidsjCopy);
                DoctorAdviceMain yzMainCopy = yzMainService.getYzByCondition(needCopyMap);

                if (null != yzMainCopy) {
                    bean.setOrderNo(bean.getOrderNo() + "_");
                    yzMainMap = setYzMainData(map, yzMainCopy, bean);
                    yzMainMap.put("transfusion", transfusion);
                    yzMainMap.put("pidsj", pidsjCopy);
                    yzMainMap.put("yzlx", 1);
                    yzMainMap.put("yzResource", yzReasource);
                    yzMainService.updateYZ(yzMainMap);
                }
                // 如果为化疗类长期医嘱，则需要复制一条
                else if (isNeedCopy) {
                    map.put("yzlx", 1);
                    bean.setOrderNo(bean.getOrderNo() + "_");
                    map.put("yzResource", yzResourceCopy);
                    map.put("pidsj", pidsjCopy);

                    map.put("actOrderNo", bean.getOrderNo());
                    map.put("parentNo", bean.getOrderGroupNo() + "_");
                    yzMainService.addYZ(map);

                    // 过滤未添加的子医嘱
                    needCopyMap.put("pidsj", pidsj);
                    needCopyMap.put("pidsjcopy", pidsjCopy);
                    List<DoctorAdvice> yzNeedCopyList = yzService.qryListBeanNotCopyInMain(needCopyMap);

                    needCopyMap.put("pidsj", pidsjCopy);

                    if (null != yzNeedCopyList) {
                        for (DoctorAdvice yzCopy : yzNeedCopyList) {
                            yzMainCopy = yzMainService.getYzByCondition(needCopyMap);
                            yzMainMap = setYzToYzMainData(yzMainCopy, yzCopy);
                            yzMainMap.put("transfusion", transfusion);
                            yzMainMap.put("yzlx", 1);
                            yzMainMap.put("pidsj", pidsjCopy);
                            yzMainMap.put("yzResource", yzReasource);
                            yzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
                            yzMainService.updateYZ(yzMainMap);
                        }
                    }
                }
            } else if (null != yzMain.getActOrderNo() && !"".equals(yzMain.getActOrderNo()) && yzMain.getActOrderNo().contains(actOrderNo)) {
                if (needUpdate && (orderExecuteStatus == 0)) {
                    // 修改医嘱主表中对应的停止的子医嘱数据
                    updateActOrderInYzMain(bean, yzMain, yz, map);
                    Map<String, Object> yzMainMap = setYzMainDataForUpdate(yzMain, bean);

                    //如果床号发生变化，则同步修改pivas_yd,pivas_yd_main,SRVS_LABEL
                    if (!yzMain.getBedno().equals(bean.getBedNo())) {
                        yzMainService.changeYDBedno(map);
                        yzMainService.changeYDMainBedno(map);
                        scansService.changePQBedno(map);
                    }
                    yzMainMap.put("yzResource", yzReasource);
                    yzMainMap.put("transfusion", transfusion);
                    yzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
                    yzMainService.updateYZ(yzMainMap);

                    // 修改临时医嘱数据
                    Map<String, Object> needCopyMap = new HashMap<String, Object>();
                    needCopyMap.put("pidsj", pidsjCopy);
                    DoctorAdviceMain yzMainCopy = yzMainService.getYzByCondition(needCopyMap);

                    if (null != yzMainCopy) {
                        yzMainMap.put("pidsj", pidsjCopy);
                        yzMainMap.put("yzlx", 1);
                        yzMainMap.put("actOrderNo", yzMainCopy.getActOrderNo());
                        yzMainMap.put("yzResource", yzReasource);
                        yzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
                        yzMainService.updateYZ(yzMainMap);
                    }
                    // 如果为化疗类长期医嘱，则需要复制一条
                    else if (isNeedCopy) {
                        map.put("yzlx", 1);
                        map.put("yzResource", yzResourceCopy);
                        map.put("pidsj", pidsjCopy);

                        map.put("actOrderNo", bean.getOrderNo() + "_");
                        map.put("parentNo", bean.getOrderGroupNo() + "_");
                        yzMainService.addYZ(map);

                        // 过滤未添加的子医嘱
                        needCopyMap.put("pidsj", pidsj);
                        needCopyMap.put("pidsjcopy", pidsjCopy);
                        List<DoctorAdvice> yzNeedCopyList = yzService.qryListBeanNotCopyInMain(needCopyMap);

                        needCopyMap.put("pidsj", pidsjCopy);

                        if (null != yzNeedCopyList) {
                            for (DoctorAdvice yzCopy : yzNeedCopyList) {
                                yzMainCopy = yzMainService.getYzByCondition(needCopyMap);
                                yzMainMap = setYzToYzMainData(yzMainCopy, yzCopy);
                                yzMainMap.put("yzlx", 1);
                                yzMainMap.put("pidsj", pidsjCopy);
                                yzMainMap.put("yzResource", yzReasource);
                                yzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
                                yzMainService.updateYZ(yzMainMap);
                            }
                        }
                    }
                } else {
                    if (YZZT_NORMAL != orderExecuteStatus) {
                        // 删除医嘱主表中对应的停止的子医嘱数据
                        //delActOrderInYzMain(bean, yzMain);
                        Map<String, Object> yzMainMap = setYzMainDataForUpdate(yzMain, bean);
                        yzMainMap.put("transfusion", transfusion);
                        if (!"".equals(yzMain.getActOrderNo())) {
                            yzMainMap.put("orderExecuteStatus", 0);
                        }
                        yzMainMap.put("yzResource", yzReasource);
                        yzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
                        yzMainService.updateYZ(yzMainMap);

                        // 如果全部删除，则修改主医嘱状态为停止
                        //                        if ("".equals(yzMain.getActOrderNo()))
                        //                        {
                        yzMainService.changeYzMainStatus(map);

                        // 医嘱拆分规则 ydzxjl：药单执行记录  ybgz：根据一般规则
                        if ("ybgz".equals(Propertiesconfiguration.getStringProperty("pivas.yz.split.mode"))) {
                            // 修改药单表状态
                            yzMainService.changeYDStateByParentNo(map);

                            // 修改药单主表
                            yzMainService.changeYDMainStateByParentNo(map);

                            // 修改瓶签表状态
                            scansService.changePQStateByParentNo(map);
                        }

                        //                        }

                        // 如果存在复制的临时医嘱，修改对应的临时医嘱数据
                        Map<String, Object> needCopyMap = new HashMap<String, Object>();
                        needCopyMap.put("pidsj", pidsjCopy);
                        DoctorAdviceMain yzMainCopy = yzMainService.getYzByCondition(needCopyMap);

                        if (null != yzMainCopy) {
                            // 删除医嘱主表中对应的停止的子医嘱数据
                            bean.setOrderGroupNo(bean.getOrderGroupNo() + "_");
                            bean.setOrderNo(bean.getOrderNo() + "_");
                            //delActOrderInYzMain(bean, yzMainCopy);
                            yzMainMap = new HashMap<String, Object>();
                            yzMainMap = setYzMainDataForUpdate(yzMainCopy, bean);
                            yzMainMap.put("transfusion", transfusion);
                            if (!"".equals(yzMainCopy.getActOrderNo())) {
                                yzMainMap.put("orderExecuteStatus", 0);
                            }
                            yzMainMap.put("yzResource", yzReasource);
                            yzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
                            yzMainService.updateYZ(yzMainMap);

                            //                            if ("".equals(yzMainCopy.getActOrderNo()))
                            //                            {
                            map.put("parentNo", bean.getOrderGroupNo());

                            // 修改医嘱主表状态
                            yzMainService.changeYzMainStatus(map);

                            // 医嘱拆分规则 ydzxjl：药单执行记录  ybgz：根据一般规则
                            if ("ybgz".equals(Propertiesconfiguration.getStringProperty("pivas.yz.split.mode"))) {
                                // 修改药单表状态
                                yzMainService.changeYDStateByParentNo(map);

                                // 修改药单主表
                                yzMainService.changeYDMainStateByParentNo(map);

                                // 修改瓶签表状态
                                scansService.changePQStateByParentNo(map);
                            }
                            //                            }
                        }

                    } else if (!yzMain.getBedno().equals(bean.getBedNo())) {
                        yzMainService.changeYDBedno(map);
                        yzMainService.changeYDMainBedno(map);
                        yzMainService.changeBedno(map);
                        scansService.changePQBedno(map);
                    }
                }
            }
        }

    }

    private void copyYzInfo(SynDoctorAdviceBean bean, Map<String, Object> map, String pidsj, String pidsjCopy) {
        map.put("actOrderNo", bean.getOrderNo() + "_");
        map.put("parentNo", bean.getOrderGroupNo() + "_");
        map.put("yzlx", 1);
        map.put("yzResource", "2");
        map.put("pidsj", pidsjCopy);

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("actOrderNo", bean.getOrderNo() + "_");
        condition.put("parentNo", bean.getOrderGroupNo() + "_");
        condition.put("pidsj", pidsjCopy);
        DoctorAdvice yz = yzService.getYzByCondition(condition);

        if (null == yz) {
            yzService.addYZ(map);
        }

        // 查询未复制的子医嘱
        Map<String, Object> needCopyMap = new HashMap<String, Object>();
        needCopyMap.put("pidsj", pidsj);
        needCopyMap.put("pidsjcopy", pidsjCopy);
        List<DoctorAdvice> yzNeedCopyList = yzService.qryListBeanNotCopyByMap(needCopyMap);

        if (null != yzNeedCopyList) {
            for (DoctorAdvice yzNeed : yzNeedCopyList) {
                needCopyMap = setYzCopy(yzNeed, pidsjCopy);
                needCopyMap.put("yzResource", "2");
                yzService.addYZ(needCopyMap);
            }
        }
    }

    /**
     * 判断是否为化疗类药品
     *
     * @param medicaments
     * @return
     */
    private boolean checkIsChemotherapy(Medicaments medicaments) {
        boolean isChemotherapy = false;
        if (null != medicaments) {
            try {
                long categoryId = Long.parseLong(medicaments.getCategoryId());

                // 判断药品是否属于化疗类
                MedicCategory medicCategory = medicCategoryService.displayCategory(categoryId);

                if (null != medicCategory && medicCategory.getCategoryCode() != null && medicCategory.getCategoryCode().contains("化")) {
                    isChemotherapy = true;
                }
            } catch (NumberFormatException e) {
                isChemotherapy = false;
            }

        }
        return isChemotherapy;
    }

    /**
     * 判断是否为营养液
     *
     * @param medicaments
     * @return
     */
    private boolean checkIsNutrientSolution(Medicaments medicaments) {
        // 获取系统配置
        String nutrientSolutions = Propertiesconfiguration.getStringProperty("yingyangye");

        if (null != nutrientSolutions && !"".equals(nutrientSolutions)) {
            for (String nutrientSolution : nutrientSolutions.split(",")) {
                if (medicaments.getMedicamentsName().contains(nutrientSolution)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断是否需要复制医嘱
     *
     * @param bean
     * @param yzResourceCopy
     * @return
     * @throws ParseException
     */
    private boolean checkIsNeedCopy(SynDoctorAdviceBean bean, String yzResourceCopy) throws ParseException {
        // 判断医嘱开立时间是否满足复制的条件
        SimpleDateFormat fromat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fromat2 = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat fromat3 = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = fromat2.parse(fromat1.format(bean.getOrderOrderDate()));
        String synData = fromat3.format(fromat2.parse(bean.getSynData()));
        Date startTime = fromat2.parse(synData + " 00");
        Date endTime = fromat2.parse(synData + " " + Propertiesconfiguration.getStringProperty("synhis.synyz.checktime.copy"));

        boolean checkOrderTiem = false;
        if (!orderDate.before(startTime) && !orderDate.after(endTime)) {
            checkOrderTiem = true;
        }

        // 是否需要复制数据
        boolean isNeedCopy = false;

        // 如果为化疗类长期医嘱，则需要复制一条
        if ("2".equals(yzResourceCopy) && checkOrderTiem && bean.getOrderExecuteStatus() == 0) {
            isNeedCopy = true;
        }
        return isNeedCopy;
    }

    /**
     * 删除医嘱主表中对应的停止的子医嘱数据
     *
     * @param bean
     * @param yzMain
     */
    private void updateActOrderInYzMain(SynDoctorAdviceBean bean, DoctorAdviceMain yzMain, DoctorAdvice yz, Map<String, Object> map) {
        int index = 0;
        String[] actOrderNOs = yzMain.getActOrderNo().split(",");

        for (String orderNo : actOrderNOs) {
            if (orderNo.equals(bean.getOrderNo())) {
                break;
            }
            index++;
        }

        // 药品编码
        yzMain.setChargeCode(yzMain.getChargeCode().replaceAll(yz.getChargeCode(), bean.getDrugCode()));

        // 药品名称
        if (StrUtil.isNotNull(yzMain.getDrugname())) {
            yzMain.setDrugname(StrUtil.changeString(yzMain.getDrugname().split("@@"), index, String.valueOf(map.get("drugName")), "@@", false));
        }

        // 药品规格
        if (StrUtil.isNotNull(yzMain.getSpecifications())) {
            yzMain.setSpecifications(StrUtil.changeString(yzMain.getSpecifications().split("@@"), index, String.valueOf(map.get("medicamentsPackingUnit")), "@@", false));
        }

        // 药品单次计量
        if (StrUtil.isNotNull(yzMain.getDose())) {
            yzMain.setDose(StrUtil.changeString(yzMain.getDose().split("@@"), index, bean.getDrugUseOneDosAge(), "@@", false));
        }

        // 剂量单位
        if (StrUtil.isNotNull(yzMain.getDoseUnit())) {
            yzMain.setDoseUnit(StrUtil.changeString(yzMain.getDoseUnit().split("@@"), index, bean.getDrugUseOneDosAgeUnit(), "@@", false));
        }

        // 药品数量
        if (StrUtil.isNotNull(yzMain.getQuantity())) {
            yzMain.setQuantity(StrUtil.changeString(yzMain.getQuantity().split("@@"), index, bean.getDrugAmount(), "@@", false));
        }

        if (StrUtil.isNotNull(yzMain.getMedicamentsPackingUnit())) {
            try {
                // 包装单位
                yzMain.setMedicamentsPackingUnit(StrUtil.changeString(yzMain.getMedicamentsPackingUnit().split("@@"), index, bean.getMedicamentsPackingUnit(), "@@", false));
            } catch (Exception e) {
                log.error("更新医嘱，包装单位修改失败");
            }


        }

        // 滴速
        yzMain.setDropSpeed((bean.getDropSpeed() == null || "".equals(bean.getDropSpeed())) ? yzMain.getDropSpeed() : bean.getDropSpeed());

        if (StrUtil.isNotNull(yzMain.getMedicamentsName())) {
            yzMain.setMedicamentsName(StrUtil.changeString(yzMain.getMedicamentsName().split("@@"), index, String.valueOf(map.get("medicaments_name")), "@@", false));
        }

    }

    /**
     * 删除医嘱主表中对应的停止的子医嘱数据
     *
     * @param bean
     * @param yzMain
     */
    private void delActOrderInYzMain(SynDoctorAdviceBean bean, DoctorAdviceMain yzMain) {
        int index = 0;
        String[] actOrderNOs = yzMain.getActOrderNo().split(",");

        for (String orderNo : actOrderNOs) {
            if (orderNo.equals(bean.getOrderNo())) {
                break;
            }
            index++;
        }

        // 子医嘱号
        yzMain.setActOrderNo(StrUtil.changeString(yzMain.getActOrderNo().split(","), index, "", ",", true));

        // 药品编码
        yzMain.setChargeCode(StrUtil.changeString(yzMain.getChargeCode().split("@@"), index, "", "@@", true));

        // 药品名称
        yzMain.setDrugname(StrUtil.changeString(yzMain.getDrugname().split("@@"), index, "", "@@", true));

        // 药品规格
        yzMain.setSpecifications(StrUtil.changeString(yzMain.getSpecifications().split("@@"), index, "", "@@", true));

        // 药品单次计量
        yzMain.setDose(StrUtil.changeString(yzMain.getDose().split("@@"), index, "", "@@", true));

        // 剂量单位
        yzMain.setDoseUnit(StrUtil.changeString(yzMain.getDoseUnit().split("@@"), index, "", "@@", true));

        // 药品数量
        yzMain.setQuantity(StrUtil.changeString(yzMain.getQuantity().split("@@"), index, "", "@@", true));

        if (StrUtil.isNotNull(yzMain.getMedicamentsPackingUnit())) {
            try {
                // 包装单位
                yzMain.setMedicamentsPackingUnit(StrUtil.changeString(yzMain.getMedicamentsPackingUnit().split("@@"), index, "", "@@", true));
            } catch (Exception e) {
                log.error("更新医嘱，包装单位修改失败");
            }

        }

        if (StrUtil.isNotNull(yzMain.getMedicamentsName())) {
            yzMain.setMedicamentsName(StrUtil.changeString(yzMain.getMedicamentsName().split("@@"), index, "", "@@", true));
        }
    }

    private void yzSynDataHandle(SynDoctorAdviceBean bean, Map<String, Object> map) throws Exception {
        map.put("zxsj", bean.getZxsj());

        // 拼接主表唯一id：pidsj
        map.put("pidsj", bean.getOrderGroupNo() + "_" + bean.getZxrq() + "_" + bean.getZxsj());

        // 获取医嘱主表信息
        DoctorAdviceMain yzMain = yzMainService.getYzByCondition(map);

        DoctorAdvice yz = yzService.getYzByCondition(map);

        // 如果不存在新增
        if (null == yz) {
            yzService.addYZ(map);
        } else {
            yzService.updateYZ(map);
        }

        // 判断主主医嘱表是否存在
        if (null == yzMain) {
            yzMainService.addYZ(map);
        } else {
            // 判断该子医嘱在主表中是否存在，不存在拼接
            String actOrderNo = (String) map.get("actOrderNo");

            if (!yzMain.getActOrderNo().contains(actOrderNo)) {
                Map<String, Object> yzMainMap = setYzMainData(map, yzMain, bean);
                yzMainService.updateYZ(yzMainMap);
            } else {
                // 判断医嘱状态是否为停用、全部修改为停用：医嘱主表、药单表、标签表
                int orderExecuteStatus = Integer.parseInt((String) map.get("orderExecuteStatus"));

                if (YZZT_NORMAL != orderExecuteStatus) {
                    // 修改医嘱主表状态
                    yzMainService.changeYzMainStatus(map);

                    // 修改药单表状态
                    yzMainService.changeYDStateByParentNo(map);

                    // 修改药单主表
                    yzMainService.changeYDMainStateByParentNo(map);

                    // 修改瓶签表状态
                    scansService.changePQStateByParentNo(map);
                }
            }
        }

    }

    private Map<String, Object> setYzToYzMainData(DoctorAdviceMain yzMain, DoctorAdvice yz) {
        Map<String, Object> updateYzMainMap = new HashMap<String, Object>();
        // 主表中医嘱id集合
        StringBuffer actOrderNos = new StringBuffer();
        actOrderNos.append(yzMain.getActOrderNo()).append(",").append(yz.getActOrderNo() + "_");

        // 主医嘱表中用药途径code集合
        //StringBuffer drugWayCodes = new StringBuffer();
        //drugWayCodes.append(yzMain.getSupplyCode()).append("  ").append(bean.getDoseWayCode());

        // 药品编码集合
        StringBuffer chargeCodes = new StringBuffer();
        chargeCodes.append(yzMain.getChargeCode()).append("@@").append(yz.getChargeCode());

        // 主医嘱表中医嘱的药品名称。
        StringBuffer drugNames = new StringBuffer();
        drugNames.append(yzMain.getDrugname()).append("@@").append(yz.getDrugname());

        // 主表中医嘱的药品规格。
        StringBuffer specifications = new StringBuffer();
        specifications.append(yzMain.getSpecifications()).append("@@").append(yz.getSpecifications());

        // 主医嘱表中医嘱的药品单次剂量。
        StringBuffer dose = new StringBuffer();
        dose.append(yzMain.getDose()).append("@@").append(yz.getDose());

        // 主表中医嘱的药品单次剂量单位。
        StringBuffer doseUnits = new StringBuffer();
        doseUnits.append(yzMain.getDoseUnit()).append("@@").append(yz.getDoseUnit());

        // 主医嘱表中药品数量
        StringBuffer quantity = new StringBuffer();
        quantity.append(yzMain.getQuantity()).append("@@").append(yz.getQuantity());

        // 包装单位
        StringBuffer medicamentsPackingUnit = new StringBuffer();
        medicamentsPackingUnit.append((yzMain.getMedicamentsPackingUnit() == null) ? "" : yzMain.getMedicamentsPackingUnit()).append("@@").append((yz.getMedicamentsPackingUnit() == null) ? "" : yz.getMedicamentsPackingUnit());

        // 关联药品表的药品名称
        StringBuffer medicaments_Name = new StringBuffer();
        medicaments_Name.append((yzMain.getMedicamentsName() == null) ? "" : yzMain.getMedicamentsName()).append("@@").append((yz.getMedicamentsName() == null) ? "" : yz.getMedicamentsName());

        updateYzMainMap.put("actOrderNo", actOrderNos.toString());
        updateYzMainMap.put("drugCode", chargeCodes.toString());
        updateYzMainMap.put("drugName", drugNames.toString());
        updateYzMainMap.put("specifications", specifications.toString());
        updateYzMainMap.put("drugUseOneDosage", dose.toString());
        updateYzMainMap.put("drugUseOneDosageUnit", doseUnits.toString());
        updateYzMainMap.put("drugAmount", quantity.toString());
        updateYzMainMap.put("orderExecuteStatus", yz.getYzzt());
        updateYzMainMap.put("zxsj", yz.getZxsj());
        updateYzMainMap.put("medicamentsPackingUnit", medicamentsPackingUnit.toString());

        updateYzMainMap.put("dropSpeed", (yz.getDropSpeed() == null || "".equals(yz.getDropSpeed())) ? yzMain.getDropSpeed() : yz.getDropSpeed());
        updateYzMainMap.put("bedNo", yz.getBedno());
        updateYzMainMap.put("bedNoEn", PinYin.exchange(yz.getBedno()));
        updateYzMainMap.put("sex", yz.getSex());
        updateYzMainMap.put("medicaments_name", medicaments_Name.toString());
        updateYzMainMap.put("age", yz.getAge());
        updateYzMainMap.put("ageUnit", yz.getAgeunit());

        updateYzMainMap.put("birth", yz.getBirthday());
        // 拼接主表唯一id：pidsj
        updateYzMainMap.put("synDate", yz.getSynDate());
        return updateYzMainMap;
    }

    private Map<String, Object> setYzMainData(Map<String, Object> map, DoctorAdviceMain yzMain, SynDoctorAdviceBean bean) {
        Map<String, Object> updateYzMainMap = new HashMap<String, Object>();
        // 主表中医嘱id集合
        StringBuffer actOrderNos = new StringBuffer();

        if (StrUtil.isNotNull(yzMain.getActOrderNo())) {
            actOrderNos.append(yzMain.getActOrderNo()).append(",").append(bean.getOrderNo());
        } else {
            actOrderNos.append(bean.getOrderNo());
        }

        // 主医嘱表中用药途径code集合
        //StringBuffer drugWayCodes = new StringBuffer();
        //drugWayCodes.append(yzMain.getSupplyCode()).append("  ").append(bean.getDoseWayCode());

        // 药品编码集合
        StringBuffer chargeCodes = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getChargeCode())) {
            chargeCodes.append(yzMain.getChargeCode()).append("@@").append(bean.getDrugCode());
        } else {
            chargeCodes.append(bean.getDrugCode());
        }

        // 主医嘱表中医嘱的药品名称。
        StringBuffer drugNames = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getDrugname())) {
            drugNames.append(yzMain.getDrugname()).append("@@").append(String.valueOf(map.get("drugName")));
        } else {
            drugNames.append(String.valueOf(map.get("drugName")));
        }

        // 主表中医嘱的药品规格。
        StringBuffer specifications = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getSpecifications())) {
            specifications.append(yzMain.getSpecifications()).append("@@").append(bean.getSpecifications());
        } else {
            specifications.append(bean.getSpecifications());
        }

        // 主医嘱表中医嘱的药品单次剂量。
        StringBuffer dose = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getDose())) {
            dose.append(yzMain.getDose()).append("@@").append(bean.getDrugUseOneDosAge());
        } else {
            dose.append(bean.getDrugUseOneDosAge());
        }

        // 主表中医嘱的药品单次剂量单位。
        StringBuffer doseUnits = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getDoseUnit())) {
            doseUnits.append(yzMain.getDoseUnit()).append("@@").append(bean.getDrugUseOneDosAgeUnit());
        } else {
            doseUnits.append(bean.getDrugUseOneDosAgeUnit());
        }

        // 主医嘱表中药品数量
        StringBuffer quantity = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getQuantity())) {
            quantity.append(yzMain.getQuantity()).append("@@").append(bean.getDrugAmount());
        } else {
            quantity.append(bean.getDrugAmount());
        }

        // 包装单位
        StringBuffer medicamentsPackingUnit = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getMedicamentsPackingUnit())) {
            medicamentsPackingUnit.append((yzMain.getMedicamentsPackingUnit() == null) ? "" : yzMain.getMedicamentsPackingUnit()).append("@@").append(map.get("medicamentsPackingUnit"));
        } else {
            medicamentsPackingUnit.append((map.get("medicamentsPackingUnit") == null) ? "" : map.get("medicamentsPackingUnit"));
        }

        // 关联药品表的药品名称
        StringBuffer medicaments_Name = new StringBuffer();
        if (StrUtil.isNotNull(yzMain.getMedicamentsName())) {
            medicaments_Name.append((yzMain.getMedicamentsName() == null) ? "" : yzMain.getMedicamentsName()).append("@@").append(map.get("medicaments_name"));
        } else {
            medicaments_Name.append((map.get("medicaments_name") == null) ? "" : map.get("medicaments_name"));
        }

        // 首日用药次数
        if (StrUtil.isNotNull(map.get("firstUseCount"))) {
            updateYzMainMap.put("firstUseCount", map.get("firstUseCount"));
        }

        updateYzMainMap.put("actOrderNo", actOrderNos.toString());
        updateYzMainMap.put("drugCode", chargeCodes.toString());
        updateYzMainMap.put("drugName", drugNames.toString());
        updateYzMainMap.put("specifications", specifications.toString());
        updateYzMainMap.put("drugUseOneDosage", dose.toString());
        updateYzMainMap.put("drugUseOneDosageUnit", doseUnits.toString());
        updateYzMainMap.put("drugAmount", quantity.toString());
        updateYzMainMap.put("orderExecuteStatus", bean.getOrderExecuteStatus());
        updateYzMainMap.put("zxsj", String.valueOf(map.get("zxsj")));
        updateYzMainMap.put("medicamentsPackingUnit", medicamentsPackingUnit.toString());

        updateYzMainMap.put("dropSpeed", (bean.getDropSpeed() == null || "".equals(bean.getDropSpeed())) ? yzMain.getDropSpeed() : bean.getDropSpeed());
        updateYzMainMap.put("bedNo", bean.getBedNo());
        updateYzMainMap.put("bedNoEn", PinYin.exchange(bean.getBedNo()));
        updateYzMainMap.put("sex", bean.getSex());
        updateYzMainMap.put("medicaments_name", medicaments_Name.toString());

        // 拼接主表唯一id：pidsj
        updateYzMainMap.put("pidsj", String.valueOf(map.get("pidsj")));
        updateYzMainMap.put("synDate", bean.getSynData());
        updateYzMainMap.put("orderFrequencyCode", map.get("orderFrequencyCode"));
        updateYzMainMap.put("age", bean.getAge());
        updateYzMainMap.put("ageUnit", bean.getAgeUnit());
        updateYzMainMap.put("birth", bean.getBirth());
        return updateYzMainMap;
    }

    private Map<String, Object> setYzMainDataForUpdate(DoctorAdviceMain yzMain, SynDoctorAdviceBean bean) {
        Map<String, Object> updateYzMainMap = new HashMap<String, Object>();
        updateYzMainMap.put("age", bean.getAge());
        updateYzMainMap.put("ageUnit", bean.getAgeUnit());
        updateYzMainMap.put("transfusion", yzMain.getTransfusion());
        updateYzMainMap.put("actOrderNo", yzMain.getActOrderNo());
        updateYzMainMap.put("drugCode", yzMain.getChargeCode());
        updateYzMainMap.put("drugName", yzMain.getDrugname());
        updateYzMainMap.put("specifications", yzMain.getSpecifications());
        updateYzMainMap.put("drugUseOneDosage", yzMain.getDose());
        updateYzMainMap.put("drugUseOneDosageUnit", yzMain.getDoseUnit());
        updateYzMainMap.put("drugAmount", yzMain.getQuantity());
        updateYzMainMap.put("orderExecuteStatus", bean.getOrderExecuteStatus());
        updateYzMainMap.put("zxsj", (yzMain.getZxsj() == null) ? "" : yzMain.getZxsj());
        updateYzMainMap.put("medicamentsPackingUnit", (yzMain.getMedicamentsPackingUnit() == null) ? "" : yzMain.getMedicamentsPackingUnit());

        updateYzMainMap.put("dropSpeed", (bean.getDropSpeed() == null || "".equals(bean.getDropSpeed())) ? yzMain.getDropSpeed() : bean.getDropSpeed());

        updateYzMainMap.put("medicaments_name", (yzMain.getMedicamentsName() == null) ? "" : yzMain.getMedicamentsName());
        updateYzMainMap.put("bedNo", bean.getBedNo());
        updateYzMainMap.put("bedNoEn", PinYin.exchange(bean.getBedNo()));
        updateYzMainMap.put("sex", bean.getSex());
        updateYzMainMap.put("birth", bean.getBirth());
        // 拼接主表唯一id：pidsj
        updateYzMainMap.put("pidsj", yzMain.getPidsj());
        updateYzMainMap.put("synDate", bean.getSynData());

        // 首日用药次数
        if (StrUtil.isNotNull(bean.getFirstUseCount())) {
            updateYzMainMap.put("firstUseCount", bean.getFirstUseCount());
        }
        return updateYzMainMap;
    }

    private Map<String, Object> setYzData(SynDoctorAdviceBean bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("actOrderNo", bean.getOrderNo());
        map.put("parentNo", bean.getOrderGroupNo());
        map.put("orderOpenDeptCode", bean.getOrderOpenDeptCode());
        map.put("orderOpenDeptName", bean.getOrderOpenDeptName());
        map.put("orderOpenDeptNameEn", PinYin.exchange(bean.getOrderOpenDeptName()));
        map.put("bedNo", bean.getBedNo());
        map.put("bedNoEn", PinYin.exchange(bean.getBedNo()));
        map.put("inhospNo", bean.getInhospNo());
        map.put("inhospIndexNo", bean.getInhospIndexNo());
        map.put("orderOpenDRCode", bean.getOrderOpendrCode());
        map.put("orderOpenDRName", bean.getOrderOpendrName());
        map.put("recordDRCode", bean.getRecordDrCode());
        map.put("recordDRName", bean.getRecordDrName());
        map.put("orderFrequencyCode", bean.getOrderFrequencyCode());
        map.put("doseWayCode", bean.getDoseWayCode());
        map.put("drugCode", bean.getDrugCode());
        map.put("drugName", bean.getDrugName());
        map.put("specifications", bean.getSpecifications());
        map.put("drugUseOneDosage", bean.getDrugUseOneDosAge());
        map.put("drugUseOneDosageUnit", bean.getDrugUseOneDosAgeUnit());
        map.put("drugAmount", bean.getDrugAmount());
        map.put("orderOrderDate", bean.getOrderOrderDate());
        map.put("orderStopDate", bean.getOrderStopDate());
        map.put("note", bean.getNote());
        map.put("selfDrugFlag", bean.getSelfDrugFlag());
        map.put("nutritionLiquidFlag", bean.getNutritionliquidFlag());
        map.put("orderExecuteStatus", bean.getOrderExecuteStatus());
        map.put("drugPlaceCode", bean.getDrugPlaceCode());
        map.put("zxrq", bean.getZxrq());
        map.put("zxsj", bean.getZxsj());
        map.put("yzlx", bean.getYzlx());
        map.put("medicamentsPackingUnit", bean.getMedicamentsPackingUnit());

        map.put("patName", bean.getPatName());
        map.put("sex", bean.getSex());
        map.put("birth", bean.getBirth());
        map.put("age", bean.getAge());
        map.put("ageUnit", bean.getAgeUnit());
        map.put("avdp", bean.getAvdp());
        map.put("dropSpeed", (bean.getDropSpeed() == null || "".equals(bean.getDropSpeed())) ? null : bean.getDropSpeed().trim());
        map.put("transfusion", "0");
        map.put("confirmDate", bean.getConfirmDate());
        map.put("checkDate", bean.getCheckDate());
        map.put("firstUseCount", bean.getFirstUseCount());

        map.put("transfusion", 0);

        // 输液量ML
        if ("ml".equals(bean.getDrugUseOneDosAgeUnit())) {
            float transfusion = 0;

            if (!"".equals(bean.getDrugUseOneDosAge())) {
                transfusion = transfusion + Float.parseFloat(bean.getDrugUseOneDosAge());
            }

            map.put("transfusion", transfusion);
        }

        // 根据医嘱中药品剂量单位转换后单位判断是否需要融入输液量中
        Medicaments medicament = medicamentsService.getMediclByCode(bean.getDrugCode());

        if (null != medicament && StrUtil.isNotNull(medicament.getUnitChangeAfter()) && medicament.getUnitChangeAfter().endsWith("ml") && !"".equals(bean.getDrugUseOneDosAge())) {
            float transfusion = 0;

            // 单位转换前的值
            float doseChangeBefore = Float.parseFloat(medicament.getUnitChangeBefore().split("&&")[0]);

            // 单位转换后的值
            float unitChangeAfter = Float.parseFloat(medicament.getUnitChangeAfter().split("&&")[0]);

            // 获取转换后的输液量
            if (!"".equals(bean.getDrugUseOneDosAge())) {
                transfusion = transfusion + Float.parseFloat(bean.getDrugUseOneDosAge()) * (unitChangeAfter / doseChangeBefore);
            }

            map.put("transfusion", transfusion);
        }

        return map;
    }

    private Map<String, Object> setYzCopy(DoctorAdvice yz, String pidsj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("actOrderNo", yz.getActOrderNo() + "_");
        map.put("parentNo", yz.getParentNo() + "_");
        map.put("orderOpenDeptCode", yz.getWardCode());
        map.put("orderOpenDeptName", yz.getWardName());
        map.put("bedNo", yz.getBedno());
        map.put("inhospNo", yz.getInpatientNo());
        map.put("inhospIndexNo", yz.getCaseId());
        map.put("orderOpenDRCode", (yz.getDoctor() == null) ? "" : yz.getDoctor());
        map.put("orderOpenDRName", (yz.getDoctorName() == null) ? "" : yz.getDoctorName());
        map.put("recordDRCode", (yz.getDrawer() == null) ? "" : yz.getDrawer());
        map.put("recordDRName", (yz.getDrawername() == null) ? "" : yz.getDrawername());
        map.put("orderFrequencyCode", yz.getFreqCode());
        map.put("doseWayCode", yz.getSupplyCode());
        map.put("drugCode", yz.getChargeCode());
        map.put("drugName", yz.getDrugname());
        map.put("specifications", yz.getSpecifications());
        map.put("drugUseOneDosage", yz.getDose());
        map.put("drugUseOneDosageUnit", yz.getDoseUnit());
        map.put("drugAmount", yz.getQuantity());
        map.put("orderOrderDate", yz.getStartTime());
        map.put("orderStopDate", yz.getEndTime());
        map.put("note", (yz.getRemark() == null) ? "" : yz.getRemark());
        map.put("selfDrugFlag", yz.getSelfbuy());
        map.put("nutritionLiquidFlag", yz.getTpn());
        map.put("orderExecuteStatus", yz.getYzzt());
        map.put("zxrq", yz.getZxrq());
        map.put("zxsj", yz.getZxsj());
        map.put("yzlx", "1");
        map.put("medicamentsPackingUnit", yz.getMedicamentsPackingUnit());

        map.put("patName", yz.getPatname());
        map.put("sex", yz.getSex());
        map.put("birth", yz.getBirthday());
        map.put("age", yz.getAge());
        map.put("ageUnit", yz.getAgeunit());
        map.put("avdp", yz.getAvdp());
        map.put("dropSpeed", yz.getDropSpeed());

        map.put("pidsj", pidsj);
        map.put("yzResource", "2");
        map.put("medicaments_name", yz.getMedicamentsName());

        // 输液量ML
        map.put("transfusion", yz.getTransfusion());
        map.put("synDate", yz.getSynDate());
        map.put("confirmDate", yz.getConfirmDate());
        return map;
    }

    /**
     * 将json对象中的数据转成对应的bean
     *
     * @param data json对象
     * @param bean
     * @throws JSONException
     * @throws ParseException
     */
    private void transformYZData(JSONObject data, SynDoctorAdviceBean bean) throws JSONException, ParseException {
        // 医嘱的唯一性标志
        bean.setOrderNo((data.has("orderNo")) ? data.getString("orderNo") : "");

        // 成组医嘱的唯一性标志
        bean.setOrderGroupNo((data.has("orderGroupNo")) ? data.getString("orderGroupNo") : "");

        // 开立医嘱的科室在医疗机构的科室代码
        bean.setOrderOpenDeptCode((data.has("orderOpenDeptCode")) ? data.getString("orderOpenDeptCode") : "");

        // 开立医嘱的科室在医疗机构的科室名称
        bean.setOrderOpenDeptName((data.has("orderOpenDeptName")) ? data.getString("orderOpenDeptName") : "");

        // 患者住院期间，所住床位对应的编号
        bean.setBedNo((data.has("bedNo")) ? data.getString("bedNo") : "");

        // 住院流水号
        bean.setInhospNo((data.has("inhospNo")) ? data.getString("inhospNo") : "");

        // 患者住院期间，住院对应的索引号，每次就诊相同，类似于就诊卡号。。
        bean.setInhospIndexNo((data.has("inhospIndexNo")) ? data.getString("inhospIndexNo") : "");

        // 医嘱开立医生工号
        bean.setOrderOpendrCode((data.has("orderOpenDRCode")) ? data.getString("orderOpenDRCode") : "");

        // 医嘱开立医生姓名
        bean.setOrderOpendrName((data.has("orderOpenDRName")) ? data.getString("orderOpenDRName").trim() : "");

        // 根据员工号查询员工名称
        String staff_Name = "";
        if (!"".equals(bean.getOrderOpendrCode()) && "".equals(bean.getOrderOpendrName())) {
            EmployeeInfoBean employeeInfoBean = new EmployeeInfoBean();
            employeeInfoBean.setStaff_Code(bean.getOrderOpendrCode());

            employeeInfoBean = employeeInfoService.getEmployeeInfo(employeeInfoBean);

            if (null != employeeInfoBean) {
                staff_Name = employeeInfoBean.getStaff_Name();

                bean.setOrderOpendrName(staff_Name);
            }
        }

        // 录入医生工号
        bean.setRecordDrCode((data.has("recordDRCode")) ? data.getString("recordDRCode") : "");

        // 录入医生姓名
        bean.setRecordDrName((data.has("recordDRName")) ? data.getString("recordDRName").trim() : "");

        if (!"".equals(bean.getRecordDrCode()) && "".equals(bean.getRecordDrName())) {
            EmployeeInfoBean employeeInfoBean = new EmployeeInfoBean();
            employeeInfoBean.setStaff_Code(bean.getRecordDrCode());

            employeeInfoBean = employeeInfoService.getEmployeeInfo(employeeInfoBean);

            if (null != employeeInfoBean) {
                staff_Name = employeeInfoBean.getStaff_Name();

                bean.setRecordDrName(staff_Name);
            }
        }

        // 医嘱频次代码,对应医嘱频次信息
        bean.setOrderFrequencyCode((data.has("orderFrequencyCode")) ? data.getString("orderFrequencyCode") : "");

        // 用药途径代码,对应用药途径
        bean.setDoseWayCode("");

        if (data.has("doseWayCode")) {
            DrugWayBean drugWay = new DrugWayBean();
            drugWay.setId(data.getString("doseWayCode"));
            drugWay = drugWayService.getDrugWayByCondition(drugWay);
            bean.setDoseWayCode((drugWay == null) ? "" : drugWay.getName());
        }

        // 药品代码,对应药品字典
        bean.setDrugCode((data.has("drugCode")) ? data.getString("drugCode") : "");

        // 药品名称
        bean.setDrugName((data.has("drugName")) ? data.getString("drugName") : "");

        // 规格
        bean.setSpecifications((data.has("specifications")) ? data.getString("specifications") : "");

        // 药品使用次剂量
        bean.setDrugUseOneDosAge((data.has("drugUseOneDosage")) ? data.getString("drugUseOneDosage") : "");

        // 药品使用次剂量单位
        bean.setDrugUseOneDosAgeUnit((data.has("drugUseOneDosageUnit")) ? data.getString("drugUseOneDosageUnit") : "");

        // 药品数量
        bean.setDrugAmount((data.has("drugAmount")) ? data.getString("drugAmount") : "");

        // 医嘱开立时间
        String exchangDate = "";
        SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (data.has("orderOrderDate")) {
            exchangDate = data.getString("orderOrderDate").trim();
            bean.setOrderOrderDate(new Timestamp(fromat.parse(exchangDate).getTime()));
        }

        if (data.has("orderStopDate")) {
            // 医嘱停止时间
            exchangDate = data.getString("orderStopDate").trim();
            bean.setOrderStopDate(new Timestamp(fromat.parse(exchangDate).getTime()));
        }

        // 备注
        bean.setNote((data.has("note")) ? data.getString("note") : "");

        // 自备药标志,0是，1不是默认不是
        bean.setSelfDrugFlag((data.has("selfDrugFlag")) ? data.getInt("selfDrugFlag") : 1);

        // 营养液标志,0是，1不是默认不是
        bean.setNutritionliquidFlag((data.has("nutritionLiquidFlag")) ? data.getInt("nutritionLiquidFlag") : 1);

        // 用药途径代码,对应用药途径
        bean.setOrderExecuteStatus((data.has("orderExecuteStatus")) ? data.getInt("orderExecuteStatus") : 0);

        // 药品产地代码
        bean.setDrugPlaceCode((data.has("drugPlaceCode")) ? data.getString("drugPlaceCode") : "");

        // 执行日期
        bean.setZxrq((data.has("zxrq")) ? data.getString("zxrq") : "");

        // 执行时间
        bean.setZxsj((data.has("zxsj")) ? data.getString("zxsj") : "");

        // 医嘱类型
        bean.setYzlx((data.has("yzlx")) ? data.getInt("yzlx") : 0);

        // 包装单位
        bean.setMedicamentsPackingUnit((data.has("medicamentsPackingUnit")) ? data.getString("medicamentsPackingUnit") : "");

        // 患者姓名
        bean.setPatName((data.has("patName")) ? data.getString("patName") : "");

        // 生理性别代码0:女 1：男
        bean.setSex((data.has("sex")) ? data.getString("sex") : "");

        // 出生日期，HIS过来的格式是yyyymmdd
        if (data.has("birth")) {
            String birthDay = data.getString("birth");
            fromat = new SimpleDateFormat("yyyy-MM-dd");
            bean.setBirth(new Timestamp(fromat.parse(birthDay).getTime()));
        }

        // 年龄
        bean.setAge((data.has("age")) ? data.getString("age") : "");

        // 年龄单位，0代表天，1代表月，2代表年，HIS过来的是D（天）、M（月）、Y（年）
        bean.setAgeUnit((data.has("ageUnit")) ? data.getInt("ageUnit") : null);

        // 病人体重kg
        bean.setAvdp((data.has("avdp")) ? data.getString("avdp") : "");

        // 医嘱滴速
        bean.setDropSpeed((data.has("dropSpeed")) ? data.getString("dropSpeed") : "");

        // 医嘱确认时间
        //bean.setConfirmDate((data.has("confirmDate")) ? data.getString("confirmDate") : "");

        // 护士审核时间
        bean.setCheckDate((data.has("checkDate")) ? data.getString("checkDate") : "");

        // 医嘱首日用药次数
        bean.setFirstUseCount((data.has("firstUseCount")) ? data.getString("firstUseCount") : "");

        // 同步时间
        bean.setSynData((data.has("synData")) ? data.getString("synData") : "");

        // 判断同步操作0：增加 1：变更
        bean.setAction((data.has("action")) ? data.getInt("action") : 0);
    }

    /**
     * 将json对象中的数据转成对应的bean
     *
     * @param data json对象
     * @param bean
     * @throws JSONException
     * @throws ParseException
     */
    private void transformPatientData(JSONObject data, PatientBean bean) throws JSONException, ParseException {
        // 住院流水号，病人唯一编码标识
        bean.setInhospNo(data.getString("inhospNo"));

        // 病人住院号
        bean.setCase_ID((data.has("CASE_ID") ? data.getString("CASE_ID") : ""));

        // 患者姓名
        bean.setPatName(data.getString("patName"));

        // 性别：0女，1男
        bean.setSex(data.getInt("sex"));

        // 病人出生日期
        if (data.has("BIRTHDAY")) {
            String birthDay = data.getString("BIRTHDAY");
            SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd");
            bean.setBirth(new Timestamp(fromat.parse(birthDay).getTime()));
        }

        // 病人年龄
        bean.setAge((data.has("AGE") ? data.getString("AGE") : ""));

        // 年龄单位，0天 1月 2年
        bean.setAgeUnit((data.has("AGEUNIT") ? data.getInt("AGEUNIT") : 2));

        // 病人体重
        bean.setAvdp((data.has("AVDP") ? data.getString("AVDP") : ""));

        // 病人当前病区(科室)。对应病区表
        bean.setWardCode((data.has("wardCode") ? data.getString("wardCode") : ""));

        // 患者住院期间，所住床位对应的编号
        bean.setBedNo((data.has("bedNo") ? data.getString("bedNo") : ""));

        // 病人当前状态。 如病人状态对HIS的医嘱状态或药单生成产生影响，此值必须提供。如HIS无此值，可为空。1是 0 不是
        bean.setState((data.has("state") ? data.getString("state") : ""));

        // 病人预出院时间
        bean.setHosOutTime((data.has("hosOutTime") ? data.getString("hosOutTime") : ""));

        // 病人预出院状态
        //bean.setHosOutSta((data.has("hosOutSta") ? data.getString("hosOutSta") : ""));
    }

    /**
     * 员工调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyEmployeeInfoRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                EmployeeInfoBean bean = null;
                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new EmployeeInfoBean();
                    // 将json对象中的数据转成对应的bean
                    // 编码
                    bean.setStaff_Code(data.getString("staff_Code"));

                    // 名称
                    bean.setStaff_Name(data.getString("staff_Name"));

                    employeeInfoService.synData(bean);
                    //                    // 判断同步操作0：增加 1：变更
                    //                    int action = data.getInt("action");
                    //                    
                    //                    if (0 == action)
                    //                    {
                    //                        employeeInfoService.addEmployeeInfo(bean);
                    //                    }
                    //                    else
                    //                    {
                    //                        employeeInfoService.updateEmployeeInfo(bean);
                    //                    }
                }
            }

        }
    }

    /**
     * 药单执行记录调用成功，解析响应，入表
     *
     * @param paramJsonObject
     * @throws JSONException
     * @throws ParseException
     */
    public void analyYdRecordRespon(JSONObject paramJsonObject) throws JSONException, ParseException {
        if (null != paramJsonObject) {
            JSONArray dataArray = paramJsonObject.getJSONArray("datas");

            if (null != dataArray) {
                JSONObject data = null;
                ExcuteRecordBean bean = null;

                // 药单执行批次ID
                String batchID = "";

                for (int i = 0; i < dataArray.length(); i++) {
                    data = dataArray.getJSONObject(i);
                    bean = new ExcuteRecordBean();
                    // 将json对象中的数据转成对应的bean
                    bean.setRecipeID((data.has("RecipeID")) ? data.getString("RecipeID") : "");
                    bean.setGroupNo((data.has("GroupNo")) ? data.getString("GroupNo") : "");
                    bean.setDrugListID((data.has("DrugListID")) ? data.getString("DrugListID") : "");
                    bean.setDrugFreq((data.has("DrugFreq")) ? data.getString("DrugFreq") : "");
                    bean.setDrugCode((data.has("DrugCode")) ? data.getString("DrugCode") : "");
                    bean.setDrugName((data.has("DrugName")) ? data.getString("DrugName") : "");
                    bean.setQuantity((data.has("Quantity")) ? data.getString("Quantity") : "");
                    bean.setQuantityUnit((data.has("QuantityUnit")) ? data.getString("QuantityUnit") : "");
                    bean.setSchedule((data.has("Schedule")) ? data.getString("Schedule") : "");
                    bean.setOccDT((data.has("OccDT")) ? data.getString("OccDT") : "");
                    bean.setChargeDT((data.has("ChargeDT")) ? data.getString("ChargeDT") : "");
                    bean.setInfusionDate((data.has("InfusionDate")) ? data.getString("InfusionDate") : "");
                    bean.setLabelNo((data.has("LabelNo")) ? data.getString("LabelNo") : "");
                    bean.setBegindt((data.has("Begindt")) ? data.getString("Begindt") : "");
                    bean.setEnddt((data.has("Enddt")) ? data.getString("Enddt") : "");

                    // 根据用药时间判断对应的执行批次
                    if (data.has("InfusionDate")) {
                        batchID = synYdRecordService.getBatchID(data.getString("InfusionDate"));

                        bean.setBatchID(batchID);
                    }

                    // 全天用药次数
                    bean.setUseCount((data.has("amount")) ? data.getString("amount") : "");
                    synYdRecordService.synData(bean);
                }
            }

        }
    }

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setYzService(DoctorAdviceService yzService) {
        this.yzService = yzService;
    }

    @Autowired
    public void setInpatientAreaService(InpatientAreaService inpatientAreaService) {
        this.inpatientAreaService = inpatientAreaService;
    }

    @Autowired
    public void setDrugWayService(DrugWayService drugWayService) {
        this.drugWayService = drugWayService;
    }

    @Autowired
    public void setYzMainService(DoctorAdviceMainService yzMainService) {
        this.yzMainService = yzMainService;
    }

    @Autowired
    public void setTaskResultService(TaskResultService taskResultService) {
        this.taskResultService = taskResultService;
    }

}