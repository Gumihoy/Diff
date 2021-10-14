package com.github.gumihoy.diff.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.github.gumihoy.diff.adt.DiffArrayObject;
import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.config.DiffConfig;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-10-13
 */
public abstract class DiffArrayUtils {

    public static <T> DiffArrayObject diff(T[] sources, T[] targets) {
        return diff(sources, targets, null);
    }

    public static <T> DiffArrayObject diff(T[] sources, T[] targets, DiffConfig config) {
        if ((sources == null
                || sources.length == 0)
                && (targets == null
                || targets.length == 0)) {
            return null;
        }

        DiffArrayObject diffObject = new DiffArrayObject();
        // delete/update
        if (sources != null) {
            Arrays.stream(sources).forEach(source -> {
                Optional<?> optional = findFirst(targets, source);
                IDiffObject diff = DiffUtils.diff(source, optional.orElse(null), config);
                diffObject.addDiff(diff);
            });
        }

        // add
        if (targets != null) {
            Arrays.stream(targets).forEach(target -> {
                Optional<?> optional = findFirst(sources, target);
                IDiffObject diff = DiffUtils.diff(optional.orElse(null), target, config);
                diffObject.addDiff(diff);
            });
        }

        if (diffObject.getDiffs() == null
                || diffObject.getDiffs().isEmpty()) {
            return null;
        }
        return diffObject;
    }


    private static <T> Optional<T> findFirst(T[] arrays, T element) {
        if (arrays == null
                || arrays.length == 0
                || element == null) {
            return Optional.empty();
        }
        return Arrays.stream(arrays).filter(e -> Objects.equals(e, element)).findFirst();
    }

}
