package com.weiyi.lock.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//权限校验器,使用该注解的方法都会被SecurityInterceptor拦截
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecurityAnnotation
{
    String[] value() default {};
}
