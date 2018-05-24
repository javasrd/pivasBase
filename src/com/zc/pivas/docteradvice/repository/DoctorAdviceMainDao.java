package com.zc.pivas.docteradvice.repository;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.docteradvice.bean.*;
import com.zc.pivas.docteradvice.entity.DoctorAdvice;
import com.zc.pivas.docteradvice.entity.DoctorAdviceMain;
import com.zc.pivas.titileshow.bean.ConfigTitleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 主医嘱DAO
 * 
 * @author cacabin
 * @version 0.1
 */
@MyBatisRepository("yzMainDao")
public interface DoctorAdviceMainDao {

	/**
	 * 查询医嘱列表
	 * 
	 * @param map
	 * @param jquryStylePaging
	 * @return
	 */
	List<DoctorAdviceMain> qryListBeanByMap(@Param("qry") Map<String, Object> map,
			@Param("paging") JqueryStylePaging jquryStylePaging);

	/**
	 * 查询医嘱总数
	 * 
	 * @param map
	 * @return
	 */
	Integer qryCountByMap(@Param("qry") Map<String, Object> map);

	/**
	 * 查询没有频次的医嘱
	 * 
	 * @param pidsjN
	 * @return
	 */
	List<String> qryYZNoPinCi(List<String> pidsjN);

	/**
	 * 根据pidsj查询药单
	 * 
	 * @param pidsj
	 * @return
	 */
	List<PrescriptionBLabel> qryYdMoreByPidsj(@Param("pidsj") String pidsj);

	/**
	 * 统计医嘱数量
	 * 
	 * @param map
	 * @return
	 */
	List<DoctorAdviceGroupBean> groupByMap(@Param("qry") Map<String, Object> map);

	/**
	 * 更新医嘱状态
	 * 
	 * @param updateMap
	 * @return
	 */
	Integer updateCheckYZMain(Map<String, Object> updateMap);

	/**
	 * 更新子医嘱状态
	 * 
	 * @param updateMap
	 * @return
	 */
	Integer updateCheckYZ(Map<String, Object> updateMap);

	/**
	 * 删除药单数据
	 * 
	 * @param pidsjN
	 */
	void deleteYDMainByPidsjN(@Param("pidsjN") List<String> pidsjN);

	/**
	 * 删除子药单数据
	 * 
	 * @param pidsjN
	 */
	void deleteYDByPidsjN(@Param("pidsjN") List<String> pidsjN);

	/**
	 * 删除批瓶签数据
	 * 
	 * @param pidsjN
	 */
	void deletePQByPidsjN(@Param("pidsjN") List<String> pidsjN);

	/**
	 * 根据病区统计总数
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> groupNumByInpArea(@Param("qry") Map<String, Object> map);

	/**
	 * 根据条件查询子医嘱
	 * 
	 * @param map
	 * @return
	 */
	DoctorAdviceMain getYzByCondition(@Param("qry") Map<String, Object> map);

	/**
	 * 增加医嘱表数据
	 * 
	 * @param addMap
	 */
	void addYZ(Map<String, Object> addMap);

	/**
	 * 修改医嘱表数据
	 * 
	 * @param updateMap
	 */
	void updateYZ(Map<String, Object> updateMap);

	/**
	 * 重置医嘱审核状态
	 * 
	 * @param updateMap
	 */
	void resetYZSH(Map<String, Object> updateMap);

	/**
	 * 修改医嘱主表状态
	 * 
	 * @param yzMap
	 */
	void changeYzMainStatus(Map<String, Object> yzMap);

	/**
	 * 修改药单表状态
	 * 
	 * @param yzMap
	 */
	void changeYDStateByParentNo(Map<String, Object> yzMap);

	/**
	 * 修改药单主表状态
	 * 
	 * @param yzMap
	 */
	void changeYDMainStateByParentNo(Map<String, Object> yzMap);

	/**
	 * 修改药单主表状态
	 * 
	 * @param yzMap
	 */
	void changeYDBedno(Map<String, Object> yzMap);

	/**
	 * 修改药单主表状态
	 * 
	 * @param yzMap
	 */
	void changeYDMainBedno(Map<String, Object> yzMap);

