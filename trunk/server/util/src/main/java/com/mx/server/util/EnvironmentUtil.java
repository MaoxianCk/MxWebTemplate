package com.mx.server.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Maoxian
 */
@Component
public class EnvironmentUtil {
    private static Environment environment;

    public EnvironmentUtil(Environment environment) {
        EnvironmentUtil.environment = environment;
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static <T> T getProperty(String key, Class<T> clazz) {
        return environment.getProperty(key, clazz);
    }
}
