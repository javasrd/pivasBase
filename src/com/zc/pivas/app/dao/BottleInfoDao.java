package com.zc.pivas.app.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.pivas.app.bean.BottleInfo;

import java.util.List;

/**
 * 瓶签信息dao
 *
 * @author kunkka
 * @version 1.0
 */
@MyBatisRepository("BottleInfoDao")
public interface BottleInfoDao {
    List<BottleInfo> queryBottleInfo(BottleInfo bottleInfo);


}
