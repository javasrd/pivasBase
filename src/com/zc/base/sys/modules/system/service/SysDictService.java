package com.zc.base.sys.modules.system.service;

import com.zc.base.sys.modules.system.entity.SysDict;

import java.util.List;
import java.util.Map;

public abstract interface SysDictService {
    public abstract Map<String, List<SysDict>> getAllBLADict() throws Exception;
}
