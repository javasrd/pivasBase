package com.zc.schedule.common.entity;

import java.io.Serializable;
import java.util.Map;

import com.zc.schedule.common.util.DataFormat;
import com.zc.schedule.common.util.SysConstant;

/**
 * 分页实体类
 *
 * @author Justin
 * @version v1.0
 */
public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public PageInfo() {

    }


    public PageInfo(Map<String, Object> map) {
        page = DataFormat.getObjInt(map.get("page"), 1);
        pageNumber = DataFormat.getObjInt(map.get("onePageNum"), SysConstant.PAGENUMBER);
    }

    /**
     * 当前页码
     */
    Integer page = 1;

    /**
     * 每页记录数
     */
    Integer pageNumber = SysConstant.PAGENUMBER;

    /**
     * 1 分页并统计总行数; 2 分页，但是不统计总行数
     */
    Integer pageType = 1;

    /**
     * 总页数
     */
    Integer totalPage = 0;

    /**
     * 总行数
     */
    Integer totalRow = 0;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer onePageNum) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }
}
