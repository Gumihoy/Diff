package com.github.gumihoy.diff.adt;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.gumihoy.diff.annotation.DiffDateTime;
import com.github.gumihoy.diff.annotation.DiffEnum;
import com.github.gumihoy.diff.annotation.DiffMoney;
import com.github.gumihoy.diff.annotation.DiffTimeStamp;
import com.github.gumihoy.diff.annotation.IDiffEnum;
import com.github.gumihoy.diff.enums.DiffCategory;
import com.github.gumihoy.diff.exception.DiffException;
import com.github.gumihoy.diff.util.DiffEnumUtils;
import com.github.gumihoy.diff.visitor.IDiffObjectVisitor;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
public class DiffValueObject extends AbstractDiffObject {

    protected Object sourceValue;
    protected Object targetValue;

    protected String showSource;
    protected String showTarget;

    public DiffValueObject() {
    }

    public DiffValueObject(Annotation annotation) {
        super(annotation);
    }


    @Override
    protected void accept0(IDiffObjectVisitor visitor) {
        visitor.visit(this);
    }

    public Object getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(Object sourceValue) {
        if (sourceValue == null) {
            return;
        }
        this.sourceValue = sourceValue;
        this.cascadeChangeDiffCategory();
        this.cascadeChangeShowSource();
    }

    public Object getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Object targetValue) {
        if (targetValue == null) {
            return;
        }
        this.targetValue = targetValue;
        this.cascadeChangeDiffCategory();
        this.cascadeChangeShowTarget();
    }


    public String getShowSource() {
        return showSource;
    }

    public String getShowTarget() {
        return showTarget;
    }

    private void cascadeChangeDiffCategory() {
        if (this.sourceValue == null && this.targetValue != null) {
            setCategory(DiffCategory.CREATE);
        } else if (this.sourceValue != null && this.targetValue == null) {
            setCategory(DiffCategory.DELETE);
        } else if (this.sourceValue != null && !this.sourceValue.equals(this.targetValue)) {
            setCategory(DiffCategory.UPDATE);
        }
    }

    private void cascadeChangeShowSource() {
        this.showSource = getShowValue(this.annotation, this.name, sourceValue);
    }

    private void cascadeChangeShowTarget() {
        this.showTarget = getShowValue(this.annotation, this.name, targetValue);
    }

    private static String getShowValue(Annotation annotation, String name, Object value) {
        if (value == null) {
            return null;
        }

        if (annotation instanceof DiffDateTime) {
            return getShowValueByDiffDateTime((DiffDateTime) annotation, name, value);

        } else if (annotation instanceof DiffTimeStamp) {
            return getShowValueByDiffTimeStamp((DiffTimeStamp) annotation, value);

        } else if (annotation instanceof DiffEnum) {
            return getShowValueByDiffEnum((DiffEnum) annotation, value);

        } else if (value instanceof IDiffEnum) {
            return ((IDiffEnum<?>) value).show();

        } else if (annotation instanceof DiffMoney) {
            return getShowValueByDiffMoney((DiffMoney) annotation, value);

        }

        return value.toString();
    }

    private static String getShowValueByDiffDateTime(DiffDateTime diffDateTime, String name, Object value) {
        if (value instanceof Number) {
            throw new DiffException("field: " + name + "(name =\"" + diffDateTime.name() + "\") annotation error.");
        }
        return format(diffDateTime.format(), TimeUnit.MILLISECONDS, ((Date) value).getTime());
    }

    private static String getShowValueByDiffTimeStamp(DiffTimeStamp diffTimeStamp, Object value) {
        return format(diffTimeStamp.format(), diffTimeStamp.unit(), (long) value);
    }

    private static String getShowValueByDiffEnum(DiffEnum diffEnum, Object value) {
        try {
            return DiffEnumUtils.getShowByValue(diffEnum.enumClass(), value);
        } catch (Exception e) {
            return value.toString();
        }
    }

    private static String getShowValueByDiffMoney(DiffMoney diffMoney, Object value) {
        return diffMoney.code().getSymbol() + diffMoney.unit().toBasic(value);
    }


    private static String format(String pattern, TimeUnit unit, long time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        Instant instant = Instant.ofEpochMilli(unit.toMillis(time));
        return formatter.format(instant);
    }
}
