package com.github.gumihoy.diff.visitor;

import com.github.gumihoy.diff.adt.DiffArrayObject;
import com.github.gumihoy.diff.adt.DiffCollectionObject;
import com.github.gumihoy.diff.adt.DiffCustomizeObject;
import com.github.gumihoy.diff.adt.DiffMapObject;
import com.github.gumihoy.diff.adt.DiffValueObject;
import com.github.gumihoy.diff.adt.IDiffObject;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-08
 */
public interface IDiffObjectVisitor {

    default boolean beforeVisit(IDiffObject diff) {
        return true;
    }

    default boolean afterVisit(IDiffObject diff) {
        return true;
    }

    default boolean visit(DiffValueObject diff) {
        return true;
    }

    default boolean visit(DiffCustomizeObject diff) {
        return true;
    }

    default boolean visit(DiffArrayObject diff) {
        return true;
    }
    default boolean visit(DiffArrayObject.DiffArrayElement diff) {
        return true;
    }

    default boolean visit(DiffCollectionObject diff) {
        return true;
    }
    default boolean visit(DiffCollectionObject.DiffCollectionElement diff) {
        return true;
    }

    default boolean visit(DiffMapObject diff) {
        return true;
    }

    default boolean visit(DiffMapObject.DiffMapElement diff) {
        return true;
    }

}
