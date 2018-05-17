package com.zc.base.common.util.execl;

import org.apache.poi.hssf.util.HSSFColor;


public class ExcelComment {
    private String value;
    private HSSFColor cellForegroundColor;
    private int row;
    private int column;

    public ExcelComment() {
    }

    public ExcelComment(String value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }


    public String getValue() {
        return this.value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public HSSFColor getCellForegroundColor() {
        return this.cellForegroundColor;
    }

    public void setCellForegroundColor(HSSFColor cellForegroundColor) {
        this.cellForegroundColor = cellForegroundColor;
    }


    public int getRow() {
        return this.row;
    }


    public void setRow(int row) {
        this.row = row;
    }


    public int getColumn() {
        return this.column;
    }


    public void setColumn(int column) {
        this.column = column;
    }
}
