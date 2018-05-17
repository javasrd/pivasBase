package com.zc.pivas.allowork.service.impl;

import com.zc.pivas.allowork.bean.*;
import com.zc.pivas.allowork.dao.AlloWorkDao;
import com.zc.pivas.allowork.service.AlloWorkService;
import com.zc.pivas.statistics.bean.medicalAdvice.StaticDoctorNameBean;
import com.zc.pivas.statistics.bean.prescription.PrescriptionSearchBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置工作量统计服务层接口实现
 *
 * @author jagger
 * @version 1.0
 */
@Service("alloWorkService")
public class AlloWorkServiceImpl implements AlloWorkService {
    @Resource
    private AlloWorkDao alloWorkDao;

    /**
     * 所有药品分类
     */
    private List<String> ruleNameList = new ArrayList<String>();


    public List<StaticDoctorNameBean> queryAlloWorkNameList() {
        return alloWorkDao.queryAlloWorkNameList();
    }

    @Override
    public List<AlloWorkPieBean> queryPieList(PrescriptionSearchBean condition) {
        return alloWorkDao.queryPieList(condition);
    }

    @Override
    public List<AlloWorkPieBean> queryDetailPieList(PrescriptionSearchBean condition) {
        return alloWorkDao.queryDetailPieList(condition);
    }

    @Override
    public AlloWorkBarBean querybarList(PrescriptionSearchBean condition) {
        List<AlloWorkPieBean> allowWorkPieList = queryBarList(condition);

        if (allowWorkPieList == null) {
            return null;
        }

        // 获取所有的配置分类信息
        ruleNameList = queryAllRuleNames(condition);

        List<PzMc2AlloWorkBarBean> pzMC2AlloWorkBarList = formatPzMc2StatusStatic(allowWorkPieList);

        // 批次名称列表
        AlloWorkBarBean alloWorkBarBean = formatRule2PzMcStatic(pzMC2AlloWorkBarList);
        alloWorkBarBean.setRuleNameList(ruleNameList);
        alloWorkBarBean.setCostMap(getCostMap(allowWorkPieList));
        return alloWorkBarBean;
    }

    private Map<String, String> getCostMap(List<AlloWorkPieBean> allowWorkPieList) {
        DecimalFormat df = new DecimalFormat("#.##");

        // key = 批次+","+药品分类  value=金额
        Map<String, String> costMap = new HashMap<String, String>();

        String pzMc = null;
        String ruleName = null;
        String key = null;
        double cost = 0;
        for (AlloWorkPieBean alloWorkPieBean : allowWorkPieList) {
            pzMc = alloWorkPieBean.getName();
            ruleName = alloWorkPieBean.getRuleName();
            key = pzMc + "," + ruleName;

            cost = Double.parseDouble(alloWorkPieBean.getCost());

            costMap.put(key, df.format(cost));
        }

        return costMap;
    }

