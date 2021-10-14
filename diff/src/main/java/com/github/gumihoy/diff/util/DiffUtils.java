package com.github.gumihoy.diff.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.github.gumihoy.diff.adt.DiffArrayObject;
import com.github.gumihoy.diff.adt.DiffCollectionObject;
import com.github.gumihoy.diff.adt.DiffCustomizeObject;
import com.github.gumihoy.diff.adt.DiffMapObject;
import com.github.gumihoy.diff.adt.DiffValueObject;
import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.annotation.Diff;
import com.github.gumihoy.diff.annotation.IDiffEnum;
import com.github.gumihoy.diff.config.DiffConfig;
import com.github.gumihoy.diff.enums.DiffCategory;
import com.github.gumihoy.diff.enums.DiffObjectType;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-02
 */
@Slf4j
public abstract class DiffUtils {

    /**
     * Diff object field filter.
     */
    protected static final Predicate<? super Field> FIELD_FILTER = field -> {
        return DiffAnnotationUtils.getAnnotation(field) != null;
    };

    private static <T> Class<?> getDiffObjectClass(T source, T target) {
        return source != null ? source.getClass() : target.getClass();
    }

    public static <T> IDiffObject diff(T source, T target) {
        return diff(source, target, null);
    }

    public static <T> IDiffObject diff(T source, T target, DiffConfig config) {
        if (source == null
                && target == null) {
            return null;
        }

        if (config == null) {
            config = new DiffConfig();
        }

        Class<?> clazz = getDiffObjectClass(source, target);

        DiffObjectType diffObjectType = DiffObjectType.of(clazz);

        return dispatch(diffObjectType, source, target, config);
    }


    public static <T> boolean is(T t) {
        t.getClass().isEnum();
        return false;
    }


    private static <T> IDiffObject dispatch(DiffObjectType diffObjectType, T source, T target, DiffConfig config) {
        switch (diffObjectType) {
            case PRIMITIVE:
                return diffPrimitive(source, target, config);

            case ENUM:
                return diffEnum(source, target, config);

            case ARRAY:
                return DiffArrayUtils.diff((Object[]) source, (Object[]) target, config);

            case COLLECTION:
                return DiffCollectionUtils.diff((Collection<?>) source, (Collection<?>) target, config);

            case MAP:
                return DiffMapUtils.diff((Map) source, (Map) target, config);

            case JAVA:
                return diffJava(source, target, config);

            default:
                return diffCustom(source, target, config);
        }
    }

    public static <T> IDiffObject diffField(T source, T target, AnnotationField annotationField, DiffConfig config) throws Exception {

        DiffObjectType diffFiledType = DiffObjectType.of(annotationField.field);

        switch (diffFiledType) {
            case PRIMITIVE:
                return diffPrimitive(source, target, annotationField, config);

            case ENUM:
                return diffEnum(source, target, annotationField, config);

            case ARRAY:
                return diffArray(source, target, annotationField, config);

            case COLLECTION:
                return diffCollection(source, target, annotationField, config);

            case MAP:
                return diffMap(source, target, annotationField, config);

            case JAVA:
                return diffJava(source, target, annotationField, config);

            default:
                return diffCustom(source, target, annotationField, config);
        }

    }

    public static <T> DiffCollectionObject diff(Collection<T> source, Collection<T> target) {
        if (source == null
                && target == null) {
            return null;
        }
        return null;
    }

    public static <K, V> DiffCollectionObject diff(Map<K, V> source, Map<K, V> target) {
        if (source == null
                && target == null) {
            return null;
        }
        return null;
    }

    private static <T> void setFieldAccessible(T source, T target, Field field) {
        if (field != null
                && ((source != null && !field.canAccess(source)) || (target != null && !field.canAccess(target)))) {
            field.setAccessible(true);
        }
    }

    private static <T> DiffValueObject diffPrimitive(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }

    public static <T> DiffValueObject diffPrimitive(T source, T target, final AnnotationField annotationField, DiffConfig config)
            throws IllegalArgumentException, IllegalAccessException {

        final Annotation annotation = annotationField.annotation;
        final Field field = annotationField.field;

        setFieldAccessible(source, target, field);

        Object sourceFieldValue = null;
        Object targetFieldValue = null;

        if (source != null) {
            sourceFieldValue = field.get(source);
        }
        if (target != null) {
            targetFieldValue = field.get(target);
        }

        if (sourceFieldValue == null
                && targetFieldValue == null) {
            return null;
        }

        if (DiffAnnotationUtils.isCompare(annotation)) {
            boolean equals = Objects.equals(sourceFieldValue, targetFieldValue);
            if (equals) {
                return null;
            }
        }

        DiffValueObject diff = new DiffValueObject();
        diff.setAnnotation(annotation);
        diff.setDiffFiledType(DiffObjectType.PRIMITIVE);
        diff.setName(field.getName());
        diff.setClassName(field.getType().getName());

        diff.setSourceValue(sourceFieldValue);
        diff.setTargetValue(targetFieldValue);

        return diff;
    }

