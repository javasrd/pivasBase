package com.zc.base.orm.mybatis.paging;

class JqueryRowData {
    private String id;

    private Object[] cell;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object[] getCell() {
        return this.cell;
    }

    public void setCell(Object[] cell) {
        this.cell = cell;
    }
}
