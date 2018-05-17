package com.zc.schedule.system.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zc.schedule.system.user.dao.ScheduleUserDao;
import org.springframework.stereotype.Repository;

import com.zc.schedule.common.entity.PageInfo;

/**
 * 人员管理服务实现
 *
 * @author jagger
 * @version v1.0
 */
@SuppressWarnings("hiding")
@Repository(value = "userServiceImpl")
public class ScheduleUserServiceImpl<User> implements ScheduleUserService<User> {

    @Resource
    private ScheduleUserDao<User> scheduleUserDao;

    @Override
    public List<User> qryList(Map<String, Object> map, PageInfo page) {
        return scheduleUserDao.qryList(map, page);
    }

    @Override
    public List<User> qryList(Map<String, Object> map) {
        return scheduleUserDao.qryList(map);
    }

    @Override
    public Integer insert(User t) {
        return scheduleUserDao.insert(t);
    }

    @Override
    public Integer update(Map<String, Object> t) {
        return scheduleUserDao.update(t);
    }

    @Override
    public Integer delete(Map<String, Object> t) {
        return scheduleUserDao.delete(t);
    }
    
    /*

	@Override
	public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
		return SUserDao.queryListMap(map);
	}

	@Override
	public Integer queryCount(Map<String, Object> map) {
		return SUserDao.queryCount(map);
	}
    */
}
