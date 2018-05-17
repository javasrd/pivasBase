package com.zc.base.sys.modules.system.service.impl;

import com.zc.base.sys.modules.system.entity.SysDict;
import com.zc.base.sys.modules.system.repository.SysDictDao;
import com.zc.base.sys.modules.system.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("blaDictService")
public class SysDictServiceImpl implements SysDictService {
    @Resource
    private SysDictDao blaDictDao;

    public Map<String, List<SysDict>> getAllBLADict()
            throws Exception {
        Map<String, List<SysDict>> map = null;

        List<SysDict> list = this.blaDictDao.getAllBLADict();

        if ((list != null) && (list.size() > 0)) {

            map = new HashMap();

            SysDict dict = null;
            List<SysDict> sub = null;
            String lang = null;
            for (int i = 0; i < list.size(); i++) {

                dict = (SysDict) list.get(i);

                lang = dict.getLang();

                if (map.keySet().contains(lang)) {

                    sub = map.get(lang);
                    sub.add(dict);
                    map.put(lang, sub);

                } else {
                    sub = new ArrayList();
                    sub.add(dict);
                    map.put(lang, sub);
                }
            }
        }

        return map;
    }
}
