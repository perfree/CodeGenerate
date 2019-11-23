package com.perfree.generate;

import java.util.List;

/**
 * module类
 */
public class ModuleClass {
    // 所属包名
    private String packageName;
    // 类名
    private String className;
    // 类注释
    private String comment;
    // 作者
    private String author;
    // 字段的集合
    private List<ModuleField> fieldList;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<ModuleField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ModuleField> fieldList) {
        this.fieldList = fieldList;
    }
}
