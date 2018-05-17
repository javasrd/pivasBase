package com.zc.pivas.wardgroup.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.wardgroup.bean.WardGroupBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 病区分组DAO类
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("wardGroupDao")
public interface WardGroupDao {

    /**
     * 获取分组数据
     *
     * @param jquryStylePaging
     * @return
     */
    List<WardGroupBean> getGroupList(@Param("paging") JqueryStylePaging jquryStylePaging);

    int getGroupListTotal();

    List<WardGroupBean> getSubGroupName(@Param("id") String id);

    /**
     * 获取病区数据
     *
     * @param wardId
     * @return
     */
    List<WardGroupBean> getWardList(@Param("id") String wardId);

    int checkParam(@Param("type") String type, @Param("value") String value);

    void saveGroup(WardGroupBean wardBean);

    void saveSubWard(@Param("parentid") String id, @Param("ids") String[] wardIdArray);

    void delSubWard(@Param("ids") String[] idArray);

    void delWardGroup(@Param("ids") String[] idArray);

    void updateWardGroup(@Param("id") String parentid, @Param("order") String groupOrder);

    void updateSubWardById(@Param("parentid") String parentid);

}
