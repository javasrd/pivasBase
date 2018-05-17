package com.zc.pivas.allowork.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置人员->配置工作量统计
 *
 * @author jagger
 * @version 1.0
 */
public class PzMc2AlloWorkBarBean implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 批次
     */
    private String pzCode;

    /**
     * 批次名称
     */
    private String pzMc;

    /**
     * 批次药品分类数量列表
     */
    private List<AlloWorkCount> alloWorkCountList = new ArrayList<AlloWorkCount>();

    public String getPzCode() {
        return pzCode;
    }

    public void setPzCode(String pzCode) {
        this.pzCode = pzCode;
    }

    public String getPzMc() {
        return pzMc;
    }

    public void setPzMc(String pzMc) {
        this.pzMc = pzMc;
    }

    public List<AlloWorkCount> getAlloWorkCountList() {
        return alloWorkCountList;
    }

    public void setAlloWorkCountList(List<AlloWorkCount> alloWorkCountList) {
        this.alloWorkCountList = alloWorkCountList;
    }

    public void alloWorkCountList(AlloWorkCount count) {
        this.alloWorkCountList.add(count);
    }
}
