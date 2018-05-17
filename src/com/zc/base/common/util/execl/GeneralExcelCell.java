package com.zc.base.common.util.execl;


public class GeneralExcelCell {
    private String beanField;


    private boolean parse = true;


    private boolean select = false;


    private String[] selectValues;


    private GeneralExcelValueHandler valueHandler;


    private int startRow;


    private int endRow;


    private int startColumn;


    private int endColumn;


    public String getBeanField() {
        return this.beanField;
    }


    public GeneralExcelCell setBeanField(String beanField) {
        this.beanField = beanField;
        return this;
    }


    protected boolean isParse() {
        return this.parse;
    }


    public GeneralExcelCell setParse(boolean parse) {
        this.parse = parse;
        return this;
    }


    protected boolean isSelect() {
        return this.select;
    }


    public GeneralExcelCell setSelect(boolean select) {
        this.select = select;
        return this;
    }


    protected String[] getSelectValues() {
        return this.selectValues;
    }


    public GeneralExcelCell setSelectValues(String[] selectValues) {
        this.selectValues = selectValues;
        return this;
    }


    protected GeneralExcelValueHandler getValueHandler() {
        return this.valueHandler;
    }


    public void setValueHandler(GeneralExcelValueHandler valueHandler) {
        this.valueHandler = valueHandler;
        if (this.valueHandler != null) {
            this.valueHandler.setExcelCell(this);
        }
    }


    protected int getStartRow() {
        return this.startRow;
    }


    protected GeneralExcelCell setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }


    protected int getEndRow() {
        return this.endRow;
    }


    protected GeneralExcelCell setEndRow(int endRow) {
        this.endRow = endRow;
        return this;
    }


    protected int getStartColumn() {
        return this.startColumn;
    }


    protected GeneralExcelCell setStartColumn(int startColumn) {
        this.startColumn = startColumn;
        return this;
    }


    protected int getEndColumn() {
        return this.endColumn;
    }


    protected GeneralExcelCell setEndColumn(int endColumn) {
        this.endColumn = endColumn;
        return this;
    }
}
