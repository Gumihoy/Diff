package com.github.gumihoy.diff.util;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import com.github.gumihoy.diff.adt.DiffCollectionObject;
import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.config.DiffConfig;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-10-13
 */
public abstract class DiffCollectionUtils {

    public static DiffCollectionObject diff(Collection<?> sources, Collection<?> targets) {
        return diff(sources, targets, null);
    }

    public static DiffCollectionObject diff(Collection<?> sources, Collection<?> targets, DiffConfig config) {
        if ((sources == null
                || sources.isEmpty())
                && (targets == null
                || targets.isEmpty())) {
            return null;
        }

        DiffCollectionObject diffObject = new DiffCollectionObject();
        // delete/update
        if (sources != null) {
            sources.forEach(source -> {
                Optional<?> optional = findFirst(targets, source);
                IDiffObject diff = DiffUtils.diff(source, optional.orElse(null), config);
                diffObject.addDiff(diff);
            });
        }

        // add
        if (targets != null) {
            targets.forEach(target -> {
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


    private static <T> Optional<T> findFirst(Collection<T> collection, Object element) {
        if (collection == null
                || collection.isEmpty()
                || element == null) {
            return Optional.empty();
        }
        return collection.stream().filter(e -> Objects.equals(e, element)).findFirst();
    }

}
