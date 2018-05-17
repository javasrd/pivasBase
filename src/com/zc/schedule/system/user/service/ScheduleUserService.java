package com.zc.schedule.system.user.service;

import com.zc.schedule.system.user.dao.ScheduleUserDao;
import org.springframework.stereotype.Repository;

/**
 * 人员管理服务接口
 *
 * @author jagger
 * @version v1.0
 */
@Repository(value = "userService")
public interface ScheduleUserService<User> extends ScheduleUserDao<User> {

}
