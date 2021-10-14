package com.github.gumihoy.diff.visitor;

import static com.github.gumihoy.diff.constant.Constants.CHINESE_LEFT_BRACKET;
import static com.github.gumihoy.diff.constant.Constants.CHINESE_RIGHT_BRACKET;
import static com.github.gumihoy.diff.constant.Constants.COLON;

import com.github.gumihoy.diff.adt.DiffArrayObject;
import com.github.gumihoy.diff.adt.DiffCollectionObject;
import com.github.gumihoy.diff.adt.DiffCustomizeObject;
import com.github.gumihoy.diff.adt.DiffMapObject;
import com.github.gumihoy.diff.adt.DiffValueObject;
import com.github.gumihoy.diff.util.DiffAnnotationUtils;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-08
 */
public class DiffObjectIndentOutputVisitor extends AbstractDiffObjectOutputVisitor {


    public DiffObjectIndentOutputVisitor(StringBuilder appender) {
        super(appender);
    }

    @Override
    public boolean visit(DiffValueObject diff) {
        if (!DiffAnnotationUtils.isShow(diff.getAnnotation())) {
            return false;
        }

        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name == null) {
            name = diff.getName();
        }
        if (name != null) {
            print(name);
            print(COLON);
            printSpace();
        }

        print(diff.getShowSource());
        printSpaceAndValue("->");
        printSpaceAndValue(diff.getShowTarget());
        return false;
    }

    @Override
    public boolean visit(DiffCustomizeObject diff) {
        if (!DiffAnnotationUtils.isShow(diff.getAnnotation())) {
            return false;
        }

        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name != null) {
            print(name);

            incrementIndent();
            println();
        }

        printAccept(diff.getFieldDiffs(), true);

        if (name != null) {
            decrementIndent();
        }
        return false;
    }

    @Override
    public boolean visit(DiffArrayObject diff) {
        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name != null) {
            print(name);

            incrementIndent();
            println();
        }

        printAccept(diff.getDiffs(), true);

        if (name != null) {
            decrementIndent();
        }
        return false;
    }

    @Override
    public boolean visit(DiffArrayObject.DiffArrayElement diff) {
        if (diff.getCategory() != null) {
            print(CHINESE_LEFT_BRACKET);
            print(diff.getCategory());
            print(CHINESE_RIGHT_BRACKET);
        }
        diff.getDiff().accept(this);
        return false;
    }

    @Override
    public boolean visit(DiffCollectionObject diff) {
        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name != null) {
            print(name);

            incrementIndent();
            println();
        }

        printAccept(diff.getDiffs(), true);

        if (name != null) {
            decrementIndent();
        }
        return false;
    }

    @Override
    public boolean visit(DiffCollectionObject.DiffCollectionElement diff) {
        if (diff.getCategory() != null) {
            print(CHINESE_LEFT_BRACKET);
            print(diff.getCategory());
            print(CHINESE_RIGHT_BRACKET);
        }
        diff.getDiff().accept(this);
        return false;
    }

    @Override
    public boolean visit(DiffMapObject diff) {
        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name != null) {
            print(name);

            incrementIndent();
            println();
        }

        printAccept(diff.getDiffs(), true);

        if (name != null) {
            decrementIndent();
        }
        return false;
    }

    @Override
    public boolean visit(DiffMapObject.DiffMapElement diff) {
        if (diff.getCategory() != null) {
            print(CHINESE_LEFT_BRACKET);
            print(diff.getCategory());
            print(CHINESE_RIGHT_BRACKET);
        }

        print(diff.getKey());

        incrementIndent();
        println();

        diff.getValueDiff().accept(this);

        decrementIndent();
        return false;
    }
}
