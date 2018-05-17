package com.zc.schedule.system.config.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zc.schedule.system.config.dao.CommDao;
import com.zc.schedule.system.config.entity.Dict;
/**
 * 公共服务层接口
 *
 * @author jagger
 * @version v1.0
 */
@Repository(value = "commService")
public interface CommService extends CommDao{
	
	/**
	 * 根据语言和字典分类 获取字典list
	 * @param lang
	 * @param categoryN
	 * @return List
	 */
	List<Dict> qryDicByCateS(String lang,String... categoryN);
	
	/**
	 * 根据语言和字典分类 获取字典Map
	 * @param lang
	 * @param categoryN
	 * @return  Map
	 */
    Map<String,List<Dict>> qryDicMapByCateS(String lang,String... categoryN);
    
    
}
