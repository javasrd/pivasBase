package com.zc.pivas.activemq;

import java.io.Serializable;

public class SyncData implements Serializable {
    private String data;

    public SyncData() {
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
