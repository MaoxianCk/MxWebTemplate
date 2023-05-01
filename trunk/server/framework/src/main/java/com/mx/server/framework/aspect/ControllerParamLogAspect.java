package com.mx.server.framework.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Ck
 * AOP，日志中记录 接口的出入参信息
 */
@Aspect
@Order(0)
@Component
@Slf4j
public class ControllerParamLogAspect {
    private final ControllerExceptionAdvice exceptionHandle;

    private final ObjectMapper objectMapper;

    private static int globalReqCount = 0;

    public ControllerParamLogAspect(ControllerExceptionAdvice exceptionHandle, ObjectMapper objectMapper) {
        this.exceptionHandle = exceptionHandle;
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(com.mx.server.framework.annotation.IgnoreAOP)")
    private void ignore() {};
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && execution(public * *(..))")
    private void withins() {};


    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && execution(public * com.mx.server..*(..))")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint pjp) {
        try {
            // 处理前
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();
            String type = request.getMethod();
            String url = request.getRequestURL().toString();
            String method = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
            String params;
            int reqCount = globalReqCount;
            globalReqCount = (globalReqCount + 1) % 10000;
            if (pjp.getArgs() != null && pjp.getArgs().length > 0) {
                params = objectMapper.writeValueAsString(pjp.getArgs());
            } else {
                params = "[]";
            }
            log.debug("[{}] AOP start, type:{}, url:{}, method:{}, params:{}",
                    reqCount, type, url, method, params);

            // 处理
            Object o;
            try {
                o = pjp.proceed();
            } catch (Exception e) {
                o = exceptionHandle.exceptionGet(e);
            }

            // 处理完请求，返回内容
            log.debug("[{}] AOP end, return:{}", reqCount, objectMapper.writeValueAsString(o));
            log.debug("=======================================================");
            return o;
        } catch (Throwable e) {
            log.debug("AOP ERROR:");
            e.printStackTrace();
            return null;
        }
    }
}
