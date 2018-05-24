package com.zc.pivas.docteradvice.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 医嘱DAO
 *
 * @author cacabin
 * @version 1.0
 */
@MyBatisRepository("yzDao")
public interface DoctorAdviceDao {

	/**
	 * 
	 * 查询医嘱列表
	 * 
	 * @param map
	 * @param jquryStylePaging
	 * @return
	 */
	List<DoctorAdvice> qryListBeanByMap(@Param("qry") Map<String, Object> map,
			@Param("paging") JqueryStylePaging jquryStylePaging);

	/**
	 * 
	 * 查询医嘱总数
	 * 
	 * @param map
	 * @return
	 */
	Integer qryCountByMap(@Param("qry") Map<String, Object> map);

	/**
	 * 
	 * 根据条件查询子医嘱
	 * 
	 * @param map
	 * @return
	 */
	DoctorAdvice getYzByCondition(@Param("qry") Map<String, Object> map);

	/**
	 * 
	 * 增加医嘱子表数据
	 * 
	 * @param addMap
	 */
	void addYZ(Map<String, Object> addMap);

	/**
	 * 
	 * 修改子医嘱表数据
	 * 
	 * @param updateMap
	 */
	void updateYZ(Map<String, Object> updateMap);

	/**
	 * 
	 * 增加医嘱子表异常（同步）数据
	 * 
	 * @param addMap
	 */
	void addYZExceptionData(Map<String, Object> addMap);

	/**
	 * 
	 * 查询未复制的子医嘱
	 * 
	 * @param map
	 * @return
	 */
	List<DoctorAdvice> qryListBeanNotCopyByMap(Map<String, Object> map);

	/**
	 * 
	 * 查询未复制的子医嘱
	 * 
	 * @param map
	 * @return
	 */
	List<DoctorAdvice> qryListBeanNotCopyInMain(Map<String, Object> map);

	/**
	 * 
	 * 修改医嘱状态
	 * 
	 * @param yzMap
	 */
	void changeYzStatus(Map<String, Object> yzMap);

	/**
	 * 
	 * 重置医嘱审核状态
	 * 
	 * @param updateMap
	 */
	void resetYZSH(Map<String, Object> updateMap);
}
