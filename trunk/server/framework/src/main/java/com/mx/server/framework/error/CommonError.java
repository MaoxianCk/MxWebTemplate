package com.mx.server.framework.error;

public interface CommonError {
    public int getCode();

    public String getMsg();

    public void setMsg(String msg);
}
