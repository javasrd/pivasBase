package com.zc.pivas.statistics.bean.configFee;

/**
 * 配置费报表 批次bean
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeBatchPieBean {
    /**
     * 批次名称
     */
    private String name;

    /**
     * 统计数量
     */
    private float value;

    /**
     * 批次
     */
    private Integer zxbc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Integer getZxbc() {
        return zxbc;
    }

    public void setZxbc(Integer zxbc) {
        this.zxbc = zxbc;
    }

}
