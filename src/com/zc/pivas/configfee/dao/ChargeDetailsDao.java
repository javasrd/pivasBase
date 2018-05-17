package com.zc.pivas.configfee.dao;

import com.zc.base.orm.mybatis.annotation.MyBatisRepository;
import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import com.zc.pivas.configfee.bean.ChargePzfDetailsBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 配置费详情dao
 *
 * @author  cacabin
 * @version  1.0
 */
@MyBatisRepository("ChargeDetailsDao")
public interface ChargeDetailsDao
{
    List<Map<String, Object>> groupListMapByMap(@Param("qry")
    Map<String, Object> map, @Param("paging")
    JqueryStylePaging jquryStylePaging);
    
    Integer groupCountByMap(@Param("qry")
    Map<String, Object> map);
    
    List<ChargeBean> qryListChargeByMap(@Param("qry")
    Map<String, Object> map, @Param("paging")
    JqueryStylePaging jquryStylePaging);
    
    /**
     * 
     * 获取信息
     * 
     * @param scansSearch
     * 
     * @return
     */
    List<ChargeDetailsBean> queryChargeDetailsList(@Param("chargeDetails")
    ChargeDetailsBean chargedetailsbean, @Param("paging")
    JqueryStylePaging jqueryStylePaging);
    
    int queryChargeDetailsListTotal(@Param("chargeDetails")
    ChargeDetailsBean chargedetailsbean);
    
    List<ChargeDetailsBean> dbqueryChargeDetailsList(@Param("chargeDetails")
    ChargeDetailsBean chargedetailsbean, @Param("paging")
    JqueryStylePaging jqueryStylePaging);
    
    Integer insertPzf(@Param("configFee")
    ChargeDetailsBean configFee);
    
    Integer getChargeCount(@Param("chargeDetails")
    ChargeDetailsBean chargedetailsbean);
    
    Date qryLastChargeDate(@Param("prfid")
    Long prfid);
    
    List<ChargeBean> qryListChargeByMap2(@Param("qry")
    Map<String, Object> map, @Param("paging")
    JqueryStylePaging jquryStylePaging);
    
    ChargeBean qryPZFDetail(@Param("ydPzfId")
    String ydPzfId);
    
    Integer insertPzf1(@Param("chargeBean")
    ChargeBean chargeBean);
    
    Integer checkChargeInABatch(@Param("pqrfid")
    Long pqrfID);
    
    void updatePPRCF(@Param("gid")
    Long gid, @Param("feeresult")
    String feeresult, @Param("feedetails")
    String feedetails, @Param("feedate")
    String feedate);
    
    List<ChargePzfDetailsBean> qryPzfPQRefInfo(@Param("pqrfid")
    Long pqrfID);
    
    void updatePZFStatus(@Param("chargeBean")
    ChargeBean chargeBean);
}
