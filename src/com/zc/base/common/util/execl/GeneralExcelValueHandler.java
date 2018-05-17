package com.zc.base.common.util.execl;


public abstract class GeneralExcelValueHandler {
    private GeneralExcelCell excelCell;


    public abstract String handler(String paramString);


    public GeneralExcelCell getExcelCell() {
        return this.excelCell;
    }


    protected void setExcelCell(GeneralExcelCell excelCell) {
        this.excelCell = excelCell;
    }
}
