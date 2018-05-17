package com.zc.base.sys.modules.log.bo;

import com.zc.base.sys.modules.log.entity.Log;


public class LogBo
        extends Log {
    private static final long serialVersionUID = 4207326335579767706L;
    private String start;
    private String end;

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
