package com.zc.pivas.wardgroup.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.wardgroup.bean.WardGroupBean;
import com.zc.pivas.wardgroup.dao.WardGroupDao;
import com.zc.pivas.wardgroup.service.WardGroupService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 病区分组业务实现类
 *
 * @author kunkka
 * @version 1.0
 */
@Repository(value = "wardGroupServiceImpl")
public class WardGroupServiceImpl implements WardGroupService {
    @Resource
    private WardGroupDao wardGroupDao;

    /**
     * 获取分组数据
     *
     * @param jquryStylePaging
     * @return
     * @throws Exception
     */
    @Override
    public JqueryStylePagingResults<WardGroupBean> getGroupLsit(JqueryStylePaging jquryStylePaging) throws Exception {

        //用于和页面显示顺序一样
        String[] columns =
                new String[]{"id", "deptcode", "deptname", "ordernum", "wardgroup"};
        JqueryStylePagingResults<WardGroupBean> pagingResults = new JqueryStylePagingResults<WardGroupBean>(columns);


        List<WardGroupBean> groupList = wardGroupDao.getGroupList(jquryStylePaging);

        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(groupList) && jquryStylePaging.getPage() != 1) {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            groupList = wardGroupDao.getGroupList(jquryStylePaging);
        }


        List<WardGroupBean> nameList = null;
        for (WardGroupBean group : groupList) {

            String id = group.getId();
            nameList = wardGroupDao.getSubGroupName(id);
            int size = nameList.size();
            if (size > 1) {

                String num = nameList.get(0).getWardgroup();
                String name = nameList.get(1).getWardgroup();
                int count = Integer.parseInt(num);
                if (count > 3) {
                    name = name + ",...";
                }
                if (!"empty".equals(name)) {
                    group.setWardgroup(name);
                }
            }
        }

        // 总数
        int total = wardGroupDao.getGroupListTotal();

        pagingResults.setDataRows(groupList);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }

    /**
     * 获取病区数据
     *
     * @param wardId
     * @return
     */
    @Override
    public List<WardGroupBean> getWardList(String wardId) {
        return wardGroupDao.getWardList(wardId);
    }

    @Override
    public int checkParam(String type, String value) {
        return wardGroupDao.checkParam(type, value);
    }

    @Override
    public void saveGroup(WardGroupBean wardBean) {
        wardGroupDao.saveGroup(wardBean);
    }

    @Override
    public void saveSubWard(String id, String[] wardIdArray) {
        wardGroupDao.saveSubWard(id, wardIdArray);
    }

    @Override
    public void delWardGroup(String[] idArray) {
        wardGroupDao.delSubWard(idArray);
        wardGroupDao.delWardGroup(idArray);
    }

    /**
     * 更新病区数据
     *
     * @param parentid
     * @param wardIdArray
     * @param groupOrder
     */
    @Override
    public void updateWardGroup(String parentid, String[] wardIdArray, String groupOrder) {
        //更新分组优先级
        wardGroupDao.updateWardGroup(parentid, groupOrder);
        //重置该分组下的病区关联
        wardGroupDao.updateSubWardById(parentid);
        //设置新的子病区
        if (wardIdArray.length > 0) {
            wardGroupDao.saveSubWard(parentid, wardIdArray);
        }

    }

}
