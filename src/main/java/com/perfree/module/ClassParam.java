package com.perfree.module;

import java.util.Date;

/**
 * 类参数
 */
public class ClassParam {
    // 所属包名
    private String packageName;
    // 类名
    private String className;
    // 类描述
    private String description;
    // 作者
    private String author;
    // 创建时间
    private Date createTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return new Date();
    }
}
