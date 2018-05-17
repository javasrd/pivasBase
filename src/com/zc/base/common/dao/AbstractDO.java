package com.zc.base.common.dao;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;


public abstract class AbstractDO
        implements Serializable {
    private static final long serialVersionUID = -3942149913171834745L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
