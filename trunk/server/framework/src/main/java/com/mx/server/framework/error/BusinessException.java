package com.mx.server.framework.error;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Maoxian
 */
public class BusinessException extends RuntimeException implements CommonError {
    private final Exception exception;
    private final CommonError commonError;

    public BusinessException(CommonError commonError) {
        this(null, commonError);
    }

    public BusinessException(CommonError commonError, String msg) {
        this(null, commonError, msg);
    }

    public BusinessException(Exception e, CommonError commonError) {
        this(e, commonError, commonError.getMsg());
    }

    public BusinessException(Exception e, CommonError commonError, String msg) {
        super();
        this.exception = e;
        this.commonError = commonError;
        this.commonError.setMsg(msg);
    }

    public Exception getInnerException() {
        return this.exception;
    }

    public String getExceptionMsg() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        this.exception.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    public boolean isInnerExceptionNull() {
        return this.exception == null;
    }

    @Override
    public int getCode() {
        return this.commonError.getCode();
    }

    @Override
    public String getMsg() {
        return this.commonError.getMsg();
    }

    @Override
    public void setMsg(String msg) {
        this.commonError.setMsg(msg);
    }
}
