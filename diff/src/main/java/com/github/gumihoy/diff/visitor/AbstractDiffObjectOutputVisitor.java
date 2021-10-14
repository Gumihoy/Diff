package com.github.gumihoy.diff.visitor;

import java.util.List;

import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.enums.DiffCategory;
import com.github.gumihoy.diff.util.DiffAnnotationUtils;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-08-24
 * @see DiffObjectIndentOutputVisitor
 */
public abstract class AbstractDiffObjectOutputVisitor implements IDiffObjectOutputVisitor, IPrintable {

    private StringBuilder appender;
    private long indentCount = 0L;

    public AbstractDiffObjectOutputVisitor(StringBuilder appender) {
        this.appender = appender;
    }

    @Override
    public void print(Object value) {
        if (value == null) {
            return;
        }
        appender.append(value);
    }

    public void print(DiffCategory value) {
        if (value == null) {
            return;
        }
        appender.append(value.getLabel());
    }

    public void printH1HTML(Object value) {
        if (value == null) {
            return;
        }
        appender.append("<h1>");
        appender.append(value);
        appender.append("</h1>");
    }

    public void printHTML(Object value) {
        if (value == null) {
            return;
        }
        appender.append("<p>");
        appender.append(value);
        appender.append("</p>");
    }

    public void printAccept(IDiffObject diffObject) {
        if (diffObject == null) {
            return;
        }
        diffObject.accept(this);
    }

    public void printAccept(List<? extends IDiffObject> diffObjects, boolean ln) {
        if (diffObjects == null
                || diffObjects.isEmpty()) {
            return;
        }
        for (int i = 0; i < diffObjects.size(); i++) {
            if (ln && i != 0 && DiffAnnotationUtils.isShow(diffObjects.get(i - 1).getAnnotation())) {
                println();
            }
            printAccept(diffObjects.get(i));
        }
    }

    public void printSpace() {
        appender.append(" ");
    }

    public void printSpaceAndValue(Object value) {
        if (value == null) {
            return;
        }
        appender.append(" ");
        appender.append(value);
    }

    public void printSpaceAndValueHTML(Object value) {
        if (value == null) {
            return;
        }
        appender.append(" ");
        appender.append("<p>");
        appender.append(value);
        appender.append("</p>");
    }

    public void println() {
        appender.append("\n");
        printIndent();
    }

    public void printlnAndAccept(List<IDiffObject> diffs, boolean incrementIndent) {
        if (diffs == null
                || diffs.isEmpty()) {
            return;
        }
        if (incrementIndent) {
            incrementIndent();
        }
        println();
        printIndent();
        diffs.forEach(diff -> diff.accept(this));

        if (incrementIndent) {
            decrementIndent();
        }
    }

    @Override
    public void printIndent() {
        for (int i = 0; i < this.indentCount; ++i) {
            this.appender.append('\t');
        }
    }

    public void decrementIndent() {
        this.indentCount--;
    }

    public void incrementIndent() {
        this.indentCount++;
    }
}
