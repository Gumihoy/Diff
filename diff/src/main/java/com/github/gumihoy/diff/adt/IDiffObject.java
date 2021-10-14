package com.github.gumihoy.diff.adt;

import java.lang.annotation.Annotation;

import com.github.gumihoy.diff.enums.DiffCategory;
import com.github.gumihoy.diff.enums.DiffObjectType;
import com.github.gumihoy.diff.visitor.IDiffObjectVisitor;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
public interface IDiffObject {

    void accept(IDiffObjectVisitor visitor);

    DiffCategory getCategory();

    void setCategory(DiffCategory category);

    Annotation getAnnotation();

    void setAnnotation(Annotation annotation);


    DiffObjectType getDiffFiledType();

    void setDiffFiledType(DiffObjectType diffFiledType);

    String getName();

    void setName(String name);

    String getClassName();

    void setClassName(String className);
}
