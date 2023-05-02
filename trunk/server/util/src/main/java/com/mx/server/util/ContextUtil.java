package com.mx.server.util;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @author Maoxian
 * springMvc请求上下文工具
 */
@Slf4j
public class ContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 获取当前会话的 request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();// 大善人SpringMVC提供的封装
        if (servletRequestAttributes == null) {
            throw new RuntimeException("当前环境非JavaWeb");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前会话的  response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();// 大善人SpringMVC提供的封装
        if (servletRequestAttributes == null) {
            throw new RuntimeException("当前环境非JavaWeb");
        }
        return servletRequestAttributes.getResponse();
    }

    public static String getDevice() {
        UserAgent userAgent = UserAgentUtil.parse(getRequest().getHeader("User-Agent"));
        return userAgent.getPlatform().getName();
    }

    /**
     * 添加一对Cookie键值到response里
     */
    public static void addCookie(String key, String val) {
        addCookie(key, val, getResponse());
    }

    public static void addCookie(String key, String val, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, val);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 添加一对Cookie键值到response里，并设置有效时间，单位秒
     */
    public static void addCookie(String key, String val, int seconds) {
        addCookie(key, val, seconds, getResponse());
    }

    public static void addCookie(String key, String val, int seconds, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, val);
        cookie.setMaxAge(seconds);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 根据键获取值
     */
    public static String getCookie(String key) {
        for (Cookie cookie : getRequest().getCookies()) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getFromHeader(String key) {
        return getRequest().getHeader(key);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
