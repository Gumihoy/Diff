package com.github.gumihoy.diff.util;

import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.enums.DiffFormatType;
import com.github.gumihoy.diff.visitor.DiffObjectHTMLOutputVisitor;
import com.github.gumihoy.diff.visitor.DiffObjectIndentOutputVisitor;
import com.github.gumihoy.diff.visitor.IDiffObjectOutputVisitor;
import com.github.gumihoy.diff.visitor.IDiffObjectVisitor;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-19
 */
public final class DiffFormatUtils {

    public static String format(String diff) {
        if (diff == null) {
            return null;
        }

        return null;
    }

    public static String formatHTML(String diff) {
        if (diff == null) {
            return null;
        }

        return null;
    }

    public static String toIdent(IDiffObject diffObject) {
        if (diffObject == null) {
            return null;
        }
        return toString(diffObject, DiffFormatType.INDENT);
    }

    public static String toHTML(IDiffObject diffObject) {
        if (diffObject == null) {
            return null;
        }
        return toString(diffObject, DiffFormatType.HTML);
    }

    public static String toString(IDiffObject diffObject, DiffFormatType formatType) {
        if (diffObject == null) {
            return null;
        }
        StringBuilder appender = new StringBuilder();

        IDiffObjectVisitor visitor = createOutputVisitor(appender, formatType);
        diffObject.accept(visitor);

        return appender.toString();
    }

    private static IDiffObjectOutputVisitor createOutputVisitor(StringBuilder appender, DiffFormatType formatType) {
        switch (formatType) {
            case INDENT:
                return createIndentOutputVisitor(appender);
            case HTML:
                return createHTMLOutputVisitor(appender);
        }
        throw new UnsupportedOperationException("Unsupported Operation formatType:" + formatType.name());
    }

    private static DiffObjectIndentOutputVisitor createIndentOutputVisitor(StringBuilder appender) {
        return new DiffObjectIndentOutputVisitor(appender);
    }

    private static DiffObjectHTMLOutputVisitor createHTMLOutputVisitor(StringBuilder appender) {
        return new DiffObjectHTMLOutputVisitor(appender);
    }

}
