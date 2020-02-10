package com.cntest.su.jpa.dao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 该组件用于触发自动生成Dao组件，该组件必须和 @see EntityScan 注解一起使用。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DaoRegistrar.class)
public @interface DaoScan {
}
