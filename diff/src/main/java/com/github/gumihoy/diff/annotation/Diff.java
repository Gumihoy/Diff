package com.github.gumihoy.diff.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-02
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Diff {
    /**
     * Field Name Desc.
     */
    String name() default "";

    /**
     * 是否比较
     * true: 比较，如果修改前、修改后相等 不创建 DiffNode
     * false: 不比较，如果修改前、修改后不为null 就创建DiffNode
     */
    boolean isCompare() default true;

    /**
     * diff content show.
     */
    boolean isShow() default true;
}
