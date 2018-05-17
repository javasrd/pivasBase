package com.zc.pivas.wardgroup.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.wardgroup.bean.WardGroupBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 病区分组Service类
 *
 * @author kunkka
 * @version 1.0
 */
@Repository(value = "wardGroupService")
public interface WardGroupService {

    /**
     * 获取分组数据
     *
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    JqueryStylePagingResults<WardGroupBean> getGroupLsit(JqueryStylePaging jquryStylePaging) throws Exception;

    /**
     * 获取病区数据
     *
     * @param wardId
     * @return
     */
    List<WardGroupBean> getWardList(String wardId);

    int checkParam(String type, String value);

    /**
     * 保存分组
     *
     * @param wardBean
     */
    void saveGroup(WardGroupBean wardBean);

    void delWardGroup(String[] idArray);

    /**
     * 更新病区数据
     *
     * @param parentid
     * @param wardIdArray
     * @param groupOrder
     */
    void updateWardGroup(String parentid, String[] wardIdArray, String groupOrder);

    void saveSubWard(String id, String[] wardIdArray);


}
