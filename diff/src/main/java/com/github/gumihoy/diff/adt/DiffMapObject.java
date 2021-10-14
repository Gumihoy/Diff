package com.github.gumihoy.diff.adt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.github.gumihoy.diff.enums.DiffObjectType;
import com.github.gumihoy.diff.visitor.IDiffObjectVisitor;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
public class DiffMapObject<K> extends AbstractDiffObject {

    private final List<DiffMapElement> diffs = new ArrayList<>();

    public DiffMapObject() {
        super(DiffObjectType.MAP);
    }

    public DiffMapObject(Annotation annotation) {
        super(annotation, DiffObjectType.MAP);
    }

    @Override
    protected void accept0(IDiffObjectVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, diffs);
        }
    }

    public boolean addDiff(K key, IDiffObject diff) {
        if (diff == null) {
            return false;
        }
        DiffMapElement<K> element = new DiffMapElement<>(key, diff);
        return addDiff(element);
    }

    public boolean addDiff(DiffMapElement<K> diff) {
        if (diff == null) {
            return false;
        }
        return diffs.add(diff);
    }

    public List<DiffMapElement> getDiffs() {
        return diffs;
    }


    public static class DiffMapElement<K> extends AbstractDiffObject {

        private K key;
        private IDiffObject valueDiff;

        public DiffMapElement(K key, IDiffObject valueDiff) {
            setKey(key);
            setValueDiff(valueDiff);
        }

        @Override
        protected void accept0(IDiffObjectVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, valueDiff);
            }
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public IDiffObject getValueDiff() {
            return valueDiff;
        }

        public void setValueDiff(IDiffObject valueDiff) {
            this.valueDiff = valueDiff;
            setCategory(valueDiff.getCategory());
        }
    }

}
