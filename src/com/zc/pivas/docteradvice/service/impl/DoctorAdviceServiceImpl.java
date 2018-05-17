package com.zc.pivas.docteradvice.service.impl;

import com.zc.base.common.util.StrUtil;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.repository.DoctorAdviceDao;
import com.zc.pivas.docteradvice.service.DoctorAdviceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 医嘱service实现类
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
@Service("yzService")
public class DoctorAdviceServiceImpl implements DoctorAdviceService

{
    @Resource
    private DoctorAdviceDao yzDao;
    
    /**
     * 
     * 查询医嘱列表
     * @param map
     * @param jquryStylePaging
     * @return
     */
    @Override
    public List<DoctorAdvice> qryListBeanByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return yzDao.qryListBeanByMap(map, jquryStylePaging);
    }
    
    /**
     * 
     * 查询医嘱总数
     * @param map
     * @return
     */
    @Override
    public Integer qryCountByMap(Map<String, Object> map)
    {
        return yzDao.qryCountByMap(map);
    }
    
    /**
     * 
     * 根据医嘱唯一标识查询医嘱信息
     * @param pidsj
     * @return
     */
    
    @Override
    public List<DoctorAdvice> qryBeanByPidsj(String pidsj)
    {
        if (StrUtil.getObjStr(pidsj) != null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pidsj", pidsj);
            return yzDao.qryListBeanByMap(map, new JqueryStylePaging());
        }
        return null;
    }
    
    /**
     * 
     * 根据医嘱唯一标识查询医嘱信息
     * @param pidsj
     * @return
     */
    @Override
    public List<DoctorAdvice> qryBeanDistByPidsj(String pidsj, Integer yzzt)
    {
        List<DoctorAdvice> yzList = new ArrayList<DoctorAdvice>();
        if (StrUtil.getObjStr(pidsj) != null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pidsj", pidsj);
            map.put("leftWithMediCati", 1);
            if(null != yzzt)
            {
                map.put("yzzt", yzzt);
            }
            List<DoctorAdvice> yzList1 = yzDao.qryListBeanByMap(map, new JqueryStylePaging());
            
            Map<String, Integer> actOrderNoMap = new HashMap<String, Integer>();
            if (yzList1 != null && yzList1.size() > 0)
            {
                for (int i = 0; i < yzList1.size(); i++)
                {
                    if (actOrderNoMap.get(yzList1.get(i).getActOrderNo()) == null)
                    {
                        yzList.add(yzList1.get(i));
                        actOrderNoMap.put(yzList1.get(i).getActOrderNo(), 1);
                    }
                }
            }
        }
        return yzList;
    }
    
    /**
     * 
     * 
     * 根据条件查询子医嘱
     * @param map 查询条件
     * @return 医嘱信息
     */
    @Override
    public DoctorAdvice getYzByCondition(Map<String, Object> map)
    {
        return yzDao.getYzByCondition(map);
    }
    
    /**
     * 
     * 增加医嘱子表数据
     * @param addMap
     */
    @Override
    public void addYZ(Map<String, Object> addMap)
    {
        yzDao.addYZ(addMap);
        
    }
    
    /**
     * 
     * 修改子医嘱表数据
     * @param updateMap
     */
    @Override
    public void updateYZ(Map<String, Object> updateMap)
    {
        yzDao.updateYZ(updateMap);
    }
    
    /**
     * 
     * 增加医嘱子表异常（同步）数据
     * @param addMap
     */
    @Override
    public void addYZExceptionData(Map<String, Object> addMap)
    {
        yzDao.addYZExceptionData(addMap);
    }
    
    /**
     * 
     * 查询未复制的子医嘱
     * @param map
     * @return
     */
    @Override
    public List<DoctorAdvice> qryListBeanNotCopyByMap(Map<String, Object> map)
    {
        return yzDao.qryListBeanNotCopyByMap(map);
    }
    
    /**
     * 
     * 查询未复制的子医嘱
     * @param map
     * @return
     */
    @Override
    public List<DoctorAdvice> qryListBeanNotCopyInMain(@Param("qry")
    Map<String, Object> map)
    {
        return yzDao.qryListBeanNotCopyInMain(map);
    }
    
    /**
     * 
     * 修改医嘱状态
     * @param yzMap
     */
    @Override
    public void changeYzStatus(Map<String, Object> yzMap)
    {
        yzDao.changeYzStatus(yzMap);
    }
    
    /**
     * 
     * 重置医嘱审核状态
     * @param updateMap
     */
    @Override
    public void resetYZSH(Map<String, Object> updateMap)
    {
        yzDao.resetYZSH(updateMap);
    }
    
}
