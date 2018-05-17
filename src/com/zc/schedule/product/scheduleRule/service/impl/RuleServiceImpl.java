package com.zc.schedule.product.scheduleRule.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleRule.dao.CountDao;
import com.zc.schedule.product.scheduleRule.dao.GroupDao;
import com.zc.schedule.product.scheduleRule.entity.CountBean;
import com.zc.schedule.product.scheduleRule.entity.GroupBean;
import com.zc.schedule.product.scheduleRule.service.RuleService;
/**
 * 
 * 班次规则实现类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Repository(value = "ruleServiceImpl")
public class RuleServiceImpl implements RuleService
{
    
    @Resource(name = "countDao")
    private CountDao<CountBean> countDao;
    
    @Resource(name = "groupDao")
    private GroupDao<GroupBean> groupDao;
    
    @Override
    public List<CountBean> getWorkCountInfo(Map<String, Object> map)
    {
        return countDao.getWorkCountInfo(map);
    }


    @Override
    public Integer updateCount(CountBean countBean)
    {
        return countDao.updateCount(countBean);
    }


    @Override
    public List<GroupBean> getGroupInfo(Map<String, Object> map)
    {
        //获得分组的信息
        List<GroupBean> groupList = groupDao.getGroupInfo(map);
        
        //循环分组的信息
        if (null != groupList && groupList.size() > 0) 
        {
            for (GroupBean group : groupList) 
            {
                //查询分组与班次的关联信息
                List<GroupBean> workList = groupDao.getWorkInfo(group);
                
                group.setWorkList(workList);
            }
        }
        
        return groupList;
    }


    @Override
    public Integer updateGroup(GroupBean groupBean)
    {
        int count = 0;
        
        //1、删除原始的数据
        count = groupDao.deleteGroupWork(groupBean);
        
        //2、新增最新的数据
        String strWorkIds = groupBean.getWorkIds();
        if (!"".equals(strWorkIds)) 
        {
            String[] workIds = strWorkIds.split(",");
            //
            for (String workid : workIds)
            {
                GroupBean group = new GroupBean();
                group.setGroupId(groupBean.getGroupId());
                group.setWorkId(Integer.parseInt(workid));
                
                count = groupDao.addGroupWork(group);
            }
        }
        
        return count;
    }


    @Override
    public List<GroupBean> getWorkInfo(GroupBean groupBean)
    {
        List<GroupBean> workList = groupDao.getWorkInfo(groupBean);
        return workList;
    }

}
