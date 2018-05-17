package com.zc.schedule.system.config.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 错误日志
 *
 * @author jagger
 * @version v1.0
 */
public class ErrLog implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 错误id
     */
    Long errId;

    /**
     * 错误时间
     */
    Date errTime;

    /**
     * 错误方法
     */
    String errFun;

    /**
     * 错误原因
     */
    String errCause;

    /**
     * 错误信息
     */
    String errMess;

    /**
     * 错误堆栈
     */
    String errStack;

    /**
     * 错误参数
     */
    String errParam;

    /**
     * 错误人员id
     */
    Long errUserId;

    public ErrLog() {

    }

    public ErrLog(String errFun, String errCause, String errMess, String errStack, Long errUserId, String errParam) {
        errTime = new Date();
        this.errFun = errFun;
        if (errCause != null) {
            this.errCause = errCause;
            if (this.errCause.length() > 3500) {
                this.errCause = this.errCause.substring(0, 3500);
            }
        }
        if (errMess != null) {
            this.errMess = errCause;
            if (this.errMess.length() > 3500) {
                this.errMess = this.errMess.substring(0, 3500);
            }
        }
        if (errStack != null) {
            this.errStack = errStack;
            if (this.errStack.length() > 3500) {
                this.errStack = this.errStack.substring(0, 3500);
            }
        }
        if (errParam != null) {
            this.errParam = errParam;
            if (this.errParam.length() > 400) {
                this.errParam = this.errParam.substring(0, 400);
            }
        }
        if (errUserId != null) {
            this.errUserId = errUserId;
        }
    }

    public Long getErrId() {
        return errId;
    }

    public void setErrId(Long errId) {
        this.errId = errId;
    }

    public Date getErrTime() {
        if (errTime != null) {
            return (Date) errTime.clone();
        } else {
            return null;
        }
    }

    public void setErrTime(Date errTime) {
        if (errTime != null) {
            this.errTime = (Date) errTime.clone();
        } else {
            this.errTime = null;
        }
    }

    public String getErrFun() {
        return errFun;
    }

    public void setErrFun(String errFun) {
        this.errFun = errFun;
    }

    public String getErrCause() {
        return errCause;
    }

    public void setErrCause(String errCause) {
        this.errCause = errCause;
    }

    public String getErrMess() {
        return errMess;
    }

    public void setErrMess(String errMess) {
        this.errMess = errMess;
    }

    public String getErrStack() {
        return errStack;
    }

    public void setErrStack(String errStack) {
        this.errStack = errStack;
    }

    public Long getErrUserId() {
        return errUserId;
    }

    public void setErrUserId(Long errUserId) {
        this.errUserId = errUserId;
    }

    public String getErrParam() {
        return errParam;
    }

    public void setErrParam(String errParam) {
        this.errParam = errParam;
    }


}
