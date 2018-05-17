package com.zc.base.orm.mybatis.paging;

public class JqueryStylePaging {
    private Integer start;
    private Integer page;
    private Integer rp;

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
        if (this.page != null && this.rp != null) {
            this.start = Integer.valueOf((page.intValue() - 1) * this.rp.intValue());
        }
    }

    public Integer getRp() {
        return this.rp;
    }

    public void setRp(Integer rp) {
        this.rp = rp;
        if (this.page != null && this.rp != null) {
            this.start = Integer.valueOf((this.page.intValue() - 1) * rp.intValue());
        }
    }

    public Integer getStart() {
        return this.start;
    }
}