    public static DiffValueObject dispatchDiffPrimitive(Object source, Object target) {
        return null;
    }


    private static DiffValueObject diffBoolPrimitive(Object source, Object target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }

    private static <T> DiffValueObject diffCharPrimitive(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }

    private static <T> DiffValueObject diffIntegerPrimitive(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }

    private static <T> DiffValueObject diffFloatPrimitive(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }


    private static <T> DiffValueObject diffEnum(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }


    private static <T> DiffValueObject diffEnum(T source, T target, AnnotationField annotationField, DiffConfig config)
            throws IllegalArgumentException, IllegalAccessException {

        Field field = annotationField.field;

        setFieldAccessible(source, target, field);

        IDiffEnum<?> sourceFieldValue = null;
        IDiffEnum<?> targetFieldValue = null;

        if (source != null) {
            sourceFieldValue = (IDiffEnum<?>) field.get(source);
        }
        if (target != null) {
            targetFieldValue = (IDiffEnum<?>) field.get(target);
        }

        if (DiffAnnotationUtils.isCompare(annotationField.annotation)) {
            boolean equals = Objects.equals(sourceFieldValue, targetFieldValue);
            if (equals) {
                return null;
            }
        }

        DiffValueObject diff = new DiffValueObject();
        diff.setName(field.getName());

        if (sourceFieldValue != null) {
            diff.setSourceValue(sourceFieldValue);
        }
        if (targetFieldValue != null) {
            diff.setTargetValue(targetFieldValue);
        }


        return diff;
    }

    private static <T> IDiffObject diffArray(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }

    public static <T> IDiffObject diffArray(T source, T target, AnnotationField annotationField, DiffConfig config)
            throws IllegalArgumentException, IllegalAccessException {
        final Annotation annotation = annotationField.annotation;
        final Field field = annotationField.field;

        setFieldAccessible(source, target, field);

        Object[] sourceFieldValue = null;
        Object[] targetFieldValue = null;

        if (source != null) {
            sourceFieldValue = (Object[]) field.get(source);
        }
        if (target != null) {
            targetFieldValue = (Object[]) field.get(target);
        }

        if (sourceFieldValue == null
                && targetFieldValue == null) {
            return null;
        }

        DiffArrayObject diff = DiffArrayUtils.diff(sourceFieldValue, targetFieldValue, config);
        if (diff == null) {
            return null;
        }

        diff.setAnnotation(annotation);
        diff.setName(field.getName());
        diff.setClassName(field.getClass().getName());

        return diff;
    }

    private static <T> IDiffObject diffCollection(T source, T target, AnnotationField annotationField, DiffConfig config)
            throws IllegalArgumentException, IllegalAccessException {

        final Annotation annotation = annotationField.annotation;
        final Field field = annotationField.field;

        setFieldAccessible(source, target, field);

        Collection<?> sourceFieldValue = null;
        Collection<?> targetFieldValue = null;

        if (source != null) {
            sourceFieldValue = (Collection<?>) field.get(source);
        }
        if (target != null) {
            targetFieldValue = (Collection<?>) field.get(target);
        }

        if (sourceFieldValue == null
                && targetFieldValue == null) {
            return null;
        }

        DiffCollectionObject diff = DiffCollectionUtils.diff(sourceFieldValue, targetFieldValue, config);
        if (diff == null) {
            return null;
        }

        diff.setAnnotation(annotation);
        diff.setName(field.getName());
        diff.setClassName(field.getClass().getName());

        return diff;
    }