	/**
	 * 修改医嘱主表床号
	 * 
	 * @param yzMap
	 */
	void changeBedno(Map<String, Object> yzMap);

	/**
	 * 查询批次列表
	 * 
	 * @param map
	 * @return
	 */
	List<Frequency> qryBatchRule(@Param("qry") Map<String, Object> map);

	/**
	 * 查询一般规则
	 * 
	 * @param map
	 * @return
	 */
	List<GeneralRule> qryGeneralRule(@Param("qry") Map<String, Object> map);

	/**
	 * 查询病人相关医嘱
	 * 
	 * @param map
	 * @param jquryStylePaging
	 * @return
	 */
	List<Map<String, Object>> qryPatListByYZ(@Param("qry") Map<String, Object> map,
			@Param("paging") JqueryStylePaging jquryStylePaging);

	/**
	 * 查询病人总数
	 * 
	 * @param map
	 * @return
	 */
	Integer qryPatListSize(@Param("qry") Map<String, Object> map);

	/**
	 * 更新自动检查医嘱状态
	 * 
	 * @param map
	 * @return
	 */
	Integer updateYZAutoCheckN(@Param("map") Map<String, Object> map);

	/**
	 * 更新主医嘱检查状态
	 * 
	 * @param map
	 * @return
	 */
	Integer updateYZMainAutoCheckN(@Param("map") Map<String, Object> map);

	/**
	 * 查询医嘱列表
	 * 
	 * @param map
	 * @return
	 */
	List<DoctorAdviceMainBean> qryListWithYZByMap(@Param("qry") Map<String, Object> map);

	/**
	 * 重置医嘱审核状态
	 * 
	 * @param map
	 * @return
	 */
	Integer resetYZCheckStatus(@Param("qry") Map<String, Object> map);

	/**
	 * 停止非当日开立的临时医嘱
	 * 
	 * @return
	 */
	Integer stopTempYZNotToday();

	/**
	 * 添加医嘱审核日志
	 * 
	 * @param map
	 * @return
	 */
	void addOprLogMany(@Param("map") Map<String, Object> map);

	/**
	 * 查询知识库表中所有数据
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectKnowledgeData(Map<String, Object> map);

	/**
	 * 设置知识库表主键
	 * 
	 * @param map
	 * @return
	 */
	String selectKnowledgeKey(Map<String, Object> map);

	/**
	 * 删除药学知识库
	 */
	void deleteKnowledge();

	/**
	 * 根据病人病区分组
	 * 
	 * @param map
	 * @return
	 */
	List<DoctorAdviceAreaPat> groupByAreaPat(@Param("map") Map<String, Object> map);

	/**
	 * 根据自学习状态 添加审方状态
	 * 
	 * @param map
	 * @return
	 */
	Integer insertCheckResultMany(Map<String, Object> map);

	/**
	 * 根据自学习状态 确认审方状态
	 * 
	 * @param map
	 * @return
	 */
	Integer updateYZMainSHZTByYSH(@Param("map") Map<String, Object> map);

	/**
	 * 根据自学习状态 确认审方状态
	 * 
	 * @param map
	 * @return
	 */
	Integer updateYZSHZTByYSH(@Param("map") Map<String, Object> map);

	List<ConfigTitleBean> qryTitleList();

	void updateRecheckState(@Param("parentNo") String parentNo, @Param("state") Integer newYzshzt);

	List<DoctorAdvice> getYZDetail(@Param("groupNo") String groupNo, @Param("drugCode") String drugCode);

	int judgePCExistBy(@Param("parentNo") String parentNo, @Param("dayDate") String dayDate, @Param("pcID") int pcID);

	String[] qryShowTitle(@Param("id") Long id);

	/**
	 *
	 * 统计医嘱输液量
	 *
	 * @param map
	 * @return
	 */
	String getTransfusion(Map<String, Object> map);

	BottleLabel getPqInfo(@Param("parentNo") String parentStr, @Param("pidsj") String pidsjStr);

	int getPcIsExist(BottleLabel pqBean);

}
