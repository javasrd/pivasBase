package com.zc.pivas.excuteRecord.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import com.zc.pivas.excuteRecord.dao.SynYdRecordDAO;
import com.zc.pivas.excuteRecord.service.SynYdRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 药单执行记录
 * <br> 
 * @author  cacabin
 * @version  0.1
 */
@Service("synYdRecordService")
public class SynYdRecordServiceImpl implements SynYdRecordService
{
    
    @Resource
    private SynYdRecordDAO synYdRecordDAO;
    
    @Override
    public void synData(ExcuteRecordBean bean)
    {
        synYdRecordDAO.synData(bean);
    }
    
    /**
     * 根据用药时间匹配用用药批次
     *
     */
    public String getBatchID(String useTime)
    {
        return synYdRecordDAO.getBatchID(useTime);
    }
    
    @Override
    public List<ExcuteRecordBean> getRecordAllDay(String dayNowS, String parentNo)
    {
        return synYdRecordDAO.getRecordAllDay(dayNowS, parentNo);
    }
    
    @Override
    public List<ExcuteRecordBean> getExcuteRecordBean(String parentNo, String actOrderNo)
    {
        return synYdRecordDAO.getExcuteRecordBean(parentNo, actOrderNo);
    }
    
    @Override
    public List<ExcuteRecordBean> getRecordByPC(String dayNow, String parentNo, String pcID)
    {
        return synYdRecordDAO.getRecordByPC(dayNow, parentNo, pcID);
    }
    
    @Override
    public List<ExcuteRecordBean> getRecordSplit(String dayNow, String parentNo)
    {
        return synYdRecordDAO.getRecordSplit(dayNow, parentNo);
    }
    
    @Override
    public List<ExcuteRecordBean> getRecordSplitExistYy(String dayNow, String parentNo)
    {
        return synYdRecordDAO.getRecordSplitExistYy(dayNow, parentNo);
    }
    
    @Override
    public List<ExcuteRecordBean> getRecordYyDetail(String dayNow, String parentNo)
    {
        return synYdRecordDAO.getRecordYyDetail(dayNow, parentNo);
    }
    
    @Override
    public List<ExcuteRecordBean> getExcuteRecordBeanByPC(String parentNo, String actOrderNo, Long pc_id, Date yyrq)
    {
        return synYdRecordDAO.getExcuteRecordBeanByPC(parentNo, actOrderNo, pc_id, yyrq);
    }
    
    @Override
    public void checkYdRefund(Map<String, Object> map)
    {
        // 检索不合理的药单
        List<ExcuteRecordBean> excuteRecordList = synYdRecordDAO.checkYdRefund(map);
        
        if (CollectionUtils.isNotEmpty(excuteRecordList))
        {
            for (ExcuteRecordBean bean : excuteRecordList)
            {
                synYdRecordDAO.insertYdRefund(bean);
            }
        }
    }
    
    @Override
    public JqueryStylePagingResults<ExcuteRecordBean> queryYdrefundList(Map<String, Object> map,
        JqueryStylePaging jquryStylePaging)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        String[] columns = new String[] {"id"};
        
        List<ExcuteRecordBean> list = synYdRecordDAO.queryYdrefundList(map, jquryStylePaging);
        
        int rowCount = synYdRecordDAO.queryYdrefundTotal(map);
        JqueryStylePagingResults<ExcuteRecordBean> pagingResults = new JqueryStylePagingResults<ExcuteRecordBean>(columns);
        pagingResults.setDataRows(list);
        pagingResults.setTotal(rowCount);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    @Override
    public void processYdRefund(ExcuteRecordBean bean)
    {
        synYdRecordDAO.processYdRefund(bean);
    }
}