    public static <T> IDiffObject diffMap(T source, T target, AnnotationField annotationField, DiffConfig config)
            throws IllegalArgumentException, IllegalAccessException {
        final Annotation annotation = annotationField.annotation;
        final Field field = annotationField.field;

        setFieldAccessible(source, target, field);

        Map sourceFieldValue = null;
        Map targetFieldValue = null;

        if (source != null) {
            sourceFieldValue = (Map) field.get(source);
        }
        if (target != null) {
            targetFieldValue = (Map) field.get(target);
        }

        if (sourceFieldValue == null
                && targetFieldValue == null) {
            return null;
        }

        if (DiffAnnotationUtils.isCompare(annotation)) {
            boolean equals = Objects.equals(sourceFieldValue, targetFieldValue);
            if (equals) {
                return null;
            }
        }

        DiffMapObject diff = DiffMapUtils.diff(sourceFieldValue, targetFieldValue, config);
        if (diff == null) {
            return null;
        }

        diff.setAnnotation(annotation);
        diff.setName(field.getName());
        diff.setClassName(field.getClass().getName());

        return diff;
    }

    private static <T> IDiffObject diffJava(T source, T target, DiffConfig config) {
        return getDiffObject(source, target, config);
    }

    public static <T> IDiffObject diffJava(T source, T target, AnnotationField annotationField, DiffConfig config) throws Exception {

        setFieldAccessible(source, target, annotationField.field);

        Object sourceFieldValue = null;
        Object targetFieldValue = null;

        if (source != null) {
            sourceFieldValue = annotationField.field.get(source);
        }
        if (target != null) {
            targetFieldValue = annotationField.field.get(target);
        }

        if (sourceFieldValue == null
                && targetFieldValue == null) {
            return null;
        }

        if (DiffAnnotationUtils.isCompare(annotationField.annotation)) {
            boolean equals = Objects.equals(sourceFieldValue, targetFieldValue);
            if (equals) {
                return null;
            }
        }

        DiffValueObject diff = new DiffValueObject(annotationField.annotation);
        diff.setName(annotationField.field.getName());
        diff.setSourceValue(sourceFieldValue);
        diff.setTargetValue(targetFieldValue);

        return diff;
    }

    private static <T> DiffCustomizeObject diffCustom(T source, T target, DiffConfig config) {
        if (source == null
                && target == null) {
            return null;
        }

        DiffCustomizeObject diffObject = new DiffCustomizeObject();

        Class<?> clazz = source != null ? source.getClass() : target.getClass();
        List<Field> fields = ReflectionUtils.getAllFields(clazz, FIELD_FILTER);

        fields.forEach(field -> {
            try {
                Annotation annotation = DiffAnnotationUtils.getAnnotation(field);
                AnnotationField annotationField = AnnotationField.of(annotation, field);
                IDiffObject fieldDiff = diffField(source, target, annotationField, config);
                if (fieldDiff != null) {
                    fieldDiff.setAnnotation(annotation);
                    fieldDiff.setName(field.getName());
                    fieldDiff.setClassName(field.getType().getName());
                }

                diffObject.addFieldDiff(fieldDiff);
            } catch (Exception e) {
                log.error("", e);
                e.printStackTrace();
            }

        });

        // nothing diff
        if (diffObject.getFieldDiffs() == null
                || diffObject.getFieldDiffs().isEmpty()) {
            return null;
        }

        if (source == null) {
            diffObject.setCategory(DiffCategory.CREATE);
        } else if (target == null) {
            diffObject.setCategory(DiffCategory.DELETE);
        } else {
            diffObject.setCategory(DiffCategory.UPDATE);
        }

        Diff diffAnnotation = clazz.getAnnotation(Diff.class);
        if (diffAnnotation != null) {
            diffObject.setAnnotation(diffAnnotation);
        }

        return diffObject;
    }

    public static <T> IDiffObject diffCustom(T source, T target, AnnotationField annotationField, DiffConfig config) throws Exception {

        Field field = annotationField.field;
        setFieldAccessible(source, target, field);

        IDiffObject diff = DiffUtils.diff(field.get(source), field.get(target));
        if (diff != null) {
            diff.setName(field.getName());
        }

        return diff;
    }


    private static <T> DiffValueObject getDiffObject(T source, T target, DiffConfig config) {
        if (source == null
                && target == null) {
            return null;
        }

        boolean equals = Objects.equals(source, target);
        if (equals) {
            return null;
        }
        DiffValueObject diff = new DiffValueObject();
        diff.setSourceValue(source);
        diff.setTargetValue(target);

        return diff;
    }


    private static class AnnotationField {
        private final Annotation annotation;
        private final Field field;

        public static AnnotationField of(Annotation annotation, Field field) {
            return new AnnotationField(annotation, field);
        }

        private AnnotationField(Annotation annotation, Field field) {
            this.annotation = annotation;
            this.field = field;
        }

        public Annotation getAnnotation() {
            return annotation;
        }

        public Field getField() {
            return field;
        }
    }

}
