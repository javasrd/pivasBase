package com.zc.base.common.bo;

import java.util.List;


public class ExcelCell {
    private String cellValue;
    private int firstRow;
    private int lastRow;
    private int firstColumn;
    private int lastColumn;
    private int direction;
    private String formatAsString;
    private List<String> cellInclude;

    public String getFormatAsString() {
        return this.formatAsString;
    }

    public void setFormatAsString(String formatAsString) {
        this.formatAsString = formatAsString;
    }

    public List<String> getCellInclude() {
        return this.cellInclude;
    }

    public void setCellInclude(List<String> cellInclude) {
        this.cellInclude = cellInclude;
    }

    public String getCellValue() {
        return this.cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public int getFirstRow() {
        return this.firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFirstColumn() {
        return this.firstColumn;
    }

    public void setFirstColumn(int firstColumn) {
        this.firstColumn = firstColumn;
    }

    public int getLastColumn() {
        return this.lastColumn;
    }

    public void setLastColumn(int lastColumn) {
        this.lastColumn = lastColumn;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
