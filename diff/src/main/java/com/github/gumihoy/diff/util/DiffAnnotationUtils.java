package com.github.gumihoy.diff.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.github.gumihoy.diff.annotation.Diff;
import com.github.gumihoy.diff.annotation.DiffDateTime;
import com.github.gumihoy.diff.annotation.DiffEnum;
import com.github.gumihoy.diff.annotation.DiffImage;
import com.github.gumihoy.diff.annotation.DiffMoney;
import com.github.gumihoy.diff.annotation.DiffTimeStamp;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-08-24
 */
public abstract class DiffAnnotationUtils {

    /**
     * Diff Annotation.
     */
    public static Annotation getAnnotation(Field field) {
        Annotation annotation = null;

        annotation = field.getAnnotation(Diff.class);
        if (annotation != null) {
            return annotation;
        }

        annotation = field.getAnnotation(DiffDateTime.class);
        if (annotation != null) {
            return annotation;
        }
        annotation = field.getAnnotation(DiffTimeStamp.class);
        if (annotation != null) {
            return annotation;
        }

        annotation = field.getAnnotation(DiffEnum.class);
        if (annotation != null) {
            return annotation;
        }

        annotation = field.getAnnotation(DiffImage.class);
        if (annotation != null) {
            return annotation;
        }

        return field.getAnnotation(DiffMoney.class);
    }


    public static String name(Annotation annotation) {
        if (annotation instanceof Diff) {
            return ((Diff) annotation).name();
        }
        if (annotation instanceof DiffDateTime) {
            return ((DiffDateTime) annotation).name();
        }
        if (annotation instanceof DiffTimeStamp) {
            return ((DiffTimeStamp) annotation).name();
        }
        if (annotation instanceof DiffEnum) {
            return ((DiffEnum) annotation).name();
        }
        if (annotation instanceof DiffImage) {
            return ((DiffImage) annotation).name();
        }
        if (annotation instanceof DiffMoney) {
            return ((DiffMoney) annotation).name();
        }

        return null;
    }

    public static boolean isCompare(Annotation annotation) {
        if (annotation instanceof Diff) {
            return ((Diff) annotation).isCompare();
        }
        if (annotation instanceof DiffDateTime) {
            return ((DiffDateTime) annotation).isCompare();
        }
        if (annotation instanceof DiffTimeStamp) {
            return ((DiffTimeStamp) annotation).isCompare();
        }
        if (annotation instanceof DiffEnum) {
            return ((DiffEnum) annotation).isCompare();
        }
        if (annotation instanceof DiffImage) {
            return ((DiffImage) annotation).isCompare();
        }
        if (annotation instanceof DiffMoney) {
            return ((DiffMoney) annotation).isCompare();
        }

        return true;
    }

    public static boolean isShow(Annotation annotation) {
        if (annotation instanceof Diff) {
            return ((Diff) annotation).isShow();
        }
        
        return true;
    }

}
