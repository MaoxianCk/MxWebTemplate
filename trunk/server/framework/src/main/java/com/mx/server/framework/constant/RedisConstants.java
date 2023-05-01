package com.mx.server.framework.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class RedisConstants {
    public static String REDIS_KEY_PREFIX;

    public RedisConstants(@Value("${spring.data.redis.prefix}") String redisKeyPrefix) {
        REDIS_KEY_PREFIX = redisKeyPrefix + ":";
    }

    /**
     * Param 模块
     */

    public static final String PARAM_PREFIX = "param:";

    public static final String PARAM_MAP_KEY = PARAM_PREFIX + "map";
}
