package com.github.gumihoy.diff.annotation;

/**
 * Field is enum value, But not enum object.
 *
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-14
 */
public interface IDiffEnum<T> {
    /**
     * Enum value.
     *
     * @return enum value
     */
    T value();

    /**
     * Enum show.
     *
     * @return enum show value
     */
    String show();
}
