package com.zc.schedule.product.scheduleSettings.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zc.schedule.common.entity.PageInfo;
import com.zc.schedule.product.scheduleSettings.dao.WorkDao;
import com.zc.schedule.product.scheduleSettings.entity.WorkBean;
import com.zc.schedule.product.scheduleSettings.service.WorkService;
/**
 * 
 * 班次设置实现类
 * 
 * @author  Justin
 * @version  v1.0
 */
@Repository(value = "workServiceImpl")
public class WorkServiceImpl implements WorkService
{
    @Resource(name = "workDao")
    private WorkDao<WorkBean> workDao;
    
    @Override
    public List<WorkBean> getWorkInfoList(Map<String, Object> map, PageInfo page)
    {
        return workDao.getWorkInfoList(map, page);
    }
    
    @Override
    public WorkBean getWorkById(Integer workId)
    {
        return workDao.getWorkById(workId);
    }
    
    @Override
    public Integer checkWorkNameIsExitst(String workName)
    {
        return workDao.checkWorkNameIsExitst(workName);
    }
    
    @Override
    public Integer addWorkInfo(WorkBean workBean)
    {
        workBean.setStatus("1");
        workBean.setWorkStatus("1");
        return workDao.addWorkInfo(workBean);
    }
    
    @Override
    public Integer updateWorkInfo(WorkBean workBean, WorkBean oldworkBean)
    {
        int row = 0;
        /** 修改操作service层处理 */
        //事务回滚 开始
        
        //备份原先的数据 -- 新增操作
        WorkBean newWorkBean = new WorkBean(); 
        newWorkBean.setCopyId(oldworkBean.getWorkId());
        newWorkBean.setWorkName(oldworkBean.getWorkName());
        newWorkBean.setWorkType(oldworkBean.getWorkType());
        newWorkBean.setTotalTime(oldworkBean.getTotalTime());
        newWorkBean.setTimeInterval(oldworkBean.getTimeInterval());
        newWorkBean.setWorkColor(oldworkBean.getWorkColor());
        newWorkBean.setWorkStatus(oldworkBean.getWorkStatus());
        newWorkBean.setStatus(oldworkBean.getStatus());
        newWorkBean.setCount(oldworkBean.getCount());
        newWorkBean.setCreater(oldworkBean.getCreater());
        newWorkBean.setCreationTime(oldworkBean.getCreationTime());
        newWorkBean.setSplitWork(oldworkBean.getSplitWork());
        
        int count = workDao.addNewWorkInfo(newWorkBean);
        
        //修改原先数据的状态
        workDao.updateOldWorkInfo(oldworkBean);
        
        //获得新的数据 -- 根据copyid获取新的班次信息
        if (count > 0) {
            
//            WorkBean newWork = new WorkBean();
            WorkBean newWork = workDao.getWorkInfoByCopyId(oldworkBean.getWorkId());
            
            //再修改新的班次信息
            workBean.setWorkId(newWork.getWorkId());
            row = workDao.updateWorkInfo(workBean);
            
            //旧的workId
            workBean.setOldWorkId(oldworkBean.getWorkId());
            
            //修改分组班次关联信息
            workDao.changeGroupInfo(workBean);
            
            //修改一周排班表中关联信息
            //workDao.changeDataInfo(workBean);
            
        }
        //事务回滚 结束
        
        return row;
    }
    
    @Override
    public Integer delWorkInfo(String workId)
    {
        //未使用的班次，需查看该班次的历史信息,若未使用，则删除
        //删除分组班次关联信息
        //
        String[] workIds = workId.split(",");
        int count = 0;
        //删除
        for (String workid : workIds)
        {
            
            //未使用的班次，需查看该班次的历史信息,若未使用，则删除
            //1、根据子节点获取父节点，得到父节点
            List<WorkBean> list = workDao.getOldWorkIds(Integer.parseInt(workid));
            int num = 0;
            
            //2、判断父节点是否被使用
            if (null != list && list.size() > 0) 
            {
                String[] strWorkIds = new String[list.size()];
                
                //存在历史信息
                for (int i = 0 ; i < list.size(); i++) 
                {
                    WorkBean work = list.get(i);
                    strWorkIds[i] = String.valueOf(work.getWorkId());
                }
                
                num = workDao.getScheduleHistoryCount(strWorkIds);
                
                //判断历史班次是否存在历史排班数据中
                if (0 == num) 
                {
                    //不存在
                    //3、删除父节点的班次信息 for循环
                    for (int i = 0; i < strWorkIds.length; i++) 
                    {
                        workDao.delWorkInfo(Integer.parseInt(strWorkIds[i]));
                    }
                }
            }
            
            //删除分组与班次的关联关系
            workDao.deleteGroupInfo(Integer.parseInt(workid));
            
            //删除未排班的班次信息
            workDao.delWorkInfo(Integer.parseInt(workid));
            count ++;
            
        }
        return count;
    }
    
    @Override
    public Boolean checkWorkIsExitst(String workId)
    {
        boolean flag = true;
        
        //将workId转换为数组
        String[] workIds = workId.split(",");
        
        //获得岗位存在个数
        Integer scheduleCount = workDao.getScheduleCount(workIds);
        Integer scheduleHistoryCount = workDao.getScheduleHistoryCount(workIds);
        
        if (scheduleCount > 0 || scheduleHistoryCount > 0) 
        {
            //存在
            flag = false;
        }
        return flag;
    }

    @Override
    public Integer changeWorkStatus(WorkBean workBean)
    {
        return workDao.changeWorkStatus(workBean);
    }

    @Override
    public List<WorkBean> queryOldWorkInfo(Map<String, Object> map, PageInfo page)
    {
        return workDao.queryOldWorkInfo(map, page);
    }

    @Override
    public Integer changeWorkColor(WorkBean workBean)
    {
        return workDao.changeWorkColor(workBean);
    }
    
}
