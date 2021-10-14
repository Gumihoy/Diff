package com.github.gumihoy.diff.enums;

import com.github.gumihoy.diff.annotation.IDiffEnum;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-08-24
 */
public enum Color implements IDiffEnum<Integer> {
    RED(1, "红色"),
    ORANGE(2, "橙色"),
    YELLOW(3, "黄色"),
    GREEN(4, "青色"),
    BLUE(6, "蓝色"),
    PURPLE(7, "紫色"),

    ;

    private final int value;
    private final String label;

    Color(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Color of(int value) {
        for (Color c : Color.values()) {
            if (c.value == value) {
                return c;
            }
        }
        return null;
    }


    @Override
    public Integer value() {
        return value;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String show() {
        return label;
    }


}
