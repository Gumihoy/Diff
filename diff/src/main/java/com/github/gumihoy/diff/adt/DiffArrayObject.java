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
public class DiffArrayObject extends AbstractDiffObject {

    private List<DiffArrayElement> diffs = new ArrayList<>();

    public DiffArrayObject() {
        super(DiffObjectType.ARRAY);
    }

    public DiffArrayObject(Annotation annotation) {
        super(annotation, DiffObjectType.ARRAY);
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
        DiffArrayElement element = null;
        if (diff instanceof DiffArrayElement) {
            element = (DiffArrayElement) diff;
        } else {
            element = new DiffArrayElement(diff);
        }
        return addDiff(element);
    }

    public boolean addDiff(DiffArrayElement diff) {
        if (diff == null) {
            return false;
        }
        return diffs.add(diff);
    }

    public List<DiffArrayElement> getDiffs() {
        return diffs;
    }

    public static class DiffArrayElement extends AbstractDiffObject {
        private IDiffObject diff;

        public DiffArrayElement(IDiffObject diff) {
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
