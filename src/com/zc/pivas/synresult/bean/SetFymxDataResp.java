package com.zc.pivas.synresult.bean;

import java.io.Serializable;

/**
 * 收取配置费接口
 *
 * @author kunkka
 * @version 1.0
 */
public class SetFymxDataResp implements Serializable {

    private static final long serialVersionUID = 1L;


    //返回标志 1  成功  -1 失败
    private String alret;


    //错误信息
    private String aserrtext;

    public String getAlret() {
        return alret;
    }

    public void setAlret(String alret) {
        this.alret = alret;
    }

    public String getAserrtext() {
        return aserrtext;
    }

    public void setAserrtext(String aserrtext) {
        this.aserrtext = aserrtext;
    }

    @Override
    public String toString() {
        return "SetFymxDataResp [alret=" + alret + ", aserrtext=" + aserrtext + "]";
    }

}
