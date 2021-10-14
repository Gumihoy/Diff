package com.github.gumihoy.diff.visitor;

import com.github.gumihoy.diff.adt.DiffCustomizeObject;
import com.github.gumihoy.diff.adt.DiffValueObject;
import com.github.gumihoy.diff.util.DiffAnnotationUtils;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-08
 */
public class DiffObjectHTMLOutputVisitor extends AbstractDiffObjectOutputVisitor {

    public DiffObjectHTMLOutputVisitor(StringBuilder appender) {
        super(appender);
    }

    @Override
    public boolean visit(DiffValueObject diff) {
        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name != null) {
            printH1HTML(name + ":");
        } else {
            printH1HTML(diff.getName() + ":");
        }

        printSpaceAndValueHTML(diff.getSourceValue());
        printSpaceAndValue("->");
        printSpaceAndValueHTML(diff.getTargetValue());
        return false;
    }

    @Override
    public boolean visit(DiffCustomizeObject diff) {
        String name = DiffAnnotationUtils.name(diff.getAnnotation());
        if (name != null) {
            printH1HTML(name);

            incrementIndent();
            println();
        }

        printAccept(diff.getFieldDiffs(), true);

        if (name != null) {
            decrementIndent();
        }
        return false;
    }


}
