package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/17 16:30
 * @version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = LogAspect.class)//让springboot可以扫描到
public @interface EnableLogAspect {
}
