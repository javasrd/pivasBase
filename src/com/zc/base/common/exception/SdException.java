package com.zc.base.common.exception;

import com.zc.base.core.exception.DreambaseException;


public class SdException
        extends DreambaseException {
    private static final long serialVersionUID = 6776012674635493006L;

    public SdException(String message, Throwable cause) {
        super(message, cause);
    }


    public SdException(String message) {
        super(message);
    }


    public SdException(Throwable cause) {
        super(cause);
    }


    public static String getStackTrace(Throwable e) {
        StackTraceElement[] trace = e.getStackTrace();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < trace.length; i++) {
            sb.append("\tat " + trace[i]);
        }
        return sb.toString();
    }
}
