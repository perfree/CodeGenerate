package com.perfree.module;

import java.util.Date;
import java.util.List;

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
    // 创建时间
    private Date createTime;
    // 要导的包
    private List<String> packages;

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

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

    public Date getCreateTime() {
        return new Date();
    }
}
