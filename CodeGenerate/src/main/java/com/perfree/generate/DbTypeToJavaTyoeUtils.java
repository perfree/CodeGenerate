package com.perfree.generate;


import java.util.HashMap;

/**
 * 数据库类型转java类型工具类
 */
public class DbTypeToJavaTyoeUtils {
    // 类型定义
    private static HashMap<String, String> dbTypeDict = new HashMap<String, String>() {
        {
            // 字符串
            put("VARCHAR2", "String");
            put("CHAR", "String");
            put("CLOB", "String");
            // 日期
            put("DATE", "Date");
            // 数字
            put("NUMBER(9)", "Integer");
            put("NUMBER", "Long");
            put("FLOAT", "BigDecimal");
            // 其他
            put("BLOB", "Object");
        }
    };

    /**
     * 获取对应的java类型
     *
     * @param dbType 数据库类型
     * @return String java类型
     */
    public static String getJavaType(String dbType, String fieldLength) {
        String upperCase = dbType.toUpperCase();
        // 处理number类型
        if(upperCase.equals("NUMBER")){
            Integer length = Integer.valueOf(fieldLength);
            if(length <= 9 ){
                upperCase += "(9)";
            }
        }
        return dbTypeDict.get(upperCase);
    }
}
