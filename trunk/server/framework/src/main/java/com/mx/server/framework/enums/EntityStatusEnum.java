package com.mx.server.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Maoxian
 * @since 2023/4/29
 * 通用表状态
 */
public enum EntityStatusEnum implements IEnum<Integer> {
    ENABLE(0, "正常"),
    DISABLED(1, "已禁用");

    @EnumValue
    @JsonValue
    private final int value;

    private final String desc;

    EntityStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static EntityStatusEnum getDefault() {
        return ENABLE;
    }
}
