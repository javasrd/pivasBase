package com.zc.base.common.util.execl;


public class GeneralExcelHeader {
    private String value;


    private String comment;


    protected String getValue() {
        return this.value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    protected String getComment() {
        return this.comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public String toString() {
        return this.value;
    }
}
