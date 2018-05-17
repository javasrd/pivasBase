package com.zc.pivas.configfee.service.impl;

import com.zc.base.orm.mybatis.paging.JqueryStylePaging;
import com.zc.base.orm.mybatis.paging.JqueryStylePagingResults;
import com.zc.base.sys.common.util.DefineCollectionUtil;
import com.zc.pivas.configfee.bean.ChargeBean;
import com.zc.pivas.configfee.bean.ChargeDetailsBean;
import com.zc.pivas.configfee.dao.ChargeDetailsDao;
import com.zc.pivas.configfee.service.ChargeDetailsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 收费明细实现类
 *
 * @author  cacabin
 * @version  1.0
 */
@Service("chargeDetailsService")
public class ChargeDetailsServiceImpl implements ChargeDetailsService
{
    
    @Resource
    private ChargeDetailsDao chargedetailsdao;
    
    @Override
    public List<Map<String, Object>> groupListMapByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return chargedetailsdao.groupListMapByMap(map, jquryStylePaging);
    }
    
    @Override
    public Integer groupCountByMap(Map<String, Object> map)
    {
        return chargedetailsdao.groupCountByMap(map);
    }
    
    @Override
    public List<ChargeBean> qryListChargeByMap(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return chargedetailsdao.qryListChargeByMap(map, jquryStylePaging);
    }
    
    @Override
    public JqueryStylePagingResults<ChargeDetailsBean> queryChargeDetailsList(ChargeDetailsBean chargedetailsbean,
        JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        String[] columns =
            new String[] {"pZFSBYY", "wARDNAME", "pATNAME", "aGE", "yDPQ", "nAME_", "dRUGNAME", "dOSE", "qUANTITY",
                "pZFZT", "pZFSQRQ", "aCTORDER_NO", "yDZXZT", "bEDNO", "cASE_ID", "sEX", "sFYSMC"};
        JqueryStylePagingResults<ChargeDetailsBean> pagingResults =
            new JqueryStylePagingResults<ChargeDetailsBean>(columns);
        
        // 总数
        int total;
        List<ChargeDetailsBean> chargedetailsbeanlist = null;
        chargedetailsbeanlist = chargedetailsdao.queryChargeDetailsList(chargedetailsbean, jquryStylePaging);
        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(chargedetailsbeanlist) && jquryStylePaging.getPage() != 1)
        {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            chargedetailsbeanlist = chargedetailsdao.queryChargeDetailsList(chargedetailsbean, jquryStylePaging);
        }
        
        total = chargedetailsdao.queryChargeDetailsListTotal(chargedetailsbean);
        pagingResults.setDataRows(chargedetailsbeanlist);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        
//        System.out.println("pagingResults" + pagingResults);
        return pagingResults;
    }
    
    @Override
    public JqueryStylePagingResults<ChargeDetailsBean> dbqueryChargeDetailsList(ChargeDetailsBean chargedetailsbean,
        JqueryStylePaging jquryStylePaging)
        throws Exception
    {
        String[] columns = new String[] {"parentNo", "zXBC"};
        JqueryStylePagingResults<ChargeDetailsBean> pagingResults =
            new JqueryStylePagingResults<ChargeDetailsBean>(columns);
        
        // 总数
        int total;
        List<ChargeDetailsBean> chargedetailsbeanlist = null;
        chargedetailsbeanlist = chargedetailsdao.queryChargeDetailsList(chargedetailsbean, jquryStylePaging);
        // 处理最后一页不是第一页且没有数据的问题
        if (DefineCollectionUtil.isEmpty(chargedetailsbeanlist) && jquryStylePaging.getPage() != 1)
        {
            jquryStylePaging.setPage(jquryStylePaging.getPage() - 1);
            chargedetailsbeanlist = chargedetailsdao.queryChargeDetailsList(chargedetailsbean, jquryStylePaging);
        }
        
        total = chargedetailsdao.queryChargeDetailsListTotal(chargedetailsbean);
        pagingResults.setDataRows(chargedetailsbeanlist);
        pagingResults.setTotal(total);
        pagingResults.setPage(jquryStylePaging.getPage());
        return pagingResults;
    }
    
    @Override
    public List<ChargeDetailsBean> queryChargeDetailsListBean(ChargeDetailsBean chargedetailsbean,
        JqueryStylePaging jqueryStylePaging)
    {
        return chargedetailsdao.queryChargeDetailsList(chargedetailsbean, jqueryStylePaging);
    }
    
    @Override
    public Integer insertPzf(@Param("configFee")
    ChargeDetailsBean configFee)
    {
        return chargedetailsdao.insertPzf(configFee);
    }
    
    public Integer getChargeCount(ChargeDetailsBean chargedetailsbean)
    {
        Integer count = chargedetailsdao.getChargeCount(chargedetailsbean);
        return count;
    }
    
    @Override
    public Date queryLastChargeState(Long prfid)
    {
        Date date = chargedetailsdao.qryLastChargeDate(prfid);
        if(date!=null){
            return new Date(date.getTime());
        }
        return null;
    }
    
    @Override
    public List<ChargeBean> qryListChargeByMap2(Map<String, Object> map, JqueryStylePaging jquryStylePaging)
    {
        return chargedetailsdao.qryListChargeByMap2(map, jquryStylePaging);
    }
    
    @Override
    public ChargeBean qryChargeDetail(String ydPzfId)
    {
        return chargedetailsdao.qryPZFDetail(ydPzfId);
    }
}
