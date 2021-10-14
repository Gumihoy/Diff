package com.github.gumihoy.diff.enums;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-08
 */
public enum DiffCategory {
    CREATE("新增"),
    DELETE("删除"),
    UPDATE("修改"),
    ;
    private final String label;

    DiffCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
