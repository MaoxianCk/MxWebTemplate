package com.mx.server.framework.annotation;

import java.lang.annotation.*;

/**
 * @author Ck
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAOP {
}
