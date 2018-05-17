package com.zc.base.core.exception;


public class DreambaseException
        extends RuntimeException {
    private static final long serialVersionUID = -3947752630173021259L;


    private String code;


    private String description;


    private String solutionDesc;


    public DreambaseException() {
    }


    public DreambaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DreambaseException(String message) {
        super(message);
    }

    public DreambaseException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolutionDesc() {
        return this.solutionDesc;
    }

    public void setSolutionDesc(String solutionDesc) {
        this.solutionDesc = solutionDesc;
    }
}
