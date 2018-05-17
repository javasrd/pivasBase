package com.zc.schedule.product.scheduleSettings.dao;

import java.util.List;
import java.util.Map;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.base.NormalDao;

@MyBatisRepository(value = "postDao")
public interface PostDao<PostBean> extends NormalDao<PostBean>
{
    /**
     * 
     * 查询所有岗位信息
     * @return List
     */
    List<PostBean> getPostSettingsList(Map<String,Object> map);
    
    /**
     * 查询单个岗位信息
     * 
     * @param postId
     * @return PostBean
     */
    PostBean getPostById(Integer postId);
    
    /**
     * 
     * 判断岗位名称是否重复
     * 
     * @param postName
     * @return Integer
     */
    Integer checkPostNameIsExitst(String postName);
    
    /**
     * 新增岗位信息
     * 
     * @param postBean
     * @return Integer
     */
    Integer addPostSettings(PostBean postBean);

    
    /**
     * 
     * 修改岗位信息
     * @param postBean
     * @return Integer
     */
    Integer updatePostSettings(PostBean postBean);
    
    
    /**
     * 删除岗位信息
     * @param postId
     * @return Integer
     */
    Integer delPostSettings(Integer postId);
    
    /** 删除岗位信息之前需判断该岗位是否已被使用 */
    
    /**
     * 
     * 判断岗位是否存在排班数据中
     * @param postIds
     * @return Integer
     */
    Integer getScheduleCount(String[] postIds);
    
    
    /**
     * 
     * 判断岗位是否存在排班历史数据中
     * @param postIds
     * @return Integer
     */
    Integer getScheduleHistoryCount(String[] postIds);
}
