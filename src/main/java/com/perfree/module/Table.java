package com.perfree.module;

import java.util.List;

/**
 * 表
 */
public class Table {
    // 表名
    private String tableName;
    // 表注释
    private String tableComments;
    // 表字段
    private List<TableField> tableFields;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComments() {
        return tableComments;
    }

    public void setTableComments(String tableComments) {
        this.tableComments = tableComments;
    }

    public List<TableField> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<TableField> tableFields) {
        this.tableFields = tableFields;
    }
}
