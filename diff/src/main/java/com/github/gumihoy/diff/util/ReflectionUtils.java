package com.github.gumihoy.diff.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-19
 */
public abstract class ReflectionUtils {
    /**
     * Cache for {@link Class#getDeclaredFields()}, allowing for fast iteration.
     */
    private static final Map<Class<?>, List<Field>> DECLARED_FIELDS_CACHE = new ConcurrentReferenceHashMap<>(256);

    private static final List<Field> EMPTY_FIELD_ARRAY = new ArrayList<>(0);


    public static List<Field> getAllFields(Class<?> clazz, @Nullable Predicate<? super Field> fieldFilter) {
        List<Field> findFields = new ArrayList<>();
        Class<?> targetClass = clazz;
        do {
            List<Field> fields = getDeclaredFields(targetClass);
            Stream<Field> stream = fields.stream();
            if (fieldFilter != null) {
                stream = stream.filter(fieldFilter);
            }
            stream.forEach(findFields::add);

            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        return findFields;
    }


    /**
     * This variant retrieves {@link Class#getDeclaredFields()} from a local cache
     * in order to avoid the JVM's SecurityManager check and defensive array copying.
     *
     * @param clazz the class to introspect
     * @return the cached array of fields
     * @throws IllegalStateException if introspection fails
     * @see Class#getDeclaredFields()
     */
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");

        if (DECLARED_FIELDS_CACHE.get(clazz) != null) {
            return DECLARED_FIELDS_CACHE.get(clazz);
        }

        try {
            Field[] declaredFields = clazz.getDeclaredFields();

            List<Field> finalResult = new ArrayList<>(declaredFields.length);

            finalResult.addAll(Arrays.asList(declaredFields));

            DECLARED_FIELDS_CACHE.put(clazz, (finalResult.isEmpty() ? EMPTY_FIELD_ARRAY : finalResult));

            return finalResult;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to introspect Class [" + clazz.getName()
                    + "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }
    }


    /**
     * Field is Primitive Type
     *
     * @param field Field
     */
    public static boolean isPrimitive(Field field) {
        if (field == null) {
            return false;
        }
        return isPrimitive(field.getType());
    }

    public static boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        return clazz.isPrimitive();
    }

    /**
     * Field is Enum Type
     *
     * @param field Field
     */
    public static boolean isEnum(Field field) {
        if (field == null) {
            return false;
        }
        return isEnum(field.getType());
    }

    /**
     * clazz is Enum Type
     *
     * @param clazz Class
     */
    public static boolean isEnum(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isEnum();
    }

    /**
     * Field is Array Type
     *
     * @param field Field
     */
    public static boolean isArray(Field field) {
        if (field == null) {
            return false;
        }
        return isArray(field.getType());
    }

    /**
     * clazz is Array Type
     *
     * @param clazz Class
     */
    public static boolean isArray(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isArray();
    }

    /**
     * Field is Collection Class Type
     *
     * @param field Field
     */
    public static boolean isCollection(Field field) {
        if (field == null) {
            return false;
        }
        return isCollection(field.getType());
    }

    public static boolean isCollection(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * Field is Map Class Type
     *
     * @param field Field
     */
    public static boolean isMap(Field field) {
        if (field == null) {
            return false;
        }
        return isMap(field.getType());
    }

    public static boolean isMap(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return Map.class.isAssignableFrom(clazz);
    }


    /**
     * Field is Java Class Type
     *
     * @param field Field
     */
    public static boolean isJava(Field field) {
        if (field == null) {
            return false;
        }
        return isJava(field.getType());
    }

    public static boolean isJava(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.getClassLoader() == null;
    }

    @FunctionalInterface
    public interface FieldCallback {
        void callback(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    @FunctionalInterface
    public interface FieldFilter {
        boolean filter(Field field);
    }

}
