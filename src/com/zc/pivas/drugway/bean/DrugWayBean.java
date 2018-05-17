package com.zc.pivas.drugway.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用药途径实体类
 *
 * @author Ray
 * @version 1.0
 */
public class DrugWayBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String gid;

    /**
     * 用药方法id
     */
    private String id;

    /**
     * 用药方法编码
     */
    private String code;

    private String[] codes;

    /**
     * 用药方法名字
     */
    private String name;

    private String[] names;

    /**
     * 预留字段0
     */
    private String reserve0;

    /**
     * 预留字段1
     */
    private String reserve1;

    /**
     * 预留字段2
     */
    private String reserve2;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReserve0() {
        return reserve0;
    }

    public void setReserve0(String reserve0) {
        this.reserve0 = reserve0;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "DrugWayBean [gid=" + gid + ", id=" + id + ", code=" + code + ", codes=" + Arrays.toString(codes) + ", name=" + name + ", names=" + Arrays.toString(names) + ", reserve0=" + reserve0 + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + "]";
    }

}
