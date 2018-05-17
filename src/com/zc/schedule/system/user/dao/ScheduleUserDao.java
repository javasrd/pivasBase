package com.zc.schedule.system.user.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.base.NormalDao;

/**
 * 人员持久层
 *
 * @author jagger
 * @version v1.0
 */
@MyBatisRepository
public interface ScheduleUserDao<User> extends NormalDao<User> {//PageDao

}
