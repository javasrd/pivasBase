package com.zc.schedule.product.personnelgroup.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.zc.schedule.common.base.BaseLogService;
import com.zc.schedule.common.entity.ResultData;
import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SpringUtil;
import com.zc.schedule.common.util.DateUtils;
import com.zc.schedule.common.util.SysConstant;
import com.zc.schedule.product.manager.entity.ScheduleBean;
import com.zc.schedule.product.personnel.entity.Personnel;
import com.zc.schedule.product.personnelgroup.dao.PersonnelGroupDao;
import com.zc.schedule.product.personnelgroup.entity.GroupInfo;
import com.zc.schedule.product.personnelgroup.service.PersonnelGroupService;

@Service("personnelGroupService")
public class PersonnelGroupServiceImpl extends BaseLogService implements PersonnelGroupService
{
    
    /**
     * 人员分组数据交互接口
     */
    @Resource
    private PersonnelGroupDao personnelGroupDao;
    
    @Override
    public ResultData getGroupInfos(String startDate)
        throws ParseException
    {
        List<GroupInfo> groups = null;
        
        if (StringUtils.isBlank(startDate))
        {
            startDate = DateUtils.getMondayOfThisWeek();
        }
        
        boolean queryHis = DateUtils.stringToDate(startDate, DateUtils.YYYYMMDD)
            .before(DateUtils.stringToDate(DateUtils.getMondayOfThisWeek(), DateUtils.YYYYMMDD));
        
        //查询当前周的分组信息
        groups = personnelGroupDao.getGroups(startDate);
        if (groups == null || groups.size() == 0)
        {
            //查询最近的历史分组信息的开始和结束时间
            Map<String, String> dateMap = personnelGroupDao.getLatestValidityDate(startDate);
            startDate = dateMap == null ? null : dateMap.get("VALIDITY_START");
            //endDate = dateMap.get("VALIDITY_END");
        }
        
        List<Personnel> persons = null;
        if (StringUtils.isNotBlank(startDate))//查询对应的当周或者历史数据
        {
            groups = personnelGroupDao.getGroups(startDate);
            
            //查询分组下的人员信息
            for (GroupInfo group : groups)
            {
                persons = personnelGroupDao.getPersonnels(group.getGroupId(), startDate);
                group.setPersonnelList(persons);
            }
        }
        else //没有分组信息
        {
            Map<String, String> dateMap = personnelGroupDao.getLatestValidityDate("9999-12-31");
            startDate = dateMap == null ? null : dateMap.get("VALIDITY_START");
            
            if (queryHis || StringUtils.isNotEmpty(startDate))//历史数据不存在或者存在未来的分组关联信息
            {
                groups = new ArrayList<GroupInfo>();
            }
            else
            {
                //查询所有的分组
                groups = personnelGroupDao.getAllGroups();
                if (groups == null)
                {
                    groups = new ArrayList<GroupInfo>();
                }
            }
        }
        
        if (!queryHis)
        {
            //查询没有参与分组的人员
            persons = personnelGroupDao.getDefaultPersonnels(startDate);
            
            //创建待分配分组
            GroupInfo defaultGroup = new GroupInfo();
            defaultGroup.setGroupId(0l);
            defaultGroup.setGroupName("待分配人员");
            defaultGroup.setPersonnelList(persons);
            groups.add(0, defaultGroup);
        }
        
        return DataFormat.formatSucc(groups);
    }
    
    @Override
    public ResultData saveGroup(String opr, Long groupId, String groupName, String startDate, String endDate)
    {
        String oprMsg = "add".equals(opr) ? "添加分组" : "修改分组";
        if (StringUtils.isEmpty(startDate))
        {
            //获取本周的周一
            startDate = DateUtils.getMondayOfThisWeek();
            endDate = DateUtils.getSundayOfThisWeek();
        }
        
        //复制下以前周的分组信息
        this.copyGroup(startDate, endDate);
        
        //判断本周内分组名称是否重复
        int count = personnelGroupDao.getGroupCount(groupId, groupName, startDate);
        if (count > 0)
        {
            addLogFail(SpringUtil.getRequest(),
                SysConstant.SCHEDULEMGR,
                SysConstant.人员分组,
                oprMsg + "[名称=" + groupName + "]重复");
            return DataFormat.formatAdd(-1, "分组名称重复！");
        }
        
        GroupInfo group = new GroupInfo();
        if ("add".equals(opr))//添加新的分组
        {
            group.setGroupName(groupName);
            group.setCreater(getSessionUserId());
            group.setValidityStart(startDate);
            group.setValidityEnd(endDate);
            personnelGroupDao.saveGroup(group);//入分组表
            personnelGroupDao.saveGroupInfo(group);//入分组人员关联表
        }
        else//修改分组名称
        {
            personnelGroupDao.updateGroupName(groupId, groupName);
        }
        
        addLogSucc(SpringUtil.getRequest(),
            SysConstant.SCHEDULEMGR,
            SysConstant.人员分组,
            oprMsg + "[名称=" + groupName + "]");
        
        return DataFormat.formatAdd(1, "保存成功！", group.getGroupId());
    }
    
