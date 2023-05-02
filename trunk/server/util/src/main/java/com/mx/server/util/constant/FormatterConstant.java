package com.mx.server.util.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author Maoxian
 * @since 2022/5/22
 * 格式化常量
 */
public final class FormatterConstant {
    /**
     * 标准时间格式
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准时间格式转换器
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
}
