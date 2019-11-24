package com.perfree.module;

import com.perfree.common.DbTypeToJavaTyoeUtils;
import com.perfree.common.StringUtils;

/**
 * 表字段
 */
public class TableField {
    // 表名
    private String tableName;
    // 字段名
    private String fieldName;
    // 字段类型
    private String  fieldType;
    // 字段长度
    private String  fieldLength;
    // 字段是否可为空
    private String  fieldNullable;
    // 字段注释
    private String  fieldComments;
    // 字段对应的java类型
    private String  fieldJavaType;
    // 字段名首字母大写
    private String  fieldNameUpperFirstLetter;
    // 字段名首字母小写
    private String  fieldNameLowerFirstLetter;

    public String getFieldJavaType() {
        return DbTypeToJavaTyoeUtils.getJavaType(this.fieldType);
    }

    public String getFieldNameLowerFirstLetter() {
        return StringUtils.strLowerFirst(this.fieldName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldNullable() {
        return fieldNullable;
    }

    public void setFieldNullable(String fieldNullable) {
        this.fieldNullable = fieldNullable;
    }

    public String getFieldComments() {
        return StringUtils.replaceWrap(this.fieldComments);
    }

    public void setFieldComments(String fieldComments) {
        this.fieldComments = fieldComments;
    }

    public String getFieldNameUpperFirstLetter() {
        return StringUtils.strUpperFirst(this.fieldName);
    }
}
