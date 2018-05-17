package com.zc.base.orm.mybatis.paging;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class JqueryStylePagingResults<T> {
    private Integer total;
    private Integer page;
    private List<JqueryRowData> rows;
    private List<T> rawRecords;
    private String[] columns;
    private String[] titles;

    public JqueryStylePagingResults(String[] columns) {
        this.columns = columns;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<JqueryRowData> getRows() {
        return this.rows;
    }


    public void setDataRows(List<T> dataRows)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (dataRows != null) {
            this.rawRecords = dataRows;
            this.rows = new ArrayList();
            for (int i = 0; i < dataRows.size(); i++) {
                T dataRow = dataRows.get(i);
                JqueryRowData rowData = new JqueryRowData();
                rowData.setId(i + "");
                Object[] cell = new Object[this.columns.length];

                for (int j = 0; j < this.columns.length; j++) {
                    cell[j] = PropertyUtils.getProperty(dataRow, this.columns[j]);
                }
                rowData.setCell(cell);
                this.rows.add(rowData);
            }
        }
    }

    public String[] getColumns() {
        return this.columns;
    }

    public List<T> getRawRecords() {
        return this.rawRecords;
    }


    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[] getTitles() {
        return this.titles;
    }
}