    /**
     * 格式化 批次->药物使用->药品分类数量 维度的统计
     *
     * @param allowWorkPieList
     * @return
     */
    private List<PzMc2AlloWorkBarBean> formatPzMc2StatusStatic(List<AlloWorkPieBean> allowWorkPieList) {
        List<PzMc2AlloWorkBarBean> pzMc2AlloWorkBarList = new ArrayList<PzMc2AlloWorkBarBean>();

        AlloWorkCount alloWorkCount = null;
        for (AlloWorkPieBean alloWorkPieBean : allowWorkPieList) {
            PzMc2AlloWorkBarBean pzMc2AlloWorkBarBean = null;
            for (PzMc2AlloWorkBarBean pzMc2AlloWorkBar : pzMc2AlloWorkBarList) {
                //配置人员是否相同
                if (pzMc2AlloWorkBar.getPzCode().equals(alloWorkPieBean.getPzCode())) {
                    pzMc2AlloWorkBarBean = pzMc2AlloWorkBar;
                }
            }

            //如果为空，没有找到对象，初始化对象，并插入列表
            if (pzMc2AlloWorkBarBean == null) {
                pzMc2AlloWorkBarBean = new PzMc2AlloWorkBarBean();
                pzMc2AlloWorkBarBean.setPzMc(alloWorkPieBean.getName());
                pzMc2AlloWorkBarBean.setPzCode(alloWorkPieBean.getPzCode());
                pzMc2AlloWorkBarList.add(pzMc2AlloWorkBarBean);
            }

            alloWorkCount = new AlloWorkCount();
            alloWorkCount.setRuleName(alloWorkPieBean.getRuleName());
            alloWorkCount.setStstsCount(alloWorkPieBean.getValue());
            pzMc2AlloWorkBarBean.alloWorkCountList(alloWorkCount);
        }

        // 获取当前所有的分类
        List<String> ruleList = null;

        // 数据转换完成后，对某一配置人员不存在的分类，数量默认0
        for (PzMc2AlloWorkBarBean bean : pzMc2AlloWorkBarList) {
            List<AlloWorkCount> alloWorkCountList = bean.getAlloWorkCountList();

            // 获取当前所有的分类
            ruleList = new ArrayList<String>();

            for (AlloWorkCount count : alloWorkCountList) {
                ruleList.add(count.getRuleName());
            }
            // 获取缺失的药品分类
            List<String> ruleNameLoseList = new ArrayList<String>();
            for (String ruleName : ruleNameList) {
                ruleNameLoseList.add(ruleName);
            }
            ruleNameLoseList.removeAll(ruleList);

            for (String ruleName : ruleNameLoseList) {
                alloWorkCount = new AlloWorkCount();
                alloWorkCount.setRuleName(ruleName);
                alloWorkCount.setStstsCount("0");
                bean.alloWorkCountList(alloWorkCount);
            }
        }

        return pzMc2AlloWorkBarList;
    }


    /**
     * 格式化  配置工作量统计->配置人员-> 维度的统计
     *
     * @param PzMc2AlloWorkBarList
     * @return
     */
    private AlloWorkBarBean formatRule2PzMcStatic(List<PzMc2AlloWorkBarBean> PzMc2AlloWorkBarList) {
        AlloWorkBarBean alloWorkBarBean = new AlloWorkBarBean();

        List<AlloWork2BarBean> alloWork2BarList = alloWorkBarBean.getAlloWork2BarList();
        List<String> pzMcList = null;
        for (PzMc2AlloWorkBarBean pzMc2AlloWorkBarBean : PzMc2AlloWorkBarList) {
            // 批次药品分类数量列表
            List<AlloWorkCount> alloWorkCountList = pzMc2AlloWorkBarBean.getAlloWorkCountList();

            for (AlloWorkCount alloWorkCount : alloWorkCountList) {

                AlloWork2BarBean alloWork2Bar = null;
                for (AlloWork2BarBean alloWork2BarBean : alloWork2BarList) {
                    if (alloWork2BarBean.getRuleName().equals(alloWorkCount.getRuleName())) {
                        alloWork2Bar = alloWork2BarBean;
                    }
                }
                if (alloWork2Bar == null) {
                    alloWork2Bar = new AlloWork2BarBean();
                    alloWork2Bar.setRuleName(alloWorkCount.getRuleName());
                    alloWork2BarList.add(alloWork2Bar);
                }

                if (alloWorkCount.getStstsCount() == null) {
                    alloWork2Bar.addYdCountList("0");
                } else {
                    alloWork2Bar.addYdCountList(alloWorkCount.getStstsCount());
                }
                pzMcList = alloWorkBarBean.getPzMcList();

                if (!pzMcList.contains(pzMc2AlloWorkBarBean.getPzMc())) {
                    alloWorkBarBean.getPzMcList().add(pzMc2AlloWorkBarBean.getPzMc());
                }
            }
        }

        return alloWorkBarBean;
    }

    @Override
    public List<String> queryAllRuleNames(PrescriptionSearchBean condition) {
        return alloWorkDao.queryAllRuleNames(condition);
    }

    @Override
    public List<AlloWorkPieBean> queryBarList(PrescriptionSearchBean condition) {
        return alloWorkDao.queryBarList(condition);
    }
}
