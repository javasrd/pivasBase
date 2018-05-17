package com.zc.schedule.product.scheduleSettings.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleSettings.dao.PostDao;
import com.zc.schedule.product.scheduleSettings.entity.PostBean;
import com.zc.schedule.product.scheduleSettings.service.PostService;

/**
 * 
 * 岗位设置实现类
 * 
 * @author  v1.0
 * @version  v1.0
 */
@Repository(value = "postServiceImpl")
public class PostServiceImpl implements PostService
{
    @Resource(name = "postDao")
    private  PostDao<PostBean> postDao;
    
    @Override
    public List<PostBean> getPostSettingsList(Map<String,Object> map)
    {
        return postDao.getPostSettingsList(map);
    }
    
    @Override
    public PostBean getPostById(Integer postId) 
    {
        return postDao.getPostById(postId);
    }
    
    @Override
    public Integer addPostSettings(PostBean postBean)
    {
        return postDao.addPostSettings(postBean);
    }
    
    @Override
    public Integer updatePostSettings(PostBean postBean)
    {
        return postDao.updatePostSettings(postBean);
    }
    
    @Override
    public Integer delPostSettings(String postId)
    {
        //
        String[] postIds = postId.split(",");
        int count = 0;
        //删除
        for (String postid : postIds)
        {
            postDao.delPostSettings(Integer.parseInt(postid));
            count ++;
        }
        return count;
    }
    
    @Override
    public Integer checkPostNameIsExitst(String postName)
    {
        return postDao.checkPostNameIsExitst(postName);
    }

    @Override
    public Boolean checkPostIsExitst(String postId)
    {
        boolean flag = true;
        
        //将postId转换为数组
        String[] postIds = postId.split(",");
        
        //获得岗位存在个数
        Integer scheduleCount = postDao.getScheduleCount(postIds);
        Integer scheduleHistoryCount = postDao.getScheduleHistoryCount(postIds);
        
        if (scheduleCount > 0 || scheduleHistoryCount > 0) 
        {
            //存在
            flag = false;
        }
        return flag;
    }
    
}
