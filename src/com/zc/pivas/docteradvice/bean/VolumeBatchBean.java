package com.zc.pivas.docteradvice.bean;

import com.zc.base.sc.modules.batch.entity.Batch;
import com.zc.base.sys.common.util.NumberUtil;
import com.zc.pivas.docteradvice.entity.PrescriptionMain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 容积规则配置bean
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
public class VolumeBatchBean
{
    public VolumeBatchBean(Batch batch,VolumeRulesWithBatch volumeRuleWithBatch){
        this.batch = batch ;
        this.batchId = batch.getId().intValue();
        this.batchName = batch.getName();
        this.orderNum = batch.getOrderNum();
        this.volumeRuleWithBatch = volumeRuleWithBatch;
        if(volumeRuleWithBatch!=null){
            maxval = NumberUtil.getObjLong(volumeRuleWithBatch.getMaxval(),0L)+NumberUtil.getObjLong(volumeRuleWithBatch.getOverrun(),0L);
            minval = volumeRuleWithBatch.getMinval()==null?0L:volumeRuleWithBatch.getMinval();
        }else{
            this.volumeRuleWithBatch = new VolumeRulesWithBatch();
            maxval = 0L;
            minval = 0L;
        }
        ydMainList = new ArrayList<PrescriptionMain>();
    }

    /**
     * 
     */
    VolumeRulesWithBatch volumeRuleWithBatch;

    /**
     * 批次bean
     */
    Batch batch;

    /**
     * 批次id
     */
    Integer batchId;

    /**
     * 批次名称
     */
    String batchName;

    /**
     * 排序
     */
    Integer orderNum;

    /**
     * 最大容积
     */
    Long maxval;

    /**
     * 最小容积
     */
    Long minval;

    /**
     * 药单列表
     */
    List<PrescriptionMain> ydMainList;

    /**
     * 当前容积
     */
    Double nowval = 0D ;

    public Batch getBatch()
    {
        return batch;
    }

    public void setBatch(Batch batch)
    {
        this.batch = batch;
    }

    public Integer getBatchId()
    {
        return batchId;
    }

    public void setBatchId(Integer batchId)
    {
        this.batchId = batchId;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Long getMaxval()
    {
        return maxval;
    }

    public void setMaxval(Long maxval)
    {
        this.maxval = maxval;
    }

    public Long getMinval()
    {
        return minval;
    }

    public void setMinval(Long minval)
    {
        this.minval = minval;
    }

    public List<PrescriptionMain> getYdMainList()
    {
        return ydMainList;
    }

    public void setYdMainList(List<PrescriptionMain> ydMainList)
    {
        this.ydMainList = ydMainList;
    }

    public Double getNowval()
    {
        return nowval;
    }

    public void setNowval(Double nowval)
    {
        this.nowval = nowval;
    }

    public String getBatchName()
    {
        return batchName;
    }

    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }
    
    
    
}
