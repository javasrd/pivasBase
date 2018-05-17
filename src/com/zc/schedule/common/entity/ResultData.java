package com.zc.schedule.common.entity;

import java.io.Serializable;

/**
 * 返回数据实体类
 *
 * @author Justin
 * @version v1.0
 */
public class ResultData extends ResultDataTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
