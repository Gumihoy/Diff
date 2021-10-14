package com.github.gumihoy.diff.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import com.github.gumihoy.diff.annotation.IDiffEnum;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-10-11
 */
public abstract class DiffEnumUtils {

    private static final String FIND_SHOW_BY_VALUE_METHOD_NAME = "findShowByValue";

    public static String getShowByValue(Class<? extends IDiffEnum> clazz, Object value) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (clazz == null
                || value == null) {
            return null;
        }
        for (IDiffEnum enumConstant : clazz.getEnumConstants()) {
            if (Objects.equals(enumConstant.value(), value)) {
                return enumConstant.show();
            }
        }
        return null;
    }
}
