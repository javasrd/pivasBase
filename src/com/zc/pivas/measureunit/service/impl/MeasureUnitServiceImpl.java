package com.zc.pivas.measureunit.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.base.sys.common.util.DefineStringUtil;
import com.zc.base.sys.modules.system.facade.DictFacade;
import com.zc.base.web.Servlets;
import com.zc.pivas.measureunit.bean.MeasureUnitBean;
import com.zc.pivas.measureunit.dao.MeasureUnitDAO;
import com.zc.pivas.measureunit.service.MeasureUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * 计量单位
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("measureUnitService")
public class MeasureUnitServiceImpl implements MeasureUnitService
{
    private final static String PIVAS_MEASUREUNIT_UNITY = "pivas.measureunit.unity";
    
    private final static String PIVAS_MEASUREUNIT_PROTYPE = "pivas.measureunit.protype";
    
    private MeasureUnitDAO measureUnitDAO;
    
    @Override
    public void addMeasureUnit(MeasureUnitBean bean)
    {
        measureUnitDAO.addMeasureUnit(bean);
    }
    
    @Override
    public void delMeasureUnit(String gids)
    {
        String[] gid = gids.split(",");
        
        for (String str : gid)
        {
            measureUnitDAO.delMeasureUnit(str);
        }
        
    }
    
    @Override
    public JqueryStylePagingResults<MeasureUnitBean> getMeasureUnitLsit(MeasureUnitBean bean,
        JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        // 用于和页面显示顺序一样
        String[] columns = new String[] {"code", "unityName", "proType"};
        JqueryStylePagingResults<MeasureUnitBean> pagingResults =
            new JqueryStylePagingResults<MeasureUnitBean>(columns);
        
        // 总数
        int total;
        List<MeasureUnitBean> measureUnitBeanList = null;
        
        measureUnitBeanList = measureUnitDAO.getMeasureUnitList(bean, jquryStylePaging);
        
        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(measureUnitBeanList) && jquryStylePaging.getPage() != 1)
        {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            measureUnitBeanList = measureUnitDAO.getMeasureUnitList(bean, jquryStylePaging);
        }
        
        //获取状态对应的国际化
        if (DefineCollectionUtil.isNotEmpty(measureUnitBeanList))
        {
            String lang = Servlets.getCurrentRequestLocal().getLanguage();
            for (MeasureUnitBean measureUnit : measureUnitBeanList)
            {
                measureUnit.setUnityName(DictFacade.getName(PIVAS_MEASUREUNIT_UNITY,
                    DefineStringUtil.parseNull(measureUnit.getUnity()),
                    lang));
                
                measureUnit.setProTypeName(DictFacade.getName(PIVAS_MEASUREUNIT_PROTYPE,
                    DefineStringUtil.parseNull(measureUnit.getProType()),
                    lang));
            }
        }
        
        total = measureUnitDAO.getMeasureUnitTotal(bean);
        
        pagingResults.setDataRows(measureUnitBeanList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }
    
    @Override
    public void updateMeasureUnit(MeasureUnitBean bean)
    {
        measureUnitDAO.updateMeasureUnit(bean);
        
    }
    
    /**
     * 查询数据
     * @param bean 名称
     * @return 审核错误类型
     */
    public MeasureUnitBean getMeasureUnit(MeasureUnitBean bean)
    {
        return measureUnitDAO.getMeasureUnit(bean);
    }
    
    @Autowired
    public void setMeasureUnitDAO(MeasureUnitDAO measureUnitDAO)
    {
        this.measureUnitDAO = measureUnitDAO;
    }
    
    @Override
    public boolean checkMeasureUnitCode(MeasureUnitBean bean)
    {
        MeasureUnitBean result = measureUnitDAO.checkMeasureUnitCode(bean);
        
        if (null == result)
        {
            return false;
        }
        return true;
    }
}
