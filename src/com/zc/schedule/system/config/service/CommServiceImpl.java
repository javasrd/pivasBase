package com.zc.schedule.system.config.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zc.schedule.system.config.dao.CommDao;
import com.zc.schedule.system.config.entity.Dict;
import com.zc.schedule.system.config.entity.ErrLog;
import com.zc.schedule.system.config.entity.Log;
import org.springframework.stereotype.Service;

/**
 * 公共服务层接口实现
 *
 * @author jagger
 * @version v1.0
 */
@Service("commService")
public class CommServiceImpl implements CommService {

    @Resource
    private CommDao commDao;

    @Override
    public List<Dict> qryDicByMap(Map<String, Object> map) {
        return commDao.qryDicByMap(map);
    }

    @Override
    public List<Dict> qryDicByCateS(String lang, String... categoryN) {
        if (categoryN != null && categoryN.length > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lang", lang);
            map.put("categoryN", categoryN);
            return qryDicByMap(map);
        }
        return null;
    }

    @Override
    public Map<String, List<Dict>> qryDicMapByCateS(String lang, String... categoryN) {
        Map<String, List<Dict>> map = new HashMap<String, List<Dict>>();
        if (categoryN != null && categoryN.length > 0) {
            List<Dict> dictList = qryDicByCateS(lang, categoryN);
            List<Dict> dictOneList = null;
            if (dictList != null && dictList.size() > 0) {
                for (Dict dict : dictList) {
                    if (map.get(dict.getCategory()) == null) {
                        dictOneList = new ArrayList<Dict>();
                        map.put(dict.getCategory(), dictOneList);
                    } else {
                        dictOneList = map.get(dict.getCategory());
                    }
                    dictOneList.add(dict);
                }
            }
        }
        return map;
    }

    @Override
    public void addLog(Log log) {
        commDao.addLog(log);
    }

    @Override
    public Integer addErrLog(ErrLog errLog) {
        return commDao.addErrLog(errLog);
    }
}