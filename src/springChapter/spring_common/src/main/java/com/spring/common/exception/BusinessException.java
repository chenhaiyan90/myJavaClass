package com.spring.common.exception;

/**
 * @desc: 需要关心的业务异常
 * @User: ambitor_luo
 * @Date: 2015/6/16
 */
public class BusinessException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;
    private int code = -1;
    private Object[] args;
    private boolean notPrintStack;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, boolean notPrintStack) {
        this.notPrintStack = notPrintStack;
        this.code = code;
    }

    public BusinessException(int code, Throwable cause) {
        super(null, cause);
        this.code = code;
    }

    public BusinessException(int code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public boolean isNotPrintStack() {
        return notPrintStack;
    }
}
