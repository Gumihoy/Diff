package com.github.gumihoy.diff.adt;

import java.lang.annotation.Annotation;
import java.util.List;

import com.github.gumihoy.diff.enums.DiffAnnotationType;
import com.github.gumihoy.diff.enums.DiffCategory;
import com.github.gumihoy.diff.enums.DiffObjectType;
import com.github.gumihoy.diff.visitor.IDiffObjectVisitor;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
public abstract class AbstractDiffObject implements IDiffObject {

    protected DiffCategory category;
    protected DiffAnnotationType annotationType;
    protected Annotation annotation;

    protected DiffObjectType diffFiledType;
    protected String name;
    protected String className;

    public AbstractDiffObject() {
    }

    public AbstractDiffObject(Annotation annotation) {
        this.annotation = annotation;
    }

    public AbstractDiffObject(DiffObjectType diffFiledType) {
        this.diffFiledType = diffFiledType;
    }

    public AbstractDiffObject(Annotation annotation, DiffObjectType diffFiledType) {
        this.annotation = annotation;
        this.diffFiledType = diffFiledType;
    }

    public void accept(IDiffObjectVisitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("visitor is null.");
        }
        visitor.beforeVisit(this);

        accept0(visitor);

        visitor.afterVisit(this);
    }

    protected abstract void accept0(IDiffObjectVisitor visitor);

    protected final void acceptChild(IDiffObjectVisitor visitor, List<? extends IDiffObject> children) {
        if (children == null) {
            return;
        }

        for (IDiffObject child : children) {
            acceptChild(visitor, child);
        }
    }

    protected final void acceptChild(IDiffObjectVisitor visitor, IDiffObject child) {
        if (child == null) {
            return;
        }

        child.accept(visitor);
    }

    @Override
    public DiffCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(DiffCategory category) {
        this.category = category;
    }


    public DiffAnnotationType getAnnotationType() {
        return annotationType;
    }

    @Override
    public Annotation getAnnotation() {
        return annotation;
    }

    @Override
    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        this.annotationType = DiffAnnotationType.of(annotation);
    }

    @Override
    public DiffObjectType getDiffFiledType() {
        return diffFiledType;
    }

    @Override
    public void setDiffFiledType(DiffObjectType diffFiledType) {
        this.diffFiledType = diffFiledType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

}
