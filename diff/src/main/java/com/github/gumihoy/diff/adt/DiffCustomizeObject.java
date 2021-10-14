package com.github.gumihoy.diff.adt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.github.gumihoy.diff.visitor.IDiffObjectVisitor;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
public class DiffCustomizeObject extends AbstractDiffObject {

    private List<IDiffObject> fieldDiffs;

    public DiffCustomizeObject() {
    }

    public DiffCustomizeObject(Annotation annotation) {
        super(annotation);
    }

    @Override
    protected void accept0(IDiffObjectVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, fieldDiffs);
        }
    }

    public boolean addFieldDiff(IDiffObject fieldDiff) {
        if (fieldDiff == null) {
            return false;
        }
        if (fieldDiffs == null) {
            fieldDiffs = new ArrayList<>();
        }
        return fieldDiffs.add(fieldDiff);
    }

    public List<IDiffObject> getFieldDiffs() {
        return fieldDiffs;
    }
}
