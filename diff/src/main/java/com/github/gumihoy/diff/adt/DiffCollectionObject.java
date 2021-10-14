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
public class DiffCollectionObject extends AbstractDiffObject {

    private List<DiffCollectionElement> diffs = new ArrayList<>();

    public DiffCollectionObject() {
        super(DiffObjectType.COLLECTION);
    }

    public DiffCollectionObject(Annotation annotation) {
        super(annotation, DiffObjectType.COLLECTION);
    }

    @Override
    protected void accept0(IDiffObjectVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, diffs);
        }
    }

    public boolean addDiff(IDiffObject diff) {
        if (diff == null) {
            return false;
        }
        DiffCollectionElement element = null;
        if (diff instanceof DiffCollectionElement) {
            element = (DiffCollectionElement) diff;
        } else {
            element = new DiffCollectionElement(diff);
        }
        return addDiff(element);
    }

    public boolean addDiff(DiffCollectionElement diff) {
        if (diff == null) {
            return false;
        }
        return diffs.add(diff);
    }

    public List<DiffCollectionElement> getDiffs() {
        return diffs;
    }

    public static class DiffCollectionElement extends AbstractDiffObject {
        private IDiffObject diff;

        public DiffCollectionElement(IDiffObject diff) {
            setDiff(diff);
        }

        @Override
        protected void accept0(IDiffObjectVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, diff);
            }
        }

        public IDiffObject getDiff() {
            return diff;
        }

        public void setDiff(IDiffObject diff) {
            this.diff = diff;
            setCategory(diff.getCategory());
        }
    }


}
