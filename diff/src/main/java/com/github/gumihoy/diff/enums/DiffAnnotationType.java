package com.github.gumihoy.diff.enums;

import java.lang.annotation.Annotation;

import com.github.gumihoy.diff.annotation.Diff;
import com.github.gumihoy.diff.annotation.DiffDateTime;
import com.github.gumihoy.diff.annotation.DiffEnum;
import com.github.gumihoy.diff.annotation.DiffImage;
import com.github.gumihoy.diff.annotation.DiffMoney;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-08
 */
public enum DiffAnnotationType {
    DIFF("1", "diff"),
    DIFF_DATETIME("2", "diff datetime"),
    DIFF_ENUM("3", "diff enum"),
    DIFF_IMAGE("4", "diff image"),
    DIFF_MONEY("5", "diff money"),
    ;

    private final String value;
    private final String label;

    DiffAnnotationType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static DiffAnnotationType of(Annotation annotation) {
        if (annotation instanceof Diff) {
            return DIFF;
        }
        if (annotation instanceof DiffDateTime) {
            return DIFF_DATETIME;
        }
        if (annotation instanceof DiffEnum) {
            return DIFF_ENUM;
        }
        if (annotation instanceof DiffImage) {
            return DIFF_IMAGE;
        }
        if (annotation instanceof DiffMoney) {
            return DIFF_MONEY;
        }

        return null;
    }


}
