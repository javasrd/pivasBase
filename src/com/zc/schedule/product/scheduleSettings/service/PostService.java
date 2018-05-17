package com.zc.schedule.product.scheduleSettings.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zc.schedule.product.scheduleSettings.entity.PostBean;
/**
 * 
 * 岗位设置接口
 * 
 * @author  Justin
 * @version  v1.0
 */
@Repository(value = "postService")
public interface PostService
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
     * 
     * @param postId
     * @return Integer
     */
    Integer delPostSettings(String postId);
    
    
    /**
     * 
     * 验证岗位是否存在排班数据和排班历史数据中
     * @param postId
     * @return Boolean
     */
    Boolean checkPostIsExitst(String postId);
}
