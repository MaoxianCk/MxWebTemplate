package com.mx.server.framework.model.response;

import com.github.pagehelper.PageInfo;
import com.mx.server.framework.error.EmBusinessErr;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ck
 * 接口通用返回类型
 */
public class CommonReturn<T> {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Schema(description = "状态代码,success表示服务器处理成功，fail表示处理出错")
    private String status;

    @Schema(description = "错误代码,供报错时使用")
    private Integer errCode;

    @Schema(description = "错误信息,供报错时使用")
    private String errMsg;

    @Schema(description = "返回的数据")
    private T data;

    public CommonReturn() {
    }

    public CommonReturn(String status, Integer errCode, String errMsg, T data) {
        this.status = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }

    //返回成功数据
    public static CommonReturn<?> success() {
        return new CommonReturn<>(SUCCESS, null, null, null);
    }

    public static <T> CommonReturn<T> success(T data) {
        return new CommonReturn<>(SUCCESS, null, null, data);
    }

    public static CommonReturn<?> success(List<?> data) {
        PageInfo<?> pageInfo = new PageInfo<>(data);
        Map<String, Object> map = new HashMap<>(2);
        map.put("list", data);
        map.put("total", pageInfo.getTotal());
        return new CommonReturn<>(SUCCESS, null, null, map);
    }

    //返回出错数据
    public static CommonReturn<?> fail(EmBusinessErr errEnum) {
        return new CommonReturn<>(FAIL, errEnum.getCode(), errEnum.getMsg(), null);
    }

    public static CommonReturn<?> fail(Integer errCode, String errMsg) {
        return new CommonReturn<>(FAIL, errCode, errMsg, null);
    }

    public static <T> CommonReturn<T> fail(Integer errCode, String errMsg, T data) {
        return new CommonReturn<>(FAIL, errCode, errMsg, data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
