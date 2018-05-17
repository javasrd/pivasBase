package com.zc.pivas.configfee.service;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收费明细接口
 *
 * @author  cacabin
 * @version  1.0
 */
public interface ChargeDetailsService {

    List<Map<String, Object>> groupListMapByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging);

    Integer groupCountByMap(Map<String, Object> map);

    List<ChargeBean> qryListChargeByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging);

    JqueryStylePagingResults<ChargeDetailsBean> queryChargeDetailsList(ChargeDetailsBean chargedetailsbean,
                                                                       JqueryStylePaging jquryStylePaging)
            throws Exception;

    JqueryStylePagingResults<ChargeDetailsBean> dbqueryChargeDetailsList(ChargeDetailsBean chargedetailsbean,
                                                                         JqueryStylePaging jquryStylePaging)
            throws Exception;

    List<ChargeDetailsBean> queryChargeDetailsListBean(ChargeDetailsBean chargedetailsbean,
                                                       JqueryStylePaging jqueryStylePaging);

    Integer insertPzf(@Param("configFee")
                              ChargeDetailsBean configFee);

    Integer getChargeCount(ChargeDetailsBean chargedetailsbean);

    Date queryLastChargeState(Long prfid);

    List<ChargeBean> qryListChargeByMap2(Map<String, Object> map, JqueryStylePaging jquryStylePaging);

    ChargeBean qryChargeDetail(String ydPzfId);
}
