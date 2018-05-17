package com.zc.schedule.common.entity;

import java.util.List;

/**
 * 返回分页实体类
 *
 * @author Justin
 * @version v1.0
 */
public class ResultPage extends ResultDataTemplate {
    /**
     * 总行数
     */
    Integer totalRow;
    /**
     * 返回的条目
     */
    List<?> items;
    /**
     * 分页信息
     */
    PageInfo pageInfo;

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }


}
