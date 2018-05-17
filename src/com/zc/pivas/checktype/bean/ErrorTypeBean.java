package com.zc.pivas.checktype.bean;

import java.io.Serializable;

/**
 * 审方错误类型实体类
 *
 * @author kunkka
 * @version 1.0
 */
public class ErrorTypeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String gid;

    /**
     * 预留字段0
     */
    private String reserve0;

    /**
     * 预留字段1
     */
    private String reserve1;

    /**
     * 颜色
     */
    private String color;

    /**
     * 名称
     */
    private String name;

    /**
     * 等级
     */
    private int level;

    /**
     * 等级名称0：一般，1：严重，2：警告
     */
    private String levelName;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getReserve0() {
        return reserve0;
    }

    public void setReserve0(String reserve0) {
        this.reserve0 = reserve0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "ErrorTypeBean [gid=" + gid + ", level=" + level + ", levelName=" + levelName + ", name=" + name
                + ", reserve0=" + reserve0 + ", reserve1=" + reserve1 + ", color=" + color + "]";
    }

}
