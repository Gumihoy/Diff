package com.github.gumihoy.diff.util;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.github.gumihoy.diff.adt.DiffMapObject;
import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.config.DiffConfig;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-10-13
 */
public abstract class DiffMapUtils {


    public static <K, V> DiffMapObject diff(Map<K, V> sources, Map<K, V> targets, DiffConfig config) {
        if ((sources == null
                || sources.isEmpty())
                && (targets == null
                || targets.isEmpty())) {
            return null;
        }

        DiffMapObject diffObject = new DiffMapObject();

        // delete/update
        if (sources != null) {
            sources.forEach((sourceKey, sourceValue) -> {
                Optional<Map.Entry<K, V>> optional = findFirst(targets, sourceKey);
                IDiffObject diff = DiffUtils.diff(sourceValue, optional.map(Map.Entry::getValue).orElse(null), config);
                diffObject.addDiff(sourceKey, diff);
            });
        }

        // add
        if (targets != null) {
            targets.forEach((targetKey, targetValue) -> {
                Optional<Map.Entry<K, V>> optional = findFirst(sources, targetKey);
                IDiffObject diff = DiffUtils.diff(optional.map(Map.Entry::getValue).orElse(null), targetValue, config);
                diffObject.addDiff(targetKey, diff);
            });
        }

        if (diffObject.getDiffs() == null
                || diffObject.getDiffs().isEmpty()) {
            return null;
        }
        return diffObject;
    }

    private static <K, V> Optional<Map.Entry<K, V>> findFirst(Map<K, V> map, K key) {
        if (map == null
                || map.isEmpty()
                || key == null) {
            return Optional.empty();
        }
        return map.entrySet().stream().filter(entry -> Objects.equals(entry.getKey(), key)).findFirst();
    }
}
