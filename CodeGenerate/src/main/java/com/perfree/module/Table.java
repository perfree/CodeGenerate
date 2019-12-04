package com.perfree.module;

import com.perfree.common.StringUtils;

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
    // 表名首字母大写
    private String tableNameUpperFirstLetter;
    // 表名首字母小写
    private String tableNameLowerFirstLetter;

    public String getTableNameUpperFirstLetter() {
        return StringUtils.strUpperFirst(this.tableName);
    }

    public String getTableNameLowerFirstLetter() {
        return StringUtils.strLowerFirst(this.tableName);
    }

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
