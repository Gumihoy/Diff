package com.github.gumihoy.diff.enums;

import java.lang.reflect.Field;

import com.github.gumihoy.diff.util.ReflectionUtils;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-08
 */
public enum DiffObjectType {
    /**
     * The primitive type.
     */
    PRIMITIVE,
    /**
     * The enum type.
     */
    ENUM,
    ARRAY,
    COLLECTION,
    MAP,
    JAVA,
    CUSTOM,
    ;

    public static DiffObjectType of(Class<?> clazz) {
        if (ReflectionUtils.isPrimitive(clazz)) {
            return PRIMITIVE;
        } else if (ReflectionUtils.isEnum(clazz)) {
            return ENUM;
        } else if (ReflectionUtils.isArray(clazz)) {
            return ARRAY;
        } else if (ReflectionUtils.isCollection(clazz)) {
            return COLLECTION;
        } else if (ReflectionUtils.isMap(clazz)) {
            return MAP;
        } else if (ReflectionUtils.isJava(clazz)) {
            return JAVA;
        } else {
            return CUSTOM;
        }
    }

    public static DiffObjectType of(Field field) {
        return of(field.getType());
    }

}
