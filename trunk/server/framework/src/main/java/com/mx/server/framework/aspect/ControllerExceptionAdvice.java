package com.mx.server.framework.aspect;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.mx.server.framework.error.BusinessException;
import com.mx.server.framework.error.EmBusinessErr;
import com.mx.server.framework.model.response.CommonReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ck
 * 异常统一处理器
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonReturn<?> exceptionGet(Exception ex) {
        BusinessException be;
        if (ex instanceof BusinessException) {
            be = (BusinessException) ex;
        } else if (ex instanceof NotLoginException) {
            be = new BusinessException(ex, EmBusinessErr.NOT_LOGIN);
        } else if (ex instanceof NotPermissionException) {
            be = new BusinessException(ex, EmBusinessErr.AUTHORIZATION_DENIED);
        } else if (ex instanceof BindException) {
            be = new BusinessException(ex, EmBusinessErr.PARAMETER_INVALIDATION);
        } else {
            be = new BusinessException(ex, EmBusinessErr.UNKNOWN_ERROR);
        }

        // 格式化输出异常信息
        if (be.isInnerExceptionNull()) {
            log.error(String.format("[Error] 业务异常 : %s\t异常编号 : %s\t异常详情 : %s",
                    be.getMsg(),
                    be.getCode(),
                    be.getStackTrace().length > 0 ? be.getStackTrace()[0] : ExceptionUtil.getMessage(be)));
        } else if (be.getInnerException() instanceof NotLoginException) {
            log.error(String.format("[Error] 登录异常 : %s\t异常编号 : %s",
                    EmBusinessErr.NOT_LOGIN.getMsg(),
                    EmBusinessErr.NOT_LOGIN.getCode()));
        } else if (be.getInnerException() instanceof BindException) {
            BindingResult result = ((BindException) be.getInnerException()).getBindingResult();
            List<FieldError> list = result.getFieldErrors();
            String errorMsg = list.stream()
                    .map(item -> String.format("%s: %s", item.getField(), item.getDefaultMessage()))
                    .collect(Collectors.joining(", "));
            log.error(String.format("[Error] 业务异常 : %s\t异常编号 : %s\t异常详情 : %s",
                    be.getMsg(),
                    be.getCode(),
                    errorMsg));
            // 返回参数的第一个错误
            if (list.size() > 0) {
                be.setMsg(list.get(0).getDefaultMessage());
            }
        } else {
            log.error(String.format("[Error] 程序异常 : %s\n异常详情 : \n%s",
                    be.getMsg(),
                    be.getExceptionMsg()));
        }

        // 异常时的返回数据
        return CommonReturn.fail(be.getCode(), be.getMsg());
    }
}