    /**
     * 复制分组信息
     * 
     * @param startDate
     * @param endDate
     */
    private void copyGroup(String startDate, String endDate)
    {
        //查询最近的分组信息的开始和结束时间
        Map<String, String> dateMap = personnelGroupDao.getLatestValidityDate(startDate);
        String lastStartDate = dateMap == null ? null : dateMap.get("VALIDITY_START");
        String lastEndDate = dateMap == null ? null : dateMap.get("VALIDITY_END");
        copyGroup(lastStartDate, lastEndDate, startDate, endDate);
    }
    
    @Override
    public void copyGroup(String lastStartDate, String lastEndDate, String startDate, String endDate)
    {
        //查询是否已经存在人员分组关系
        List<GroupInfo> groups = personnelGroupDao.getGroups(startDate);
        if (groups == null || groups.size() == 0) //不存在分组信息则复制
        {
            if (StringUtils.isNotEmpty(lastStartDate))
            {
                groups = personnelGroupDao.getGroupInfos(lastStartDate);
                if (groups != null && groups.size() > 0) //有数据，如果没有则不进行任何操作
                {
                    for (GroupInfo group : groups)
                    {
                        group.setValidityStart(startDate);
                        group.setValidityEnd(endDate);
                        personnelGroupDao.saveGroupInfo2(group);
                    }
                }
            }
        }
    }
    
    @Override
    public ResultData saveGroupInfos(String startDate, String endDate, String datas)
    {
        //删除旧的人员分组信息
        personnelGroupDao.removeAll(startDate);
        
        if (StringUtils.isNotEmpty(datas))
        {
            Long groupId = null;
            String userIds = null;
            Long userId = null;
            GroupInfo group = null;
            
            Map<Long, Long> refNew = new HashMap<Long, Long>();
            
            int index = 1;
            for (String data : datas.split("@"))
            {
                groupId = Long.valueOf(data.split(":")[0]);
                //更新分组排序
                personnelGroupDao.updateGroupSort(groupId, index);
                
                userIds = data.split(":").length > 1 ? data.split(":")[1] : null;
                
                int index2 = 1;
                
                group = new GroupInfo();
                group.setGroupId(groupId);
                group.setValidityStart(startDate);
                group.setValidityEnd(endDate);
                group.setSort(index);
                
                if (StringUtils.isNotEmpty(userIds))
                {
                    for (String s : userIds.split(","))
                    {
                        userId = NumberUtils.toLong(s);
                        if (userId != null && userId > 0)
                        {
                            group.setUserId(userId);
                            group.setSort(index2);
                            personnelGroupDao.saveGroupInfo3(group);
                            
                            refNew.put(userId, groupId);
                        }
                        index2++;
                    }
                }
                else
                {
                    personnelGroupDao.saveGroupInfo(group);
                }
                
                index++;
            }
            
            //更新排版数据
            List<ScheduleBean> list = personnelGroupDao.getScheduleDataByStartDate(startDate);
            Long groupIdNew = null;
            if (list != null && list.size() > 0)
            {
                for (ScheduleBean data : list)
                {
                    groupIdNew = refNew.get(Long.valueOf(data.getUser_id()));
                    if (groupIdNew == null || groupIdNew < 1)//该人员已不参与排班，删除该条记录
                    {
                        personnelGroupDao.delScheduleData(data.getUser_id(), startDate);
                    }
                    else if (groupIdNew.longValue() != data.getGroupId().longValue()) //更新人员所属分组
                    {
                        personnelGroupDao.updateScheduleData(data.getUser_id(), startDate, groupIdNew);
                    }
                }
            }
        }
        else
        {
            //删除排班数据
            personnelGroupDao.delScheduleDataByStartDate(startDate);
        }
        
        addLogSucc(SpringUtil.getRequest(), SysConstant.SCHEDULEMGR, SysConstant.人员分组, "修改分组数据成功");
        
        return DataFormat.formatSucc("保存成功！", null);
    }
    
    @Override
    public ResultData del(Long groupId, String startDate, String endDate)
    {
        GroupInfo group = personnelGroupDao.getById(groupId);
        
        //移除指定周中的分组信息
        personnelGroupDao.deleteGroupInfoByGroupIdAndStartDate(groupId, startDate);
        
        //从分组表中删除该分组，如果该分组没有被关联
        personnelGroupDao.delGroupByGroupId(groupId);
        
        //删除与班次关联的信息
        personnelGroupDao.delWorkRefDataByGroupId(groupId);
        
        addLogSucc(SpringUtil.getRequest(),
            SysConstant.SCHEDULEMGR,
            SysConstant.人员分组,
            "删除分组[名称=" + group.getGroupName() + "]");
        
        return DataFormat.formatSucc("删除成功！", null);
    }
    
}
